package twilightforest.client.renderer.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import twilightforest.components.item.ItemDisplayContents;
import twilightforest.item.travellers_gear.TravellersGogglesItem;
import twilightforest.item.travellers_gear.modifiers.display.ItemDisplayType;

public class ItemDisplayTooltipComponent implements ClientTooltipComponent {
   private static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("container/bundle/background");
   private static final ResourceLocation SLOT_SPRITE = ResourceLocation.withDefaultNamespace("container/bundle/slot");
   private static final int SLOT_WIDTH = 18;
   private static final int SLOT_HEIGHT = 20;
   private final NonNullList<ItemStack> contents;

   public ItemDisplayTooltipComponent(TravellersGogglesItem.Tooltip tooltip) {
      this.contents = tooltip.contents().items();
   }

   public void renderImage(@NotNull Font font, int x, int y, GuiGraphics guiGraphics) {
      guiGraphics.blitSprite(BACKGROUND_SPRITE, x, y, this.backgroundWidth(), this.backgroundHeight());
      int k = 0;

      for (int gridY = 0; gridY < this.gridSizeY(); gridY++) {
         for (int gridX = 0; gridX < this.gridSizeX(); gridX++) {
            int renderX = x + gridX * 18 + 1;
            int renderY = y + gridY * 20 + 1;
            this.renderSlot(renderX, renderY, k++, guiGraphics, font);
         }
      }
   }

   private void renderSlot(int x, int y, int itemIndex, GuiGraphics graphics, Font font) {
      graphics.blitSprite(SLOT_SPRITE, x, y, 0, 18, 20);
      if (itemIndex < this.contents.size()) {
         ItemStack itemstack = (ItemStack)this.contents.get(itemIndex);
         if (itemstack.isEmpty()) {
            this.renderBlankSlot(graphics, itemIndex, x, y);
         } else {
            graphics.renderItem(itemstack, x + 1, y + 1, itemIndex);
            graphics.renderItemDecorations(font, itemstack, x + 1, y + 1);
         }
      } else {
         this.renderBlankSlot(graphics, itemIndex, x, y);
      }
   }

   private void renderBlankSlot(GuiGraphics graphics, int index, int x, int y) {
      if (index >= 0 && index < ItemDisplayContents.LAYOUT.size()) {
         ItemDisplayType type = (ItemDisplayType)ItemDisplayContents.LAYOUT.get(index).get();
         type.slotTexture().ifPresent(resourceLocation -> graphics.blit(resourceLocation, x + 1, y + 1, 0.0F, 0.0F, 16, 16, 16, 16));
      }
   }

   private int backgroundWidth() {
      return this.gridSizeX() * 18 + 2;
   }

   private int backgroundHeight() {
      return this.gridSizeY() * 20 + 2;
   }

   private int gridSizeX() {
      return ItemDisplayContents.LAYOUT.size();
   }

   private int gridSizeY() {
      return 1;
   }

   public int getHeight() {
      return this.backgroundHeight() + 4;
   }

   public int getWidth(@NotNull Font font) {
      return this.backgroundWidth();
   }
}
