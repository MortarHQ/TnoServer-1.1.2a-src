package dev.ftb.mods.ftbxmodcompat.ftbquests.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftbquests.quest.loot.WeightedReward;
import dev.ftb.mods.ftbquests.registry.ModDataComponents;
import dev.ftb.mods.ftbquests.registry.ModItems;
import dev.ftb.mods.ftbxmodcompat.ftbquests.recipemod_common.LootCrateTextRenderer;
import dev.ftb.mods.ftbxmodcompat.ftbquests.recipemod_common.WrappedLootCrate;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class LootCrateCategory implements IRecipeCategory<WrappedLootCrate> {
   private final IDrawable background;
   private final IDrawable icon;

   public LootCrateCategory(IGuiHelper guiHelper) {
      this.background = guiHelper.createBlankDrawable(144, 144);
      this.icon = new IDrawable() {
         public int getWidth() {
            return 16;
         }

         public int getHeight() {
            return 16;
         }

         public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
            PoseStack poseStack = graphics.pose();
            poseStack.pushPose();
            poseStack.translate(xOffset + 8, yOffset + 8, 100.0F);
            List<WrappedLootCrate> crates = LootCrateRecipeManagerPlugin.INSTANCE.getWrappedLootCrates();
            if (!crates.isEmpty()) {
               GuiHelper.drawItem(graphics, crates.get((int)(System.currentTimeMillis() / 1000L % crates.size())).crateStack, 0, true, null);
            } else {
               GuiHelper.drawItem(graphics, new ItemStack((ItemLike)ModItems.LOOTCRATE.get()), 0, true, null);
            }

            poseStack.popPose();
         }
      };
   }

   public RecipeType<WrappedLootCrate> getRecipeType() {
      return JEIRecipeTypes.LOOT_CRATE;
   }

   public Component getTitle() {
      return Component.translatable("jei.ftbquests.lootcrates");
   }

   public int getWidth() {
      return this.background.getWidth();
   }

   public int getHeight() {
      return this.background.getHeight();
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, WrappedLootCrate recipe, IFocusGroup focuses) {
      ItemStack catStack = recipe.crateStack.copy();
      catStack.set((DataComponentType)ModDataComponents.LOOT_CRATE.get(), recipe.crate.getStringID());
      builder.addSlot(RecipeIngredientRole.CATALYST, 30, 11).addIngredient(VanillaTypes.ITEM_STACK, catStack);

      for (int slot = 0; slot < Math.min(48, recipe.outputs.size()); slot++) {
         int finalSlot = slot;
         ((IRecipeSlotBuilder)builder.addSlot(RecipeIngredientRole.OUTPUT, slot % 8 * 18, slot / 8 * 18 + 36)
               .addIngredients(recipe.outputIngredients().get(slot)))
            .addRichTooltipCallback(
               (recipeSlotView, tooltip) -> recipeSlotView.getDisplayedIngredient()
                  .flatMap(ingr -> ingr.getIngredient(VanillaTypes.ITEM_STACK))
                  .ifPresent(
                     stack -> {
                        if (ItemStack.isSameItemSameComponents(stack, recipe.outputs.get(finalSlot))) {
                           String chanceStr = ChatFormatting.GOLD
                              + WeightedReward.chanceString(recipe.sortedRewards.get(finalSlot).getWeight(), recipe.crate.getTable().getTotalWeight(true));
                           tooltip.add(Component.translatable("jei.ftbquests.lootcrates.chance", new Object[]{chanceStr}).withStyle(ChatFormatting.GRAY));
                        }
                     }
                  )
            );
      }
   }

   public void draw(WrappedLootCrate recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
      LootCrateTextRenderer.drawText(graphics, recipe.crate, 0, this.background.getWidth());
   }
}
