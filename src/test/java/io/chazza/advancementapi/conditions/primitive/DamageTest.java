package io.chazza.advancementapi.conditions.primitive;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

public class DamageTest {
    private static final Gson gson = new Gson();
    private Damage underTest;

    @Test
    public void testDamage_GIVEN_Dealt_THEN_ExpectJsonToBeWithDealt() {
        underTest = Damage.builder().dealt(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"dealt\":1}"));
    }

    @Test
    public void testDamage_GIVEN_Taken_THEN_ExpectJsonToBeWithTaken() {
        underTest = Damage.builder().taken(Range.builder()).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"taken\":1}"));
    }

    @Test
    public void testDamage_GVEN_Blocked_THEN_ExpectJsonToBeWithBlocked() {
        underTest = Damage.builder().blocked(true).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"blocked\":true}"));
    }

    @Test
    public void testDamage_GIVEN_Type_THEN_ExpectJsonToBeWithType() {
        underTest = Damage.builder().type(DamageFlags.builder().bypassesArmor(true)).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"type\":{\"bypasses_armor\":true}}"));
    }

    @Test
    public void testDamage_GIVEN_SourceEntity_THEN_ExpectJsonToBeWithSourceEntity() {
        underTest = Damage.builder().sourceEntity(Entity.builder().type("minecraft:creeper")).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"source_entity\":{\"type\":\"minecraft:creeper\"}}"));
    }
}