package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignited_Revenant_Entity;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;

public class Ashen_Breath_Entity extends Entity {
   private static final int RANGE = 7;
   private static final int ARC = 45;
   private LivingEntity caster;
   private UUID casterUuid;
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Ashen_Breath_Entity.class, EntityDataSerializers.FLOAT);

   public Ashen_Breath_Entity(EntityType<? extends Ashen_Breath_Entity> type, Level world) {
      super(type, world);
   }

   public Ashen_Breath_Entity(EntityType<? extends Ashen_Breath_Entity> type, Level world, float damage, LivingEntity caster) {
      super(type, world);
      this.setCaster(caster);
      this.setDamage(damage);
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   public void tick() {
      super.tick();
      if (this.caster != null && !this.caster.isAlive()) {
         this.discard();
      }

      if (this.caster != null) {
         this.setYRot(this.caster.yHeadRot);
      }

      float yaw = (float)Math.toRadians(-this.getYRot());
      float pitch = (float)Math.toRadians(-this.getXRot());
      float spread = 0.25F;
      float speed = 0.56F;
      float xComp = (float)(Math.sin(yaw) * Math.cos(pitch));
      float yComp = (float)Math.sin(pitch);
      float zComp = (float)(Math.cos(yaw) * Math.cos(pitch));
      double theta = this.getYRot() * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);
      double vec = 0.9;
      if (this.level().isClientSide) {
         for (int i = 0; i < 80; i++) {
            double xSpeed = speed * xComp + spread * 1.0F * (this.random.nextFloat() * 2.0F - 1.0F) * Math.sqrt(1.0F - xComp * xComp);
            double ySpeed = speed * yComp + spread * 1.0F * (this.random.nextFloat() * 2.0F - 1.0F) * Math.sqrt(1.0F - yComp * yComp);
            double zSpeed = speed * zComp + spread * 1.0F * (this.random.nextFloat() * 2.0F - 1.0F) * Math.sqrt(1.0F - zComp * zComp);
            this.level().addParticle(ParticleTypes.SMOKE, this.getX() + vec * vecX, this.getY(), this.getZ() + vec * vecZ, xSpeed, ySpeed, zSpeed);
         }

         for (int i = 0; i < 2; i++) {
            double xSpeed = speed * xComp + spread * 0.7 * (this.random.nextFloat() * 2.0F - 1.0F) * Math.sqrt(1.0F - xComp * xComp);
            double ySpeed = speed * yComp + spread * 0.7 * (this.random.nextFloat() * 2.0F - 1.0F) * Math.sqrt(1.0F - yComp * yComp);
            double zSpeed = speed * zComp + spread * 0.7 * (this.random.nextFloat() * 2.0F - 1.0F) * Math.sqrt(1.0F - zComp * zComp);
            this.level().addParticle(ParticleTypes.FLAME, this.getX() + vec * vecX, this.getY(), this.getZ() + vec * vecZ, xSpeed, ySpeed, zSpeed);
         }
      }

      if (this.tickCount > 2 && this.caster != null) {
         this.hitEntities();
      }

      if (this.tickCount > 25) {
         this.discard();
      }
   }

   public void hitEntities() {
      for (LivingEntity entityHit : this.getEntityLivingBaseNearby(7.0, 7.0, 7.0, 7.0)) {
         float entityHitYaw = (float)((Math.atan2(entityHit.getZ() - this.getZ(), entityHit.getX() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0);
         float entityAttackingYaw = this.getYRot() % 360.0F;
         if (entityHitYaw < 0.0F) {
            entityHitYaw += 360.0F;
         }

         if (entityAttackingYaw < 0.0F) {
            entityAttackingYaw += 360.0F;
         }

         float entityRelativeYaw = entityHitYaw - entityAttackingYaw;
         float xzDistance = (float)Math.sqrt(
            (entityHit.getZ() - this.getZ()) * (entityHit.getZ() - this.getZ()) + (entityHit.getX() - this.getX()) * (entityHit.getX() - this.getX())
         );
         double hitY = entityHit.getY() + entityHit.getBbHeight() / 2.0;
         float entityHitPitch = (float)(Math.atan2(hitY - this.getY(), xzDistance) * (180.0 / Math.PI) % 360.0);
         float entityAttackingPitch = -this.getXRot() % 360.0F;
         if (entityHitPitch < 0.0F) {
            entityHitPitch += 360.0F;
         }

         if (entityAttackingPitch < 0.0F) {
            entityAttackingPitch += 360.0F;
         }

         float entityRelativePitch = entityHitPitch - entityAttackingPitch;
         float entityHitDistance = (float)Math.sqrt(
            (entityHit.getZ() - this.getZ()) * (entityHit.getZ() - this.getZ())
               + (entityHit.getX() - this.getX()) * (entityHit.getX() - this.getX())
               + (hitY - this.getY()) * (hitY - this.getY())
         );
         int distance = this.tickCount / 2;
         boolean inRange = entityHitDistance <= distance + 1.0F;
         boolean yawCheck = entityRelativeYaw <= 22.5F && entityRelativeYaw >= -22.5F || entityRelativeYaw >= 337.5F || entityRelativeYaw <= -337.5F;
         boolean pitchCheck = entityRelativePitch <= 22.5F && entityRelativePitch >= -22.5F || entityRelativePitch >= 337.5F || entityRelativePitch <= -337.5F;
         boolean CloseCheck = this.caster instanceof Ignited_Revenant_Entity && entityHitDistance <= 2.0F;
         if ((inRange && yawCheck && pitchCheck || CloseCheck) && this.tickCount % 3 == 0 && !this.isAlliedTo(entityHit) && entityHit != this.caster) {
            boolean flag = entityHit.hurt(this.damageSources().indirectMagic(this, this.caster), this.getDamage());
            if (flag) {
               MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.BLINDNESS, 60, 0, false, false, true);
               entityHit.addEffect(effectinstance);
            }
         }
      }
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

   public void setCaster(@Nullable LivingEntity p_190549_1_) {
      this.caster = p_190549_1_;
      this.casterUuid = p_190549_1_ == null ? null : p_190549_1_.getUUID();
   }

   @Nullable
   public LivingEntity getCaster() {
      if (this.caster == null && this.casterUuid != null && this.level() instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.level()).getEntity(this.casterUuid);
         if (entity instanceof LivingEntity) {
            this.caster = (LivingEntity)entity;
         }
      }

      return this.caster;
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      if (compound.hasUUID("Owner")) {
         this.casterUuid = compound.getUUID("Owner");
      }

      this.setDamage(compound.getFloat("damage"));
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      if (this.casterUuid != null) {
         compound.putUUID("Owner", this.casterUuid);
      }

      compound.putFloat("damage", this.getDamage());
   }

   public boolean isPickable() {
      return false;
   }

   public void push(Entity entityIn) {
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public List<LivingEntity> getEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(LivingEntity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return this.level()
         .getEntitiesOfClass(
            entityClass,
            this.getBoundingBox().inflate(dX, dY, dZ),
            e -> e != this && this.distanceTo(e) <= r + e.getBbWidth() / 2.0F && e.getY() <= this.getY() + dY
         );
   }

   public boolean isPushable() {
      return false;
   }
}
