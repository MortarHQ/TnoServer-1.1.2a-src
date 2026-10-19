package org.crypticdev.stextras.mixin.architectury.event;

import dev.architectury.event.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "dev.architectury.event.EventFactory$EventImpl", remap = false)
abstract class EventFactoryEventImplMixin<T> implements Event<T> {
   @Shadow
   @Final
   private Function<List<T>, T> function;
   @Shadow
   private T invoker;
   @Shadow
   private ArrayList<T> listeners;

   @Overwrite
   public synchronized T invoker() {
      if (this.invoker == null) {
         this.update();
      }

      return this.invoker;
   }

   @Overwrite
   public synchronized void register(T listener) {
      this.listeners.add(listener);
      this.invoker = null;
   }

   @Overwrite
   public synchronized void unregister(T listener) {
      this.listeners.remove(listener);
      this.listeners.trimToSize();
      this.invoker = null;
   }

   @Overwrite
   public synchronized boolean isRegistered(T listener) {
      return this.listeners.contains(listener);
   }

   @Overwrite
   public synchronized void clearListeners() {
      this.listeners.clear();
      this.listeners.trimToSize();
      this.invoker = null;
   }

   @Overwrite
   public synchronized void update() {
      if (this.listeners.size() == 1) {
         this.invoker = this.listeners.getFirst();
      } else {
         this.invoker = this.function.apply(List.copyOf(this.listeners));
      }
   }
}
