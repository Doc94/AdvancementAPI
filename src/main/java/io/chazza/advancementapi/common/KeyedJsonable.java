package io.chazza.advancementapi.common;

import com.google.gson.JsonElement;

/**
 * Represents an object that can be keyed as {@link JsonElement} or one of it's
 * subclasses.
 * 
 * @author Kaonashi97
 */
public interface KeyedJsonable extends Jsonable {
    /**
     * Returns the corresponding key to this {@link Jsonable} object.
     * 
     * @return the key
     */
    String getJsonKey();
}