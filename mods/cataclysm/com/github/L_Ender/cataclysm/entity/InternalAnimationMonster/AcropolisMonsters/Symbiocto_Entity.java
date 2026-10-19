package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AcropolisMonsters;

import com.github.L_Ender.cataclysm.entity.etc.path.SemiAquaticPathNavigator;
import com.github.L_Ender.cataclysm.entity.projectile.Octo_Ink_Entity;
import com.github.L_Ender.cataclysm.init.ModTag;
import java.util.EnumSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class Symbiocto_Entity extends Monster implements RangedAttackMob {
   boolean searchingForLand;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState spitAnimationState = new AnimationState();
   public AnimationState attackAnimationState = new AnimationState();
   public static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Symbiocto_Entity.class, EntityDataSerializers.INT);
   public int attackTicks;
   protected final SemiAquaticPathNavigator waterNavigation;
   protected final GroundPathNavigation groundNavigation;

   public Symbiocto_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 5;
      this.setPathfindingMalus(PathType.WATER, 0.0F);
      this.waterNavigation = new SemiAquaticPathNavigator(this, world);
      this.groundNavigation = new GroundPathNavigation(this, world);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(2, new Symbiocto_Entity.CrossBowShootGoal(this, 0, 1, 0, 34, 19, 16.0F));
      this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, true));
      this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 80));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder octo() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 16.0)
         .add(Attributes.MOVEMENT_SPEED, 0.3F)
         .add(Attributes.ATTACK_DAMAGE, 3.0)
         .add(Attributes.MAX_HEALTH, 16.0);
   }

   boolean wantsToSwim() {
      if (this.searchingForLand) {
         return true;
      }

      LivingEntity livingentity = this.getTarget();
      return livingentity != null && livingentity.isInWater();
   }

   public void travel(Vec3 travelVector) {
      if (this.isControlledByLocalInstance() && this.isInWater() && this.wantsToSwim()) {
         this.moveRelative(0.01F, travelVector);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
      } else {
         super.travel(travelVector);
      }
   }

   public void updateSwimming() {
      if (!this.level().isClientSide) {
         boolean inWaterAI = this.isEffectiveAi() && this.isInWater() && this.wantsToSwim();
         if (inWaterAI && !(this.moveControl instanceof Symbiocto_Entity.SymbioctoSwimControl)) {
            this.navigation = this.waterNavigation;
            this.moveControl = new Symbiocto_Entity.SymbioctoSwimControl(this, 4.0F);
            this.setSwimming(true);
         } else if (!inWaterAI && this.moveControl instanceof Symbiocto_Entity.SymbioctoSwimControl) {
            this.navigation = this.groundNavigation;
            this.moveControl = new MoveControl(this);
            this.setSwimming(false);
         }
      }
   }

   public AnimationState getAnimationState(String input) {
      if (input == "spit") {
         return this.spitAnimationState;
      } else if (input == "idle") {
         return this.idleAnimationState;
      } else {
         return input == "attack" ? this.attackAnimationState : new AnimationState();
      }
   }

   public boolean isCloseEye() {
      return this.getVehicle() instanceof Drowned_Host_Entity;
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(ATTACK_STATE, 0);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_)) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.spitAnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_21104_);
   }

   public int getAttackState() {
      return (Integer)this.entityData.get(ATTACK_STATE);
   }

   public void setAttackState(int input) {
      this.attackTicks = 0;
      this.entityData.set(ATTACK_STATE, input);
      this.level().broadcastEntityEvent(this, (byte)(-input));
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return source.is(DamageTypes.IN_WALL) || super.isInvulnerableTo(source);
   }

   public void stopAllAnimationStates() {
      this.spitAnimationState.stop();
   }

   public boolean doHurtTarget(Entity p_219472_) {
      this.level().broadcastEntityEvent(this, (byte)4);
      return super.doHurtTarget(p_219472_);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
   }

   public void handleEntityEvent(byte id) {
      if (id <= 0) {
         this.attackTicks = 0;
      } else if (id == 4) {
         this.attackAnimationState.start(this.tickCount);
      } else {
         super.handleEntityEvent(id);
      }
   }

   public void tick() {
      super.tick();
      if (this.getAttackState() > 0) {
         this.attackTicks++;
      }

      if (this.level().isClientSide()) {
         this.idleAnimationState.animateWhen(true, this.tickCount);
      }
   }

   public void aiStep() {
      super.aiStep();
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.isAlliedTo(entityIn)) {
         return true;
      } else {
         return !entityIn.getType().is(ModTag.TEAM_SCYLLA) ? false : this.getTeam() == null && entityIn.getTeam() == null;
      }
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.SQUID_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.SQUID_DEATH;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.SQUID_AMBIENT;
   }

   private void spit(LivingEntity target) {
      Octo_Ink_Entity llamaspit = new Octo_Ink_Entity(this.level(), this);
      double d0 = target.getX() - this.getX();
      double d1 = target.getY(0.3333333333333333) - llamaspit.getY();
      double d2 = target.getZ() - this.getZ();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2) * 0.2F;
      llamaspit.shoot(d0, d1 + d3, d2, 1.5F, 10.0F);
      if (!this.isSilent()) {
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.SQUID_SQUIRT, this.getSoundSource(), 0.5F, 1.0F);
      }

      this.level().addFreshEntity(llamaspit);
   }

   public void performRangedAttack(LivingEntity target, float velocity) {
      this.spit(target);
   }

   static class CrossBowShootGoal extends Goal {
      protected final Symbiocto_Entity entity;
      private final int getattackstate;
      private final int attackstate;
      private final int attackendstate;
      private final int attackMaxtick;
      private final int attackseetick;
      private final float attackrange;

      public CrossBowShootGoal(
         Symbiocto_Entity entity, int getattackstate, int attackstate, int attackendstate, int attackMaxtick, int attackseetick, float attackrange
      ) {
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.getattackstate = getattackstate;
         this.attackstate = attackstate;
         this.attackendstate = attackendstate;
         this.attackMaxtick = attackMaxtick;
         this.attackseetick = attackseetick;
         this.attackrange = attackrange;
      }

      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return target != null
            && target.isAlive()
            && this.entity.distanceTo(target) < this.attackrange
            && !this.entity.isWithinMeleeAttackRange(target)
            && this.entity.getAttackState() == this.getattackstate;
      }

      public void start() {
         this.entity.setAttackState(this.attackstate);
      }

      public void stop() {
         this.entity.setAttackState(this.attackendstate);
         LivingEntity target = this.entity.getTarget();
         if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.entity.setTarget((LivingEntity)null);
         }
      }

      public boolean canContinueToUse() {
         return this.entity.getAttackState() == this.attackstate && this.entity.attackTicks <= this.attackMaxtick;
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.attackTicks < this.attackseetick && target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            this.entity.lookAt(target, 30.0F, 30.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (target != null && this.entity.attackTicks == 19) {
            this.entity.performRangedAttack(target, 1.0F);
         }
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class SymbioctoSwimControl extends MoveControl {
      private final Symbiocto_Entity drowned;
      private final float speedMulti;

      public SymbioctoSwimControl(Symbiocto_Entity p_32433_, float speedMulti) {
         super(p_32433_);
         this.drowned = p_32433_;
         this.speedMulti = speedMulti;
      }

      public void tick() {
         LivingEntity livingentity = this.drowned.getTarget();
         if (this.drowned.wantsToSwim() && this.drowned.isInWater()) {
            if (livingentity != null && livingentity.getY() > this.drowned.getY() || this.drowned.searchingForLand) {
               this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(0.0, 0.002, 0.0));
            }

            if (this.operation != Operation.MOVE_TO || this.drowned.getNavigation().isDone()) {
               this.drowned.setSpeed(0.0F);
               return;
            }

            double d0 = this.wantedX - this.drowned.getX();
            double d1 = this.wantedY - this.drowned.getY();
            double d2 = this.wantedZ - this.drowned.getZ();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d1 /= d3;
            float f = (float)(Mth.atan2(d2, d0) * 180.0F / (float)Math.PI) - 90.0F;
            this.drowned.setYRot(this.rotlerp(this.drowned.getYRot(), f, 90.0F));
            this.drowned.yBodyRot = this.drowned.getYRot();
            float f1 = (float)(this.speedModifier * this.speedMulti * this.drowned.getAttributeValue(Attributes.MOVEMENT_SPEED));
            float f2 = Mth.lerp(0.125F, this.drowned.getSpeed(), f1);
            this.drowned.setSpeed(f2);
            this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(f2 * d0 * 0.005, f2 * d1 * 0.1, f2 * d2 * 0.005));
         } else {
            if (!this.drowned.onGround()) {
               this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(0.0, -0.008, 0.0));
            }

            super.tick();
         }
      }
   }
}
