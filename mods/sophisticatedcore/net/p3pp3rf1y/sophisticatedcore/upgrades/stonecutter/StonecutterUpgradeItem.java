package net.p3pp3rf1y.sophisticatedcore.upgrades.stonecutter;

import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.blockconverter.BlockConverterUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.blockconverter.BlockConverterUpgradeWrapper;

public class StonecutterUpgradeItem extends BlockConverterUpgradeItem<StonecutterUpgradeItem, StonecutterUpgradeItem.Wrapper> {
   private static final UpgradeType<StonecutterUpgradeItem.Wrapper> TYPE = new UpgradeType<>(StonecutterUpgradeItem.Wrapper::new);

   public StonecutterUpgradeItem(IUpgradeCountLimitConfig upgradeTypeLimitConfig) {
      super(upgradeTypeLimitConfig);
   }

   @Override
   public UpgradeType<StonecutterUpgradeItem.Wrapper> getType() {
      return TYPE;
   }

   public static class Wrapper extends BlockConverterUpgradeWrapper<StonecutterUpgradeItem, StonecutterUpgradeItem.Wrapper> {
      protected Wrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
         super(storageWrapper, upgrade, upgradeSaveHandler);
      }
   }
}
