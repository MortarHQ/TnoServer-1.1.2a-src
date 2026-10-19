package com.chronodawn;

import com.chronodawn.config.ChronoDawnConfig;
import com.chronodawn.config.ConfigLoader;
import com.chronodawn.core.dimension.ChronoDawnBiomeProvider;
import com.chronodawn.core.dimension.ChronoDawnDimension;
import com.chronodawn.core.portal.PortalPersistenceManager;
import com.chronodawn.events.ChronoDawnEvents;
import com.chronodawn.registry.ModBlockEntities;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModCreativeTabs;
import com.chronodawn.registry.ModDimensions;
import com.chronodawn.registry.ModEffects;
import com.chronodawn.registry.ModEntities;
import com.chronodawn.registry.ModFeatures;
import com.chronodawn.registry.ModFluids;
import com.chronodawn.registry.ModItems;
import com.chronodawn.registry.ModParticles;
import com.chronodawn.registry.ModSounds;
import com.chronodawn.registry.ModStructureProcessorTypes;
import com.chronodawn.registry.ModTreeDecoratorTypes;
import com.chronodawn.worldgen.runtime.OverlayPackBootstrap;
import com.chronodawn.worldgen.runtime.OverlayPackPlatform;
import com.chronodawn.worldgen.spawning.ChronosWardenSpawner;
import com.chronodawn.worldgen.spawning.ClockworkColossusSpawner;
import com.chronodawn.worldgen.spawning.MasterClockBossRoomPlacer;
import com.chronodawn.worldgen.spawning.PhantomCatacombsBossRoomPlacer;
import com.chronodawn.worldgen.spawning.TemporalPhantomSpawner;
import com.chronodawn.worldgen.spawning.TimeGuardianSpawner;
import com.chronodawn.worldgen.spawning.TimeTyrantSpawner;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChronoDawn {
   public static final String MOD_ID = "chronodawn";
   public static final Logger LOGGER = LoggerFactory.getLogger(ChronoDawn.class);

   public static void init() {
      LOGGER.info("ChronoDawn Mod (common) initialized");
      Path configDir = OverlayPackPlatform.getConfigDirectory();
      ChronoDawnConfig config = ConfigLoader.load(configDir);
      OverlayPackBootstrap.writeOverlay(configDir, config);
      ModFluids.register();
      ModBlocks.register();
      ModBlockEntities.register();
      ModItems.register();
      ModSounds.register();
      ModEffects.register();
      ModEntities.register();
      ModParticles.register();
      ModDimensions.register();
      ModCreativeTabs.register();
      ModTreeDecoratorTypes.register();
      ModStructureProcessorTypes.register();
      ModFeatures.register();
      ChronoDawnEvents.register();
      ChronoDawnDimension.init();
      ChronoDawnBiomeProvider.init();
      PortalPersistenceManager.initialize();
      TimeGuardianSpawner.register();
      TimeTyrantSpawner.register();
      ChronosWardenSpawner.register();
      ClockworkColossusSpawner.register();
      MasterClockBossRoomPlacer.register();
      PhantomCatacombsBossRoomPlacer.register();
      TemporalPhantomSpawner.register();
   }
}
