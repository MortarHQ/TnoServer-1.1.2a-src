package twilightforest.world.components.processors;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
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

public final class StoneBricksVariants extends StructureProcessor {
   public static final StoneBricksVariants INSTANCE = new StoneBricksVariants();
   public static final MapCodec<StoneBricksVariants> CODEC = MapCodec.unit(() -> INSTANCE);

   private StoneBricksVariants() {
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
      RandomSource random = settings.getRandom(modifiedBlockInfo.pos());
      random.setSeed(random.nextLong() * 3L);
      BlockState state = modifiedBlockInfo.state();
      if (state.is(Blocks.STONE_BRICKS) && random.nextBoolean()) {
         return new StructureBlockInfo(
            modifiedBlockInfo.pos(),
            random.nextBoolean() ? Blocks.MOSSY_STONE_BRICKS.defaultBlockState() : Blocks.CRACKED_STONE_BRICKS.defaultBlockState(),
            null
         );
      } else if (state.is(Blocks.STONE_BRICK_STAIRS) && random.nextBoolean()) {
         return new StructureBlockInfo(modifiedBlockInfo.pos(), FeaturePlacers.transferAllStateKeys(state, Blocks.MOSSY_STONE_BRICK_STAIRS), null);
      } else if (state.is(Blocks.STONE_BRICK_SLAB) && random.nextBoolean()) {
         return new StructureBlockInfo(modifiedBlockInfo.pos(), FeaturePlacers.transferAllStateKeys(state, Blocks.MOSSY_STONE_BRICK_SLAB), null);
      } else {
         return state.is(Blocks.STONE_BRICK_WALL) && random.nextBoolean()
            ? new StructureBlockInfo(modifiedBlockInfo.pos(), FeaturePlacers.transferAllStateKeys(state, Blocks.MOSSY_STONE_BRICK_WALL), null)
            : modifiedBlockInfo;
      }
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)TFStructureProcessors.STONE_BRICK_VARIANTS.get();
   }
}
