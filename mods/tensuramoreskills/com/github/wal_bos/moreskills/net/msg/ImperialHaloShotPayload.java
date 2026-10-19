package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.ImperialHaloShotClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record ImperialHaloShotPayload(
   int packetId,
   int casterId,
   int targetId,
   double casterX,
   double casterY,
   double casterZ,
   double targetX,
   double targetY,
   double targetZ,
   int rings,
   int seed,
   int duration
) implements CustomPacketPayload {
   public static final Type<ImperialHaloShotPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "imperial_halo_shot"));
   public static final StreamCodec<ByteBuf, ImperialHaloShotPayload> STREAM_CODEC = new StreamCodec<ByteBuf, ImperialHaloShotPayload>() {
      public ImperialHaloShotPayload decode(ByteBuf buf) {
         return new ImperialHaloShotPayload(
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
            buf.readInt()
         );
      }

      public void encode(ByteBuf buf, ImperialHaloShotPayload payload) {
         buf.writeInt(payload.packetId());
         buf.writeInt(payload.casterId());
         buf.writeInt(payload.targetId());
         buf.writeDouble(payload.casterX());
         buf.writeDouble(payload.casterY());
         buf.writeDouble(payload.casterZ());
         buf.writeDouble(payload.targetX());
         buf.writeDouble(payload.targetY());
         buf.writeDouble(payload.targetZ());
         buf.writeInt(payload.rings());
         buf.writeInt(payload.seed());
         buf.writeInt(payload.duration());
      }
   };

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(ImperialHaloShotPayload payload, PacketContext context) {
      context.queue(
         () -> ImperialHaloShotClient.play(
            payload.packetId(),
            payload.casterId(),
            payload.targetId(),
            payload.casterX(),
            payload.casterY(),
            payload.casterZ(),
            payload.targetX(),
            payload.targetY(),
            payload.targetZ(),
            payload.rings(),
            payload.seed(),
            payload.duration()
         )
      );
   }
}
