package org.crypticdev.stextras.integration.FTB.Quests.reward;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftbquests.net.NotifyRewardMessage;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.Stextras;
import org.crypticdev.stextras.integration.FTB.Quests.STExtrasRewardTypes;
import org.crypticdev.stextras.net.STNetworkGuards;
import org.crypticdev.stextras.quest.assignment.RequiredQuestSelector;
import org.crypticdev.stextras.storage.STExtarsStorage;

public class RequiredQuestRerollReward extends Reward {
   public RequiredQuestRerollReward(long id, Quest quest) {
      super(id, quest);
   }

   public RewardType getType() {
      return STExtrasRewardTypes.REQUIRED_QUEST_REROLL;
   }

   public void claim(ServerPlayer player, boolean notify) {
      int prestige = TensuraStorages.getPlayerDataFrom(player).getResetCounter();
      RequiredQuestSelector.applyRequiredOnCharacterReset(player, prestige);
      STExtarsStorage.Quests.get(player).syncAllTo(player);
      if (notify) {
         if (!STNetworkGuards.canSendTo(player)) {
            Stextras.LOGGER
               .warn(
                  "[NETWORK_GUARD] Skipped required quest reroll reward notification because player cannot receive packets | player={}",
                  STNetworkGuards.playerName(player)
               );
            return;
         }

         NetworkManager.sendToPlayer(player, new NotifyRewardMessage(this.id, this.getAltTitle(), Color4I.empty(), this.disableRewardScreenBlur));
      }
   }

   public Component getAltTitle() {
      return Component.translatable("ftbquests.reward.stextras.required_quest_reroll");
   }

   public String getButtonText() {
      return Component.translatable("ftbquests.reward.stextras.required_quest_reroll").getString();
   }
}
