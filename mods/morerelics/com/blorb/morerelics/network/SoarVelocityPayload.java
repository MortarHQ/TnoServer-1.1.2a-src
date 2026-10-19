package com.blorb.morerelics.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SoarVelocityPayload(double x, double y, double z) implements CustomPacketPayload {
   public static final Type<SoarVelocityPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("morerelics", "soar_velocity"));
   public static final StreamCodec<ByteBuf, SoarVelocityPayload> STREAM_CODEC = new StreamCodec<ByteBuf, SoarVelocityPayload>() {
      public void encode(ByteBuf o, SoarVelocityPayload soarVelocityPayload) {
         ByteBufCodecs.DOUBLE.encode(o, soarVelocityPayload.x());
         ByteBufCodecs.DOUBLE.encode(o, soarVelocityPayload.y());
         ByteBufCodecs.DOUBLE.encode(o, soarVelocityPayload.z());
      }

      @NotNull
      public SoarVelocityPayload decode(ByteBuf byteBuf) {
         double x = (Double)ByteBufCodecs.DOUBLE.decode(byteBuf);
         double y = (Double)ByteBufCodecs.DOUBLE.decode(byteBuf);
         double z = (Double)ByteBufCodecs.DOUBLE.decode(byteBuf);
         return new SoarVelocityPayload(x, y, z);
      }
   };

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
