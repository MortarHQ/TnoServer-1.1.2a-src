package cn.leolezury.eternalstarlight.common.item.menu;

import cn.leolezury.eternalstarlight.common.block.entity.AlloyFurnaceBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESMenuTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AlloyFurnaceMenu extends AbstractContainerMenu {
   public static final int INGREDIENT_SLOT_START = 0;
   public static final int INGREDIENT_SLOT_END = 9;
   public static final int FUEL_SLOT = 9;
   public static final int COOLING_SLOT = 10;
   public static final int RESULT_SLOT_START = 11;
   public static final int RESULT_SLOT_END = 14;
   public static final int INV_SLOT_START = 14;
   public static final int INV_SLOT_END = 41;
   public static final int USE_ROW_SLOT_START = 41;
   public static final int USE_ROW_SLOT_END = 50;
   private final Container container;
   private final ContainerData data;

   public AlloyFurnaceMenu(int containerId, Inventory playerInventory) {
      this(containerId, playerInventory, new SimpleContainer(14), new SimpleContainerData(7));
   }

   public AlloyFurnaceMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
      super(ESMenuTypes.ALLOY_FURNACE.get(), containerId);
      this.container = container;
      this.data = data;
      checkContainerSize(container, 14);
      checkContainerDataCount(data, 7);

      for (int y = 0; y < 3; y++) {
         for (int x = 0; x < 3; x++) {
            this.addSlot(new Slot(this.container, x + y * 3, 30 + x * 18, 17 + y * 18));
         }
      }

      this.addSlot(new AlloyFurnaceFuelSlot(this.container, 9, 10, 53));
      this.addSlot(new AlloyFurnaceCoolingSlot(this.container, 10, 86, 53));
      this.addSlot(new AlloyResultSlot(playerInventory.player, this.container, 11, 124, 18));
      this.addSlot(new AlloyResultSlot(playerInventory.player, this.container, 12, 115, 53));
      this.addSlot(new AlloyResultSlot(playerInventory.player, this.container, 13, 133, 53));

      for (int y = 0; y < 3; y++) {
         for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
         }
      }

      for (int i = 0; i < 9; i++) {
         this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
      }

      this.addDataSlots(data);
   }

   public boolean stillValid(Player player) {
      return this.container.stillValid(player);
   }

   public float getLitProgress() {
      int lit = this.data.get(0);
      int total = this.data.get(1);
      return total != 0 && lit != 0 ? Mth.clamp((float)lit / total, 0.0F, 1.0F) : 0.0F;
   }

   public float getBurnProgress() {
      int burn = this.data.get(2);
      int total = this.data.get(3);
      return total != 0 && burn != 0 ? Mth.clamp((float)burn / total, 0.0F, 1.0F) : 0.0F;
   }

   public float getOverheatProgress() {
      int overheat = this.data.get(4);
      return Mth.clamp((float)overheat / AlloyFurnaceBlockEntity.getTotalOverheatTicks(), 0.0F, 1.0F);
   }

   public float getCoolingProgress() {
      int cooling = this.data.get(5);
      int total = this.data.get(6);
      return total != 0 && cooling != 0 ? Mth.clamp((float)cooling / total, 0.0F, 1.0F) : 0.0F;
   }

   public ItemStack quickMoveStack(Player player, int index) {
      ItemStack stack = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(index);
      if (slot != null && slot.hasItem()) {
         ItemStack slotItem = slot.getItem();
         stack = slotItem.copy();
         if (index >= 11 && index < 14) {
            if (!this.moveItemStackTo(slotItem, 14, 50, true)) {
               return ItemStack.EMPTY;
            }

            slot.onQuickCraft(slotItem, stack);
         } else if (index >= 14 && index < 50) {
            if ((!AlloyFurnaceBlockEntity.isFuel(slotItem) || !this.moveItemStackTo(slotItem, 9, 10, false))
               && (!AlloyFurnaceBlockEntity.isCoolingItem(slotItem) || !this.moveItemStackTo(slotItem, 10, 11, false))
               && !this.moveItemStackTo(slotItem, 0, 9, false)) {
               if (index < 41) {
                  if (!this.moveItemStackTo(slotItem, 41, 50, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (!this.moveItemStackTo(slotItem, 14, 41, false)) {
                  return ItemStack.EMPTY;
               }
            }
         } else if (!this.moveItemStackTo(slotItem, 14, 50, false)) {
            return ItemStack.EMPTY;
         }

         if (slotItem.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (slotItem.getCount() == stack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(player, slotItem);
      }

      return stack;
   }
}
