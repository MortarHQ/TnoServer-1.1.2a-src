package com.natamus.collective.forge.networking;

import com.natamus.collective_common_forge.data.Constants;
import com.natamus.collective_common_forge.implementations.networking.PacketRegistrationHandler;
import com.natamus.collective_common_forge.implementations.networking.data.PacketContainer;
import com.natamus.collective_common_forge.implementations.networking.data.PacketContext;
import com.natamus.collective_common_forge.implementations.networking.data.Side;
import com.natamus.collective_common_forge.implementations.networking.exceptions.RegistrationException;
import io.netty.buffer.Unpooled;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent.Context;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.EventNetworkChannel;

public class ForgeNetworkHandler extends PacketRegistrationHandler {
   private final Map<Class<?>, ForgeNetworkHandler.Message<?>> CHANNELS = new HashMap<>();

   public ForgeNetworkHandler(Side side) {
      super(side);
   }

   @Override
   protected <T> void registerPacket(PacketContainer<T> container) {
      if (this.CHANNELS.get(container.classType()) == null) {
         EventNetworkChannel channel = ChannelBuilder.named(container.type().id()).optional().eventNetworkChannel().addListener(event -> {
            T message = container.decoder().apply(event.getPayload());
            this.buildHandler(container.handler()).accept(message, event.getSource());
         });
         this.CHANNELS.put(container.classType(), new ForgeNetworkHandler.Message<>(channel, container.encoder()));
      }
   }

   @Override
   public <T> void sendToServer(T packet) {
      this.sendToServer(packet, false);
   }

   @Override
   public <T> void sendToServer(T packet, boolean ignoreCheck) {
      ForgeNetworkHandler.Message<T> message = (ForgeNetworkHandler.Message<T>)this.CHANNELS.get(packet.getClass());
      if (message == null) {
         throw new RegistrationException(packet.getClass() + "{} packet not registered on the client, packets need to be registered on both sides!");
      }

      EventNetworkChannel channel = message.channel();
      Connection connection = Minecraft.getInstance().getConnection().getConnection();
      if (ignoreCheck || channel.isRemotePresent(connection)) {
         FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
         message.encoder().accept(packet, buf);
         channel.send(buf, connection);
      }
   }

   @Override
   public <T> void sendToClient(T packet, ServerPlayer player) {
      ForgeNetworkHandler.Message<T> message = (ForgeNetworkHandler.Message<T>)this.CHANNELS.get(packet.getClass());
      EventNetworkChannel channel = message.channel();
      Connection connection = player.connection.getConnection();
      if (message != null) {
         if (!channel.isRemotePresent(connection)) {
            throw new RegistrationException(packet.getClass() + "{} packet not registered on the server, packets need to be registered on both sides!");
         }

         FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
         message.encoder().accept(packet, buf);
         channel.send(buf, connection);
      }
   }

   private <T> BiConsumer<T, Context> buildHandler(Consumer<PacketContext<T>> handler) {
      return (message, ctx) -> ctx.enqueueWork(() -> {
         try {
            Side side = ctx.isServerSide() ? Side.SERVER : Side.CLIENT;
            ServerPlayer player = ctx.getSender();
            handler.accept(new PacketContext<>(player, (T)message, side));
            ctx.setPacketHandled(true);
         } catch (Throwable t) {
            Constants.LOG.error("{} error handling packet", message.getClass(), t);
         }
      });
   }

   public record Message<T>(EventNetworkChannel channel, BiConsumer<T, FriendlyByteBuf> encoder) {
   }
}
