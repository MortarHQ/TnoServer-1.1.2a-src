package dev.ftb.mods.ftbchunks.util;

import dev.ftb.mods.ftbchunks.CustomMinYRegistryImpl;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.core.BlockStateFTBC;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class HeightUtils {
   public static final int UNKNOWN = -32767;

   public static boolean isWater(BlockState state) {
      if (state == null) {
         return false;
      } else if (state.getBlock() == Blocks.WATER) {
         return true;
      } else {
         return state instanceof BlockStateFTBC ftbc ? ftbc.getFTBCIsWater() : state.getFluidState().getType().isSame(Fluids.WATER);
      }
   }

   public static boolean skipBlock(Level level, BlockState state) {
      return !level.isClientSide ? false : state.isAir() || FTBChunksClient.INSTANCE.skipBlock(state);
   }

   public static int getHeight(Level level, @Nullable ChunkAccess chunkAccess, MutableBlockPos pos) {
      if (chunkAccess == null) {
         return -32767;
      }

      int startY = CustomMinYRegistryImpl.getInstance(level.isClientSide).getMinYAt(level, pos);
      int bottomY = Mth.clamp(startY, chunkAccess.getMinBuildHeight(), chunkAccess.getMaxBuildHeight());
      int topY = pos.getY();
      boolean hasCeiling = level.dimensionType().hasCeiling();
      int currentWaterY = -32767;

      label45:
      for (int by = topY; by >= bottomY; by--) {
         pos.setY(by);
         BlockState state = chunkAccess.getBlockState(pos);
         if (hasCeiling && (by == topY || state.getBlock() == Blocks.BEDROCK)) {
            while (by > bottomY) {
               pos.setY(by);
               state = chunkAccess.getBlockState(pos);
               if (skipBlock(level, state)) {
                  continue label45;
               }

               by--;
            }
         }

         boolean water = isWater(state);
         if (water && currentWaterY == -32767) {
            currentWaterY = by;
         }

         if (!water && !skipBlock(level, state)) {
            return currentWaterY;
         }
      }

      pos.setY(-32767);
      return -32767;
   }
}
