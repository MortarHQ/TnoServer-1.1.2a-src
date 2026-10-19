package com.natamus.collective_common_forge.events;

import com.mojang.datafixers.util.Pair;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.server.TickTask;

public class CollectiveClientEvents {
   public static CopyOnWriteArrayList<Pair<Integer, Runnable>> scheduledClientRunnables = new CopyOnWriteArrayList<>();
   public static int clientTickCount = 0;

   public static void onClientTick() {
      for (Pair<Integer, Runnable> pair : scheduledClientRunnables) {
         if ((Integer)pair.getFirst() <= clientTickCount) {
            Minecraft.getInstance().execute(new TickTask(clientTickCount, (Runnable)pair.getSecond()));
            scheduledClientRunnables.remove(pair);
         }
      }

      clientTickCount++;
   }
}
