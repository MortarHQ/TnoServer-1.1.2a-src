package dev.ftb.mods.ftbquests.client;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.util.PanelPositioning;
import dev.ftb.mods.ftblibrary.util.PanelPositioning.PanelPos;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.Task;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

public enum PinnedQuestsTracker {
   INSTANCE;

   private static final int INTERVAL = 30;
   public static final int VERTICAL_SPACING = 3;
   private final List<FormattedCharSequence> pinnedQuestText = new ArrayList<>();
   private int ticker = 0;
   private boolean showChapterTitle;

   public void tick(ClientQuestFile file) {
      if (++this.ticker >= 30) {
         this.collectPinnedQuests(file);
         this.ticker = 0;
      }
   }

   public void refresh() {
      this.ticker = 30;
   }

   private void collectPinnedQuests(ClientQuestFile file) {
      TeamData data = file.selfTeamData;
      this.showChapterTitle = false;
      List<Quest> pinnedQuests = new ArrayList<>();
      LongSet pinnedIds = data.getPinnedQuestIds(FTBQuestsClient.getClientPlayer());
      if (!pinnedIds.isEmpty()) {
         if (pinnedIds.contains(1L)) {
            boolean wholeBook = FTBQuestsClientConfig.AUTO_PIN_FOLLOWS.get() == AutoPinTarget.QUEST_BOOK;
            file.forAllQuests(
               quest -> {
                  if (!data.isCompleted(quest)
                     && quest.isVisible(data)
                     && data.canStartTasks(quest)
                     && (wholeBook || file.isChapterSelected(quest.getChapter()))) {
                     pinnedQuests.add(quest);
                  }
               }
            );
            this.showChapterTitle = !wholeBook;
         } else {
            pinnedIds.longStream().mapToObj(file::getQuest).filter(Objects::nonNull).forEach(pinnedQuests::add);
         }
      }

      this.rebuildPinnedText(pinnedQuests, Minecraft.getInstance(), data);
   }

   private void rebuildPinnedText(List<Quest> pinnedQuests, Minecraft mc, TeamData data) {
      this.pinnedQuestText.clear();

      for (int i = 0; i < pinnedQuests.size(); i++) {
         Quest quest = pinnedQuests.get(i);
         if (i > 0) {
            this.pinnedQuestText.add(FormattedCharSequence.EMPTY);
         }

         this.pinnedQuestText
            .addAll(
               mc.font
                  .split(
                     FormattedText.composite(
                        new FormattedText[]{
                           mc.font.getSplitter().headByWidth(quest.getTitle(), 160, Style.EMPTY.withBold(true)),
                           Component.literal(" ").withStyle(ChatFormatting.DARK_AQUA).append(data.getRelativeProgress(quest) + "%")
                        }
                     ),
                     500
                  )
            );

         for (Task task : quest.getTasks()) {
            if (!data.isCompleted(task)) {
               this.pinnedQuestText
                  .addAll(
                     mc.font
                        .split(
                           FormattedText.composite(
                              new FormattedText[]{
                                 Component.literal("└").withStyle(ChatFormatting.GRAY),
                                 mc.font
                                    .getSplitter()
                                    .headByWidth(task.getMutableTitle().withStyle(ChatFormatting.GRAY), 160, Style.EMPTY.applyFormat(ChatFormatting.GRAY)),
                                 Component.literal(" ")
                                    .withStyle(ChatFormatting.GREEN)
                                    .append(task.formatProgress(data, data.getProgress(task)))
                                    .append("/")
                                    .append(task.formatMaxProgress())
                              }
                           ),
                           500
                        )
                  );
            }
         }
      }
   }

   void render(Minecraft mc, GuiGraphics graphics) {
      if (!this.pinnedQuestText.isEmpty()) {
         MutableComponent title = Component.translatable("ftbquests.pinned");
         if (this.showChapterTitle) {
            ClientQuestFile.INSTANCE
               .getQuestScreen()
               .flatMap(QuestScreen::getSelectedChapter)
               .ifPresent(chapter -> title.append(": ").append(chapter.getTitle()));
         }

         int titleWidth = mc.font.width(title);
         int width = titleWidth + 5;
         int height = 9 + 10;

         for (FormattedCharSequence s : this.pinnedQuestText) {
            width = Math.max(width, (int)mc.font.getSplitter().stringWidth(s));
            height += s == FormattedCharSequence.EMPTY ? 3 : 9;
         }

         width += 8;
         float scale = ((Double)FTBQuestsClientConfig.PINNED_QUESTS_SCALE.get()).floatValue();
         int insetX = (Integer)FTBQuestsClientConfig.PINNED_QUESTS_INSET_X.get();
         int insetY = (Integer)FTBQuestsClientConfig.PINNED_QUESTS_INSET_Y.get();
         PanelPos pos = ((PanelPositioning)FTBQuestsClientConfig.PINNED_QUESTS_POS.get())
            .getPanelPos(mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight(), (int)(width * scale), (int)(height * scale), insetX, insetY);
         graphics.pose().pushPose();
         graphics.pose().translate(pos.x(), pos.y(), 100.0F);
         graphics.pose().scale(scale, scale, 1.0F);
         GuiHelper.drawHollowRect(graphics, 0, 0, width, height, Color4I.BLACK.withAlpha(100), false);
         Color4I.BLACK.withAlpha(100).draw(graphics, 0, 0, width, height);
         Color4I.GRAY.withAlpha(50).draw(graphics, 1, 1, width - 2, 9 + 4);
         Color4I.BLACK.draw(graphics, 0, 9 + 4, width, 1);
         graphics.drawString(mc.font, title, (width - titleWidth) / 2, 4, -256);
         int yPos = 9 + 8;

         for (FormattedCharSequence fcs : this.pinnedQuestText) {
            if (fcs == FormattedCharSequence.EMPTY) {
               yPos += 3;
            } else {
               graphics.drawString(mc.font, fcs, 4, yPos, -1);
               yPos += 9;
            }
         }

         graphics.pose().popPose();
      }
   }
}
