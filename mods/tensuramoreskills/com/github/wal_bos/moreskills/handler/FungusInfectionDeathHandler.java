package com.github.wal_bos.moreskills.handler;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class FungusInfectionDeathHandler {
   private FungusInfectionDeathHandler() {
   }

   public static void init() {
   }

   @SubscribeEvent
   public static void onPlayerClone(Clone event) {
      if (event.isWasDeath()) {
         CompoundTag oldData = event.getOriginal().getPersistentData();
         if (oldData.contains("tms_fungus_infection")) {
            CompoundTag copied = oldData.getCompound("tms_fungus_infection").copy();
            event.getEntity().getPersistentData().put("tms_fungus_infection", copied);
         }
      }
   }
}
