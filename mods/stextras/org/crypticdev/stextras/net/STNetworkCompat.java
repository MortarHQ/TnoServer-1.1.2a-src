package org.crypticdev.stextras.net;

import dev.architectury.impl.NetworkAggregator;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public final class STNetworkCompat {
   public static <T extends CustomPacketPayload> void registerS2CType(Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
      if (Platform.getEnvironment() == Env.CLIENT) {
         NetworkAggregator.S2C_CODECS.put(type.id(), castCodec(codec));
      } else {
         NetworkManager.registerS2CPayloadType(type, codec);
      }
   }

   private static StreamCodec<ByteBuf, ?> castCodec(StreamCodec<?, ?> codec) {
      return (StreamCodec<ByteBuf, ?>)codec;
   }

   private STNetworkCompat() {
   }
}
