package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.MichaelLataibBarrageVfxClient;
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

public record MichaelLataibBarrageVfxPayload(
   int casterId, int targetId, double x, double y, double z, double x2, double y2, double z2, float radius, int duration, long seed
) implements CustomPacketPayload {
   public static final Type<MichaelLataibBarrageVfxPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "michael_lataib_barrage_vfx")
   );
   public static final StreamCodec<FriendlyByteBuf, MichaelLataibBarrageVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      MichaelLataibBarrageVfxPayload::encode, MichaelLataibBarrageVfxPayload::new
   );

   public MichaelLataibBarrageVfxPayload(FriendlyByteBuf buf) {
      this(
         buf.readInt(),
         buf.readInt(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readFloat(),
         buf.readInt(),
         buf.readLong()
      );
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.casterId);
      buf.writeInt(this.targetId);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeDouble(this.x2);
      buf.writeDouble(this.y2);
      buf.writeDouble(this.z2);
      buf.writeFloat(this.radius);
      buf.writeInt(this.duration);
      buf.writeLong(this.seed);
   }

   public static void send(LivingEntity caster, LivingEntity target, int duration) {
      if (caster.level() instanceof ServerLevel level) {
         Vec3 var6 = caster.getEyePosition();
         Vec3 b = target.position().add(0.0, target.getBbHeight() * 0.62, 0.0);
         send(
            level,
            new MichaelLataibBarrageVfxPayload(caster.getId(), target.getId(), var6.x, var6.y, var6.z, b.x, b.y, b.z, 15.0F, duration, level.random.nextLong()),
            b,
            280.0
         );
      }
   }

   private static void send(ServerLevel level, MichaelLataibBarrageVfxPayload payload, Vec3 center, double range) {
      double r = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(center.x, center.y, center.z) <= r) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(MichaelLataibBarrageVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> MichaelLataibBarrageVfxClient.play(payload));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
