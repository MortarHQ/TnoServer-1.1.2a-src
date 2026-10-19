package net.caffeinemc.mods.lithium.common.block.entity.inventory_change_tracking;

import net.minecraft.world.Container;

public interface InventoryChangeListener {
   default void handleStackListReplaced(Container inventory) {
      this.lithium$handleInventoryRemoved(inventory);
   }

   void lithium$handleInventoryContentModified(Container var1);

   void lithium$handleInventoryRemoved(Container var1);

   boolean lithium$handleComparatorAdded(Container var1);
}
