package twilightforest.compat.jei.categories;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec3;
import twilightforest.block.DryingRackBlock;
import twilightforest.compat.RecipeViewerConstants;
import twilightforest.init.TFBlocks;
import twilightforest.item.recipe.DryingRecipe;

public class DryingCategory implements IRecipeCategory<DryingRecipe> {
   public static final RecipeType<DryingRecipe> DRYING = RecipeType.create("twilightforest", "drying", DryingRecipe.class);
   private final IDrawable icon;
   private final IDrawable arrow;
   private final Component localizedName;

   public DryingCategory(IGuiHelper helper) {
      this.arrow = helper.createAnimatedRecipeArrow(1200);
      this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ((DryingRackBlock)TFBlocks.SORTING_DRYING_RACK.get()).asItem().getDefaultInstance());
      this.localizedName = Component.translatable("gui.twilightforest.drying_jei");
   }

   public RecipeType<DryingRecipe> getRecipeType() {
      return DRYING;
   }

   public Component getTitle() {
      return this.localizedName;
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public int getWidth() {
      return 70;
   }

   public int getHeight() {
      return 30;
   }

   public void draw(DryingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
      this.arrow.draw(graphics, 23, 1);
      Minecraft minecraft = Minecraft.getInstance();
      Font font = minecraft.font;
      Component time = RecipeViewerConstants.getDryingTime(recipe.getDryingTime());
      graphics.drawString(font, time, 35 - font.width(time.getString()) / 2, 20, -8355712, false);
      RecipeViewerConstants.renderFlatBlock(
         graphics.pose(), ((DryingRackBlock)TFBlocks.OAK_DRYING_RACK.get()).defaultBlockState(), new Vec3(-1.0, 19.0, 201.0), 20.0F
      );
      RecipeViewerConstants.renderFlatBlock(
         graphics.pose(), ((DryingRackBlock)TFBlocks.OAK_DRYING_RACK.get()).defaultBlockState(), new Vec3(51.0, 19.0, 201.0), 20.0F
      );
   }

   public void getTooltip(ITooltipBuilder tooltip, DryingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
      if (mouseX > 23.0 && mouseX < 47.0 && mouseY > 1.0 && mouseY < 17.0) {
         tooltip.add(
            Component.translatable("gui.twilightforest.drying_ticks", new Object[]{recipe.getDryingTime()})
               .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY})
         );
      }
   }

   public void setRecipe(IRecipeLayoutBuilder builder, DryingRecipe recipe, IFocusGroup focuses) {
      builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients((Ingredient)recipe.getIngredients().getFirst());
      builder.addSlot(RecipeIngredientRole.OUTPUT, 53, 1).addItemStack(recipe.getResult());
   }
}
