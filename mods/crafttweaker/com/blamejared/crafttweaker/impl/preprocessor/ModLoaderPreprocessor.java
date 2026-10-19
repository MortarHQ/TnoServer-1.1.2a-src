package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.blamejared.crafttweaker.platform.Services;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

@ZenRegister
@Preprocessor
public final class ModLoaderPreprocessor implements IPreprocessor {
   private static final String SPACE = Pattern.quote(" ");

   @Override
   public String name() {
      return "modloader";
   }

   @Nullable
   @Override
   public String defaultValue() {
      return null;
   }

   @Override
   public boolean apply(IScriptFile file, List<String> preprocessedContents, IMutableScriptRunInfo runInfo, List<IPreprocessor.Match> matches) {
      return matches.stream()
         .map(IPreprocessor.Match::content)
         .map(it -> it.split(SPACE))
         .flatMap(Arrays::stream)
         .distinct()
         .anyMatch(Services.PLATFORM.getPlatformName()::equalsIgnoreCase);
   }
}
