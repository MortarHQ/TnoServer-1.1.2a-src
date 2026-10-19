package com.foxablazeultimate.network;

import com.foxablazeultimate.client.FoxAblazeUltimateClientAccess;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record ShowWisdomCrystalEasterEggPayload() implements CustomPacketPayload {
   public static final Type<ShowWisdomCrystalEasterEggPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "show_wisdom_crystal_easter_egg")
   );
   public static final StreamCodec<FriendlyByteBuf, ShowWisdomCrystalEasterEggPayload> STREAM_CODEC = CustomPacketPayload.codec(
      ShowWisdomCrystalEasterEggPayload::encode, ShowWisdomCrystalEasterEggPayload::new
   );

   public ShowWisdomCrystalEasterEggPayload(FriendlyByteBuf buffer) {
      this();
   }

   public void encode(FriendlyByteBuf buffer) {
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(FoxAblazeUltimateClientAccess::showWisdomCrystalEasterEgg);
      }
   }

   public Type<ShowWisdomCrystalEasterEggPayload> type() {
      return TYPE;
   }
}
