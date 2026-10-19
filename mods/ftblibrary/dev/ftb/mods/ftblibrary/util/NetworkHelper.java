package dev.ftb.mods.ftblibrary.util;

import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function8;
import com.mojang.datafixers.util.Function9;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.NetworkReceiver;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.ftb.mods.ftblibrary.util.neoforge.NetworkHelperImpl;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class NetworkHelper {
   public static <T extends CustomPacketPayload> void registerC2S(
      Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, NetworkReceiver<T> handler
   ) {
      NetworkManager.registerReceiver(NetworkManager.c2s(), type, codec, handler);
   }

   public static <T extends CustomPacketPayload> void registerS2C(
      Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, NetworkReceiver<T> handler
   ) {
      if (Platform.getEnvironment() == Env.CLIENT) {
         NetworkManager.registerReceiver(NetworkManager.s2c(), type, codec, handler);
      } else {
         NetworkManager.registerS2CPayloadType(type, codec);
      }
   }

   @ExpectPlatform
   @Transformed
   public static void sendToAll(Type<?> type, MinecraftServer server, Packet<?> packet) {
      NetworkHelperImpl.sendToAll(type, server, packet);
   }

   @ExpectPlatform
   @Transformed
   public static void sendTo(Type<?> type, ServerPlayer player, Packet<?> packet) {
      NetworkHelperImpl.sendTo(type, player, packet);
   }

   @ExpectPlatform
   @Transformed
   public static <T extends CustomPacketPayload> void sendToAll(MinecraftServer server, T packet) {
      NetworkHelperImpl.sendToAll(server, packet);
   }

   @ExpectPlatform
   @Transformed
   public static <T extends CustomPacketPayload> void sendTo(ServerPlayer player, T packet) {
      NetworkHelperImpl.sendTo(player, packet);
   }

   public static <B extends FriendlyByteBuf, V extends Enum<V>> StreamCodec<B, V> enumStreamCodec(Class<V> enumClass) {
      return new StreamCodec<B, V>() {
         public V decode(B buf) {
            return (V)buf.readEnum(enumClass);
         }

         public void encode(B buf, V value) {
            buf.writeEnum(value);
         }
      };
   }

   public static <B, C, T1, T2, T3, T4, T5, T6, T7> StreamCodec<B, C> composite(
      StreamCodec<? super B, T1> codec1,
      Function<C, T1> getter1,
      StreamCodec<? super B, T2> codec2,
      Function<C, T2> getter2,
      StreamCodec<? super B, T3> codec3,
      Function<C, T3> getter3,
      StreamCodec<? super B, T4> codec4,
      Function<C, T4> getter4,
      StreamCodec<? super B, T5> codec5,
      Function<C, T5> getter5,
      StreamCodec<? super B, T6> codec6,
      Function<C, T6> getter6,
      StreamCodec<? super B, T7> codec7,
      Function<C, T7> getter7,
      Function7<T1, T2, T3, T4, T5, T6, T7, C> factory
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B buf) {
            T1 t1 = (T1)codec1.decode(buf);
            T2 t2 = (T2)codec2.decode(buf);
            T3 t3 = (T3)codec3.decode(buf);
            T4 t4 = (T4)codec4.decode(buf);
            T5 t5 = (T5)codec5.decode(buf);
            T6 t6 = (T6)codec6.decode(buf);
            T7 t7 = (T7)codec7.decode(buf);
            return (C)factory.apply(t1, t2, t3, t4, t5, t6, t7);
         }

         public void encode(B buf, C object) {
            codec1.encode(buf, getter1.apply(object));
            codec2.encode(buf, getter2.apply(object));
            codec3.encode(buf, getter3.apply(object));
            codec4.encode(buf, getter4.apply(object));
            codec5.encode(buf, getter5.apply(object));
            codec6.encode(buf, getter6.apply(object));
            codec7.encode(buf, getter7.apply(object));
         }
      };
   }

   public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8> StreamCodec<B, C> composite(
      StreamCodec<? super B, T1> codec1,
      Function<C, T1> getter1,
      StreamCodec<? super B, T2> codec2,
      Function<C, T2> getter2,
      StreamCodec<? super B, T3> codec3,
      Function<C, T3> getter3,
      StreamCodec<? super B, T4> codec4,
      Function<C, T4> getter4,
      StreamCodec<? super B, T5> codec5,
      Function<C, T5> getter5,
      StreamCodec<? super B, T6> codec6,
      Function<C, T6> getter6,
      StreamCodec<? super B, T7> codec7,
      Function<C, T7> getter7,
      StreamCodec<? super B, T8> codec8,
      Function<C, T8> getter8,
      Function8<T1, T2, T3, T4, T5, T6, T7, T8, C> factory
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B buf) {
            T1 t1 = (T1)codec1.decode(buf);
            T2 t2 = (T2)codec2.decode(buf);
            T3 t3 = (T3)codec3.decode(buf);
            T4 t4 = (T4)codec4.decode(buf);
            T5 t5 = (T5)codec5.decode(buf);
            T6 t6 = (T6)codec6.decode(buf);
            T7 t7 = (T7)codec7.decode(buf);
            T8 t8 = (T8)codec8.decode(buf);
            return (C)factory.apply(t1, t2, t3, t4, t5, t6, t7, t8);
         }

         public void encode(B buf, C object) {
            codec1.encode(buf, getter1.apply(object));
            codec2.encode(buf, getter2.apply(object));
            codec3.encode(buf, getter3.apply(object));
            codec4.encode(buf, getter4.apply(object));
            codec5.encode(buf, getter5.apply(object));
            codec6.encode(buf, getter6.apply(object));
            codec7.encode(buf, getter7.apply(object));
            codec8.encode(buf, getter8.apply(object));
         }
      };
   }

   public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9> StreamCodec<B, C> composite(
      StreamCodec<? super B, T1> codec1,
      Function<C, T1> getter1,
      StreamCodec<? super B, T2> codec2,
      Function<C, T2> getter2,
      StreamCodec<? super B, T3> codec3,
      Function<C, T3> getter3,
      StreamCodec<? super B, T4> codec4,
      Function<C, T4> getter4,
      StreamCodec<? super B, T5> codec5,
      Function<C, T5> getter5,
      StreamCodec<? super B, T6> codec6,
      Function<C, T6> getter6,
      StreamCodec<? super B, T7> codec7,
      Function<C, T7> getter7,
      StreamCodec<? super B, T8> codec8,
      Function<C, T8> getter8,
      StreamCodec<? super B, T9> codec9,
      Function<C, T9> getter9,
      Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, C> factory
   ) {
      return new StreamCodec<B, C>() {
         public C decode(B buf) {
            T1 t1 = (T1)codec1.decode(buf);
            T2 t2 = (T2)codec2.decode(buf);
            T3 t3 = (T3)codec3.decode(buf);
            T4 t4 = (T4)codec4.decode(buf);
            T5 t5 = (T5)codec5.decode(buf);
            T6 t6 = (T6)codec6.decode(buf);
            T7 t7 = (T7)codec7.decode(buf);
            T8 t8 = (T8)codec8.decode(buf);
            T9 t9 = (T9)codec9.decode(buf);
            return (C)factory.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
         }

         public void encode(B buf, C object) {
            codec1.encode(buf, getter1.apply(object));
            codec2.encode(buf, getter2.apply(object));
            codec3.encode(buf, getter3.apply(object));
            codec4.encode(buf, getter4.apply(object));
            codec5.encode(buf, getter5.apply(object));
            codec6.encode(buf, getter6.apply(object));
            codec7.encode(buf, getter7.apply(object));
            codec8.encode(buf, getter8.apply(object));
            codec9.encode(buf, getter9.apply(object));
         }
      };
   }
}
