package com.blamejared.crafttweaker.impl.preprocessor.onlyif;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import java.util.List;
import javax.annotation.Nullable;

@ZenRegister
@Preprocessor
public final class EndIfPreprocessor implements IPreprocessor {
   public static final EndIfPreprocessor INSTANCE = new EndIfPreprocessor();

   private EndIfPreprocessor() {
   }

   @Override
   public String name() {
      return "endif";
   }

   @Nullable
   @Override
   public String defaultValue() {
      return null;
   }

   @Override
   public boolean apply(IScriptFile file, List<String> preprocessedContents, IMutableScriptRunInfo runInfo, List<IPreprocessor.Match> matches) {
      return true;
   }
}
