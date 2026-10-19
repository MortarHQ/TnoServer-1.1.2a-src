package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.MichaelShifuruMeteorVfxClient;
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

public record MichaelShifuruMeteorVfxPayload(
   byte vfxType, int casterId, double x, double y, double z, double x2, double y2, double z2, float radius, float power, int duration, long seed
) implements CustomPacketPayload {
   public static final byte WORMHOLE = 0;
   public static final byte METEOR = 1;
   public static final byte IMPACT = 2;
   public static final Type<MichaelShifuruMeteorVfxPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "michael_shifuru_meteor_vfx")
   );
   public static final StreamCodec<FriendlyByteBuf, MichaelShifuruMeteorVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      MichaelShifuruMeteorVfxPayload::encode, MichaelShifuruMeteorVfxPayload::new
   );

   public MichaelShifuruMeteorVfxPayload(FriendlyByteBuf buf) {
      this(
         buf.readByte(),
         buf.readInt(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readFloat(),
         buf.readFloat(),
         buf.readInt(),
         buf.readLong()
      );
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeByte(this.vfxType);
      buf.writeInt(this.casterId);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeDouble(this.x2);
      buf.writeDouble(this.y2);
      buf.writeDouble(this.z2);
      buf.writeFloat(this.radius);
      buf.writeFloat(this.power);
      buf.writeInt(this.duration);
      buf.writeLong(this.seed);
   }

   public static void sendWormhole(LivingEntity caster, int chargeTicks, int duration) {
      if (caster.level() instanceof ServerLevel level) {
         Vec3 var8 = caster.getLookAngle();
         Vec3 look = new Vec3(var8.x, 0.0, var8.z);
         if (look.lengthSqr() < 0.001) {
            look = new Vec3(0.0, 0.0, 1.0);
         } else {
            look = look.normalize();
         }

         Vec3 ground = caster.position().add(look.scale(24.0));
         Vec3 sky = caster.position().subtract(look.scale(190.0)).add(0.0, 102.0, 0.0);
         send(
            level,
            new MichaelShifuruMeteorVfxPayload(
               (byte)0,
               caster.getId(),
               ground.x,
               ground.y + caster.getBbHeight() * 0.5,
               ground.z,
               sky.x,
               sky.y,
               sky.z,
               150.0F,
               chargeTicks,
               duration,
               level.random.nextLong()
            ),
            caster.position(),
            8000.0
         );
      }
   }

   public static void sendMeteor(ServerLevel level, LivingEntity caster, Vec3 center, float radius, int duration, boolean mastered) {
      Vec3 rawLook = caster.getLookAngle();
      Vec3 look = new Vec3(rawLook.x, 0.0, rawLook.z);
      if (look.lengthSqr() < 0.001) {
         look = new Vec3(0.0, 0.0, 1.0);
      } else {
         look = look.normalize();
      }

      Vec3 sky = center.subtract(look.scale(mastered ? 320.0 : 280.0)).add(0.0, mastered ? 142.0 : 128.0, 0.0);
      send(
         level,
         new MichaelShifuruMeteorVfxPayload(
            (byte)1, caster.getId(), center.x, center.y, center.z, sky.x, sky.y, sky.z, radius, mastered ? 1.0F : 0.0F, duration, level.random.nextLong()
         ),
         caster.position(),
         8000.0
      );
   }

   public static void sendMeteor(ServerLevel level, Vec3 center, float radius, int duration, boolean mastered) {
      send(
         level,
         new MichaelShifuruMeteorVfxPayload(
            (byte)1, -1, center.x, center.y, center.z, center.x, center.y + 92.0, center.z, radius, mastered ? 1.0F : 0.0F, duration, level.random.nextLong()
         ),
         center,
         8000.0
      );
   }

   public static void sendImpact(ServerLevel level, Vec3 center, float radius, boolean big) {
      send(
         level,
         new MichaelShifuruMeteorVfxPayload(
            (byte)2, -1, center.x, center.y, center.z, center.x, center.y + 1.0, center.z, radius, big ? 1.0F : 0.0F, big ? 520 : 230, level.random.nextLong()
         ),
         center,
         8000.0
      );
   }

   private static void send(ServerLevel level, MichaelShifuruMeteorVfxPayload payload, Vec3 center, double range) {
      double r = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(center.x, center.y, center.z) <= r) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(MichaelShifuruMeteorVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> MichaelShifuruMeteorVfxClient.play(payload));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
