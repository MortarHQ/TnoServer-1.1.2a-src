package net.p3pp3rf1y.sophisticatedcore.settings.memory;

import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SettingsContainerMenu;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsContainerBase;

public class MemorySettingsContainer extends SettingsContainerBase<MemorySettingsCategory> {
   private static final String ACTION_TAG = "action";
   private static final String SELECT_ALL_ACTION = "selectAll";
   private static final String UNSELECT_ALL_ACTION = "unselectAll";
   private static final String UNSELECT_SLOT_TAG = "unselectSlot";
   private static final String SELECT_SLOT_TAG = "selectSlot";
   private static final String IGNORE_NBT_TAG = "ignoreNbt";

   public MemorySettingsContainer(SettingsContainerMenu<?> settingsContainer, String categoryName, MemorySettingsCategory category) {
      super(settingsContainer, categoryName, category);
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("action")) {
         switch (data.getString("action")) {
            case "selectAll":
               this.selectAllSlots();
               break;
            case "unselectAll":
               this.unselectAllSlots();
         }
      } else if (data.contains("selectSlot")) {
         this.selectSlot(data.getInt("selectSlot"));
      } else if (data.contains("unselectSlot")) {
         this.unselectSlot(data.getInt("unselectSlot"));
      } else if (data.contains("ignoreNbt")) {
         this.setIgnoreNbt(data.getBoolean("ignoreNbt"));
      }
   }

   public void unselectSlot(int slotNumber) {
      if (this.isSlotSelected(slotNumber)) {
         if (this.isServer()) {
            this.getCategory().unselectSlot(slotNumber);
            this.getSettingsContainer().onMemorizedStackRemoved(slotNumber);
         } else {
            this.sendIntToServer("unselectSlot", slotNumber);
         }
      }
   }

   public void selectSlot(int slotNumber) {
      if (!this.isSlotSelected(slotNumber)) {
         if (this.isServer()) {
            this.getCategory().selectSlot(slotNumber);
            this.getSettingsContainer().onMemorizedStackAdded(slotNumber);
         } else {
            this.sendIntToServer("selectSlot", slotNumber);
         }
      }
   }

   public void unselectAllSlots() {
      if (this.isServer()) {
         Set<Integer> selectedSlots = this.getCategory().getSlotIndexes();
         this.getCategory().unselectAllSlots();
         selectedSlots.forEach(slotNumber -> this.getSettingsContainer().onMemorizedStackRemoved(slotNumber));
      } else {
         this.sendStringToServer("action", "unselectAll");
      }
   }

   public void selectAllSlots() {
      if (this.isServer()) {
         this.getCategory().selectSlots(0, this.getSettingsContainer().getNumberOfSlots());
      } else {
         this.sendStringToServer("action", "selectAll");
      }
   }

   public boolean isSlotSelected(int slotNumber) {
      return this.getCategory().isSlotSelected(slotNumber);
   }

   public boolean ignoresNbt() {
      return this.getCategory().ignoresNbt();
   }

   public void setIgnoreNbt(boolean ignoreNbt) {
      if (this.isServer()) {
         this.getCategory().setIgnoreNbt(ignoreNbt);
         this.getSettingsContainer().onMemorizedItemsChanged();
      } else {
         this.sendBooleanToServer("ignoreNbt", ignoreNbt);
      }
   }

   public ItemStack getMemorizedStack(int slotNumber) {
      return this.getCategory().getSlotFilterStack(slotNumber, false).orElse(ItemStack.EMPTY);
   }

   public ItemStack getSelectedTemplatesMemorizedStack(int slotNumber) {
      return this.getSettingsContainer()
         .getSelectedTemplatesCategory(MemorySettingsCategory.class)
         .flatMap(cat -> cat.getSlotFilterStack(slotNumber, false))
         .orElse(ItemStack.EMPTY);
   }
}
