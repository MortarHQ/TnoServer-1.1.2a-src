package net.p3pp3rf1y.sophisticatedcore.client.gui.controls;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public abstract class CompositeWidgetBase<T extends WidgetBase> extends WidgetBase implements ContainerEventHandler {
   protected final List<T> children = new ArrayList<>();
   private boolean dragging = false;
   @Nullable
   private GuiEventListener listener;

   protected CompositeWidgetBase(Position position, Dimension dimension) {
      super(position, dimension);
   }

   @Override
   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      this.children.forEach(child -> child.render(guiGraphics, mouseX, mouseY, partialTicks));
   }

   protected <U extends T> U addChild(U child) {
      this.children.add((T)child);
      return child;
   }

   public List<? extends GuiEventListener> children() {
      return this.children;
   }

   public boolean isDragging() {
      for (T child : this.children) {
         if (child instanceof ContainerEventHandler containerEventHandler && containerEventHandler.isDragging()) {
            return true;
         }
      }

      return this.dragging;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      return this.getChildAt(mouseX, mouseY).map(l -> {
         if (l.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(l);
            if (button == 0) {
               this.setDragging(true);
            }

            return true;
         } else {
            return false;
         }
      }).orElse(false);
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      return this.getChildAt(mouseX, mouseY).map(l -> l.mouseDragged(mouseX, mouseY, button, dragX, dragY)).orElse(false);
   }

   public void setDragging(boolean dragging) {
      this.dragging = dragging;
   }

   @Nullable
   public GuiEventListener getFocused() {
      return this.listener;
   }

   public void setFocused(@Nullable GuiEventListener listener) {
      this.listener = listener;
   }

   @Override
   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
      this.children.forEach(c -> c.renderTooltip(screen, guiGraphics, mouseX, mouseY));
   }
}
