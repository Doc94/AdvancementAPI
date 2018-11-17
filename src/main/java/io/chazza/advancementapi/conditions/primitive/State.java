package io.chazza.advancementapi.conditions.primitive;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.Condition;
import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;

/**
 * For the first part see {@link Block}.
 * <p>
 * <b>2. state</b>
 * <p>
 * The state object contains a list of custom keys, much like criteria does. The
 * names for these keys will correspond to the blockstate name you want to
 * detect, and the value corresponds to possible values for that blockstate. For
 * "minecraft:tallgrass", the type blockstate specifies which of the tallgrass
 * blocks it is. The block string must be specified to use this condition. The
 * following checks if the tallgrass is a fern.
 * <p>
 * Use the {@link State} object for adding states to your {@link Condition}.
 * 
 * <pre>
 * Condition.add(Block.builder("minecraft:tallgrass")).add(State.builder().add("type", "fern")).build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see "https://github.com/skylinerw/guides/blob/master/java/advancements/data_structures.md#-shared-block-object"
 */
public class State implements Jsonable {
    private Map<String, String> states = new LinkedHashMap<>();

    private State(Map<String, String> states) {
        this.states = states;
    }

    /**
     * Returns a {@link StateBuilder} for building {@link State}s.
     * 
     * @return the builder
     */
    public static StateBuilder builder() {
        return new StateBuilder();
    }

    @Override
    public JsonElement toJson() {
        JsonObject statesObj = new JsonObject();
        states.forEach((s, v) -> statesObj.addProperty(s, v));
        return statesObj;
    }

    /**
     * Builder for {@link State}s. See {@link State} for more information on
     * block states in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class StateBuilder implements Builder<State> {
        private Map<String, String> states = new LinkedHashMap<>();

        private StateBuilder() {
            // builder pattern
        }

        /**
         * The state object contains a list of custom keys, much like criteria
         * does. The names for these keys will correspond to the blockstate name
         * you want to detect, and the value corresponds to possible values for
         * that blockstate.
         * 
         * @param state the key
         * @param value the value
         * @return this builder
         */
        public StateBuilder add(String state, String value) {
            this.states.put(state, value);
            return this;
        }

        /**
         * Removes all saved states from this block.
         * 
         * @return this builder
         */
        public StateBuilder clearStates() {
            states.clear();
            return this;
        }

        @Override
        public State build() {
            return new State(Collections.unmodifiableMap(states));
        }
    }
}