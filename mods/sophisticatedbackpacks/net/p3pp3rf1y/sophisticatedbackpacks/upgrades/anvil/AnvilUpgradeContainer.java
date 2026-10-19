package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.anvil;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition.SlotDefinition;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SlotSuppliedHandler;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class AnvilUpgradeContainer extends UpgradeContainerBase<AnvilUpgradeWrapper, AnvilUpgradeContainer> {
   private static final String DATA_SHIFT_CLICK_INTO_STORAGE = "shiftClickIntoStorage";
   private final Slot resultSlot;
   private final AnvilUpgradeContainer.PersistableAnvilMenu anvilMenuDelegate;
   private Runnable nameChangeListener = () -> {};
   private boolean processingOnTakeLogic = false;

   public AnvilUpgradeContainer(
      Player player, int upgradeContainerId, AnvilUpgradeWrapper upgradeWrapper, UpgradeContainerType<AnvilUpgradeWrapper, AnvilUpgradeContainer> type
   ) {
      super(player, upgradeContainerId, upgradeWrapper, type);
      this.anvilMenuDelegate = new AnvilUpgradeContainer.PersistableAnvilMenu(new Inventory(player));
      this.slots.add(this.anvilMenuDelegate.getSlot(0));
      this.slots.add(this.anvilMenuDelegate.getSlot(1));
      this.resultSlot = this.anvilMenuDelegate.getSlot(2);
      this.slots.add(this.resultSlot);
   }

   public void setNameChangeListener(Runnable nameChangeListener) {
      this.nameChangeListener = nameChangeListener;
   }

   public void handlePacket(CompoundTag data) {
      if (data.contains("shiftClickIntoStorage")) {
         this.setShiftClickIntoStorage(data.getBoolean("shiftClickIntoStorage"));
      } else if (data.contains("itemName")) {
         this.setItemName(data.getString("itemName"));
      }
   }

   public void setUpgradeWrapper(IUpgradeWrapper updatedUpgradeWrapper) {
      super.setUpgradeWrapper(updatedUpgradeWrapper);
      this.anvilMenuDelegate.setItemName(((AnvilUpgradeWrapper)this.upgradeWrapper).getItemName());
      this.anvilMenuDelegate.createResult();
      this.nameChangeListener.run();
   }

   public boolean shouldShiftClickIntoStorage() {
      return ((AnvilUpgradeWrapper)this.upgradeWrapper).shouldShiftClickIntoStorage();
   }

   public boolean isProcessingOnTakeLogic() {
      return this.processingOnTakeLogic;
   }

   public void setShiftClickIntoStorage(boolean shiftClickIntoStorage) {
      ((AnvilUpgradeWrapper)this.upgradeWrapper).setShiftClickIntoStorage(shiftClickIntoStorage);
      this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), "shiftClickIntoStorage", shiftClickIntoStorage));
   }

   public boolean mergeIntoStorageFirst(Slot slot) {
      return !(slot instanceof ResultSlot) || this.shouldShiftClickIntoStorage();
   }

   public boolean allowsPickupAll(Slot slot) {
      return slot != this.resultSlot;
   }

   public void setItemName(String name) {
      this.anvilMenuDelegate.setItemName(name);
      ((AnvilUpgradeWrapper)this.upgradeWrapper).setItemName(name);
      this.sendDataToServer(() -> NBTHelper.putString(new CompoundTag(), "itemName", name));
   }

   public int getCost() {
      return this.anvilMenuDelegate.getCost();
   }

   @Nullable
   public String getItemName() {
      return ((AnvilUpgradeWrapper)this.upgradeWrapper).getItemName();
   }

   private class PersistableAnvilMenu extends AnvilMenu {
      public PersistableAnvilMenu(Inventory playerInventory) {
         super(
            0,
            playerInventory,
            playerInventory.player.level().isClientSide()
               ? ContainerLevelAccess.NULL
               : ContainerLevelAccess.create(playerInventory.player.level(), playerInventory.player.blockPosition())
         );
         super.setItemName(((AnvilUpgradeWrapper)AnvilUpgradeContainer.this.upgradeWrapper).getItemName());
      }

      protected void createInputSlots(ItemCombinerMenuSlotDefinition itemCombinerMenuSlotDefinition) {
         for (final SlotDefinition slotDefinition : itemCombinerMenuSlotDefinition.getSlots()) {
            this.addSlot(
               new SlotSuppliedHandler(() -> ((AnvilUpgradeWrapper)AnvilUpgradeContainer.this.upgradeWrapper).getInventory(), slotDefinition.slotIndex(), 0, 0) {
                  public void setChanged() {
                     super.setChanged();
                     PersistableAnvilMenu.this.slotsChanged(PersistableAnvilMenu.this.inputSlots);
                     if (slotDefinition.slotIndex() == 0) {
                        if (((AnvilUpgradeWrapper)AnvilUpgradeContainer.this.upgradeWrapper).getItemName().isEmpty() != this.getItem().isEmpty()) {
                           String newItemName = this.getItem().isEmpty() ? "" : this.getItem().getHoverName().getString();
                           ((AnvilUpgradeWrapper)AnvilUpgradeContainer.this.upgradeWrapper).setItemName(newItemName);
                           PersistableAnvilMenu.this.setItemName(newItemName);
                           AnvilUpgradeContainer.this.nameChangeListener.run();
                        }

                        if (this.getItem().isEmpty()) {
                           PersistableAnvilMenu.this.setItemName("");
                           ((AnvilUpgradeWrapper)AnvilUpgradeContainer.this.upgradeWrapper).setItemName("");
                        }
                     }
                  }

                  public boolean mayPlace(ItemStack p_267156_) {
                     return slotDefinition.mayPlace().test(p_267156_);
                  }
               }
            );
         }
      }

      protected SimpleContainer createContainer(int size) {
         return new SimpleContainer(size) {
            public void setChanged() {
               super.setChanged();
               PersistableAnvilMenu.this.slotsChanged(this);
            }

            public ItemStack getItem(int pIndex) {
               return ((AnvilUpgradeWrapper)AnvilUpgradeContainer.this.upgradeWrapper).getInventory().getStackInSlot(pIndex);
            }

            public void setItem(int pIndex, ItemStack pStack) {
               ((AnvilUpgradeWrapper)AnvilUpgradeContainer.this.upgradeWrapper).getInventory().setStackInSlot(pIndex, pStack);
            }
         };
      }

      public void slotsChanged(Container pInventory) {
         this.createResult();
      }

      protected void onTake(Player player, ItemStack stack) {
         AnvilUpgradeContainer.this.processingOnTakeLogic = true;
         super.onTake(player, stack);
         AnvilUpgradeContainer.this.processingOnTakeLogic = false;
      }
   }
}
