package com.b1n_ry.yigd.networking.packets;

import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record LockGraveC2SPacket(UUID graveId, boolean locked) implements CustomPacketPayload {
   public static final Type<LockGraveC2SPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("yigd", "lock_grave_request"));
   public static final StreamCodec<RegistryFriendlyByteBuf, LockGraveC2SPacket> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC, LockGraveC2SPacket::graveId, ByteBufCodecs.BOOL, LockGraveC2SPacket::locked, LockGraveC2SPacket::new
   );

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
