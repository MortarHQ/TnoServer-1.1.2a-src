package cn.leolezury.eternalstarlight.neoforge.network;

import cn.leolezury.eternalstarlight.common.handler.ESCommonSetupHandler;
import cn.leolezury.eternalstarlight.common.network.ESPackets;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = "eternal_starlight")
public class ESNeoNetworkHandler {
   @SubscribeEvent
   public static void onNetworkInit(RegisterPayloadHandlersEvent event) {
      final PayloadRegistrar registrar = event.registrar("eternal_starlight").optional();
      ESCommonSetupHandler.registerPackets(
         new ESCommonSetupHandler.NetworkRegisterStrategy() {
            @Override
            public <T extends CustomPacketPayload> void register(ESPackets.PacketInfo<T> packetInfo) {
               registrar.playBidirectional(
                  packetInfo.type(),
                  packetInfo.streamCodec(),
                  (packet, context) -> context.enqueueWork(() -> packetInfo.handler().handle((T)packet, context.player()))
               );
            }
         }
      );
   }

   public static void sendToClient(ServerPlayer serverPlayer, CustomPacketPayload packet) {
      PacketDistributor.sendToPlayer(serverPlayer, packet, new CustomPacketPayload[0]);
   }

   public static void sendToServer(CustomPacketPayload packet) {
      PacketDistributor.sendToServer(packet, new CustomPacketPayload[0]);
   }
}
