package net.jadenxgamer.elysium_api.impl.compat;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import java.util.List;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public class ElysiumEmiBrewingRecipe implements EmiRecipe {
   private static final ResourceLocation BACKGROUND = ResourceLocation.parse("minecraft:textures/gui/container/brewing_stand.png");
   private static final EmiStack BLAZE_POWDER = EmiStack.of(Items.BLAZE_POWDER);
   private final EmiIngredient input;
   private final EmiIngredient ingredient;
   private final EmiStack output;
   private final EmiStack input3;
   private final EmiStack output3;
   private final ResourceLocation id;

   public ElysiumEmiBrewingRecipe(EmiStack input, EmiIngredient ingredient, EmiStack output, ResourceLocation id) {
      this.input = input;
      this.ingredient = ingredient;
      this.output = output;
      this.input3 = input.copy().setAmount(3L);
      this.output3 = output.copy().setAmount(3L);
      this.id = id;
   }

   public EmiRecipeCategory getCategory() {
      return VanillaEmiRecipeCategories.BREWING;
   }

   @Nullable
   public ResourceLocation getId() {
      return ResourceLocation.fromNamespaceAndPath(this.id.getNamespace(), "/" + this.id.getPath());
   }

   public List<EmiIngredient> getInputs() {
      return List.of(this.input3, this.ingredient);
   }

   public List<EmiStack> getOutputs() {
      return List.of(this.output3);
   }

   public int getDisplayWidth() {
      return 120;
   }

   public int getDisplayHeight() {
      return 61;
   }

   public void addWidgets(WidgetHolder widgets) {
      widgets.addTexture(BACKGROUND, 0, 0, 103, 61, 16, 14);
      widgets.addAnimatedTexture(BACKGROUND, 81, 2, 9, 28, 176, 0, 20000, false, false, false)
         .tooltip((mx, my) -> List.of(ClientTooltipComponent.create(ordered(Component.translatable("emi.cooking.time", new Object[]{20})))));
      widgets.addAnimatedTexture(BACKGROUND, 47, 0, 12, 29, 185, 0, 700, false, true, false);
      widgets.addTexture(BACKGROUND, 44, 30, 18, 4, 176, 29);
      widgets.addSlot(BLAZE_POWDER, 0, 2).drawBack(false);
      widgets.addSlot(this.input, 39, 36).drawBack(false);
      widgets.addSlot(this.ingredient, 62, 2).drawBack(false);
      widgets.addSlot(this.output, 85, 36).drawBack(false).recipeContext(this);
   }

   public static FormattedCharSequence ordered(Component text) {
      return text.getVisualOrderText();
   }
}
