package dev.ftb.mods.ftbxmodcompat.ftbquests.jei;

import dev.architectury.fluid.FluidStack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.registry.ModDataComponents;
import dev.ftb.mods.ftbxmodcompat.FTBXModCompat;
import dev.ftb.mods.ftbxmodcompat.ftbquests.QuestItems;
import dev.ftb.mods.ftbxmodcompat.ftbquests.jei.neoforge.FTBQuestsJEIIntegrationImpl;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IAdvancedRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@JeiPlugin
public class FTBQuestsJEIIntegration implements IModPlugin {
   private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath("ftbxmodcompat", "ftbquests_jei");
   public static IJeiRuntime runtime;

   public void onRuntimeAvailable(IJeiRuntime r) {
      runtime = FTBXModCompat.isFTBQuestsLoaded ? r : null;
      if (ClientQuestFile.exists()) {
         ClientQuestFile.INSTANCE.updateLootCrates();
      }
   }

   public ResourceLocation getPluginUid() {
      return UID;
   }

   public void registerItemSubtypes(ISubtypeRegistration r) {
      if (FTBXModCompat.isFTBQuestsLoaded) {
         r.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, QuestItems.lootCrate(), new ISubtypeInterpreter<ItemStack>() {
            @Nullable
            public Object getSubtypeData(ItemStack ingredient, UidContext context) {
               return ingredient.getOrDefault((DataComponentType)ModDataComponents.LOOT_CRATE.get(), "");
            }

            public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
               return "";
            }
         });
      }
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
      if (FTBXModCompat.isFTBQuestsLoaded) {
         registration.addRecipeCatalyst(new ItemStack(QuestItems.questBook()), new RecipeType[]{JEIRecipeTypes.QUEST});
         registration.addRecipeCatalyst(new ItemStack(QuestItems.lootCrate()), new RecipeType[]{JEIRecipeTypes.LOOT_CRATE});
      }
   }

   public void registerCategories(IRecipeCategoryRegistration r) {
      if (FTBXModCompat.isFTBQuestsLoaded) {
         r.addRecipeCategories(new IRecipeCategory[]{new QuestCategory(r.getJeiHelpers().getGuiHelper())});
         r.addRecipeCategories(new IRecipeCategory[]{new LootCrateCategory(r.getJeiHelpers().getGuiHelper())});
      }
   }

   public void registerAdvanced(IAdvancedRegistration registration) {
      if (FTBXModCompat.isFTBQuestsLoaded) {
         registration.addRecipeManagerPlugin(QuestRecipeManagerPlugin.INSTANCE);
         registration.addRecipeManagerPlugin(LootCrateRecipeManagerPlugin.INSTANCE);
      }
   }

   public static void showRecipes(ItemStack stack) {
      if (runtime != null) {
         runtime.getIngredientManager()
            .getIngredientTypeChecked(stack)
            .ifPresent(type -> runtime.getRecipesGui().show(runtime.getJeiHelpers().getFocusFactory().createFocus(RecipeIngredientRole.OUTPUT, type, stack)));
      }
   }

   public static void showRecipes(FluidStack fluid) {
      showFluidRecipes(runtime, fluid);
   }

   @ExpectPlatform
   @Transformed
   public static void showFluidRecipes(IJeiRuntime runtime, FluidStack fluid) {
      FTBQuestsJEIIntegrationImpl.showFluidRecipes(runtime, fluid);
   }
}
