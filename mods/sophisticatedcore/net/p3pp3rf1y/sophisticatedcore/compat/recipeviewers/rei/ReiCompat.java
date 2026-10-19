package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.rei;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.p3pp3rf1y.sophisticatedcore.compat.ICompat;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common.CommonPayloads;

public class ReiCompat implements ICompat {
   @Override
   public void init(IEventBus modBus) {
      modBus.addListener(this::registerPackets);
   }

   @Override
   public void setup() {
   }

   private void registerPackets(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("sophisticatedcore").versioned("1.0");
      registrar.optional().playToServer(ReiTransferRecipePayload.TYPE, ReiTransferRecipePayload.STREAM_CODEC, ReiTransferRecipePayload::handlePayload);
      CommonPayloads.registerPackets(registrar);
   }
}
