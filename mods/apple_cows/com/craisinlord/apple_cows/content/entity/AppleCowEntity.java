package com.craisinlord.apple_cows.content.entity;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class AppleCowEntity extends Cow implements GeoEntity, Shearable {
   private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(AppleCowEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> QUIRK_TICKS = SynchedEntityData.defineId(AppleCowEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(AppleCowEntity.class, EntityDataSerializers.BOOLEAN);
   private static final String SLEEPING_KEY = "Sleeping";
   private static final String SLEEP_TICKS_KEY = "SleepTicks";
   private static final String NAP_COOLDOWN_KEY = "NapCooldown";
   private static final String QUIRK_TICKS_KEY = "QuirkTicks";
   private static final String SHEARED_KEY = "Sheared";
   private static final String GRASS_EAT_TICKS_KEY = "GrassEatTicks";
   private static final String MAIN_CONTROLLER = "main_controller";
   private static final String SLEEP_TRANSITION_CONTROLLER = "sleep_transition_controller";
   private static final String START_SLEEP_TRIGGER = "start_sleep";
   private static final String WAKE_UP_TRIGGER = "wake_up";
   private static final int IDLE_ANIMATION_CYCLE_TICKS = 40;
   private static final int QUIRK_ANIMATION_TICKS = 35;
   private static final int QUIRK_CHANCE = 50;
   private static final int DAY_NAP_MIN_TICKS = 1200;
   private static final int DAY_NAP_MAX_TICKS = 2400;
   private static final int DAY_NAP_COOLDOWN_TICKS = 12000;
   private static final int NIGHT_NAP_COOLDOWN_TICKS = 9600;
   private static final int GRASS_REGROW_TICKS = 3600;
   private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
   private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
   private static final RawAnimation SLEEP_ANIM = RawAnimation.begin().thenLoop("sleep");
   private static final RawAnimation QUIRK_ANIM = RawAnimation.begin().thenPlay("quirk");
   private static final RawAnimation START_SLEEP_ANIM = RawAnimation.begin().thenPlay("start sleep");
   private static final RawAnimation WAKE_UP_ANIM = RawAnimation.begin().thenPlay("wake up");
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
   private int sleepTicksRemaining;
   private int napCooldownTicks = this.randomNapCooldown();
   private int idleAnimationCycleTicks;
   private int grassEatTicks;

   protected AppleCowEntity(EntityType<? extends Cow> entityType, Level level) {
      super(entityType, level);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(SLEEPING, false);
      builder.define(QUIRK_TICKS, 0);
      builder.define(SHEARED, false);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new AppleCowEntity.AppleCowSleepGoal());
      this.goalSelector.addGoal(1, new AppleCowEntity.AppleCowGrazeGoal());
   }

   public Cow getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
      return this.getType().create(level) instanceof Cow offspring ? offspring : null;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "main_controller", 0, this::mainAnimController));
      controllers.add(
         new AnimationController(this, "sleep_transition_controller", 0, state -> PlayState.STOP)
            .triggerableAnim("start_sleep", START_SLEEP_ANIM)
            .triggerableAnim("wake_up", WAKE_UP_ANIM)
      );
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         int quirkTicks = this.getQuirkTicks();
         if (quirkTicks > 0) {
            this.setQuirkTicks(quirkTicks - 1);
         }

         if (this.napCooldownTicks > 0) {
            this.napCooldownTicks--;
         }

         if (this.isSleeping()) {
            this.applySleepStillness();
            this.idleAnimationCycleTicks = 0;
            if (this.shouldWakeUp()) {
               this.stopCowSleeping();
            } else {
               if (this.sleepTicksRemaining > 0) {
                  this.sleepTicksRemaining--;
                  if (this.sleepTicksRemaining <= 0) {
                     this.stopCowSleeping();
                  }
               }
            }
         } else {
            if (!this.isSheared()) {
               this.grassEatTicks = 0;
            }

            if (this.shouldStartNap()) {
               this.startSleeping(Mth.nextInt(this.random, 1200, 2400));
            } else {
               this.tickIdleQuirk();
            }
         }
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      this.stopCowSleeping();
      return super.hurt(source, amount);
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack heldItem = player.getItemInHand(hand);
      if (heldItem.is(Items.SHEARS) && this.readyForShearing()) {
         if (!this.level().isClientSide) {
            this.shear(player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS);
            heldItem.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
         }

         this.stopCowSleeping();
         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else {
         this.stopCowSleeping();
         return super.mobInteract(player, hand);
      }
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putBoolean("Sleeping", this.isSleeping());
      tag.putInt("SleepTicks", this.sleepTicksRemaining);
      tag.putInt("NapCooldown", this.napCooldownTicks);
      tag.putInt("QuirkTicks", this.getQuirkTicks());
      tag.putBoolean("Sheared", this.isSheared());
      tag.putInt("GrassEatTicks", this.grassEatTicks);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.entityData.set(SLEEPING, tag.getBoolean("Sleeping"));
      this.sleepTicksRemaining = tag.getInt("SleepTicks");
      this.napCooldownTicks = tag.contains("NapCooldown") ? tag.getInt("NapCooldown") : this.randomNapCooldown();
      this.setQuirkTicks(tag.getInt("QuirkTicks"));
      this.setSheared(tag.getBoolean("Sheared"));
      this.grassEatTicks = tag.getInt("GrassEatTicks");
   }

   public boolean isSleeping() {
      return (Boolean)this.entityData.get(SLEEPING);
   }

   public int getQuirkTicks() {
      return (Integer)this.entityData.get(QUIRK_TICKS);
   }

   public boolean isSheared() {
      return (Boolean)this.entityData.get(SHEARED);
   }

   private void setQuirkTicks(int ticks) {
      this.entityData.set(QUIRK_TICKS, Math.max(0, ticks));
   }

   private void setSheared(boolean sheared) {
      this.entityData.set(SHEARED, sheared);
   }

   private PlayState mainAnimController(AnimationState<AppleCowEntity> state) {
      if (this.isSleeping()) {
         state.getController().setAnimationSpeed(1.0);
         return state.setAndContinue(SLEEP_ANIM);
      } else if (state.isMoving()) {
         state.getController().setAnimationSpeed(1.0);
         return state.setAndContinue(WALK_ANIM);
      } else if (this.getQuirkTicks() > 0) {
         state.getController().setAnimationSpeed(1.0);
         return state.setAndContinue(QUIRK_ANIM);
      } else {
         state.getController().setAnimationSpeed(1.0);
         return state.setAndContinue(IDLE_ANIM);
      }
   }

   private void tickIdleQuirk() {
      if (this.getQuirkTicks() <= 0 && this.isEligibleForSleep() && !this.isMovingForAnimation()) {
         this.idleAnimationCycleTicks++;
         if (this.idleAnimationCycleTicks >= 40) {
            this.idleAnimationCycleTicks = 0;
            if (this.random.nextInt(50) == 0) {
               this.setQuirkTicks(35);
            }
         }
      } else {
         this.idleAnimationCycleTicks = 0;
      }
   }

   private boolean shouldStartNap() {
      return this.napCooldownTicks <= 0 && this.isEligibleForSleep();
   }

   private boolean shouldWakeUp() {
      return !this.isSleeping()
         ? false
         : !this.isAlive()
            || this.isInWaterOrBubble()
            || this.isOnFire()
            || this.hurtTime > 0
            || this.isLeashed()
            || this.isPassenger()
            || this.isVehicle()
            || this.isInLove()
            || !this.onGround();
   }

   private boolean isEligibleForSleep() {
      return this.onGround()
         && !this.isInWaterOrBubble()
         && !this.isOnFire()
         && !this.isLeashed()
         && !this.isPassenger()
         && !this.isVehicle()
         && !this.isInLove()
         && this.getTarget() == null
         && !this.isPanicking()
         && !this.isMovingForAnimation();
   }

   private boolean isMovingForAnimation() {
      return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-4 || this.getNavigation().isInProgress();
   }

   private void startSleeping(int sleepTicks) {
      this.entityData.set(SLEEPING, true);
      this.sleepTicksRemaining = sleepTicks;
      this.idleAnimationCycleTicks = 0;
      this.setQuirkTicks(0);
      this.applySleepStillness();
      if (!this.level().isClientSide) {
         this.triggerAnim("sleep_transition_controller", "start_sleep");
      }
   }

   private void stopCowSleeping() {
      if (this.isSleeping()) {
         this.entityData.set(SLEEPING, false);
         this.sleepTicksRemaining = 0;
         this.napCooldownTicks = this.randomNapCooldown();
         if (!this.level().isClientSide) {
            this.triggerAnim("sleep_transition_controller", "wake_up");
         }
      }
   }

   private void applySleepStillness() {
      Vec3 motion = this.getDeltaMovement();
      this.getNavigation().stop();
      this.setJumping(false);
      this.setSprinting(false);
      this.zza = 0.0F;
      this.xxa = 0.0F;
      this.setDeltaMovement(0.0, motion.y < 0.0 ? motion.y : 0.0, 0.0);
   }

   private int randomNapCooldown() {
      return this.level().isNight() ? 9600 : 12000;
   }

   public boolean readyForShearing() {
      return !this.isBaby() && !this.isSleeping() && !this.isSheared();
   }

   public void shear(SoundSource source) {
      if (!this.level().isClientSide && this.readyForShearing()) {
         this.setSheared(true);
         this.grassEatTicks = 0;
         this.stopCowSleeping();
         this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
         this.spawnAtLocation(new ItemStack(this.shearDropItem(), 2), this.getBbHeight() * 0.66F);
      }
   }

   private Item shearDropItem() {
      if (this instanceof AppleCowEntityVariants.EnchantedGoldenAppleCow) {
         return Items.ENCHANTED_GOLDEN_APPLE;
      } else {
         return this instanceof AppleCowEntityVariants.GoldenAppleCow ? Items.GOLDEN_APPLE : Items.APPLE;
      }
   }

   private boolean isOnGrassBlock() {
      BlockPos below = this.blockPosition().below();
      return this.level().getBlockState(below).is(Blocks.GRASS_BLOCK);
   }

   private final class AppleCowGrazeGoal extends Goal {
      private AppleCowGrazeGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         return AppleCowEntity.this.isSheared()
            && AppleCowEntity.this.isOnGrassBlock()
            && AppleCowEntity.this.isEligibleForSleep()
            && AppleCowEntity.this.random.nextInt(20) == 0;
      }

      public boolean canContinueToUse() {
         return AppleCowEntity.this.isSheared() && AppleCowEntity.this.isOnGrassBlock() && AppleCowEntity.this.grassEatTicks < 3600;
      }

      public void start() {
         AppleCowEntity.this.getNavigation().stop();
      }

      public void tick() {
         AppleCowEntity.this.getNavigation().stop();
         if (!AppleCowEntity.this.level().isClientSide && AppleCowEntity.this.isSheared() && AppleCowEntity.this.isOnGrassBlock()) {
            AppleCowEntity.this.grassEatTicks++;
            if (AppleCowEntity.this.grassEatTicks >= 3600) {
               AppleCowEntity.this.setSheared(false);
               AppleCowEntity.this.grassEatTicks = 0;
               AppleCowEntity.this.playSound(SoundEvents.GENERIC_EAT, 0.7F, 1.0F + AppleCowEntity.this.random.nextFloat() * 0.2F);
            } else if (AppleCowEntity.this.grassEatTicks % 40 == 0) {
               AppleCowEntity.this.playSound(SoundEvents.GENERIC_EAT, 0.4F, 1.0F + AppleCowEntity.this.random.nextFloat() * 0.15F);
            }
         }
      }

      public void stop() {
         if (!AppleCowEntity.this.isSheared()) {
            AppleCowEntity.this.grassEatTicks = 0;
         }
      }
   }

   private final class AppleCowSleepGoal extends Goal {
      private AppleCowSleepGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public boolean canUse() {
         return AppleCowEntity.this.isSleeping();
      }

      public boolean canContinueToUse() {
         return AppleCowEntity.this.isSleeping();
      }

      public void start() {
         AppleCowEntity.this.applySleepStillness();
      }

      public void tick() {
         AppleCowEntity.this.applySleepStillness();
      }
   }
}
