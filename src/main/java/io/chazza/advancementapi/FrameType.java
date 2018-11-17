package io.chazza.advancementapi;

/**
 * The possible frame types.
 * 
 * @author ysl3000
 * @author Kaonashi97
 */
public enum FrameType {
    TASK, GOAL, CHALLENGE;

    /**
     * Returns a random frame type.
     * 
     * @return a random frame type
     */
    public static FrameType RANDOM() {
        FrameType[] frameTypes = FrameType.values();
        return frameTypes[(int) (Math.random() * (frameTypes.length - 1))];
    }

    /**
     * Returns the {@link FrameType} parsing from a string.
     * 
     * @param s a string
     * @return the type if found or {@link FrameType#TASK TASK} on default
     */
    public static FrameType getFromString(String s) {
        if (s.equalsIgnoreCase("random")) {
            return FrameType.RANDOM();
        }
        try {
            return FrameType.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return FrameType.TASK;
        }
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}