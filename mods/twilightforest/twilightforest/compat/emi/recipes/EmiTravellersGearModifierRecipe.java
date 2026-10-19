package twilightforest.compat.emi.recipes;

import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.recipe.EmiPatternCraftingRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.GeneratedSlotWidget;
import dev.emi.emi.api.widget.SlotWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import twilightforest.item.recipe.travellers.TravellersGearModifierRecipe;

public class EmiTravellersGearModifierRecipe extends EmiPatternCraftingRecipe {
   private final TravellersGearModifierRecipe recipe;
   private final int uniqueSeed = EmiUtil.RANDOM.nextInt();

   public EmiTravellersGearModifierRecipe(TravellersGearModifierRecipe recipe) {
      super(recipe.getIngredients().stream().map(EmiIngredient::of).toList(), EmiStack.EMPTY, recipe.getId(), recipe.isShapeless());
      this.recipe = recipe;
   }

   public ResourceLocation getId() {
      return super.getId().withPrefix("/");
   }

   public SlotWidget getInputWidget(int slot, int x, int y) {
      return slot < this.input.size() ? new SlotWidget((EmiIngredient)this.input.get(slot), x, y) : new SlotWidget(EmiStack.EMPTY, x, y);
   }

   public SlotWidget getOutputWidget(int x, int y) {
      return new GeneratedSlotWidget(
         rand -> EmiStack.of(
            this.recipe
               .applyModifier(
                  Minecraft.getInstance().level.registryAccess(),
                  TravellersGearModifierRecipe.getModifiableArmorFromIngredients(this.recipe.getIngredients()),
                  this.recipe.getIngredients()
               )
         ),
         this.uniqueSeed,
         x,
         y
      );
   }
}
