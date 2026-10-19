package com.tensura_tno.race.fox_spirit;

import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class SummonMaxEPRequirement extends EvolutionRequirement {
   private static final String TAG_MAX_SUMMON_BONUS_EP = "maxSummonBonusEP";
   private final double requiredEP;

   public SummonMaxEPRequirement(double requiredEP) {
      this.requiredEP = requiredEP;
   }

   public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
      if (!(entity instanceof Player)) {
         return 0.0F;
      }

      double maxEP = instance.getOrCreateTag().getDouble("maxSummonBonusEP");
      return (float)Math.min(1.0, maxEP / this.requiredEP);
   }

   public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
      return Component.translatable("tensura_tno.evolution_menu.summon_max_ep_requirement", new Object[]{formatEP(this.requiredEP)});
   }

   private static String formatEP(double value) {
      if (value >= 1.0E9) {
         return stripTrailing(value / 1.0E9) + "G";
      } else if (value >= 1000000.0) {
         return stripTrailing(value / 1000000.0) + "M";
      } else {
         return value >= 1000.0 ? stripTrailing(value / 1000.0) + "k" : String.valueOf((long)value);
      }
   }

   private static String stripTrailing(double v) {
      long lv = (long)v;
      return lv == v ? String.valueOf(lv) : String.format("%.1f", v);
   }

   public static void updateMaxSummonBonusEP(ManasRaceInstance instance, double currentMax) {
      CompoundTag tag = instance.getOrCreateTag();
      double recorded = tag.getDouble("maxSummonBonusEP");
      if (currentMax > recorded) {
         tag.putDouble("maxSummonBonusEP", currentMax);
         instance.markDirty();
      }
   }

   public double getRequiredEP() {
      return this.requiredEP;
   }
}
