package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.blamejared.crafttweaker.platform.Services;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

@ZenRegister
@Preprocessor
public final class ModNotLoadedPreprocessor implements IPreprocessor {
   private static final String SPACE = Pattern.quote(" ");

   @Override
   public String name() {
      return "modnotloaded";
   }

   @Nullable
   @Override
   public String defaultValue() {
      return null;
   }

   @Override
   public boolean apply(IScriptFile file, List<String> preprocessedContents, IMutableScriptRunInfo runInfo, List<IPreprocessor.Match> matches) {
      return !matches.stream()
         .map(IPreprocessor.Match::content)
         .map(it -> it.split(SPACE))
         .flatMap(Arrays::stream)
         .peek(this::verifyLowercase)
         .distinct()
         .allMatch(Services.PLATFORM::isModLoaded);
   }

   private void verifyLowercase(String id) {
      if (!id.toLowerCase(Locale.ENGLISH).equals(id)) {
         PREPROCESSOR_LOGGER.error("Mod IDs cannot have uppercase letters: the ID " + id + " will never match");
      }
   }
}
