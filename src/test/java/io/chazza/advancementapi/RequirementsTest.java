package io.chazza.advancementapi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

public class RequirementsTest {
    private static final Gson gson = new Gson();
    private Requirements underTest;

    @Test
    public void testRequirements_GIVEN_Empty_THEN_ExpectJsonToBeEmpty() {
        underTest = Requirements.builder().build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("[]"));
    }

    @Test
    public void testRequirements_GIVEN_One_THEN_ExpectJsonToBeWithOneList() {
        underTest = Requirements.builder().andOneOfThese("t1").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("[[\"t1\"]]"));
    }

    @Test
    public void testRequirements_GIVEN_And_THEN_ExpectJsonToBeWithTwoLists() {
        underTest = Requirements.builder().andOneOfThese("t1").andOneOfThese("t2").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("[[\"t1\"],[\"t2\"]]"));
    }

    @Test
    public void testRequirements_GIVEN_Or_THEN_ExpectJsonToBeWithOneListAndTwoItems() {
        underTest = Requirements.builder().andOneOfThese("t1", "t2").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("[[\"t1\",\"t2\"]]"));
    }

    @Test
    public void testRequirements_GIVEN_AndOr_THEN_ExpectJsonToBeWithTwoListsAndTwoItems() {
        underTest = Requirements.builder().andOneOfThese("t1", "t2").andOneOfThese("t3").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("[[\"t1\",\"t2\"],[\"t3\"]]"));
    }
}