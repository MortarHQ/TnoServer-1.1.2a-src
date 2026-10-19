package com.natamus.collective.forge.config;

import com.natamus.collective_common_forge.config.DuskConfig;
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.minecraftforge.fml.ModLoadingContext;

public class ForgeCollectiveConfigScreen {
   public static void registerScreen(ModLoadingContext modLoadingContext) {
      modLoadingContext.registerExtensionPoint(
         ConfigScreenFactory.class, () -> new ConfigScreenFactory((mc, screen) -> DuskConfig.DuskConfigScreen.getScreen(screen, "collective"))
      );
   }
}
