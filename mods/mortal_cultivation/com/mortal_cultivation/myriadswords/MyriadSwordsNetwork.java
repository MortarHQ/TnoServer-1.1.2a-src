package com.mortal_cultivation.myriadswords;

import com.mortal_cultivation.client.vfx.MyriadSwordsClientEffects;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class MyriadSwordsNetwork {
   private MyriadSwordsNetwork() {
   }

   public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("1.1");
      registrar.playToClient(MyriadSwordsStatePayload.TYPE, MyriadSwordsStatePayload.STREAM_CODEC, MyriadSwordsNetwork::handleClientState);
   }

   public static void sendToTrackingAndSelf(ServerPlayer caster, MyriadSwordsStatePayload payload) {
      PacketDistributor.sendToPlayersTrackingEntityAndSelf(caster, payload, new CustomPacketPayload[0]);
   }

   public static void sendToPlayer(ServerPlayer recipient, MyriadSwordsStatePayload payload) {
      PacketDistributor.sendToPlayer(recipient, payload, new CustomPacketPayload[0]);
   }

   private static void handleClientState(MyriadSwordsStatePayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         if (FMLEnvironment.dist == Dist.CLIENT) {
            MyriadSwordsNetwork.ClientHandler.handle(payload);
         }
      });
   }

   @OnlyIn(Dist.CLIENT)
   private static final class ClientHandler {
      private static void handle(MyriadSwordsStatePayload payload) {
         MyriadSwordsClientEffects.handleState(payload);
      }
   }
}
