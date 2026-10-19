package com.natamus.collective.fabric.config;

import com.natamus.collective_common_fabric.config.DuskConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class FabricCollectiveConfigScreen implements ModMenuApi {
   public ConfigScreenFactory<?> getModConfigScreenFactory() {
      return parent -> DuskConfig.DuskConfigScreen.getScreen(parent, "collective");
   }
}
