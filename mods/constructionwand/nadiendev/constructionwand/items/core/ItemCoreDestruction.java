package nadiendev.constructionwand.items.core;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandAction;
import nadiendev.constructionwand.wand.action.ActionDestruction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item.Properties;

public class ItemCoreDestruction extends ItemCore {
   public ItemCoreDestruction(Properties properties) {
      super(properties);
   }

   @Override
   public int getColor() {
      return 16711680;
   }

   @Override
   public IWandAction getWandAction() {
      return new ActionDestruction();
   }

   @Override
   public ResourceLocation getRegistryName() {
      return ConstructionWand.loc("core_destruction");
   }
}
