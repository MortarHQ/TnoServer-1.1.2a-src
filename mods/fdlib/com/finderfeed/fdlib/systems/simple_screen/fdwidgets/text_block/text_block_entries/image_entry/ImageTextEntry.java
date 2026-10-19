package com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.text_block_entries.image_entry;

import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.TextBlockCursor;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.TextBlockEntry;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.TextBlockWidget;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.interactions.InteractionBox;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.interactions.TextBlockEntryInteraction;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class ImageTextEntry implements TextBlockEntry {
   public ImageInText image;
   public float textScale;
   public TextBlockEntryInteraction interaction;

   public ImageTextEntry(ImageInText imageInText, float textScale, TextBlockEntryInteraction interaction) {
      this.image = imageInText;
      this.textScale = textScale;
      this.interaction = interaction;
   }

   public ImageTextEntry(ResourceLocation location, float textScale, TextBlockEntryInteraction interaction) {
      this.image = new ImageInText(location, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      this.textScale = textScale;
      this.interaction = interaction;
   }

   @Override
   public void render(GuiGraphics graphics, TextBlockWidget textBlock, TextBlockCursor cursor, float mouseX, float mouseY, float pticks, boolean last) {
      float widthheight = 9.0F * this.textScale;
      if (cursor.shouldGoToNextLine(widthheight, textBlock.getBorderX())) {
         cursor.nextLine(widthheight);
      }

      float x = cursor.x;
      float y = cursor.y;
      FDRenderUtil.bindTexture(this.image.location);
      FDRenderUtil.blitWithBlend(
         graphics.pose(),
         x,
         y,
         widthheight,
         widthheight,
         this.image.u0,
         this.image.v0,
         this.image.u1 - this.image.u0,
         this.image.v1 - this.image.v0,
         1.0F,
         1.0F,
         0.0F,
         1.0F
      );
      float addX = widthheight + this.textScale;
      textBlock.addInteractionBox(new InteractionBox(x, y, addX, widthheight, this.interaction));
      cursor.addX(addX);
      if (last) {
         cursor.nextLine(widthheight);
      }
   }
}
