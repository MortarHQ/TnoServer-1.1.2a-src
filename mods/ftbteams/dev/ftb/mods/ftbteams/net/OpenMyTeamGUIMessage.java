package dev.ftb.mods.ftbteams.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyCollection;
import dev.ftb.mods.ftbteams.client.FTBTeamsClient;
import dev.ftb.mods.ftbteams.data.PlayerPermissions;
import dev.ftb.mods.ftbteams.data.TeamPropertyCollectionImpl;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record OpenMyTeamGUIMessage(TeamPropertyCollection properties, PlayerPermissions permissions) implements CustomPacketPayload {
   public static final Type<OpenMyTeamGUIMessage> TYPE = new Type(FTBTeamsAPI.rl("open_my_team_gui"));
   public static final StreamCodec<RegistryFriendlyByteBuf, OpenMyTeamGUIMessage> STREAM_CODEC = StreamCodec.composite(
      TeamPropertyCollectionImpl.STREAM_CODEC,
      OpenMyTeamGUIMessage::properties,
      PlayerPermissions.STREAM_CODEC,
      OpenMyTeamGUIMessage::permissions,
      OpenMyTeamGUIMessage::new
   );

   public static void handle(OpenMyTeamGUIMessage message, PacketContext context) {
      context.queue(() -> FTBTeamsClient.openMyTeamGui(message.properties, message.permissions));
   }

   public Type<OpenMyTeamGUIMessage> type() {
      return TYPE;
   }
}
