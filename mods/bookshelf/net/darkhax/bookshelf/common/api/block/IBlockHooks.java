package net.darkhax.bookshelf.common.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;

public interface IBlockHooks {
   @Nullable
   default PathType getPathfindingType(BlockState state, BlockGetter context, BlockPos pos) {
      return null;
   }
}
