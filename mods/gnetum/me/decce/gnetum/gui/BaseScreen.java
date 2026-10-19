package me.decce.gnetum.gui;

import me.decce.gnetum.Gnetum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class BaseScreen extends Screen {
   protected final Screen parent;

   public BaseScreen() {
      this(Minecraft.getInstance().screen);
   }

   protected BaseScreen(Screen parent) {
      this(Component.literal("Gnetum"), parent);
   }

   protected BaseScreen(Component p_96550_, Screen parent) {
      super(p_96550_);
      this.parent = parent;
   }

   public void close() {
      Gnetum.config.save();
      Minecraft.getInstance().setScreen(this.parent);
   }

   public void onClose() {
      super.onClose();
      Gnetum.config.save();
   }

   public void init() {
      this.rebuild();
   }

   protected void rebuild() {
      this.clearWidgets();
   }

   protected void addDoneButton() {
      Button btnClose = Button.builder(Component.translatable("gui.done"), btn -> this.close())
         .pos(this.width / 2 - 60, this.height / 2 + 90)
         .size(120, 20)
         .build();
      this.addRenderableWidget(btnClose);
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(graphics, mouseX, mouseY, partialTick);
      super.render(graphics, mouseX, mouseY, partialTick);
      this.renderTitle(graphics);
   }

   protected void renderTitle(GuiGraphics graphics) {
      int var10003 = this.width / 2;
      graphics.drawCenteredString(Minecraft.getInstance().font, "Gnetum", var10003, 10, 16777215);
   }
}
