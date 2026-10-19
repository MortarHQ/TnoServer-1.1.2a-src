package com.cerbon.bosses_of_mass_destruction.entity.custom.lich;

import com.cerbon.bosses_of_mass_destruction.entity.ai.ValidatedTargetSelector;
import com.cerbon.bosses_of_mass_destruction.entity.ai.VelocitySteering;
import com.cerbon.bosses_of_mass_destruction.entity.ai.goals.VelocityGoal;
import com.cerbon.bosses_of_mass_destruction.entity.ai.valid_direction.CanMoveThrough;
import com.cerbon.bosses_of_mass_destruction.entity.ai.valid_direction.InDesiredRange;
import com.cerbon.bosses_of_mass_destruction.entity.ai.valid_direction.ValidDirectionAnd;
import com.cerbon.bosses_of_mass_destruction.entity.util.EntityAdapter;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntity;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LichMovement {
   private final LichEntity entity;
   private final IEntity iEntity;
   private final double reactionDistance = 4.0;
   private final double idleWanderDistance = 50.0;
   private final double tooFarFromTargetDistance = 30.0;
   private final double tooCloseToTargetDistance = 15.0;

   public LichMovement(LichEntity entity) {
      this.entity = entity;
      this.iEntity = new EntityAdapter(entity);
   }

   public VelocityGoal buildAttackMovement() {
      Function<Vec3, Boolean> tooCloseToTarget = v -> this.getWithinDistancePredicate(15.0, this.entity::safeGetTargetPos).test(v);
      ValidDirectionAnd canMoveTowardsPositionValidator = this.getValidDirectionAnd(tooCloseToTarget);
      ValidatedTargetSelector targetSelector = new ValidatedTargetSelector(this.iEntity, canMoveTowardsPositionValidator);
      return new VelocityGoal(this::moveWhileAttacking, this.createSteering(), targetSelector);
   }

   @NotNull
   private ValidDirectionAnd getValidDirectionAnd(Function<Vec3, Boolean> tooCloseToTarget) {
      Function<Vec3, Boolean> tooFarFromTarget = v -> !this.getWithinDistancePredicate(30.0, this.entity::safeGetTargetPos).test(v);
      Function<Vec3, Boolean> movingToTarget = v -> MathUtils.movingTowards(this.entity.safeGetTargetPos(), this.entity.position(), v);
      return new ValidDirectionAnd(Arrays.asList(new CanMoveThrough(this.entity, 4.0), new InDesiredRange(tooCloseToTarget, tooFarFromTarget, movingToTarget)));
   }

   private void moveWhileAttacking(Vec3 velocity) {
      this.entity.addDeltaMovement(velocity);
      LivingEntity target = this.entity.getTarget();
      if (target != null) {
         this.entity.getLookControl().setLookAt(target.position());
         VanillaCopiesServer.lookAtTarget(this.entity, target.position(), this.entity.getHeadRotSpeed(), this.entity.getMaxHeadXRot());
      }
   }

   public VelocityGoal buildWanderGoal() {
      Function<Vec3, Boolean> tooFarFromTarget = v -> this.getWithinDistancePredicate(50.0, () -> this.entity.idlePosition).test(v);
      Function<Vec3, Boolean> movingTowardsIdleCenter = v -> MathUtils.movingTowards(this.entity.idlePosition, this.entity.position(), v);
      ValidDirectionAnd canMoveTowardsPositionValidator = new ValidDirectionAnd(
         Arrays.asList(new CanMoveThrough(this.entity, 4.0), new InDesiredRange(v -> false, tooFarFromTarget, movingTowardsIdleCenter))
      );
      ValidatedTargetSelector targetSelector = new ValidatedTargetSelector(this.iEntity, canMoveTowardsPositionValidator);
      return new VelocityGoal(this::moveTowards, this.createSteering(), targetSelector);
   }

   private VelocitySteering createSteering() {
      return new VelocitySteering(this.iEntity, this.entity.getAttributeValue(Attributes.FLYING_SPEED), 120.0);
   }

   private Predicate<Vec3> getWithinDistancePredicate(double distance, Supplier<Vec3> targetPos) {
      return v -> {
         Vec3 target = this.entity.position().add(v.scale(4.0));
         return MathUtils.withinDistance(target, targetPos.get(), distance);
      };
   }

   private void moveTowards(Vec3 velocity) {
      this.entity.addDeltaMovement(velocity);
      Vec3 lookTarget = this.entity.position().add(new Vec3(0.0, this.entity.getEyeHeight(), 0.0)).add(velocity);
      this.entity.getLookControl().setLookAt(lookTarget);
      VanillaCopiesServer.lookAtTarget(this.entity, lookTarget, this.entity.getHeadRotSpeed(), this.entity.getMaxHeadXRot());
   }
}
