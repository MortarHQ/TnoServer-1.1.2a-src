package dev.ftb.mods.ftblibrary.ui;

import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class ContextButton extends Button {
   public final ContextMenuItem item;
   private final boolean hasIcons;

   public ContextButton(Panel panel, ContextMenuItem item, boolean hasIcons) {
      super(panel, item.getTitle(), item.getIcon());
      this.hasIcons = hasIcons;
      this.item = item;
      this.setSize(panel.getGui().getTheme().getStringWidth(item.getTitle()) + (hasIcons ? 14 : 4), 12);
   }

   @Override
   public void addMouseOverText(TooltipList list) {
      this.item.addMouseOverText(list);
   }

   @Override
   public WidgetType getWidgetType() {
      if (!this.item.isClickable()) {
         return WidgetType.NORMAL;
      } else {
         return this.item.isEnabled() ? super.getWidgetType() : WidgetType.DISABLED;
      }
   }

   @Override
   public void drawIcon(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      this.item.drawIcon(graphics, theme, x, y, w, h);
   }

   @Override
   public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      GuiHelper.setupDrawing();
      if (this.hasIcons) {
         this.drawIcon(graphics, theme, x + 1, y + 2, 8, 8);
         theme.drawString(graphics, this.getTitle(), x + 11, y + 2, theme.getContentColor(this.getWidgetType()), 2);
      } else {
         theme.drawString(graphics, this.getTitle(), x + 2, y + 2, theme.getContentColor(this.getWidgetType()), 2);
      }
   }

   @Override
   public void onClicked(MouseButton button) {
      if (this.item.isClickable()) {
         this.playClickSound();
      }

      if (this.item.getYesNoText().getString().isEmpty()) {
         this.item.onClicked(this, this.parent, button);
      } else {
         this.getGui().openYesNo(this.item.getYesNoText(), Component.literal(""), () -> this.item.onClicked(this, this.parent, button));
      }
   }
}
