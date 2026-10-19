package com.b1n_ry.yigd.networking.packets;

import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record DeleteGraveC2SPacket(UUID graveId) implements CustomPacketPayload {
   public static final Type<DeleteGraveC2SPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("yigd", "delete_grave_request"));
   public static final StreamCodec<RegistryFriendlyByteBuf, DeleteGraveC2SPacket> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC, DeleteGraveC2SPacket::graveId, DeleteGraveC2SPacket::new
   );

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
