package net.blay09.mods.netherportalfix;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("netherportalfix")
public class NeoForgeNetherPortalFix {
   public NeoForgeNetherPortalFix(IEventBus modEventBus) {
      NeoForgeLoadContext context = new NeoForgeLoadContext(modEventBus);
      Balm.initialize("netherportalfix", context, NetherPortalFix::initialize);
   }
}
