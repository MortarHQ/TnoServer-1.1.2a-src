package com.tom.storagemod.util;

import java.util.function.Predicate;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FilingCabinetContainer implements Container {
   private SimpleContainer inv;
   private Item item;
   private Runnable onChange;
   private Predicate<Player> isValidFor;

   public FilingCabinetContainer(int size, Runnable onChange, Predicate<Player> isValidFor) {
      this.inv = new SimpleContainer(size);
      this.onChange = onChange;
      this.isValidFor = isValidFor;
   }

   public void clearContent() {
      this.inv.clearContent();
   }

   public int getContainerSize() {
      return this.inv.getContainerSize();
   }

   public boolean isEmpty() {
      return this.inv.isEmpty();
   }

   public ItemStack getItem(int p_18941_) {
      return this.inv.getItem(p_18941_);
   }

   public ItemStack removeItem(int p_18942_, int p_18943_) {
      ItemStack is = this.inv.removeItem(p_18942_, p_18943_);
      if (this.inv.isEmpty()) {
         this.item = null;
      }

      return is;
   }

   public ItemStack removeItemNoUpdate(int p_18951_) {
      ItemStack is = this.inv.removeItemNoUpdate(p_18951_);
      if (this.inv.isEmpty()) {
         this.item = null;
      }

      return is;
   }

   public void setItem(int p_18944_, ItemStack stack) {
      this.inv.setItem(p_18944_, stack);
      if (!stack.isEmpty()) {
         this.item = stack.getItem();
      } else if (this.inv.isEmpty()) {
         this.item = null;
      }
   }

   public boolean stillValid(Player p_18946_) {
      return this.isValidFor.test(p_18946_);
   }

   public boolean canPlaceItem(int slot, ItemStack stack) {
      return this.item != null ? stack.getItem() == this.item : stack.getMaxStackSize() == 1;
   }

   public int getMaxStackSize() {
      return 1;
   }

   public void setChanged() {
      if (this.onChange != null) {
         this.onChange.run();
      }
   }

   public ListTag createTag(Provider reg) {
      return this.inv.createTag(reg);
   }

   public void fromTag(ListTag list, Provider reg) {
      this.inv.fromTag(list, reg);
      ItemStack is = this.inv.getItem(0);
      if (is.isEmpty()) {
         this.item = null;
      } else {
         this.item = is.getItem();
      }
   }

   public NonNullList<ItemStack> getItems() {
      return this.inv.getItems();
   }
}
