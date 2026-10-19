package org.openzen.zenscript.lexer;

import java.io.IOException;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.SourceFile;

public class TokenParser<T extends Token<TT>, TT extends TokenType> implements TokenStream<TT, T> {
   private final CountingCharReader reader;
   private final CompiledDFA<TT> dfa;
   private final TT eof;
   private final TT invalid;
   private final TokenFactory<T, TT> factory;

   public TokenParser(SourceFile file, CharReader reader, CompiledDFA<TT> dfa, TT eof, TT invalid, TokenFactory<T, TT> factory) {
      if (eof.isWhitespace()) {
         throw new IllegalArgumentException("EOF cannot be whitespace");
      }

      this.reader = new CountingCharReader(reader, file);
      this.dfa = dfa;
      this.eof = eof;
      this.invalid = invalid;
      this.factory = factory;
   }

   public TokenParser(SourceFile file, String data, CompiledDFA<TT> dfa, TT eof, TT invalid, TokenFactory<T, TT> factory) {
      this(file, new StringCharReader(data), dfa, eof, invalid, factory);
   }

   @Override
   public CodePosition getPosition() {
      return this.reader.getPosition();
   }

   public boolean hasNext() {
      try {
         return this.reader.peek() >= 0;
      } catch (IOException ex) {
         return false;
      }
   }

   @Override
   public TT getEOF() {
      return this.eof;
   }

   @Override
   public T next() throws ParseException {
      try {
         if (this.reader.peek() < 0) {
            return this.factory.create(this.eof, "");
         }

         int state = 0;
         StringBuilder value = new StringBuilder();

         while (this.dfa.transitions[state].containsKey(Math.min(this.reader.peek(), 128))) {
            int c = this.reader.next();
            value.append((char)c);
            state = this.dfa.transitions[state].get(Math.min(c, 128));
         }

         if (((TokenType[])this.dfa.finals)[state] != null) {
            if (state == 0) {
               value.append((char)this.reader.next());
               return this.factory.create(this.invalid, value.toString());
            } else {
               return this.factory.create(this.dfa.finals[state], value.toString());
            }
         } else {
            if (this.reader.peek() < 0 && value.length() == 0) {
               return this.factory.create(this.eof, "");
            }

            if (value.length() == 0) {
               value.append((char)this.reader.next());
            }

            return this.factory.create(this.invalid, value.toString());
         }
      } catch (IOException ex) {
         throw new ParseException(this.getPosition(), "I/O exception: " + ex.getMessage());
      }
   }
}
