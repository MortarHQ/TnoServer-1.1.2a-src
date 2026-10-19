package fuzs.puzzleslib.impl.network;

import fuzs.puzzleslib.api.client.core.v1.ClientAbstractions;
import fuzs.puzzleslib.api.core.v1.ModContainer;
import fuzs.puzzleslib.api.core.v1.Proxy;
import fuzs.puzzleslib.api.network.v2.MessageV2;
import fuzs.puzzleslib.api.network.v3.ClientboundMessage;
import fuzs.puzzleslib.api.network.v3.MessageV3;
import fuzs.puzzleslib.api.network.v3.NetworkHandler;
import fuzs.puzzleslib.api.network.v3.PlayerSet;
import fuzs.puzzleslib.api.network.v3.ServerboundMessage;
import fuzs.puzzleslib.impl.network.codec.CustomPacketPayloadAdapter;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientCommonPacketListener;
import net.minecraft.network.protocol.common.ServerCommonPacketListener;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public abstract class NetworkHandlerRegistryImpl implements NetworkHandler.Builder {
   private final Map<Class<?>, Type<CustomPacketPayloadAdapter<?>>> messageTypes = new IdentityHashMap<>();
   private final Map<Class<?>, StreamDecoder<FriendlyByteBuf, ?>> clientboundMessages = new LinkedHashMap<>();
   private final Map<Class<?>, StreamDecoder<FriendlyByteBuf, ?>> serverboundMessages = new LinkedHashMap<>();
   protected final AtomicInteger discriminator = new AtomicInteger();
   protected final ResourceLocation channelName;
   protected boolean optional;

   protected NetworkHandlerRegistryImpl(ResourceLocation channelName) {
      this.channelName = channelName;
   }

   @Override
   public abstract <T> Packet<ClientCommonPacketListener> toClientboundPacket(ClientboundMessage<T> var1);

   @Override
   public abstract <T> Packet<ServerCommonPacketListener> toServerboundPacket(ServerboundMessage<T> var1);

   @Override
   public <T> void sendMessage(PlayerSet playerSet, ClientboundMessage<T> message) {
      Type<?> type = this.getMessageType(message);
      playerSet.broadcast(type, this.toClientboundPacket(message));
   }

   @Override
   public <T> void sendMessage(ServerboundMessage<T> message) {
      ClientPacketListener clientPacketListener = Proxy.INSTANCE.getClientPacketListener();
      Type<?> type = this.getMessageType(message);
      if (ClientAbstractions.INSTANCE.hasChannel(clientPacketListener, type)) {
         clientPacketListener.send(this.toServerboundPacket(message));
      }
   }

   @Override
   public <T extends Record & ClientboundMessage<T>> NetworkHandler.Builder registerClientbound(Class<T> clazz) {
      this.registerMessage(this.clientboundMessages, clazz, null);
      return this;
   }

   @Override
   public <T extends Record & ServerboundMessage<T>> NetworkHandler.Builder registerServerbound(Class<T> clazz) {
      this.registerMessage(this.serverboundMessages, clazz, null);
      return this;
   }

   @Override
   public <T extends MessageV2<T>> NetworkHandler.Builder registerLegacyClientbound(Class<T> clazz, StreamDecoder<FriendlyByteBuf, T> factory) {
      this.registerMessage(this.clientboundMessages, clazz, factory);
      return this;
   }

   @Override
   public <T extends MessageV2<T>> NetworkHandler.Builder registerLegacyServerbound(Class<T> clazz, StreamDecoder<FriendlyByteBuf, T> factory) {
      this.registerMessage(this.serverboundMessages, clazz, factory);
      return this;
   }

   private void registerMessage(Map<Class<?>, StreamDecoder<FriendlyByteBuf, ?>> messages, Class<?> clazz, @Nullable StreamDecoder<FriendlyByteBuf, ?> factory) {
      if (messages.containsKey(clazz)) {
         throw new IllegalStateException("Duplicate message of type " + clazz);
      }

      messages.put(clazz, factory);
   }

   @Override
   public NetworkHandler.Builder optional() {
      this.optional = true;
      return this;
   }

   @Override
   public void build() {
      for (Entry<Class<?>, StreamDecoder<FriendlyByteBuf, ?>> entry : this.clientboundMessages.entrySet()) {
         if (entry.getValue() != null) {
            this.registerLegacyClientbound$Internal(entry.getKey(), entry.getValue());
         } else {
            this.registerClientbound$Internal(entry.getKey());
         }
      }

      for (Entry<Class<?>, StreamDecoder<FriendlyByteBuf, ?>> entry : this.serverboundMessages.entrySet()) {
         if (entry.getValue() != null) {
            this.registerLegacyServerbound$Internal(entry.getKey(), entry.getValue());
         } else {
            this.registerServerbound$Internal(entry.getKey());
         }
      }

      this.clientboundMessages.clear();
      this.serverboundMessages.clear();
   }

   protected BiConsumer<Throwable, Consumer<Component>> disconnectExceptionally(Class<?> clazz) {
      return (throwable, consumer) -> {
         String modName = ModContainer.getDisplayName(this.channelName.getNamespace());
         consumer.accept(Component.literal("Receiving %s from %s failed: %s".formatted(clazz.getSimpleName(), modName, throwable.getMessage())));
      };
   }

   protected Consumer<Consumer<Component>> disconnectWrongSide(Class<?> clazz) {
      return consumer -> {
         String modName = ModContainer.getDisplayName(this.channelName.getNamespace());
         consumer.accept(Component.literal("Receiving %s from %s on wrong side!".formatted(clazz.getSimpleName(), modName)));
      };
   }

   protected <T> Type<CustomPacketPayloadAdapter<T>> registerMessageType(Class<T> clazz) {
      ResourceLocation resourceLocation = this.channelName.withPath(path -> path + "/" + this.discriminator.getAndIncrement());
      Type<CustomPacketPayloadAdapter<T>> type = new Type(resourceLocation);
      this.messageTypes.put(clazz, type);
      return type;
   }

   protected <T1 extends MessageV3<T2, ?>, T2, L extends PacketListener> Packet<L> toPacket(Function<CustomPacketPayload, Packet<L>> packetFactory, T1 message) {
      Type<CustomPacketPayloadAdapter<T2>> type = this.getMessageType(message);
      return packetFactory.apply(new CustomPacketPayloadAdapterImpl<>(type, message.unwrap()));
   }

   protected <T1 extends MessageV3<T2, ?>, T2> Type<CustomPacketPayloadAdapter<T2>> getMessageType(T1 message) {
      Class<T2> clazz = (Class<T2>)message.unwrap().getClass();
      Type<CustomPacketPayloadAdapter<T2>> type = (Type<CustomPacketPayloadAdapter<T2>>)this.messageTypes.get(clazz);
      Objects.requireNonNull(type, "Unknown message of type: " + clazz);
      return type;
   }

   protected abstract <T extends Record & ClientboundMessage<T>> void registerClientbound$Internal(Class<?> var1);

   protected abstract <T extends Record & ServerboundMessage<T>> void registerServerbound$Internal(Class<?> var1);

   protected abstract <T extends MessageV2<T>> void registerLegacyClientbound$Internal(Class<?> var1, StreamDecoder<FriendlyByteBuf, ?> var2);

   protected abstract <T extends MessageV2<T>> void registerLegacyServerbound$Internal(Class<?> var1, StreamDecoder<FriendlyByteBuf, ?> var2);
}
