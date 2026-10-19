package com.mortal_cultivation.flight;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record FlightAnimationStatePayload(UUID playerId, boolean active) implements CustomPacketPayload {
   public static final Type<FlightAnimationStatePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "flight_animation_state"));
   public static final StreamCodec<FriendlyByteBuf, FlightAnimationStatePayload> STREAM_CODEC = CustomPacketPayload.codec(
      FlightAnimationStatePayload::write, FlightAnimationStatePayload::new
   );

   private FlightAnimationStatePayload(FriendlyByteBuf buffer) {
      this(buffer.readUUID(), buffer.readBoolean());
   }

   private void write(FriendlyByteBuf buffer) {
      buffer.writeUUID(this.playerId);
      buffer.writeBoolean(this.active);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
