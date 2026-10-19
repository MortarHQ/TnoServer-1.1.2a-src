package com.finderfeed.fdlib.systems.multiblock;

import com.finderfeed.fdlib.init.FDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TestMultiBlock extends MultiblockBlock {
   public TestMultiBlock(Properties properties) {
      super(
         properties,
         () -> MultiblockShape.builder(FDBlocks.TEST_MULTIBLOCK.get().defaultBlockState())
            .addMultiblockPartOffset(new BlockPos(-1, 0, 0), FDBlocks.TEST_MULTIBLOCK.get().defaultBlockState())
            .addMultiblockPartOffset(new BlockPos(1, 0, 0), FDBlocks.TEST_MULTIBLOCK.get().defaultBlockState())
            .addMultiblockPartOffset(new BlockPos(0, 1, 0), FDBlocks.TEST_MULTIBLOCK.get().defaultBlockState())
            .addMultiblockPartOffset(new BlockPos(0, 1, 1), FDBlocks.TEST_MULTIBLOCK.get().defaultBlockState())
            .addMultiblockPartOffset(new BlockPos(0, 3, 0), FDBlocks.TEST_MULTIBLOCK.get().defaultBlockState())
            .build()
      );
      this.cacheShape = false;
   }
}
