package it.hurts.octostudios.reliquified_twilight_forest.client.gui.tooltip;

import it.hurts.octostudios.reliquified_twilight_forest.gui.tooltip.BundleLikeTooltip;
import it.hurts.octostudios.reliquified_twilight_forest.item.component.BundleLikeContents;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientBundleLikeTooltip implements ClientTooltipComponent {
   public static final ResourceLocation EMPTY_SLOT = ResourceLocation.fromNamespaceAndPath(
      "reliquified_twilight_forest", "textures/gui/tooltip/bundle_like/empty_slot.png"
   );
   public BundleLikeContents contents;

   public ClientBundleLikeTooltip(BundleLikeTooltip tooltip) {
      this.contents = tooltip.contents;
   }

   public int getRowCount() {
      return 9;
   }

   public int getItemGapX() {
      return 0;
   }

   public int getItemGapY() {
      return 0;
   }

   public int getHeight() {
      return this.contents.getSize() == 0 ? 0 : (16 + this.getItemGapY()) * ((this.contents.getSize() - 1) / this.getRowCount() + 1) + 4;
   }

   public int getWidth(Font font) {
      return this.contents.getSize() >= this.getRowCount()
         ? (16 + this.getItemGapX()) * this.getRowCount()
         : this.contents.getSize() * (16 + this.getItemGapX());
   }

   public void renderItem(ItemStack stack, int x, int y, GuiGraphics guiGraphics, Font font) {
      guiGraphics.renderItem(stack, x, y);
      guiGraphics.renderItemDecorations(font, stack, x, y);
   }

   public void renderSlot(ItemStack stack, int x, int y, GuiGraphics guiGraphics, Font font) {
      if (stack.isEmpty()) {
         guiGraphics.blit(EMPTY_SLOT, x, y, 0.0F, 0.0F, 16, 16, 16, 16);
      } else {
         this.renderItem(stack, x, y, guiGraphics, font);
      }
   }

   public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
      for (int i = 0; i < this.contents.getSize(); i++) {
         ItemStack stack = this.contents.get(i);
         int ix = x + i % this.getRowCount() * (16 + this.getItemGapX());
         int iy = y + i / this.getRowCount() * (16 + this.getItemGapY());
         this.renderSlot(stack, ix, iy, guiGraphics, font);
      }
   }
}
