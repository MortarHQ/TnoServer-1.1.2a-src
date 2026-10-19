package org.crypticdev.stextras.utils;

import java.util.Locale;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public class TextUtil {
   public static final Map<Character, Character> SMALL_CAPS_MAP = Map.ofEntries(
      Map.entry('a', 'ᴀ'),
      Map.entry('b', 'ʙ'),
      Map.entry('c', 'ᴄ'),
      Map.entry('d', 'ᴅ'),
      Map.entry('e', 'ᴇ'),
      Map.entry('f', 'ꜰ'),
      Map.entry('g', 'ɢ'),
      Map.entry('h', 'ʜ'),
      Map.entry('i', 'ɪ'),
      Map.entry('j', 'ᴊ'),
      Map.entry('k', 'ᴋ'),
      Map.entry('l', 'ʟ'),
      Map.entry('m', 'ᴍ'),
      Map.entry('n', 'ɴ'),
      Map.entry('o', 'ᴏ'),
      Map.entry('p', 'ᴘ'),
      Map.entry('q', 'ǫ'),
      Map.entry('r', 'ʀ'),
      Map.entry('s', 'ѕ'),
      Map.entry('t', 'ᴛ'),
      Map.entry('u', 'ᴜ'),
      Map.entry('v', 'ᴠ'),
      Map.entry('w', 'ᴡ'),
      Map.entry('x', 'x'),
      Map.entry('y', 'ʏ'),
      Map.entry('z', 'ᴢ')
   );
   private static final String[] NUMBER_SUFFIX = new String[]{"", "K", "M", "B", "T"};

   public static String toSmallCaps(String input) {
      StringBuilder result = new StringBuilder();
      boolean skipNext = false;

      for (char c : input.toCharArray()) {
         if ((c == 167 || c == '&') && !skipNext) {
            skipNext = true;
            result.append(c);
         } else if (skipNext) {
            skipNext = false;
            result.append(c);
         } else {
            result.append(SMALL_CAPS_MAP.getOrDefault(Character.toLowerCase(c), c));
         }
      }

      return result.toString();
   }

   public static String formatedResourceName(ResourceLocation name) {
      String[] words = name.getPath().split("_");
      StringBuilder formatted = new StringBuilder();

      for (String word : words) {
         if (!word.isEmpty()) {
            formatted.append(Character.toUpperCase(word.charAt(0)));
            if (word.length() > 1) {
               formatted.append(word.substring(1));
            }

            formatted.append(" ");
         }
      }

      return formatted.toString().trim();
   }

   public static String formatNumber(long value) {
      if (value < 1000L) {
         return String.valueOf(value);
      }

      int index = 0;

      double scaled;
      for (scaled = value; scaled >= 1000.0 && index < NUMBER_SUFFIX.length - 1; index++) {
         scaled /= 1000.0;
      }

      return scaled >= 10.0
         ? String.format(Locale.ROOT, "%.0f%s", scaled, NUMBER_SUFFIX[index])
         : String.format(Locale.ROOT, "%.1f%s", scaled, NUMBER_SUFFIX[index]);
   }
}
