package com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.text_block_entries;

import com.finderfeed.fdlib.data_structures.Pair;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.TextBlockCursor;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.TextBlockEntry;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.TextBlockWidget;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.interactions.InteractionBox;
import com.finderfeed.fdlib.systems.simple_screen.fdwidgets.text_block.interactions.TextBlockEntryInteraction;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

public class SimpleTextEntry implements TextBlockEntry {
   private FormattedText component;
   private float textScale;
   private TextBlockEntryInteraction interaction;
   private Style styleOverride;
   private boolean renderShadow;
   private int textColor;

   public SimpleTextEntry(FormattedText component, float textScale, boolean renderShadow, int textColor, TextBlockEntryInteraction interaction) {
      this.component = component;
      this.textScale = textScale;
      this.renderShadow = renderShadow;
      this.interaction = interaction;
      this.textColor = textColor;
   }

   public SimpleTextEntry(FormattedText component, float textScale, boolean renderShadow, int textColor) {
      this(component, textScale, renderShadow, textColor, TextBlockEntryInteraction.empty());
   }

   public SimpleTextEntry chatFormattingOverride(Style chatFormatting) {
      this.styleOverride = chatFormatting;
      return this;
   }

   @Override
   public void render(GuiGraphics graphics, TextBlockWidget textBlock, TextBlockCursor cursor, float mx, float my, float pticks, boolean last) {
      if (!this.component.getString().isEmpty()) {
         Font font = Minecraft.getInstance().font;
         float lineHeight = 9.0F * this.textScale;
         int remainingWidth = (int)cursor.remainingWidth(textBlock.getBorderX());
         if (remainingWidth > 0) {
            Pair<FormattedText, FormattedText> texts = splitOneTime(this.component, Math.round(remainingWidth / this.textScale));
            FormattedText first = texts.first;
            if (this.styleOverride != null) {
               first = FormattedText.of(first.getString(), this.styleOverride);
            }

            FDRenderUtil.renderScaledText(
               graphics, Language.getInstance().getVisualOrder(first), cursor.x, cursor.y, this.textScale, this.renderShadow, this.textColor
            );
            float width = font.width(first) * this.textScale;
            textBlock.addInteractionBox(new InteractionBox(cursor.x, cursor.y, width, lineHeight, this.interaction));
            cursor.addX(width);
            if (texts.second != null) {
               FormattedText second = texts.second;
               if (this.styleOverride != null) {
                  second = FormattedText.of(second.getString(), this.styleOverride);
               }

               cursor.nextLine(9.0F * this.textScale);
               this.renderNormalText(second, graphics, textBlock, cursor, this.textScale, last);
            } else if (last) {
               cursor.nextLine(lineHeight);
            }
         } else {
            cursor.nextLine(9.0F * this.textScale);
            this.renderNormalText(this.component, graphics, textBlock, cursor, this.textScale, last);
         }
      }
   }

   private void renderNormalText(FormattedText text, GuiGraphics graphics, TextBlockWidget textBlock, TextBlockCursor cursor, float scale, boolean last) {
      Font font = Minecraft.getInstance().font;
      float lineHeight = 9.0F * this.textScale;
      StringSplitter splitter = font.getSplitter();
      List<FormattedText> sequences = splitter.splitLines(text, Math.round(textBlock.getWidth() / scale), Style.EMPTY);
      if (!sequences.isEmpty()) {
         for (FormattedText formattedText : sequences) {
            if (this.styleOverride != null) {
               formattedText = FormattedText.of(formattedText.getString(), this.styleOverride);
            }

            FDRenderUtil.renderScaledText(
               graphics, Language.getInstance().getVisualOrder(formattedText), cursor.x, cursor.y, this.textScale, this.renderShadow, this.textColor
            );
            textBlock.addInteractionBox(new InteractionBox(cursor.x, cursor.y, font.width(formattedText) * this.textScale, lineHeight, this.interaction));
            cursor.nextLine(lineHeight);
         }

         if (!last) {
            cursor.nextLine(-lineHeight);
         }

         cursor.addX(font.width(sequences.getLast()) * scale - 3.0F * scale);
      }
   }

   public static Pair<FormattedText, FormattedText> splitOneTime(FormattedText text, int pixels) {
      StringSplitter splitter = Minecraft.getInstance().font.getSplitter();
      String str = text.getString();
      int lineBreak = splitter.findLineBreak(str, pixels, Style.EMPTY);
      if (lineBreak >= str.length()) {
         return new Pair<>(text, null);
      }

      List<Pair<Style, String>> styleString = new ArrayList<>();
      text.visit((string, stylex) -> {
         styleString.add(new Pair<>(string, stylex));
         return Optional.empty();
      }, Style.EMPTY);
      int currentIndex = 0;
      FormattedText before = FormattedText.EMPTY;
      FormattedText after = FormattedText.EMPTY;
      boolean wasSplit = false;
      boolean shouldDeleteSpace = false;

      for (Pair<Style, String> pair : styleString) {
         Style style = pair.first;
         String s = pair.second;
         int stringLength = s.length();
         if (wasSplit) {
            if (shouldDeleteSpace) {
               if (s.charAt(0) == ' ' || s.charAt(0) == '\n') {
                  s = s.substring(1);
               }

               shouldDeleteSpace = false;
            }

            after = FormattedText.composite(new FormattedText[]{after, FormattedText.of(s, style)});
         } else if (stringLength + currentIndex < lineBreak) {
            currentIndex += stringLength;
            before = FormattedText.composite(new FormattedText[]{before, FormattedText.of(s, style)});
         } else {
            int substrlength = lineBreak - currentIndex;
            String first = s.substring(0, substrlength);
            String second = s.substring(substrlength);
            before = FormattedText.composite(new FormattedText[]{before, FormattedText.of(first, style)});
            if (!second.isEmpty()) {
               if (second.charAt(0) == ' ' || second.charAt(0) == '\n') {
                  second = second.substring(1);
               }

               after = FormattedText.composite(new FormattedText[]{after, FormattedText.of(second, style)});
            } else {
               shouldDeleteSpace = true;
            }

            wasSplit = true;
         }
      }

      return new Pair<>(before, after);
   }
}
