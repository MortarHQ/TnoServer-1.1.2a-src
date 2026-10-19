package org.crypticdev.stextras.neoforge;

import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.crypticdev.stextras.Stextras;
import org.crypticdev.stextras.client.screen.ImitatorScreen;
import org.crypticdev.stextras.registary.menu.STExtrasMenuTypes;

@Mod("stextras")
public final class StextrasNeoForge {
   public StextrasNeoForge(IEventBus modEventBus) {
      Stextras.init();
      EnvExecutor.runInEnv(Env.CLIENT, () -> () -> {
         modEventBus.addListener(StextrasNeoForge::onClientSetup);
         modEventBus.addListener(StextrasNeoForge::onRegisterMenuScreens);
      });
   }

   private static void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         Stextras.initClient();
         StextrasNeoForgeClient.init();
      });
   }

   private static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
      event.register((MenuType)STExtrasMenuTypes.IMITATOR_SKILL.get(), ImitatorScreen::new);
   }
}
