package com.mortal_cultivation.myriadswords;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record MyriadSwordsStatePayload(UUID casterId, boolean active, int seed, long startGameTime, int targetEntityId) implements CustomPacketPayload {
   public static final Type<MyriadSwordsStatePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "myriad_swords_state"));
   public static final StreamCodec<FriendlyByteBuf, MyriadSwordsStatePayload> STREAM_CODEC = CustomPacketPayload.codec(
      MyriadSwordsStatePayload::write, MyriadSwordsStatePayload::new
   );

   private MyriadSwordsStatePayload(FriendlyByteBuf buffer) {
      this(buffer.readUUID(), buffer.readBoolean(), buffer.readInt(), buffer.readLong(), buffer.readInt());
   }

   private void write(FriendlyByteBuf buffer) {
      buffer.writeUUID(this.casterId);
      buffer.writeBoolean(this.active);
      buffer.writeInt(this.seed);
      buffer.writeLong(this.startGameTime);
      buffer.writeInt(this.targetEntityId);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
