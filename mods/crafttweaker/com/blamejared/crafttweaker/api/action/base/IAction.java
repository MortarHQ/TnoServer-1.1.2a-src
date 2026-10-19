package com.blamejared.crafttweaker.api.action.base;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.util.PositionUtil;
import javax.annotation.Nonnull;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.shared.CodePosition;

public interface IAction {
   void apply();

   String describe();

   String systemName();

   default boolean validate(Logger logger) {
      return true;
   }

   default boolean shouldApplyOn(IScriptLoadSource source, Logger logger) {
      return source.id().equals(CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID);
   }

   @Nonnull
   default CodePosition getDeclaredScriptPosition() {
      return PositionUtil.getZCScriptPositionFromStackTrace();
   }

   default boolean assertLoader(IScriptLoader loader, Logger logger) {
      IScriptLoader currentLoader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
      if (currentLoader.equals(loader)) {
         return true;
      }

      logger.warn(
         "Action '{}' ({}) can only be invoked on loader '{}'. You tried to run it on loader '{}'.",
         this.getClass().getName(),
         this.getDeclaredScriptPosition(),
         loader,
         currentLoader
      );
      return false;
   }

   default Logger logger() {
      return CraftTweakerAPI.getLogger(this.systemName());
   }
}
