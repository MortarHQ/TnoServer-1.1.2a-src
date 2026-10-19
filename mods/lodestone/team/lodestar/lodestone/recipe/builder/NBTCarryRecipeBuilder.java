package team.lodestar.lodestone.recipe.builder;

import java.util.Objects;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.recipe.NBTCarryRecipe;

public class NBTCarryRecipeBuilder extends ShapedRecipeBuilder implements LodestoneRecipeBuilder<NBTCarryRecipe> {
   public final Ingredient copyFrom;

   public NBTCarryRecipeBuilder(RecipeCategory category, ItemStack result, Ingredient copyFrom) {
      super(category, result);
      this.copyFrom = copyFrom;
   }

   public NBTCarryRecipe buildRecipe(ResourceLocation id) {
      ShapedRecipe recipe = new ShapedRecipe(
         Objects.requireNonNullElse(this.group, ""),
         RecipeBuilder.determineBookCategory(this.category),
         this.ensureValid(id),
         this.resultStack,
         this.showNotification
      );
      return new NBTCarryRecipe(recipe, this.copyFrom);
   }

   public void save(RecipeOutput recipeOutput) {
      this.save(recipeOutput, BuiltInRegistries.ITEM.getKey(this.getResult()));
   }

   public void save(RecipeOutput recipeOutput, String id) {
      this.save(recipeOutput, ResourceLocation.parse(id));
   }

   public NBTCarryRecipeBuilder define(Character symbol, TagKey<Item> tag) {
      return (NBTCarryRecipeBuilder)super.define(symbol, tag);
   }

   public NBTCarryRecipeBuilder define(Character symbol, ItemLike item) {
      return (NBTCarryRecipeBuilder)super.define(symbol, item);
   }

   public NBTCarryRecipeBuilder define(Character symbol, Ingredient ingredient) {
      return (NBTCarryRecipeBuilder)super.define(symbol, ingredient);
   }

   public NBTCarryRecipeBuilder pattern(String pattern) {
      return (NBTCarryRecipeBuilder)super.pattern(pattern);
   }

   public NBTCarryRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
      return (NBTCarryRecipeBuilder)super.unlockedBy(name, criterion);
   }

   public NBTCarryRecipeBuilder group(@Nullable String groupName) {
      return (NBTCarryRecipeBuilder)super.group(groupName);
   }

   public NBTCarryRecipeBuilder showNotification(boolean showNotification) {
      return (NBTCarryRecipeBuilder)super.showNotification(showNotification);
   }
}
