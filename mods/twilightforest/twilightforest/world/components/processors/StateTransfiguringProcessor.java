package twilightforest.world.components.processors;

import com.mojang.serialization.MapCodec;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import twilightforest.init.TFStructureProcessors;
import twilightforest.util.features.FeaturePlacers;

public class StateTransfiguringProcessor extends StructureProcessor {
   public static final MapCodec<StateTransfiguringProcessor> CODEC = ProcessorRule.CODEC
      .listOf()
      .fieldOf("rules")
      .xmap(StateTransfiguringProcessor::new, p -> p.rules);
   private final List<ProcessorRule> rules;

   public StateTransfiguringProcessor(List<? extends ProcessorRule> rules) {
      this.rules = Collections.unmodifiableList(rules);
   }

   @Nullable
   public StructureBlockInfo process(
      LevelReader level,
      BlockPos origin,
      BlockPos centerBottom,
      StructureBlockInfo originalBlockInfo,
      StructureBlockInfo modifiedBlockInfo,
      StructurePlaceSettings settings,
      @Nullable StructureTemplate template
   ) {
      BlockState state = level.getBlockState(modifiedBlockInfo.pos());
      RandomSource random = RandomSource.create(Mth.getSeed(modifiedBlockInfo.pos()));
      long i = random.nextLong();

      for (ProcessorRule processorRule : this.rules) {
         random.setSeed(i * 3L);
         i += 115L;
         if (processorRule.test(modifiedBlockInfo.state(), state, originalBlockInfo.pos(), modifiedBlockInfo.pos(), centerBottom, random)) {
            return new StructureBlockInfo(
               modifiedBlockInfo.pos(),
               FeaturePlacers.transferAllStateKeys(modifiedBlockInfo.state(), processorRule.getOutputState()),
               processorRule.getOutputTag(random, modifiedBlockInfo.nbt())
            );
         }
      }

      return modifiedBlockInfo;
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)TFStructureProcessors.STATE_TRANSFIGURING.get();
   }
}
