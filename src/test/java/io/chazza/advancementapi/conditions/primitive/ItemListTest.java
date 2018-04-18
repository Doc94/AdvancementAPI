package io.chazza.advancementapi.conditions.primitive;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.bukkit.Material;
import org.junit.Test;

import com.google.gson.Gson;

public class ItemListTest {
    private static final Gson gson = new Gson();
    private ItemList underTest;

    @Test
    public void testItemList_GIVEN_OneItem_THEN_ExpectJsonToBeWithOneItem() {
        underTest = ItemList.builder().add(Item.builder(Material.SAND)).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("[{\"item\":\"minecraft:sand\"}]"));
    }

    @Test
    public void testItemList_GIVEN_MoreItems_THEN_ExpectJsonToBeWithMoreItems() {
        underTest = ItemList.builder().add(Item.builder(Material.SAND)).add(Item.builder(Material.WOOD)).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("[{\"item\":\"minecraft:sand\"},{\"item\":\"minecraft:wood\"}]"));
    }

    @Test
    public void testItemList_GIVEN_ItemsClear_THEN_ExpectJsonToBeEmpty() {
        underTest = ItemList.builder().add(Item.builder(Material.SAND)).clear().build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("[]"));
    }
}