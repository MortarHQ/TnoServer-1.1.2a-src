package com.tensura_tno.ftb.tasks;

import com.tensura_tno.ftb.STExtrasHelper;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.AbstractBooleanTask;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import io.github.manasmods.tensura.item.misc.ResetScrollItem;
import net.minecraft.server.level.ServerPlayer;

public class ResetCounterTask extends AbstractBooleanTask {
   public static TaskType RESET_COUNTER_TASK;

   public ResetCounterTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return RESET_COUNTER_TASK;
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      if (player == null || teamData == null || !this.checkTaskSequence(teamData)) {
         return false;
      } else {
         return STExtrasHelper.isLoaded() ? STExtrasHelper.canPrestige(player) : ResetScrollItem.isFullReset(player);
      }
   }

   public int autoSubmitOnPlayerTick() {
      return 60;
   }
}
