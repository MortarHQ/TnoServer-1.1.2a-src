package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import java.util.List;
import javax.annotation.Nullable;

@ZenRegister
@Preprocessor
public final class LoadLastPreprocessor implements IPreprocessor {
   public static final LoadLastPreprocessor INSTANCE = new LoadLastPreprocessor();

   private LoadLastPreprocessor() {
   }

   @Override
   public String name() {
      return "loadlast";
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

   @Override
   public int priority() {
      return 11;
   }

   @Override
   public int compare(IScriptFile a, IScriptFile b) {
      return Boolean.compare(a.hasMatchesFor(this), b.hasMatchesFor(this));
   }
}
