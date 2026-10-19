package com.sbjeiindex.upgrade;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeGroup;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem.UpgradeConflictDefinition;

public class JEIIndexUpgradeItem extends UpgradeItemBase<JEIIndexUpgradeItem.Wrapper> {
   public static final UpgradeType<JEIIndexUpgradeItem.Wrapper> TYPE = new UpgradeType(JEIIndexUpgradeItem.Wrapper::new);

   public JEIIndexUpgradeItem() {
      super(new IUpgradeCountLimitConfig() {
         public int getMaxUpgradesInGroupPerStorage(String storageId, UpgradeGroup group) {
            return 1;
         }

         public int getMaxUpgradesPerStorage(String storageId, ResourceLocation upgradeId) {
            return 1;
         }
      });
   }

   public UpgradeType<JEIIndexUpgradeItem.Wrapper> getType() {
      return TYPE;
   }

   public List<UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }

   public static class Wrapper extends UpgradeWrapperBase<JEIIndexUpgradeItem.Wrapper, JEIIndexUpgradeItem> {
      public Wrapper(IStorageWrapper backpackWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
         super(backpackWrapper, upgrade, upgradeSaveHandler);
      }

      public boolean hideSettingsTab() {
         return true;
      }
   }
}
