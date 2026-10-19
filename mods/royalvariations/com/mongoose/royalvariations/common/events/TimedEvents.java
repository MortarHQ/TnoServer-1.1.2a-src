package com.mongoose.royalvariations.common.events;

import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.utils.EventTask;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = "royalvariations")
public class TimedEvents {
   private static final Queue<Pair<String, EventTask>> TASKS = new ArrayDeque<>();

   @SubscribeEvent
   public static void ServerTickEvents(Post event) {
      Iterator<Pair<String, EventTask>> it = TASKS.iterator();

      while (it.hasNext()) {
         Pair<String, EventTask> current = it.next();

         try {
            if (((EventTask)current.getRight()).getAsBoolean()) {
               ((EventTask)current.getRight()).endTask();
               it.remove();
            } else {
               ((EventTask)current.getRight()).tickTask();
            }
         } catch (Exception ex) {
            RoyalVariations.LOGGER.error("An exception occurred while running a ticking task with ID {}.  It will be terminated.", current.getLeft());
            it.remove();
         }
      }
   }

   @SubscribeEvent
   public static void stopped(ServerStoppedEvent event) {
      for (Pair<String, EventTask> pair : TASKS) {
         if (pair.getRight() != null) {
            ((EventTask)pair.getRight()).endTask();
         }
      }

      TASKS.clear();
   }

   @SubscribeEvent
   public static void started(ServerStartedEvent event) {
      TASKS.clear();
   }

   public static void submitTask(String id, EventTask task) {
      TASKS.add(Pair.of(id, task));
      task.startTask();
   }
}
