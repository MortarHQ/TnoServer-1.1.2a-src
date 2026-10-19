package com.blamejared.crafttweaker.impl.preprocessor.onlyif.parameter;

import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameter;
import com.blamejared.crafttweaker.impl.preprocessor.onlyif.OnlyIfParameterHit;
import java.util.Arrays;
import net.minecraft.SharedConstants;

public final class OnlyIfParameterGameVersion extends OnlyIfParameter {
   public OnlyIfParameterGameVersion() {
      super("gameversion");
   }

   @Override
   public OnlyIfParameterHit isHit(String[] additionalArguments) {
      if (additionalArguments.length == 0) {
         return OnlyIfParameterHit.invalid();
      }

      String gameVersion = SharedConstants.getCurrentVersion().getName();
      boolean conditionMet = Arrays.asList(additionalArguments).contains(gameVersion);
      return OnlyIfParameterHit.basedOn(conditionMet, additionalArguments.length);
   }
}
