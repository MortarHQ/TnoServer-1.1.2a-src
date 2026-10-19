package it.hurts.octostudios.reliquified_twilight_forest.init;

import it.hurts.octostudios.reliquified_twilight_forest.network.CastRideAlongAbilityPacket;
import it.hurts.octostudios.reliquified_twilight_forest.network.EntityStartRidingPacket;
import it.hurts.octostudios.reliquified_twilight_forest.network.EntityStopRidingPacket;
import it.hurts.octostudios.reliquified_twilight_forest.network.ExecutionEffectPacket;
import it.hurts.octostudios.reliquified_twilight_forest.network.LaunchTwilightBoltPacket;
import it.hurts.octostudios.reliquified_twilight_forest.network.LifedrainParticlePacket;
import it.hurts.octostudios.reliquified_twilight_forest.network.ParasiteEvolveParticlePacket;
import it.hurts.octostudios.reliquified_twilight_forest.network.ScaledCloakWallClimbPacket;
import it.hurts.octostudios.reliquified_twilight_forest.network.UpdateChunkPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(bus = Bus.MOD)
public class PacketRegistry {
   @SubscribeEvent
   public static void setupPackets(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("reliquified_twilight_forest").versioned("1.0.0");
      registrar.playToClient(UpdateChunkPacket.TYPE, UpdateChunkPacket.STREAM_CODEC, UpdateChunkPacket::handle);
      registrar.playToClient(ExecutionEffectPacket.TYPE, ExecutionEffectPacket.STREAM_CODEC, ExecutionEffectPacket::handle);
      registrar.playToClient(EntityStopRidingPacket.TYPE, EntityStopRidingPacket.STREAM_CODEC, EntityStopRidingPacket::handle);
      registrar.playToClient(LifedrainParticlePacket.TYPE, LifedrainParticlePacket.STREAM_CODEC, LifedrainParticlePacket::handle);
      registrar.playToClient(EntityStartRidingPacket.TYPE, EntityStartRidingPacket.STREAM_CODEC, EntityStartRidingPacket::handle);
      registrar.playToClient(ParasiteEvolveParticlePacket.TYPE, ParasiteEvolveParticlePacket.STREAM_CODEC, ParasiteEvolveParticlePacket::handle);
      registrar.playToServer(CastRideAlongAbilityPacket.TYPE, CastRideAlongAbilityPacket.STREAM_CODEC, CastRideAlongAbilityPacket::handle);
      registrar.playToServer(ScaledCloakWallClimbPacket.TYPE, ScaledCloakWallClimbPacket.STREAM_CODEC, ScaledCloakWallClimbPacket::handle);
      registrar.playBidirectional(LaunchTwilightBoltPacket.TYPE, LaunchTwilightBoltPacket.STREAM_CODEC, LaunchTwilightBoltPacket::handle);
   }
}
