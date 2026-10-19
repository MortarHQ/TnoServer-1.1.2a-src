package com.nododiiiii.ponderer.ui.catnip;

import java.util.Locale;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class FullButtonListEntry extends LabeledEntry implements SearchableListEntry {
   private final BoxWidget button;
   private final Supplier<String> labelGetter;
   @Nullable
   private final Supplier<String> tooltipGetter;
   private final IntSupplier colorGetter;
   private final BooleanSupplier activeGetter;
   private int maxButtonWidth = -1;

   public FullButtonListEntry(String label, @Nullable String tooltipText, Runnable onClick) {
      this(() -> label, tooltipText == null ? null : () -> tooltipText, onClick, () -> 16777215, () -> true);
   }

   public FullButtonListEntry(
      Supplier<String> labelGetter, @Nullable Supplier<String> tooltipGetter, Runnable onClick, IntSupplier colorGetter, BooleanSupplier activeGetter
   ) {
      super("");
      this.labelGetter = labelGetter;
      this.tooltipGetter = tooltipGetter;
      this.colorGetter = colorGetter;
      this.activeGetter = activeGetter;
      this.button = (BoxWidget)new BoxWidget(0, 0, 200, 18).withCallback(onClick);
      this.listeners.add(this.button);
      this.refreshTooltip();
   }

   public BoxWidget button() {
      return this.button;
   }

   public FullButtonListEntry setMaxButtonWidth(int maxButtonWidth) {
      this.maxButtonWidth = maxButtonWidth <= 0 ? -1 : maxButtonWidth;
      return this;
   }

   @Override
   public boolean matchesQuery(String query) {
      return this.buildSearchText().contains(query);
   }

   @Override
   public void highlightEntry() {
      this.annotations.put("highlight", ":)");
   }

   public void tick() {
      super.tick();
      this.button.active = this.activeGetter.getAsBoolean();
      this.button.tick();
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      this.refreshTooltip();
      boolean compact = height <= 24;
      int buttonY = compact ? y + 4 : y + 10;
      int availableWidth = Math.max(40, width - 8);
      int buttonWidth = this.maxButtonWidth > 0 ? Math.min(availableWidth, this.maxButtonWidth) : availableWidth;
      int buttonX = x + (width - buttonWidth) / 2;
      int buttonHeight = compact ? 16 : Math.max(16, height - 20);
      this.button.setX(buttonX);
      this.button.setY(buttonY);
      this.button.setWidth(buttonWidth);
      this.button.setHeight(buttonHeight);
      this.button.active = this.activeGetter.getAsBoolean();
      this.button.render(graphics, mouseX, mouseY, partialTicks);
      int color = this.button.active ? this.colorGetter.getAsInt() : 7829367;
      graphics.drawCenteredString(
         Minecraft.getInstance().font,
         this.labelGetter.get(),
         this.button.getX() + this.button.getWidth() / 2,
         this.button.getY() + (this.button.getHeight() - 8) / 2,
         color
      );
   }

   private void refreshTooltip() {
      this.button.getToolTip().clear();
      String tooltip = this.tooltipGetter != null ? this.tooltipGetter.get() : null;
      if (tooltip != null && !tooltip.isBlank()) {
         this.button.getToolTip().add(Component.literal(tooltip));
      }
   }

   private String buildSearchText() {
      StringBuilder builder = new StringBuilder(this.labelGetter.get());
      if (this.tooltipGetter != null) {
         String tooltip = this.tooltipGetter.get();
         if (tooltip != null && !tooltip.isBlank()) {
            builder.append(' ').append(tooltip);
         }
      }

      return builder.toString().toLowerCase(Locale.ROOT);
   }
}
