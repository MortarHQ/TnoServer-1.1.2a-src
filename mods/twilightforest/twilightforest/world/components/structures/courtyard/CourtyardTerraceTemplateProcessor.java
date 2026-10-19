package twilightforest.world.components.structures.courtyard;

import com.mojang.serialization.MapCodec;
import java.util.HashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFStructureProcessors;

public final class CourtyardTerraceTemplateProcessor extends StructureProcessor {
   public static final CourtyardTerraceTemplateProcessor INSTANCE = new CourtyardTerraceTemplateProcessor();
   public static final MapCodec<CourtyardTerraceTemplateProcessor> CODEC = MapCodec.unit(() -> INSTANCE);
   private static final HashSet<BlockState> BLOCKS_REPLACE_TO_SLAB = new HashSet<>();

   private CourtyardTerraceTemplateProcessor() {
   }

   @Nullable
   public StructureBlockInfo process(
      LevelReader world,
      BlockPos pos,
      BlockPos piecepos,
      StructureBlockInfo oldinfo,
      StructureBlockInfo newInfo,
      StructurePlaceSettings settings,
      @Nullable StructureTemplate template
   ) {
      BlockState newState = newInfo.state();
      if (newState.getBlock() == Blocks.SANDSTONE_SLAB) {
         BlockState stateAt = world.getBlockState(newInfo.pos());
         if (newState == Blocks.SANDSTONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE)) {
            if (BLOCKS_REPLACE_TO_SLAB.contains(stateAt)) {
               return new StructureBlockInfo(newInfo.pos(), Blocks.STONE_BRICK_SLAB.defaultBlockState(), null);
            } else {
               return stateAt.is(Blocks.AIR) ? null : new StructureBlockInfo(newInfo.pos(), Blocks.STONE_BRICKS.defaultBlockState(), null);
            }
         } else {
            return stateAt.isAir() ? null : new StructureBlockInfo(newInfo.pos(), Blocks.STONE_BRICK_SLAB.defaultBlockState(), null);
         }
      } else {
         return newInfo;
      }
   }

   public StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)TFStructureProcessors.COURTYARD_TERRACE.get();
   }

   static {
      BLOCKS_REPLACE_TO_SLAB.add(Blocks.STONE_BRICKS.defaultBlockState());
      BLOCKS_REPLACE_TO_SLAB.add(Blocks.MOSSY_STONE_BRICKS.defaultBlockState());
      BLOCKS_REPLACE_TO_SLAB.add(Blocks.CRACKED_STONE_BRICKS.defaultBlockState());
      BLOCKS_REPLACE_TO_SLAB.add(Blocks.STONE_BRICK_SLAB.defaultBlockState());
      BLOCKS_REPLACE_TO_SLAB.add(Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState());
   }
}
