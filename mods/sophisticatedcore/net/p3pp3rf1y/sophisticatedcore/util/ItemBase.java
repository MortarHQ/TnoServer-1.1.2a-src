package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.function.Consumer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.p3pp3rf1y.sophisticatedcore.Config;

public class ItemBase extends Item {
   public ItemBase(Properties properties) {
      super(properties);
   }

   public void addCreativeTabItems(Consumer<ItemStack> itemConsumer) {
      if (Config.COMMON.enabledItems.isItemEnabled(this)) {
         itemConsumer.accept(new ItemStack(this));
      }
   }
}
