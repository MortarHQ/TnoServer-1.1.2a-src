package net.p3pp3rf1y.sophisticatedcore.upgrades;

import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;

public class UpgradeType<T extends IUpgradeWrapper> {
   private final UpgradeType.IFactory<T> factory;

   public UpgradeType(UpgradeType.IFactory<T> factory) {
      this.factory = factory;
   }

   public T create(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      return this.factory.create(storageWrapper, upgrade, upgradeSaveHandler);
   }

   public interface IFactory<T extends IUpgradeWrapper> {
      T create(IStorageWrapper var1, ItemStack var2, Consumer<ItemStack> var3);
   }
}
