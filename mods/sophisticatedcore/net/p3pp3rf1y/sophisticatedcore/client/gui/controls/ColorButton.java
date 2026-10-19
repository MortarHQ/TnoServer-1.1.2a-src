package net.p3pp3rf1y.sophisticatedcore.client.gui.controls;

import java.util.List;
import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public class ColorButton extends ButtonBase {
   private final Supplier<Integer> colorGetter;
   private final List<Component> tooltip;

   public ColorButton(Position position, Dimension dimension, Supplier<Integer> colorGetter, IntConsumer onClick, @Nullable Component tooltip) {
      super(position, dimension, onClick);
      this.colorGetter = colorGetter;
      this.tooltip = tooltip == null ? List.of() : List.of(tooltip);
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      int color = this.isMouseOver(mouseX, mouseY) ? -1 : -3355444;
      guiGraphics.fill(this.x, this.y, this.x + this.getWidth(), this.y + this.getHeight(), color);
   }

   @Override
   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      int color = this.colorGetter.get();
      if (color == -1) {
         for (int row = 0; row < this.getHeight() - 2; row++) {
            for (int column = 0; column < this.getWidth() - 2; column++) {
               guiGraphics.fill(this.x + column + 1, this.y + row + 1, this.x + column + 2, this.y + row + 2, (row + column) % 2 == 0 ? 268225740 : -7829368);
            }
         }
      } else {
         guiGraphics.fill(this.x + 1, this.y + 1, this.x + this.getWidth() - 1, this.y + this.getHeight() - 1, this.colorGetter.get());
      }
   }

   @Override
   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
      if (this.visible && this.isMouseOver(mouseX, mouseY) && !this.tooltip.isEmpty()) {
         guiGraphics.renderTooltip(screen.getMinecraft().font, this.tooltip, Optional.empty(), mouseX, mouseY);
      }
   }
}
