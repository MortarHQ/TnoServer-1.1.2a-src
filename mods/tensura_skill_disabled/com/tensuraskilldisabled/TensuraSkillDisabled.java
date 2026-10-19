package com.tensuraskilldisabled;

import com.mojang.logging.LogUtils;
import com.tensuraskilldisabled.command.SkillBlacklistCommand;
import com.tensuraskilldisabled.config.SkillBlacklistConfig;
import com.tensuraskilldisabled.handler.SkillBlacklistEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

@Mod("tensura_skill_disabled")
public class TensuraSkillDisabled {
   public static final String MOD_ID = "tensura_skill_disabled";
   public static final Logger LOGGER = LogUtils.getLogger();

   public TensuraSkillDisabled(IEventBus modEventBus, ModContainer modContainer) {
      modEventBus.addListener(this::commonSetup);
      NeoForge.EVENT_BUS.register(this);
   }

   private void commonSetup(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> {
         SkillBlacklistConfig.getInstance();
         SkillBlacklistEvents.register();
         LOGGER.info("TenSura skill blacklist initialized");
      });
   }

   @SubscribeEvent
   public void onRegisterCommands(RegisterCommandsEvent event) {
      SkillBlacklistCommand.register(event.getDispatcher());
   }
}
