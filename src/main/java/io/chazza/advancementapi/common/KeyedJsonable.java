package io.chazza.advancementapi.common;

public interface KeyedJsonable extends Jsonable {
    /**
     * Returns the corresponding key to this {@link Jsonable} object.
     * 
     * @return the key
     */
    String getJsonKey();
}