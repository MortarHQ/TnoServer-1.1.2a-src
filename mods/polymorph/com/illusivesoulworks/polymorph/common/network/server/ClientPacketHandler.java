package com.illusivesoulworks.polymorph.common.network.server;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.api.client.PolymorphWidgets;
import com.illusivesoulworks.polymorph.api.client.base.IRecipesWidget;
import com.illusivesoulworks.polymorph.api.common.base.IRecipePair;
import com.illusivesoulworks.polymorph.api.common.capability.IPlayerRecipeData;
import com.illusivesoulworks.polymorph.client.RecipesWidget;
import com.illusivesoulworks.polymorph.common.integration.util.RecipeTransfer;
import com.illusivesoulworks.polymorph.mixin.core.AccessorSmithingScreen;
import java.util.HashSet;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.SmithingMenu;

public class ClientPacketHandler {
   public static void handle(SPacketPlayerRecipeSync packet) {
      LocalPlayer clientPlayerEntity = Minecraft.getInstance().player;
      if (clientPlayerEntity != null) {
         IPlayerRecipeData recipeData = PolymorphApi.getInstance().getPlayerRecipeData(clientPlayerEntity);
         if (recipeData != null) {
            recipeData.setRecipesList(sort(packet.recipeList().orElse(new HashSet<>())));
            packet.selected()
               .flatMap(resourceLocation -> clientPlayerEntity.level().getRecipeManager().byKey(resourceLocation))
               .ifPresent(recipeData::setSelectedRecipe);
         }
      }
   }

   public static void handle(SPacketRecipesList packet) {
      LocalPlayer clientPlayerEntity = Minecraft.getInstance().player;
      if (clientPlayerEntity != null) {
         Optional<IRecipesWidget> maybeWidget = RecipesWidget.get();
         maybeWidget.ifPresent(widget -> widget.setRecipesList(sort(packet.recipeList().orElse(new HashSet<>())), packet.selected().orElse(null)));
         if (maybeWidget.isEmpty()) {
            RecipesWidget.enqueueRecipesList(sort(packet.recipeList().orElse(new HashSet<>())), packet.selected().orElse(null));
         }
      }
   }

   public static void handle(SPacketHighlightRecipe packet) {
      LocalPlayer clientPlayerEntity = Minecraft.getInstance().player;
      if (clientPlayerEntity != null) {
         RecipesWidget.get().ifPresent(widget -> widget.highlightRecipe(packet.recipe()));
      }
   }

   private static SortedSet<IRecipePair> sort(HashSet<IRecipePair> set) {
      return new TreeSet<>(set);
   }

   public static void handle(SPacketUpdatePreview unused) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.screen instanceof SmithingScreen smithingScreen) {
         ((AccessorSmithingScreen)smithingScreen).callUpdateArmorStandPreview(((SmithingMenu)smithingScreen.getMenu()).getSlot(3).getItem());
      }
   }

   public static void handle(SPacketRecipeHandshake unused) {
      IRecipesWidget widget = PolymorphWidgets.getInstance().getCurrentWidget();
      ResourceLocation resourceLocation = RecipeTransfer.getTransfer();
      if (widget != null && resourceLocation != null) {
         widget.selectRecipe(resourceLocation);
         RecipeTransfer.enqueueTransfer(null);
      }
   }
}
