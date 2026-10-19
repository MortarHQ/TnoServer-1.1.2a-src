package com.iafenvoy.iceandfire.world.processor;

import com.iafenvoy.iceandfire.registry.IafProcessors;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class GraveyardProcessor extends StructureProcessor {
   public static final GraveyardProcessor INSTANCE = new GraveyardProcessor();
   public static final MapCodec<GraveyardProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

   public static BlockState getRandomCobblestone(RandomSource random) {
      float rand = random.nextFloat();
      if (rand < 0.5) {
         return Blocks.COBBLESTONE.defaultBlockState();
      } else {
         return rand < 0.9 ? Blocks.MOSSY_COBBLESTONE.defaultBlockState() : Blocks.INFESTED_COBBLESTONE.defaultBlockState();
      }
   }

   public static BlockState getRandomCrackedBlock(RandomSource random) {
      float rand = random.nextFloat();
      if (rand < 0.5) {
         return Blocks.STONE_BRICKS.defaultBlockState();
      } else {
         return rand < 0.9 ? Blocks.CRACKED_STONE_BRICKS.defaultBlockState() : Blocks.MOSSY_STONE_BRICKS.defaultBlockState();
      }
   }

   public StructureBlockInfo processBlock(
      LevelReader world, BlockPos pos, BlockPos pivot, StructureBlockInfo originalBlockInfo, StructureBlockInfo currentBlockInfo, StructurePlaceSettings data
   ) {
      RandomSource random = data.getRandom(currentBlockInfo.pos());
      if (currentBlockInfo.state().getBlock() == Blocks.STONE_BRICKS) {
         BlockState state = getRandomCrackedBlock(random);
         return new StructureBlockInfo(currentBlockInfo.pos(), state, null);
      } else if (currentBlockInfo.state().getBlock() == Blocks.COBBLESTONE) {
         BlockState state = getRandomCobblestone(random);
         return new StructureBlockInfo(currentBlockInfo.pos(), state, null);
      } else {
         return currentBlockInfo;
      }
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)IafProcessors.GRAVEYARD_PROCESSOR.get();
   }
}
