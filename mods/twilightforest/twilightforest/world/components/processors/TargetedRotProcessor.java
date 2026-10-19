package twilightforest.world.components.processors;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFStructureProcessors;

public final class TargetedRotProcessor extends BlockRotProcessor {
   public static final MapCodec<TargetedRotProcessor> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            BlockState.CODEC.listOf().xmap(ImmutableSet::copyOf, ArrayList::new).fieldOf("blocks_to_rot").forGetter(p -> p.blocksToRot),
            Codec.FLOAT.fieldOf("integrity").orElse(1.0F).forGetter(p -> p.integrity)
         )
         .apply(instance, TargetedRotProcessor::new)
   );
   private final ImmutableSet<BlockState> blocksToRot;

   public TargetedRotProcessor(ImmutableSet<BlockState> blocksToRot, float integrity) {
      super(integrity);
      this.blocksToRot = blocksToRot;
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
      return !this.blocksToRot.contains(modifiedBlockInfo.state())
         ? modifiedBlockInfo
         : super.processBlock(level, origin, centerBottom, originalBlockInfo, modifiedBlockInfo, settings);
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)TFStructureProcessors.TARGETED_ROT.get();
   }
}
