package com.natamus.collective;

import com.natamus.collective.neoforge.config.NeoForgeCollectiveConfigScreen;
import com.natamus.collective.neoforge.events.RegisterCollectiveNeoForgeClientEvents;
import com.natamus.collective.neoforge.events.RegisterCollectiveNeoForgeEvents;
import com.natamus.collective.neoforge.networking.NeoForgeNetworkHandler;
import com.natamus.collective.neoforge.services.NeoForgeRegisterItemHelper;
import com.natamus.collective_common_neoforge.CollectiveCommon;
import com.natamus.collective_common_neoforge.check.RegisterMod;
import com.natamus.collective_common_neoforge.implementations.networking.NetworkSetup;
import com.natamus.collective_common_neoforge.implementations.networking.PacketRegistrationHandler;
import com.natamus.collective_common_neoforge.implementations.networking.data.Side;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;

@Mod("collective")
public class CollectiveNeoForge {
   public static CollectiveNeoForge instance;
   private final PacketRegistrationHandler handler;

   public CollectiveNeoForge(IEventBus modEventBus) {
      instance = this;
      setGlobalConstants();
      CollectiveCommon.init();
      NeoForgeCollectiveConfigScreen.registerScreen(ModLoadingContext.get());
      modEventBus.addListener(this::commonSetupEvent);
      modEventBus.addListener(this::loadComplete);
      modEventBus.addListener(NeoForgeRegisterItemHelper::addItemsToCreativeInventory);
      this.handler = new NeoForgeNetworkHandler(FMLLoader.getDist().isClient() ? Side.CLIENT : Side.SERVER);
      modEventBus.register(this.handler);
      RegisterMod.register("Collective", "collective", "8.3", "[1.21.1]");
   }

   public void commonSetupEvent(FMLCommonSetupEvent event) {
      new NetworkSetup(this.handler);
   }

   private void loadComplete(FMLLoadCompleteEvent event) {
      NeoForge.EVENT_BUS.register(RegisterCollectiveNeoForgeEvents.class);
      if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
         NeoForge.EVENT_BUS.register(RegisterCollectiveNeoForgeClientEvents.class);
      }
   }

   private static void setGlobalConstants() {
   }
}
