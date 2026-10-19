package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.platform.NeoForgeRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod("crafttweaker")
public class CraftTweakerNeoForge {
   public CraftTweakerNeoForge(IEventBus bus) {
      CraftTweakerCommon.init();
      NeoForgeRegistryHelper.init(bus);
      CraftTweakerCommon.getPluginManager().loadPlugins();
      bus.addListener(this::setup);
      CraftTweakerCommon.getPluginManager().broadcastSetupEnd();
   }

   private void setup(FMLCommonSetupEvent event) {
      CraftTweakerCommon.loadInitScripts();
   }
}
