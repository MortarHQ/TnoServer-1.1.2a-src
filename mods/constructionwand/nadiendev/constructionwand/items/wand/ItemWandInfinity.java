package nadiendev.constructionwand.items.wand;

import net.minecraft.world.item.Item.Properties;

public class ItemWandInfinity extends ItemWand {
   public ItemWandInfinity(Properties properties) {
      super(properties.stacksTo(1).fireResistant());
   }
}
