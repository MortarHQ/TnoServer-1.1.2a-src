package com.tensura_tno.ftb;

import com.tensura_tno.ftb.rewards.ReincarnationEssenceReward;
import com.tensura_tno.ftb.rewards.SoulEnergyReward;
import com.tensura_tno.ftb.rewards.SoulGradeReward;
import com.tensura_tno.ftb.tasks.ReincarnationEssenceTask;
import com.tensura_tno.ftb.tasks.ResetCounterTask;
import com.tensura_tno.ftb.tasks.STExtrasQuestTask;
import com.tensura_tno.ftb.tasks.SoulGradeTask;
import com.tensura_tno.ftb.tasks.TaskTableItemTask;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftbquests.quest.reward.RewardTypes;
import dev.ftb.mods.ftbquests.quest.task.TaskTypes;
import net.minecraft.resources.ResourceLocation;

public final class TNOFtbQuests {
   public static final String MOD_ID = "tensura_tno";

   public static void init() {
      ReincarnationEssenceTask.RE_TASK = TaskTypes.register(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "re_value"), ReincarnationEssenceTask::new, Icon::empty
      );
      STExtrasQuestTask.STEXTRAS_QUEST = TaskTypes.register(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "stextras_quest"), STExtrasQuestTask::new, Icon::empty
      );
      SoulGradeTask.SOUL_GRADE_TASK = TaskTypes.register(ResourceLocation.fromNamespaceAndPath("tensura_tno", "soul_grade"), SoulGradeTask::new, Icon::empty);
      ResetCounterTask.RESET_COUNTER_TASK = TaskTypes.register(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "reset_counter"),
         ResetCounterTask::new,
         () -> Icon.getIcon("tensura:textures/gui/reset_medals/stone_reset_medal.png")
      );
      TaskTableItemTask.TASK_TABLE_ITEM_TASK = TaskTypes.register(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "task_table_item"),
         TaskTableItemTask::new,
         () -> Icon.getIcon("ftblibrary:textures/icons/money_bag.png")
      );
      ReincarnationEssenceReward.RE_REWARD = RewardTypes.register(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "re_value"), ReincarnationEssenceReward::new, Icon::empty
      );
      SoulGradeReward.SOUL_GRADE_REWARD = RewardTypes.register(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "soul_grade"), SoulGradeReward::new, Icon::empty
      );
      SoulEnergyReward.SOUL_ENERGY_REWARD = RewardTypes.register(
         ResourceLocation.fromNamespaceAndPath("tensura_tno", "soul_energy"), SoulEnergyReward::new, Icon::empty
      );
   }

   private TNOFtbQuests() {
   }
}
