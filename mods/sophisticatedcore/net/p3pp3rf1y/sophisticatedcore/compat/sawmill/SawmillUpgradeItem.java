package net.p3pp3rf1y.sophisticatedcore.compat.sawmill;

import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.blockconverter.BlockConverterUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.blockconverter.BlockConverterUpgradeWrapper;

public class SawmillUpgradeItem extends BlockConverterUpgradeItem<SawmillUpgradeItem, SawmillUpgradeItem.Wrapper> {
   private static final UpgradeType<SawmillUpgradeItem.Wrapper> TYPE = new UpgradeType<>(SawmillUpgradeItem.Wrapper::new);

   public SawmillUpgradeItem(IUpgradeCountLimitConfig upgradeTypeLimitConfig) {
      super(upgradeTypeLimitConfig);
   }

   @Override
   public UpgradeType<SawmillUpgradeItem.Wrapper> getType() {
      return TYPE;
   }

   public static class Wrapper extends BlockConverterUpgradeWrapper<SawmillUpgradeItem, SawmillUpgradeItem.Wrapper> {
      protected Wrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
         super(storageWrapper, upgrade, upgradeSaveHandler);
      }
   }
}
