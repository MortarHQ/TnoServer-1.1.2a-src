package com.tom.storagemod.util;

import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LimitedContainer implements Container {
   private final Container delegate;
   private int sizeLimit;
   private int startOffset;

   public LimitedContainer(Container delegate, int sizeLimit) {
      this.delegate = delegate;
      this.sizeLimit = sizeLimit;
   }

   public void clearContent() {
      this.delegate.clearContent();
   }

   public int getContainerSize() {
      return Math.min(this.sizeLimit, this.delegate.getContainerSize() - this.startOffset);
   }

   public boolean isEmpty() {
      return this.delegate.isEmpty();
   }

   public ItemStack getItem(int pSlot) {
      return this.delegate.getItem(this.startOffset + pSlot);
   }

   public ItemStack removeItem(int pSlot, int pAmount) {
      return this.delegate.removeItem(this.startOffset + pSlot, pAmount);
   }

   public ItemStack removeItemNoUpdate(int pSlot) {
      return this.delegate.removeItemNoUpdate(this.startOffset + pSlot);
   }

   public void setItem(int pSlot, ItemStack pStack) {
      this.delegate.setItem(this.startOffset + pSlot, pStack);
   }

   public int getMaxStackSize() {
      return this.delegate.getMaxStackSize();
   }

   public void setChanged() {
      this.delegate.setChanged();
   }

   public boolean stillValid(Player pPlayer) {
      return this.delegate.stillValid(pPlayer);
   }

   public void startOpen(Player pPlayer) {
      this.delegate.startOpen(pPlayer);
   }

   public void stopOpen(Player pPlayer) {
      this.delegate.stopOpen(pPlayer);
   }

   public boolean canPlaceItem(int pIndex, ItemStack pStack) {
      return this.delegate.canPlaceItem(this.startOffset + pIndex, pStack);
   }

   public int countItem(Item pItem) {
      return this.delegate.countItem(pItem);
   }

   public boolean hasAnyOf(Set<Item> pSet) {
      return this.delegate.hasAnyOf(pSet);
   }

   public boolean hasAnyMatching(Predicate<ItemStack> p_216875_) {
      return this.delegate.hasAnyMatching(p_216875_);
   }

   public void setSizeLimit(int sizeLimit) {
      this.sizeLimit = sizeLimit;
   }

   public void setStartOffset(int startOffset) {
      this.startOffset = startOffset;
   }
}
