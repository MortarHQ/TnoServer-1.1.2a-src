package com.blorb.morerelics.helper;

import java.util.ArrayList;
import java.util.List;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Pre;

@EventBusSubscriber
public class DelayedTaskManager {
   public static List<DelayedTask> tasks = new ArrayList<>();

   public static void addTask(Runnable task, int delay) {
      tasks.add(new DelayedTask(task, delay));
   }

   @SubscribeEvent
   public static void onServerEvent(Pre event) {
      List<DelayedTask> toRemove = new ArrayList<>();

      for (DelayedTask task : tasks) {
         if (task.tickDelay == 0) {
            task.runnable.run();
            toRemove.add(task);
         } else {
            task.tickDelay--;
         }
      }

      for (DelayedTask task : toRemove) {
         tasks.remove(task);
      }
   }
}
