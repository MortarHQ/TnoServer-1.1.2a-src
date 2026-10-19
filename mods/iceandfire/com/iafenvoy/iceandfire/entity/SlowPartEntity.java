package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.registry.IafEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SlowPartEntity extends MultipartPartEntity {
   private final float baseRadius;
   private final float baseOffsetY;
   private final float baseSizeX;
   private final float baseSizeY;

   public SlowPartEntity(EntityType<?> t, Level world) {
      super(t, world);
      this.baseRadius = 0.0F;
      this.baseOffsetY = 0.0F;
      this.baseSizeX = 0.0F;
      this.baseSizeY = 0.0F;
   }

   public SlowPartEntity(
      EntityType<?> t, LivingEntity parent, float baseRadius, float angleYaw, float baseOffsetY, float baseSizeX, float baseSizeY, float damageMultiplier
   ) {
      super(t, parent, baseRadius, angleYaw, baseOffsetY, baseSizeX, baseSizeY, damageMultiplier);
      this.baseRadius = baseRadius;
      this.baseOffsetY = baseOffsetY;
      this.baseSizeX = baseSizeX;
      this.baseSizeY = baseSizeY;
   }

   public SlowPartEntity(Entity parent, float baseRadius, float angleYaw, float baseOffsetY, float baseSizeX, float baseSizeY, float damageMultiplier) {
      super((EntityType<?>)IafEntities.SLOW_MULTIPART.get(), parent, baseRadius, angleYaw, baseOffsetY, baseSizeX, baseSizeY, damageMultiplier);
      this.baseRadius = baseRadius;
      this.baseOffsetY = baseOffsetY;
      this.baseSizeX = baseSizeX;
      this.baseSizeY = baseSizeY;
   }

   public void updateScale(float scale) {
      this.radius = this.baseRadius * scale;
      this.offsetY = this.baseOffsetY * scale;
      this.setScaleX(this.baseSizeX * scale);
      this.setScaleY(this.baseSizeY * scale);
   }

   @Override
   protected boolean isSlowFollow() {
      return true;
   }
}
