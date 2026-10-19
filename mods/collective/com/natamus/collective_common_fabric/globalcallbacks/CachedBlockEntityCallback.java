package com.natamus.collective_common_fabric.globalcallbacks;

import com.natamus.collective_common_fabric.implementations.event.Event;
import com.natamus.collective_common_fabric.implementations.event.EventFactory;
import net.minecraft.class_1937;
import net.minecraft.class_2586;
import net.minecraft.class_2591;

public class CachedBlockEntityCallback {
   public static final Event<CachedBlockEntityCallback.On_Block_Entity_Added> BLOCK_ENTITY_ADDED = EventFactory.createArrayBacked(
      CachedBlockEntityCallback.On_Block_Entity_Added.class, callbacks -> (level, blockEntity, blockEntityType) -> {
         for (CachedBlockEntityCallback.On_Block_Entity_Added callback : callbacks) {
            callback.onBlockEntityAdded(level, blockEntity, blockEntityType);
         }
      }
   );
   public static final Event<CachedBlockEntityCallback.On_Block_Entity_Removed> BLOCK_ENTITY_REMOVED = EventFactory.createArrayBacked(
      CachedBlockEntityCallback.On_Block_Entity_Removed.class, callbacks -> (level, blockEntity, blockEntityType) -> {
         for (CachedBlockEntityCallback.On_Block_Entity_Removed callback : callbacks) {
            callback.onBlockEntityRemoved(level, blockEntity, blockEntityType);
         }
      }
   );

   private CachedBlockEntityCallback() {
   }

   @FunctionalInterface
   public interface On_Block_Entity_Added {
      void onBlockEntityAdded(class_1937 var1, class_2586 var2, class_2591<?> var3);
   }

   @FunctionalInterface
   public interface On_Block_Entity_Removed {
      void onBlockEntityRemoved(class_1937 var1, class_2586 var2, class_2591<?> var3);
   }
}
