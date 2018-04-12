package io.chazza.advancementapi.conditions.primitive;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

import io.chazza.advancementapi.conditions.primitive.Enchantment;
import io.chazza.advancementapi.conditions.primitive.Range;

public class EnchantmentTest {
    private static final Gson gson = new Gson();
    private Enchantment underTest;

    @Test
    public void testEnchantment_GIVEN_Enchantment_THEN_ExpectJsonToBeObjWithEnchantment() throws Exception {
        underTest = Enchantment.builder("minecraft:looting").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"enchantment\":\"minecraft:looting\"}"));
    }

    @Test
    public void testEnchantment_GIVEN_Levels_THEN_ExpectJsonToBeObjWithLevels() throws Exception {
        underTest = Enchantment.builder(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"levels\":1}"));
    }
}