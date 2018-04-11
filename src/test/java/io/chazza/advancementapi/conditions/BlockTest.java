package io.chazza.advancementapi.conditions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;

public class BlockTest {
    private static final Gson gson = new Gson();
    private Block underTest;

    @Test
    public void testBlock_GIVEN_Block_THEN_ExpectJsonToBeWithBlock() {
        underTest = Block.builder("minecraft:stone").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"block\":\"minecraft:stone\"}"));
    }
    
    @Test
    public void testBlock_GIVEN_State_THEN_ExpectJsonToBeWithState() throws Exception {
        underTest = Block.builder("minecraft:tallgrass").state("type", "fern").build();
        
        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"block\":\"minecraft:tallgrass\",\"state\":{\"type\":\"fern\"}}"));
    }
}