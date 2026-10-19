package com.github.wal_bos.moreskills.menu;

import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class CaedrosReconstructMenu extends AbstractContainerMenu {
   private final List<String> entries;
   private final double guilt;
   private final double ep;

   public CaedrosReconstructMenu(int id, Inventory inv, FriendlyByteBuf buf) {
      super((MenuType)ModMenus.CAEDROS_RECONSTRUCT_MENU.get(), id);
      this.guilt = buf == null ? 0.0 : buf.readDouble();
      this.ep = buf == null ? 0.0 : buf.readDouble();
      int size = buf == null ? 0 : buf.readVarInt();
      this.entries = new ArrayList<>();

      for (int i = 0; i < size; i++) {
         this.entries.add(buf.readUtf());
      }
   }

   public List<String> entries() {
      return this.entries;
   }

   public double guilt() {
      return this.guilt;
   }

   public double ep() {
      return this.ep;
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }

   public boolean stillValid(Player player) {
      return true;
   }
}
