package com.mortal_cultivation.entity;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record QilinEffectPayload(ResourceLocation dimension, int effect, Vec3 center, long gameTime, int seed, float yaw, int entityId)
   implements CustomPacketPayload {
   public static final int STOMP = 0;
   public static final int ROOTS = 1;
   public static final int ROOT_WARNING = 2;
   public static final int SWEEP = 3;
   public static final int CHARGE_WARNING = 4;
   public static final int CHARGE_TRAIL = 5;
   public static final int ENRAGE = 6;
   public static final Type<QilinEffectPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "qilin_effect"));
   public static final StreamCodec<FriendlyByteBuf, QilinEffectPayload> STREAM_CODEC = CustomPacketPayload.codec(
      QilinEffectPayload::write, QilinEffectPayload::new
   );

   private QilinEffectPayload(FriendlyByteBuf buffer) {
      this(
         buffer.readResourceLocation(),
         buffer.readUnsignedByte(),
         new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
         buffer.readLong(),
         buffer.readInt(),
         buffer.readFloat(),
         buffer.readVarInt()
      );
   }

   public QilinEffectPayload(ResourceLocation dimension, int effect, Vec3 center, long gameTime, int seed) {
      this(dimension, effect, center, gameTime, seed, 0.0F, -1);
   }

   public QilinEffectPayload(ResourceLocation dimension, int effect, Vec3 center, long gameTime, int seed, float yaw) {
      this(dimension, effect, center, gameTime, seed, yaw, -1);
   }

   private void write(FriendlyByteBuf buffer) {
      buffer.writeResourceLocation(this.dimension);
      buffer.writeByte(this.effect);
      buffer.writeDouble(this.center.x);
      buffer.writeDouble(this.center.y);
      buffer.writeDouble(this.center.z);
      buffer.writeLong(this.gameTime);
      buffer.writeInt(this.seed);
      buffer.writeFloat(this.yaw);
      buffer.writeVarInt(this.entityId);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
