package com.github.manasmods.tensura.race;

import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public abstract class Race extends DefaultRace {
   private static final ResourceLocation LEGACY_MAX_HEALTH = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "legacy_race_max_health");
   private static final ResourceLocation LEGACY_SCALE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "legacy_race_scale");
   private static final ResourceLocation LEGACY_ATTACK_DAMAGE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "legacy_race_attack_damage");
   private static final ResourceLocation LEGACY_ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "legacy_race_attack_speed");
   private static final ResourceLocation LEGACY_KNOCKBACK = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "legacy_race_knockback");
   private static final ResourceLocation LEGACY_MOVEMENT = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "legacy_race_movement");
   private static final ResourceLocation LEGACY_JUMP = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "legacy_race_jump");
   private static final ResourceLocation LEGACY_SPIRITUAL_HEALTH = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "legacy_race_spiritual_health");
   private static final double VANILLA_BASE_SPIRITUAL_HEALTH = 60.0;
   private boolean legacyModifiersInitialized;

   protected Race(Difficulty difficulty) {
      super(difficulty);
   }

   public abstract double getBaseHealth();

   public abstract float getPlayerSize();

   public abstract double getBaseAttackDamage();

   public abstract double getBaseAttackSpeed();

   public abstract double getKnockbackResistance();

   public abstract double getJumpHeight();

   public abstract double getMovementSpeed();

   public abstract double getSprintSpeed();

   public double getBaseSpiritualHealth() {
      return 0.0;
   }

   public boolean isMajin() {
      return false;
   }

   public Alignment getAlignment() {
      return this.isMajin() ? Alignment.MAJIN : super.getAlignment();
   }

   public List<TensuraSkill> getIntrinsicSkills(Player player) {
      return new ArrayList<>();
   }

   @Nullable
   public Race getDefaultEvolution(Player player) {
      return null;
   }

   @Nullable
   public ManasRace getDefaultManasEvolution(Player player) {
      return this.getDefaultEvolution(player);
   }

   @Nullable
   public Race getAwakeningEvolution(Player player) {
      return null;
   }

   @Nullable
   public ManasRace getAwakeningManasEvolution(Player player) {
      return this.getAwakeningEvolution(player);
   }

   @Nullable
   public Race getHarvestFestivalEvolution(Player player) {
      return null;
   }

   @Nullable
   public ManasRace getHarvestFestivalManasEvolution(Player player) {
      return this.getHarvestFestivalEvolution(player);
   }

   public List<Race> getNextEvolutions(Player player) {
      return new ArrayList<>();
   }

   public List<ManasRace> getNextManasEvolutions(Player player) {
      return new ArrayList<>(this.getNextEvolutions(player));
   }

   public double getEvolutionPercentage(Player player) {
      return 0.0;
   }

   public List<Component> getRequirementsForRendering(Player player) {
      return new ArrayList<>();
   }

   protected List<Pair<Component, Float>> getRequirementProgresses(Player player) {
      return List.of();
   }

   public List<Race> getPreviousEvolutions(Player player) {
      return new ArrayList<>();
   }

   public List<ManasRace> getPreviousManasEvolutions(Player player) {
      return new ArrayList<>(this.getPreviousEvolutions(player));
   }

   public void raceAbility(Player player) {
   }

   public final List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return entity instanceof Player player ? new ArrayList<>(this.getIntrinsicSkills(player)) : new ArrayList<>();
   }

   @Nullable
   public final ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         ManasRace configuredDefault = this.getDefaultManasEvolution(player);
         if (configuredDefault != null) {
            return configuredDefault;
         }

         List<ManasRace> evolutions = this.getNextManasEvolutions(player);
         if (evolutions != null) {
            for (ManasRace evolution : evolutions) {
               if (evolution != null) {
                  return evolution;
               }
            }
         }
      }

      return null;
   }

   @Nullable
   public final ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return entity instanceof Player player ? this.getAwakeningManasEvolution(player) : null;
   }

   @Nullable
   public final ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return entity instanceof Player player ? this.getHarvestFestivalManasEvolution(player) : null;
   }

   public final List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         List<ManasRace> configuredEvolutions = this.getNextManasEvolutions(player);
         List<ManasRace> evolutions = configuredEvolutions == null ? new ArrayList<>() : new ArrayList<>(configuredEvolutions);
         evolutions.removeIf(evolution -> evolution == null);
         ManasRace configuredDefault = this.getDefaultManasEvolution(player);
         if (configuredDefault != null && !evolutions.contains(configuredDefault)) {
            evolutions.add(0, configuredDefault);
         }

         return evolutions;
      } else {
         return new ArrayList<>();
      }
   }

   public final List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return entity instanceof Player player ? new ArrayList<>(this.getPreviousManasEvolutions(player)) : new ArrayList<>();
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of();
   }

   public Default getDefaultConfig() {
      return new Race.RaceConfigAdapter();
   }

   public void onActivateAbility(ManasRaceInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         this.raceAbility(player);
      }
   }

   public void addAttributeModifiers(ManasRaceInstance instance, LivingEntity entity) {
      this.ensureLegacyAttributeModifiers();
      super.addAttributeModifiers(instance, entity);
   }

   public void removeAttributeModifiers(ManasRaceInstance instance, LivingEntity entity) {
      this.ensureLegacyAttributeModifiers();
      super.removeAttributeModifiers(instance, entity);
   }

   private void ensureLegacyAttributeModifiers() {
      if (!this.legacyModifiersInitialized) {
         this.legacyModifiersInitialized = true;
         this.addAttributeModifier(Attributes.MAX_HEALTH, LEGACY_MAX_HEALTH, this.getBaseHealth() - 20.0, Operation.ADD_VALUE);
         this.addAttributeModifier(Attributes.ATTACK_DAMAGE, LEGACY_ATTACK_DAMAGE, this.getBaseAttackDamage(), Operation.ADD_VALUE);
         this.addAttributeModifier(Attributes.ATTACK_SPEED, LEGACY_ATTACK_SPEED, this.getBaseAttackSpeed() - 4.0, Operation.ADD_VALUE);
         this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, LEGACY_KNOCKBACK, this.getKnockbackResistance(), Operation.ADD_VALUE);
         this.addAttributeModifier(Attributes.MOVEMENT_SPEED, LEGACY_MOVEMENT, this.getMovementSpeed() - 0.1, Operation.ADD_VALUE);
         this.addAttributeModifier(Attributes.JUMP_STRENGTH, LEGACY_JUMP, this.getJumpHeight(), Operation.ADD_VALUE);
         double spiritualDelta = this.getBaseSpiritualHealth() - 60.0;
         if (Math.abs(spiritualDelta) > 1.0E-4) {
            this.addAttributeModifier(TensuraAttributes.MAX_SPIRITUAL_HEALTH, LEGACY_SPIRITUAL_HEALTH, spiritualDelta, Operation.ADD_VALUE);
         }

         double scaleDelta = this.getPlayerSize() / 2.0F - 1.0;
         if (Math.abs(scaleDelta) > 1.0E-4) {
            this.addAttributeModifier(Attributes.SCALE, LEGACY_SCALE, scaleDelta, Operation.ADD_VALUE);
         }
      }
   }

   private class RaceConfigAdapter extends Default {
      public double getMinAura() {
         return (Double)Race.this.getBaseAuraRange().getFirst();
      }

      public double getMaxAura() {
         return (Double)Race.this.getBaseAuraRange().getSecond();
      }

      public double getMinMagicule() {
         return (Double)Race.this.getBaseMagiculeRange().getFirst();
      }

      public double getMaxMagicule() {
         return (Double)Race.this.getBaseMagiculeRange().getSecond();
      }

      public double getSize() {
         return Race.this.getPlayerSize() / 2.0F - 1.0;
      }

      public double getMaxHealth() {
         return Race.this.getBaseHealth() - 20.0;
      }

      public double getMaxSpiritualHealth() {
         return Race.this.getBaseSpiritualHealth();
      }

      public double getAttack() {
         return Race.this.getBaseAttackDamage();
      }

      public double getAttackSpeed() {
         return Race.this.getBaseAttackSpeed() - 4.0;
      }

      public double getKnockbackResistance() {
         return Race.this.getKnockbackResistance();
      }

      public double getMovementSpeed() {
         return Race.this.getMovementSpeed() - 0.1;
      }

      public double getSwimSpeed() {
         return 0.0;
      }
   }
}
