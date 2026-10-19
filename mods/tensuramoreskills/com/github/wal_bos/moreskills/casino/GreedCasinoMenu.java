package com.github.wal_bos.moreskills.casino;

import com.github.wal_bos.moreskills.ability.skill.ultimate.MammonCasinoUltimateSkill;
import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class GreedCasinoMenu extends AbstractContainerMenu {
   private final Player player;
   private final DataSlot chips = DataSlot.standalone();

   public GreedCasinoMenu(int id, Inventory inventory) {
      super((MenuType)ModMenus.GREED_CASINO.get(), id);
      this.player = inventory.player;
      this.addDataSlot(this.chips);
      if (this.player instanceof ServerPlayer serverPlayer) {
         this.chips.set(MammonCasinoUltimateSkill.getChips(serverPlayer));
      }
   }

   public int getChipsClient() {
      return this.chips.get();
   }

   public void broadcastChanges() {
      super.broadcastChanges();
      if (this.player instanceof ServerPlayer serverPlayer) {
         this.chips.set(MammonCasinoUltimateSkill.getChips(serverPlayer));
      }
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }
}
