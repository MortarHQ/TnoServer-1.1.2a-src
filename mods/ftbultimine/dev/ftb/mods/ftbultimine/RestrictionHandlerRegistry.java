package dev.ftb.mods.ftbultimine;

import dev.ftb.mods.ftbultimine.api.restriction.RegisterRestrictionHandlerEvent;
import dev.ftb.mods.ftbultimine.api.restriction.RestrictionHandler;
import dev.ftb.mods.ftbultimine.api.util.CanUltimineResult;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.world.entity.player.Player;

public enum RestrictionHandlerRegistry implements RegisterRestrictionHandlerEvent.Registry {
   INSTANCE;

   private final Collection<RestrictionHandler> handlers = new CopyOnWriteArrayList<>();

   @Override
   public void register(RestrictionHandler handler) {
      this.handlers.add(handler);
   }

   public CanUltimineResult canUltimine(Player player) {
      for (RestrictionHandler handler : this.handlers) {
         if (!handler.canUltimine(player)) {
            return handler.ultimineBlockReason(player);
         }
      }

      return CanUltimineResult.ALLOWED;
   }
}
