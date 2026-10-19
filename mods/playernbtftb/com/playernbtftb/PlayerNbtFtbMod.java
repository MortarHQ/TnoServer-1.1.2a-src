package com.playernbtftb;

import com.mojang.logging.LogUtils;
import com.playernbtftb.config.PlayerNbtConfig;
import com.playernbtftb.tasks.ModTaskTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod("playernbtftb")
public class PlayerNbtFtbMod {
   public static final String MODID = "playernbtftb";
   public static final Logger LOGGER = LogUtils.getLogger();

   public PlayerNbtFtbMod(IEventBus modEventBus, ModContainer modContainer) {
      modEventBus.addListener(this::commonSetup);
      NeoForge.EVENT_BUS.register(this);
      modContainer.registerConfig(Type.COMMON, PlayerNbtConfig.SPEC);
   }

   private void commonSetup(FMLCommonSetupEvent event) {
      ModTaskTypes.init();
      LOGGER.info("PlayerNBT FTB mod initialized successfully");
   }

   @SubscribeEvent
   public void onServerStarting(ServerStartingEvent event) {
      LOGGER.info("PlayerNBT FTB server starting");
   }
}
