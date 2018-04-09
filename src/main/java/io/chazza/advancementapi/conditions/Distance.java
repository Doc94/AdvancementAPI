package io.chazza.advancementapi.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;
import io.chazza.advancementapi.conditions.Range.RangeBuilder;

/**
 * The distance object contains information about the distance between the
 * advancement-earning player and an unspecified origin. The origin cannot be
 * directly defined but changes depending on the trigger; see each trigger that
 * uses this for specific information. All options are available anywhere that
 * this location object is used.
 * <p>
 * <b>1. "x", "y", "z"</b>
 * <p>
 * The "x", "y", and "z" {@link Range}s each check if the player is a number of
 * blocks in either direction of the origin in the specified axis. You do not
 * need to specify all of the axes. For example, the following checks if the
 * player is within 40 blocks in either the positive or negative "x" direction
 * of the origin.
 * 
 * <pre>
 * Distance.builder(Range.builder().max(40)).build();
 * </pre>
 * 
 * <b>2. "absolute"</b>
 * <p>
 * The "absolute" {@link Range} checks if the player is within a number of
 * blocks on all axes. You would use this instead of "x/y/z" if all axes are
 * uniform. The following checks if the player is outside a 5-block range of an
 * origin.
 * 
 * <pre>
 * Distance.absolut(Range.builder().max(5)).build();
 * </pre>
 * 
 * <b>3. "horizontal"</b>
 * <p>
 * The "horizontal" {@link Range} checks if the player is within a number of
 * blocks on the "x" and "z" axes, ignoring the "y" axis. The following checks
 * if the player is within a 10-block range of an origin, but only on the "x"
 * and "z" axes.
 * 
 * <pre>
 * Distance.horizontal(Range.builder().max(10)).build();
 * </pre>
 * 
 * <b>Implementation default</b>
 * <p>
 * Setting no values will end in a absolut distance with the range of 1.
 * 
 * <pre>
 * Range.builder().build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see https://www.minecraftforum.net/forums/minecraft-java-edition/redstone-discussion-and/commands-command-blocks-and/2809368#GenericDistance
 */
public class Distance implements Jsonable {
    private RangeBuilder x;
    private RangeBuilder y;
    private RangeBuilder z;

    private RangeBuilder range;
    private boolean absolut;

    private Distance(RangeBuilder x, RangeBuilder y, RangeBuilder z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private Distance(RangeBuilder range, boolean absolut) {
        this.range = range;
        this.absolut = absolut;
    }

    /**
     * Returns a {@link DistanceBuilder} for building {@link Range}s.
     * 
     * @return the builder
     */
    public static DistanceBuilder builder() {
        return new DistanceBuilder();
    }

    @Override
    public JsonElement toJson() {
        JsonObject distanceObj = new JsonObject();
        if (range != null) {
            if (absolut) {
                distanceObj.add("absolut", range.build().toJson());
                return distanceObj;
            }
            distanceObj.add("horizontal", range.build().toJson());
            return distanceObj;
        }
        //@formatter:off
        if (x != null) distanceObj.add("x", x.build().toJson());
        if (y != null) distanceObj.add("y", y.build().toJson());
        if (z != null) distanceObj.add("z", z.build().toJson());
        //@formatter:on
        return distanceObj;
    }

    public static class DistanceBuilder implements Builder<Distance> {
        private RangeBuilder x;
        private RangeBuilder y;
        private RangeBuilder z;
        private boolean useCoordinates = false;

        private RangeBuilder range = Range.builder();
        private boolean absolut = true;

        private DistanceBuilder() {
            // builder pattern
        }

        /**
         * The "x" {@link Range} checks if the player is a number of blocks in
         * this direction (axe) of the origin.
         * <p>
         * If set a setted absolut or horizontal range will be ignored.
         * 
         * @param x the {@link RangeBuilder} on x
         * @return this bilder
         */
        public DistanceBuilder x(RangeBuilder x) {
            this.x = x;
            useCoordinates = true;
            absolut = false;
            return this;
        }

        /**
         * The "y" {@link Range} checks if the player is a number of blocks in
         * this direction (axe) of the origin.
         * <p>
         * If set a setted absolut or horizontal range will be ignored.
         * 
         * @param y the {@link RangeBuilder} on y
         * @return this builder
         */
        public DistanceBuilder y(RangeBuilder y) {
            this.y = y;
            useCoordinates = true;
            absolut = false;
            return this;
        }

        /**
         * The "y" {@link Range} checks if the player is a number of blocks in
         * this direction of the origin.
         * <p>
         * If set a setted absolut or horizontal range will be ignored.
         * 
         * @param z the {@link RangeBuilder} on y
         * @return this builder
         */
        public DistanceBuilder z(RangeBuilder z) {
            this.z = z;
            useCoordinates = true;
            absolut = false;
            return this;
        }

        /**
         * The "absolute" {@link Range} checks if the player is within a number
         * of blocks on all axes. You would use this instead of "x/y/z" if all
         * axes are uniform.
         * 
         * @param absolutRangeBuilder the {@link RangeBuilder} on all axes
         * @return this builder
         */
        public DistanceBuilder absolut(RangeBuilder absolutRangeBuilder) {
            this.range = absolutRangeBuilder;
            this.absolut = true;
            this.useCoordinates = false;
            return this;
        }

        /**
         * The "horizontal" {@link Range} checks if the player is within a
         * number of blocks on the "x" and "z" axes, ignoring the "y" axis.
         * 
         * @param horizontalRangeBuilder the {@link RangeBuilder} on "x" and "z"
         * axes
         * @return this builder
         */
        public DistanceBuilder horizontal(RangeBuilder horizontalRangeBuilder) {
            this.range = horizontalRangeBuilder;
            this.absolut = false;
            this.useCoordinates = false;
            return this;
        }

        @Override
        public Distance build() {
            if (useCoordinates) {
                return new Distance(x, y, z);
            }
            return new Distance(range, absolut);
        }
    }
}