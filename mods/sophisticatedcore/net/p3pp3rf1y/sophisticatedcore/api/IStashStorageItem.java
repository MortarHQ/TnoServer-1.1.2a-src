package net.p3pp3rf1y.sophisticatedcore.api;

import java.util.Optional;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public interface IStashStorageItem {
   Optional<TooltipComponent> getInventoryTooltip(ItemStack var1);

   IStashStorageItem.StashResult getItemStashable(Provider var1, ItemStack var2, ItemStack var3);

   enum StashResult {
      MATCH_AND_SPACE,
      SPACE,
      NO_SPACE;
   }
}
