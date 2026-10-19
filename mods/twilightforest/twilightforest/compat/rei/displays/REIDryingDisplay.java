package twilightforest.compat.rei.displays;

import java.util.List;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay.Serializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import twilightforest.block.DryingRackBlock;
import twilightforest.compat.rei.TFREIClientPlugin;
import twilightforest.compat.rei.categories.REIDryingCategory;
import twilightforest.init.TFBlocks;
import twilightforest.item.recipe.DryingRecipe;

public class REIDryingDisplay extends BasicDisplay {
   private final int dryingTime;

   public REIDryingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, int dryingTime) {
      super(inputs, outputs);
      this.dryingTime = dryingTime;
   }

   public static REIDryingDisplay of(DryingRecipe recipe) {
      List<EntryIngredient> inputs = List.of(
         EntryIngredients.ofIngredient(recipe.getInput()),
         EntryIngredient.of(EntryStack.of(TFREIClientPlugin.BLOCKSTATE_DEFINITION, ((DryingRackBlock)TFBlocks.OAK_DRYING_RACK.get()).defaultBlockState()))
      );
      List<EntryIngredient> outputs = List.of(EntryIngredients.of(recipe.getResult()));
      return new REIDryingDisplay(inputs, outputs, recipe.getDryingTime());
   }

   public int getDryingTime() {
      return this.dryingTime;
   }

   public CategoryIdentifier<?> getCategoryIdentifier() {
      return REIDryingCategory.DRYING;
   }

   public static Serializer<REIDryingDisplay> serializer() {
      return Serializer.of(
         (input, output, location1, tag) -> new REIDryingDisplay(input, output, tag.getInt("drying_time")),
         (display, tag) -> tag.putInt("drying_time", display.getDryingTime())
      );
   }
}
