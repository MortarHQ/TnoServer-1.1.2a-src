package com.chronodawn.entities.projectiles;

import com.chronodawn.registry.ModEntities;
import com.chronodawn.registry.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class GearProjectileEntity extends ThrowableProjectile {
   private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(GearProjectileEntity.class, EntityDataSerializers.FLOAT);
   private static final float DAMAGE = 8.0F;
   private static final float KNOCKBACK = 0.5F;
   private int ticksInAir = 0;

   public GearProjectileEntity(EntityType<? extends GearProjectileEntity> entityType, Level level) {
      super(entityType, level);
   }

   public GearProjectileEntity(Level level, LivingEntity shooter) {
      super((EntityType)ModEntities.GEAR_PROJECTILE.get(), shooter, level);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(ROTATION, 0.0F);
   }

   public void shoot(double deltaX, double deltaY, double deltaZ, float velocity, float inaccuracy) {
      Vec3 vec3 = new Vec3(deltaX, deltaY, deltaZ)
         .normalize()
         .add(
            this.random.triangle(0.0, 0.0172275 * inaccuracy),
            this.random.triangle(0.0, 0.0172275 * inaccuracy),
            this.random.triangle(0.0, 0.0172275 * inaccuracy)
         )
         .scale(velocity);
      this.setDeltaMovement(vec3);
      double horizontalDistance = vec3.horizontalDistance();
      this.setYRot((float)(Math.atan2(vec3.x, vec3.z) * 180.0 / Math.PI));
      this.setXRot((float)(Math.atan2(vec3.y, horizontalDistance) * 180.0 / Math.PI));
      this.yRotO = this.getYRot();
      this.xRotO = this.getXRot();
   }

   public void tick() {
      super.tick();
      this.ticksInAir++;
      if (this.ticksInAir > 100) {
         this.discard();
      } else {
         if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel && this.ticksInAir % 2 == 0) {
            serverLevel.sendParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 1, 0.1, 0.1, 0.1, 0.0);
         }

         float currentRotation = (Float)this.entityData.get(ROTATION);
         this.entityData.set(ROTATION, (currentRotation + 20.0F) % 360.0F);
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      Entity entity = result.getEntity();
      Entity owner = this.getOwner();
      DamageSource damageSource = this.damageSources().mobProjectile(this, owner instanceof LivingEntity living ? living : null);
      if (entity.hurt(damageSource, 8.0F)) {
         if (entity instanceof LivingEntity livingEntity) {
            Vec3 movement = this.getDeltaMovement();
            double knockbackX = movement.x * 0.5;
            double knockbackZ = movement.z * 0.5;
            livingEntity.knockback(0.5, -knockbackX, -knockbackZ);
         }

         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.GEAR_IMPACT.get(), SoundSource.HOSTILE, 0.5F, 1.2F);
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 10, 0.2, 0.2, 0.2, 0.1);
         }
      }

      this.discard();
   }

   protected void onHitBlock(BlockHitResult result) {
      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)ModSounds.GEAR_GROUND_IMPACT.get(), SoundSource.HOSTILE, 0.5F, 1.0F);
      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 5, 0.1, 0.1, 0.1, 0.02);
      }

      this.discard();
   }

   public float getRotation() {
      return (Float)this.entityData.get(ROTATION);
   }

   protected boolean canHitEntity(Entity entity) {
      return super.canHitEntity(entity) && !entity.equals(this.getOwner());
   }

   public boolean isPickable() {
      return false;
   }

   public boolean hurt(DamageSource source, float amount) {
      return false;
   }
}
