package team.lodestar.lodestone.systems.recipe;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class LodestoneInWorldRecipe<T extends RecipeInput> implements Recipe<T> {
   private final RecipeSerializer<?> recipeSerializer;
   private final RecipeType<?> recipeType;
   public final ItemStack output;

   public LodestoneInWorldRecipe(RecipeSerializer<?> recipeSerializer, RecipeType<?> recipeType, ItemStack output) {
      this.recipeSerializer = recipeSerializer;
      this.recipeType = recipeType;
      this.output = output;
   }

   public LodestoneInWorldRecipe(RecipeSerializer<?> recipeSerializer, RecipeType<?> recipeType) {
      this(recipeSerializer, recipeType, ItemStack.EMPTY);
   }

   public ItemStack assemble(T input, Provider registries) {
      return this.getResultItem(registries).copy();
   }

   public ItemStack getResultItem(Provider registries) {
      return this.output;
   }

   public boolean canCraftInDimensions(int width, int height) {
      return true;
   }

   public RecipeSerializer<?> getSerializer() {
      return this.recipeSerializer;
   }

   public RecipeType<?> getType() {
      return this.recipeType;
   }
}
