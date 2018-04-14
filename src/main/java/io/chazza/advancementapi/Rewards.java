package io.chazza.advancementapi;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;

/**
 * The rewards object specifies multiple types of rewards to provide the player
 * upon completing the advancement.
 * <p>
 * <b>recipes</b>
 * <p>
 * This list specifies multiple recipes to unlock for the player upon completing
 * the advancement. The following advancement unlocks the "minecraft:redstone"
 * and "minecraft:ladder" recipes together.
 * 
 * <pre>
 * {
 *     "criteria": {
 *         "custom_test_name": {
 *             "trigger": "minecraft:entity_hurt_player"
 *         }
 *     },
 *     "rewards": {
 *         "recipes": ["minecraft:redstone", "minecraft:ladder"]
 *     }
 * }
 * </pre>
 * 
 * <b>loot</b>
 * <p>
 * This list specifies multiple loot tables to process, providing the player
 * with the resulting item(s). The following advancement provides players with
 * items from "minecraft:entities/creeper" and
 * "minecraft:chests/simple_dungeon".
 * 
 * <pre>
 * {
 *     "criteria": {
 *         "custom_test_name": {
 *             "trigger": "minecraft:entity_hurt_player"
 *         }
 *     },
 *     "rewards": {
 *         "loot": ["minecraft:entities/creeper", "minecraft:chests/simple_dungeon"]
 *     }
 * }
 * </pre>
 * 
 * <b>experience</b>
 * <p>
 * This number (not range) specifies the amount of experience (not levels) to
 * reward the player with.
 * 
 * <pre>
 * {
 *     "criteria": {
 *         "custom_test_name": {
 *             "trigger": "minecraft:entity_hurt_player"
 *         }
 *     },
 *     "rewards": {
 *         "experience": 500
 *     }
 * }
 * </pre>
 * 
 * <b>function</b>
 * <p>
 * This string specifies a single function file to run, with the value being the
 * resource location to that function. The player will be considered the command
 * sender and CommandStats trigger target, which means sender bias (such as from
 * "@s") will always target that player, and that player will trigger their own
 * stored CommandStats. Each command will run in the specified order in the
 * function. If the advancement is granted via command block, all of the listed
 * commands will execute immediately, allowing other command blocks further in
 * the chain to run based off of the results of the advancement's function being
 * run (although you do not need to use advancements for this if focusing on
 * functions).
 * 
 * <pre>
 * {
 *     "criteria": {
 *         "custom_test_name": {
 *             "trigger": "minecraft:entity_hurt_player"
 *         }
 *     },
 *     "rewards": {
 *         "function": "path:to/function"
 *     }
 * }
 * </pre>
 * 
 * @author Kaonashi97
 * @see https://github.com/skylinerw/guides/blob/master/java/advancements.md#-rewards
 */
public class Rewards implements Jsonable {
    private List<String> recipes = new LinkedList<>();
    private List<String> loots = new LinkedList<>();
    private Integer experience;
    private String function;

    private Rewards(List<String> recipes, List<String> loots, Integer experience, String function) {
        this.recipes = recipes;
        this.loots = loots;
        this.experience = experience;
        this.function = function;
    }

    /**
     * Returns a {@link RewardsBuilder} for building {@link Rewards}s.
     * 
     * @return the builder
     */
    public static RewardsBuilder builder() {
        return new RewardsBuilder();
    }

    @Override
    public JsonElement toJson() {
        JsonObject rewardsObj = new JsonObject();
        if (!recipes.isEmpty()) {
            JsonArray recipeArray = new JsonArray();
            recipes.forEach(recipe -> recipeArray.add(recipe));
            rewardsObj.add("recipes", recipeArray);
        }
        if (!loots.isEmpty()) {
            JsonArray lootsArray = new JsonArray();
            loots.forEach(loot -> lootsArray.add(loot));
            rewardsObj.add("loots", lootsArray);
        }
        //@formatter:off
        if (experience != null) rewardsObj.addProperty("experience", experience);
        if (function != null) rewardsObj.addProperty("function", function);
        //@formatter:on
        return rewardsObj;
    }

    /**
     * Builder for {@link Rewards}s. See {@link Rewards} for more information.
     * 
     * @author Kaonashi97
     */
    public static class RewardsBuilder implements Builder<Rewards> {
        private List<String> recipes = new LinkedList<>();
        private List<String> loots = new LinkedList<>();
        private Integer experience;
        private String function;

        private RewardsBuilder() {
            // builder pattern
        }

        /**
         * This list specifies multiple recipes to unlock for the player upon
         * completing the advancement.
         * 
         * @param recipes a list of recipes
         * @return this builder
         */
        public RewardsBuilder recipe(String... recipes) {
            this.recipes.addAll(Arrays.asList(recipes));
            return this;
        }

        /**
         * This list specifies multiple loot tables to process, providing the
         * player with the resulting item(s).
         * 
         * @param loots a list of loot tables
         * @return this builder
         */
        public RewardsBuilder loots(String... loots) {
            this.loots.addAll(Arrays.asList(loots));
            return this;
        }

        /**
         * This number (not range) specifies the amount of experience (not
         * levels) to reward the player with.
         * 
         * @param experience the amount of experience
         * @return this builder
         */
        public RewardsBuilder experience(int experience) {
            this.experience = new Integer(experience);
            return this;
        }

        /**
         * This string specifies a single function file to run, with the value
         * being the resource location to that function. The player will be
         * considered the command sender and CommandStats trigger target, which
         * means sender bias (such as from "@s") will always target that player,
         * and that player will trigger their own stored CommandStats. Each
         * command will run in the specified order in the function. If the
         * advancement is granted via command block, all of the listed commands
         * will execute immediately, allowing other command blocks further in
         * the chain to run based off of the results of the advancement's
         * function being run (although you do not need to use advancements for
         * this if focusing on functions).
         * 
         * @param function path to a function or command
         * @return this builder
         */
        public RewardsBuilder function(String function) {
            this.function = function;
            return this;
        }

        @Override
        public Rewards build() {
            return new Rewards(recipes, loots, experience, function);
        }
    }
}