package com.tensura_tno.event;

import com.tensura_tno.race.fox_spirit.SpiritSummonEntityHelper;
import com.tensura_tno.race.fox_spirit.SpiritSummonLifecycle;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

@EventBusSubscriber(modid = "tensura_tno")
public final class SpiritSummonDropsEventHandler {
   private SpiritSummonDropsEventHandler() {
   }

   @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
   public static void onLivingDrops(LivingDropsEvent event) {
      LivingEntity entity = event.getEntity();
      if (!entity.level().isClientSide()) {
         if (SpiritSummonEntityHelper.isSpiritSummon(entity)) {
            event.getDrops().clear();
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onDeath(LivingDeathEvent event) {
      LivingEntity entity = event.getEntity();
      if (!entity.level().isClientSide() && SpiritSummonEntityHelper.isSpiritSummon(entity)) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         entity.getPersistentData().putBoolean("tensura_tno_spirit_summon_entity", true);
         existence.setSkippingEPDrop(true);
         entity.skipDropExperience();
         if (SpiritSummonLifecycle.isSummoning(entity)) {
            event.setCanceled(true);
            entity.discard();
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
   public static void onExperienceDrop(LivingExperienceDropEvent event) {
      if (SpiritSummonEntityHelper.isSpiritSummon(event.getEntity())) {
         event.setDroppedExperience(0);
         event.setCanceled(true);
      }
   }
}
