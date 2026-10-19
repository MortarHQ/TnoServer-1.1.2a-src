package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.SatellaVfxClient;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record SatellaVfxPayload(
   byte vfxType, double x, double y, double z, double x2, double y2, double z2, int count, int life, boolean mastered, long seed, int casterId, int targetId
) implements CustomPacketPayload {
   public static final byte HAND = 0;
   public static final byte TENDRILS = 1;
   public static final byte SPREAD = 2;
   public static final Type<SatellaVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "satella_vfx"));
   public static final StreamCodec<FriendlyByteBuf, SatellaVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      SatellaVfxPayload::encode, SatellaVfxPayload::new
   );

   public SatellaVfxPayload(FriendlyByteBuf buf) {
      this(
         buf.readByte(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readInt(),
         buf.readInt(),
         buf.readBoolean(),
         buf.readLong(),
         buf.readInt(),
         buf.readInt()
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
      buf.writeInt(this.count);
      buf.writeInt(this.life);
      buf.writeBoolean(this.mastered);
      buf.writeLong(this.seed);
      buf.writeInt(this.casterId);
      buf.writeInt(this.targetId);
   }

   public static void sendHand(ServerLevel level, LivingEntity entity, int life, boolean mastered) {
      send(
         level,
         new SatellaVfxPayload(
            (byte)0,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.55,
            entity.getZ(),
            entity.getId(),
            0.0,
            0.0,
            1,
            life,
            mastered,
            level.random.nextLong(),
            entity.getId(),
            -1
         ),
         entity.position(),
         96.0
      );
   }

   public static void sendTendrils(ServerLevel level, Vec3 start, Vec3 end, int count, int life, boolean mastered) {
      Vec3 center = start.add(end).scale(0.5);
      send(
         level,
         new SatellaVfxPayload((byte)1, start.x, start.y, start.z, end.x, end.y, end.z, count, life, mastered, level.random.nextLong(), -1, -1),
         center,
         Math.max(128.0, start.distanceTo(end) + 64.0)
      );
   }

   public static void sendTendrilsTracking(
      ServerLevel level, LivingEntity caster, LivingEntity target, Vec3 start, Vec3 end, int count, int life, boolean mastered
   ) {
      Vec3 center = start.add(end).scale(0.5);
      send(
         level,
         new SatellaVfxPayload(
            (byte)1, start.x, start.y, start.z, end.x, end.y, end.z, count, life, mastered, level.random.nextLong(), caster.getId(), target.getId()
         ),
         center,
         Math.max(160.0, start.distanceTo(end) + 96.0)
      );
   }

   public static void sendSpread(ServerLevel level, Vec3 center, float radius, int life, boolean mastered) {
      send(
         level,
         new SatellaVfxPayload((byte)2, center.x, center.y + 0.05, center.z, radius, 0.0, 0.0, 1, life, mastered, level.random.nextLong(), -1, -1),
         center,
         Math.max(160.0, radius * 10.0)
      );
   }

   private static void send(ServerLevel level, SatellaVfxPayload payload, Vec3 center, double range) {
      double rangeSqr = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(center.x, center.y, center.z) <= rangeSqr) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(SatellaVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(
            () -> {
               if (payload.vfxType() == 0) {
                  SatellaVfxClient.spawnHand(payload.x(), payload.y(), payload.z(), payload.casterId(), payload.life(), payload.mastered(), payload.seed());
               } else if (payload.vfxType() == 1) {
                  SatellaVfxClient.spawnTendrils(
                     new Vec3(payload.x(), payload.y(), payload.z()),
                     new Vec3(payload.x2(), payload.y2(), payload.z2()),
                     payload.casterId(),
                     payload.targetId(),
                     payload.count(),
                     payload.life(),
                     payload.mastered(),
                     payload.seed()
                  );
               } else {
                  SatellaVfxClient.spawnSpread(
                     new Vec3(payload.x(), payload.y(), payload.z()), (float)payload.x2(), payload.life(), payload.mastered(), payload.seed()
                  );
               }
            }
         );
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
