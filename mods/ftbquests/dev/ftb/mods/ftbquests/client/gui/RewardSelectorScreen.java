package dev.ftb.mods.ftbquests.client.gui;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleButton;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractGroupedButtonListScreen;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractButtonListScreen.ButtonPanel;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractGroupedButtonListScreen.GroupData;
import dev.ftb.mods.ftblibrary.util.TextComponentUtils;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import dev.ftb.mods.ftbquests.client.gui.quests.RewardButton;
import dev.ftb.mods.ftbquests.net.ClaimAllRewardsMessage;
import dev.ftb.mods.ftbquests.quest.Chapter;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardTypes;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

public class RewardSelectorScreen extends AbstractGroupedButtonListScreen<Chapter, Quest> {
   private static final int REFRESH_TIME = 80;
   private final TeamData selfData;
   private final QuestScreen questScreen;
   private int widestQuestName;
   private int maxRewardCount;
   private int totalRewards;
   private int xpRewards;
   private int excludedRewards;
   private int msgTimeout = 0;
   private Component message = Component.empty();
   private long rewardInfoGenerated;

   public RewardSelectorScreen(TeamData selfData, QuestScreen questScreen) {
      super(Component.translatable("ftbquests.gui.reward_selector"));
      this.selfData = selfData;
      this.questScreen = questScreen;
      this.showBottomPanel(true);
   }

   public void alignWidgets() {
      super.alignWidgets();
      ((ButtonPanel)this.mainPanel).getWidgets().forEach(widget -> {
         if (widget instanceof RewardSelectorScreen.RowPanel row) {
            row.alignWidgets();
         }
      });
   }

   protected List<GroupData<Chapter, Quest>> getGroups() {
      List<GroupData<Chapter, Quest>> groups = new ArrayList<>();
      Player player = Minecraft.getInstance().player;
      this.widestQuestName = this.maxRewardCount = this.excludedRewards = this.totalRewards = this.xpRewards = 0;
      if (player != null && ClientQuestFile.exists()) {
         TeamData data = ClientQuestFile.INSTANCE.selfTeamData;
         ClientQuestFile.INSTANCE.forAllChapters(chapter -> {
            List<Quest> quests = chapter.getQuests().stream().filter(quest -> quest.hasUnclaimedRewardsRaw(data, player.getUUID())).toList();
            if (!quests.isEmpty()) {
               groups.add(new GroupData(chapter, false, chapter.getTitle(), quests));
               quests.forEach(quest -> {
                  int unclaimed = 0;

                  for (Reward reward : quest.getRewards()) {
                     if (!data.isRewardClaimed(player.getUUID(), reward)) {
                        this.totalRewards++;
                        unclaimed++;
                        if (reward.getType() == RewardTypes.XP_LEVELS || reward.getType() == RewardTypes.XP) {
                           this.xpRewards++;
                        }
                     }

                     if (reward.getExcludeFromClaimAll()) {
                        this.excludedRewards++;
                     }
                  }

                  int width = Mth.clamp(this.getTheme().getStringWidth(quest.getTitle()), 100, 250);
                  this.widestQuestName = Math.max(this.widestQuestName, width);
                  this.maxRewardCount = Math.max(this.maxRewardCount, unclaimed);
               });
            }
         });
      }

      this.rewardInfoGenerated = Util.getEpochMillis();
      return groups;
   }

   protected AbstractGroupedButtonListScreen<Chapter, Quest>.RowPanel createRowPanel(Panel panel, Quest quest) {
      return new RewardSelectorScreen.RowPanel(panel, quest);
   }

   protected Panel createBottomPanel() {
      return new RewardSelectorScreen.CustomBottomPanel();
   }

   public boolean onInit() {
      this.setSize(
         Mth.clamp(this.widestQuestName + this.maxRewardCount * 20 + 50, 220, this.getWindow().getGuiScaledWidth() * 4 / 5),
         this.getWindow().getGuiScaledHeight() * 4 / 5
      );
      return true;
   }

   private void doClaimAll(boolean xpOnly) {
      NetworkManager.sendToServer(new ClaimAllRewardsMessage(xpOnly));
      this.message = xpOnly
         ? Component.translatable("ftbquests.gui.xp_rewards_claimed")
         : (
            this.excludedRewards > 0
               ? Component.translatable("ftbquests.gui.claim_all_exclusion", new Object[]{this.excludedRewards})
               : Component.translatable("ftbquests.gui.all_rewards_claimed")
         );
      this.msgTimeout = 80;
   }

   public Theme getTheme() {
      return FTBQuestsTheme.INSTANCE;
   }

   public void tick() {
      super.tick();
      if (this.msgTimeout > 0 && --this.msgTimeout == 0) {
         this.groups.clear();
         this.groups.addAll(this.getGroups());
         this.refreshWidgets();
      }
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      super.drawBackground(graphics, theme, x, y, w, h);
      theme.drawPanelBackground(graphics, x, y, w, h);
      if (this.msgTimeout > 0) {
         int barWidth = theme.getStringWidth(this.message) * this.msgTimeout / 80;
         Color4I.BLACK.withAlpha(60).draw(graphics, x - 1, y - theme.getFontHeight() - 4, barWidth, theme.getFontHeight() + 4);
         theme.drawString(graphics, this.message.copy().withStyle(ChatFormatting.YELLOW), x, y - theme.getFontHeight() - 2, 2);
      }
   }

   protected void doCancel() {
      this.closeGui(true);
   }

   private class CustomBottomPanel extends Panel {
      private Button buttonClaimAll;
      private Button buttonClaimXP;
      private final Button buttonClose = SimpleTextButton.create(
         this,
         Component.translatable("gui.close"),
         Icons.CANCEL,
         mb -> RewardSelectorScreen.this.doCancel(),
         new Component[]{TextComponentUtils.hotkeyTooltip("ESC")}
      );

      public CustomBottomPanel() {
         super(RewardSelectorScreen.this);
      }

      public void addWidgets() {
         Component[] tooltip = RewardSelectorScreen.this.excludedRewards > 0
            ? new Component[]{Component.translatable("ftbquests.gui.claim_all_exclusion", new Object[]{RewardSelectorScreen.this.excludedRewards})}
            : new Component[0];
         this.buttonClaimAll = SimpleTextButton.create(
            this, Component.translatable("ftbquests.reward.claim_all"), Icons.MONEY_BAG, mb -> RewardSelectorScreen.this.doClaimAll(false), tooltip
         );
         this.buttonClaimXP = SimpleTextButton.create(
            this,
            Component.translatable("ftbquests.reward.claim_xp"),
            ItemIcon.getItemIcon(Items.EXPERIENCE_BOTTLE),
            mb -> RewardSelectorScreen.this.doClaimAll(true),
            new Component[0]
         );
         this.add(this.buttonClose);
         if (RewardSelectorScreen.this.totalRewards - RewardSelectorScreen.this.excludedRewards > 0) {
            this.add(this.buttonClaimAll);
         }

         if (RewardSelectorScreen.this.xpRewards > 0) {
            this.add(this.buttonClaimXP);
         }
      }

      public void alignWidgets() {
         this.buttonClose.setPos(this.width - this.buttonClose.width - 5, 3);
         this.buttonClaimAll.setPos(this.buttonClose.posX - this.buttonClaimAll.width - 5, 3);
         this.buttonClaimXP.setPos(this.buttonClose.posX - this.buttonClaimAll.width - this.buttonClaimXP.width - 10, 3);
      }

      public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         theme.drawPanelBackground(graphics, x, y, w, h);
         Color4I.BLACK.withAlpha(80).draw(graphics, x, y + h - 1, w, 1);
      }
   }

   private class RowPanel extends AbstractGroupedButtonListScreen<Chapter, Quest>.RowPanel {
      public RowPanel(Panel parent, Quest value) {
         super(RewardSelectorScreen.this, parent, value);
      }

      public void addWidgets() {
         this.add(new TextField(this).setText(((Quest)this.value).getTitle()).setMaxWidth(250).addFlags(2));
         List<RewardButton> buttons = ((Quest)this.value)
            .getRewards()
            .stream()
            .filter(this::shouldShowButton)
            .map(reward -> new RewardButton(this, reward, RewardSelectorScreen.this.questScreen))
            .toList();
         if (buttons.isEmpty()) {
            this.add(new SimpleButton(this, Component.empty(), Icons.ACCEPT, (btn, mb) -> {}) {
               public void addMouseOverText(TooltipList list) {
                  list.add(Component.translatable("ftbquests.gui.all_rewards_claimed"));
               }
            });
         } else {
            this.addAll(buttons);
         }
      }

      private boolean shouldShowButton(Reward reward) {
         return switch (RewardSelectorScreen.this.selfData.getClaimType(Minecraft.getInstance().player.getUUID(), reward)) {
            case CANT_CLAIM -> false;
            case CLAIMED -> RewardSelectorScreen.this.selfData
               .getRewardClaimTime(Minecraft.getInstance().player.getUUID(), reward)
               .map(date -> date.getTime() > RewardSelectorScreen.this.rewardInfoGenerated)
               .orElse(false);
            case CAN_CLAIM -> true;
         };
      }

      public void alignWidgets() {
         int xPos = this.width - 22 - (RewardSelectorScreen.this.scrollBar.shouldDraw() ? RewardSelectorScreen.this.scrollBar.width : 0);
         int tallest = 0;

         for (Widget w : this.widgets) {
            if (w instanceof TextField) {
               w.setPosAndSize(18, (this.height - w.height) / 2, RewardSelectorScreen.this.widestQuestName, w.height);
            } else if (w instanceof Button) {
               w.setPosAndSize(xPos, (this.height - w.height) / 2, 16, 16);
               xPos -= 22;
            }

            tallest = Math.max(tallest, w.height);
         }

         this.setHeight(Math.max(this.height, tallest + 6));
      }

      public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         super.drawBackground(graphics, theme, x, y, w, h);
         ((Quest)this.value).getIcon().drawStatic(graphics, x + 2, y + (this.height - 12) / 2, 12, 12);
         graphics.hLine(x, x + w, y + h, 1082163328);
      }
   }
}
