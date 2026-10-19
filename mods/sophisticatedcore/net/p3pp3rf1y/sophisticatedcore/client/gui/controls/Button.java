package net.p3pp3rf1y.sophisticatedcore.client.gui.controls;

import java.util.List;
import java.util.Optional;
import java.util.function.IntConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;

public class Button extends ButtonBase {
   private final TextureBlitData backgroundTexture;
   @Nullable
   private final TextureBlitData hoveredBackgroundTexture;
   @Nullable
   private final TextureBlitData foregroundTexture;
   private List<Component> tooltip;
   private boolean hovered = false;

   public Button(Position position, ButtonDefinition buttonDefinition, IntConsumer onClick) {
      super(position, buttonDefinition.getDimension(), onClick);
      this.backgroundTexture = buttonDefinition.getBackgroundTexture();
      this.foregroundTexture = buttonDefinition.getForegroundTexture();
      this.hoveredBackgroundTexture = buttonDefinition.getHoveredBackgroundTexture();
      this.tooltip = buttonDefinition.getTooltip();
   }

   public boolean isHovered() {
      return this.hovered;
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      if (this.isMouseOver(mouseX, mouseY)) {
         this.hovered = true;
         this.renderHoveredBackground(guiGraphics);
      } else {
         this.hovered = false;
         GuiHelper.blit(guiGraphics, this.x, this.y, this.backgroundTexture);
      }
   }

   protected void renderHoveredBackground(GuiGraphics guiGraphics) {
      if (this.hoveredBackgroundTexture != null) {
         GuiHelper.blit(guiGraphics, this.x, this.y, this.hoveredBackgroundTexture);
      }
   }

   protected List<Component> getTooltip() {
      return this.tooltip;
   }

   @Override
   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      if (this.foregroundTexture != null) {
         GuiHelper.blit(guiGraphics, this.x, this.y, this.foregroundTexture);
      }
   }

   @Override
   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }

   public void setTooltip(List<Component> tooltip) {
      this.tooltip = tooltip;
   }

   @Override
   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
      if (this.visible && this.isMouseOver(mouseX, mouseY)) {
         guiGraphics.renderTooltip(screen.font, this.getTooltip(), Optional.empty(), mouseX, mouseY);
      }
   }
}
