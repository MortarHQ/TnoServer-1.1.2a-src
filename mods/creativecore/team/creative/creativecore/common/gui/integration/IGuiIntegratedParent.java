package team.creative.creativecore.common.gui.integration;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.HolderLookup.Provider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.ContainerScreenEvent.Render.Background;
import net.neoforged.neoforge.common.NeoForge;
import team.creative.creativecore.client.render.GuiRenderHelper;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.IGuiParent;
import team.creative.creativecore.common.gui.event.GuiEvent;
import team.creative.creativecore.common.gui.event.GuiTooltipEvent;
import team.creative.creativecore.common.network.CreativePacket;
import team.creative.creativecore.common.util.math.geo.Rect;

public interface IGuiIntegratedParent extends IGuiParent {
   GuiLayer EMPTY = new GuiLayer("empty") {
      @Override
      public void create() {
      }
   };

   Provider provider();

   List<GuiLayer> getLayers();

   GuiLayer getTopLayer();

   default boolean isOpen(Class<? extends GuiLayer> clazz) {
      for (GuiLayer layer : this.getLayers()) {
         if (clazz.isInstance(layer)) {
            return true;
         }
      }

      return false;
   }

   @OnlyIn(Dist.CLIENT)
   default void render(GuiGraphics graphics, Screen screen, ScreenEventListener listener, int mouseX, int mouseY) {
      PoseStack pose = graphics.pose();
      int width = screen.width;
      int height = screen.height;
      listener.tick();
      Rect screenRect = Rect.getScreenRect();
      RenderSystem.enableDepthTest();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      List<GuiLayer> layers = this.getLayers();

      for (int i = 0; i < layers.size(); i++) {
         GuiLayer layer = layers.get(i);
         if (i == layers.size() - 1) {
            RenderSystem.disableDepthTest();
            if (layer.hasGrayBackground()) {
               GuiRenderHelper.verticalGradientRect(graphics, 0, 0, width, height, -1072689136, -804253680);
            }

            if (screen instanceof AbstractContainerScreen) {
               NeoForge.EVENT_BUS.post(new Background((AbstractContainerScreen)screen, graphics, mouseX, mouseY));
            }
         }

         pose.pushPose();
         int offX = (width - layer.getWidth()) / 2;
         int offY = (height - layer.getHeight()) / 2;
         pose.translate(offX, offY, 0.0F);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         Rect controlRect = new Rect(offX, offY, offX + layer.getWidth(), offY + layer.getHeight());
         layer.render(graphics, controlRect, screenRect.intersection(controlRect), 1.0, mouseX, mouseY);
         pose.popPose();
         RenderSystem.disableScissor();
      }

      if (!layers.isEmpty()) {
         GuiLayer layer = this.getTopLayer();
         GuiTooltipEvent event = layer.getTooltipEvent(mouseX - listener.getOffsetX(), mouseY - listener.getOffsetY());
         if (event != null) {
            layer.raiseEvent(event);
            if (!event.isCanceled()) {
               graphics.renderTooltip(Minecraft.getInstance().font, event.tooltip, Optional.empty(), mouseX, mouseY);
            }
         }
      }
   }

   @Override
   default void raiseEvent(GuiEvent event) {
   }

   @Override
   default void reflow() {
   }

   void openLayer(GuiLayer var1);

   void closeLayer(int var1);

   @Override
   default boolean isParent(IGuiParent parent) {
      return parent == this;
   }

   @Override
   default boolean hasGui() {
      return true;
   }

   default GuiControl get(String control) {
      for (GuiLayer layer : this.getLayers()) {
         if (control.startsWith(layer.getNestedName())) {
            if (control.equals(layer.getNestedName())) {
               return layer;
            }

            return layer.get(control.substring(layer.getNestedName().length() + 1));
         }
      }

      return null;
   }

   @Override
   default Rect toScreenRect(GuiControl control, Rect rect) {
      if (control instanceof GuiLayer layer) {
         int offX = (Minecraft.getInstance().getWindow().getGuiScaledWidth() - layer.getWidth()) / 2;
         int offY = (Minecraft.getInstance().getWindow().getGuiScaledHeight() - layer.getHeight()) / 2;
         rect.move(offX, offY);
      }

      return rect;
   }

   @Override
   default Rect toLayerRect(GuiControl control, Rect rect) {
      return rect;
   }

   @Override
   default IGuiIntegratedParent getIntegratedParent() {
      return this;
   }

   void send(CreativePacket var1);
}
