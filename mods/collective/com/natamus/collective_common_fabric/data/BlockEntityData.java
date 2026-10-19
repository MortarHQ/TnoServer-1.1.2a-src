package com.natamus.collective_common_fabric.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_1937;
import net.minecraft.class_2586;
import net.minecraft.class_2591;

public class BlockEntityData {
   public static List<class_2591<?>> blockEntitiesToCache = new ArrayList<>();
   public static HashMap<class_2591<?>, WeakHashMap<class_1937, CopyOnWriteArrayList<class_2586>>> cachedBlockEntities = new HashMap<>();

   public static void addBlockEntityToCache(class_2591<?> blockEntityType) {
      if (!blockEntitiesToCache.contains(blockEntityType)) {
         blockEntitiesToCache.add(blockEntityType);
      }

      if (!cachedBlockEntities.containsKey(blockEntityType)) {
         cachedBlockEntities.put(blockEntityType, new WeakHashMap<>());
      }
   }
}
