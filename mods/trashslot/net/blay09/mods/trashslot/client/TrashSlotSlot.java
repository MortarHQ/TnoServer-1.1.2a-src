package net.blay09.mods.trashslot.client;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class TrashSlotSlot extends Slot {
   private final Pair<ResourceLocation, ResourceLocation> backgroundPair = Pair.of(
      InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath("trashslot", "item/trashcan")
   );

   public TrashSlotSlot() {
      super(new TrashSlotSlot.TrashInventory(), 0, 0, 0);
   }

   @Nullable
   public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
      return this.backgroundPair;
   }

   public static class TrashInventory implements Container {
      private ItemStack currentStack = ItemStack.EMPTY;

      public int getContainerSize() {
         return 1;
      }

      public boolean isEmpty() {
         return this.currentStack.isEmpty();
      }

      public ItemStack getItem(int index) {
         return this.currentStack;
      }

      public ItemStack removeItem(int index, int count) {
         ItemStack itemStack = !this.currentStack.isEmpty() && count > 0 ? this.currentStack.split(count) : ItemStack.EMPTY;
         if (!itemStack.isEmpty()) {
            this.setChanged();
         }

         return itemStack;
      }

      public ItemStack removeItemNoUpdate(int index) {
         ItemStack itemStack = this.currentStack;
         this.currentStack = ItemStack.EMPTY;
         return itemStack;
      }

      public void setItem(int index, ItemStack stack) {
         this.currentStack = stack;
      }

      public int getMaxStackSize() {
         return 64;
      }

      public void setChanged() {
      }

      public boolean stillValid(Player player) {
         return true;
      }

      public void startOpen(Player player) {
      }

      public void stopOpen(Player player) {
      }

      public boolean canPlaceItem(int index, ItemStack stack) {
         return true;
      }

      public void clearContent() {
         this.currentStack = ItemStack.EMPTY;
      }
   }
}
