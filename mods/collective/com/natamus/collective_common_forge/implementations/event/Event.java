package com.natamus.collective_common_forge.implementations.event;

import net.minecraft.resources.ResourceLocation;

public abstract class Event<T> {
   protected volatile T invoker;
   public static final ResourceLocation DEFAULT_PHASE = ResourceLocation.fromNamespaceAndPath("collective", "default");

   public final T invoker() {
      return this.invoker;
   }

   public abstract void register(T var1);

   public void register(ResourceLocation phase, T listener) {
      this.register(listener);
   }

   public void addPhaseOrdering(ResourceLocation firstPhase, ResourceLocation secondPhase) {
   }
}
