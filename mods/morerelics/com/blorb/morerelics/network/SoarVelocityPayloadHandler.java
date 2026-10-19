package com.blorb.morerelics.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public class SoarVelocityPayloadHandler {
   public static final IPayloadHandler<SoarVelocityPayload> CLIENT_HANDLER = SoarVelocityPayloadHandler::handleClientSide;
   public static final IPayloadHandler<SoarVelocityPayload> SERVER_HANDLER = SoarVelocityPayloadHandler::handleServerSide;

   public static void handleClientSide(SoarVelocityPayload payload, IPayloadContext context) {
      context.player().setDeltaMovement(payload.x(), payload.y(), payload.z());
   }

   public static void handleServerSide(SoarVelocityPayload payload, IPayloadContext context) {
   }
}
