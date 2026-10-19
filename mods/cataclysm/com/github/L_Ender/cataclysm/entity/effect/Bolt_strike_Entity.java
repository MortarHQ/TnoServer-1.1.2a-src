package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.client.particle.Options.LightningParticleOptions;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Bolt_strike_Entity extends Entity {
   private int warmupDelayTicks = 34;
   private boolean sentSpikeEvent;
   public int lifeTicks;
   private boolean clientSideAttackStarted;
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Bolt_strike_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Float> HPDAMAGE = SynchedEntityData.defineId(Bolt_strike_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> R = SynchedEntityData.defineId(Bolt_strike_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> G = SynchedEntityData.defineId(Bolt_strike_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> B = SynchedEntityData.defineId(Bolt_strike_Entity.class, EntityDataSerializers.INT);
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerUUID;

   public Bolt_strike_Entity(EntityType<? extends Bolt_strike_Entity> entityType, Level level) {
      super(entityType, level);
   }

   public Bolt_strike_Entity(Level worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, float damage, LivingEntity casterIn) {
      this((EntityType<? extends Bolt_strike_Entity>)ModEntities.BOLT_STRIKE.get(), worldIn);
      this.warmupDelayTicks = p_i47276_9_;
      this.setCaster(casterIn);
      this.setDamage(damage);
      this.setYRot(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.setPos(x, y, z);
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(DAMAGE, 0.0F);
      p_326229_.define(HPDAMAGE, 0.0F);
      p_326229_.define(R, 0);
      p_326229_.define(G, 0);
      p_326229_.define(B, 0);
   }

   public int getR() {
      return (Integer)this.entityData.get(R);
   }

   public void setR(int r) {
      this.entityData.set(R, r);
   }

   public int getG() {
      return (Integer)this.entityData.get(G);
   }

   public void setG(int g) {
      this.entityData.set(G, g);
   }

   public int getB() {
      return (Integer)this.entityData.get(B);
   }

   public void setB(int b) {
      this.entityData.set(B, b);
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

   public boolean shouldRenderAtSqrDistance(double distance) {
      double d0 = 64.0 * getViewScale();
      return distance < d0 * d0;
   }

   public Vec3 getAnglePosition(float p_20347_, double height, double Maxradius, double Minradius) {
      double angle = this.random.nextFloat() * 2.0F * Math.PI;
      double radius = Minradius + this.random.nextDouble() * (Maxradius - Minradius);
      double randomX = radius * Math.cos(angle);
      double randomZ = radius * Math.sin(angle);
      return this.getPosition(p_20347_).add(randomX, height, randomZ);
   }

   public float getAnimationProgress(float partialTicks) {
      if (!this.clientSideAttackStarted) {
         return 0.0F;
      } else {
         int i = this.lifeTicks;
         if (i >= 12 && i < 18) {
            return 0.3F;
         } else {
            return i >= 18 ? Math.max(0.3F - (i - 18 + partialTicks) / 20.0F, 0.0F) : 0.0F;
         }
      }
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         if (this.clientSideAttackStarted) {
            this.lifeTicks++;
            if (this.lifeTicks < 24 && this.lifeTicks > 12) {
               this.smolder(6);
            }

            if (this.lifeTicks == 12 && !this.isSilent()) {
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
         }
      } else if (--this.warmupDelayTicks < 0) {
         if (this.warmupDelayTicks == -12) {
            this.damageEntityLivingBaseNearby(1.0);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.1F, 0, 30);
         }

         if (!this.sentSpikeEvent) {
            this.level().broadcastEntityEvent(this, (byte)4);
            this.sentSpikeEvent = true;
         }

         if (++this.lifeTicks > 22) {
            this.discard();
         }
      }
   }

   public void damageEntityLivingBaseNearby(double radius) {
      AABB region = new AABB(
         this.getX() - radius, this.getY() - 0.5, this.getZ() - radius, this.getX() + radius, this.level().getMaxBuildHeight() + 20, this.getZ() + radius
      );

      for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, region)) {
         this.damage(entity);
      }
   }

   private void damage(LivingEntity Hitentity) {
      LivingEntity livingentity = this.getCaster();
      if (Hitentity.isAlive() && !Hitentity.isInvulnerable() && Hitentity != livingentity) {
         if (livingentity == null) {
            Hitentity.hurt(this.damageSources().magic(), this.getDamage());
         } else if (!livingentity.isAlliedTo(Hitentity) && !Hitentity.isAlliedTo(livingentity)) {
            Hitentity.hurt(this.damageSources().indirectMagic(this, livingentity), this.getDamage());
         }
      }
   }

   public void handleEntityEvent(byte p_36935_) {
      super.handleEntityEvent(p_36935_);
      if (p_36935_ == 4) {
         this.clientSideAttackStarted = true;
      }
   }

   private void smolder(int amount) {
      for (int i = 0; i < amount; i++) {
         float velocity = 1.5F;
         float yaw = (float)(this.random.nextFloat() * 2.0F * Math.PI);
         float r = this.random.nextFloat() * 0.7F;
         float x = r * Mth.cos(yaw);
         float z = r * Mth.sin(yaw);
         float motionY = this.random.nextFloat() * 0.8F;
         float motionX = 1.5F * Mth.cos(yaw);
         float motionZ = 1.5F * Mth.sin(yaw);
         this.level()
            .addParticle(
               new LightningParticleOptions(this.getR(), this.getG(), this.getB()),
               this.getX() + x,
               this.getY() + 0.1,
               this.getZ() + z,
               motionX,
               motionY,
               motionZ
            );
      }
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      if (this.ownerUUID != null) {
         compound.putUUID("Owner", this.ownerUUID);
      }

      compound.putFloat("damage", this.getDamage());
      compound.putInt("r", this.getR());
      compound.putInt("g", this.getG());
      compound.putInt("b", this.getB());
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      if (compound.hasUUID("Owner")) {
         this.ownerUUID = compound.getUUID("Owner");
      }

      this.setDamage(compound.getFloat("damage"));
      this.setR(compound.getInt("r"));
      this.setG(compound.getInt("g"));
      this.setB(compound.getInt("b"));
   }
}
