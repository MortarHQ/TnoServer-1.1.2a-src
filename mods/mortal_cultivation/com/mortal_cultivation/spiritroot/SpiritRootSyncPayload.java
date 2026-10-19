package com.mortal_cultivation.spiritroot;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record SpiritRootSyncPayload(String rootId) implements CustomPacketPayload {
   public static final Type<SpiritRootSyncPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "sync_spirit_root"));
   public static final StreamCodec<ByteBuf, SpiritRootSyncPayload> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.STRING_UTF8, SpiritRootSyncPayload::rootId, SpiritRootSyncPayload::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
