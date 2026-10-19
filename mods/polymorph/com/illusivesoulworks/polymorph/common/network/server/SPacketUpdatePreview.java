package com.illusivesoulworks.polymorph.common.network.server;

import javax.annotation.Nonnull;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public class SPacketUpdatePreview implements CustomPacketPayload {
   public static final Type<SPacketUpdatePreview> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("polymorph", "update_preview"));
   public static final SPacketUpdatePreview INSTANCE = new SPacketUpdatePreview();
   public static final StreamCodec<FriendlyByteBuf, SPacketUpdatePreview> STREAM_CODEC = StreamCodec.unit(INSTANCE);

   public static void handle(SPacketUpdatePreview packet) {
      ClientPacketHandler.handle(packet);
   }

   @Nonnull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
