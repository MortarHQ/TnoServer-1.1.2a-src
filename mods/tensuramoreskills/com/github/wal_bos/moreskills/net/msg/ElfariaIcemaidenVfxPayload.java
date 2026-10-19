package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.ElfariaIcemaidenVfxClient;
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

public record ElfariaIcemaidenVfxPayload(
   byte vfxType, double x, double y, double z, double x2, double y2, double z2, float radius, float power, long seed, boolean main
) implements CustomPacketPayload {
   public static final byte MYRDAS_BARRIER = 0;
   public static final byte MYRDAS_BREAK = 1;
   public static final byte STELLAS_CHARGE = 2;
   public static final byte STELLAS_STRIKE = 3;
   public static final byte FRUZEL_CATHEDRAL = 4;
   public static final byte FRUZEL_SPIKE = 5;
   public static final byte GLACIA_PHOENIX = 6;
   public static final byte GLACIA_BURST = 7;
   public static final byte ICE_STEP = 8;
   public static final byte ARS_WEISS_BLOOM = 9;
   public static final byte FIELD_FREEZE = 10;
   public static final Type<ElfariaIcemaidenVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "elfaria_icemaiden_vfx"));
   public static final StreamCodec<FriendlyByteBuf, ElfariaIcemaidenVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      ElfariaIcemaidenVfxPayload::encode, ElfariaIcemaidenVfxPayload::new
   );

   public ElfariaIcemaidenVfxPayload(FriendlyByteBuf buf) {
      this(
         buf.readByte(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readFloat(),
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
      buf.writeFloat(this.power);
      buf.writeLong(this.seed);
      buf.writeBoolean(this.main);
   }

   public static void sendMyrdasBarrier(ServerLevel level, Vec3 center, float radius, int lifeTicks, boolean main) {
      send(
         level,
         new ElfariaIcemaidenVfxPayload(
            (byte)0, center.x, center.y, center.z, center.x, center.y + 3.2, center.z, radius, lifeTicks, level.random.nextLong(), main
         ),
         center,
         Math.max(96.0, radius * 24.0)
      );
   }

   public static void sendMyrdasBreak(ServerLevel level, Vec3 center, float radius, boolean main) {
      send(
         level,
         new ElfariaIcemaidenVfxPayload((byte)1, center.x, center.y, center.z, center.x, center.y + 2.5, center.z, radius, 1.0F, level.random.nextLong(), main),
         center,
         Math.max(96.0, radius * 20.0)
      );
   }

   public static void sendStellasCharge(ServerLevel level, Vec3 center, float radius, int count, boolean main) {
      send(
         level,
         new ElfariaIcemaidenVfxPayload(
            (byte)2, center.x, center.y, center.z, center.x, center.y + 18.0, center.z, radius, count, level.random.nextLong(), main
         ),
         center,
         Math.max(112.0, radius * 18.0)
      );
   }

   public static void sendStellasStrike(ServerLevel level, Vec3 top, Vec3 impact, float radius, boolean main) {
      Vec3 center = top.add(impact).scale(0.5);
      send(
         level,
         new ElfariaIcemaidenVfxPayload((byte)3, top.x, top.y, top.z, impact.x, impact.y, impact.z, radius, 1.0F, level.random.nextLong(), main),
         center,
         Math.max(240.0, top.distanceTo(impact) + radius * 150.0)
      );
   }

   public static void sendStellasImpact(ServerLevel level, Vec3 impact, float radius, boolean main) {
      Vec3 top = impact.add(0.0, 0.08, 0.0);
      send(
         level,
         new ElfariaIcemaidenVfxPayload((byte)3, top.x, top.y, top.z, impact.x, impact.y, impact.z, radius, 2.0F, level.random.nextLong(), main),
         impact,
         Math.max(240.0, radius * 180.0)
      );
   }

   public static void sendCathedral(ServerLevel level, Vec3 caster, Vec3 center, float radius, int lifeTicks, boolean main) {
      send(
         level,
         new ElfariaIcemaidenVfxPayload((byte)4, center.x, center.y, center.z, caster.x, caster.y, caster.z, radius, lifeTicks, level.random.nextLong(), main),
         center,
         Math.max(256.0, radius * 52.0)
      );
   }

   public static void sendFruzelSpike(ServerLevel level, Vec3 pos, float radius, boolean main) {
      send(
         level,
         new ElfariaIcemaidenVfxPayload((byte)5, pos.x, pos.y, pos.z, pos.x, pos.y + 6.0, pos.z, radius, 1.0F, level.random.nextLong(), main),
         pos,
         Math.max(128.0, radius * 42.0)
      );
   }

   public static void sendPhoenixDive(ServerLevel level, Vec3 start, Vec3 target, float radius, boolean main) {
      Vec3 center = start.add(target).scale(0.5);
      double distance = start.distanceTo(target);
      double visualExtra = Math.min(900.0, Math.max(220.0, distance * (main ? 4.25 : 1.75)));
      send(
         level,
         new ElfariaIcemaidenVfxPayload((byte)6, start.x, start.y, start.z, target.x, target.y, target.z, radius, 1.0F, level.random.nextLong(), main),
         center,
         Math.max(1024.0, distance + visualExtra + radius * 260.0)
      );
   }

   public static void sendGlaciaBurst(ServerLevel level, Vec3 target, float radius, boolean main) {
      float hugeRadius = Math.max(main ? 32.0F : 12.0F, radius);
      send(
         level,
         new ElfariaIcemaidenVfxPayload(
            (byte)7,
            target.x,
            target.y,
            target.z,
            target.x,
            target.y + Math.max(48.0, hugeRadius * 4.25),
            target.z,
            hugeRadius,
            1.0F,
            level.random.nextLong(),
            main
         ),
         target,
         Math.max(1200.0, hugeRadius * 280.0)
      );
   }

   public static void sendIceStep(ServerLevel level, Vec3 pos, Vec3 look, float radius, boolean main) {
      send(level, new ElfariaIcemaidenVfxPayload((byte)8, pos.x, pos.y, pos.z, look.x, look.y, look.z, radius, 1.0F, level.random.nextLong(), main), pos, 96.0);
   }

   public static void sendArsWeissBloom(ServerLevel level, Vec3 center, float radius, boolean main) {
      send(
         level,
         new ElfariaIcemaidenVfxPayload((byte)9, center.x, center.y, center.z, center.x, center.y + 3.0, center.z, radius, 1.0F, level.random.nextLong(), main),
         center,
         Math.max(128.0, radius * 32.0)
      );
   }

   public static void sendFieldFreeze(ServerLevel level, Vec3 center, float radius, boolean main) {
      send(
         level,
         new ElfariaIcemaidenVfxPayload((byte)10, center.x, center.y, center.z, center.x, center.y + 2.0, center.z, radius, 1.0F, level.random.nextLong(), main),
         center,
         Math.max(128.0, radius * 36.0)
      );
   }

   private static void send(ServerLevel level, ElfariaIcemaidenVfxPayload payload, Vec3 center, double range) {
      double rangeSqr = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(center.x, center.y, center.z) <= rangeSqr) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(ElfariaIcemaidenVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> ElfariaIcemaidenVfxClient.spawn(payload));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
