package io.chazza.advancementapi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.Display.DisplayBuilder;
import io.chazza.advancementapi.Requirements.RequirementsBuilder;
import io.chazza.advancementapi.Rewards.RewardsBuilder;
import io.chazza.advancementapi.Trigger.TriggerBuilder;
import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;

/**
 * This API provides simple access and support for creating Advancements in
 * Minecraft. This API uses the builder pattern. For each element which is used
 * in this API there is a {@link Builder} class.
 * <p>
 * <b>Creating Advancements</b>
 * <p>
 * For creating call the {@link #builder(NamespacedKey)} method in the
 * {@link AdvancementAPI} class and start adding objects through the setters
 * (e.g. {@link AdvancementAPIBuilder#display(DisplayBuilder)
 * display(DisplayBuilder)},
 * {@link AdvancementAPIBuilder#trigger(TriggerBuilder)
 * trigger(TriggerBuilder)}. At the end of building your advancement call the
 * {@link AdvancementAPIBuilder#build() build()} method. This will build your
 * Advancement and give you the correct object.
 * <p>
 * <b>Working with Advancements</b>
 * <p>
 * On the Advancement you can access some basic actions for working with them as
 * {@link #grant(Player...)} and {@link #revoke(Player...)}. You can also save
 * them to your server world when using {@link #save(String)} and
 * {@link #delete(String)}. When saving with these method remember to restart or
 * reload your server to apply changes.
 * <p>
 * For direct access without restart use {@link #add()} and {@link #remove()}.
 * <p>
 * <b>Documentation and Q&amp;A</b>
 * <p>
 * Most of the classes and methods provide documentation and examples. If you
 * still miss something check out the documentation of @skylinerw on github (see
 * links down). Also googling helps a lot :)
 * <p>
 * For errors or question open a issue on
 * <a href="https://github.com/kaonashi97/AdvancementAPI">github</a>.
 * <p>
 * Thanks to <a href="https://github.com/skylinerw">@skylinerw</a> for
 * provinding such great documentation :)
 * 
 * @version 2.0.0
 * 
 * @author charliej - the very API
 * @author DiscowZombie - adopting for Builder-Pattern
 * @author 2008Choco - NamespacedKey support
 * @author GiansCode - small but useful changes
 * @author Ste3et_C0st - add/take advancement logic
 * @author PROgrammer_JARvis - rework and combining
 * @author ysl3000 - useful advice and bug-tracking at PullRequests/JUnit-Tests,
 * full Builder-Pattern support, Lombok
 * @author Kaonashi97 - Conditions
 * 
 * @see "https://github.com/skylinerw/guides/blob/master/java/advancements.md"
 * @see "https://github.com/kaonashi97/AdvancementAPI"
 */
public class AdvancementAPI implements Jsonable {
    private static final String LOG_PREFIX = "[AdvancementAPI] ";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private NamespacedKey id;
    private String parent;
    private DisplayBuilder display;
    private List<TriggerBuilder> triggers = new LinkedList<>();
    private RequirementsBuilder requirements;
    private RewardsBuilder rewards;

    private AdvancementAPI(NamespacedKey id, String parent, DisplayBuilder display, List<TriggerBuilder> triggers,
            RequirementsBuilder requirements, RewardsBuilder rewards) {
        this.id = id;
        this.parent = parent;
        this.display = display;
        this.triggers = triggers;
        this.requirements = requirements;
        this.rewards = rewards;
    }

    /**
     * Returns a {@link AdvancementAPIBuilder} for building
     * {@link AdvancementAPI}s.
     * 
     * @param id a {@link NamespacedKey}
     * @return the builder
     */
    public static AdvancementAPIBuilder builder(NamespacedKey id) {
        return new AdvancementAPIBuilder(id);
    }

    /**
     * Returns the advancement id.
     * 
     * @return the id
     */
    public NamespacedKey getId() {
        return id;
    }

    /**
     * Returns the parent advancement id.
     * 
     * @return the id or <code>null</code> if this is a root advancement
     */
    public String getParent() {
        return parent;
    }

    @Override
    public JsonElement toJson() {
        JsonObject advancementObj = new JsonObject();

        //@formatter:off
        if (parent != null) advancementObj.addProperty("parent", parent);
        if (display != null) advancementObj.add("display", display.build().toJson());
        //@formatter:on

        JsonObject criteria = new JsonObject();
        triggers.forEach(triggerBuilder -> {
            Trigger trigger = triggerBuilder.build();
            criteria.add(trigger.getJsonKey(), trigger.toJson());
        });
        advancementObj.add("criteria", criteria);

        //@formatter:off
        if (requirements != null) advancementObj.add("requirements", requirements.build().toJson());
        if (rewards != null) advancementObj.add("rewards", rewards.build().toJson());
        //@formatter:on

        return advancementObj;
    }

    /**
     * Returns the Json of this advancement.
     * 
     * @return the Json String
     */
    public String getJson() {
        return gson.toJson(toJson());
    }

    /**
     * Adds the advancement to the server.
     * 
     * @return this advancement
     */
    public AdvancementAPI add() {
        if (add0()) {
            Bukkit.getLogger().info(() -> LOG_PREFIX + "Successfully registered advancement \"" + id + "\".");
            return this;
        }
        Bukkit.getLogger().severe(() -> LOG_PREFIX + "Error registering advancement \"" + id + "\".");
        return this;
    }

    @SuppressWarnings("deprecation")
    boolean add0() {
        try {
            return Bukkit.getUnsafe().loadAdvancement(id, getJson()) != null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Removes the advancement from the server.
     * 
     * @return this advancement
     */
    public AdvancementAPI remove() {
        if (remove0()) {
            Bukkit.getLogger().info(() -> LOG_PREFIX + "Successfully removed advancement \"" + id + "\".");
            return this;
        }
        Bukkit.getLogger().severe(() -> LOG_PREFIX + "Error removing advancement \"" + id + "\".");
        return this;
    }

    @SuppressWarnings("deprecation")
    boolean remove0() {
        return Bukkit.getUnsafe().removeAdvancement(id);
    }

    /**
     * Shows the advancement to the {@link Player}s.
     * 
     * @param plugin the owning {@link JavaPlugin}
     * @param players the list of {@link Player}s
     * @return this advancement
     */
    public AdvancementAPI show(JavaPlugin plugin, Player... players) {
        add();
        grant(players);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            revoke(players);
            remove();
        }, 20L);
        return this;
    }

    /**
     * Grants this advancement for the given {@link Player}s.
     * 
     * @param players the list of {@link Player}s
     * @return this advancement
     */
    public AdvancementAPI grant(Player... players) {
        Advancement advancement = getAdvancement();
        for (Player player : players) {
            AdvancementProgress progress = player.getAdvancementProgress(advancement);
            if (!progress.isDone()) {
                Collection<String> remainingCriteria = progress.getRemainingCriteria();
                for (String remainingCriterion : remainingCriteria) {
                    progress.awardCriteria(remainingCriterion);
                }
            }
        }
        return this;
    }

    /**
     * Revokes the advancement from the given {@link Player}s.
     * 
     * @param players the list of {@link Player}s
     * @return this advancement
     */
    public AdvancementAPI revoke(Player... players) {
        Advancement advancement = getAdvancement();
        for (Player player : players) {
            AdvancementProgress progress = player.getAdvancementProgress(advancement);
            if (progress.isDone()) {
                Collection<String> awardedCriteria = progress.getAwardedCriteria();
                for (String awardedCriterion : awardedCriteria) {
                    progress.revokeCriteria(awardedCriterion);
                }
            }
        }
        return this;
    }

    /**
     * Gets the Bukkit implementation of this {@link Advancement}.
     * 
     * @return the Bukkit {@link Advancement}
     */
    private Advancement getAdvancement() {
        return Bukkit.getAdvancement(id);
    }

    private String getAdvancementFile() {
        return "data" + File.separator + "advancements" + File.separator + id.getNamespace() + File.separator
                + id.getKey() + ".json";
    }

    /**
     * Saves this advancement in the world folder.
     * <p>
     * This will not register this advancement within the server. You have to
     * restart the server or use {@link #add()}.
     * 
     * @param world the worldname
     */
    public void save(String world) {
        if (save0(Bukkit.getWorld(world).getWorldFolder())) {
            Bukkit.getLogger().info(() -> LOG_PREFIX + "Created " + id);
            return;
        }
        Bukkit.getLogger().severe(() -> LOG_PREFIX + "Error on saving advancement " + id);
    }

    boolean save0(File worldFolder) {
        File file = new File(worldFolder, getAdvancementFile());
        File dir = file.getParentFile();
        if (dir.mkdirs() || dir.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(getJson());
                return true;
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Deletes this advancement from the world folder.
     * <p>
     * This will not unregister this advancement from the server. You have to
     * restart the server or use {@link #remove()}.
     * 
     * @param world the worldname
     */
    public void delete(String world) {
        if (delete0(Bukkit.getWorld(world).getWorldFolder())) {
            Bukkit.getLogger().info(() -> LOG_PREFIX + "Deleted " + id);
            return;
        }
        Bukkit.getLogger().severe(() -> LOG_PREFIX + "Error on deleting advancement " + id);
    }

    boolean delete0(File worldFolder) {
        File file = new File(worldFolder, getAdvancementFile());
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * Builder for {@link AdvancementAPI}s. See {@link AdvancementAPI} for more
     * information on advancements in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class AdvancementAPIBuilder implements Builder<AdvancementAPI> {
        private NamespacedKey id;
        private String parent;
        private DisplayBuilder display;
        private List<TriggerBuilder> triggers = new LinkedList<>();
        private RequirementsBuilder requirements;
        private RewardsBuilder rewards;

        private AdvancementAPIBuilder(NamespacedKey id) {
            this.id = id;
        }

        /**
         * A branch is defined by having a parent string. The parent specifies
         * the resource location of another advancement, where the branch
         * visually extends from the parent. Note that the parent does not
         * actually need to be completed in order for the branch to be
         * completed.
         * <p>
         * The visual structure of the tree will be automatically generated.
         * <p>
         * See {@link #parent(NamespacedKey)} for using with
         * {@link NamespacedKey}.
         * 
         * @param parent the parent advancement
         * @return this builder
         */
        public AdvancementAPIBuilder parent(String parent) {
            this.parent = parent;
            return this;
        }

        /**
         * See {@link #parent(String)} for more information.
         * 
         * @param parentID the parent {@link NamespacedKey}
         * @return this builder
         */
        public AdvancementAPIBuilder parent(NamespacedKey parentID) {
            this.parent = parentID.toString();
            return this;
        }

        /**
         * The optional <code>display</code> object contains information about
         * the display in the "Advancements" menu. If this object does not
         * exist, the advancement will be hidden from the "Advancements" menu
         * and popup notifications will not appear.
         * 
         * @param displayBuilder a {@link DisplayBuilder}
         * @return this builder
         */
        public AdvancementAPIBuilder display(DisplayBuilder displayBuilder) {
            this.display = displayBuilder;
            return this;
        }

        /**
         * An advancement <b>must</b> include a set of rules that activate the
         * advancement, specified in the <code>criteria</code> object. Each
         * object nested within will contain a custom name of your choosing, to
         * later be used with the optional <code>requirements</code> list or the
         * <code>/advancement</code> command. When the player matches a
         * criterion at any point, that fulfillment will be remembered, allowing
         * the player to fulfill all criteria over time rather than all at the
         * same time.
         * <p>
         * Within each criterion there <b>must</b> be a {@link Trigger},
         * specified by the trigger string.
         * <p>
         * If <b>requirements</b> is not specified, then all criteria must be
         * met for the advancement to be granted.
         * 
         * @param trigger a {@link TriggerBuilder}
         * @return this builder
         */
        public AdvancementAPIBuilder trigger(TriggerBuilder trigger) {
            this.triggers.add(trigger);
            return this;
        }

        /**
         * See {@link #trigger(TriggerBuilder)} for more information.
         * <p>
         * Adds the collection of {@link TriggerBuilder} to the list of
         * triggers.
         * 
         * @param triggers a list of {@link TriggerBuilder}
         * @return this builder
         */
        public AdvancementAPIBuilder triggers(Collection<? extends TriggerBuilder> triggers) {
            this.triggers.addAll(triggers);
            return this;
        }

        /**
         * Clears the list of triggers.
         * 
         * @return this builder
         */
        public AdvancementAPIBuilder clearTriggers() {
            this.triggers.clear();
            return this;
        }

        /**
         * The <code>requirements</code> list specifies a Conjunctive Normal
         * Form structure to accompany criteria. It essentially allows boolean
         * logic to determine when the advancement is granted based on the
         * accompanying criteria. The list contains more lists, which in turn
         * contains strings that equal the names of the criteria. You would use
         * this if you want to use an OR operation. Each new list is a new AND
         * operation, while each comma-separated string within the list is an OR
         * operation.
         * <p>
         * If <code>requirements</code> is not specified, then <b>all</b>
         * criteria must be fulfilled. If this list is specified, then every
         * criteria must be included in <code>requirements</code> to determine
         * which ones need to be fulfilled.
         * 
         * @param requirementsBuilder a {@link RequirementsBuilder}
         * @return this builder
         */
        public AdvancementAPIBuilder requirements(RequirementsBuilder requirementsBuilder) {
            this.requirements = requirementsBuilder;
            return this;
        }

        /**
         * The <code>rewards</code> object specifies multiple types of rewards
         * to provide the player upon completing the advancement.
         * 
         * @param rewardsBuilder a {@link RewardsBuilder}
         * @return this builder
         */
        public AdvancementAPIBuilder rewards(RewardsBuilder rewardsBuilder) {
            this.rewards = rewardsBuilder;
            return this;
        }

        @Override
        public AdvancementAPI build() {
            if (triggers.isEmpty()) {
                triggers.add(Trigger.builder(TriggerType.IMPOSSIBLE, "default"));
            }
            return new AdvancementAPI(id, parent, display, Collections.unmodifiableList(triggers), requirements,
                    rewards);
        }
    }
}