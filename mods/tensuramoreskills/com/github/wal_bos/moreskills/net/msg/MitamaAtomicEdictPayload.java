package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.MitamaAtomicEdictVfxClient;
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

public record MitamaAtomicEdictPayload(int casterId, double x, double y, double z, float radius, long seed, int durationTicks, int releaseTick)
   implements CustomPacketPayload {
   public static final Type<MitamaAtomicEdictPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mitama_atomic_edict_vfx"));
   public static final StreamCodec<FriendlyByteBuf, MitamaAtomicEdictPayload> STREAM_CODEC = CustomPacketPayload.codec(
      MitamaAtomicEdictPayload::encode, MitamaAtomicEdictPayload::new
   );

   public MitamaAtomicEdictPayload(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readLong(), buf.readInt(), buf.readInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.casterId);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeFloat(this.radius);
      buf.writeLong(this.seed);
      buf.writeInt(this.durationTicks);
      buf.writeInt(this.releaseTick);
   }

   public static void send(ServerLevel level, LivingEntity caster, Vec3 pos, float radius, int durationTicks, int releaseTick) {
      long seed = level.random.nextLong();
      MitamaAtomicEdictPayload payload = new MitamaAtomicEdictPayload(caster.getId(), pos.x, pos.y, pos.z, radius, seed, durationTicks, releaseTick);
      double range = Math.max(220.0, radius * 4.0);
      double rangeSqr = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(pos.x, pos.y, pos.z) <= rangeSqr) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(MitamaAtomicEdictPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(
            () -> MitamaAtomicEdictVfxClient.spawn(
               payload.casterId(), payload.x(), payload.y(), payload.z(), payload.radius(), payload.seed(), payload.durationTicks(), payload.releaseTick()
            )
         );
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
