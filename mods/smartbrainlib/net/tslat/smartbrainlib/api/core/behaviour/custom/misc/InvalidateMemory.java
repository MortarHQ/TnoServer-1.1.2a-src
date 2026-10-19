package net.tslat.smartbrainlib.api.core.behaviour.custom.misc;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.MemoryTest;
import net.tslat.smartbrainlib.util.BrainUtils;

public class InvalidateMemory<E extends LivingEntity, M> extends ExtendedBehaviour<E> {
   private List<Pair<MemoryModuleType<?>, MemoryStatus>> memoryRequirements;
   protected BiPredicate<E, M> customPredicate = (entity, target) -> true;
   protected MemoryModuleType<M> memory;

   public InvalidateMemory(MemoryModuleType<M> memory) {
      this.memory = memory;
      this.memoryRequirements = MemoryTest.builder(1).hasMemory(this.memory);
   }

   @Deprecated(forRemoval = true)
   public InvalidateMemory<E, M> forMemory(MemoryModuleType<M> memory) {
      this.memory = memory;
      this.memoryRequirements = MemoryTest.builder(1).hasMemory(memory);
      return this;
   }

   public InvalidateMemory<E, M> invalidateIf(BiPredicate<E, M> predicate) {
      this.customPredicate = predicate;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return this.memoryRequirements == null ? List.of() : this.memoryRequirements;
   }

   @Override
   protected void start(E entity) {
      M memory = BrainUtils.getMemory(entity, this.memory);
      if (memory != null && this.customPredicate.test(entity, memory)) {
         BrainUtils.clearMemory(entity, this.memory);
      }
   }
}
