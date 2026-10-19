package dev.ftb.mods.ftbchunks.api.client.waypoint;

import java.util.Collection;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public interface WaypointManager {
   Waypoint addWaypointAt(BlockPos var1, String var2);

   Waypoint addTransientWaypointAt(BlockPos var1, String var2);

   boolean removeWaypointAt(BlockPos var1);

   boolean removeWaypoint(Waypoint var1);

   Collection<Waypoint> getAllWaypoints();

   Optional<? extends Waypoint> getNearestDeathpoint(Player var1);
}
