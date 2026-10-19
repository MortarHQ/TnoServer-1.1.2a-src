package com.tensura_guild.client.screen;

import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class GuildOtherSettingsScreen extends Screen {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/other_panel.png");
   private static final int TEXTURE_SIZE = 256;
   private static final int IMAGE_WIDTH = 138;
   private static final int IMAGE_HEIGHT = 175;
   private static final GuildOtherSettingsScreen.Rect BACK_BUTTON = new GuildOtherSettingsScreen.Rect(10, 11, 28, 21);
   private static final GuildOtherSettingsScreen.Rect BACK_BUTTON_HOVER_SRC = new GuildOtherSettingsScreen.Rect(2, 177, 20, 187);
   private static final GuildOtherSettingsScreen.Rect TEXT_AREA = new GuildOtherSettingsScreen.Rect(16, 29, 122, 159);
   private static final int TEXT_COLOR = -13950696;
   private final Screen parent;
   private final List<Component> content;
   private int leftPos;
   private int topPos;

   public GuildOtherSettingsScreen() {
      this(null, List.of());
   }

   public GuildOtherSettingsScreen(Screen parent) {
      this(parent, List.of());
   }

   public GuildOtherSettingsScreen(Screen parent, Component content) {
      this(parent, content == null ? List.of() : List.of(content));
   }

   public GuildOtherSettingsScreen(Screen parent, List<Component> content) {
      super(Component.empty());
      this.parent = parent;
      this.content = content == null ? List.of() : List.copyOf(content);
   }

   protected void init() {
      this.leftPos = (this.width - 138) / 2;
      this.topPos = (this.height - 175) / 2;
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0.0F, 0.0F, 138, 175, 256, 256);
      this.renderBackButton(graphics, mouseX, mouseY);
      this.renderContent(graphics);
      super.render(graphics, mouseX, mouseY, partialTick);
   }

   private void renderBackButton(GuiGraphics graphics, int mouseX, int mouseY) {
      if (this.isMouseOver(mouseX, mouseY, BACK_BUTTON)) {
         graphics.blit(
            TEXTURE,
            this.leftPos + BACK_BUTTON.left(),
            this.topPos + BACK_BUTTON.top(),
            BACK_BUTTON.width(),
            BACK_BUTTON.height(),
            BACK_BUTTON_HOVER_SRC.left(),
            BACK_BUTTON_HOVER_SRC.top(),
            BACK_BUTTON_HOVER_SRC.width(),
            BACK_BUTTON_HOVER_SRC.height(),
            256,
            256
         );
      }
   }

   private void renderContent(GuiGraphics graphics) {
      if (!this.content.isEmpty()) {
         int x = this.leftPos + TEXT_AREA.left();
         int y = this.topPos + TEXT_AREA.top();
         int right = this.leftPos + TEXT_AREA.right() + 1;
         int bottom = this.topPos + TEXT_AREA.bottom() + 1;
         graphics.enableScissor(x, y, right, bottom);

         try {
            int drawY = y;

            for (Component paragraph : this.content) {
               for (FormattedCharSequence line : this.font.split(paragraph, TEXT_AREA.width())) {
                  if (drawY + 9 > bottom) {
                     return;
                  }

                  graphics.drawString(this.font, line, x, drawY, -13950696, false);
                  drawY += 9 + 1;
               }

               drawY += 2;
            }
         } finally {
            graphics.disableScissor();
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.isMouseOver(mouseX, mouseY, BACK_BUTTON)) {
         this.returnToParent();
         ScreenHelper.clicked();
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public void onClose() {
      this.returnToParent();
   }

   private void returnToParent() {
      if (this.minecraft != null) {
         this.minecraft.setScreen(this.parent);
      }
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
   }

   protected void renderBlurredBackground(float partialTick) {
   }

   public boolean isPauseScreen() {
      return false;
   }

   private boolean isMouseOver(double mouseX, double mouseY, GuildOtherSettingsScreen.Rect rect) {
      return mouseX >= this.leftPos + rect.left()
         && mouseX <= this.leftPos + rect.right()
         && mouseY >= this.topPos + rect.top()
         && mouseY <= this.topPos + rect.bottom();
   }

   private record Rect(int left, int top, int right, int bottom) {
      int width() {
         return this.right - this.left + 1;
      }

      int height() {
         return this.bottom - this.top + 1;
      }
   }
}
