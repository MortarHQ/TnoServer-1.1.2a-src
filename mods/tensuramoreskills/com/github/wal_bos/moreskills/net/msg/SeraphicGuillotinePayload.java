package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.SeraphicGuillotineClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record SeraphicGuillotinePayload(
   int bossId, int targetId, double centerX, double centerY, double centerZ, double dirX, double dirZ, int duration, int seed
) implements CustomPacketPayload {
   public static final Type<SeraphicGuillotinePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "seraphic_guillotine"));
   public static final StreamCodec<ByteBuf, SeraphicGuillotinePayload> STREAM_CODEC = StreamCodec.of(
      SeraphicGuillotinePayload::write, SeraphicGuillotinePayload::read
   );

   private static void write(ByteBuf buffer, SeraphicGuillotinePayload payload) {
      buffer.writeInt(payload.bossId);
      buffer.writeInt(payload.targetId);
      buffer.writeDouble(payload.centerX);
      buffer.writeDouble(payload.centerY);
      buffer.writeDouble(payload.centerZ);
      buffer.writeDouble(payload.dirX);
      buffer.writeDouble(payload.dirZ);
      buffer.writeInt(payload.duration);
      buffer.writeInt(payload.seed);
   }

   private static SeraphicGuillotinePayload read(ByteBuf buffer) {
      return new SeraphicGuillotinePayload(
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

   public static void handle(SeraphicGuillotinePayload payload, PacketContext context) {
      context.queue(
         () -> SeraphicGuillotineClient.add(
            payload.bossId, payload.targetId, payload.centerX, payload.centerY, payload.centerZ, payload.dirX, payload.dirZ, payload.duration, payload.seed
         )
      );
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
