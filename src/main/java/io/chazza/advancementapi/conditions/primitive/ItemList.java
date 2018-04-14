package io.chazza.advancementapi.conditions.primitive;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;
import io.chazza.advancementapi.conditions.primitive.Item.ItemBuilder;

/**
 * Represents a list of {@link Item}s.
 * 
 * @author Kaonashi97
 */
public class ItemList implements Jsonable {
    private List<ItemBuilder> items = new LinkedList<>();

    private ItemList(List<ItemBuilder> items) {
        this.items = items;
    }

    /**
     * Returns a {@link ItemListBuilder} for building {@link ItemList}s.
     * 
     * @return the builder
     */
    public static ItemListBuilder builder() {
        return new ItemListBuilder();
    }

    @Override
    public JsonElement toJson() {
        JsonArray itemsList = new JsonArray();
        items.forEach(e -> itemsList.add(e.build().toJson()));
        return itemsList;
    }

    /**
     * Builder for {@link ItemList}s. See {@link ItemList} for more information
     * on item lists in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class ItemListBuilder implements Builder<ItemList> {
        private List<ItemBuilder> items = new LinkedList<>();

        private ItemListBuilder() {
            // builder pattern
        }

        /**
         * Adds an {@link Item} to the list.
         * 
         * @param itemBuilder the {@link ItemBuilder}
         * @return this builder
         */
        public ItemListBuilder add(ItemBuilder itemBuilder) {
            items.add(itemBuilder);
            return this;
        }

        /**
         * Clears the list of items.
         * 
         * @return this builder
         */
        public ItemListBuilder clear() {
            items.clear();
            return this;
        }

        @Override
        public ItemList build() {
            return new ItemList(items);
        }
    }
}