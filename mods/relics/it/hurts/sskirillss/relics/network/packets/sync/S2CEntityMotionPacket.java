package it.hurts.sskirillss.relics.network.packets.sync;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class S2CEntityMotionPacket implements CustomPacketPayload {
   private final int id;
   private final double x;
   private final double y;
   private final double z;
   public static final Type<S2CEntityMotionPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("relics", "entity_motion"));
   public static final StreamCodec<ByteBuf, S2CEntityMotionPacket> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT,
      S2CEntityMotionPacket::getId,
      ByteBufCodecs.DOUBLE,
      S2CEntityMotionPacket::getX,
      ByteBufCodecs.DOUBLE,
      S2CEntityMotionPacket::getY,
      ByteBufCodecs.DOUBLE,
      S2CEntityMotionPacket::getZ,
      S2CEntityMotionPacket::new
   );

   public S2CEntityMotionPacket(int id, Vec3 motion) {
      this(id, motion.x(), motion.y(), motion.z());
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Level level = ctx.player().getCommandSenderWorld();
         Entity entity = level.getEntity(this.id);
         if (entity != null) {
            entity.setDeltaMovement(this.x, this.y, this.z);
         }
      });
   }

   public int getId() {
      return this.id;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof S2CEntityMotionPacket other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getId() != other.getId()) {
         return false;
      } else if (Double.compare(this.getX(), other.getX()) != 0) {
         return false;
      } else {
         return Double.compare(this.getY(), other.getY()) != 0 ? false : Double.compare(this.getZ(), other.getZ()) == 0;
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof S2CEntityMotionPacket;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getId();
      long $x = Double.doubleToLongBits(this.getX());
      result = result * 59 + (int)($x >>> 32 ^ $x);
      long $y = Double.doubleToLongBits(this.getY());
      result = result * 59 + (int)($y >>> 32 ^ $y);
      long $z = Double.doubleToLongBits(this.getZ());
      return result * 59 + (int)($z >>> 32 ^ $z);
   }

   @Override
   public String toString() {
      return "S2CEntityMotionPacket(id=" + this.getId() + ", x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ")";
   }

   public S2CEntityMotionPacket(int id, double x, double y, double z) {
      this.id = id;
      this.x = x;
      this.y = y;
      this.z = z;
   }
}
