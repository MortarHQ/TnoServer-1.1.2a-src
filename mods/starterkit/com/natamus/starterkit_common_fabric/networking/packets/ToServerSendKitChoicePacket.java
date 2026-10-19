package com.natamus.starterkit_common_fabric.networking.packets;

import com.natamus.collective_common_fabric.functions.MessageFunctions;
import com.natamus.collective_common_fabric.implementations.networking.data.PacketContext;
import com.natamus.collective_common_fabric.implementations.networking.data.Side;
import com.natamus.starterkit_common_fabric.config.ConfigHandler;
import com.natamus.starterkit_common_fabric.functions.StarterCheckFunctions;
import com.natamus.starterkit_common_fabric.functions.StarterGearFunctions;
import com.natamus.starterkit_common_fabric.util.Util;
import net.minecraft.class_124;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2960;

public class ToServerSendKitChoicePacket {
   public static final class_2960 CHANNEL = class_2960.method_60655("starterkit", "to_server_send_kit_choice_packet");
   private final String kitName;

   public ToServerSendKitChoicePacket(String kitNameIn) {
      this.kitName = kitNameIn;
   }

   public static ToServerSendKitChoicePacket decode(class_2540 buf) {
      String kitNameIn = buf.method_19772();
      return new ToServerSendKitChoicePacket(kitNameIn);
   }

   public void encode(class_2540 buf) {
      buf.method_10814(this.kitName);
   }

   public static void handle(PacketContext<ToServerSendKitChoicePacket> ctx) {
      if (ctx.side().equals(Side.SERVER)) {
         ToServerSendKitChoicePacket packet = (ToServerSendKitChoicePacket)ctx.message();
         class_1657 player = ctx.sender();
         if (StarterCheckFunctions.shouldPlayerReceiveStarterKit(player)) {
            class_1937 level = player.method_37908();
            if (level.method_8503().method_3816()) {
               if (ConfigHandler.announcePlayerKitChoiceInDedicatedServer) {
                  MessageFunctions.broadcastMessage(
                     level,
                     class_2561.method_43470(player.method_5477().getString() + " has chosen the '" + Util.formatKitName(packet.kitName) + "' kit!")
                        .method_27692(class_124.field_1077)
                  );
               }
            } else {
               MessageFunctions.sendMessage(
                  player, "You have been given the '" + Util.formatKitName(packet.kitName) + "' starter kit.", class_124.field_1077, true
               );
            }

            StarterGearFunctions.giveStarterKit(player, null, packet.kitName);
         }
      }
   }
}
