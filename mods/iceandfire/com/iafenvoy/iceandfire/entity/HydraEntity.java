package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.util.BlacklistedFromStatues;
import com.iafenvoy.iceandfire.entity.util.IAnimalFear;
import com.iafenvoy.iceandfire.entity.util.IHasCustomizableAttributes;
import com.iafenvoy.iceandfire.entity.util.IMultipartEntity;
import com.iafenvoy.iceandfire.entity.util.IVillagerFear;
import com.iafenvoy.iceandfire.entity.util.IafEntityUtil;
import com.iafenvoy.iceandfire.entity.util.dragon.DragonUtils;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class HydraEntity extends Monster implements IAnimatedEntity, IMultipartEntity, IVillagerFear, IAnimalFear, IHasCustomizableAttributes {
   public static final int HEADS = 9;
   public static final double HEAD_HEALTH_THRESHOLD = 20.0;
   private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(HydraEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> HEAD_COUNT = SynchedEntityData.defineId(HydraEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> SEVERED_HEAD = SynchedEntityData.defineId(HydraEntity.class, EntityDataSerializers.INT);
   private static final float[][] ROTATE = new float[][]{
      {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {10.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {10.0F, 0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {25.0F, 10.0F, -10.0F, -25.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {30.0F, 15.0F, 0.0F, -15.0F, -30.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {40.0F, 25.0F, 5.0F, -5.0F, -25.0F, -40.0F, 0.0F, 0.0F, 0.0F},
      {40.0F, 30.0F, 15.0F, 0.0F, -15.0F, -30.0F, -40.0F, 0.0F, 0.0F},
      {45.0F, 30.0F, 20.0F, 5.0F, -5.0F, -20.0F, -30.0F, -45.0F, 0.0F},
      {50.0F, 37.0F, 25.0F, 15.0F, 0.0F, -15.0F, -25.0F, -37.0F, -50.0F}
   };
   public final boolean[] isStriking = new boolean[9];
   public final float[] strikingProgress = new float[9];
   public final float[] prevStrikeProgress = new float[9];
   public final boolean[] isBreathing = new boolean[9];
   public final float[] speakingProgress = new float[9];
   public final float[] prevSpeakingProgress = new float[9];
   public final float[] breathProgress = new float[9];
   public final float[] prevBreathProgress = new float[9];
   public final int[] breathTicks = new int[9];
   public final float[] headDamageTracker = new float[9];
   private final float headDamageThreshold;
   private int animationTick;
   private Animation currentAnimation;
   private HydraHeadEntity[] headBoxes = new HydraHeadEntity[81];
   private int strikeCooldown = 0;
   private int breathCooldown = 0;
   private int lastHitHead = 0;
   private int prevHeadCount = -1;
   private int regrowHeadCooldown = 0;
   private boolean onlyRegrowOneHeadNotTwo = false;
   private boolean multipartLoaded = false;

   public HydraEntity(EntityType<HydraEntity> type, Level worldIn) {
      super(type, worldIn);
      this.headDamageThreshold = Math.max(5.0F, ((Double)IafCommonConfig.INSTANCE.hydra.maxHealth.getValue()).floatValue() * 0.08F);
   }

   public static Builder bakeAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, (Double)IafCommonConfig.INSTANCE.hydra.maxHealth.getValue())
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.ATTACK_DAMAGE, 3.0)
         .add(Attributes.ARMOR, 1.0);
   }

   @Override
   public void setConfigurableAttributes() {
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)IafCommonConfig.INSTANCE.hydra.maxHealth.getValue());
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector
         .addGoal(
            3,
            new NearestAttackableTargetGoal(
               this,
               LivingEntity.class,
               10,
               true,
               false,
               entity -> DragonUtils.isAlive(entity) && !(entity instanceof Enemy)
                  || entity instanceof BlacklistedFromStatues blacklisted && blacklisted.canBeTurnedToStone()
            )
         );
   }

   public boolean doHurtTarget(Entity entityIn) {
      return false;
   }

   public void aiStep() {
      super.aiStep();
      LivingEntity attackTarget = this.getTarget();
      if (attackTarget != null && this.hasLineOfSight(attackTarget)) {
         int index = this.random.nextInt(this.getHeadCount());
         if (!this.isBreathing[index] && !this.isStriking[index]) {
            if (this.distanceTo(attackTarget) < 6.0F) {
               if (this.strikeCooldown == 0 && this.strikingProgress[index] == 0.0F) {
                  this.isBreathing[index] = false;
                  this.isStriking[index] = true;
                  this.level().broadcastEntityEvent(this, (byte)(40 + index));
                  this.strikeCooldown = 3;
               }
            } else if (this.random.nextBoolean() && this.breathCooldown == 0) {
               this.isBreathing[index] = true;
               this.isStriking[index] = false;
               this.level().broadcastEntityEvent(this, (byte)(50 + index));
               this.breathCooldown = 15;
            }
         }
      }

      for (int i = 0; i < 9; i++) {
         boolean striking = this.isStriking[i];
         boolean breathing = this.isBreathing[i];
         this.prevStrikeProgress[i] = this.strikingProgress[i];
         if (striking && this.strikingProgress[i] > 9.0F) {
            this.isStriking[i] = false;
            if (attackTarget != null && this.distanceTo(attackTarget) < 6.0F) {
               attackTarget.hurt(this.level().damageSources().mobAttack(this), (float)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
               attackTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 3, false, false));
               attackTarget.knockback(0.25, this.getX() - attackTarget.getX(), this.getZ() - attackTarget.getZ());
            }
         }

         if (breathing) {
            if (this.tickCount % 7 == 0 && attackTarget != null && i < this.getHeadCount()) {
               Vec3 Vector3d = this.getViewVector(1.0F);
               if (this.random.nextFloat() < 0.2F) {
                  this.playSound((SoundEvent)IafSounds.HYDRA_SPIT.get(), this.getSoundVolume(), this.getVoicePitch());
               }

               double headPosX = this.headBoxes[i].getX() + Vector3d.x;
               double headPosY = this.headBoxes[i].getY() + 1.3F;
               double headPosZ = this.headBoxes[i].getZ() + Vector3d.z;
               double d2 = attackTarget.getX() - headPosX + this.random.nextGaussian() * 0.4;
               double d3 = attackTarget.getY() + attackTarget.getEyeHeight() - headPosY + this.random.nextGaussian() * 0.4;
               double d4 = attackTarget.getZ() - headPosZ + this.random.nextGaussian() * 0.4;
               HydraBreathEntity entitylargefireball = new HydraBreathEntity(
                  (EntityType<? extends Fireball>)IafEntities.HYDRA_BREATH.get(), this.level(), this, d2, d3, d4
               );
               entitylargefireball.setPos(headPosX, headPosY, headPosZ);
               if (!this.level().isClientSide) {
                  this.level().addFreshEntity(entitylargefireball);
               }
            }

            if (this.isBreathing[i] && (attackTarget == null || !attackTarget.isAlive() || this.breathTicks[i] > 60) && !this.level().isClientSide) {
               this.isBreathing[i] = false;
               this.breathTicks[i] = 0;
               this.breathCooldown = 15;
               this.level().broadcastEntityEvent(this, (byte)(70 + i));
            }

            this.breathTicks[i]++;
         } else {
            this.breathTicks[i] = 0;
         }

         if (striking && this.strikingProgress[i] < 10.0F) {
            this.strikingProgress[i] = this.strikingProgress[i] + 2.5F;
         } else if (!striking && this.strikingProgress[i] > 0.0F) {
            this.strikingProgress[i] = this.strikingProgress[i] - 2.5F;
         }

         this.prevSpeakingProgress[i] = this.speakingProgress[i];
         if (this.speakingProgress[i] > 0.0F) {
            this.speakingProgress[i] = this.speakingProgress[i] - 0.1F;
         }

         this.prevBreathProgress[i] = this.breathProgress[i];
         if (breathing && this.breathProgress[i] < 10.0F) {
            this.breathProgress[i]++;
         } else if (!breathing && this.breathProgress[i] > 0.0F) {
            this.breathProgress[i]--;
         }
      }

      if (this.strikeCooldown > 0) {
         this.strikeCooldown--;
      }

      if (this.breathCooldown > 0) {
         this.breathCooldown--;
      }

      if (this.getHeadCount() == 1 && this.getSeveredHead() != -1) {
         this.setSeveredHead(-1);
      }

      if (this.getHeadCount() == 1 && !this.isOnFire()) {
         this.setHeadCount(2);
         this.setSeveredHead(1);
         this.onlyRegrowOneHeadNotTwo = true;
      }

      if (this.getSeveredHead() != -1 && this.getSeveredHead() < this.getHeadCount()) {
         this.setSeveredHead(Mth.clamp(this.getSeveredHead(), 0, this.getHeadCount() - 1));
         this.regrowHeadCooldown++;
         if (this.regrowHeadCooldown >= 100) {
            this.headDamageTracker[this.getSeveredHead()] = 0.0F;
            this.setSeveredHead(-1);
            if (this.isOnFire()) {
               this.setHeadCount(this.getHeadCount() - 1);
            } else {
               this.playSound((SoundEvent)IafSounds.HYDRA_REGEN_HEAD.get(), this.getSoundVolume(), this.getVoicePitch());
               if (!this.onlyRegrowOneHeadNotTwo) {
                  this.setHeadCount(this.getHeadCount() + 1);
               }
            }

            this.onlyRegrowOneHeadNotTwo = false;
            this.regrowHeadCooldown = 0;
         }
      } else {
         this.regrowHeadCooldown = 0;
      }
   }

   public void resetParts() {
      this.clearParts();
      this.headBoxes = new HydraHeadEntity[18];

      for (int i = 0; i < this.getHeadCount(); i++) {
         this.headBoxes[i] = new HydraHeadEntity(this, 3.2F, ROTATE[this.getHeadCount() - 1][i] * 1.1F, 1.0F, 0.75F, 1.75F, 1.0F, i, false);
         this.headBoxes[9 + i] = new HydraHeadEntity(this, 2.1F, ROTATE[this.getHeadCount() - 1][i] * 1.1F, 1.0F, 0.75F, 0.75F, 1.0F, i, true);
         this.headBoxes[i].copyPosition(this);
         this.headBoxes[9 + i].copyPosition(this);
         this.headBoxes[i].setParent(this);
         this.headBoxes[9 + i].setParent(this);
         this.level().addFreshEntity(this.headBoxes[i]);
         this.level().addFreshEntity(this.headBoxes[9 + i]);
      }

      this.multipartLoaded = true;
   }

   public void tick() {
      super.tick();
      if (!this.multipartLoaded || this.prevHeadCount != this.getHeadCount()) {
         this.resetParts();
      }

      float partY = 1.0F - this.walkAnimation.speed() * 0.5F;

      for (int i = 0; i < this.getHeadCount(); i++) {
         this.headBoxes[i].setPos(this.headBoxes[i].getX(), this.getY() + partY, this.headBoxes[i].getZ());
         IafEntityUtil.updatePart(this.headBoxes[i], this);
         this.headBoxes[9 + i].setPos(this.headBoxes[9 + i].getX(), this.getY() + partY, this.headBoxes[9 + i].getZ());
         IafEntityUtil.updatePart(this.headBoxes[10], this);
      }

      if (this.getHeadCount() > 1 && !this.isOnFire() && this.getHealth() < this.getMaxHealth() && this.tickCount % 30 == 0) {
         int level = this.getHeadCount() - 1;
         if (this.getSeveredHead() != -1) {
            level--;
         }

         this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 30, level, false, false));
      }

      if (this.isOnFire()) {
         this.removeEffect(MobEffects.REGENERATION);
      }

      this.prevHeadCount = this.getHeadCount();
   }

   private void clearParts() {
      for (Entity entity : this.headBoxes) {
         if (entity != null) {
            entity.remove(RemovalReason.DISCARDED);
         }
      }

      this.multipartLoaded = false;
   }

   public void remove(RemovalReason reason) {
      this.clearParts();
      super.remove(reason);
   }

   protected void playHurtSound(DamageSource source) {
      this.speakingProgress[this.random.nextInt(this.getHeadCount())] = 1.0F;
      super.playHurtSound(source);
   }

   public void playAmbientSound() {
      this.speakingProgress[this.random.nextInt(this.getHeadCount())] = 1.0F;
      super.playAmbientSound();
   }

   public int getAmbientSoundInterval() {
      return 100 / this.getHeadCount();
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Variant", this.getVariant());
      compound.putInt("HeadCount", this.getHeadCount());
      compound.putInt("SeveredHead", this.getSeveredHead());

      for (int i = 0; i < 9; i++) {
         compound.putFloat("HeadDamage" + i, this.headDamageTracker[i]);
      }

      this.clearParts();
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setVariant(compound.getInt("Variant"));
      this.setHeadCount(compound.getInt("HeadCount"));
      this.setSeveredHead(compound.getInt("SeveredHead"));

      for (int i = 0; i < 9; i++) {
         this.headDamageTracker[i] = compound.getFloat("HeadDamage" + i);
      }

      this.setConfigurableAttributes();
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VARIANT, 0);
      builder.define(HEAD_COUNT, 3);
      builder.define(SEVERED_HEAD, -1);
   }

   public boolean hurt(DamageSource source, float amount) {
      if (this.lastHitHead > this.getHeadCount()) {
         this.lastHitHead = this.getHeadCount() - 1;
      }

      int headIndex = this.lastHitHead;
      this.headDamageTracker[headIndex] = this.headDamageTracker[headIndex] + amount;
      if (this.headDamageTracker[headIndex] > this.headDamageThreshold && (this.getSeveredHead() == -1 || this.getSeveredHead() >= this.getHeadCount())) {
         this.headDamageTracker[headIndex] = 0.0F;
         this.regrowHeadCooldown = 0;
         this.setSeveredHead(headIndex);
         this.playSound(SoundEvents.GUARDIAN_FLOP, this.getSoundVolume(), this.getVoicePitch());
      }

      if (this.getHealth() <= amount + 5.0F && this.getHeadCount() > 1 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         amount = 0.0F;
      }

      return super.hurt(source, amount);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn) {
      SpawnGroupData data = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
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
      return new Animation[0];
   }

   @Override
   public boolean shouldAnimalsFear(Entity entity) {
      return true;
   }

   public boolean isPersistenceRequired() {
      return true;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public int getVariant() {
      return (Integer)this.entityData.get(VARIANT);
   }

   public void setVariant(int variant) {
      this.entityData.set(VARIANT, variant);
   }

   public int getHeadCount() {
      return Mth.clamp((Integer)this.entityData.get(HEAD_COUNT), 1, 9);
   }

   public void setHeadCount(int count) {
      this.entityData.set(HEAD_COUNT, Mth.clamp(count, 1, 9));
   }

   public int getSeveredHead() {
      return Mth.clamp((Integer)this.entityData.get(SEVERED_HEAD), -1, 9);
   }

   public void setSeveredHead(int count) {
      this.entityData.set(SEVERED_HEAD, Mth.clamp(count, -1, 9));
   }

   public void handleEntityEvent(byte id) {
      if (id >= 40 && id <= 48) {
         int index = id - 40;
         this.isStriking[Mth.clamp(index, 0, 8)] = true;
      } else if (id >= 50 && id <= 58) {
         int index = id - 50;
         this.isBreathing[Mth.clamp(index, 0, 8)] = true;
      } else if (id >= 70 && id <= 78) {
         int index = id - 70;
         this.isBreathing[Mth.clamp(index, 0, 8)] = false;
      } else {
         super.handleEntityEvent(id);
      }
   }

   public boolean canBeAffected(MobEffectInstance potioneffectIn) {
      return potioneffectIn.getEffect() != MobEffects.POISON && super.canBeAffected(potioneffectIn);
   }

   public void onHitHead(float damage, int headIndex) {
      this.lastHitHead = headIndex;
   }

   public void triggerHeadFlags(int index) {
      this.lastHitHead = index;
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)IafSounds.HYDRA_IDLE.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)IafSounds.HYDRA_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)IafSounds.HYDRA_DIE.get();
   }
}
