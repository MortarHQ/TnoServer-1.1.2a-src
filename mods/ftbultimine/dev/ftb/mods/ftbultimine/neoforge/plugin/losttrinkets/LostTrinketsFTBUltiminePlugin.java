package dev.ftb.mods.ftbultimine.neoforge.plugin.losttrinkets;

import dev.ftb.mods.ftbultimine.api.restriction.RestrictionHandler;
import net.minecraft.world.entity.player.Player;

public class LostTrinketsFTBUltiminePlugin implements RestrictionHandler {
   @Override
   public boolean canUltimine(Player player) {
      return true;
   }
}
