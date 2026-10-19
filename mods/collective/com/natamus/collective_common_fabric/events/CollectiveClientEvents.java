package com.natamus.collective_common_fabric.events;

import com.mojang.datafixers.util.Pair;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_310;
import net.minecraft.class_3738;

public class CollectiveClientEvents {
   public static CopyOnWriteArrayList<Pair<Integer, Runnable>> scheduledClientRunnables = new CopyOnWriteArrayList<>();
   public static int clientTickCount = 0;

   public static void onClientTick() {
      for (Pair<Integer, Runnable> pair : scheduledClientRunnables) {
         if ((Integer)pair.getFirst() <= clientTickCount) {
            class_310.method_1551().execute(new class_3738(clientTickCount, (Runnable)pair.getSecond()));
            scheduledClientRunnables.remove(pair);
         }
      }

      clientTickCount++;
   }
}
