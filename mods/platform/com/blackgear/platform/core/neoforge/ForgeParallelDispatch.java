package com.blackgear.platform.core.neoforge;

import com.blackgear.platform.core.ParallelDispatch;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.neoforged.fml.event.lifecycle.ParallelDispatchEvent;

public class ForgeParallelDispatch implements ParallelDispatch {
   private final ParallelDispatchEvent event;

   public ForgeParallelDispatch(ParallelDispatchEvent event) {
      this.event = event;
   }

   @Override
   public CompletableFuture<Void> enqueueWork(Runnable work) {
      return this.event.enqueueWork(work);
   }

   @Override
   public <T> CompletableFuture<T> enqueueWork(Supplier<T> work) {
      return this.event.enqueueWork(work);
   }
}
