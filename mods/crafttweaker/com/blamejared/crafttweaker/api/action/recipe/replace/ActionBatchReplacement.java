package com.blamejared.crafttweaker.api.action.recipe.replace;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.GenericRecipesManager;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker.api.recipe.replacement.IReplacerRegistry;
import com.blamejared.crafttweaker.api.recipe.replacement.ReplacementRequest;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;

public final class ActionBatchReplacement extends CraftTweakerAction implements IRuntimeAction {
   private final Collection<IFilteringRule> targetingRules;
   private final Collection<ReplacementRequest<?>> requests;
   private final IReplacerRegistry registry;

   private ActionBatchReplacement(Collection<IFilteringRule> targetingRules, Collection<ReplacementRequest<?>> requests) {
      this.targetingRules = targetingRules;
      this.requests = requests;
      this.registry = CraftTweakerAPI.getRegistry().getReplacerRegistry();
   }

   public static ActionBatchReplacement of(Collection<IFilteringRule> targetingRules, Collection<ReplacementRequest<?>> requests) {
      return new ActionBatchReplacement(targetingRules, requests);
   }

   @Override
   public void apply() {
      this.castFilters(GenericRecipesManager.INSTANCE.getAllRecipesRaw().stream()).forEach(this::replace);
   }

   @Override
   public String describe() {
      Collector<CharSequence, ?, String> joiner = Collectors.joining(",", "{", "}");
      return "Replacing in %s according to requests %s"
         .formatted(
            this.targetingRules.isEmpty() ? "everything" : this.targetingRules.stream().map(IFilteringRule::describe).collect(joiner),
            this.requests.stream().map(ReplacementRequest::describe).collect(joiner)
         );
   }

   private Stream<RecipeHolder<?>> castFilters(Stream<RecipeHolder<?>> recipeStream) {
      return Stream.concat(this.registry.filters().stream(), this.targetingRules.stream())
         .reduce((a, b) -> it -> b.castFilter(a.castFilter(it)))
         .map(it -> it.castFilter(recipeStream))
         .orElse(recipeStream);
   }

   private <C extends RecipeInput, T extends Recipe<C>> void replace(RecipeHolder<?> recipe) {
      RecipeHolder<T> typedRecipe = GenericUtil.uncheck(recipe);
      IRecipeHandler<T> handler = CraftTweakerAPI.getRegistry().getRecipeHandlerFor(typedRecipe);
      IRecipeManager<? super T> manager = RecipeTypeBracketHandler.getOrDefault(recipe.value().getType());
      handler.decompose(manager, AccessibleElementsProvider.get().registryAccess(), (T)typedRecipe.value())
         .ifPresent(it -> this.replace(manager, handler, typedRecipe.id(), it));
   }

   private <C extends RecipeInput, T extends Recipe<C>> void replace(
      IRecipeManager<? super T> manager, IRecipeHandler<T> handler, ResourceLocation name, IDecomposedRecipe recipe
   ) {
      if (this.apply(recipe)) {
         CraftTweakerAPI.apply(new ActionReplaceRecipe<>(name, manager, newName -> GenericUtil.uncheck(this.rebuild(recipe, manager, handler, newName))));
      }
   }

   private boolean apply(IDecomposedRecipe recipe) {
      boolean any = false;

      for (ReplacementRequest<?> request : this.requests) {
         any |= request.applyRequest(recipe);
      }

      return any;
   }

   private <C extends RecipeInput, T extends Recipe<C>> RecipeHolder<T> rebuild(
      IDecomposedRecipe recipe, IRecipeManager<? super T> manager, IRecipeHandler<T> handler, ResourceLocation newName
   ) {
      return handler.recompose(manager, AccessibleElementsProvider.get().registryAccess(), recipe)
         .map(it -> new RecipeHolder(newName, it))
         .orElseThrow(() -> new IllegalStateException("Recomposition failed due to an error"));
   }
}
