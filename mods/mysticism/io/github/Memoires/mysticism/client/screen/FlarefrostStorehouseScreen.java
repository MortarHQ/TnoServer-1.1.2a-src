package io.github.Memoires.mysticism.client.screen;

import io.github.Memoires.mysticism.menu.FlarefrostStorehouseMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FlarefrostStorehouseScreen extends AbstractContainerScreen<FlarefrostStorehouseMenu<?>> {
   private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath("mysticism", "textures/gui/flarefrost_storehouse.png");
   private static final int[] AXIOM = new int[]{75, 56};
   private static final int[] WALTZ = new int[]{118, 56};
   private static final int slotWidth = 22;
   private static final int slotHeight = 22;

   public FlarefrostStorehouseScreen(FlarefrostStorehouseMenu pMenu, Inventory pPlayerInventory) {
      super(pMenu, pPlayerInventory, pMenu.getSkill().getName());
      this.imageWidth = 214;
      this.imageHeight = 221;
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
      guiGraphics.blit(BG, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
      if (((FlarefrostStorehouseMenu)this.menu).getSlot(0).hasItem()) {
         guiGraphics.blit(BG, this.leftPos + AXIOM[0], this.topPos + AXIOM[1], 0, 223, 22, 22);
      }

      if (((FlarefrostStorehouseMenu)this.menu).getSlot(1).hasItem()) {
         guiGraphics.blit(BG, this.leftPos + WALTZ[0], this.topPos + WALTZ[1], 23, 223, 22, 22);
      }
   }

   protected void renderLabels(GuiGraphics guiGraphics, int i, int j) {
      guiGraphics.drawCenteredString(this.font, this.title, this.imageWidth / 2, this.titleLabelY, 11184810);
   }

   public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
      this.renderTooltip(guiGraphics, pMouseX, pMouseY);
   }
}
