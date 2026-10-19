package com.natamus.starterkit_common_neoforge.networking.packets;

import com.natamus.collective_common_neoforge.implementations.networking.data.PacketContext;
import com.natamus.collective_common_neoforge.implementations.networking.data.Side;
import com.natamus.starterkit_common_neoforge.data.VariablesClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ToClientAskIfModIsInstalledPacket {
   public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath("starterkit", "to_client_ask_if_mod_is_installed_packet");

   public static ToClientAskIfModIsInstalledPacket decode(FriendlyByteBuf buf) {
      return new ToClientAskIfModIsInstalledPacket();
   }

   public void encode(FriendlyByteBuf buf) {
   }

   public static void handle(PacketContext<ToClientAskIfModIsInstalledPacket> ctx) {
      if (ctx.side().equals(Side.CLIENT)) {
         VariablesClient.waitingForAnnouncement = true;
      }
   }
}
