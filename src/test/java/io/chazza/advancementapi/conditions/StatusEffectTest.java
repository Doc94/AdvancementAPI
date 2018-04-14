package io.chazza.advancementapi.conditions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.conditions.enums.Effect;
import io.chazza.advancementapi.conditions.primitive.Range;

public class StatusEffectTest {
    private static final Gson gson = new Gson();
    private StatusEffect underTest;

    @Test
    public void testStatusEffect_GIVEN_NoArgs_THEN_ExpectJsonToBeEffect() {
        underTest = StatusEffect.builder(Effect.LEVITATION).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"minecraft:levitation\":{}}"));
    }

    @Test
    public void testStatusEffect_GIVEN_TwoEffects_THEN_ExpectJsonToHaveTwoEffects() {
        underTest = StatusEffect.builder(Effect.LEVITATION).build();
        StatusEffect secondEffect = StatusEffect.builder(Effect.SPEED).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        testHolder.add(secondEffect.getJsonKey(), secondEffect.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"minecraft:levitation\":{},\"minecraft:speed\":{}}"));
    }

    @Test
    public void testStatusEffect_GIVEN_Amplifier_THEN_ExpectJsonToBeWithAmplifier() {
        underTest = StatusEffect.builder(Effect.LEVITATION).amplifier(Range.builder()).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"minecraft:levitation\":{\"amplifier\":1}}"));
    }

    @Test
    public void testStatusEffect_GIVEN_Ambient_THEN_ExpectJsonToBeWithAmbient() {
        underTest = StatusEffect.builder(Effect.LEVITATION).ambient(false).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"minecraft:levitation\":{\"ambient\":false}}"));
    }

    @Test
    public void testStatusEffect_GIVEN_Duration_THEN_ExpectJsonToBeWithDuration() {
        underTest = StatusEffect.builder(Effect.LEVITATION).duration(Range.builder()).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"minecraft:levitation\":{\"duration\":1}}"));
    }

    @Test
    public void testStatusEffect_GIVEN_Visible_THEN_ExpectJsonToBeWithVisible() {
        underTest = StatusEffect.builder(Effect.LEVITATION).visible(true).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"minecraft:levitation\":{\"visible\":true}}"));
    }

    @Test
    public void testStatusEffect_GIVEN_MultipleAttributes_THEN_ExpectJsonToBeWithAll() {
        underTest = StatusEffect.builder(Effect.LEVITATION).visible(false).duration(Range.builder()).visible(true)
                .build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"minecraft:levitation\":{\"duration\":1,\"visible\":true}}"));
    }
}