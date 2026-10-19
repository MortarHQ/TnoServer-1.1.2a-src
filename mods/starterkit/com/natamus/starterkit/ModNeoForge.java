package com.natamus.starterkit;

import com.natamus.collective_common_neoforge.check.RegisterMod;
import com.natamus.collective_common_neoforge.check.ShouldLoadCheck;
import com.natamus.starterkit.neoforge.config.IntegrateNeoForgeConfig;
import com.natamus.starterkit.neoforge.events.NeoForgeStarterClientEvents;
import com.natamus.starterkit.neoforge.events.NeoForgeStarterServerEvents;
import com.natamus.starterkit_common_neoforge.ModCommon;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod("starterkit")
public class ModNeoForge {
   public ModNeoForge(IEventBus modEventBus) {
      if (ShouldLoadCheck.shouldLoad("starterkit")) {
         modEventBus.addListener(this::loadComplete);
         setGlobalConstants();
         ModCommon.init();
         IntegrateNeoForgeConfig.registerScreen(ModLoadingContext.get());
         RegisterMod.register("Starter Kit", "starterkit", "7.4", "[1.21.1]");
      }
   }

   private void loadComplete(FMLLoadCompleteEvent event) {
      if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
         NeoForge.EVENT_BUS.register(NeoForgeStarterClientEvents.class);
      }

      NeoForge.EVENT_BUS.register(NeoForgeStarterServerEvents.class);
   }

   private static void setGlobalConstants() {
   }
}
