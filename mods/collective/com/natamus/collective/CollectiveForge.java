package com.natamus.collective;

import com.natamus.collective.forge.config.ForgeCollectiveConfigScreen;
import com.natamus.collective.forge.events.RegisterCollectiveForgeClientEvents;
import com.natamus.collective.forge.events.RegisterCollectiveForgeEvents;
import com.natamus.collective.forge.networking.ForgeNetworkHandler;
import com.natamus.collective.forge.services.ForgeRegisterItemHelper;
import com.natamus.collective_common_forge.CollectiveCommon;
import com.natamus.collective_common_forge.check.RegisterMod;
import com.natamus.collective_common_forge.implementations.networking.NetworkSetup;
import com.natamus.collective_common_forge.implementations.networking.data.Side;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod("collective")
public class CollectiveForge {
   public static CollectiveForge instance;

   public CollectiveForge() {
      instance = this;
      setGlobalConstants();
      CollectiveCommon.init();
      ForgeCollectiveConfigScreen.registerScreen(ModLoadingContext.get());
      IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
      modEventBus.addListener(this::commonSetupEvent);
      modEventBus.addListener(this::loadComplete);
      modEventBus.addListener(ForgeRegisterItemHelper::addItemsToCreativeInventory);
      RegisterMod.register("Collective", "collective", "8.3", "[1.21.1]");
   }

   private void commonSetupEvent(FMLCommonSetupEvent event) {
      new NetworkSetup(new ForgeNetworkHandler(FMLLoader.getDist().isClient() ? Side.CLIENT : Side.SERVER));
   }

   private void loadComplete(FMLLoadCompleteEvent event) {
      MinecraftForge.EVENT_BUS.register(new RegisterCollectiveForgeEvents());
      if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
         MinecraftForge.EVENT_BUS.register(RegisterCollectiveForgeClientEvents.class);
      }
   }

   private static void setGlobalConstants() {
   }
}
