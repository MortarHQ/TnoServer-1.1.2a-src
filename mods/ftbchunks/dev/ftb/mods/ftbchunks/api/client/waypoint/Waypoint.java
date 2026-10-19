package dev.ftb.mods.ftbchunks.api.client.waypoint;

import dev.ftb.mods.ftbchunks.api.client.icon.WaypointIcon;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface Waypoint {
   ResourceKey<Level> getDimension();

   BlockPos getPos();

   boolean isHidden();

   Waypoint setHidden(boolean var1);

   String getName();

   default Component getDisplayName() {
      return Component.translatable(this.getName());
   }

   Waypoint setName(String var1);

   int getColor();

   Waypoint setColor(int var1);

   boolean isDeathpoint();

   boolean isTransient();

   Waypoint setTransient(boolean var1);

   double getDistanceSq(Entity var1);

   WaypointIcon getMapIcon();
}
