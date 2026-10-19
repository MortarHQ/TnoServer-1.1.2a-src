package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class CorpseBlockAddedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
      if ((blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip1 ? (Integer)blockstate.getValue(_getip1) : -1)
         == 0) {
         world.setBlock(BlockPos.containing(x, y + 1.0, z), blockstate, 3);
         int _value = 1;
         BlockPos _pos = BlockPos.containing(x, y + 1.0, z);
         BlockState _bs = world.getBlockState(_pos);
         if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp
            && _integerProp.getPossibleValues().contains(_value)) {
            world.setBlock(_pos, (BlockState)_bs.setValue(_integerProp, _value), 3);
         }

         _value = 2;
         _pos = BlockPos.containing(x, y, z);
         _bs = world.getBlockState(_pos);
         if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp
            && _integerProp.getPossibleValues().contains(_value)) {
            world.setBlock(_pos, (BlockState)_bs.setValue(_integerProp, _value), 3);
         }
      }
   }
}
