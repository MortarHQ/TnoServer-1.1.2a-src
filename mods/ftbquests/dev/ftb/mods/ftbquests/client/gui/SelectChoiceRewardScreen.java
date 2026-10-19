package dev.ftb.mods.ftbquests.client.gui;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractButtonListScreen;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftblibrary.util.client.PositionedIngredient;
import dev.ftb.mods.ftbquests.net.ClaimChoiceRewardMessage;
import dev.ftb.mods.ftbquests.quest.loot.WeightedReward;
import dev.ftb.mods.ftbquests.quest.reward.ChoiceReward;
import java.util.Optional;
import net.minecraft.network.chat.Component;

public class SelectChoiceRewardScreen extends AbstractButtonListScreen {
   private final ChoiceReward choiceReward;
   private WeightedReward acceptedReward;

   public SelectChoiceRewardScreen(ChoiceReward choiceReward) {
      this.choiceReward = choiceReward;
      this.setTitle(Component.translatable("ftbquests.reward.ftbquests.choice"));
      this.setBorder(1, 1, 1);
      this.showBottomPanel(false);
      this.showCloseButton(true);
   }

   public void addButtons(Panel panel) {
      if (this.choiceReward.getTable() != null) {
         this.choiceReward.getTable().getWeightedRewards().forEach(wr -> panel.add(new SelectChoiceRewardScreen.ChoiceRewardButton(panel, wr)));
      }
   }

   public Theme getTheme() {
      return FTBQuestsTheme.INSTANCE;
   }

   protected void doCancel() {
      this.closeGui();
   }

   protected void doAccept() {
      this.closeGui();
      if (this.choiceReward.getTable() != null) {
         int idx = this.choiceReward.getTable().getWeightedRewards().indexOf(this.acceptedReward);
         NetworkManager.sendToServer(new ClaimChoiceRewardMessage(this.choiceReward.id, idx));
      }
   }

   private class ChoiceRewardButton extends SimpleTextButton {
      private final WeightedReward weightedReward;

      private ChoiceRewardButton(Panel panel, WeightedReward wr) {
         super(panel, wr.getReward().getTitle(), wr.getReward().getIcon());
         this.weightedReward = wr;
      }

      public void addMouseOverText(TooltipList list) {
         super.addMouseOverText(list);
         this.weightedReward.getReward().addMouseOverText(list);
      }

      public void onClicked(MouseButton button) {
         this.playClickSound();
         SelectChoiceRewardScreen.this.acceptedReward = this.weightedReward;
         SelectChoiceRewardScreen.this.doAccept();
      }

      public Optional<PositionedIngredient> getIngredientUnderMouse() {
         return PositionedIngredient.of(this.weightedReward.getReward().getIngredient(this), this);
      }
   }
}
