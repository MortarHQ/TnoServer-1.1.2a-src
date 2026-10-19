package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.MichaelSegvaVfxClient;
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

public record MichaelSegvaVfxPayload(
   byte vfxType, int casterId, int targetId, double x, double y, double z, double x2, double y2, double z2, int marks, int duration, long seed
) implements CustomPacketPayload {
   public static final byte MARK = 0;
   public static final byte LOCK = 1;
   public static final byte CLEAR = 2;
   public static final Type<MichaelSegvaVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "michael_segva_vfx"));
   public static final StreamCodec<FriendlyByteBuf, MichaelSegvaVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      MichaelSegvaVfxPayload::encode, MichaelSegvaVfxPayload::new
   );

   public MichaelSegvaVfxPayload(FriendlyByteBuf buf) {
      this(
         buf.readByte(),
         buf.readInt(),
         buf.readInt(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readInt(),
         buf.readInt(),
         buf.readLong()
      );
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeByte(this.vfxType);
      buf.writeInt(this.casterId);
      buf.writeInt(this.targetId);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeDouble(this.x2);
      buf.writeDouble(this.y2);
      buf.writeDouble(this.z2);
      buf.writeInt(this.marks);
      buf.writeInt(this.duration);
      buf.writeLong(this.seed);
   }

   public static void sendMark(LivingEntity caster, LivingEntity target, int marks, int duration) {
      if (caster.level() instanceof ServerLevel level) {
         Vec3 var7 = caster.getEyePosition();
         Vec3 b = target.position().add(0.0, target.getBbHeight() * 0.58, 0.0);
         send(
            level,
            new MichaelSegvaVfxPayload((byte)0, caster.getId(), target.getId(), var7.x, var7.y, var7.z, b.x, b.y, b.z, marks, duration, level.random.nextLong()),
            b,
            220.0
         );
      }
   }

   public static void sendMark(LivingEntity caster, LivingEntity target, int marks) {
      sendMark(caster, target, marks, 74);
   }

   public static void sendLock(LivingEntity caster, LivingEntity target, int duration) {
      if (caster.level() instanceof ServerLevel level) {
         Vec3 var6 = caster.getEyePosition();
         Vec3 b = target.position().add(0.0, target.getBbHeight() * 0.54, 0.0);
         send(
            level,
            new MichaelSegvaVfxPayload((byte)1, caster.getId(), target.getId(), var6.x, var6.y, var6.z, b.x, b.y, b.z, 3, duration, level.random.nextLong()),
            b,
            240.0
         );
      }
   }

   public static void sendClear(LivingEntity target) {
      if (target.level() instanceof ServerLevel level) {
         Vec3 var3 = target.position().add(0.0, target.getBbHeight() * 0.58, 0.0);
         send(
            level,
            new MichaelSegvaVfxPayload((byte)2, -1, target.getId(), var3.x, var3.y, var3.z, var3.x, var3.y, var3.z, 0, 1, level.random.nextLong()),
            var3,
            220.0
         );
      }
   }

   private static void send(ServerLevel level, MichaelSegvaVfxPayload payload, Vec3 center, double range) {
      double r = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(center.x, center.y, center.z) <= r) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(MichaelSegvaVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> MichaelSegvaVfxClient.play(payload));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
