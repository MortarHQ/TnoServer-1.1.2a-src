package com.github.b4ndithelps.tensuraenigmatic;

import com.github.b4ndithelps.tensuraenigmatic.compat.EnigmaticCompat;
import com.github.b4ndithelps.tensuraenigmatic.config.TensuraEnigmaticConfig;
import com.github.b4ndithelps.tensuraenigmatic.handler.ElfPrayerHandler;
import com.github.b4ndithelps.tensuraenigmatic.handler.ManaRingHandler;
import com.github.b4ndithelps.tensuraenigmatic.handler.PowerOfForceHandler;
import com.github.b4ndithelps.tensuraenigmatic.handler.RingOfArcaneSourceHandler;
import com.github.b4ndithelps.tensuraenigmatic.handler.RingOfTheElvesHandler;
import com.github.b4ndithelps.tensuraenigmatic.registry.TensuraEnigmaticItems;
import com.github.b4ndithelps.tensuraenigmatic.registry.TensuraEnigmaticLoots;
import com.github.b4ndithelps.tensuraenigmatic.registry.TensuraEnigmaticTabs;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod("tensuraenigmatic")
public class TensuraEnigmatic {
   public static final String MODID = "tensuraenigmatic";
   public static final Logger LOGGER = LogUtils.getLogger();

   public TensuraEnigmatic(IEventBus modEventBus) {
      TensuraEnigmaticConfig.register();
      TensuraEnigmaticItems.ITEMS.register(modEventBus);
      TensuraEnigmaticTabs.CREATIVE_MODE_TABS.register(modEventBus);
      TensuraEnigmaticLoots.LOOT_MODIFIERS.register(modEventBus);
      IEventBus gameBus = NeoForge.EVENT_BUS;
      gameBus.register(ElfPrayerHandler.class);
      gameBus.register(ManaRingHandler.class);
      gameBus.register(PowerOfForceHandler.class);
      gameBus.register(RingOfArcaneSourceHandler.class);
      gameBus.register(RingOfTheElvesHandler.class);
      if (ModList.get().isLoaded("enigmaticlegacyplus")) {
         EnigmaticCompat.register(gameBus);
      }

      LOGGER.info("Tensura Enigmatic loaded.");
   }

   public static ResourceLocation location(String path) {
      return ResourceLocation.fromNamespaceAndPath("tensuraenigmatic", path);
   }
}
