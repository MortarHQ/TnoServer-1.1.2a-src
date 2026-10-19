package com.teamresourceful.resourcefullib.common.network.defaults;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.StreamCodecByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;

public abstract class CodecPacketType<T extends Packet<T>> extends AbstractPacketType<T> {
   protected StreamCodec<RegistryFriendlyByteBuf, T> codec;

   public CodecPacketType(ResourceLocation id, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
      super(id);
      this.codec = codec;
   }

   @Deprecated
   @ScheduledForRemoval(inVersion = "22.0")
   public CodecPacketType(Class<T> clazz, ResourceLocation id, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
      super(clazz, id);
      this.codec = codec;
   }

   public CodecPacketType(ResourceLocation id, ByteCodec<T> codec) {
      this(id, StreamCodecByteCodec.toRegistry(codec));
   }

   @Deprecated
   @ScheduledForRemoval(inVersion = "22.0")
   public CodecPacketType(Class<T> clazz, ResourceLocation id, ByteCodec<T> codec) {
      this(clazz, id, StreamCodecByteCodec.toRegistry(codec));
   }

   @Override
   public void encode(T message, RegistryFriendlyByteBuf buffer) {
      this.codec.encode(buffer, message);
   }

   @Override
   public T decode(RegistryFriendlyByteBuf buffer) {
      return (T)this.codec.decode(buffer);
   }

   public abstract static class Client<T extends Packet<T>> extends CodecPacketType<T> implements ClientboundPacketType<T> {
      public Client(ResourceLocation id, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
         super(id, codec);
      }

      public Client(ResourceLocation id, ByteCodec<T> codec) {
         super(id, codec);
      }

      public static <T extends Packet<T>> CodecPacketType.Client<T> create(
         ResourceLocation id, StreamCodec<RegistryFriendlyByteBuf, T> codec, Function<T, Runnable> handler
      ) {
         return new CodecPacketType.Client<T>(id, codec) {
            @Override
            public Runnable handle(T message) {
               return handler.apply(message);
            }
         };
      }

      public static <T extends Packet<T>> CodecPacketType.Client<T> create(ResourceLocation id, ByteCodec<T> codec, Function<T, Runnable> handler) {
         return new CodecPacketType.Client<T>(id, codec) {
            @Override
            public Runnable handle(T message) {
               return handler.apply(message);
            }
         };
      }
   }

   public abstract static class Server<T extends Packet<T>> extends CodecPacketType<T> implements ServerboundPacketType<T> {
      public Server(ResourceLocation id, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
         super(id, codec);
      }

      public Server(ResourceLocation id, ByteCodec<T> codec) {
         super(id, codec);
      }

      public static <T extends Packet<T>> CodecPacketType.Server<T> create(
         ResourceLocation id, StreamCodec<RegistryFriendlyByteBuf, T> codec, Function<T, Consumer<Player>> handler
      ) {
         return new CodecPacketType.Server<T>(id, codec) {
            @Override
            public Consumer<Player> handle(T message) {
               return handler.apply(message);
            }
         };
      }

      public static <T extends Packet<T>> CodecPacketType.Server<T> create(ResourceLocation id, ByteCodec<T> codec, Function<T, Consumer<Player>> handler) {
         return new CodecPacketType.Server<T>(id, codec) {
            @Override
            public Consumer<Player> handle(T message) {
               return handler.apply(message);
            }
         };
      }
   }
}
