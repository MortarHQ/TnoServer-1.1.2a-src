package com.tensura_guild.compat.ftbquests;

import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.Task;
import net.minecraft.server.level.ServerPlayer;

abstract class AbstractGuildEventTask extends Task {
   protected AbstractGuildEventTask(long id, Quest quest) {
      super(id, quest);
   }

   public long getMaxProgress() {
      return 1L;
   }

   public void recordCompletion(TeamData teamData, ServerPlayer player) {
      if (!teamData.isCompleted(this) && this.checkTaskSequence(teamData)) {
         teamData.setProgress(this, 1L);
      }
   }
}
