package net.tslat.smartbrainlib.api.core.behaviour.custom.move;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

public class FleeTarget<E extends PathfinderMob> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(1).hasMemory(MemoryModuleType.ATTACK_TARGET);
   protected int fleeDistance = 20;
   protected float speedModifier = 1.0F;
   protected Path runPath = null;

   public FleeTarget() {
      this.noTimeout();
   }

   public FleeTarget<E> fleeDistance(int blocks) {
      this.fleeDistance = blocks;
      return this;
   }

   public FleeTarget<E> speedModifier(float mod) {
      this.speedModifier = mod;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      LivingEntity target = BrainUtils.getTargetOfEntity(entity);
      double distToTarget = entity.distanceToSqr(target);
      Vec3 runPos = DefaultRandomPos.getPosAway(entity, this.fleeDistance, 10, target.position());
      if (runPos != null && !(target.distanceToSqr(runPos.x, runPos.y, runPos.z) < distToTarget)) {
         this.runPath = entity.getNavigation().createPath(runPos.x, runPos.y, runPos.z, 0);
         return this.runPath != null;
      } else {
         return false;
      }
   }

   protected boolean shouldKeepRunning(E entity) {
      return entity.getNavigation().getPath() == this.runPath && !entity.getNavigation().isDone();
   }

   protected void start(E entity) {
      entity.getNavigation().moveTo(this.runPath, this.speedModifier);
      BrainUtils.clearMemory(entity, MemoryModuleType.ATTACK_TARGET);
   }

   protected void stop(E entity) {
      if (entity.getNavigation().getPath() == this.runPath) {
         entity.getNavigation().setSpeedModifier(1.0);
      }

      this.runPath = null;
   }
}
