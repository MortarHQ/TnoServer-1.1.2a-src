package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.AsmodaySpatialCubeClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record AsmodaySpatialCubePayload(
   int id,
   int casterId,
   int targetId,
   double casterX,
   double casterY,
   double casterZ,
   double targetX,
   double targetY,
   double targetZ,
   int visualCubes,
   int seed,
   int duration,
   boolean mastered,
   boolean shifted
) implements CustomPacketPayload {
   public static final Type<AsmodaySpatialCubePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "asmoday_spatial_cube"));
   public static final StreamCodec<ByteBuf, AsmodaySpatialCubePayload> STREAM_CODEC = new StreamCodec<ByteBuf, AsmodaySpatialCubePayload>() {
      public AsmodaySpatialCubePayload decode(ByteBuf buf) {
         return new AsmodaySpatialCubePayload(
            buf.readInt(),
            buf.readInt(),
            buf.readInt(),
            buf.readDouble(),
            buf.readDouble(),
            buf.readDouble(),
            buf.readDouble(),
            buf.readDouble(),
            buf.readDouble(),
            buf.readInt(),
            buf.readInt(),
            buf.readInt(),
            buf.readBoolean(),
            buf.readBoolean()
         );
      }

      public void encode(ByteBuf buf, AsmodaySpatialCubePayload payload) {
         buf.writeInt(payload.id());
         buf.writeInt(payload.casterId());
         buf.writeInt(payload.targetId());
         buf.writeDouble(payload.casterX());
         buf.writeDouble(payload.casterY());
         buf.writeDouble(payload.casterZ());
         buf.writeDouble(payload.targetX());
         buf.writeDouble(payload.targetY());
         buf.writeDouble(payload.targetZ());
         buf.writeInt(payload.visualCubes());
         buf.writeInt(payload.seed());
         buf.writeInt(payload.duration());
         buf.writeBoolean(payload.mastered());
         buf.writeBoolean(payload.shifted());
      }
   };

   public static void handle(AsmodaySpatialCubePayload payload, PacketContext context) {
      context.queue(
         () -> AsmodaySpatialCubeClient.play(
            payload.id(),
            payload.casterId(),
            payload.targetId(),
            payload.casterX(),
            payload.casterY(),
            payload.casterZ(),
            payload.targetX(),
            payload.targetY(),
            payload.targetZ(),
            payload.visualCubes(),
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
