package com.teamresourceful.resourcefullib.client.components;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.jetbrains.annotations.NotNull;

public abstract class ParentWidget extends AbstractContainerEventHandler implements Renderable, LayoutElement, NarratableEntry {
   public final List<Renderable> renderables = Lists.newArrayList();
   protected final List<GuiEventListener> children = Lists.newArrayList();
   protected int x;
   protected int y;
   protected int width;
   protected int height;
   private boolean active = true;

   public ParentWidget(int x, int y) {
      this.x = x;
      this.y = y;
   }

   protected abstract void init();

   @NotNull
   public List<? extends GuiEventListener> children() {
      return !this.active ? List.of() : this.children;
   }

   protected <T extends GuiEventListener & Renderable> T addRenderableWidget(T widget) {
      this.renderables.add(widget);
      this.children.add(widget);
      return widget;
   }

   public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      if (this.active) {
         for (Renderable renderable : this.renderables) {
            renderable.render(graphics, mouseX, mouseY, partialTicks);
         }
      }
   }

   @NotNull
   public NarrationPriority narrationPriority() {
      return NarrationPriority.NONE;
   }

   public void updateNarration(@NotNull NarrationElementOutput output) {
   }

   public void setFocused(boolean focused) {
      if (!focused) {
         this.setFocused(null);
      }
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public void visitWidgets(Consumer<AbstractWidget> consumer) {
      for (GuiEventListener child : this.children()) {
         if (child instanceof AbstractWidget widget) {
            consumer.accept(widget);
         }

         if (child instanceof LayoutElement layout) {
            layout.visitWidgets(consumer);
         }
      }
   }

   @NotNull
   public ScreenRectangle getRectangle() {
      return super.getRectangle();
   }

   public boolean isMouseOver(double mouseX, double mouseY) {
      return this.active && mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      return this.active && super.mouseClicked(mouseX, mouseY, button);
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public boolean isActive() {
      return this.active;
   }
}
