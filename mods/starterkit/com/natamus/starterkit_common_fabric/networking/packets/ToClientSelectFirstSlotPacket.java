package com.natamus.starterkit_common_fabric.networking.packets;

import com.natamus.collective_common_fabric.implementations.networking.data.PacketContext;
import com.natamus.collective_common_fabric.implementations.networking.data.Side;
import com.natamus.starterkit_common_fabric.functions.StarterClientFunctions;
import net.minecraft.class_2540;
import net.minecraft.class_2960;

public class ToClientSelectFirstSlotPacket {
   public static final class_2960 CHANNEL = class_2960.method_60655("starterkit", "to_client_select_first_slot_packet");

   public static ToClientSelectFirstSlotPacket decode(class_2540 buf) {
      return new ToClientSelectFirstSlotPacket();
   }

   public void encode(class_2540 buf) {
   }

   public static void handle(PacketContext<ToClientSelectFirstSlotPacket> ctx) {
      if (ctx.side().equals(Side.CLIENT)) {
         StarterClientFunctions.selectFirstSlot();
      }
   }
}
