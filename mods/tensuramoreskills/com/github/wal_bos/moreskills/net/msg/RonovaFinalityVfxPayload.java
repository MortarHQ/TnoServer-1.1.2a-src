package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.RonovaFinalityVfxClient;
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

public record RonovaFinalityVfxPayload(double x, double y, double z, float radius, long seed, boolean passive) implements CustomPacketPayload {
   public static final Type<RonovaFinalityVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "ronova_finality_vfx"));
   public static final StreamCodec<FriendlyByteBuf, RonovaFinalityVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      RonovaFinalityVfxPayload::encode, RonovaFinalityVfxPayload::new
   );

   public RonovaFinalityVfxPayload(FriendlyByteBuf buf) {
      this(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readLong(), buf.readBoolean());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeFloat(this.radius);
      buf.writeLong(this.seed);
      buf.writeBoolean(this.passive);
   }

   public static void send(ServerLevel level, Vec3 pos, float radius, boolean passive) {
      long seed = level.random.nextLong();
      RonovaFinalityVfxPayload payload = new RonovaFinalityVfxPayload(pos.x, pos.y, pos.z, radius, seed, passive);
      double range = Math.max(160.0, radius * 3.0);
      double rangeSqr = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(pos.x, pos.y, pos.z) <= rangeSqr) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(RonovaFinalityVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> RonovaFinalityVfxClient.spawn(payload.x(), payload.y(), payload.z(), payload.radius(), payload.seed(), payload.passive()));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
