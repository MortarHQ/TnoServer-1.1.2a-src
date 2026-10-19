package it.hurts.sskirillss.relics.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PacketPlayerMotion implements CustomPacketPayload {
   private final double motionX;
   private final double motionY;
   private final double motionZ;
   public static final Type<PacketPlayerMotion> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("relics", "player_motion"));
   public static final StreamCodec<ByteBuf, PacketPlayerMotion> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.DOUBLE,
      PacketPlayerMotion::getMotionX,
      ByteBufCodecs.DOUBLE,
      PacketPlayerMotion::getMotionY,
      ByteBufCodecs.DOUBLE,
      PacketPlayerMotion::getMotionZ,
      PacketPlayerMotion::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Vec3 motion = new Vec3(this.motionX, this.motionY, this.motionZ);
         Minecraft.getInstance().player.setDeltaMovement(motion);
      });
   }

   public double getMotionX() {
      return this.motionX;
   }

   public double getMotionY() {
      return this.motionY;
   }

   public double getMotionZ() {
      return this.motionZ;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PacketPlayerMotion other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (Double.compare(this.getMotionX(), other.getMotionX()) != 0) {
         return false;
      } else {
         return Double.compare(this.getMotionY(), other.getMotionY()) != 0 ? false : Double.compare(this.getMotionZ(), other.getMotionZ()) == 0;
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof PacketPlayerMotion;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      long $motionX = Double.doubleToLongBits(this.getMotionX());
      result = result * 59 + (int)($motionX >>> 32 ^ $motionX);
      long $motionY = Double.doubleToLongBits(this.getMotionY());
      result = result * 59 + (int)($motionY >>> 32 ^ $motionY);
      long $motionZ = Double.doubleToLongBits(this.getMotionZ());
      return result * 59 + (int)($motionZ >>> 32 ^ $motionZ);
   }

   @Override
   public String toString() {
      return "PacketPlayerMotion(motionX=" + this.getMotionX() + ", motionY=" + this.getMotionY() + ", motionZ=" + this.getMotionZ() + ")";
   }

   public PacketPlayerMotion(double motionX, double motionY, double motionZ) {
      this.motionX = motionX;
      this.motionY = motionY;
      this.motionZ = motionZ;
   }
}
