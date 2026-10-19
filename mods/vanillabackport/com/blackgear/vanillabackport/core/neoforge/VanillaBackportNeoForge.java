package com.blackgear.vanillabackport.core.neoforge;

import com.blackgear.platform.core.Environment;
import com.blackgear.vanillabackport.core.VanillaBackport;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod("vanillabackport")
public final class VanillaBackportNeoForge {
   public VanillaBackportNeoForge() {
      VanillaBackport.bootstrap();
      IEventBus bus = ModLoadingContext.get().getActiveContainer().getEventBus();
      if (bus == null) {
         throw new IllegalStateException("Failed to load mod event bus for vanillabackport");
      }

      bus.addListener(this::commonSetup);
   }

   public void commonSetup(FMLCommonSetupEvent event) {
      if (Environment.hasModLoaded("terrablender")) {
         VanillaBackportTerrablender.onTerraBlenderInitialized();
      }
   }
}
