package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.MichaelHalberdShotVfxClient;
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

public record MichaelHalberdShotVfxPayload(int casterId, double x, double y, double z, double x2, double y2, double z2, float range, int duration, long seed)
   implements CustomPacketPayload {
   public static final Type<MichaelHalberdShotVfxPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "michael_halberd_shot_vfx")
   );
   public static final StreamCodec<FriendlyByteBuf, MichaelHalberdShotVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      MichaelHalberdShotVfxPayload::encode, MichaelHalberdShotVfxPayload::new
   );

   public MichaelHalberdShotVfxPayload(FriendlyByteBuf buf) {
      this(
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
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeDouble(this.x2);
      buf.writeDouble(this.y2);
      buf.writeDouble(this.z2);
      buf.writeFloat(this.range);
      buf.writeInt(this.duration);
      buf.writeLong(this.seed);
   }

   public static void send(LivingEntity caster, float range, int duration) {
      if (caster.level() instanceof ServerLevel level) {
         Vec3 a = caster.getEyePosition();
         Vec3 b = a.add(caster.getLookAngle().normalize().scale(range));
         send(
            level,
            new MichaelHalberdShotVfxPayload(caster.getId(), a.x, a.y, a.z, b.x, b.y, b.z, range, duration, level.random.nextLong()),
            a,
            Math.max(180.0, range * 18.0)
         );
      }
   }

   private static void send(ServerLevel level, MichaelHalberdShotVfxPayload payload, Vec3 center, double range) {
      double r = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(center.x, center.y, center.z) <= r) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(MichaelHalberdShotVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> MichaelHalberdShotVfxClient.play(payload));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
