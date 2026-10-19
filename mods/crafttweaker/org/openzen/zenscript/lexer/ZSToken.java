package org.openzen.zenscript.lexer;

public class ZSToken implements Token<ZSTokenType> {
   public final ZSTokenType type;
   public final String content;

   public ZSToken(ZSTokenType type, String content) {
      if (content.isEmpty() && type != ZSTokenType.EOF) {
         throw new IllegalArgumentException("Token must not be empty!");
      }

      this.type = type;
      this.content = content;
   }

   public ZSTokenType getType() {
      return this.type;
   }

   @Override
   public String getContent() {
      return this.content;
   }

   @Override
   public String toString() {
      return this.type + ":" + this.content;
   }

   public ZSToken delete(int offset, int characters) {
      return new ZSToken(ZSTokenType.INVALID, this.content.substring(0, offset) + this.content.substring(offset + characters));
   }

   public ZSToken insert(int offset, String value) {
      return new ZSToken(ZSTokenType.INVALID, this.content.substring(0, offset) + value + this.content.substring(offset));
   }
}
