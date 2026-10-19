package com.foxablazeultimate.ability.skill.beelzebub;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class BeelzebubFusionTickHandler {
   private static final int CHECK_INTERVAL = 20;

   private BeelzebubFusionTickHandler() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide()) {
         if (player instanceof ServerPlayer serverPlayer) {
            BeelzebubFusion.tickCeremony(serverPlayer);
            if (player.tickCount % 20 == 0) {
               BeelzebubFusion.tryFuse(serverPlayer);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerClone(Clone event) {
      BeelzebubFusion.copyInactivityReclaimed(event.getOriginal(), event.getEntity());
   }

   @SubscribeEvent
   public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         BeelzebubFusion.clearCeremony(player.getUUID());
      }
   }
}
