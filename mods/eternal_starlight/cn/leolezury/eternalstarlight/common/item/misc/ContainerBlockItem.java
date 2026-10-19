package cn.leolezury.eternalstarlight.common.item.misc;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public class ContainerBlockItem extends BlockItem {
   public ContainerBlockItem(Block block, Properties properties) {
      super(block, properties);
   }

   public boolean canFitInsideContainerItems() {
      return false;
   }
}
