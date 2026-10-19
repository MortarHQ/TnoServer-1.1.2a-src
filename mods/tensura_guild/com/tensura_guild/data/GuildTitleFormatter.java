package com.tensura_guild.data;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public final class GuildTitleFormatter {
   private GuildTitleFormatter() {
   }

   public static MutableComponent format(String title) {
      return format(title, Style.EMPTY);
   }

   public static MutableComponent format(String title, Style baseStyle) {
      MutableComponent result = Component.empty();
      if (title != null && !title.isBlank()) {
         Style currentStyle = baseStyle == null ? Style.EMPTY : baseStyle;
         StringBuilder segment = new StringBuilder();

         for (int i = 0; i < title.length(); i++) {
            char current = title.charAt(i);
            if ((current == '&' || current == 167) && i + 1 < title.length()) {
               ChatFormatting formatting = ChatFormatting.getByCode(title.charAt(i + 1));
               if (formatting != null) {
                  appendSegment(result, segment, currentStyle);
                  currentStyle = applyFormatting(currentStyle, formatting);
                  i++;
                  continue;
               }
            }

            segment.append(current);
         }

         appendSegment(result, segment, currentStyle);
         return result;
      } else {
         return result;
      }
   }

   private static Style applyFormatting(Style currentStyle, ChatFormatting formatting) {
      if (formatting == ChatFormatting.RESET) {
         return Style.EMPTY;
      } else {
         return formatting.isColor() ? Style.EMPTY.applyFormat(formatting) : currentStyle.applyFormat(formatting);
      }
   }

   private static void appendSegment(MutableComponent result, StringBuilder segment, Style style) {
      if (!segment.isEmpty()) {
         result.append(Component.literal(segment.toString()).setStyle(style));
         segment.setLength(0);
      }
   }
}
