package com.iafenvoy.iceandfire.world.processor;

import com.iafenvoy.iceandfire.item.block.DreadBaseBlock;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafProcessors;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class DreadPortalProcessor extends StructureProcessor {
   public static final DreadPortalProcessor INSTANCE = new DreadPortalProcessor();
   public static final MapCodec<DreadPortalProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

   public static BlockState getRandomCrackedBlock(RandomSource random) {
      float rand = random.nextFloat();
      if (rand < 0.3) {
         return ((DreadBaseBlock)IafBlocks.DREAD_STONE_BRICKS.get()).defaultBlockState();
      } else {
         return rand < 0.6
            ? ((DreadBaseBlock)IafBlocks.DREAD_STONE_BRICKS_CRACKED.get()).defaultBlockState()
            : ((DreadBaseBlock)IafBlocks.DREAD_STONE_BRICKS_MOSSY.get()).defaultBlockState();
      }
   }

   public StructureBlockInfo processBlock(
      LevelReader world, BlockPos pos, BlockPos pivot, StructureBlockInfo originalBlockInfo, StructureBlockInfo currentBlockInfo, StructurePlaceSettings data
   ) {
      RandomSource random = data.getRandom(pos);
      float integrity = 1.0F;
      if (random.nextFloat() <= integrity && currentBlockInfo.state().is((Block)IafBlocks.DREAD_STONE_BRICKS.get())) {
         BlockState state = getRandomCrackedBlock(random);
         return new StructureBlockInfo(pos, state, null);
      } else {
         return currentBlockInfo;
      }
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)IafProcessors.DREAD_PORTAL_PROCESSOR.get();
   }
}
