package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

public class PlaceholderItemInInventoryTickProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity instanceof Player _player) {
            ItemStack _stktoremove = new ItemStack((ItemLike)BossesRiseItems.PLACEHOLDER.get());
            _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 64, _player.inventoryMenu.getCraftSlots());
         }
      }
   }
}
