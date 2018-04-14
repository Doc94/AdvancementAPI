package io.chazza.advancementapi.conditions.primitive;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

public class DistanceTest {
    private static final Gson gson = new Gson();
    private Distance underTest;

    @Test
    public void testDistance_GIVEN_NoArgs_THEN_ExpectJsonToBeWithAbsolutDefaultRange() {
        underTest = Distance.builder().build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"absolut\":1}"));
    }

    @Test
    public void testDistance_GIVEN_XRange_THEN_ExpectJsonToBeWithXRange() {
        underTest = Distance.builder().x(Range.builder().max(4)).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"x\":{\"max\":4}}"));
    }

    @Test
    public void testDistance_GIVEN_AllRanges_THEN_ExpectJsonToBeWithAllRanges() {
        underTest = Distance.builder().x(Range.builder().max(4)).y(Range.builder()).z(Range.builder().min(1).max(2))
                .build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"x\":{\"max\":4},\"y\":1,\"z\":{\"min\":1,\"max\":2}}"));
    }

    @Test
    public void testDistance_GIVEN_Absolut_THEN_ExpectJsonToBeWithAbsolut() {
        underTest = Distance.builder().absolut(Range.builder().max(4)).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"absolut\":{\"max\":4}}"));
    }

    @Test
    public void testDistance_GIVEN_Horizontal_THEN_ExpectJsonToBeWithHorizontal() {
        underTest = Distance.builder().horizontal(Range.builder().max(10)).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"horizontal\":{\"max\":10}}"));
    }

    @Test
    public void testDistance_GIVEN_RangeXHorizontal_THEN_ExpectJsonToBeWithHorizontal() {
        underTest = Distance.builder().x(Range.builder()).horizontal(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"horizontal\":1}"));
    }

    @Test
    public void testDistance_GIVEN_RangeXAbsolut_THEN_ExpectJsonToBeWithAbsolut() {
        underTest = Distance.builder().x(Range.builder()).absolut(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"absolut\":1}"));
    }

    @Test
    public void testDistance_GIVEN_AbsolutRangeY_THEN_ExpectJsonToBeWithRangeY() {
        underTest = Distance.builder().absolut(Range.builder()).y(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"y\":1}"));
    }
}