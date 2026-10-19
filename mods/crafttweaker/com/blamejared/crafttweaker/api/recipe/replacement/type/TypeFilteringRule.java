package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/recipe/replacement/type/ManagerFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.ManagerFilteringRule")
@ZenRegister
public final class TypeFilteringRule implements IFilteringRule {
   private final Set<IRecipeManager<?>> managers;
   private final Set<RecipeType<?>> types;

   private TypeFilteringRule(Set<IRecipeManager<?>> managers) {
      this.managers = Set.copyOf(managers);
      this.types = this.managers.stream().map(IRecipeManager::getRecipeType).collect(Collectors.toSet());
   }

   @ZenCodeType.Method
   public static IFilteringRule of(IRecipeManager<?>... managers) {
      return new TypeFilteringRule(Set.of(managers));
   }

   @Override
   public Stream<RecipeHolder<?>> castFilter(Stream<RecipeHolder<?>> allRecipes) {
      return allRecipes.filter(it -> this.types.contains(it.value().getType()));
   }

   @Override
   public String describe() {
      return this.managers.stream().map(IRecipeManager::getCommandString).collect(Collectors.joining(", ", "recipes from managers {", "}"));
   }
}
