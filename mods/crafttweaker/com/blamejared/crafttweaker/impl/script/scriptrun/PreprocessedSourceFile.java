package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.impl.preprocessor.LoadFirstPreprocessor;
import com.blamejared.crafttweaker.impl.preprocessor.LoadLastPreprocessor;
import com.blamejared.crafttweaker.impl.preprocessor.PriorityPreprocessor;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.openzen.zencode.shared.SourceFile;

final class PreprocessedSourceFile implements SourceFile {
   private final String fileName;
   private final List<String> contents;
   private final Map<IPreprocessor, List<IPreprocessor.Match>> matches;

   PreprocessedSourceFile(String fileName, List<String> contents, Map<IPreprocessor, List<IPreprocessor.Match>> matches) {
      this.fileName = fileName;
      this.contents = new ArrayList<>(contents);
      this.matches = Map.copyOf(matches);
   }

   @Override
   public String getFilename() {
      return this.fileName;
   }

   @Override
   public Reader open() {
      String contents = this.contents.stream().collect(Collectors.joining(System.lineSeparator(), "", System.lineSeparator()));
      return new StringReader(contents);
   }

   @Override
   public void update(String content) {
      this.contents.clear();
      this.contents.addAll(Arrays.asList(content.split(Pattern.quote(System.lineSeparator()))));
   }

   Map<IPreprocessor, List<IPreprocessor.Match>> matches() {
      return this.matches;
   }

   @Override
   public int getOrder() {
      if (this.matches().containsKey(LoadFirstPreprocessor.INSTANCE)) {
         return Integer.MAX_VALUE;
      } else {
         return this.matches().containsKey(LoadLastPreprocessor.INSTANCE)
            ? Integer.MIN_VALUE
            : Integer.parseInt(this.matches().get(PriorityPreprocessor.INSTANCE).get(0).content());
      }
   }
}
