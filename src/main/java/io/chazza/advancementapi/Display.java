package io.chazza.advancementapi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

/**
 * The optional display object contains information about the display in the
 * "Advancements" menu. If this object does not exist, the advancement will be
 * hidden from the "Advancements" menu and popup notifications will not appear.
 * 
 * <h3>Title, description, & icon</h3>
 * <p>
 * When the display object exists, the title, description, and icon tags must be
 * specified. The background for the icon for each advancement will range from
 * gray to red depending on the number of criteria fulfilled in that
 * advancement.
 * <p>
 * <b>1. title</b>
 * <p>
 * The title can either be a simple string or a text component object. This
 * title is shown in the "Advancements" menu when hovering over the icon, as
 * well as in the popup notification when completing the advancement.
 * <p>
 * Given the following advancement, which uses a simple string for the title:
 * 
 * <pre>
 * Display.builder("Stew", "", "mineraft:mushroom:stew").build();
 * </pre>
 * 
 * <p>
 * Or you can specify formatting for the text using the text component (note
 * that many text component features are unavailable, such as selectors, scores,
 * and event listeners):
 * 
 * <pre>
 * {@link TextComponent} text = new {@link TextComponent#TextComponent(String) TextComponent}("Stew")
 * text.{@link TextComponent#setObfuscated(Boolean) setObfuscated}(true);
 * Display.builder(text, "", "minecraft:mushroom_stew").build();
 * </pre>
 * 
 * <b>2. description</b>
 * <p>
 * The description can either be a simple string or a text component object.
 * This description is shown in the "Advancements" menu when hovering over the
 * icon, but not in the popup notification when completing the advancement. The
 * description can be blank for no description to appear, but the tag itself
 * must still exist.
 * <p>
 * Given the following advancement, which uses a simple string for the
 * description:
 * 
 * <pre>
 * Display.builder("Learn to Fly", "Learn how to fly using a new pair of elytra.", "minecraft:elytra").build();
 * </pre>
 * 
 * <p>
 * Or you can specify formatting for the text using the text component (note
 * that many text component features are unavailable, such as selectors, scores,
 * and event listeners):
 * 
 * <pre>
 * {@link TextComponent} text = new {@link TextComponent#TextComponent(String) TextComponent}("Stew")
 * text.{@link TextComponent#setItalic(Boolean) setItalic}(true);
 * Display.builder("Learn to Fly", text, "minecraft:elytra").build();
 * </pre>
 * 
 * <b>3. icon</b>
 * <p>
 * The icon object holds a required item ID of the item. This icon is shown in
 * the "Advancements" menu, as well as in the popup notification when completing
 * the advancement. The following icon specifies red wool:
 * 
 * <pre>
 * Display.builder("", "", "minecraft:red_wool").build();
 * </pre>
 * 
 * Currently you may not specify any NBT data for the item, including
 * durability.
 * 
 * <h3>Background</h3>
 * <p>
 * The background is an optional string for all advancements, but is only used
 * by "root" advancements (i.e. when no parent is defined). This is the
 * background that appears behind all of the icons. The value is a resource
 * location to any image within a resource pack. For example, the following
 * advancement will display a tiled gold block background, coming from the
 * default block texture:
 * 
 * <pre>
 * Display.builder("Stew", "", "minecraft:mushroom_stew").background("minecraft:textures/blocks/gold_block.png")
 *         .build();
 * </pre>
 * 
 * <h3>Frame</h3>
 * <p>
 * An optional {@link FrameType} string modifies the border shape around the
 * icon. When not specified, it will default to {@link FrameType#TASK}.
 * <p>
 * The following advancement makes use of the "challenge" frame.
 * 
 * <pre>
 * Display.builder("Stew", "", "minecraft:mushroom_stew").frame(FrameType.CHALLENGE).build();
 * </pre>
 * 
 * The following advancement makes use of the "goal" frame.
 * 
 * <pre>
 * Display.builder("Stew", "", "minecraft:mushroom_stew").frame(FrameType.GOAL).build();
 * </pre>
 * 
 * <h3>Show toast</h3>
 * <p>
 * An optional <code>show_toast</code> boolean can be specified in order to
 * prevent the popup notification in the upper right-hand corner of the screen
 * from appearing when the player fulfills an advancement. It defaults to true,
 * and setting it to false will hide the popup.
 * 
 * <pre>
 * Display.builder("Stew", "", "minecraft:mushroom_stew").toast(false).build();
 * </pre>
 * 
 * <h3>Announce to chat</h3>
 * <p>
 * An optional <code>announce_to_chat</code> boolean can be specified in order
 * to prevent a chat message from being sent telling all players that somebody
 * fulfilled an advancement. It defaults to true, and setting it to false will
 * prevent chat messages from being sent.
 * 
 * <pre>
 * Display.builder("Stew", "", "minecraft:mushroom_stew").announce(false).build();
 * </pre>
 * 
 * The <code>announceAdvancements</code> gamerule can globally disable
 * fulfillment messages, overriding the value of <code>announce_to_chat</code>
 * for individual advancements.
 * 
 * <pre>
 * /gamerule announceAdvancements false
 * </pre>
 * 
 * <h3>Hidden</h3>
 * <p>
 * An optional hidden boolean can be specified in order to prevent child
 * advancements from displaying in the "Advancements" menu until they are
 * completed.
 * 
 * <pre>
 * Display.builder("Stew", "", "minecraft:mushroom_stew").hidden(true).build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see "https://github.com/skylinerw/guides/blob/master/java/advancements.md#-display"
 */
public class Display implements Jsonable {
    private static final Gson gson = new Gson();

    private TextComponent title;
    private TextComponent description;
    private String icon;
    private String background;
    private FrameType frame;
    private Boolean announce;
    private Boolean toast;
    private Boolean hidden;

    private Display(TextComponent title, TextComponent description, String icon, String background, FrameType frame,
            Boolean announce, Boolean toast, Boolean hidden) {
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.background = background;
        this.frame = frame;
        this.announce = announce;
        this.toast = toast;
        this.hidden = hidden;
    }

    /**
     * Returns a {@link DisplayBuilder} for building {@link Display}s.
     * 
     * @param title a title
     * @param description a description
     * @param icon an icon
     * @return the builder
     */
    public static DisplayBuilder builder(String title, String description, String icon) {
        return new DisplayBuilder().title(title).description(description).icon(icon);
    }

    @Override
    public JsonElement toJson() {
        JsonObject displayObj = new JsonObject();

        if (title.getText().isEmpty()) {
            displayObj.addProperty("title", "");
        } else {
            displayObj.add("title", getJsonStringFromComponent(title));
        }
        if (description.getText().isEmpty()) {
            displayObj.addProperty("description", "");
        } else {
            displayObj.add("description", getJsonStringFromComponent(description));
        }
        JsonObject iconObj = new JsonObject();
        iconObj.addProperty("item", icon);
        displayObj.add("icon", iconObj);

        //@formatter:off
        if (background != null) displayObj.addProperty("background", background);
        if (frame != null) displayObj.addProperty("frame", frame.toString());
        if (toast != null) displayObj.addProperty("show_toast", toast);
        if (announce != null) displayObj.addProperty("announce_to_chat", announce);
        if (hidden != null) displayObj.addProperty("hidden", hidden);
        //@formatter:on

        return displayObj;
    }

    private static JsonElement getJsonStringFromComponent(TextComponent textComponent) {
        return gson.fromJson(ComponentSerializer.toString(textComponent), JsonElement.class);
    }

    /**
     * Builder for {@link Display}. See {@link Display} for more information.
     * 
     * @author Kaonashi97
     */
    public static class DisplayBuilder implements Builder<Display> {
        private TextComponent title;
        private TextComponent description;
        private String icon;
        private String background;
        private FrameType frame;
        private Boolean announce;
        private Boolean toast;
        private Boolean hidden;

        private DisplayBuilder() {
            // builder pattern
        }

        /**
         * The title can either be a simple string or a text component object.
         * This title is shown in the "Advancements" menu when hovering over the
         * icon, as well as in the popup notification when completing the
         * advancement.
         * 
         * @param title a title
         * @return this builder
         */
        public DisplayBuilder title(String title) {
            this.title = new TextComponent(title);
            return this;
        }

        /**
         * The title can either be a simple string or a text component object.
         * This title is shown in the "Advancements" menu when hovering over the
         * icon, as well as in the popup notification when completing the
         * advancement.
         * 
         * @param title a {@link TextComponent}
         * @return this builder
         */
        public DisplayBuilder title(TextComponent title) {
            this.title = title;
            return this;
        }

        /**
         * The description can either be a simple string or a text component
         * object. This description is shown in the "Advancements" menu when
         * hovering over the icon, but not in the popup notification when
         * completing the advancement. The description can be blank for no
         * description to appear, but the tag itself must still exist.
         * 
         * @param description a description
         * @return this builder
         */
        public DisplayBuilder description(String description) {
            this.description = new TextComponent(description);
            return this;
        }

        /**
         * The description can either be a simple string or a text component
         * object. This description is shown in the "Advancements" menu when
         * hovering over the icon, but not in the popup notification when
         * completing the advancement. The description can be blank for no
         * description to appear, but the tag itself must still exist.
         * 
         * @param description a {@link TextComponent}
         * @return this builder
         */
        public DisplayBuilder description(TextComponent description) {
            this.description = description;
            return this;
        }

        /**
         * The icon object holds a required item ID of the item. This icon is
         * shown in the "Advancements" menu, as well as in the popup
         * notification when completing the advancement.
         * 
         * @param icon the icon
         * @return this builder
         */
        public DisplayBuilder icon(String icon) {
            this.icon = icon;
            return this;
        }

        /**
         * The background is an optional string for all advancements, but is
         * only used by "root" advancements (i.e. when no parent is defined).
         * This is the background that appears behind all of the icons. The
         * value is a resource location to any image within a resource pack.
         * 
         * @param background the background
         * @return this builder
         */
        public DisplayBuilder background(String background) {
            this.background = background;
            return this;
        }

        /**
         * An optional {@link FrameType} string modifies the border shape around
         * the icon. When not specified, it will default to
         * {@link FrameType#TASK}.
         * 
         * @param frame the {@link FrameType}
         * @return this builder
         */
        public DisplayBuilder frame(FrameType frame) {
            this.frame = frame;
            return this;
        }

        /**
         * An optional <code>announce_to_chat</code> boolean can be specified in
         * order to prevent a chat message from being sent telling all players
         * that somebody fulfilled an advancement. It defaults to true, and
         * setting it to false will prevent chat messages from being sent.
         * 
         * @param announce true or false
         * @return this builder
         */
        public DisplayBuilder announce(boolean announce) {
            this.announce = new Boolean(announce);
            return this;
        }

        /**
         * An optional <code>show_toast</code> boolean can be specified in order
         * to prevent the popup notification in the upper right-hand corner of
         * the screen from appearing when the player fulfills an advancement. It
         * defaults to true, and setting it to false will hide the popup.
         * 
         * @param toast true or false
         * @return this builder
         */
        public DisplayBuilder toast(boolean toast) {
            this.toast = new Boolean(toast);
            return this;
        }

        /**
         * An optional hidden boolean can be specified in order to prevent child
         * advancements from displaying in the "Advancements" menu until they
         * are completed.
         * 
         * @param hidden true or false
         * @return this builder
         */
        public DisplayBuilder hidden(boolean hidden) {
            this.hidden = new Boolean(hidden);
            return this;
        }

        @Override
        public Display build() {
            return new Display(title, description, icon, background, frame, announce, toast, hidden);
        }
    }
}