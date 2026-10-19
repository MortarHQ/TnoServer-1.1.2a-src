package fuzs.puzzleslib.api.data.v2;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.JsonOps;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractRecipeProvider extends RecipeProvider {
   protected final String modId;

   public AbstractRecipeProvider(DataProviderContext context) {
      this(context.getModId(), context.getPackOutput(), context.getRegistries());
   }

   public AbstractRecipeProvider(String modId, PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
      super(packOutput, lookupProvider);
      this.modId = modId;
   }

   @Nullable
   protected static <T> JsonElement searchAndReplaceValue(@Nullable JsonElement jsonElement, T searchFor, T replaceWith) {
      Objects.requireNonNull(searchFor, "search for is null");
      Objects.requireNonNull(replaceWith, "replace with is null");
      if (jsonElement != null && !jsonElement.isJsonNull()) {
         if (jsonElement.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (jsonPrimitive.isNumber()) {
               if (searchFor.equals(jsonPrimitive.getAsNumber())) {
                  return new JsonPrimitive((Number)replaceWith);
               }
            } else if (jsonPrimitive.isBoolean()) {
               if (searchFor.equals(jsonPrimitive.getAsBoolean())) {
                  return new JsonPrimitive((Boolean)replaceWith);
               }
            } else if (jsonPrimitive.isString() && searchFor.toString().equals(jsonPrimitive.getAsString())) {
               return new JsonPrimitive(replaceWith.toString());
            }

            return jsonElement;
         }

         if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
               jsonArray.set(i, searchAndReplaceValue(jsonArray.get(i), searchFor, replaceWith));
            }
         } else if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
               entry.setValue(searchAndReplaceValue(entry.getValue(), searchFor, replaceWith));
            }
         }
      }

      return jsonElement;
   }

   public static String getItemName(Ingredient ingredient) {
      return getItemName(Arrays.stream(ingredient.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new));
   }

   public static String getItemName(ItemLike... items) {
      Preconditions.checkState(items.length > 0, "items is empty");
      return Arrays.stream(items).<CharSequence>map(RecipeProvider::getItemName).collect(Collectors.joining("_or_"));
   }

   public static String getConversionRecipeName(ItemLike result, Ingredient ingredient) {
      return getConversionRecipeName(result, Arrays.stream(ingredient.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new));
   }

   public static String getConversionRecipeName(ItemLike result, ItemLike... items) {
      Preconditions.checkState(items.length > 0, "items is empty");
      return getItemName(result) + "_from_" + getItemName(items);
   }

   public static String getHasName(Ingredient ingredient) {
      return getHasName(Arrays.stream(ingredient.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new));
   }

   public static String getHasName(ItemLike... items) {
      Preconditions.checkState(items.length > 0, "items is empty");
      return "has_" + getItemName(items);
   }

   public static Criterion<TriggerInstance> has(Ingredient ingredient) {
      return has(Arrays.stream(ingredient.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new));
   }

   public static Criterion<TriggerInstance> has(ItemLike... items) {
      Preconditions.checkState(items.length > 0, "items is empty");
      return inventoryTrigger(new ItemPredicate[]{Builder.item().of(items).build()});
   }

   public static void stonecutterResultFromBase(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, Ingredient material) {
      stonecutterResultFromBase(recipeOutput, category, result, material, 1);
   }

   public static void stonecutterResultFromBase(RecipeOutput recipeOutput, RecipeCategory category, ItemLike result, Ingredient material, int resultCount) {
      SingleItemRecipeBuilder.stonecutting(material, category, result, resultCount)
         .unlockedBy(getHasName(material), has(material))
         .save(recipeOutput, getConversionRecipeName(result, material) + "_stonecutting");
   }

   public CompletableFuture<?> run(CachedOutput output, Provider registries) {
      List<CompletableFuture<?>> completableFutures = new ArrayList<>();
      this.buildRecipes(new AbstractRecipeProvider.IdentifiableRecipeOutput(output, registries, completableFutures));
      return CompletableFuture.allOf(completableFutures.toArray(CompletableFuture[]::new));
   }

   public final void buildRecipes(RecipeOutput recipeOutput) {
      this.addRecipes(recipeOutput);
   }

   public abstract void addRecipes(RecipeOutput var1);

   public class IdentifiableRecipeOutput implements RecipeOutput {
      private final CachedOutput output;
      private final Provider registries;
      private final List<CompletableFuture<?>> completableFutures;
      private final Set<ResourceLocation> generatedRecipes = new HashSet<>();

      public IdentifiableRecipeOutput(CachedOutput output, Provider registries, List<CompletableFuture<?>> completableFutures) {
         this.output = output;
         this.registries = registries;
         this.completableFutures = completableFutures;
      }

      public String getModId() {
         return AbstractRecipeProvider.this.modId;
      }

      public void accept(ResourceLocation location, Recipe<?> recipe, @Nullable AdvancementHolder advancement) {
         ResourceLocation oldLocation = location;
         location = ResourceLocationHelper.fromNamespaceAndPath(AbstractRecipeProvider.this.modId, location.getPath());
         if (!this.generatedRecipes.add(location)) {
            throw new IllegalStateException("Duplicate recipe " + location);
         }

         this.completableFutures
            .add(DataProvider.saveStable(this.output, this.registries, Recipe.CODEC, recipe, AbstractRecipeProvider.this.recipePathProvider.json(location)));
         if (advancement != null) {
            RegistryOps<JsonElement> registryOps = this.registries.createSerializationContext(JsonOps.INSTANCE);
            JsonElement jsonElement = (JsonElement)Advancement.CODEC.encodeStart(registryOps, advancement.value()).getOrThrow();
            jsonElement = AbstractRecipeProvider.searchAndReplaceValue(jsonElement, oldLocation, location);
            ResourceLocation advancementLocation = ResourceLocationHelper.fromNamespaceAndPath(AbstractRecipeProvider.this.modId, advancement.id().getPath());
            this.completableFutures
               .add(DataProvider.saveStable(this.output, jsonElement, AbstractRecipeProvider.this.advancementPathProvider.json(advancementLocation)));
         }
      }

      public net.minecraft.advancements.Advancement.Builder advancement() {
         return net.minecraft.advancements.Advancement.Builder.recipeAdvancement().parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT);
      }
   }
}
