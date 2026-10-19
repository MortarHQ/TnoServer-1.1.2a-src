package com.illusivesoulworks.polymorph.api.client.widgets;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.api.client.base.AbstractRecipesWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.RecipeHolder;

public class PlayerRecipesWidget extends AbstractRecipesWidget {
   final Slot outputSlot;

   public PlayerRecipesWidget(AbstractContainerScreen<?> containerScreen, Slot outputSlot) {
      super(containerScreen);
      this.outputSlot = outputSlot;
   }

   @Override
   public void selectRecipe(ResourceLocation resourceLocation) {
      PolymorphApi api = PolymorphApi.getInstance();
      Player player = Minecraft.getInstance().player;
      if (player != null) {
         player.level().getRecipeManager().byKey(resourceLocation).ifPresent(recipe -> api.getPlayerRecipeData(player).selectRecipe((RecipeHolder<?>)recipe));
      }

      api.getNetwork().sendPlayerRecipeSelectionC2S(resourceLocation);
   }

   @Override
   public Slot getOutputSlot() {
      return this.outputSlot;
   }
}
