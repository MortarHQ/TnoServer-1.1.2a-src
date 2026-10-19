package twilightforest.compat.emi.recipes;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.compat.emi.TFEmiCategories;
import twilightforest.compat.emi.widget.EmiItemEntityWidget;

public class EmiCrumbleHornRecipe implements EmiRecipe {
   private static final int WIDTH = 116;
   private static final int HEIGHT = 46;
   private final Block input;
   private final Block output;

   public EmiCrumbleHornRecipe(Block input, Block output) {
      this.input = input;
      this.output = output;
   }

   public EmiRecipeCategory getCategory() {
      return TFEmiCategories.CRUMBLE_HORN;
   }

   @Nullable
   public ResourceLocation getId() {
      return TwilightForestMod.prefix(
         "/crumbling/" + BuiltInRegistries.BLOCK.getKey(this.input).getNamespace() + "/" + BuiltInRegistries.BLOCK.getKey(this.input).getPath()
      );
   }

   public List<EmiIngredient> getInputs() {
      return List.of(EmiStack.of(this.input));
   }

   public List<EmiStack> getOutputs() {
      return List.of(EmiStack.of(this.output));
   }

   public int getDisplayWidth() {
      return 116;
   }

   public int getDisplayHeight() {
      return 46;
   }

   public void addWidgets(WidgetHolder widgets) {
      widgets.addTexture(EmiTexture.EMPTY_ARROW, 44, 15);
      if (this.output != Blocks.AIR) {
         widgets.addSlot(this.getInputs().getFirst(), 14, 14);
         widgets.addSlot((EmiIngredient)this.getOutputs().getFirst(), 76, 10).large(true).recipeContext(this);
      } else {
         widgets.addSlot(this.getInputs().getFirst(), 14, 14).recipeContext(this);
         widgets.add(new EmiItemEntityWidget(this.input, 76, 10));
      }
   }
}
