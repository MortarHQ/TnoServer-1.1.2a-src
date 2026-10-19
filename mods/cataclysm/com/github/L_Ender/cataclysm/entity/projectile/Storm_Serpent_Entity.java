package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.client.particle.Options.CircleLightningParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.NotSpinTrailParticleOptions;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class Storm_Serpent_Entity extends Entity {
   private int warmupDelayTicks;
   private boolean sentSpikeEvent;
   public int lifeTicks;
   private boolean clientSideAttackStarted;
   private LivingEntity caster;
   private UUID casterUuid;
   @Nullable
   private Entity finalTarget;
   @Nullable
   private UUID targetId;
   private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(Storm_Serpent_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Storm_Serpent_Entity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Boolean> RIGHT = SynchedEntityData.defineId(Storm_Serpent_Entity.class, EntityDataSerializers.BOOLEAN);
   public AnimationState SpawnAnimationState = new AnimationState();
   public AnimationState Spawn2AnimationState = new AnimationState();

   public Storm_Serpent_Entity(EntityType<? extends Storm_Serpent_Entity> p_i50170_1_, Level p_i50170_2_) {
      super(p_i50170_1_, p_i50170_2_);
   }

   public Storm_Serpent_Entity(
      Level worldIn,
      double x,
      double y,
      double z,
      float p_i47276_8_,
      int p_i47276_9_,
      LivingEntity casterIn,
      float damage,
      LivingEntity finalTarget,
      boolean right
   ) {
      this((EntityType<? extends Storm_Serpent_Entity>)ModEntities.STORM_SERPENT.get(), worldIn);
      this.warmupDelayTicks = p_i47276_9_;
      this.setCaster(casterIn);
      this.finalTarget = finalTarget;
      this.setYRot(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.setDamage(damage);
      this.setPos(x, y, z);
      this.setRight(right);
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(STATE, 0);
      p_326229_.define(DAMAGE, 0.0F);
      p_326229_.define(RIGHT, false);
   }

   public AnimationState getAnimationState(String input) {
      if (input == "spawn") {
         return this.SpawnAnimationState;
      } else {
         return input == "spawn2" ? this.Spawn2AnimationState : new AnimationState();
      }
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
      if (STATE.equals(p_21104_) && this.level().isClientSide) {
         switch (this.getState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.SpawnAnimationState.startIfStopped(this.tickCount);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.Spawn2AnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.SpawnAnimationState.stop();
      this.Spawn2AnimationState.stop();
   }

   public int getState() {
      return (Integer)this.entityData.get(STATE);
   }

   public void setState(int state) {
      this.entityData.set(STATE, state);
   }

   public boolean getRight() {
      return (Boolean)this.entityData.get(RIGHT);
   }

   public void setRight(boolean right) {
      this.entityData.set(RIGHT, right);
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
      this.warmupDelayTicks = compound.getInt("Warmup");
      if (compound.hasUUID("Owner")) {
         this.casterUuid = compound.getUUID("Owner");
      }

      if (compound.hasUUID("Target")) {
         this.targetId = compound.getUUID("Target");
      }

      this.setDamage(compound.getFloat("Damage"));
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      if (this.finalTarget != null) {
         compound.putUUID("Target", this.finalTarget.getUUID());
      }

      compound.putInt("Warmup", this.warmupDelayTicks);
      if (this.casterUuid != null) {
         compound.putUUID("Owner", this.casterUuid);
      }

      compound.putFloat("Damage", this.getDamage());
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         if (this.clientSideAttackStarted) {
            this.lifeTicks++;
            if (this.lifeTicks == 10) {
               for (int i = 0; i < 12; i++) {
                  float angle = (float) (Math.PI / 180.0) * this.getYRot() + i;
                  float r = 0.5F + this.random.nextFloat() * 0.9F;
                  float velocity = r + this.random.nextFloat() * 4.0F;
                  float x = r * Mth.sin((float)(Math.PI + angle));
                  float z = r * Mth.cos(angle);
                  double d0 = this.getX() + x;
                  double d1 = this.getY() + 0.1;
                  double d2 = this.getZ() + z;
                  double extraX = d0 + velocity * 0.4F * Mth.sin((float)(Math.PI + angle));
                  double extraY = d1 + 0.3F + this.random.nextFloat() * 2.0F;
                  double extraZ = d2 + velocity * 0.4F * Mth.cos(angle);
                  this.level()
                     .addParticle(
                        new NotSpinTrailParticleOptions(0.44313726F, 0.7607843F, 0.9411765F, 0.05F, 0.75F, 0.5F, 0.0, 60 + this.random.nextInt(40)),
                        d0,
                        d1,
                        d2,
                        extraX,
                        extraY,
                        extraZ
                     );
               }
            }

            if (this.lifeTicks > 12 && this.lifeTicks < 18) {
               for (int i = 0; i < 5; i++) {
                  this.level()
                     .addParticle(
                        new CircleLightningParticleOptions(0.1F, 113, 194, 240),
                        this.getX(),
                        this.getY() + 0.1,
                        this.getZ(),
                        this.getX() + (this.random.nextFloat() - 0.5F) * 7.0F,
                        this.getY() + 0.1,
                        this.getZ() + (this.random.nextFloat() - 0.5F) * 7.0F
                     );
               }
            }

            if (this.lifeTicks == 52) {
               for (int i = 0; i < 12; i++) {
                  double theta = Math.toRadians(this.getYRot());
                  double vecX = Math.cos(theta) * 8.0;
                  double vecZ = Math.sin(theta) * 8.0;
                  float angle = (float) (Math.PI / 180.0) * this.getYRot() + i;
                  float r = 0.5F + this.random.nextFloat() * 0.9F;
                  float velocity = r + this.random.nextFloat() * 4.0F;
                  float x = (float)(vecX + r * Mth.sin((float)(Math.PI + angle)));
                  float z = (float)(vecZ + r * Mth.cos(angle));
                  double d0 = this.getX() + x;
                  double d1 = this.getY() + 0.1;
                  double d2 = this.getZ() + z;
                  double extraX = d0 + velocity * 0.6F * Mth.sin((float)(Math.PI + angle));
                  double extraY = d1 + 0.3F + this.random.nextFloat() * 1.2F;
                  double extraZ = d2 + velocity * 0.6F * Mth.cos(angle);
                  this.level()
                     .addParticle(
                        new NotSpinTrailParticleOptions(0.44313726F, 0.7607843F, 0.9411765F, 0.05F, 0.75F, 0.5F, 0.0, 80 + this.random.nextInt(40)),
                        d0,
                        d1,
                        d2,
                        extraX,
                        extraY,
                        extraZ
                     );
               }
            }

            if (this.lifeTicks > 52 && this.lifeTicks < 56) {
               for (int i = 0; i < 5; i++) {
                  double theta = Math.toRadians(this.getYRot());
                  double vecX = Math.cos(theta) * 8.0;
                  double vecZ = Math.sin(theta) * 8.0;
                  this.level()
                     .addParticle(
                        new CircleLightningParticleOptions(0.1F, 113, 194, 240),
                        this.getX() + vecX,
                        this.getY() + 0.1,
                        this.getZ() + vecZ,
                        this.getX() + vecX + (this.random.nextFloat() - 0.5F) * 7.0F,
                        this.getY() + 0.1,
                        this.getZ() + vecZ + (this.random.nextFloat() - 0.5F) * 7.0F
                     );
               }
            }
         }
      } else if (--this.warmupDelayTicks < 0) {
         if (this.warmupDelayTicks == -10 && this.getState() == 0) {
            this.setState(!this.getRight() ? 1 : 2);
         }

         if (this.finalTarget == null && this.targetId != null) {
            this.finalTarget = ((ServerLevel)this.level()).getEntity(this.targetId);
            if (this.finalTarget == null) {
               this.targetId = null;
            }
         }

         if (this.finalTarget != null && this.finalTarget.isAlive() && this.warmupDelayTicks > -48) {
            this.lookAt(this.finalTarget, 30.0F, 0.0F);
         }

         if (this.warmupDelayTicks == -52 || this.warmupDelayTicks == -53 || this.warmupDelayTicks == -54 || this.warmupDelayTicks == -55) {
            double theta = Math.toRadians(this.getYRot());
            double vecX = this.getX() + Math.cos(theta) * 8.0;
            double vecZ = this.getZ() + Math.sin(theta) * 8.0;
            AABB selection = new AABB(vecX - 1.5, this.getY() - 2.0, vecZ - 1.5, vecX + 1.5, this.getY() + this.getBbHeight(), vecZ + 1.5);

            for (LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, selection)) {
               this.damage(livingentity);
            }

            Vec3 vec3 = new Vec3(vecX, this.getY(), vecZ);
            ScreenShake_Entity.ScreenShake(this.level(), vec3, 10.0F, 0.07F, 0, 20);
         }

         if (!this.sentSpikeEvent) {
            this.level().broadcastEntityEvent(this, (byte)4);
            this.sentSpikeEvent = true;
         }

         if (++this.lifeTicks > 70) {
            this.discard();
         }
      }
   }

   protected void lookAt(Entity entity, float maxYRotIncrease, float maxXRotIncrease) {
      double d0 = entity.getX() - this.getX();
      double d2 = entity.getZ() - this.getZ();
      double d1;
      if (entity instanceof LivingEntity livingentity) {
         d1 = livingentity.getEyeY() - this.getEyeY();
      } else {
         d1 = (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0 - this.getEyeY();
      }

      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      float f = (float)(Mth.atan2(d2, d0) * 180.0 / (float) Math.PI);
      float f1 = (float)(-(Mth.atan2(d1, d3) * 180.0 / (float) Math.PI));
      this.setXRot(this.rotlerp(this.getXRot(), f1, maxXRotIncrease));
      this.setYRot(this.rotlerp(this.getYRot(), f, maxYRotIncrease));
   }

   private float rotlerp(float angle, float targetAngle, float maxIncrease) {
      float f = Mth.wrapDegrees(targetAngle - angle);
      if (f > maxIncrease) {
         f = maxIncrease;
      }

      if (f < -maxIncrease) {
         f = -maxIncrease;
      }

      return angle + f;
   }

   protected void damage(LivingEntity Hitentity) {
      LivingEntity livingentity = this.getCaster();
      if (Hitentity.isAlive() && !Hitentity.isInvulnerable() && Hitentity != livingentity) {
         if (livingentity == null) {
            Hitentity.hurt(this.damageSources().magic(), this.getDamage());
         } else if (!livingentity.isAlliedTo(Hitentity) && !Hitentity.isAlliedTo(livingentity)) {
            boolean flag = Hitentity.hurt(this.damageSources().indirectMagic(this, livingentity), this.getDamage());
            if (flag) {
               MobEffectInstance effectinstance = new MobEffectInstance(ModEffect.EFFECTWETNESS, 150, 4, false, true, true);
               Hitentity.addEffect(effectinstance);
            }
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void handleEntityEvent(byte id) {
      super.handleEntityEvent(id);
      if (id == 4) {
         this.clientSideAttackStarted = true;
         if (!this.isSilent()) {
         }
      }
   }

   public float getAnimationProgress(float p_36937_) {
      if (!this.clientSideAttackStarted) {
         return 0.0F;
      }

      int i = this.lifeTicks - 2;
      return i <= 0 ? 1.0F : 1.0F - (i - p_36937_) / 20.0F;
   }
}
