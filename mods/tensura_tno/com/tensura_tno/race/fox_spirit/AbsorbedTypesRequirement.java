package com.tensura_tno.race.fox_spirit;

import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AbsorbedTypesRequirement extends EvolutionRequirement {
   private static final String TAG_ABSORBED_COUNT = "absorbedTypesCount";
   private final int requirement;

   public AbsorbedTypesRequirement(int requirement) {
      this.requirement = requirement;
   }

   public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
      if (!(entity instanceof Player)) {
         return 0.0F;
      }

      int count = instance.getOrCreateTag().getInt("absorbedTypesCount");
      return (float)count / this.requirement;
   }

   public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
      return Component.translatable("tensura_tno.evolution_menu.absorbed_types_requirement", new Object[]{this.requirement});
   }

   public int getRequirement() {
      return this.requirement;
   }
}
