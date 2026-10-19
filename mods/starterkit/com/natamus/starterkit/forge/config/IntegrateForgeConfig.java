package com.natamus.starterkit.forge.config;

import com.natamus.collective_common_forge.config.DuskConfig.DuskConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.minecraftforge.fml.ModLoadingContext;

public class IntegrateForgeConfig {
   public static void registerScreen(ModLoadingContext modLoadingContext) {
      modLoadingContext.registerExtensionPoint(
         ConfigScreenFactory.class, () -> new ConfigScreenFactory((mc, screen) -> DuskConfigScreen.getScreen(screen, "starterkit"))
      );
   }
}
