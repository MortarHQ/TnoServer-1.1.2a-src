package com.tom.storagemod.menu;

import com.tom.storagemod.item.IItemFilter;
import com.tom.storagemod.menu.slot.FilterSlot;
import com.tom.storagemod.menu.slot.PhantomSlot;
import com.tom.storagemod.network.NetworkHandler;
import com.tom.storagemod.util.IDataReceiver;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractFilteredMenu extends AbstractContainerMenu implements IDataReceiver {
   protected final Inventory pinv;

   protected AbstractFilteredMenu(MenuType<?> type, int wid, Inventory pinv) {
      super(type, wid);
      this.pinv = pinv;
   }

   public ItemStack quickMoveStack(Player player, int slot) {
      return ItemStack.EMPTY;
   }

   public void clicked(int slotId, int dragType, ClickType click, Player player) {
      Slot slot = slotId > -1 && slotId < this.slots.size() ? (Slot)this.slots.get(slotId) : null;
      if (slot instanceof PhantomSlot) {
         ItemStack s = this.getCarried().copy();
         if (!s.isEmpty()) {
            s.setCount(1);
         }

         slot.set(s);
      } else if (!(slot instanceof FilterSlot)) {
         super.clicked(slotId, dragType, click, player);
      } else {
         ItemStack c = this.getCarried();
         ItemStack s = slot.getItem();
         boolean cf = !c.isEmpty() && c.getItem() instanceof IItemFilter;
         boolean sf = !s.isEmpty() && s.getItem() instanceof IItemFilter;
         if ((!sf || !cf) && (!sf || !c.isEmpty())) {
            if (!sf) {
               if (cf) {
                  slot.set(ItemStack.EMPTY);
                  super.clicked(slotId, dragType, click, player);
               } else {
                  c = c.copy();
                  if (!c.isEmpty()) {
                     c.setCount(1);
                  }

                  slot.set(c);
               }
            }
         } else {
            super.clicked(slotId, dragType, click, player);
         }
      }
   }

   public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
      return !(slot instanceof PhantomSlot) && !(slot instanceof FilterSlot);
   }

   public void setPhantom(Slot slot, ItemStack ingredient) {
      CompoundTag tag = new CompoundTag();
      CompoundTag t = new CompoundTag();
      tag.put("setPhantom", t);
      t.putInt("id", slot.index);
      t.put("item", ingredient.save(this.pinv.player.registryAccess()));
      NetworkHandler.sendDataToServer(tag);
   }

   @Override
   public void receive(CompoundTag tag) {
      if (!this.pinv.player.isSpectator()) {
         if (tag.contains("setPhantom")) {
            CompoundTag t = tag.getCompound("setPhantom");
            int slotId = t.getInt("id");
            ItemStack item = ItemStack.parseOptional(this.pinv.player.registryAccess(), t.getCompound("item"));
            Slot slot = slotId > -1 && slotId < this.slots.size() ? (Slot)this.slots.get(slotId) : null;
            if (slot instanceof PhantomSlot) {
               if (!item.isEmpty()) {
                  item.setCount(1);
                  slot.set(item);
               }
            } else if (slot instanceof FilterSlot && !(item.getItem() instanceof IItemFilter)) {
               ItemStack s = slot.getItem();
               boolean sf = !s.isEmpty() && s.getItem() instanceof IItemFilter;
               if (!sf && !item.isEmpty()) {
                  item.setCount(1);
                  slot.set(item);
               }
            }
         }
      }
   }
}
