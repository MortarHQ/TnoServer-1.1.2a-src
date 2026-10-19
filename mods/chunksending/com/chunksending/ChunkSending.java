package com.chunksending;

import com.chunksending.config.CommonConfiguration;
import com.cupboard.config.CupboardConfig;
import java.util.Random;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("chunksending")
public class ChunkSending {
   public static final String MODID = "chunksending";
   public static final Logger LOGGER = LogManager.getLogger();
   public static CupboardConfig<CommonConfiguration> config = new CupboardConfig("chunksending", new CommonConfiguration());
   public static Random rand = new Random();

   public ChunkSending(IEventBus modEventBus, ModContainer modContainer) {
      modEventBus.addListener(this::setup);
   }

   private void setup(FMLCommonSetupEvent event) {
      LOGGER.info("chunksending mod initialized");
   }
}
