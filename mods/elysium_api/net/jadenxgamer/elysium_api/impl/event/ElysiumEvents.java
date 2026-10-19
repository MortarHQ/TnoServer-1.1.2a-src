package net.jadenxgamer.elysium_api.impl.event;

import java.util.Optional;
import net.jadenxgamer.elysium_api.impl.client.assetdriven.fog_settings.FogSettingsManager;
import net.jadenxgamer.elysium_api.impl.client.assetdriven.lightmap_settings.LightmapSettingsManager;
import net.jadenxgamer.elysium_api.impl.core.biome.MosaicBiomeSource;
import net.jadenxgamer.elysium_api.impl.core.datadriven.block.properties_transformer.BlockPropertiesTransformerHelper;
import net.jadenxgamer.elysium_api.impl.core.datadriven.block.use_behaviors.UseBehaviorImpl;
import net.jadenxgamer.elysium_api.impl.core.datadriven.item.properties_transformer.ItemPropertiesTransformerHelper;
import net.jadenxgamer.elysium_api.impl.core.surface_rules.ElysiumSurfaceRulesManager;
import net.jadenxgamer.elysium_api.impl.networking.ElysiumPayloads;
import net.jadenxgamer.elysium_api.impl.registry.ElysiumRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Pre;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent.NewRegistry;

@EventBusSubscriber(modid = "elysium_api")
public class ElysiumEvents {
   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onServerAboutToStart(ServerAboutToStartEvent event) {
      BlockPropertiesTransformerHelper.invalidateCache();
      ItemPropertiesTransformerHelper.invalidateCache();
      RegistryAccess registryAccess = event.getServer().registryAccess();
      Registry<LevelStem> levelStems = registryAccess.registryOrThrow(Registries.LEVEL_STEM);

      for (LevelStem dimension : levelStems.stream().toList()) {
         Optional<ResourceKey<LevelStem>> dimensionKey = levelStems.getResourceKey(dimension);
         if (dimensionKey.isPresent() && dimension.generator().getBiomeSource() instanceof MosaicBiomeSource biomeSource) {
            long seed = event.getServer().getWorldData().worldGenOptions().seed();
            biomeSource.initialize(seed, dimensionKey.get());
         }

         if (dimensionKey.isPresent() && dimension.generator() instanceof NoiseBasedChunkGenerator noiseGenerator) {
            ElysiumSurfaceRulesManager.handleSurfaceRules(dimensionKey.get(), noiseGenerator);
         }
      }
   }

   @SubscribeEvent
   public static void tickPlayerPre(Pre event) {
   }

   @SubscribeEvent
   public static void rightClickBlock(RightClickBlock event) {
      UseBehaviorImpl.init(event);
   }

   public static void modifyDefaultAttributes(EntityAttributeModificationEvent event) {
   }

   @SubscribeEvent
   public static void registerPayloads(RegisterPayloadHandlersEvent event) {
      ElysiumPayloads.registerPayloads(event);
   }

   @SubscribeEvent
   public static void commonSetup(FMLCommonSetupEvent event) {
   }

   @SubscribeEvent
   public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
      event.registerReloadListener(new FogSettingsManager());
      event.registerReloadListener(new LightmapSettingsManager());
   }

   @SubscribeEvent
   public static void datapackRegistry(NewRegistry event) {
      ElysiumRegistries.datapackInit(event);
   }
}
