package io.github.Memoires.mysticism.bridge.neoforge;

import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import net.minecraft.world.entity.player.Player;

public final class SubordinateAPIImpl {
   public static void tame(ISubordinate subordinate, Player player) {
      subordinate.tame(player);
   }

   public static boolean isOrderedToSit(ISubordinate subordinate) {
      return subordinate.isOrderedToSit();
   }
}
