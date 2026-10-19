package com.natamus.collective_common_fabric.functions;

import com.mojang.datafixers.util.Pair;
import com.natamus.collective_common_fabric.events.CollectiveClientEvents;
import com.natamus.collective_common_fabric.events.CollectiveEvents;
import com.natamus.collective_common_fabric.services.Services;
import net.minecraft.class_1937;
import net.minecraft.class_3218;
import net.minecraft.class_3738;
import net.minecraft.server.MinecraftServer;

public class TaskFunctions {
   @Deprecated
   public static void enqueueCollectiveTask(MinecraftServer minecraftServer, Runnable runnable, int delay) {
      enqueueCollectiveServerTask(minecraftServer, runnable, delay);
   }

   public static void enqueueCollectiveServerTask(MinecraftServer minecraftServer, Runnable runnable, int delay) {
      CollectiveEvents.scheduledServerRunnables.add(new Pair(minecraftServer.method_3780() + delay, runnable));
   }

   public static void enqueueCollectiveClientTask(Runnable runnable, int delay) {
      if (Services.MODLOADER.isClientSide()) {
         CollectiveClientEvents.scheduledClientRunnables.add(new Pair(CollectiveClientEvents.clientTickCount + delay, runnable));
      }
   }

   public static void enqueueImmediateTask(class_1937 world, Runnable task, boolean allowClient) {
      if (world.field_9236 && allowClient) {
         task.run();
      } else {
         enqueueTask(world, task, 0);
      }
   }

   public static void enqueueTask(class_1937 world, Runnable task, int delay) {
      if (world instanceof class_3218) {
         MinecraftServer server = ((class_3218)world).method_8503();
         server.method_20493(new class_3738(server.method_3780() + delay, task));
      }
   }
}
