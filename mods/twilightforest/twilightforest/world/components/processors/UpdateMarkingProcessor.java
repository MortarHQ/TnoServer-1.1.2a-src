package twilightforest.world.components.processors;

import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFStructureProcessors;

public class UpdateMarkingProcessor extends StructureProcessor {
   public static final MapCodec<UpdateMarkingProcessor> CODEC = Block.CODEC
      .codec()
      .listOf()
      .xmap(UpdateMarkingProcessor::new, p -> p.blocksToMarkUpdate)
      .fieldOf("mark_updates");
   private final List<Block> blocksToMarkUpdate;

   public static UpdateMarkingProcessor forBlocks(Block... blocks) {
      return new UpdateMarkingProcessor(Arrays.asList(blocks));
   }

   public UpdateMarkingProcessor(List<Block> blocksToMarkUpdate) {
      this.blocksToMarkUpdate = blocksToMarkUpdate;
   }

   @Nullable
   public StructureBlockInfo process(
      LevelReader level,
      BlockPos offset,
      BlockPos piecePos,
      StructureBlockInfo originalInfo,
      StructureBlockInfo modifiedInfo,
      StructurePlaceSettings placeSettings,
      @Nullable StructureTemplate template
   ) {
      if (this.blocksToMarkUpdate.contains(modifiedInfo.state().getBlock())) {
         level.getChunk(modifiedInfo.pos()).markPosForPostprocessing(modifiedInfo.pos());
      }

      return modifiedInfo;
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)TFStructureProcessors.UPDATE_MARKING_PROCESSOR.value();
   }
}
