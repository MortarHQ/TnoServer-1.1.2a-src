package com.mongoose.royalvariations.common.entities.hostile;

import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.attachments.RVCapHelper;
import com.mongoose.royalvariations.common.entities.projectile.NetEntity;
import com.mongoose.royalvariations.common.entities.projectile.RoyalArrow;
import com.mongoose.royalvariations.utils.MathHelper;
import com.mongoose.royalvariations.utils.MobUtil;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags.Items;

public class RoyalSkeleton extends RoyalMonster implements RangedAttackMob {
   private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(RoyalSkeleton.class, EntityDataSerializers.INT);
   public static String IDLE = "idle";
   public static String PREPARE = "prepare";
   public static String AGGRESSION = "aggression";
   public static String STAND_DOWN = "stand_down";
   private boolean isWalking;
   private int toIdleTick;
   public int toAggroTick;
   public int standDownTick;
   private int netCool;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState toIdleAnimationState = new AnimationState();
   public AnimationState aggressionAnimationState = new AnimationState();
   public AnimationState prepareAnimationState = new AnimationState();
   public AnimationState standDownAnimationState = new AnimationState();
   public AnimationState crownAnimationState = new AnimationState();

   public RoyalSkeleton(EntityType<? extends Monster> p_33002_, Level p_33003_) {
      super(p_33002_, p_33003_);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new RoyalSkeleton.KnightingGoal(this));
      this.goalSelector.addGoal(2, new RestrictSunGoal(this));
      this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
      this.goalSelector.addGoal(3, new AvoidEntityGoal(this, Wolf.class, 6.0F, 1.0, 1.2));
      this.goalSelector.addGoal(4, new RoyalSkeleton.RoyalBowAttackGoal<>(this, 1.25, 50, 20.0F));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 45.0)
         .add(Attributes.FOLLOW_RANGE, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.23)
         .add(Attributes.ATTACK_DAMAGE, 6.0)
         .add(Attributes.ARMOR, 12.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ANIM_STATE, 0);
   }

   public void setAnimationState(String input) {
      this.setAnimationState(this.getAnimationState(input));
   }

   public void setAnimationState(int id) {
      this.entityData.set(ANIM_STATE, id);
   }

   public int getAnimationState(String animation) {
      if (Objects.equals(animation, IDLE)) {
         return 0;
      } else if (Objects.equals(animation, PREPARE)) {
         return 1;
      } else if (Objects.equals(animation, AGGRESSION)) {
         return 2;
      } else {
         return Objects.equals(animation, STAND_DOWN) ? 3 : 0;
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

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) {
      if (ANIM_STATE.equals(p_219422_) && this.level().isClientSide) {
         switch (this.entityData.get(ANIM_STATE)) {
            case 0:
               this.stopAllAnimation();
               break;
            case 1:
               this.stopAllAnimation();
               this.prepareAnimationState.startIfStopped(this.tickCount);
               break;
            case 2:
               this.stopAllAnimation();
               this.aggressionAnimationState.startIfStopped(this.tickCount);
               break;
            case 3:
               this.stopAllAnimation();
               this.standDownAnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_219422_);
   }

   public List<AnimationState> getAnimations() {
      List<AnimationState> animationStates = new ArrayList<>();
      animationStates.add(this.prepareAnimationState);
      animationStates.add(this.aggressionAnimationState);
      animationStates.add(this.standDownAnimationState);
      return animationStates;
   }

   public boolean isCurrentAnimation(String string) {
      return this.getCurrentAnimation() == this.getAnimationState(string);
   }

   public boolean isAlliedTo(Entity p_32665_) {
      if (p_32665_ == null) {
         return false;
      } else if (p_32665_ == this) {
         return true;
      } else {
         return !(p_32665_ instanceof AbstractSkeleton) ? super.isAlliedTo(p_32665_) : this.getTeam() == null && p_32665_.getTeam() == null;
      }
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.SKELETON_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource p_34327_) {
      return SoundEvents.SKELETON_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.SKELETON_DEATH;
   }

   protected SoundEvent getStepSound() {
      return SoundEvents.SKELETON_STEP;
   }

   protected void playStepSound(BlockPos p_34316_, BlockState p_34317_) {
      this.playSound(this.getStepSound(), 0.15F, 1.0F);
   }

   public boolean hurt(DamageSource damageSource, float damage) {
      if (this.netCool <= 0
         && damageSource.getEntity() instanceof LivingEntity attacker
         && attacker.distanceTo(this) <= 6.0F
         && !MobUtil.areAllies(this, attacker)
         && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(attacker)) {
         MobUtil.instaLook(this, attacker);
         double power = 10.0;
         double deltaX = this.getX() - attacker.getX();
         double deltaY = this.getY() - (attacker.getY() + 1.5);
         double deltaZ = this.getZ() - attacker.getZ();
         double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
         double x = deltaX / distance * power * 0.2;
         double y = -(deltaY / distance * power * 0.06);
         double z = deltaZ / distance * power * 0.2;
         this.playSound(SoundEvents.PLAYER_ATTACK_STRONG, 2.0F, 0.75F);
         this.setDeltaMovement(x, y > 0.0 ? y + 0.2 : 0.2, z);
         this.poof(this);
         NetEntity net = new NetEntity(this.level(), this, attacker);
         net.setLifeSpan(50);
         net.playSound(SoundEvents.LEASH_KNOT_PLACE);
         this.poof(this.getTarget());
         this.level().addFreshEntity(net);
         this.netCool = 220;
      }

      return super.hurt(damageSource, damage);
   }

   public void poof(Entity entity) {
      if (entity != null && this.level() instanceof ServerLevel serverLevel) {
         for (int i = 0; i < 20; i++) {
            double d0 = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            double d3 = 10.0;
            serverLevel.sendParticles(ParticleTypes.POOF, entity.getX() - d0 * d3, entity.getRandomY() - d1 * d3, entity.getZ() - d2 * d3, 0, d0, d1, d2, 1.0);
         }
      }
   }

   @Override
   protected boolean isSunSensitive() {
      return true;
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
         this.crownAnimationState.startIfStopped(this.tickCount);
      } else {
         if (this.toAggroTick > 0) {
            this.toAggroTick--;
         } else if (this.isAggressive()) {
            this.setAnimationState(AGGRESSION);
         }

         if (this.standDownTick > 0) {
            this.standDownTick--;
         } else if (this.isCurrentAnimation(STAND_DOWN)) {
            this.setAnimationState(IDLE);
         }

         if (this.netCool > 0) {
            this.netCool--;
         }
      }
   }

   public void performRangedAttack(LivingEntity p_32141_, float p_32142_) {
      RoyalArrow royalArrow = new RoyalArrow(this.level(), this);
      royalArrow.setMarking(true);
      double d0 = p_32141_.getX() - this.getX();
      double d1 = p_32141_.getY(0.3333333333333333) - royalArrow.getY();
      double d2 = p_32141_.getZ() - this.getZ();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      royalArrow.shoot(d0, d1 + d3 * 0.2F, d2, 1.6F, 14 - this.level().getDifficulty().getId() * 4);
      this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
      this.level().addFreshEntity(royalArrow);
      this.toAggroTick = MathHelper.secondsToTicks(2.5F);
      this.setAnimationState(PREPARE);
   }

   public boolean canFireProjectileWeapon(ProjectileWeaponItem p_32144_) {
      return true;
   }

   protected float getStandingEyeHeight(Pose p_32154_, EntityDimensions p_32155_) {
      return 1.74F;
   }

   public double getMyRidingOffset() {
      return -0.6;
   }

   static class KnightingGoal extends Goal {
      public static int FLASH_TIME = 5;
      public RoyalSkeleton royal;
      public List<AbstractSkeleton> list = new ArrayList<>();
      public int blessTick;

      public KnightingGoal(RoyalSkeleton skeleton) {
         this.royal = skeleton;
         this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
      }

      public boolean canUse() {
         if (this.royal.getTarget() != null && this.royal.knightCool <= 0) {
            List<AbstractSkeleton> list = this.royal.level().getEntitiesOfClass(AbstractSkeleton.class, this.royal.getBoundingBox().inflate(14.0));
            list.removeIf(
               skeleton -> !skeleton.hasLineOfSight(this.royal)
                  && skeleton.getMainHandItem().isEnchanted()
                  && !skeleton.getMainHandItem().is(Items.TOOLS_BOW)
                  && MobUtil.areAllies(skeleton, this.royal.getTarget())
            );
            this.list = list;
            return !this.list.isEmpty();
         } else {
            return false;
         }
      }

      public boolean canContinueToUse() {
         return this.blessTick > 0;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void start() {
         super.start();
         this.royal.getNavigation().stop();
         this.royal.getMoveControl().strafe(0.0F, 0.0F);
         this.blessTick = FLASH_TIME + 1;
         if (this.royal.getTarget() != null) {
            this.royal.getLookControl().setLookAt(this.royal.getTarget(), 100.0F, 100.0F);
            this.royal.lookAt(this.royal.getTarget(), 100.0F, 100.0F);
         }

         if (!this.list.isEmpty()) {
            for (AbstractSkeleton skeleton : this.list) {
               if (skeleton.getMainHandItem().is(Items.TOOLS_BOW) && !skeleton.getMainHandItem().isEnchanted()) {
                  RVCapHelper.setFlashTick(skeleton, FLASH_TIME);
               }
            }
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

         if (this.blessTick == 1 && !this.list.isEmpty()) {
            for (AbstractSkeleton skeleton : this.list) {
               if (this.royal.level() instanceof ServerLevel serverLevel) {
                  if (skeleton.getMainHandItem().is(Items.TOOLS_BOW) && !skeleton.getMainHandItem().isEnchanted()) {
                     Holder<Enchantment> power = skeleton.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.POWER);
                     skeleton.getMainHandItem().enchant(power, 2);
                     serverLevel.sendParticles(
                        (SimpleParticleType)RVParticleTypes.SPARKLE.get(),
                        skeleton.getX(),
                        skeleton.getY() + skeleton.getBbHeight(),
                        skeleton.getZ(),
                        serverLevel.getRandom().nextIntBetweenInclusive(5, 6),
                        0.2,
                        0.3,
                        0.2,
                        0.1
                     );
                     skeleton.playSound(SoundEvents.AMETHYST_CLUSTER_FALL, 0.9F, 1.0F);
                     serverLevel.playSound(
                        null, skeleton.getX(), skeleton.getY(), skeleton.getZ(), SoundEvents.AMETHYST_CLUSTER_FALL, SoundSource.HOSTILE, 0.9F, 1.0F
                     );

                     for (int i = 0; i <= serverLevel.getRandom().nextInt(10) + 10; i++) {
                        serverLevel.sendParticles(
                           (SimpleParticleType)RVParticleTypes.HOLY.get(),
                           skeleton.getRandomX(1.5),
                           skeleton.getRandomY(),
                           skeleton.getRandomZ(1.5),
                           0,
                           0.0,
                           1.0,
                           0.0,
                           1.0
                        );
                     }
                  }

                  skeleton.setTarget(this.royal.getTarget());
               }
            }
         }
      }

      public void stop() {
         super.stop();
         this.blessTick = 0;
         this.royal.knightCool = 300;
         this.list.clear();
      }
   }

   public static class RoyalBowAttackGoal<T extends RoyalSkeleton> extends Goal {
      private final T mob;
      private final double speedModifier;
      private int attackIntervalMin;
      private final float attackRadiusSqr;
      private int attackTime = -1;
      private int seeTime;
      private boolean strafingClockwise;
      private boolean strafingBackwards;
      private int strafingTime = -1;

      public RoyalBowAttackGoal(T p_25792_, double p_25793_, int p_25794_, float p_25795_) {
         this.mob = p_25792_;
         this.speedModifier = p_25793_;
         this.attackIntervalMin = p_25794_;
         this.attackRadiusSqr = p_25795_ * p_25795_;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         return this.mob.getTarget() != null && this.mob.getTarget().isAlive();
      }

      public boolean canContinueToUse() {
         return this.canUse() || !this.mob.getNavigation().isDone();
      }

      public void start() {
         super.start();
         this.mob.setAggressive(true);
         this.mob.toAggroTick = MathHelper.secondsToTicks(2.5F);
         this.mob.setAnimationState(RoyalSkeleton.PREPARE);
      }

      public void stop() {
         super.stop();
         this.mob.setAggressive(false);
         this.seeTime = 0;
         this.attackTime = 0;
         this.mob.standDownTick = MathHelper.secondsToTicks(2);
         this.mob.setAnimationState(RoyalSkeleton.STAND_DOWN);
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         LivingEntity livingentity = this.mob.getTarget();
         if (livingentity != null && livingentity.isAlive()) {
            double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
            boolean flag = this.mob.getSensing().hasLineOfSight(livingentity);
            boolean flag1 = this.seeTime > 0;
            if (flag != flag1) {
               this.seeTime = 0;
            }

            if (flag) {
               this.seeTime++;
            } else {
               this.seeTime--;
            }

            if (!(d0 > this.attackRadiusSqr) && this.seeTime >= 20) {
               this.mob.getNavigation().stop();
               this.strafingTime++;
            } else {
               this.mob.getNavigation().moveTo(livingentity, this.speedModifier);
               this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
               if (this.mob.getRandom().nextFloat() < 0.3) {
                  this.strafingClockwise = !this.strafingClockwise;
               }

               if (this.mob.getRandom().nextFloat() < 0.3) {
                  this.strafingBackwards = !this.strafingBackwards;
               }

               this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
               if (d0 > this.attackRadiusSqr * 0.75F) {
                  this.strafingBackwards = false;
               } else if (d0 < this.attackRadiusSqr * 0.25F) {
                  this.strafingBackwards = true;
               }

               this.mob.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
               if (this.mob.getControlledVehicle() instanceof Mob mob1) {
                  mob1.lookAt(livingentity, 30.0F, 30.0F);
               }

               this.mob.lookAt(livingentity, 30.0F, 30.0F);
            } else {
               this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            }

            if (flag) {
               this.attackTime++;
               if (this.attackTime >= this.attackIntervalMin && !this.mob.isCurrentAnimation(RoyalSkeleton.PREPARE)) {
                  this.mob.performRangedAttack(livingentity, 1.0F);
                  this.attackTime = 0;
               }
            }
         }
      }
   }
}
