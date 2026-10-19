package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.ChronoJudgementClockClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record ChronoJudgementClockPayload(int bossId, int targetId, double centerX, double centerY, double centerZ, int duration, int seed)
   implements CustomPacketPayload {
   public static final Type<ChronoJudgementClockPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "chrono_judgement_clock"));
   public static final StreamCodec<ByteBuf, ChronoJudgementClockPayload> STREAM_CODEC = StreamCodec.of(
      ChronoJudgementClockPayload::write, ChronoJudgementClockPayload::read
   );

   private static void write(ByteBuf buffer, ChronoJudgementClockPayload payload) {
      buffer.writeInt(payload.bossId);
      buffer.writeInt(payload.targetId);
      buffer.writeDouble(payload.centerX);
      buffer.writeDouble(payload.centerY);
      buffer.writeDouble(payload.centerZ);
      buffer.writeInt(payload.duration);
      buffer.writeInt(payload.seed);
   }

   private static ChronoJudgementClockPayload read(ByteBuf buffer) {
      return new ChronoJudgementClockPayload(
         buffer.readInt(), buffer.readInt(), buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readInt(), buffer.readInt()
      );
   }

   public static void handle(ChronoJudgementClockPayload payload, PacketContext context) {
      context.queue(
         () -> ChronoJudgementClockClient.add(
            payload.bossId, payload.targetId, payload.centerX, payload.centerY, payload.centerZ, payload.duration, payload.seed
         )
      );
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
