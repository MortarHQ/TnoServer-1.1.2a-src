package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import java.util.List;

@ZenRegister
@Preprocessor
public final class PriorityPreprocessor implements IPreprocessor {
   public static final PriorityPreprocessor INSTANCE = new PriorityPreprocessor();
   private static final int DEFAULT_PRIORITY = 0;

   private PriorityPreprocessor() {
   }

   @Override
   public String name() {
      return "priority";
   }

   @Override
   public String defaultValue() {
      return Integer.toString(0);
   }

   @Override
   public boolean apply(IScriptFile file, List<String> preprocessedContents, IMutableScriptRunInfo runInfo, List<IPreprocessor.Match> matches) {
      if (matches.size() > 1) {
         PREPROCESSOR_LOGGER.warn("Conflicting priorities in file {}: only the first will be used", file.name());
      }

      String priority = matches.get(0).content().trim();

      try {
         Integer.parseInt(priority);
      } catch (NumberFormatException e) {
         PREPROCESSOR_LOGGER.warn("Invalid priority value '{}' for file {}", priority, file.name());
      }

      return true;
   }

   @Override
   public int compare(IScriptFile a, IScriptFile b) {
      return Integer.compare(this.getIntSafely(a.matchesFor(this).get(0)), this.getIntSafely(b.matchesFor(this).get(0)));
   }

   @Override
   public int priority() {
      return 100;
   }

   private int getIntSafely(IPreprocessor.Match match) {
      try {
         return Integer.parseInt(match.content().trim());
      } catch (NumberFormatException e) {
         return 0;
      }
   }
}
