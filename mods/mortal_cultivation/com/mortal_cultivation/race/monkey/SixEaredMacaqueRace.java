package com.mortal_cultivation.race.monkey;

import com.github.manasmods.tensura.race.Race;
import com.github.manasmods.tensura.util.JumpPowerHelper;
import com.mojang.datafixers.util.Pair;
import com.mortal_cultivation.config.race.MonkeyRaceConfig;
import com.mortal_cultivation.race.requirement.WanEPRequirement;
import com.mortal_cultivation.registry.race.CultivationRaces;
import com.mortal_cultivation.registry.skill.CultivationSkills;
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

public class SixEaredMacaqueRace extends Race {
   private static final MonkeyRaceConfig.SixEaredMacaque CONFIG = new MonkeyRaceConfig.SixEaredMacaque();

   public SixEaredMacaqueRace() {
      super(Difficulty.INTERMEDIATE);
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
      List<TensuraSkill> list = new ArrayList<>();
      list.add((TensuraSkill)CultivationSkills.SIZE_TRANSFORMATION.get());
      return list;
   }

   @Nullable
   @Override
   public Race getDefaultEvolution(Player player) {
      return (Race)CultivationRaces.SOUNDWAVE_IMMORTAL_APE_RACE.get();
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
      List<Race> nextEvolutions = new ArrayList<>();
      nextEvolutions.add((Race)CultivationRaces.SOUNDWAVE_IMMORTAL_APE_RACE.get());
      return nextEvolutions;
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new WanEPRequirement(900000.0), 100.0F);
   }

   @Override
   public List<Race> getPreviousEvolutions(Player player) {
      List<Race> list = new ArrayList<>();
      list.add((Race)CultivationRaces.ENLIGHTENED_STONE_MONKEY_RACE.get());
      return list;
   }

   @Override
   public boolean isMajin() {
      return true;
   }

   public boolean isSpiritual() {
      return true;
   }

   public boolean isDivine() {
      return false;
   }
}
