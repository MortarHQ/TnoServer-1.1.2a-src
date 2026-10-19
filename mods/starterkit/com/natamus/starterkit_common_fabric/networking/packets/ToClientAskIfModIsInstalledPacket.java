package com.natamus.starterkit_common_fabric.networking.packets;

import com.natamus.collective_common_fabric.implementations.networking.data.PacketContext;
import com.natamus.collective_common_fabric.implementations.networking.data.Side;
import com.natamus.starterkit_common_fabric.data.VariablesClient;
import net.minecraft.class_2540;
import net.minecraft.class_2960;

public class ToClientAskIfModIsInstalledPacket {
   public static final class_2960 CHANNEL = class_2960.method_60655("starterkit", "to_client_ask_if_mod_is_installed_packet");

   public static ToClientAskIfModIsInstalledPacket decode(class_2540 buf) {
      return new ToClientAskIfModIsInstalledPacket();
   }

   public void encode(class_2540 buf) {
   }

   public static void handle(PacketContext<ToClientAskIfModIsInstalledPacket> ctx) {
      if (ctx.side().equals(Side.CLIENT)) {
         VariablesClient.waitingForAnnouncement = true;
      }
   }
}
