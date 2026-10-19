package net.p3pp3rf1y.sophisticatedcore.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.IntConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.Label;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.WidgetBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public abstract class SettingsTabBase<T extends AbstractContainerScreen<?>> extends Tab {
   private static final int RIGHT_BORDER_WIDTH = 6;
   private static final int BOTTOM_BORDER_HEIGHT = 6;
   protected final T screen;
   protected Dimension openTabDimension = new Dimension(0, 0);
   protected boolean isOpen = false;
   private Runnable onOpen = () -> {};
   private Runnable onClose = () -> {};
   private final List<WidgetBase> hideableChildren = new ArrayList<>();
   private final List<Component> openTooltip;

   protected SettingsTabBase(
      Position position, T screen, Component tabLabel, List<Component> tooltip, List<Component> openTooltip, Function<IntConsumer, ButtonBase> getTabButton
   ) {
      super(position, tooltip, getTabButton);
      this.screen = screen;
      this.openTooltip = openTooltip;
      this.addLabel(tabLabel);
   }

   private void addLabel(Component tabLabel) {
      this.addHideableChild(new Label(new Position(this.x + 20, this.y + 8), tabLabel));
   }

   protected SettingsTabBase(Position position, T screen, Component tabLabel, Component tooltip, Function<IntConsumer, ButtonBase> getTabButton) {
      super(position, tooltip, getTabButton);
      this.screen = screen;
      this.addLabel(tabLabel);
      this.openTooltip = Collections.emptyList();
   }

   protected <U extends WidgetBase> U addHideableChild(U widget) {
      this.hideableChildren.add(widget);
      this.updateOpenTabDimension(widget);
      return widget;
   }

   private <U extends WidgetBase> void updateOpenTabDimension(U widget) {
      int widgetMaxWidthExtension = widget.getX() + widget.getWidth() + 6 - this.x;
      int widgetMaxHeightExtension = widget.getY() + widget.getHeight() + 6 - this.y;
      this.openTabDimension = new Dimension(
         Math.max(this.openTabDimension.width(), widgetMaxWidthExtension), Math.max(this.openTabDimension.height(), widgetMaxHeightExtension)
      );
   }

   public void setHandlers(Runnable onOpen, Runnable onClose, BooleanSupplier shouldRender, BooleanSupplier shouldShowTooltip) {
      this.onOpen = onOpen;
      this.onClose = onClose;
      this.setHandlers(shouldShowTooltip, shouldRender);
   }

   @Override
   protected boolean isClosedTooltipVisible(int mouseX, int mouseY) {
      return !this.isOpen && super.isClosedTooltipVisible(mouseX, mouseY);
   }

   @Override
   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
      if (!this.openTooltip.isEmpty() && this.isOpenTooltipVisible(mouseX, mouseY)) {
         guiGraphics.renderTooltip(screen.font, this.openTooltip, Optional.empty(), mouseX, mouseY);
      }
   }

   private boolean isOpenTooltipVisible(int mouseX, int mouseY) {
      return this.isOpen && mouseX > this.x && mouseY > this.y + 3 && mouseX < this.x + 18 && mouseY < this.y + 21;
   }

   public void close() {
      this.setOpen(false);
      this.onTabClose();
   }

   protected void onTabOpen() {
      this.setWidth(this.openTabDimension.width());
      this.setHeight(this.openTabDimension.height());
      this.hideableChildren.forEach(x$0 -> this.addChild(x$0));
      this.onOpen.run();
   }

   protected void onTabClose() {
      this.setWidth(21);
      this.setHeight(24);
      this.children.removeAll(this.hideableChildren);
      this.onClose.run();
   }

   protected void setOpen(boolean isOpen) {
      this.isOpen = isOpen;
      if (isOpen) {
         this.onTabOpen();
      } else {
         this.onTabClose();
      }
   }

   @Override
   protected void onTabIconClicked(int button) {
      if (button == 0) {
         this.setOpen(!this.isOpen);
      }
   }
}
