package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.client.particle.Options.LightningParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.LightningStormParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.RingParticleOptions;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;

public class Lightning_Storm_Entity extends Entity {
   public static final int STRIKE = 10;
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Lightning_Storm_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> HPDAMAGE = SynchedEntityData.defineId(Lightning_Storm_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(Lightning_Storm_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> LIFESPAN = SynchedEntityData.defineId(Lightning_Storm_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DELAY = SynchedEntityData.defineId(Lightning_Storm_Entity.class, EntityDataSerializers.INT);
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerUUID;

   public Lightning_Storm_Entity(EntityType<? extends Lightning_Storm_Entity> entityType, Level level) {
      super(entityType, level);
   }

   public Lightning_Storm_Entity(
      Level worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, float damage, float Hpdamage, LivingEntity casterIn, float size
   ) {
      this((EntityType<? extends Lightning_Storm_Entity>)ModEntities.LIGHTNING_STORM.get(), worldIn);
      this.setDelay(p_i47276_9_);
      this.setCaster(casterIn);
      this.setDamage(damage);
      this.setHpDamage(Hpdamage);
      this.setYRot(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.setPos(x, y, z);
      this.setSize(size);
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(DAMAGE, 0.0F);
      p_326229_.define(HPDAMAGE, 0.0F);
      p_326229_.define(SIZE, 0.0F);
      p_326229_.define(LIFESPAN, 0);
      p_326229_.define(DELAY, 0);
   }

   public int getLifespan() {
      return (Integer)this.entityData.get(LIFESPAN);
   }

   public void setLifespan(int lifespan) {
      this.entityData.set(LIFESPAN, lifespan);
   }

   public int getDelay() {
      return (Integer)this.entityData.get(DELAY);
   }

   public void setDelay(int delay) {
      this.entityData.set(DELAY, delay);
   }

   public float getSize() {
      return (Float)this.entityData.get(SIZE);
   }

   public void setSize(float size) {
      if (!this.level().isClientSide) {
         this.getEntityData().set(SIZE, Mth.clamp(size, 1.0F, 5.0F));
      }
   }

   public void setCaster(@Nullable LivingEntity p_190549_1_) {
      this.owner = p_190549_1_;
      this.ownerUUID = p_190549_1_ == null ? null : p_190549_1_.getUUID();
   }

   @Nullable
   public LivingEntity getCaster() {
      if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
         if (entity instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
         }
      }

      return this.owner;
   }

   public float getDamage() {
      return (Float)this.entityData.get(DAMAGE);
   }

   public void setDamage(float damage) {
      this.entityData.set(DAMAGE, damage);
   }

   public float getHpDamage() {
      return (Float)this.entityData.get(HPDAMAGE);
   }

   public void setHpDamage(float damage) {
      this.entityData.set(HPDAMAGE, damage);
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      double d0 = 64.0 * getViewScale();
      return distance < d0 * d0;
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_19729_) {
      if (SIZE.equals(p_19729_)) {
         this.refreshDimensions();
      }

      super.onSyncedDataUpdated(p_19729_);
   }

   public void refreshDimensions() {
      double d0 = this.getX();
      double d1 = this.getY();
      double d2 = this.getZ();
      super.refreshDimensions();
      this.setPos(d0, d1, d2);
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   public EntityDimensions getDimensions(Pose p_19721_) {
      return EntityDimensions.scalable(this.getSize() * 0.5F, this.getSize() * 1.75F);
   }

   public void tick() {
      super.tick();
      this.setLifespan(this.getLifespan() + 1);
      int adjustedLifespan = this.getLifespan() - this.getDelay();
      if (this.level().isClientSide) {
         if (adjustedLifespan == 2) {
            this.level()
               .addParticle(
                  new RingParticleOptions(0.0F, (float) (Math.PI / 2), 20, 99, 194, 201, 1.0F, this.getSize() * 6.0F, false, 1),
                  this.getX(),
                  this.getY() + 0.3F,
                  this.getZ(),
                  0.0,
                  0.0,
                  0.0
               );
         }

         for (int i = 11; i < 30; i += 2) {
            if (adjustedLifespan == i) {
               this.smolder(1);
            }
         }

         if (adjustedLifespan == 10) {
            if (!this.isSilent()) {
               this.level()
                  .playLocalSound(
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     (SoundEvent)ModSounds.EMP_ACTIVATED.get(),
                     this.getSoundSource(),
                     1.0F,
                     this.random.nextFloat() * 0.2F + 0.85F,
                     false
                  );
            }

            double d0 = this.getX();
            double d1 = this.getY() + this.getSize() + 0.03;
            double d2 = this.getZ();
            this.level().addAlwaysVisibleParticle(new LightningStormParticleOptions(this.getSize()), d0, d1, d2, 0.0, 0.0, 0.0);
         }
      } else {
         if (adjustedLifespan == 11 || adjustedLifespan == 12 || adjustedLifespan == 13 || adjustedLifespan == 14) {
            this.damageEntityLivingBaseNearby(0.1);
         }

         if (adjustedLifespan > 20) {
            this.discard();
         }
      }
   }

   public void damageEntityLivingBaseNearby(double radius) {
      AABB aabb = this.getBoundingBox().inflate(radius);
      AABB selection = new AABB(aabb.minX, this.getY() - 0.1, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);

      for (LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, selection)) {
         this.damage(livingentity);
      }
   }

   private void damage(LivingEntity Hitentity) {
      LivingEntity livingentity = this.getCaster();
      if (Hitentity.isAlive() && !Hitentity.isInvulnerable() && Hitentity != livingentity) {
         if (livingentity == null) {
            Hitentity.hurt(this.damageSources().magic(), this.getDamage());
         } else if (!livingentity.isAlliedTo(Hitentity) && !Hitentity.isAlliedTo(livingentity)) {
            Hitentity.hurt(CMDamageTypes.causeLightningDamage(this, livingentity), this.getDamage() + Hitentity.getMaxHealth() * this.getHpDamage());
         }
      }
   }

   public void handleEntityEvent(byte p_36935_) {
      super.handleEntityEvent(p_36935_);
   }

   private void smolder(int amount) {
      for (int i = 0; i < amount; i++) {
         float velocity = 0.25F * this.getSize();
         float yaw = (float)(this.random.nextFloat() * 2.0F * Math.PI);
         float r = this.random.nextFloat() * 0.7F;
         float x = r * Mth.cos(yaw);
         float z = r * Mth.sin(yaw);
         float motionY = this.random.nextFloat() * 0.8F;
         float motionX = velocity * Mth.cos(yaw);
         float motionZ = velocity * Mth.sin(yaw);
         this.level().addParticle(new LightningParticleOptions(99, 194, 201), this.getX() + x, this.getY() + 0.1, this.getZ() + z, motionX, motionY, motionZ);
      }
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("lifespan", this.getLifespan());
      compound.putInt("delay", this.getDelay());
      if (this.ownerUUID != null) {
         compound.putUUID("Owner", this.ownerUUID);
      }

      compound.putFloat("damage", this.getDamage());
      compound.putFloat("Hpdamage", this.getHpDamage());
      compound.putFloat("size", this.getSize());
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.setLifespan(compound.getInt("lifespan"));
      this.setDelay(compound.getInt("delay"));
      if (compound.hasUUID("Owner")) {
         this.ownerUUID = compound.getUUID("Owner");
      }

      this.setDamage(compound.getFloat("damage"));
      this.setHpDamage(compound.getFloat("Hpdamage"));
      this.setSize(compound.getFloat("size"));
   }
}
