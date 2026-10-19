package com.nododiiiii.ponderer.ui.catnip;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.gui.element.FadableScreenElement;
import net.createmod.catnip.gui.element.TextStencilElement;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class LocalizedTextListEntry extends PlainTextListEntry {
   private static final int LANG_BUTTON_WIDTH = 28;
   private final Supplier<String> langGetter;
   private final BoxWidget langButton;
   private final TextStencilElement langText;

   public LocalizedTextListEntry(
      String labelKey,
      @Nullable String tooltipKey,
      @Nullable String hintKey,
      String initialValue,
      Supplier<String> langGetter,
      Runnable onToggle,
      Consumer<String> responder
   ) {
      super(labelKey, tooltipKey, hintKey, initialValue, responder);
      this.langGetter = langGetter;
      this.langText = new TextStencilElement(Minecraft.getInstance().font, "en_us").centered(true, true);
      this.langButton = (BoxWidget)new BoxWidget(0, 0, 28, 16).showingElement(this.langText).withCallback(onToggle);
      this.langText.withElementRenderer((FadableScreenElement)BoxWidget.gradientFactory.apply(this.langButton));
      this.listeners.add(this.langButton);
   }

   @Override
   public void tick() {
      super.tick();
      this.langButton.tick();
   }

   public BoxWidget langButton() {
      return this.langButton;
   }

   @Override
   protected int getTrailingWidth() {
      return 28 + CONTROL_GAP;
   }

   @Override
   protected void renderTrailing(GuiGraphics graphics, int y, int x, int width, int height, int mouseX, int mouseY, float partialTicks) {
      this.langText.withText(shortLang(this.langGetter.get()));
      this.langButton.setX(x + width - 4 - 28);
      this.langButton.setY(y + 10);
      this.langButton.setWidth(28);
      this.langButton.setHeight(height - 20);
      this.langButton.render(graphics, mouseX, mouseY, partialTicks);
   }

   private static String shortLang(String lang) {
      if (lang != null && !lang.isBlank()) {
         return lang.length() > 5 ? lang.substring(0, 5) : lang;
      } else {
         return "en_us";
      }
   }
}
