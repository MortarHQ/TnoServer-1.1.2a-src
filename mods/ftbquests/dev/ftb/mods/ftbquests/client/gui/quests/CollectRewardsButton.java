package dev.ftb.mods.ftbquests.client.gui.quests;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbquests.client.gui.RewardSelectorScreen;
import dev.ftb.mods.ftbquests.quest.theme.property.ThemeProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class CollectRewardsButton extends TabButton {
   public CollectRewardsButton(Panel panel) {
      super(panel, Component.empty(), ThemeProperties.COLLECT_REWARDS_ICON.get());
      this.title = this.questScreen.file.getTitle();
   }

   public void onClicked(MouseButton button) {
      if (this.anyUnclaimedRewards()) {
         this.playClickSound();
         new RewardSelectorScreen(this.questScreen.file.selfTeamData, this.questScreen).openGui();
      }
   }

   public void addMouseOverText(TooltipList list) {
      if (this.anyUnclaimedRewards()) {
         list.translate("ftbquests.gui.reward_selector", new Object[0]);
      } else {
         list.translate("ftbquests.gui.no_rewards", new Object[0]);
      }
   }

   @Override
   public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      Color4I c = Color4I.WHITE.withAlpha(this.anyUnclaimedRewards() ? 255 : 128);
      this.icon.withColor(c).draw(graphics, x + (w - 16) / 2, y + (h - 16) / 2, 16, 16);
      if (this.isMouseOver()) {
         Color4I backgroundColor = ThemeProperties.WIDGET_BACKGROUND.get(this.questScreen.selectedChapter);
         backgroundColor.draw(graphics, x + 1, y, w - 2, h);
      }

      if (this.anyUnclaimedRewards()) {
         GuiHelper.setupDrawing();
         int s = w / 3 + 1;
         graphics.pose().pushPose();
         graphics.pose().translate(x + w - s, y, 200.0F);
         ThemeProperties.ALERT_ICON.get(this.questScreen.file).draw(graphics, 0, 0, s, s);
         graphics.pose().popPose();
      }
   }

   private boolean anyUnclaimedRewards() {
      return this.questScreen.file.selfTeamData.hasUnclaimedRewards(Minecraft.getInstance().player.getUUID(), this.questScreen.file);
   }
}
