package com.blamejared.crafttweaker.api.zencode.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.openzen.zenscript.lexer.ZSTokenType;

public final class ZenKeywordUtil {
   private static final Set<String> KEYWORDS = Arrays.stream(ZSTokenType.values())
      .filter(zsTokenType -> zsTokenType.isKeyword)
      .map(zsTokenType -> zsTokenType.flyweight.content)
      .collect(Collectors.toSet());

   private ZenKeywordUtil() {
   }

   public static boolean isKeyword(String string) {
      return KEYWORDS.contains(string);
   }

   public static String sanitize(String string) {
      if (isKeyword(string)) {
         string = "\"" + string + "\"";
      }

      return string;
   }
}
