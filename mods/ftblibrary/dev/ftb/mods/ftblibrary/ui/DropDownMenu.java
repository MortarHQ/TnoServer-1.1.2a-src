package dev.ftb.mods.ftblibrary.ui;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class DropDownMenu extends ModalPanel implements PopupMenu {
   private final ScrollBar scrollBar;
   private final Panel mainPanel;
   private final TextBox textBox;
   private float maxHeightPercent = 0.5F;

   public DropDownMenu(Panel panel, List<ContextMenuItem> i) {
      super(panel);
      this.textBox = new TextBox(this) {
         @Override
         public void onTextChanged() {
            DropDownMenu.this.refreshWidgets();
            DropDownMenu.this.scrollBar.setValue(0.0);
            super.onTextChanged();
         }
      };
      this.textBox.ghostText = Component.translatable("ftblibrary.gui.search").getString();
      boolean hasIcons = i.stream().anyMatch(item -> !item.getIcon().isEmpty());
      this.mainPanel = new DropDownMenu.MainPanel(this, i, hasIcons, this.textBox::getText);
      this.scrollBar = new PanelScrollBar(this, ScrollBar.Plane.VERTICAL, this.mainPanel);
   }

   @Override
   public boolean scrollPanel(double scroll) {
      return super.scrollPanel(scroll);
   }

   @Override
   public void addWidgets() {
      this.add(this.textBox);
      this.add(this.mainPanel);
      this.add(this.scrollBar);
   }

   @Override
   public boolean mousePressed(MouseButton button) {
      boolean pressed = super.mousePressed(button);
      if (!pressed && !this.isMouseOver()) {
         this.closeContextMenu();
         return true;
      } else {
         return pressed;
      }
   }

   @Override
   public void alignWidgets() {
      this.mainPanel.setPos(0, 14);
      this.mainPanel.alignWidgets();
      this.mainPanel.setHeight(this.mainPanel.getHeight() + 14);
      this.setWidth(this.mainPanel.width + 14);
      this.setHeight(this.mainPanel.height + 14);
      this.scrollBar.setPosAndSize(this.width - 14, 12, 14, this.height - 12);
      this.textBox.setPosAndSize(0, 0, this.width, 12);
   }

   @Override
   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      theme.drawContextMenuBackground(graphics, x, y, w, h);
   }

   @Override
   public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      GuiHelper.setupDrawing();
      graphics.pose().pushPose();
      graphics.pose().translate(0.0F, 0.0F, 900.0F);
      Color4I.BLACK.withAlpha(45).draw(graphics, x + 3, y + 3, w, h);
      super.draw(graphics, theme, x, y, w, h);
      graphics.pose().popPose();
   }

   public void setMaxHeightPercent(float maxHeightPercent) {
      this.maxHeightPercent = maxHeightPercent;
   }

   @Override
   public ModalPanel getModalPanel() {
      return this;
   }

   public class MainPanel extends Panel {
      private final List<ContextMenuItem> items;
      private List<ContextButton> activeWidgets;
      private final boolean hasIcons;
      private final Supplier<String> filter;

      public MainPanel(Panel panel, List<ContextMenuItem> items, boolean hasIcons, Supplier<String> filter) {
         super(panel);
         this.items = items;
         this.hasIcons = hasIcons;
         this.filter = filter;
      }

      @Override
      public void addWidgets() {
         List<ContextButton> list = this.items.stream().filter(item -> {
            String string = item.getTitle().getString().toLowerCase();
            String lowerCase = this.filter.get().toLowerCase();
            return string.contains(lowerCase);
         }).map(item -> new ContextButton(this, item, this.hasIcons)).toList();
         this.activeWidgets = list;
         this.addAll(list);
      }

      @Override
      public void alignWidgets() {
         int totalHeight = 0;
         int maxWidth = 0;

         for (Widget widget : this.activeWidgets) {
            maxWidth = Math.max(maxWidth, widget.width);
            totalHeight += widget.height;
         }

         this.setWidth(Math.max(maxWidth + 12, 128));
         int wantedHeight = (int)Math.min(totalHeight, this.getWindow().getGuiScaledHeight() * DropDownMenu.this.maxHeightPercent);
         this.setHeight(Math.max(wantedHeight, 32));

         for (int i = 0; i < this.activeWidgets.size(); i++) {
            Widget widget = this.activeWidgets.get(i);
            widget.setPosAndSize(0, i * 12, widget.width, widget.height);
         }
      }
   }
}
