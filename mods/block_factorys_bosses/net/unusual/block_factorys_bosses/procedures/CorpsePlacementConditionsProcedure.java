package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class CorpsePlacementConditionsProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
      if (2
            == (
               blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip1
                  ? (Integer)blockstate.getValue(_getip1)
                  : -1
            )
         && world.getBlockState(BlockPos.containing(x, y + 1.0, z)).getBlock() == blockstate.getBlock()
         && 1
            == (
               world.getBlockState(BlockPos.containing(x, y + 1.0, z)).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip6
                  ? (Integer)world.getBlockState(BlockPos.containing(x, y + 1.0, z)).getValue(_getip6)
                  : -1
            )) {
         return true;
      } else {
         return 1
                  == (
                     blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip8
                        ? blockstate.getValue(_getip8)
                        : -1
                  )
               && world.getBlockState(BlockPos.containing(x, y - 1.0, z)).getBlock() == blockstate.getBlock()
               && 2
                  == (
                     world.getBlockState(BlockPos.containing(x, y - 1.0, z)).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip13
                        ? world.getBlockState(BlockPos.containing(x, y - 1.0, z)).getValue(_getip13)
                        : -1
                  )
            ? true
            : (
                  world.getBlockState(BlockPos.containing(x, y + 1.0, z)).getBlock() == Blocks.AIR
                     || world.getBlockState(BlockPos.containing(x, y + 1.0, z)).getBlock() == blockstate.getBlock()
               )
               && 0
                  == (
                     blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip20
                        ? (Integer)blockstate.getValue(_getip20)
                        : -1
                  );
      }
   }
}
