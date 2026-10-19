package dev.ftb.mods.ftbchunks.client.map;

import dev.ftb.mods.ftbchunks.api.client.icon.WaypointIcon;
import dev.ftb.mods.ftbchunks.api.client.waypoint.Waypoint;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.client.mapicon.WaypointMapIcon;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WaypointImpl implements Waypoint {
   private final MapDimension mapDimension;
   private final BlockPos pos;
   private final WaypointType type;
   public double minimapDistance = 50000.0;
   public double inWorldDistance = 0.0;
   private boolean hidden = false;
   private String name = "";
   private int color = 16777215;
   private WaypointMapIcon mapIcon;
   private boolean isTransient;

   public WaypointImpl(WaypointType type, MapDimension mapDimension, BlockPos pos) {
      this.type = type;
      this.mapDimension = mapDimension;
      this.pos = pos;
   }

   public WaypointType getType() {
      return this.type;
   }

   @Override
   public boolean isDeathpoint() {
      return this.type == WaypointType.DEATH;
   }

   @Override
   public ResourceKey<Level> getDimension() {
      return this.mapDimension.dimension;
   }

   @Override
   public BlockPos getPos() {
      return this.pos;
   }

   @Override
   public boolean isHidden() {
      return this.hidden;
   }

   public WaypointImpl setHidden(boolean hidden) {
      this.hidden = hidden;
      this.mapDimension.markDirty();
      return this;
   }

   @Override
   public String getName() {
      return this.name;
   }

   public WaypointImpl setName(String name) {
      this.name = name;
      this.mapDimension.markDirty();
      return this;
   }

   @Override
   public int getColor() {
      return this.color;
   }

   public WaypointImpl setColor(int color) {
      this.color = color;
      this.mapDimension.markDirty();
      return this;
   }

   @Override
   public boolean isTransient() {
      return this.isTransient;
   }

   public WaypointImpl setTransient(boolean isTransient) {
      this.isTransient = isTransient;
      return this;
   }

   @Override
   public double getDistanceSq(Entity entity) {
      return entity.distanceToSqr(Vec3.atCenterOf(this.pos));
   }

   @Override
   public WaypointIcon getMapIcon() {
      return this.mapIcon;
   }

   public void refreshIcon() {
      this.mapIcon = new WaypointMapIcon(this);
   }

   public double getDrawDistance(boolean isMinimap) {
      if (isMinimap) {
         return this.minimapDistance;
      } else {
         return this.inWorldDistance == 0.0 ? (Double)FTBChunksClientConfig.WAYPOINT_MAX_DISTANCE.get() : this.inWorldDistance;
      }
   }

   public void removeFromManager() {
      this.mapDimension.getWaypointManager().remove(this);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         WaypointImpl waypoint = (WaypointImpl)o;
         return this.pos.equals(waypoint.pos) && this.mapDimension.dimension.equals(waypoint.mapDimension.dimension);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.mapDimension.dimension, this.pos);
   }
}
