package org.crypticdev.stextras.integration.FTB.Quests.task;

import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.integration.FTB.Quests.FTBQuestsEventBridge;
import org.crypticdev.stextras.integration.FTB.Quests.STExtrasTaskTypes;

public class PrestigeTask extends AbstractSTExtrasTask {
   public PrestigeTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return STExtrasTaskTypes.PRESTIGE;
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      return this.matchesAnyMember(teamData, player, FTBQuestsEventBridge::isPrestigeSubmitting);
   }

   public boolean checkOnLogin() {
      return false;
   }
}
