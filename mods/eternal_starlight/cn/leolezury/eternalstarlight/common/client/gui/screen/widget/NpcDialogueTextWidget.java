package cn.leolezury.eternalstarlight.common.client.gui.screen.widget;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

public class NpcDialogueTextWidget extends AbstractWidget {
   private int tickCount;

   public NpcDialogueTextWidget(Component text) {
      super(0, 0, 0, 0, text);
   }

   public int getIncrement(int width) {
      List<FormattedCharSequence> lines = Minecraft.getInstance().font.split(this.getMessage(), width / 5 * 4);
      return Math.max(1, lines.size()) * 9 + 10;
   }

   public void reposition(int x, int y, int width) {
      this.setRectangle(width, this.getIncrement(width), x, y);
   }

   public void tick() {
      if (this.tickCount < this.getMessage().getString().length()) {
         this.tickCount++;
      }
   }

   protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
      guiGraphics.fillGradient(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 1698785761, 1698785761);
      String message = this.getMessage().getString().substring(0, Math.min(this.getMessage().getString().length(), this.tickCount));
      List<FormattedCharSequence> lines = Minecraft.getInstance().font.split(FormattedText.of(message), this.width / 5 * 4);
      int y = 0;

      for (FormattedCharSequence sequence : lines) {
         guiGraphics.drawCenteredString(Minecraft.getInstance().font, sequence, this.getX() + this.width / 2, this.getY() + y + 5, 15910236);
         y += 9;
      }
   }

   protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
      this.defaultButtonNarrationText(narrationElementOutput);
   }
}
