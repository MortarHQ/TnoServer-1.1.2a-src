package com.natamus.starterkit_common_forge.networking.packets;

import com.natamus.collective_common_forge.implementations.networking.api.Dispatcher;
import com.natamus.collective_common_forge.implementations.networking.data.PacketContext;
import com.natamus.collective_common_forge.implementations.networking.data.Side;
import com.natamus.starterkit_common_forge.data.Variables;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ToServerAnnounceModIsInstalledPacket {
   public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath("starterkit", "to_server_announce_mod_is_installed_packet");

   public static ToServerAnnounceModIsInstalledPacket decode(FriendlyByteBuf buf) {
      return new ToServerAnnounceModIsInstalledPacket();
   }

   public void encode(FriendlyByteBuf buf) {
   }

   public static void handle(PacketContext<ToServerAnnounceModIsInstalledPacket> ctx) {
      if (ctx.side().equals(Side.SERVER)) {
         Player player = ctx.sender();
         UUID uuid = player.getUUID();
         if (!Variables.playersWithModInstalledOnClient.contains(uuid)) {
            Variables.playersWithModInstalledOnClient.add(uuid);
         }

         Dispatcher.sendToClient(new ToClientReceiveKitDataPacket(Variables.starterGearEntries, Variables.starterKitDescriptions), (ServerPlayer)player);
      }
   }
}
