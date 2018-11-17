package io.chazza.advancementapi.conditions.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

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
 * @author Kaonashi97
 * @see "https://github.com/skylinerw/guides/blob/master/java/advancements/data_structures.md#-shared-block-object"
 */
public class Block implements Jsonable {
    private String block;

    private Block(String block) {
        this.block = block;
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
        return new JsonPrimitive(block);
    }

    /**
     * Builder for {@link Block}s. See {@link Block} for more information on
     * blocks in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class BlockBuilder implements Builder<Block> {
        private String block;

        private BlockBuilder(String block) {
            this.block = block;
        }

        @Override
        public Block build() {
            return new Block(block);
        }
    }
}