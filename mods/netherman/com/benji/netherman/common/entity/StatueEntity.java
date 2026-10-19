package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class StatueEntity extends Monster implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public boolean isFrozen = false;
   public boolean hasAttacked = false;

   public StatueEntity(EntityType<? extends Monster> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 50.0)
         .add(Attributes.MOVEMENT_SPEED, 0.4)
         .add(Attributes.ATTACK_DAMAGE, 10.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new StatueEntity.StatueAttackGoal(this, 1.0, false));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         boolean isLookedAtNow = this.isBeingLookedAt();
         if (isLookedAtNow) {
            if (!this.isFrozen) {
               this.isFrozen = true;
               this.getNavigation().stop();
               this.hasAttacked = false;
               int rand = this.random.nextInt(3);
               SoundEvent springSound = rand == 0
                  ? (SoundEvent)ModSounds.SPRING_1.get()
                  : (rand == 1 ? (SoundEvent)ModSounds.SPRING_2.get() : (SoundEvent)ModSounds.SPRING_3.get());
               this.playSound(springSound, 1.0F, 1.0F);
               this.triggerAnim("action_controller", "approach");
            }

            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
         } else {
            this.isFrozen = false;
         }
      }
   }

   private boolean isBeingLookedAt() {
      for (Player player : this.level().players()) {
         if (!player.isSpectator() && player.isAlive()) {
            Vec3 viewVector = player.getViewVector(1.0F).normalize();
            Vec3 toStatue = new Vec3(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
            double dist = toStatue.length();
            if (dist < 64.0) {
               toStatue = toStatue.normalize();
               double dotProduct = viewVector.dot(toStatue);
               if (dotProduct > 0.1 && player.hasLineOfSight(this)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      int rand = this.random.nextInt(3);
      return rand == 0
         ? (SoundEvent)ModSounds.STATUE_HURT_1.get()
         : (rand == 1 ? (SoundEvent)ModSounds.STATUE_HURT_2.get() : (SoundEvent)ModSounds.STATUE_HURT_3.get());
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "base_controller", 0, event -> event.setAndContinue(RawAnimation.begin().thenLoop("idle"))));
      AnimationController<StatueEntity> actionController = new AnimationController(this, "action_controller", 0, event -> PlayState.STOP);
      actionController.triggerableAnim("approach", RawAnimation.begin().thenPlay("approach"));
      controllers.add(actionController);
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public boolean requiresCustomPersistence() {
      return true;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   class StatueAttackGoal extends MeleeAttackGoal {
      public StatueAttackGoal(StatueEntity mob, double speed, boolean followingTargetEvenIfNotSeen) {
         super(mob, speed, followingTargetEvenIfNotSeen);
      }

      public boolean canUse() {
         StatueEntity statue = (StatueEntity)this.mob;
         return !statue.isFrozen && !statue.hasAttacked && super.canUse();
      }

      public boolean canContinueToUse() {
         StatueEntity statue = (StatueEntity)this.mob;
         return !statue.isFrozen && !statue.hasAttacked && super.canContinueToUse();
      }

      protected void checkAndPerformAttack(LivingEntity enemy) {
         if (this.canPerformAttack(enemy) && this.getTicksUntilNextAttack() <= 0) {
            this.resetAttackCooldown();
            this.mob.doHurtTarget(enemy);
            ((StatueEntity)this.mob).hasAttacked = true;
         }
      }
   }
}
