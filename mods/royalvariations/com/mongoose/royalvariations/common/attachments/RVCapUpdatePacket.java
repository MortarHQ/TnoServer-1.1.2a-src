package com.mongoose.royalvariations.common.attachments;

import com.mongoose.royalvariations.RoyalVariations;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class RVCapUpdatePacket implements CustomPacketPayload {
   public static final Type<RVCapUpdatePacket> TYPE = new Type(RoyalVariations.location("capacity_update"));
   public static final StreamCodec<RegistryFriendlyByteBuf, RVCapUpdatePacket> STREAM_CODEC = CustomPacketPayload.codec(
      RVCapUpdatePacket::encode, RVCapUpdatePacket::new
   );
   private final int entityID;
   private final int flash;
   private final int marked;
   private final int gaze;

   public RVCapUpdatePacket(int id, int flash, int marked, int gaze) {
      this.entityID = id;
      this.flash = flash;
      this.marked = marked;
      this.gaze = gaze;
   }

   public RVCapUpdatePacket(LivingEntity living) {
      this.entityID = living.getId();
      this.flash = RVCapHelper.getFlashTick(living);
      this.marked = RVCapHelper.getMarkedTick(living);
      this.gaze = RVCapHelper.getGazeTick(living);
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityID);
      buffer.writeInt(this.flash);
      buffer.writeInt(this.marked);
      buffer.writeInt(this.gaze);
   }

   public RVCapUpdatePacket(FriendlyByteBuf buffer) {
      this(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void consume(RVCapUpdatePacket packet, IPayloadContext ctx) {
      ctx.enqueueWork(
         () -> {
            if (RoyalVariations.PROXY.getLevel() instanceof ClientLevel clientLevel
               && clientLevel.getEntity(packet.entityID) instanceof LivingEntity livingEntity) {
               RVCapHelper.setFlashTick(livingEntity, packet.flash);
               RVCapHelper.setMarkedTick(livingEntity, packet.marked);
               RVCapHelper.setGazeTick(livingEntity, packet.gaze);
            }
         }
      );
   }
}
