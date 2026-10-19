package team.lodestar.lodestone.recipe.builder;

import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

public interface LodestoneRecipeBuilder<R extends Recipe<?>> {
   default void save(RecipeOutput recipeOutput, ItemLike definingItem) {
      this.save(recipeOutput, BuiltInRegistries.ITEM.getKey(definingItem.asItem()));
   }

   default void save(RecipeOutput recipeOutput, ResourceLocation id) {
      Builder advancement$builder = recipeOutput.advancement()
         .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
         .rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(id))
         .requirements(Strategy.OR);
      this.tweakAdvancement(advancement$builder);
      this.write(recipeOutput, id, this.buildRecipe(id), advancement$builder);
   }

   default void tweakAdvancement(Builder advancement) {
   }

   R buildRecipe(ResourceLocation var1);

   default void write(RecipeOutput consumer, ResourceLocation id, R recipe, Builder advancement) {
      String subfolder = this.getRecipeSubfolder();
      if (!subfolder.isEmpty()) {
         subfolder = subfolder + "/";
      }

      consumer.accept(id.withPrefix(subfolder), recipe, advancement.build(id.withPrefix("recipes/" + subfolder)));
   }

   default String getRecipeSubfolder() {
      return "";
   }
}
