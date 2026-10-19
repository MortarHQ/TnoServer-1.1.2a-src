package com.blorb.morerelics.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SilkCounterPayload(int silkCounter) implements CustomPacketPayload {
   public static final Type<SilkCounterPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("morerelics", "silk_counter"));
   public static final StreamCodec<ByteBuf, SilkCounterPayload> STREAM_CODEC = new StreamCodec<ByteBuf, SilkCounterPayload>() {
      public void encode(ByteBuf o, SilkCounterPayload silkCounterPayload) {
         ByteBufCodecs.VAR_INT.encode(o, silkCounterPayload.silkCounter());
      }

      @NotNull
      public SilkCounterPayload decode(ByteBuf byteBuf) {
         int silkCounter = (Integer)ByteBufCodecs.VAR_INT.decode(byteBuf);
         return new SilkCounterPayload(silkCounter);
      }
   };

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
