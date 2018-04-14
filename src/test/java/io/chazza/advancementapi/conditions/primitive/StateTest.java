package io.chazza.advancementapi.conditions.primitive;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;

public class StateTest {
    private static final Gson gson = new Gson();
    private State underTest;

    @Test
    public void testState_GIVEN_State_THEN_ExpectJsonToBeWithState() {
        underTest = State.builder().add("type", "fern").add("level", "14").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"type\":\"fern\",\"level\":\"14\"}"));
    }

    @Test
    public void testState_GIVEN_ClearState_THEN_ExpectJsonToWithoutState() {
        underTest = State.builder().add("type", "fern").clearStates().build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{}"));
    }
}