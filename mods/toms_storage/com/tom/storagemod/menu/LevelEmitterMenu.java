package com.tom.storagemod.menu;

import com.tom.storagemod.Content;
import com.tom.storagemod.block.entity.LevelEmitterBlockEntity;
import com.tom.storagemod.menu.slot.PhantomSlot;
import com.tom.storagemod.util.DataSlots;
import com.tom.storagemod.util.IntDataSlots;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LevelEmitterMenu extends AbstractFilteredMenu {
   private final Container inv;
   private LevelEmitterBlockEntity te;
   public Runnable onPacket;
   public boolean lessThan;
   public int count = 1;

   public LevelEmitterMenu(int wid, Inventory pinv) {
      this(wid, pinv, null);
   }

   public LevelEmitterMenu(int wid, Inventory pinv, final LevelEmitterBlockEntity te) {
      super(Content.levelEmitterMenu.get(), wid, pinv);
      this.inv = (Container)(te == null ? new SimpleContainer(1) : new Container() {
         public void clearContent() {
         }

         public void setItem(int index, ItemStack stack) {
            te.setFilter(stack);
         }

         public ItemStack removeItemNoUpdate(int index) {
            return ItemStack.EMPTY;
         }

         public void setChanged() {
         }

         public boolean stillValid(Player player) {
            return false;
         }

         public boolean isEmpty() {
            return false;
         }

         public ItemStack getItem(int index) {
            return te.getFilter() != null ? te.getFilter().getStack() : ItemStack.EMPTY;
         }

         public int getContainerSize() {
            return 1;
         }

         public ItemStack removeItem(int index, int count) {
            return ItemStack.EMPTY;
         }
      });
      this.inv.startOpen(pinv.player);
      this.te = te;
      this.addSlot(new PhantomSlot(this.inv, 0, 43, 38));

      for (int k = 0; k < 3; k++) {
         for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(pinv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
         }
      }

      for (int l = 0; l < 9; l++) {
         this.addSlot(new Slot(pinv, l, 8 + l * 18, 142));
      }

      this.addDataSlots(IntDataSlots.create(v -> this.count = v, () -> te != null ? te.getCount() : 0).onUpdate(this::updateGui));
      this.addDataSlot(DataSlots.create(v -> this.lessThan = v > 0, () -> te != null && te.isLessThan() ? 1 : 0).onUpdate(this::updateGui));
   }

   private void updateGui() {
      if (this.onPacket != null) {
         this.onPacket.run();
      }
   }

   public boolean stillValid(Player playerIn) {
      return this.te != null ? this.te.stillValid(playerIn) : true;
   }

   @Override
   public ItemStack quickMoveStack(Player playerIn, int index) {
      Slot slot = (Slot)this.slots.get(index);
      if (slot != null && slot.hasItem() && index >= 1) {
         ItemStack is = slot.getItem().copy();
         is.setCount(1);
         Slot sl = (Slot)this.slots.get(0);
         if (!ItemStack.isSameItemSameComponents(sl.getItem(), is) && sl.getItem().isEmpty()) {
            sl.set(is);
         }
      }

      return ItemStack.EMPTY;
   }

   public void removed(Player playerIn) {
      super.removed(playerIn);
      this.inv.stopOpen(playerIn);
   }

   @Override
   public void receive(CompoundTag tag) {
      if (!this.pinv.player.isSpectator() && this.te != null) {
         super.receive(tag);
         if (tag.contains("count")) {
            int count = tag.getInt("count");
            boolean lt = tag.getBoolean("lessThan");
            this.te.setCount(count);
            this.te.setLessThan(lt);
         }
      }
   }
}
