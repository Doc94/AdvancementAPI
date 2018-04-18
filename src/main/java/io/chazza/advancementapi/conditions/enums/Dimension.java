package io.chazza.advancementapi.conditions.enums;

public enum Dimension {
    OVERWORLD, THE_NETHER, THE_END;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}