package io.chazza.advancementapi.conditions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class BlockTest {
    private static final Gson gson = new Gson();
    private Block underTest;

    @Test
    public void testBlock_GIVEN_Block_THEN_ExpectJsonToBeWithBlock() {
        underTest = Block.builder("minecraft:stone").build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"block\":\"minecraft:stone\"}"));
    }
}