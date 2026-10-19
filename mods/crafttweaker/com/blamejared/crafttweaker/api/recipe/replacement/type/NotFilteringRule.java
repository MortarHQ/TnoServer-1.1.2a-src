package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.GenericRecipesManager;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/recipe/replacement/type/NotFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.NotFilteringRule")
@ZenRegister
public final class NotFilteringRule implements IFilteringRule {
   private final IFilteringRule rule;

   private NotFilteringRule(IFilteringRule rule) {
      this.rule = rule;
   }

   @ZenCodeType.Method
   public static NotFilteringRule of(IFilteringRule rule) {
      return new NotFilteringRule(rule);
   }

   @Override
   public Stream<RecipeHolder<?>> castFilter(Stream<RecipeHolder<?>> allRecipes) {
      Set<RecipeHolder<?>> toRemove = this.rule.castFilter(GenericRecipesManager.INSTANCE.getAllRecipesRaw().stream()).collect(Collectors.toSet());
      return allRecipes.filter(it -> !toRemove.contains(it));
   }

   @Override
   public String describe() {
      return "the opposite of " + this.rule;
   }
}
