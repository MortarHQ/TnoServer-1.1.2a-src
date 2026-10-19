package com.tensura_tno.event;

import io.github.manasmods.tensura.entity.human.golem.TrainingDummyEntity;
import io.github.manasmods.tensura.entity.monster.OrcLordEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityMountEvent;

@EventBusSubscriber(modid = "tensura_tno")
public final class OrcTrainingDummyGrabHandler {
   private OrcTrainingDummyGrabHandler() {
   }

   @SubscribeEvent
   public static void onEntityMount(EntityMountEvent event) {
      if (event.isMounting() && event.getEntityMounting() instanceof TrainingDummyEntity && event.getEntityBeingMounted() instanceof OrcLordEntity) {
         event.setCanceled(true);
      }
   }
}
