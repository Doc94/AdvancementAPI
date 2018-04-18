package io.chazza.advancementapi.conditions.primitive;

import org.bukkit.NamespacedKey;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;
import io.chazza.advancementapi.conditions.primitive.Range.RangeBuilder;

/**
 * The "enchantments" list checks the item's enchantments (whether in the ench
 * NBT list for all items excluding books, or the StoredEnchantments NBT list
 * for only books) for matching data.
 * 
 * The "levels" range will specify the range of levels to find for an
 * enchantment. The following checks if the player has any enchantments level 3
 * or higher.
 * 
 * <pre>
 * Enchantment.builder(Range.builder().min(3)).build();
 * </pre>
 * 
 * And combining it with an ID, the following checks if the player has Sharpness
 * 5.
 * 
 * <pre>
 * Enchantment.builder("minecraft:sharpness").levels(Range.builder().range(5)).build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see https://github.com/skylinerw/guides/blob/master/java/advancements/data_structures.md#-shared-item-object
 */
public class Enchantment implements Jsonable {
    private String enchantment;
    private RangeBuilder levels;

    private Enchantment(String enchantment, RangeBuilder levels) {
        this.enchantment = enchantment;
        this.levels = levels;
    }

    /**
     * Returns a {@link EnchantmentBuilder} for building {@link Enchantment}s.
     * 
     * @param enchantment the name of the enchantment (with
     * {@link NamespacedKey})
     * @return the builder
     */
    public static EnchantmentBuilder builder(String enchantment) {
        return new EnchantmentBuilder(enchantment, null);
    }

    /**
     * Returns a {@link EnchantmentBuilder} for building {@link Enchantment}s.
     * 
     * @param levels the {@link RangeBuilder}
     * @return the builder
     */
    public static EnchantmentBuilder builder(RangeBuilder levels) {
        return new EnchantmentBuilder(null, levels);
    }

    @Override
    public JsonElement toJson() {
        JsonObject enchantmentObj = new JsonObject();
        //@formatter:off
        if (enchantment != null) enchantmentObj.addProperty("enchantment", enchantment);
        if (levels != null) enchantmentObj.add("levels", levels.build().toJson());
        //@formatter:on
        return enchantmentObj;
    }

    /**
     * Builder for {@link Enchantment}s. See {@link Enchantment} for more
     * information on enchantments in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class EnchantmentBuilder implements Builder<Enchantment> {
        private String enchantment;
        private RangeBuilder levels;

        private EnchantmentBuilder(String enchantment, RangeBuilder levels) {
            this.enchantment = enchantment;
            this.levels = levels;
        }

        /**
         * Used to specify a enchantment.
         * 
         * @param enchantment the name of the enchantment (with
         * {@link NamespacedKey})
         * @return this builder
         */
        public EnchantmentBuilder enchantment(String enchantment) {
            this.enchantment = enchantment;
            return this;
        }

        /**
         * Used to specify a level range.
         * 
         * @param levels the {@link RangeBuilder}
         * @return this builder
         */
        public EnchantmentBuilder levels(RangeBuilder levels) {
            this.levels = levels;
            return this;
        }

        @Override
        public Enchantment build() {
            return new Enchantment(enchantment, levels);
        }
    }
}