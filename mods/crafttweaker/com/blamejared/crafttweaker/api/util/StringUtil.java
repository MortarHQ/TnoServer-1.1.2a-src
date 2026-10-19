package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import java.util.regex.Pattern;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("string")
public class StringUtil {
   @ZenCodeType.Method
   public static boolean matchesRegex(String string, String regex) {
      return Pattern.compile(regex).matcher(string).matches();
   }

   public static String quoteAndEscape(ResourceLocation location) {
      return quoteAndEscape(location.toString());
   }

   @ZenCodeType.Method
   public static String quoteAndEscape(String str) {
      return wrap(str, "\"", true);
   }

   @ZenCodeType.Method
   public static String wrap(String str, String with, @ZenCodeType.OptionalBoolean boolean escape) {
      StringBuilder stringbuilder = new StringBuilder(with);

      for (int i = 0; i < str.length(); i++) {
         char c = str.charAt(i);
         if (escape && (c == '\\' || c == '"')) {
            stringbuilder.append('\\');
         }

         stringbuilder.append(c);
      }

      return stringbuilder.append(with).toString();
   }
}
