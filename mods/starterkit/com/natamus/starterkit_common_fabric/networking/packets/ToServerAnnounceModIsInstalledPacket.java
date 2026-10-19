package com.natamus.starterkit_common_fabric.networking.packets;

import com.natamus.collective_common_fabric.implementations.networking.api.Dispatcher;
import com.natamus.collective_common_fabric.implementations.networking.data.PacketContext;
import com.natamus.collective_common_fabric.implementations.networking.data.Side;
import com.natamus.starterkit_common_fabric.data.Variables;
import java.util.UUID;
import net.minecraft.class_1657;
import net.minecraft.class_2540;
import net.minecraft.class_2960;
import net.minecraft.class_3222;

public class ToServerAnnounceModIsInstalledPacket {
   public static final class_2960 CHANNEL = class_2960.method_60655("starterkit", "to_server_announce_mod_is_installed_packet");

   public static ToServerAnnounceModIsInstalledPacket decode(class_2540 buf) {
      return new ToServerAnnounceModIsInstalledPacket();
   }

   public void encode(class_2540 buf) {
   }

   public static void handle(PacketContext<ToServerAnnounceModIsInstalledPacket> ctx) {
      if (ctx.side().equals(Side.SERVER)) {
         class_1657 player = ctx.sender();
         UUID uuid = player.method_5667();
         if (!Variables.playersWithModInstalledOnClient.contains(uuid)) {
            Variables.playersWithModInstalledOnClient.add(uuid);
         }

         Dispatcher.sendToClient(new ToClientReceiveKitDataPacket(Variables.starterGearEntries, Variables.starterKitDescriptions), (class_3222)player);
      }
   }
}
