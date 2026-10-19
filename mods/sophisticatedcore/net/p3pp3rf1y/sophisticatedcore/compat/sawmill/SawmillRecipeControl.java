package net.p3pp3rf1y.sophisticatedcore.compat.sawmill;

import net.mehvahdjukaar.sawmill.SawmillMod;
import net.mehvahdjukaar.sawmill.WoodcuttingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.upgrades.blockconverter.BlockConverterRecipeControl;

public class SawmillRecipeControl extends BlockConverterRecipeControl<WoodcuttingRecipe, SawmillRecipeContainer> {
   protected SawmillRecipeControl(StorageScreenBase<?> screen, SawmillRecipeContainer container, Position position) {
      super(screen, container, position, true);
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      super.renderBg(guiGraphics, minecraft, mouseX, mouseY);
      if (this.getInputCount() > 1) {
         guiGraphics.drawString(minecraft.font, "x" + this.getInputCount(), this.x + 52, this.y + 10, 4210752, false);
      }
   }

   private int getInputCount() {
      return this.container.getSelectedRecipe() >= 0 && this.container.getSelectedRecipe() < this.container.getRecipeList().size()
         ? ((WoodcuttingRecipe)((RecipeHolder)this.container.getRecipeList().get(this.container.getSelectedRecipe())).value()).getInputCount()
         : 0;
   }

   @Override
   protected SoundEvent getSelectRecipeSound() {
      return (SoundEvent)SawmillMod.SAWMILL_SELECT.get();
   }
}
