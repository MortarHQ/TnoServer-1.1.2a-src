package com.nododiiiii.ponderer.ui.catnip;

import com.nododiiiii.ponderer.ui.UIText;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class ButtonListEntry extends LabeledEntry implements SearchableListEntry {
   private static final int BOX_WIDGET_VISUAL_INSET = 4;
   private static final int TEXT_FIELD_BORDER_NUDGE = 2;
   private final String searchText;
   private final BoxWidget button;
   private final Supplier<String> labelGetter;
   private final IntSupplier colorGetter;
   @Nullable
   private final Supplier<String> buttonTooltipGetter;
   protected final int buttonWidth;
   private int minimumControlWidth = -1;
   private float controlWidthScale = 1.0F;

   public ButtonListEntry(
      String labelKey,
      @Nullable String tooltipKey,
      int buttonWidth,
      Runnable onClick,
      Supplier<String> labelGetter,
      IntSupplier colorGetter,
      @Nullable String buttonTooltipText
   ) {
      this(labelKey, tooltipKey, buttonWidth, onClick, labelGetter, colorGetter, buttonTooltipText == null ? null : () -> buttonTooltipText);
   }

   public ButtonListEntry(
      String labelKey,
      @Nullable String tooltipKey,
      int buttonWidth,
      Runnable onClick,
      Supplier<String> labelGetter,
      IntSupplier colorGetter,
      @Nullable Supplier<String> buttonTooltipGetter
   ) {
      super(UIText.of(labelKey));
      this.searchText = EntryTextSupport.createSearchText(labelKey, tooltipKey);
      this.labelGetter = labelGetter;
      this.colorGetter = colorGetter;
      this.buttonTooltipGetter = buttonTooltipGetter;
      this.buttonWidth = buttonWidth;
      EntryTextSupport.applyTooltip(this, labelKey, tooltipKey);
      this.button = (BoxWidget)new BoxWidget(0, 0, buttonWidth, 16).withCallback(onClick);
      this.listeners.add(this.button);
      this.refreshButtonTooltip();
   }

   public BoxWidget button() {
      return this.button;
   }

   public ButtonListEntry setControlWidthScale(float controlWidthScale) {
      this.controlWidthScale = Math.max(0.1F, Math.min(1.0F, controlWidthScale));
      return this;
   }

   public ButtonListEntry setMinimumControlWidth(int minimumControlWidth) {
      this.minimumControlWidth = minimumControlWidth <= 0 ? -1 : minimumControlWidth;
      return this;
   }

   public ButtonListEntry setHalfWidthControl(int minimumControlWidth) {
      return this.setControlWidthScale(EntryTextSupport.halfWidthControlScale()).setMinimumControlWidth(minimumControlWidth);
   }

   @Override
   public boolean matchesQuery(String query) {
      return this.searchText.contains(query);
   }

   @Override
   public void highlightEntry() {
      this.annotations.put("highlight", ":)");
   }

   protected int getLabelWidth(int totalWidth) {
      return EntryTextSupport.compactLabelWidth(totalWidth);
   }

   protected int getMinimumControlWidth() {
      return this.minimumControlWidth > 0 ? this.minimumControlWidth : this.buttonWidth;
   }

   protected int getRenderedButtonWidth(int controlWidth) {
      return controlWidth;
   }

   public void tick() {
      super.tick();
      this.button.tick();
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      super.render(graphics, index, y, x, width, height, mouseX, mouseY, hovered, partialTicks);
      this.refreshButtonTooltip();
      EntryTextSupport.AlignedControlBounds controlBounds = EntryTextSupport.rightAlignedControlBounds(
         x, width, this.getLabelWidth(width), this.controlWidthScale, this.getMinimumControlWidth()
      );
      int renderedButtonWidth = Math.min(controlBounds.width(), this.getRenderedButtonWidth(controlBounds.width()));
      int visibleButtonX = controlBounds.rightAlignedContentX(renderedButtonWidth) - 2;
      int widgetX = visibleButtonX + 4;
      int widgetWidth = Math.max(1, renderedButtonWidth - 8);
      int buttonHeight = Math.max(16, height - 20);
      this.button.setX(widgetX);
      this.button.setY(y + 10);
      this.button.setWidth(widgetWidth);
      this.button.setHeight(buttonHeight);
      this.button.render(graphics, mouseX, mouseY, partialTicks);
      graphics.drawCenteredString(
         Minecraft.getInstance().font,
         this.labelGetter.get(),
         this.button.getX() + this.button.getWidth() / 2,
         this.button.getY() + (this.button.getHeight() - 8) / 2,
         this.colorGetter.getAsInt()
      );
   }

   private void refreshButtonTooltip() {
      this.button.getToolTip().clear();
      if (this.buttonTooltipGetter != null) {
         String tooltipText = this.buttonTooltipGetter.get();
         if (tooltipText != null && !tooltipText.isBlank()) {
            this.button.getToolTip().add(Component.literal(tooltipText));
         }
      }
   }
}
