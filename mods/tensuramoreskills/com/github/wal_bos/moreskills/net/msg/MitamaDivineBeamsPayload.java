package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.MitamaDivineBeamsClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record MitamaDivineBeamsPayload(
   int packetId,
   int casterId,
   int targetId,
   double casterX,
   double casterY,
   double casterZ,
   double targetX,
   double targetY,
   double targetZ,
   int beams,
   int seed,
   int duration
) implements CustomPacketPayload {
   public static final Type<MitamaDivineBeamsPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mitama_divine_beams"));
   public static final StreamCodec<ByteBuf, MitamaDivineBeamsPayload> STREAM_CODEC = new StreamCodec<ByteBuf, MitamaDivineBeamsPayload>() {
      public MitamaDivineBeamsPayload decode(ByteBuf buf) {
         return new MitamaDivineBeamsPayload(
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

      public void encode(ByteBuf buf, MitamaDivineBeamsPayload payload) {
         buf.writeInt(payload.packetId());
         buf.writeInt(payload.casterId());
         buf.writeInt(payload.targetId());
         buf.writeDouble(payload.casterX());
         buf.writeDouble(payload.casterY());
         buf.writeDouble(payload.casterZ());
         buf.writeDouble(payload.targetX());
         buf.writeDouble(payload.targetY());
         buf.writeDouble(payload.targetZ());
         buf.writeInt(payload.beams());
         buf.writeInt(payload.seed());
         buf.writeInt(payload.duration());
      }
   };

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(MitamaDivineBeamsPayload payload, PacketContext context) {
      context.queue(
         () -> MitamaDivineBeamsClient.play(
            payload.packetId(),
            payload.casterId(),
            payload.targetId(),
            payload.casterX(),
            payload.casterY(),
            payload.casterZ(),
            payload.targetX(),
            payload.targetY(),
            payload.targetZ(),
            payload.beams(),
            payload.seed(),
            payload.duration()
         )
      );
   }
}
