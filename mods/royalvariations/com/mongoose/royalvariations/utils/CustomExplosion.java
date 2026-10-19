package com.mongoose.royalvariations.utils;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class CustomExplosion {
   public CustomExplosion(Level level, Entity source, DamageSource damageSource, double x, double y, double z, float radius) {
      this(level, source, damageSource, x, y, z, radius, 0.0F);
   }

   public CustomExplosion(Level level, Entity source, DamageSource damageSource, BlockPos blockPos, float radius, float damage) {
      this(level, source, damageSource, blockPos.getX(), blockPos.getY(), blockPos.getZ(), radius, damage);
   }

   public CustomExplosion(Level level, Entity source, DamageSource damageSource, double x, double y, double z, float radius, float damage) {
      Vec3 vec3 = new Vec3(x, y, z);

      for (Entity entity : explosionRangeEntities(level, source, x, y, z, radius)) {
         double d12 = Math.sqrt(entity.distanceToSqr(vec3)) / radius;
         if (d12 <= 1.0) {
            double d5 = entity.getX() - x;
            double d7 = entity.getEyeY() - y;
            double d9 = entity.getZ() - z;
            double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
            if (d13 != 0.0) {
               d5 /= d13;
               d7 /= d13;
               d9 /= d13;
               double d14 = getSeenPercent(vec3, entity);
               double d10 = (1.0 - d12) * d14;
               float actualDamage = damage == 0.0F ? (int)((d10 * d10 + d10) / 2.0 * 7.0 * radius + 1.0) : damage;
               boolean hurt = !damageSource.is(DamageTypeTags.IS_EXPLOSION)
                  || !entity.ignoreExplosion(new Explosion(level, source, x, y, z, radius, false, BlockInteraction.KEEP));
               if (hurt) {
                  this.explodeHurt(entity, damageSource, d5, d7, d9, d10, actualDamage);
               }
            }
         }
      }
   }

   public void explodeHurt(Entity target, DamageSource damageSource, double x, double y, double z, double seen, float actualDamage) {
      target.hurt(damageSource, actualDamage);
      double d11 = seen;
      if (target instanceof LivingEntity livingEntity) {
         d11 *= 1.0 - livingEntity.getAttributeValue(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE);
      }

      if (target instanceof LivingEntity) {
         MobUtil.push(target, x * d11, y * d11, z * d11);
      }
   }

   public static List<Entity> explosionRangeEntities(Level level, Entity source, BlockPos blockPos, float range) {
      return explosionRangeEntities(level, source, blockPos.getX(), blockPos.getY(), blockPos.getZ(), range);
   }

   public static List<Entity> explosionRangeEntities(Level level, Entity source, double x, double y, double z, float radius) {
      int k1 = Mth.floor(x - radius - 1.0);
      int l1 = Mth.floor(x + radius + 1.0);
      int i2 = Mth.floor(y - radius - 1.0);
      int i1 = Mth.floor(y + radius + 1.0);
      int j2 = Mth.floor(z - radius - 1.0);
      int j1 = Mth.floor(z + radius + 1.0);
      return level.getEntities(source, new AABB(k1, i2, j2, l1, i1, j1));
   }

   public static float getSeenPercent(Vec3 vector, Entity target) {
      AABB aabb = target.getBoundingBox();
      double d0 = 1.0 / ((aabb.maxX - aabb.minX) * 2.0 + 1.0);
      double d1 = 1.0 / ((aabb.maxY - aabb.minY) * 2.0 + 1.0);
      double d2 = 1.0 / ((aabb.maxZ - aabb.minZ) * 2.0 + 1.0);
      double d3 = (1.0 - Math.floor(1.0 / d0) * d0) / 2.0;
      double d4 = (1.0 - Math.floor(1.0 / d2) * d2) / 2.0;
      if (!(d0 < 0.0) && !(d1 < 0.0) && !(d2 < 0.0)) {
         int i = 0;
         int j = 0;

         for (double d5 = 0.0; d5 <= 1.0; d5 += d0) {
            for (double d6 = 0.0; d6 <= 1.0; d6 += d1) {
               for (double d7 = 0.0; d7 <= 1.0; d7 += d2) {
                  double d8 = Mth.lerp(d5, aabb.minX, aabb.maxX);
                  double d9 = Mth.lerp(d6, aabb.minY, aabb.maxY);
                  double d10 = Mth.lerp(d7, aabb.minZ, aabb.maxZ);
                  Vec3 vec3 = new Vec3(d8 + d3, d9, d10 + d4);
                  if (target.level().clip(new ClipContext(vec3, vector, Block.COLLIDER, Fluid.NONE, target)).getType() == Type.MISS) {
                     i++;
                  }

                  j++;
               }
            }
         }

         return (float)i / j;
      } else {
         return 0.0F;
      }
   }
}
