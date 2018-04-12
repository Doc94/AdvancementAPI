package io.chazza.advancementapi.conditions.primitive;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;
import io.chazza.advancementapi.conditions.enums.Potion;
import io.chazza.advancementapi.conditions.primitive.Enchantment.EnchantmentBuilder;
import io.chazza.advancementapi.conditions.primitive.Range.RangeBuilder;

/**
 * An item object contains a handful of data to compare to an incoming item
 * stack. All options are available anywhere that this item object is used.
 * <p>
 * <b>1. "item"</b>
 * <p>
 * The item string specifies a base item ID to compare the item to. The
 * following checks if the item is redstone.
 * 
 * <pre>
 * Item.builder("minecraft:redstone").build();
 * </pre>
 * 
 * The tag string instead specifies the resource location to an ID group, minus
 * the designating # character. These groups are a list of item IDs that the
 * incoming item can match any one of. Default groups for items include:
 * "#minecraft:wool", "#minecraft:planks", "#minecraft:stone_bricks",
 * "#minecraft:wooden_buttons", "#minecraft:buttons", "#minecraft:carpets",
 * "#minecraft:wooden_doors", "#minecraft:doors", "#minecraft:saplings", and
 * "#minecraft:logs". Adding custom groups requires the use of data packs. The
 * following checks if the item matches any of the IDs in the "minecraft:doors"
 * group (which checks both the "#minecraft:wooden_doors" group as well as the
 * "minecraft:iron_door" item ID).
 * 
 * <pre>
 * Item.builder("#minecraft:wool").build();
 * </pre>
 * 
 * <b>2. "data"</b>
 * <p>
 * The "data" integer specifies a metadata of the item. The following checks if
 * the item is a polished granite block.
 * <p>
 * The "data" attribute needs a name. Without, it won't be in the output.
 * 
 * <pre>
 * Item.builder("minecraft:stone").data(2).build();
 * </pre>
 * 
 * <b>3. "durability</b>
 * <p>
 * The "data" range specifies the remaining durability of an item. The following
 * checks if the item has 400 or more durability remaining.
 * 
 * <pre>
 * Item.builder().durability(Range.builder().min(400)).build();
 * </pre>
 * 
 * <b>4. "count"</b>
 * <p>
 * The "count" range specifies the number of items in a single stack. This
 * cannot be used to check the number of items across the inventory as a whole.
 * The following checks if the item has 16 or more in its stack.
 * 
 * <pre>
 * Item.builder().count(Range.builder().min(16)).build();
 * </pre>
 * 
 * <b>5. "potion"</b>
 * <p>
 * The "potion" string specifies the default brewed potion ID that the item must
 * contain, specified in the Potion NBT string. For a list see the
 * {@link Potion} enum.
 * 
 * <pre>
 * Item.builder().potion(Potion.INVISIBILITY).build();
 * </pre>
 * 
 * <b>The item does not have to be a potion.</b> As long as the item has the
 * Potion NBT string, it will match:
 * 
 * <pre>
 * /give @p minecraft:stone 1 0 {Potion:"minecraft:invisibility"}
 * </pre>
 * 
 * <b>6. "enchantments"</b>
 * <p>
 * The "enchantments" list checks the item's enchantments (whether in the ench
 * NBT list for all items excluding books, or the StoredEnchantments NBT list
 * for only books) for matching data.
 * <p>
 * If only an empty object is specified, the player's inventory is checked for
 * any enchanted items.
 * <p>
 * Using the <code>emptyEnchantmentList()</code> will lead to not print previous
 * settet enchantments. They won't be deleted, so that you can "reset" them with
 * just adding another enchantment.
 * 
 * <pre>
 * Item.builder().emptyEnchantmentList().build();
 * </pre>
 * 
 * more information see {@link Enchantment}
 * 
 * <p>
 * <b>7. "nbt"</b>
 * <p>
 * The "nbt" string compares the raw NBT input to the item's NBT data. This raw
 * data starts within the "tag" compound of the item format and must be
 * surrounded by curly brackets. The following checks if the item has a specific
 * display name.
 * <p>
 * The NBT code will <b>not</b> be edited nor validated. The response is
 * completely on the caller side.
 * 
 * <pre>
 * Item.builder().nbt("{display:{Name:\"Test\"}}").build();
 * </pre>
 * 
 * <p>
 * <b>Build from {@link Material}</b>
 * <p>
 * It's possible to get an item from a {@link Material}. This will create an
 * item with just the name set.
 * 
 * <pre>
 * Item.builder(Material.STONE).build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see https://github.com/skylinerw/guides/blob/master/java/advancements/data_structures.md#-shared-item-object
 */
public class Item implements Jsonable {
    private String item;
    private Byte data;
    private RangeBuilder durability;
    private RangeBuilder count;
    private Potion potion;
    private List<EnchantmentBuilder> enchantments = new LinkedList<>();
    private boolean isEmptyEnchantments;
    private String nbt;

    private Item(String item, Byte data, RangeBuilder durability, RangeBuilder count, Potion potion,
            List<EnchantmentBuilder> enchantments, boolean isEmptyEnchantments, String nbt) {
        this.item = item;
        this.data = data;
        this.durability = durability;
        this.count = count;
        this.potion = potion;
        this.enchantments = enchantments;
        this.isEmptyEnchantments = isEmptyEnchantments;
        this.nbt = nbt;
    }

    /**
     * Returns a {@link ItemBuilder} for building {@link Item}s.
     * 
     * @return the builder
     */
    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    /**
     * Returns a {@link ItemBuilder} for building {@link Item}s.
     * 
     * @param item the name of an item (with {@link NamespacedKey})
     * @return the builder
     */
    public static ItemBuilder builder(String item) {
        return new ItemBuilder(item);
    }

    /**
     * Returns a {@link ItemBuilder} for building {@link Item}s.
     * 
     * @param material the {@link Material} to build from
     * @return the builder
     */
    public static ItemBuilder builder(Material material) {
        return new ItemBuilder("minecraft:" + material.toString().toLowerCase());
    }

    @Override
    public JsonElement toJson() {
        JsonObject itemObj = new JsonObject();

        //@formatter:off
        if(item != null) {
            if (item.trim().startsWith("#")) {
                itemObj.addProperty("tag", item.trim().substring(1));
            } else {
                itemObj.addProperty("item", item);
            }
        }
        if (potion != null) itemObj.addProperty("potion", potion.toString());
        if (data != null && item != null) itemObj.addProperty("data", data);
        if (durability != null) itemObj.add("durability", durability.build().toJson());
        if (count != null) itemObj.add("count", count.build().toJson());
        //@formatter:on

        JsonArray enchArray = new JsonArray();
        if (isEmptyEnchantments) {
            enchArray.add(new JsonObject());
            itemObj.add("enchantments", enchArray);
        } else {
            enchantments.stream().filter(e -> e != null)
                    .forEach(enchBuilder -> enchArray.add(enchBuilder.build().toJson()));
            /*
             * Do not set an empty enchantments list if possible enchantments
             * were set! An empty list will accept any enchantment as stated in
             * the linked article (see class javadoc).
             */
            if (enchArray.size() > 0) {
                itemObj.add("enchantments", enchArray);
            }
        }

        itemObj.addProperty("nbt", nbt);

        return itemObj;
    }

    /**
     * Builder for {@link Item}s. See {@link Item} for more information on items
     * in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class ItemBuilder implements Builder<Item> {
        private String item;
        private Byte data;
        private RangeBuilder durability;
        private RangeBuilder count;
        private Potion potion;
        private List<EnchantmentBuilder> enchantments = new LinkedList<>();
        private boolean isEmptyEnchantments;
        private String nbt;

        private ItemBuilder() {
            // builder pattern
        }

        private ItemBuilder(String item) {
            this.item = item;
        }

        /**
         * The "data" integer specifies a metadata of the item.
         * 
         * @param data 0 to 255
         * @return this builder
         */
        public ItemBuilder data(int data) {
            if (data > Byte.MAX_VALUE) {
                data = 0;
            }
            this.data = new Byte((byte) data);
            return this;
        }

        /**
         * The "data" range specifies the remaining durability of an item.
         * 
         * @param durabilityRangeBuilder the {@link RangeBuilder}
         * @return this builder
         */
        public ItemBuilder durability(RangeBuilder durabilityRangeBuilder) {
            this.durability = durabilityRangeBuilder;
            return this;
        }

        /**
         * The "count" range specifies the number of items in a single stack.
         * This cannot be used to check the number of items across the inventory
         * as a whole.
         * 
         * @param countRangeBuilder the {@link RangeBuilder}
         * @return this builder
         */
        public ItemBuilder count(RangeBuilder countRangeBuilder) {
            this.count = countRangeBuilder;
            return this;
        }

        /**
         * The "potion" string specifies the default brewed potion ID that the
         * item must contain, specified in the Potion NBT string. For a list see
         * the {@link Potion} enum.
         * 
         * @param potion the {@link Potion}
         * @return this builder
         */
        public ItemBuilder potion(Potion potion) {
            this.potion = potion;
            return this;
        }

        /**
         * The "enchantments" list checks the item's enchantments (whether in
         * the ench NBT list for all items excluding books, or the
         * StoredEnchantments NBT list for only books) for matching data.
         * 
         * @param enchantment the {@link EnchantmentBuilder}
         * @return this builder
         */
        public ItemBuilder enchantment(EnchantmentBuilder enchantment) {
            this.enchantments.add(enchantment);
            this.isEmptyEnchantments = false;
            return this;
        }

        /**
         * The "enchantments" list checks the item's enchantments (whether in
         * the ench NBT list for all items excluding books, or the
         * StoredEnchantments NBT list for only books) for matching data.
         * <p>
         * If only an empty object is specified, the player's inventory is
         * checked for any enchanted items.
         * 
         * @return this builder
         */
        public ItemBuilder emptyEnchantmentList() {
            this.isEmptyEnchantments = true;
            return this;
        }

        /**
         * The "enchantments" list checks the item's enchantments (whether in
         * the ench NBT list for all items excluding books, or the
         * StoredEnchantments NBT list for only books) for matching data.
         * 
         * Adds the list to the list of enchantments.
         * 
         * @param enchantments a collection of {@link EnchantmentBuilder}
         * @return this builder
         */
        public ItemBuilder enchantments(Collection<? extends EnchantmentBuilder> enchantments) {
            this.enchantments.addAll(enchantments);
            this.isEmptyEnchantments = false;
            return this;
        }

        /**
         * Clears the list of enchantments.
         * 
         * If some were set the will be deleted.
         * 
         * @return this bilder
         */
        public ItemBuilder clearEnchantments() {
            this.enchantments.clear();
            return this;
        }

        /**
         * The "nbt" string compares the raw NBT input to the item's NBT data.
         * This raw data starts within the "tag" compound of the item format and
         * must be surrounded by curly brackets.
         * 
         * @param nbt the NBT data string
         * @return thsi builder
         */
        public ItemBuilder nbt(String nbt) {
            this.nbt = nbt;
            return this;
        }

        @Override
        public Item build() {
            return new Item(item, data, durability, count, potion, enchantments, isEmptyEnchantments, nbt);
        }
    }
}