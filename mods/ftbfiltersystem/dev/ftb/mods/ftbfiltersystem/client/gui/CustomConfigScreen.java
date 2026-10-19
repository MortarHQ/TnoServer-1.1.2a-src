package dev.ftb.mods.ftbfiltersystem.client.gui;

import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterConfigScreen;
import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterScreen;
import dev.ftb.mods.ftbfiltersystem.filter.CustomFilter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public class CustomConfigScreen extends AbstractFilterConfigScreen<CustomFilter> {
   private EditBox idEditBox;
   private EditBox extraEditBox;

   public CustomConfigScreen(CustomFilter filter, AbstractFilterScreen parentScreen) {
      super(filter, parentScreen, 176, 80);
   }

   @Override
   protected void init() {
      super.init();
      this.idEditBox = new EditBox(this.font, this.leftPos + 8, this.topPos + 40, this.guiWidth - 16, 9 + 4, Component.empty());
      this.idEditBox.setMaxLength(1024);
      this.idEditBox.setBordered(true);
      this.idEditBox.setVisible(true);
      this.idEditBox.setTextColor(16777215);
      this.idEditBox.setValue(this.filter.getEventId());
      this.idEditBox.setFilter(s -> s.isEmpty() || StringUtils.isAlphanumeric(s));
      this.addRenderableWidget(this.idEditBox);
      this.extraEditBox = new EditBox(this.font, this.leftPos + 8, this.topPos + 70, this.guiWidth - 16, 9 + 4, Component.empty());
      this.extraEditBox.setMaxLength(1024);
      this.extraEditBox.setBordered(true);
      this.extraEditBox.setVisible(true);
      this.extraEditBox.setTextColor(16777215);
      this.extraEditBox.setValue(this.filter.getExtraData());
      this.addRenderableWidget(this.extraEditBox);
      this.setInitialFocus(this.idEditBox);
   }

   @Override
   public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
      guiGraphics.drawString(this.font, Component.translatable("ftbfiltersystem.gui.custom_id"), this.leftPos + 8, this.topPos + 30, 4210752, false);
      guiGraphics.drawString(this.font, Component.translatable("ftbfiltersystem.gui.custom_data"), this.leftPos + 8, this.topPos + 60, 4210752, false);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifier) {
      if (keyCode != 257 || !this.idEditBox.canConsumeInput() && !this.extraEditBox.canConsumeInput()) {
         return super.keyPressed(keyCode, scanCode, modifier);
      }

      this.applyChanges();
      return true;
   }

   @Nullable
   protected CustomFilter makeNewFilter() {
      return new CustomFilter(this.filter.getParent(), this.idEditBox.getValue(), this.extraEditBox.getValue());
   }
}
