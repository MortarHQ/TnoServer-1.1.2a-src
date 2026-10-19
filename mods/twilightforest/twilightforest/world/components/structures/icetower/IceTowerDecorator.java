package twilightforest.world.components.structures.icetower;

import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFBlocks;
import twilightforest.world.components.structures.TFStructureDecorator;
import twilightforest.world.components.structures.selectors.IceTowerRandomBlockSelectorFactory;

public class IceTowerDecorator extends TFStructureDecorator {
   @Autowired
   private static IceTowerRandomBlockSelectorFactory iceTower;

   public IceTowerDecorator() {
      this.blockState = ((Block)TFBlocks.AURORA_BLOCK.get()).defaultBlockState();
      this.accentState = Blocks.BIRCH_PLANKS.defaultBlockState();
      this.fenceState = Blocks.OAK_FENCE.defaultBlockState();
      this.stairState = Blocks.BIRCH_STAIRS.defaultBlockState();
      this.pillarState = (BlockState)((RotatedPillarBlock)TFBlocks.AURORA_PILLAR.get()).defaultBlockState().setValue(RotatedPillarBlock.AXIS, Axis.Y);
      this.platformState = Blocks.BIRCH_SLAB.defaultBlockState();
      this.floorState = Blocks.BIRCH_PLANKS.defaultBlockState();
      this.randomBlocks = iceTower.make();
   }
}
