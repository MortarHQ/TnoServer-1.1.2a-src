package team.creative.creativecore.common.gui.integration;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.IScaleableGuiScreen;
import team.creative.creativecore.common.network.CreativePacket;

public class GuiScreenIntegration extends Screen implements IGuiIntegratedParent, IScaleableGuiScreen {
   public final Minecraft mc = Minecraft.getInstance();
   private List<GuiLayer> layers = new ArrayList<>();
   protected ScreenEventListener listener;

   public GuiScreenIntegration(GuiLayer layer) {
      super(Component.literal("gui-api"));
      layer.setParent(this);
      this.layers.add(layer);
      layer.init();
   }

   protected void init() {
      if (this.listener == null) {
         this.listener = new ScreenEventListener(this, this);
      }

      this.addWidget(this.listener);
   }

   protected void rebuildWidgets() {
      super.rebuildWidgets();

      for (GuiLayer layer : this.layers) {
         layer.reflow();
      }
   }

   @Override
   public int getWidth() {
      int width = 0;

      for (GuiLayer layer : this.layers) {
         width = Math.max(width, layer.getWidth());
      }

      return width;
   }

   @Override
   public int getHeight() {
      int height = 0;

      for (GuiLayer layer : this.layers) {
         height = Math.max(height, layer.getHeight());
      }

      return height;
   }

   @Override
   public void clientTick() {
      for (GuiLayer layer : this.layers) {
         layer.tick();
      }
   }

   public void removed() {
      for (GuiLayer layer : this.layers) {
         layer.closed();
      }
   }

   @Override
   public boolean isContainer() {
      return false;
   }

   @Override
   public boolean isClient() {
      return true;
   }

   @Override
   public Player getPlayer() {
      return this.mc.player;
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.render(graphics, this, this.listener, mouseX, mouseY);
   }

   @Override
   public List<GuiLayer> getLayers() {
      return this.layers;
   }

   @Override
   public GuiLayer getTopLayer() {
      return this.layers.isEmpty() ? EMPTY : this.layers.get(this.layers.size() - 1);
   }

   @Override
   public void openLayer(GuiLayer layer) {
      layer.setParent(this);
      this.layers.add(layer);
      layer.init();
   }

   @Override
   public void closeLayer(GuiLayer layer) {
      int index = this.layers.indexOf(layer);
      if (index != -1) {
         this.closeLayer(index);
      }
   }

   @Override
   public void closeLayer(int layer) {
      for (int i = layer; i < this.layers.size(); i++) {
         this.layers.get(i).closed();
      }

      this.layers = this.layers.subList(0, layer);
      if (this.layers.isEmpty()) {
         this.onClose();
      } else {
         this.getTopLayer().becameTopLayer();
      }
   }

   @Override
   public void closeTopLayer() {
      this.closeLayer(this.layers.size() - 1);
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

   @Override
   public void send(CreativePacket message) {
   }

   @Override
   public Provider provider() {
      return this.mc.player != null ? this.mc.player.registryAccess() : null;
   }
}
