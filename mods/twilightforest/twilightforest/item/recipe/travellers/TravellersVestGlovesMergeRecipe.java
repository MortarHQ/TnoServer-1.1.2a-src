package twilightforest.item.recipe.travellers;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFItems;
import twilightforest.init.TFRecipes;

public class TravellersVestGlovesMergeRecipe extends CustomRecipe {
   public TravellersVestGlovesMergeRecipe(CraftingBookCategory category) {
      super(category);
   }

   public boolean matches(CraftingInput input, Level level) {
      Optional<TravellersVestGlovesMergeRecipe.InputPair> pair = this.resolve(input);
      if (pair.isEmpty()) {
         return false;
      }

      ItemStack vest = pair.get().vest();
      return !vest.has(TFDataComponents.TRAVELLERS_HAS_GLOVES);
   }

   public ItemStack assemble(CraftingInput input, Provider registries) {
      Optional<TravellersVestGlovesMergeRecipe.InputPair> pair = this.resolve(input);
      if (pair.isEmpty()) {
         return ItemStack.EMPTY;
      }

      ItemStack vest = pair.get().vest().copy();
      vest.set(TFDataComponents.TRAVELLERS_HAS_GLOVES, Unit.INSTANCE);
      return vest;
   }

   public boolean canCraftInDimensions(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)TFRecipes.TRAVELLERS_VEST_GLOVES_MERGE_RECIPE_SERIALIZER.get();
   }

   private Optional<TravellersVestGlovesMergeRecipe.InputPair> resolve(CraftingInput input) {
      List<ItemStack> items = input.items().stream().filter(stack -> !stack.isEmpty()).toList();
      if (items.size() != 2) {
         return Optional.empty();
      }

      Optional<ItemStack> vest = items.stream().filter(s -> s.is((Item)TFItems.TRAVELLERS_VEST.get())).findFirst();
      Optional<ItemStack> gloves = items.stream().filter(s -> s.is((Item)TFItems.TRAVELLERS_GLOVES.get())).findFirst();
      return vest.flatMap(w -> gloves.map(b -> new TravellersVestGlovesMergeRecipe.InputPair(w, b)));
   }

   private record InputPair(ItemStack vest, ItemStack gloves) {
   }
}
