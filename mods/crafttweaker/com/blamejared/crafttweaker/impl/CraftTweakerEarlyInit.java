package com.blamejared.crafttweaker.impl;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.logging.CraftTweakerLog4jEditor;
import com.blamejared.crafttweaker.impl.script.recipefs.RecipeFileSystemProviderInjector;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.minecraft.SharedConstants;
import org.apache.logging.log4j.Logger;

public final class CraftTweakerEarlyInit {
   private static final String EARLY_INIT_SYSTEM = "CraftTweaker-EarlyInit";
   private static final Supplier<Logger> LOGGER = Suppliers.memoize(() -> CraftTweakerAPI.getLogger("CraftTweaker-EarlyInit"));

   private CraftTweakerEarlyInit() {
   }

   public static void run() {
      CraftTweakerLog4jEditor.edit();
      announce();
      RecipeFileSystemProviderInjector.inject(LOGGER.get());
   }

   private static void announce() {
      LOGGER.get()
         .info(
            "{} is running on a Minecraft {} {} {} modded with platform {}",
            "CraftTweaker",
            SharedConstants.getCurrentVersion().getName(),
            Services.PLATFORM.isDevelopmentEnvironment() ? "development" : "production",
            Services.DISTRIBUTION.getDistributionType(),
            Services.PLATFORM.getPlatformName()
         );
   }
}
