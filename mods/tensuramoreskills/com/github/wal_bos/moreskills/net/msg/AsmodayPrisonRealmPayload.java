package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.AsmodayPrisonRealmClient;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record AsmodayPrisonRealmPayload(
   int id, int ownerId, int targetId, double centerX, double centerY, double centerZ, double size, int duration, int seed, int phase, boolean mastered
) implements CustomPacketPayload {
   public static final Type<AsmodayPrisonRealmPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "asmoday_prison_realm"));
   public static final StreamCodec<ByteBuf, AsmodayPrisonRealmPayload> STREAM_CODEC = new StreamCodec<ByteBuf, AsmodayPrisonRealmPayload>() {
      public AsmodayPrisonRealmPayload decode(ByteBuf buffer) {
         return new AsmodayPrisonRealmPayload(
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Double)ByteBufCodecs.DOUBLE.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Integer)ByteBufCodecs.INT.decode(buffer),
            (Boolean)ByteBufCodecs.BOOL.decode(buffer)
         );
      }

      public void encode(ByteBuf buffer, AsmodayPrisonRealmPayload payload) {
         ByteBufCodecs.INT.encode(buffer, payload.id());
         ByteBufCodecs.INT.encode(buffer, payload.ownerId());
         ByteBufCodecs.INT.encode(buffer, payload.targetId());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.centerX());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.centerY());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.centerZ());
         ByteBufCodecs.DOUBLE.encode(buffer, payload.size());
         ByteBufCodecs.INT.encode(buffer, payload.duration());
         ByteBufCodecs.INT.encode(buffer, payload.seed());
         ByteBufCodecs.INT.encode(buffer, payload.phase());
         ByteBufCodecs.BOOL.encode(buffer, payload.mastered());
      }
   };

   public static void handle(AsmodayPrisonRealmPayload payload, PacketContext context) {
      context.queue(
         () -> AsmodayPrisonRealmClient.play(
            payload.id(),
            payload.ownerId(),
            payload.targetId(),
            payload.centerX(),
            payload.centerY(),
            payload.centerZ(),
            payload.size(),
            payload.duration(),
            payload.seed(),
            payload.phase(),
            payload.mastered()
         )
      );
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
