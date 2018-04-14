package io.chazza.advancementapi.conditions.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;
import io.chazza.advancementapi.conditions.primitive.DamageFlags.DamageFlagsBuilder;
import io.chazza.advancementapi.conditions.primitive.Entity.EntityBuilder;
import io.chazza.advancementapi.conditions.primitive.Range.RangeBuilder;

/**
 * A damage object contains a large amount of information about the incoming or
 * outgoing damage. All options are available anywhere that this damage object
 * is used.
 * <p>
 * <b>1. dealt</b>
 * <p>
 * A range checking the raw incoming damage before damage reduction. For
 * example, the following checks the damage of an un-owned arrow (via /summon)
 * before that damage was either reduced or blocked with a shield entirely.
 * 
 * <pre>
 * Damage.builder().dealt(Range.builder().min(4)).sourceEntity(Entity.builder().type("minecraft:arrow")).build();
 * </pre>
 * 
 * 
 * <b>2. taken</b>
 * <p>
 * A range checking the incoming damage after damage reduction. For example, the
 * following checks if the resulting damage after reductions was at least 5.0.
 * 
 * <pre>
 * Damage.builder().taken(Range.builder().min(5)).build();
 * </pre>
 * 
 * <b>3. blocked</b>
 * <p>
 * Checks if the incoming damage was successfully blocked, provided that the
 * damage_flags_object.bypasses_armor ("unblockable") flag isn't true. The
 * following checks if the player failed to block a skeleton's attack (either by
 * an arrow or melee weapon).
 * 
 * <pre>
 * Damage.builder().blocked(false).sourceEntity(Entity.builder().type("minecraft:sekeleton")).build();
 * </pre>
 * 
 * 
 * <b>4. type</b>
 * <p>
 * A damage flags object that checks various flags about the damage. The
 * following checks if the damage was a projectile caused by a skeleton (though
 * does not necessarily mean the direct cause of the damage was an arrow).
 * 
 * <pre>
 * Damage.builder().type(DamageFlags.builder().isProjectile(true))
 *         .sourceEntity(Entity.builder().type("minecraft:skeleton")).build();
 * </pre>
 * 
 * <b>5. source_entity</b>
 * <p>
 * An enity object that checks information about either the entity hit or the
 * entity dealing the damage. Note that for players being the source entity, the
 * nested type string can essentially only be "minecraft:player". The following
 * checks if a skeleton was at least 10 blocks away when it hit the player.
 * 
 * <pre>
 * Damage.builder().sourceEntity(Entity.builder().type("minecraft:skeleton").distance(Range.builder().min(10))).build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see https://github.com/skylinerw/guides/blob/master/java/advancements/data_structures.md#-shared-damage-object
 */
public class Damage implements Jsonable {
    private RangeBuilder dealt;
    private RangeBuilder taken;
    private Boolean blocked;
    private DamageFlagsBuilder type;
    private EntityBuilder source_entity;

    private Damage(RangeBuilder dealt, RangeBuilder taken, Boolean blocked, DamageFlagsBuilder type,
            EntityBuilder source_entity) {
        this.dealt = dealt;
        this.taken = taken;
        this.blocked = blocked;
        this.type = type;
        this.source_entity = source_entity;
    }

    /**
     * Returns a {@link DamageBuilder} for building {@link Damage}s.
     * 
     * @return the builder
     */
    public static DamageBuilder builder() {
        return new DamageBuilder();
    }

    @Override
    public JsonElement toJson() {
        JsonObject damageObj = new JsonObject();
        //@formatter:off
        if (dealt != null) damageObj.add("dealt", dealt.build().toJson());
        if (taken != null) damageObj.add("taken", taken.build().toJson());
        if (blocked != null) damageObj.addProperty("blocked", blocked);
        if (type != null) damageObj.add("type", type.build().toJson());
        if (source_entity != null) damageObj.add("source_entity", source_entity.build().toJson());
        //@formatter:on
        return damageObj;
    }

    /**
     * Builder for {@link Damage}s. See {@link Damage} for more information on
     * damage in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class DamageBuilder implements Builder<Damage> {
        private RangeBuilder dealt;
        private RangeBuilder taken;
        private Boolean blocked;
        private DamageFlagsBuilder type;
        private EntityBuilder source_entity;

        private DamageBuilder() {
            // builder pattern
        }

        /**
         * A range checking the raw incoming damage before damage reduction. For
         * example, the following checks the damage of an un-owned arrow (via
         * /summon) before that damage was either reduced or blocked with a
         * shield entirely.
         * 
         * @param dealt a {@link RangeBuilder}
         * @return this builder
         */
        public DamageBuilder dealt(RangeBuilder dealt) {
            this.dealt = dealt;
            return this;
        }

        /**
         * A range checking the incoming damage after damage reduction.
         * 
         * @param taken a {@link RangeBuilder}
         * @return this builder
         */
        public DamageBuilder taken(RangeBuilder taken) {
            this.taken = taken;
            return this;
        }

        /**
         * Checks if the incoming damage was successfully blocked, provided that
         * the damage_flags_object.bypasses_armor ("unblockable") flag isn't
         * true.
         * 
         * @param blocked true or false
         * @return this builder
         */
        public DamageBuilder blocked(boolean blocked) {
            this.blocked = new Boolean(blocked);
            return this;
        }

        /**
         * A damage flags object that checks various flags about the damage.
         * 
         * @param type a {@link DamageFlagsBuilder}
         * @return this builder
         */
        public DamageBuilder type(DamageFlagsBuilder type) {
            this.type = type;
            return this;
        }

        /**
         * An enity object that checks information about either the entity hit
         * or the entity dealing the damage. Note that for players being the
         * source entity, the nested type string can essentially only be
         * "minecraft:player".
         * 
         * @param sourceEntity a {@link EntityBuilder}
         * @return this builder
         */
        public DamageBuilder sourceEntity(EntityBuilder sourceEntity) {
            this.source_entity = sourceEntity;
            return this;
        }

        @Override
        public Damage build() {
            return new Damage(dealt, taken, blocked, type, source_entity);
        }
    }
}