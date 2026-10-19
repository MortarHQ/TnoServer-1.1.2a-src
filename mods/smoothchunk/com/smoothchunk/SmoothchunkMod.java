package com.smoothchunk;

import com.cupboard.config.CupboardConfig;
import com.smoothchunk.config.CommonConfiguration;
import java.util.Random;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("smoothchunk")
public class SmoothchunkMod {
   public static final String MODID = "smoothchunk";
   public static final Logger LOGGER = LogManager.getLogger();
   public static CupboardConfig<CommonConfiguration> config = new CupboardConfig("smoothchunk", new CommonConfiguration());
   public static Random rand = new Random();

   public SmoothchunkMod(IEventBus modEventBus, ModContainer modContainer) {
   }
}
