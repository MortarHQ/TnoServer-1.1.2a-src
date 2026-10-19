package com.mortal_cultivation.compat.emi;

import com.mortal_cultivation.MortalCultivation;
import com.mortal_cultivation.data.recipe.TalismanRecipe;
import com.mortal_cultivation.recipe.AlchemyFurnaceRecipe;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.RecipeRegistry;
import com.mortal_cultivation.registry.item.CultivationItems;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

@EmiEntrypoint
public class MortalCultivationEmiPlugin implements EmiPlugin {
   public static final MortalCultivationEmiCategory ALCHEMY_FURNACE_CATEGORY = new MortalCultivationEmiCategory(
      ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "alchemy_furnace"),
      EmiStack.of((ItemLike)BlockRegistry.ALCHEMY_FURNACE.get()),
      Component.translatable("gui.mortal_cultivation.category.alchemy_furnace")
   );
   public static final MortalCultivationEmiCategory TALISMAN_CATEGORY = new MortalCultivationEmiCategory(
      ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "talisman_making"),
      EmiStack.of((ItemLike)CultivationItems.TALISMAN_PAPER.get()),
      Component.translatable("category.mortal_cultivation.talisman_making")
   );

   public void register(EmiRegistry registry) {
      registry.addCategory(ALCHEMY_FURNACE_CATEGORY);
      registry.addCategory(TALISMAN_CATEGORY);
      registry.addWorkstation(ALCHEMY_FURNACE_CATEGORY, EmiStack.of((ItemLike)BlockRegistry.ALCHEMY_FURNACE.get()));
      registry.addWorkstation(TALISMAN_CATEGORY, EmiStack.of((ItemLike)CultivationItems.TALISMAN_PAPER.get()));
      List<RecipeHolder<AlchemyFurnaceRecipe>> alchemyRecipes = registry.getRecipeManager()
         .getAllRecipesFor((RecipeType)RecipeRegistry.ALCHEMY_FURNACE_TYPE.get());
      MortalCultivation.LOGGER.info("Registering {} alchemy furnace recipes to EMI", alchemyRecipes.size());
      alchemyRecipes.stream().map(AlchemyFurnaceEmiRecipe::new).forEach(registry::addRecipe);
      List<RecipeHolder<TalismanRecipe>> talismanRecipes = registry.getRecipeManager().getAllRecipesFor((RecipeType)RecipeRegistry.TALISMAN_TYPE.get());
      MortalCultivation.LOGGER.info("Registering {} talisman recipes to EMI", talismanRecipes.size());
      talismanRecipes.stream().map(TalismanMakingEmiRecipe::new).forEach(registry::addRecipe);
   }
}
