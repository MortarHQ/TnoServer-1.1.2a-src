package artifacts.neoforge.network;

import artifacts.network.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NeoForgeNetworkHandler {
   public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("1");

      for (NetworkHandler.PayloadHandler<?> payloadHandler : NetworkHandler.SERVERBOUND_HANDLERS) {
         if (NetworkHandler.CLIENTBOUND_HANDLERS.stream().anyMatch(p -> p.type() == payloadHandler.type())) {
            register(registrar::playBidirectional, payloadHandler);
         } else {
            register(registrar::playToServer, payloadHandler);
         }
      }

      for (NetworkHandler.PayloadHandler<?> payloadHandler : NetworkHandler.CLIENTBOUND_HANDLERS) {
         if (NetworkHandler.SERVERBOUND_HANDLERS.stream().noneMatch(p -> p.type() == payloadHandler.type())) {
            register(registrar::playToClient, payloadHandler);
         }
      }
   }

   private static <T extends CustomPacketPayload> void register(
      NeoForgeNetworkHandler.PayloadRegistration registration, NetworkHandler.PayloadHandler<T> payloadHandler
   ) {
      StreamCodec<? super FriendlyByteBuf, T> codec = payloadHandler.codec();
      registration.register(
         payloadHandler.type(),
         codec,
         (arg, context) -> payloadHandler.receiver().receive((T)arg, new NeoForgeNetworkHandler.NeoForgePayloadContext(context.player(), context))
      );
   }

   private record NeoForgePayloadContext(Player player, IPayloadContext context) implements NetworkHandler.PayloadContext {
      @Override
      public void queue(Runnable runnable) {
         this.context.enqueueWork(runnable);
      }
   }

   @FunctionalInterface
   private interface PayloadRegistration {
      <T extends CustomPacketPayload> void register(Type<T> var1, StreamCodec<? super FriendlyByteBuf, T> var2, IPayloadHandler<T> var3);
   }
}
