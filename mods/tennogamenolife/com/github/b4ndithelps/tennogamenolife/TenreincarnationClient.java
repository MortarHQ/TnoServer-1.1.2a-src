package com.github.b4ndithelps.tennogamenolife;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

@Mod(value = "tennogamenolife", dist = Dist.CLIENT)
public class TenreincarnationClient {
   private static final Logger LOGGER = LogUtils.getLogger();

   public TenreincarnationClient(ModContainer container) {
      LOGGER.info("TenNoGameNoLife 客户端初始化");
   }

   @EventBusSubscriber(modid = "tennogamenolife", value = Dist.CLIENT)
   public static class ClientModEvents {
      @SubscribeEvent
      public static void onClientSetup(FMLClientSetupEvent event) {
         TenreincarnationClient.LOGGER.info("HELLO FROM CLIENT SETUP");
         TenreincarnationClient.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
      }
   }
}
