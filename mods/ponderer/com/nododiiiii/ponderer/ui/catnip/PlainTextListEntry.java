package com.nododiiiii.ponderer.ui.catnip;

import com.nododiiiii.ponderer.ui.UIText;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigTextField;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class PlainTextListEntry extends LabeledEntry implements SearchableListEntry {
   protected static final int CONTROL_GAP = EntryTextSupport.rightControlGap();
   protected final ConfigTextField textField;
   private final String searchText;
   protected final List<PlainTextListEntry.TrailingButton> trailingButtons = new ArrayList<>();
   @Nullable
   private Supplier<String> trailingTextGetter;
   private int preferredFieldWidth = -1;
   private int minimumControlWidth = -1;
   private float controlWidthScale = 1.0F;

   public PlainTextListEntry(String labelKey, @Nullable String tooltipKey, @Nullable String hintKey, String initialValue, Consumer<String> responder) {
      super(UIText.of(labelKey));
      this.searchText = EntryTextSupport.createSearchText(labelKey, tooltipKey);
      EntryTextSupport.applyTooltip(this, labelKey, tooltipKey);
      this.textField = new ClippedConfigTextField(Minecraft.getInstance().font, 0, 0, 200, 20);
      EntryTextSupport.applyHint(this.textField, hintKey);
      this.textField.setValue(initialValue);
      this.textField.moveCursorToStart(false);
      this.textField.setResponder(responder);
      this.listeners.add(this.textField);
   }

   public ConfigTextField field() {
      return this.textField;
   }

   public void setValue(String value) {
      this.textField.setValue(value != null ? value : "");
   }

   public BoxWidget addTrailingButton(int width, Runnable onClick, Supplier<String> labelGetter, IntSupplier colorGetter, @Nullable String tooltipText) {
      BoxWidget button = (BoxWidget)new BoxWidget(0, 0, width, 16).withCallback(onClick);
      if (tooltipText != null && !tooltipText.isBlank()) {
         button.getToolTip().add(Component.literal(tooltipText));
      }

      this.trailingButtons.add(new PlainTextListEntry.TrailingButton(button, width, labelGetter, colorGetter));
      this.listeners.add(button);
      return button;
   }

   public void setTrailingText(@Nullable Supplier<String> trailingTextGetter) {
      this.trailingTextGetter = trailingTextGetter;
   }

   public void setUnitText(@Nullable Supplier<String> unitTextGetter) {
      this.setTrailingText(unitTextGetter);
   }

   public PlainTextListEntry setPreferredFieldWidth(int preferredFieldWidth) {
      this.preferredFieldWidth = preferredFieldWidth <= 0 ? -1 : preferredFieldWidth;
      return this;
   }

   public PlainTextListEntry setMinimumControlWidth(int minimumControlWidth) {
      this.minimumControlWidth = minimumControlWidth <= 0 ? -1 : minimumControlWidth;
      return this;
   }

   public PlainTextListEntry setControlWidthScale(float controlWidthScale) {
      this.controlWidthScale = Math.max(0.1F, Math.min(1.0F, controlWidthScale));
      return this;
   }

   public PlainTextListEntry setHalfWidthControl(int minimumControlWidth) {
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

   public void tick() {
      super.tick();

      for (PlainTextListEntry.TrailingButton trailingButton : this.trailingButtons) {
         trailingButton.widget().tick();
      }
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      super.render(graphics, index, y, x, width, height, mouseX, mouseY, hovered, partialTicks);
      int labelWidth = this.getLabelWidth(width);
      int trailingWidth = this.getTrailingWidth();
      int controlMinimum = this.minimumControlWidth > 0 ? this.minimumControlWidth : trailingWidth + 60;
      if (this.preferredFieldWidth > 0) {
         controlMinimum = Math.max(controlMinimum, this.preferredFieldWidth + trailingWidth);
      }

      EntryTextSupport.AlignedControlBounds controlBounds = EntryTextSupport.rightAlignedControlBounds(
         x, width, labelWidth, this.controlWidthScale, controlMinimum
      );
      int actualFieldWidth = Math.max(60, controlBounds.width() - trailingWidth);
      int fieldX = controlBounds.x();
      this.textField.setX(fieldX);
      this.textField.setY(y + 8);
      this.textField.setWidth(actualFieldWidth);
      this.textField.setHeight(20);
      this.textField.render(graphics, mouseX, mouseY, partialTicks);
      this.renderTrailing(graphics, y, x, width, height, mouseX, mouseY, partialTicks);
   }

   protected int getTrailingWidth() {
      int width = 0;
      String trailingText = this.getTrailingText();
      if (!trailingText.isEmpty()) {
         width += Minecraft.getInstance().font.width(trailingText) + CONTROL_GAP;
      }

      if (!this.trailingButtons.isEmpty()) {
         width += CONTROL_GAP;
      }

      for (int i = 0; i < this.trailingButtons.size(); i++) {
         width += this.trailingButtons.get(i).width();
         if (i + 1 < this.trailingButtons.size()) {
            width += CONTROL_GAP;
         }
      }

      return width;
   }

   protected void renderTrailing(GuiGraphics graphics, int y, int x, int width, int height, int mouseX, int mouseY, float partialTicks) {
      int cursorX = x + width - 4;
      int buttonY = y + 10;
      int buttonHeight = Math.max(16, height - 20);
      Font font = Minecraft.getInstance().font;

      for (int i = this.trailingButtons.size() - 1; i >= 0; i--) {
         PlainTextListEntry.TrailingButton trailingButton = this.trailingButtons.get(i);
         BoxWidget button = trailingButton.widget();
         cursorX -= trailingButton.width();
         button.setX(cursorX);
         button.setY(buttonY);
         button.setWidth(trailingButton.width());
         button.setHeight(buttonHeight);
         button.render(graphics, mouseX, mouseY, partialTicks);
         graphics.drawCenteredString(
            font,
            trailingButton.labelGetter().get(),
            button.getX() + button.getWidth() / 2,
            button.getY() + (button.getHeight() - 8) / 2,
            trailingButton.colorGetter().getAsInt()
         );
         cursorX -= CONTROL_GAP;
      }

      String trailingText = this.getTrailingText();
      if (!trailingText.isEmpty()) {
         graphics.drawString(font, trailingText, cursorX - font.width(trailingText), y + 14, 10526880);
      }
   }

   private String getTrailingText() {
      if (this.trailingTextGetter == null) {
         return "";
      }

      String trailingText = this.trailingTextGetter.get();
      return trailingText == null ? "" : trailingText;
   }

   protected record TrailingButton(BoxWidget widget, int width, Supplier<String> labelGetter, IntSupplier colorGetter) {
   }
}
