package io.chazza.advancementapi.conditions.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;
import io.chazza.advancementapi.conditions.primitive.Range.RangeBuilder;

/**
 * The slots object contains generic information about all slots in the
 * inventory. Within it are three possible {@link Range}s to specify: occupied,
 * full, and empty. The armor and offhand slots are included in these checks.
 * <p>
 * The occupied range indicates the number of slots that have an item in it. The
 * full range indicates the number of slots that have the highest number of
 * items possible for that slot (such as 1 diamond pickaxe or 64 stone blocks).
 * The empty range indicates the number of empty slots.
 * <p>
 * For example, the following will trigger when the player has exactly 10 empty
 * slots in their inventory at the time that their inventory gets updated.
 * 
 * <pre>
 * Slot.builder().empty(Range.builder().range(10)).build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#1-slots
 */
public class Slot implements Jsonable {
    private RangeBuilder occupied;
    private RangeBuilder full;
    private RangeBuilder empty;

    private Slot(RangeBuilder occupied, RangeBuilder full, RangeBuilder empty) {
        this.occupied = occupied;
        this.full = full;
        this.empty = empty;
    }

    /**
     * Returns a {@link SlotBuilder} for building {@link Slot}s.
     * 
     * @return the builder
     */
    public static SlotBuilder builder() {
        return new SlotBuilder();
    }

    @Override
    public JsonElement toJson() {
        JsonObject slotsObj = new JsonObject();
        //@formatter:off
        if (occupied != null) slotsObj.add("occupied", occupied.build().toJson());
        if (full != null) slotsObj.add("full", full.build().toJson());
        if (empty != null) slotsObj.add("empty", empty.build().toJson());
        //@formatter:on
        return slotsObj;
    }

    /**
     * Builder for {@link Slot}s. See {@link Slot} for more information on slots
     * in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class SlotBuilder implements Builder<Slot> {
        private RangeBuilder occupied;
        private RangeBuilder full;
        private RangeBuilder empty;

        private SlotBuilder() {
            // builder pattern
        }

        /**
         * The occupied range indicates the number of slots that have an item in
         * it.
         * 
         * @param occupiedRangeBuilder a {@link RangeBuilder}
         * @return this builder
         */
        public SlotBuilder occupied(RangeBuilder occupiedRangeBuilder) {
            this.occupied = occupiedRangeBuilder;
            return this;
        }

        /**
         * The full range indicates the number of slots that have the highest
         * number of items possible for that slot (such as 1 diamond pickaxe or
         * 64 stone blocks).
         * 
         * @param fullRangeBuilder a {@link RangeBuilder}
         * @return this builder
         */
        public SlotBuilder full(RangeBuilder fullRangeBuilder) {
            this.full = fullRangeBuilder;
            return this;
        }

        /**
         * The empty range indicates the number of empty slots.
         * 
         * @param emptyRangeBuilder a {@link RangeBuilder}
         * @return this builder
         */
        public SlotBuilder empty(RangeBuilder emptyRangeBuilder) {
            this.empty = emptyRangeBuilder;
            return this;
        }

        @Override
        public Slot build() {
            return new Slot(occupied, full, empty);
        }
    }
}