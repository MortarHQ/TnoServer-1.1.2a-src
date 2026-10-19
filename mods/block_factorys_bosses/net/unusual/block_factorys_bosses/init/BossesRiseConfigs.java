package net.unusual.block_factorys_bosses.init;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.unusual.block_factorys_bosses.configuration.ClientConfiguration;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@EventBusSubscriber
public class BossesRiseConfigs {
   @SubscribeEvent
   public static void register(FMLConstructModEvent event) {
      event.enqueueWork(() -> ModList.get().getModContainerById("block_factorys_bosses").ifPresent(modContainer -> {
         modContainer.registerConfig(Type.CLIENT, ClientConfiguration.SPEC);
         modContainer.registerConfig(Type.SERVER, ServerConfiguration.SPEC);
      }));
   }
}
