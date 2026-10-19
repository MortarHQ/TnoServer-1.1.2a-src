package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.action.base.IAction;
import com.blamejared.crafttweaker.api.logging.ILoggerRegistry;
import com.blamejared.crafttweaker.api.zencode.expand.IDataRewrites;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunManager;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker.platform.helper.IAccessibleElementsProvider;
import com.google.common.base.Suppliers;
import java.nio.file.Path;
import java.util.function.Supplier;
import org.apache.logging.log4j.Logger;
import org.openzen.zenscript.parser.expression.ParsedExpressionArray;
import org.openzen.zenscript.parser.expression.ParsedExpressionMap;

public final class CraftTweakerAPI {
   private static final Supplier<IAccessibleElementsProvider> ACCESSIBLE_ELEMENTS = Suppliers.memoize(Services.BRIDGE::accessibleElementsProvider);
   private static final Supplier<ICraftTweakerRegistry> REGISTRY = Suppliers.memoize(Services.BRIDGE::registry);
   private static final Supplier<IScriptRunManager> SCRIPT_RUN_MANAGER = Suppliers.memoize(Services.BRIDGE::scriptRunManager);
   private static final Supplier<ILoggerRegistry> LOGGER_REGISTRY = Suppliers.memoize(Services.BRIDGE::loggerRegistry);
   private static final Supplier<Path> SCRIPTS_DIRECTORY = Suppliers.memoize(CraftTweakerConstants::scriptsDir);

   public static IAccessibleElementsProvider getAccessibleElementsProvider() {
      return ACCESSIBLE_ELEMENTS.get();
   }

   public static ICraftTweakerRegistry getRegistry() {
      return REGISTRY.get();
   }

   public static IScriptRunManager getScriptRunManager() {
      return SCRIPT_RUN_MANAGER.get();
   }

   public static ILoggerRegistry getLoggerRegistry() {
      return LOGGER_REGISTRY.get();
   }

   public static Path getScriptsDirectory() {
      return SCRIPTS_DIRECTORY.get();
   }

   public static void apply(IAction action) {
      getScriptRunManager().applyAction(action);
   }

   public static Logger getLogger(String systemName) {
      return getLoggerRegistry().getLoggerFor(systemName);
   }

   static {
      ParsedExpressionMap.compileOverrides.add(IDataRewrites::rewriteMap);
      ParsedExpressionArray.compileOverrides.add(IDataRewrites::rewriteArray);
   }
}
