package team.lodestar.lodestone.systems.multiblock;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import team.lodestar.lodestone.registry.common.LodestoneBlockEntities;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class MultiblockComponentBlock extends LodestoneEntityBlock<MultiBlockComponentEntity> implements ILodestoneMultiblockComponent {
   public MultiblockComponentBlock(Properties properties) {
      super(properties);
      this.setBlockEntity(LodestoneBlockEntities.MULTIBLOCK_COMPONENT);
   }
}
