package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.deposit;

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
import net.p3pp3rf1y.sophisticatedcore.upgrades.IFilteredUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

public class DepositUpgradeWrapper
   extends UpgradeWrapperBase<DepositUpgradeWrapper, DepositUpgradeItem>
   implements IFilteredUpgrade,
   IItemHandlerInteractionUpgrade {
   private final DepositFilterLogic filterLogic;

   public DepositUpgradeWrapper(IStorageWrapper backpackWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(backpackWrapper, upgrade, upgradeSaveHandler);
      this.filterLogic = new DepositFilterLogic(
         upgrade, upgradeSaveHandler, ((DepositUpgradeItem)this.upgradeItem).getFilterSlotCount(), ModCoreDataComponents.FILTER_ATTRIBUTES
      );
   }

   public DepositFilterLogic getFilterLogic() {
      return this.filterLogic;
   }

   @Override
   public void onHandlerInteract(IItemHandler itemHandler, Player player) {
      if (this.filterLogic.getDepositFilterType() == DepositFilterType.INVENTORY) {
         this.filterLogic.setInventory(itemHandler);
      }

      AtomicInteger stacksAdded = new AtomicInteger(0);
      InventoryHelper.transfer(
         this.storageWrapper.getInventoryForUpgradeProcessing(),
         new FilteredItemHandler(itemHandler, Collections.singletonList(this.filterLogic), Collections.emptyList()),
         s -> stacksAdded.incrementAndGet()
      );
      int stacksDeposited = stacksAdded.get();
      String translKey = stacksDeposited > 0 ? "gui.sophisticatedbackpacks.status.stacks_deposited" : "gui.sophisticatedbackpacks.status.nothing_to_deposit";
      player.displayClientMessage(Component.translatable(translKey, new Object[]{stacksDeposited}), true);
   }
}
