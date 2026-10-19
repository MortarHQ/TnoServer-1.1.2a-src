package com.tensura_tno.ftb;

import com.tensura_tno.TensuraTNOMod;
import com.tensura_tno.ftb.tasks.STExtrasQuestTask;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.TeamData;
import net.minecraft.server.level.ServerPlayer;

public final class FTBQuestsResetHelper {
   public static void onQuestReroll(ServerPlayer player, boolean rerollDaily, boolean rerollWeekly) {
      try {
         ServerQuestFile sf = ServerQuestFile.INSTANCE;
         if (sf == null) {
            return;
         }

         TeamData teamData = TeamData.get(player);
         if (teamData == null) {
            return;
         }

         sf.getAllTasks().forEach(task -> {
            if (task instanceof STExtrasQuestTask stTask) {
               if (stTask.isAutoReset()) {
                  String cat = stTask.getCategory();
                  boolean matches = rerollDaily && "DAILY".equals(cat) || rerollWeekly && "WEEKLY".equals(cat);
                  if (matches) {
                     teamData.resetProgress(task);
                  }
               }
            }
         });
      } catch (Exception e) {
         TensuraTNOMod.LOGGER.warn("[TNO] FTBQuests 委托任务重置失败: {}", e.getMessage());
      }
   }

   private FTBQuestsResetHelper() {
   }
}
