package com.blamejared.crafttweaker.impl.preprocessor;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;

@ZenRegister
@Preprocessor
public final class GameVersionPreprocessor implements IPreprocessor {
   private static final String SPACE = Pattern.quote(" ");

   @Override
   public String name() {
      return "gameversion";
   }

   @Nullable
   @Override
   public String defaultValue() {
      return null;
   }

   @Override
   public boolean apply(IScriptFile file, List<String> preprocessedContents, IMutableScriptRunInfo runInfo, List<IPreprocessor.Match> matches) {
      String gameVersion = SharedConstants.getCurrentVersion().getName();
      return matches.stream().map(IPreprocessor.Match::content).map(it -> it.split(SPACE)).flatMap(Arrays::stream).distinct().anyMatch(gameVersion::equals);
   }
}
