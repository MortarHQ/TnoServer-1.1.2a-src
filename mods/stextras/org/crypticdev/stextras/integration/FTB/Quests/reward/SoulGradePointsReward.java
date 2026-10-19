package org.crypticdev.stextras.integration.FTB.Quests.reward;

import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.integration.FTB.Quests.STExtrasRewardTypes;
import org.crypticdev.stextras.storage.STExtarsStorage;

public class SoulGradePointsReward extends AbstractAmountReward {
   public SoulGradePointsReward(long id, Quest quest) {
      super(id, quest, 1);
   }

   public RewardType getType() {
      return STExtrasRewardTypes.SOUL_GRADE_POINTS;
   }

   public void claim(ServerPlayer player, boolean notify) {
      int current = STExtarsStorage.Player.getSoulGrade(player);
      STExtarsStorage.Player.setSoulGrade(player, current + this.amount);
      this.notifyPlayer(player, notify);
   }

   @Override
   protected String getTranslationKey() {
      return "ftbquests.reward.stextras.soul_grade_points";
   }
}
