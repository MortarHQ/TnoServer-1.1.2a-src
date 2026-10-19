package com.b1n_ry.yigd.networking.packets;

import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record GraveOverviewRequestC2SPacket(UUID graveId) implements CustomPacketPayload {
   public static final Type<GraveOverviewRequestC2SPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("yigd", "grave_overview_request"));
   public static final StreamCodec<RegistryFriendlyByteBuf, GraveOverviewRequestC2SPacket> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC, GraveOverviewRequestC2SPacket::graveId, GraveOverviewRequestC2SPacket::new
   );

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
