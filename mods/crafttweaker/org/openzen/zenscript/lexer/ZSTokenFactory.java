package org.openzen.zenscript.lexer;

import java.util.HashMap;
import java.util.Map;

public class ZSTokenFactory implements TokenFactory<ZSToken, ZSTokenType> {
   private static final Map<String, ZSToken> KEYWORDS = new HashMap<>();

   public ZSToken create(ZSTokenType type, String content) {
      if (type == ZSTokenType.T_IDENTIFIER && KEYWORDS.containsKey(content)) {
         return KEYWORDS.get(content);
      } else {
         return type.flyweight != null ? type.flyweight : new ZSToken(type, content);
      }
   }

   static {
      for (ZSTokenType type : ZSTokenType.values()) {
         if (type.isKeyword) {
            KEYWORDS.put(type.flyweight.content, type.flyweight);
         }
      }
   }
}
