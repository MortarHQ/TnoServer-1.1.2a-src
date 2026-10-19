package it.hurts.octostudios.reliquified_lenders_cataclysm.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record VoidVortexMotionPacket(int targetId, double x, double y, double z) implements CustomPacketPayload {
   public static final StreamCodec<ByteBuf, VoidVortexMotionPacket> STREAM_CODEC = StreamCodec.ofMember(
      VoidVortexMotionPacket::encode, VoidVortexMotionPacket::decode
   );
   public static final Type<VoidVortexMotionPacket> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("reliquified_lenders_cataclysm", "void_vortex_motion")
   );

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static VoidVortexMotionPacket decode(ByteBuf buf) {
      return new VoidVortexMotionPacket(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble());
   }

   public void encode(ByteBuf buf) {
      buf.writeInt(this.targetId);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
   }

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Player player = ctx.player();
         Level level = player.getCommandSenderWorld();
         if (level.getEntity(this.targetId) instanceof LivingEntity entity) {
            entity.setDeltaMovement(this.x, this.y, this.z);
         }
      });
   }
}
