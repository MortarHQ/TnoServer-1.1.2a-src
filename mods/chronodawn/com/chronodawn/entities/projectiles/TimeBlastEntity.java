package com.chronodawn.entities.projectiles;

import com.chronodawn.registry.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class TimeBlastEntity extends ThrowableProjectile {
   public TimeBlastEntity(EntityType<? extends TimeBlastEntity> entityType, Level level) {
      super(entityType, level);
   }

   public TimeBlastEntity(Level level, LivingEntity shooter) {
      super((EntityType)ModEntities.TIME_BLAST.get(), shooter, level);
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         for (int i = 0; i < 2; i++) {
            this.level()
               .addParticle(
                  ParticleTypes.END_ROD,
                  this.getX() + (this.random.nextDouble() - 0.5) * 0.3,
                  this.getY() + (this.random.nextDouble() - 0.5) * 0.3,
                  this.getZ() + (this.random.nextDouble() - 0.5) * 0.3,
                  0.0,
                  0.0,
                  0.0
               );
         }

         if (this.random.nextInt(3) == 0) {
            this.level()
               .addParticle(
                  ParticleTypes.PORTAL,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  (this.random.nextDouble() - 0.5) * 0.1,
                  (this.random.nextDouble() - 0.5) * 0.1,
                  (this.random.nextDouble() - 0.5) * 0.1
               );
         }
      }
   }

   protected void onHit(HitResult result) {
      super.onHit(result);
      if (!this.level().isClientSide) {
         for (int i = 0; i < 20; i++) {
            double offsetX = (this.random.nextDouble() - 0.5) * 1.0;
            double offsetY = (this.random.nextDouble() - 0.5) * 1.0;
            double offsetZ = (this.random.nextDouble() - 0.5) * 1.0;
            this.level()
               .addParticle(
                  ParticleTypes.PORTAL, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, offsetX * 0.1, offsetY * 0.1, offsetZ * 0.1
               );
         }

         this.discard();
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      if (!this.level().isClientSide) {
         if (result.getEntity() instanceof LivingEntity target) {
            target.hurt(this.damageSources().mobProjectile(this, (LivingEntity)this.getOwner()), 4.0F);
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
            target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 0));
         }
      }
   }
}
