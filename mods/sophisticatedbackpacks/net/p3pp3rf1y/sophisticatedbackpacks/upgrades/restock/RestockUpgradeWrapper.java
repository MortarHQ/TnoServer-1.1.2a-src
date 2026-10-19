package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.restock;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedbackpacks.api.IItemHandlerInteractionUpgrade;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.inventory.FilteredItemHandler;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IContentsFilteredUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

public class RestockUpgradeWrapper
   extends UpgradeWrapperBase<RestockUpgradeWrapper, RestockUpgradeItem>
   implements IContentsFilteredUpgrade,
   IItemHandlerInteractionUpgrade {
   private final ContentsFilterLogic filterLogic;

   public RestockUpgradeWrapper(IStorageWrapper backpackWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(backpackWrapper, upgrade, upgradeSaveHandler);
      this.filterLogic = new ContentsFilterLogic(
         upgrade,
         upgradeSaveHandler,
         ((RestockUpgradeItem)this.upgradeItem).getFilterSlotCount(),
         backpackWrapper::getInventoryHandler,
         (MemorySettingsCategory)backpackWrapper.getSettingsHandler().getTypeCategory(MemorySettingsCategory.class),
         ModCoreDataComponents.FILTER_ATTRIBUTES
      );
   }

   public ContentsFilterLogic getFilterLogic() {
      return this.filterLogic;
   }

   @Override
   public void onHandlerInteract(IItemHandler itemHandler, Player player) {
      AtomicInteger stacksAdded = new AtomicInteger(0);
      InventoryHelper.transfer(
         itemHandler,
         new FilteredItemHandler(this.storageWrapper.getInventoryForUpgradeProcessing(), Collections.singletonList(this.filterLogic), Collections.emptyList()),
         s -> stacksAdded.incrementAndGet()
      );
      int stacksRestocked = stacksAdded.get();
      String translKey = stacksRestocked > 0 ? "gui.sophisticatedbackpacks.status.stacks_restocked" : "gui.sophisticatedbackpacks.status.nothing_to_restock";
      player.displayClientMessage(Component.translatable(translKey, new Object[]{stacksRestocked}), true);
   }
}
