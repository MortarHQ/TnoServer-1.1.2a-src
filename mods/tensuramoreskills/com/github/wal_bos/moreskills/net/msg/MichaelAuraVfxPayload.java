package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.MichaelAuraVfxClient;
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

public record MichaelAuraVfxPayload(int casterId, double x, double y, double z, float radius, int duration, long seed) implements CustomPacketPayload {
   public static final Type<MichaelAuraVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "michael_aura_vfx"));
   public static final StreamCodec<FriendlyByteBuf, MichaelAuraVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      MichaelAuraVfxPayload::encode, MichaelAuraVfxPayload::new
   );

   public MichaelAuraVfxPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readInt(), buf.readLong());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.casterId);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeFloat(this.radius);
      buf.writeInt(this.duration);
      buf.writeLong(this.seed);
   }

   public static void send(LivingEntity entity, float radius, int duration) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var5 = entity.position().add(0.0, entity.getBbHeight() * 0.55, 0.0);
         send(
            level,
            new MichaelAuraVfxPayload(entity.getId(), var5.x, var5.y, var5.z, radius, duration, level.random.nextLong()),
            var5,
            Math.max(160.0, radius * 18.0)
         );
      }
   }

   public static void sendTrail(LivingEntity entity) {
      send(entity, 1.8F, 72000);
   }

   public static void stop(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var3 = entity.position().add(0.0, entity.getBbHeight() * 0.55, 0.0);
         send(level, new MichaelAuraVfxPayload(entity.getId(), var3.x, var3.y, var3.z, 0.0F, 0, level.random.nextLong()), var3, 192.0);
      }
   }

   private static void send(ServerLevel level, MichaelAuraVfxPayload payload, Vec3 center, double range) {
      double r = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(center.x, center.y, center.z) <= r) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(MichaelAuraVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> MichaelAuraVfxClient.play(payload));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
