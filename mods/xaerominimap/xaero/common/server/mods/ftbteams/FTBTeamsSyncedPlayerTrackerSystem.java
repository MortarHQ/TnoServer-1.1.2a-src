package xaero.common.server.mods.ftbteams;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamRank;
import net.minecraft.world.entity.player.Player;
import xaero.common.server.radar.tracker.ISyncedPlayerTrackerSystem;

public class FTBTeamsSyncedPlayerTrackerSystem implements ISyncedPlayerTrackerSystem {
   @Override
   public int getTrackingLevel(Player tracker, Player tracked) {
      if (FTBTeamsAPI.api().getManager().arePlayersInSameTeam(tracker.getUUID(), tracked.getUUID())) {
         return 2;
      } else {
         Team trackerTeam = (Team)FTBTeamsAPI.api().getManager().getTeamForPlayerID(tracker.getUUID()).orElse(null);
         if (trackerTeam == null) {
            return 0;
         } else {
            Team trackedTeam = (Team)FTBTeamsAPI.api().getManager().getTeamForPlayerID(tracked.getUUID()).orElse(null);
            if (trackedTeam == null) {
               return 0;
            } else {
               return trackerTeam.getRankForPlayer(tracked.getUUID()) == TeamRank.ALLY && trackedTeam.getRankForPlayer(tracker.getUUID()) == TeamRank.ALLY
                  ? 1
                  : 0;
            }
         }
      }
   }

   @Override
   public boolean isPartySystem() {
      return true;
   }
}
