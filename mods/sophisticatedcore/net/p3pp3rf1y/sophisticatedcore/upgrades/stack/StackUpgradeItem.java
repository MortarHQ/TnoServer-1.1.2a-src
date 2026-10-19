package net.p3pp3rf1y.sophisticatedcore.upgrades.stack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeSlotChangeResult;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IStackableContentsUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeGroup;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;

public class StackUpgradeItem extends UpgradeItemBase<StackUpgradeItem.Wrapper> {
   public static final UpgradeType<StackUpgradeItem.Wrapper> TYPE = new UpgradeType<>(StackUpgradeItem.Wrapper::new);
   public static final UpgradeGroup UPGRADE_GROUP = new UpgradeGroup("stack_upgrades", TranslationHelper.INSTANCE.translUpgradeGroup("stack_upgrades"));
   private final double stackSizeMultiplier;

   public StackUpgradeItem(double stackSizeMultiplier, IUpgradeCountLimitConfig upgradeTypeLimitConfig) {
      super(upgradeTypeLimitConfig);
      this.stackSizeMultiplier = stackSizeMultiplier;
   }

   public static int getInventorySlotLimit(IStorageWrapper storageWrapper) {
      return getInventorySlotLimit(storageWrapper, 1.0);
   }

   private static int getInventorySlotLimit(IStorageWrapper storageWrapper, double skipMultiplier) {
      double multiplier = storageWrapper.getBaseStackSizeMultiplier();
      boolean multiplierSkipped = false;

      for (StackUpgradeItem.Wrapper stackWrapper : storageWrapper.getUpgradeHandler().getTypeWrappers(TYPE)) {
         if (!multiplierSkipped && stackWrapper.getStackSizeMultiplier() == skipMultiplier) {
            multiplierSkipped = true;
         } else {
            if (2.147483647E9 / stackWrapper.getStackSizeMultiplier() < multiplier) {
               return Integer.MAX_VALUE;
            }

            multiplier *= stackWrapper.getStackSizeMultiplier();
         }
      }

      return 3.3554431984375E7 < multiplier ? Integer.MAX_VALUE : (int)(multiplier * 64.0);
   }

   @Override
   public UpgradeType<StackUpgradeItem.Wrapper> getType() {
      return TYPE;
   }

   @Override
   public List<IUpgradeItem.UpgradeConflictDefinition> getUpgradeConflicts() {
      return List.of();
   }

   public double getStackSizeMultiplier() {
      return this.stackSizeMultiplier;
   }

   @Override
   public UpgradeSlotChangeResult canRemoveUpgradeFrom(IStorageWrapper storageWrapper, boolean isClientSide) {
      if (isClientSide) {
         return UpgradeSlotChangeResult.success();
      }

      double multiplierWhenRemoved = getInventorySlotLimit(storageWrapper, this.stackSizeMultiplier) / 64.0;
      return this.isMultiplierHighEnough(storageWrapper, multiplierWhenRemoved, -1);
   }

   @Override
   public UpgradeSlotChangeResult checkExtraInsertConditions(
      ItemStack upgradeStack, IStorageWrapper storageWrapper, boolean isClientSide, int upgradeSlot, @Nullable IUpgradeItem<?> upgradeInSlot
   ) {
      if (isClientSide) {
         return UpgradeSlotChangeResult.success();
      }

      double multiplierWhenAdded = getInventorySlotLimit(storageWrapper) / 64.0 * this.stackSizeMultiplier;
      UpgradeSlotChangeResult result = this.isMultiplierHighEnough(storageWrapper, multiplierWhenAdded, upgradeSlot);
      return !result.successful() ? result : super.checkExtraInsertConditions(upgradeStack, storageWrapper, isClientSide, upgradeInSlot);
   }

   @Override
   public UpgradeSlotChangeResult canSwapUpgradeFor(ItemStack upgradeStackToPut, int upgradeSlot, IStorageWrapper storageWrapper, boolean isClientSide) {
      if (isClientSide) {
         return UpgradeSlotChangeResult.success();
      }

      UpgradeSlotChangeResult result = super.canSwapUpgradeFor(upgradeStackToPut, upgradeSlot, storageWrapper, isClientSide);
      if (!result.successful()) {
         return result;
      }

      if (isClientSide) {
         return UpgradeSlotChangeResult.success();
      }

      if (upgradeStackToPut.getItem() instanceof StackUpgradeItem otherStackUpgradeItem) {
         if (otherStackUpgradeItem.stackSizeMultiplier >= this.stackSizeMultiplier) {
            return UpgradeSlotChangeResult.success();
         }

         double multiplierWhenRemoved = getInventorySlotLimit(storageWrapper, this.stackSizeMultiplier) / 64.0;
         return this.isMultiplierHighEnough(storageWrapper, multiplierWhenRemoved * otherStackUpgradeItem.stackSizeMultiplier, -1);
      } else {
         return this.canRemoveUpgradeFrom(storageWrapper, isClientSide);
      }
   }

   private UpgradeSlotChangeResult isMultiplierHighEnough(IStorageWrapper storageWrapper, double multiplier, int ignoreUpgradeSlot) {
      Set<Integer> slotsOverMultiplier = new HashSet<>();

      for (int slot = 0; slot < storageWrapper.getInventoryHandler().getSlots(); slot++) {
         ItemStack stack = storageWrapper.getInventoryHandler().getSlotStack(slot);
         if (stack.getCount() > 1) {
            double stackMultiplierNeeded = (double)stack.getCount() / stack.getMaxStackSize();
            if (stackMultiplierNeeded > multiplier) {
               slotsOverMultiplier.add(slot);
            }
         }
      }

      Set<Integer> errorUpgradeSlots = new HashSet<>();
      Set<Integer> errorInventoryParts = new HashSet<>();

      for (Entry<Integer, IUpgradeWrapper> entry : storageWrapper.getUpgradeHandler().getSlotWrappers().entrySet()) {
         Integer slot = entry.getKey();
         IUpgradeWrapper wrapper = entry.getValue();
         if (slot != ignoreUpgradeSlot
            && wrapper instanceof IStackableContentsUpgrade stackableContentsUpgrade
            && stackableContentsUpgrade.getMinimumMultiplierRequired() > multiplier) {
            errorUpgradeSlots.add(slot);
            errorInventoryParts.add(slot);
         }
      }

      return slotsOverMultiplier.isEmpty() && errorInventoryParts.isEmpty()
         ? UpgradeSlotChangeResult.success()
         : UpgradeSlotChangeResult.fail(
            TranslationHelper.INSTANCE.translError("remove.stack_low_multiplier", multiplier), errorUpgradeSlots, slotsOverMultiplier, errorInventoryParts
         );
   }

   @Override
   public UpgradeGroup getUpgradeGroup() {
      return UPGRADE_GROUP;
   }

   public static class Wrapper extends UpgradeWrapperBase<StackUpgradeItem.Wrapper, StackUpgradeItem> {
      protected Wrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
         super(storageWrapper, upgrade, upgradeSaveHandler);
      }

      public double getStackSizeMultiplier() {
         return this.upgradeItem.getStackSizeMultiplier();
      }

      @Override
      public boolean hideSettingsTab() {
         return true;
      }

      @Override
      public boolean canBeDisabled() {
         return false;
      }
   }
}
