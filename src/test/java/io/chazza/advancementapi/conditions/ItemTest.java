package io.chazza.advancementapi.conditions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.bukkit.Material;
import org.junit.Test;

import com.google.gson.Gson;

import io.chazza.advancementapi.conditions.enums.Potion;

public class ItemTest {
    private static final Gson gson = new Gson();
    private Item underTest;

    @Test
    public void testItem_GIVEN_Item_THEN_ExpectJsonToBeItem() {
        underTest = Item.builder("minecraft:redstone").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"item\":\"minecraft:redstone\"}"));
    }

    @Test
    public void testItem_GIVEN_WithData_THEN_ExpectJsonToBeWithData() {
        underTest = Item.builder("minecraft:stone").data(1).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"item\":\"minecraft:stone\",\"data\":1}"));
    }
    
    @Test
    public void testItem_GIVEN_LargeData_THEN_ExpectJsonToBeZero() throws Exception {
        underTest = Item.builder("minecraft:stone").data(400_000).build();
        
        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"item\":\"minecraft:stone\",\"data\":0}"));
    }

    @Test
    public void testItem_GIVEN_Material_THEN_ExpectJsonToBeItem() {
        underTest = Item.builder(Material.BED).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"item\":\"minecraft:bed\"}"));
    }

    @Test
    public void testItem_GIVEN_WithDurability_THEN_ExpectJsonToBeWithDurability() {
        underTest = Item.builder("minecraft:stone").durability(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"item\":\"minecraft:stone\",\"durability\":1}"));
    }

    @Test
    public void testItem_GIVEN_WithCount_THEN_ExpectJsonToBeWithCount() {
        underTest = Item.builder("minecraft:stone").count(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"item\":\"minecraft:stone\",\"count\":1}"));
    }

    @Test
    public void testItem_GIVEN_WithPoition_THEN_ExpectJsonToBeWithPoition() {
        underTest = Item.builder("minecraft:stone").potion(Potion.INVISIBILITY).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"item\":\"minecraft:stone\",\"potion\":\"minecraft:invisibility\"}"));
    }

    @Test
    public void testItem_GIVEN_EmptyEnchantment_THEN_ExpectJsonToBeWithEmptyEnchantmentList() {
        underTest = Item.builder("minecraft:stone").emptyEnchantmentList().build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"item\":\"minecraft:stone\",\"enchantments\":[{}]}"));
    }

    @Test
    public void testItem_GIVEN_Enchantment_THEN_ExpectJsonToBeWithEnchantment() {
        underTest = Item.builder("minecraft:stone").enchantment(Enchantment.builder("minecraft:looting")).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json,
                is("{\"item\":\"minecraft:stone\",\"enchantments\":[{\"enchantment\":\"minecraft:looting\"}]}"));
    }

    @Test
    public void testItem_GIVEN_EnchantmentLevel_THEN_ExpectJsonToBeWithEnchantmentLevel() {
        underTest = Item.builder("minecraft:stone").enchantment(Enchantment.builder(Range.builder())).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"item\":\"minecraft:stone\",\"enchantments\":[{\"levels\":1}]}"));
    }

    @Test
    public void testItem_GIVEN_RawNBT_THEN_ExpectJsonToBeWithRawNBT() {
        underTest = Item.builder("minecraft:stone").nbt("{display:{Name:\"Test\"}}").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"item\":\"minecraft:stone\",\"nbt\":\"{display:{Name:\\\"Test\\\"}}\"}"));
    }
}