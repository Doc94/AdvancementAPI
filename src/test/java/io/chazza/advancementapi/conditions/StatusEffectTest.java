package io.chazza.advancementapi.conditions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

import io.chazza.advancementapi.conditions.enums.Effect;

public class StatusEffectTest {
    private static final Gson gson = new Gson();
    private StatusEffect underTest;

    @Test
    public void testStatusEffect_GIVEN_NoArgs_THEN_ExpectJsonToBeEffect() {
        underTest = StatusEffect.builder(Effect.LEVITATION).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"minecraft:levitation\":{\"visible\":true}}"));
    }

    @Test
    public void testStatusEffect_GIVEN_Amplifier_THEN_ExpectJsonToBeWithAmplifier() throws Exception {
        underTest = StatusEffect.builder(Effect.LEVITATION).amplifier(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"minecraft:levitation\":{\"amplifier\":1}}"));
    }

    @Test
    public void testStatusEffect_GIVEN_Ambient_THEN_ExpectJsonToBeWithAmbient() throws Exception {
        underTest = StatusEffect.builder(Effect.LEVITATION).ambient(false).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"minecraft:levitation\":{\"ambient\":false}}"));
    }

    @Test
    public void testStatusEffect_GIVEN_Duration_THEN_ExpectJsonToBeWithDuration() throws Exception {
        underTest = StatusEffect.builder(Effect.LEVITATION).duration(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"minecraft:levitation\":{\"duration\":1}}"));
    }

    @Test
    public void testStatusEffect_GIVEN_Visible_THEN_ExpectJsonToBeWithVisible() throws Exception {
        underTest = StatusEffect.builder(Effect.LEVITATION).visible(true).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"minecraft:levitation\":{\"visible\":true}}"));
    }

    @Test
    public void testStatusEffect_GIVEN_MultipleAttributes_THEN_ExpectJsonToBeWithAll() throws Exception {
        underTest = StatusEffect.builder(Effect.LEVITATION).visible(false).duration(Range.builder()).visible(true)
                .build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"minecraft:levitation\":{\"duration\":1,\"visible\":true}}"));
    }
}