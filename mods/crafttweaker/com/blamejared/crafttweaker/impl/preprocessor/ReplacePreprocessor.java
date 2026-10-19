package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

@ZenRegister
@Preprocessor
public final class ReplacePreprocessor implements IPreprocessor {
   private static final String SPACE = Pattern.quote(" ");

   @Override
   public String name() {
      return "replace";
   }

   @Nullable
   @Override
   public String defaultValue() {
      return null;
   }

   @Override
   public boolean apply(IScriptFile file, List<String> preprocessedContents, IMutableScriptRunInfo runInfo, List<IPreprocessor.Match> matches) {
      matches.forEach(match -> {
         String[] split = match.content().split(SPACE, 2);
         if (split.length != 2) {
            PREPROCESSOR_LOGGER.warn("[{}:{}] Invalid Preprocessor line: #replace {}", file.name(), match.line(), match.content());
         } else {
            preprocessedContents.replaceAll(s -> s.replace(split[0], split[1]));
         }
      });
      return true;
   }
}
