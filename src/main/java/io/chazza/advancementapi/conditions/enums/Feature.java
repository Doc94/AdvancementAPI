package io.chazza.advancementapi.conditions.enums;

public enum Feature {
    //@formatter:off
    //BURIED_TREASURE("Buried_Treasure"),
    //DESERT_PYRAMID("Desert_Pyramid"),
    ENDCITY("EndCity"),
    FORTRESS("Fortress"),
    //IGLOO("Igloo"),
    //JUNGLE_PYRAMID("Jungle_Pyramid"),
    MANSION("Mansion"),
    MINESHAFT("Mineshaft"),
    MONUMENT("Monument"),
    //OCEAN_RUIN("Ocean_Ruin"),
    //SHIPWRECK("Shipwreck"),
    STRONGHOLD("Stronghold"),
    //SWAMP_HUT("Swamp_Hut"),
    TEMPLE("Temple"),
    VILLAGE("Village")
    ;
    //@formatter:on

    private final String name;

    private Feature(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}