package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.MichaelKeterVfxClient;
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

public record MichaelKeterVfxPayload(
   byte vfxType, int casterId, double x, double y, double z, double x2, double y2, double z2, float range, float power, int duration, long seed
) implements CustomPacketPayload {
   public static final byte CHARGE = 0;
   public static final byte BEAM = 1;
   public static final Type<MichaelKeterVfxPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "michael_keter_vfx"));
   public static final StreamCodec<FriendlyByteBuf, MichaelKeterVfxPayload> STREAM_CODEC = CustomPacketPayload.codec(
      MichaelKeterVfxPayload::encode, MichaelKeterVfxPayload::new
   );

   public MichaelKeterVfxPayload(FriendlyByteBuf buf) {
      this(
         buf.readByte(),
         buf.readInt(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readFloat(),
         buf.readFloat(),
         buf.readInt(),
         buf.readLong()
      );
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeByte(this.vfxType);
      buf.writeInt(this.casterId);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeDouble(this.x2);
      buf.writeDouble(this.y2);
      buf.writeDouble(this.z2);
      buf.writeFloat(this.range);
      buf.writeFloat(this.power);
      buf.writeInt(this.duration);
      buf.writeLong(this.seed);
   }

   public static void sendCharge(LivingEntity caster, int heldTicks, int duration) {
      if (caster.level() instanceof ServerLevel level) {
         Vec3 a = caster.getEyePosition();
         Vec3 b = a.add(caster.getLookAngle().normalize().scale(5.0));
         send(
            level,
            new MichaelKeterVfxPayload((byte)0, caster.getId(), a.x, a.y, a.z, b.x, b.y, b.z, 5.0F, heldTicks, duration, level.random.nextLong()),
            a,
            240.0
         );
      }
   }

   public static void sendBeam(LivingEntity caster, float range, int duration) {
      if (caster.level() instanceof ServerLevel level) {
         Vec3 a = caster.getEyePosition();
         Vec3 b = a.add(caster.getLookAngle().normalize().scale(range));
         send(
            level,
            new MichaelKeterVfxPayload((byte)1, caster.getId(), a.x, a.y, a.z, b.x, b.y, b.z, range, 1.0F, duration, level.random.nextLong()),
            a,
            Math.max(260.0, range * 18.0)
         );
      }
   }

   private static void send(ServerLevel level, MichaelKeterVfxPayload payload, Vec3 center, double range) {
      double r = range * range;

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(center.x, center.y, center.z) <= r) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   public static void handle(MichaelKeterVfxPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> MichaelKeterVfxClient.play(payload));
      }
   }

   public @NotNull Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
