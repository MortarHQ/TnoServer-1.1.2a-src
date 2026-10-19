package com.tom.storagemod.util;

import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

public class CraftingMatrix implements CraftingContainer {
   private final NonNullList<ItemStack> items;
   private final int width;
   private final int height;
   private final Runnable onChanged;

   public CraftingMatrix(int i, int j, Runnable onChanged) {
      this(i, j, onChanged, NonNullList.withSize(i * j, ItemStack.EMPTY));
   }

   public CraftingMatrix(int i, int j, Runnable onChanged, NonNullList<ItemStack> nonNullList) {
      this.items = nonNullList;
      this.width = i;
      this.height = j;
      this.onChanged = onChanged;
   }

   public int getContainerSize() {
      return this.items.size();
   }

   public boolean isEmpty() {
      for (ItemStack itemStack : this.items) {
         if (!itemStack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack getItem(int i) {
      return i >= this.getContainerSize() ? ItemStack.EMPTY : (ItemStack)this.items.get(i);
   }

   public ItemStack removeItemNoUpdate(int i) {
      return ContainerHelper.takeItem(this.items, i);
   }

   public ItemStack removeItem(int i, int j) {
      ItemStack itemStack = ContainerHelper.removeItem(this.items, i, j);
      if (!itemStack.isEmpty()) {
         this.setChanged();
      }

      return itemStack;
   }

   public void setItem(int i, ItemStack itemStack) {
      this.items.set(i, itemStack);
      this.setChanged();
   }

   public void setChanged() {
      this.onChanged.run();
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public void clearContent() {
      this.items.clear();
   }

   public int getHeight() {
      return this.height;
   }

   public int getWidth() {
      return this.width;
   }

   public List<ItemStack> getItems() {
      return List.copyOf(this.items);
   }

   public void fillStackedContents(StackedContents stackedContents) {
      for (ItemStack itemStack : this.items) {
         stackedContents.accountSimpleStack(itemStack);
      }
   }
}
