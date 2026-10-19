package com.nododiiiii.ponderer.ui.catnip;

import com.nododiiiii.ponderer.ui.UIText;
import java.util.ArrayList;
import java.util.List;
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

public class XyzListEntry extends LabeledEntry implements SearchableListEntry {
   private static final int CONTROL_GAP = EntryTextSupport.rightControlGap();
   private final String searchText;
   private final ConfigTextField xField;
   private final ConfigTextField yField;
   private final ConfigTextField zField;
   private final List<XyzListEntry.TrailingButton> trailingButtons = new ArrayList<>();

   public XyzListEntry(String labelKey, @Nullable String tooltipKey, @Nullable String xHint, @Nullable String yHint, @Nullable String zHint) {
      super(UIText.of(labelKey));
      this.searchText = EntryTextSupport.createSearchText(labelKey, tooltipKey);
      EntryTextSupport.applyTooltip(this, labelKey, tooltipKey);
      this.xField = new ClippedConfigTextField(Minecraft.getInstance().font, 0, 0, 52, 20);
      this.yField = new ClippedConfigTextField(Minecraft.getInstance().font, 0, 0, 52, 20);
      this.zField = new ClippedConfigTextField(Minecraft.getInstance().font, 0, 0, 52, 20);
      this.xField.setMaxLength(32);
      this.yField.setMaxLength(32);
      this.zField.setMaxLength(32);
      EntryTextSupport.applyHint(this.xField, xHint);
      EntryTextSupport.applyHint(this.yField, yHint);
      EntryTextSupport.applyHint(this.zField, zHint);
      this.listeners.add(this.xField);
      this.listeners.add(this.yField);
      this.listeners.add(this.zField);
   }

   public ConfigTextField xField() {
      return this.xField;
   }

   public ConfigTextField yField() {
      return this.yField;
   }

   public ConfigTextField zField() {
      return this.zField;
   }

   public BoxWidget addTrailingButton(int width, Runnable onClick, Supplier<String> labelGetter, IntSupplier colorGetter, @Nullable String tooltipText) {
      BoxWidget button = (BoxWidget)new BoxWidget(0, 0, width, 16).withCallback(onClick);
      if (tooltipText != null && !tooltipText.isBlank()) {
         button.getToolTip().add(Component.literal(tooltipText));
      }

      this.trailingButtons.add(new XyzListEntry.TrailingButton(button, width, labelGetter, colorGetter));
      this.listeners.add(button);
      return button;
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

      for (XyzListEntry.TrailingButton trailingButton : this.trailingButtons) {
         trailingButton.widget().tick();
      }
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      super.render(graphics, index, y, x, width, height, mouseX, mouseY, hovered, partialTicks);
      int labelWidth = this.getLabelWidth(width);
      int buttonsWidth = this.trailingButtons.isEmpty() ? 0 : CONTROL_GAP;

      for (int i = 0; i < this.trailingButtons.size(); i++) {
         buttonsWidth += this.trailingButtons.get(i).width();
         if (i + 1 < this.trailingButtons.size()) {
            buttonsWidth += CONTROL_GAP;
         }
      }

      int minControlWidth = 102 + CONTROL_GAP * 2 + buttonsWidth;
      int controlWidth = Math.max(minControlWidth, EntryTextSupport.controlAreaWidth(width, labelWidth));
      int fieldsWidth = controlWidth - buttonsWidth - CONTROL_GAP * 2;
      int baseFieldWidth = Math.max(34, fieldsWidth / 3);
      int remainder = Math.max(0, fieldsWidth - baseFieldWidth * 3);
      int xWidth = baseFieldWidth;
      int yWidth = baseFieldWidth;
      int zWidth = baseFieldWidth + remainder;
      int fieldX = x + labelWidth + 4;
      int fieldY = y + 8;
      this.xField.setX(fieldX);
      this.xField.setY(fieldY);
      this.xField.setWidth(xWidth);
      this.xField.setHeight(20);
      this.xField.render(graphics, mouseX, mouseY, partialTicks);
      this.yField.setX(fieldX + xWidth + CONTROL_GAP);
      this.yField.setY(fieldY);
      this.yField.setWidth(yWidth);
      this.yField.setHeight(20);
      this.yField.render(graphics, mouseX, mouseY, partialTicks);
      this.zField.setX(fieldX + xWidth + CONTROL_GAP + yWidth + CONTROL_GAP);
      this.zField.setY(fieldY);
      this.zField.setWidth(zWidth);
      this.zField.setHeight(20);
      this.zField.render(graphics, mouseX, mouseY, partialTicks);
      int cursorX = x + width - 4;
      int buttonY = y + 10;
      int buttonHeight = Math.max(16, height - 20);
      Font font = Minecraft.getInstance().font;

      for (int i = this.trailingButtons.size() - 1; i >= 0; i--) {
         XyzListEntry.TrailingButton trailingButton = this.trailingButtons.get(i);
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
   }

   private record TrailingButton(BoxWidget widget, int width, Supplier<String> labelGetter, IntSupplier colorGetter) {
   }
}
