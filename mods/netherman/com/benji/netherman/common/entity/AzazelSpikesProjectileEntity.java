package com.benji.netherman.common.entity;

import com.benji.netherman.config.AzazelConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AzazelSpikesProjectileEntity extends Projectile implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private static final EntityDataAccessor<Integer> MODE = SynchedEntityData.defineId(AzazelSpikesProjectileEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> PIVOT_ID = SynchedEntityData.defineId(AzazelSpikesProjectileEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Float> ORBIT_RADIUS = SynchedEntityData.defineId(AzazelSpikesProjectileEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> ORBIT_ANGLE = SynchedEntityData.defineId(AzazelSpikesProjectileEntity.class, EntityDataSerializers.FLOAT);
   private int lifeTicks = 0;
   private float currentAngle = -1.0F;

   public AzazelSpikesProjectileEntity(EntityType<? extends Projectile> type, Level level) {
      super(type, level);
      this.setNoGravity(true);
      this.noPhysics = true;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(MODE, 0);
      builder.define(PIVOT_ID, -1);
      builder.define(ORBIT_RADIUS, 0.0F);
      builder.define(ORBIT_ANGLE, 0.0F);
   }

   public void setMode(int mode) {
      this.entityData.set(MODE, mode);
   }

   public void setPivotId(int id) {
      this.entityData.set(PIVOT_ID, id);
   }

   public void setOrbitRadius(float radius) {
      this.entityData.set(ORBIT_RADIUS, radius);
   }

   public void setOrbitAngle(float angle) {
      this.entityData.set(ORBIT_ANGLE, angle);
   }

   public void tick() {
      super.tick();
      this.lifeTicks++;
      if (this.lifeTicks > 200) {
         this.spawnDisappearParticles();
         this.discard();
      } else {
         if (!this.level().isClientSide()) {
            for (Player player : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(0.2))) {
               if (!player.isInvulnerable()) {
                  this.onHitEntity(new EntityHitResult(player));
                  break;
               }
            }
         }

         HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, x$0 -> this.canHitEntity(x$0));
         if (hitresult.getType() == Type.ENTITY) {
            this.onHitEntity((EntityHitResult)hitresult);
         }

         int mode = (Integer)this.entityData.get(MODE);
         if (mode == 1) {
            if (this.currentAngle == -1.0F) {
               this.currentAngle = (Float)this.entityData.get(ORBIT_ANGLE);
            }

            LivingEntity pivot = (LivingEntity)this.level().getEntity((Integer)this.entityData.get(PIVOT_ID));
            if (pivot != null && pivot.isAlive()) {
               this.currentAngle += 1.5F;
               double rad = Math.toRadians(this.currentAngle);
               float radius = (Float)this.entityData.get(ORBIT_RADIUS);
               double targetX = pivot.getX() + Math.cos(rad) * radius;
               double targetZ = pivot.getZ() + Math.sin(rad) * radius;
               double targetY = pivot.getY() + 0.5;
               this.setPos(targetX, targetY, targetZ);
               this.setYRot(this.currentAngle + 90.0F);
            } else if (!this.level().isClientSide()) {
               this.discard();
            }
         } else {
            Vec3 move = this.getDeltaMovement();
            this.setPos(this.getX() + move.x, this.getY() + move.y, this.getZ() + move.z);
            if (move.lengthSqr() > 0.001) {
               this.setYRot((float)(Math.atan2(move.z, move.x) * (180.0 / Math.PI)) - 90.0F);
            }
         }

         if (this.level() instanceof ServerLevel sl && this.tickCount % 2 == 0) {
            sl.sendParticles(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY() + 0.5, this.getZ(), 1, 0.2, 0.2, 0.2, 0.0);
         }
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      if (!this.level().isClientSide() && result.getEntity() instanceof Player player) {
         if (player.isInvulnerable()) {
            return;
         }

         float projDamage = ((Double)AzazelConfig.HUMAN_PROJECTILE_SPIKE_DAMAGE.get()).floatValue();
         int witherDuration = (Integer)AzazelConfig.HUMAN_PROJECTILE_SPIKE_WITHER_DURATION.get();
         player.hurt(this.damageSources().mobProjectile(this, (LivingEntity)this.getOwner()), projDamage);
         if (witherDuration > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.WITHER, witherDuration, 1));
         }

         this.spawnDisappearParticles();
         this.discard();
      }
   }

   private void spawnDisappearParticles() {
      if (this.level() instanceof ServerLevel sl) {
         sl.sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 15, 0.5, 0.5, 0.5, 0.1);
         sl.sendParticles(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY(), this.getZ(), 15, 0.5, 0.5, 0.5, 0.1);
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
