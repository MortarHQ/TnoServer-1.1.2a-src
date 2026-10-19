package com.tensura_tno.race.fox_spirit;

import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityEvent.Size;

@EventBusSubscriber(modid = "tensura_tno")
public final class FoxSpiritPlayerSizeHandler {
   private FoxSpiritPlayerSizeHandler() {
   }

   @SubscribeEvent
   public static void onEntitySize(Size event) {
      if (event.getEntity() instanceof Player player) {
         if (event.getPose() == Pose.SLEEPING) {
            if (FoxSpiritPlayerFormHelper.shouldUseFoxForm(player)) {
               event.setNewSize(player.getDimensions(Pose.SWIMMING));
            }
         }
      }
   }
}
