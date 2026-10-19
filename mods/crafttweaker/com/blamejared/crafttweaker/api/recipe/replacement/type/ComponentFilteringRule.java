package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.DescriptivePredicate;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/recipe/replacement/type/ComponentFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.ComponentFilteringRule")
@ZenRegister
public final class ComponentFilteringRule<T> implements IFilteringRule {
   private final IRecipeComponent<T> component;
   private final DescriptivePredicate<T> thing;
   private final ITargetingStrategy checkStrategy;

   private ComponentFilteringRule(IRecipeComponent<T> component, DescriptivePredicate<T> thing, ITargetingStrategy checkStrategy) {
      this.component = component;
      this.thing = thing;
      this.checkStrategy = checkStrategy;
   }

   @ZenCodeType.Method
   public static <T> ComponentFilteringRule<T> of(IRecipeComponent<T> component) {
      return of(component, null);
   }

   @ZenCodeType.Method
   public static <T> ComponentFilteringRule<T> of(IRecipeComponent<T> component, T content) {
      return of(component, content, ITargetingStrategy.find(ITargetingStrategy.DEFAULT_STRATEGY_ID));
   }

   @ZenCodeType.Method
   public static <T> ComponentFilteringRule<T> of(IRecipeComponent<T> component, T content, ITargetingStrategy checkStrategy) {
      DescriptivePredicate<T> predicate = content == null ? null : DescriptivePredicate.of(it -> component.match(content, it), content.toString());
      return new ComponentFilteringRule<>(component, predicate, checkStrategy);
   }

   @ZenCodeType.Method
   public static <T> ComponentFilteringRule<T> of(IRecipeComponent<T> component, Predicate<T> content, ITargetingStrategy checkStrategy) {
      return new ComponentFilteringRule<>(component, content == null ? null : DescriptivePredicate.wrap(content), checkStrategy);
   }

   @Override
   public Stream<RecipeHolder<?>> castFilter(Stream<RecipeHolder<?>> allRecipes) {
      return allRecipes.filter(this::castFilter);
   }

   @Override
   public String describe() {
      return "recipes with component %s%s"
         .formatted(
            this.component.getCommandString(),
            this.thing == null ? "" : " matching %s according to strategy %s".formatted(this.thing.describe(), this.checkStrategy.getCommandString())
         );
   }

   private <C extends RecipeInput, V extends Recipe<C>> boolean castFilter(RecipeHolder<?> recipe) {
      RecipeHolder<V> typedRecipe = GenericUtil.uncheck(recipe);
      IRecipeHandler<V> handler = CraftTweakerAPI.getRegistry().getRecipeHandlerFor(typedRecipe);
      IRecipeManager<? super V> manager = RecipeTypeBracketHandler.getOrDefault(recipe.value().getType());
      Optional<IDecomposedRecipe> decomposedRecipe = handler.decompose(manager, AccessibleElementsProvider.get().registryAccess(), (V)typedRecipe.value());
      return decomposedRecipe.isPresent() && this.castFilter(decomposedRecipe.get());
   }

   private boolean castFilter(IDecomposedRecipe recipe) {
      return this.thing != null ? this.castFullFilter(recipe) : this.castBasicFilter(recipe);
   }

   private boolean castFullFilter(IDecomposedRecipe recipe) {
      return this.castBasicFilter(recipe) && this.castComponentFilter(recipe);
   }

   private boolean castBasicFilter(IDecomposedRecipe recipe) {
      return recipe.components().contains(this.component);
   }

   private boolean castComponentFilter(IDecomposedRecipe recipe) {
      for (T element : recipe.get(this.component)) {
         T result = this.checkStrategy.castStrategy(this.component, element, this::fakeReplacement);
         if (result != null) {
            return true;
         }
      }

      return false;
   }

   private T fakeReplacement(T element) {
      return this.thing.test(element) ? element : null;
   }
}
