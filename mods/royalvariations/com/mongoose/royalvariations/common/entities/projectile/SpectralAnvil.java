package com.mongoose.royalvariations.common.entities.projectile;

import com.mongoose.royalvariations.client.particles.EndSplashParticleOption;
import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.common.entities.RVEntityType;
import com.mongoose.royalvariations.init.RVSounds;
import com.mongoose.royalvariations.utils.MobUtil;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class SpectralAnvil extends Entity {
   private LivingEntity owner;
   private UUID ownerUUID;
   private boolean isDropping;
   public int hovering = 0;
   public AnimationState spawnAnimationState = new AnimationState();
   public AnimationState fallAnimationState = new AnimationState();

   public SpectralAnvil(EntityType<? extends Entity> p_i50170_1_, Level p_i50170_2_) {
      super(p_i50170_1_, p_i50170_2_);
   }

   public SpectralAnvil(Level pLevel, LivingEntity pOwner) {
      this((EntityType<? extends Entity>)RVEntityType.SPECTRAL_ANVIL.get(), pLevel);
      this.owner = pOwner;
   }

   protected void defineSynchedData(Builder builder) {
   }

   protected void readAdditionalSaveData(CompoundTag pCompound) {
      if (pCompound.hasUUID("Owner")) {
         this.ownerUUID = pCompound.getUUID("Owner");
      }

      this.hovering = pCompound.getInt("hovering");
   }

   protected void addAdditionalSaveData(CompoundTag pCompound) {
      if (this.ownerUUID != null) {
         pCompound.putUUID("Owner", this.ownerUUID);
      }

      pCompound.putInt("hovering", this.hovering);
   }

   public void setOwner(@Nullable LivingEntity p_190549_1_) {
      this.owner = p_190549_1_;
      this.ownerUUID = p_190549_1_ == null ? null : p_190549_1_.getUUID();
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel serverLevel) {
         Entity entity = serverLevel.getEntity(this.ownerUUID);
         if (entity instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
         }
      }

      return this.owner;
   }

   private void onHit() {
      if (!this.level().isClientSide()) {
         if (this.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 20; i++) {
               double d0 = this.random.nextGaussian() * 0.02;
               double d1 = this.random.nextGaussian() * 0.02;
               double d2 = this.random.nextGaussian() * 0.02;
               double d3 = 10.0;
               serverLevel.sendParticles(
                  (SimpleParticleType)RVParticleTypes.END_POOF.get(),
                  this.getX(1.0) - d0 * d3,
                  this.getRandomY() - d1 * d3,
                  this.getRandomZ(1.0) - d2 * d3,
                  0,
                  d0,
                  d1,
                  d2,
                  1.0
               );
            }

            serverLevel.sendParticles(new EndSplashParticleOption(4.0F, 2), this.getX(), this.getY() + 0.5, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
         }

         if (this.isDropping) {
            for (LivingEntity livingEntity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0), this::canHitEntity)) {
               this.damageTargets(livingEntity);
            }
         }

         this.playSound((SoundEvent)RVSounds.END_SPLASH.get(), 2.0F, 1.0F);
         this.playSound(SoundEvents.ANVIL_LAND, 0.5F, 1.0F);
      }

      this.discard();
   }

   public void damageTargets(LivingEntity livingEntity) {
      if (livingEntity != null && (this.getOwner() != null && !MobUtil.areAllies(this.getOwner(), livingEntity) || this.getOwner() == null)) {
         boolean flag = false;
         int duration = 140;
         if (!(this.getOwner() instanceof Player player && (!(livingEntity instanceof OwnableEntity ownable) || ownable.getOwner() != player))) {
            if (livingEntity instanceof Player || livingEntity.hasEffect(RVEffects.PRESSING_GAZE)) {
               flag = true;
            }
         } else {
            duration = 200;
            flag = true;
         }

         if (flag && livingEntity.hurt(this.damageSources().indirectMagic(this, this.getOwner()), 8.0F)) {
            livingEntity.addEffect(new MobEffectInstance(RVEffects.HEAVINESS_OF_THE_END, duration));
         }
      }
   }

   public void makeStuckInBlock(BlockState p_20006_, Vec3 p_20007_) {
      super.makeStuckInBlock(p_20006_, Vec3.ZERO);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         this.hovering++;
         HitResult result = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
         if (result.getType() != Type.MISS) {
            if (result.getType() == Type.ENTITY) {
               EntityHitResult result1 = (EntityHitResult)result;
               if (result1.getEntity() instanceof LivingEntity) {
                  this.damageTargets((LivingEntity)result1.getEntity());
               }
            }

            this.onHit();
         }

         if (this.onGround() || this.isInWall() || this.verticalCollision || this.horizontalCollision) {
            this.onHit();
         }
      } else {
         this.hovering++;
      }

      if (this.hovering == 1) {
         if (this.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 20; i++) {
               double d0 = this.random.nextGaussian() * 0.02;
               double d1 = this.random.nextGaussian() * 0.02;
               double d2 = this.random.nextGaussian() * 0.02;
               double d3 = 10.0;
               serverLevel.sendParticles(
                  (SimpleParticleType)RVParticleTypes.END_POOF.get(),
                  this.getX() - d0 * d3,
                  this.getRandomY() - d1 * d3,
                  this.getZ() - d2 * d3,
                  0,
                  d0,
                  d1,
                  d2,
                  1.0
               );
            }

            this.playSound(SoundEvents.EVOKER_CAST_SPELL, 2.0F, 1.0F);
         } else {
            this.spawnAnimationState.startIfStopped(this.tickCount);
         }
      }

      int hoverTime = 20;
      if (this.getOwner() instanceof Player) {
         hoverTime = 15;
      }

      this.isDropping = this.hovering > hoverTime;
      if (!this.isDropping) {
         this.setDeltaMovement(Vec3.ZERO);
      } else {
         this.setDeltaMovement(this.getDeltaMovement().subtract(0.0, 0.25, 0.0));
         if (this.level().isClientSide) {
            this.spawnAnimationState.stop();
            this.fallAnimationState.startIfStopped(this.tickCount);
         }
      }

      this.move(MoverType.SELF, this.getDeltaMovement());
   }

   public boolean isAttackable() {
      return false;
   }

   protected MovementEmission getMovementEmission() {
      return MovementEmission.NONE;
   }

   public boolean isPickable() {
      return !this.isRemoved();
   }

   public boolean displayFireAnimation() {
      return false;
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   protected boolean canHitEntity(Entity entity) {
      if (!entity.isSpectator() && entity.isAlive() && entity.isPickable() && !entity.noPhysics) {
         Entity owner = this.getOwner();
         return owner == null || !owner.isPassengerOfSameVehicle(entity);
      } else {
         return false;
      }
   }
}
