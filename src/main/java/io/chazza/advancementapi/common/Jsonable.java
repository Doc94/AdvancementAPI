package io.chazza.advancementapi.common;

import com.google.gson.JsonElement;

/**
 * Defines that this class can be represented as a {@link JsonElement} or one of
 * it's subclasses.
 * 
 * @author Kaonashi97
 */
public interface Jsonable {
    /**
     * Returns a Json representive object of the implementing instance.
     * 
     * @return a {@link JsonElement} or one of it's subclasses
     */
    JsonElement toJson();
}