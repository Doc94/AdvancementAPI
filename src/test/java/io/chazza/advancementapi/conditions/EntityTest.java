package io.chazza.advancementapi.conditions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

import io.chazza.advancementapi.conditions.enums.Effect;

public class EntityTest {
    private static final Gson gson = new Gson();
    private Entity underTest;

    @Test
    public void testEntity_GIVEN_Type_THEN_ExpectJsonToBeWithType() {
        underTest = Entity.builder().type("minecraft:creeper").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"type\":\"minecraft:creeper\"}"));
    }

    @Test
    public void testEntity_GIVEN_Distance_THEN_ExpectJsonToBeWithDistance() throws Exception {
        underTest = Entity.builder().distance(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"distance\":1}"));
    }

    @Test
    public void testEntity_GIVEN_Location_THEN_ExpectJsonToBeWithLocation() throws Exception {
        underTest = Entity.builder().location(Location.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"location\":{\"dimension\":\"overworld\"}}"));
    }

    @Test
    public void testEntity_GIVEN_StatusEffect_THEN_ExpectJsonToBeWithEffect() throws Exception {
        underTest = Entity.builder().effects(StatusEffect.builder(Effect.SPEED)).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"effects\":{\"minecraft:speed\":{\"visible\":true}}}"));
    }

    @Test
    public void testEntity_GIVEN_RawNBT_THEN_ExpectJsonToBeWithRawNBT() throws Exception {
        underTest = Entity.builder().nbt("{Tags:[\"findme\"]}").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"nbt\":\"{Tags:[\\\"findme\\\"]}\"}"));
    }
}