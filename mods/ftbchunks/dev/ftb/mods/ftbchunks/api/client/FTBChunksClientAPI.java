package dev.ftb.mods.ftbchunks.api.client;

import com.google.common.collect.ImmutableList;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapInfoComponent;
import dev.ftb.mods.ftbchunks.api.client.waypoint.WaypointManager;
import java.util.Optional;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public interface FTBChunksClientAPI {
   Optional<WaypointManager> getWaypointManager();

   Optional<WaypointManager> getWaypointManager(ResourceKey<Level> var1);

   void requestMinimapIconRefresh();

   void registerMinimapComponent(MinimapInfoComponent var1);

   boolean isMinimapComponentEnabled(MinimapInfoComponent var1);

   void setMinimapComponentEnabled(MinimapInfoComponent var1, boolean var2);

   ImmutableList<MinimapInfoComponent> getMinimapComponents();
}
