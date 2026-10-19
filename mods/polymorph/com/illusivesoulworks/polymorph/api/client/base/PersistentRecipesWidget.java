package com.illusivesoulworks.polymorph.api.client.base;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;

public abstract class PersistentRecipesWidget extends AbstractRecipesWidget {
   public PersistentRecipesWidget(AbstractContainerScreen<?> containerScreen) {
      super(containerScreen);
   }

   @Override
   public void selectRecipe(ResourceLocation resourceLocation) {
      PolymorphApi.getInstance().getNetwork().sendPersistentRecipeSelectionC2S(resourceLocation);
   }
}
