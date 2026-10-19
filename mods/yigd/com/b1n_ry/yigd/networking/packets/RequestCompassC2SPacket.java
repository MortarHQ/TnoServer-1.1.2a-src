package com.b1n_ry.yigd.networking.packets;

import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record RequestCompassC2SPacket(UUID graveId) implements CustomPacketPayload {
   public static final Type<RequestCompassC2SPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("yigd", "request_grave_compass"));
   public static final StreamCodec<RegistryFriendlyByteBuf, RequestCompassC2SPacket> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC, RequestCompassC2SPacket::graveId, RequestCompassC2SPacket::new
   );

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
