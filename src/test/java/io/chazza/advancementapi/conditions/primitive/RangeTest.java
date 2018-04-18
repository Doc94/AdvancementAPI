package io.chazza.advancementapi.conditions.primitive;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

public class RangeTest {
    private static final Gson gson = new Gson();
    private Range underTest;

    @Test
    public void testRange_GIVEN_NoArgs_THEN_ExpectJsonToBeRange1() {
        underTest = Range.builder().build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("1"));
    }

    @Test
    public void testRange_GIVEN_Exactly3_THEN_ExpectJsonToBe3() {
        underTest = Range.builder().range(3).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("3"));
    }

    @Test
    public void testRange_GIVEN_Min2Max4_THEN_ExpectJsonToBeMin2Max4() {
        underTest = Range.builder().min(2).max(4).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"min\":2,\"max\":4}"));
    }

    @Test
    public void testRange_GIVEN_Min2NoMax_THEN_ExpectJsonToBeMin2NoMax() {
        underTest = Range.builder().min(2).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"min\":2}"));
    }

    @Test
    public void testRange_GIVEN_NoMinMax4_THEN_ExpectJsonToBeNoMinMax4() {
        underTest = Range.builder().max(4).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"max\":4}"));
    }

    @Test
    public void testRange_GIVEN_MinMaxSetRange_THEN_ExpectJsonToBeRange() {
        underTest = Range.builder().min(3).max(2).range(6).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("6"));
    }

    @Test
    public void testRange_GIVEN_MinRangeMax_THEN_ExpectJsonToBeMinMax() {
        underTest = Range.builder().min(3).range(6).max(2).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"min\":3,\"max\":2}"));
    }

    @Test
    public void testRange_GIVEN_MaxRangeMin_THEN_ExpectJsonToBeMinMax() {
        underTest = Range.builder().max(3).range(6).min(2).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"min\":2,\"max\":3}"));
    }
}