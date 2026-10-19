package com.tensura.naturesaura;

import com.tensura.naturesaura.integration.LegacyBiomeAuraMigration;
import com.tensura.naturesaura.integration.LegacyGearEpMigration;
import com.tensura.naturesaura.integration.TensuraOreIntegration;
import com.tensura.naturesaura.registry.LegacyAttachments;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod("tensura_naturesaura")
public final class TensuraNaturesAura {
   public static final String MOD_ID = "tensura_naturesaura";

   public TensuraNaturesAura(ModContainer container) {
      LegacyAttachments.register(container.getEventBus());
      container.getEventBus().addListener(TensuraOreIntegration::onCommonSetup);
      NeoForge.EVENT_BUS.addListener(LegacyBiomeAuraMigration::onChunkLoad);
      NeoForge.EVENT_BUS.addListener(LegacyGearEpMigration::onEquipmentChange);
      NeoForge.EVENT_BUS.addListener(LegacyGearEpMigration::onPlayerLoggedIn);
   }
}
