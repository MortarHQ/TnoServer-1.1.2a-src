package com.tensura_guild.compat.ftbquests;

import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.task.TaskType;

public class CompleteDwarfTask extends AbstractGuildEventTask {
   public CompleteDwarfTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return GuildFtbQuestTaskTypes.COMPLETE_DWARF_TASK;
   }
}
