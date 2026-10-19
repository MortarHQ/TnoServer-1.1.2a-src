package net.p3pp3rf1y.sophisticatedbackpacks.crafting;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.crafting.IWrapperRecipe;
import net.p3pp3rf1y.sophisticatedcore.crafting.RecipeWrapperSerializer;

public class BasicBackpackRecipe extends ShapedRecipe implements IWrapperRecipe<ShapedRecipe> {
   private final ShapedRecipe compose;

   public BasicBackpackRecipe(ShapedRecipe compose) {
      super(compose.getGroup(), compose.category(), compose.pattern, compose.result);
      this.compose = compose;
   }

   public ShapedRecipe getCompose() {
      return this.compose;
   }

   public ItemStack assemble(CraftingInput inv, Provider registries) {
      ItemStack result = super.assemble(inv, registries);
      this.removeUuid(result);
      return result;
   }

   private void removeUuid(ItemStack backpack) {
      BackpackWrapper.fromStack(backpack).removeContentsUuid();
   }

   public RecipeSerializer<?> getSerializer() {
      return ModItems.BASIC_BACKPACK_RECIPE_SERIALIZER.get();
   }

   public static class Serializer extends RecipeWrapperSerializer<ShapedRecipe, BasicBackpackRecipe> {
      public Serializer() {
         super(BasicBackpackRecipe::new, RecipeSerializer.SHAPED_RECIPE);
      }
   }
}
