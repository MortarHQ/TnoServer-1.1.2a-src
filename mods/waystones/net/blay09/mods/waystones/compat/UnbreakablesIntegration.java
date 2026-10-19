package net.blay09.mods.waystones.compat;

import java.util.Optional;
import net.blay09.mods.unbreakables.api.UnbreakablesAPI;
import net.blay09.mods.unbreakables.api.parameter.NoParameter;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.blay09.mods.waystones.api.WaystonesAPI;

public class UnbreakablesIntegration {
   public UnbreakablesIntegration() {
      UnbreakablesAPI.registerCondition("is_waystone_owner", NoParameter.class, (context, params) -> context.viaServer(level -> {
         Optional<Waystone> waystone = WaystonesAPI.getWaystoneAt(level.getServer(), context.getBlockGetter(), context.getPos());
         return waystone.<Boolean>map(it -> it.isOwner(context.getPlayer())).orElse(true);
      }));
      UnbreakablesAPI.registerCondition("is_waystone_global", NoParameter.class, (context, params) -> context.viaServer(level -> {
         Optional<Waystone> waystone = WaystonesAPI.getWaystoneAt(level.getServer(), context.getBlockGetter(), context.getPos());
         return waystone.<Boolean>map(it -> it.getVisibility() == WaystoneVisibility.GLOBAL).orElse(true);
      }));
   }
}
