package twilightforest.world.components.processors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
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
import twilightforest.block.BanisterBlock;
import twilightforest.init.TFStructureProcessors;

public class VerticalDecayProcessor extends StructureProcessor {
   public static final MapCodec<VerticalDecayProcessor> CODEC = RecordCodecBuilder.mapCodec(
      inst -> inst.group(
            Block.CODEC.codec().listOf().fieldOf("decay_blocks").forGetter(VerticalDecayProcessor::getDecayBlocks),
            Codec.FLOAT.fieldOf("decay_chance").forGetter(p -> p.decayChance)
         )
         .apply(inst, VerticalDecayProcessor::new)
   );
   private final List<Block> decayBlocks;
   private final float decayChance;

   public VerticalDecayProcessor(List<Block> decayBlocks, float decayChance) {
      this.decayBlocks = Collections.unmodifiableList(decayBlocks);
      this.decayChance = decayChance;
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
      Block block = modifiedInfo.state().getBlock();
      if (this.decayBlocks.contains(block)) {
         int lookDown = block instanceof BanisterBlock ? -1 : 0;
         BlockPos randomAt = modifiedInfo.pos().atY(modifiedInfo.pos().getY() + lookDown);
         if (placeSettings.getRandom(randomAt).nextFloat() < this.decayChance) {
            return null;
         }
      }

      return modifiedInfo;
   }

   public List<Block> getDecayBlocks() {
      return this.decayBlocks;
   }

   protected StructureProcessorType<VerticalDecayProcessor> getType() {
      return (StructureProcessorType<VerticalDecayProcessor>)TFStructureProcessors.VERTICAL_DECAY.value();
   }
}
