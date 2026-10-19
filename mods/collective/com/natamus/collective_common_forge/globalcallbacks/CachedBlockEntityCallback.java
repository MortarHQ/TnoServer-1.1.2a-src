package com.natamus.collective_common_forge.globalcallbacks;

import com.natamus.collective_common_forge.implementations.event.Event;
import com.natamus.collective_common_forge.implementations.event.EventFactory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

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
      void onBlockEntityAdded(Level var1, BlockEntity var2, BlockEntityType<?> var3);
   }

   @FunctionalInterface
   public interface On_Block_Entity_Removed {
      void onBlockEntityRemoved(Level var1, BlockEntity var2, BlockEntityType<?> var3);
   }
}
