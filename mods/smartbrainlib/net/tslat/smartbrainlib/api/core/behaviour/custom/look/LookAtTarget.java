package net.tslat.smartbrainlib.api.core.behaviour.custom.look;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

public class LookAtTarget<E extends Mob> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(1).hasMemory(MemoryModuleType.LOOK_TARGET);

   public LookAtTarget() {
      this.noTimeout();
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
      return this.testAndInvalidateLookTarget(entity);
   }

   protected boolean shouldKeepRunning(E entity) {
      return this.testAndInvalidateLookTarget(entity);
   }

   protected void tick(E entity) {
      BrainUtils.withMemory(entity, MemoryModuleType.LOOK_TARGET, target -> entity.getLookControl().setLookAt(target.currentPosition()));
   }

   protected boolean testAndInvalidateLookTarget(E entity) {
      PositionTracker lookTarget = BrainUtils.getMemory(entity, MemoryModuleType.LOOK_TARGET);
      if (lookTarget == null) {
         return false;
      } else if (!(lookTarget instanceof EntityTracker entityTracker && (!entityTracker.getEntity().isAlive() || entityTracker.getEntity().isSpectator()))) {
         return true;
      } else {
         BrainUtils.clearMemory(entity, MemoryModuleType.LOOK_TARGET);
         return false;
      }
   }
}
