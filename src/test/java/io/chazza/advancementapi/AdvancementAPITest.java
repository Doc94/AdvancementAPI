package io.chazza.advancementapi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.bukkit.NamespacedKey;
import org.junit.Test;

import com.google.gson.Gson;

public class AdvancementAPITest {
    @SuppressWarnings("deprecation")
    private static final NamespacedKey nsk = new NamespacedKey("tests", "id");

    private static final Gson gson = new Gson();
    private AdvancementAPI underTest;

    @Test
    public void testAdvancement_GIVEN_Root_THEN_ExpectJsonToBeOnlyRoot() {
        underTest = AdvancementAPI.builder(nsk).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"criteria\":{\"default\":{\"trigger\":\"minecraft:impossible\"}}}"));
    }

    @Test
    public void testAdvancement_GetId() {
        underTest = AdvancementAPI.builder(nsk).build();

        assertThat(underTest.getId(), is(nsk));
    }

    @Test
    public void testAdvancement_GIVEN_WithParent_THEN_ExpectJsonToBeWithParent() {
        underTest = AdvancementAPI.builder(nsk).parent("test/id2").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json,
                is("{\"parent\":\"test/id2\",\"criteria\":{\"default\":{\"trigger\":\"minecraft:impossible\"}}}"));
    }

    @Test
    public void testAdvancement_GetParent() {
        underTest = AdvancementAPI.builder(nsk).parent("test/id2").build();

        assertThat(underTest.getParent(), is("test/id2"));
    }

    @Test
    public void testAdvancement_GIVEN_OneTrigger_THEN_ExpectJsonToBeWithTrigger() {
        underTest = AdvancementAPI.builder(nsk).trigger(Trigger.builder(TriggerType.BRED_ANIMALS, "trigger1")).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"criteria\":{\"trigger1\":{\"trigger\":\"minecraft:bred_animals\"}}}"));
    }

    @Test
    public void testAdvancement_GIVEN_ClearTrigger_THEN_ExpectJsonToBeWithoutTrigger() {
        underTest = AdvancementAPI.builder(nsk).trigger(Trigger.builder(TriggerType.BRED_ANIMALS, "trigger1"))
                .clearTriggers().build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"criteria\":{\"default\":{\"trigger\":\"minecraft:impossible\"}}}"));
    }

    @Test
    public void testAdvancement_GIVEN_Display_THEN_ExpectJsonToBeWithDisplay() {
        underTest = AdvancementAPI.builder(nsk).display(Display.builder("", "", "")).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"display\":{\"title\":\"\",\"description\":\"\",\"icon\":{\"item\":\"\"}},"
                + "\"criteria\":{\"default\":{\"trigger\":\"minecraft:impossible\"}}}"));
    }

    @Test
    public void testAdvancement_GIVEN_Requirements_THEN_ExpectJsonToBeWithRequirements() {
        underTest = AdvancementAPI.builder(nsk).requirements(Requirements.builder().andOneOfThese("t1", "t2")).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"criteria\":{\"default\":{\"trigger\":\"minecraft:impossible\"}},"
                + "\"requirements\":[[\"t1\",\"t2\"]]}"));
    }

    @Test
    public void testAdvancement_GIVEN_Rewards_THEN_ExpectJsonToBeWithRewards() {
        underTest = AdvancementAPI.builder(nsk).rewards(Rewards.builder().experience(100)).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"criteria\":{\"default\":{\"trigger\":\"minecraft:impossible\"}},\"rewards\":"
                + "{\"experience\":100}}"));
    }

    @Test
    public void testAdvancement_GetJson_THEN_ReturnNotEmptyOrNull() {
        underTest = AdvancementAPI.builder(nsk).build();
        assertThat(underTest.getJson(), is(notNullValue()));
        assertThat(underTest.getJson(), is(not("")));
    }
}