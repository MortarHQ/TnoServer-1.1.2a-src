package it.hurts.sskirillss.relics.utils;

import it.hurts.sskirillss.relics.client.particles.BasicColoredParticle;
import java.awt.Color;
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

public class ParticleUtils {
   public static ParticleOptions constructSimpleSpark(Color color, float diameter, int lifetime, float scaleModifier) {
      return new BasicColoredParticle.Options(
         BasicColoredParticle.Constructor.builder()
            .color(color.getRGB())
            .diameter(diameter)
            .lifetime(lifetime)
            .scaleModifier(scaleModifier)
            .visibleThroughWalls(false)
            .physical(false)
            .roll(0.5F)
            .build()
      );
   }

   public static void createBall(ParticleOptions particle, Vec3 vec, Level world, int size, float speed) {
      if (world.isClientSide()) {
         for (int i = -size; i <= size; i++) {
            for (int j = -size; j <= size; j++) {
               for (int k = -size; k <= size; k++) {
                  double d3 = j + (world.random.nextDouble() - world.random.nextDouble()) * 0.5;
                  double d4 = i + (world.random.nextDouble() - world.random.nextDouble()) * 0.5;
                  double d5 = k + (world.random.nextDouble() - world.random.nextDouble()) * 0.5;
                  double d6 = (double)Mth.sqrt((float)(d3 * d3 + d4 * d4 + d5 * d5)) / speed + world.random.nextGaussian() * 0.05;
                  world.addParticle(particle, vec.x(), vec.y(), vec.z(), d3 / d6, d4 / d6, d5 / d6);
                  if (i != -size && i != size && j != -size && j != size) {
                     k += size * 2 - 1;
                  }
               }
            }
         }
      }
   }

   @Deprecated(forRemoval = true)
   public static void createCyl(ParticleOptions particle, Vec3 center, Level level, double radius, float step) {
      createCyl(particle, center, level, radius, step, false);
   }

   public static void createCyl(ParticleOptions particle, Vec3 center, Level level, double radius, float step, boolean spherical) {
      int maxTries = 16;
      if (spherical) {
         BlockHitResult result = level.clip(new ClipContext(center, center.add(0.0, -radius, 0.0), Block.COLLIDER, Fluid.ANY, CollisionContext.empty()));
         if (result.getType() == Type.MISS) {
            return;
         }

         maxTries = (int)Math.ceil(radius * 2.0);
         radius -= result.getLocation().distanceTo(center);
      }

      int numPoints = (int)Math.ceil((Math.PI * 2) * radius / step);
      double angleIncrement = (Math.PI * 2) / numPoints;

      for (int i = 0; i < numPoints; i++) {
         double angle = i * angleIncrement;
         double x = center.x() + radius * Math.cos(angle);
         double z = center.z() + radius * Math.sin(angle);
         double y = center.y();
         boolean foundSolid = false;

         int tries;
         for (tries = 0; tries < maxTries; tries++) {
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
         }

         if (tries < maxTries) {
            level.addParticle(particle, x, y + 0.1, z, 0.0, 0.0, 0.0);
         }
      }
   }

   public static void createLine(ParticleOptions particle, Level level, Vec3 start, Vec3 end, int amount, Vec3 motion) {
      Vec3 delta = end.subtract(start);
      Vec3 dir = delta.normalize();

      for (int i = 0; i < amount; i++) {
         double progress = i * delta.length() / amount;
         level.addParticle(particle, start.x + dir.x * progress, start.y + dir.y * progress, start.z + dir.z * progress, motion.x, motion.y, motion.z);
      }
   }

   public static void createLine(ParticleOptions particle, Level level, Vec3 start, Vec3 end, int amount) {
      createLine(particle, level, start, end, amount, Vec3.ZERO);
   }
}
