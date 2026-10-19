package com.cerbon.bosses_of_mass_destruction.structure.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public interface IStructurePiece {
   void placeBlock(WorldGenLevel var1, BlockState var2, BlockPos var3, BoundingBox var4);
}
