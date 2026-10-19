package org.crypticdev.ancient_artifacts.registry;

import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.item.weapon.SimpleSpearItem;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import java.util.List;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import org.crypticdev.ancient_artifacts.artifact.ArtifactDefinition;
import org.crypticdev.ancient_artifacts.artifact.ArtifactEffect;
import org.crypticdev.ancient_artifacts.artifact.ArtifactRefinement;
import org.crypticdev.ancient_artifacts.artifact.ArtifactSlot;
import org.crypticdev.ancient_artifacts.artifact.ArtifactUnlockCondition;
import org.crypticdev.ancient_artifacts.artifact.UnlockableAttributeArtifactEffect;
import org.crypticdev.ancient_artifacts.attachments.CombatTracker;
import org.crypticdev.ancient_artifacts.component.RefinementComponent;
import org.crypticdev.ancient_artifacts.component.UnlockComponent;
import org.crypticdev.ancient_artifacts.config.SpecialEffectConfig;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.BeastAuraConfig;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.CommandersControlledRageConfig;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.CurrentMasteryConfig;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.OgresWrathConfig;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.RisingInfernoConfig;

public final class Artifacts {
   public static final ArtifactDefinition TitanstrideGreaves = ArtifactDefinition.builder("titanstride_greaves")
      .slot(ArtifactSlot.FEET)
      .grade3(
         (grade, ability) -> ArtifactEffect.builder("unyielding_stance")
            .Multi(
               unlock -> List.of(
                  new UnlockableAttributeArtifactEffect("unyielding_stance1", () -> Attributes.ARMOR, 5.0, 0.5, Operation.ADD_VALUE, unlock),
                  new UnlockableAttributeArtifactEffect(
                     "unyielding_stance2", () -> Attributes.KNOCKBACK_RESISTANCE, 0.25, 0.05, Operation.ADD_MULTIPLIED_TOTAL, unlock
                  )
               ),
               grade,
               ability
            )
            .build()
      )
      .build();
   public static final ArtifactDefinition MagicuelConduitBelt = ArtifactDefinition.builder("magicule_conduit_belt")
      .slot(ArtifactSlot.BELT)
      .grade3(
         (grade, ability) -> ArtifactEffect.builder("magicule_efficiency")
            .Unlockable(() -> TensuraAttributes.MAGIC_COST_MULTIPLIER, -0.1, -0.01, Operation.ADD_VALUE, grade, ability)
            .build()
      )
      .build();
   public static final ArtifactDefinition AmuletOfTheSylphs = ArtifactDefinition.builder("amulet_of_the_sylphs")
      .slot(ArtifactSlot.NECKLACE)
      .grade2(
         (grade, ability) -> ArtifactEffect.builder("windborne_movement")
            .Unlockable(() -> Attributes.FLYING_SPEED, 0.15, 0.05, Operation.ADD_MULTIPLIED_TOTAL, grade, ability)
            .build(),
         (grade, ability) -> ArtifactEffect.builder("sovereign_wind")
            .Unlockable(() -> TensuraAttributes.WIND_BOOST, 0.25, 0.05, Operation.ADD_MULTIPLIED_TOTAL, grade, ability)
            .build()
      )
      .build();
   public static final ArtifactDefinition WarPendant = ArtifactDefinition.builder("war_pendant")
      .slot(ArtifactSlot.NECKLACE)
      .grade2(
         (grade, ability) -> ArtifactEffect.builder("magic_bullet_acceleration")
            .Unlockable(() -> ArtifactAttributesRegistry.MAGIC_BULLET_CHARGE_SPEED, 3.0, 1.0, Operation.ADD_MULTIPLIED_TOTAL, 3, grade, ability)
            .build(),
         (grade, ability) -> ArtifactEffect.builder("ogres_wrath")
            .Dynamic(() -> Attributes.ATTACK_DAMAGE, (SpecialEffectConfig)ConfigRegistry.getConfig(OgresWrathConfig.class), (entity, stack) -> {
               OgresWrathConfig config = (OgresWrathConfig)ConfigRegistry.getConfig(OgresWrathConfig.class);
               if (entity.getHealth() > entity.getMaxHealth() * config.health_percent_for_activate) {
                  return 0.0;
               }

               RefinementComponent refinement = ArtifactRefinement.get(stack);
               int refinementSteps = Math.max(0, refinement.upgrades().get(ability));
               if (config.max_refinements != -1) {
                  refinementSteps = Math.min(refinementSteps, config.max_refinements);
               }

               return config.base_value + config.value_per_refinement * refinementSteps;
            }, Operation.ADD_MULTIPLIED_TOTAL, grade, ability)
            .build()
      )
      .build();
   public static final ArtifactDefinition LionEmblem = ArtifactDefinition.builder("lion_emblem")
      .slot(ArtifactSlot.EMLEM)
      .grade1(
         (grade, ability) -> ArtifactEffect.builder("rising_inferno")
            .Dynamic(() -> TensuraAttributes.FLAME_BOOST, (SpecialEffectConfig)ConfigRegistry.getConfig(RisingInfernoConfig.class), (entity, stack) -> {
               if (entity instanceof Player player) {
                  RisingInfernoConfig risingInfernoConfig = (RisingInfernoConfig)ConfigRegistry.getConfig(RisingInfernoConfig.class);
                  RefinementComponent refinement = ArtifactRefinement.get(stack);
                  int refinementSteps = Math.max(0, refinement.upgrades().get(ability));
                  if (risingInfernoConfig.max_refinements != -1) {
                     refinementSteps = Math.min(refinementSteps, risingInfernoConfig.max_refinements);
                  }

                  if (!CombatTracker.isInCombat(player)) {
                     return 0.0;
                  }

                  long firstCombatTick = CombatTracker.getFirstCombatTick(player);
                  long ticksInCombat = player.tickCount - firstCombatTick;
                  int secondsInCombat = (int)ticksInCombat / 20;
                  double time_reduction = risingInfernoConfig.time_till_next_stack - refinementSteps * risingInfernoConfig.time_reduction_per_refine;
                  int StacksInFullPercent = (int)(secondsInCombat / time_reduction);
                  double stacks = StacksInFullPercent * risingInfernoConfig.increase_per_stack;
                  if (stacks > risingInfernoConfig.max_stack) {
                     stacks = risingInfernoConfig.max_stack;
                  }

                  return stacks;
               } else {
                  return 0.0;
               }
            }, Operation.ADD_MULTIPLIED_TOTAL, grade, ability)
            .build(),
         (grade, ability) -> ArtifactEffect.builder("hell_flare_amplification")
            .Unlockable(() -> ArtifactAttributesRegistry.HELL_FLARE_DAMAGE_MULTIPLIER, 0.5, 0.1, Operation.ADD_MULTIPLIED_TOTAL, grade, ability)
            .build(),
         (grade, ability) -> ArtifactEffect.builder("commanders_controlled_rage")
            .Dynamic(
               () -> TensuraAttributes.MAGIC_COST_MULTIPLIER,
               (SpecialEffectConfig)ConfigRegistry.getConfig(CommandersControlledRageConfig.class),
               (entity, stack) -> {
                  CommandersControlledRageConfig config = (CommandersControlledRageConfig)ConfigRegistry.getConfig(CommandersControlledRageConfig.class);
                  RefinementComponent refinement = ArtifactRefinement.get(stack);
                  int refinementSteps = Math.max(0, refinement.upgrades().get(ability));
                  if (config.max_refinements != -1) {
                     refinementSteps = Math.min(refinementSteps, config.max_refinements);
                  }

                  double bonusThreshold = refinementSteps * config.bonusThresholdPerRefinement;
                  double magiculePercent = TensuraStorages.getExistenceFrom(entity).getMagicule() / entity.getAttributeValue(TensuraAttributes.MAX_MAGICULE);
                  double missingMagicules = 1.0 - magiculePercent;
                  double magiculeChunk = config.magiculePercentForNextIncrease + bonusThreshold;
                  double reductionPerChunk = config.magiculeCostReductionPerIncrease;
                  int steps = (int)Math.floor(missingMagicules / magiculeChunk);
                  double reduction = steps * reductionPerChunk;
                  return -reduction;
               },
               Operation.ADD_MULTIPLIED_TOTAL,
               new ArtifactUnlockCondition()
                  .addEPCondition(grade, ability)
                  .addCondition(
                     (stack, player) -> {
                        if (stack.has(ArtifactDataComponents.UNLOCKS.get())) {
                           if (SkillUtils.isSkillMastered(player, (ManasSkill)UniqueSkills.COMMANDER.get())) {
                              stack.set(
                                 ArtifactDataComponents.UNLOCKS.get(),
                                 ((UnlockComponent)stack.get(ArtifactDataComponents.UNLOCKS.get())).append("commanders_controlled_rage")
                              );
                           }

                           return ((UnlockComponent)stack.get(ArtifactDataComponents.UNLOCKS.get())).has("commanders_controlled_rage");
                        } else {
                           return false;
                        }
                     }
                  )
            )
            .build()
      )
      .build();
   public static final ArtifactDefinition ShadowbindBracelet = ArtifactDefinition.builder("shadowbind_bracelet")
      .slot(ArtifactSlot.ARM)
      .grade1(
         (grade, ability) -> ArtifactEffect.builder("silent_strike").Description(grade, ability).build(),
         (grade, ability) -> ArtifactEffect.builder("armor_exploitation").Description(grade, ability).build(),
         (grade, ability) -> ArtifactEffect.builder("shadow_slash_mode")
            .Description(new ArtifactUnlockCondition().addEPCondition(grade, ability).addCondition((stack, player) -> {
               if (stack.has(ArtifactDataComponents.UNLOCKS.get())) {
                  if (SkillUtils.isSkillMastered(player, (ManasSkill)UniqueSkills.SHADOW_STRIKER.get())) {
                     stack.set(
                        ArtifactDataComponents.UNLOCKS.get(), ((UnlockComponent)stack.get(ArtifactDataComponents.UNLOCKS.get())).append("shadow_slash_mode")
                     );
                  }

                  return ((UnlockComponent)stack.get(ArtifactDataComponents.UNLOCKS.get())).has("shadow_slash_mode");
               } else {
                  return false;
               }
            }))
            .build()
      )
      .build();
   public static final ArtifactDefinition CrimsonMizuhiki = ArtifactDefinition.builder("crimson_mizuhiki")
      .slot(ArtifactSlot.EMLEM)
      .grade2(
         (grade, ability) -> ArtifactEffect.builder("rapid_incantation")
            .Unlockable(() -> TensuraAttributes.CHANT_SPEED, 0.1, 0.02, Operation.ADD_MULTIPLIED_TOTAL, grade, ability)
            .build(),
         (grade, ability) -> ArtifactEffect.builder("flow_of_mastery")
            .Unlockable(() -> TensuraAttributes.ABILITY_MASTERY_GAIN, 1.0, 0.1, Operation.ADD_MULTIPLIED_TOTAL, grade, ability)
            .build()
      )
      .build();
   public static final ArtifactDefinition HornsOfObservation = ArtifactDefinition.builder("horns_of_observation")
      .slot(ArtifactSlot.HEAD)
      .grade1(
         (grade, ability) -> ArtifactEffect.builder("prediction_evasion")
            .Unlockable(() -> TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, 0.05, 0.0133, Operation.ADD_MULTIPLIED_TOTAL, grade, ability)
            .build(),
         (grade, ability) -> ArtifactEffect.builder("demonic_awareness")
            .Unlockable(() -> TensuraAttributes.PRESENCE_SENSE, 2.0, 1.0, Operation.ADD_VALUE, grade, ability)
            .build(),
         (grade, ability) -> ArtifactEffect.builder("true_dodge").Description(grade, ability).build()
      )
      .build();
   public static final ArtifactDefinition TidalFin = ArtifactDefinition.builder("tidal_fin")
      .slot(ArtifactSlot.HEAD)
      .grade3(
         (grade, ability) -> ArtifactEffect.builder("current_mastery")
            .Dynamic(
               () -> ManasCoreAttributes.SWIM_SPEED_MULTIPLIER,
               new CurrentMasteryConfig(),
               (player, stack) -> {
                  CurrentMasteryConfig config = (CurrentMasteryConfig)ConfigRegistry.getConfig(CurrentMasteryConfig.class);
                  RefinementComponent refinement = ArtifactRefinement.get(stack);
                  int refinementSteps = Math.max(0, refinement.upgrades().get(ability));
                  if (config.max_refinements != -1) {
                     refinementSteps = Math.min(refinementSteps, config.max_refinements);
                  }

                  return SkillUtils.hasSkill(player, (ManasSkill)CommonSkills.WATER_CURRENT_CONTROL.get())
                        && SkillUtils.isSkillToggled(player, (ManasSkill)CommonSkills.WATER_CURRENT_CONTROL.get())
                     ? config.base_value + config.value_per_refinement * refinementSteps
                     : 0.0;
               },
               Operation.ADD_MULTIPLIED_TOTAL,
               grade,
               ability
            )
            .build()
      )
      .build();
   public static final ArtifactDefinition SashOfQuickness = ArtifactDefinition.builder("sash_of_quickness")
      .slot(ArtifactSlot.BELT)
      .grade2(
         (grade, ability) -> ArtifactEffect.builder("quick_movement")
            .Unlockable(() -> Attributes.MOVEMENT_SPEED, 0.01, 0.01, Operation.ADD_MULTIPLIED_TOTAL, 9, grade, ability)
            .build(),
         (grade, ability) -> ArtifactEffect.builder("focused_vision").Description(1, grade, ability).build()
      )
      .build();
   public static final ArtifactDefinition VermillionBirdCrown = ArtifactDefinition.builder("vermillion_bird_crown")
      .slot(ArtifactSlot.HEAD)
      .grade1(
         (grade, ability) -> ArtifactEffect.builder("spear_sovereignty")
            .Dynamic(() -> Attributes.ENTITY_INTERACTION_RANGE, new CurrentMasteryConfig(), (player, stack) -> {
               CurrentMasteryConfig config = (CurrentMasteryConfig)ConfigRegistry.getConfig(CurrentMasteryConfig.class);
               RefinementComponent refinement = ArtifactRefinement.get(stack);
               int refinementSteps = Math.max(0, refinement.upgrades().get(ability));
               if (config.max_refinements != -1) {
                  refinementSteps = Math.min(refinementSteps, config.max_refinements);
               }

               return player.getMainHandItem().getItem() instanceof SimpleSpearItem ? 1 + refinementSteps : 0.0;
            }, Operation.ADD_VALUE, grade, ability)
            .build(),
         (grade, ability) -> ArtifactEffect.builder("beast_aura")
            .Description(((BeastAuraConfig)ConfigRegistry.getConfig(BeastAuraConfig.class)).max_refinements(), grade, ability)
            .build(),
         (grade, ability) -> ArtifactEffect.builder("kings_awakening").Description(0, grade, ability).build()
      )
      .build();
   public static final List<ArtifactDefinition> ALL = List.of(
      TitanstrideGreaves, WarPendant, MagicuelConduitBelt, AmuletOfTheSylphs, LionEmblem, ShadowbindBracelet, CrimsonMizuhiki
   );

   private Artifacts() {
   }
}
