package io.chazza.advancementapi.conditions;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.KeyedJsonable;
import io.chazza.advancementapi.conditions.enums.Dimension;
import io.chazza.advancementapi.conditions.enums.Feature;
import io.chazza.advancementapi.conditions.primitive.Range;
import io.chazza.advancementapi.conditions.primitive.Range.RangeBuilder;

/**
 * A location object contains a small amount of data to specify an origin,
 * biome, or generated structure. All options are available anywhere that this
 * location object is used.
 * <p>
 * <b>1. "position"</b>
 * <p>
 * The "position" object contains "x", "y", and "z" {@link Range}s to check the
 * global position of the player in the world. You do not need to specify all of
 * the axes. For example, the following checks if the player is at Y62 or lower.
 * 
 * <pre>
 * Location.builder().y(Range.builder().max(62)).build();
 * </pre>
 * 
 * <b>2. "biome"</b>
 * <p>
 * The "biome" string specifies the name ID of the {@link Biome} that the player
 * must stand within. The following checks if the player has visited the
 * "minecraft:desert" biome.
 * 
 * <pre>
 * Location.builder().biome(Biome.DESERT).build();
 * </pre>
 * 
 * <b>3. "feature"</b>
 * <p>
 * The "feature" string specifies the name ID of a {@link Feature structure}.
 * The player must be standing within the bounding box of that structure to be
 * detected. The following checks if the player is inside an "EndCity"
 * structure.
 * 
 * <pre>
 * Location.builder().feature(Feature.ENDCITY).build();
 * </pre>
 * 
 * <b>4. "dimension"</b>
 * <p>
 * The "dimension" string specifies the name ID of a {@link Dimension} to find
 * the player in. The following checks if the player is anywhere in the nether.
 * 
 * <pre>
 * Location.builder().dimension(Dimension.OVERWORLD).build();
 * </pre>
 * 
 * <b>Implementation default</b>
 * <p>
 * Setting no values will end in a location with dimension set to "overworld".
 * 
 * <pre>
 * Location.builder().build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see "https://github.com/skylinerw/guides/blob/master/java/advancements/data_structures.md#-shared-location-object"
 */
public class Location implements KeyedJsonable {
    private RangeBuilder x;
    private RangeBuilder y;
    private RangeBuilder z;

    private Biome biome;
    private Feature feature;
    private Dimension dimension;

    private Location(RangeBuilder x, RangeBuilder y, RangeBuilder z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private Location(Biome biome) {
        this.biome = biome;
    }

    private Location(Feature feature) {
        this.feature = feature;
    }

    private Location(Dimension dimension) {
        this.dimension = dimension;
    }

    /**
     * Returns a {@link LocationBuilder} for building {@link Location}s.
     * 
     * @return the builder
     */
    public static LocationBuilder builder() {
        return new LocationBuilder();
    }

    @Override
    public JsonElement toJson() {
        if (biome != null) {
            return new JsonPrimitive(NamespacedKey.minecraft(biome.toString().toLowerCase()).toString());
        }
        if (feature != null) {
            return new JsonPrimitive(feature.toString());
        }
        if (dimension != null) {
            return new JsonPrimitive(dimension.toString());
        }
        JsonObject locationObj = new JsonObject();
        //@formatter:off
        if (x != null) locationObj.add("x", x.build().toJson());
        if (y != null) locationObj.add("y", y.build().toJson());
        if (z != null) locationObj.add("z", z.build().toJson());
        //@formatter:on
        return locationObj;
    }

    @Override
    public String getJsonKey() {
        if (biome != null) {
            return "biome";
        }
        if (feature != null) {
            return "feature";
        }
        if (dimension != null) {
            return "dimension";
        }
        return "position";
    }

    /**
     * Builder for {@link Location}s. See {@link Location} for more information
     * on locations in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class LocationBuilder implements Builder<Location> {
        private RangeBuilder x;
        private RangeBuilder y;
        private RangeBuilder z;
        private boolean useCoordinates = false;

        private Biome biome;
        private boolean useBiome = false;

        private Feature feature;
        private boolean useFeature = false;

        private Dimension dimension = Dimension.OVERWORLD;

        private LocationBuilder() {
            // builder pattern
        }

        /**
         * The "position" object contains "x" range to check the global position
         * of the player in the world.
         * 
         * @param x the {@link RangeBuilder} on x
         * @return this builder
         */
        public LocationBuilder x(RangeBuilder x) {
            this.x = x;
            this.useCoordinates = true;
            this.useBiome = this.useFeature = false;
            return this;
        }

        /**
         * The "position" object contains "y" range to check the global position
         * of the player in the world.
         * 
         * @param y the {@link RangeBuilder} on y
         * @return this builder
         */
        public LocationBuilder y(RangeBuilder y) {
            this.y = y;
            this.useCoordinates = true;
            this.useBiome = this.useFeature = false;
            return this;
        }

        /**
         * The "position" object contains "z" range to check the global position
         * of the player in the world.
         * 
         * @param z the {@link RangeBuilder} on z
         * @return this builder
         */
        public LocationBuilder z(RangeBuilder z) {
            this.z = z;
            this.useCoordinates = true;
            this.useBiome = this.useFeature = false;
            return this;
        }

        /**
         * The "biome" string specifies the name ID of the {@link Biome} that
         * the player must stand within.
         * 
         * @param biome the biome
         * @return this builder
         */
        public LocationBuilder biome(Biome biome) {
            this.biome = biome;
            this.useBiome = true;
            this.useCoordinates = this.useFeature = false;
            return this;
        }

        /**
         * The "feature" string specifies the name ID of a {@link Feature
         * structure}. The player must be standing within the bounding box of
         * that structure to be detected.
         * 
         * @param feature the structure
         * @return this builder
         */
        public LocationBuilder feature(Feature feature) {
            this.feature = feature;
            this.useFeature = true;
            this.useBiome = this.useCoordinates = false;
            return this;
        }

        /**
         * The "dimension" string specifies the name ID of a {@link Dimension}
         * to find the player in.
         * 
         * @param dimension the dimension
         * @return this builder
         */
        public LocationBuilder dimension(Dimension dimension) {
            this.dimension = dimension;
            this.useBiome = this.useCoordinates = this.useFeature = false;
            return this;
        }

        @Override
        public Location build() {
            //@formatter:off
            if (useBiome)       return new Location(biome);
            if (useFeature)     return new Location(feature);
            if (useCoordinates) return new Location(x, y, z);
            //@formatter:on
            return new Location(dimension);
        }
    }
}