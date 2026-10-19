package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.CopperGolemAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.KeyframeAnimationContext;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityDataSerializers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.mixin.LimbAnimatorAccessor;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import com.faboslav.friendsandfoes.common.versions.VersionedInteractionResult;
import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import com.faboslav.friendsandfoes.common.versions.VersionedProfilerProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class CopperGolemEntity extends AbstractGolem implements AnimatedEntity {
   private static final String POSE_NBT_NAME = "Pose";
   private static final String POSE_TICKS_NBT_NAME = "PoseTicks";
   private static final String OXIDATION_LEVEL_NBT_NAME = "OxidationLevel";
   private static final String IS_WAXED_NBT_NAME = "IsWaxed";
   private static final String ENTITY_SNAPSHOT_NBT_NAME = "EntitySnapshot";
   private AnimationContextTracker animationContextTracker;
   private static final EntityDataAccessor<Integer> POSE_TICKS = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<FriendsAndFoesEntityPose> ENTITY_POSE = SynchedEntityData.defineId(
      CopperGolemEntity.class, FriendsAndFoesEntityDataSerializers.ENTITY_POSE
   );
   private static final EntityDataAccessor<Integer> OXIDATION_LEVEL = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> STRUCT_BY_LIGHTNING_TICKS = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> WAS_STATUE = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_WAXED = SynchedEntityData.defineId(CopperGolemEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<CompoundTag> ENTITY_SNAPSHOT = SynchedEntityData.defineId(
      CopperGolemEntity.class, EntityDataSerializers.COMPOUND_TAG
   );
   private static final float MOVEMENT_SPEED = 0.2F;
   private static final int COPPER_INGOT_HEAL_AMOUNT = 5;
   private static final float SPARK_CHANCE = 0.025F;
   private static final float OXIDATION_CHANCE = 2.0E-5F;
   public static final int MIN_STRUCT_BY_LIGHTNING_TICKS = 1200;
   public static final int MAX_STRUCT_BY_LIGHTNING_TICKS = 2400;

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
      return CopperGolemAnimations.TRACKED_ANIMATIONS;
   }

   @Override
   public AnimationHolder getMovementAnimation() {
      return CopperGolemAnimations.WALK;
   }

   @Override
   public int getCurrentAnimationTick() {
      return (Integer)this.entityData.get(POSE_TICKS);
   }

   @Override
   public void setCurrentAnimationTick(int keyframeAnimationTicks) {
      this.entityData.set(POSE_TICKS, keyframeAnimationTicks);
   }

   public CopperGolemEntity(EntityType<? extends CopperGolemEntity> entityType, Level world) {
      super(entityType, world);
      this.moveControl = new CopperGolemEntity.CopperGolemMoveControl(this);
      this.lookControl = new CopperGolemEntity.CopperGolemLookControl(this);
      this.jumpControl = new CopperGolemEntity.CopperGolemJumpControl(this);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
      SpawnGroupData superEntityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData);
      if (spawnReason == VersionedEntitySpawnReason.STRUCTURE) {
         return superEntityData;
      }

      this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
      CopperGolemBrain.setSpinHeadCooldown(this);
      CopperGolemBrain.setPressButtonCooldown(this);
      return superEntityData;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(POSE_TICKS, 0);
      builder.define(ENTITY_POSE, FriendsAndFoesEntityPose.IDLE);
      builder.define(OXIDATION_LEVEL, WeatherState.UNAFFECTED.ordinal());
      builder.define(STRUCT_BY_LIGHTNING_TICKS, 0);
      builder.define(WAS_STATUE, false);
      builder.define(IS_WAXED, false);
      builder.define(ENTITY_SNAPSHOT, new CompoundTag());
   }

   public void addAdditionalSaveData(CompoundTag nbt) {
      super.addAdditionalSaveData(nbt);
      nbt.putInt("OxidationLevel", this.getOxidationLevel().ordinal());
      nbt.putBoolean("IsWaxed", this.isWaxed());
      if (this.isOxidized()) {
         nbt.putString("Pose", this.getEntityPose().name());
         nbt.putInt("PoseTicks", this.getCurrentAnimationTick());
         CopperGolemEntity.EntitySnapshot entitySnapshot = this.takeEntitySnapshot();
         nbt.put("EntitySnapshot", CopperGolemEntity.EntitySnapshot.toNbt(entitySnapshot));
      }
   }

   public void readAdditionalSaveData(CompoundTag nbt) {
      super.readAdditionalSaveData(nbt);
      String savedEntityPose = VersionedNbt.getString(nbt, "Pose", FriendsAndFoesEntityPose.IDLE.name());

      FriendsAndFoesEntityPose entityPose;
      try {
         entityPose = FriendsAndFoesEntityPose.valueOf(savedEntityPose);
      } catch (Exception e) {
         entityPose = FriendsAndFoesEntityPose.IDLE;
      }

      this.setEntityPose(entityPose);
      this.setCurrentAnimationTick(VersionedNbt.getInt(nbt, "PoseTicks", 0));
      this.setOxidationLevel(WeatherState.values()[VersionedNbt.getInt(nbt, "OxidationLevel", 0)]);
      this.setIsWaxed(VersionedNbt.getBoolean(nbt, "IsWaxed", false));
      this.setEntitySnapshot(CopperGolemEntity.EntitySnapshot.fromNbt(VersionedNbt.getCompound(nbt, "EntitySnapshot")));
      if (this.isOxidized()) {
         this.applyEntitySnapshot();
      }
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createCopperGolemAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.2F).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected BodyRotationControl createBodyControl() {
      return new CopperGolemEntity.CopperGolemBodyRotationControl(this);
   }

   protected Brain<?> makeBrain(Dynamic<?> dynamic) {
      return CopperGolemBrain.create(dynamic);
   }

   public Brain<CopperGolemEntity> getBrain() {
      return super.getBrain();
   }

   public void setEntitySnapshot(CopperGolemEntity.EntitySnapshot entitySnapshot) {
      this.entityData.set(ENTITY_SNAPSHOT, CopperGolemEntity.EntitySnapshot.toNbt(entitySnapshot));
   }

   public void applyEntitySnapshot() {
      CopperGolemEntity.EntitySnapshot entitySnapshot = this.getEntitySnapshot();
      if (!this.isPassenger()) {
         this.yRotO = entitySnapshot.prevYaw;
         this.setYRot(this.yRotO);
         this.yBodyRotO = entitySnapshot.prevBodyYaw;
         this.yBodyRot = this.yBodyRotO;
         this.lerpYHeadRot = entitySnapshot.serverHeadYaw;
         this.yHeadRotO = entitySnapshot.prevHeadYaw;
         this.yHeadRot = this.yHeadRotO;
      }

      this.xRotO = entitySnapshot.prevPitch;
      this.setXRot(this.xRotO);
      this.oAttackAnim = entitySnapshot.lastHandSwingProgress;
      this.attackAnim = this.oAttackAnim;
      ((LimbAnimatorAccessor)this.walkAnimation).setPrevSpeed(entitySnapshot.limbAnimatorPrevSpeed);
      this.walkAnimation.setSpeed(entitySnapshot.limbAnimatorSpeed);
      ((LimbAnimatorAccessor)this.walkAnimation).setPos(entitySnapshot.limbAnimatorPos);
   }

   public CopperGolemEntity.EntitySnapshot getEntitySnapshot() {
      return CopperGolemEntity.EntitySnapshot.fromNbt((CompoundTag)this.entityData.get(ENTITY_SNAPSHOT));
   }

   private CopperGolemEntity.EntitySnapshot takeEntitySnapshot() {
      return new CopperGolemEntity.EntitySnapshot(
         this.yRotO,
         this.xRotO,
         this.yBodyRotO,
         this.lerpYHeadRot,
         this.yHeadRotO,
         this.oAttackAnim,
         ((LimbAnimatorAccessor)this.walkAnimation).getPresSpeed(),
         this.walkAnimation.speed(),
         this.walkAnimation.position()
      );
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      WeatherState oxidationLevel = this.getOxidationLevel();
      if (oxidationLevel == WeatherState.OXIDIZED) {
         return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_OXIDIZED_HURT.get();
      } else {
         return oxidationLevel == WeatherState.WEATHERED
            ? (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_WEATHERED_HURT.get()
            : (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_HURT.get();
      }
   }

   protected SoundEvent getDeathSound() {
      WeatherState oxidationLevel = this.getOxidationLevel();
      if (oxidationLevel == WeatherState.OXIDIZED) {
         return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_OXIDIZED_DEATH.get();
      } else {
         return oxidationLevel == WeatherState.WEATHERED
            ? (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_WEATHERED_DEATH.get()
            : (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_DEATH.get();
      }
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      if (!this.isImmobilized() && !state.liquid()) {
         BlockState blockState = this.level().getBlockState(pos.above());
         SoundType blockSoundGroup = blockState.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState.getSoundType() : state.getSoundType();
         WeatherState oxidationLevel = this.getOxidationLevel();
         SoundEvent stepSound;
         if (oxidationLevel == WeatherState.OXIDIZED) {
            stepSound = (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_OXIDIZED_STEP.get();
         } else if (oxidationLevel == WeatherState.WEATHERED) {
            stepSound = (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_WEATHERED_STEP.get();
         } else {
            stepSound = (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_STEP.get();
         }

         this.playSound(stepSound, blockSoundGroup.getVolume() * 0.15F, this.getVoicePitch());
      }
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      Entity attacker = damageSource.getEntity();
      return !(attacker instanceof LightningBolt) && damageSource != this.damageSources().sweetBerryBush() ? super.hurt(damageSource, amount) : false;
   }

   public Vec3 getLeashOffset() {
      return new Vec3(0.0, this.getEyeHeight() * 0.45, 0.0);
   }

   public float getMovementSpeedModifier() {
      return this.isStructByLightning() ? 1.5F : (0.2F - this.getOxidationModifier() * 0.033333335F) / 0.2F;
   }

   @Override
   public float getAnimationSpeedModifier() {
      return this.isStructByLightning() ? 0.6666666F : 0.2F / (0.2F - this.getOxidationModifier() * 0.033333335F);
   }

   private int getOxidationModifier() {
      return Math.min(this.getOxidationLevel().ordinal(), WeatherState.WEATHERED.ordinal());
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      Item itemInHand = itemStack.getItem();
      boolean interactionResult = false;
      if (itemInHand == Items.COPPER_INGOT) {
         interactionResult = this.tryToInteractMobWithCopperIngot(player, itemStack);
      } else if (itemInHand == Items.HONEYCOMB) {
         interactionResult = this.tryToInteractMobWithHoneycomb(player, itemStack);
      } else if (itemInHand instanceof AxeItem) {
         interactionResult = this.tryToInteractMobWithAxe(player, hand, itemStack);
      } else {
         interactionResult = this.tryToInteractMobWithHand();
      }

      if (interactionResult) {
         this.gameEvent(GameEvent.ENTITY_INTERACT, this);
         return VersionedInteractionResult.success(this);
      } else {
         return super.mobInteract(player, hand);
      }
   }

   private boolean tryToInteractMobWithCopperIngot(Player player, ItemStack itemStack) {
      if (this.getHealth() == this.getMaxHealth()) {
         return false;
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         this.heal(5.0F);
         itemStack.consume(1, player);
         this.playSound((SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_REPAIR.get(), 1.0F, this.getVoicePitch() - 1.0F);
      }

      return true;
   }

   private boolean tryToInteractMobWithHoneycomb(Player player, ItemStack itemStack) {
      if (!this.isWaxed() && !this.isImmobilized()) {
         if (this.level() instanceof ServerLevel serverLevel) {
            this.setIsWaxed(true);
            if (!player.getAbilities().instabuild) {
               itemStack.shrink(1);
            }

            this.playSound(SoundEvents.HONEYCOMB_WAX_ON, 1.0F, 1.0F);
            ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_ON, 7, 1.0);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean tryToInteractMobWithAxe(Player player, InteractionHand hand, ItemStack itemStack) {
      if ((this.isWaxed() || this.isDegraded()) && (!this.wasStatue() || this.isOxidized())) {
         if (this.level() instanceof ServerLevel serverLevel) {
            if (this.isWaxed()) {
               this.setIsWaxed(false);
               this.playSound(SoundEvents.AXE_WAX_OFF, 1.0F, 1.0F);
               ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_OFF, 7, 1.0);
            } else if (this.isDegraded()) {
               int increasedOxidationLevelOrdinal = this.getOxidationLevel().ordinal() - 1;
               WeatherState[] OxidationLevels = WeatherState.values();
               this.setOxidationLevel(OxidationLevels[increasedOxidationLevelOrdinal]);
               this.playSound(SoundEvents.AXE_SCRAPE, 1.0F, 1.0F);
               ParticleSpawner.spawnParticles(this, ParticleTypes.SCRAPE, 7, 1.0);
            }

            if (!player.getAbilities().instabuild) {
               itemStack.hurtAndBreak(1, player, VersionedEntity.getEquipmentSlotForItem(hand));
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean tryToInteractMobWithHand() {
      if (!this.isWaxed() && this.isOxidized()) {
         if (this.level() instanceof ServerLevel) {
            ArrayList<AnimationHolder> possibleAnimations = new ArrayList<AnimationHolder>() {
               {
                  this.add(CopperGolemAnimations.IDLE);
                  this.add(CopperGolemAnimations.SPIN_HEAD);
                  this.add(CopperGolemAnimations.PRESS_BUTTON_UP);
                  this.add(CopperGolemAnimations.PRESS_BUTTON_DOWN);
               }
            };
            int randomPoseIndex = this.getRandom().nextInt(possibleAnimations.size());
            AnimationHolder randomAnimation = possibleAnimations.get(randomPoseIndex);
            FriendsAndFoesEntityPose copperGolemEntityPose = FriendsAndFoesEntityPose.IDLE;
            if (randomAnimation == CopperGolemAnimations.SPIN_HEAD) {
               copperGolemEntityPose = FriendsAndFoesEntityPose.SPIN_HEAD;
            } else if (randomAnimation == CopperGolemAnimations.PRESS_BUTTON_UP) {
               copperGolemEntityPose = FriendsAndFoesEntityPose.PRESS_BUTTON_UP;
            } else if (randomAnimation == CopperGolemAnimations.PRESS_BUTTON_DOWN) {
               copperGolemEntityPose = FriendsAndFoesEntityPose.PRESS_BUTTON_DOWN;
            }

            this.setEntityPose(copperGolemEntityPose);
            int keyFrameAnimationLengthInTicks = randomAnimation.get().lengthInTicks();
            int randomKeyframeAnimationTick = this.getRandom().nextIntBetweenInclusive(0, keyFrameAnimationLengthInTicks);
            this.setCurrentAnimationTick(randomKeyframeAnimationTick);
            this.playSound((SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_OXIDATION.get(), this.getSoundVolume(), this.getVoicePitch());
         }

         return true;
      } else {
         return false;
      }
   }

   protected void updateWalkAnimation(float f) {
      if (!this.isImmobilized()) {
         super.updateWalkAnimation(f);
      }
   }

   protected void customServerAiStep() {
      ServerLevel level = (ServerLevel)this.level();
      if (this.isImmobilized()) {
         super.customServerAiStep();
      } else {
         ProfilerFiller profiler = VersionedProfilerProvider.getProfiler(this);
         profiler.push("copperGolemBrain");
         this.getBrain().tick(level, this);
         profiler.pop();
         profiler.push("copperGolemActivityUpdate");
         CopperGolemBrain.updateActivities(this);
         profiler.pop();
         super.customServerAiStep();
      }
   }

   public void tick() {
      if (!FriendsAndFoes.getConfig().enableCopperGolem) {
         this.discard();
      }

      this.updateKeyframeAnimations();
      super.tick();
      if (this.isOxidized()) {
         this.applyEntitySnapshot();
         MovementUtil.stopMovement(this);
      } else {
         if (this.isStructByLightning() && !this.level().isClientSide()) {
            this.setStructByLightningTicks(this.getStructByLightningTicks() - 1);
            if (this.getRandom().nextFloat() < 0.025F) {
               for (int i = 0; i < 7; i++) {
                  ((ServerLevel)this.level())
                     .sendParticles(
                        ParticleTypes.ELECTRIC_SPARK,
                        this.getRandomX(0.35),
                        this.getRandomY() + 0.25,
                        this.getRandomZ(0.35),
                        1,
                        this.getRandom().nextGaussian() * 0.01,
                        this.getRandom().nextGaussian() * 0.01,
                        this.getRandom().nextGaussian() * 0.01,
                        0.1
                     );
               }
            }
         }

         this.handleOxidationIncrease();
      }
   }

   public boolean isPushable() {
      return !this.isImmobilized();
   }

   public void thunderHit(ServerLevel serverWorld, LightningBolt lightning) {
      super.thunderHit(serverWorld, lightning);
      this.setRemainingFireTicks(0);
      this.setSharedFlagOnFire(false);
      this.setHealth(this.getMaxHealth());
      if (this.isDegraded()) {
         ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_OFF, 7, 1.0);
      }

      if (!this.level().isClientSide()) {
         this.refreshStructByLightningTicks();
         if (!this.isWaxed()) {
            this.setOxidationLevel(WeatherState.UNAFFECTED);
         }
      }
   }

   private void updateKeyframeAnimations() {
      if (!this.level().isClientSide() && !this.isOxidized()) {
         this.updateCurrentAnimationTick();
      }

      AnimationHolder keyframeAnimationToStart = this.getAnimationByPose();
      if (keyframeAnimationToStart != null) {
         this.tryToStartAnimation(keyframeAnimationToStart);
      }
   }

   @Override
   public void updateCurrentAnimationTick() {
      if (this.isAnyKeyframeAnimationRunning()) {
         this.setCurrentAnimationTick(this.getCurrentAnimationTick() - 1);
         if (!this.level().isClientSide() && this.wasStatue() && this.getCurrentAnimationTick() == 1) {
            this.setEntityPose(FriendsAndFoesEntityPose.IDLE);
            this.setWasStatue(false);
         }

         if (this.getCurrentAnimationTick() <= 1) {
            for (AnimationHolder animation : this.getTrackedAnimations()) {
               if (animation.get().looping()) {
                  KeyframeAnimationContext keyframeAnimationContext = this.getAnimationContextTracker().get(animation);
                  if (keyframeAnimationContext.isRunning()) {
                     this.setCurrentAnimationTick(animation.get().lengthInTicks(this.getAnimationSpeedModifier()));
                  }
               }
            }
         }
      }
   }

   @Nullable
   @Override
   public AnimationHolder getAnimationByPose() {
      AnimationHolder animationHolder = null;
      if (this.isInEntityPose(FriendsAndFoesEntityPose.IDLE)) {
         animationHolder = CopperGolemAnimations.IDLE;
      } else if (this.isInEntityPose(FriendsAndFoesEntityPose.SPIN_HEAD)) {
         animationHolder = CopperGolemAnimations.SPIN_HEAD;
      } else if (this.isInEntityPose(FriendsAndFoesEntityPose.PRESS_BUTTON_UP)) {
         animationHolder = CopperGolemAnimations.PRESS_BUTTON_UP;
      } else if (this.isInEntityPose(FriendsAndFoesEntityPose.PRESS_BUTTON_DOWN)) {
         animationHolder = CopperGolemAnimations.PRESS_BUTTON_DOWN;
      }

      return animationHolder;
   }

   private void tryToStartAnimation(AnimationHolder animationHolder) {
      if (!this.isKeyframeAnimationRunning(animationHolder)) {
         if (!this.level().isClientSide() && !this.isOxidized()) {
            this.setCurrentAnimationTick(animationHolder.get().lengthInTicks(this.getAnimationSpeedModifier()));
         }

         this.startKeyframeAnimation(animationHolder);
      }
   }

   private void startKeyframeAnimation(AnimationHolder animationToStart) {
      for (AnimationHolder animation : this.getTrackedAnimations()) {
         if (animation != animationToStart) {
            this.stopKeyframeAnimation(animation);
         }
      }

      int initialTick = this.isOxidized() ? this.tickCount - this.getCurrentKeyframeAnimationTick() : this.tickCount;
      this.startKeyframeAnimation(animationToStart, initialTick);
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

   public void startSpinHeadAnimation() {
      if (!this.isInEntityPose(FriendsAndFoesEntityPose.SPIN_HEAD)) {
         WeatherState oxidationLevel = this.getOxidationLevel();
         SoundEvent spinHeadSound;
         if (oxidationLevel == WeatherState.OXIDIZED) {
            spinHeadSound = (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_OXIDIZED_SPIN.get();
         } else if (oxidationLevel == WeatherState.WEATHERED) {
            spinHeadSound = (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_WEATHERED_SPIN.get();
         } else {
            spinHeadSound = (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_SPIN.get();
         }

         this.playSound(spinHeadSound, this.getSoundVolume(), this.getVoicePitch());
         this.gameEvent(GameEvent.ENTITY_ACTION);
         this.setEntityPose(FriendsAndFoesEntityPose.SPIN_HEAD);
      }
   }

   public void startPressButtonUpAnimation() {
      if (!this.isInEntityPose(FriendsAndFoesEntityPose.PRESS_BUTTON_UP)) {
         this.gameEvent(GameEvent.ENTITY_ACTION);
         this.setEntityPose(FriendsAndFoesEntityPose.PRESS_BUTTON_UP);
      }
   }

   public void startPressButtonDownAnimation() {
      if (!this.isInEntityPose(FriendsAndFoesEntityPose.PRESS_BUTTON_DOWN)) {
         this.gameEvent(GameEvent.ENTITY_ACTION);
         this.setEntityPose(FriendsAndFoesEntityPose.PRESS_BUTTON_DOWN);
      }
   }

   public void handleOxidationIncrease() {
      if (!this.level().isClientSide() && !this.isImmobilized() && !this.isWaxed()) {
         if (this.getRandom().nextFloat() < 2.0E-5F) {
            int degradedOxidationLevelOrdinal = this.getOxidationLevel().ordinal() + 1;
            WeatherState[] OxidationLevels = WeatherState.values();
            this.setOxidationLevel(OxidationLevels[degradedOxidationLevelOrdinal]);
            this.playSound((SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_OXIDATION.get(), this.getSoundVolume(), this.getVoicePitch());
         }
      }
   }

   public boolean isOxidized() {
      return this.getOxidationLevel() == WeatherState.OXIDIZED;
   }

   public boolean isDegraded() {
      return this.getOxidationLevel().ordinal() > WeatherState.UNAFFECTED.ordinal();
   }

   public WeatherState getOxidationLevel() {
      return WeatherState.values()[this.entityData.get(OXIDATION_LEVEL)];
   }

   public void setOxidationLevel(WeatherState oxidationLevel) {
      this.entityData.set(OXIDATION_LEVEL, oxidationLevel.ordinal());
      this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2F * this.getMovementSpeedModifier());
      if (this.isOxidized()) {
         this.setWasStatue(true);
      }

      if (this.isOxidized() && this.getBrain().getMemoryInternal((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get()).isEmpty()) {
         this.getBrain().setMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), true);
         this.becomeStatue();
      } else if (!this.isOxidized()
         && this.getBrain().getMemoryInternal((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get()).isPresent()) {
         this.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get());
         this.becomeEntity();
      }
   }

   public void setStructByLightningTicks(int structByLightningTicks) {
      this.entityData.set(STRUCT_BY_LIGHTNING_TICKS, structByLightningTicks);
   }

   public int getStructByLightningTicks() {
      return (Integer)this.entityData.get(STRUCT_BY_LIGHTNING_TICKS);
   }

   public void refreshStructByLightningTicks() {
      this.setStructByLightningTicks(this.getRandom().nextIntBetweenInclusive(1200, 2400));
   }

   public boolean isStructByLightning() {
      return this.getStructByLightningTicks() > 0;
   }

   @Nullable
   public GlobalPos getButtonPos() {
      return (GlobalPos)this.getBrain().getMemoryInternal((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get()).orElse(null);
   }

   public boolean isButtonValidToBePressed(BlockPos pos) {
      return this.level().getBlockState(pos).is(FriendsAndFoesTags.COPPER_BUTTONS);
   }

   private void becomeStatue() {
      this.setEntitySnapshot(this.takeEntitySnapshot());
   }

   private void becomeEntity() {
      CopperGolemBrain.setSpinHeadCooldown(this);
      CopperGolemBrain.setPressButtonCooldown(this);
   }

   public boolean isWaxed() {
      return (Boolean)this.entityData.get(IS_WAXED);
   }

   public void setIsWaxed(boolean isWaxed) {
      this.entityData.set(IS_WAXED, isWaxed);
   }

   public boolean wasStatue() {
      return (Boolean)this.entityData.get(WAS_STATUE);
   }

   public void setWasStatue(boolean wasStatue) {
      this.entityData.set(WAS_STATUE, wasStatue);
   }

   public boolean isImmobilized() {
      return this.isOxidized() || this.wasStatue();
   }

   public void setSpawnYaw(float yaw) {
      this.yRotO = yaw;
      this.setYRot(yaw);
      this.yBodyRotO = yaw;
      this.yBodyRot = yaw;
      this.lerpYHeadRot = yaw;
      this.yHeadRotO = yaw;
      this.yHeadRot = yaw;
   }

   final class CopperGolemBodyRotationControl extends BodyRotationControl {
      public CopperGolemBodyRotationControl(final CopperGolemEntity copperGolem) {
         super(copperGolem);
      }

      public void clientTick() {
         if (!CopperGolemEntity.this.isImmobilized()) {
            super.clientTick();
         }
      }
   }

   final class CopperGolemJumpControl extends JumpControl {
      public CopperGolemJumpControl(final CopperGolemEntity copperGolem) {
         super(copperGolem);
      }

      public void tick() {
         if (CopperGolemEntity.this.isImmobilized()) {
            CopperGolemEntity.this.setJumping(false);
         }

         super.tick();
      }
   }

   final class CopperGolemLookControl extends LookControl {
      public CopperGolemLookControl(final CopperGolemEntity copperGolem) {
         super(copperGolem);
      }

      public void tick() {
         if (!CopperGolemEntity.this.isImmobilized()) {
            super.tick();
         }
      }
   }

   final class CopperGolemMoveControl extends MoveControl {
      public CopperGolemMoveControl(final CopperGolemEntity copperGolem) {
         super(copperGolem);
      }

      public void tick() {
         if (!CopperGolemEntity.this.isImmobilized()) {
            super.tick();
         }
      }
   }

   public record EntitySnapshot(
      float prevYaw,
      float prevPitch,
      float prevBodyYaw,
      double serverHeadYaw,
      float prevHeadYaw,
      float lastHandSwingProgress,
      float limbAnimatorPrevSpeed,
      float limbAnimatorSpeed,
      float limbAnimatorPos
   ) implements StringRepresentable {
      public static final Codec<CopperGolemEntity.EntitySnapshot> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.FLOAT.fieldOf("prevYaw").forGetter(CopperGolemEntity.EntitySnapshot::prevYaw),
               Codec.FLOAT.fieldOf("prevPitch").forGetter(CopperGolemEntity.EntitySnapshot::prevPitch),
               Codec.FLOAT.fieldOf("prevBodyYaw").forGetter(CopperGolemEntity.EntitySnapshot::prevBodyYaw),
               Codec.DOUBLE.fieldOf("serverHeadYaw").forGetter(CopperGolemEntity.EntitySnapshot::serverHeadYaw),
               Codec.FLOAT.fieldOf("prevHeadYaw").forGetter(CopperGolemEntity.EntitySnapshot::prevHeadYaw),
               Codec.FLOAT.fieldOf("lastHandSwingProgress").forGetter(CopperGolemEntity.EntitySnapshot::lastHandSwingProgress),
               Codec.FLOAT.fieldOf("limbAnimatorPrevSpeed").forGetter(CopperGolemEntity.EntitySnapshot::limbAnimatorPrevSpeed),
               Codec.FLOAT.fieldOf("limbAnimatorSpeed").forGetter(CopperGolemEntity.EntitySnapshot::limbAnimatorSpeed),
               Codec.FLOAT.fieldOf("limbAnimatorPos").forGetter(CopperGolemEntity.EntitySnapshot::limbAnimatorPos)
            )
            .apply(instance, CopperGolemEntity.EntitySnapshot::new)
      );

      public static CompoundTag toNbt(CopperGolemEntity.EntitySnapshot snapshot) {
         CompoundTag tag = new CompoundTag();
         tag.putFloat("prevYaw", snapshot.prevYaw);
         tag.putFloat("prevPitch", snapshot.prevPitch);
         tag.putFloat("prevBodyYaw", snapshot.prevBodyYaw);
         tag.putDouble("serverHeadYaw", snapshot.serverHeadYaw);
         tag.putFloat("prevHeadYaw", snapshot.prevHeadYaw);
         tag.putFloat("lastHandSwingProgress", snapshot.lastHandSwingProgress);
         tag.putFloat("limbAnimatorPrevSpeed", snapshot.limbAnimatorPrevSpeed);
         tag.putFloat("limbAnimatorSpeed", snapshot.limbAnimatorSpeed);
         tag.putFloat("limbAnimatorPos", snapshot.limbAnimatorPos);
         return tag;
      }

      public static CopperGolemEntity.EntitySnapshot fromNbt(CompoundTag tag) {
         return new CopperGolemEntity.EntitySnapshot(
            VersionedNbt.getFloat(tag, "prevYaw", 0.0F),
            VersionedNbt.getFloat(tag, "prevPitch", 0.0F),
            VersionedNbt.getFloat(tag, "prevBodyYaw", 0.0F),
            VersionedNbt.getDouble(tag, "serverHeadYaw", 0.0),
            VersionedNbt.getFloat(tag, "prevHeadYaw", 0.0F),
            VersionedNbt.getFloat(tag, "lastHandSwingProgress", 0.0F),
            VersionedNbt.getFloat(tag, "limbAnimatorPrevSpeed", 0.0F),
            VersionedNbt.getFloat(tag, "limbAnimatorSpeed", 0.0F),
            VersionedNbt.getFloat(tag, "limbAnimatorPos", 0.0F)
         );
      }

      public String getSerializedName() {
         return "";
      }
   }
}
