package com.mongoose.royalvariations.common.entities.neutral;

import com.mongoose.royalvariations.client.particles.EndSplashParticleOption;
import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.common.entities.hostile.RoyalMonster;
import com.mongoose.royalvariations.common.entities.projectile.SpectralAnvil;
import com.mongoose.royalvariations.init.RVSounds;
import com.mongoose.royalvariations.utils.MathHelper;
import com.mongoose.royalvariations.utils.MobUtil;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent.EnderEntity;

public class RoyalEnderman extends RoyalMonster implements NeutralMob {
   private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(RoyalEnderman.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_CREEPY = SynchedEntityData.defineId(RoyalEnderman.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_STARED_AT = SynchedEntityData.defineId(RoyalEnderman.class, EntityDataSerializers.BOOLEAN);
   private static final ResourceLocation SPEED_MODIFIER_ATTACKING_ID = ResourceLocation.withDefaultNamespace("attacking");
   private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_ID, 0.15F, Operation.ADD_VALUE);
   private int lastStareSound = Integer.MIN_VALUE;
   private int targetChangeTime;
   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
   private int remainingPersistentAngerTime;
   @Nullable
   private UUID persistentAngerTarget;
   public static String IDLE = "idle";
   public static String ATTACK = "attack";
   public static String BLESS = "bless";
   private boolean isWalking;
   private int attackTick;
   private int toIdleTick;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState toIdleAnimationState = new AnimationState();
   public AnimationState tremorAnimationState = new AnimationState();
   public AnimationState crownAnimationState = new AnimationState();
   public AnimationState blinkAnimationState = new AnimationState();
   public AnimationState attackAnimationState = new AnimationState();
   public AnimationState blessAnimationState = new AnimationState();

   public RoyalEnderman(EntityType<? extends RoyalEnderman> p_32485_, Level p_32486_) {
      super(p_32485_, p_32486_);
      this.xpReward = 40;
      this.setPathfindingMalus(PathType.WATER, -1.0F);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new RoyalEnderman.AnvilGoal(this));
      this.goalSelector.addGoal(1, new RoyalEnderman.EndermanFreezeWhenLookedAt(this));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new RoyalEnderman.EndermanLookForPlayerGoal(this, this::isAngryAt));
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Endermite.class, true, false));
      this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal(this, false));
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 100.0)
         .add(Attributes.MOVEMENT_SPEED, 0.24F)
         .add(Attributes.ATTACK_DAMAGE, 14.0)
         .add(Attributes.FOLLOW_RANGE, 100.0)
         .add(Attributes.ARMOR, 17.0)
         .add(Attributes.ATTACK_KNOCKBACK, 0.7)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.7)
         .add(Attributes.STEP_HEIGHT, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ANIM_STATE, 0);
      builder.define(DATA_CREEPY, false);
      builder.define(DATA_STARED_AT, false);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag p_32520_) {
      super.addAdditionalSaveData(p_32520_);
      this.addPersistentAngerSaveData(p_32520_);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag p_32511_) {
      super.readAdditionalSaveData(p_32511_);
      this.readPersistentAngerSaveData(this.level(), p_32511_);
   }

   public void setAnimationState(String input) {
      this.setAnimationState(this.getAnimationState(input));
   }

   public void setAnimationState(int id) {
      this.entityData.set(ANIM_STATE, id);
   }

   public int getAnimationState(String animation) {
      if (Objects.equals(animation, "idle")) {
         return 0;
      } else if (Objects.equals(animation, "attack")) {
         return 1;
      } else {
         return Objects.equals(animation, "bless") ? 2 : 0;
      }
   }

   public void stopAllAnimation() {
      for (AnimationState state : this.getAnimations()) {
         state.stop();
      }
   }

   public int getCurrentAnimation() {
      return (Integer)this.entityData.get(ANIM_STATE);
   }

   public List<AnimationState> getAnimations() {
      List<AnimationState> animationStates = new ArrayList<>();
      animationStates.add(this.attackAnimationState);
      animationStates.add(this.blessAnimationState);
      return animationStates;
   }

   public boolean isCurrentAnimation(String string) {
      return this.getCurrentAnimation() == this.getAnimationState(string);
   }

   public void setTarget(@Nullable LivingEntity p_32537_) {
      AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
      if (p_32537_ == null) {
         this.targetChangeTime = 0;
         this.entityData.set(DATA_CREEPY, false);
         this.entityData.set(DATA_STARED_AT, false);
         if (attributeinstance != null) {
            attributeinstance.removeModifier(SPEED_MODIFIER_ATTACKING);
         }
      } else {
         this.targetChangeTime = this.tickCount;
         this.entityData.set(DATA_CREEPY, true);
         if (attributeinstance != null && !attributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING_ID)) {
            attributeinstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
         }
      }

      super.setTarget(p_32537_);
   }

   public void startPersistentAngerTimer() {
      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
   }

   public void setRemainingPersistentAngerTime(int p_32515_) {
      this.remainingPersistentAngerTime = p_32515_;
   }

   public int getRemainingPersistentAngerTime() {
      return this.remainingPersistentAngerTime;
   }

   public void setPersistentAngerTarget(@Nullable UUID p_32509_) {
      this.persistentAngerTarget = p_32509_;
   }

   @Nullable
   public UUID getPersistentAngerTarget() {
      return this.persistentAngerTarget;
   }

   public void playStareSound() {
      if (this.tickCount >= this.lastStareSound + 400) {
         this.lastStareSound = this.tickCount;
         if (!this.isSilent()) {
            this.level().playLocalSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENDERMAN_STARE, this.getSoundSource(), 2.5F, 1.0F, false);
         }
      }
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_32513_) {
      if (DATA_CREEPY.equals(p_32513_) && this.hasBeenStaredAt() && this.level().isClientSide) {
         this.playStareSound();
      }

      if (ANIM_STATE.equals(p_32513_) && this.level().isClientSide) {
         switch (this.entityData.get(ANIM_STATE)) {
            case 0:
               this.stopAllAnimation();
               break;
            case 1:
               this.stopAllAnimation();
               this.attackAnimationState.start(this.tickCount);
               break;
            case 2:
               this.stopAllAnimation();
               this.blessAnimationState.start(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_32513_);
   }

   boolean isLookingAtMe(Player player) {
      ItemStack itemstack = (ItemStack)player.getInventory().armor.get(3);
      if (CommonHooks.shouldSuppressEnderManAnger(new EnderMan(EntityType.ENDERMAN, this.level()), player, itemstack)) {
         return false;
      }

      Vec3 vec3 = player.getViewVector(1.0F).normalize();
      Vec3 vec31 = new Vec3(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
      double d0 = vec31.length();
      vec31 = vec31.normalize();
      double d1 = vec3.dot(vec31);
      return d1 > 1.0 - 0.025 / d0 ? player.hasLineOfSight(this) : false;
   }

   protected float getStandingEyeHeight(Pose p_32517_, EntityDimensions p_32518_) {
      return 2.55F;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         if (!this.walkAnimation.isMoving()) {
            if (this.isWalking) {
               this.isWalking = false;
               this.toIdleTick = MathHelper.secondsToTicks(0.5F);
            }
         } else {
            this.isWalking = true;
         }

         if (this.toIdleTick > 0) {
            this.toIdleTick--;
         }

         boolean flag = !this.walkAnimation.isMoving() && this.isCurrentAnimation(IDLE);
         this.toIdleAnimationState.animateWhen(this.toIdleTick > 0 && flag, this.tickCount);
         this.idleAnimationState.animateWhen(this.toIdleTick <= 0 && flag, this.tickCount);
         this.tremorAnimationState.animateWhen(this.isCreepy(), this.tickCount);
         this.crownAnimationState.startIfStopped(this.tickCount);
         this.blinkAnimationState.startIfStopped(this.tickCount);
      } else {
         if (this.attackTick > 0) {
            this.attackTick--;
         } else if (this.isCurrentAnimation(ATTACK)) {
            this.setAnimationState(IDLE);
         }

         if (this.getTarget() != null && this.getTarget().distanceTo(this) >= 100.0F) {
            this.setTarget(null);
         }
      }
   }

   @Override
   public void aiStep() {
      if (this.level().isClientSide) {
         for (int i = 0; i < 2; i++) {
            this.level()
               .addParticle(
                  ParticleTypes.PORTAL,
                  this.getRandomX(0.5),
                  this.getRandomY() - 0.25,
                  this.getRandomZ(0.5),
                  (this.random.nextDouble() - 0.5) * 2.0,
                  -this.random.nextDouble(),
                  (this.random.nextDouble() - 0.5) * 2.0
               );
         }
      }

      this.jumping = false;
      if (!this.level().isClientSide) {
         this.updatePersistentAnger((ServerLevel)this.level(), true);
      }

      super.aiStep();
   }

   public boolean isSensitiveToWater() {
      return true;
   }

   protected void customServerAiStep() {
      if (this.level().isDay() && this.tickCount >= this.targetChangeTime + 600) {
         float f = this.getLightLevelDependentMagicValue();
         if (f > 0.5F && this.level().canSeeSky(this.blockPosition()) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
            this.setTarget((LivingEntity)null);
            this.teleport();
         }
      }

      super.customServerAiStep();
   }

   protected boolean teleport() {
      if (!this.level().isClientSide() && this.isAlive()) {
         double d0 = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
         double d1 = this.getY() + (this.random.nextInt(64) - 32);
         double d2 = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
         return this.teleport(d0, d1, d2);
      } else {
         return false;
      }
   }

   boolean teleportTowards(Entity p_32501_) {
      Vec3 vec3 = new Vec3(this.getX() - p_32501_.getX(), this.getY(0.5) - p_32501_.getEyeY(), this.getZ() - p_32501_.getZ());
      vec3 = vec3.normalize();
      double d0 = 16.0;
      double d1 = this.getX() + (this.random.nextDouble() - 0.5) * 8.0 - vec3.x * 16.0;
      double d2 = this.getY() + (this.random.nextInt(16) - 8) - vec3.y * 16.0;
      double d3 = this.getZ() + (this.random.nextDouble() - 0.5) * 8.0 - vec3.z * 16.0;
      return this.teleport(d1, d2, d3);
   }

   private boolean teleport(double p_32544_, double p_32545_, double p_32546_) {
      MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos(p_32544_, p_32545_, p_32546_);

      while (blockpos$mutableblockpos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(blockpos$mutableblockpos).blocksMotion()) {
         blockpos$mutableblockpos.move(Direction.DOWN);
      }

      BlockState blockstate = this.level().getBlockState(blockpos$mutableblockpos);
      boolean flag = blockstate.blocksMotion();
      boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
      if (flag && !flag1) {
         EnderEntity event = EventHooks.onEnderTeleport(this, p_32544_, p_32545_, p_32546_);
         if (event.isCanceled()) {
            return false;
         }

         Vec3 vec3 = this.position();
         boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
         if (flag2) {
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

               serverLevel.sendParticles(new EndSplashParticleOption(4.0F, 2), this.getX(), this.getY() + 0.25, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
            }

            for (LivingEntity livingEntity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0))) {
               if (!MobUtil.areAllies(livingEntity, this)
                  && (livingEntity instanceof Player || livingEntity.hasEffect(RVEffects.PRESSING_GAZE))
                  && livingEntity.hurt(this.damageSources().indirectMagic(this, this), 4.0F)) {
                  livingEntity.addEffect(new MobEffectInstance(RVEffects.HEAVINESS_OF_THE_END, 80));
               }
            }

            this.level().gameEvent(GameEvent.TELEPORT, vec3, Context.of(this));
            if (!this.isSilent()) {
               this.level().playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
               this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
               this.playSound((SoundEvent)RVSounds.END_SPLASH.get(), 2.0F, 1.0F);
            }
         }

         return flag2;
      } else {
         return false;
      }
   }

   public boolean isAlliedTo(Entity p_32665_) {
      if (p_32665_ == null) {
         return false;
      } else if (p_32665_ == this) {
         return true;
      } else {
         return !(p_32665_ instanceof EnderMan) ? super.isAlliedTo(p_32665_) : this.getTeam() == null && p_32665_.getTeam() == null;
      }
   }

   protected SoundEvent getAmbientSound() {
      return this.isCreepy() ? SoundEvents.ENDERMAN_SCREAM : SoundEvents.ENDERMAN_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource p_32527_) {
      return SoundEvents.ENDERMAN_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENDERMAN_DEATH;
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      if (this.isInvulnerableTo(damageSource)) {
         return false;
      }

      boolean isWaterBottle = damageSource.getDirectEntity() instanceof ThrownPotion;
      if (!damageSource.is(DamageTypeTags.IS_PROJECTILE) && !isWaterBottle) {
         boolean flag2 = super.hurt(damageSource, amount);
         if (!this.level().isClientSide() && !(damageSource.getEntity() instanceof LivingEntity) && this.random.nextInt(10) != 0) {
            this.teleport();
         }

         if (damageSource.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(RVEffects.PRESSING_GAZE, 400));
         }

         return flag2;
      } else {
         boolean flag1 = isWaterBottle && this.hurtWithCleanWater(damageSource, (ThrownPotion)damageSource.getDirectEntity(), amount);

         for (int i = 0; i < 64; i++) {
            if (this.teleport()) {
               return true;
            }
         }

         return flag1;
      }
   }

   private boolean hurtWithCleanWater(DamageSource source, ThrownPotion potion, float amount) {
      ItemStack itemstack = potion.getItem();
      PotionContents potioncontents = (PotionContents)itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
      boolean flag = potioncontents.is(Potions.WATER);
      if (flag && potion.getOwner() instanceof LivingEntity livingEntity) {
         livingEntity.addEffect(new MobEffectInstance(RVEffects.PRESSING_GAZE, 400));
      }

      return flag && super.hurt(source, amount);
   }

   public boolean isCreepy() {
      return (Boolean)this.entityData.get(DATA_CREEPY);
   }

   public boolean hasBeenStaredAt() {
      return (Boolean)this.entityData.get(DATA_STARED_AT);
   }

   public void setBeingStaredAt() {
      this.entityData.set(DATA_STARED_AT, true);
   }

   public void swing(InteractionHand p_21007_) {
      super.swing(p_21007_);
      this.setAnimationState(ATTACK);
      this.attackTick = MathHelper.secondsToTicks(0.5F);
   }

   static class AnvilGoal extends Goal {
      public RoyalEnderman royal;
      public int blessTick;

      public AnvilGoal(RoyalEnderman enderman) {
         this.royal = enderman;
         this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
      }

      public boolean canUse() {
         return this.royal.getTarget() != null && this.royal.knightCool <= 0
            ? this.royal.getTarget().hasLineOfSight(this.royal) && this.royal.getHealth() <= this.royal.getMaxHealth() * 0.85F
            : false;
      }

      public boolean canContinueToUse() {
         return this.blessTick > 0;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void start() {
         super.start();
         this.royal.setAnimationState(RoyalEnderman.BLESS);
         this.royal.playSound((SoundEvent)RVSounds.ROYAL_ENDERMAN_CAST.get(), 1.0F, this.royal.getVoicePitch());
         this.royal.getNavigation().stop();
         this.royal.getMoveControl().strafe(0.0F, 0.0F);
         this.blessTick = 20;
         if (this.royal.getTarget() != null) {
            this.royal.getLookControl().setLookAt(this.royal.getTarget(), 100.0F, 100.0F);
            this.royal.lookAt(this.royal.getTarget(), 100.0F, 100.0F);
         }
      }

      public void tick() {
         super.tick();
         this.blessTick--;
         this.royal.getNavigation().stop();
         this.royal.getMoveControl().strafe(0.0F, 0.0F);
         if (this.royal.getTarget() != null) {
            this.royal.getLookControl().setLookAt(this.royal.getTarget(), 100.0F, 100.0F);
            this.royal.lookAt(this.royal.getTarget(), 100.0F, 100.0F);
         }

         if (this.royal.getTarget() != null && this.blessTick == 10) {
            SpectralAnvil anvil = new SpectralAnvil(this.royal.level(), this.royal);
            float height = Math.max(this.royal.getBbHeight(), this.royal.getTarget().getBbHeight());
            anvil.setPos(this.royal.getTarget().position().add(0.0, height + 1.0F, 0.0));
            this.royal.level().addFreshEntity(anvil);
         }
      }

      public void stop() {
         super.stop();
         this.blessTick = 0;
         this.royal.knightCool = 180;
         this.royal.setAnimationState(RoyalEnderman.IDLE);
      }
   }

   static class EndermanFreezeWhenLookedAt extends Goal {
      private final RoyalEnderman enderman;
      @Nullable
      private LivingEntity target;

      public EndermanFreezeWhenLookedAt(RoyalEnderman p_32550_) {
         this.enderman = p_32550_;
         this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
      }

      public boolean canUse() {
         this.target = this.enderman.getTarget();
         if (!(this.target instanceof Player)) {
            return false;
         }

         double d0 = this.target.distanceToSqr(this.enderman);
         return !(d0 > 256.0) && this.enderman.isLookingAtMe((Player)this.target);
      }

      public void start() {
         this.enderman.getNavigation().stop();
      }

      public void tick() {
         if (this.target != null) {
            this.enderman.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
         }
      }
   }

   static class EndermanLookForPlayerGoal extends NearestAttackableTargetGoal<Player> {
      private final RoyalEnderman enderman;
      @Nullable
      private Player pendingTarget;
      private int aggroTime;
      private int teleportTime;
      private final TargetingConditions startAggroTargetConditions;
      private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();
      private final Predicate<LivingEntity> isAngerInducing;

      public EndermanLookForPlayerGoal(RoyalEnderman p_32573_, @Nullable Predicate<LivingEntity> p_32574_) {
         super(p_32573_, Player.class, 10, false, false, p_32574_);
         this.enderman = p_32573_;
         this.isAngerInducing = p_269940_ -> (p_32573_.isLookingAtMe((Player)p_269940_) || p_32573_.isAngryAt(p_269940_))
            && !p_32573_.hasIndirectPassenger(p_269940_);
         this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(this.isAngerInducing);
      }

      public boolean canUse() {
         this.pendingTarget = this.enderman.level().getNearestPlayer(this.startAggroTargetConditions, this.enderman);
         return this.pendingTarget != null;
      }

      public void start() {
         this.aggroTime = this.adjustedTickDelay(5);
         this.teleportTime = 0;
         this.enderman.setBeingStaredAt();
      }

      public void stop() {
         this.pendingTarget = null;
         super.stop();
      }

      public boolean canContinueToUse() {
         if (this.pendingTarget != null) {
            if (!this.isAngerInducing.test(this.pendingTarget)) {
               return false;
            }

            this.enderman.lookAt(this.pendingTarget, 10.0F, 10.0F);
            return true;
         } else {
            if (this.target != null) {
               if (this.enderman.hasIndirectPassenger(this.target)) {
                  return false;
               }

               if (this.continueAggroTargetConditions.test(this.enderman, this.target)) {
                  return true;
               }
            }

            return super.canContinueToUse();
         }
      }

      public void tick() {
         if (this.enderman.getTarget() == null) {
            super.setTarget(null);
         }

         if (this.pendingTarget != null) {
            if (--this.aggroTime <= 0) {
               this.target = this.pendingTarget;
               this.pendingTarget = null;
               super.start();
            }
         } else {
            if (this.target != null && !this.enderman.isPassenger()) {
               if (this.enderman.isLookingAtMe((Player)this.target)) {
                  if (this.target.distanceToSqr(this.enderman) < 16.0) {
                     this.enderman.teleport();
                  }

                  this.target.addEffect(new MobEffectInstance(RVEffects.PRESSING_GAZE, 400, 0));
                  this.teleportTime = 0;
               } else if ((this.target.distanceTo(this.enderman) >= 8.0 || !this.enderman.hasLineOfSight(this.target))
                  && this.teleportTime++ >= this.adjustedTickDelay(30)
                  && this.enderman.teleportTowards(this.target)) {
                  this.teleportTime = 0;
               }
            }

            super.tick();
         }
      }
   }
}
