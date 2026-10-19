package com.craftix.medieval_buildings;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod("medieval_buildings")
public class MainEntrypointNeoforge {
   public static final String MODID = "medieval_buildings";
   private static final Logger LOGGER = LogUtils.getLogger();

   public MainEntrypointNeoforge(IEventBus modEventBus, ModContainer modContainer) {
   }
}
