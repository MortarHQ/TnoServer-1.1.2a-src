package org.popcraft.chunky.api;

import java.util.function.Consumer;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.GenerationTask;
import org.popcraft.chunky.Selection;
import org.popcraft.chunky.api.event.task.GenerationCompleteEvent;
import org.popcraft.chunky.api.event.task.GenerationProgressEvent;
import org.popcraft.chunky.platform.World;
import org.popcraft.chunky.util.Input;
import org.popcraft.chunky.util.Parameter;

public class ChunkyAPIImpl implements ChunkyAPI {
   private final Chunky chunky;

   public ChunkyAPIImpl(Chunky chunky) {
      this.chunky = chunky;
   }

   @Override
   public int version() {
      return 0;
   }

   @Override
   public boolean isRunning(String world) {
      return this.chunky.getGenerationTasks().containsKey(world);
   }

   @Override
   public boolean startTask(String world, String shape, double centerX, double centerZ, double radiusX, double radiusZ, String pattern) {
      World implWorld = Input.tryWorld(this.chunky, world).orElse(null);
      if (implWorld == null) {
         return false;
      }

      if (this.chunky.getGenerationTasks().containsKey(world)) {
         return false;
      }

      Selection selection = Selection.builder(this.chunky, implWorld)
         .shape(shape)
         .center(centerX, centerZ)
         .radiusX(radiusX)
         .radiusZ(radiusZ)
         .pattern(Parameter.of(pattern))
         .build();
      GenerationTask task = new GenerationTask(this.chunky, selection);
      this.chunky.getGenerationTasks().put(world, task);
      this.chunky.getScheduler().runTask(task);
      return true;
   }

   @Override
   public boolean pauseTask(String world) {
      GenerationTask task = this.chunky.getGenerationTasks().get(world);
      if (task == null) {
         return false;
      }

      task.stop(false);
      return true;
   }

   @Override
   public boolean continueTask(String world) {
      World implWorld = Input.tryWorld(this.chunky, world).orElse(null);
      if (implWorld == null) {
         return false;
      }

      GenerationTask task = this.chunky.getTaskLoader().loadTask(implWorld).orElse(null);
      if (task != null && !task.isCancelled()) {
         if (this.chunky.getGenerationTasks().containsKey(world)) {
            return false;
         }

         this.chunky.getGenerationTasks().put(world, task);
         this.chunky.getScheduler().runTask(task);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean cancelTask(String world) {
      World implWorld = Input.tryWorld(this.chunky, world).orElse(null);
      if (implWorld == null) {
         return false;
      }

      if (!this.chunky.getGenerationTasks().containsKey(world)) {
         return false;
      }

      this.chunky.getGenerationTasks().remove(world).stop(true);
      this.chunky.getTaskLoader().cancelTask(implWorld);
      return true;
   }

   @Override
   public void onGenerationProgress(Consumer<GenerationProgressEvent> event) {
      this.chunky.getEventBus().subscribe(GenerationProgressEvent.class, event);
   }

   @Override
   public void onGenerationComplete(Consumer<GenerationCompleteEvent> event) {
      this.chunky.getEventBus().subscribe(GenerationCompleteEvent.class, event);
   }
}
