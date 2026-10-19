package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.WitchOfEnvySatellaVfxClient;
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

public record WitchOfEnvySatellaVfxPayload(
   byte vfxType, double x, double y, double z, double x2, double y2, double z2, int casterId, int targetId, int count, int life, boolean mastered, long seed
) implements CustomPacketPayload {
   public static final byte TENDRILS = 0;
   public static final byte SHADOW_HAND = 1;
   public static final byte SHADOW_HANDS = 2;
   public static final byte AUTHORITY = 3;
   public static final byte CHECKPOINT = 4;
   public static final Type<WitchOfEnvySatellaVfxPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "witch_of_envy_satella_vfx")
   );
   public static final StreamCodec<FriendlyByteBuf, WitchOfEnvySatellaVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      WitchOfEnvySatellaVfxPayload::encode, WitchOfEnvySatellaVfxPayload::new
   );

   public WitchOfEnvySatellaVfxPayload(FriendlyByteBuf buf) {
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
         buf.readInt(),
         buf.readInt(),
         buf.readBoolean(),
         buf.readLong()
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
      buf.writeInt(this.casterId);
      buf.writeInt(this.targetId);
      buf.writeInt(this.count);
      buf.writeInt(this.life);
      buf.writeBoolean(this.mastered);
      buf.writeLong(this.seed);
   }

   public static void sendTendrils(ServerLevel level, LivingEntity caster, LivingEntity target, int count, int life, boolean mastered) {
      Vec3 start = caster.getEyePosition().add(caster.getLookAngle().scale(0.8));
      Vec3 end = target.getEyePosition().add(0.0, target.getBbHeight() * 0.16, 0.0);
      Vec3 center = start.add(end).scale(0.5);
      send(
         level,
         new WitchOfEnvySatellaVfxPayload(
            (byte)0, start.x, start.y, start.z, end.x, end.y, end.z, caster.getId(), target.getId(), count, life, mastered, level.random.nextLong()
         ),
         center,
         180.0
      );
   }

   public static void sendShadowHand(ServerLevel level, LivingEntity caster, LivingEntity target, int life, boolean mastered) {
      Vec3 start = caster.getEyePosition().add(caster.getLookAngle().scale(0.55));
      Vec3 end = target.getEyePosition().add(0.0, target.getBbHeight() * 0.12, 0.0);
      Vec3 center = start.add(end).scale(0.5);
      send(
         level,
         new WitchOfEnvySatellaVfxPayload(
            (byte)1, start.x, start.y, start.z, end.x, end.y, end.z, caster.getId(), target.getId(), 1, life, mastered, level.random.nextLong()
         ),
         center,
         150.0
      );
   }

   public static void sendShadowHands(ServerLevel level, LivingEntity caster, LivingEntity target, int count, int life, boolean mastered) {
      Vec3 start = caster.getEyePosition().add(caster.getLookAngle().scale(0.55));
      Vec3 end = target.getEyePosition().add(0.0, target.getBbHeight() * 0.12, 0.0);
      Vec3 center = start.add(end).scale(0.5);
      send(
         level,
         new WitchOfEnvySatellaVfxPayload(
            (byte)2, start.x, start.y, start.z, end.x, end.y, end.z, caster.getId(), target.getId(), count, life, mastered, level.random.nextLong()
         ),
         center,
         170.0
      );
   }

   public static void sendAuthority(ServerLevel level, LivingEntity caster, LivingEntity target, int life, boolean mastered) {
      Vec3 start = caster.getEyePosition();
      Vec3 end = target.getEyePosition();
      Vec3 center = start.add(end).scale(0.5);
      send(
         level,
         new WitchOfEnvySatellaVfxPayload(
            (byte)3, start.x, start.y, start.z, end.x, end.y, end.z, caster.getId(), target.getId(), 1, life, mastered, level.random.nextLong()
         ),
         center,
         160.0
      );
   }

   public static void sendCheckpoint(ServerLevel level, LivingEntity target, int life, boolean mastered) {
      Vec3 pos = target.position().add(0.0, target.getBbHeight() * 0.45, 0.0);
      send(
         level,
         new WitchOfEnvySatellaVfxPayload((byte)4, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0, -1, target.getId(), 1, life, mastered, level.random.nextLong()),
         pos,
         128.0
      );
   }

   private static void send(ServerLevel level, WitchOfEnvySatellaVfxPayload payload, Vec3 center, double range) {
      double rangeSqr = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(center.x, center.y, center.z) <= rangeSqr) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(WitchOfEnvySatellaVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(
            () -> {
               Vec3 start = new Vec3(payload.x(), payload.y(), payload.z());
               Vec3 end = new Vec3(payload.x2(), payload.y2(), payload.z2());
               if (payload.vfxType() == 0) {
                  WitchOfEnvySatellaVfxClient.spawnTendrils(
                     start, end, payload.casterId(), payload.targetId(), payload.count(), payload.life(), payload.mastered(), payload.seed()
                  );
               } else if (payload.vfxType() == 1) {
                  WitchOfEnvySatellaVfxClient.spawnShadowHand(
                     start, end, payload.casterId(), payload.targetId(), payload.life(), payload.mastered(), payload.seed()
                  );
               } else if (payload.vfxType() == 2) {
                  WitchOfEnvySatellaVfxClient.spawnShadowHands(
                     start, end, payload.casterId(), payload.targetId(), payload.count(), payload.life(), payload.mastered(), payload.seed()
                  );
               } else if (payload.vfxType() == 3) {
                  WitchOfEnvySatellaVfxClient.spawnAuthority(
                     start, end, payload.casterId(), payload.targetId(), payload.life(), payload.mastered(), payload.seed()
                  );
               } else {
                  WitchOfEnvySatellaVfxClient.spawnCheckpoint(start, payload.targetId(), payload.life(), payload.mastered(), payload.seed());
               }
            }
         );
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
