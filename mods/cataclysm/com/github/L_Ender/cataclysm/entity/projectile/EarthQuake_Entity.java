package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EarthQuake_Entity extends ThrowableProjectile {
   private int lifeTime = 60;
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(EarthQuake_Entity.class, EntityDataSerializers.FLOAT);

   public EarthQuake_Entity(EntityType<? extends EarthQuake_Entity> type, Level worldIn) {
      super(type, worldIn);
   }

   public EarthQuake_Entity(Level worldIn, double x, double y, double z) {
      this((EntityType<? extends EarthQuake_Entity>)ModEntities.EARTHQUAKE.get(), worldIn);
      this.setPos(x, y + 1.5, z);
   }

   public EarthQuake_Entity(Level worldIn, LivingEntity throwerIn) {
      this((EntityType<? extends EarthQuake_Entity>)ModEntities.EARTHQUAKE.get(), worldIn);
      this.setOwner(throwerIn);
      this.setDeltaMovement(0.1, 0.0, 0.1);
   }

   public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
      Vec3 vector3d = new Vec3(pX, pY, pZ)
         .normalize()
         .add(
            this.random.nextGaussian() * 0.0075F * pInaccuracy,
            this.random.nextGaussian() * 0.0075F * pInaccuracy,
            this.random.nextGaussian() * 0.0075F * pInaccuracy
         )
         .scale(pVelocity);
      this.setDeltaMovement(vector3d);
      double d0 = vector3d.horizontalDistance();
      this.setYRot((float)(Mth.atan2(vector3d.x, vector3d.z) * 180.0F / (float)Math.PI));
      this.setXRot((float)(Mth.atan2(vector3d.y, d0) * 180.0F / (float)Math.PI));
      this.yRotO = this.getYRot();
      this.xRotO = this.getXRot();
   }

   public void shootFromRotation(Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
      float f = -Mth.sin(pY * (float) (Math.PI / 180.0)) * Mth.cos(pX * (float) (Math.PI / 180.0));
      float f1 = -1.0F;
      float f2 = Mth.cos(pY * (float) (Math.PI / 180.0)) * Mth.cos(pX * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, pVelocity, pInaccuracy);
      Vec3 vector3d = pShooter.getDeltaMovement();
      this.setDeltaMovement(this.getDeltaMovement().add(vector3d.x, pShooter.onGround() ? 0.0 : vector3d.y, vector3d.z));
   }

   public boolean canCollideWith(Entity pEntity) {
      return this.canHitEntity(pEntity);
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public void tick() {
      if (this.getOwner() != null && !this.getOwner().isAlive()) {
         this.discard();
      } else {
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.onUpdateInAir();
      }

      super.tick();
   }

   protected boolean canHitEntity(Entity pTarget) {
      return super.canHitEntity(pTarget) && pTarget != this.getOwner();
   }

   public boolean isOnFire() {
      return false;
   }

   private void onUpdateInAir() {
      this.lifeTime--;
      if (this.lifeTime <= 0) {
         this.discard();
      }

      BlockPos pos = BlockPos.containing(this.getX(), this.getY() - 1.0, this.getZ());
      BlockState iblockstate = this.level().getBlockState(pos);
      Entity entity1 = this.getOwner();
      LivingEntity livingonwer = entity1 instanceof LivingEntity ? (LivingEntity)entity1 : null;

      for (LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.5, 0.5, 0.5))) {
         if (this.getOwner() != null
            && this.tickCount % 5 == 0
            && livingentity != this.getOwner()
            && livingentity.onGround()
            && !this.getOwner().isAlliedTo(livingentity)
            && livingentity.isAlive()
            && livingentity.hurt(this.damageSources().mobProjectile(this, livingonwer), this.getDamage())) {
            this.strongKnockback(livingentity, 0.5);
         }
      }

      if (this.level().isClientSide) {
         for (int i = 0; i < 3; i++) {
            this.level()
               .addParticle(
                  new BlockParticleOption(ParticleTypes.BLOCK, iblockstate),
                  this.getX() + this.random.nextFloat() - 0.5,
                  this.getY() + this.random.nextFloat() - 0.5,
                  this.getZ() + this.random.nextFloat() - 0.5,
                  4.0 * (this.random.nextFloat() - 0.5),
                  this.random.nextFloat() * 5.0 + 0.5,
                  (this.random.nextFloat() - 0.5) * 4.0
               );
         }

         this.level().addParticle((ParticleOptions)ModParticle.DUST_BLAST.get(), this.getX(), this.getY() + 0.1, this.getZ(), 0.0, 0.0, 0.0);
      }
   }

   private void strongKnockback(Entity p_33340_, double modifier) {
      double d0 = p_33340_.getX() - this.getX();
      double d1 = p_33340_.getZ() - this.getZ();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001) * 2.0;
      p_33340_.push(d0 * modifier / d2, 0.5 * modifier, d1 * modifier / d2);
   }

   public float maxUpStep() {
      return 2.0F;
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(DAMAGE, 0.0F);
   }

   public float getDamage() {
      return (Float)this.entityData.get(DAMAGE);
   }

   public void setDamage(float damage) {
      this.entityData.set(DAMAGE, damage);
   }
}
