package com.github.manasmods.tensura.capability.ep;

import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.world.entity.player.Player;

public final class TensuraEPCapability {
   private TensuraEPCapability() {
   }

   public static int getHumanKill(Player player) {
      return TensuraStorages.getExistenceFrom(player).getHumanKill();
   }
}
