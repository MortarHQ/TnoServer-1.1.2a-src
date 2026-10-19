package com.tensura_guild.client;

import com.tensura_guild.client.screen.GuildPartyScreen;
import com.tensura_guild.data.GuildPartyState;
import com.tensura_guild.network.GuildPartyActionPayload;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;

public final class GuildPartyClient {
   private static GuildPartyState currentState = GuildPartyState.EMPTY;
   private static boolean hasSyncedState;

   private GuildPartyClient() {
   }

   public static void handleState(GuildPartyState state) {
      currentState = state == null ? GuildPartyState.EMPTY : state;
      hasSyncedState = true;
      if (Minecraft.getInstance().screen instanceof GuildPartyScreen screen) {
         screen.updateState(currentState);
      }
   }

   public static GuildPartyState currentState() {
      return currentState;
   }

   public static boolean hasSyncedState() {
      return hasSyncedState;
   }

   public static Optional<String> currentPartyName() {
      return currentState.inParty() && !currentState.partyName().isBlank() ? Optional.of(currentState.partyName()) : Optional.empty();
   }

   public static boolean isInParty() {
      return currentState.inParty();
   }

   public static int currentMemberCount() {
      return currentState.inParty() ? currentState.members().size() : 0;
   }

   public static boolean isPartyUndermanned() {
      return currentState.inParty() && currentState.members().size() <= 1;
   }

   public static boolean isLocalPlayerLeader() {
      Minecraft minecraft = Minecraft.getInstance();
      UUID playerId = minecraft.player == null ? null : minecraft.player.getUUID();
      return playerId != null && currentState.inParty() && playerId.equals(currentState.leaderId());
   }

   public static void requestSync() {
      if (Minecraft.getInstance().player != null) {
         PacketDistributor.sendToServer(GuildPartyActionPayload.requestSync(), new CustomPacketPayload[0]);
      }
   }
}
