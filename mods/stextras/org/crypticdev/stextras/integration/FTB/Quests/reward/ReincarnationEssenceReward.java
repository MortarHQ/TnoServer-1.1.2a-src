package org.crypticdev.stextras.integration.FTB.Quests.reward;

import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.integration.FTB.Quests.STExtrasRewardTypes;
import org.crypticdev.stextras.storage.STExtarsStorage;

public class ReincarnationEssenceReward extends AbstractAmountReward {
   public ReincarnationEssenceReward(long id, Quest quest) {
      super(id, quest, 100);
   }

   public RewardType getType() {
      return STExtrasRewardTypes.REINCARNATION_ESSENCE;
   }

   public void claim(ServerPlayer player, boolean notify) {
      int current = STExtarsStorage.Player.getReincarnationEssence(player);
      STExtarsStorage.Player.setReincarnationEssence(player, current + this.amount);
      this.notifyPlayer(player, notify);
   }

   @Override
   protected String getTranslationKey() {
      return "ftbquests.reward.stextras.reincarnation_essence";
   }
}
