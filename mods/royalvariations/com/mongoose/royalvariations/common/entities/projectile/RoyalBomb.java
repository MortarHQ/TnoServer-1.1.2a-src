package com.mongoose.royalvariations.common.entities.projectile;

import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.common.entities.RVEntityType;
import com.mongoose.royalvariations.init.RVSounds;
import com.mongoose.royalvariations.utils.CustomExplosion;
import javax.annotation.Nullable;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.Vec3;

public class RoyalBomb extends Mob implements TraceableEntity {
   private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(RoyalBomb.class, EntityDataSerializers.INT);
   @Nullable
   private LivingEntity owner;
   public AnimationState startAnimationState = new AnimationState();

   public RoyalBomb(EntityType<? extends RoyalBomb> p_32076_, Level p_32077_) {
      super(p_32076_, p_32077_);
      this.blocksBuilding = true;
   }

   public RoyalBomb(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
      this((EntityType<? extends RoyalBomb>)RVEntityType.ROYAL_BOMB.get(), p_32079_);
      this.setPos(p_32080_, p_32081_, p_32082_);
      this.setFuse(40);
      this.xo = p_32080_;
      this.yo = p_32081_;
      this.zo = p_32082_;
      this.owner = p_32083_;
   }

   public RoyalBomb(Level p_32079_, Vec3 vec3, @Nullable LivingEntity p_32083_) {
      this(p_32079_, vec3.x, vec3.y, vec3.z, p_32083_);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_FUSE_ID, 80);
   }

   public boolean isInvulnerable() {
      return true;
   }

   public boolean isInvulnerableTo(DamageSource p_20122_) {
      return true;
   }

   public boolean attackable() {
      return false;
   }

   protected MovementEmission getMovementEmission() {
      return MovementEmission.NONE;
   }

   public boolean isPickable() {
      return !this.isRemoved();
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         this.startAnimationState.startIfStopped(this.tickCount);
      } else if (this.level() instanceof ServerLevel serverLevel && this.tickCount == 1) {
         for (int i = 0; i < 5; i++) {
            double d0 = serverLevel.random.nextGaussian() * 0.02;
            double d1 = serverLevel.random.nextGaussian() * 0.02;
            double d2 = serverLevel.random.nextGaussian() * 0.02;
            serverLevel.sendParticles(
               (SimpleParticleType)RVParticleTypes.GOLDEN_TRAIL.get(), this.getRandomX(1.0), this.getRandomY(), this.getRandomZ(1.0), 0, d0, d1, d2, 0.5
            );
         }
      }

      if (this.tickCount == 1) {
         this.playSound((SoundEvent)RVSounds.ROYAL_BOMB.get());
      }

      int i = this.getFuse() - 1;
      this.setFuse(i);
      if (i <= 0) {
         this.discard();
         if (!this.level().isClientSide) {
            this.explode();
         }
      } else {
         this.updateInWaterStateAndDoFluidPushing();
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles((SimpleParticleType)RVParticleTypes.SPARKLE.get(), this.getX(), this.getY() + 0.9, this.getZ(), 1, 0.2, 0.2, 0.2, 0.1);
         }
      }
   }

   protected void explode() {
      float f = 9.0F;
      this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), f, ExplosionInteraction.TNT);
      new CustomExplosion(this.level(), this, this.damageSources().explosion(this, this), this.getX(), this.getY(), this.getZ(), 10.0F + f, 1.0F) {
         @Override
         public void explodeHurt(Entity target, DamageSource damageSource, double x, double y, double z, double seen, float actualDamage) {
            if (target instanceof LivingEntity livingEntity && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target) && target != RoyalBomb.this) {
               livingEntity.addEffect(new MobEffectInstance(RVEffects.DAZED, 200));
            }
         }
      };
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putShort("Fuse", (short)this.getFuse());
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.setFuse(compoundTag.getShort("Fuse"));
   }

   @Nullable
   public LivingEntity getOwner() {
      return this.owner;
   }

   protected float getStandingEyeHeight(Pose p_21131_, EntityDimensions p_21132_) {
      return 0.15F;
   }

   public void setFuse(int p_32086_) {
      this.entityData.set(DATA_FUSE_ID, p_32086_);
   }

   public int getFuse() {
      return (Integer)this.entityData.get(DATA_FUSE_ID);
   }
}
