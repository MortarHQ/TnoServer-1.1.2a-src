package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.entity.ai.DreadAITargetNonDreadGoal;
import com.iafenvoy.iceandfire.entity.util.IAnimalFear;
import com.iafenvoy.iceandfire.entity.util.IDreadMob;
import com.iafenvoy.iceandfire.entity.util.IVillagerFear;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.AnimationHandler;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DreadGhoulEntity extends DreadMobEntity implements IAnimatedEntity, IVillagerFear, IAnimalFear {
   public static final Animation ANIMATION_SPAWN = Animation.create(40);
   public static final Animation ANIMATION_SLASH = Animation.create(25);
   private static final EntityDataAccessor<Float> SCALE = SynchedEntityData.defineId(DreadGhoulEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(DreadGhoulEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> SCREAMS = SynchedEntityData.defineId(DreadGhoulEntity.class, EntityDataSerializers.INT);
   private static final float INITIAL_WIDTH = 0.6F;
   private static final float INITIAL_HEIGHT = 1.8F;
   private int animationTick;
   private Animation currentAnimation;
   private int hostileTicks = 0;
   private float firstWidth = 1.0F;
   private float firstHeight = 1.0F;

   public DreadGhoulEntity(EntityType<? extends DreadGhoulEntity> type, Level worldIn) {
      super(type, worldIn);
   }

   public static Builder bakeAttributes() {
      return createMobAttributes()
         .add(Attributes.MAX_HEALTH, 30.0)
         .add(Attributes.MOVEMENT_SPEED, 0.35)
         .add(Attributes.ATTACK_DAMAGE, 5.0)
         .add(Attributes.FOLLOW_RANGE, 128.0)
         .add(Attributes.ARMOR, 4.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{IDreadMob.class}));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, DragonUtils::canHostilesTarget));
      this.targetSelector.addGoal(3, new DreadAITargetNonDreadGoal(this, LivingEntity.class, false, DragonUtils::canHostilesTarget));
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VARIANT, 0);
      builder.define(SCREAMS, 0);
      builder.define(SCALE, 1.0F);
   }

   public float getSize() {
      return (Float)this.entityData.get(SCALE);
   }

   public void setSize(float scale) {
      this.entityData.set(SCALE, scale);
   }

   public boolean doHurtTarget(Entity entityIn) {
      if (this.getAnimation() == NO_ANIMATION) {
         this.setAnimation(ANIMATION_SLASH);
      }

      return true;
   }

   @Override
   public void aiStep() {
      super.aiStep();
      LivingEntity attackTarget = this.getTarget();
      if (Math.abs(this.firstWidth - 0.6F * this.getSize()) > 0.01F || Math.abs(this.firstHeight - 1.8F * this.getSize()) > 0.01F) {
         this.firstWidth = 0.6F * this.getSize();
         this.firstHeight = 1.8F * this.getSize();
      }

      if (this.getAnimation() == ANIMATION_SPAWN && this.getAnimationTick() < 30) {
         BlockState belowBlock = this.level().getBlockState(this.blockPosition().below());
         if (belowBlock.getBlock() != Blocks.AIR) {
            for (int i = 0; i < 5; i++) {
               this.level()
                  .addParticle(
                     new BlockParticleOption(ParticleTypes.BLOCK, belowBlock),
                     this.getX() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
                     this.getBoundingBox().minY,
                     this.getZ() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
                     this.random.nextGaussian() * 0.02,
                     this.random.nextGaussian() * 0.02,
                     this.random.nextGaussian() * 0.02
                  );
            }
         }

         this.setDeltaMovement(0.0, this.getDeltaMovement().y, this.getDeltaMovement().z);
      }

      if (attackTarget != null && this.distanceTo(attackTarget) < 4.0F && this.hasLineOfSight(attackTarget)) {
         if (this.getAnimation() == NO_ANIMATION) {
            this.setAnimation(ANIMATION_SLASH);
         }

         this.lookAt(attackTarget, 360.0F, 80.0F);
         if (this.getAnimation() == ANIMATION_SLASH && (this.getAnimationTick() == 9 || this.getAnimationTick() == 19)) {
            attackTarget.hurt(this.level().damageSources().mobAttack(this), (float)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
            attackTarget.knockback(0.25, this.getX() - attackTarget.getX(), this.getZ() - attackTarget.getZ());
         }
      }

      if (!this.level().isClientSide) {
         if (this.getTarget() != null) {
            this.hostileTicks++;
            if (this.getScreamStage() == 0) {
               if (this.hostileTicks > 20) {
                  this.setScreamStage(1);
               }
            } else if (this.tickCount % 20 < 10) {
               this.setScreamStage(1);
            } else {
               this.setScreamStage(2);
            }
         } else {
            if (this.getScreamStage() > 0) {
               if (this.tickCount % 20 < 10 && this.getScreamStage() == 2) {
                  this.setScreamStage(1);
               } else {
                  this.setScreamStage(0);
               }
            }

            this.hostileTicks = 0;
         }
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Variant", this.getVariant());
      compound.putInt("ScreamStage", this.getScreamStage());
      compound.putFloat("DreadScale", this.getSize());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setVariant(compound.getInt("Variant"));
      this.setScreamStage(compound.getInt("ScreamStage"));
      this.setSize(compound.getFloat("DreadScale"));
   }

   public int getVariant() {
      return (Integer)this.entityData.get(VARIANT);
   }

   public void setVariant(int variant) {
      this.entityData.set(VARIANT, variant);
   }

   public int getScreamStage() {
      return (Integer)this.entityData.get(SCREAMS);
   }

   public void setScreamStage(int screamStage) {
      this.entityData.set(SCREAMS, screamStage);
   }

   public float getAgeScale() {
      return this.getSize();
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn) {
      SpawnGroupData data = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
      this.setAnimation(ANIMATION_SPAWN);
      this.setVariant(this.random.nextInt(3));
      return data;
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public Animation[] getAnimations() {
      return new Animation[]{ANIMATION_SPAWN, ANIMATION_SLASH};
   }

   @Override
   public boolean shouldAnimalsFear(Entity entity) {
      return true;
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)IafSounds.DREAD_GHOUL_IDLE.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ZOMBIE_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ZOMBIE_DEATH;
   }

   protected void playStepSound(BlockPos pos, Block blockIn) {
      this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
   }

   public float getVoicePitch() {
      return super.getVoicePitch() * 0.7F;
   }
}
