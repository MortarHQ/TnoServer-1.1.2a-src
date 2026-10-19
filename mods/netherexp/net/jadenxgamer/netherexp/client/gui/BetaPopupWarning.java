package net.jadenxgamer.netherexp.client.gui;

import java.awt.Color;
import java.util.List;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class BetaPopupWarning extends Screen {
   private static final ResourceLocation POPUP_TEXTURE = NetherExp.netherexpPath("textures/gui/popup.png");
   private static final int POPUP_WIDTH = 227;
   private static final int POPUP_HEIGHT = 185;
   private static final int TEXT_AREA_X_OFFSET = 10;
   private static final int TEXT_AREA_Y_OFFSET = 44;
   private static final int TEXT_AREA_WIDTH = 208;
   private static final int TEXT_AREA_HEIGHT = 120;
   private static final int LINE_HEIGHT = 9;

   public BetaPopupWarning() {
      super(Component.empty());
   }

   protected void init() {
      super.init();
      int popupX = (this.width - 227) / 2;
      int popupY = (this.height - 185) / 2;
      Button closeButton = Button.builder(Component.literal("I Understand"), button -> this.onClose()).bounds(popupX + 10, popupY + 156, 100, 20).build();
      Button dontShowAgainButton = Button.builder(Component.literal("Don't show again"), button -> {
         this.onClose();
         JNEConfigs.SHOW_BETA_WARNING_POPUP.set(false);
         JNEConfigImpl.COMMON.save();
      }).bounds(popupX + 116, popupY + 156, 100, 20).build();
      this.addRenderableWidget(closeButton);
      this.addRenderableWidget(dontShowAgainButton);
   }

   public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
   }

   public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
      this.renderTransparentBackground(gui);
      int popupX = (this.width - 227) / 2;
      int popupY = (this.height - 185) / 2;
      gui.blit(POPUP_TEXTURE, popupX, popupY, 0.0F, 0.0F, 227, 185, 227, 185);
      int textStartX = popupX + 10;
      int textStartY = popupY + 44;
      this.renderWrappedText(gui, textStartX, textStartY);
      super.render(gui, mouseX, mouseY, partialTick);
   }

   private void renderWrappedText(GuiGraphics gui, int x, int y) {
      Minecraft minecraft = Minecraft.getInstance();
      Font font = minecraft.font;
      List<Component> textLines = List.of(
         Component.literal("WARNING!").withStyle(ChatFormatting.BOLD),
         Component.literal(" "),
         Component.literal("This is just a BETA build of JNE 1.21.1; it is not in a playable state yet,"),
         Component.literal("as such expect many bugs, missing features and general unstableness."),
         Component.literal(" "),
         Component.literal(
               "It is recommended that you do not use this current build in worlds that you care about as there is a high chance things will inevitably "
            )
            .append(Component.literal("BREAK!").withStyle(ChatFormatting.BOLD))
      );
      int currentY = y;

      for (Component line : textLines) {
         for (FormattedCharSequence wrappedLine : font.split(line, 208)) {
            if (currentY + 9 > y + 120) {
               return;
            }

            int lineWidth = font.width(wrappedLine);
            int centeredX = x + (208 - lineWidth) / 2;
            gui.drawString(font, wrappedLine, centeredX, currentY, 16777215, false);
            currentY += 9;
         }
      }
   }

   public void renderTransparentBackground(GuiGraphics gui) {
      Color color = new Color(10693401);
      gui.fillGradient(0, 0, this.width, this.height, -1073741824, color.getRGB());
   }
}
