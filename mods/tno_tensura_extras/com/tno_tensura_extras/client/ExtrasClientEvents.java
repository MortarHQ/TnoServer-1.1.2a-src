package com.tno_tensura_extras.client;

import com.tno_tensura_extras.registry.ExtrasBlocks;
import com.tno_tensura_extras.registry.ExtrasEntityTypes;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

public class ExtrasClientEvents {
   public static void onRegisterRenderers(RegisterRenderers event) {
      event.registerEntityRenderer(ExtrasEntityTypes.DIABLO.get(), DiabloRenderer::new);
      event.registerEntityRenderer(ExtrasEntityTypes.DIABLO_DECOY.get(), DiabloDecoyRenderer::new);
      event.registerEntityRenderer(ExtrasEntityTypes.TELEPORT_PORTAL.get(), TeleportPortalRenderer::new);
      event.registerEntityRenderer(ExtrasEntityTypes.LOOTER_BLACK_HOLE.get(), LooterBlackHoleRenderer::new);
      event.registerEntityRenderer(ExtrasEntityTypes.ALIGNMENT_FIELD.get(), AlignmentFieldRenderer::new);
      event.registerBlockEntityRenderer(ExtrasBlocks.CARGO_IN_TRANSIT_ENTITY.get(), CargoInTransitRenderer::new);
   }
}
