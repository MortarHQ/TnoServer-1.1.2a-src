package com.illusivesoulworks.polymorph.common.network.server;

import javax.annotation.Nonnull;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record SPacketHighlightRecipe(ResourceLocation recipe) implements CustomPacketPayload {
   public static final Type<SPacketHighlightRecipe> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("polymorph", "highlight_recipe"));
   public static final StreamCodec<FriendlyByteBuf, SPacketHighlightRecipe> STREAM_CODEC = StreamCodec.composite(
      ResourceLocation.STREAM_CODEC, SPacketHighlightRecipe::recipe, SPacketHighlightRecipe::new
   );

   public static void handle(SPacketHighlightRecipe packet) {
      ClientPacketHandler.handle(packet);
   }

   @Nonnull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
