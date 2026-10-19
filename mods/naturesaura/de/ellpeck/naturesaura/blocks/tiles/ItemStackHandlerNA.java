package de.ellpeck.naturesaura.blocks.tiles;

import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ItemStackHandlerNA extends ItemStackHandler {
   private final BlockEntityImpl tile;
   private final boolean sendToClients;

   public ItemStackHandlerNA(int size) {
      this(size, null, false);
   }

   public ItemStackHandlerNA(int size, BlockEntityImpl tile, boolean sendToClients) {
      super(size);
      this.tile = tile;
      this.sendToClients = sendToClients;
   }

   protected void onContentsChanged(int slot) {
      if (this.tile != null) {
         this.tile.setChanged();
         if (this.sendToClients && !this.tile.getLevel().isClientSide) {
            this.tile.sendToClients();
         }
      }
   }

   protected boolean canInsert(ItemStack stack, int slot) {
      return true;
   }

   protected boolean canExtract(ItemStack stack, int slot, int amount) {
      return true;
   }

   public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
      return this.canInsert(stack, slot);
   }

   @Nonnull
   public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
      return this.canInsert(stack, slot) ? super.insertItem(slot, stack, simulate) : stack;
   }

   @Nonnull
   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      return this.canExtract(this.getStackInSlot(slot), slot, amount) ? super.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
   }
}
