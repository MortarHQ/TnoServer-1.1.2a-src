package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.mod.Mod;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/recipe/replacement/type/ModsFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.ModsFilteringRule")
@ZenRegister
public final class ModsFilteringRule implements IFilteringRule {
   private final Set<String> modIds;

   private ModsFilteringRule(Set<String> modIds) {
      this.modIds = Set.copyOf(modIds);
   }

   @ZenCodeType.Method
   public static IFilteringRule of(String... modIds) {
      if (modIds.length < 1) {
         throw new IllegalArgumentException("Unable to create mods filtering rule without any mods");
      } else {
         return new ModsFilteringRule(Set.of(modIds));
      }
   }

   @ZenCodeType.Method
   public static IFilteringRule of(Mod... mods) {
      int l = mods.length;
      String[] ids = new String[l];

      for (int i = 0; i < l; i++) {
         ids[i] = mods[i].id();
      }

      return of(ids);
   }

   @Override
   public Stream<RecipeHolder<?>> castFilter(Stream<RecipeHolder<?>> allRecipes) {
      return allRecipes.filter(it -> this.modIds.contains(it.id().getNamespace()));
   }

   @Override
   public String describe() {
      return this.modIds.stream().collect(Collectors.joining(", ", "recipes from mods {", "}"));
   }
}
