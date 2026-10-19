package dev.ftb.mods.ftbultimine.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbultimine.FTBUltimine;
import dev.ftb.mods.ftbultimine.api.FTBUltimineAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerPlayer;

public record ModeChangedPacket(boolean next) implements CustomPacketPayload {
   public static final Type<ModeChangedPacket> TYPE = new Type(FTBUltimineAPI.id("mode_changed_packet"));
   public static final StreamCodec<FriendlyByteBuf, ModeChangedPacket> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.BOOL, ModeChangedPacket::next, ModeChangedPacket::new
   );

   public Type<ModeChangedPacket> type() {
      return TYPE;
   }

   public static void handle(ModeChangedPacket message, PacketContext context) {
      context.queue(() -> FTBUltimine.getInstance().modeChanged((ServerPlayer)context.getPlayer(), message.next));
   }
}
