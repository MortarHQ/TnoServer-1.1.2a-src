package cn.leolezury.eternalstarlight.common.client.gui.screen.widget;

import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.CommonComponents;

public class BookHistoryButton extends Button {
   private final BookDefinition book;
   private final boolean isLeft;

   public BookHistoryButton(int x, int y, BookDefinition book, boolean isLeft, OnPress onPress) {
      super(x, y, book.buttons().leftRightButtonWidth(), book.buttons().leftRightButtonHeight(), CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
      this.book = book;
      this.isLeft = isLeft;
   }

   public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(0.0, 0.0, 780.0);
      guiGraphics.blit(
         this.isLeft ? this.book.textures().leftButton() : this.book.textures().rightButton(),
         this.getX(),
         this.getY(),
         0.0F,
         0.0F,
         this.book.buttons().leftRightButtonWidth(),
         this.book.buttons().leftRightButtonHeight(),
         this.book.buttons().leftRightButtonWidth(),
         this.book.buttons().leftRightButtonHeight()
      );
      guiGraphics.pose().popPose();
   }
}
