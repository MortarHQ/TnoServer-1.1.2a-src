package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.AsmodayFallingCubeClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record AsmodayFallingCubePayload(
   int id,
   int casterId,
   int targetId,
   double originX,
   double originY,
   double originZ,
   double centerX,
   double centerY,
   double centerZ,
   int cubes,
   int seed,
   int duration,
   boolean mastered,
   boolean shifted
) implements CustomPacketPayload {
   public static final Type<AsmodayFallingCubePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "asmoday_falling_cube"));
   public static final StreamCodec<ByteBuf, AsmodayFallingCubePayload> STREAM_CODEC = new StreamCodec<ByteBuf, AsmodayFallingCubePayload>() {
      public AsmodayFallingCubePayload decode(ByteBuf buffer) {
         return new AsmodayFallingCubePayload(
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Boolean)ByteBufCodecs.BOOL.decode(buffer),
            (Boolean)ByteBufCodecs.BOOL.decode(buffer)
         );
      }

      public void encode(ByteBuf buffer, AsmodayFallingCubePayload payload) {
         ByteBufCodecs.INT.encode(buffer, payload.id());
         ByteBufCodecs.INT.encode(buffer, payload.casterId());
         ByteBufCodecs.INT.encode(buffer, payload.targetId());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.originX());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.originY());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.originZ());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.centerX());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.centerY());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.centerZ());
         ByteBufCodecs.INT.encode(buffer, payload.cubes());
         ByteBufCodecs.INT.encode(buffer, payload.seed());
         ByteBufCodecs.INT.encode(buffer, payload.duration());
         ByteBufCodecs.BOOL.encode(buffer, payload.mastered());
         ByteBufCodecs.BOOL.encode(buffer, payload.shifted());
      }
   };

   public static void handle(AsmodayFallingCubePayload payload, PacketContext context) {
      context.queue(
         () -> AsmodayFallingCubeClient.play(
            payload.id(),
            payload.casterId(),
            payload.targetId(),
            payload.originX(),
            payload.originY(),
            payload.originZ(),
            payload.centerX(),
            payload.centerY(),
            payload.centerZ(),
            payload.cubes(),
            payload.seed(),
            payload.duration(),
            payload.mastered(),
            payload.shifted()
         )
      );
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
