package net.p3pp3rf1y.sophisticatedcore.client.gui;

import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.TextBox;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.util.Easing;

class SearchBox extends TextBox {
   private static final List<Component> TOOLTIP = List.of(
      Component.translatable(TranslationHelper.INSTANCE.translGui("text_box.search_box")),
      Component.translatable(TranslationHelper.INSTANCE.translGui("text_box.search_box_detail")).withStyle(ChatFormatting.GRAY)
   );
   public static final String MAGNIFYING_GLASS = "\ud83d\udd0d";
   public static final int UNFOCUSED_COLOR = 12303291;
   private final StorageScreenBase<?> screen;
   private long lastFocusChangeTime = 0L;
   private final int maximizedX;
   private final int maximizedWidth;

   public SearchBox(Position position, Dimension dimension, StorageScreenBase<?> screen) {
      super(position, dimension);
      this.screen = screen;
      this.setTextColor(12303291);
      this.setTextColorUneditable(12303291);
      this.setBordered(false);
      this.setMaxLength(50);
      this.setUnfocusedEmptyHint("\ud83d\udd0d");
      this.maximizedX = position.x();
      this.maximizedWidth = dimension.width();
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (!this.isMouseOver(mouseX, mouseY)) {
         return false;
      }

      if (this.isEditable()) {
         if (button == 0) {
            this.setFocused(true);
            this.screen.setFocused(this);
         } else if (button == 1) {
            this.setValue("");
         }

         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   @Override
   public void setFocused(boolean focused) {
      if (this.isFocused() != focused) {
         this.lastFocusChangeTime = System.currentTimeMillis();
      }

      super.setFocused(focused);
      if (focused) {
         this.setTextColor(-1);
      } else {
         this.setTextColor(12303291);
      }
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      int minWidth = this.getHeight();
      if (this.isFocused() && this.maximizedWidth > this.getWidth() || !this.isFocused() && this.getValue().isEmpty() && this.getWidth() > minWidth) {
         float ratio = Easing.EASE_IN_OUT_CUBIC.ease(Math.min((float)(System.currentTimeMillis() - this.lastFocusChangeTime) / 200.0F, 1.0F));
         int currentWidth = this.isFocused()
            ? (int)(minWidth + (this.maximizedWidth - minWidth) * ratio)
            : (int)(this.maximizedWidth - (this.maximizedWidth - minWidth) * ratio);
         this.setPosition(new Position(this.maximizedX + this.maximizedWidth - currentWidth, this.y));
         this.updateDimensions(currentWidth, this.getHeight());
      }

      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
      guiGraphics.fill(this.x, this.y, this.x + this.getWidth(), this.y + this.getHeight(), -8947849);
      guiGraphics.pose().popPose();
   }

   @Override
   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
      if (!this.isFocused() && this.isMouseOver(mouseX, mouseY)) {
         guiGraphics.renderTooltip(screen.font, TOOLTIP, Optional.empty(), mouseX, mouseY);
      }
   }
}
