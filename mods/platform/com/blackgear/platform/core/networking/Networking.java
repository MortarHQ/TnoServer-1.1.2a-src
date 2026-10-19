package com.blackgear.platform.core.networking;

import com.blackgear.platform.core.networking.neoforge.NetworkingImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public class Networking {
   @ExpectPlatform
   @Transformed
   public static void register(Consumer<Networking.Registrar> listener) {
      NetworkingImpl.register(listener);
   }

   public interface Registrar {
      <T extends CustomPacketPayload> void registerToServer(
         Type<T> var1, StreamCodec<? super RegistryFriendlyByteBuf, T> var2, BiConsumer<T, PayloadContext> var3
      );

      <T extends CustomPacketPayload> void registerToClient(
         Type<T> var1, StreamCodec<? super RegistryFriendlyByteBuf, T> var2, BiConsumer<T, PayloadContext> var3
      );

      default <T extends CustomPacketPayload> void registerBidirectional(
         Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, BiConsumer<T, PayloadContext> handler
      ) {
         this.registerToServer(type, codec, handler);
         this.registerToClient(type, codec, handler);
      }
   }
}
