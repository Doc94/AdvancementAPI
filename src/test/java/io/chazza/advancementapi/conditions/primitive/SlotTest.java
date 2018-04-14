package io.chazza.advancementapi.conditions.primitive;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

public class SlotTest {
    private static final Gson gson = new Gson();
    private Slot underTest;

    @Test
    public void testSlot_GIVEN_Empty_THEN_ExpectJsonToBeWithEmpty() {
        underTest = Slot.builder().empty(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"empty\":1}"));
    }

    @Test
    public void testSlot_GIVEN_Full_THEN_ExpectJsonToBeWithFull() throws Exception {
        underTest = Slot.builder().full(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"full\":1}"));
    }

    @Test
    public void testSlot_GIVEN_Occupied_THEN_ExpectJsonToBeWithOccupied() throws Exception {
        underTest = Slot.builder().occupied(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"occupied\":1}"));
    }

    @Test
    public void testSlot_GIVEN_Multiple_THEN_ExpectJsonToBeWithMultiple() throws Exception {
        underTest = Slot.builder().full(Range.builder()).occupied(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"occupied\":1,\"full\":1}"));
    }
}