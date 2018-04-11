package io.chazza.advancementapi.conditions;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;

/**
 * A block object contains a handful of data to compare to an incoming block.
 * All options are available anywhere that this entity object is used.
 * <p>
 * <b>1. block</b>
 * <p>
 * The block string specifies a base block ID to detect the player in. For
 * example, the following checks if the block has the base ID of
 * "minecraft:tallgrass" (includes grass, fern, and double tallgrass).
 * 
 * <pre>
 * Block.builder("minecraft:tallgrass").build();
 * </pre>
 * 
 * <b>2. state</b>
 * <p>
 * The state object contains a list of custom keys, much like criteria does. The
 * names for these keys will correspond to the blockstate name you want to
 * detect, and the value corresponds to possible values for that blockstate. For
 * "minecraft:tallgrass", the type blockstate specifies which of the tallgrass
 * blocks it is. The block string must be specified to use this condition. The
 * following checks if the tallgrass is a fern.
 * <p>
 * 
 * <pre>
 * Block.builder("minecraft:tallgrass").state("type", "fern").build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see https://github.com/skylinerw/guides/blob/master/java/advancements/data_structures.md#-shared-block-object
 */
public class Block implements Jsonable {
    private String block;
    private Map<String, String> states = new LinkedHashMap<>();

    private Block(String block, Map<String, String> states) {
        this.block = block;
        this.states = states;
    }

    /**
     * Returns a {@link BlockBuilder} for building {@link Block}s.
     * 
     * @param block the block ID
     * @return the builder
     */
    public static BlockBuilder builder(String block) {
        return new BlockBuilder(block);
    }

    @Override
    public JsonElement toJson() {
        JsonObject blockObj = new JsonObject();
        blockObj.addProperty("block", block);
        if (states != null && !states.isEmpty()) {
            JsonObject statesObj = new JsonObject();
            states.forEach((k, v) -> statesObj.addProperty(k, v));
            blockObj.add("state", statesObj);
        }
        return blockObj;
    }

    /**
     * Builder for {@link Block}s. See {@link Block} for more information on
     * blocks in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class BlockBuilder implements Builder<Block> {
        private String block;
        private Map<String, String> states = new LinkedHashMap<>();

        private BlockBuilder(String block) {
            this.block = block;
        }

        /**
         * The state object contains a list of custom keys, much like criteria
         * does. The names for these keys will correspond to the blockstate name
         * you want to detect, and the value corresponds to possible values for
         * that blockstate.
         * 
         * @param key the key
         * @param value the value
         * @return this builder
         */
        public BlockBuilder state(String key, String value) {
            states.put(key, value);
            return this;
        }

        /**
         * Removes all saved states from this block.
         * 
         * @return this builder
         */
        public BlockBuilder clearStates() {
            states.clear();
            return this;
        }

        @Override
        public Block build() {
            return new Block(block, states);
        }
    }
}