package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.BerettaAbyssalGearSweepVfxClient;
import com.github.wal_bos.moreskills.client.fx.BerettaCameraShakeClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record BerettaAbyssalGearSweepPayload(
   int bossId, int targetId, double anchorX, double anchorY, double anchorZ, double dirX, double dirZ, int duration, int seed
) implements CustomPacketPayload {
   public static final Type<BerettaAbyssalGearSweepPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "beretta_abyssal_gear_sweep")
   );
   public static final StreamCodec<ByteBuf, BerettaAbyssalGearSweepPayload> STREAM_CODEC = StreamCodec.of(
      BerettaAbyssalGearSweepPayload::write, BerettaAbyssalGearSweepPayload::read
   );

   private static void write(ByteBuf buffer, BerettaAbyssalGearSweepPayload payload) {
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

   private static BerettaAbyssalGearSweepPayload read(ByteBuf buffer) {
      return new BerettaAbyssalGearSweepPayload(
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

   public static void handle(BerettaAbyssalGearSweepPayload payload, PacketContext context) {
      context.queue(
         () -> {
            BerettaAbyssalGearSweepVfxClient.add(
               payload.bossId, payload.targetId, payload.anchorX, payload.anchorY, payload.anchorZ, payload.dirX, payload.dirZ, payload.duration, payload.seed
            );
            BerettaCameraShakeClient.add(9, payload.anchorX, payload.anchorY, payload.anchorZ, payload.duration);
         }
      );
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
