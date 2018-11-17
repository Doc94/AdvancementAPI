package io.chazza.advancementapi.conditions.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;
import io.chazza.advancementapi.conditions.primitive.Entity.EntityBuilder;

/**
 * A damage flags object checks different flags concerning the incoming or
 * outgoing damage. All options are available anywhere that this damage flags
 * object is used.
 * <p>
 * <b>1. bypasses_armor</b>
 * </p>
 * <p>
 * Checks the "unblockable" flag for the incoming damage. This is true for:
 * fire, suffocation (blocks & world border), entity cramming, drowning,
 * starving, falling, flying into a wall, void (the Void & /kill), health
 * recalcuation, magic damage, and wither effect damage. The following checks if
 * the damage is caused by any damage that cannot be blocked.
 * 
 * <pre>
 * DamageFlags.builder().bypassesArmor(true).build();
 * </pre>
 * 
 * <b>2. bypasses_invulnerability</b>
 * <p>
 * Checks if the damage source can inflict damage on creative mode players. This
 * is true for: void damage. The following checks if the incoming damage is not
 * caused by the Void or /kill.
 * 
 * <pre>
 * DamageFlags.builder().bypassesInvulnerability(false).build();
 * </pre>
 * 
 * <b>3. bypasses_magic</b>
 * <p>
 * Checks the "damageIsAbsolute" flag for the incoming damage. This is true for:
 * starvation. The following checks if the player is taking starvation damage.
 * 
 * <pre>
 * DamageFlags.builder().bypassesMagic(true).build();
 * </pre>
 * 
 * 
 * <b>4. is_explosion</b>
 * <p>
 * Checks the "explosion" flag for the incoming damage. This is true for:
 * creepers, ender crystals, TNT, Minecart TNT, ghast fireballs, beds, the
 * wither, and wither skulls. The following checks if the "explosion" flag is
 * true.
 * 
 * <pre>
 * DamageFlags.builder().explosion(true).build();
 * </pre>
 * 
 * 
 * <b>5. is_fire</b>
 * <p>
 * Checks the "fire" flag for the incoming damage. This is true for: standing in
 * a fire block, being on fire, standing on magma, standing in lava, ghast
 * fireballs, and blaze fireballs. The following checks if the player is not
 * taking damage from fire sources.
 * 
 * <pre>
 * DamageFlags.builder().isFire(false).build();
 * </pre>
 * 
 * 
 * <b>6. is_magic</b>
 * <p>
 * Checks the "magicDamage" flag for the incoming damage. This is true for:
 * thorns, Instant Damage effect, Poison effect, part of Guardian laser damage,
 * evocation fangs, and un-owned wither skulls (via /summon). The following
 * checks if the incoming damage is flagged as magic damage.
 * 
 * <pre>
 * DamageFlags.builder().isMagic(true).build();
 * </pre>
 * 
 * 
 * <b>7. is_projectile</b>
 * <p>
 * Checks the "projectile" flag for the incoming damage. This is true for:
 * arrows, ghast fireballs, blaze fireballs, enderpearls, eggs, snowballs,
 * shulker bullets, and llama spit. The following checks if the incoming damage
 * is specifically not a projectile.
 * 
 * <pre>
 * DamageFlags.builder().isProjectile(false).build();
 * </pre>
 * 
 * 
 * <b>8. source_entity</b>
 * <p>
 * An enity object that specifies the "owner" of the damage. For example, if the
 * player was hit by an arrow shot by a skeleton, the skeleton would be the
 * "source entity". The damage object already makes use of this check, so it is
 * pointless to specify a source entity twice. This particular check is still
 * useful for triggers that only use a damage flags object rather than a damage
 * object.
 * 
 * <pre>
 * DamageFlags.builder().sourceEntity(Entity.builder().type("minecraft:skeleton")).build();
 * </pre>
 * 
 * <b>9. direct_entity</b>
 * <p>
 * An enity object that specifies the direct cause of the damage. For example,
 * if the player was hit by an arrow shot by a skeleton, the arrow would be the
 * "direct entity". The following ensures the player was hit by an arrow shot by
 * a skeleton.
 * 
 * <pre>
 * DamageFlags.builder().directEntity(Entity.builder().type("minecraft:arrow"))
 *         .sourceEntity(Entity.builder().type("minecraft:skeleton")).build();
 * </pre>
 * 
 * @author Kaonashi97
 * @see "https://github.com/skylinerw/guides/blob/master/java/advancements/data_structures.md#-shared-damage-flags-object"
 */
public class DamageFlags implements Jsonable {
    private Boolean bypasses_armor;
    private Boolean bypasses_invulnerability;
    private Boolean bypasses_magic;
    private Boolean is_explosion;
    private Boolean is_fire;
    private Boolean is_magic;
    private Boolean is_projectile;
    private EntityBuilder source_entity;
    private EntityBuilder direct_entity;

    private DamageFlags(Boolean bypasses_armor, Boolean bypasses_invulnerability, Boolean bypasses_magic,
            Boolean is_explosion, Boolean is_fire, Boolean is_magic, Boolean is_projectile, EntityBuilder source_entity,
            EntityBuilder direct_entity) {
        this.bypasses_armor = bypasses_armor;
        this.bypasses_invulnerability = bypasses_invulnerability;
        this.bypasses_magic = bypasses_magic;
        this.is_explosion = is_explosion;
        this.is_fire = is_fire;
        this.is_magic = is_magic;
        this.is_projectile = is_projectile;
        this.source_entity = source_entity;
        this.direct_entity = direct_entity;
    }

    /**
     * Returns a {@link DamageFlagsBuilder} for building {@link DamageFlags}s.
     * 
     * @return the builder
     */
    public static DamageFlagsBuilder builder() {
        return new DamageFlagsBuilder();
    }

    @Override
    public JsonElement toJson() {
        JsonObject damageFlagsObj = new JsonObject();
        //@formatter:off
        if (bypasses_armor != null) damageFlagsObj.addProperty("bypasses_armor", bypasses_armor);
        if (bypasses_invulnerability != null) damageFlagsObj.addProperty("bypasses_invulnerability", bypasses_invulnerability);
        if (bypasses_magic != null) damageFlagsObj.addProperty("bypasses_magic", bypasses_magic);
        if (is_explosion != null) damageFlagsObj.addProperty("is_explosion", is_explosion);
        if (is_fire != null) damageFlagsObj.addProperty("is_fire", is_fire);
        if (is_magic != null) damageFlagsObj.addProperty("is_magic", is_magic);
        if (is_projectile != null) damageFlagsObj.addProperty("is_projectile", is_projectile);
        if (source_entity != null) damageFlagsObj.add("source_entity", source_entity.build().toJson());
        if (direct_entity != null) damageFlagsObj.add("direct_entity", direct_entity.build().toJson());
        //@formatter:on
        return damageFlagsObj;
    }

    /**
     * Builder for {@link DamageFlags}s. See {@link DamageFlags} for more
     * information on damage flags in Minecraft.
     * 
     * @author Kaonashi97
     */
    public static class DamageFlagsBuilder implements Builder<DamageFlags> {
        private Boolean bypasses_armor;
        private Boolean bypasses_invulnerability;
        private Boolean bypasses_magic;
        private Boolean is_explosion;
        private Boolean is_fire;
        private Boolean is_magic;
        private Boolean is_projectile;
        private EntityBuilder source_entity;
        private EntityBuilder direct_entity;

        private DamageFlagsBuilder() {
            // builder pattern
        }

        /**
         * Checks the "unblockable" flag for the incoming damage. This is true
         * for: fire, suffocation (blocks & world border), entity cramming,
         * drowning, starving, falling, flying into a wall, void (the Void &
         * /kill), health recalcuation, magic damage, and wither effect damage.
         * 
         * @param bypasses true or false
         * @return this builder
         */
        public DamageFlagsBuilder bypassesArmor(boolean bypasses) {
            this.bypasses_armor = new Boolean(bypasses);
            return this;
        }

        /**
         * Checks if the damage source can inflict damage on creative mode
         * players. This is true for: void damage.
         * 
         * @param bypasses true or false
         * @return this builder
         */
        public DamageFlagsBuilder bypassesInvulnerability(boolean bypasses) {
            this.bypasses_invulnerability = new Boolean(bypasses);
            return this;
        }

        /**
         * Checks the "damageIsAbsolute" flag for the incoming damage. This is
         * true for: starvation.
         * 
         * @param bypasses true or false
         * @return this builder
         */
        public DamageFlagsBuilder bypassesMagic(boolean bypasses) {
            this.bypasses_magic = new Boolean(bypasses);
            return this;
        }

        /**
         * Checks the "explosion" flag for the incoming damage. This is true
         * for: creepers, ender crystals, TNT, Minecart TNT, ghast fireballs,
         * beds, the wither, and wither skulls.
         * 
         * @param isExplosion true of false
         * @return this builder
         */
        public DamageFlagsBuilder isExplosion(boolean isExplosion) {
            this.is_explosion = new Boolean(isExplosion);
            return this;
        }

        /**
         * Checks the "fire" flag for the incoming damage. This is true for:
         * standing in a fire block, being on fire, standing on magma, standing
         * in lava, ghast fireballs, and blaze fireballs.
         * 
         * @param isFire true or false
         * @return this builder
         */
        public DamageFlagsBuilder isFire(boolean isFire) {
            this.is_fire = new Boolean(isFire);
            return this;
        }

        /**
         * Checks the "magicDamage" flag for the incoming damage. This is true
         * for: thorns, Instant Damage effect, Poison effect, part of Guardian
         * laser damage, evocation fangs, and un-owned wither skulls (via
         * /summon).
         * 
         * @param isMagic true or false
         * @return this builder
         */
        public DamageFlagsBuilder isMagic(boolean isMagic) {
            this.is_magic = new Boolean(isMagic);
            return this;
        }

        /**
         * Checks the "projectile" flag for the incoming damage. This is true
         * for: arrows, ghast fireballs, blaze fireballs, enderpearls, eggs,
         * snowballs, shulker bullets, and llama spit.
         * 
         * @param isProjectile true or false
         * @return this builder
         */
        public DamageFlagsBuilder isProjectile(boolean isProjectile) {
            this.is_projectile = new Boolean(isProjectile);
            return this;
        }

        /**
         * An enity object that specifies the "owner" of the damage. For
         * example, if the player was hit by an arrow shot by a skeleton, the
         * skeleton would be the "source entity". The damage object already
         * makes use of this check, so it is pointless to specify a source
         * entity twice. This particular check is still useful for triggers that
         * only use a damage flags object rather than a damage object.
         * 
         * @param sourceEntity the {@link EntityBuilder}
         * @return the builder
         */
        public DamageFlagsBuilder sourceEntity(EntityBuilder sourceEntity) {
            this.source_entity = sourceEntity;
            return this;
        }

        /**
         * An enity object that specifies the direct cause of the damage. For
         * example, if the player was hit by an arrow shot by a skeleton, the
         * arrow would be the "direct entity".
         * 
         * @param directEntity the {@link EntityBuilder}
         * @return the builder
         */
        public DamageFlagsBuilder directEntity(EntityBuilder directEntity) {
            this.direct_entity = directEntity;
            return this;
        }

        @Override
        public DamageFlags build() {
            return new DamageFlags(bypasses_armor, bypasses_invulnerability, bypasses_magic, is_explosion, is_fire,
                    is_magic, is_projectile, source_entity, direct_entity);
        }
    }
}