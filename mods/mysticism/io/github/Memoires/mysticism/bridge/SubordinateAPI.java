package io.github.Memoires.mysticism.bridge;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import io.github.Memoires.mysticism.bridge.neoforge.SubordinateAPIImpl;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import net.minecraft.world.entity.player.Player;

public final class SubordinateAPI {
   @ExpectPlatform
   @Transformed
   public static void tame(ISubordinate subordinate, Player player) {
      SubordinateAPIImpl.tame(subordinate, player);
   }

   @ExpectPlatform
   @Transformed
   public static boolean isOrderedToSit(ISubordinate subordinate) {
      return SubordinateAPIImpl.isOrderedToSit(subordinate);
   }
}
