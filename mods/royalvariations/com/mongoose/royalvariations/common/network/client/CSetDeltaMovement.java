package com.mongoose.royalvariations.common.network.client;

import com.mongoose.royalvariations.RoyalVariations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CSetDeltaMovement implements CustomPacketPayload {
   public static final Type<CSetDeltaMovement> TYPE = new Type(RoyalVariations.location("delta_movement"));
   public static final StreamCodec<RegistryFriendlyByteBuf, CSetDeltaMovement> STREAM_CODEC = CustomPacketPayload.codec(
      CSetDeltaMovement::encode, CSetDeltaMovement::new
   );
   public int mob;
   public double x;
   public double y;
   public double z;

   public CSetDeltaMovement(int id, double x, double y, double z) {
      this.mob = id;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public CSetDeltaMovement(FriendlyByteBuf buffer) {
      this(buffer.readInt(), buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.mob);
      buffer.writeDouble(this.x);
      buffer.writeDouble(this.y);
      buffer.writeDouble(this.z);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void consume(CSetDeltaMovement packet, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         if (ctx.player() instanceof ServerPlayer serverPlayer) {
            Entity entity = serverPlayer.level().getEntity(packet.mob);
            if (entity != null) {
               entity.setDeltaMovement(packet.x, packet.y, packet.z);
               entity.hasImpulse = true;
            }
         }
      });
   }
}
