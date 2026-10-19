package net.tslat.smartbrainlib.api.core.behaviour.custom.move;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

public class AvoidEntity<E extends PathfinderMob> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(1).hasMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
   protected Predicate<LivingEntity> avoidingPredicate = target -> false;
   protected float noCloserThanSqr = 9.0F;
   protected float stopAvoidingAfterSqr = 49.0F;
   protected float speedModifier = 1.0F;
   private Path runPath = null;

   public AvoidEntity() {
      this.noTimeout();
   }

   public AvoidEntity<E> noCloserThan(float blocks) {
      this.noCloserThanSqr = blocks * blocks;
      return this;
   }

   public AvoidEntity<E> stopCaringAfter(float blocks) {
      this.stopAvoidingAfterSqr = blocks * blocks;
      return this;
   }

   public AvoidEntity<E> avoiding(Predicate<LivingEntity> predicate) {
      this.avoidingPredicate = predicate;
      return this;
   }

   public AvoidEntity<E> speedModifier(float mod) {
      this.speedModifier = mod;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      Optional<LivingEntity> target = BrainUtils.<NearestVisibleLivingEntities>getMemory(entity, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
         .findClosest(this.avoidingPredicate);
      if (target.isEmpty()) {
         return false;
      } else {
         LivingEntity avoidingEntity = target.get();
         double distToTarget = avoidingEntity.distanceToSqr(entity);
         if (distToTarget > this.noCloserThanSqr) {
            return false;
         } else {
            Vec3 runPos = DefaultRandomPos.getPosAway(entity, 16, 7, avoidingEntity.position());
            if (runPos != null && !(avoidingEntity.distanceToSqr(runPos.x, runPos.y, runPos.z) < distToTarget)) {
               this.runPath = entity.getNavigation().createPath(runPos.x, runPos.y, runPos.z, 0);
               return this.runPath != null;
            } else {
               return false;
            }
         }
      }
   }

   protected boolean shouldKeepRunning(E entity) {
      return !this.runPath.isDone();
   }

   protected void start(E entity) {
      entity.getNavigation().moveTo(this.runPath, this.speedModifier);
   }

   protected void stop(E entity) {
      this.runPath = null;
      entity.getNavigation().setSpeedModifier(1.0);
   }
}
