package io.chazza.advancementapi.conditions.primitive;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

public class DamageFlagsTest {
    private static final Gson gson = new Gson();
    private DamageFlags underTest;

    @Test
    public void testDamageFlags_GIVEN_BypassesArmor_THEN_ExpectJsonToBeWithBypassesArmor() {
        underTest = DamageFlags.builder().bypassesArmor(true).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"bypasses_armor\":true}"));
    }

    @Test
    public void testDamageFlags_GIVEN_BypassesInvulnerability_THEN_ExpectJsonToBeWithBypassesInvulnerability() {
        underTest = DamageFlags.builder().bypassesInvulnerability(true).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"bypasses_invulnerability\":true}"));
    }

    @Test
    public void testDamageFlags_GIVEN_BypassesMagic_THEN_ExpectJsonToBeWithMagic() {
        underTest = DamageFlags.builder().bypassesMagic(true).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"bypasses_magic\":true}"));
    }

    @Test
    public void testDamageFlags_GIVEN_IsExplosion_THEN_ExpectJsonToBeWithExplosion() {
        underTest = DamageFlags.builder().isExplosion(true).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"is_explosion\":true}"));
    }

    @Test
    public void testDamageFlags_GIVEN_IsFire_THEN_ExpectJsonToBeWithFire() {
        underTest = DamageFlags.builder().isFire(true).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"is_fire\":true}"));
    }

    @Test
    public void testDamageFlags_GIVEN_IsMagic_THEN_ExpectJsonToBeWithMagic() {
        underTest = DamageFlags.builder().isMagic(true).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"is_magic\":true}"));
    }

    @Test
    public void testDamageFlags_GIVEN_IsProjectile_THEN_ExpectJsonToBeWithProjectile() {
        underTest = DamageFlags.builder().isProjectile(true).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"is_projectile\":true}"));
    }

    @Test
    public void testDamageFlags_GIVEN_SourceEntity_THEN_ExpectJsonToBeWithSourceEntity() {
        underTest = DamageFlags.builder().sourceEntity(Entity.builder().type("minecraft:creeper")).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"source_entity\":{\"type\":\"minecraft:creeper\"}}"));
    }

    @Test
    public void testDamageFlags_GIVEN_DirectEntity_THEN_ExpectJsonToBeWithDirectEntity() {
        underTest = DamageFlags.builder().directEntity(Entity.builder().type("minecraft:creeper")).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"direct_entity\":{\"type\":\"minecraft:creeper\"}}"));
    }
}