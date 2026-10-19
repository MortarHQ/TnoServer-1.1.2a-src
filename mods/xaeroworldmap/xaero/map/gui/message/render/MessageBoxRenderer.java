package xaero.map.gui.message.render;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import xaero.map.gui.message.Message;
import xaero.map.gui.message.MessageBox;

public class MessageBoxRenderer {
   private final int OPAQUE_FOR = 5000;
   private final int FADE_FOR = 3000;

   public void render(GuiGraphics guiGraphics, MessageBox messageBox, Font font, int x, int y, boolean rightAlign) {
      PoseStack matrixStack = guiGraphics.pose();
      long time = System.currentTimeMillis();
      matrixStack.pushPose();
      matrixStack.translate(x, y, 0.0F);
      int index = 0;

      for (Iterator<Message> iterator = messageBox.getIterator(); iterator.hasNext(); index++) {
         Message message = iterator.next();
         int passed = (int)(time - message.getAdditionTime());
         float opacity = passed < 5000 ? 1.0F : (3000 - (passed - 5000)) / 3000.0F;
         int alphaInt = (int)(opacity * 255.0F);
         if (alphaInt <= 3) {
            break;
         }

         int textColor = 16777215 | alphaInt << 24;
         int bgColor = (int)(0.5F * alphaInt) << 24;
         int textWidth = font.width(message.getText());
         int textX = rightAlign ? -textWidth - 1 : 2;
         int textY = -index * 10 - 4;
         int bgWidth = textWidth + 3;
         guiGraphics.fill(textX - 2, textY - 1, textX - 2 + bgWidth, textY + 9, bgColor);
         guiGraphics.drawString(font, message.getText(), textX, textY, textColor);
      }

      matrixStack.popPose();
   }
}
