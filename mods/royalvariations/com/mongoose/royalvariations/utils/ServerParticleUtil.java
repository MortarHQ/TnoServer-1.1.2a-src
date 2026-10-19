package com.mongoose.royalvariations.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ServerParticleUtil {
   public static void blockBreakParticles(ParticleOptions pParticleData, BlockPos pPos, BlockState pState, ServerLevel serverWorld) {
      if (serverWorld != null) {
         VoxelShape voxelshape = pState.getShape(serverWorld, pPos);
         voxelshape.forAllBoxes((p_228348_3_, p_228348_5_, p_228348_7_, p_228348_9_, p_228348_11_, p_228348_13_) -> {
            double d1 = Math.min(1.0, p_228348_9_ - p_228348_3_);
            double d2 = Math.min(1.0, p_228348_11_ - p_228348_5_);
            double d3 = Math.min(1.0, p_228348_13_ - p_228348_7_);
            int i = Math.max(2, Mth.ceil(d1 / 0.25));
            int j = Math.max(2, Mth.ceil(d2 / 0.25));
            int k = Math.max(2, Mth.ceil(d3 / 0.25));

            for (int l = 0; l < i; l++) {
               for (int i1 = 0; i1 < j; i1++) {
                  for (int j1 = 0; j1 < k; j1++) {
                     double d4 = (l + 0.5) / i;
                     double d5 = (i1 + 0.5) / j;
                     double d6 = (j1 + 0.5) / k;
                     double d7 = d4 * d1 + p_228348_3_;
                     double d8 = d5 * d2 + p_228348_5_;
                     double d9 = d6 * d3 + p_228348_7_;
                     serverWorld.sendParticles(pParticleData, pPos.getX() + d7, pPos.getY() + d8, pPos.getZ() + d9, 0, d4 - 0.5, d5 - 0.5, d6 - 0.5, 0.5);
                  }
               }
            }
         });
      }
   }
}
