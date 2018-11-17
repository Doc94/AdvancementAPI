package io.chazza.advancementapi;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.Condition.ConditionBuilder;
import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.KeyedJsonable;

/**
 * This is the base class for a representing a trigger. A trigger must have a
 * {@link TriggerType} and can have zero to more {@link Condition}s.
 * 
 * @author ysl3000
 * @author Kaonashi97
 */
public class Trigger implements KeyedJsonable {
    private TriggerType type;
    private String name;
    private List<ConditionBuilder> conditions = new LinkedList<>();

    private Trigger(TriggerType type, String name, List<ConditionBuilder> conditions) {
        this.type = type;
        this.name = name;
        this.conditions = conditions;
    }

    /**
     * Returns a {@link TriggerBuilder} for building {@link Trigger}s.
     * 
     * @param type the {@link TriggerType}
     * @param name the unique name
     * @return the builder
     */
    public static TriggerBuilder builder(TriggerType type, String name) {
        return new TriggerBuilder().type(type).name(name);
    }

    @Override
    public String getJsonKey() {
        return name;
    }

    @Override
    public JsonElement toJson() {
        JsonObject triggerObj = new JsonObject();
        triggerObj.addProperty("trigger", type.toString());
        if (!conditions.isEmpty()) {
            JsonObject conditionsObj = new JsonObject();
            conditions.forEach(conditionBuilder -> {
                Condition condition = conditionBuilder.build();
                conditionsObj.add(condition.getJsonKey(), condition.toJson());
            });
            triggerObj.add("conditions", conditionsObj);
        }
        return triggerObj;
    }

    /**
     * Builder for {@link Trigger}s. See {@link Trigger} for more information.
     * 
     * @author ysl3000
     * @author Kaonashi97
     */
    public static class TriggerBuilder implements Builder<Trigger> {
        private TriggerType type;
        private String name;
        private List<ConditionBuilder> conditions = new LinkedList<>();

        private TriggerBuilder() {
            // builder pattern
        }

        /**
         * Sets the {@link TriggerType}.
         * 
         * @param type the type
         * @return this builder
         */
        public TriggerBuilder type(TriggerType type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the trigger name. Each trigger name must be unique.
         * 
         * @param name the unique name
         * @return this builder
         */
        public TriggerBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Adds a {@link Condition} to this trigger.
         * 
         * @param condition a {@link ConditionBuilder}
         * @return this builder
         */
        public TriggerBuilder condition(ConditionBuilder condition) {
            this.conditions.add(condition);
            return this;
        }

        /**
         * Adds a {@link Collection} of {@link Condition}s to this trigger.
         * 
         * @param conditions a {@link Collection}
         * @return this builder
         */
        public TriggerBuilder conditions(Collection<? extends ConditionBuilder> conditions) {
            this.conditions.addAll(conditions);
            return this;
        }

        /**
         * Clears the saved {@link Condition}s.
         * 
         * @return this builder
         */
        public TriggerBuilder clearConditions() {
            this.conditions.clear();
            return this;
        }

        @Override
        public Trigger build() {
            return new Trigger(type, name, Collections.unmodifiableList(this.conditions));
        }
    }
}