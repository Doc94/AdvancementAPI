package io.chazza.advancementapi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.bukkit.Material;
import org.junit.Test;

import com.google.gson.Gson;

import io.chazza.advancementapi.conditions.primitive.Damage;
import io.chazza.advancementapi.conditions.primitive.DamageFlags;
import io.chazza.advancementapi.conditions.primitive.Enchantment;
import io.chazza.advancementapi.conditions.primitive.Entity;
import io.chazza.advancementapi.conditions.primitive.Item;
import io.chazza.advancementapi.conditions.primitive.Range;

public class TriggerTest {
    private static final Gson gson = new Gson();
    private Trigger underTest;

    @Test
    public void testTrigger_NoCondition_THEN_ExpectJsonToBeWithoutCondition() {
        underTest = Trigger.builder(TriggerType.IMPOSSIBLE, "test_impossible").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"trigger\":\"minecraft:impossible\"}"));
    }

    @Test
    public void testTrigger_SimpleCondition_THEN_ExpectJsonToBeWithSimpleCondition() {
        underTest = Trigger.builder(TriggerType.BRED_ANIMALS, "test_simple")
                .condition(Condition.builder("c1", Range.builder())).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"trigger\":\"minecraft:bred_animals\",\"conditions\":{\"c1\":1}}"));
    }

    @Test
    public void testTrigger_ItemCondition_THEN_ExpectJsonToBeWithItemCondition() {
        underTest = Trigger.builder(TriggerType.ENCHANTED_ITEM, "test_complex")
                .condition(Condition.builder("item",
                        Item.builder(Material.DIAMOND_PICKAXE).enchantment(Enchantment.builder("minecraft:fortune"))))
                .build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"trigger\":\"minecraft:enchanted_item\",\"conditions\":{\"item\":"
                + "{\"item\":\"minecraft:diamond_pickaxe\",\"enchantments\":[{\"enchantment\":\"minecraft:fortune\"}]}}}"));
    }

    @Test
    public void testTrigger_EntityCondition_THEN_ExpectJsonToBeWithEntityCondition() {
        underTest = Trigger.builder(TriggerType.ENTITY_HURT_PLAYER, "test_complex")
                .condition(Condition.builder("damage",
                        Damage.builder().taken(Range.builder().min(10)).type(DamageFlags.builder().isExplosion(true))
                                .sourceEntity(Entity.builder().type("minecraft:creeper"))))
                .build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json,
                is("{\"trigger\":\"minecraft:entity_hurt_player\",\"conditions\":{\"damage\":"
                        + "{\"taken\":{\"min\":10},\"type\":{\"is_explosion\":true},\"source_entity\":"
                        + "{\"type\":\"minecraft:creeper\"}}}}"));
    }

    @Test
    public void testTrigger_MoreCondition_THEN_ExpectJsonToBeWithBothCondition() {
        underTest = Trigger.builder(TriggerType.ENTITY_HURT_PLAYER, "test_complex")
                .condition(Condition.builder("damage",
                        Damage.builder().taken(Range.builder().min(10)).type(DamageFlags.builder().isExplosion(true))
                                .sourceEntity(Entity.builder().type("minecraft:creeper"))))
                .condition(Condition.builder("item",
                        Item.builder(Material.DIAMOND_PICKAXE).enchantment(Enchantment.builder("minecraft:fortune"))))
                .build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json,
                is("{\"trigger\":\"minecraft:entity_hurt_player\",\"conditions\":{\"damage\":"
                        + "{\"taken\":{\"min\":10},\"type\":{\"is_explosion\":true},\"source_entity\":"
                        + "{\"type\":\"minecraft:creeper\"}},\"item\":{\"item\":\"minecraft:diamond_pickaxe\","
                        + "\"enchantments\":[{\"enchantment\":\"minecraft:fortune\"}]}}}"));
    }

    @Test
    public void testTrigger_ClearCondition_THEN_ExpectJsonToBeWithoutCondition() {
        underTest = Trigger.builder(TriggerType.ENTITY_HURT_PLAYER, "test_complex")
                .condition(Condition.builder("damage",
                        Damage.builder().taken(Range.builder().min(10)).type(DamageFlags.builder().isExplosion(true))
                                .sourceEntity(Entity.builder().type("minecraft:creeper"))))
                .clearConditions().build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"trigger\":\"minecraft:entity_hurt_player\"}"));
    }
}