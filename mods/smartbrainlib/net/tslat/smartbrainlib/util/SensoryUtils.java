package net.tslat.smartbrainlib.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.tslat.smartbrainlib.object.ExtendedTargetingConditions;

public class SensoryUtils {
   public static boolean isEntityTargetable(LivingEntity attacker, LivingEntity target) {
      ExtendedTargetingConditions predicate = BrainUtils.getTargetOfEntity(attacker) == target
         ? ExtendedTargetingConditions.forLookTarget().withFollowRange().skipInvisibilityCheck()
         : ExtendedTargetingConditions.forLookTarget().withFollowRange();
      return predicate.test(attacker, target);
   }

   public static boolean isEntityAttackable(LivingEntity attacker, LivingEntity target) {
      ExtendedTargetingConditions predicate = BrainUtils.getTargetOfEntity(attacker) == target
         ? ExtendedTargetingConditions.forAttackTarget().withFollowRange().skipInvisibilityCheck()
         : ExtendedTargetingConditions.forAttackTarget().withFollowRange();
      return predicate.test(attacker, target);
   }

   public static boolean isEntityAttackableIgnoringLineOfSight(LivingEntity attacker, LivingEntity target) {
      ExtendedTargetingConditions predicate = BrainUtils.getTargetOfEntity(attacker) == target
         ? ExtendedTargetingConditions.forAttackTarget().ignoreLineOfSight().withFollowRange().skipInvisibilityCheck()
         : ExtendedTargetingConditions.forAttackTarget().ignoreLineOfSight().withFollowRange();
      return predicate.test(attacker, target);
   }

   public static boolean hasLineOfSight(LivingEntity entity, Entity target) {
      return entity instanceof Mob mob ? mob.getSensing().hasLineOfSight(target) : entity.hasLineOfSight(target);
   }
}
