package nadiendev.constructionwand.items.core;

import nadiendev.constructionwand.ConstructionWand;
import nadiendev.constructionwand.api.IWandAction;
import nadiendev.constructionwand.api.IWandCore;
import nadiendev.constructionwand.wand.action.ActionConstruction;
import net.minecraft.resources.ResourceLocation;

public class CoreDefault implements IWandCore {
   @Override
   public int getColor() {
      return -1;
   }

   @Override
   public IWandAction getWandAction() {
      return new ActionConstruction();
   }

   @Override
   public ResourceLocation getRegistryName() {
      return ConstructionWand.loc("default");
   }
}
