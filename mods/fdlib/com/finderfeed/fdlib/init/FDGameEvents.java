package com.finderfeed.fdlib.init;

import com.finderfeed.fdlib.FDLib;
import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.config.JsonConfig;
import com.finderfeed.fdlib.systems.config.packets.JsonConfigSyncPacket;
import com.finderfeed.fdlib.systems.config.packets.TriggerClientsideConfigReloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = "fdlib", bus = Bus.GAME)
public class FDGameEvents {
   @SubscribeEvent
   public static void serverStartedEvent(ServerStartedEvent event) {
      FDLib.LOGGER.info("Loading FD configs...");

      for (JsonConfig config : FDRegistries.CONFIGS) {
         config.loadFromDisk();
      }

      FDLib.LOGGER.info("Loaded FD configs.");
   }

   @SubscribeEvent
   public static void onPlayerJoin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         PacketDistributor.sendToPlayer(player, new JsonConfigSyncPacket(), new CustomPacketPayload[0]);
         PacketDistributor.sendToPlayer(player, new TriggerClientsideConfigReloadPacket(false), new CustomPacketPayload[0]);
      }
   }
}
