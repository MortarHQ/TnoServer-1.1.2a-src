package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ActionRemoveRecipe<T extends Recipe<?>> extends ActionRecipeBase<T> {
   protected final Predicate<RecipeHolder<T>> removePredicate;
   protected Function<ActionRecipeBase<T>, String> describeFunction;

   public ActionRemoveRecipe(IRecipeManager<T> manager, Predicate<RecipeHolder<T>> removePredicate) {
      super(manager);
      this.removePredicate = removePredicate;
      this.describeFunction = action -> "Removing '%s' recipes that match a custom condition".formatted(this.getRecipeTypeName());
   }

   public ActionRemoveRecipe(IRecipeManager<T> manager, Predicate<RecipeHolder<T>> removePredicate, Function<ActionRecipeBase<T>, String> describeFunction) {
      super(manager);
      this.removePredicate = removePredicate;
      this.describeFunction = describeFunction;
   }

   @Override
   public void apply() {
      this.getRecipeMutator().removeByRecipeTest(this.removePredicate);
   }

   @Override
   public String describe() {
      return this.describeFunction.apply(this);
   }

   public ActionRemoveRecipe<T> describeDefaultRemoval(CommandStringDisplayable output) {
      this.describeFunction = action -> "Removing '%s' recipes with output: '%s'".formatted(action.getRecipeType(), output);
      return this;
   }
}
