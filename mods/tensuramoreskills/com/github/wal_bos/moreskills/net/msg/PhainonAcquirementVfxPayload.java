package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.net.client.ClientPayloadExecutor;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public record PhainonAcquirementVfxPayload(int entityId, UUID uuid, boolean start) implements CustomPacketPayload {
   public static final Type<PhainonAcquirementVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "phainon_acquirement_vfx"));
   public static final StreamCodec<FriendlyByteBuf, PhainonAcquirementVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      PhainonAcquirementVfxPayload::encode, PhainonAcquirementVfxPayload::new
   );
   private static final double DEFAULT_BROADCAST_RANGE = 256.0;

   public PhainonAcquirementVfxPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readUUID(), buf.readBoolean());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeUUID(this.uuid);
      buf.writeBoolean(this.start);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> ClientPayloadExecutor.phainonAcquirementVfx(this));
      }
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static UUID broadcastStart(Entity entity) {
      UUID effectId = UUID.randomUUID();
      broadcast(entity, effectId, true, 256.0);
      return effectId;
   }

   public static void broadcastStop(Entity entity, UUID effectId) {
      broadcast(entity, effectId, false, 256.0);
   }

   public static void broadcast(Entity entity, UUID effectId, boolean start) {
      broadcast(entity, effectId, start, 256.0);
   }

   public static void broadcast(Entity entity, UUID effectId, boolean start, double range) {
      if (entity != null && !entity.level().isClientSide()) {
         if (entity.level() instanceof ServerLevel serverLevel) {
            if (effectId == null) {
               effectId = UUID.randomUUID();
            }

            double rangeSqr = range * range;
            PhainonAcquirementVfxPayload payload = new PhainonAcquirementVfxPayload(entity.getId(), effectId, start);

            for (ServerPlayer player : serverLevel.players()) {
               if (player != null && !player.hasDisconnected() && player.distanceToSqr(entity) <= rangeSqr) {
                  NetworkManager.sendToPlayer(player, payload);
               }
            }
         }
      }
   }
}
