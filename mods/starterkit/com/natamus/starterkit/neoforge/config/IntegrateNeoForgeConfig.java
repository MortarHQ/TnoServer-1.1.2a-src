package com.natamus.starterkit.neoforge.config;

import com.natamus.collective_common_neoforge.config.DuskConfig.DuskConfigScreen;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

public class IntegrateNeoForgeConfig {
   public static void registerScreen(ModLoadingContext modLoadingContext) {
      modLoadingContext.registerExtensionPoint(IConfigScreenFactory.class, () -> new IConfigScreenFactory() {
         @NotNull
         public Screen createScreen(@NotNull ModContainer modContainer, @NotNull Screen screen) {
            return DuskConfigScreen.getScreen(screen, "starterkit");
         }
      });
   }
}
