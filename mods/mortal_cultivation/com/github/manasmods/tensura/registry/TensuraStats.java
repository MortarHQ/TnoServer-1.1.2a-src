package com.github.manasmods.tensura.registry;

import net.minecraft.world.entity.player.Player;

public final class TensuraStats {
   private TensuraStats() {
   }

   public static int getBossKilled(Player player) {
      return io.github.manasmods.tensura.registry.TensuraStats.getBossDefeated(player);
   }
}
