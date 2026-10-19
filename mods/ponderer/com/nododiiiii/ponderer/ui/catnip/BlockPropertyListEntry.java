package com.nododiiiii.ponderer.ui.catnip;

import com.nododiiiii.ponderer.ui.UIText;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigTextField;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.createmod.ponder.enums.PonderGuiTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class BlockPropertyListEntry extends LabeledEntry implements SearchableListEntry {
   private static final int CONTROL_GAP = EntryTextSupport.rightControlGap();
   private final String searchText;
   private final ConfigTextField keyField;
   private final ConfigTextField valueField;
   private final BoxWidget removeButton;

   public BlockPropertyListEntry(String labelKey, @Nullable String tooltipKey, String keyValue, String propertyValue, Runnable onRemove) {
      super(UIText.of(labelKey));
      this.searchText = EntryTextSupport.createSearchText(labelKey, tooltipKey);
      EntryTextSupport.applyTooltip(this, labelKey, tooltipKey);
      this.keyField = new ClippedConfigTextField(Minecraft.getInstance().font, 0, 0, 70, 20);
      this.valueField = new ClippedConfigTextField(Minecraft.getInstance().font, 0, 0, 70, 20);
      this.keyField.setValue(keyValue);
      this.valueField.setValue(propertyValue);
      this.keyField.moveCursorToStart(false);
      this.valueField.moveCursorToStart(false);
      this.keyField.setHint("facing");
      this.valueField.setHint("north");
      this.removeButton = (BoxWidget)new BoxWidget(0, 0, 20, 16).withCallback(onRemove);
      PonderIconStencils.attachFail(this.removeButton, PonderIconStencils.centered(PonderGuiTextures.ICON_DISABLE));
      this.listeners.add(this.keyField);
      this.listeners.add(this.valueField);
      this.listeners.add(this.removeButton);
   }

   public ConfigTextField keyField() {
      return this.keyField;
   }

   public ConfigTextField valueField() {
      return this.valueField;
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
      this.removeButton.tick();
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      super.render(graphics, index, y, x, width, height, mouseX, mouseY, hovered, partialTicks);
      int labelWidth = this.getLabelWidth(width);
      int buttonWidth = 20;
      int equalsWidth = Minecraft.getInstance().font.width("=");
      int minControlWidth = 96 + buttonWidth + equalsWidth + CONTROL_GAP * 3;
      int controlWidth = Math.max(minControlWidth, EntryTextSupport.controlAreaWidth(width, labelWidth));
      int fieldsWidth = controlWidth - buttonWidth - equalsWidth - CONTROL_GAP * 3;
      int keyWidth = Math.max(48, fieldsWidth / 2);
      int valueWidth = Math.max(48, fieldsWidth - keyWidth);
      int fieldX = x + labelWidth + 4;
      int fieldY = y + 8;
      this.keyField.setX(fieldX);
      this.keyField.setY(fieldY);
      this.keyField.setWidth(keyWidth);
      this.keyField.setHeight(20);
      this.keyField.render(graphics, mouseX, mouseY, partialTicks);
      int equalsX = fieldX + keyWidth + CONTROL_GAP;
      int valueX = equalsX + equalsWidth + CONTROL_GAP;
      this.valueField.setX(valueX);
      this.valueField.setY(fieldY);
      this.valueField.setWidth(valueWidth);
      this.valueField.setHeight(20);
      this.valueField.render(graphics, mouseX, mouseY, partialTicks);
      graphics.drawString(Minecraft.getInstance().font, "=", equalsX, y + 14, 10526880);
      int buttonHeight = Math.max(16, height - 20);
      this.removeButton.setX(valueX + valueWidth + CONTROL_GAP);
      this.removeButton.setY(y + 10);
      this.removeButton.setWidth(buttonWidth);
      this.removeButton.setHeight(buttonHeight);
      this.removeButton.render(graphics, mouseX, mouseY, partialTicks);
   }
}
