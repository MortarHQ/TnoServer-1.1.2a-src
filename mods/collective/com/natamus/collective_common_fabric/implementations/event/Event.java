package com.natamus.collective_common_fabric.implementations.event;

import net.minecraft.class_2960;

public abstract class Event<T> {
   protected volatile T invoker;
   public static final class_2960 DEFAULT_PHASE = class_2960.method_60655("collective", "default");

   public final T invoker() {
      return this.invoker;
   }

   public abstract void register(T var1);

   public void register(class_2960 phase, T listener) {
      this.register(listener);
   }

   public void addPhaseOrdering(class_2960 firstPhase, class_2960 secondPhase) {
   }
}
