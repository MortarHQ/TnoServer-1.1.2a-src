package com.natamus.starterkit_common_neoforge.networking.packets;

import com.natamus.collective_common_neoforge.implementations.networking.data.PacketContext;
import com.natamus.collective_common_neoforge.implementations.networking.data.Side;
import com.natamus.starterkit_common_neoforge.functions.StarterClientFunctions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ToClientSelectFirstSlotPacket {
   public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath("starterkit", "to_client_select_first_slot_packet");

   public static ToClientSelectFirstSlotPacket decode(FriendlyByteBuf buf) {
      return new ToClientSelectFirstSlotPacket();
   }

   public void encode(FriendlyByteBuf buf) {
   }

   public static void handle(PacketContext<ToClientSelectFirstSlotPacket> ctx) {
      if (ctx.side().equals(Side.CLIENT)) {
         StarterClientFunctions.selectFirstSlot();
      }
   }
}
