package dev.ftb.mods.ftbteams.api;

import dev.ftb.mods.ftbteams.api.event.TeamEvent;
import dev.ftb.mods.ftbteams.api.event.TeamPropertiesChangedEvent;
import dev.ftb.mods.ftbteams.api.property.TeamProperties;
import dev.ftb.mods.ftbteams.api.property.TeamPropertyCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class TeamStagesHelper {
   public static boolean addTeamStage(Team team, String stage) {
      return addTeamStages(team, List.of(stage)) == 1;
   }

   public static int addTeamStages(Team team, Collection<String> stages) {
      return updateStages(team, stages, true);
   }

   public static boolean removeTeamStage(Team team, String stage) {
      return removeTeamStages(team, List.of(stage)) == 1;
   }

   public static int removeTeamStages(Team team, Collection<String> stages) {
      return updateStages(team, stages, false);
   }

   public static boolean hasTeamStage(Team team, String stage) {
      return team.getProperty(TeamProperties.TEAM_STAGES).contains(stage);
   }

   public static boolean hasTeamStage(Player player, String stage) {
      return player instanceof ServerPlayer sp
         ? FTBTeamsAPI.api().getManager().getTeamForPlayer(sp).map(team -> team.getProperty(TeamProperties.TEAM_STAGES).contains(stage)).orElse(false)
         : FTBTeamsAPI.api()
            .getClientManager()
            .getTeamForPlayer(player)
            .map(team -> team.getProperty(TeamProperties.TEAM_STAGES).contains(stage))
            .orElse(false);
   }

   public static Collection<String> getStages(Team team) {
      return Collections.unmodifiableSet(team.getProperty(TeamProperties.TEAM_STAGES));
   }

   private static int updateStages(Team team, Collection<String> stages, boolean adding) {
      if (team.isClientTeam()) {
         return 0;
      }

      Set<String> stageSet = team.getProperty(TeamProperties.TEAM_STAGES);
      int changed = (int)stages.stream().filter(stage -> adding && stageSet.add(stage) || !adding && stageSet.remove(stage)).count();
      if (changed > 0) {
         TeamPropertyCollection old = team.getProperties().copy();
         team.setProperty(TeamProperties.TEAM_STAGES, stageSet);
         ((Consumer)TeamEvent.PROPERTIES_CHANGED.invoker()).accept(new TeamPropertiesChangedEvent(team, old));
         team.syncOnePropertyToTeam(TeamProperties.TEAM_STAGES, stageSet);
      }

      return changed;
   }
}
