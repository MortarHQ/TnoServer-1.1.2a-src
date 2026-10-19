package com.finderfeed.fdlib.systems.screen;

import com.finderfeed.fdlib.systems.screen.default_components.EmptyComponent;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Deprecated
public abstract class FDScreen extends Screen {
   private HashMap<String, FDScreenComponent> componentLookup = new HashMap<>();
   private List<FDScreenComponent> screenLayers = new ArrayList<>();
   private FDScreenComponent focused = null;
   private FDScreenComponent hovered = null;
   protected float mx;
   protected float my;

   public FDScreen() {
      super(Component.empty());
   }

   protected void init() {
      super.init();
      this.componentLookup.clear();
      this.screenLayers.clear();
      Window window = Minecraft.getInstance().getWindow();
      EmptyComponent component = new EmptyComponent(this, "main", 0.0F, 0.0F, window.getGuiScaledWidth(), window.getGuiScaledHeight());
      this.screenLayers.add(component);
      this.componentLookup.put("main", component);
   }

   public void tick() {
      super.tick();
      boolean wasHovered = false;

      for (FDScreenComponent component : this.screenLayers) {
         component.tick();
         if (!wasHovered) {
            wasHovered = this.decideHoveredComponent(component, this.mx, this.my);
         }
      }
   }

   private boolean decideHoveredComponent(FDScreenComponent component, float mx, float my) {
      if (component.isMouseOver(mx, my)) {
         List<FDScreenComponent> children = component.getChildren().getChildren();

         for (int i = children.size() - 1; i >= 0; i--) {
            FDScreenComponent child = children.get(i);
            Anchor anchor = child.getAnchor();
            float chx = anchor.xTransform.apply(component, child.getX());
            float chy = anchor.yTransform.apply(component, child.getY());
            float cmx = mx - chx;
            float cmy = my - chy;
            if (this.decideHoveredComponent(child, cmx, cmy)) {
               return true;
            }
         }

         if (this.hovered != component) {
            if (this.hovered != null) {
               this.hovered.setHovered(false);
            }

            component.setHovered(true);
            this.hovered = component;
         }

         return true;
      } else {
         return false;
      }
   }

   public void render(GuiGraphics graphics, int mx, int my, float partialTicks) {
      this.mx = mx;
      this.my = my;

      for (FDScreenComponent component : this.screenLayers) {
         this.renderComponent(component, graphics, 0.0F, 0.0F, mx, my, partialTicks);
      }
   }

   public void renderComponent(FDScreenComponent component, GuiGraphics graphics, float x, float y, float mx, float my, float partialTicks) {
      component.renderComponent(graphics, x, y, mx, my, partialTicks);
      PoseStack matrices = graphics.pose();
      matrices.pushPose();
      matrices.translate(0.0F, 0.0F, 10.0F);

      for (FDScreenComponent child : component.getChildren()) {
         float cx = x + child.getX();
         float cy = y + child.getY();
         float cmx = mx - child.getX();
         float cmy = my - child.getY();
         Anchor anchor = child.getAnchor();
         cx = anchor.xTransform.apply(component, cx);
         cy = anchor.yTransform.apply(component, cy);
         this.renderComponent(child, graphics, cx, cy, cmx, cmy, partialTicks);
      }

      matrices.popPose();
      component.postRenderComponent(graphics, x, y, mx, my, partialTicks);
   }

   public boolean mouseClicked(double mx, double my, int button) {
      for (FDScreenComponent component : this.screenLayers) {
         if (this.clickRecursively(component, (float)mx, (float)my, button)) {
            return true;
         }
      }

      if (this.focused != null) {
         this.focused.setFocused(false);
         this.focused = null;
      }

      return false;
   }

   protected boolean clickRecursively(FDScreenComponent component, float mx, float my, int button) {
      if (component.isMouseOver(mx, my)) {
         for (FDScreenComponent child : component.getChildren()) {
            Anchor anchor = child.getAnchor();
            float chx = anchor.xTransform.apply(component, child.getX());
            float chy = anchor.yTransform.apply(component, child.getY());
            float cmx = mx - chx;
            float cmy = my - chy;
            if (this.clickRecursively(child, cmx, cmy, button)) {
               return true;
            }
         }

         if (component.mouseClicked(mx, my, button)) {
            if (this.focused != null && this.focused != component) {
               this.focused.setFocused(false);
            }

            component.setFocused(true);
            this.focused = component;
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void mouseMoved(double mx, double my) {
      if (this.focused != null) {
         float accumulatedX = 0.0F;
         float accumulatedY = 0.0F;

         for (FDScreenComponent component = this.focused; component != null; component = component.getParent()) {
            accumulatedX += component.getX();
            accumulatedY += component.getY();
         }

         this.focused.mouseMoved(mx - accumulatedX, my - accumulatedY);
      }
   }

   public boolean mouseDragged(double mx, double my, int button, double leftRight, double upDown) {
      if (this.focused != null && this.focused.getParent() != null) {
         Anchor anchor = this.focused.getAnchor();
         float cmx = anchor.xTransform.apply(this.focused.getParent(), this.focused.getX());
         float cmy = anchor.yTransform.apply(this.focused.getParent(), this.focused.getY());

         for (FDScreenComponent parent = this.focused.getParent(); parent != null && parent.getParent() != null; parent = parent.getParent()) {
            anchor = parent.getAnchor();
            float acmx = anchor.xTransform.apply(parent.getParent(), parent.getX());
            float acmy = anchor.yTransform.apply(parent.getParent(), parent.getY());
            cmx += acmx;
            cmy += acmy;
         }

         float fmx = (float)(mx - cmx);
         float fmy = (float)(my - cmy);
         return this.focused.mouseDragged(fmx, fmy, button, leftRight, upDown);
      } else {
         return false;
      }
   }

   public boolean mouseReleased(double mx, double my, int action) {
      if (this.focused != null && this.focused.getParent() != null) {
         Anchor anchor = this.focused.getAnchor();
         float cmx = anchor.xTransform.apply(this.focused.getParent(), this.focused.getX());
         float cmy = anchor.yTransform.apply(this.focused.getParent(), this.focused.getY());

         for (FDScreenComponent parent = this.focused.getParent(); parent != null && parent.getParent() != null; parent = parent.getParent()) {
            anchor = parent.getAnchor();
            float acmx = anchor.xTransform.apply(parent.getParent(), parent.getX());
            float acmy = anchor.yTransform.apply(parent.getParent(), parent.getY());
            cmx += acmx;
            cmy += acmy;
         }

         float fmx = (float)(mx - cmx);
         float fmy = (float)(my - cmy);
         return this.focused.mouseReleased(fmx, fmy, action);
      } else {
         return false;
      }
   }

   public boolean mouseScrolled(double mx, double my, double xd, double yd) {
      for (FDScreenComponent component : this.screenLayers) {
         if (this.scrollRecursively(component, (float)mx, (float)my, (float)xd, (float)yd)) {
            return true;
         }
      }

      return false;
   }

   protected boolean scrollRecursively(FDScreenComponent component, float mx, float my, float xd, float yd) {
      for (FDScreenComponent child : component.getChildren()) {
         Anchor anchor = child.getAnchor();
         float chx = anchor.xTransform.apply(component, child.getX());
         float chy = anchor.yTransform.apply(component, child.getY());
         float cmx = mx - chx;
         float cmy = my - chy;
         if (this.scrollRecursively(child, cmx, cmy, xd, yd)) {
            return true;
         }
      }

      return component.isMouseOver(mx, my) && component.mouseScrolled(mx, my, xd, yd);
   }

   public boolean charTyped(char character, int button) {
      return this.focused != null && this.focused.charTyped(character, button);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return this.focused != null && this.focused.keyPressed(keyCode, scanCode, modifiers) ? true : super.keyPressed(keyCode, scanCode, modifiers);
   }

   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      return this.focused != null && this.focused.keyReleased(keyCode, scanCode, modifiers);
   }

   public FDScreenComponent getComponentById(String uniqueId) {
      return this.componentLookup.get(uniqueId);
   }

   public void addComponent(FDScreenComponent component) {
      this.componentLookup.put(component.getUniqueId(), component);
      if (component.getParent() == null) {
         FDScreenComponent main = this.componentLookup.get("main");
         main.getChildren().setAsChild(component);
         component.setParent(main);
      }
   }

   public void removeComponent(String id) {
      FDScreenComponent component = this.getComponentById(id);
      if (component != null) {
         this.componentLookup.remove(id);
         FDScreenComponent parent = component.getParent();
         if (parent != null) {
            parent.getChildren().removeChild(id);
         }
      }
   }

   public List<FDScreenComponent> getScreenLayers() {
      return this.screenLayers;
   }
}
