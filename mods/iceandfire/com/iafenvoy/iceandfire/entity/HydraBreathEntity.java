package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.entity.util.dragon.IDragonProjectile;
import com.iafenvoy.iceandfire.registry.IafParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class HydraBreathEntity extends Fireball implements IDragonProjectile {
   public HydraBreathEntity(EntityType<? extends Fireball> t, Level worldIn) {
      super(t, worldIn);
   }

   public HydraBreathEntity(EntityType<? extends Fireball> t, Level worldIn, HydraEntity shooter, double accelX, double accelY, double accelZ) {
      super(t, shooter, new Vec3(accelX, accelY, accelZ), worldIn);
   }

   protected boolean shouldBurn() {
      return false;
   }

   public boolean hurt(DamageSource source, float amount) {
      return false;
   }

   public float getPickRadius() {
      return 0.0F;
   }

   public boolean isPickable() {
      return false;
   }

   public void tick() {
      this.clearFire();
      if (this.tickCount > 30) {
         this.remove(RemovalReason.DISCARDED);
      }

      Entity shootingEntity = this.getOwner();
      if (this.level().isClientSide || (shootingEntity == null || shootingEntity.isAlive()) && this.level().hasChunkAt(this.blockPosition())) {
         this.baseTick();
         if (this.shouldBurn()) {
            this.igniteForSeconds(1.0F);
         }

         HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, x$0 -> this.canHitEntity(x$0));
         if (hitResult.getType() != Type.MISS) {
            this.onHit(hitResult);
         }

         Vec3 Vector3d = this.getDeltaMovement();
         double d0 = this.getX() + Vector3d.x;
         double d1 = this.getY() + Vector3d.y;
         double d2 = this.getZ() + Vector3d.z;
         ProjectileUtil.rotateTowardsMovement(this, 0.2F);
         if (this.level().isClientSide) {
            for (int i = 0; i < 15; i++) {
               this.level()
                  .addParticle(
                     (ParticleOptions)IafParticles.HYDRA_BREATH.get(),
                     this.getX() + this.random.nextFloat() * this.getBbWidth() - this.getBbWidth() * 0.5,
                     this.getY() - 0.5,
                     this.getZ() + this.random.nextFloat() * this.getBbWidth() - this.getBbWidth() * 0.5,
                     0.1,
                     1.0,
                     0.1
                  );
            }
         }

         Vec3 vec3d = this.getDeltaMovement();
         this.setDeltaMovement(vec3d.add(vec3d.normalize().scale(this.accelerationPower)).scale(this.getInertia()));
         if (this.isInWater()) {
            for (int i = 0; i < 4; i++) {
               this.level()
                  .addParticle(
                     ParticleTypes.BUBBLE,
                     this.getX() - this.getDeltaMovement().x * 0.25,
                     this.getY() - this.getDeltaMovement().y * 0.25,
                     this.getZ() - this.getDeltaMovement().z * 0.25,
                     this.getDeltaMovement().x,
                     this.getDeltaMovement().y,
                     this.getDeltaMovement().z
                  );
            }
         }

         this.setPos(d0, d1, d2);
         this.setPos(this.getX(), this.getY(), this.getZ());
      }
   }

   protected void onHit(HitResult movingObject) {
      this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
      Entity shootingEntity = this.getOwner();
      if (!this.level().isClientSide && movingObject.getType() == Type.ENTITY) {
         Entity entity = ((EntityHitResult)movingObject).getEntity();
         if (entity instanceof HydraHeadEntity) {
            return;
         }

         if (shootingEntity instanceof HydraEntity dragon) {
            if (dragon.isAlliedTo(entity) || dragon.is(entity)) {
               return;
            }

            entity.hurt(this.level().damageSources().mobAttack(dragon), 2.0F);
            if (entity instanceof LivingEntity) {
               ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
            }
         }
      }
   }
}
