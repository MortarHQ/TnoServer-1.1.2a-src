package com.mortal_cultivation.flight;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record FlightBrakeRequestPayload(int sequence, boolean brakeHeld) implements CustomPacketPayload {
   public static final Type<FlightBrakeRequestPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "flight_brake_request"));
   public static final StreamCodec<FriendlyByteBuf, FlightBrakeRequestPayload> STREAM_CODEC = CustomPacketPayload.codec(
      FlightBrakeRequestPayload::write, FlightBrakeRequestPayload::new
   );

   private FlightBrakeRequestPayload(FriendlyByteBuf buffer) {
      this(buffer.readVarInt(), buffer.readBoolean());
   }

   private void write(FriendlyByteBuf buffer) {
      buffer.writeVarInt(this.sequence);
      buffer.writeBoolean(this.brakeHeld);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
