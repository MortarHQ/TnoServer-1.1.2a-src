package com.nododiiiii.ponderer.ui.catnip;

import com.nododiiiii.ponderer.ui.UIText;
import java.util.Locale;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.HintableTextFieldWidget;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.catnip.lang.FontHelper.Palette;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public final class EntryTextSupport {
   private static final int RIGHT_CONTROL_GAP = 8;
   private static final float HALF_WIDTH_CONTROL_SCALE = 0.5F;

   private EntryTextSupport() {
   }

   public static String createSearchText(String labelKey, @Nullable String tooltipKey) {
      StringBuilder builder = new StringBuilder(UIText.of(labelKey));
      if (tooltipKey != null && !tooltipKey.isBlank()) {
         builder.append(' ').append(UIText.of(tooltipKey));
      }

      return builder.toString().toLowerCase(Locale.ROOT);
   }

   public static void applyTooltip(LabeledEntry entry, String labelKey, @Nullable String tooltipKey) {
      entry.getLabelTooltip().clear();
      entry.getLabelTooltip().add(Component.literal(UIText.of(labelKey)).withStyle(ChatFormatting.WHITE));
      if (tooltipKey != null && !tooltipKey.isBlank()) {
         entry.getLabelTooltip().addAll(FontHelper.cutTextComponent(Component.literal(UIText.of(tooltipKey)), Palette.ALL_GRAY));
      }
   }

   public static void applyHint(@Nullable EditBox field, @Nullable String hintKey) {
      if (field instanceof HintableTextFieldWidget hintableField && hintKey != null && !hintKey.isBlank()) {
         hintableField.setHint(UIText.of(hintKey));
      }
   }

   public static int compactLabelWidth(int totalWidth) {
      return (int)(totalWidth * 0.3F) + 14;
   }

   public static int controlAreaWidth(int totalWidth, int labelWidth) {
      return Math.max(60, totalWidth - labelWidth - 8);
   }

   public static int scaledControlWidth(int totalWidth, int labelWidth, float controlWidthScale, int minimumWidth) {
      int fullControlWidth = controlAreaWidth(totalWidth, labelWidth);
      int scaledWidth = Math.max(minimumWidth, Math.round(fullControlWidth * controlWidthScale));
      return Math.min(fullControlWidth, scaledWidth);
   }

   public static EntryTextSupport.AlignedControlBounds rightAlignedControlBounds(
      int x, int totalWidth, int labelWidth, float controlWidthScale, int minimumWidth
   ) {
      int renderedControlWidth = scaledControlWidth(totalWidth, labelWidth, controlWidthScale, minimumWidth);
      return new EntryTextSupport.AlignedControlBounds(rightAlignedControlX(x, totalWidth, renderedControlWidth), renderedControlWidth);
   }

   public static int rightAlignedControlX(int x, int totalWidth, int renderedControlWidth) {
      return x + totalWidth - 4 - renderedControlWidth;
   }

   public static float halfWidthControlScale() {
      return 0.5F;
   }

   public static int rightControlGap() {
      return 8;
   }

   public record AlignedControlBounds(int x, int width) {
      public int right() {
         return this.x + this.width;
      }

      public int rightAlignedContentX(int contentWidth) {
         return this.right() - contentWidth;
      }
   }
}
