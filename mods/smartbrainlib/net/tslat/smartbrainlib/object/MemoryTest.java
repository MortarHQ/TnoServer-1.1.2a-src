package net.tslat.smartbrainlib.object;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class MemoryTest extends ObjectArrayList<Pair<MemoryModuleType<?>, MemoryStatus>> {
   private MemoryTest(int size) {
      super(size);
   }

   public static MemoryTest builder(int size) {
      return new MemoryTest(size);
   }

   public static MemoryTest builder() {
      return builder(10);
   }

   public MemoryTest hasMemory(MemoryModuleType<?> memory) {
      return this.add(memory, MemoryStatus.VALUE_PRESENT);
   }

   public MemoryTest hasMemories(MemoryModuleType<?>... memories) {
      for (MemoryModuleType<?> memory : memories) {
         this.hasMemory(memory);
      }

      return this;
   }

   public MemoryTest noMemory(MemoryModuleType<?> memory) {
      return this.add(memory, MemoryStatus.VALUE_ABSENT);
   }

   public MemoryTest hasNoMemories(MemoryModuleType<?>... memories) {
      for (MemoryModuleType<?> memory : memories) {
         this.noMemory(memory);
      }

      return this;
   }

   public MemoryTest usesMemory(MemoryModuleType<?> memory) {
      return this.add(memory, MemoryStatus.REGISTERED);
   }

   public MemoryTest usesMemories(MemoryModuleType<?>... memories) {
      for (MemoryModuleType<?> memory : memories) {
         this.usesMemory(memory);
      }

      return this;
   }

   public MemoryTest add(MemoryModuleType<?> memory, MemoryStatus status) {
      super.add(Pair.of(memory, status));
      return this;
   }
}
