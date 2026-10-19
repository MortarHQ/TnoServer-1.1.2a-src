package com.blamejared.crafttweaker.impl.preprocessor.onlyif;

import java.io.IOException;
import java.io.Reader;
import org.openzen.zencode.shared.SourceFile;

final class FakeSourceFile implements SourceFile {
   private final String fileName;

   FakeSourceFile(String fileName) {
      this.fileName = fileName;
   }

   @Override
   public String getFilename() {
      return this.fileName;
   }

   @Override
   public Reader open() throws IOException {
      throw new IOException();
   }

   @Override
   public void update(String content) throws IOException {
      throw new IOException();
   }
}
