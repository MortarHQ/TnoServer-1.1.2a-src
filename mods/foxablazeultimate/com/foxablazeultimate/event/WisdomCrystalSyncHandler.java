package com.foxablazeultimate.event;

import com.foxablazeultimate.item.CrystalLockState;
import com.foxablazeultimate.network.SyncCrystalLockPayload;
import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class WisdomCrystalSyncHandler {
   private WisdomCrystalSyncHandler() {
   }

   @SubscribeEvent
   public static void onLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         syncTo(player);
      }
   }

   @SubscribeEvent
   public static void onClone(Clone event) {
      CrystalLockState.copyToClone(event.getOriginal(), event.getEntity());
   }

   public static void syncTo(ServerPlayer player) {
      NetworkManager.sendToPlayer(player, new SyncCrystalLockPayload(CrystalLockState.getLockedSkills(player)));
   }
}
