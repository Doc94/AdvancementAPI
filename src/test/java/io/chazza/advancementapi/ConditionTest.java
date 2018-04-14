package io.chazza.advancementapi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.conditions.Location;
import io.chazza.advancementapi.conditions.enums.Dimension;
import io.chazza.advancementapi.conditions.primitive.Block;
import io.chazza.advancementapi.conditions.primitive.Entity;
import io.chazza.advancementapi.conditions.primitive.Item;
import io.chazza.advancementapi.conditions.primitive.ItemList;
import io.chazza.advancementapi.conditions.primitive.Range;

public class ConditionTest {
    private static final Gson gson = new Gson();
    private Condition underTest;

    @Test
    public void testCondition_GIVEN_SimpleRange_THEN_ExpectJsonWithSimpleRange() {
        underTest = Condition.builder("durability", Range.builder()).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"durability\":1}"));
    }

    @Test
    public void testCondition_GIVEN_ComplexRange_THEN_ExpectJsonWithComplexRange() {
        underTest = Condition.builder("delta", Range.builder().min(1).max(1)).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"delta\":{\"min\":1,\"max\":1}}"));
    }

    @Test
    public void testCondition_GIVEN_ComplexItemsList_THEN_ExpectJsonWithListOfItems() {
        underTest = Condition
                .builder("items", ItemList.builder().add(Item.builder(Material.STONE).count(Range.builder()))).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"items\":[{\"item\":\"minecraft:stone\",\"count\":1}]}"));
    }

    @Test
    public void testCondition_GIVEN_ComplexItem_THEN_ExpectJsonWithItem() {
        underTest = Condition.builder("item", Item.builder(Material.STONE).durability(Range.builder().min(1).max(1)))
                .build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"item\":{\"item\":\"minecraft:stone\",\"durability\":{\"min\":1,\"max\":1}}}"));
    }

    @Test
    public void testCondition_GIVEN_Entity_THEN_ExpectJsonWithEntity() {
        underTest = Condition.builder("child", Entity.builder().type("minecraft:creeper").distance(Range.builder()))
                .build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"child\":{\"type\":\"minecraft:creeper\",\"distance\":1}}"));
    }

    @Test
    public void testCondition_GIVEN_BiomeLocation_THEN_ExpectJsonWithBiome() {
        underTest = Condition.builder(Location.builder().biome(Biome.VOID)).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"biome\":\"minecraft:void\"}"));
    }

    @Test
    public void testCondition_GIVEN_Block_THEN_ExpectJsonWithBlock() {
        underTest = Condition.builder("block", Block.builder("minecraft:stone")).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"block\":\"minecraft:stone\"}"));
    }

    @Test
    public void testCondition_GIVEN_From_THEN_ExpectJsonWithFrom() {
        underTest = Condition.builder("from", Dimension.OVERWORLD.toString()).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"from\":\"overworld\"}"));
    }

    @Test
    public void testCondition_GIVEN_NetherTravel_THEN_ExpectJsonWithLocation() {
        underTest = Condition.builder("entered", Location.builder().biome(Biome.DESERT)).build();

        JsonObject testHolder = new JsonObject();
        testHolder.add(underTest.getJsonKey(), underTest.toJson());
        String json = gson.toJson(testHolder);

        assertThat(json, is("{\"entered\":{\"biome\":\"minecraft:desert\"}}"));
    }
}