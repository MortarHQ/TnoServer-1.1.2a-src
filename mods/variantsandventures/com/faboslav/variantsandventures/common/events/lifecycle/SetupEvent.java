package com.faboslav.variantsandventures.common.events.lifecycle;

import com.faboslav.variantsandventures.common.events.base.EventHandler;
import java.util.function.Consumer;

public record SetupEvent(Consumer<Runnable> enqueue) {
   public static final EventHandler<SetupEvent> EVENT = new EventHandler<>();

   public void enqueueWork(Runnable runnable) {
      this.enqueue.accept(runnable);
   }
}
