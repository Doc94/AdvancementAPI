package io.chazza.advancementapi;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;
import io.chazza.advancementapi.common.KeyedJsonable;
import io.chazza.advancementapi.conditions.Location;
import io.chazza.advancementapi.conditions.Location.LocationBuilder;
import io.chazza.advancementapi.conditions.StatusEffect;
import io.chazza.advancementapi.conditions.StatusEffect.StatusEffectBuilder;

/**
 * Condition is the base class for adding conditions to {@link Trigger}.
 * <p>
 * A condition consists of two values. A key and a {@link Builder}. The key is
 * representing the name of this condition part (e.g.
 * <code>source_entitiy</code>, <code>items</code>, etc.) whereas the
 * {@link Builder} represents the condition itself.
 * <p>
 * The condition for checking a biome would be:
 * 
 * <pre>
 * "conditions": {
 *     "biome": "minecraft:void"
 * }
 * </pre>
 * 
 * This would be translated into code as:
 * 
 * <pre>
 * Condition.builder("biome", Location.builder().biome(Biome.VOID));
 * </pre>
 * 
 * @author ysl3000
 * @author Kaonashi97
 */
public class Condition implements KeyedJsonable {
    private String name;
    private Builder<? extends Jsonable> condition;
    private boolean wrapInJsonObject = false;

    private Condition(String name, Builder<? extends Jsonable> condition) {
        this.name = name;
        this.condition = condition;

        Jsonable preBuiltCondition = condition.build();
        if (preBuiltCondition instanceof KeyedJsonable) {
            if (name == null) {
                this.name = ((KeyedJsonable) preBuiltCondition).getJsonKey();
            } else {
                wrapInJsonObject = true;
            }
        }
    }

    /**
     * Returns a {@link ConditionBuilder} for building {@link Condition}s.
     * 
     * @param name the condition name (type)
     * @param jsonable the condition builder
     * @return the builder
     */
    public static ConditionBuilder builder(String name, Builder<? extends Jsonable> jsonable) {
        return new ConditionBuilder().name(name).set(jsonable);
    }

    /**
     * Returns a {@link ConditionBuilder} for building {@link Condition}s.
     * <p>
     * Use this method if you want {@link Location} or {@link StatusEffect} as
     * condition. The {@link Location} and {@link StatusEffect} will choose
     * their key on their own. You don't have to set a name for them.
     * 
     * @param keyedJsonable a {@link LocationBuilder} or
     * {@link StatusEffectBuilder}
     * @return the builder
     */
    public static ConditionBuilder builder(Builder<? extends KeyedJsonable> keyedJsonable) {
        return new ConditionBuilder().set(keyedJsonable);
    }

    /**
     * Returns a {@link ConditionBuilder} for building {@link Condition}s.
     * <p>
     * Sets a string as condition (e.g. when using with <code>from</code>,
     * <code>to</code>, <code>recipe</code>, etc.).
     * 
     * @param name the condition name (e.g. <code>from</code>)
     * @param string the condition value
     * @return the builder
     */
    public static ConditionBuilder builder(String name, String string) {
        return new ConditionBuilder().name(name).set(string);
    }

    @Override
    public String getJsonKey() {
        return name;
    }

    @Override
    public JsonElement toJson() {
        if (wrapInJsonObject) {
            JsonObject obj = new JsonObject();
            obj.add(((KeyedJsonable) condition.build()).getJsonKey(), condition.build().toJson());
            return obj;
        }
        return condition.build().toJson();
    }

    /**
     * Builder for {@link Condition}s. See {@link Condition} for more
     * information on conditions in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class ConditionBuilder implements Builder<Condition> {
        private String name;
        private Builder<? extends Jsonable> condition;

        private ConditionBuilder() {
            // builder pattern
        }

        /**
         * Sets the name of the condition. Will replace previous setted name.
         * 
         * @param name the name
         * @return this builder
         */
        public ConditionBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the condition builder. Will replace previous setted builder.
         * 
         * @param jsonable the {@link Builder}
         * @return this builder
         */
        public ConditionBuilder set(Builder<? extends Jsonable> jsonable) {
            this.condition = jsonable;
            return this;
        }

        /**
         * Sets a string as condition (e.g. when using with <code>from</code>,
         * <code>to</code>, <code>recipe</code>, etc.). Will replace previous
         * setted builder.
         * 
         * @param string the String
         * @return this builder
         */
        public ConditionBuilder set(String string) {
            condition = () -> () -> new JsonPrimitive(string);
            return this;
        }

        @Override
        public Condition build() {
            return new Condition(name, condition);
        }
    }
}