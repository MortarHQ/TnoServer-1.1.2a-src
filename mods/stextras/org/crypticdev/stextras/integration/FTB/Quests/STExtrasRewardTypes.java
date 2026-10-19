package org.crypticdev.stextras.integration.FTB.Quests;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import dev.ftb.mods.ftbquests.quest.reward.RewardTypes;
import dev.ftb.mods.ftbquests.quest.reward.RewardType.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.integration.FTB.Quests.reward.ReincarnationEssenceReward;
import org.crypticdev.stextras.integration.FTB.Quests.reward.RequiredQuestRerollReward;
import org.crypticdev.stextras.integration.FTB.Quests.reward.SoulGradePointsReward;

public final class STExtrasRewardTypes {
   public static final RewardType REINCARNATION_ESSENCE = register(
      "reincarnation_essence", ReincarnationEssenceReward::new, "stextras:textures/ftb_visual/reincarnation-essence.png"
   );
   public static final RewardType SOUL_GRADE_POINTS = register("soul_grade_points", SoulGradePointsReward::new, "stextras:textures/ftb_visual/soul-grade.png");
   public static final RewardType REQUIRED_QUEST_REROLL = register("required_quest_reroll", RequiredQuestRerollReward::new, "minecraft:item/writable_book");

   private STExtrasRewardTypes() {
   }

   public static void init() {
   }

   private static RewardType register(String id, Provider provider, String icon) {
      return RewardTypes.register(ResourceLocation.fromNamespaceAndPath("stextras", id), provider, () -> Icon.getIcon(icon))
         .setDisplayName(Component.translatable("ftbquests.reward.stextras." + id));
   }
}
