package com.mortal_cultivation.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mortal_cultivation.registry.RecipeRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class AlchemyFurnaceRecipe implements Recipe<AlchemyFurnaceRecipe.Input> {
   private static final int[] MATERIAL_SLOTS = new int[]{0, 2, 3};
   private final NonNullList<Ingredient> ingredients;
   private final List<ItemStack> results;
   private final int cookingTime;
   private final Map<Ingredient, Integer> ingredientCounts;

   public AlchemyFurnaceRecipe(NonNullList<Ingredient> ingredients, List<ItemStack> results, int cookingTime, Map<Ingredient, Integer> ingredientCounts) {
      this.ingredients = ingredients;
      this.results = results;
      this.cookingTime = cookingTime;
      this.ingredientCounts = ingredientCounts;
   }

   public boolean matches(AlchemyFurnaceRecipe.Input container, Level level) {
      return this.getInputConsumption(container).isPresent();
   }

   public Optional<int[]> getInputConsumption(AlchemyFurnaceRecipe.Input input) {
      List<Entry<Ingredient, Integer>> requirements = List.copyOf(this.ingredientCounts.entrySet());
      if (requirements.isEmpty()) {
         return Optional.empty();
      }

      int firstIngredient = 1 + MATERIAL_SLOTS.length;
      int sink = firstIngredient + requirements.size();
      int[][] remaining = new int[sink + 1][sink + 1];
      int[] available = new int[MATERIAL_SLOTS.length];
      long requiredTotal = 0L;

      for (int i = 0; i < requirements.size(); i++) {
         int count = requirements.get(i).getValue();
         if (count <= 0) {
            return Optional.empty();
         }

         remaining[firstIngredient + i][sink] = count;
         requiredTotal += count;
      }

      long availableTotal = 0L;

      for (int i = 0; i < MATERIAL_SLOTS.length; i++) {
         ItemStack stack = input.getItem(MATERIAL_SLOTS[i]);
         if (!stack.isEmpty()) {
            available[i] = stack.getCount();
            availableTotal += available[i];
            remaining[0][1 + i] = available[i];
            boolean matchesIngredient = false;

            for (int j = 0; j < requirements.size(); j++) {
               if (requirements.get(j).getKey().test(stack)) {
                  remaining[1 + i][firstIngredient + j] = available[i];
                  matchesIngredient = true;
               }
            }

            if (!matchesIngredient) {
               return Optional.empty();
            }
         }
      }

      if (availableTotal < requiredTotal) {
         return Optional.empty();
      }

      long assigned = 0L;

      while (assigned < requiredTotal) {
         int[] parent = new int[sink + 1];
         Arrays.fill(parent, -1);
         parent[0] = 0;
         int[] queue = new int[sink + 1];
         int end = 1;

         for (int start = 0; start < end && parent[sink] == -1; start++) {
            int node = queue[start];

            for (int next = 1; next <= sink; next++) {
               if (parent[next] == -1 && remaining[node][next] > 0) {
                  parent[next] = node;
                  queue[end++] = next;
               }
            }
         }

         if (parent[sink] == -1) {
            return Optional.empty();
         }

         int count = Integer.MAX_VALUE;

         for (int node = sink; node != 0; node = parent[node]) {
            count = Math.min(count, remaining[parent[node]][node]);
         }

         for (int node = sink; node != 0; node = parent[node]) {
            remaining[parent[node]][node] = remaining[parent[node]][node] - count;
            remaining[node][parent[node]] = remaining[node][parent[node]] + count;
         }

         assigned += count;
      }

      int[] consumption = new int[input.size()];

      for (int i = 0; i < MATERIAL_SLOTS.length; i++) {
         if (MATERIAL_SLOTS[i] < consumption.length) {
            consumption[MATERIAL_SLOTS[i]] = available[i] - remaining[0][1 + i];
         }
      }

      return Optional.of(consumption);
   }

   public ItemStack assemble(AlchemyFurnaceRecipe.Input input, Provider provider) {
      return this.getResultItem(provider);
   }

   public boolean canCraftInDimensions(int width, int height) {
      return true;
   }

   public ItemStack getResultItem(Provider provider) {
      return this.results.isEmpty() ? ItemStack.EMPTY : this.results.get(0).copy();
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)RecipeRegistry.ALCHEMY_FURNACE_SERIALIZER.get();
   }

   public RecipeType<?> getType() {
      return (RecipeType<?>)RecipeRegistry.ALCHEMY_FURNACE_TYPE.get();
   }

   public List<ItemStack> getResults() {
      return this.results;
   }

   public int getCookingTime() {
      return this.cookingTime;
   }

   public NonNullList<Ingredient> getIngredients() {
      return this.ingredients;
   }

   public Map<Ingredient, Integer> getIngredientCounts() {
      return this.ingredientCounts;
   }

   public List<Integer> getIngredientCountsList() {
      List<Integer> counts = new ArrayList<>(this.ingredients.size());

      for (Ingredient ingredient : this.ingredients) {
         counts.add(this.ingredientCounts.getOrDefault(ingredient, 1));
      }

      return counts;
   }

   private static AlchemyFurnaceRecipe fromSerializedData(List<Ingredient> ingredients, List<ItemStack> results, int cookingTime, List<Integer> counts) {
      NonNullList<Ingredient> ingredientList = NonNullList.create();
      ingredientList.addAll(ingredients);
      Map<Ingredient, Integer> ingredientCounts = new HashMap<>();

      for (int index = 0; index < ingredientList.size(); index++) {
         ingredientCounts.put((Ingredient)ingredientList.get(index), index < counts.size() ? counts.get(index) : 1);
      }

      return new AlchemyFurnaceRecipe(ingredientList, List.copyOf(results), cookingTime, ingredientCounts);
   }

   public record Input(List<ItemStack> items) implements RecipeInput {
      public Input(List<ItemStack> items) {
         this.items = List.copyOf(items);
      }

      public ItemStack getItem(int index) {
         return index >= 0 && index < this.items.size() ? this.items.get(index) : ItemStack.EMPTY;
      }

      public int size() {
         return this.items.size();
      }

      public boolean isEmpty() {
         return this.items.stream().allMatch(ItemStack::isEmpty);
      }
   }

   public static class Serializer implements RecipeSerializer<AlchemyFurnaceRecipe> {
      private static final MapCodec<AlchemyFurnaceRecipe> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(recipe -> List.copyOf(recipe.getIngredients())),
               ItemStack.CODEC.listOf().fieldOf("results").forGetter(AlchemyFurnaceRecipe::getResults),
               Codec.INT.optionalFieldOf("cookingTime", 200).forGetter(AlchemyFurnaceRecipe::getCookingTime),
               Codec.INT.listOf().optionalFieldOf("counts", List.of()).forGetter(AlchemyFurnaceRecipe::getIngredientCountsList)
            )
            .apply(instance, AlchemyFurnaceRecipe::fromSerializedData)
      );
      private static final StreamCodec<RegistryFriendlyByteBuf, AlchemyFurnaceRecipe> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, AlchemyFurnaceRecipe>() {
         public AlchemyFurnaceRecipe decode(RegistryFriendlyByteBuf buffer) {
            int ingredientCount = (Integer)ByteBufCodecs.VAR_INT.decode(buffer);
            List<Ingredient> ingredients = new ArrayList<>(ingredientCount);
            List<Integer> counts = new ArrayList<>(ingredientCount);

            for (int i = 0; i < ingredientCount; i++) {
               ingredients.add((Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
               counts.add((Integer)ByteBufCodecs.VAR_INT.decode(buffer));
            }

            int resultCount = (Integer)ByteBufCodecs.VAR_INT.decode(buffer);
            List<ItemStack> results = new ArrayList<>(resultCount);

            for (int i = 0; i < resultCount; i++) {
               results.add((ItemStack)ItemStack.STREAM_CODEC.decode(buffer));
            }

            int cookingTime = (Integer)ByteBufCodecs.VAR_INT.decode(buffer);
            return AlchemyFurnaceRecipe.fromSerializedData(ingredients, results, cookingTime, counts);
         }

         public void encode(RegistryFriendlyByteBuf buffer, AlchemyFurnaceRecipe recipe) {
            ByteBufCodecs.VAR_INT.encode(buffer, recipe.ingredients.size());

            for (int i = 0; i < recipe.ingredients.size(); i++) {
               Ingredient ingredient = (Ingredient)recipe.ingredients.get(i);
               Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
               ByteBufCodecs.VAR_INT.encode(buffer, recipe.ingredientCounts.getOrDefault(ingredient, 1));
            }

            ByteBufCodecs.VAR_INT.encode(buffer, recipe.results.size());

            for (ItemStack result : recipe.results) {
               ItemStack.STREAM_CODEC.encode(buffer, result);
            }

            ByteBufCodecs.VAR_INT.encode(buffer, recipe.cookingTime);
         }
      };

      public MapCodec<AlchemyFurnaceRecipe> codec() {
         return CODEC;
      }

      public StreamCodec<RegistryFriendlyByteBuf, AlchemyFurnaceRecipe> streamCodec() {
         return STREAM_CODEC;
      }
   }

   public static class Type implements RecipeType<AlchemyFurnaceRecipe> {
      public static final AlchemyFurnaceRecipe.Type INSTANCE = new AlchemyFurnaceRecipe.Type();

      private Type() {
      }
   }
}
