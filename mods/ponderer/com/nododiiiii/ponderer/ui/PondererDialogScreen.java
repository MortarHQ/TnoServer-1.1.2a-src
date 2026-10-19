package com.nododiiiii.ponderer.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.createmod.catnip.gui.AbstractSimiScreen;
import net.createmod.catnip.gui.UIRenderHelper;
import net.createmod.catnip.gui.element.BoxElement;
import net.createmod.catnip.gui.element.FadableScreenElement;
import net.createmod.catnip.gui.element.TextStencilElement;
import net.createmod.catnip.gui.widget.AbstractSimiWidget;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.createmod.catnip.platform.CatnipClientServices;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

public class PondererDialogScreen extends AbstractSimiScreen {
   private static final int BUTTON_HEIGHT = 16;
   private static final int BUTTON_GAP = 12;
   private static final int MAX_TEXT_WIDTH = 300;
   private static final int MIN_BUTTON_WIDTH = 70;
   private final Screen source;
   private final List<Component> titleLines;
   private final List<Component> bodyLines;
   private final List<PondererDialogScreen.DialogButton> buttons;
   private final List<PondererDialogScreen.DialogTextLine> text = new ArrayList<>();
   private int x;
   private int y;
   private int textWidth;
   private int textHeight;
   private BoxElement textBackground;

   public PondererDialogScreen(@Nonnull Screen source, List<Component> titleLines, List<Component> bodyLines, List<PondererDialogScreen.DialogButton> buttons) {
      this.source = source;
      this.titleLines = List.copyOf(titleLines);
      this.bodyLines = List.copyOf(bodyLines);
      this.buttons = List.copyOf(buttons);
   }

   public static PondererDialogScreen.DialogButton button(Component label, Consumer<PondererDialogScreen> action) {
      return new PondererDialogScreen.DialogButton(label, action);
   }

   public static PondererDialogScreen.DialogButton closeButton(Component label) {
      return button(label, PondererDialogScreen::closeToSource);
   }

   public void open() {
      Minecraft client = CatnipClientServices.CLIENT_HOOKS.getMinecraftFromScreen(this.source);
      this.init(client, client.getWindow().getGuiScaledWidth(), client.getWindow().getGuiScaledHeight());
      this.minecraft.screen = this;
   }

   public Screen source() {
      return this.source;
   }

   public void closeToSource() {
      Minecraft.getInstance().screen = this.source;
   }

   protected void init() {
      super.init();
      this.text.clear();

      for (Component line : this.titleLines) {
         this.addWrappedText(line.copy().withStyle(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.BOLD}), true);
      }

      for (Component line : this.bodyLines) {
         this.addWrappedText(line, false);
      }

      this.textHeight = this.text.size() * (9 + 1) + 4;
      this.textWidth = 300;
      this.x = this.width / 2 - this.textWidth / 2 - 2;
      this.y = this.height / 2 - this.textHeight / 2 - 16;
      if (this.x + this.textWidth > this.width) {
         this.x = this.width - this.textWidth;
      }

      if (this.y + this.textHeight + 30 > this.height) {
         this.y = this.height - this.textHeight - 30;
      }

      int totalButtonWidth = 0;
      List<Integer> buttonWidths = new ArrayList<>();

      for (PondererDialogScreen.DialogButton button : this.buttons) {
         int buttonWidth = Math.max(70, this.font.width(button.label()) + 12);
         buttonWidths.add(buttonWidth);
         totalButtonWidth += buttonWidth;
      }

      if (!this.buttons.isEmpty()) {
         totalButtonWidth += Math.max(0, this.buttons.size() - 1) * 12;
      }

      int buttonX = this.x + this.textWidth / 2 - totalButtonWidth / 2;
      int buttonY = this.y + this.textHeight + 6;

      for (int i = 0; i < this.buttons.size(); i++) {
         int buttonWidth = buttonWidths.get(i);
         PondererDialogScreen.DialogButton spec = this.buttons.get(i);
         BoxWidget widget = (BoxWidget)new BoxWidget(buttonX, buttonY, buttonWidth, 16).withCallback(() -> spec.action().accept(this));
         TextStencilElement textElement = new TextStencilElement(this.font, spec.label().copy()).centered(true, true);
         widget.showingElement(textElement.withElementRenderer((FadableScreenElement)BoxWidget.gradientFactory.apply(widget)));
         this.addRenderableWidget(widget);
         buttonX += buttonWidth + 12;
      }

      this.textBackground = (BoxElement)new BoxElement()
         .withBackground(BoxElement.COLOR_BACKGROUND_FLAT)
         .gradientBorder(AbstractSimiWidget.COLOR_DISABLED)
         .withBounds(this.width + 10, this.textHeight + 35)
         .at(-5.0F, this.y - 5);
      if (this.text.size() == 1) {
         this.x = (this.width - this.font.width(this.text.get(0).text())) / 2;
      }
   }

   private void addWrappedText(Component line, boolean title) {
      for (FormattedText wrapped : this.font.getSplitter().splitLines(line, 300, Style.EMPTY)) {
         this.text.add(new PondererDialogScreen.DialogTextLine(wrapped, title));
      }
   }

   public void tick() {
      super.tick();
      this.source.tick();
   }

   public void onClose() {
      this.closeToSource();
   }

   protected void renderWindowBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.endFrame();
      this.source.render(graphics, 0, 0, 10.0F);
      this.prepareFrame();
      graphics.fillGradient(0, 0, this.width, this.height, 1880100880, -2146430960);
   }

   protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.textBackground.render(graphics);
      int offset = 9 + 1;
      int lineY = this.y - offset;
      PoseStack poseStack = graphics.pose();
      poseStack.pushPose();
      poseStack.translate(0.0F, 0.0F, 200.0F);

      for (PondererDialogScreen.DialogTextLine line : this.text) {
         lineY += offset;
         if (line.text() != null) {
            FormattedCharSequence orderedLine = Language.getInstance().getVisualOrder(line.text());
            graphics.drawString(this.font, orderedLine, this.x, lineY, line.title() ? 16777045 : 15395562, false);
         }
      }

      poseStack.popPose();
   }

   protected void prepareFrame() {
      UIRenderHelper.swapAndBlitColor(this.minecraft.getMainRenderTarget(), UIRenderHelper.framebuffer);
      RenderSystem.clear(1280, Minecraft.ON_OSX);
   }

   protected void endFrame() {
      UIRenderHelper.swapAndBlitColor(UIRenderHelper.framebuffer, this.minecraft.getMainRenderTarget());
   }

   public void resize(@Nonnull Minecraft client, int width, int height) {
      super.resize(client, width, height);
      this.source.resize(client, width, height);
   }

   public boolean isPauseScreen() {
      return true;
   }

   public record DialogButton(Component label, Consumer<PondererDialogScreen> action) {
   }

   private record DialogTextLine(FormattedText text, boolean title) {
   }
}
