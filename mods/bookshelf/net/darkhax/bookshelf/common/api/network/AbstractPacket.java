package net.darkhax.bookshelf.common.api.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractPacket<T extends CustomPacketPayload> implements IPacket<T> {
   private final Type<T> type;
   private final StreamCodec<RegistryFriendlyByteBuf, T> codec;
   private final Destination direction;

   public AbstractPacket(ResourceLocation id, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
      this(id, codec, Destination.SERVER_TO_CLIENT);
   }

   public AbstractPacket(ResourceLocation id, StreamCodec<RegistryFriendlyByteBuf, T> codec, Destination direction) {
      this.type = new Type(id);
      this.codec = codec;
      this.direction = direction;
   }

   @Override
   public Type<T> type() {
      return this.type;
   }

   @Override
   public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
      return this.codec;
   }

   @Override
   public Destination destination() {
      return this.direction;
   }
}
