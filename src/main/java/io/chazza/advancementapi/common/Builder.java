package io.chazza.advancementapi.common;

/**
 * Indicates that a class is a builder class for some other class {@code T}.
 * 
 * @author Kaonashi97
 * @param <T> the target class this builder is for
 */
public interface Builder<T> {
    /**
     * Builds the underlying object.
     * 
     * @return the object
     */
    T build();
}