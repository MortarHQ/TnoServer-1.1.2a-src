package team.creative.creativecore.common.gui.integration;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.IScaleableGuiScreen;

public class ContainerScreenIntegration extends AbstractContainerScreen<ContainerIntegration> implements IScaleableGuiScreen {
   protected ScreenEventListener listener = new ScreenEventListener((IGuiIntegratedParent)this.getMenu(), this);

   public ContainerScreenIntegration(ContainerIntegration screenContainer, Inventory inv) {
      super(screenContainer, inv, Component.literal("gui-api"));
   }

   protected void init() {
      this.addWidget(this.listener);
   }

   protected void rebuildWidgets() {
      super.rebuildWidgets();

      for (GuiLayer layer : ((ContainerIntegration)this.getMenu()).getLayers()) {
         layer.reflow();
      }
   }

   public boolean mouseDragged(double x, double y, int button, double dragX, double dragY) {
      return this.getFocused() != null && this.isDragging() && button == 0 && this.getFocused().mouseDragged(x, y, button, dragX, dragY);
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      ((ContainerIntegration)this.getMenu()).render(graphics, this, this.listener, mouseX, mouseY);
      int minX = Integer.MAX_VALUE;
      int minY = Integer.MAX_VALUE;
      int maxX = Integer.MIN_VALUE;
      int maxY = Integer.MIN_VALUE;

      for (GuiLayer layer : ((ContainerIntegration)this.getMenu()).getLayers()) {
         int offsetX = (this.width - layer.getWidth()) / 2;
         int offsetY = (this.height - layer.getHeight()) / 2;
         minX = Math.min(minX, offsetX + layer.rect.getX());
         minY = Math.min(minY, offsetY + layer.rect.getY());
         maxX = Math.max(maxX, offsetX + layer.rect.getRight());
         maxY = Math.max(maxY, offsetY + layer.rect.getBottom());
      }

      this.leftPos = minX;
      this.topPos = minY;
      this.imageWidth = maxX - minX;
      this.imageHeight = maxY - minY;
   }

   @Override
   public void clientTick() {
      for (GuiLayer layer : ((ContainerIntegration)this.getMenu()).getLayers()) {
         layer.tick();
      }
   }

   @Override
   public int getWidth() {
      int width = 0;

      for (GuiLayer layer : ((ContainerIntegration)this.getMenu()).getLayers()) {
         width = Math.max(width, layer.getWidth());
      }

      return width;
   }

   @Override
   public int getHeight() {
      int height = 0;

      for (GuiLayer layer : ((ContainerIntegration)this.getMenu()).getLayers()) {
         height = Math.max(height, layer.getHeight());
      }

      return height;
   }

   protected void renderBg(GuiGraphics graphics, float partialTicks, int x, int y) {
   }

   public void mouseMoved(double x, double y) {
      this.listener.mouseMoved(x, y);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return this.listener.keyPressed(keyCode, scanCode, modifiers) ? true : super.keyPressed(keyCode, scanCode, modifiers);
   }

   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      return this.listener.keyReleased(keyCode, scanCode, modifiers) ? true : super.keyReleased(keyCode, scanCode, modifiers);
   }

   public boolean charTyped(char codePoint, int modifiers) {
      return this.listener.charTyped(codePoint, modifiers) ? true : super.charTyped(codePoint, modifiers);
   }
}
