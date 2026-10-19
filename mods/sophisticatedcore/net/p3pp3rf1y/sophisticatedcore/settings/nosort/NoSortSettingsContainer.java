package net.p3pp3rf1y.sophisticatedcore.settings.nosort;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SettingsContainerMenu;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsContainerBase;

public class NoSortSettingsContainer extends SettingsContainerBase<NoSortSettingsCategory> {
   private static final String ACTION_TAG = "action";
   private static final String SELECT_ALL_ACTION = "selectAll";
   private static final String UNSELECT_ALL_ACTION = "unselectAll";
   private static final String UNSELECT_SLOT_TAG = "unselectSlot";
   private static final String SELECT_SLOT_TAG = "selectSlot";
   private static final String COLOR_TAG = "color";

   public NoSortSettingsContainer(SettingsContainerMenu<?> settingsContainer, String categoryName, NoSortSettingsCategory category) {
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
      } else if (data.contains("color")) {
         this.setColor(DyeColor.byId(data.getInt("color")));
      }
   }

   public void unselectSlot(int slotNumber) {
      if (this.isSlotSelected(slotNumber)) {
         if (this.isServer()) {
            this.getCategory().unselectSlot(slotNumber);
         } else {
            this.sendIntToServer("unselectSlot", slotNumber);
         }
      }
   }

   public void selectSlot(int slotNumber) {
      if (!this.isSlotSelected(slotNumber)) {
         if (this.isServer()) {
            this.getCategory().selectSlot(slotNumber);
         } else {
            this.sendIntToServer("selectSlot", slotNumber);
         }
      }
   }

   public void unselectAllSlots() {
      if (this.isServer()) {
         this.getCategory().unselectAllSlots();
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

   public void setColor(DyeColor color) {
      if (this.isServer()) {
         this.getCategory().setColor(color);
      } else {
         this.sendIntToServer("color", color.getId());
      }
   }

   public boolean isSlotSelected(int slotNumber) {
      return this.getCategory().isSlotSelected(slotNumber);
   }

   public DyeColor getColor() {
      return this.getCategory().getColor();
   }
}
