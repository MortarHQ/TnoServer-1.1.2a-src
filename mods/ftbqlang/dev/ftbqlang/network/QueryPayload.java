package dev.ftbqlang.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record QueryPayload(boolean bookOpened) implements CustomPacketPayload {
   public static final Type<QueryPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("ftbqlang", "query"));
   public static final StreamCodec<FriendlyByteBuf, QueryPayload> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.BOOL, QueryPayload::bookOpened, QueryPayload::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
