package it.hurts.octostudios.reliquified_lenders_cataclysm.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RECParticleUtils {
   public static void createCircleSegment(ParticleOptions particle, Level level, Vec3 center, Vec3 target, double radius, float step) {
      BlockHitResult result = level.clip(new ClipContext(center, center.add(0.0, -radius, 0.0), Block.COLLIDER, Fluid.ANY, CollisionContext.empty()));
      if (result.getType() != Type.MISS) {
         double distanceToCircle = radius - target.distanceTo(center);
         if (!(distanceToCircle < 0.0)) {
            double distanceFrac = distanceToCircle / radius;
            double arcMin = Math.PI / 12;
            double arcMax = Math.PI * 2.0 / 3.0;
            double arcWidth = Math.clamp(1.0 - Math.clamp(distanceFrac, 0.0, 1.0), arcMin, arcMax);
            double targetAngle = Math.atan2(target.z - center.z, target.x - center.x);
            int n = (int)(Math.abs(targetAngle) * 180.0 / Math.PI);
            int arcLength = (int)(Math.PI * radius * (360 - n) / 180.0);
            int segmentPointsNum = (int)(target.distanceTo(center) * arcLength / radius);
            int maxTries = (int)Math.ceil(radius * 2.0);

            for (int i = 0; i < segmentPointsNum; i++) {
               double pointsFrac = (double)i / segmentPointsNum;
               double angle = Mth.lerp(pointsFrac, targetAngle - arcWidth / 2.0, targetAngle + arcWidth / 2.0);
               double x = center.x() + radius * Math.cos(angle);
               double y = center.y();
               double z = center.z() + radius * Math.sin(angle);
               spawnParticleOnSolid(level, particle, x, y, z, maxTries, step);
            }
         }
      }
   }

   private static void spawnParticleOnSolid(Level level, ParticleOptions particle, double x, double y, double z, int maxTries, float step) {
      int tries = 0;
      boolean foundSolid = false;

      while (tries < maxTries) {
         BlockPos pos = new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z));
         BlockState state = level.getBlockState(pos);
         VoxelShape shape = state.getCollisionShape(level, pos);
         if (state.getBlock() instanceof LiquidBlock) {
            shape = Shapes.block();
         }

         if (shape.isEmpty()) {
            if (foundSolid) {
               break;
            }

            y--;
         } else {
            foundSolid = true;
            AABB bounds = shape.bounds();
            if (!bounds.move(pos).contains(new Vec3(x, y, z))) {
               if (!(bounds.maxY >= 1.0)) {
                  break;
               }

               y++;
            } else {
               y += step;
            }
         }

         tries++;
      }

      if (tries < maxTries) {
         level.addParticle(particle, x, y + 0.1, z, 0.0, 0.0, 0.0);
      }
   }
}
