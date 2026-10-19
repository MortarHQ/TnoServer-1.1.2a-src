package com.b1n_ry.yigd.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class GraveyardData {
   @SerializedName("point2point")
   public boolean point2point = false;
   @SerializedName("dimension")
   public ResourceLocation dimensionId = ResourceLocation.withDefaultNamespace("overworld");
   @SerializedName("use_closest")
   public boolean useClosest = false;
   @SerializedName("coordinates")
   public List<GraveyardData.GraveLocation> graveLocations = new ArrayList<>();

   public void handlePoint2Point() {
      if (this.point2point && this.graveLocations.size() == 2) {
         GraveyardData.GraveLocation first = this.graveLocations.get(0);
         GraveyardData.GraveLocation second = this.graveLocations.get(1);
         int minX = Math.min(first.x, second.x);
         int minY = Math.min(first.y, second.y);
         int minZ = Math.min(first.z, second.z);
         int maxX = Math.max(first.x, second.x);
         int maxY = Math.max(first.y, second.y);
         int maxZ = Math.max(first.z, second.z);
         this.graveLocations.clear();

         for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
               for (int z = minZ; z <= maxZ; z++) {
                  this.graveLocations.add(new GraveyardData.GraveLocation(x, y, z));
               }
            }
         }
      }
   }

   public static class GraveLocation {
      @SerializedName("x")
      public int x;
      @SerializedName("y")
      public int y;
      @SerializedName("z")
      public int z;
      @SerializedName("for_player")
      @Nullable
      public String forPlayer = null;
      @SerializedName("direction")
      @Nullable
      public Direction direction = null;

      public GraveLocation(int x, int y, int z) {
         this.x = x;
         this.y = y;
         this.z = z;
      }
   }
}
