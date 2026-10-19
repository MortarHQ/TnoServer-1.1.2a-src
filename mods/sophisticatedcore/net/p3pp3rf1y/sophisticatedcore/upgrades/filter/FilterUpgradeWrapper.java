package net.p3pp3rf1y.sophisticatedcore.upgrades.filter;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.api.IIOFilterUpgrade;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IContentsFilteredUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;

public class FilterUpgradeWrapper extends UpgradeWrapperBase<FilterUpgradeWrapper, FilterUpgradeItem> implements IContentsFilteredUpgrade, IIOFilterUpgrade {
   private final ContentsFilterLogic filterLogic;

   public FilterUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      this.filterLogic = new ContentsFilterLogic(
         upgrade,
         upgradeSaveHandler,
         this.upgradeItem.getFilterSlotCount(),
         storageWrapper::getInventoryHandler,
         storageWrapper.getSettingsHandler().getTypeCategory(MemorySettingsCategory.class),
         ModCoreDataComponents.FILTER_ATTRIBUTES
      );
   }

   public void setDirection(Direction direction) {
      this.upgrade.set(ModCoreDataComponents.DIRECTION, direction);
      this.save();
      this.storageWrapper.refreshInventoryForInputOutput();
   }

   public Direction getDirection() {
      return (Direction)this.upgrade.getOrDefault(ModCoreDataComponents.DIRECTION, Direction.BOTH);
   }

   @Override
   public ContentsFilterLogic getFilterLogic() {
      return this.filterLogic;
   }

   @Override
   public void setEnabled(boolean enabled) {
      if (this.isEnabled() != enabled) {
         super.setEnabled(enabled);
         this.storageWrapper.refreshInventoryForInputOutput();
      }
   }

   @Override
   public Optional<FilterLogic> getInputFilter() {
      if (!this.isEnabled()) {
         return Optional.empty();
      }

      Direction direction = this.getDirection();
      return direction != Direction.INPUT && direction != Direction.BOTH ? Optional.empty() : Optional.of(this.getFilterLogic());
   }

   @Override
   public Optional<FilterLogic> getOutputFilter() {
      if (!this.isEnabled()) {
         return Optional.empty();
      }

      Direction direction = this.getDirection();
      return direction != Direction.OUTPUT && direction != Direction.BOTH ? Optional.empty() : Optional.of(this.getFilterLogic());
   }
}
