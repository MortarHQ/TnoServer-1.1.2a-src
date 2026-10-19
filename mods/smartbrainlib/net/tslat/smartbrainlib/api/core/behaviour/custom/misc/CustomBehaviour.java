package net.tslat.smartbrainlib.api.core.behaviour.custom.misc;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

public final class CustomBehaviour<E extends LivingEntity> extends ExtendedBehaviour<E> {
   private Consumer<E> callback;

   public CustomBehaviour(Consumer<E> callback) {
      this.callback = callback;
   }

   public CustomBehaviour<E> callback(Consumer<E> callback) {
      this.callback = callback;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return List.of();
   }

   @Override
   protected void start(E entity) {
      this.callback.accept(entity);
   }
}
