package org.openzen.zenscript.lexer;

import org.openzen.zencode.shared.CodePosition;

public class WhitespaceFilteringParser<TT extends TokenType, T extends Token<TT>> implements TokenStream<TT, T> {
   private final TokenStream<TT, T> stream;
   private T next;
   private CodePosition position;
   private CodePosition positionBeforeWhitespace;
   private String whitespace;

   public WhitespaceFilteringParser(TokenStream<TT, T> stream) throws ParseException {
      this.stream = stream;
      this.advance();
   }

   public String getLastWhitespace() {
      return this.whitespace;
   }

   public void skipWhitespaceNewline() {
      int index = this.whitespace.indexOf(10);
      if (index >= 0) {
         this.whitespace = this.whitespace.substring(index + 1);
      }
   }

   public String grabWhitespaceLine() {
      if (this.whitespace.contains("\n")) {
         int index = this.whitespace.indexOf(10);
         String result = this.whitespace.substring(0, index);
         this.whitespace = this.whitespace.substring(index + 1);
         return result;
      } else {
         String result = this.whitespace;
         this.whitespace = "";
         return result;
      }
   }

   public T peek() {
      return this.next;
   }

   public void replace(T other) {
      this.next = other;
   }

   @Override
   public T next() throws ParseException {
      T result = this.next;
      this.advance();
      return result;
   }

   @Override
   public CodePosition getPosition() {
      return this.position;
   }

   public CodePosition getPositionBeforeWhitespace() {
      return this.positionBeforeWhitespace;
   }

   @Override
   public TT getEOF() {
      return this.stream.getEOF();
   }

   private void advance() throws ParseException {
      this.whitespace = "";
      this.position = this.stream.getPosition();
      this.positionBeforeWhitespace = this.position;

      for (this.next = this.stream.next(); this.next.getType().isWhitespace(); this.next = this.stream.next()) {
         this.whitespace = this.whitespace + this.next.getContent();
         this.position = this.stream.getPosition();
      }
   }
}
