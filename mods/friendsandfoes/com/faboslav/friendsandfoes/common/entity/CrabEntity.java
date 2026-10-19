package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.block.CrabEggBlock;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CrabBrain;
import com.faboslav.friendsandfoes.common.entity.ai.control.WallClimbNavigation;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.CrabAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityDataSerializers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedGameRulesProvider;
import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import com.faboslav.friendsandfoes.common.versions.VersionedProfilerProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CrabEntity extends Animal implements FlyingAnimal, AnimatedEntity {
   public static final float BABY_SCALE = 0.3F;
   private static final float MOVEMENT_SPEED = 0.225F;
   private static final String SIZE_NBT_NAME = "Size";
   private static final String HOME_NBT_NAME = "Home";
   private static final String HOME_NBT_NAME_X = "x";
   private static final String HOME_NBT_NAME_Y = "y";
   private static final String HOME_NBT_NAME_Z = "z";
   private static final String HAS_EGG_NBT_NAME = "HasEgg";
   private AnimationContextTracker animationContextTracker;
   private static final EntityDataAccessor<Integer> POSE_TICKS = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<FriendsAndFoesEntityPose> ENTITY_POSE = SynchedEntityData.defineId(
      CrabEntity.class, FriendsAndFoesEntityDataSerializers.ENTITY_POSE
   );
   private static final EntityDataAccessor<Boolean> IS_CLIMBING_WALL = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<String> SIZE = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.STRING);
   private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_DANCING = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
   private int climbingTicks = 0;
   private CrabEntity.Home home = new CrabEntity.Home(0.0, 0.0, 0.0);

   public CrabEntity(EntityType<? extends CrabEntity> entityType, Level world) {
      super(entityType, world);
      this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
      this.setPathfindingMalus(PathType.WATER, 0.0F);
      this.setPathfindingMalus(PathType.DOOR_IRON_CLOSED, -1.0F);
      this.setPathfindingMalus(PathType.DOOR_WOOD_CLOSED, -1.0F);
      this.setPathfindingMalus(PathType.DOOR_OPEN, -1.0F);
      this.lookControl = new CrabEntity.CrabLookControl(this, 10);
      this.navigation = new CrabEntity.CrabWallClimbNavigation(this, world);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
      SpawnGroupData superEntityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData);
      this.setHome(this.getNewHome());
      this.setSize(CrabEntity.CrabSize.getRandomCrabSize(world.getRandom()));
      this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
      CrabBrain.setWaveCooldown(this);
      return superEntityData;
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
      return CrabAnimations.ANIMATIONS;
   }

   @Override
   public AnimationHolder getMovementAnimation() {
      return CrabAnimations.WALK;
   }

   public boolean isPushedByFluid() {
      return !this.isSwimming();
   }

   public boolean shouldDiscardFriction() {
      return this.isSwimming();
   }

   public void jumpFromGround() {
   }

   public boolean isFlying() {
      return this.onClimbable();
   }

   public float getSpeed() {
      return this.isBaby() ? 0.1125F : 0.225F;
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
      builder.define(IS_CLIMBING_WALL, false);
      builder.define(SIZE, CrabEntity.CrabSize.getDefaultCrabSize().getName());
      builder.define(HAS_EGG, false);
      builder.define(IS_DANCING, false);
   }

   public void addAdditionalSaveData(CompoundTag nbt) {
      super.addAdditionalSaveData(nbt);
      nbt.putString("Size", this.getSize().getName());
      nbt.putBoolean("HasEgg", this.hasEgg());
      nbt.put("Home", CrabEntity.Home.toNbt(this.home));
   }

   public void readAdditionalSaveData(CompoundTag saveData) {
      super.readAdditionalSaveData(saveData);
      this.setSize(
         Objects.requireNonNull(
            CrabEntity.CrabSize.getCrabSizeByName(VersionedNbt.getString(saveData, "Size", CrabEntity.CrabSize.getDefaultCrabSize().getName()))
         )
      );
      this.setHasEgg(VersionedNbt.getBoolean(saveData, "HasEgg", false));
      this.setHome(CrabEntity.Home.fromNbt(VersionedNbt.getCompound(saveData, "Home")));
   }

   protected Brain<?> makeBrain(Dynamic<?> dynamic) {
      return CrabBrain.create(dynamic);
   }

   public Brain<CrabEntity> getBrain() {
      return super.getBrain();
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createCrabAttributes() {
      net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder attributes = Mob.createMobAttributes();
      return attributes.add(Attributes.MAX_HEALTH, 15.0)
         .add(Attributes.MOVEMENT_SPEED, 0.225F)
         .add(Attributes.ATTACK_DAMAGE, 2.0)
         .add(Attributes.STEP_HEIGHT, 0.0)
         .add(Attributes.SCALE);
   }

   protected float nextStep() {
      return this.moveDist + 0.175F;
   }

   public boolean onClimbable() {
      return this.climbingTicks > 8 && this.isClimbingWall();
   }

   public boolean isClimbingWall() {
      return (Boolean)this.entityData.get(IS_CLIMBING_WALL);
   }

   public void setClimbingWall(boolean isClimbingWall) {
      this.entityData.set(IS_CLIMBING_WALL, isClimbingWall);
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_CRAB_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_CRAB_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      if (this.onGround() || !state.liquid()) {
         this.playSound((SoundEvent)FriendsAndFoesSoundEvents.ENTITY_CRAB_STEP.get(), 0.15F, 1.0F + this.random.nextFloat() * 0.2F);
      }
   }

   public void tick() {
      if (!this.level().isClientSide() && !FriendsAndFoes.getConfig().enableCrab) {
         this.discard();
      }

      this.updateKeyframeAnimations();
      this.calculateSize();
      super.tick();
      if (!this.level().isClientSide()) {
         this.setClimbingWall(this.horizontalCollision);
      }

      if (this.isClimbingWall()) {
         this.climbingTicks++;
         BlockState blockStateAtPos = this.getInBlockState();
         if (this.isMoving() && !blockStateAtPos.liquid() && this.climbingTicks % 6 == 0) {
            this.playStepSound(this.blockPosition(), blockStateAtPos);
         }
      } else {
         this.climbingTicks = 0;
      }

      if (this.onClimbable()) {
         Vec3 velocity = this.getDeltaMovement();
         this.setDeltaMovement(velocity.x, velocity.y * 0.33F, velocity.z);
      }
   }

   public void aiStep() {
      super.aiStep();
      if (this.tickCount % 5 == 0) {
         boolean isDancing = false;

         for (BlockPos blockPos : BlockPos.withinManhattan(this.blockPosition(), 7, 7, 7)) {
            BlockPos possibleJukeboxBlockPos = blockPos.mutable();
            BlockState possibleJukeboxBlockState = this.level().getBlockState(possibleJukeboxBlockPos);
            if (possibleJukeboxBlockState.is(Blocks.JUKEBOX) && possibleJukeboxBlockState.hasBlockEntity()) {
               BlockEntity possibleJukeboxBlockEntity = this.level().getBlockEntity(possibleJukeboxBlockPos);
               if (possibleJukeboxBlockEntity instanceof JukeboxBlockEntity && ((JukeboxBlockEntity)possibleJukeboxBlockEntity).getSongPlayer().isPlaying()) {
                  isDancing = true;
                  break;
               }
            }
         }

         this.setIsDancing(isDancing);
      }
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
         animation = CrabAnimations.IDLE;
      } else if (this.isInEntityPose(FriendsAndFoesEntityPose.WAVE)) {
         animation = CrabAnimations.WAVE;
      } else if (this.isInEntityPose(FriendsAndFoesEntityPose.DANCE)) {
         animation = CrabAnimations.DANCE;
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

   public void startWaveAnimation() {
      if (!this.isInEntityPose(FriendsAndFoesEntityPose.WAVE)) {
         this.setEntityPose(FriendsAndFoesEntityPose.WAVE);
      }
   }

   public void startDanceAnimation() {
      if (!this.isInEntityPose(FriendsAndFoesEntityPose.DANCE)) {
         this.setEntityPose(FriendsAndFoesEntityPose.DANCE);
      }
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

   protected void customServerAiStep() {
      ServerLevel level = (ServerLevel)this.level();
      ProfilerFiller profiler = VersionedProfilerProvider.getProfiler(this);
      profiler.push("crabBrain");
      this.getBrain().tick(level, this);
      profiler.pop();
      profiler.push("crabMemoryUpdate");
      CrabBrain.updateMemories(this);
      profiler.pop();
      profiler.push("crabActivityUpdate");
      CrabBrain.updateActivities(this);
      profiler.pop();
      super.customServerAiStep();
   }

   protected void ageBoundaryReached() {
      super.ageBoundaryReached();
      if (!this.isBaby() && VersionedGameRulesProvider.getBoolean(this, VersionedGameRulesProvider.MOB_DROPS)) {
         VersionedEntity.spawnAtLocation(this, ((Item)FriendsAndFoesItems.CRAB_CLAW.get()).getDefaultInstance(), 1.0F);
      }
   }

   public boolean isMoving() {
      return (this.onGround() || this.onClimbable()) && this.getDeltaMovement().lengthSqr() >= 1.0E-4;
   }

   public static boolean canSpawn(EntityType<? extends Animal> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
      return world.getBlockState(pos.below()).is(FriendsAndFoesTags.CRABS_SPAWNABLE_ON)
         && CrabEggBlock.isSuitableBelow(world, pos)
         && isBrightEnoughToSpawn(world, pos);
   }

   public boolean isFood(ItemStack itemStack) {
      return CrabBrain.getTemptations().test(itemStack);
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob entity) {
      CrabEntity crab = (CrabEntity)((EntityType)FriendsAndFoesEntityTypes.CRAB.get()).create(serverWorld);
      CrabBrain.setWaveCooldown(crab);
      return crab;
   }

   public void spawnChildFromBreeding(ServerLevel world, Animal mate) {
      ServerPlayer serverPlayerEntity = this.getLoveCause();
      if (serverPlayerEntity == null && mate.getLoveCause() != null) {
         serverPlayerEntity = mate.getLoveCause();
      }

      if (serverPlayerEntity != null) {
         serverPlayerEntity.awardStat(Stats.ANIMALS_BRED);
         CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayerEntity, this, mate, null);
      }

      this.setHasEgg(true);
      this.resetLove();
      mate.resetLove();
      RandomSource random = this.getRandom();
      if (VersionedGameRulesProvider.getBoolean(this, VersionedGameRulesProvider.MOB_DROPS)) {
         this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY(), this.getZ(), random.nextInt(7) + 1));
      }
   }

   @Nullable
   public GlobalPos getBurrowSpotPos() {
      return (GlobalPos)this.getBrain().getMemoryInternal((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.CRAB_BURROW_POS.get()).orElse(null);
   }

   public boolean isBurrowSpotAccessible(BlockPos pos) {
      Level world = this.level();
      boolean isBlockSand = world.getBlockState(pos.below()).is(FriendsAndFoesTags.CRAB_BURROW_SPOT_BLOCKS);
      boolean isBlockAccessible = world.isEmptyBlock(pos) && world.isEmptyBlock(pos.above());
      return isBlockSand && isBlockAccessible;
   }

   public CrabEntity.Home getNewHome() {
      return new CrabEntity.Home(this.position().x(), this.position().y(), this.position().z());
   }

   public void setHome(CrabEntity.Home home) {
      this.home = home;
   }

   public CrabEntity.Home getHome() {
      return this.home;
   }

   public Vec3 getHomePos() {
      CrabEntity.Home home = this.getHome();
      return new Vec3(home.x, home.y, home.z);
   }

   public boolean isAtHomePos() {
      return this.distanceToSqr(this.getHomePos()) < 0.1;
   }

   public boolean isCloseToHomePos(float distance) {
      return this.distanceToSqr(this.getHomePos()) < distance;
   }

   public boolean hasEgg() {
      return (Boolean)this.entityData.get(HAS_EGG);
   }

   public void setHasEgg(boolean hasEgg) {
      this.entityData.set(HAS_EGG, hasEgg);
   }

   public boolean isDancing() {
      return (Boolean)this.entityData.get(IS_DANCING);
   }

   public void setIsDancing(boolean isDancing) {
      this.entityData.set(IS_DANCING, isDancing);
   }

   private void setSize(CrabEntity.CrabSize size) {
      this.entityData.set(SIZE, size.getName());
      this.calculateSize();
   }

   private void calculateSize() {
      this.refreshDimensions();
      this.makeBoundingBox();
   }

   public CrabEntity.CrabSize getSize() {
      return CrabEntity.CrabSize.getCrabSizeByName((String)this.entityData.get(SIZE));
   }

   public float getAgeScale() {
      CrabEntity.CrabSize size = this.getSize();
      float scaleModifier = size.getScaleModifier();
      return this.isBaby() ? scaleModifier * 0.3F : scaleModifier;
   }

   class CrabLookControl extends SmoothSwimmingLookControl {
      public CrabLookControl(CrabEntity crab, int yawAdjustThreshold) {
         super(crab, yawAdjustThreshold);
      }

      public void tick() {
         if (!CrabEntity.this.onClimbable()) {
            super.tick();
         }
      }
   }

   public enum CrabSize {
      SMALL("small", 0.9F),
      MEDIUM("medium", 0.95F),
      BIG("big", 1.0F);

      private final String name;
      private final float scaleModifier;

      CrabSize(String name, float scaleModifier) {
         this.name = name;
         this.scaleModifier = scaleModifier;
      }

      public String getName() {
         return this.name;
      }

      public float getScaleModifier() {
         return this.scaleModifier;
      }

      @Nullable
      public static CrabEntity.CrabSize getCrabSizeByName(String name) {
         for (CrabEntity.CrabSize crabSize : values()) {
            if (Objects.equals(crabSize.getName(), name)) {
               return crabSize;
            }
         }

         return null;
      }

      public static CrabEntity.CrabSize getDefaultCrabSize() {
         return BIG;
      }

      public static CrabEntity.CrabSize getRandomCrabSize(RandomSource random) {
         Object[] values = values();
         int min = 0;
         int max = values.length - 1;
         return (CrabEntity.CrabSize)values[random.nextInt(max - min + 1) + min];
      }
   }

   class CrabWallClimbNavigation extends WallClimbNavigation {
      public CrabWallClimbNavigation(Mob mobEntity, Level world) {
         super(mobEntity, world);
      }

      @Override
      public void tick() {
         if (!CrabEntity.this.isDancing()) {
            super.tick();
         }
      }
   }

   public record Home(double x, double y, double z) {
      public static final Codec<CrabEntity.Home> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.DOUBLE.fieldOf("x").forGetter(CrabEntity.Home::x),
               Codec.DOUBLE.fieldOf("y").forGetter(CrabEntity.Home::y),
               Codec.DOUBLE.fieldOf("z").forGetter(CrabEntity.Home::z)
            )
            .apply(instance, CrabEntity.Home::new)
      );

      public static CompoundTag toNbt(CrabEntity.Home home) {
         CompoundTag tag = new CompoundTag();
         tag.putDouble("x", home.x);
         tag.putDouble("y", home.y);
         tag.putDouble("z", home.z);
         return tag;
      }

      public static CrabEntity.Home fromNbt(CompoundTag tag) {
         return new CrabEntity.Home(VersionedNbt.getDouble(tag, "x", 0.0), VersionedNbt.getDouble(tag, "y", 0.0), VersionedNbt.getDouble(tag, "z", 0.0));
      }
   }
}
