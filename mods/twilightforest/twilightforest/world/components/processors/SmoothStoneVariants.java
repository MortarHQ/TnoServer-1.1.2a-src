package twilightforest.world.components.processors;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFStructureProcessors;
import twilightforest.util.features.FeaturePlacers;

public class SmoothStoneVariants extends StructureProcessor {
   public static final SmoothStoneVariants INSTANCE = new SmoothStoneVariants();
   public static final MapCodec<SmoothStoneVariants> CODEC = MapCodec.unit(() -> INSTANCE);

   private SmoothStoneVariants() {
   }

   public StructureBlockInfo process(
      LevelReader level,
      BlockPos origin,
      BlockPos centerBottom,
      StructureBlockInfo originalBlockInfo,
      StructureBlockInfo modifiedBlockInfo,
      StructurePlaceSettings settings,
      @Nullable StructureTemplate template
   ) {
      RandomSource random = settings.getRandom(modifiedBlockInfo.pos());
      random.setSeed(random.nextLong() * 4L);
      if (modifiedBlockInfo.state().is(Blocks.SMOOTH_STONE_SLAB) && random.nextBoolean()) {
         return new StructureBlockInfo(modifiedBlockInfo.pos(), FeaturePlacers.transferAllStateKeys(modifiedBlockInfo.state(), Blocks.COBBLESTONE_SLAB), null);
      } else {
         return modifiedBlockInfo.state().is(Blocks.SMOOTH_STONE) && random.nextBoolean()
            ? new StructureBlockInfo(modifiedBlockInfo.pos(), Blocks.COBBLESTONE.defaultBlockState(), null)
            : modifiedBlockInfo;
      }
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)TFStructureProcessors.SMOOTH_STONE_VARIANTS.get();
   }
}
