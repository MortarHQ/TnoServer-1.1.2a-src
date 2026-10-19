package cn.leolezury.eternalstarlight.common.client.gui.screen.widget;

import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.CommonComponents;

public class BookProgressButton extends Button {
   private final BookDefinition book;
   private final boolean isDown;

   public BookProgressButton(int x, int y, BookDefinition book, boolean isDown, OnPress onPress) {
      super(x, y, book.buttons().upDownButtonWidth(), book.buttons().upDownButtonHeight(), CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
      this.book = book;
      this.isDown = isDown;
   }

   public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(0.0, 0.0, 780.0);
      guiGraphics.blit(
         this.isDown ? this.book.textures().downButton() : this.book.textures().upButton(),
         this.getX(),
         this.getY(),
         0.0F,
         0.0F,
         this.book.buttons().upDownButtonWidth(),
         this.book.buttons().upDownButtonHeight(),
         this.book.buttons().upDownButtonWidth(),
         this.book.buttons().upDownButtonHeight()
      );
      guiGraphics.pose().popPose();
   }
}
