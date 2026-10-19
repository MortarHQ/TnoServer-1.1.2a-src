package mcjty.incontrol.areas;

import com.google.gson.JsonObject;
import mcjty.incontrol.ErrorHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public record Area(ResourceKey<Level> dimension, String name, Area.Type type, BlockPos center, int dimx, int dimy, int dimz) {
   public static Area.Builder create() {
      return new Area.Builder();
   }

   public static boolean parse(JsonObject object, Area.Builder builder) {
      if (object.has("dimension")) {
         ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(object.get("dimension").getAsString()));
         builder.dimension(key);
         if (object.has("name")) {
            builder.name(object.get("name").getAsString());
            if (object.has("type")) {
               builder.type(Area.Type.valueOf(object.get("type").getAsString().toUpperCase()));
               if (object.has("x")) {
                  int x = object.get("x").getAsInt();
                  if (object.has("y")) {
                     int y = object.get("y").getAsInt();
                     if (object.has("z")) {
                        int z = object.get("z").getAsInt();
                        builder.center(new BlockPos(x, y, z));
                        if (object.has("dimx")) {
                           int dimx = object.get("dimx").getAsInt();
                           if (object.has("dimy")) {
                              int dimy = object.get("dimy").getAsInt();
                              if (object.has("dimz")) {
                                 int dimz = object.get("dimz").getAsInt();
                                 builder.dim(dimx, dimy, dimz);
                                 return true;
                              } else {
                                 ErrorHandler.error("Area '" + builder.name + "' has no dimz!");
                                 return false;
                              }
                           } else {
                              ErrorHandler.error("Area '" + builder.name + "' has no dimy!");
                              return false;
                           }
                        } else {
                           ErrorHandler.error("Area '" + builder.name + "' has no dimx!");
                           return false;
                        }
                     } else {
                        ErrorHandler.error("Area '" + builder.name + "' has no z!");
                        return false;
                     }
                  } else {
                     ErrorHandler.error("Area '" + builder.name + "' has no y!");
                     return false;
                  }
               } else {
                  ErrorHandler.error("Area '" + builder.name + "' has no x!");
                  return false;
               }
            } else {
               ErrorHandler.error("Area '" + builder.name + "' has no type!");
               return false;
            }
         } else {
            ErrorHandler.error("Area has no name!");
            return false;
         }
      } else {
         ErrorHandler.error("Area has no dimension!");
         return false;
      }
   }

   public boolean isInArea(int x, int y, int z) {
      return switch (this.type) {
         case BOX -> this.isInBox(x, y, z);
         case CYLINDER -> this.isInCylinder(x, y, z);
         default -> this.isInEllipsoid(x, y, z);
      };
   }

   private boolean isInBox(int x, int y, int z) {
      int dx = Math.abs(x - this.center.getX());
      int dy = Math.abs(y - this.center.getY());
      int dz = Math.abs(z - this.center.getZ());
      return dx <= this.dimx && dy <= this.dimy && dz <= this.dimz;
   }

   private boolean isInCylinder(int x, int y, int z) {
      int dx = Math.abs(x - this.center.getX());
      int dy = Math.abs(y - this.center.getY());
      int dz = Math.abs(z - this.center.getZ());
      return dx * dx / (this.dimx * this.dimx) + dz * dz / (this.dimz * this.dimz) <= 1 && dy <= this.dimy;
   }

   private boolean isInEllipsoid(int x, int y, int z) {
      int dx = Math.abs(x - this.center.getX());
      int dy = Math.abs(y - this.center.getY());
      int dz = Math.abs(z - this.center.getZ());
      return dx * dx / (this.dimx * this.dimx) + dy * dy / (this.dimy * this.dimy) + dz * dz / (this.dimz * this.dimz) <= 1;
   }

   public static class Builder {
      private String name;
      private ResourceKey<Level> dimension;
      private Area.Type type;
      private BlockPos center;
      private int dimx;
      private int dimy;
      private int dimz;

      public Area.Builder name(String name) {
         this.name = name;
         return this;
      }

      public Area.Builder dimension(ResourceKey<Level> dimension) {
         this.dimension = dimension;
         return this;
      }

      public Area.Builder type(Area.Type type) {
         this.type = type;
         return this;
      }

      public Area.Builder center(BlockPos center) {
         this.center = center;
         return this;
      }

      public Area.Builder dim(int dimx, int dimy, int dimz) {
         this.dimx = dimx;
         this.dimy = dimy;
         this.dimz = dimz;
         return this;
      }

      public Area build() {
         return new Area(this.dimension, this.name, this.type, this.center, this.dimx, this.dimy, this.dimz);
      }
   }

   enum Type {
      BOX,
      SPHERE,
      CYLINDER;
   }
}
