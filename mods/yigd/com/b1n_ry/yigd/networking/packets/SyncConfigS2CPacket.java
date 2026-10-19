package com.b1n_ry.yigd.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SyncConfigS2CPacket(boolean gravesBreakable, boolean glowingGraves, int maxGraveGlowingDistance, double deathSightDistance)
   implements CustomPacketPayload {
   public static final Type<SyncConfigS2CPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("yigd", "sync_config"));
   public static final StreamCodec<RegistryFriendlyByteBuf, SyncConfigS2CPacket> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.BOOL,
      SyncConfigS2CPacket::gravesBreakable,
      ByteBufCodecs.BOOL,
      SyncConfigS2CPacket::glowingGraves,
      ByteBufCodecs.INT,
      SyncConfigS2CPacket::maxGraveGlowingDistance,
      ByteBufCodecs.DOUBLE,
      SyncConfigS2CPacket::deathSightDistance,
      SyncConfigS2CPacket::new
   );

   @NotNull
   public Type<SyncConfigS2CPacket> type() {
      return TYPE;
   }
}
