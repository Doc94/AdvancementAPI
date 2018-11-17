package io.chazza.advancementapi.conditions.primitive;

import org.bukkit.NamespacedKey;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;
import io.chazza.advancementapi.conditions.Location.LocationBuilder;
import io.chazza.advancementapi.conditions.StatusEffect.StatusEffectBuilder;
import io.chazza.advancementapi.conditions.primitive.Range.RangeBuilder;

/**
 * An entity object contains a handful of data to compare to an incoming entity.
 * All options are available anywhere that this entity object is used.
 * <p>
 * <b>1. type</b>
 * <p>
 * The type string specifies the entity ID to match against. For example, the
 * following checks if the incoming entity is a creeper.
 * 
 * <pre>
 * Entity.builder().type("minecraft:creeper").build();
 * </pre>
 * 
 * <b>2. distance</b>
 * <p>
 * The distance distance object specifies the distance between the
 * advancement-earning player and an entity's origin. The following checks if
 * the player is within 10 blocks of the entity.
 * 
 * <pre>
 * Entity.builder().distance(Range.builder().max(10)).build();
 * </pre>
 * 
 * <b>3. location</b>
 * <p>
 * The location location object checks data concerning the entity's location in
 * the world. The following checks if the entity is in the desert biome.
 * 
 * <pre>
 * Entity.builder().location(Location.builder(Biome.DESERT)).build();
 * </pre>
 * 
 * <b>4. effects</b>
 * <p>
 * The effects status effects object checks data concerning the entity's status
 * effects. The following checks if the entity has Speed 2.
 * 
 * <pre>
 * Entity.builder().effects(StatusEffect.builder("minecraft:speed").amplifier(Range.builder().range(2))).build();
 * </pre>
 * 
 * <b>5. nbt</b>
 * <p>
 * The nbt string compares the raw NBT input to the entity's NBT data. This raw
 * data starts within the root of the entity format and must be surrounded by
 * curly brackets. If the entity being checked is a player, the SelectedItem
 * compound (containing the player's mainhand item) will be appended to the
 * player's NBT before comparing to the provided input. The following checks if
 * the entity has a specific tag within its Tags list.
 * 
 * <pre>
 * Entity.builder().nbt("{Tags:[\"findme\"]}").build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see "https://github.com/skylinerw/guides/blob/master/java/advancements/data_structures.md#-shared-entity-object"
 */
public class Entity implements Jsonable {
    private String type;
    private RangeBuilder distance;
    private LocationBuilder location;
    private StatusEffectBuilder effects;
    private String nbt;

    private Entity(String type, RangeBuilder distance, LocationBuilder location, StatusEffectBuilder effects,
            String nbt) {
        this.type = type;
        this.distance = distance;
        this.location = location;
        this.effects = effects;
        this.nbt = nbt;
    }

    /**
     * Returns a {@link EntityBuilder} for building {@link Entity}s.
     * 
     * @return the builder
     */
    public static EntityBuilder builder() {
        return new EntityBuilder();
    }

    @Override
    public JsonElement toJson() {
        JsonObject entityObj = new JsonObject();
        //@formatter:off
        if (type != null) entityObj.addProperty("type", type);
        if (distance != null) entityObj.add("distance", distance.build().toJson());
        if (location != null) {
            JsonObject locationObj = new JsonObject();
            locationObj.add(location.build().getJsonKey(), location.build().toJson());
            entityObj.add("location", locationObj);
        }
        if (effects != null) {
            JsonObject effectsObj = new JsonObject();
            effectsObj.add(effects.build().getJsonKey(), effects.build().toJson());
            entityObj.add("effects", effectsObj);
        }
        if (nbt != null) entityObj.addProperty("nbt", nbt);
        //@formatter:on
        return entityObj;
    }

    /**
     * Builder for {@link Entity}s. See {@link Entity} for more information on
     * entities in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class EntityBuilder implements Builder<Entity> {
        private String type;
        private RangeBuilder distance;
        private LocationBuilder location;
        private StatusEffectBuilder effects;
        private String nbt;

        private EntityBuilder() {
            // builder pattern
        }

        /**
         * The type string specifies the entity ID to match against.
         * 
         * @param type the type (with {@link NamespacedKey})
         * @return this builder
         */
        public EntityBuilder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * The distance distance object specifies the distance between the
         * advancement-earning player and an entity's origin.
         * 
         * @param distanceRangeBuilder a {@link RangeBuilder}
         * @return this builder
         */
        public EntityBuilder distance(RangeBuilder distanceRangeBuilder) {
            this.distance = distanceRangeBuilder;
            return this;
        }

        /**
         * The location location object checks data concerning the entity's
         * location in the world.
         * 
         * @param locationBuilder a {@link LocationBuilder}
         * @return this builder
         */
        public EntityBuilder location(LocationBuilder locationBuilder) {
            this.location = locationBuilder;
            return this;
        }

        /**
         * The effects status effects object checks data concerning the entity's
         * status effects.
         * 
         * @param statusEffectBuilder the {@link StatusEffectBuilder}
         * @return this builder
         */
        public EntityBuilder effects(StatusEffectBuilder statusEffectBuilder) {
            this.effects = statusEffectBuilder;
            return this;
        }

        /**
         * The nbt string compares the raw NBT input to the entity's NBT data.
         * This raw data starts within the root of the entity format and must be
         * surrounded by curly brackets. If the entity being checked is a
         * player, the SelectedItem compound (containing the player's mainhand
         * item) will be appended to the player's NBT before comparing to the
         * provided input.
         * 
         * @param nbt the raw NBT data
         * @return this builder
         */
        public EntityBuilder nbt(String nbt) {
            this.nbt = nbt;
            return this;
        }

        @Override
        public Entity build() {
            return new Entity(type, distance, location, effects, nbt);
        }
    }
}