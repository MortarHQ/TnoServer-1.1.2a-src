package dev.ftb.mods.ftbteams.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.client.gui.MyTeamScreen;
import dev.ftb.mods.ftbteams.data.ClientTeamManagerImpl;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record SyncTeamsMessage(ClientTeamManagerImpl manager, UUID selfTeamID, boolean fullSync) implements CustomPacketPayload {
   public static final Type<SyncTeamsMessage> TYPE = new Type(FTBTeamsAPI.rl("sync_teams"));
   public static final StreamCodec<RegistryFriendlyByteBuf, SyncTeamsMessage> STREAM_CODEC = StreamCodec.composite(
      ClientTeamManagerImpl.STREAM_CODEC,
      SyncTeamsMessage::manager,
      UUIDUtil.STREAM_CODEC,
      SyncTeamsMessage::selfTeamID,
      ByteBufCodecs.BOOL,
      SyncTeamsMessage::fullSync,
      SyncTeamsMessage::new
   );

   public static void handle(SyncTeamsMessage message, PacketContext context) {
      context.queue(() -> {
         ClientTeamManagerImpl.syncFromServer(message.manager, message.selfTeamID, message.fullSync);
         MyTeamScreen.refreshIfOpen();
      });
   }

   public Type<SyncTeamsMessage> type() {
      return TYPE;
   }
}
