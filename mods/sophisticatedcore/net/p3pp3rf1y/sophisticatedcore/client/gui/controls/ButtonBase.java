package net.p3pp3rf1y.sophisticatedcore.client.gui.controls;

import java.util.function.IntConsumer;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiSoundHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public abstract class ButtonBase extends WidgetBase {
   protected IntConsumer onClick;

   protected ButtonBase(Position position, Dimension dimension, IntConsumer onClick) {
      super(position, dimension);
      this.onClick = onClick;
   }

   protected void setOnClick(IntConsumer onClick) {
      this.onClick = onClick;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (!this.isMouseOver(mouseX, mouseY)) {
         return false;
      }

      this.onClick.accept(button);
      GuiSoundHelper.playButtonClickSound();
      return true;
   }
}
