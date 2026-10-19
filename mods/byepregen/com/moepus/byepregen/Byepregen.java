package com.moepus.byepregen;

import com.moepus.byepregen.bootstrap.TestWorldGenBootstrap;
import com.moepus.byepregen.compat.GcFreeCompat;
import com.moepus.byepregen.yalight.YABlockStateLightClass;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod("byepregen")
public class Byepregen {
   public static final String MODID = "byepregen";
   private static final String TEST_WORLD_GEN_PROPERTY = "byepregen.testWorldGen";

   public Byepregen(IEventBus modEventBus, ModContainer modContainer) {
      modEventBus.addListener(Byepregen::onLoadComplete);
      GcFreeCompat.register();
      if (Boolean.getBoolean("byepregen.testWorldGen")) {
         TestWorldGenBootstrap.register();
      }
   }

   private static void onLoadComplete(FMLLoadCompleteEvent event) {
      if (ConfigParser.getConfig().enableYALightEngine) {
         event.enqueueWork(() -> YABlockStateLightClass.initialize());
      }
   }
}
