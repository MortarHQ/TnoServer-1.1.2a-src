package com.finderfeed.fdlib.systems.simple_screen;

import com.finderfeed.fdlib.data_structures.ObjectHolder;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.util.FDWidgetMovement;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Function;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.gui.screens.Screen;

public abstract class FDWidget implements GuiEventListener, Renderable, NarratableEntry {
   private float x;
   private float y;
   private float width;
   private float height;
   private boolean focused = false;
   private boolean active = true;
   private boolean hovered = false;
   private FDWidget parent;
   protected Screen widgetOwner;
   protected HashMap<String, FDWidget> children = new LinkedHashMap<>();
   protected FDWidgetMovement widgetMovement;

   public FDWidget(Screen screen, float x, float y, float width, float height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.widgetOwner = screen;
      this.widgetMovement = new FDWidgetMovement(this);
   }

   public abstract void renderWidget(GuiGraphics var1, float var2, float var3, float var4);

   public void renderChildren(GuiGraphics graphics, float mx, float my, float pticks) {
      boolean isChildHovered = false;

      for (FDWidget child : this.children.values()) {
         if (child.isActive() && FDRenderUtil.isMouseInBounds(mx, my, child.getX(), child.getY(), child.getWidth(), child.getHeight())) {
            isChildHovered = true;
         }

         child.render(graphics, (int)mx, (int)my, pticks);
      }

      if (!isChildHovered) {
         this.hovered = FDRenderUtil.isMouseInBounds(mx, my, this.getX(), this.getY(), this.getWidth(), this.getHeight());
      } else {
         this.hovered = false;
      }
   }

   public void render(GuiGraphics graphics, int mx, int my, float pticks) {
      this.widgetMovement.setWidgetPositionInRender();
      if (this.isActive()) {
         this.renderWidget(graphics, mx, my, pticks);
         this.useScissor();
         this.renderChildren(graphics, mx, my, pticks);
         this.endScissor();
      }
   }

   public void useScissor() {
   }

   public void endScissor() {
   }

   public void tickChildren() {
      for (FDWidget child : this.children.values()) {
         child.tick();
      }
   }

   public void tick() {
      this.widgetMovement.tick();
      this.tickChildren();
   }

   public boolean isMoving() {
      return this.widgetMovement.isMoving();
   }

   public void moveWidgetTo(int time, float x, float y, Function<Float, Float> easing) {
      this.widgetMovement.moveTo(time, x, y, easing);
   }

   public void addChild(String id, FDWidget widget) {
      float widgetX = widget.getX();
      float widgetY = widget.getY();
      widget.setX(this.getX() + widgetX);
      widget.setY(this.getY() + widgetY);
      widget.parent = this;
      this.children.put(id, widget);
   }

   public FDWidget getChild(String id) {
      return this.children.get(id);
   }

   public abstract boolean onMouseClick(float var1, float var2, int var3);

   public abstract boolean onMouseRelease(float var1, float var2, int var3);

   public abstract boolean onMouseScroll(float var1, float var2, float var3, float var4);

   public abstract boolean onCharTyped(char var1, int var2);

   public abstract boolean onKeyPress(int var1, int var2, int var3);

   public abstract boolean onKeyRelease(int var1, int var2, int var3);

   public final boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (!this.isActive()) {
         return false;
      }

      if (this.isFocused()) {
         return this.onKeyPress(keyCode, scanCode, modifiers);
      }

      boolean result = false;

      for (FDWidget widget : this.children.values()) {
         result = widget.keyPressed(keyCode, scanCode, modifiers) || result;
      }

      return result;
   }

   public final boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      if (!this.isActive()) {
         return false;
      }

      if (this.isFocused()) {
         return this.onKeyRelease(keyCode, scanCode, modifiers);
      }

      boolean result = false;

      for (FDWidget widget : this.children.values()) {
         result = widget.keyReleased(keyCode, scanCode, modifiers) || result;
      }

      return result;
   }

   public final boolean charTyped(char character, int idk) {
      if (!this.isActive()) {
         return false;
      }

      boolean result = this.isFocused();
      if (this.isFocused()) {
         result = this.onCharTyped(character, idk);
      } else {
         for (FDWidget child : this.children.values()) {
            child.charTyped(character, idk);
         }
      }

      return result;
   }

   private void setFocusStateFor(FDWidget widget) {
      this.setFocused(false);

      for (FDWidget w : this.children.values()) {
         w.setFocusStateFor(widget);
      }

      if (widget == this) {
         this.setFocused(true);
      }
   }

   private boolean mouseClick(double mx, double my, int key, ObjectHolder<FDWidget> result) {
      if (!this.isActive()) {
         return false;
      }

      boolean childWasClicked = false;
      if (FDRenderUtil.isMouseInBounds((float)mx, (float)my, this.x, this.y, this.width, this.height)) {
         for (FDWidget widget : this.children.values()) {
            if (widget.mouseClick(mx, my, key, result)) {
               childWasClicked = true;
               break;
            }
         }

         if (!childWasClicked) {
            boolean res = this.onMouseClick((float)mx, (float)my, key);
            if (res) {
               result.setValue(this);
            }

            return res;
         }
      }

      return childWasClicked;
   }

   public final boolean mouseClicked(double mx, double my, int key) {
      if (!this.isActive()) {
         return false;
      }

      ObjectHolder<FDWidget> result = new ObjectHolder<>(null);
      this.mouseClick(mx, my, key, result);
      FDWidget clickedWidget = result.getValue();
      if (clickedWidget != null) {
         this.setFocusStateFor(clickedWidget);
         this.widgetOwner.setFocused(clickedWidget);
      }

      return false;
   }

   private boolean mouseRelease(double mx, double my, int key, ObjectHolder<FDWidget> result) {
      if (!this.isActive()) {
         return false;
      }

      boolean childWasClicked = false;
      if (FDRenderUtil.isMouseInBounds((float)mx, (float)my, this.x, this.y, this.width, this.height)) {
         for (FDWidget widget : this.children.values()) {
            if (widget.mouseRelease(mx, my, key, result)) {
               childWasClicked = true;
               break;
            }
         }

         if (!childWasClicked) {
            boolean res = this.onMouseRelease((float)mx, (float)my, key);
            if (res) {
               result.setValue(this);
            }

            return res;
         }
      }

      return childWasClicked;
   }

   public final boolean mouseReleased(double mx, double my, int key) {
      if (!this.isActive()) {
         return false;
      }

      ObjectHolder<FDWidget> result = new ObjectHolder<>(null);
      this.mouseRelease(mx, my, key, result);
      FDWidget clickedWidget = result.getValue();
      if (clickedWidget != null) {
         this.setFocusStateFor(clickedWidget);
      }

      return clickedWidget != null;
   }

   private boolean scroll(double mx, double my, double scrollX, double scrollY, ObjectHolder<FDWidget> interactedWidget) {
      if (!this.isActive()) {
         return false;
      }

      boolean childWasScrolled = false;
      if (FDRenderUtil.isMouseInBounds((float)mx, (float)my, this.x, this.y, this.width, this.height)) {
         for (FDWidget widget : this.children.values()) {
            if (widget.scroll(mx, my, scrollX, scrollY, interactedWidget)) {
               childWasScrolled = true;
               break;
            }
         }

         if (!childWasScrolled) {
            boolean result = this.onMouseScroll((float)mx, (float)my, (float)scrollX, (float)scrollY);
            if (result) {
               interactedWidget.setValue(this);
            }

            return result;
         }
      }

      return childWasScrolled;
   }

   public final boolean mouseScrolled(double mx, double my, double scrollX, double scrollY) {
      if (!this.isActive()) {
         return false;
      }

      ObjectHolder<FDWidget> result = new ObjectHolder<>(null);
      this.scroll(mx, my, scrollX, scrollY, result);
      FDWidget scrolledWidget = result.getValue();
      if (scrolledWidget != null) {
         this.setFocusStateFor(scrolledWidget);
      }

      return scrolledWidget != null;
   }

   public final boolean isMouseOver(double mx, double my) {
      if (!this.isActive()) {
         return false;
      }

      boolean childUnderMouse = false;
      if (FDRenderUtil.isMouseInBounds((float)mx, (float)my, this.x, this.y, this.width, this.height)) {
         for (FDWidget widget : this.children.values()) {
            if (widget.isMouseOver(mx, my)) {
               childUnderMouse = true;
               break;
            }
         }

         if (!childUnderMouse) {
            return true;
         }
      }

      return childUnderMouse;
   }

   public void setX(float x) {
      float d = x - this.x;

      for (FDWidget child : this.children.values()) {
         child.setX(child.getX() + d);
      }

      this.x = x;
   }

   public void setY(float y) {
      float d = y - this.y;

      for (FDWidget child : this.children.values()) {
         child.setY(child.getY() + d);
      }

      this.y = y;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getWidth() {
      return this.width;
   }

   public float getHeight() {
      return this.height;
   }

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public boolean isHovered() {
      return this.hovered;
   }

   public void unfocusChildren() {
      for (FDWidget child : this.children.values()) {
         child.setFocused(false);
      }
   }

   public void moveChildren(float x, float y) {
      for (FDWidget widget : this.children.values()) {
         widget.setX(widget.getX() + x);
         widget.setY(widget.getY() + y);
      }
   }

   public void setFocused(boolean focused) {
      this.focused = focused;
   }

   public boolean isFocused() {
      return this.focused;
   }

   public void removeAllChildren() {
      this.children.clear();
   }

   public HashMap<String, FDWidget> getChildren() {
      return this.children;
   }

   public NarrationPriority narrationPriority() {
      return NarrationPriority.NONE;
   }

   public void updateNarration(NarrationElementOutput p_169152_) {
   }

   public FDWidget getParent() {
      return this.parent;
   }
}
