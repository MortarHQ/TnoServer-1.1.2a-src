package twilightforest.world.components.processors;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructureProcessors;
import twilightforest.util.features.FeaturePlacers;

public final class NagastoneVariants extends StructureProcessor {
   public static final NagastoneVariants INSTANCE = new NagastoneVariants();
   public static final MapCodec<NagastoneVariants> CODEC = MapCodec.unit(() -> INSTANCE);

   private NagastoneVariants() {
   }

   public StructureBlockInfo process(
      LevelReader worldIn,
      BlockPos pos,
      BlockPos piecepos,
      StructureBlockInfo oldInfo,
      StructureBlockInfo modifiedBlockInfo,
      StructurePlaceSettings settings,
      @Nullable StructureTemplate template
   ) {
      RandomSource random = settings.getRandom(modifiedBlockInfo.pos());
      random.setSeed(random.nextLong() * 5L);
      BlockState state = modifiedBlockInfo.state();
      Block block = state.getBlock();
      if (block == TFBlocks.ETCHED_NAGASTONE.get() && random.nextBoolean()) {
         return new StructureBlockInfo(
            modifiedBlockInfo.pos(),
            FeaturePlacers.transferAllStateKeys(
               state, random.nextBoolean() ? (Block)TFBlocks.MOSSY_ETCHED_NAGASTONE.get() : (Block)TFBlocks.CRACKED_ETCHED_NAGASTONE.get()
            ),
            null
         );
      } else if (block == TFBlocks.NAGASTONE_PILLAR.get() && random.nextBoolean()) {
         return new StructureBlockInfo(
            modifiedBlockInfo.pos(),
            FeaturePlacers.transferAllStateKeys(
               state, random.nextBoolean() ? (Block)TFBlocks.MOSSY_NAGASTONE_PILLAR.get() : (Block)TFBlocks.CRACKED_NAGASTONE_PILLAR.get()
            ),
            null
         );
      } else if (block == TFBlocks.NAGASTONE_STAIRS_LEFT.get() && random.nextBoolean()) {
         return new StructureBlockInfo(
            modifiedBlockInfo.pos(),
            FeaturePlacers.transferAllStateKeys(
               state, random.nextBoolean() ? (Block)TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT.get() : (Block)TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT.get()
            ),
            null
         );
      } else {
         return block == TFBlocks.NAGASTONE_STAIRS_RIGHT.get() && random.nextBoolean()
            ? new StructureBlockInfo(
               modifiedBlockInfo.pos(),
               FeaturePlacers.transferAllStateKeys(
                  state, random.nextBoolean() ? (Block)TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT.get() : (Block)TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT.get()
               ),
               null
            )
            : modifiedBlockInfo;
      }
   }

   public StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)TFStructureProcessors.NAGASTONE_VARIANTS.get();
   }
}
