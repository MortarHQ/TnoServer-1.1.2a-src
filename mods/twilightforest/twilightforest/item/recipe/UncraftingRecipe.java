package twilightforest.item.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;
import twilightforest.init.TFRecipes;

public class UncraftingRecipe extends ShapedRecipe {
   private final int cost;
   private final Ingredient input;
   private final int count;
   private final ShapedRecipePattern pattern;

   public UncraftingRecipe(int cost, Ingredient input, int count, ShapedRecipePattern pattern) {
      super("uncrafting", CraftingBookCategory.MISC, pattern, new ItemStack(Items.AIR, count));
      this.cost = cost;
      this.input = input;
      this.count = count;
      this.pattern = pattern;
   }

   public boolean matches(CraftingInput input, Level level) {
      return false;
   }

   public ItemStack assemble(CraftingInput input, Provider provider) {
      return ItemStack.EMPTY;
   }

   public boolean isItemStackAnIngredient(ItemStack stack) {
      return Arrays.stream(this.input.getItems()).anyMatch(i -> stack.getItem() == i.getItem() && stack.getCount() >= this.count);
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)TFRecipes.UNCRAFTING_SERIALIZER.get();
   }

   public RecipeType<?> getType() {
      return (RecipeType<?>)TFRecipes.UNCRAFTING_RECIPE.get();
   }

   public Ingredient getInput() {
      return this.input;
   }

   public int getCount() {
      return this.count;
   }

   public int getCost() {
      return this.cost;
   }

   public static class Serializer implements RecipeSerializer<UncraftingRecipe> {
      public static final MapCodec<UncraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Codec.INT.optionalFieldOf("cost", -1).forGetter(o -> o.cost),
               Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(o -> o.input),
               Codec.INT.optionalFieldOf("input_count", 1).forGetter(o -> o.count),
               ShapedRecipePattern.MAP_CODEC.forGetter(o -> o.pattern)
            )
            .apply(instance, UncraftingRecipe::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, UncraftingRecipe> STREAM_CODEC = StreamCodec.of(
         UncraftingRecipe.Serializer::toNetwork, UncraftingRecipe.Serializer::fromNetwork
      );

      public MapCodec<UncraftingRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, UncraftingRecipe> streamCodec() {
         return STREAM_CODEC;
      }

      public static UncraftingRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
         int cost = buf.readInt();
         Ingredient input = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
         int count = buf.readInt();
         ShapedRecipePattern pattern = (ShapedRecipePattern)ShapedRecipePattern.STREAM_CODEC.decode(buf);
         return new UncraftingRecipe(cost, input, count, pattern);
      }

      public static void toNetwork(RegistryFriendlyByteBuf buf, UncraftingRecipe recipe) {
         buf.writeInt(recipe.cost);
         Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.input);
         buf.writeInt(recipe.count);
         ShapedRecipePattern.STREAM_CODEC.encode(buf, recipe.pattern);
      }
   }
}
