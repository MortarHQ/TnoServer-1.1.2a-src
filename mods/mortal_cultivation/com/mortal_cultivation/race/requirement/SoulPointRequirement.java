package com.mortal_cultivation.race.requirement;

import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class SoulPointRequirement extends EvolutionRequirement {
   private final int requirement;

   public SoulPointRequirement(int requirement) {
      this.requirement = requirement;
   }

   public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
      return entity instanceof Player player ? (float)TensuraStorages.getExistenceFrom(player).getSoulPoints() / this.requirement : 0.0F;
   }

   public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
      return Component.translatable("mortal_cultivation.evolution_menu.soul_point_requirement", new Object[]{this.requirement});
   }

   public int getRequirement() {
      return this.requirement;
   }
}
