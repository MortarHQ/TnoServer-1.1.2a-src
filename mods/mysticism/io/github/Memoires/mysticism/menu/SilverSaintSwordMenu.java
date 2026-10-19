package io.github.Memoires.mysticism.menu;

import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SilverSaintSwordMenu extends AbstractContainerMenu {
   private final Player player;
   public final SpatialStorageContainer storageContainer;

   public SilverSaintSwordMenu(int id, Inventory inv, SpatialStorageContainer container) {
      super(null, id);
      this.player = inv.player;
      this.storageContainer = new SpatialStorageContainer(5, 128);
      container.startOpen(inv.player);
      this.addPlayerInventory(inv);
   }

   private void addPlayerInventory(Inventory playerInventory) {
      for (int i = 0; i < 3; i++) {
         for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 168 + i * 18));
         }
      }

      for (int i = 0; i < 9; i++) {
         this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 226));
      }
   }

   public boolean stillValid(Player player) {
      return player.isAlive();
   }

   public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
      return super.canTakeItemForPickAll(pStack, pSlot);
   }

   @NotNull
   public ItemStack quickMoveStack(Player player, int index) {
      Slot from = (Slot)this.slots.get(index);
      if (!from.hasItem()) {
         return ItemStack.EMPTY;
      }

      ItemStack stack = from.getItem();
      return stack.copy();
   }

   public Player getPlayer() {
      return this.player;
   }
}
