package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.AlbisVinaVfxClient;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record AlbisVinaVfxPayload(byte vfxType, double x, double y, double z, double x2, double y2, double z2, float radius, long seed, boolean main)
   implements CustomPacketPayload {
   public static final byte FIELD_IMPACT = 0;
   public static final byte BEAM = 1;
   public static final byte SPIKE_IMPACT = 2;
   public static final Type<AlbisVinaVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "albis_vina_vfx"));
   public static final StreamCodec<FriendlyByteBuf, AlbisVinaVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      AlbisVinaVfxPayload::encode, AlbisVinaVfxPayload::new
   );

   public AlbisVinaVfxPayload(FriendlyByteBuf buf) {
      this(
         buf.readByte(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readFloat(),
         buf.readLong(),
         buf.readBoolean()
      );
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeByte(this.vfxType);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeDouble(this.x2);
      buf.writeDouble(this.y2);
      buf.writeDouble(this.z2);
      buf.writeFloat(this.radius);
      buf.writeLong(this.seed);
      buf.writeBoolean(this.main);
   }

   public static void sendFieldImpact(ServerLevel level, Vec3 pos, float radius, boolean main) {
      send(
         level,
         new AlbisVinaVfxPayload((byte)0, pos.x, pos.y, pos.z, pos.x, pos.y, pos.z, radius, level.random.nextLong(), main),
         pos,
         Math.max(96.0, radius * 28.0)
      );
   }

   public static void sendSpikeImpact(ServerLevel level, Vec3 pos, float radius, boolean main) {
      send(
         level,
         new AlbisVinaVfxPayload((byte)2, pos.x, pos.y, pos.z, pos.x, pos.y, pos.z, radius, level.random.nextLong(), main),
         pos,
         Math.max(80.0, radius * 22.0)
      );
   }

   public static void sendBeam(ServerLevel level, Vec3 start, Vec3 end, float radius, boolean main) {
      Vec3 center = start.add(end).scale(0.5);
      double range = Math.max(128.0, start.distanceTo(end) + 48.0);
      send(level, new AlbisVinaVfxPayload((byte)1, start.x, start.y, start.z, end.x, end.y, end.z, radius, level.random.nextLong(), main), center, range);
   }

   private static void send(ServerLevel level, AlbisVinaVfxPayload payload, Vec3 center, double range) {
      double rangeSqr = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(center.x, center.y, center.z) <= rangeSqr) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(AlbisVinaVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(
            () -> {
               if (payload.vfxType() == 1) {
                  AlbisVinaVfxClient.spawnBeam(
                     payload.x(), payload.y(), payload.z(), payload.x2(), payload.y2(), payload.z2(), payload.radius(), payload.seed(), payload.main()
                  );
               } else if (payload.vfxType() == 2) {
                  AlbisVinaVfxClient.spawnSpikeImpact(payload.x(), payload.y(), payload.z(), payload.radius(), payload.seed(), payload.main());
               } else {
                  AlbisVinaVfxClient.spawnFieldImpact(payload.x(), payload.y(), payload.z(), payload.radius(), payload.seed(), payload.main());
               }
            }
         );
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
