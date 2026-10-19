package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.client.fx.IWantLimboClient;
import com.github.wal_bos.moreskills.limbo.IWantLimboData;
import dev.architectury.networking.NetworkManager.PacketContext;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record IWantLimboRoomPayload(boolean active, double x, double y, double z, List<IWantLimboRoomPayload.CardView> cards) implements CustomPacketPayload {
   public static final Type<IWantLimboRoomPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "i_want_limbo_room"));
   public static final StreamCodec<FriendlyByteBuf, IWantLimboRoomPayload> STREAM_CODEC = StreamCodec.of(
      IWantLimboRoomPayload::write, IWantLimboRoomPayload::read
   );

   private static void write(FriendlyByteBuf buffer, IWantLimboRoomPayload payload) {
      buffer.writeBoolean(payload.active);
      buffer.writeDouble(payload.x);
      buffer.writeDouble(payload.y);
      buffer.writeDouble(payload.z);
      buffer.writeInt(payload.cards.size());

      for (IWantLimboRoomPayload.CardView card : payload.cards) {
         buffer.writeInt(card.id());
         buffer.writeEnum(card.rarity());
         buffer.writeDouble(card.x());
         buffer.writeDouble(card.y());
         buffer.writeDouble(card.z());
      }
   }

   private static IWantLimboRoomPayload read(FriendlyByteBuf buffer) {
      boolean active = buffer.readBoolean();
      double x = buffer.readDouble();
      double y = buffer.readDouble();
      double z = buffer.readDouble();
      int size = buffer.readInt();
      List<IWantLimboRoomPayload.CardView> cards = new ArrayList<>();

      for (int i = 0; i < size; i++) {
         cards.add(
            new IWantLimboRoomPayload.CardView(
               buffer.readInt(),
               (IWantLimboData.Rarity)buffer.readEnum(IWantLimboData.Rarity.class),
               buffer.readDouble(),
               buffer.readDouble(),
               buffer.readDouble()
            )
         );
      }

      return new IWantLimboRoomPayload(active, x, y, z, cards);
   }

   public static void handle(IWantLimboRoomPayload payload, PacketContext context) {
      context.queue(() -> IWantLimboClient.setRoom(payload));
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public record CardView(int id, IWantLimboData.Rarity rarity, double x, double y, double z) {
   }
}
