package it.hurts.octostudios.immersiveui.neoforge;

import dev.architectury.platform.Platform;
import it.hurts.octostudios.immersiveui.ImmersiveUI;
import it.hurts.octostudios.immersiveui.compat.SophisticatedProxy;
import it.hurts.octostudios.octolib.module.config.ConfigManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod("immersiveui")
@EventBusSubscriber(bus = Bus.MOD)
public final class ImmersiveUINeoForge {
   public ImmersiveUINeoForge() {
      try {
         ImmersiveUI.init();
         if (Platform.isModLoaded("sophisticatedcore")) {
            ImmersiveUI.SOPHISTICATED_COMPAT = (SophisticatedProxy)Class.forName("it.hurts.octostudios.immersiveui.compat.SophisticatedCompat")
               .getDeclaredConstructor()
               .newInstance();
         }
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   @SubscribeEvent
   public static void setup(FMLCommonSetupEvent event) {
      ConfigManager.registerConfig("immersiveui", ImmersiveUI.CONFIG);
   }
}
