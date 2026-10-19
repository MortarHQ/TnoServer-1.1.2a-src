package net.p3pp3rf1y.sophisticatedcore.crafting;

import javax.annotation.Nullable;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;

public class HoldingRecipeOutput implements RecipeOutput {
   private final Builder advancement;
   private Recipe<?> recipe;
   @Nullable
   private AdvancementHolder advancementHolder;
   private ICondition[] conditions;

   public HoldingRecipeOutput(Builder advancement) {
      this.advancement = advancement;
   }

   public Builder advancement() {
      return this.advancement;
   }

   public void accept(ResourceLocation id, Recipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition... conditions) {
      this.recipe = recipe;
      this.advancementHolder = advancement;
      this.conditions = conditions;
   }

   public Recipe<?> getRecipe() {
      return this.recipe;
   }

   @Nullable
   public AdvancementHolder getAdvancementHolder() {
      return this.advancementHolder;
   }

   public ICondition[] getConditions() {
      return this.conditions;
   }
}
