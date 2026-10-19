package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.everlasting;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPTranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem.UpgradeConflictDefinition;

public class EverlastingUpgradeItem extends UpgradeItemBase<EverlastingUpgradeItem.Wrapper> {
   public static final UpgradeType<EverlastingUpgradeItem.Wrapper> TYPE = new UpgradeType(EverlastingUpgradeItem.Wrapper::new);
   public static final List<UpgradeConflictDefinition> UPGRADE_CONFLICT_DEFINITIONS = List.of(
      new UpgradeConflictDefinition(
         EverlastingUpgradeItem.class::isInstance, 0, SBPTranslationHelper.INSTANCE.translError("add.everlasting_exists", new Object[0])
      )
   );

   public EverlastingUpgradeItem() {
      super(Config.SERVER.maxUpgradesPerStorage);
   }

   public UpgradeType<EverlastingUpgradeItem.Wrapper> getType() {
      return TYPE;
   }

   public List<UpgradeConflictDefinition> getUpgradeConflicts() {
      return UPGRADE_CONFLICT_DEFINITIONS;
   }

   public static class Wrapper extends UpgradeWrapperBase<EverlastingUpgradeItem.Wrapper, EverlastingUpgradeItem> {
      public Wrapper(IStorageWrapper backpackWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
         super(backpackWrapper, upgrade, upgradeSaveHandler);
      }

      public boolean hideSettingsTab() {
         return true;
      }

      public boolean canBeDisabled() {
         return false;
      }
   }
}
