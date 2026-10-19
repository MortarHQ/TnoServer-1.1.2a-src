package com.moepus.biomespy;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("biomespy")
public class Biomespy {
   public Biomespy(IEventBus eventBus) {
      BiomeSpyCommon.init();
   }
}
