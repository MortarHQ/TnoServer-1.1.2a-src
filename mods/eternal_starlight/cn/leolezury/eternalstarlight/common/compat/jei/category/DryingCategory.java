package cn.leolezury.eternalstarlight.common.compat.jei.category;

import cn.leolezury.eternalstarlight.common.item.recipe.DryingRecipe;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.gui.widgets.ITextWidget;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.library.util.RecipeUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class DryingCategory extends AbstractRecipeCategory<RecipeHolder<DryingRecipe>> {
   public static final RecipeType<RecipeHolder<DryingRecipe>> DRYING = RecipeType.createFromVanilla(ESRecipes.DRYING.get());

   public DryingCategory(IGuiHelper guiHelper) {
      super(
         DRYING,
         Component.translatable("gui.eternal_starlight.jei.category.drying"),
         guiHelper.createDrawableItemLike((ItemLike)ESItems.DRYING_RACK.get()),
         82,
         44
      );
   }

   public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<DryingRecipe> recipeHolder, IFocusGroup focuses) {
      DryingRecipe recipe = (DryingRecipe)recipeHolder.value();
      builder.addInputSlot(1, 9).setStandardSlotBackground().addIngredients(recipe.input());
      builder.addOutputSlot(61, 9).setOutputSlotBackground().addItemStack(RecipeUtil.getResultItem(recipe));
      if (recipe.fireBelow()) {
         builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 1, 28)
            .addItemStacks(
               StreamSupport.<Holder>stream(BuiltInRegistries.BLOCK.getTagOrEmpty(BlockTags.CAMPFIRES).spliterator(), false)
                  .map(holder -> ((Block)holder.value()).asItem().getDefaultInstance())
                  .collect(Collectors.toList())
            );
      }
   }

   public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<DryingRecipe> recipeHolder, IFocusGroup focuses) {
      DryingRecipe recipe = (DryingRecipe)recipeHolder.value();
      builder.addRecipeArrow().setPosition(26, 9);
      this.addDryTime(builder, recipe.durationTicks());
   }

   protected void addDryTime(IRecipeExtrasBuilder builder, int dryTime) {
      if (dryTime > 0) {
         int dryTimeSeconds = dryTime / 20;
         Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", new Object[]{dryTimeSeconds});
         ((ITextWidget)builder.addText(timeString, this.getWidth(), 10)
               .setPosition(0, 0, this.getWidth(), this.getHeight(), HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM))
            .setTextAlignment(HorizontalAlignment.RIGHT)
            .setTextAlignment(VerticalAlignment.BOTTOM)
            .setColor(-8355712);
      }
   }
}
