package com.mortal_cultivation.entity;

import com.mortal_cultivation.client.vfx.QilinClientEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.MOD)
public final class QilinEffectNetwork {
   private QilinEffectNetwork() {
   }

   @SubscribeEvent
   public static void register(RegisterPayloadHandlersEvent event) {
      event.registrar("2").playToClient(QilinEffectPayload.TYPE, QilinEffectPayload.STREAM_CODEC, QilinEffectNetwork::handle);
   }

   private static void handle(QilinEffectPayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         if (FMLEnvironment.dist == Dist.CLIENT) {
            QilinEffectNetwork.ClientHandler.accept(payload);
         }
      });
   }

   @OnlyIn(Dist.CLIENT)
   private static final class ClientHandler {
      private static void accept(QilinEffectPayload payload) {
         QilinClientEffects.accept(payload);
      }
   }
}
