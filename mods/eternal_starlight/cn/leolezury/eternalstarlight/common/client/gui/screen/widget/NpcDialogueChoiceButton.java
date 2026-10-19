package cn.leolezury.eternalstarlight.common.client.gui.screen.widget;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class NpcDialogueChoiceButton extends Button {
   public NpcDialogueChoiceButton(Component text, OnPress onPress) {
      super(0, 0, 0, 0, text, onPress, DEFAULT_NARRATION);
   }

   public int getIncrement(int width) {
      List<FormattedCharSequence> lines = Minecraft.getInstance().font.split(this.getMessage(), width / 5 * 4);
      return Math.max(1, lines.size()) * 9 + 10;
   }

   public void reposition(int x, int y, int width) {
      this.setRectangle(width, this.getIncrement(width), x, y);
   }

   protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
      guiGraphics.fillGradient(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 1711276032, 1711276032);
      List<FormattedCharSequence> lines = Minecraft.getInstance().font.split(this.getMessage(), this.width / 5 * 4);
      int y = 0;

      for (FormattedCharSequence sequence : lines) {
         guiGraphics.drawCenteredString(
            Minecraft.getInstance().font, sequence, this.getX() + this.width / 2, this.getY() + y + 5, this.isHovered() ? 2796728 : 16777215
         );
         y += 9;
      }
   }
}
