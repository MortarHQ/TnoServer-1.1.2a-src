package com.github.wal_bos.moreskills.net.msg;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public record ArthurAvalonVfxData(
   int sourceId, int targetId, double x, double y, double z, double x2, double y2, double z2, float radius, int durationTicks, long seed, int variant
) {
   public ArthurAvalonVfxData(FriendlyByteBuf buffer) {
      this(
         buffer.readVarInt(),
         buffer.readVarInt(),
         buffer.readDouble(),
         buffer.readDouble(),
         buffer.readDouble(),
         buffer.readDouble(),
         buffer.readDouble(),
         buffer.readDouble(),
         buffer.readFloat(),
         buffer.readVarInt(),
         buffer.readLong(),
         buffer.readVarInt()
      );
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeVarInt(this.sourceId);
      buffer.writeVarInt(this.targetId);
      buffer.writeDouble(this.x);
      buffer.writeDouble(this.y);
      buffer.writeDouble(this.z);
      buffer.writeDouble(this.x2);
      buffer.writeDouble(this.y2);
      buffer.writeDouble(this.z2);
      buffer.writeFloat(this.radius);
      buffer.writeVarInt(this.durationTicks);
      buffer.writeLong(this.seed);
      buffer.writeVarInt(this.variant);
   }

   public static ArthurAvalonVfxData create(
      ServerLevel level, LivingEntity source, LivingEntity target, Vec3 start, Vec3 end, float radius, int durationTicks, int variant
   ) {
      return new ArthurAvalonVfxData(
         source == null ? -1 : source.getId(),
         target == null ? -1 : target.getId(),
         start.x,
         start.y,
         start.z,
         end.x,
         end.y,
         end.z,
         radius,
         Math.max(1, durationTicks),
         level.getRandom().nextLong(),
         variant
      );
   }

   public Vec3 start() {
      return new Vec3(this.x, this.y, this.z);
   }

   public Vec3 end() {
      return new Vec3(this.x2, this.y2, this.z2);
   }
}
