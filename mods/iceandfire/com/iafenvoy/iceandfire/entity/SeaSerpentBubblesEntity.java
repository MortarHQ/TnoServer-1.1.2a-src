package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.entity.util.dragon.IDragonProjectile;
import com.iafenvoy.iceandfire.registry.IafParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class SeaSerpentBubblesEntity extends Fireball implements IDragonProjectile {
   public SeaSerpentBubblesEntity(EntityType<? extends Fireball> t, Level worldIn) {
      super(t, worldIn);
   }

   public SeaSerpentBubblesEntity(
      EntityType<? extends Fireball> t, Level worldIn, double posX, double posY, double posZ, double accelX, double accelY, double accelZ
   ) {
      super(t, posX, posY, posZ, new Vec3(accelX, accelY, accelZ), worldIn);
   }

   public SeaSerpentBubblesEntity(EntityType<? extends Fireball> t, Level worldIn, SeaSerpentEntity shooter, double accelX, double accelY, double accelZ) {
      super(t, shooter, new Vec3(accelX, accelY, accelZ), worldIn);
   }

   public boolean isPickable() {
      return false;
   }

   protected boolean shouldBurn() {
      return false;
   }

   public void tick() {
      Entity shootingEntity = this.getOwner();
      if (this.tickCount > 400) {
         this.remove(RemovalReason.DISCARDED);
      }

      this.autoTarget();
      if (this.level().isClientSide || (shootingEntity == null || !shootingEntity.isAlive()) && this.level().hasChunkAt(this.blockPosition())) {
         this.baseTick();
         HitResult raytraceresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
         if (raytraceresult.getType() != Type.MISS) {
            this.onHit(raytraceresult);
         }

         Vec3 vec3d = this.getDeltaMovement();
         double d0 = this.getX() + vec3d.x;
         double d1 = this.getY() + vec3d.y;
         double d2 = this.getZ() + vec3d.z;
         ProjectileUtil.rotateTowardsMovement(this, 0.2F);
         float f = this.getInertia();
         if (this.level().isClientSide) {
            for (int i = 0; i < 3; i++) {
               this.level()
                  .addParticle(
                     (ParticleOptions)IafParticles.SERPENT_BUBBLE.get(),
                     this.getX() + this.random.nextFloat() * this.getBbWidth() - this.getBbWidth() * 0.5,
                     this.getY() - 0.5,
                     this.getZ() + this.random.nextFloat() * this.getBbWidth() - this.getBbWidth() * 0.5,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         this.setDeltaMovement(vec3d.add(vec3d.normalize().multiply(this.stuckSpeedMultiplier)).scale(f));
         this.setPos(d0, d1, d2);
         this.setPos(this.getX(), this.getY(), this.getZ());
      }

      this.setPos(this.getX(), this.getY(), this.getZ());
      if (this.tickCount > 20 && !this.isInWaterOrRain()) {
         this.remove(RemovalReason.DISCARDED);
      }
   }

   protected boolean canHitEntity(Entity entityIn) {
      return super.canHitEntity(entityIn) && !(entityIn instanceof MultipartPartEntity) && !(entityIn instanceof SeaSerpentBubblesEntity);
   }

   public void autoTarget() {
      if (this.level().isClientSide && !(this.getOwner() instanceof SeaSerpentEntity seaSerpent && seaSerpent.getTarget() != null) && this.tickCount > 20) {
         this.remove(RemovalReason.DISCARDED);
      }
   }

   protected ParticleOptions getTrailParticle() {
      return ParticleTypes.BUBBLE;
   }

   public boolean hurt(DamageSource source, float amount) {
      return false;
   }

   public float getPickRadius() {
      return 0.0F;
   }

   protected void onHit(HitResult movingObject) {
      boolean flag = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
      if (!this.level().isClientSide && movingObject.getType() == Type.ENTITY) {
         Entity entity = ((EntityHitResult)movingObject).getEntity();
         if (entity instanceof SlowPartEntity) {
            return;
         }

         if (this.getOwner() instanceof SeaSerpentEntity dragon) {
            if (dragon.isAlliedTo(entity) || dragon.is(entity)) {
               return;
            }

            entity.hurt(this.level().damageSources().mobAttack(dragon), 6.0F);
         }
      }
   }
}
