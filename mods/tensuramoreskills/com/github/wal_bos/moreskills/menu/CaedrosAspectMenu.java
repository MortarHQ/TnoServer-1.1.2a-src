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

public class CaedrosAspectMenu extends AbstractContainerMenu {
   private final List<String> entries;
   private final int currentAspectOrdinal;

   public CaedrosAspectMenu(int id, Inventory inv, FriendlyByteBuf buf) {
      super((MenuType)ModMenus.CAEDROS_ASPECT_MENU.get(), id);
      this.currentAspectOrdinal = buf == null ? -1 : buf.readInt();
      int size = buf == null ? 0 : buf.readVarInt();
      this.entries = new ArrayList<>();

      for (int i = 0; i < size; i++) {
         this.entries.add(buf.readUtf());
      }
   }

   public List<String> entries() {
      return this.entries;
   }

   public int currentAspectOrdinal() {
      return this.currentAspectOrdinal;
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }

   public boolean stillValid(Player player) {
      return true;
   }
}
