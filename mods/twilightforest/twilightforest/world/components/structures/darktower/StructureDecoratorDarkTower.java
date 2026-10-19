package twilightforest.world.components.structures.darktower;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFBlocks;
import twilightforest.world.components.structures.TFStructureDecorator;
import twilightforest.world.components.structures.selectors.TowerwoodRandomBlockSelectorFactory;

public class StructureDecoratorDarkTower extends TFStructureDecorator {
   @Autowired
   private static TowerwoodRandomBlockSelectorFactory towerwood;

   public StructureDecoratorDarkTower() {
      this.blockState = ((Block)TFBlocks.TOWERWOOD.get()).defaultBlockState();
      this.accentState = ((Block)TFBlocks.ENCASED_TOWERWOOD.get()).defaultBlockState();
      this.fenceState = Blocks.OAK_FENCE.defaultBlockState();
      this.stairState = Blocks.SPRUCE_STAIRS.defaultBlockState();
      this.pillarState = ((Block)TFBlocks.ENCASED_TOWERWOOD.get()).defaultBlockState();
      this.platformState = ((Block)TFBlocks.ENCASED_TOWERWOOD.get()).defaultBlockState();
      this.randomBlocks = towerwood.make();
   }
}
