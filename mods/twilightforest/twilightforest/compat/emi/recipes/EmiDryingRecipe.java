package twilightforest.compat.emi.recipes;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.DryingRackBlock;
import twilightforest.compat.RecipeViewerConstants;
import twilightforest.compat.emi.TFEmiCategories;
import twilightforest.compat.emi.widget.EmiBlockWidget;
import twilightforest.init.TFBlocks;
import twilightforest.item.recipe.DryingRecipe;

public class EmiDryingRecipe implements EmiRecipe {
   private static final int WIDTH = 70;
   private static final int HEIGHT = 30;
   private final RecipeHolder<DryingRecipe> recipe;
   private final Ingredient input;
   private final ItemStack output;

   public EmiDryingRecipe(RecipeHolder<DryingRecipe> recipe) {
      this.recipe = recipe;
      this.input = ((DryingRecipe)recipe.value()).getInput();
      this.output = ((DryingRecipe)recipe.value()).getResult();
   }

   public EmiRecipeCategory getCategory() {
      return TFEmiCategories.DRYING;
   }

   @Nullable
   public ResourceLocation getId() {
      return this.recipe.id();
   }

   public List<EmiIngredient> getInputs() {
      return List.of(EmiIngredient.of(this.input));
   }

   public List<EmiStack> getOutputs() {
      return List.of(EmiStack.of(this.output));
   }

   public int getDisplayWidth() {
      return 70;
   }

   public int getDisplayHeight() {
      return 30;
   }

   public void addWidgets(WidgetHolder widgets) {
      widgets.addFillingArrow(22, 0, 60000);
      widgets.addSlot(this.getInputs().getFirst(), 0, 0).drawBack(false);
      widgets.add(new EmiBlockWidget(((DryingRackBlock)TFBlocks.OAK_DRYING_RACK.get()).defaultBlockState(), -1, 19));
      widgets.add(new EmiBlockWidget(((DryingRackBlock)TFBlocks.OAK_DRYING_RACK.get()).defaultBlockState(), 50, 19));
      widgets.addSlot((EmiIngredient)this.getOutputs().getFirst(), 51, 0).drawBack(false).recipeContext(this);
      Component time = RecipeViewerConstants.getDryingTime(((DryingRecipe)this.recipe.value()).getDryingTime());
      widgets.addText(time, 35 - Minecraft.getInstance().font.width(time.getString()) / 2, 20, -8355712, false);
      widgets.addTooltipText(
         List.of(
            Component.translatable("gui.twilightforest.drying_ticks", new Object[]{((DryingRecipe)this.recipe.value()).getDryingTime()})
               .withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY})
         ),
         22,
         1,
         24,
         17
      );
   }
}
