package nadiendev.constructionwand.items.core;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandAction;
import nadiendev.constructionwand.wand.action.ActionAngel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item.Properties;

public class ItemCoreAngel extends ItemCore {
   public ItemCoreAngel(Properties properties) {
      super(properties);
   }

   @Override
   public int getColor() {
      return 15315221;
   }

   @Override
   public IWandAction getWandAction() {
      return new ActionAngel();
   }

   @Override
   public ResourceLocation getRegistryName() {
      return ConstructionWand.loc("core_angel");
   }
}
