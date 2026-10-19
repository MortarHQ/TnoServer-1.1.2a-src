package net.darkhax.pricklemc.neoforge.impl;

import net.darkhax.pricklemc.common.impl.PrickleMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("prickle")
public class NeoForgeMod {
   public NeoForgeMod(IEventBus eventBus) {
      PrickleMod.getInstance().init();
   }
}
