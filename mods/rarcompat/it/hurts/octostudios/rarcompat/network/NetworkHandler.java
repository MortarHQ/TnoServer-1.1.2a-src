package it.hurts.octostudios.rarcompat.network;

import it.hurts.octostudios.rarcompat.network.packets.DoubleJumpPacket;
import it.hurts.octostudios.rarcompat.network.packets.FlamingoSwimPacket;
import it.hurts.octostudios.rarcompat.network.packets.PowerJumpPacket;
import it.hurts.octostudios.rarcompat.network.packets.RepulsionUmbrellaPacket;
import it.hurts.octostudios.rarcompat.network.packets.SteadfastSpikesPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(bus = Bus.MOD)
public class NetworkHandler {
   @SubscribeEvent
   public static void onRegisterPayloadHandler(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("rarcompat").versioned("1.0").optional();
      registrar.playToServer(DoubleJumpPacket.TYPE, DoubleJumpPacket.STREAM_CODEC, DoubleJumpPacket::handle);
      registrar.playToServer(PowerJumpPacket.TYPE, PowerJumpPacket.STREAM_CODEC, PowerJumpPacket::handle);
      registrar.playToServer(RepulsionUmbrellaPacket.TYPE, RepulsionUmbrellaPacket.STREAM_CODEC, RepulsionUmbrellaPacket::handle);
      registrar.playToServer(FlamingoSwimPacket.TYPE, FlamingoSwimPacket.STREAM_CODEC, FlamingoSwimPacket::handle);
      registrar.playToServer(SteadfastSpikesPacket.TYPE, SteadfastSpikesPacket.STREAM_CODEC, SteadfastSpikesPacket::handle);
   }
}
