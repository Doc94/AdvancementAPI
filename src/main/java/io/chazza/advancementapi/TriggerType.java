package io.chazza.advancementapi;

import io.chazza.advancementapi.conditions.Location;
import io.chazza.advancementapi.conditions.StatusEffect;
import io.chazza.advancementapi.conditions.primitive.Block;
import io.chazza.advancementapi.conditions.primitive.Damage;
import io.chazza.advancementapi.conditions.primitive.DamageFlags;
import io.chazza.advancementapi.conditions.primitive.Distance;
import io.chazza.advancementapi.conditions.primitive.Entity;
import io.chazza.advancementapi.conditions.primitive.Item;
import io.chazza.advancementapi.conditions.primitive.Range;
import io.chazza.advancementapi.conditions.primitive.Slot;
import io.chazza.advancementapi.conditions.primitive.State;

public enum TriggerType {
    /**
     * Available conditions: <code>parent ({@link Entity})</code>,
     * <code>partner ({@link Entity})</code> and
     * <code>child ({@link Entity})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-bred_animals
     */
    BRED_ANIMALS,

    /**
     * Available conditions: <code>potion (String)</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-brewed_potion
     */
    BREWED_POTION,

    /**
     * Available conditions: <code>to (String)</code>,
     * <code>from (String)</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-changed_dimension
     */
    CHANGED_DIMENSION,

    /**
     * Available conditions: <code>level ({@link Range})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-construct_beacon
     */
    CONSTRUCT_BEACON,

    /**
     * Available conditions: <code>item ({@link Item})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-consume_item
     */
    CONSUME_ITEM,

    /**
     * Available conditions: <code>zombie ({@link Entity})</code> and
     * <code>villager ({@link Entity}</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-cured_zombie_villager
     */
    CURED_ZOMBIE_VILLAGER,

    /**
     * Available conditions: <code>effects ({@link StatusEffect})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-effects_changed
     */
    EFFECTS_CHANGED,

    /**
     * Available conditions: <code>item ({@link Item})</code> and
     * <code>levels ({@link Range})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-enchanted_item
     */
    ENCHANTED_ITEM,

    /**
     * Available conditions: <code>block ({@link Block})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-enter_block
     */
    ENTER_BLOCK,

    /**
     * Available conditions: <code>damage ({@link Damage})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-entity_hurt_player
     */
    ENTITY_HURT_PLAYER,

    /**
     * Available conditions: <code>entity ({@link Entity})</code> and
     * <code>killing_blow ({@link DamageFlags})</code>.
     * <p>
     * The stated shared death object do not exists in this API due to the fact
     * that the shared death object consists of an {@link Entity} condition and
     * an {@link DamageFlags} condition. They can be added separately to a
     * {@link Trigger}.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-entity_killed_player
     */
    ENTITY_KILLED_PLAYER,

    /**
     * Available conditions: none.
     * <p>
     * This does not trigger naturally. The only way to trigger this is in
     * vanilla to use the <code>/advancement</code> command.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-impossible
     */
    IMPOSSIBLE,

    /**
     * Available conditions: <code>slots ({@link Slot})</code> and
     * <code>items ({@link Item})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-inventory_changed
     */
    INVENTORY_CHANGED,

    /**
     * Available conditions: <code>item ({@link Item})</code>,
     * <code>durability ({@link Range})</code> and
     * <code>delta ({@link Range})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-item_durability_changed
     */
    ITEM_DURABILITY_CHANGED,

    /**
     * Available conditions: <code>duration ({@link Range})</code> and
     * <code>distance ({@link Range})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-levitation
     */
    LEVITATION,

    /**
     * Available conditions: a {@link Location} object
     * <p>
     * This trigger dosn't need a condition key.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-location
     */
    LOCATION,

    /**
     * Available conditions: <code>entered ({@link Location})</code>,
     * <code>exited ({@link Location})</code> and
     * <code>distance ({@link Distance})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-nether_travel
     */
    NETHER_TRAVEL,

    /**
     * Available conditions: <code>block ({@link Block})</code>,
     * <code>state ({@link State})</code>, <code>item ({@link Item})</code> and
     * <code>location ({@link Location})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-placed_block
     */
    PLACED_BLOCK,

    /**
     * Available conditions: <code>damage ({@link Damage})</code> and
     * <code>entity ({@link Entity})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-player_hurt_entity
     */
    PLAYER_HURT_ENTITY,

    /**
     * Available conditions: <code>entity ({@link Entity})</code> and
     * <code>killing_blow ({@link DamageFlags})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-player_killed_entity
     */
    PLAYER_KILLED_ENTITY,

    /**
     * Needed conditions: <code>recipe (String)</code>
     * <p>
     * This triggers when the player unlocks a recipe. There is 1
     * <b>required</b> condition for this trigger: <code>recipe</code>, which
     * specifies the resource location to the desired recipe that must be
     * unlocked. Note that this check only occurs at the time of receiving the
     * recipe; if the player unlocked it before the advancement exists, the
     * advancement will not take that into consideration. As the recipe is only
     * detected at the moment of unlocking it, you must revoke the recipe from
     * the player using the <code>/recipe</code> command before it can be
     * detected again.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-recipe_unlocked
     */
    RECIPE_UNLOCKED,

    /**
     * Available conditions: a {@link Location} object.
     * <p>
     * This trigger doesn't need a condition key.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-slept_in_bed
     */
    SLEPT_IN_BED,

    /**
     * Available conditions: <code>entity ({@link Entity})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-summoned_entity
     */
    SUMMONED_ENTITY,

    /**
     * Available conditions: <code>entity ({@link Entity})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-tame_animal
     */
    TAME_ANIMAL,

    /**
     * Available conditions: none
     * <p>
     * This simply triggers every tick. This can be used to help simulate
     * command block systems or automatically unlock advancements. The following
     * activates every tick, running a function as a reward that revokes the
     * advancement so that it may activate in the next tick.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-tick
     */
    TICK,

    /**
     * Available conditions: <code>distance ({@link Range})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-used_ender_eye
     */
    USED_ENDER_EYE,

    /**
     * Available conditions: <code>item ({@link Item})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-used_totem
     */
    USED_TOTEM,

    /**
     * Available conditions: <code>villager ({@link Entity})</code> and
     * <code>item ({@link Item})</code>.
     * 
     * @see https://github.com/skylinerw/guides/blob/master/java/advancements/triggers.md#-villager_trade
     */
    VILLAGER_TRADE;

    @Override
    public String toString() {
        return "minecraft:" + super.toString().toLowerCase();
    }
}