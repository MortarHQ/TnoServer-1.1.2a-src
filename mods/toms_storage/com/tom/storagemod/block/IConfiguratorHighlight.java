package com.tom.storagemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface IConfiguratorHighlight {
   int getHighlightColor();

   VoxelShape getHighlightShape(BlockState var1, BlockGetter var2, BlockPos var3);
}
