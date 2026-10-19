package net.tslat.smartbrainlib.api.core.behaviour.custom.attack;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.util.BrainUtils;

public class LeapAtTarget<E extends Mob> extends AnimatableMeleeAttack<E> {
   protected BiFunction<E, LivingEntity, Float> verticalJumpStrength = (entity, target) -> 0.3F;
   protected BiFunction<E, LivingEntity, Float> jumpStrength = (entity, target) -> 0.4F;
   protected BiFunction<E, LivingEntity, Float> moveSpeedContribution = (entity, target) -> 0.2F;
   protected BiFunction<E, LivingEntity, Float> leapRange = (entity, target) -> 8.0F;
   @Deprecated(forRemoval = true)
   protected BiPredicate<E, LivingEntity> leapPredicate = (entity, target) -> true;

   public LeapAtTarget(int delayTicks) {
      super(delayTicks);
   }

   @Deprecated(forRemoval = true)
   public LeapAtTarget<E> leapIf(BiPredicate<E, LivingEntity> predicate) {
      this.leapPredicate = predicate;
      return this;
   }

   public LeapAtTarget<E> leapRange(BiFunction<E, LivingEntity, Float> range) {
      this.leapRange = range;
      return this;
   }

   public LeapAtTarget<E> jumpStrength(BiFunction<E, LivingEntity, Float> function) {
      this.jumpStrength = function;
      return this;
   }

   public LeapAtTarget<E> verticalJumpStrength(BiFunction<E, LivingEntity, Float> function) {
      this.verticalJumpStrength = function;
      return this;
   }

   public LeapAtTarget<E> moveSpeedContribution(BiFunction<E, LivingEntity, Float> function) {
      this.moveSpeedContribution = function;
      return this;
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      this.target = BrainUtils.getTargetOfEntity(entity);
      return entity.onGround()
         && entity.getSensing().hasLineOfSight(this.target)
         && entity.closerThan(this.target, this.leapRange.apply(entity, this.target).floatValue());
   }

   @Override
   protected void start(E entity) {
      super.start(entity);
      Vec3 velocity = new Vec3(this.target.getX() - entity.getX(), 0.0, this.target.getZ() - entity.getZ());
      if (velocity.lengthSqr() > 1.0E-7) {
         velocity = velocity.normalize()
            .scale(this.jumpStrength.apply(entity, this.target).floatValue())
            .add(entity.getDeltaMovement().scale(this.moveSpeedContribution.apply(entity, this.target).floatValue()));
      }

      entity.setDeltaMovement(velocity.x, this.verticalJumpStrength.apply(entity, this.target).floatValue(), velocity.z);
   }
}
