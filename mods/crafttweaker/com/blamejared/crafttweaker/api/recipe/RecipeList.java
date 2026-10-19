package com.blamejared.crafttweaker.api.recipe;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeList<T extends Recipe<?>> {
   private final RecipeType<T> recipeType;
   private final Collection<RecipeHolder<T>> recipes;
   private final Map<ResourceLocation, RecipeHolder<T>> byName;
   private final Collection<RecipeHolder<T>> unmodifiableRecipes;
   private final Map<ResourceLocation, RecipeHolder<T>> unmodifiableByName;

   public RecipeList(RecipeType<T> recipeType, Collection<RecipeHolder<T>> recipes, Map<ResourceLocation, RecipeHolder<?>> byName) {
      this.recipeType = recipeType;
      this.recipes = recipes;
      this.byName = GenericUtil.uncheck(byName);
      this.unmodifiableRecipes = Collections.unmodifiableCollection(this.recipes);
      this.unmodifiableByName = Collections.unmodifiableMap(this.byName);
   }

   public List<RecipeHolder<T>> getRecipesByOutput(IIngredient output) {
      return AccessibleElementsProvider.get()
         .registryAccess(
            registryAccess -> this.getRecipes().stream().filter(recipe -> output.matches(IItemStack.of(recipe.value().getResultItem(registryAccess)))).toList()
         );
   }

   public List<RecipeHolder<T>> getRecipesMatching(Predicate<RecipeHolder<T>> predicate) {
      return this.getRecipes().stream().filter(predicate).toList();
   }

   public RecipeHolder<T> get(ResourceLocation id) {
      return this.getByName().get(id);
   }

   public RecipeHolder<T> get(String id) {
      return this.get(ResourceLocation.tryParse(id));
   }

   public boolean has(ResourceLocation id) {
      return this.getByName().containsKey(id);
   }

   public boolean has(String id) {
      return this.has(ResourceLocation.tryParse(id));
   }

   public void add(ResourceLocation id, RecipeHolder<T> recipe) {
      if (this.getByName().containsKey(recipe.id())) {
         CommonLoggers.api()
            .warn("A recipe with the name '{}' already exists and will be overwritten: this is most likely an error in your scripts", recipe.id().getPath());
      }

      this.recipes.add(recipe);
      this.byName.put(id, recipe);
   }

   public void remove(ResourceLocation id) {
      this.recipes.removeIf(tRecipeHolder -> tRecipeHolder.id().equals(id));
      this.byName.remove(id);
   }

   public void removeByRecipeTest(Predicate<RecipeHolder<T>> recipePredicate) {
      Iterator<RecipeHolder<T>> iterator = this.recipes.iterator();

      while (iterator.hasNext()) {
         RecipeHolder<T> next = iterator.next();
         if (recipePredicate.test(next)) {
            this.byName.remove(next.id());
            iterator.remove();
         }
      }
   }

   public void removeByIdTest(Predicate<ResourceLocation> idPredicate) {
      this.removeByIdTest(idPredicate, s -> false);
   }

   public void removeByIdTest(Predicate<ResourceLocation> idPredicate, Predicate<String> exclusions) {
      Iterator<RecipeHolder<T>> iterator = this.recipes.iterator();

      while (iterator.hasNext()) {
         ResourceLocation next = iterator.next().id();
         if (idPredicate.test(next) && !exclusions.test(next.getPath())) {
            this.byName.remove(next);
            iterator.remove();
         }
      }
   }

   public void removeAll() {
      this.byName.values().removeAll(this.recipes);
      this.recipes.clear();
   }

   public RecipeType<T> getRecipeType() {
      return this.recipeType;
   }

   public Collection<RecipeHolder<T>> getRecipes() {
      return this.unmodifiableRecipes;
   }

   public Map<ResourceLocation, RecipeHolder<T>> getByName() {
      return this.unmodifiableByName;
   }

   public int getSize() {
      return this.getRecipes().size();
   }
}
