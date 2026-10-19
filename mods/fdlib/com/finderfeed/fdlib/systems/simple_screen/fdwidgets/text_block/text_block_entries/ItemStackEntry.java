package com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.text_block_entries;

import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.TextBlockCursor;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.TextBlockEntry;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.TextBlockWidget;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.interactions.InteractionBox;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.interactions.TextBlockEntryInteraction;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class ItemStackEntry implements TextBlockEntry {
   private float textScale;
   private ItemStack itemStack;
   private TextBlockEntryInteraction interaction;

   public ItemStackEntry(ItemStack itemStack, float textScale, TextBlockEntryInteraction interaction) {
      this.itemStack = itemStack;
      this.textScale = textScale;
      this.interaction = interaction;
   }

   @Override
   public void render(GuiGraphics graphics, TextBlockWidget textBlock, TextBlockCursor cursor, float mouseX, float mouseY, float pticks, boolean lastEntry) {
      Font font = Minecraft.getInstance().font;
      float fontHeight = 9.0F * this.textScale;
      float scale = fontHeight / 16.0F;
      float width = scale * 16.0F;
      if (cursor.shouldGoToNextLine(width, textBlock.getBorderX())) {
         cursor.nextLine(fontHeight);
      }

      float x = cursor.x;
      float y = cursor.y;
      FDRenderUtil.renderScaledItemStack(graphics, x, y, scale, this.itemStack);
      textBlock.addInteractionBox(new InteractionBox(x, y, width, width, this.interaction));
      cursor.addX(width);
      if (lastEntry) {
         cursor.nextLine(scale * 16.0F);
      }
   }
}
