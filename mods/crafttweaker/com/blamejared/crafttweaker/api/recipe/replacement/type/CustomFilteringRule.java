package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/recipe/replacement/type/CustomFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.CustomFilteringRule")
@ZenRegister
public final class CustomFilteringRule implements IFilteringRule {
   private final BiPredicate<IRecipeManager<?>, RecipeHolder<?>> predicate;
   private final boolean requiresComputation;

   private CustomFilteringRule(BiPredicate<IRecipeManager<?>, RecipeHolder<?>> predicate, boolean requiresComputation) {
      this.predicate = predicate;
      this.requiresComputation = requiresComputation;
   }

   public static CustomFilteringRule of(Predicate<RecipeHolder<?>> predicate) {
      return new CustomFilteringRule((a, b) -> predicate.test(b), false);
   }

   public static CustomFilteringRule of(BiPredicate<IRecipeManager<?>, RecipeHolder<?>> predicate) {
      return new CustomFilteringRule(predicate, true);
   }

   @ZenCodeType.Method("of")
   public static CustomFilteringRule ofZen(Predicate<RecipeHolder<Recipe<RecipeInput>>> predicate) {
      return of(GenericUtil.uncheck(predicate));
   }

   @ZenCodeType.Method("of")
   public static CustomFilteringRule ofZen(BiPredicate<IRecipeManager<Recipe<RecipeInput>>, RecipeHolder<Recipe<RecipeInput>>> predicate) {
      return of(GenericUtil.uncheck(predicate));
   }

   @Override
   public Stream<RecipeHolder<?>> castFilter(Stream<RecipeHolder<?>> allRecipes) {
      return allRecipes.filter(this::castFilter);
   }

   @Override
   public String describe() {
      return "a custom set of recipes";
   }

   private <C extends RecipeInput, T extends Recipe<C>> boolean castFilter(RecipeHolder<?> holder) {
      IRecipeManager<? super T> manager = this.requiresComputation ? RecipeTypeBracketHandler.getOrDefault(holder.value().getType()) : null;
      return this.predicate.test(manager, holder);
   }
}
