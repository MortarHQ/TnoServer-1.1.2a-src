package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.AsmodaySpatialAbsorptionClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record AsmodaySpatialAbsorptionPayload(
   int id,
   int ownerId,
   int threatId,
   double startX,
   double startY,
   double startZ,
   double endX,
   double endY,
   double endZ,
   double size,
   int duration,
   int seed,
   boolean mastered
) implements CustomPacketPayload {
   public static final Type<AsmodaySpatialAbsorptionPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "asmoday_spatial_absorption")
   );
   public static final StreamCodec<ByteBuf, AsmodaySpatialAbsorptionPayload> STREAM_CODEC = new StreamCodec<ByteBuf, AsmodaySpatialAbsorptionPayload>() {
      public AsmodaySpatialAbsorptionPayload decode(ByteBuf buffer) {
         return new AsmodaySpatialAbsorptionPayload(
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Boolean)ByteBufCodecs.BOOL.decode(buffer)
         );
      }

      public void encode(ByteBuf buffer, AsmodaySpatialAbsorptionPayload payload) {
         ByteBufCodecs.INT.encode(buffer, payload.id());
         ByteBufCodecs.INT.encode(buffer, payload.ownerId());
         ByteBufCodecs.INT.encode(buffer, payload.threatId());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.startX());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.startY());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.startZ());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.endX());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.endY());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.endZ());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.size());
         ByteBufCodecs.INT.encode(buffer, payload.duration());
         ByteBufCodecs.INT.encode(buffer, payload.seed());
         ByteBufCodecs.BOOL.encode(buffer, payload.mastered());
      }
   };

   public static void handle(AsmodaySpatialAbsorptionPayload payload, PacketContext context) {
      context.queue(
         () -> AsmodaySpatialAbsorptionClient.play(
            payload.id(),
            payload.ownerId(),
            payload.threatId(),
            payload.startX(),
            payload.startY(),
            payload.startZ(),
            payload.endX(),
            payload.endY(),
            payload.endZ(),
            payload.size(),
            payload.duration(),
            payload.seed(),
            payload.mastered()
         )
      );
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
