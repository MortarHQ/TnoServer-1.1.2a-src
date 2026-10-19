package org.openzen.zenscript.lexer;

import java.io.IOException;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.SourceFile;

public class CountingCharReader implements CharReader {
   private final CharReader reader;
   private final SourceFile file;
   private int line;
   private int lineOffset;

   public CountingCharReader(CharReader reader, SourceFile file) {
      this.reader = reader;
      this.file = file;
      this.line = 1;
      this.lineOffset = 0;
   }

   public CodePosition getPosition() {
      return new CodePosition(this.file, this.line, this.lineOffset, this.line, this.lineOffset);
   }

   @Override
   public int peek() throws IOException {
      return this.reader.peek();
   }

   @Override
   public int next() throws IOException {
      int ch = this.reader.next();
      if (ch == -1) {
         return ch;
      }

      if (ch == 10) {
         this.line++;
         this.lineOffset = 0;
      } else {
         this.lineOffset++;
      }

      return ch;
   }
}
