package com.mortal_cultivation.flight;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record FlightBrakeStatePayload(UUID playerId, boolean brakeHeld, int sequence) implements CustomPacketPayload {
   public static final Type<FlightBrakeStatePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "flight_brake_state"));
   public static final StreamCodec<FriendlyByteBuf, FlightBrakeStatePayload> STREAM_CODEC = CustomPacketPayload.codec(
      FlightBrakeStatePayload::write, FlightBrakeStatePayload::new
   );

   private FlightBrakeStatePayload(FriendlyByteBuf buffer) {
      this(buffer.readUUID(), buffer.readBoolean(), buffer.readVarInt());
   }

   private void write(FriendlyByteBuf buffer) {
      buffer.writeUUID(this.playerId);
      buffer.writeBoolean(this.brakeHeld);
      buffer.writeVarInt(this.sequence);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
