package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.BerettaCameraShakeClient;
import com.github.wal_bos.moreskills.client.fx.BerettaReversedCrossVfxClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record BerettaReversedCrossPayload(
   int bossId, int targetId, double anchorX, double anchorY, double anchorZ, double dirX, double dirZ, int duration, int seed
) implements CustomPacketPayload {
   public static final Type<BerettaReversedCrossPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "beretta_reversed_cross"));
   public static final StreamCodec<ByteBuf, BerettaReversedCrossPayload> STREAM_CODEC = StreamCodec.of(
      BerettaReversedCrossPayload::write, BerettaReversedCrossPayload::read
   );

   private static void write(ByteBuf buffer, BerettaReversedCrossPayload payload) {
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

   private static BerettaReversedCrossPayload read(ByteBuf buffer) {
      return new BerettaReversedCrossPayload(
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

   public static void handle(BerettaReversedCrossPayload payload, PacketContext context) {
      context.queue(
         () -> {
            BerettaReversedCrossVfxClient.add(
               payload.bossId, payload.targetId, payload.anchorX, payload.anchorY, payload.anchorZ, payload.dirX, payload.dirZ, payload.duration, payload.seed
            );
            BerettaCameraShakeClient.add(5, payload.anchorX, payload.anchorY, payload.anchorZ, payload.duration);
         }
      );
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
