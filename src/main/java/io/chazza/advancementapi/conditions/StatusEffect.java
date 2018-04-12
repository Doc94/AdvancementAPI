package io.chazza.advancementapi.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;
import io.chazza.advancementapi.conditions.enums.Effect;
import io.chazza.advancementapi.conditions.primitive.Range.RangeBuilder;

/**
 * A status effect object contains nested objects, whose key names reflect
 * status effect IDS. All options are available anywhere that this item object
 * is used.
 * <p>
 * <b>1. "amplifier"</b>
 * <p>
 * The "amplifier" range checks the amplifier of the specified effect. The
 * following checks if the amplifier is 10 or higher.
 * 
 * <pre>
 * StatusEffect.builder(Effect.LEVITATION).amplifier(Range.builder().min(10)).build();
 * </pre>
 * 
 * <b>2. "duration"</b>
 * <p>
 * The "duration" range checks the remaining duration in ticks of the specified
 * effect. The following checks if the effect has at least 15 seconds (300
 * ticks) remaining.
 * 
 * <pre>
 * StatusEffect.builder(Effect.LEVITATION).duration(Range.builder().max(300)).build();
 * <pre>
 * 
 * <b>3. "ambient"</b>
 * <p>
 * The "ambient" boolean checks if the effect has the "ambient" flag set to true.
 * 
 * <pre>
 * StatusEffect.builder(Effect.LEVITATION).ambient(true).build();
 * </pre>
 * 
 * <b>4. "visible"</b>
 * <p>
 * The "visible" boolean checks if the effect has the "visible" flag set to
 * true.
 * 
 * <pre>
 * StatusEffect.builder(Effect.LEVITATION).visible(true).build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see https://github.com/skylinerw/guides/blob/master/java/advancements/data_structures.md#-shared-status-effects-object
 */
public class StatusEffect implements Jsonable {
    private Effect effectType;
    private RangeBuilder amplifier;
    private RangeBuilder duration;
    private Boolean ambient;
    private Boolean visible;

    private StatusEffect(Effect effectType, RangeBuilder amplifier, RangeBuilder duration, Boolean ambient,
            Boolean visible) {
        this.effectType = effectType;
        this.amplifier = amplifier;
        this.duration = duration;
        this.ambient = ambient;
        this.visible = visible;
    }

    /**
     * Returns a {@link StatusEffectBuilder} for building {@link StatusEffect}s.
     * 
     * @param effectType the {@link Effect} type
     * @return the builder
     */
    public static StatusEffectBuilder builder(Effect effectType) {
        return new StatusEffectBuilder(effectType);
    }

    @Override
    public JsonElement toJson() {
        JsonObject statusEffectObj = new JsonObject();
        //@formatter:off
        if (amplifier != null) statusEffectObj.add("amplifier", amplifier.build().toJson());
        if (duration != null) statusEffectObj.add("duration", duration.build().toJson());
        if (ambient != null) statusEffectObj.add("ambient", new JsonPrimitive(ambient));
        if (visible != null) statusEffectObj.add("visible", new JsonPrimitive(visible));
        //@formatter:on
        return statusEffectObj;
    }

    public String getJsonKey() {
        return effectType.toString().toLowerCase();
    }

    /**
     * Builder for {@link StatusEffect}s. See {@link StatusEffect} for more
     * information on status effects in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class StatusEffectBuilder implements Builder<StatusEffect> {
        private Effect effectType;
        private RangeBuilder amplifier;
        private RangeBuilder duration;
        private Boolean ambient;
        private Boolean visible;

        private StatusEffectBuilder(Effect effectType) {
            this.effectType = effectType;
        }

        /**
         * The "amplifier" range checks the amplifier of the specified effect.
         * 
         * @param amplifierRange the {@link RangeBuilder} for the amplifier
         * @return this builder
         */
        public StatusEffectBuilder amplifier(RangeBuilder amplifierRange) {
            this.amplifier = amplifierRange;
            return this;
        }

        /**
         * The "duration" range checks the remaining duration in ticks of the
         * specified effect.
         * 
         * @param durationRange the {@link RangeBuilder} for the duration
         * @return this builder
         */
        public StatusEffectBuilder duration(RangeBuilder durationRange) {
            this.duration = durationRange;
            return this;
        }

        /**
         * The "ambient" boolean checks if the effect has the "ambient" flag set
         * to <code>true</code> or <code>false</code>.
         * 
         * @param ambient whether it should be <code>true</code> or
         * <code>false</code>
         * @return this builder
         */
        public StatusEffectBuilder ambient(boolean ambient) {
            this.ambient = new Boolean(ambient);
            return this;
        }

        /**
         * The "visible" boolean checks if the effect has the "visible" flag set
         * to <code>true</code> or <code>false</code>.
         * 
         * @param visible whether it should be <code>true</code> or
         * <code>false</code>
         * @return this builder
         */
        public StatusEffectBuilder visible(boolean visible) {
            this.visible = new Boolean(visible);
            return this;
        }

        @Override
        public StatusEffect build() {
            return new StatusEffect(effectType, amplifier, duration, ambient, visible);
        }
    }
}