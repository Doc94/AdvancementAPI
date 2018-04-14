package io.chazza.advancementapi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

public class RewardsTest {
    private static final Gson gson = new Gson();
    private Rewards underTest;

    @Test
    public void testRewards_GIVEN_Recipes_THEN_ExpectJsonToBeWithRecipes() {
        underTest = Rewards.builder().recipe("minecraft:stone").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"recipes\":[\"minecraft:stone\"]}"));
    }

    @Test
    public void testRewards_GIVEN_Loots_THEN_ExpectJsonToBeWithLoots() {
        underTest = Rewards.builder().loots("minecraft:entities/creeper").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"loots\":[\"minecraft:entities/creeper\"]}"));
    }

    @Test
    public void testRewards_GIVEN_Experience_THEN_ExpectJsonToBeWithExperience() {
        underTest = Rewards.builder().experience(100).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"experience\":100}"));
    }

    @Test
    public void testRewards_GIVEN_Function_THEN_ExpectJsonToBeWithFunction() {
        underTest = Rewards.builder().function("test").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"function\":\"test\"}"));
    }

    @Test
    public void testRewards_GIVEN_Multiple_THEN_ExpectJsonHasMultiple() {
        underTest = Rewards.builder().function("test").experience(100).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"experience\":100,\"function\":\"test\"}"));
    }
}