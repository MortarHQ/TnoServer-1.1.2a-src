package mezz.jei.library.plugins.debug;

import java.util.List;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.recipe.advanced.ISimpleRecipeManagerPlugin;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ItemLike;

public class DebugSimpleRecipeManagerPlugin implements ISimpleRecipeManagerPlugin<RecipeHolder<CraftingRecipe>> {
   private final IVanillaRecipeFactory vanillaRecipeFactory;

   public DebugSimpleRecipeManagerPlugin(IJeiHelpers jeiHelpers) {
      this.vanillaRecipeFactory = jeiHelpers.getVanillaRecipeFactory();
   }

   @Override
   public boolean isHandledInput(ITypedIngredient<?> input) {
      ItemStack itemStack = input.getItemStack().orElse(ItemStack.EMPTY);
      return itemStack.is(Items.LIGHT);
   }

   @Override
   public boolean isHandledOutput(ITypedIngredient<?> output) {
      ItemStack itemStack = output.getItemStack().orElse(ItemStack.EMPTY);
      return itemStack.is(Items.DARK_PRISMARINE_SLAB);
   }

   @Override
   public List<RecipeHolder<CraftingRecipe>> getRecipesForInput(ITypedIngredient<?> input) {
      return List.of(this.generateRecipe());
   }

   @Override
   public List<RecipeHolder<CraftingRecipe>> getRecipesForOutput(ITypedIngredient<?> output) {
      return List.of(this.generateRecipe());
   }

   @Override
   public List<RecipeHolder<CraftingRecipe>> getAllRecipes() {
      return List.of(this.generateRecipe());
   }

   private RecipeHolder<CraftingRecipe> generateRecipe() {
      CraftingRecipe recipe = this.vanillaRecipeFactory
         .createShapedRecipeBuilder(CraftingBookCategory.MISC, List.of(new ItemStack(Items.DARK_PRISMARINE_SLAB)))
         .pattern("lll")
         .pattern("lll")
         .pattern("lll")
         .define('l', Ingredient.of(new ItemLike[]{Items.LIGHT}))
         .build();
      ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("jei", "debug_simple_recipe");
      return new RecipeHolder(resourceLocation, recipe);
   }
}
