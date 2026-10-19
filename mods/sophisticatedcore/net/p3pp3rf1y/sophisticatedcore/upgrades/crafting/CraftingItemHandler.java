package net.p3pp3rf1y.sophisticatedcore.upgrades.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

public class CraftingItemHandler extends TransientCraftingContainer {
   private final Supplier<IItemHandlerModifiable> supplyInventory;
   private final Consumer<Container> onCraftingMatrixChanged;
   private boolean itemsInitialized = false;
   private List<ItemStack> items = List.of();

   public CraftingItemHandler(Supplier<IItemHandlerModifiable> supplyInventory, Consumer<Container> onCraftingMatrixChanged) {
      super(new AbstractContainerMenu(null, -1) {
         public ItemStack quickMoveStack(Player player, int index) {
            return ItemStack.EMPTY;
         }

         public boolean stillValid(Player playerIn) {
            return false;
         }
      }, 3, 3);
      this.supplyInventory = supplyInventory;
      this.onCraftingMatrixChanged = onCraftingMatrixChanged;
   }

   public int getContainerSize() {
      return this.supplyInventory.get().getSlots();
   }

   public boolean isEmpty() {
      return InventoryHelper.isEmpty((IItemHandler)this.supplyInventory.get());
   }

   public ItemStack getItem(int index) {
      IItemHandlerModifiable itemHandler = this.supplyInventory.get();
      return index >= itemHandler.getSlots() ? ItemStack.EMPTY : itemHandler.getStackInSlot(index);
   }

   public List<ItemStack> getItems() {
      if (!this.itemsInitialized) {
         this.items = new ArrayList<>();

         for (int slot = 0; slot < this.supplyInventory.get().getSlots(); slot++) {
            this.items.add(this.supplyInventory.get().getStackInSlot(slot));
         }

         this.itemsInitialized = true;
      }

      return this.items;
   }

   public ItemStack removeItemNoUpdate(int index) {
      return InventoryHelper.getAndRemove((IItemHandler)this.supplyInventory.get(), index);
   }

   public ItemStack removeItem(int index, int count) {
      ItemStack itemstack = this.supplyInventory.get().extractItem(index, count, false);
      if (!itemstack.isEmpty()) {
         this.itemsInitialized = false;
         this.onCraftingMatrixChanged.accept(this);
      }

      return itemstack;
   }

   public void setItem(int index, ItemStack stack) {
      this.supplyInventory.get().setStackInSlot(index, stack);
      this.onCraftingMatrixChanged.accept(this);
      this.itemsInitialized = false;
   }

   public void setChanged() {
      super.setChanged();
      this.itemsInitialized = false;
   }

   public void fillStackedContents(StackedContents helper) {
      InventoryHelper.iterate((IItemHandler)this.supplyInventory.get(), (slot, stack) -> helper.accountSimpleStack(stack));
   }
}
