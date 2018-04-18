package io.chazza.advancementapi.conditions.enums;

public enum Potion {
    //@formatter:off
    WATER,
    THICK,
    MUNDANE,
    AWKWARD,
    
    FIRE_RESISTANCE,
    LONG_FIRE_RESISTANCE,
    LUCK,
    HEALING,
    STRONG_HEALING,
    NIGHT_VISION,
    LONG_NIGHT_VISION,
    REGENERATION,
    LONG_REGENERATION,
    STRONG_REGENERATION,
    SWIFTNESS,
    LONG_SWIFTNESS,
    STRONG_SWIFTNESS,
    LEAPING,
    LONG_LEAPING,
    STRONG_LEAPING,
    STRENGTH,
    LONG_STRENGTH,
    STRONG_STRENGTH,
    INVISIBILITY,
    LONG_INVISIBILITY,
    WATER_BREATHING,
    LONG_WATER_BREATHING,
    
    SLOWNESS,
    LONG_SLOWNESS,
    STRONG_SLOWNESS,
    HARMING,
    STRONG_HARMING,
    WEAKNESS,
    LONG_WEAKNESS,
    POISON,
    LONG_POISON,
    STRONG_POISON,
    
    TURTLE_MASTER,
    LONG_TURTLE_MASTER,
    STRONG_TURTLE_MASTER
    ;
    //@formatter:on

    @Override
    public String toString() {
        return "minecraft:" + super.toString().toLowerCase();
    }
}