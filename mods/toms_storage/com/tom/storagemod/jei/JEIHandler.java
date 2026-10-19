package com.tom.storagemod.jei;

import com.tom.storagemod.Content;
import com.tom.storagemod.screen.AbstractFilteredScreen;
import com.tom.storagemod.screen.CraftingTerminalScreen;
import com.tom.storagemod.screen.PlatformContainerScreen;
import com.tom.storagemod.util.IAutoFillTerminal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

@JeiPlugin
public class JEIHandler implements IModPlugin {
   private static IJeiRuntime jeiRuntime;

   public ResourceLocation getPluginUid() {
      return ResourceLocation.tryBuild("toms_storage", "jei");
   }

   public void registerGuiHandlers(IGuiHandlerRegistration registration) {
      registration.addGuiContainerHandler(CraftingTerminalScreen.class, new IGuiContainerHandler<CraftingTerminalScreen>() {
         private RecipeType[] rt = new RecipeType[]{RecipeTypes.CRAFTING};

         public Collection<IGuiClickableArea> getGuiClickableAreas(CraftingTerminalScreen containerScreen, double mouseX, double mouseY) {
            int rowCount = containerScreen.getRowCount();
            IGuiClickableArea clickableArea = IGuiClickableArea.createBasic(83, 35 + rowCount * 18, 28, 23, this.rt);
            return List.of(clickableArea);
         }
      });
      registration.addGhostIngredientHandler(AbstractFilteredScreen.class, new JeiGhostIngredientHandler());
      registration.addGenericGuiContainerHandler(PlatformContainerScreen.class, new IGuiContainerHandler<PlatformContainerScreen<?>>() {
         public List<Rect2i> getGuiExtraAreas(PlatformContainerScreen<?> s) {
            List<Rect2i> rects = new ArrayList<>();
            s.getExclusionAreas(b -> rects.add(new Rect2i(b.x(), b.y(), b.width(), b.height())));
            return rects;
         }
      });
   }

   public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
      CraftingTerminalTransferHandler.registerTransferHandlers(registration);
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
      registration.addRecipeCatalyst(new ItemStack((ItemLike)Content.craftingTerminal.get()), new RecipeType[]{RecipeTypes.CRAFTING});
   }

   public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
      JEIHandler.jeiRuntime = jeiRuntime;
   }

   static {
      IAutoFillTerminal.updateSearch
         .add(
            new IAutoFillTerminal.ISearchHandler() {
               @Override
               public void setSearch(String text) {
                  if (JEIHandler.jeiRuntime != null && JEIHandler.jeiRuntime.getIngredientFilter() != null) {
                     JEIHandler.jeiRuntime.getIngredientFilter().setFilterText(text);
                  }
               }

               @Override
               public String getSearch() {
                  return JEIHandler.jeiRuntime != null && JEIHandler.jeiRuntime.getIngredientFilter() != null
                     ? JEIHandler.jeiRuntime.getIngredientFilter().getFilterText()
                     : "";
               }

               @Override
               public String getName() {
                  return "JEI";
               }
            }
         );
   }
}
