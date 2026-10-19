package net.p3pp3rf1y.sophisticatedcore.client.gui.controls;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;

public class ProgressBar extends WidgetBase {
   private final TextureBlitData progressTexture;
   private final Supplier<Float> getProgress;
   private final ProgressBar.ProgressDirection dir;

   public ProgressBar(Position position, TextureBlitData progressTexture, Supplier<Float> getProgress, ProgressBar.ProgressDirection dir) {
      super(position, new Dimension(progressTexture.getWidth(), progressTexture.getHeight()));
      this.progressTexture = progressTexture;
      this.getProgress = getProgress;
      this.dir = dir;
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
   }

   @Override
   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      int height = this.progressTexture.getHeight();
      int width = this.progressTexture.getWidth();
      float progress = this.getProgress.get();
      if (!(progress <= 0.0F)) {
         int yOffset = 0;
         if (this.dir == ProgressBar.ProgressDirection.BOTTOM_UP) {
            yOffset = (int)(height * progress);
            height -= yOffset;
         } else if (this.dir == ProgressBar.ProgressDirection.LEFT_RIGHT) {
            width = (int)(width * progress);
         }

         guiGraphics.blit(
            this.progressTexture.getTextureName(),
            this.x,
            this.y + yOffset,
            this.progressTexture.getU(),
            (float)this.progressTexture.getV() + yOffset,
            width,
            height,
            this.progressTexture.getTextureWidth(),
            this.progressTexture.getTextureHeight()
         );
      }
   }

   @Override
   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }

   public enum ProgressDirection {
      LEFT_RIGHT,
      BOTTOM_UP;
   }
}
