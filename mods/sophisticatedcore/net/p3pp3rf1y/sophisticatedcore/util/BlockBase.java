package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public abstract class BlockBase extends Block {
   public BlockBase(Properties properties) {
      super(properties);
   }

   public void addCreativeTabItems(Consumer<ItemStack> itemConsumer) {
      itemConsumer.accept(new ItemStack(this));
   }
}
