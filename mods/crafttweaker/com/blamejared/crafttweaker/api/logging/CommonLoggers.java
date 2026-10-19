package com.blamejared.crafttweaker.api.logging;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import org.apache.logging.log4j.Logger;

public final class CommonLoggers {
   private static final Supplier<Logger> API = loggerSupplier("CraftTweaker");
   private static final Supplier<Logger> COMMANDS = loggerSupplier(ctSystem("Commands"));
   private static final Supplier<Logger> ZEN_CODE = loggerSupplier(ctSystem("ZenCode"));

   private CommonLoggers() {
   }

   public static Logger api() {
      return API.get();
   }

   public static Logger commands() {
      return COMMANDS.get();
   }

   public static Logger zenCode() {
      return ZEN_CODE.get();
   }

   private static Supplier<Logger> loggerSupplier(String system) {
      return Suppliers.memoize(() -> CraftTweakerAPI.getLogger(system));
   }

   private static String ctSystem(String system) {
      return "CraftTweaker-" + system;
   }
}
