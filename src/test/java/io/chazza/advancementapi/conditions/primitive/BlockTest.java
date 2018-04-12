package io.chazza.advancementapi.conditions.primitive;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

public class BlockTest {
    private static final Gson gson = new Gson();
    private Block underTest;

    @Test
    public void testBlock_GIVEN_Block_THEN_ExpectJsonToBeWithBlock() {
        underTest = Block.builder("minecraft:stone").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("\"minecraft:stone\""));
    }
}