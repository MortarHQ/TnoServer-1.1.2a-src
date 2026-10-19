package net.p3pp3rf1y.sophisticatedcore.crafting;

import java.util.function.Function;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;

public class ShapeBasedRecipeBuilder extends ShapedRecipeBuilder {
   private final Function<ShapedRecipe, ? extends ShapedRecipe> factory;

   private ShapeBasedRecipeBuilder(ItemStack result, Function<ShapedRecipe, ? extends ShapedRecipe> factory) {
      super(RecipeCategory.MISC, result);
      this.factory = factory;
   }

   public static ShapeBasedRecipeBuilder shaped(ItemStack result) {
      return new ShapeBasedRecipeBuilder(result, r -> r);
   }

   public static ShapeBasedRecipeBuilder shaped(ItemLike result) {
      return shaped(new ItemStack(result));
   }

   public static ShapeBasedRecipeBuilder shaped(ItemLike result, Function<ShapedRecipe, ? extends ShapedRecipe> factory) {
      return shaped(new ItemStack(result, 1), factory);
   }

   public static ShapeBasedRecipeBuilder shaped(ItemStack result, Function<ShapedRecipe, ? extends ShapedRecipe> factory) {
      return new ShapeBasedRecipeBuilder(result, factory);
   }

   public void save(RecipeOutput recipeOutput) {
      this.save(recipeOutput, BuiltInRegistries.ITEM.getKey(this.getResult().asItem()));
   }

   public void save(RecipeOutput recipeOutput, ResourceLocation id) {
      HoldingRecipeOutput holdingRecipeOutput = new HoldingRecipeOutput(recipeOutput.advancement());
      super.save(holdingRecipeOutput, id);
      if (holdingRecipeOutput.getRecipe() instanceof ShapedRecipe compose) {
         recipeOutput.withConditions(new ICondition[]{new ItemEnabledCondition(this.getResult())})
            .accept(id, (Recipe)this.factory.apply(compose), holdingRecipeOutput.getAdvancementHolder(), holdingRecipeOutput.getConditions());
      }
   }
}
