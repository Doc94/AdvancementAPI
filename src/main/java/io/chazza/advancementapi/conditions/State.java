package io.chazza.advancementapi.conditions;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;

public class State implements Jsonable {
    private Map<String, String> states = new LinkedHashMap<>();

    public State(Map<String, String> states) {
        this.states = states;
    }

    public static StateBuilder builder() {
        return new StateBuilder();
    }

    public String getJsonKey() {
        return "state";
    }

    @Override
    public JsonElement toJson() {
        JsonObject statesObj = new JsonObject();
        states.forEach((s, v) -> statesObj.addProperty(s, v));
        return statesObj;
    }

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
         * @param key the key
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
            return new State(states);
        }
    }
}