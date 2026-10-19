package com.github.L_Ender.cataclysm.entity.AnimationMonster;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.projectile.Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class The_Watcher_Entity extends LLibrary_Monster {
   public static final Animation WATCHER_BITE = Animation.create(22);
   public static final Animation WATCHER_SHOT = Animation.create(55);
   public static final Animation WATCHER_EXTRA_SHOT = Animation.create(17);

   public The_Watcher_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 8;
      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, WATCHER_BITE, WATCHER_EXTRA_SHOT, WATCHER_SHOT};
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(2, new The_Watcher_Entity.WatcherMoveGoal(this, false, 1.0));
      this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 80));
      this.goalSelector.addGoal(0, new The_Watcher_Entity.ShotPrepare(this, WATCHER_SHOT));
      this.goalSelector.addGoal(0, new The_Watcher_Entity.Shot(this, WATCHER_EXTRA_SHOT));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder the_watcher() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 20.0)
         .add(Attributes.MOVEMENT_SPEED, 0.28F)
         .add(Attributes.ATTACK_DAMAGE, 5.0)
         .add(Attributes.MAX_HEALTH, 25.0)
         .add(Attributes.ARMOR, 5.0)
         .add(Attributes.STEP_HEIGHT, 1.25)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
   }

   public boolean hurt(DamageSource source, float damage) {
      if (source.is(CMDamageTypes.EMP)) {
         super.hurt(source, 1000.0F);
         return true;
      } else {
         return super.hurt(source, damage);
      }
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
   }

   @Override
   public void tick() {
      super.tick();
      this.setYRot(this.yBodyRot);
      LivingEntity target = this.getTarget();
      if (this.getAnimation() == WATCHER_BITE
         && this.getAnimationTick() == 13
         && target != null
         && this.distanceTo(target) < 3.0F
         && this.hasLineOfSight(target)) {
         float damage = (int)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
         target.hurt(this.damageSources().mobAttack(this), damage);
      }

      if (this.getAnimation() == WATCHER_EXTRA_SHOT && this.getAnimationTick() == 9) {
         if (!this.isSilent()) {
            this.playSound((SoundEvent)ModSounds.HARBINGER_LASER.get(), 1.0F, 1.0F);
         }

         if (target != null && target.isAlive()) {
            double d0 = this.getX();
            double d1 = this.getY() + this.getBbHeight() * 1.0F / 2.0F;
            double d2 = this.getZ();
            double d3 = target.getX() - d0;
            double d4 = target.getY() + target.getBbHeight() * 1.0F / 2.0F - d1;
            double d5 = target.getZ() - d2;
            Vec3 vec3 = new Vec3(d3, d4, d5);
            Laser_Beam_Entity laserBeam = new Laser_Beam_Entity(this, vec3.normalize(), this.level(), 7.0F);
            float yRot = (float)(Mth.atan2(vec3.z, vec3.x) * (180.0 / Math.PI)) + 90.0F;
            float xRot = (float)(-(Mth.atan2(vec3.y, Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)) * (180.0 / Math.PI)));
            laserBeam.setYRot(yRot);
            laserBeam.setXRot(xRot);
            laserBeam.setPosRaw(d0, d1, d2);
            this.level().addFreshEntity(laserBeam);
         }
      }
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.isAlliedTo(entityIn)) {
         return true;
      } else {
         return !entityIn.getType().is(ModTag.TEAM_THE_HARBINGER) ? false : this.getTeam() == null && entityIn.getTeam() == null;
      }
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.WATCHER_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.WATCHER_DEATH.get();
   }

   static class Shot extends SimpleAnimationGoal<The_Watcher_Entity> {
      public Shot(The_Watcher_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void start() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.start();
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.getAnimationTick() < 7 && target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         } else {
            this.entity.setYRot(this.entity.yRotO);
         }

         if (target != null && this.entity.getAnimationTick() == 11 && this.entity.getRandom().nextFloat() * 100.0F < 60.0F) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, The_Watcher_Entity.WATCHER_EXTRA_SHOT);
         }
      }
   }

   static class ShotPrepare extends SimpleAnimationGoal<The_Watcher_Entity> {
      public ShotPrepare(The_Watcher_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void start() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }

         super.start();
      }

      public void stop() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }

         super.stop();
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (this.entity.getAnimationTick() == 45) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, The_Watcher_Entity.WATCHER_EXTRA_SHOT);
            }
         }
      }
   }

   static class WatcherMoveGoal extends Goal {
      private final The_Watcher_Entity watcher;
      private final boolean followingTargetEvenIfNotSeen;
      private Path path;
      private int delayCounter;
      protected final double moveSpeed;

      public WatcherMoveGoal(The_Watcher_Entity boss, boolean followingTargetEvenIfNotSeen, double moveSpeed) {
         this.watcher = boss;
         this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
         this.moveSpeed = moveSpeed;
         this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
      }

      public boolean canUse() {
         LivingEntity target = this.watcher.getTarget();
         return target != null && target.isAlive();
      }

      public void stop() {
         this.watcher.getNavigation().stop();
         LivingEntity livingentity = this.watcher.getTarget();
         if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.watcher.setTarget((LivingEntity)null);
         }

         this.watcher.setAggressive(false);
         this.watcher.getNavigation().stop();
      }

      public boolean canContinueToUse() {
         LivingEntity target = this.watcher.getTarget();
         if (target == null) {
            return false;
         } else if (!target.isAlive()) {
            return false;
         } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.watcher.getNavigation().isDone();
         } else {
            return !this.watcher.isWithinRestriction(target.blockPosition())
               ? false
               : !(target instanceof Player) || !target.isSpectator() && !((Player)target).isCreative();
         }
      }

      public void start() {
         this.watcher.getNavigation().moveTo(this.path, this.moveSpeed);
         this.watcher.setAggressive(true);
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         LivingEntity target = this.watcher.getTarget();
         if (target != null) {
            this.watcher.getLookControl().setLookAt(target, 30.0F, 30.0F);
            double distSq = this.watcher.distanceToSqr(target.getX(), target.getBoundingBox().minY, target.getZ());
            if (--this.delayCounter <= 0) {
               this.delayCounter = 4 + this.watcher.getRandom().nextInt(7);
               if (distSq > Math.pow(this.watcher.getAttribute(Attributes.FOLLOW_RANGE).getValue(), 2.0)) {
                  if (!this.watcher.isPathFinding() && !this.watcher.getNavigation().moveTo(target, 1.0)) {
                     this.delayCounter += 5;
                  }
               } else {
                  this.watcher.getNavigation().moveTo(target, this.moveSpeed);
               }
            }

            if (target.isAlive() && this.watcher.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
               if (this.watcher.distanceTo(target) < 1.5F) {
                  this.watcher.setAnimation(The_Watcher_Entity.WATCHER_BITE);
               } else if (this.watcher.getRandom().nextFloat() * 100.0F < 24.0F && this.watcher.distanceTo(target) >= 6.0) {
                  this.watcher.setAnimation(The_Watcher_Entity.WATCHER_SHOT);
               }
            }
         }
      }
   }
}
