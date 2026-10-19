package com.teamresourceful.resourcefullib.common.datagen;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CodecRecipeBuilder implements RecipeBuilder {
   @Nullable
   protected String group;
   protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

   @NotNull
   public RecipeBuilder unlockedBy(String string, Criterion<?> criterion) {
      this.criteria.put(string, criterion);
      return this;
   }

   @NotNull
   public RecipeBuilder group(@Nullable String string) {
      this.group = string;
      return this;
   }
}
