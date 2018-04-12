package io.chazza.advancementapi.conditions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

public class StateTest {
    private static final Gson gson = new Gson();
    private State underTest;

    @Test
    public void testState_GIVEN_State_THEN_ExpectJsonToBeWithState() throws Exception {
        underTest = State.builder().add("type", "fern").add("level", "14").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"type\":\"fern\",\"level\":\"14\"}"));
    }
}