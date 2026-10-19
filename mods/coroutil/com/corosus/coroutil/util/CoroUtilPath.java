package com.corosus.coroutil.util;

import com.corosus.coroutil.config.ConfigCoroUtil;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class CoroUtilPath {
   public static boolean tryMoveToEntityLivingLongDist(Mob entSource, Entity entityTo, double moveSpeedAmp) {
      return tryMoveToXYZLongDist(entSource, entityTo.getX(), entityTo.getBoundingBox().minY, entityTo.getZ(), moveSpeedAmp);
   }

   public static boolean tryMoveToXYZLongDist(Mob ent, double x, double y, double z, double moveSpeedAmp) {
      Level world = ent.level();
      boolean success = false;

      try {
         if (ent.getNavigation().isDone()) {
            double distToPlayer = Math.sqrt(ent.distanceToSqr(x, y, z));
            double followDist = ent.getAttribute(Attributes.FOLLOW_RANGE).getValue();
            if (distToPlayer <= followDist) {
               success = CoroUtilCompatibility.tryPathToXYZModCompat(ent, Mth.floor(x), Mth.floor(y), Mth.floor(z), moveSpeedAmp);
            } else {
               double d = x + 0.5 - ent.getX();
               double d2 = z + 0.5 - ent.getZ();
               double d1 = y + 0.5 - (ent.getY() + ent.getEyeHeight());
               double d3 = Mth.sqrt((float)(d * d + d2 * d2));
               float f2 = (float)(Math.atan2(d2, d) * 180.0 / (float) Math.PI) - 90.0F;
               float f3 = (float)(-(Math.atan2(d1, d3) * 180.0 / (float) Math.PI));
               float rotationPitch = -f3;
               float rotationYaw = f2;
               Mob center = ent;
               Random rand = new Random();
               float randLook = rand.nextInt(90) - 45;
               double dist = followDist * 0.75 + rand.nextInt((int)followDist / 2);
               int gatherX = (int)Math.floor(center.getX() + -Math.sin((rotationYaw + randLook) / 180.0F * (float) Math.PI) * dist);
               int gatherY = (int)center.getY();
               int gatherZ = (int)Math.floor(center.getZ() + Math.cos((rotationYaw + randLook) / 180.0F * (float) Math.PI) * dist);
               BlockPos pos = new BlockPos(gatherX, gatherY, gatherZ);
               if (!world.isLoaded(pos)) {
                  return false;
               }

               BlockState state = world.getBlockState(pos);
               int tries = 0;
               if (!CoroUtilBlock.isAir(state.getBlock())) {
                  int offset = -5;

                  while (tries < 30 && !CoroUtilBlock.isAir(state.getBlock()) && state.isPathfindable(PathComputationType.LAND)) {
                     gatherY += offset++;
                     pos = new BlockPos(gatherX, gatherY, gatherZ);
                     state = world.getBlockState(pos);
                     tries++;
                  }
               } else {
                  while (tries < 30 && (CoroUtilBlock.isAir(state.getBlock()) || !state.isPathfindable(PathComputationType.LAND))) {
                     pos = new BlockPos(gatherX, --gatherY, gatherZ);
                     state = world.getBlockState(pos);
                     tries++;
                  }
               }

               if (tries < 30) {
                  success = CoroUtilCompatibility.tryPathToXYZModCompat(ent, gatherX, gatherY, gatherZ, moveSpeedAmp);
               } else {
                  pos = new BlockPos(pos.getX(), world.getHeight(Types.MOTION_BLOCKING, pos.getX(), pos.getZ()), pos.getZ()).below();
                  if (!world.isLoaded(pos)) {
                     return false;
                  }

                  state = world.getBlockState(pos);
                  if (state.isPathfindable(PathComputationType.LAND)) {
                     success = CoroUtilCompatibility.tryPathToXYZModCompat(ent, pos.getX(), pos.getY(), pos.getZ(), moveSpeedAmp);
                  }
               }
            }
         }
      } catch (Exception ex) {
         CULog.err("Exception trying to pathfind");
         if (ConfigCoroUtil.useLoggingError) {
            ex.printStackTrace();
         }
      }

      return success;
   }
}
