package de.keksuccino.konkrete;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("konkrete")
public class KonkreteNeoForge {
   public KonkreteNeoForge(IEventBus eventBus) {
      Konkrete.init();
   }
}
