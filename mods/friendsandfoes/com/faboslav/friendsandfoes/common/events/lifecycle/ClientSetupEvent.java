package com.faboslav.friendsandfoes.common.events.lifecycle;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import java.util.function.Consumer;

public record ClientSetupEvent(Consumer<Runnable> enqueue) {
   public static final EventHandler<ClientSetupEvent> EVENT = new EventHandler<>();

   public void enqueueWork(Runnable runnable) {
      this.enqueue.accept(runnable);
   }
}
