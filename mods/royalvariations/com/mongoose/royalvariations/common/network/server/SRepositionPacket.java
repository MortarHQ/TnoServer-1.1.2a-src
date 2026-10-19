package com.mongoose.royalvariations.common.network.server;

import com.mongoose.royalvariations.RoyalVariations;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SRepositionPacket implements CustomPacketPayload {
   public static final Type<SRepositionPacket> TYPE = new Type(RoyalVariations.location("reposition"));
   public static final StreamCodec<RegistryFriendlyByteBuf, SRepositionPacket> STREAM_CODEC = CustomPacketPayload.codec(
      SRepositionPacket::encode, SRepositionPacket::new
   );
   public int mob;
   public double x;
   public double y;
   public double z;

   public SRepositionPacket(int id, double x, double y, double z) {
      this.mob = id;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.mob);
      buffer.writeDouble(this.x);
      buffer.writeDouble(this.y);
      buffer.writeDouble(this.z);
   }

   public SRepositionPacket(FriendlyByteBuf buffer) {
      this(buffer.readInt(), buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void consume(SRepositionPacket packet, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         if (RoyalVariations.PROXY.getLevel() instanceof ClientLevel clientWorld) {
            Entity entity = clientWorld.getEntity(packet.mob);
            if (entity != null) {
               entity.setPos(packet.x, packet.y, packet.z);
               entity.xo = packet.x;
               entity.yo = packet.y;
               entity.zo = packet.z;
               entity.xOld = packet.x;
               entity.yOld = packet.y;
               entity.zOld = packet.z;
            }
         }
      });
   }
}
