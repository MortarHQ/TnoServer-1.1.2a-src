package com.mortal_cultivation.race.requirement;

import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

public class WanEPRequirement extends EvolutionRequirement {
   private final double requirement;

   public WanEPRequirement(double requirement) {
      this.requirement = requirement;
   }

   public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
      return (float)(EnergyHelper.getBaseMaxEP(entity) / this.requirement);
   }

   public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
      long wan = Math.round(this.requirement / 10000.0);
      String fullFormatted = String.format("%,d", (long)this.requirement);
      return Component.translatable("mortal_cultivation.evolution_menu.ep_requirement_wan", new Object[]{wan, fullFormatted});
   }

   public double getRequirement() {
      return this.requirement;
   }
}
