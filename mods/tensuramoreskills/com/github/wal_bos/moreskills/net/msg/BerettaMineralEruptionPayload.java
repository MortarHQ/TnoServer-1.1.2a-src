package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.BerettaCameraShakeClient;
import com.github.wal_bos.moreskills.client.fx.BerettaMineralEruptionVfxClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record BerettaMineralEruptionPayload(
   int bossId, int targetId, double anchorX, double anchorY, double anchorZ, double dirX, double dirZ, int duration, int seed
) implements CustomPacketPayload {
   public static final Type<BerettaMineralEruptionPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "beretta_mineral_eruption")
   );
   public static final StreamCodec<ByteBuf, BerettaMineralEruptionPayload> STREAM_CODEC = StreamCodec.of(
      BerettaMineralEruptionPayload::write, BerettaMineralEruptionPayload::read
   );

   private static void write(ByteBuf buffer, BerettaMineralEruptionPayload payload) {
      buffer.writeInt(payload.bossId);
      buffer.writeInt(payload.targetId);
      buffer.writeDouble(payload.anchorX);
      buffer.writeDouble(payload.anchorY);
      buffer.writeDouble(payload.anchorZ);
      buffer.writeDouble(payload.dirX);
      buffer.writeDouble(payload.dirZ);
      buffer.writeInt(payload.duration);
      buffer.writeInt(payload.seed);
   }

   private static BerettaMineralEruptionPayload read(ByteBuf buffer) {
      return new BerettaMineralEruptionPayload(
         buffer.readInt(),
         buffer.readInt(),
         buffer.readDouble(),
         buffer.readDouble(),
         buffer.readDouble(),
         buffer.readDouble(),
         buffer.readDouble(),
         buffer.readInt(),
         buffer.readInt()
      );
   }

   public static void handle(BerettaMineralEruptionPayload payload, PacketContext context) {
      context.queue(
         () -> {
            BerettaMineralEruptionVfxClient.add(
               payload.bossId, payload.targetId, payload.anchorX, payload.anchorY, payload.anchorZ, payload.dirX, payload.dirZ, payload.duration, payload.seed
            );
            BerettaCameraShakeClient.add(4, payload.anchorX, payload.anchorY, payload.anchorZ, payload.duration);
         }
      );
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
