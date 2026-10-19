package net.tslat.smartbrainlib.object;

import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.tslat.smartbrainlib.util.SensoryUtils;
import org.jetbrains.annotations.Nullable;

public class ExtendedTargetingConditions {
   protected BiPredicate<LivingEntity, LivingEntity> customFilter = null;
   protected Function<LivingEntity, Double> rangeRetriever = null;
   protected boolean isAttacking = true;
   protected boolean checkLineOfSight = true;
   protected boolean ignoresInvisibility = false;

   public static ExtendedTargetingConditions forLookTarget() {
      return new ExtendedTargetingConditions().isJustLooking();
   }

   public static ExtendedTargetingConditions forLookTargetIgnoringInvisibility() {
      return forLookTarget().skipInvisibilityCheck();
   }

   public static ExtendedTargetingConditions forAttackTarget() {
      return new ExtendedTargetingConditions();
   }

   public static ExtendedTargetingConditions forAttackTargetIgnoringInvisibility() {
      return forAttackTarget().skipInvisibilityCheck();
   }

   public ExtendedTargetingConditions isJustLooking() {
      this.isAttacking = false;
      return this;
   }

   public ExtendedTargetingConditions withRange(double range) {
      return this.withRange(entity -> range);
   }

   public ExtendedTargetingConditions withRange(Function<LivingEntity, Double> function) {
      this.rangeRetriever = function;
      return this;
   }

   public ExtendedTargetingConditions withFollowRange() {
      return this.withRange(entity -> entity.getAttribute(Attributes.FOLLOW_RANGE) != null ? entity.getAttributeValue(Attributes.FOLLOW_RANGE) : 16.0);
   }

   public ExtendedTargetingConditions onlyTargeting(BiPredicate<@Nullable LivingEntity, LivingEntity> predicate) {
      this.customFilter = predicate;
      return this;
   }

   public ExtendedTargetingConditions ignoreLineOfSight() {
      this.checkLineOfSight = false;
      return this;
   }

   public ExtendedTargetingConditions skipInvisibilityCheck() {
      this.ignoresInvisibility = true;
      return this;
   }

   public boolean test(@Nullable LivingEntity entity, LivingEntity target) {
      if (entity == target || !target.canBeSeenByAnyone()) {
         return false;
      }

      if (this.customFilter != null && !this.customFilter.test(entity, target)) {
         return false;
      }

      if (entity == null) {
         return !this.isAttacking || target.canBeSeenAsEnemy() && target.level().getDifficulty() != Difficulty.PEACEFUL;
      }

      if (!this.isAttacking || entity.canAttack(target) && entity.canAttackType(target.getType()) && !entity.isAlliedTo(target)) {
         double range = this.rangeRetriever.apply(entity);
         if (range > 0.0) {
            double sightRange = Math.max(range * (this.ignoresInvisibility ? 1.0 : target.getVisibilityPercent(entity)), 2.0);
            if (entity.distanceToSqr(target) > sightRange * sightRange) {
               return false;
            }
         }

         return this.checkLineOfSight ? SensoryUtils.hasLineOfSight(entity, target) : true;
      } else {
         return false;
      }
   }
}
