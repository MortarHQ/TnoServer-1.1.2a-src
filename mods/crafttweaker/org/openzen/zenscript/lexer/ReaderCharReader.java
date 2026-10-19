package org.openzen.zenscript.lexer;

import java.io.IOException;
import java.io.Reader;

public class ReaderCharReader implements CharReader {
   private final Reader reader;
   private int next;

   public ReaderCharReader(Reader reader) throws IOException {
      this.reader = reader;
      this.next = reader.read();
   }

   @Override
   public int peek() {
      return this.next;
   }

   @Override
   public int next() throws IOException {
      int result = this.next;
      this.next = this.reader.read();
      return result;
   }
}
