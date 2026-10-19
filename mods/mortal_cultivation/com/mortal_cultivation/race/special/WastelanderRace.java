package com.mortal_cultivation.race.special;

import com.github.manasmods.tensura.race.Race;
import com.github.manasmods.tensura.registry.race.TensuraRaces;
import com.github.manasmods.tensura.util.JumpPowerHelper;
import com.mojang.datafixers.util.Pair;
import com.mortal_cultivation.config.race.SpecialRaceConfig;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class WastelanderRace extends Race {
   private static final SpecialRaceConfig.Wastelander CONFIG = new SpecialRaceConfig.Wastelander();

   public WastelanderRace() {
      super(Difficulty.EXTREME);
   }

   @Override
   public double getBaseHealth() {
      return CONFIG.baseHealth;
   }

   @Override
   public float getPlayerSize() {
      return CONFIG.playerSize;
   }

   @Override
   public double getBaseAttackDamage() {
      return CONFIG.baseAttackDamage;
   }

   @Override
   public double getBaseAttackSpeed() {
      return CONFIG.baseAttackSpeed;
   }

   @Override
   public double getKnockbackResistance() {
      return CONFIG.knockbackResistance;
   }

   @Override
   public double getJumpHeight() {
      return JumpPowerHelper.defaultPlayer(CONFIG.jumpHeight);
   }

   @Override
   public double getMovementSpeed() {
      return CONFIG.movementSpeed;
   }

   @Override
   public double getSprintSpeed() {
      return CONFIG.sprintSpeed;
   }

   public Pair<Double, Double> getBaseAuraRange() {
      return Pair.of(CONFIG.auraMin, CONFIG.auraMax);
   }

   public Pair<Double, Double> getBaseMagiculeRange() {
      return Pair.of(CONFIG.startingMagiculeMin, CONFIG.startingMagiculeMax);
   }

   @Override
   public double getBaseSpiritualHealth() {
      return CONFIG.baseSpiritualHealth;
   }

   @Override
   public List<TensuraSkill> getIntrinsicSkills(Player player) {
      return new ArrayList<>();
   }

   @Override
   public void raceAbility(Player player) {
   }

   @Nullable
   @Override
   public Race getDefaultEvolution(Player player) {
      return null;
   }

   @Nullable
   @Override
   public ManasRace getDefaultManasEvolution(Player player) {
      return (ManasRace)TensuraRaces.HUMAN.get();
   }

   @Nullable
   @Override
   public Race getAwakeningEvolution(Player player) {
      return null;
   }

   @Nullable
   @Override
   public Race getHarvestFestivalEvolution(Player player) {
      return null;
   }

   @Override
   public List<Race> getNextEvolutions(Player player) {
      return new ArrayList<>();
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of();
   }

   @Override
   public List<Race> getPreviousEvolutions(Player player) {
      return new ArrayList<>();
   }
}
