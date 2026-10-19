package com.nododiiiii.ponderer.ui.catnip;

import com.nododiiiii.ponderer.ui.UIText;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigTextField;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class DualTextListEntry extends LabeledEntry implements SearchableListEntry {
   private static final int FIELD_GAP = EntryTextSupport.rightControlGap();
   private final String searchText;
   private final ConfigTextField firstField;
   private final ConfigTextField secondField;
   private int firstPreferredWidth = -1;
   private int secondPreferredWidth = -1;

   public DualTextListEntry(String labelKey, @Nullable String tooltipKey, @Nullable String firstHint, @Nullable String secondHint) {
      super(UIText.of(labelKey));
      this.searchText = EntryTextSupport.createSearchText(labelKey, tooltipKey);
      EntryTextSupport.applyTooltip(this, labelKey, tooltipKey);
      this.firstField = new ClippedConfigTextField(Minecraft.getInstance().font, 0, 0, 60, 20);
      this.secondField = new ClippedConfigTextField(Minecraft.getInstance().font, 0, 0, 60, 20);
      this.firstField.setMaxLength(32);
      this.secondField.setMaxLength(32);
      EntryTextSupport.applyHint(this.firstField, firstHint);
      EntryTextSupport.applyHint(this.secondField, secondHint);
      this.listeners.add(this.firstField);
      this.listeners.add(this.secondField);
   }

   public void setPreferredWidths(int firstWidth, int secondWidth) {
      this.firstPreferredWidth = firstWidth;
      this.secondPreferredWidth = secondWidth;
   }

   public ConfigTextField firstField() {
      return this.firstField;
   }

   public ConfigTextField secondField() {
      return this.secondField;
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
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      super.render(graphics, index, y, x, width, height, mouseX, mouseY, hovered, partialTicks);
      int labelWidth = this.getLabelWidth(width);
      int controlWidth = Math.max(120, EntryTextSupport.controlAreaWidth(width, labelWidth));
      int fieldsWidth = Math.max(80, controlWidth - FIELD_GAP);
      int firstWidth;
      int secondWidth;
      if (this.firstPreferredWidth > 0 && this.secondPreferredWidth > 0) {
         int preferredTotal = this.firstPreferredWidth + this.secondPreferredWidth;
         firstWidth = Math.max(40, fieldsWidth * this.firstPreferredWidth / preferredTotal);
         secondWidth = Math.max(40, fieldsWidth - firstWidth);
         if (firstWidth + secondWidth > fieldsWidth) {
            secondWidth = Math.max(40, fieldsWidth - firstWidth);
         }
      } else {
         firstWidth = Math.max(40, fieldsWidth / 2);
         secondWidth = Math.max(40, fieldsWidth - firstWidth);
      }

      int widthOverflow = firstWidth + secondWidth - fieldsWidth;
      if (widthOverflow > 0) {
         secondWidth = Math.max(40, secondWidth - widthOverflow);
      }

      int fieldX = x + labelWidth + 4;
      int fieldY = y + 8;
      this.firstField.setX(fieldX);
      this.firstField.setY(fieldY);
      this.firstField.setWidth(firstWidth);
      this.firstField.setHeight(20);
      this.firstField.render(graphics, mouseX, mouseY, partialTicks);
      this.secondField.setX(fieldX + firstWidth + FIELD_GAP);
      this.secondField.setY(fieldY);
      this.secondField.setWidth(secondWidth);
      this.secondField.setHeight(20);
      this.secondField.render(graphics, mouseX, mouseY, partialTicks);
   }
}
