package twilightforest.world.components.structures.finalcastle;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFBlocks;
import twilightforest.world.components.structures.TFStructureDecorator;
import twilightforest.world.components.structures.selectors.CastleRandomBlockSelectorFactory;

public class StructureTFDecoratorCastle extends TFStructureDecorator {
   @Autowired
   private static CastleRandomBlockSelectorFactory castle;

   public StructureTFDecoratorCastle() {
      this.blockState = ((Block)TFBlocks.CASTLE_BRICK.get()).defaultBlockState();
      this.accentState = Blocks.CHISELED_QUARTZ_BLOCK.defaultBlockState();
      this.roofState = ((Block)TFBlocks.CASTLE_ROOF_TILE.get()).defaultBlockState();
      this.pillarState = ((Block)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get()).defaultBlockState();
      this.fenceState = Blocks.OAK_FENCE.defaultBlockState();
      this.stairState = Blocks.QUARTZ_STAIRS.defaultBlockState();
      this.randomBlocks = castle.make();
   }
}
