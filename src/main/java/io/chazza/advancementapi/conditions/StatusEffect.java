package io.chazza.advancementapi.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;
import io.chazza.advancementapi.conditions.Range.RangeBuilder;
import io.chazza.advancementapi.conditions.enums.Effect;

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
 * "effects_object": {
 *     "minecraft:levitation": {
 *         "amplifier": {
 *             "min": 10
 *          }
 *     }
 * }
 * </pre>
 * 
 * <b>2. "duration"</b>
 * <p>
 * The "duration" range checks the remaining duration in ticks of the specified
 * effect. The following checks if the effect has at least 15 seconds (300
 * ticks) remaining.
 * 
 * <pre>
 * "effects_object": {
 *     "minecraft:levitation": {
 *         "duration": {
 *             "max": 300
 *         }
 *     }
 * }<pre>
 * 
 * <b>3. "ambient"</b>
 * <p>
 * The "ambient" boolean checks if the effect has the "ambient" flag set to true.
 * 
 * <pre>"effects_object": {
 *     "minecraft:levitation": {
 *         "ambient": true
 *     }
 * }
 * </pre>
 * 
 * <b>4. "visible"</b>
 * <p>
 * The "visible" boolean checks if the effect has the "visible" flag set to
 * true.
 * 
 * <pre>
 * "effects_object": {
 *     "minecraft:levitation": {
 *         "visible": true
 *     }
 * }
 * </pre>
 * 
 * @author Kaonashi97
 * @see https://www.minecraftforum.net/forums/minecraft-java-edition/redstone-discussion-and/commands-command-blocks-and/2809368#GenericEffects
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

    public static StatusEffectBuilder builder(Effect effectType) {
        return new StatusEffectBuilder(effectType);
    }

    @Override
    public JsonElement toJson() {
        JsonObject statusEffectObj = new JsonObject();

        JsonObject attributesObj = new JsonObject();
        //@formatter:off
        if (amplifier != null) attributesObj.add("amplifier", amplifier.build().toJson());
        if (duration != null) attributesObj.add("duration", duration.build().toJson());
        if (ambient != null) attributesObj.add("ambient", new JsonPrimitive(ambient));
        if (visible != null) attributesObj.add("visible", new JsonPrimitive(visible));
        //@formatter:on
        if (attributesObj.size() == 0) {
            attributesObj.add("visible", new JsonPrimitive(true));
        }
        statusEffectObj.add(effectType.toString().toLowerCase(), attributesObj);

        return statusEffectObj;
    }

    public static class StatusEffectBuilder implements Builder<StatusEffect> {
        private Effect effectType;
        private RangeBuilder amplifier;
        private RangeBuilder duration;
        private Boolean ambient;
        private Boolean visible;

        private StatusEffectBuilder(Effect effectType) {
            this.effectType = effectType;
        }

        public StatusEffectBuilder amplifier(RangeBuilder amplifierRange) {
            this.amplifier = amplifierRange;
            return this;
        }

        public StatusEffectBuilder duration(RangeBuilder durationRange) {
            this.duration = durationRange;
            return this;
        }

        public StatusEffectBuilder ambient(boolean ambient) {
            this.ambient = new Boolean(ambient);
            return this;
        }

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