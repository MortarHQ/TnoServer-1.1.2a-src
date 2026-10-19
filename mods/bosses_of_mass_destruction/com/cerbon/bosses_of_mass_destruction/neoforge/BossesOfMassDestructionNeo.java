package com.cerbon.bosses_of_mass_destruction.neoforge;

import com.cerbon.bosses_of_mass_destruction.BossesOfMassDestruction;
import com.cerbon.bosses_of_mass_destruction.neoforge.attachment.BMDAttachments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("bosses_of_mass_destruction")
public class BossesOfMassDestructionNeo {
   public BossesOfMassDestructionNeo(IEventBus modEventBus) {
      BossesOfMassDestruction.init();
      BMDAttachments.register(modEventBus);
   }
}
