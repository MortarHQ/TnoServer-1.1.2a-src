package it.hurts.sskirillss.relics.network.packets.sync;

import io.netty.buffer.ByteBuf;
import it.hurts.sskirillss.relics.entities.misc.ITargetableEntity;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class S2CEntityTargetPacket implements CustomPacketPayload {
   private final int sourceId;
   private final int targetId;
   public static final Type<S2CEntityTargetPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("relics", "entity_target"));
   public static final StreamCodec<ByteBuf, S2CEntityTargetPacket> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, S2CEntityTargetPacket::getTargetId, ByteBufCodecs.INT, S2CEntityTargetPacket::getSourceId, S2CEntityTargetPacket::new
   );

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Level level = ctx.player().getCommandSenderWorld();
         if (level.getEntity(this.sourceId) instanceof ITargetableEntity source && level.getEntity(this.targetId) instanceof LivingEntity target) {
            source.setTarget(target);
         }
      });
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public int getSourceId() {
      return this.sourceId;
   }

   public int getTargetId() {
      return this.targetId;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof S2CEntityTargetPacket other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         return this.getSourceId() != other.getSourceId() ? false : this.getTargetId() == other.getTargetId();
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof S2CEntityTargetPacket;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getSourceId();
      return result * 59 + this.getTargetId();
   }

   @Override
   public String toString() {
      return "S2CEntityTargetPacket(sourceId=" + this.getSourceId() + ", targetId=" + this.getTargetId() + ")";
   }

   public S2CEntityTargetPacket(int sourceId, int targetId) {
      this.sourceId = sourceId;
      this.targetId = targetId;
   }
}
