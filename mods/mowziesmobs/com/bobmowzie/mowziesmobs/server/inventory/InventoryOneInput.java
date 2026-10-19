package com.bobmowzie.mowziesmobs.server.inventory;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class InventoryOneInput implements Container {
   protected final MowzieEntity tradingEntity;
   protected ItemStack input = ItemStack.EMPTY;
   protected List<InventoryOneInput.ChangeListener> listeners;

   public InventoryOneInput(MowzieEntity tradingEntity) {
      this.tradingEntity = tradingEntity;
   }

   public void addListener(InventoryOneInput.ChangeListener listener) {
      if (this.listeners == null) {
         this.listeners = new ArrayList<>();
      }

      this.listeners.add(listener);
   }

   public int getContainerSize() {
      return 1;
   }

   public void startOpen(Player player) {
   }

   public void stopOpen(Player player) {
   }

   public ItemStack getItem(int index) {
      return index == 0 ? this.input : ItemStack.EMPTY;
   }

   public ItemStack removeItem(int index, int count) {
      ItemStack stack;
      if (index == 0 && this.input != ItemStack.EMPTY && count > 0) {
         ItemStack split = this.input.split(count);
         if (this.input.getCount() == 0) {
            this.input = ItemStack.EMPTY;
         }

         stack = split;
         this.setChanged();
      } else {
         stack = ItemStack.EMPTY;
      }

      return stack;
   }

   public ItemStack removeItemNoUpdate(int index) {
      if (index != 0) {
         return ItemStack.EMPTY;
      }

      ItemStack s = this.input;
      this.input = ItemStack.EMPTY;
      this.setChanged();
      return s;
   }

   public void setItem(int index, ItemStack stack) {
      if (index == 0) {
         this.input = stack;
         if (stack != ItemStack.EMPTY && stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
         }

         this.setChanged();
      }
   }

   public boolean canPlaceItem(int index, ItemStack stack) {
      return true;
   }

   public void clearContent() {
      this.input = ItemStack.EMPTY;
      this.setChanged();
   }

   public boolean isEmpty() {
      return !this.input.isEmpty();
   }

   public int getMaxStackSize() {
      return 64;
   }

   public void setChanged() {
      if (this.listeners != null) {
         for (InventoryOneInput.ChangeListener listener : this.listeners) {
            listener.onChange(this);
         }
      }
   }

   public interface ChangeListener {
      void onChange(Container var1);
   }
}
