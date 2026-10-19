package dev.ftb.mods.ftbultimine.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftbultimine.CooldownTracker;
import dev.ftb.mods.ftbultimine.api.FTBUltimineAPI;
import dev.ftb.mods.ftbultimine.client.FTBUltimineClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record SyncUltimineTimePacket(long when, SyncUltimineTimePacket.TimeType timetype) implements CustomPacketPayload {
   public static final Type<SyncUltimineTimePacket> TYPE = new Type(FTBUltimineAPI.id("sync_ultimine_time_packet"));
   public static final StreamCodec<FriendlyByteBuf, SyncUltimineTimePacket> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_LONG,
      SyncUltimineTimePacket::when,
      NetworkHelper.enumStreamCodec(SyncUltimineTimePacket.TimeType.class),
      SyncUltimineTimePacket::timetype,
      SyncUltimineTimePacket::new
   );

   public Type<SyncUltimineTimePacket> type() {
      return TYPE;
   }

   public static void handle(SyncUltimineTimePacket message, PacketContext context) {
      context.queue(() -> {
         switch (message.timetype) {
            case COOLDOWN:
               CooldownTracker.setClientCooldownTime(message.when);
               break;
            case LAST_USED:
               CooldownTracker.setLastUltimineTime(FTBUltimineClient.getClientPlayer(), message.when);
         }
      });
   }

   public enum TimeType {
      COOLDOWN,
      LAST_USED;
   }
}
