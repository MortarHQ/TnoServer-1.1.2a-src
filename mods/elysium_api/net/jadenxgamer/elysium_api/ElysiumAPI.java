package net.jadenxgamer.elysium_api;

import net.jadenxgamer.elysium_api.impl.client.assetdriven.fog_settings.FogSettingsManager;
import net.jadenxgamer.elysium_api.impl.client.assetdriven.lightmap_settings.LightmapSettingsManager;
import net.jadenxgamer.elysium_api.impl.config.ElysiumConfigImpl;
import net.jadenxgamer.elysium_api.impl.registry.ElysiumAttributes;
import net.jadenxgamer.elysium_api.impl.registry.ElysiumBlocks;
import net.jadenxgamer.elysium_api.impl.registry.ElysiumItems;
import net.jadenxgamer.elysium_api.impl.registry.ElysiumRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("elysium_api")
public final class ElysiumAPI {
   public static final String MOD_ID = "elysium_api";
   public static final Logger LOGGER = LoggerFactory.getLogger("ElysiumAPI");
   public static final FogSettingsManager FOG_SETTINGS = new FogSettingsManager();
   public static final LightmapSettingsManager LIGHTMAP_SETTINGS = new LightmapSettingsManager();

   public ElysiumAPI(IEventBus modEventBus, ModContainer modContainer) {
      modContainer.registerConfig(Type.COMMON, ElysiumConfigImpl.COMMON);
      ElysiumRegistries.init(modEventBus);
      ElysiumAttributes.init(modEventBus);
      ElysiumBlocks.init(modEventBus);
      ElysiumItems.init(modEventBus);
   }

   public static ResourceLocation elysiumPath(String path) {
      return ResourceLocation.fromNamespaceAndPath("elysium_api", path);
   }

   public static ResourceLocation idPath(String namespace, String path) {
      return ResourceLocation.fromNamespaceAndPath(namespace, path);
   }
}
