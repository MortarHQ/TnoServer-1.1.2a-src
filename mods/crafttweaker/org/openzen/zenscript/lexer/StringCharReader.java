package org.openzen.zenscript.lexer;

import java.io.IOException;

public class StringCharReader implements CharReader {
   private final char[] data;
   private int index;

   public StringCharReader(String data) {
      this.data = data.toCharArray();
      this.index = 0;
   }

   @Override
   public int peek() {
      return this.index >= this.data.length ? -1 : this.data[this.index];
   }

   @Override
   public int next() throws IOException {
      return this.index >= this.data.length ? -1 : this.data[this.index++];
   }
}
