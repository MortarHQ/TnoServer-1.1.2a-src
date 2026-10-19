package com.cerbon.bosses_of_mass_destruction.structure.void_blossom_cavern;

import com.cerbon.bosses_of_mass_destruction.structure.util.IStructurePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public interface ICaveDecorator {
   void onBlockPlaced(BlockPos var1, Block var2);

   void generate(WorldGenLevel var1, ChunkGenerator var2, RandomSource var3, BoundingBox var4, BlockPos var5, IStructurePiece var6);
}
