package net.p3pp3rf1y.sophisticatedcore.settings.itemdisplay;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SettingsContainerMenu;
import net.p3pp3rf1y.sophisticatedcore.renderdata.DisplaySide;
import net.p3pp3rf1y.sophisticatedcore.settings.SettingsContainerBase;

public class ItemDisplaySettingsContainer extends SettingsContainerBase<ItemDisplaySettingsCategory> {
   private static final String COLOR_TAG = "color";
   private static final String DISPLAY_SIDE_TAG = "displaySide";
   private static final String SELECT_SLOT_TAG = "selectSlot";
   private static final String UNSELECT_SLOT_TAG = "unselectSlot";
   private static final String ROTATE_CLOCKWISE_TAG = "rotateClockwise";
   private static final String ROTATE_COUNTER_CLOCKWISE_TAG = "rotateCounterClockwise";

   public ItemDisplaySettingsContainer(SettingsContainerMenu<?> settingsContainer, String categoryName, ItemDisplaySettingsCategory category) {
      super(settingsContainer, categoryName, category);
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("selectSlot")) {
         this.selectSlot(data.getInt("selectSlot"));
      } else if (data.contains("unselectSlot")) {
         this.unselectSlot(data.getInt("unselectSlot"));
      } else if (data.contains("rotateClockwise")) {
         this.rotateClockwise(data.getInt("rotateClockwise"));
      } else if (data.contains("rotateCounterClockwise")) {
         this.rotateCounterClockwise(data.getInt("rotateCounterClockwise"));
      } else if (data.contains("color")) {
         this.setColor(DyeColor.byId(data.getInt("color")));
      } else if (data.contains("displaySide")) {
         this.getCategory().setDisplaySide(DisplaySide.fromName(data.getString("displaySide")));
      }
   }

   public void unselectSlot(int slotIndex) {
      if (this.isSlotSelected(slotIndex)) {
         if (this.isServer()) {
            this.getCategory().unselectSlot(slotIndex);
         } else {
            this.getCategory().unselectSlot(slotIndex);
            this.sendIntToServer("unselectSlot", slotIndex);
         }
      }
   }

   public void selectSlot(int slotIndex) {
      if (!this.isSlotSelected(slotIndex)) {
         if (this.isServer()) {
            this.getCategory().selectSlot(slotIndex);
         } else {
            this.getCategory().selectSlot(slotIndex);
            this.sendIntToServer("selectSlot", slotIndex);
         }
      }
   }

   public void rotateClockwise(int slotIndex) {
      if (this.isServer()) {
         this.getCategory().rotate(slotIndex, true);
      } else {
         this.sendIntToServer("rotateClockwise", slotIndex);
      }
   }

   public void rotateCounterClockwise(int slotIndex) {
      if (this.isServer()) {
         this.getCategory().rotate(slotIndex, false);
      } else {
         this.sendIntToServer("rotateCounterClockwise", slotIndex);
      }
   }

   public void setColor(DyeColor color) {
      if (this.isServer()) {
         this.getCategory().setColor(color);
      } else {
         this.sendIntToServer("color", color.getId());
      }
   }

   public void setDisplaySide(DisplaySide displaySide) {
      if (this.isServer()) {
         this.getCategory().setDisplaySide(displaySide);
      } else {
         this.sendStringToServer("displaySide", displaySide.getSerializedName());
      }
   }

   public boolean isSlotSelected(int slotIndex) {
      return this.getCategory().getSlots().contains(slotIndex);
   }

   public DyeColor getColor() {
      return this.getCategory().getColor();
   }

   public int getRotation(int slotIndex) {
      return this.getCategory().getRotation(slotIndex);
   }

   public int getFirstSelectedSlot() {
      List<Integer> slots = this.getCategory().getSlots();
      return slots.isEmpty() ? -1 : slots.get(0);
   }

   public DisplaySide getDisplaySide() {
      return this.getCategory().getDisplaySide();
   }

   public boolean supportsSideSelection() {
      return this.getSettingsContainer().supportsItemDisplaySideSelection();
   }
}
