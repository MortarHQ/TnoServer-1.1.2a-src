package com.mortal_cultivation.race.requirement;

import com.mortal_cultivation.registry.CultivationEntities;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public final class QilinKillRequirement extends EvolutionRequirement {
   public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         return player.getStats().getValue(Stats.ENTITY_KILLED.get((EntityType)CultivationEntities.AZURE_MOSS_QILIN.get())) > 0 ? 1.0F : 0.0F;
      } else {
         return entity.level().isClientSide ? QilinKillRequirement.ClientProgress.get(entity) : 0.0F;
      }
   }

   public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
      return Component.translatable("mortal_cultivation.evolution_menu.qilin_kill_requirement");
   }

   @OnlyIn(Dist.CLIENT)
   private static final class ClientProgress {
      private static float get(LivingEntity entity) {
         return entity instanceof LocalPlayer player
               && player.getStats().getValue(Stats.ENTITY_KILLED.get((EntityType)CultivationEntities.AZURE_MOSS_QILIN.get())) > 0
            ? 1.0F
            : 0.0F;
      }
   }
}
