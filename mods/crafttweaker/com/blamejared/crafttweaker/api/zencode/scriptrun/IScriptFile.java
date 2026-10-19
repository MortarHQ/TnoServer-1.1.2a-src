package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import java.util.List;
import java.util.Optional;
import org.openzen.zencode.shared.SourceFile;

public interface IScriptFile {
   String name();

   List<String> fileContents();

   List<String> preprocessedContents();

   Optional<SourceFile> toSourceFile();

   List<IPreprocessor.Match> matchesFor(IPreprocessor var1);

   default boolean hasMatchesFor(IPreprocessor preprocessor) {
      return !this.matchesFor(preprocessor).isEmpty();
   }
}
