package net.tslat.smartbrainlib.api.core.behaviour.custom.move;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.TriPredicate;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.RandomUtil;

public class FollowEntity<E extends PathfinderMob, T extends Entity> extends ExtendedBehaviour<E> {
   protected Function<E, T> followingEntityProvider = entity -> null;
   protected BiFunction<E, T, Double> teleportDistance = (entity, target) -> Double.MAX_VALUE;
   protected BiFunction<E, T, Double> followDistMin = (entity, target) -> 4.0;
   protected BiFunction<E, T, Float> speedMod = (entity, target) -> 1.0F;
   protected TriPredicate<E, BlockPos, BlockState> teleportPredicate = this::isTeleportable;
   protected Predicate<E> canTeleportOffGround = entity -> entity.getNavigation().getNodeEvaluator() instanceof SwimNodeEvaluator
      || entity.getNavigation().getNodeEvaluator() instanceof FlyNodeEvaluator;
   protected float oldWaterPathMalus = 0.0F;
   protected float oldLavaPathMalus = 0.0F;

   public FollowEntity<E, T> following(Function<E, T> following) {
      this.followingEntityProvider = following;
      return this;
   }

   public FollowEntity<E, T> teleportToTargetAfter(double distance) {
      return this.teleportToTargetAfter((entity, target) -> distance);
   }

   public FollowEntity<E, T> teleportToTargetAfter(BiFunction<E, T, Double> distanceProvider) {
      this.teleportDistance = distanceProvider;
      return this;
   }

   public FollowEntity<E, T> stopFollowingWithin(double distance) {
      return this.stopFollowingWithin((entity, target) -> distance);
   }

   public FollowEntity<E, T> stopFollowingWithin(BiFunction<E, T, Double> distanceProvider) {
      this.followDistMin = distanceProvider;
      return this;
   }

   public FollowEntity<E, T> speedMod(float modifier) {
      return this.speedMod((entity, target) -> modifier);
   }

   public FollowEntity<E, T> speedMod(BiFunction<E, T, Float> modifier) {
      this.speedMod = modifier;
      return this;
   }

   public FollowEntity<E, T> canTeleportTo(TriPredicate<E, BlockPos, BlockState> teleportPosPredicate) {
      this.teleportPredicate = teleportPosPredicate;
      return this;
   }

   public FollowEntity<E, T> canTeleportOffGroundWhen(Predicate<E> predicate) {
      this.canTeleportOffGround = predicate;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return List.of();
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      T target = this.followingEntityProvider.apply(entity);
      if (target != null && !target.isSpectator()) {
         double minDist = this.followDistMin.apply(entity, target);
         return entity.distanceToSqr(target) > minDist * minDist;
      } else {
         return false;
      }
   }

   protected boolean shouldKeepRunning(E entity) {
      T target = this.followingEntityProvider.apply(entity);
      if (target == null) {
         return false;
      }

      double dist = entity.distanceToSqr(target);
      double minDist = this.followDistMin.apply(entity, target);
      return dist > minDist * minDist;
   }

   protected void start(E entity) {
      T target = this.followingEntityProvider.apply(entity);
      double minDist = this.followDistMin.apply(entity, target);
      float speedMod = this.speedMod.apply(entity, target);
      this.oldWaterPathMalus = entity.getPathfindingMalus(PathType.WATER);
      if (entity.fireImmune()) {
         this.oldLavaPathMalus = entity.getPathfindingMalus(PathType.LAVA);
         entity.setPathfindingMalus(PathType.LAVA, 0.0F);
      }

      BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(target, speedMod, (int)minDist));
      BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));
      entity.setPathfindingMalus(PathType.WATER, 0.0F);
   }

   protected void stop(E entity) {
      entity.setPathfindingMalus(PathType.WATER, this.oldWaterPathMalus);
      if (entity.fireImmune()) {
         entity.setPathfindingMalus(PathType.LAVA, this.oldLavaPathMalus);
      }

      entity.getNavigation().stop();
      BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
   }

   protected void tick(E entity) {
      T target = this.followingEntityProvider.apply(entity);
      double teleportDist = this.teleportDistance.apply(entity, target);
      if (entity.distanceToSqr(target) >= teleportDist * teleportDist) {
         this.teleportToTarget(entity, target);
      }
   }

   protected void teleportToTarget(E entity, T target) {
      BlockPos targetPos = target.blockPosition();
      BlockPos teleportPos = this.getTeleportPos(entity, target, targetPos);
      if (!teleportPos.equals(targetPos)) {
         entity.moveTo(teleportPos.getX() + 0.5, teleportPos.getY(), teleportPos.getZ() + 0.5, entity.getYRot(), entity.getXRot());
         entity.getNavigation().stop();
         BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
      }
   }

   protected BlockPos getTeleportPos(E entity, T target, BlockPos targetPos) {
      Level level = entity.level();
      return RandomUtil.getRandomPositionWithinRange(
         targetPos,
         5,
         5,
         5,
         1,
         1,
         1,
         !this.canTeleportOffGround.test(entity),
         level,
         10,
         (state, statePos) -> this.teleportPredicate.test(entity, statePos, state)
      );
   }

   protected boolean isTeleportable(E entity, BlockPos pos, BlockState state) {
      NodeEvaluator nodeEvaluator = entity.getNavigation().getNodeEvaluator();
      PathType pathType = nodeEvaluator.getPathType(new PathfindingContext(entity.level(), entity), pos.getX(), pos.getY(), pos.getZ());
      if (!this.canTeleportOffGround.test(entity)) {
         if (pathType != PathType.WALKABLE) {
            return false;
         }
      } else if (pathType != PathType.OPEN && pathType != PathType.WALKABLE) {
         return false;
      }

      return entity.level().noCollision(entity, entity.getBoundingBox().move(Vec3.atBottomCenterOf(pos).subtract(entity.position())));
   }
}
