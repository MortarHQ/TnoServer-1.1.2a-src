package com.tom.storagemod.menu;

import com.tom.storagemod.Content;
import com.tom.storagemod.inventory.filter.SimpleItemFilter;
import com.tom.storagemod.menu.slot.PhantomSlot;
import com.tom.storagemod.util.DataSlots;
import java.util.function.BooleanSupplier;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ItemFilterMenu extends AbstractFilteredMenu {
   private final Container dispenserInventory;
   private SimpleItemFilter filter;
   public boolean matchNBT;
   public boolean allowList;
   private BooleanSupplier isValid;
   private Runnable refresh;

   public ItemFilterMenu(int wid, Inventory pinv) {
      this(wid, pinv, new SimpleContainer(9));
      this.addDataSlot(DataSlots.set(v -> this.matchNBT = v == 1));
      this.addDataSlot(DataSlots.set(v -> this.allowList = v == 1));
      this.isValid = () -> true;
   }

   public ItemFilterMenu(int wid, Inventory pinv, SimpleItemFilter filter, BooleanSupplier isValid, Runnable refresh) {
      this(wid, pinv, filter.getContainer());
      this.filter = filter;
      this.isValid = isValid;
      this.refresh = refresh;
      this.addDataSlot(DataSlots.get(() -> filter.isMatchNBT() ? 1 : 0));
      this.addDataSlot(DataSlots.get(() -> filter.isAllowList() ? 1 : 0));
   }

   private ItemFilterMenu(int wid, Inventory pinv, Container inv) {
      super(Content.itemFilterMenu.get(), wid, pinv);
      checkContainerSize(inv, 9);
      this.dispenserInventory = inv;
      inv.startOpen(pinv.player);

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            this.addSlot(new PhantomSlot(inv, j + i * 3, 62 + j * 18, 17 + i * 18));
         }
      }

      for (int k = 0; k < 3; k++) {
         for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(pinv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
         }
      }

      for (int l = 0; l < 9; l++) {
         this.addSlot(new Slot(pinv, l, 8 + l * 18, 142));
      }
   }

   public boolean clickMenuButton(Player p_38875_, int btn) {
      boolean v = (btn & 1) != 0;
      btn >>= 1;
      if (btn == 0) {
         this.filter.setMatchNBT(v);
      } else if (btn == 1) {
         this.filter.setAllowList(v);
      }

      return false;
   }

   public boolean stillValid(Player playerIn) {
      return this.isValid.getAsBoolean() && this.dispenserInventory.stillValid(playerIn);
   }

   @Override
   public ItemStack quickMoveStack(Player playerIn, int index) {
      Slot slot = (Slot)this.slots.get(index);
      if (slot != null && slot.hasItem() && index >= 9) {
         ItemStack is = slot.getItem().copy();
         is.setCount(1);

         for (int i = 0; i < 9; i++) {
            Slot sl = (Slot)this.slots.get(i);
            if (ItemStack.isSameItemSameComponents(sl.getItem(), is)) {
               break;
            }

            if (sl.getItem().isEmpty()) {
               sl.set(is);
               break;
            }
         }
      }

      return ItemStack.EMPTY;
   }

   public void removed(Player playerIn) {
      super.removed(playerIn);
      this.dispenserInventory.stopOpen(playerIn);
      if (this.filter != null) {
         this.filter.flush();
      }

      if (this.refresh != null) {
         this.refresh.run();
      }
   }
}
