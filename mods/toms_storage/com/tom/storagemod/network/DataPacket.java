package com.tom.storagemod.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record DataPacket(CompoundTag tag) implements CustomPacketPayload {
   public static final Type<DataPacket> ID = new Type(ResourceLocation.tryBuild("toms_storage", "data"));
   public static final StreamCodec<FriendlyByteBuf, DataPacket> STREAM_CODEC = CustomPacketPayload.codec(DataPacket::write, DataPacket::new);

   private DataPacket(FriendlyByteBuf pb) {
      this((CompoundTag)pb.readNbt(NbtAccounter.unlimitedHeap()));
   }

   private void write(FriendlyByteBuf pb) {
      pb.writeNbt(this.tag);
   }

   public Type<DataPacket> type() {
      return ID;
   }
}
