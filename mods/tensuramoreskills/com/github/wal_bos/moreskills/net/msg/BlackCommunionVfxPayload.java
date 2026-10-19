package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.BlackCommunionVfxClient;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.List;
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

public record BlackCommunionVfxPayload(int progenitorId, float radius, long seed, int[] linkedIds) implements CustomPacketPayload {
   private static final int MAX_LINKED_IDS = 64;
   public static final Type<BlackCommunionVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "black_communion_vfx"));
   public static final StreamCodec<FriendlyByteBuf, BlackCommunionVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      BlackCommunionVfxPayload::encode, BlackCommunionVfxPayload::new
   );

   public BlackCommunionVfxPayload(FriendlyByteBuf buf) {
      this(buf.readVarInt(), buf.readFloat(), buf.readLong(), readIds(buf));
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeVarInt(this.progenitorId);
      buf.writeFloat(this.radius);
      buf.writeLong(this.seed);
      int count = Math.min(64, this.linkedIds == null ? 0 : this.linkedIds.length);
      buf.writeVarInt(count);

      for (int i = 0; i < count; i++) {
         buf.writeVarInt(this.linkedIds[i]);
      }
   }

   private static int[] readIds(FriendlyByteBuf buf) {
      int writtenSize = Math.max(0, buf.readVarInt());
      int keptSize = Math.min(64, writtenSize);
      int[] ids = new int[keptSize];

      for (int i = 0; i < writtenSize; i++) {
         int id = buf.readVarInt();
         if (i < keptSize) {
            ids[i] = id;
         }
      }

      return ids;
   }

   public static void send(ServerLevel level, LivingEntity progenitor, List<LivingEntity> linked, float radius) {
      if (level != null && progenitor != null && linked != null) {
         int max = Math.min(64, linked.size());
         int[] ids = new int[max];

         for (int i = 0; i < max; i++) {
            LivingEntity entity = linked.get(i);
            ids[i] = entity == null ? -1 : entity.getId();
         }

         BlackCommunionVfxPayload payload = new BlackCommunionVfxPayload(progenitor.getId(), radius, level.random.nextLong(), ids);
         Vec3 pos = progenitor.position();
         double range = Math.max(220.0, radius * 4.0);
         double rangeSqr = range * range;

         for (ServerPlayer player : level.players()) {
            if (player != null && !player.hasDisconnected() && player.distanceToSqr(pos.x, pos.y, pos.z) <= rangeSqr) {
               NetworkManager.sendToPlayer(player, payload);
            }
         }
      }
   }

   public static void handle(BlackCommunionVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> BlackCommunionVfxClient.spawn(payload.progenitorId(), payload.radius(), payload.seed(), payload.linkedIds()));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
