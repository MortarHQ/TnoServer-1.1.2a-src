package com.tensura_tno.race.fox_spirit;

import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class FoxKillRequirement extends EvolutionRequirement {
   private static final String TAG_SUMMON_KILLS = "summonKills";
   private final int requiredKills;

   public FoxKillRequirement(int requiredKills) {
      this.requiredKills = requiredKills;
   }

   public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
      if (!(entity instanceof Player)) {
         return 0.0F;
      }

      int kills = getSummonKills(instance);
      return Math.min(1.0F, (float)kills / this.requiredKills);
   }

   public static int getSummonKills(ManasRaceInstance instance) {
      if (instance == null) {
         return 0;
      }

      CompoundTag tag = instance.getOrCreateTag();
      return tag.getInt("summonKills");
   }

   public static int incrementSummonKills(ManasRaceInstance instance) {
      if (instance == null) {
         return 0;
      }

      CompoundTag tag = instance.getOrCreateTag();
      int kills = tag.getInt("summonKills") + 1;
      tag.putInt("summonKills", kills);
      instance.markDirty();
      return kills;
   }

   public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
      return Component.translatable("tensura_tno.evolution_menu.summon_kill_requirement", new Object[]{this.requiredKills});
   }

   public int getRequiredKills() {
      return this.requiredKills;
   }
}
