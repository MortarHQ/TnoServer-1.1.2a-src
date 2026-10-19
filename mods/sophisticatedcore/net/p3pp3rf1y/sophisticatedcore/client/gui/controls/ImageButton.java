package net.p3pp3rf1y.sophisticatedcore.client.gui.controls;

import java.util.function.IntConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;

public class ImageButton extends ButtonBase {
   private final TextureBlitData texture;

   public ImageButton(Position position, Dimension dimension, TextureBlitData texture, IntConsumer onClick) {
      super(position, dimension, onClick);
      this.texture = texture;
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
   }

   @Override
   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      GuiHelper.blit(guiGraphics, this.x, this.y, this.texture);
   }

   @Override
   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }
}
