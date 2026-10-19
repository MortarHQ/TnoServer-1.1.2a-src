package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper;

import com.yungnickyoung.minecraft.yungscavebiomes.block.PricklyPeachCactusBlock;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal.BuryLootGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal.EatPeachGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal.EatPricklyPeachCactusGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal.EmergeGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal.GiftLootGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal.RunFromPlayerGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal.SnapperStrollGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal.SnapperTemptGoal;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ItemModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.SoundModule;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SandSnapperEntity extends PathfinderMob implements GeoEntity {
   public static final byte HEART_PARTICLES_EVENT = 7;
   public static final byte BURY_LOOT_PARTICLES_EVENT_SAND = 8;
   public static final byte BURY_LOOT_PARTICLES_EVENT_ANCIENT_SAND = 9;
   public static final byte BURY_LOOT_PARTICLES_EVENT_GRAVEL = 10;
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private final RawAnimation EMERGE_PLAYER = RawAnimation.begin().thenPlay("look").thenPlay("look_loop");
   private final RawAnimation EMERGE = RawAnimation.begin().thenPlay("look_turn_head");
   private final RawAnimation DIVE = RawAnimation.begin().thenPlay("diveback");
   private final RawAnimation SWIM = RawAnimation.begin().thenPlay("swim");
   private final RawAnimation WALK = RawAnimation.begin().thenPlay("walk");
   private final RawAnimation DIG_DOWN = RawAnimation.begin().thenPlay("dig_down");
   private final RawAnimation DIG_UP = RawAnimation.begin().thenPlay("dig_up");
   private final RawAnimation EAT = RawAnimation.begin().thenPlay("eat");
   private static final EntityDataAccessor<Boolean> SUBMERGED = SynchedEntityData.defineId(SandSnapperEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> LOOKING_AT_PLAYER = SynchedEntityData.defineId(SandSnapperEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> EMERGING = SynchedEntityData.defineId(SandSnapperEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DIVING = SynchedEntityData.defineId(SandSnapperEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DIGGING_DOWN = SynchedEntityData.defineId(SandSnapperEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DIGGING_UP = SynchedEntityData.defineId(SandSnapperEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(SandSnapperEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> FORCE_SPAWN_DIG_PARTICLES = SynchedEntityData.defineId(
      SandSnapperEntity.class, EntityDataSerializers.BOOLEAN
   );
   private boolean submergedHolder;
   private boolean lookingAtPlayerHolder;
   private boolean emergingHolder;
   private boolean divingHolder;
   private boolean diggingDownHolder;
   private boolean diggingUpHolder;
   private boolean eatingHolder;
   private boolean forceSpawnDigParticlesHolder;
   private int divingTimer;
   private static final int DIVING_LENGTH = 15;
   private int digUpTimer;
   private static final int DIG_UP_LENGTH = 34;
   private int digDownTimer;
   private static final int DIG_DOWN_LENGTH = 37;
   public ItemStack carryingItem = ItemStack.EMPTY;
   private int aboveGroundTimer;
   private static final int MIN_TICKS_ABOVE_GROUND = 100;
   private boolean submergeLocked = false;
   public int friendlyTimer;
   private static final int FRIENDLY_TIMER_LENGTH = 3600;
   private int invalidSubmergeTimer;
   private static final int MAX_INVALID_SUBMERGE_TICKS = 10;
   public int cactusEatCooldownTimer;
   public static final int CACTUS_EAT_COOLDOWN = 6000;
   public static final int CACTUS_EAT_INTERRUPTED_COOLDOWN = 200;
   public int recentlyFedTimer;
   public static final int RECENTLY_FED_COOLDOWN = 1200;
   private int panicSoundCooldownTimer;
   private static final int PANIC_SOUND_COOLDOWN = 60;
   public boolean searchingForGift;

   public SandSnapperEntity(EntityType<SandSnapperEntity> entityType, Level level) {
      super(entityType, level);
   }

   public void addAdditionalSaveData(@NotNull CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      ItemStack itemStack = this.carryingItem;
      if (!itemStack.isEmpty()) {
         tag.put("carryingItemStack", itemStack.save(this.registryAccess()));
      }

      tag.putInt("friendlyTimer", this.friendlyTimer);
      tag.putInt("recentlyFedTimer", this.recentlyFedTimer);
      tag.putBoolean("searchingForGift", this.searchingForGift);
   }

   public void readAdditionalSaveData(@NotNull CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.contains("carryingItemStack")) {
         CompoundTag carryingItemTag = tag.getCompound("carryingItemStack");
         this.carryingItem = ItemStack.parseOptional(this.registryAccess(), carryingItemTag);
      }

      this.friendlyTimer = tag.getInt("friendlyTimer");
      this.recentlyFedTimer = tag.getInt("recentlyFedTimer");
      this.searchingForGift = tag.getBoolean("searchingForGift");
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 35.0)
         .add(Attributes.MOVEMENT_SPEED, 0.15F)
         .add(Attributes.ATTACK_DAMAGE, 3.0)
         .add(Attributes.ARMOR, 2.0);
   }

   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(EMERGING, false);
      builder.define(DIVING, false);
      builder.define(SUBMERGED, false);
      builder.define(LOOKING_AT_PLAYER, false);
      builder.define(DIGGING_DOWN, false);
      builder.define(DIGGING_UP, false);
      builder.define(EATING, false);
      builder.define(FORCE_SPAWN_DIG_PARTICLES, false);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(1, new BuryLootGoal(this, 2, 1, 120));
      this.goalSelector.addGoal(1, new GiftLootGoal(this, 2, 1, 120));
      this.goalSelector.addGoal(2, new SnapperTemptGoal(this, 1.0, 2.5F, 10.0F));
      this.goalSelector.addGoal(3, new EatPeachGoal(this, 16.0F, 4.0F, 1.0F, 1.5F));
      this.goalSelector.addGoal(4, new RunFromPlayerGoal(this, 8.0F, 1.25, 2.0));
      this.goalSelector.addGoal(5, new SnapperStrollGoal(this, 1.0, 1.25));
      this.goalSelector.addGoal(6, new EatPricklyPeachCactusGoal(this, 6, 1, 4.0F, 2.0F));
      this.goalSelector.addGoal(6, new EmergeGoal(this, 8.0F, 2.0F, 32.0F, 100));
   }

   @NotNull
   public EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
      if (this.isEmerging() || this.isDiving() || this.isDiggingUp() || this.isDiggingDown()) {
         return EntityDimensions.fixed(0.4F, 0.7F);
      } else {
         return this.isSubmerged() ? EntityDimensions.fixed(0.01F, 0.01F) : super.getDefaultDimensions(pose);
      }
   }

   public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> dataAccessor) {
      super.onSyncedDataUpdated(dataAccessor);
      if (dataAccessor.equals(EMERGING)) {
         if (this.level().isClientSide) {
            AnimationController<?> controller = (AnimationController<?>)this.getAnimatableInstanceCache()
               .getManagerForId(this.getId())
               .getAnimationControllers()
               .get("generalController");
            if (controller != null) {
               controller.forceAnimationReset();
            }
         }

         this.emergingHolder = (Boolean)this.entityData.get(EMERGING);
         this.refreshDimensions();
      } else if (dataAccessor.equals(DIVING)) {
         this.divingHolder = (Boolean)this.entityData.get(DIVING);
         if (this.isDiving()) {
            this.divingTimer = 15;
         }

         this.refreshDimensions();
      } else if (dataAccessor.equals(SUBMERGED)) {
         this.submergedHolder = (Boolean)this.entityData.get(SUBMERGED);
         this.refreshDimensions();
      } else if (dataAccessor.equals(LOOKING_AT_PLAYER)) {
         this.lookingAtPlayerHolder = (Boolean)this.entityData.get(LOOKING_AT_PLAYER);
      } else if (dataAccessor.equals(DIGGING_UP)) {
         this.diggingUpHolder = (Boolean)this.entityData.get(DIGGING_UP);
         if (this.isDiggingUp()) {
            this.digUpTimer = 34;
         }

         this.refreshDimensions();
      } else if (dataAccessor.equals(DIGGING_DOWN)) {
         this.diggingDownHolder = (Boolean)this.entityData.get(DIGGING_DOWN);
         if (this.isDiggingDown()) {
            this.digDownTimer = 37;
         }

         this.refreshDimensions();
      } else if (dataAccessor.equals(EATING)) {
         this.eatingHolder = (Boolean)this.entityData.get(EATING);
      } else if (dataAccessor.equals(FORCE_SPAWN_DIG_PARTICLES)) {
         this.forceSpawnDigParticlesHolder = (Boolean)this.entityData.get(FORCE_SPAWN_DIG_PARTICLES);
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.aboveGroundTimer > 0) {
            this.aboveGroundTimer--;
         }

         if (this.panicSoundCooldownTimer > 0) {
            this.panicSoundCooldownTimer--;
         }

         if (this.friendlyTimer > 0) {
            this.friendlyTimer--;
         }

         if (this.cactusEatCooldownTimer > 0) {
            this.cactusEatCooldownTimer--;
         }

         if (this.recentlyFedTimer > 0) {
            this.recentlyFedTimer--;
         }

         if (this.canMove()) {
            if (!this.isSubmerged() && this.canSubmerge(false)) {
               this.setSubmerged(true);
               this.invalidSubmergeTimer = 0;
            } else if (this.isSubmerged() && !this.canSubmerge(false)) {
               this.invalidSubmergeTimer++;
               if (this.invalidSubmergeTimer > 10) {
                  this.setSubmerged(false);
                  this.invalidSubmergeTimer = 0;
               }
            }
         }

         if (this.isDiving()) {
            if (this.divingTimer > 0) {
               this.divingTimer--;
            } else {
               this.setDiving(false);
               this.divingTimer = 0;
            }
         }

         if (this.isDiggingDown()) {
            if (this.digDownTimer > 0) {
               this.digDownTimer--;
            } else {
               this.setDiggingDown(false);
               this.digDownTimer = 0;
               this.entityData.set(SUBMERGED, true);
               this.submergedHolder = true;
               this.refreshDimensions();
            }
         }

         if (this.isDiggingUp()) {
            if (this.digUpTimer > 0) {
               this.digUpTimer--;
            } else {
               this.setDiggingUp(false);
               this.digUpTimer = 0;
               this.entityData.set(SUBMERGED, false);
               this.submergedHolder = false;
               this.refreshDimensions();
            }
         }
      }
   }

   public boolean canMove() {
      return !this.isDiving() && !this.isDiggingUp() && !this.isDiggingDown() && !this.isEmerging() && !this.isEating();
   }

   @NotNull
   protected InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
      if (!this.isSubmerged() && !this.isDiggingDown() && !this.isDiggingUp() && !this.isDiving() && !this.searchingForGift && this.carryingItem.isEmpty()) {
         ItemStack itemStack = player.getItemInHand(hand);
         if (itemStack.is((Item)ItemModule.PRICKLY_PEACH_ITEM.get()) && this.recentlyFedTimer <= 0) {
            if (this.level().isClientSide) {
               return InteractionResult.CONSUME;
            }

            if (!player.getAbilities().instabuild) {
               itemStack.shrink(1);
            }

            this.gameEvent(GameEvent.ENTITY_INTERACT);
            this.onEat();
            this.friendlyTimer = 3600;
            this.searchingForGift = true;
            this.recentlyFedTimer = 1200;
            return InteractionResult.SUCCESS;
         } else {
            if (itemStack.isEmpty() || itemStack.is((Item)ItemModule.PRICKLY_PEACH_ITEM.get())) {
               return InteractionResult.PASS;
            }

            if (this.level().isClientSide) {
               return InteractionResult.CONSUME;
            }

            this.carryingItem = itemStack.copyWithCount(1);
            if (!player.getAbilities().instabuild) {
               itemStack.shrink(1);
            }

            this.gameEvent(GameEvent.ENTITY_INTERACT);
            float pitch = Mth.randomBetween(this.random, 0.9F, 1.2F);
            this.playSound((SoundEvent)SoundModule.SAND_SNAPPER_HAPPY.get(), 1.0F, pitch);
            return InteractionResult.SUCCESS;
         }
      } else {
         return InteractionResult.FAIL;
      }
   }

   public void onEat() {
      this.heal(4.0F);
      this.level().broadcastEntityEvent(this, (byte)7);
      float pitch = Mth.randomBetween(this.random, 0.9F, 1.2F);
      this.playSound((SoundEvent)SoundModule.SAND_SNAPPER_HAPPY.get(), 1.0F, pitch);
   }

   protected void spawnHeartParticles() {
      for (int i = 0; i < 7; i++) {
         double xSpeed = this.random.nextGaussian() * 0.02;
         double ySpeed = this.random.nextGaussian() * 0.02;
         double zSpeed = this.random.nextGaussian() * 0.02;
         this.level().addParticle(ParticleTypes.HEART, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), xSpeed, ySpeed, zSpeed);
      }
   }

   protected void spawnLootBuryParticles(BlockState blockState) {
      BlockParticleOption particleOption = new BlockParticleOption(ParticleTypes.BLOCK, blockState);

      for (int i = 0; i < 7; i++) {
         double xSpeed = this.random.nextGaussian() * 0.02;
         double ySpeed = this.random.nextGaussian() * 0.02;
         double zSpeed = this.random.nextGaussian() * 0.02;
         this.level().addParticle(particleOption, this.getRandomX(1.0), this.getRandomY() + 1.5, this.getRandomZ(1.0), xSpeed, ySpeed, zSpeed);
      }
   }

   public void handleEntityEvent(byte eventId) {
      if (eventId == 7) {
         this.spawnHeartParticles();
      } else if (eventId == 8) {
         this.spawnLootBuryParticles(Blocks.SAND.defaultBlockState());
      } else if (eventId == 9) {
         this.spawnLootBuryParticles(((Block)BlockModule.ANCIENT_SAND.get()).defaultBlockState());
      } else if (eventId == 10) {
         this.spawnLootBuryParticles(Blocks.GRAVEL.defaultBlockState());
      } else {
         super.handleEntityEvent(eventId);
      }
   }

   public boolean canCollideWith(@NotNull Entity entity) {
      return !this.isSubmerged() && super.canCollideWith(entity);
   }

   public boolean isPushable() {
      return super.isPushable() && !this.isSubmerged();
   }

   public void push(@NotNull Entity entity) {
      if (!this.isSubmerged()) {
         super.push(entity);
      }
   }

   public boolean isInvulnerable() {
      return this.isSubmerged() || super.isInvulnerable();
   }

   public boolean isInvulnerableTo(@NotNull DamageSource source) {
      if (this.isSubmerged() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !source.isCreativePlayer()) {
         return true;
      } else {
         return source.is(DamageTypes.CACTUS) ? true : super.isInvulnerableTo(source);
      }
   }

   public float getWalkTargetValue(BlockPos movePos, LevelReader level) {
      return level.getBlockState(movePos.below()).is(BlockModule.SAND_SNAPPER_BLOCKS) ? 10.0F : 0.0F;
   }

   public void aiStep() {
      super.aiStep();
      if (!this.canMove()) {
         this.getNavigation().stop();
      }

      Vec3 movement = this.getDeltaMovement();
      if (this.level().isClientSide() && this.isSubmerged() && (movement.horizontalDistance() > 0.01F || this.forceSpawnDigParticlesHolder)) {
         float width = this.getDimensions(this.getPose()).width() * 0.8F;
         Vector3d particlePos = new Vector3d(
            this.getX() + (this.random.nextDouble() - 0.5) * width, this.getY() + 0.1, this.getZ() + (this.random.nextDouble() - 0.5) * width
         );
         Vector3d particleSpeed = new Vector3d(movement.x * -4.0, 1.5, movement.z * -4.0);
         this.level()
            .addParticle(
               new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()),
               particlePos.x,
               particlePos.y,
               particlePos.z,
               particleSpeed.x,
               particleSpeed.y,
               particleSpeed.z
            );
      }
   }

   public boolean isInWall() {
      return false;
   }

   @ParametersAreNonnullByDefault
   protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean $$2) {
      super.dropCustomDeathLoot(serverLevel, damageSource, $$2);
      this.spawnAtLocation(this.carryingItem);
   }

   public boolean requiresCustomPersistence() {
      return super.requiresCustomPersistence() || !this.carryingItem.isEmpty();
   }

   public boolean isOnCactusEatCooldown() {
      return this.cactusEatCooldownTimer > 0;
   }

   public void eatCactus(BlockPos cactusPos) {
      BlockPos cactusBlockPos = new BlockPos(cactusPos);
      BlockState state = this.level().getBlockState(cactusBlockPos);
      if (state.is((Block)BlockModule.PRICKLY_PEACH_CACTUS.get()) && (Boolean)state.getValue(PricklyPeachCactusBlock.FRUIT)) {
         this.level()
            .setBlock(
               cactusBlockPos, (BlockState)((BlockState)state.setValue(PricklyPeachCactusBlock.FRUIT, false)).setValue(PricklyPeachCactusBlock.AGE, 0), 3
            );
         this.onEat();
      }
   }

   public void setSubmerged(boolean isSubmerged) {
      boolean prevValue = this.submergedHolder;
      this.entityData.set(SUBMERGED, isSubmerged);
      this.aboveGroundTimer = isSubmerged ? 0 : 100;
      if (isSubmerged && !prevValue) {
         this.setDiggingDown(true);
      } else if (!isSubmerged && prevValue) {
         this.setDiggingUp(true);
      }

      this.refreshDimensions();
   }

   public boolean canSubmerge(boolean ignoreTimer) {
      if (!ignoreTimer && this.aboveGroundTimer > 0) {
         return false;
      } else if (!this.canMove()) {
         return false;
      } else {
         return this.isSubmergeLocked() ? false : this.canSubmergeAt(this.getOnPos());
      }
   }

   public boolean canSubmergeAt(BlockPos blockPos) {
      BlockState blockStateOn = this.level().getBlockState(blockPos);
      BlockState blockStateBelow = this.level().getBlockState(blockPos.below());
      return blockStateOn.is(BlockModule.SAND_SNAPPER_BLOCKS) || blockStateOn.is(Blocks.AIR) && blockStateBelow.is(BlockModule.SAND_SNAPPER_BLOCKS);
   }

   public void setSubmergeLocked(boolean locked) {
      this.submergeLocked = locked;
   }

   public boolean isSubmergeLocked() {
      return this.submergeLocked;
   }

   public void setEmerging(boolean isEmerging) {
      this.entityData.set(EMERGING, isEmerging);
      this.emergingHolder = isEmerging;
      this.entityData.set(SUBMERGED, !isEmerging);
      this.submergedHolder = !isEmerging;
   }

   public boolean isEmerging() {
      return this.emergingHolder;
   }

   public void setDiving(boolean isDiving) {
      this.entityData.set(DIVING, isDiving);
      this.divingHolder = isDiving;
   }

   public boolean isDiving() {
      return this.divingHolder;
   }

   public boolean isSubmerged() {
      return this.submergedHolder;
   }

   public void setDiggingUp(boolean diggingUp) {
      this.entityData.set(DIGGING_UP, diggingUp);
      this.diggingUpHolder = diggingUp;
   }

   public boolean isDiggingUp() {
      return this.diggingUpHolder;
   }

   public void setDiggingDown(boolean diggingDown) {
      this.entityData.set(DIGGING_DOWN, diggingDown);
      this.diggingDownHolder = diggingDown;
   }

   public boolean isDiggingDown() {
      return this.diggingDownHolder;
   }

   public boolean isLookingAtPlayer() {
      return this.lookingAtPlayerHolder;
   }

   public void setLookingAtPlayer(boolean isLookingAtPlayer) {
      this.entityData.set(LOOKING_AT_PLAYER, isLookingAtPlayer);
      this.lookingAtPlayerHolder = isLookingAtPlayer;
   }

   public boolean isEating() {
      return this.eatingHolder;
   }

   public void setEating(boolean isEating) {
      this.entityData.set(EATING, isEating);
      this.eatingHolder = isEating;
   }

   public void setForceSpawnDigParticles(boolean forceSpawnDigParticles) {
      this.entityData.set(FORCE_SPAWN_DIG_PARTICLES, forceSpawnDigParticles);
      this.forceSpawnDigParticlesHolder = forceSpawnDigParticles;
   }

   public boolean isForceSpawnDigParticles() {
      return this.forceSpawnDigParticlesHolder;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource $$0) {
      return (SoundEvent)SoundModule.SAND_SNAPPER_HURT.get();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return (SoundEvent)SoundModule.SAND_SNAPPER_DEATH.get();
   }

   public void tryPlayPanicSound() {
      if (this.panicSoundCooldownTimer <= 0) {
         float pitch = Mth.randomBetween(this.getRandom(), 1.0F, 1.2F);
         this.playSound((SoundEvent)SoundModule.SAND_SNAPPER_PANIC.get(), 1.0F, pitch);
         this.panicSoundCooldownTimer = 60;
      }
   }

   private <E extends GeoAnimatable> PlayState generalPredicate(AnimationState<E> event) {
      if (this.isEmerging()) {
         if (this.isLookingAtPlayer()) {
            event.getController().setAnimation(this.EMERGE_PLAYER);
         } else {
            event.getController().setAnimation(this.EMERGE);
         }

         return PlayState.CONTINUE;
      } else {
         if (this.isEating()) {
            event.getController().setAnimation(this.EAT);
            return PlayState.CONTINUE;
         }

         if (this.isDiving()) {
            event.getController().setAnimation(this.DIVE);
            return PlayState.CONTINUE;
         }

         if (this.isDiggingDown()) {
            event.getController().setAnimation(this.DIG_DOWN);
            return PlayState.CONTINUE;
         }

         if (this.isDiggingUp()) {
            event.getController().setAnimation(this.DIG_UP);
            return PlayState.CONTINUE;
         }

         if (event.isMoving()) {
            if (this.isSubmerged()) {
               event.getController().setAnimation(this.SWIM);
            } else {
               event.getController().setAnimation(this.WALK);
            }

            return PlayState.CONTINUE;
         } else {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
         }
      }
   }

   protected void playStepSound(BlockPos pos, BlockState stateOn) {
      if (!this.isSubmerged()) {
         super.playStepSound(pos, stateOn);
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "generalController", 0, this::generalPredicate));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
