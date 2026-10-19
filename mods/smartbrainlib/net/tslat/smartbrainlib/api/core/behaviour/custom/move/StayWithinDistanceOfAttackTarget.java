package net.tslat.smartbrainlib.api.core.behaviour.custom.move;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

public class StayWithinDistanceOfAttackTarget<E extends PathfinderMob> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(2).hasMemory(MemoryModuleType.ATTACK_TARGET).noMemory(MemoryModuleType.WALK_TARGET);
   protected BiFunction<E, LivingEntity, Float> distMax = (entity, target) -> 20.0F;
   protected BiFunction<E, LivingEntity, Float> distMin = (entity, target) -> 5.0F;
   protected Predicate<E> stopWhen = entity -> false;
   protected float speedMod = 1.0F;
   protected float repositionSpeedMod = 1.3F;

   public StayWithinDistanceOfAttackTarget<E> minDistance(float distance) {
      return this.minDistance((entity, target) -> distance);
   }

   public StayWithinDistanceOfAttackTarget<E> minDistance(BiFunction<E, LivingEntity, Float> distance) {
      this.distMin = distance;
      return this;
   }

   public StayWithinDistanceOfAttackTarget<E> maxDistance(float distance) {
      return this.maxDistance((entity, target) -> distance);
   }

   public StayWithinDistanceOfAttackTarget<E> maxDistance(BiFunction<E, LivingEntity, Float> distance) {
      this.distMax = distance;
      return this;
   }

   public StayWithinDistanceOfAttackTarget<E> speedMod(float modifier) {
      this.speedMod = modifier;
      return this;
   }

   public StayWithinDistanceOfAttackTarget<E> repositionSpeedMod(float modifier) {
      this.speedMod = modifier;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected boolean shouldKeepRunning(E entity) {
      return BrainUtils.hasMemory(entity, MemoryModuleType.ATTACK_TARGET) && !this.stopWhen.test(entity);
   }

   protected void tick(E entity) {
      LivingEntity target = BrainUtils.getTargetOfEntity(entity);
      double distanceToTarget = target.distanceToSqr(entity);
      float maxDist = this.distMax.apply(entity, target);
      double maxDistSq = Math.pow(maxDist, 2.0);
      double minDistSq = Math.pow(this.distMin.apply(entity, target).floatValue(), 2.0);
      PathNavigation navigation = entity.getNavigation();
      if (!(distanceToTarget > maxDistSq) && entity.hasLineOfSight(target)) {
         if (distanceToTarget < minDistSq) {
            if (navigation.isDone()) {
               Vec3 runPos = DefaultRandomPos.getPosAway(entity, (int)maxDist, 5, target.position());
               if (runPos != null) {
                  navigation.moveTo(navigation.createPath(BlockPos.containing(runPos), 1), this.repositionSpeedMod);
               }
            }
         } else {
            if (navigation instanceof GroundPathNavigation) {
               navigation.stop();
            }

            BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));
            if (distanceToTarget > maxDistSq * 0.5) {
               entity.lookAt(target, 30.0F, 30.0F);
               entity.getMoveControl().strafe(0.5F * this.speedMod, 0.0F);
            } else if (distanceToTarget < minDistSq * 3.0) {
               entity.lookAt(target, 30.0F, 30.0F);
               entity.getMoveControl().strafe(-0.5F * this.speedMod, 0.0F);
            }
         }
      } else {
         if (navigation.isDone()) {
            navigation.moveTo(target, this.repositionSpeedMod);
         }
      }
   }
}
