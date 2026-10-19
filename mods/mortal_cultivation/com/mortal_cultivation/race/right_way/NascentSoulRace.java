package com.mortal_cultivation.race.right_way;

import com.github.manasmods.tensura.race.Race;
import com.github.manasmods.tensura.util.JumpPowerHelper;
import com.mojang.datafixers.util.Pair;
import com.mortal_cultivation.config.race.RightWayRaceConfig;
import com.mortal_cultivation.registry.race.CultivationRaces;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.BossRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class NascentSoulRace extends Race {
   private static final RightWayRaceConfig.NascentSoul CONFIG = new RightWayRaceConfig.NascentSoul();
   private static final Map<UUID, Boolean> doubleJumpEnabled = new HashMap<>();
   private static final Map<UUID, Boolean> hasDoubleJumped = new HashMap<>();

   public NascentSoulRace() {
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
      return new ArrayList<>();
   }

   @Nullable
   @Override
   public Race getDefaultEvolution(Player player) {
      return null;
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
      nextEvolutions.add((Race)CultivationRaces.SPIRIT_TRANSFORMATION_RACE.get());
      return nextEvolutions;
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new EPRequirement(60000.0), 50.0F, new BossRequirement(5), 50.0F);
   }

   @Override
   public List<Race> getPreviousEvolutions(Player player) {
      List<Race> list = new ArrayList<>();
      list.add((Race)CultivationRaces.GOLDEN_CORE_RACE.get());
      return list;
   }

   @Override
   public void raceAbility(Player player) {
      if (!player.isDeadOrDying() && !player.isSleeping()) {
         UUID playerId = player.getUUID();
         boolean currentlyEnabled = doubleJumpEnabled.getOrDefault(playerId, false);
         if (currentlyEnabled) {
            doubleJumpEnabled.put(playerId, false);
            player.displayClientMessage(Component.translatable("mortal_cultivation.ability.double_jump.disabled").withStyle(ChatFormatting.RED), true);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 0.6F, 1.2F);
         } else {
            doubleJumpEnabled.put(playerId, true);
            player.displayClientMessage(Component.translatable("mortal_cultivation.ability.double_jump.enabled").withStyle(ChatFormatting.GREEN), true);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.6F, 1.0F);

            for (int i = 0; i < 20; i++) {
               player.level()
                  .addParticle(
                     ParticleTypes.CLOUD,
                     player.getX() + (player.getRandom().nextDouble() - 0.5) * 0.5,
                     player.getY(),
                     player.getZ() + (player.getRandom().nextDouble() - 0.5) * 0.5,
                     0.0,
                     0.1,
                     0.0
                  );
            }
         }
      }
   }

   public boolean isDoubleJumpEnabled(UUID playerId) {
      return doubleJumpEnabled.getOrDefault(playerId, false);
   }

   public void setDoubleJumped(UUID playerId) {
      hasDoubleJumped.put(playerId, true);
   }

   public void resetDoubleJump(Player player) {
      UUID playerId = player.getUUID();
      if (doubleJumpEnabled.getOrDefault(playerId, false)) {
         hasDoubleJumped.put(playerId, false);
      }
   }

   @Override
   public boolean isMajin() {
      return false;
   }

   public boolean isSpiritual() {
      return true;
   }

   public boolean isDivine() {
      return false;
   }
}
