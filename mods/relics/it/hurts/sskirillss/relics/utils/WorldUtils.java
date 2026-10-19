package it.hurts.sskirillss.relics.utils;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class WorldUtils {
   public static List<BlockPos> getBlockSphere(BlockPos center, double radius) {
      List<BlockPos> sphere = new ArrayList<>((int)Math.pow(radius, 3.0));

      for (int i = -((int)radius); i <= radius; i++) {
         float r1 = Mth.sqrt((float)(radius * radius - i * i));

         for (int j = -((int)r1); j <= r1; j++) {
            float r2 = Mth.sqrt((float)(radius * radius - i * i - j * j));

            for (int k = -((int)r2); k <= r2; k++) {
               sphere.add(center.offset(i, j, k));
            }
         }
      }

      return sphere;
   }

   public static double getGroundHeight(Entity entity, Vec3 position, int iterations) {
      Level level = entity.level();
      HitResult result = level.clip(new ClipContext(position, position.add(0.0, -iterations, 0.0), Block.COLLIDER, Fluid.ANY, entity));
      return result.getType() == Type.BLOCK ? result.getLocation().y() : -level.getMaxBuildHeight();
   }
}
