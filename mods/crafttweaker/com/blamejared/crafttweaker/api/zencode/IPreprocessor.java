package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.logging.log4j.Logger;

public interface IPreprocessor extends Comparator<IScriptFile> {
   Pattern PREPROCESSOR_PATTERN = Pattern.compile("#\\w+ ?");
   Logger PREPROCESSOR_LOGGER = CommonLoggers.zenCode();

   String name();

   default String preprocessorEndMarker() {
      return this.name();
   }

   default int priority() {
      return 10;
   }

   @Nullable
   String defaultValue();

   boolean apply(IScriptFile var1, List<String> var2, IMutableScriptRunInfo var3, List<IPreprocessor.Match> var4);

   default int compare(IScriptFile a, IScriptFile b) {
      return 0;
   }

   record Match(IPreprocessor preprocessor, int line, String content) {
   }
}
