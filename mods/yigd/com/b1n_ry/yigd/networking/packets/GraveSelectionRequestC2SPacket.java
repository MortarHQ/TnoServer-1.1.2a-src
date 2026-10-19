package com.b1n_ry.yigd.networking.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.ResolvableProfile;
import org.jetbrains.annotations.NotNull;

public record GraveSelectionRequestC2SPacket(ResolvableProfile profile) implements CustomPacketPayload {
   public static final Type<GraveSelectionRequestC2SPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("yigd", "grave_selection_request"));
   public static final StreamCodec<RegistryFriendlyByteBuf, GraveSelectionRequestC2SPacket> STREAM_CODEC = StreamCodec.composite(
      ResolvableProfile.STREAM_CODEC, GraveSelectionRequestC2SPacket::profile, GraveSelectionRequestC2SPacket::new
   );

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
