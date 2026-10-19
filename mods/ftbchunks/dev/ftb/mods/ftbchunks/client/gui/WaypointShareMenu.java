package dev.ftb.mods.ftbchunks.client.gui;

import com.mojang.authlib.GameProfile;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.api.client.waypoint.Waypoint;
import dev.ftb.mods.ftbchunks.net.ShareWaypointPacket;
import dev.ftb.mods.ftblibrary.icon.FaceIcon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.NordButton;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractButtonListScreen;
import dev.ftb.mods.ftblibrary.ui.misc.SimpleToast;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.client.KnownClientPlayer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class WaypointShareMenu {
   public static Optional<ContextMenuItem> makeShareMenu(Player sharingPlayer, Waypoint waypoint) {
      List<ContextMenuItem> items = new ArrayList<>();
      if ((Boolean)FTBChunksWorldConfig.WAYPOINT_SHARING_SERVER.get()) {
         items.add(
            new ContextMenuItem(
               Component.translatable("ftbchunks.waypoint.share.server"),
               Icons.BEACON,
               b -> shareWaypoint(waypoint, ShareWaypointPacket.ShareType.SERVER, List.of())
            )
         );
      }

      if ((Boolean)FTBChunksWorldConfig.WAYPOINT_SHARING_PARTY.get()) {
         items.add(
            new ContextMenuItem(
               Component.translatable("ftbchunks.waypoint.share.party"),
               Icons.FRIENDS_GROUP,
               b -> shareWaypoint(waypoint, ShareWaypointPacket.ShareType.PARTY, List.of())
            )
         );
      }

      if ((Boolean)FTBChunksWorldConfig.WAYPOINT_SHARING_PLAYERS.get()) {
         items.add(
            new ContextMenuItem(
               Component.translatable("ftbchunks.waypoint.share.player"),
               Icons.PLAYER,
               b -> {
                  Collection<KnownClientPlayer> knownClientPlayers = FTBTeamsAPI.api().getClientManager().knownClientPlayers();
                  List<GameProfile> list = knownClientPlayers.stream()
                     .filter(KnownClientPlayer::online)
                     .filter(p -> !p.id().equals(sharingPlayer.getGameProfile().getId()))
                     .<GameProfile>map(KnownClientPlayer::profile)
                     .toList();
                  List<GameProfile> selectedProfiles = new ArrayList<>();
                  new WaypointShareMenu.ShareWaypointButtonList(selectedProfiles, waypoint, list).openGui();
               }
            )
         );
      }

      return items.isEmpty() ? Optional.empty() : Optional.of(ContextMenuItem.subMenu(Component.translatable("ftbchunks.waypoint.share"), Icons.INFO, items));
   }

   private static void shareWaypoint(Waypoint waypoint, ShareWaypointPacket.ShareType type, List<UUID> targets) {
      GlobalPos waypointPos = new GlobalPos(waypoint.getDimension(), waypoint.getPos());
      NetworkManager.sendToServer(new ShareWaypointPacket(waypoint.getName(), waypointPos, type, targets));
      SimpleToast.info(Component.translatable("ftbchunks.waypoint.shared_by_you", new Object[]{waypoint.getDisplayName()}), Component.empty());
   }

   private static class ShareWaypointButtonList extends AbstractButtonListScreen {
      private final List<GameProfile> selectedProfiles;
      private final Waypoint waypoint;
      private final List<GameProfile> gameProfiles;

      public ShareWaypointButtonList(List<GameProfile> selectedProfiles, Waypoint waypoint, List<GameProfile> gameProfiles) {
         this.selectedProfiles = selectedProfiles;
         this.waypoint = waypoint;
         this.gameProfiles = gameProfiles;
      }

      protected void doCancel() {
         this.closeGui();
      }

      protected void doAccept() {
         List<UUID> toShare = this.selectedProfiles.stream().<UUID>map(GameProfile::getId).toList();
         if (!toShare.isEmpty()) {
            WaypointShareMenu.shareWaypoint(this.waypoint, ShareWaypointPacket.ShareType.PLAYER, toShare);
         }

         this.closeGui();
      }

      public void addButtons(Panel panel) {
         for (final GameProfile gameProfile : this.gameProfiles) {
            final Component unchecked = Component.literal("☐ ").append(gameProfile.getName());
            final Component checked = Component.literal("☑ ").withStyle(ChatFormatting.GREEN).append(gameProfile.getName());
            NordButton widget = new NordButton(panel, unchecked, FaceIcon.getFace(gameProfile)) {
               public void onClicked(MouseButton button) {
                  if (ShareWaypointButtonList.this.selectedProfiles.contains(gameProfile)) {
                     ShareWaypointButtonList.this.selectedProfiles.remove(gameProfile);
                     this.title = unchecked;
                  } else {
                     ShareWaypointButtonList.this.selectedProfiles.add(gameProfile);
                     this.title = checked;
                  }

                  this.playClickSound();
               }
            };
            panel.add(widget);
         }
      }
   }
}
