package io.chazza.advancementapi.conditions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.bukkit.block.Biome;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.conditions.enums.Dimension;
import io.chazza.advancementapi.conditions.enums.Feature;
import io.chazza.advancementapi.conditions.primitive.Range;

public class LocationTest {
    private static final Gson gson = new Gson();
    private Location underTest;

    @Test
    public void testLocation_GIVEN_NoArgs_THEN_ExpectJsonToBeLocationWithDimensionOverworld() {
        underTest = Location.builder().build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"dimension\":\"overworld\"}"));
    }

    @Test
    public void testLocation_GIVEN_XPos_THEN_ExpectJsonToBeLocationWithXPos() {
        underTest = Location.builder().x(Range.builder().range(4)).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"position\":{\"x\":4}}"));
    }

    @Test
    public void testLocation_GIVEN_YPos_THEN_ExpectJsonToBeLocationWithYPos() {
        underTest = Location.builder().y(Range.builder().range(4)).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"position\":{\"y\":4}}"));
    }

    @Test
    public void testLocation_GIVEN_ZPos_THEN_ExpectJsonToBeLocationWithZPos() {
        underTest = Location.builder().z(Range.builder().range(4)).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"position\":{\"z\":4}}"));
    }

    @Test
    public void testLocation_GIVEN_Pos_THEN_ExpectJsonToBeLocationWithPos() {
        underTest = Location.builder().x(Range.builder().min(2).max(4)).z(Range.builder().range(4)).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"position\":{\"x\":{\"min\":2,\"max\":4},\"z\":4}}"));
    }

    @Test
    public void testLocation_GIVEN_AllPos_THEN_ExpectJsonToBeLocationWithAllPos() {
        underTest = Location.builder().x(Range.builder().min(2).max(4)).y(Range.builder().range(3))
                .z(Range.builder().range(4)).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"position\":{\"x\":{\"min\":2,\"max\":4},\"y\":3,\"z\":4}}"));
    }

    @Test
    public void testLocation_GIVEN_Biome_THEN_ExpectJsonToBeLocationWithBiome() {
        underTest = Location.builder().biome(Biome.TAIGA_COLD_HILLS).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"biome\":\"minecraft:taiga_cold_hills\"}"));
    }

    @Test
    public void testLocation_GIVEN_Feature_THEN_ExpectJsonToBeLocationWithFeature() {
        underTest = Location.builder().feature(Feature.ENDCITY).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"feature\":\"EndCity\"}"));
    }

    @Test
    public void testLocation_GIVEN_Dimension_THEN_ExpectJsonToBeLocationWithDimension() {
        underTest = Location.builder().dimension(Dimension.THE_NETHER).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"dimension\":\"the_nether\"}"));
    }

    @Test
    public void testLocation_GIVEN_RangeXDimension_THEN_ExpectJsonToBeLocationWithDimension() {
        underTest = Location.builder().x(Range.builder()).dimension(Dimension.THE_END).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"dimension\":\"the_end\"}"));
    }

    @Test
    public void testLocation_GIVEN_BiomeRangeX_THEN_ExpectJsonToBeLocationWithRangeX() {
        underTest = Location.builder().biome(Biome.BEACHES).x(Range.builder()).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"position\":{\"x\":1}}"));
    }
}