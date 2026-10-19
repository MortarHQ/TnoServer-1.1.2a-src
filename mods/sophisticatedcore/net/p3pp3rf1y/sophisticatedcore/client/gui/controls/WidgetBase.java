package net.p3pp3rf1y.sophisticatedcore.client.gui.controls;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.gui.screens.Screen;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public abstract class WidgetBase implements Renderable, GuiEventListener, NarratableEntry {
   protected int x;
   protected int y;
   protected final Minecraft minecraft;
   protected final Font font;
   private int height;
   private int width;
   protected boolean isHovered;
   protected boolean visible = true;
   private boolean focused = false;

   protected WidgetBase(Position position, Dimension dimension) {
      this.x = position.x();
      this.y = position.y();
      this.width = dimension.width();
      this.height = dimension.height();
      this.minecraft = Minecraft.getInstance();
      this.font = this.minecraft.font;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      if (this.visible) {
         this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
         RenderSystem.enableDepthTest();
         this.renderBg(guiGraphics, this.minecraft, mouseX, mouseY);
         this.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
      }
   }

   public NarrationPriority narrationPriority() {
      return this.isHovered ? NarrationPriority.HOVERED : NarrationPriority.NONE;
   }

   protected abstract void renderBg(GuiGraphics var1, Minecraft var2, int var3, int var4);

   protected abstract void renderWidget(GuiGraphics var1, int var2, int var3, float var4);

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   protected void updateDimensions(int width, int height) {
      this.width = width;
      this.height = height;
   }

   public boolean isMouseOver(double mouseX, double mouseY) {
      return mouseX >= this.x && mouseX < this.x + this.getWidth() && mouseY >= this.y && mouseY < this.y + this.getHeight();
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public void setPosition(Position position) {
      this.x = position.x();
      this.y = position.y();
   }

   protected int getCenteredX(int elementWidth) {
      return (this.getWidth() - elementWidth) / 2;
   }

   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
   }

   public void setFocused(boolean focused) {
      this.focused = focused;
   }

   public boolean isFocused() {
      return this.focused;
   }

   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }
}
