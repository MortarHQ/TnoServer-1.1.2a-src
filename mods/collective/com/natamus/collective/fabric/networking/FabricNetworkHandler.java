package com.natamus.collective.fabric.networking;

import com.natamus.collective_common_fabric.data.Constants;
import com.natamus.collective_common_fabric.implementations.networking.PacketRegistrationHandler;
import com.natamus.collective_common_fabric.implementations.networking.data.CommonPacketWrapper;
import com.natamus.collective_common_fabric.implementations.networking.data.PacketContainer;
import com.natamus.collective_common_fabric.implementations.networking.data.PacketContext;
import com.natamus.collective_common_fabric.implementations.networking.data.Side;
import com.natamus.collective_common_fabric.implementations.networking.exceptions.RegistrationException;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_3222;

public class FabricNetworkHandler extends PacketRegistrationHandler {
   public FabricNetworkHandler(Side side) {
      super(side);
   }

   @Override
   protected <T> void registerPacket(PacketContainer<T> container) {
      try {
         PayloadTypeRegistry.playC2S().register(container.getType(), container.getCodec());
         PayloadTypeRegistry.playS2C().register(container.getType(), container.getCodec());
      } catch (IllegalArgumentException var3) {
      }

      if (Side.CLIENT.equals(this.side)) {
         Constants.LOG.debug("Registering packet {} : {} on the: {}", new Object[]{container.type().comp_2242(), container.classType(), Side.CLIENT});
         ClientPlayNetworking.registerGlobalReceiver(
            container.getType(),
            (payload, context) -> context.client().execute(() -> container.handler().accept(new PacketContext<>((T)payload.packet(), this.side)))
         );
      }

      Constants.LOG.debug("Registering packet {} : {} on the: {}", new Object[]{container.type().comp_2242(), container.classType(), Side.SERVER});
      ServerPlayNetworking.registerGlobalReceiver(
         container.getType(),
         (payload, context) -> context.player()
            .field_13995
            .execute(() -> container.handler().accept(new PacketContext<>(context.player(), (T)payload.packet(), this.side)))
      );
   }

   @Override
   public <T> void sendToServer(T packet) {
      this.sendToServer(packet, false);
   }

   @Override
   public <T> void sendToServer(T packet, boolean ignoreCheck) {
      PacketContainer<T> container = (PacketContainer<T>)this.PACKET_MAP.get(packet.getClass());
      if (container == null) {
         throw new RegistrationException(packet.getClass() + "{} packet not registered on the client, packets need to be registered on both sides!");
      }

      if (ignoreCheck || ClientPlayNetworking.canSend(container.type().comp_2242())) {
         ClientPlayNetworking.send(new CommonPacketWrapper<>(container, packet));
      }
   }

   @Override
   public <T> void sendToClient(T packet, class_3222 player) {
      PacketContainer<T> container = (PacketContainer<T>)this.PACKET_MAP.get(packet.getClass());
      if (container != null) {
         if (ServerPlayNetworking.canSend(player, container.type().comp_2242())) {
            ServerPlayNetworking.send(player, new CommonPacketWrapper<>(container, packet));
         }
      } else {
         throw new RegistrationException(packet.getClass() + "{} packet not registered on the server, packets need to be registered on both sides!");
      }
   }
}
