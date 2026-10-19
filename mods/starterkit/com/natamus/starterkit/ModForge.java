package com.natamus.starterkit;

import com.natamus.collective_common_forge.check.RegisterMod;
import com.natamus.collective_common_forge.check.ShouldLoadCheck;
import com.natamus.starterkit.forge.config.IntegrateForgeConfig;
import com.natamus.starterkit.forge.events.ForgeStarterClientEvents;
import com.natamus.starterkit.forge.events.ForgeStarterServerEvents;
import com.natamus.starterkit_common_forge.ModCommon;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod("starterkit")
public class ModForge {
   public ModForge() {
      if (ShouldLoadCheck.shouldLoad("starterkit")) {
         IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
         modEventBus.addListener(this::loadComplete);
         setGlobalConstants();
         ModCommon.init();
         IntegrateForgeConfig.registerScreen(ModLoadingContext.get());
         RegisterMod.register("Starter Kit", "starterkit", "7.4", "[1.21.1]");
      }
   }

   private void loadComplete(FMLLoadCompleteEvent event) {
      if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
         MinecraftForge.EVENT_BUS.register(ForgeStarterClientEvents.class);
      }

      MinecraftForge.EVENT_BUS.register(ForgeStarterServerEvents.class);
   }

   private static void setGlobalConstants() {
   }
}
