package com.mortal_cultivation.flight;

import com.mortal_cultivation.client.animation.FlightAnimationClientState;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class FlightAnimationNetwork {
   private FlightAnimationNetwork() {
   }

   public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("1.1");
      registrar.playToClient(FlightAnimationStatePayload.TYPE, FlightAnimationStatePayload.STREAM_CODEC, FlightAnimationNetwork::handleClientSync);
      registrar.playToServer(FlightBrakeRequestPayload.TYPE, FlightBrakeRequestPayload.STREAM_CODEC, FlightAnimationNetwork::handleBrakeRequest);
      registrar.playToClient(FlightBrakeStatePayload.TYPE, FlightBrakeStatePayload.STREAM_CODEC, FlightAnimationNetwork::handleBrakeSync);
   }

   public static void requestBrakeState(int sequence, boolean brakeHeld) {
      PacketDistributor.sendToServer(new FlightBrakeRequestPayload(sequence, brakeHeld), new CustomPacketPayload[0]);
   }

   public static void syncToTrackingAndSelf(ServerPlayer player, boolean active) {
      PacketDistributor.sendToPlayersTrackingEntityAndSelf(player, new FlightAnimationStatePayload(player.getUUID(), active), new CustomPacketPayload[0]);
   }

   public static void syncToPlayer(ServerPlayer recipient, ServerPlayer flyingPlayer, boolean active) {
      PacketDistributor.sendToPlayer(recipient, new FlightAnimationStatePayload(flyingPlayer.getUUID(), active), new CustomPacketPayload[0]);
   }

   public static void syncBrakeToTrackingAndSelf(ServerPlayer player, boolean brakeHeld, int sequence) {
      PacketDistributor.sendToPlayersTrackingEntityAndSelf(
         player, new FlightBrakeStatePayload(player.getUUID(), brakeHeld, sequence), new CustomPacketPayload[0]
      );
   }

   public static void syncBrakeToPlayer(ServerPlayer recipient, ServerPlayer flyingPlayer, boolean brakeHeld) {
      PacketDistributor.sendToPlayer(recipient, new FlightBrakeStatePayload(flyingPlayer.getUUID(), brakeHeld, -1), new CustomPacketPayload[0]);
   }

   private static void handleClientSync(FlightAnimationStatePayload payload, IPayloadContext context) {
      context.enqueueWork(() -> FlightAnimationClientState.setActive(payload.playerId(), payload.active()));
   }

   private static void handleBrakeRequest(FlightBrakeRequestPayload payload, IPayloadContext context) {
      if (context.player() instanceof ServerPlayer player) {
         FlightAnimationServerHandler.updateBrakeState(player, payload.brakeHeld(), payload.sequence());
      }
   }

   private static void handleBrakeSync(FlightBrakeStatePayload payload, IPayloadContext context) {
      context.enqueueWork(() -> FlightAnimationClientState.setBrakeHeld(payload.playerId(), payload.brakeHeld(), payload.sequence()));
   }
}
