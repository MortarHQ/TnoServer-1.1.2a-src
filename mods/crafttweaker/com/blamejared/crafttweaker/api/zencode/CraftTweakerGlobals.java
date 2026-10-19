package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(loaders = "*")
@ZenCodeType.Name("crafttweaker.api.Globals")
public final class CraftTweakerGlobals {
   private static final Supplier<Logger> LOGGER = Suppliers.memoize(() -> CraftTweakerAPI.getLogger("Script"));

   @ZenCodeGlobals.Global
   public static void println(String msg) {
      LOGGER.get().info(msg);
   }

   @ZenCodeGlobals.Global
   public static void print(String msg) {
      println(msg);
   }
}
