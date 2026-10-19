package net.tslat.smartbrainlib.api.core.behaviour.custom.misc;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.util.BrainUtils;

public class ReactToUnreachableTarget<E extends LivingEntity> extends ExtendedBehaviour<E> {
   private static final MemoryTest MEMORY_REQUIREMENTS = MemoryTest.builder(2)
      .hasMemories(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, SBLMemoryTypes.TARGET_UNREACHABLE.get());
   protected Function<E, Integer> ticksToReact = entity -> 100;
   protected BiConsumer<E, Boolean> callback = (entity, towering) -> {};
   protected long reactAtTime = 0L;

   public ReactToUnreachableTarget<E> timeBeforeReacting(Function<E, Integer> ticksToReact) {
      this.ticksToReact = ticksToReact;
      return this;
   }

   public ReactToUnreachableTarget<E> reaction(BiConsumer<E, Boolean> callback) {
      this.callback = callback;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return MEMORY_REQUIREMENTS;
   }

   @Override
   protected boolean timedOut(long gameTime) {
      return this.reactAtTime == 0L || this.reactAtTime < gameTime;
   }

   @Override
   protected boolean shouldKeepRunning(E entity) {
      return this.hasRequiredMemories(entity);
   }

   @Override
   protected void start(E entity) {
      this.reactAtTime = entity.level().getGameTime() + this.ticksToReact.apply(entity).intValue();
   }

   @Override
   protected void stop(E entity) {
      this.reactAtTime = 0L;
   }

   @Override
   protected void tick(E entity) {
      if (entity.level().getGameTime() == this.reactAtTime) {
         this.callback.accept(entity, BrainUtils.getMemory(entity, SBLMemoryTypes.TARGET_UNREACHABLE.get()));
      }
   }
}
