package dev.corgitaco.dataanchor.neoforge;

import dev.corgitaco.dataanchor.DataAnchor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("dataanchor")
public class DataAnchorNeoForge {
   public DataAnchorNeoForge(IEventBus eventBus) {
      DataAnchor.init();
   }
}
