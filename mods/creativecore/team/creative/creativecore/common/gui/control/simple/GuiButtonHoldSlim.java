package team.creative.creativecore.common.gui.control.simple;

import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.style.ControlFormatting;

public class GuiButtonHoldSlim extends GuiButtonHold {
   public GuiButtonHoldSlim(String name, Consumer<Integer> pressed) {
      super(name, pressed);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, int mouseX, int mouseY) {
      if (this.rect.inside(mouseX, mouseY)) {
         this.text.setDefaultColor(this.getStyle().fontColorHighlight.toInt());
      } else {
         this.text.setDefaultColor(this.getStyle().fontColor.toInt());
      }

      super.renderContent(graphics, mouseX, mouseY);
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.TRANSPARENT;
   }

   @Override
   protected int minWidth(int availableWidth) {
      return -1;
   }
}
