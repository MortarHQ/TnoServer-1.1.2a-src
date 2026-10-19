package com.nododiiiii.ponderer.ui.catnip;

import java.util.Locale;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class CollapsibleSectionHeaderListEntry extends LabeledEntry implements SearchableListEntry {
   private static final int BUTTON_WIDTH = 12;
   private static final int BUTTON_HEIGHT = 8;
   private static final int OUTER_GAP = 4;
   private final Supplier<String> titleGetter;
   private final BooleanSupplier collapsedGetter;
   @Nullable
   private final Supplier<String> collapsedTooltipGetter;
   @Nullable
   private final Supplier<String> expandedTooltipGetter;
   private final BoxWidget toggleButton;

   public CollapsibleSectionHeaderListEntry(
      Supplier<String> titleGetter,
      BooleanSupplier collapsedGetter,
      Runnable onToggle,
      @Nullable Supplier<String> collapsedTooltipGetter,
      @Nullable Supplier<String> expandedTooltipGetter
   ) {
      super("");
      this.titleGetter = titleGetter;
      this.collapsedGetter = collapsedGetter;
      this.collapsedTooltipGetter = collapsedTooltipGetter;
      this.expandedTooltipGetter = expandedTooltipGetter;
      this.toggleButton = (BoxWidget)new BoxWidget(0, 0, 12, 8).withPadding(2.0F, 2.0F).withCallback(onToggle);
      this.listeners.add(this.toggleButton);
   }

   @Override
   public boolean matchesQuery(String query) {
      return this.titleGetter.get().toLowerCase(Locale.ROOT).contains(query);
   }

   @Override
   public void highlightEntry() {
      this.annotations.put("highlight", ":)");
   }

   public void tick() {
      super.tick();
      this.toggleButton.tick();
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      this.refreshTooltip();
      boolean compact = height <= 24;
      int buttonHeight = 8;
      int buttonY = y + Math.max(4, (height - buttonHeight) / 2);
      int buttonX = x + width - 4 - 12;
      this.toggleButton.setX(buttonX);
      this.toggleButton.setY(buttonY);
      this.toggleButton.setWidth(12);
      this.toggleButton.setHeight(buttonHeight);
      this.toggleButton.active = true;
      this.toggleButton.updateGradientFromState();
      this.toggleButton.render(graphics, mouseX, mouseY, partialTicks);
      String title = this.titleGetter.get();
      Font font = Minecraft.getInstance().font;
      int color = this.annotations.containsKey("highlight") ? -797589 : -3355529;
      int titleWidth = Math.max(20, buttonX - x - 12);
      graphics.drawString(font, font.plainSubstrByWidth(title, titleWidth), x + 4, compact ? y + 8 : y + 11, color);
      int lineY = compact ? y + height - 3 : y + height - 10;
      graphics.fill(x + 4, lineY, buttonX - 4, lineY + 1, 1090519039);
      String symbol = this.collapsedGetter.getAsBoolean() ? "+" : "-";
      graphics.drawCenteredString(
         font,
         symbol,
         this.toggleButton.getX() + this.toggleButton.getWidth() / 2,
         this.toggleButton.getY() + (this.toggleButton.getHeight() - 8) / 2,
         16777215
      );
   }

   private void refreshTooltip() {
      this.toggleButton.getToolTip().clear();
      Supplier<String> tooltipGetter = this.collapsedGetter.getAsBoolean() ? this.collapsedTooltipGetter : this.expandedTooltipGetter;
      String tooltip = tooltipGetter == null ? null : tooltipGetter.get();
      if (tooltip != null && !tooltip.isBlank()) {
         this.toggleButton.getToolTip().add(Component.literal(tooltip));
      }
   }
}
