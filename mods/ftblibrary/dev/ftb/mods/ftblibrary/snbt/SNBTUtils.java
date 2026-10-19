package dev.ftb.mods.ftblibrary.snbt;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.function.BooleanSupplier;
import net.minecraft.Util;

public class SNBTUtils {
   public static final BooleanSupplier ALWAYS_TRUE = () -> true;
   public static final char[] ESCAPE_CHARS = (char[])Util.make(new char[128], array -> {
      array[34] = '"';
      array[92] = '\\';
      array[9] = 't';
      array[8] = 'b';
      array[10] = 'n';
      array[13] = 'r';
      array[12] = 'f';
   });
   public static final char[] REVERSE_ESCAPE_CHARS = (char[])Util.make(new char[128], array -> {
      for (int i = 0; i < array.length; i++) {
         if (ESCAPE_CHARS[i] != 0) {
            array[ESCAPE_CHARS[i]] = (char)i;
         }
      }
   });

   public static boolean isSimpleCharacter(char c) {
      return Character.isAlphabetic(c) || Character.isDigit(c) || c == '.' || c == '_' || c == '-' || c == '+' || c == 8734;
   }

   public static boolean isSimpleString(String string) {
      for (int i = 0; i < string.length(); i++) {
         if (!isSimpleCharacter(string.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   public static int getNumberType(String s) {
      if (s.isEmpty()) {
         return 8;
      } else {
         char last = Character.toLowerCase(s.charAt(s.length() - 1));
         if (Character.isDigit(last) && Ints.tryParse(s) != null) {
            return 3;
         } else {
            String start = s.substring(0, s.length() - 1);
            if (last == 'b' && Ints.tryParse(start) != null) {
               return 1;
            } else if (last == 's' && Ints.tryParse(start) != null) {
               return 2;
            } else if (last == 'l' && Longs.tryParse(start) != null) {
               return 4;
            } else if (last == 'f' && Floats.tryParse(start) != null) {
               return 5;
            } else if (last == 'd' && Doubles.tryParse(start) != null) {
               return 6;
            } else {
               return Floats.tryParse(s) != null ? -6 : 8;
            }
         }
      }
   }

   public static String handleEscape(String string) {
      return isSimpleString(string) ? string : quoteAndEscape(string);
   }

   public static String quoteAndEscape(String string) {
      int len = string.length();
      StringBuilder sb = new StringBuilder(len + 2);
      sb.append('"');

      for (int i = 0; i < len; i++) {
         char c = string.charAt(i);
         if (c < ESCAPE_CHARS.length && ESCAPE_CHARS[c] != 0) {
            sb.append('\\');
            sb.append(ESCAPE_CHARS[c]);
         } else {
            sb.append(c);
         }
      }

      sb.append('"');
      return sb.toString();
   }
}
