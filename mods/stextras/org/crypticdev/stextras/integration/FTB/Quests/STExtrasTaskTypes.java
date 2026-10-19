package org.crypticdev.stextras.integration.FTB.Quests;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import dev.ftb.mods.ftbquests.quest.task.TaskTypes;
import dev.ftb.mods.ftbquests.quest.task.TaskType.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.integration.FTB.Quests.task.LockSkillTask;
import org.crypticdev.stextras.integration.FTB.Quests.task.PrestigeTask;
import org.crypticdev.stextras.integration.FTB.Quests.task.RacePrestigeTask;
import org.crypticdev.stextras.integration.FTB.Quests.task.ReachPrestigeLevelTask;
import org.crypticdev.stextras.integration.FTB.Quests.task.ReachSoulGradeTask;
import org.crypticdev.stextras.integration.FTB.Quests.task.STExtrasQuestCompleteTask;

public final class STExtrasTaskTypes {
   public static final TaskType PRESTIGE = register("prestige", PrestigeTask::new, "tensura:item/character_reset_scroll");
   public static final TaskType REACH_PRESTIGE_LEVEL = register(
      "reach_prestige_level", ReachPrestigeLevelTask::new, "tensura:textures/gui/reset_medals/hihiirokane_reset_medal.png"
   );
   public static final TaskType REACH_SOUL_GRADE = register("reach_soul_grade", ReachSoulGradeTask::new, "stextras:textures/ftb_visual/soul-grade.png");
   public static final TaskType RACE_PRESTIGE = register("race_prestige", RacePrestigeTask::new, "tensura:item/race_reset_scroll");
   public static final TaskType LOCK_SKILL = register(
      "lock_skill", LockSkillTask::new, Icon.getIcon("stextras:textures/gui/skill/skill_lock_button.png").withUV(0.0F, 0.0F, 16.0F, 17.0F, 16.0F, 34.0F)
   );
   public static final TaskType STEXTRAS_QUEST_COMPLETE = register("stextras_quest_complete", STExtrasQuestCompleteTask::new, "minecraft:item/book");

   private STExtrasTaskTypes() {
   }

   public static void init() {
   }

   private static TaskType register(String id, Provider provider, String icon) {
      return register(id, provider, Icon.getIcon(icon));
   }

   private static TaskType register(String id, Provider provider, Icon icon) {
      return TaskTypes.register(ResourceLocation.fromNamespaceAndPath("stextras", id), provider, () -> icon)
         .setDisplayName(Component.translatable("ftbquests.task.stextras." + id));
   }
}
