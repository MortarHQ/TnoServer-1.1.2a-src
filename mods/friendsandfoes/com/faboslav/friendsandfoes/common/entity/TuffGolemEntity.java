package com.faboslav.friendsandfoes.common.entity;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.ai.brain.TuffGolemBrain;
import com.faboslav.friendsandfoes.common.entity.animation.AnimatedEntity;
import com.faboslav.friendsandfoes.common.entity.animation.TuffGolemAnimations;
import com.faboslav.friendsandfoes.common.entity.animation.animator.context.AnimationContextTracker;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityDataSerializers;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class TuffGolemEntity extends AbstractGolem implements AnimatedEntity {
   private static final float MOVEMENT_SPEED = 0.225F;
   private static final float MOVEMENT_SPEED_WITH_ITEM = 0.175F;
   private static final int TUFF_HEAL_AMOUNT = 5;
   private static final int INACTIVE_TICKS_AFTER_SPAWN = 200;
   private static final String COLOR_NBT_NAME = "Color";
   private static final String PREV_POSE_NBT_NAME = "PrevPose";
   private static final String POSE_NBT_NAME = "Pose";
   private static final String IS_GLUED_NBT_NAME = "IsGlued";
   private static final String HOME_NBT_NAME_X = "x";
   private static final String HOME_NBT_NAME_Y = "y";
   private static final String HOME_NBT_NAME_Z = "z";
   private static final String HOME_NBT_NAME_YAW = "yaw";
   private static final EntityDataAccessor<String> COLOR = SynchedEntityData.defineId(TuffGolemEntity.class, EntityDataSerializers.STRING);
   private static final EntityDataAccessor<Integer> POSE_TICKS = SynchedEntityData.defineId(TuffGolemEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<FriendsAndFoesEntityPose> ENTITY_POSE = SynchedEntityData.defineId(
      TuffGolemEntity.class, FriendsAndFoesEntityDataSerializers.ENTITY_POSE
   );
   private static final EntityDataAccessor<FriendsAndFoesEntityPose> PREV_ENTITY_POSE = SynchedEntityData.defineId(
      TuffGolemEntity.class, FriendsAndFoesEntityDataSerializers.ENTITY_POSE
   );
   private static final EntityDataAccessor<Boolean> IS_GLUED = SynchedEntityData.defineId(TuffGolemEntity.class, EntityDataSerializers.BOOLEAN);
   private int inactiveTicksAfterSpawn = 0;
   private TuffGolemEntity.Home home;
   private AnimationContextTracker animationContextTracker;

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
      return TuffGolemAnimations.ANIMATIONS;
   }

   @Override
   public AnimationHolder getMovementAnimation() {
      return this.isHoldingItem() ? TuffGolemAnimations.WALK_WITH_ITEM : TuffGolemAnimations.WALK;
   }

   @Override
   public int getCurrentAnimationTick() {
      return (Integer)this.entityData.get(POSE_TICKS);
   }

   @Override
   public void setCurrentAnimationTick(int keyframeAnimationTicks) {
      this.entityData.set(POSE_TICKS, keyframeAnimationTicks);
   }

   public TuffGolemEntity(EntityType<? extends TuffGolemEntity> entityType, Level world) {
      super(entityType, world);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
      SpawnGroupData superEntityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData);
      if (spawnReason == VersionedEntitySpawnReason.MOB_SUMMONED || spawnReason == VersionedEntitySpawnReason.COMMAND) {
         float randomSpawnYaw = 90.0F * this.getRandom().nextIntBetweenInclusive(0, 3);
         this.setSpawnYaw(randomSpawnYaw);
      }

      this.setPrevEntityPose(FriendsAndFoesEntityPose.STANDING);
      this.setEntityPoseWithoutPrevPose(FriendsAndFoesEntityPose.STANDING);
      this.setHome(this.getNewHome());
      TuffGolemBrain.setSleepCooldown(this);
      this.inactiveTicksAfterSpawn = 200;
      return superEntityData;
   }

   protected Brain<?> makeBrain(Dynamic<?> dynamic) {
      return TuffGolemBrain.create(dynamic);
   }

   public Brain<TuffGolemEntity> getBrain() {
      return super.getBrain();
   }

   protected void customServerAiStep() {
      ServerLevel level = (ServerLevel)this.level();
      ProfilerFiller profiler = VersionedProfilerProvider.getProfiler(this);
      profiler.push("tuffGolemBrain");
      this.getBrain().tick(level, this);
      profiler.pop();
      profiler.push("tuffGolemActivityUpdate");
      TuffGolemBrain.updateActivities(this);
      profiler.pop();
      super.customServerAiStep();
   }

   public static Builder createTuffGolemAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.225F).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(COLOR, TuffGolemEntity.Color.RED.getName());
      builder.define(PREV_ENTITY_POSE, FriendsAndFoesEntityPose.STANDING);
      builder.define(POSE_TICKS, 0);
      builder.define(ENTITY_POSE, FriendsAndFoesEntityPose.STANDING);
      builder.define(IS_GLUED, false);
   }

   public void addAdditionalSaveData(CompoundTag nbt) {
      super.addAdditionalSaveData(nbt);
      nbt.putString("Color", this.getColor().getName());
      nbt.putString("PrevPose", this.getPrevEntityPose().name());
      nbt.putString("Pose", this.getEntityPose().name());
      nbt.putBoolean("IsGlued", this.isGlued());
      nbt.putDouble("x", this.getHomePos().x);
      nbt.putDouble("y", this.getHomePos().y);
      nbt.putDouble("z", this.getHomePos().z);
      nbt.putFloat("yaw", this.yBodyRot);
   }

   public void readAdditionalSaveData(CompoundTag nbt) {
      super.readAdditionalSaveData(nbt);
      this.setColor(TuffGolemEntity.Color.fromName(VersionedNbt.getString(nbt, "Color", TuffGolemEntity.Color.RED.getName())));
      this.setGlued(VersionedNbt.getBoolean(nbt, "IsGlued", false));
      this.setHome(
         new TuffGolemEntity.Home(
            VersionedNbt.getDouble(nbt, "x", this.position().x()),
            VersionedNbt.getDouble(nbt, "y", this.position().y()),
            VersionedNbt.getDouble(nbt, "z", this.position().z()),
            VersionedNbt.getFloat(nbt, "yaw", this.yBodyRot)
         )
      );
      if (this.isAtHomePos()) {
         this.setSpawnYaw(this.getHomeYaw());
      }

      String prevSavedPose = VersionedNbt.getString(nbt, "PrevPose", "");
      if (prevSavedPose != "") {
         try {
            this.setPrevEntityPose(FriendsAndFoesEntityPose.valueOf(prevSavedPose));
         } catch (Exception var6) {
         }
      }

      String savedPose = VersionedNbt.getString(nbt, "Pose", "");
      if (savedPose != "") {
         FriendsAndFoesEntityPose entityPose;
         try {
            entityPose = FriendsAndFoesEntityPose.valueOf(savedPose);
         } catch (Exception e) {
            if (this.isHoldingItem()) {
               entityPose = FriendsAndFoesEntityPose.STANDING_WITH_ITEM;
            } else {
               entityPose = FriendsAndFoesEntityPose.STANDING;
            }
         }

         this.setEntityPoseWithoutPrevPose(entityPose);
         if (!this.level().isClientSide() && (entityPose == FriendsAndFoesEntityPose.SLEEPING || entityPose == FriendsAndFoesEntityPose.SLEEPING_WITH_ITEM)) {
            this.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            this.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
            TuffGolemBrain.resetSleepCooldown(this);
         }
      }
   }

   private SoundEvent getGlueOnSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_GLUE_ON.get();
   }

   private void playGlueOnSound() {
      this.playSound(this.getGlueOnSound(), 1.0F, 1.0F);
   }

   private SoundEvent getGlueOffSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_GLUE_OFF.get();
   }

   private void playGlueOffSound() {
      this.playSound(this.getGlueOffSound(), 1.0F, 1.0F);
   }

   public SoundEvent getMoveSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_MOVE.get();
   }

   public void playMoveSound() {
      this.playSound(this.getMoveSound(), 1.0F, 1.05F + this.getRandom().nextFloat() * 0.05F);
   }

   public SoundEvent getHoldItemSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_HOLD_ITEM.get();
   }

   public void playHoldItemSound() {
      this.playSound(this.getHoldItemSound(), 1.0F, 1.05F + this.getRandom().nextFloat() * 0.05F);
   }

   public SoundEvent getDropItemSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_DROP_ITEM.get();
   }

   public void playDropItemSound() {
      this.playSound(this.getDropItemSound(), 1.0F, 1.05F + this.getRandom().nextFloat() * 0.05F);
   }

   private SoundEvent getRepairSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_REPAIR.get();
   }

   private void playRepairSound() {
      this.playSound(this.getRepairSound(), 1.0F, 1.0F + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F);
   }

   public SoundEvent getWakeSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_WAKE.get();
   }

   public void playWakeSound() {
      this.playSound(this.getWakeSound(), 1.0F, 1.05F + this.getRandom().nextFloat() * 0.05F);
   }

   public SoundEvent getSleepSound() {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_SLEEP.get();
   }

   public void playSleepSound() {
      this.playSound(this.getSleepSound(), 1.0F, 1.05F + this.getRandom().nextFloat() * 0.05F);
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_HURT.get();
   }

   protected void playHurtSound(DamageSource source) {
      this.ambientSoundTime = -this.getAmbientSoundInterval();
      this.playSound(this.getHurtSound(source), 2.0F, 0.7F + this.getRandom().nextFloat() * 0.15F);
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      if (!this.isInSleepingPose() && !state.liquid()) {
         BlockState blockState = this.level().getBlockState(pos.above());
         SoundType blockSoundGroup = blockState.is(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState.getSoundType() : state.getSoundType();
         this.playSound(
            (SoundEvent)FriendsAndFoesSoundEvents.ENTITY_TUFF_GOLEM_STEP.get(), blockSoundGroup.getVolume(), 0.75F + this.getRandom().nextFloat() * 0.15F
         );
      }
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      Item itemInHand = itemStack.getItem();
      boolean interactionResult = false;
      if (itemInHand == Items.TUFF) {
         interactionResult = this.tryToInteractMobWithTuff(player, itemStack);
      } else if (itemInHand instanceof DyeItem) {
         interactionResult = this.tryToInteractMobWithDye(player, itemStack);
      } else if (itemInHand instanceof HoneycombItem) {
         interactionResult = this.tryToInteractMobWithHoneycomb(player, itemStack);
      } else if (itemInHand instanceof AxeItem) {
         interactionResult = this.tryToInteractMobWithAxe(player, hand, itemStack);
      }

      if (!interactionResult) {
         interactionResult = this.tryToInteractMobWithItem(player, itemStack);
      }

      if (interactionResult) {
         this.gameEvent(GameEvent.ENTITY_INTERACT, this);
         return VersionedInteractionResult.success(this);
      } else {
         return super.mobInteract(player, hand);
      }
   }

   private boolean tryToInteractMobWithTuff(Player player, ItemStack itemStack) {
      if (this.getHealth() >= this.getMaxHealth()) {
         return false;
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         this.heal(5.0F);
         if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
         }

         this.playRepairSound();
      }

      return true;
   }

   private boolean tryToInteractMobWithDye(Player player, ItemStack itemStack) {
      TuffGolemEntity.Color usedColor = TuffGolemEntity.Color.fromDyeColor(((DyeItem)itemStack.getItem()).getDyeColor());
      if (this.getColor() == usedColor) {
         return false;
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         this.setColor(usedColor);
         if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
         }

         this.playSound(SoundEvents.DYE_USE, 1.0F, 1.0F);
      }

      return true;
   }

   private boolean tryToInteractMobWithHoneycomb(Player player, ItemStack itemStack) {
      if (this.isGlued()) {
         return false;
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         this.setGlued(true);
         if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
         }

         this.playGlueOnSound();
         ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_ON, 7, 1.0);
      }

      return true;
   }

   private boolean tryToInteractMobWithAxe(Player player, InteractionHand hand, ItemStack itemStack) {
      if (!this.isGlued()) {
         return false;
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         this.setGlued(false);
         this.playGlueOffSound();
         ParticleSpawner.spawnParticles(this, ParticleTypes.WAX_OFF, 7, 1.0);
         if (player.getAbilities().instabuild) {
            itemStack.hurtAndBreak(1, player, VersionedEntity.getEquipmentSlotForItem(hand));
         }
      }

      return true;
   }

   private boolean tryToInteractMobWithItem(Player player, ItemStack itemStack) {
      if ((this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() != Items.AIR || itemStack.getItem() != Items.AIR)
         && !(itemStack.getItem() instanceof SpawnEggItem)
         && !this.isAnyKeyframeAnimationRunning()) {
         if (this.level() instanceof ServerLevel serverLevel) {
            MovementUtil.stopMovement(this);
            if (this.isHoldingItem()) {
               if (!player.getAbilities().instabuild) {
                  VersionedEntity.spawnAtLocation(this, this.getItemBySlot(EquipmentSlot.MAINHAND));
               }

               this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
               this.startStanding();
               if (this.isInSleepingPose()) {
                  this.playWakeSound();
               } else {
                  this.playDropItemSound();
               }
            } else {
               ItemStack itemsStackToBeEquipped = itemStack.copy();
               if (!player.getAbilities().instabuild) {
                  itemStack.shrink(1);
               }

               itemsStackToBeEquipped.setCount(1);
               this.setItemSlot(EquipmentSlot.MAINHAND, itemsStackToBeEquipped);
               this.startStandingWithItem();
               this.playHoldItemSound();
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void setColor(TuffGolemEntity.Color color) {
      this.entityData.set(COLOR, color.getName());
   }

   public TuffGolemEntity.Color getColor() {
      return TuffGolemEntity.Color.fromName((String)this.entityData.get(COLOR));
   }

   public void setEntityPose(FriendsAndFoesEntityPose pose) {
      if (!this.level().isClientSide()) {
         this.setPrevEntityPose(this.getEntityPose());
         this.entityData.set(ENTITY_POSE, pose);
      }
   }

   public FriendsAndFoesEntityPose getEntityPose() {
      return (FriendsAndFoesEntityPose)this.entityData.get(ENTITY_POSE);
   }

   public boolean isInEntityPose(FriendsAndFoesEntityPose pose) {
      return this.getEntityPose() == pose;
   }

   public void setEntityPoseWithoutPrevPose(FriendsAndFoesEntityPose pose) {
      if (!this.level().isClientSide()) {
         this.setEntityPose(pose);
      }
   }

   public void setPrevEntityPose(FriendsAndFoesEntityPose pose) {
      if (!this.level().isClientSide()) {
         this.entityData.set(PREV_ENTITY_POSE, pose);
      }
   }

   public FriendsAndFoesEntityPose getPrevEntityPose() {
      return (FriendsAndFoesEntityPose)this.entityData.get(PREV_ENTITY_POSE);
   }

   public boolean wasInEntityPose(FriendsAndFoesEntityPose pose) {
      return this.getPrevEntityPose() == pose;
   }

   public boolean isInStandingPose() {
      return this.isInEntityPose(FriendsAndFoesEntityPose.STANDING) || this.isInEntityPose(FriendsAndFoesEntityPose.STANDING_WITH_ITEM);
   }

   public boolean isInSleepingPose() {
      return this.isInEntityPose(FriendsAndFoesEntityPose.SLEEPING) || this.isInEntityPose(FriendsAndFoesEntityPose.SLEEPING_WITH_ITEM);
   }

   public boolean isInHoldingItemPose() {
      return this.isInEntityPose(FriendsAndFoesEntityPose.STANDING_WITH_ITEM) || this.isInEntityPose(FriendsAndFoesEntityPose.SLEEPING_WITH_ITEM);
   }

   public void setGlued(boolean isGlued) {
      this.entityData.set(IS_GLUED, isGlued);
   }

   public boolean isGlued() {
      return (Boolean)this.entityData.get(IS_GLUED);
   }

   public TuffGolemEntity.Home getNewHome() {
      return new TuffGolemEntity.Home(this.position().x(), this.position().y(), this.position().z(), this.yBodyRot);
   }

   public void setHome(TuffGolemEntity.Home home) {
      this.home = home;
   }

   public TuffGolemEntity.Home getHome() {
      return this.home;
   }

   public Vec3 getHomePos() {
      TuffGolemEntity.Home home = this.getHome();
      return new Vec3(home.x, home.y, home.z);
   }

   public float getHomeYaw() {
      return this.getHome().yaw;
   }

   public boolean isAtHomePos() {
      return this.distanceToSqr(this.getHomePos()) < 0.1;
   }

   public boolean isCloseToHomePos(float distance) {
      return this.distanceToSqr(this.getHomePos()) < distance;
   }

   public boolean isAtHomeYaw() {
      return this.yRotO == this.getHomeYaw()
         && this.getYRot() == this.getHomeYaw()
         && this.yBodyRotO == this.getHomeYaw()
         && this.getVisualRotationYInDegrees() == this.getHomeYaw()
         && this.yHeadRotO == this.getHomeYaw()
         && this.getYHeadRot() == this.getHomeYaw();
   }

   public boolean isAtHome() {
      return this.isAtHomePos() && this.isAtHomeYaw();
   }

   public boolean isHoldingItem() {
      return this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() != Items.AIR;
   }

   public boolean isShowingItem() {
      return this.isHoldingItem() && this.isInHoldingItemPose();
   }

   public boolean isNotImmobilized() {
      return this.getCurrentAnimationTick() == 0 && this.inactiveTicksAfterSpawn == 0 && !this.isGlued() && !this.isInSleepingPose();
   }

   public void startSleeping() {
      if (!this.isInEntityPose(FriendsAndFoesEntityPose.SLEEPING)) {
         this.playSleepSound();
         this.setEntityPose(FriendsAndFoesEntityPose.SLEEPING);
      }
   }

   public void startSleepingWithItem() {
      if (!this.isInEntityPose(FriendsAndFoesEntityPose.SLEEPING_WITH_ITEM)) {
         this.playSleepSound();
         this.setEntityPose(FriendsAndFoesEntityPose.SLEEPING_WITH_ITEM);
      }
   }

   public void startStanding() {
      if (!this.isInEntityPose(FriendsAndFoesEntityPose.STANDING)) {
         this.setEntityPose(FriendsAndFoesEntityPose.STANDING);
      }
   }

   public void startStandingWithItem() {
      if (!this.isInEntityPose(FriendsAndFoesEntityPose.STANDING_WITH_ITEM)) {
         this.setEntityPose(FriendsAndFoesEntityPose.STANDING_WITH_ITEM);
      }
   }

   public void tick() {
      if (!this.level().isClientSide() && !FriendsAndFoes.getConfig().enableTuffGolem) {
         this.discard();
      }

      if (!this.level().isClientSide() && this.inactiveTicksAfterSpawn > 0) {
         this.inactiveTicksAfterSpawn--;
      }

      this.updateKeyframeAnimations();
      super.tick();
   }

   public boolean isPushable() {
      return !this.isSleeping();
   }

   private void updateKeyframeAnimations() {
      if (!this.level().isClientSide()) {
         this.updateCurrentAnimationTick();
      }

      AnimationHolder animationToStart = this.getAnimationByPose();
      if (animationToStart != null && !this.isKeyframeAnimationRunning(animationToStart)) {
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

   @Nullable
   @Override
   public AnimationHolder getAnimationByPose() {
      FriendsAndFoesEntityPose prevPose = this.getPrevEntityPose();
      FriendsAndFoesEntityPose pose = this.getEntityPose();
      if (pose == prevPose) {
         return null;
      }

      AnimationHolder animationHolder = null;
      if (this.wasInEntityPose(FriendsAndFoesEntityPose.STANDING)) {
         if (this.isInEntityPose(FriendsAndFoesEntityPose.STANDING_WITH_ITEM)) {
            animationHolder = TuffGolemAnimations.SHOW_ITEM;
         } else if (this.isInEntityPose(FriendsAndFoesEntityPose.SLEEPING)) {
            animationHolder = TuffGolemAnimations.SLEEP;
         }
      } else if (this.wasInEntityPose(FriendsAndFoesEntityPose.STANDING_WITH_ITEM)) {
         if (this.isInEntityPose(FriendsAndFoesEntityPose.STANDING)) {
            animationHolder = TuffGolemAnimations.HIDE_ITEM;
         } else if (this.isInEntityPose(FriendsAndFoesEntityPose.SLEEPING_WITH_ITEM)) {
            animationHolder = TuffGolemAnimations.SLEEP_WITH_ITEM;
         }
      } else if (this.wasInEntityPose(FriendsAndFoesEntityPose.SLEEPING)) {
         if (this.isInEntityPose(FriendsAndFoesEntityPose.SLEEPING_WITH_ITEM)) {
            animationHolder = TuffGolemAnimations.SHOW_ITEM;
         } else if (this.isInEntityPose(FriendsAndFoesEntityPose.STANDING_WITH_ITEM)) {
            animationHolder = TuffGolemAnimations.WAKE_AND_SHOW_ITEM;
         } else if (this.isInEntityPose(FriendsAndFoesEntityPose.STANDING)) {
            animationHolder = TuffGolemAnimations.WAKE;
         }
      } else if (this.wasInEntityPose(FriendsAndFoesEntityPose.SLEEPING_WITH_ITEM)) {
         if (this.isInEntityPose(FriendsAndFoesEntityPose.SLEEPING)) {
            animationHolder = TuffGolemAnimations.HIDE_ITEM;
         } else if (this.isInEntityPose(FriendsAndFoesEntityPose.STANDING)) {
            animationHolder = TuffGolemAnimations.WAKE_AND_HIDE_ITEM;
         } else if (this.isInEntityPose(FriendsAndFoesEntityPose.STANDING_WITH_ITEM)) {
            animationHolder = TuffGolemAnimations.WAKE_WITH_ITEM;
         }
      }

      return animationHolder;
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      Level level = this.level();
      Entity attacker = damageSource.getEntity();
      if (!(attacker instanceof LightningBolt) && damageSource != this.damageSources().sweetBerryBush()) {
         if (!this.level().isClientSide() && this.isInSleepingPose()) {
            if (this.isHoldingItem()) {
               this.startStandingWithItem();
               if (this.isInSleepingPose()) {
                  this.playWakeSound();
               } else {
                  this.playMoveSound();
               }
            } else {
               this.startStanding();
               if (this.isInSleepingPose()) {
                  this.playWakeSound();
               } else {
                  this.playMoveSound();
               }
            }
         }

         return super.hurt(damageSource, amount);
      } else {
         return false;
      }
   }

   public boolean canBeLeashed() {
      return super.canBeLeashed() && !this.isInSleepingPose();
   }

   public Vec3 getLeashOffset() {
      return new Vec3(0.0, this.getEyeHeight() * 0.45, 0.0);
   }

   public float getSpeed() {
      return this.isHoldingItem() ? 0.175F : 0.225F;
   }

   protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
      super.dropCustomDeathLoot(level, damageSource, recentlyHit);
      if (this.isHoldingItem()) {
         VersionedEntity.spawnAtLocation(this, this.getItemBySlot(EquipmentSlot.MAINHAND));
         this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
      }
   }

   public float getMovementSpeedModifier() {
      return this.isHoldingItem() ? 1.0F : 0.7777778F;
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   public void setSpawnYaw(float yaw) {
      this.yRotO = yaw;
      this.setYRot(yaw);
      this.yBodyRotO = yaw;
      this.setYBodyRot(yaw);
      this.yHeadRotO = yaw;
      this.setYHeadRot(yaw);
   }

   public enum Color {
      RED("red"),
      BLACK("black"),
      BLUE("blue"),
      BROWN("brown"),
      CYAN("cyan"),
      GRAY("gray"),
      GREEN("green"),
      LIGHT_BLUE("light_blue"),
      LIGHT_GRAY("light_gray"),
      LIME("lime"),
      MAGENTA("magenta"),
      ORANGE("orange"),
      PINK("pink"),
      PURPLE("purple"),
      WHITE("white"),
      YELLOW("yellow");

      private final String name;

      Color(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      private static TuffGolemEntity.Color fromName(String name) {
         TuffGolemEntity.Color[] colors = values();

         for (TuffGolemEntity.Color color : colors) {
            if (color.name.equals(name)) {
               return color;
            }
         }

         return RED;
      }

      public static TuffGolemEntity.Color fromDyeColor(DyeColor dyeColor) {
         return switch (dyeColor) {
            case BLACK -> BLACK;
            case BLUE -> BLUE;
            case BROWN -> BROWN;
            case CYAN -> CYAN;
            case GRAY -> GRAY;
            case GREEN -> GREEN;
            case LIGHT_BLUE -> LIGHT_BLUE;
            case LIGHT_GRAY -> LIGHT_GRAY;
            case LIME -> LIME;
            case MAGENTA -> MAGENTA;
            case ORANGE -> ORANGE;
            case PINK -> PINK;
            case PURPLE -> PURPLE;
            case WHITE -> WHITE;
            case YELLOW -> YELLOW;
            default -> RED;
         };
      }

      public static TuffGolemEntity.Color fromWool(Block block) {
         if (block == Blocks.BLACK_WOOL) {
            return BLACK;
         } else if (block == Blocks.BLUE_WOOL) {
            return BLUE;
         } else if (block == Blocks.BROWN_WOOL) {
            return BROWN;
         } else if (block == Blocks.CYAN_WOOL) {
            return CYAN;
         } else if (block == Blocks.GRAY_WOOL) {
            return GRAY;
         } else if (block == Blocks.GREEN_WOOL) {
            return GREEN;
         } else if (block == Blocks.LIGHT_BLUE_WOOL) {
            return LIGHT_BLUE;
         } else if (block == Blocks.LIGHT_GRAY_WOOL) {
            return LIGHT_GRAY;
         } else if (block == Blocks.LIME_WOOL) {
            return LIME;
         } else if (block == Blocks.MAGENTA_WOOL) {
            return MAGENTA;
         } else if (block == Blocks.ORANGE_WOOL) {
            return ORANGE;
         } else if (block == Blocks.PINK_WOOL) {
            return PINK;
         } else if (block == Blocks.PURPLE_WOOL) {
            return PURPLE;
         } else if (block == Blocks.WHITE_WOOL) {
            return WHITE;
         } else {
            return block == Blocks.YELLOW_WOOL ? YELLOW : RED;
         }
      }
   }

   public record Home(double x, double y, double z, float yaw) {
      public static final Codec<TuffGolemEntity.Home> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.DOUBLE.fieldOf("x").forGetter(TuffGolemEntity.Home::x),
               Codec.DOUBLE.fieldOf("y").forGetter(TuffGolemEntity.Home::y),
               Codec.DOUBLE.fieldOf("z").forGetter(TuffGolemEntity.Home::z),
               Codec.FLOAT.fieldOf("yaw").forGetter(TuffGolemEntity.Home::yaw)
            )
            .apply(instance, TuffGolemEntity.Home::new)
      );
   }
}
