package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.function.Consumer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.p3pp3rf1y.sophisticatedcore.Config;

public class BlockItemBase extends BlockItem {
   public BlockItemBase(Block block, Properties properties) {
      super(block, properties);
   }

   public void addCreativeTabItems(Consumer<ItemStack> itemConsumer) {
      if (Config.COMMON.enabledItems.isItemEnabled(this) && this.getBlock() instanceof BlockBase blockBase) {
         blockBase.addCreativeTabItems(itemConsumer);
      }
   }
}
