package com.iafenvoy.iceandfire.entity;

import com.google.common.base.Predicate;
import com.iafenvoy.iceandfire.entity.ai.DreadAITargetNonDreadGoal;
import com.iafenvoy.iceandfire.entity.util.IAnimalFear;
import com.iafenvoy.iceandfire.entity.util.IDreadMob;
import com.iafenvoy.iceandfire.entity.util.IVillagerFear;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.AnimationHandler;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DreadBeastEntity extends DreadMobEntity implements IAnimatedEntity, IVillagerFear, IAnimalFear {
   public static final Animation ANIMATION_SPAWN = Animation.create(40);
   public static final Animation ANIMATION_BITE = Animation.create(15);
   private static final EntityDataAccessor<Float> SCALE = SynchedEntityData.defineId(DreadBeastEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(DreadBeastEntity.class, EntityDataSerializers.INT);
   private static final float INITIAL_WIDTH = 1.2F;
   private static final float INITIAL_HEIGHT = 0.9F;
   private final int hostileTicks = 0;
   private int animationTick;
   private Animation currentAnimation;
   private float firstWidth = 1.0F;
   private float firstHeight = 1.0F;

   public DreadBeastEntity(EntityType<? extends DreadBeastEntity> type, Level worldIn) {
      super(type, worldIn);
   }

   public static Builder bakeAttributes() {
      return createMobAttributes()
         .add(Attributes.MAX_HEALTH, 30.0)
         .add(Attributes.MOVEMENT_SPEED, 0.45)
         .add(Attributes.ATTACK_DAMAGE, 4.0)
         .add(Attributes.FOLLOW_RANGE, 128.0)
         .add(Attributes.ARMOR, 1.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{IDreadMob.class}));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, DragonUtils::canHostilesTarget));
      this.targetSelector
         .addGoal(
            3,
            new DreadAITargetNonDreadGoal(
               this, LivingEntity.class, false, (Predicate)entity -> entity instanceof LivingEntity && DragonUtils.canHostilesTarget(entity)
            )
         );
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VARIANT, 0);
      builder.define(SCALE, 1.0F);
   }

   public float getAgeScale() {
      return this.getSize();
   }

   public float getSize() {
      return (Float)this.entityData.get(SCALE);
   }

   public void setSize(float scale) {
      this.entityData.set(SCALE, scale);
   }

   public boolean doHurtTarget(Entity entityIn) {
      if (this.getAnimation() == NO_ANIMATION) {
         this.setAnimation(ANIMATION_BITE);
      }

      return true;
   }

   @Override
   public void aiStep() {
      super.aiStep();
      if (Math.abs(this.firstWidth - 1.2F * this.getSize()) > 0.01F || Math.abs(this.firstHeight - 0.9F * this.getSize()) > 0.01F) {
         this.firstWidth = 1.2F * this.getSize();
         this.firstHeight = 0.9F * this.getSize();
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

         this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
      }

      if (this.getTarget() != null && this.distanceTo(this.getTarget()) < 4.0F && this.hasLineOfSight(this.getTarget())) {
         if (this.getAnimation() == NO_ANIMATION) {
            this.setAnimation(ANIMATION_BITE);
         }

         this.lookAt(this.getTarget(), 360.0F, 80.0F);
         if (this.getAnimation() == ANIMATION_BITE && this.getAnimationTick() == 6) {
            this.getTarget().hurt(this.level().damageSources().mobAttack(this), (float)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
            if (this.getTarget() != null) {
               this.getTarget().knockback(0.25, this.getX() - this.getTarget().getX(), this.getZ() - this.getTarget().getZ());
            }
         }
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Variant", this.getVariant());
      compound.putFloat("DreadScale", this.getSize());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setVariant(compound.getInt("Variant"));
      this.setSize(compound.getFloat("DreadScale"));
   }

   public int getVariant() {
      return (Integer)this.entityData.get(VARIANT);
   }

   public void setVariant(int variant) {
      this.entityData.set(VARIANT, variant);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn) {
      SpawnGroupData data = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
      this.setAnimation(ANIMATION_SPAWN);
      this.setVariant(this.random.nextInt(2));
      this.setSize(0.85F + this.random.nextFloat() * 0.5F);
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
      return new Animation[]{ANIMATION_SPAWN, ANIMATION_BITE};
   }

   @Override
   public boolean shouldAnimalsFear(Entity entity) {
      return true;
   }

   @Override
   public Entity getCommander() {
      return null;
   }

   @Override
   public boolean isAlliedTo(Entity entityIn) {
      return entityIn instanceof IDreadMob || super.isAlliedTo(entityIn);
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.WOLF_GROWL;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.WOLF_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.WOLF_DEATH;
   }

   public float getVoicePitch() {
      return super.getVoicePitch() * 0.7F;
   }
}
