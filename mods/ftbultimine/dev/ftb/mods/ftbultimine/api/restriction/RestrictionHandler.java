package dev.ftb.mods.ftbultimine.api.restriction;

import dev.ftb.mods.ftbultimine.api.util.CanUltimineResult;
import net.minecraft.world.entity.player.Player;

@FunctionalInterface
public interface RestrictionHandler {
   boolean canUltimine(Player var1);

   default CanUltimineResult ultimineBlockReason(Player player) {
      return CanUltimineResult.OTHER_RESTRICTION;
   }
}
