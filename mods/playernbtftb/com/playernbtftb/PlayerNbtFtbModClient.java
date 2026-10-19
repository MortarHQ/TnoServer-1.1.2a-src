package com.playernbtftb;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = "playernbtftb", dist = Dist.CLIENT)
@EventBusSubscriber(modid = "playernbtftb", value = Dist.CLIENT)
public class PlayerNbtFtbModClient {
   public PlayerNbtFtbModClient(ModContainer container) {
   }

   @SubscribeEvent
   static void onClientSetup(FMLClientSetupEvent event) {
      PlayerNbtFtbMod.LOGGER.info("PlayerNBT FTB client setup complete");
      PlayerNbtFtbMod.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
   }
}
