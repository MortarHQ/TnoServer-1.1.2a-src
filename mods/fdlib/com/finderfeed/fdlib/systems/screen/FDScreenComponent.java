package com.finderfeed.fdlib.systems.screen;

import com.finderfeed.fdlib.systems.screen.annotations.FDColor;
import com.finderfeed.fdlib.systems.screen.annotations.FDName;
import com.finderfeed.fdlib.systems.screen.annotations.VComponent;
import com.finderfeed.fdlib.systems.screen.default_components.buttons.selector_button.EnumSelectorButton;
import com.finderfeed.fdlib.systems.screen.default_components.value_components.FloatVComponent;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;

public abstract class FDScreenComponent implements GuiEventListener {
   private FDScreen screen;
   private String uniqueId;
   private boolean focused;
   private boolean hovered;
   @VComponent(FloatVComponent.class)
   @FDColor(r = 0.25F, g = 0.25F, b = 0.25F, a = 1.0F)
   private float x;
   @VComponent(FloatVComponent.class)
   @FDColor(r = 0.25F, g = 0.25F, b = 0.25F, a = 1.0F)
   private float y;
   @VComponent(FloatVComponent.class)
   @FDColor(r = 0.25F, g = 0.25F, b = 0.25F, a = 1.0F)
   @FDName("Width")
   private float width;
   @VComponent(FloatVComponent.class)
   @FDColor(r = 0.25F, g = 0.25F, b = 0.25F, a = 1.0F)
   @FDName("Height")
   private float height;
   private boolean removed;
   @VComponent(EnumSelectorButton.class)
   @FDName("Rendering anchor")
   private Anchor anchor = Anchor.TOP_LEFT;
   private FDSCChildren children;
   private FDScreenComponent parent;

   public FDScreenComponent(FDScreen screen, String uniqueId, float x, float y, float width, float height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.uniqueId = uniqueId;
      this.screen = screen;
      this.children = new FDSCChildren(this, screen);
   }

   public abstract void renderComponent(GuiGraphics var1, float var2, float var3, float var4, float var5, float var6);

   public void postRenderComponent(GuiGraphics graphics, float x, float y, float mx, float my, float partialTicks) {
   }

   public void tick() {
      this.children.tick();
   }

   public void setFocused(boolean focused) {
      this.focused = focused;
      this.onFocused(focused);
   }

   public void onFocused(boolean state) {
   }

   public boolean isFocused() {
      return this.focused;
   }

   public void setParent(FDScreenComponent parent) {
      this.parent = parent;
   }

   public void setX(float x) {
      this.x = x;
   }

   public boolean isMouseOver(float mx, float my) {
      return FDRenderUtil.isMouseInBounds(mx, my, 0.0F, 0.0F, this.getWidth(), this.getHeight());
   }

   public void setY(float y) {
      this.y = y;
   }

   public void setHeight(float height) {
      float prev = this.height;
      this.height = height;
      if (this.parent != null) {
         this.parent.onChildHeightChanged(this, prev, height);
      }
   }

   public void setHeightWithoutUpdate(float height) {
      this.height = height;
   }

   public void setWidth(float width) {
      float prev = this.width;
      this.width = width;
      if (this.parent != null) {
         this.parent.onChildWidthChanged(this, prev, width);
      }
   }

   public void setWidthWithoutUpdate(float width) {
      this.width = width;
   }

   public float getHeight() {
      return this.height;
   }

   public float getWidth() {
      return this.width;
   }

   public float getY() {
      return this.y;
   }

   public float getX() {
      return this.x;
   }

   public String getUniqueId() {
      return this.uniqueId;
   }

   public FDScreen getScreen() {
      return this.screen;
   }

   public FDScreenComponent getParent() {
      return this.parent;
   }

   public FDSCChildren getChildren() {
      return this.children;
   }

   public void setRemoved(boolean removed) {
      this.removed = removed;
   }

   public boolean isRemoved() {
      return this.removed;
   }

   public void setHovered(boolean hovered) {
      this.hovered = hovered;
      this.onHovered(hovered);
   }

   public void onHovered(boolean state) {
   }

   public boolean isHovered() {
      return this.hovered;
   }

   public Anchor getAnchor() {
      return this.anchor;
   }

   public void setAnchor(Anchor anchor) {
      this.anchor = anchor;
   }

   public void onChildAdded(FDScreenComponent component) {
   }

   public void onChildRemoved(FDScreenComponent component) {
   }

   public void onChildWidthChanged(FDScreenComponent child, float previous, float current) {
   }

   public void onChildHeightChanged(FDScreenComponent child, float previous, float current) {
   }
}
