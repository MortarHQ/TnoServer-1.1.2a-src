package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import net.minecraft.world.item.crafting.Recipe;

public class ActionRemoveRecipeByRegex<T extends Recipe<?>> extends ActionRecipeBase<T> {
   private final Pattern compiledPat;
   private final Predicate<String> exclude;

   public ActionRemoveRecipeByRegex(IRecipeManager<T> manager, String regex, Predicate<String> exclude) {
      super(manager);
      this.compiledPat = Pattern.compile(regex);
      this.exclude = exclude;
   }

   @Override
   public void apply() {
      this.getRecipeMutator().removeByIdTest(resourceLocation -> this.compiledPat.matcher(resourceLocation.toString()).matches(), this.exclude);
   }

   @Override
   public String describe() {
      return "Removing \"" + this.getRecipeTypeName() + "\" recipe with names that match the regex: \"" + this.compiledPat.pattern() + "\"";
   }
}
