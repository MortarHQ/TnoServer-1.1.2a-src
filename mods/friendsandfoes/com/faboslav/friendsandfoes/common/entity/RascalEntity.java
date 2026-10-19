package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.ai.brain.RascalBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.RascalAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityDataSerializers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.util.RandomGenerator;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import com.faboslav.friendsandfoes.common.versions.VersionedProfilerProvider;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;

public final class RascalEntity extends AgeableMob implements AnimatedEntity {
   private AnimationContextTracker animationContextTracker;
   private static final EntityDataAccessor<Integer> POSE_TICKS = SynchedEntityData.defineId(RascalEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<FriendsAndFoesEntityPose> ENTITY_POSE = SynchedEntityData.defineId(
      RascalEntity.class, FriendsAndFoesEntityDataSerializers.ENTITY_POSE
   );
   private static final EntityDataAccessor<Integer> CAUGHT_COUNT = SynchedEntityData.defineId(RascalEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DAMAGE_COUNT = SynchedEntityData.defineId(RascalEntity.class, EntityDataSerializers.INT);
   private boolean ambientSounds;

   public RascalEntity(EntityType<? extends AgeableMob> entityType, Level world) {
      super(entityType, world);
      this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
      this.enableAmbientSounds();
      this.setPathfindingMalus(PathType.RAIL, 0.0F);
      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.WATER, 0.0F);
      this.setPathfindingMalus(PathType.WATER_BORDER, 0.0F);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
      SpawnGroupData superEntityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData);
      this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
      RascalBrain.setNodCooldown(this);
      return superEntityData;
   }

   public static boolean canSpawn(
      EntityType<? extends Mob> rascalEntityType, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random
   ) {
      if (spawnReason != VersionedEntitySpawnReason.NATURAL) {
         return true;
      }

      ServerLevel serverWorld = serverWorldAccess.getLevel();
      RegistryLookup<Structure> structureRegistry = serverWorldAccess.registryAccess().lookupOrThrow(Registries.STRUCTURE);
      StructureManager structureAccessor = serverWorld.structureManager();
      if (blockPos.getY() <= 63
         && !serverWorldAccess.canSeeSky(blockPos)
         && serverWorldAccess.getMaxLocalRawBrightness(blockPos, 0) != 0
         && (
            serverWorldAccess.getBlockState(blockPos.below()).is(BlockTags.PLANKS)
               || serverWorldAccess.getBlockState(blockPos.above()).is(BlockTags.PLANKS)
               || serverWorldAccess.getBlockState(blockPos.north()).is(BlockTags.PLANKS)
               || serverWorldAccess.getBlockState(blockPos.west()).is(BlockTags.PLANKS)
               || serverWorldAccess.getBlockState(blockPos.south()).is(BlockTags.PLANKS)
               || serverWorldAccess.getBlockState(blockPos.east()).is(BlockTags.PLANKS)
         )) {
         for (Holder<Structure> structure : structureRegistry.getOrThrow(StructureTags.MINESHAFT)) {
            if (structureAccessor.getStructureWithPieceAt(blockPos, (Structure)structure.value()).isValid()) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Override
   public AnimationContextTracker getAnimationContextTracker() {
      if (this.animationContextTracker == null) {
         this.animationContextTracker = new AnimationContextTracker();

         for (AnimationHolder animation : this.getTrackedAnimations()) {
            this.animationContextTracker.add(animation);
         }
      }

      return this.animationContextTracker;
   }

   @Override
   public ArrayList<AnimationHolder> getTrackedAnimations() {
      return RascalAnimations.ANIMATIONS;
   }

   @Override
   public AnimationHolder getMovementAnimation() {
      return RascalAnimations.WALK;
   }

   @Override
   public int getCurrentAnimationTick() {
      return (Integer)this.entityData.get(POSE_TICKS);
   }

   @Override
   public void setCurrentAnimationTick(int keyframeAnimationTicks) {
      this.entityData.set(POSE_TICKS, keyframeAnimationTicks);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(POSE_TICKS, 0);
      builder.define(ENTITY_POSE, FriendsAndFoesEntityPose.IDLE);
      builder.define(CAUGHT_COUNT, 0);
      builder.define(DAMAGE_COUNT, 0);
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
      return null;
   }

   protected Brain<?> makeBrain(Dynamic<?> dynamic) {
      return RascalBrain.create(dynamic);
   }

   public Brain<RascalEntity> getBrain() {
      return super.getBrain();
   }

   protected void customServerAiStep() {
      ServerLevel level = (ServerLevel)this.level();
      ProfilerFiller profiler = VersionedProfilerProvider.getProfiler(this);
      profiler.push("rascalBrain");
      this.getBrain().tick(level, this);
      profiler.pop();
      profiler.push("rascalActivityUpdate");
      RascalBrain.updateActivities(this);
      profiler.pop();
      super.customServerAiStep();
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createRascalAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.55).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   public void tick() {
      if (!FriendsAndFoes.getConfig().enableRascal) {
         this.discard();
      }

      MobEffectInstance invisibilityStatusEffect = this.getEffect(MobEffects.INVISIBILITY);
      if (this.isHidden() && invisibilityStatusEffect != null && invisibilityStatusEffect.getDuration() == 1) {
         this.playReappearSound();
      }

      this.updateKeyframeAnimations();
      super.tick();
   }

   private void updateKeyframeAnimations() {
      if (!this.level().isClientSide()) {
         this.updateCurrentAnimationTick();
      }

      AnimationHolder animationToStart = this.getAnimationByPose();
      if (animationToStart != null) {
         this.tryToStartAnimation(animationToStart);
      }
   }

   @Nullable
   @Override
   public AnimationHolder getAnimationByPose() {
      AnimationHolder animation = null;
      if (this.isInEntityPose(FriendsAndFoesEntityPose.IDLE) && !this.isMoving()) {
         animation = RascalAnimations.IDLE;
      } else if (this.isInEntityPose(FriendsAndFoesEntityPose.NOD)) {
         animation = RascalAnimations.NOD;
      } else if (this.isInEntityPose(FriendsAndFoesEntityPose.GIVE_REWARD)) {
         animation = RascalAnimations.GIVE_REWARD;
      }

      return animation;
   }

   private void tryToStartAnimation(AnimationHolder animationToStart) {
      if (!this.isKeyframeAnimationRunning(animationToStart)) {
         if (!this.level().isClientSide()) {
            this.setCurrentAnimationTick(animationToStart.get().lengthInTicks());
         }

         this.startKeyframeAnimation(animationToStart);
      }
   }

   private void startKeyframeAnimation(AnimationHolder animationToStart) {
      for (AnimationHolder animation : this.getTrackedAnimations()) {
         if (animation != animationToStart) {
            this.stopKeyframeAnimation(animation);
         }
      }

      this.startKeyframeAnimation(animationToStart, this.tickCount);
   }

   public void setEntityPose(FriendsAndFoesEntityPose pose) {
      if (!this.level().isClientSide()) {
         this.entityData.set(ENTITY_POSE, pose);
      }
   }

   public FriendsAndFoesEntityPose getEntityPose() {
      return (FriendsAndFoesEntityPose)this.entityData.get(ENTITY_POSE);
   }

   public boolean isInEntityPose(FriendsAndFoesEntityPose pose) {
      return this.getEntityPose() == pose;
   }

   public void startNodAnimation() {
      if (!this.isInEntityPose(FriendsAndFoesEntityPose.NOD)) {
         this.playNodSound();
         this.gameEvent(GameEvent.ENTITY_ACTION);
         this.setEntityPose(FriendsAndFoesEntityPose.NOD);
      }
   }

   public void startGiveRewardAnimation() {
      if (!this.isInEntityPose(FriendsAndFoesEntityPose.GIVE_REWARD)) {
         this.playRewardSound();
         this.gameEvent(GameEvent.ENTITY_ACTION);
         this.setEntityPose(FriendsAndFoesEntityPose.GIVE_REWARD);
      }
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      Entity attacker = damageSource.getEntity();
      if (attacker instanceof Player && !this.hasCustomName()) {
         this.playHurtSound(damageSource);
         this.spawnAngerParticles();
         this.addToDamageCount();
         if (this.getDamageCount() >= 3) {
            this.playDisappearSound();
            this.spawnCloudParticles();
            this.discard();
         }

         return false;
      } else {
         return super.hurt(damageSource, amount);
      }
   }

   public SoundEvent getNodSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_RASCAL_NOD.get();
   }

   public void playNodSound() {
      this.playSound(this.getNodSound(), 1.0F, RandomGenerator.generateFloat(1.15F, 1.3F));
   }

   public SoundEvent getRewardSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_RASCAL_REWARD.get();
   }

   public void playRewardSound() {
      this.playSound(this.getRewardSound(), 1.0F, RandomGenerator.generateFloat(1.15F, 1.3F));
   }

   public SoundEvent getBadRewardSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_RASCAL_REWARD_BAD.get();
   }

   public void playBadRewardSound() {
      this.playSound(this.getBadRewardSound(), 1.0F, RandomGenerator.generateFloat(1.15F, 1.3F));
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_RASCAL_AMBIENT.get();
   }

   public void playAmbientSound() {
      if (!this.isHidden() && this.ambientSounds) {
         SoundEvent soundEvent = this.getAmbientSound();
         this.playSound(soundEvent, 1.5F, RandomGenerator.generateFloat(1.15F, 1.3F));
      }
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_RASCAL_HURT.get();
   }

   protected void playHurtSound(DamageSource source) {
      this.ambientSoundTime = -this.getAmbientSoundInterval();
      this.playSound(this.getHurtSound(source), 1.0F, RandomGenerator.generateFloat(1.15F, 1.3F));
   }

   public SoundEvent getDisappearSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_RASCAL_DISAPPEAR.get();
   }

   public void playDisappearSound() {
      SoundEvent soundEvent = this.getDisappearSound();
      this.playSound(soundEvent, 2.0F, RandomGenerator.generateFloat(1.5F, 1.6F));
   }

   public SoundEvent getReappearSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_RASCAL_REAPPEAR.get();
   }

   public void playReappearSound() {
      SoundEvent soundEvent = this.getReappearSound();
      this.playSound(soundEvent, 2.0F, RandomGenerator.generateFloat(1.5F, 1.6F));
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      if (!this.isHidden() && !state.liquid()) {
         super.playStepSound(pos, state);
      }
   }

   public boolean isHidden() {
      return this.getBrain().getMemoryInternal(MemoryModuleType.AVOID_TARGET).orElse(null) instanceof Player;
   }

   public boolean isMoving() {
      return this.onGround() && this.getDeltaMovement().lengthSqr() >= 1.0E-4;
   }

   public int getDamageCount() {
      return (Integer)this.entityData.get(DAMAGE_COUNT);
   }

   public void addToDamageCount() {
      this.entityData.set(DAMAGE_COUNT, this.getDamageCount() + 1);
   }

   public int getCaughtCount() {
      return (Integer)this.entityData.get(CAUGHT_COUNT);
   }

   public void addToCaughtCount() {
      this.entityData.set(CAUGHT_COUNT, this.getCaughtCount() + 1);
   }

   public boolean shouldGiveReward() {
      return this.getCaughtCount() == 3;
   }

   public boolean disableAmbientSounds() {
      return this.ambientSounds = false;
   }

   public boolean enableAmbientSounds() {
      return this.ambientSounds = true;
   }

   public void spawnCloudParticles() {
      ParticleSpawner.spawnParticles(this, ParticleTypes.CLOUD, 16, 0.1);
   }

   public void spawnHappyParticles() {
      ParticleSpawner.spawnParticles(this, ParticleTypes.HAPPY_VILLAGER, 16, 0.1);
   }

   public void spawnAngerParticles() {
      ParticleSpawner.spawnParticles(this, ParticleTypes.ANGRY_VILLAGER, 16, 0.1);
   }
}
