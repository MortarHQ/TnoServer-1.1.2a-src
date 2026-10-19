package net.tslat.smartbrainlib.api.core.behaviour.custom.misc;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.HeldBehaviour;

public final class CustomHeldBehaviour<E extends LivingEntity> extends HeldBehaviour<E> {
   private Consumer<E> callback;

   public CustomHeldBehaviour(Consumer<E> callback) {
      this.callback = callback;
   }

   public CustomHeldBehaviour<E> callback(Consumer<E> callback) {
      this.callback = callback;
      return this;
   }

   @Override
   protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
      return List.of();
   }

   @Override
   protected void tick(E entity) {
      this.callback.accept(entity);
   }
}
