package net.p3pp3rf1y.sophisticatedcore.upgrades;

import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;

public abstract class UpgradeWrapperBase<W extends IUpgradeWrapper, T extends UpgradeItemBase<W>> implements IUpgradeWrapper {
   protected final IStorageWrapper storageWrapper;
   protected final Consumer<ItemStack> upgradeSaveHandler;
   protected final ItemStack upgrade;
   protected final T upgradeItem;
   private long cooldown = 0L;

   protected UpgradeWrapperBase(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
      this.storageWrapper = storageWrapper;
      this.upgrade = upgrade;
      this.upgradeItem = (T)upgrade.getItem();
      this.upgradeSaveHandler = upgradeSaveHandler;
   }

   @Override
   public ItemStack getUpgradeStack() {
      return this.upgrade;
   }

   protected void save() {
      this.upgradeSaveHandler.accept(this.upgrade);
   }

   protected void setCooldown(Level level, int time) {
      this.cooldown = level.getGameTime() + time;
   }

   public long getCooldownTime() {
      return this.cooldown;
   }

   public boolean isInCooldown(Level level) {
      return this.getCooldownTime() > level.getGameTime();
   }

   @Override
   public boolean isEnabled() {
      return (Boolean)this.upgrade.getOrDefault(ModCoreDataComponents.ENABLED, true);
   }

   @Override
   public void setEnabled(boolean enabled) {
      this.upgrade.set(ModCoreDataComponents.ENABLED, enabled);
      this.save();
      this.storageWrapper.getUpgradeHandler().refreshWrappersThatImplementAndTypeWrappers();
   }
}
