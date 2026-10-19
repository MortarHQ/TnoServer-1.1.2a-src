package bigchadguys.sellingbin.screen.handler;

import bigchadguys.sellingbin.block.entity.SellingBinBlockEntity;
import bigchadguys.sellingbin.init.ModScreenHandlers;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class SellingBinScreenHandler extends AbstractContainerMenu {
   private final Container binInventory;
   private final BlockPos pos;
   private final int rows;

   public SellingBinScreenHandler(int syncId, Inventory playerInventory, FriendlyByteBuf buffer) {
      this(syncId, playerInventory, buffer.readBlockPos(), buffer.readVarInt());
   }

   public SellingBinScreenHandler(int syncId, Inventory playerInventory, BlockPos pos, int rows) {
      this(syncId, playerInventory, new SimpleContainer(9 * rows), pos, rows);
   }

   public SellingBinScreenHandler(int syncId, Inventory playerInventory, Container binInventory, BlockPos pos, int rows) {
      super((MenuType)ModScreenHandlers.SELLING_BIN.get(), syncId);
      this.binInventory = binInventory;
      this.pos = pos;
      this.rows = rows;
      int i = (this.rows - 4) * 18;

      for (int y = 0; y < this.rows; y++) {
         for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(binInventory, x + y * 9, 8 + x * 18, 18 + y * 18));
         }
      }

      for (int x = 0; x < 9; x++) {
         for (int y = 0; y < 3; y++) {
            this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 103 + y * 18 + i));
         }
      }

      for (int x = 0; x < 9; x++) {
         this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 161 + i));
      }

      this.binInventory.startOpen(playerInventory.player);
   }

   public BlockPos getPos() {
      return this.pos;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<SellingBinBlockEntity> getBlockEntity() {
      if (this.pos == null) {
         return Optional.empty();
      } else {
         ClientLevel world = Minecraft.getInstance().level;
         if (world == null) {
            return Optional.empty();
         } else {
            return world.getBlockEntity(this.pos) instanceof SellingBinBlockEntity bin ? Optional.of(bin) : Optional.empty();
         }
      }
   }

   public int getRows() {
      return this.rows;
   }

   public boolean stillValid(Player player) {
      return this.binInventory.stillValid(player);
   }

   public ItemStack quickMoveStack(Player player, int slotIndex) {
      Slot slot = (Slot)this.slots.get(slotIndex);
      if (!slot.hasItem()) {
         return ItemStack.EMPTY;
      }

      ItemStack stack = slot.getItem();
      ItemStack copy = stack.copy();
      if (slotIndex < this.binInventory.getContainerSize()) {
         if (!this.moveItemStackTo(stack, this.binInventory.getContainerSize(), this.slots.size(), true)) {
            return ItemStack.EMPTY;
         }
      } else if (!this.moveItemStackTo(stack, 0, this.binInventory.getContainerSize(), false)) {
         return ItemStack.EMPTY;
      }

      if (stack.isEmpty()) {
         slot.setByPlayer(ItemStack.EMPTY);
      } else {
         slot.setChanged();
      }

      return copy;
   }

   public void removed(Player player) {
      super.removed(player);
      this.binInventory.stopOpen(player);
   }
}
