package com.nododiiiii.ponderer.ui.catnip;

import java.util.Locale;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.createmod.ponder.enums.PonderGuiTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class PageTurnListEntry extends LabeledEntry implements SearchableListEntry {
   private static final int OUTER_GAP = 4;
   private static final int INNER_GAP = 8;
   private static final int BUTTON_WIDTH = 22;
   private final BoxWidget prevButton;
   private final BoxWidget nextButton;
   private final Supplier<String> centerTextGetter;
   @Nullable
   private final Supplier<String> prevTooltipGetter;
   @Nullable
   private final Supplier<String> nextTooltipGetter;
   private final BooleanSupplier prevActiveGetter;
   private final BooleanSupplier nextActiveGetter;

   public PageTurnListEntry(
      Supplier<String> centerTextGetter,
      Runnable onPrev,
      Runnable onNext,
      @Nullable Supplier<String> prevTooltipGetter,
      @Nullable Supplier<String> nextTooltipGetter,
      BooleanSupplier prevActiveGetter,
      BooleanSupplier nextActiveGetter
   ) {
      super("");
      this.centerTextGetter = centerTextGetter;
      this.prevTooltipGetter = prevTooltipGetter;
      this.nextTooltipGetter = nextTooltipGetter;
      this.prevActiveGetter = prevActiveGetter;
      this.nextActiveGetter = nextActiveGetter;
      this.prevButton = (BoxWidget)new BoxWidget(0, 0, 22, 18).withCallback(onPrev);
      this.nextButton = (BoxWidget)new BoxWidget(0, 0, 22, 18).withCallback(onNext);
      PonderIconStencils.attach(this.prevButton, PonderIconStencils.centered(PonderGuiTextures.ICON_PONDER_LEFT));
      PonderIconStencils.attach(this.nextButton, PonderIconStencils.centered(PonderGuiTextures.ICON_PONDER_RIGHT));
      this.listeners.add(this.prevButton);
      this.listeners.add(this.nextButton);
      refreshTooltip(this.prevButton, prevTooltipGetter);
      refreshTooltip(this.nextButton, nextTooltipGetter);
   }

   @Override
   public boolean matchesQuery(String query) {
      StringBuilder builder = new StringBuilder(this.centerTextGetter.get());
      if (this.prevTooltipGetter != null) {
         builder.append(' ').append(this.prevTooltipGetter.get());
      }

      if (this.nextTooltipGetter != null) {
         builder.append(' ').append(this.nextTooltipGetter.get());
      }

      return builder.toString().toLowerCase(Locale.ROOT).contains(query);
   }

   @Override
   public void highlightEntry() {
      this.annotations.put("highlight", ":)");
   }

   public void tick() {
      super.tick();
      this.prevButton.active = this.prevActiveGetter.getAsBoolean();
      this.nextButton.active = this.nextActiveGetter.getAsBoolean();
      this.prevButton.tick();
      this.nextButton.tick();
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      refreshTooltip(this.prevButton, this.prevTooltipGetter);
      refreshTooltip(this.nextButton, this.nextTooltipGetter);
      boolean compact = height <= 24;
      int buttonHeight = compact ? 16 : Math.max(16, height - 20);
      int buttonY = compact ? y + 4 : y + 10;
      this.prevButton.setX(x + 4);
      this.prevButton.setY(buttonY);
      this.prevButton.setWidth(22);
      this.prevButton.setHeight(buttonHeight);
      this.prevButton.active = this.prevActiveGetter.getAsBoolean();
      this.prevButton.updateGradientFromState();
      this.prevButton.render(graphics, mouseX, mouseY, partialTicks);
      this.nextButton.setX(x + width - 4 - 22);
      this.nextButton.setY(buttonY);
      this.nextButton.setWidth(22);
      this.nextButton.setHeight(buttonHeight);
      this.nextButton.active = this.nextActiveGetter.getAsBoolean();
      this.nextButton.updateGradientFromState();
      this.nextButton.render(graphics, mouseX, mouseY, partialTicks);
      Font font = Minecraft.getInstance().font;
      int centerLeft = this.prevButton.getX() + this.prevButton.getWidth() + 8;
      int centerRight = this.nextButton.getX() - 8;
      int centerX = (centerLeft + centerRight) / 2;
      int centerY = buttonY + (buttonHeight - 8) / 2;
      int textColor = this.annotations.containsKey("highlight") ? -797589 : 16777215;
      graphics.drawCenteredString(
         font, font.plainSubstrByWidth(this.centerTextGetter.get(), Math.max(20, centerRight - centerLeft)), centerX, centerY, textColor
      );
   }

   private static void refreshTooltip(BoxWidget button, @Nullable Supplier<String> tooltipGetter) {
      button.getToolTip().clear();
      if (tooltipGetter != null) {
         String tooltip = tooltipGetter.get();
         if (tooltip != null && !tooltip.isBlank()) {
            button.getToolTip().add(Component.literal(tooltip));
         }
      }
   }
}
