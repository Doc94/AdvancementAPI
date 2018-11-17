package io.chazza.advancementapi;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import io.chazza.advancementapi.common.Builder;
import io.chazza.advancementapi.common.Jsonable;

/**
 * The <code>requirements</code> list specifies a Conjunctive Normal Form
 * structure to accompany criteria. It essentially allows boolean logic to
 * determine when the advancement is granted based on the accompanying criteria.
 * The list contains more lists, which in turn contains strings that equal the
 * names of the criteria. You would use this if you want to use an OR operation.
 * Each new list is a new AND operation, while each comma-separated string
 * within the list is an OR operation.
 * <p>
 * If <code>requirements</code> is not specified, then <b>all</b> criteria must
 * be fulfilled. If this list is specified, then every criteria must be included
 * in <code>requirements</code> to determine which ones need to be fulfilled.
 * <p>
 * Given the following advancement:
 * 
 * <pre>
 * {
 *     "criteria": {
 *         "trigger_1": {
 *             "trigger": "minecraft:entity_hurt_player"
 *         },
 *         "trigger_2": {
 *             "trigger": "minecraft:used_ender_eye"
 *         }
 *     },
 *     "requirements": [
 *         ["trigger_1"], ["trigger_2"]
 *     ]
 * }
 * 
 * Requirements.builder().andOneOfThese("trigger_1").andOneOfThese("trigger_2").build();
 * </pre>
 * 
 * Both "trigger_1" and "trigger_2" criteria must be fulfilled before the
 * advancement can be granted. Since that would be the case without
 * <code>requirements</code>, it is not necessary to use it here. Using logical
 * operators, this can be viewed as:
 * 
 * <pre>
 * "trigger_1" && "trigger_2"
 * </pre>
 * 
 * Modifying the requirements list slightly, which joins "trigger_2" with
 * "trigger_1":
 * 
 * <pre>
 * {
 *     "criteria": {
 *         "trigger_1": {
 *             "trigger": "minecraft:entity_hurt_player"
 *         },
 *         "trigger_2": {
 *             "trigger": "minecraft:used_ender_eye"
 *         }
 *     },
 *     "requirements": [
 *         ["trigger_1", "trigger_2"]
 *     ]
 * }
 * 
 * Requirements.builder().andOneOfThese("trigger_1", "trigger_2").build();
 * </pre>
 * 
 * Now <b>either</b> "trigger_1" or "trigger_2" must be completed to be granted
 * the advancement. Using logical operators, this can be viewed as:
 * 
 * <pre>
 * "trigger_1" || "trigger_2"
 * </pre>
 * 
 * Given the following advancement:
 * 
 * <pre>
 * {
 *     "criteria": {
 *         "trigger_1": {
 *             "trigger": "minecraft:entity_hurt_player"
 *         },
 *         "trigger_2": {
 *             "trigger": "minecraft:used_ender_eye"
 *         },
 *         "trigger_3": {
 *             "trigger": "minecraft:player_killed_entity"
 *         }
 *     },
 *     "requirements": [
 *         ["trigger_1"],
 *         ["trigger_2", "trigger_3"]
 *     ]
 * }
 * 
 * Requirements.builder().andOneOfThese("trigger_1").andOneOfThese("trigger_2", "trigger_3").build();
 * </pre>
 * 
 * The advancement is granted if the "trigger_1" criterion is met, while either
 * "trigger_2" and "trigger_3" are also met. If only "trigger_1" is completed,
 * the advancement is not granted. Using logical operators, this can be viewed
 * as:
 * 
 * <pre>
 * "trigger_1" && ("trigger_2" || "trigger_3")
 * </pre>
 * 
 * And modifying the requirements slightly:
 * 
 * <pre>
 * {
 *     "criteria": {
 *         "trigger_1": {
 *             "trigger": "minecraft:entity_hurt_player"
 *         },
 *         "trigger_2": {
 *             "trigger": "minecraft:used_ender_eye"
 *         },
 *         "trigger_3": {
 *             "trigger": "minecraft:player_killed_entity"
 *         }
 *     },
 *     "requirements": [
 *         ["trigger_1", "trigger_2", "trigger_3"]
 *     ]
 * }
 * 
 * Requirements.builder().andOneOfThese("trigger_1", "trigger_2", "trigger_3").build();
 * </pre>
 * 
 * The advancement is granted if any of the criteria are met. Using logical
 * operators, this can be viewed as:
 * 
 * <pre>
 * "trigger_1" || "trigger_2" || "trigger_3"
 * </pre>
 * 
 * @author Kaonashi97
 * @see "https://github.com/skylinerw/guides/blob/master/java/advancements.md#-requirements"
 */
public class Requirements implements Jsonable {
    private List<List<String>> requirements = new LinkedList<>();

    private Requirements(List<List<String>> requirements) {
        this.requirements = requirements;
    }

    /**
     * Returns a {@link RequirementsBuilder} for building {@link Requirements}s.
     * 
     * @return the builder
     */
    public static RequirementsBuilder builder() {
        return new RequirementsBuilder();
    }

    @Override
    public JsonElement toJson() {
        JsonArray requirementsArray = new JsonArray();
        requirements.forEach(l -> {
            JsonArray orArray = new JsonArray();
            l.forEach(orTrigger -> orArray.add(orTrigger));
            requirementsArray.add(orArray);
        });
        return requirementsArray;
    }

    /**
     * Builder for {@link Trigger}s. See {@link Trigger} for more information.
     * 
     * @author Kaonashi97
     */
    public static class RequirementsBuilder implements Builder<Requirements> {
        private List<List<String>> requirements = new LinkedList<>();

        private RequirementsBuilder() {
            // build pattern
        }

        /**
         * Adds this {@link Trigger} names to the requirements list. All of the
         * names specified here are in an OR relation. This whole list is in an
         * AND relation with the existing lists.
         * 
         * @param ors names of triggers
         * @return this builder
         */
        public RequirementsBuilder andOneOfThese(String... ors) {
            requirements.add(Arrays.asList(ors));
            return this;
        }

        @Override
        public Requirements build() {
            return new Requirements(requirements);
        }
    }
}