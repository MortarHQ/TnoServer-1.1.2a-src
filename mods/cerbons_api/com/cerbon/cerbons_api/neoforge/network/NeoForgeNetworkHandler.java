package com.cerbon.cerbons_api.neoforge.network;

import com.cerbon.cerbons_api.api.network.PacketRegistrationHandler;
import com.cerbon.cerbons_api.api.network.data.CommonPacketWrapper;
import com.cerbon.cerbons_api.api.network.data.PacketContainer;
import com.cerbon.cerbons_api.api.network.data.PacketContext;
import com.cerbon.cerbons_api.api.network.data.Side;
import com.cerbon.cerbons_api.api.network.exceptions.RegistrationException;
import com.cerbon.cerbons_api.util.Constants;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public class NeoForgeNetworkHandler extends PacketRegistrationHandler {
   public NeoForgeNetworkHandler(Side side) {
      super(side);
   }

   @Override
   protected <T> void registerPacket(PacketContainer<T> container) {
   }

   @SubscribeEvent
   public void register(RegisterPayloadHandlersEvent event) {
      if (!this.PACKET_MAP.isEmpty()) {
         this.PACKET_MAP
            .forEach(
               (type, container) -> event.registrar(container.getType().id().getNamespace())
                  .optional()
                  .commonBidirectional(container.getType(), container.getCodec(), this.buildHandler(container.handler()))
            );
      }
   }

   @Override
   public <T> void sendToServer(T packet, boolean ignoreCheck) {
      PacketContainer<T> container = (PacketContainer<T>)this.PACKET_MAP.get(packet.getClass());
      if (container == null) {
         throw new RegistrationException(packet.getClass() + "{} packet not registered on the client, packets need to be registered on both sides!");
      }

      if (ignoreCheck || Minecraft.getInstance().getConnection().hasChannel(container.getType())) {
         Minecraft.getInstance().getConnection().send(new ServerboundCustomPayloadPacket(new CommonPacketWrapper<>(container, packet)));
      }
   }

   @Override
   public <T> void sendToClient(T packet, ServerPlayer player, boolean ignoreCheck) {
      PacketContainer<T> container = (PacketContainer<T>)this.PACKET_MAP.get(packet.getClass());
      if (container == null) {
         throw new RegistrationException(packet.getClass() + "{} packet not registered on the server, packets need to be registered on both sides!");
      }

      if (ignoreCheck || player.connection.hasChannel(container.type())) {
         PacketDistributor.sendToPlayer(player, new CommonPacketWrapper<>(container, packet), new CustomPacketPayload[0]);
      }
   }

   private <T, K extends CommonPacketWrapper<T>> IPayloadHandler<K> buildHandler(Consumer<PacketContext<T>> handler) {
      return (payload, ctx) -> {
         try {
            Side side = ctx.flow().getReceptionSide().equals(LogicalSide.SERVER) ? Side.SERVER : Side.CLIENT;
            if (Side.SERVER.equals(side)) {
               handler.accept(new PacketContext<>((ServerPlayer)ctx.player(), (T)payload.packet(), side));
            } else {
               handler.accept(new PacketContext<>((T)payload.packet(), side));
            }
         } catch (Throwable t) {
            Constants.LOGGER.error("Error handling packet: {} -> ", payload.packet().getClass(), t);
         }
      };
   }
}
