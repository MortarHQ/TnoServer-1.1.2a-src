package fzzyhmstrs.emi_loot.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public record TrimmedTitle(boolean trimmed, FormattedCharSequence title, Component rawTitle) {
   public static TrimmedTitle of(Component text, int width) {
      FormattedCharSequence title;
      boolean trimmed;
      if (Minecraft.getInstance().font.width(text) > width) {
         title = LText.trim(text, width);
         trimmed = true;
      } else {
         title = text.getVisualOrderText();
         trimmed = false;
      }

      return new TrimmedTitle(trimmed, title, text);
   }
}
