package net.witherspawnanimation.procedures;

import java.util.Comparator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RotationDetectProcedure {
   public static String execute(LevelAccessor world, double x, double y, double z) {
      double xx = 0.0;
      double zz = 0.0;
      double avx = 0.0;
      double avz = 0.0;
      if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true).isEmpty()) {
         xx = x - world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true).stream().sorted((new Object() {
            Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
               return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
            }
         }).compareDistOf(x, y, z)).findFirst().orElse(null).getX();
         zz = z - world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 50.0, 50.0, 50.0), e -> true).stream().sorted((new Object() {
            Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
               return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
            }
         }).compareDistOf(x, y, z)).findFirst().orElse(null).getZ();
         if (xx >= 0.0) {
            avx = xx;
         } else {
            avx = 0.0 - xx;
         }

         if (zz >= 0.0) {
            avz = avz;
         } else {
            avz = 0.0 - zz;
         }

         if (avz > avx) {
            if (zz < 0.0) {
               return "z";
            }
         } else if (avx > avz) {
            if (zz > avx) {
               return "minz";
            }

            if (xx > 0.0) {
               return "x";
            }

            if (xx < 0.0) {
               return "minx";
            }
         }
      }

      return "null";
   }
}
