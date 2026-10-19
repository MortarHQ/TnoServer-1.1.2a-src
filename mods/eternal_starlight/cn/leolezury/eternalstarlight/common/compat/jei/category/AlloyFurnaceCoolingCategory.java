package cn.leolezury.eternalstarlight.common.compat.jei.category;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.compat.jei.recipe.AlloyFurnaceCoolingRecipe;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.gui.widgets.ITextWidget;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.common.gui.elements.DrawableAnimated;
import mezz.jei.common.gui.elements.DrawableCombined;
import mezz.jei.library.gui.elements.DrawableBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class AlloyFurnaceCoolingCategory extends AbstractRecipeCategory<AlloyFurnaceCoolingRecipe> {
   public static final RecipeType<AlloyFurnaceCoolingRecipe> ALLOY_FURNACE_COOLING = RecipeType.create(
      "eternal_starlight", "alloy_furnace_cooling", AlloyFurnaceCoolingRecipe.class
   );

   public AlloyFurnaceCoolingCategory(IGuiHelper guiHelper) {
      super(
         ALLOY_FURNACE_COOLING,
         Component.translatable("gui.eternal_starlight.jei.category.alloy_furnace_cooling"),
         guiHelper.createDrawableItemLike((ItemLike)ESItems.FROZEN_TUBE.get()),
         getMaxWidth(),
         34
      );
   }

   private static int getMaxWidth() {
      Minecraft minecraft = Minecraft.getInstance();
      Font fontRenderer = minecraft.font;
      int maxStringWidth = Math.max(
         fontRenderer.width(createDurationText(2000000000).getString()), fontRenderer.width(createEfficiencyText(2000000000).getString())
      );
      int textPadding = 20;
      return 18 + textPadding + maxStringWidth;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, AlloyFurnaceCoolingRecipe recipe, IFocusGroup focuses) {
      builder.addInputSlot(1, 17).setStandardSlotBackground().addItemLike(recipe.item());
   }

   public void createRecipeExtras(IRecipeExtrasBuilder builder, AlloyFurnaceCoolingRecipe recipe, IFocusGroup focuses) {
      int duration = recipe.cooling().duration();
      int efficiency = recipe.cooling().efficiency();
      IDrawableStatic coolingIcon = new DrawableBuilder(EternalStarlight.id("textures/gui/jei/alloy_furnace/cooling_progress.png"), 0, 0, 15, 15)
         .setTextureSize(15, 15)
         .build();
      IDrawableAnimated animatedFill = new DrawableAnimated(coolingIcon, duration, StartDirection.TOP, true);
      IDrawable drawableCombined = new DrawableCombined(
         new IDrawable[]{
            new DrawableBuilder(EternalStarlight.id("textures/gui/jei/alloy_furnace/cooling_background.png"), 0, 0, 15, 15).setTextureSize(15, 15).build(),
            animatedFill
         }
      );
      builder.addDrawable(drawableCombined).setPosition(1, 0);
      ((ITextWidget)builder.addText(List.of(createDurationText(duration), createEfficiencyText(efficiency)), this.getWidth() - 20, this.getHeight())
            .setPosition(20, 0))
         .setTextAlignment(HorizontalAlignment.CENTER)
         .setTextAlignment(VerticalAlignment.CENTER)
         .setColor(-8355712);
   }

   public static Component createDurationText(int duration) {
      int durationSeconds = duration / 20;
      return Component.translatable("gui.jei.category.smelting.time.seconds", new Object[]{durationSeconds});
   }

   public static Component createEfficiencyText(int efficiency) {
      return Component.translatable("gui.eternal_starlight.jei.category.alloy_furnace_cooling.efficiency", new Object[]{efficiency});
   }

   @Nullable
   public ResourceLocation getRegistryName(AlloyFurnaceCoolingRecipe recipe) {
      return null;
   }
}
