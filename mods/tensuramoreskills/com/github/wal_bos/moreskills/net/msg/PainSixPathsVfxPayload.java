package com.github.wal_bos.moreskills.net.msg;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.lang.reflect.Method;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record PainSixPathsVfxPayload(double x, double y, double z, float radius, int kind, float power, long seed) implements CustomPacketPayload {
   public static final Type<PainSixPathsVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_vfx"));
   public static final StreamCodec<FriendlyByteBuf, PainSixPathsVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      PainSixPathsVfxPayload::encode, PainSixPathsVfxPayload::new
   );

   public PainSixPathsVfxPayload(FriendlyByteBuf buf) {
      this(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readVarInt(), buf.readFloat(), buf.readLong());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeFloat(this.radius);
      buf.writeVarInt(this.kind);
      buf.writeFloat(this.power);
      buf.writeLong(this.seed);
   }

   public static void send(ServerLevel level, Vec3 pos, float radius, int kind, float power) {
      send(level, pos, radius, kind, power, level.random.nextLong());
   }

   public static void send(ServerLevel level, Vec3 pos, float radius, int kind, float power, long seed) {
      PainSixPathsVfxPayload payload = new PainSixPathsVfxPayload(pos.x, pos.y, pos.z, radius, kind, power, seed);
      double range = Math.max(240.0, radius * 8.0);
      double rangeSqr = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(pos.x, pos.y, pos.z) <= rangeSqr) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(PainSixPathsVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> {
            try {
               Class<?> client = Class.forName("com.github.wal_bos.moreskills.client.fx.PainSixPathsVfxClient");
               Method spawn = client.getMethod("spawn", double.class, double.class, double.class, float.class, int.class, float.class, long.class);
               spawn.invoke(null, payload.x(), payload.y(), payload.z(), payload.radius(), payload.kind(), payload.power(), payload.seed());
            } catch (ReflectiveOperationException var3) {
            }
         });
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
