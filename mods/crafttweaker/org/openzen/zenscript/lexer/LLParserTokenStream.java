package org.openzen.zenscript.lexer;

import java.util.LinkedList;
import java.util.Stack;
import org.openzen.zencode.shared.CodePosition;

public class LLParserTokenStream<TT extends TokenType, T extends Token<TT>> extends WhitespaceFilteringParser<TT, T> {
   private final LinkedList<LLParserTokenStream<TT, T>.PositionedToken> tokenMemory = new LinkedList<>();
   private final Stack<Integer> marks = new Stack<>();
   private int tokenMemoryOffset = 0;
   private int tokenMemoryCurrent = 0;

   public LLParserTokenStream(TokenStream<TT, T> stream) throws ParseException {
      super(stream);
   }

   public void pushMark() {
      this.marks.push(this.tokenMemoryCurrent);
   }

   public void popMark() {
      this.marks.pop();
      if (this.marks.isEmpty()) {
         this.tokenMemoryOffset = this.tokenMemoryCurrent;
         this.tokenMemory.clear();
      }
   }

   public void reset() {
      this.tokenMemoryCurrent = this.marks.pop();
   }

   @Override
   public T peek() {
      return this.tokenMemoryCurrent < this.tokenMemoryOffset + this.tokenMemory.size()
         ? this.tokenMemory.get(this.tokenMemoryCurrent - this.tokenMemoryOffset).token
         : super.peek();
   }

   @Override
   public T next() throws ParseException {
      if (this.tokenMemoryCurrent < this.tokenMemoryOffset + this.tokenMemory.size()) {
         return this.tokenMemory.get(this.tokenMemoryCurrent++ - this.tokenMemoryOffset).token;
      }

      T result = super.next();
      if (this.marks.isEmpty()) {
         this.tokenMemoryOffset++;
      } else {
         this.tokenMemory.add(new LLParserTokenStream.PositionedToken(this.getPosition(), this.getPositionBeforeWhitespace(), result));
      }

      this.tokenMemoryCurrent++;
      return result;
   }

   @Override
   public CodePosition getPosition() {
      return this.tokenMemoryCurrent < this.tokenMemoryOffset + this.tokenMemory.size()
         ? this.tokenMemory.get(this.tokenMemoryCurrent - this.tokenMemoryOffset).position
         : super.getPosition();
   }

   @Override
   public CodePosition getPositionBeforeWhitespace() {
      return this.tokenMemoryCurrent < this.tokenMemoryOffset + this.tokenMemory.size()
         ? this.tokenMemory.get(this.tokenMemoryCurrent - this.tokenMemoryOffset).positionBeforeWhitespace
         : super.getPositionBeforeWhitespace();
   }

   public boolean isNext(TT type) {
      return this.peek().getType() == type;
   }

   public T optional(TT type) throws ParseException {
      return this.peek().getType() == type ? this.next() : null;
   }

   public T required(TT type, String error) throws ParseException {
      T t = this.peek();
      if (t.getType() == type) {
         return this.next();
      } else {
         throw new ParseException(this.getPosition().withLength(t.getContent().length()), error);
      }
   }

   public boolean hasNext() {
      return this.peek().getType() != this.getEOF();
   }

   public void recoverUntilTokenOrNewline(ZSTokenType type) throws ParseException {
      CodePosition last = this.getPosition();

      while (this.peek().getType() != type && this.getPosition().fromLine > last.fromLine) {
         this.next();
      }
   }

   public void recoverUntilBeforeToken(ZSTokenType type) throws ParseException {
      while (this.peek().getType() != type && this.peek().getType() != this.getEOF()) {
         this.next();
      }
   }

   public void recoverUntilOnToken(ZSTokenType type) throws ParseException {
      this.recoverUntilBeforeToken(type);
      this.next();
   }

   private class PositionedToken {
      public final CodePosition position;
      public final CodePosition positionBeforeWhitespace;
      public final T token;

      public PositionedToken(CodePosition position, CodePosition positionBeforeWhitespace, T token) {
         this.position = position;
         this.positionBeforeWhitespace = positionBeforeWhitespace;
         this.token = token;
      }
   }
}
