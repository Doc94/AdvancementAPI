package io.chazza.advancementapi.conditions.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;

/**
 * A large number of advancement features make use of a "range" object, which is
 * a comparison of the incoming number (such as the number of occupied inventory
 * slots) to the specified range of numbers.
 * <p>
 * To check for an exact value, simply declare the range as a number. The
 * following checks if the compared value is exactly 3.
 * 
 * <pre>
 * Range.builder().range(3).build();
 * </pre>
 * 
 * To check between two values, the range must be specified as an object
 * containing "min" and "max" numbers. The following checks if the compared
 * value is between 1 and 3.
 * 
 * <pre>
 * Range.builder().min(1).max(3).build();
 * </pre>
 * 
 * You can alternatively specify only the minimum or only the maximum, which
 * will ignore a check for the opposing limiter. For example, the following
 * checks if there are at least 3 occupied slots. The player's inventory could
 * have 7 occupied slots and they will still match.
 * 
 * <pre>
 * Range.builder().min(3).build();
 * </pre>
 * 
 * Versus the opposite, where the following checks if there are at most 2
 * occupied slots. The player's inventory could have 0 occupied slots and they
 * will still match.
 * 
 * <pre>
 * Range.builder().max(2).build();
 * </pre>
 * 
 * <b>Implementation default</b>
 * <p>
 * Setting no values will end in a range with the value of 1.
 * 
 * <pre>
 * Range.builder().build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see https://github.com/skylinerw/guides/blob/master/java/advancements/data_structures.md#-shared-range
 */
public class Range implements Jsonable {
    private Integer min;
    private Integer max;
    private Integer range;

    private Range(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    private Range(Integer value) {
        this.range = value;
    }

    @Override
    public JsonElement toJson() {
        if (this.min == null && this.max == null) {
            return new JsonPrimitive(range);
        }
        JsonObject rangeObj = new JsonObject();
        //@formatter:off
        if (min != null) rangeObj.addProperty("min", min);
        if (max != null) rangeObj.addProperty("max", max);
        //@formatter:on
        return rangeObj;
    }

    /**
     * Returns a {@link RangeBuilder} for building {@link Range}s.
     * 
     * @return the builder
     */
    public static RangeBuilder builder() {
        return new RangeBuilder();
    }

    /**
     * Builder for {@link Range}s. See {@link Range} for more information on
     * ranges in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class RangeBuilder implements Builder<Range> {
        private Integer min;
        private Integer max;
        private Integer range = new Integer(1);
        private boolean isRange = true;

        private RangeBuilder() {
            // builder pattern
        }

        /**
         * To check between two values.
         * <p>
         * Sets the minimal value. If set a setted range will be ignored.
         * 
         * @param min the minimal value
         * @return this builder
         */
        public RangeBuilder min(int min) {
            this.min = new Integer(min);
            this.isRange = false;
            return this;
        }

        /**
         * To check between two values.
         * <p>
         * Sets the maximal value. If set a setted range will be ignored.
         * 
         * @param max the maximal value
         * @return this builder
         */
        public RangeBuilder max(int max) {
            this.max = new Integer(max);
            this.isRange = false;
            return this;
        }

        /**
         * To check for an exact value.
         * 
         * @param value the exacte value
         * @return this builder
         */
        public RangeBuilder range(int value) {
            this.range = new Integer(value);
            this.isRange = true;
            return this;
        }

        @Override
        public Range build() {
            if (isRange) {
                return new Range(range);
            }
            return new Range(min, max);
        }
    }
}