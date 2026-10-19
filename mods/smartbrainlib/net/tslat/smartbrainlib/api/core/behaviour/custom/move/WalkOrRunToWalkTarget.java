package net.tslat.smartbrainlib.api.core.behaviour.custom.move;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.tslat.smartbrainlib.util.BrainUtils;

public class WalkOrRunToWalkTarget<E extends PathfinderMob> extends MoveToWalkTarget<E> {
   @Override
   protected void startOnNewPath(E entity) {
      BrainUtils.setMemory(entity, MemoryModuleType.PATH, this.path);
      if (entity.getNavigation().moveTo(this.path, this.speedModifier)) {
         entity.setSharedFlag(3, this.speedModifier > 1.0F);
      }
   }

   @Override
   protected void stop(E entity) {
      super.stop(entity);
      entity.setSharedFlag(3, false);
   }
}
