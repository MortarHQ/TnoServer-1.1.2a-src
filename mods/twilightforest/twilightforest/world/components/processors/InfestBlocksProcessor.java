package twilightforest.world.components.processors;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFStructureProcessors;

public final class InfestBlocksProcessor extends StructureProcessor {
   public static final InfestBlocksProcessor INSTANCE = new InfestBlocksProcessor();
   public static final MapCodec<InfestBlocksProcessor> CODEC = MapCodec.unit(() -> INSTANCE);
   private static final Supplier<Map<Block, BlockState>> CONVERSIONS = Suppliers.memoize(() -> (Map)Util.make(new HashMap(), map -> {
      map.put(Blocks.STONE, Blocks.INFESTED_STONE.defaultBlockState());
      map.put(Blocks.COBBLESTONE, Blocks.INFESTED_COBBLESTONE.defaultBlockState());
      map.put(Blocks.STONE_BRICKS, Blocks.INFESTED_STONE_BRICKS.defaultBlockState());
      map.put(Blocks.MOSSY_STONE_BRICKS, Blocks.INFESTED_MOSSY_STONE_BRICKS.defaultBlockState());
      map.put(Blocks.CRACKED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS.defaultBlockState());
      map.put(Blocks.CHISELED_STONE_BRICKS, Blocks.INFESTED_CHISELED_STONE_BRICKS.defaultBlockState());
   }));

   private InfestBlocksProcessor() {
   }

   public StructureBlockInfo process(
      LevelReader worldReaderIn,
      BlockPos pos,
      BlockPos piecepos,
      StructureBlockInfo originalBlock,
      StructureBlockInfo modifiedBlockInfo,
      StructurePlaceSettings settings,
      @Nullable StructureTemplate template
   ) {
      RandomSource random = settings.getRandom(modifiedBlockInfo.pos().below(-10));
      random.setSeed(random.nextLong() * 2L);
      BlockState replacement = CONVERSIONS.get().get(modifiedBlockInfo.state().getBlock());
      return replacement != null && !(random.nextFloat() > 0.083333336F)
         ? new StructureBlockInfo(modifiedBlockInfo.pos(), replacement, null)
         : modifiedBlockInfo;
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)TFStructureProcessors.INFEST_BLOCKS.get();
   }
}
