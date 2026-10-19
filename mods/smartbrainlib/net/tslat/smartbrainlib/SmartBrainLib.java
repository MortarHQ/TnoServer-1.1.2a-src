package net.tslat.smartbrainlib;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("smartbrainlib")
public class SmartBrainLib {
   public SmartBrainLib(IEventBus modBus) {
      SBLConstants.SBL_LOADER.init(modBus);
   }
}
