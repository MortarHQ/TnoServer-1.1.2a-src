package com.moepus.byepregen.compat;

import com.moepus.byepregen.mixin.accessor.ArchitecturyEventImplAccessor;
import dev.architectury.event.events.common.ChunkEvent;
import java.util.ArrayList;
import java.util.List;

public final class ArchitecturyChunkSaveCompat {
   private ArchitecturyChunkSaveCompat() {
   }

   public static boolean hasSaveDataListeners() {
      return !saveDataListenerClassNames().isEmpty();
   }

   public static List<String> saveDataListenerClassNames() {
      if (!(ChunkEvent.SAVE_DATA instanceof ArchitecturyEventImplAccessor accessor)) {
         return List.of(ChunkEvent.SAVE_DATA.getClass().getName());
      } else {
         ArrayList var5 = accessor.byepregen$listeners();
         ArrayList classNames = new ArrayList(var5.size());

         for (Object listener : var5) {
            classNames.add(className(listener));
         }

         return classNames;
      }
   }

   private static String className(Object listener) {
      return listener == null ? "null" : listener.getClass().getName();
   }
}
