package org.popcraft.chunky.api;

import java.util.function.Consumer;
import org.popcraft.chunky.api.event.task.GenerationCompleteEvent;
import org.popcraft.chunky.api.event.task.GenerationProgressEvent;

public interface ChunkyAPI {
   int version();

   boolean isRunning(String var1);

   boolean startTask(String var1, String var2, double var3, double var5, double var7, double var9, String var11);

   boolean pauseTask(String var1);

   boolean continueTask(String var1);

   boolean cancelTask(String var1);

   void onGenerationProgress(Consumer<GenerationProgressEvent> var1);

   void onGenerationComplete(Consumer<GenerationCompleteEvent> var1);
}
