package dev.ftb.mods.ftbteams.net;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftbteams.FTBTeams;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.TeamRank;
import dev.ftb.mods.ftbteams.api.client.KnownClientPlayer;
import dev.ftb.mods.ftbteams.data.PartyTeam;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerPlayer;

public record PlayerGUIOperationMessage(PlayerGUIOperationMessage.Operation op, List<UUID> targets) implements CustomPacketPayload {
   public static final Type<PlayerGUIOperationMessage> TYPE = new Type(FTBTeamsAPI.rl("player_gui_operation"));
   public static final StreamCodec<FriendlyByteBuf, PlayerGUIOperationMessage> STREAM_CODEC = StreamCodec.composite(
      NetworkHelper.enumStreamCodec(PlayerGUIOperationMessage.Operation.class),
      PlayerGUIOperationMessage::op,
      UUIDUtil.STREAM_CODEC.apply(ByteBufCodecs.list()),
      PlayerGUIOperationMessage::targets,
      PlayerGUIOperationMessage::new
   );

   public static PlayerGUIOperationMessage forUUID(PlayerGUIOperationMessage.Operation op, UUID target) {
      return new PlayerGUIOperationMessage(op, List.of(target));
   }

   public static PlayerGUIOperationMessage forGameProfiles(PlayerGUIOperationMessage.Operation op, Collection<GameProfile> targets) {
      return new PlayerGUIOperationMessage(op, targets.stream().<UUID>map(GameProfile::getId).toList());
   }

   public static void handle(PlayerGUIOperationMessage message, PacketContext context) {
      context.queue(() -> {
         if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
            UUID var4 = context.getPlayer().getUUID();
            FTBTeamsAPI.api().getManager().getTeamForPlayerID(var4).ifPresent(team -> {
               if (team instanceof PartyTeam partyTeam) {
                  TeamRank senderRank = partyTeam.getRankForPlayer(serverPlayer.getUUID());
                  message.targets.forEach(target -> processTarget(serverPlayer, senderRank, partyTeam, message.op, target));
               }
            });
         }
      });
   }

   private static void processTarget(ServerPlayer sourcePlayer, TeamRank senderRank, PartyTeam partyTeam, PlayerGUIOperationMessage.Operation op, UUID targetId) {
      if (!op.requireSameTeam() || FTBTeamsAPI.api().getManager().arePlayersInSameTeam(sourcePlayer.getUUID(), targetId)) {
         TeamRank targetRank = partyTeam.getRankForPlayer(targetId);
         FTBTeams.LOGGER
            .debug(
               "received teams operation msg {} from {} (rank {}), team {}, target {} (rank {})",
               op,
               sourcePlayer.getUUID(),
               senderRank,
               partyTeam.getName().getString(),
               targetId,
               targetRank
            );

         try {
            List<GameProfile> targetProfile = List.of(new GameProfile(targetId, ""));
            switch (op) {
               case PROMOTE:
                  if (senderRank.isAtLeast(TeamRank.OWNER) && targetRank.isAtLeast(TeamRank.MEMBER)) {
                     partyTeam.promote(sourcePlayer, targetProfile);
                  }
                  break;
               case DEMOTE:
                  if (senderRank.isAtLeast(TeamRank.OWNER) && targetRank.isAtLeast(TeamRank.OFFICER)) {
                     partyTeam.demote(sourcePlayer, targetProfile);
                  }
                  break;
               case LEAVE:
                  partyTeam.leave(sourcePlayer.getUUID());
                  break;
               case KICK:
                  if (senderRank.getPower() > targetRank.getPower()) {
                     partyTeam.kick(sourcePlayer.createCommandSourceStack(), targetProfile);
                  }
                  break;
               case TRANSFER_OWNER:
                  if (senderRank.isAtLeast(TeamRank.OWNER)) {
                     ServerPlayer p = sourcePlayer.getServer().getPlayerList().getPlayer(targetId);
                     if (p != null) {
                        partyTeam.transferOwnership(sourcePlayer.createCommandSourceStack(), p.getGameProfile());
                     }
                  }
                  break;
               case INVITE:
                  if (senderRank.isAtLeast(TeamRank.OFFICER)) {
                     ServerPlayer p = sourcePlayer.getServer().getPlayerList().getPlayer(targetId);
                     if (p != null) {
                        partyTeam.invite(sourcePlayer, List.of(p.getGameProfile()));
                     }
                  }
                  break;
               case ADD_ALLY:
                  if (senderRank.isAtLeast(TeamRank.OFFICER) && targetRank.isAtLeast(TeamRank.NONE)) {
                     partyTeam.addAlly(sourcePlayer.createCommandSourceStack(), targetProfile);
                  }
                  break;
               case REMOVE_ALLY:
                  if (senderRank.isAtLeast(TeamRank.OFFICER) && targetRank.isAtLeast(TeamRank.ALLY)) {
                     partyTeam.removeAlly(sourcePlayer.createCommandSourceStack(), targetProfile);
                  }
            }
         } catch (CommandSyntaxException e) {
            sourcePlayer.displayClientMessage(Component.literal(e.getMessage()).withStyle(ChatFormatting.RED), false);
         }
      }
   }

   public Type<PlayerGUIOperationMessage> type() {
      return TYPE;
   }

   public enum Operation {
      PROMOTE(true),
      DEMOTE(true),
      LEAVE(true),
      KICK(true),
      TRANSFER_OWNER(true),
      INVITE(false),
      ADD_ALLY(false),
      REMOVE_ALLY(false);

      private final boolean requireSameTeam;

      Operation(boolean requireSameTeam) {
         this.requireSameTeam = requireSameTeam;
      }

      boolean requireSameTeam() {
         return this.requireSameTeam;
      }

      public void sendMessage(KnownClientPlayer target) {
         NetworkManager.sendToServer(PlayerGUIOperationMessage.forUUID(this, target.id()));
      }
   }
}
