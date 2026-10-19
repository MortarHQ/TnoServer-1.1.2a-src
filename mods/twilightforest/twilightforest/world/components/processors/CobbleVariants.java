package twilightforest.world.components.processors;

import com.mojang.serialization.MapCodec;
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
import twilightforest.util.features.FeaturePlacers;

public final class CobbleVariants extends StructureProcessor {
   public static final CobbleVariants INSTANCE = new CobbleVariants();
   public static final MapCodec<CobbleVariants> CODEC = MapCodec.unit(() -> INSTANCE);

   private CobbleVariants() {
   }

   public StructureBlockInfo process(
      LevelReader worldReaderIn,
      BlockPos pos,
      BlockPos piecepos,
      StructureBlockInfo p_215194_3_,
      StructureBlockInfo modifiedBlockInfo,
      StructurePlaceSettings settings,
      @Nullable StructureTemplate template
   ) {
      RandomSource random = settings.getRandom(modifiedBlockInfo.pos());
      random.setSeed(random.nextLong() * 2L);
      BlockState state = modifiedBlockInfo.state();
      Block block = state.getBlock();
      if (block == Blocks.COBBLESTONE && random.nextBoolean()) {
         return new StructureBlockInfo(modifiedBlockInfo.pos(), Blocks.MOSSY_COBBLESTONE.defaultBlockState(), null);
      } else if (block == Blocks.COBBLESTONE_STAIRS && random.nextBoolean()) {
         return new StructureBlockInfo(modifiedBlockInfo.pos(), FeaturePlacers.transferAllStateKeys(state, Blocks.MOSSY_COBBLESTONE_STAIRS), null);
      } else if (block == Blocks.COBBLESTONE_SLAB && random.nextBoolean()) {
         return new StructureBlockInfo(modifiedBlockInfo.pos(), FeaturePlacers.transferAllStateKeys(state, Blocks.MOSSY_COBBLESTONE_SLAB), null);
      } else {
         return block == Blocks.COBBLESTONE_WALL && random.nextBoolean()
            ? new StructureBlockInfo(modifiedBlockInfo.pos(), FeaturePlacers.transferAllStateKeys(state, Blocks.MOSSY_COBBLESTONE_WALL), null)
            : modifiedBlockInfo;
      }
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)TFStructureProcessors.COBBLE_VARIANTS.get();
   }
}
