package org.crypticdev.stextras.client.screen;

import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.tensura.client.screen.templates.IScrollBar;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import org.crypticdev.stextras.client.screen.prestige.STPrestigeProgress;
import org.crypticdev.stextras.client.screen.prestige.STPrestigeQuestFormatter;
import org.crypticdev.stextras.net.C2S.OpenPrestigeScreenPacket;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.client.ClientSTExtrasQuests;
import org.crypticdev.stextras.utils.PrestigeUtils;
import org.jetbrains.annotations.Nullable;

public class STPrestigeScreens extends SimpleScreen implements IScrollBar {
   protected ITensuraPlayer tensuraPlayerData;
   protected Races raceData;
   protected IExistence existenceData;
   private static int PROGRESS_IN_PERCENT = 0;
   private final int NeededTextOffsetX = -9;
   private final int NeededTextOffsetY = -2;
   public static final Component TITLE = Component.translatable("stextras.prestige_menu");
   public static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("stextras", "textures/gui/prestige_screen/st_prestige_gui.png");
   public static final ResourceLocation RACE_PRES_ASSET = ResourceLocation.fromNamespaceAndPath(
      "stextras", "textures/gui/prestige_screen/race_prestige/race_prestige_section.png"
   );
   public static final ResourceLocation RACE_PRESS_BAR = ResourceLocation.fromNamespaceAndPath(
      "stextras", "textures/gui/prestige_screen/race_prestige/race_prestige_bar.png"
   );
   private STPrestigeScreens.QuestTab selectedTab = STPrestigeScreens.QuestTab.DAILY;
   private final List<ResourceLocation> questIds = new ArrayList<>();
   private ResourceLocation selectedQuest;
   private int questListStartIndex = 0;
   private float questScrollOffset = 0.0F;
   private boolean questScrolling = false;
   private static final int QUEST_ROWS_VISIBLE = 3;
   private static final int QUEST_ROW_H = 13;
   private static final int QUEST_VIEW_X = 6;
   private static final int QUEST_VIEW_Y = 118;
   private static final int QUEST_VIEW_W = 89;
   private static final int SBAR_X = 98;
   private static final int SBAR_Y = 119;
   private static final int SBAR_TRACK_H = 39;
   private long stxDailyLeftSec = -1L;
   private long stxWeeklyLeftSec = -1L;
   private long stxLastTimerMs = 0L;
   private static final int DAILY_INDEX = 0;
   private static final int WEEKLY_INDEX = 1;
   private final List<ResourceLocation> raceIds = new ArrayList<>();
   private static final int RACE_ROW_H = 13;
   private static final int RACE_ROWS_VISIBLE = 6;
   private static final int RACE_VIEW_X = 123;
   private static final int RACE_VIEW_Y = 52;
   private static final int RACE_VIEW_W = 84;
   private static final int RACE_SBAR_X = 210;
   private static final int RACE_SBAR_Y = 53;
   private static final int RACE_SBAR_TRACK_H = 78;
   private int raceListStartIndex = 0;
   private float raceScrollOffset = 0.0F;
   private boolean raceScrolling = false;
   private final IScrollBar raceBar = new IScrollBar() {
      public int getScrollBarX() {
         return STPrestigeScreens.guiLeft + 210;
      }

      public int getScrollBarY() {
         return STPrestigeScreens.guiTop + 53;
      }

      public int getScrollBarTotalSpace() {
         return 78;
      }

      public int getScrollBarListSize() {
         return STPrestigeScreens.this.raceIds.size();
      }

      public int getScrollBarRenderCount() {
         return 6;
      }

      public float getScrollOffset() {
         return STPrestigeScreens.this.raceScrollOffset;
      }

      public boolean isScrolling() {
         return STPrestigeScreens.this.raceScrolling;
      }

      public void setListStartIndex(int value) {
         int maxStart = Math.max(0, STPrestigeScreens.this.raceIds.size() - 6);
         STPrestigeScreens.this.raceListStartIndex = Mth.clamp(value, 0, maxStart);
      }

      public void setScrolling(boolean value) {
         STPrestigeScreens.this.raceScrolling = value;
      }

      public void setScrollOffset(float value) {
         STPrestigeScreens.this.raceScrollOffset = Mth.clamp(value, 0.0F, 1.0F);
      }
   };
   int thight = 14;
   private static final float QUEST_TEXT_SCALE = 0.78F;

   public STPrestigeScreens() {
      super(TITLE, 256, 163);
      this.shouldRenderWidgets = true;
      this.tensuraPlayerData = TensuraStorages.getPlayerDataFrom(this.player);
      this.existenceData = TensuraStorages.getExistenceFrom(this.player);
      this.raceData = RaceAPI.getRaceFrom(this.player);
   }

   protected void init() {
      super.init();
      List<SimpleButton> tabs = ScreenHelper.getMenuTabs(-5, guiLeft, guiTop);
      SimpleButton returnButton = new SimpleButton(
         guiLeft + 5,
         guiTop + 28,
         18,
         10,
         null,
         null,
         Component.translatable("tooltip.tensura.return"),
         self -> ScreenHelper.clicked(SoundEvents.UI_BUTTON_CLICK, () -> ScreenHelper.openScreen(4))
      );
      tabs.forEach(x$0 -> {
         SimpleButton var10000 = (SimpleButton)this.addRenderableWidget(x$0);
      });
      this.addRenderableWidget(returnButton);
      this.syncLocalSingleplayerQuestState();
      OpenPrestigeScreenPacket.sendToServer();
      this.rebuildQuestListForTab();
      this.rebuildRaceList();
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      this.refreshSyncedQuestViews();
      graphics.drawString(this.font, TITLE, guiLeft + 105, guiTop + 10, 16777215);
      graphics.blit(BACKGROUND, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
      this.renderRacePrestige(graphics, mouseX, mouseY);
      if (!this.selectedTab.equals(STPrestigeScreens.QuestTab.RACE)) {
         this.renderNeededRedMarkers(graphics);
         this.renderNeededGreenMarkers(graphics);
         this.renderNeededText(graphics, mouseX, mouseY);
      }

      this.renderQuests(graphics, mouseX, mouseY);
      this.renderScrollBar(graphics, mouseX, mouseY);
      if (this.selectedTab == STPrestigeScreens.QuestTab.RACE) {
         this.raceBar.renderScrollBar(graphics, mouseX, mouseY);
         this.renderRaceProgressBar(graphics, mouseX, mouseY);
      } else {
         this.renderProgressBar(graphics, mouseX, mouseY);
      }

      this.renderQuestCategories(graphics, mouseX, mouseY);
      super.renderWidgets(graphics, mouseX, mouseY, partialTick);
      ScreenHelper.renderTabIcon(graphics, this, 4, guiLeft, guiTop, mouseX, mouseY);
   }

   private void renderRacePrestige(GuiGraphics graphics, int mouseX, int mouseY) {
      if (this.selectedTab.equals(STPrestigeScreens.QuestTab.RACE)) {
         ResourceLocation currentRace = ((ManasRaceInstance)this.raceData.getRace().get()).getRaceId();
         graphics.blit(RACE_PRES_ASSET, guiLeft + 120, guiTop + 33, 0.0F, 0.0F, 103, 104, 103, 104);
         if (this.raceIds.isEmpty()) {
            this.rebuildRaceList();
         }

         Set<ResourceLocation> completedRaceIds = STExtarsStorage.Quests.completedRacesClient();
         List<ResourceLocation> visibleRaceIds = this.buildVisibleRaceList(currentRace);
         int end = Math.min(this.raceListStartIndex + 6, visibleRaceIds.size());

         for (int row = 0; row < 6; row++) {
            int barX = guiLeft + 123;
            int barY = guiTop + 52 + row * 13;
            boolean isHovered = mouseX >= barX && mouseX <= barX + 84 && mouseY >= barY && mouseY <= barY + 13;
            int idx = this.raceListStartIndex + row;
            if (idx < visibleRaceIds.size()) {
               ResourceLocation rid = visibleRaceIds.get(idx);
               boolean completed = completedRaceIds.contains(rid);
               boolean isCurrentRace = rid.equals(currentRace);
               boolean isTensura = rid.getNamespace().equals("tensura");
               boolean isSTExtras = rid.getNamespace().equals("stextras");
               int barVOffset = isHovered ? 13 : 0;
               int nameColor;
               if (isCurrentRace) {
                  nameColor = 5635925;
                  graphics.blit(RACE_PRESS_BAR, barX, barY, 0.0F, isHovered ? 13.0F : 0.0F, 84, 12, 84, 26);
               } else if (isTensura) {
                  nameColor = 5614335;
                  graphics.blit(RACE_PRESS_BAR, barX, barY, 0.0F, isHovered ? 13.0F : 0.0F, 84, 12, 84, 26);
               } else if (isSTExtras) {
                  nameColor = 16766720;
                  graphics.blit(RACE_PRESS_BAR, barX, barY, 0.0F, isHovered ? 13.0F : 0.0F, 84, 12, 84, 26);
               } else {
                  nameColor = completed ? 11184810 : 16777215;
                  graphics.blit(RACE_PRESS_BAR, barX, barY, 0.0F, isHovered ? 13.0F : 0.0F, 84, 12, 84, 26);
               }

               String name = STPrestigeQuestFormatter.prettyRaceName(rid);
               String right = completed ? "✓" : "";
               int rightW = this.font.width(right);
               int textBaseY = barY + 3;
               int leftPad = 3;
               int rightPad = 3;
               int leftMaxPx = 84 - leftPad - rightPad - (rightW > 0 ? rightW + 3 : 0);
               String trimmed = STPrestigeQuestFormatter.trimToWidthScaled(this.font, name, leftMaxPx, 1.0F);
               int rightColor = completed ? 8978312 : 14540253;
               graphics.drawString(this.font, Component.literal(trimmed), barX + leftPad, textBaseY, nameColor, false);
               if (!right.isEmpty()) {
                  graphics.drawString(this.font, Component.literal(right), barX + 84 - rightPad - rightW, textBaseY, rightColor, false);
               }

               if (isHovered) {
                  List<QuestDefinition> raceQuests = STExtarsStorage.Quests.raceQuestsClient(rid);
                  Set<ResourceLocation> completedRaceQuestIds = STExtarsStorage.Quests.completedClient(QuestCategory.RACE_PRESTIGE);
                  this.setTooltipForNextRenderPass(STPrestigeQuestFormatter.buildRaceTooltip(rid, completed, raceQuests, completedRaceQuestIds));
               }
            }
         }
      }
   }

   private List<ResourceLocation> buildVisibleRaceList(ResourceLocation currentRace) {
      if (currentRace != null && this.raceIds.contains(currentRace)) {
         List<ResourceLocation> ordered = new ArrayList<>(this.raceIds.size());
         ordered.add(currentRace);

         for (ResourceLocation raceId : this.raceIds) {
            if (!raceId.equals(currentRace)) {
               ordered.add(raceId);
            }
         }

         return ordered;
      } else {
         return this.raceIds;
      }
   }

   private void rebuildRaceList() {
      this.raceIds.clear();
      Set<ResourceLocation> all = STExtarsStorage.Quests.allRaceIdsClient();
      this.raceIds
         .addAll(
            all.stream()
               .sorted(Comparator.comparingInt(this::raceNamespaceOrder).thenComparing(ResourceLocation::getNamespace).thenComparing(ResourceLocation::getPath))
               .toList()
         );
      int maxStart = Math.max(0, this.raceIds.size() - 6);
      if (this.raceListStartIndex > maxStart) {
         this.raceListStartIndex = maxStart;
      }
   }

   private void refreshSyncedQuestViews() {
      this.rebuildQuestListForTab();
      this.clampQuestScroll();
      if (this.selectedTab == STPrestigeScreens.QuestTab.RACE) {
         this.rebuildRaceList();
         this.clampRaceScroll();
      }
   }

   private void syncLocalSingleplayerQuestState() {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player != null && minecraft.hasSingleplayerServer()) {
         IntegratedServer server = minecraft.getSingleplayerServer();
         if (server != null) {
            UUID playerId = minecraft.player.getUUID();
            server.execute(() -> {
               if (server.getPlayerList().getPlayer(playerId) instanceof ISTExtrasQuests questsAccess) {
                  CompoundTag questData = questsAccess.stextras$getQuests().toNet();
                  minecraft.execute(() -> {
                     if (minecraft.player instanceof ClientSTExtrasQuests clientQuests) {
                        clientQuests.stextras$clientQuests().fromNet(questData);
                     }
                  });
               }
            });
         }
      }
   }

   private int raceNamespaceOrder(ResourceLocation raceId) {
      String namespace = raceId.getNamespace();
      if ("tensura".equals(namespace)) {
         return 0;
      } else if ("stextras".equals(namespace)) {
         return 1;
      } else {
         return "mysticism".equals(namespace) ? 3 : 2;
      }
   }

   private void resetRaceScroll() {
      this.raceListStartIndex = 0;
   }

   private boolean inRaceViewport(double mX, double mY) {
      int x1 = guiLeft + 123;
      int y1 = guiTop + 52;
      int x2 = x1 + 84;
      int y2 = y1 + 78;
      return mX >= x1 && mX <= x2 && mY >= y1 && mY <= y2;
   }

   private void scrollRaceList(double deltaY) {
      int dir = deltaY < 0.0 ? 1 : -1;
      int maxStart = Math.max(0, this.raceIds.size() - 6);
      this.raceListStartIndex = Math.max(0, Math.min(maxStart, this.raceListStartIndex + dir));
   }

   private void renderNeededText(GuiGraphics gfx, int mouseX, int mouseY) {
      float neededTextScale = 0.8F;
      int neededRE = PrestigeUtils.getReincarnationEssenceCost(this.tensuraPlayerData.getResetCounter());
      int neededBosses = PrestigeUtils.requiredBossKills(this.tensuraPlayerData.getResetCounter());
      Set<ResourceLocation> resourceLocations = STExtarsStorage.Quests.getClient().completedAll();

      for (int i = 0; i < 5; i++) {
         int baseX = guiLeft + 143 + -9;
         int baseY = guiTop + 55 + i * 18 + -2;
         Component label;
         Component hover;
         if (i == 2) {
            label = Component.translatable("stextras.prestige_menu.needed_3", new Object[]{STExtarsStorage.Player.getReincarnationEssence(), neededRE});
            hover = Component.translatable("stextras.prestige_menu.needed_3_hover", new Object[]{STExtarsStorage.Player.getReincarnationEssence(), neededRE});
         } else if (i == 3) {
            label = Component.translatable("stextras.prestige_menu.needed_4", new Object[]{STExtarsStorage.Client.bossKills(), neededBosses});
            hover = Component.translatable("stextras.prestige_menu.needed_4_hover", new Object[]{STExtarsStorage.Client.bossKills(), neededBosses});
         } else {
            label = Component.translatable("stextras.prestige_menu.needed_" + (i + 1));
            hover = Component.translatable("stextras.prestige_menu.needed_" + (i + 1) + "_hover");
         }

         gfx.pose().pushPose();
         gfx.pose().translate(baseX, baseY, 0.0F);
         gfx.pose().scale(neededTextScale, neededTextScale, 1.0F);
         gfx.drawString(this.font, label, 0, 0, 16777215, false);
         gfx.pose().popPose();
         int width = (int)(this.font.width(label) * neededTextScale);
         int height = (int)(9.0F * neededTextScale);
         if (mouseX >= baseX && mouseX <= baseX + width && mouseY >= baseY && mouseY <= baseY + height) {
            this.setTooltipForNextRenderPass(hover);
         }
      }
   }

   private void renderNeededGreenMarkers(GuiGraphics gfx) {
      boolean[] completed = STPrestigeProgress.computeConditions(this.player, this.raceData, this.existenceData, this.tensuraPlayerData);

      for (int i = 0; i < 5; i++) {
         if (completed[i]) {
            int x = guiLeft + 135 + -9;
            int y = guiTop + 51 + i * 18 + -2;
            gfx.blit(BACKGROUND, x, y, 98.0F, 164.0F, 4, 14, 256, 256);
         }
      }
   }

   private void renderNeededRedMarkers(GuiGraphics gfx) {
      boolean[] completed = STPrestigeProgress.computeConditions(this.player, this.raceData, this.existenceData, this.tensuraPlayerData);

      for (int i = 0; i < 5; i++) {
         if (!completed[i]) {
            int x = guiLeft + 135 + -9;
            int y = guiTop + 51 + i * 18 + -2;
            gfx.blit(BACKGROUND, x, y, 93.0F, 164.0F, 4, 14, 256, 256);
         }
      }
   }

   private void renderProgressBar(GuiGraphics gfx, int mouseX, int mouseY) {
      int x = guiLeft + 125;
      int y = guiTop + 142;
      STPrestigeProgress.BarState barState = STPrestigeProgress.prestigeProgress(this.player, this.raceData, this.existenceData, this.tensuraPlayerData);
      int fill = barState.fillPixels();
      PROGRESS_IN_PERCENT = barState.percent();
      if (fill >= 92) {
         gfx.blit(BACKGROUND, x, y, 0.0F, 171.0F, 92, 6, 256, 256);
      } else {
         gfx.blit(BACKGROUND, x, y, 0.0F, 164.0F, fill, 6, 256, 256);
      }

      if (mouseX >= x && mouseX <= x + 92 && mouseY >= y && mouseY <= y + 6) {
         Component tooltip = Component.translatable("stextras.prestige_menu.progress_hover", new Object[]{PROGRESS_IN_PERCENT});
         this.setTooltipForNextRenderPass(tooltip);
      }
   }

   private void renderRaceProgressBar(GuiGraphics gfx, int mouseX, int mouseY) {
      int x = guiLeft + 125;
      int y = guiTop + 142;
      STPrestigeProgress.BarState barState = STPrestigeProgress.raceProgress(this.player, this.raceData, this.existenceData, this.tensuraPlayerData);
      int fill = barState.fillPixels();
      PROGRESS_IN_PERCENT = barState.percent();
      if (fill >= 92) {
         gfx.blit(BACKGROUND, x, y, 0.0F, 185.0F, 92, 6, 256, 256);
      } else {
         gfx.blit(BACKGROUND, x, y, 0.0F, 178.0F, fill, 6, 256, 256);
      }

      if (mouseX >= x && mouseX <= x + 92 && mouseY >= y && mouseY <= y + 6) {
         Component tooltip = Component.translatable("stextras.prestige_menu.progress_hover", new Object[]{PROGRESS_IN_PERCENT});
         this.setTooltipForNextRenderPass(tooltip);
      }
   }

   private void renderQuestCategories(GuiGraphics gfx, int mouseX, int mouseY) {
      this.stxUpdateCountdownOncePerSecond();
      float labelScale = 0.78F;

      for (int i = 0; i < 5; i++) {
         int x = guiLeft + 4;
         int y = guiTop + 47 + i * 14;
         boolean hovered = mouseX >= x && mouseX <= x + 105 && mouseY >= y && mouseY <= y + 13;
         boolean selected = this.tabFromIndex(i) == this.selectedTab;
         int v = !hovered && !selected ? 193 : 206;
         gfx.blit(BACKGROUND, x, y, 0.0F, v, 105, 13, 256, 256);
         Component label = Component.translatable("stextras.prestige_menu.quest_category_" + (i + 1));
         int textWidth = (int)(this.font.width(label) * labelScale);
         int textHeight = (int)(9.0F * labelScale);
         int textX = x + (105 - textWidth) / 2;
         int textY = y + (13 - textHeight) / 2;
         gfx.pose().pushPose();
         gfx.pose().translate(textX, textY, 0.0F);
         gfx.pose().scale(labelScale, labelScale, 1.0F);
         int color = !hovered && !selected ? 16777215 : 16777130;
         gfx.drawString(this.font, label, 0, 0, color, false);
         gfx.pose().popPose();
         boolean isDaily = i == 0;
         boolean isWeekly = i == 1;
         if (isDaily || isWeekly) {
            long secs = isDaily ? this.stxDailyLeftSec : this.stxWeeklyLeftSec;
            if (secs < 0L) {
               secs = 0L;
            }

            String tText = STPrestigeProgress.formatCountdown(secs);
            float tScale = 0.7F;
            int tW = (int)(this.font.width(tText) * tScale);
            int tH = (int)(9.0F * tScale);
            int rightPad = 6;
            int tX = x + 105 - rightPad - tW;
            int tY = y + (14 - tH) / 2;
            gfx.pose().pushPose();
            gfx.pose().translate(tX, tY, 0.0F);
            gfx.pose().scale(tScale, tScale, 1.0F);
            gfx.drawString(this.font, tText, 0, 0, 16733525, false);
            gfx.pose().popPose();
         }

         if (hovered) {
            Component tooltip = Component.translatable("stextras.prestige_menu.quest_category_" + (i + 1) + "_hover");
            this.setTooltipForNextRenderPass(tooltip);
         }
      }
   }

   private void stxUpdateCountdownOncePerSecond() {
      long now = System.currentTimeMillis();
      if (now - this.stxLastTimerMs >= 1000L) {
         this.stxDailyLeftSec = STExtarsStorage.Quests.secondsUntilNextDaily();
         this.stxWeeklyLeftSec = STExtarsStorage.Quests.secondsUntilNextWeekly();
         this.stxLastTimerMs = now;
      }
   }

   private STPrestigeScreens.QuestTab tabFromIndex(int i) {
      return switch (i) {
         case 0 -> STPrestigeScreens.QuestTab.DAILY;
         case 1 -> STPrestigeScreens.QuestTab.WEEKLY;
         case 2 -> STPrestigeScreens.QuestTab.REPEATABLE;
         case 3 -> STPrestigeScreens.QuestTab.REQUIRED;
         case 4 -> STPrestigeScreens.QuestTab.RACE;
         default -> STPrestigeScreens.QuestTab.DAILY;
      };
   }

   @Nullable
   private QuestDefinition getQuest(ResourceLocation id) {
      try {
         return QuestRegistry.INSTANCE.byIdClient(id);
      } catch (Exception ignored) {
         return null;
      }
   }

   private void renderQuests(GuiGraphics gfx, int mouseX, int mouseY) {
      int totalRows = Math.min(this.questIds.size(), 3);

      for (int row = 0; row < totalRows; row++) {
         int x = guiLeft + 6;
         int y = guiTop + 118 + row * 13;
         gfx.blit(BACKGROUND, x, y, 106.0F, 164.0F, 89, 13, 256, 256);
      }

      int end = Math.min(this.questListStartIndex + 3, this.questIds.size());
      int drawRow = 0;

      for (int i = this.questListStartIndex; i < end; drawRow++) {
         int x = guiLeft + 6;
         int y = guiTop + 118 + drawRow * 13;
         boolean hovered = mouseX >= x && mouseX <= x + 89 && mouseY >= y && mouseY <= y + 13;
         if (hovered) {
            gfx.blit(BACKGROUND, x, y, 106.0F, 177.0F, 89, 13, 256, 256);
         }

         ResourceLocation id = this.questIds.get(i);
         QuestDefinition quest = this.getQuest(id);
         boolean completed = STExtarsStorage.Quests.completedClient().contains(id);
         int progress = STExtarsStorage.Quests.progressClient(id);
         int amount = quest != null ? Math.max(1, quest.amount) : 0;
         boolean showProgress = quest != null && !quest.noAmountRequired();
         long cooldownRemaining = quest != null && quest.category == QuestCategory.REPEATABLE ? STExtarsStorage.Quests.cooldownRemainingClient(id) : 0L;
         boolean onCooldown = cooldownRemaining > 0L;
         String displayName = quest != null && quest.name != null && !quest.name.isBlank() ? quest.name : id.getPath();
         String rightText;
         if (onCooldown) {
            rightText = STPrestigeProgress.formatCountdown(cooldownRemaining);
         } else if (completed) {
            rightText = "✓";
         } else if (showProgress) {
            rightText = "[" + progress + "/" + amount + "]";
         } else {
            rightText = "";
         }

         int baseColor = onCooldown ? 11184810 : (completed ? 11184810 : (quest != null ? STPrestigeQuestFormatter.colorForRarity(quest.rarity) : 16777215));
         int rightColor = onCooldown ? 16733525 : (completed ? 8978312 : 14540253);
         int textBaseY = y + 3;
         int rightW = this.font.width(rightText);
         int leftMaxPx = 83 - rightW - 2;
         String trimmedName = STPrestigeQuestFormatter.trimToWidthScaled(this.font, displayName, leftMaxPx, 1.0F);
         gfx.drawString(this.font, Component.literal(trimmedName), x + 3, textBaseY, baseColor, false);
         gfx.drawString(this.font, Component.literal(rightText), x + 89 - 3 - rightW, textBaseY, rightColor, false);
         if (hovered) {
            MutableComponent tip = Component.literal("");
            if (quest != null) {
               String progressTextOverride = onCooldown ? "Cooldown " + STPrestigeProgress.formatCountdown(cooldownRemaining) : null;
               tip = STPrestigeQuestFormatter.buildQuestTooltip(
                  quest, id, completed, progress, amount, showProgress, progressTextOverride, this.tensuraPlayerData.getResetCounter(), Screen.hasShiftDown()
               );
            } else {
               tip.append(Component.literal(id.toString()).withColor(12303291));
            }

            this.setTooltipForNextRenderPass(tip);
         }

         i++;
      }
   }

   public boolean mouseClicked(double mX, double mY, int button) {
      int catX = guiLeft + 4;
      int catW = 105;

      for (int i = 0; i < 5; i++) {
         int catY = guiTop + 47 + i * 14;
         if (mX >= catX && mX <= catX + catW && mY >= catY && mY <= catY + 13) {
            STPrestigeScreens.QuestTab newTab = this.tabFromIndex(i);
            if (newTab != this.selectedTab) {
               this.selectedTab = newTab;
               this.rebuildQuestListForTab();
               this.resetQuestScroll();
               if (newTab == STPrestigeScreens.QuestTab.RACE) {
                  this.rebuildRaceList();
                  this.resetRaceScroll();
               }

               ScreenHelper.clicked();
            }

            return true;
         }
      }

      if (this.selectedTab == STPrestigeScreens.QuestTab.RACE && this.raceBar.clickedScrollBar(mX, mY)) {
         return true;
      }

      if (this.clickedScrollBar(mX, mY)) {
         return true;
      }

      int x = guiLeft + 6;
      int yTop = guiTop + 118;

      for (int row = 0; row < 3; row++) {
         int y = yTop + row * 13;
         if (mX >= x && mX <= x + 89 && mY >= y && mY <= y + 13) {
            int idx = this.questListStartIndex + row;
            if (idx >= 0 && idx < this.questIds.size()) {
               this.selectedQuest = this.questIds.get(idx);
               ScreenHelper.clicked();
               return true;
            }
         }
      }

      return super.mouseClicked(mX, mY, button);
   }

   public boolean mouseDragged(double mX, double mY, int button, double dragX, double dragY) {
      if (this.selectedTab == STPrestigeScreens.QuestTab.RACE && this.raceBar.isScrolling()) {
         return this.raceBar.draggedScrollBar(mY) || super.mouseDragged(mX, mY, button, dragX, dragY);
      } else {
         return !this.isScrolling()
            ? super.mouseDragged(mX, mY, button, dragX, dragY)
            : this.draggedScrollBar(mY) || super.mouseDragged(mX, mY, button, dragX, dragY);
      }
   }

   public boolean mouseScrolled(double mX, double mY, double dx, double dy) {
      if (this.selectedTab == STPrestigeScreens.QuestTab.RACE) {
         int rbx = guiLeft + 210;
         int rby1 = guiTop + 53;
         int rby2 = rby1 + 78;
         boolean overRaceTrack = mX >= rbx && mX <= rbx + this.raceBar.getScrollBarWidth() && mY >= rby1 && mY <= rby2;
         if (this.inRaceViewport(mX, mY) || overRaceTrack) {
            return this.raceBar.scrolledScrollBar(dy);
         }
      }

      int qx1 = guiLeft + 6;
      int qy1 = guiTop + 118;
      int qx2 = qx1 + 89;
      int qy2 = qy1 + 39;
      int qbx = guiLeft + 98;
      int qby1 = guiTop + 119;
      int qby2 = qby1 + 39;
      boolean overQuestTrack = mX >= qbx && mX <= qbx + this.getScrollBarWidth() && mY >= qby1 && mY <= qby2;
      return (!(mX >= qx1) || !(mX <= qx2) || !(mY >= qy1) || !(mY <= qy2)) && !overQuestTrack
         ? super.mouseScrolled(mX, mY, dx, dy)
         : this.scrolledScrollBar(dy);
   }

   private void rebuildQuestListForTab() {
      this.questIds.clear();

      Set<ResourceLocation> set = switch (this.selectedTab) {
         case DAILY -> {
            Set<ResourceLocation> all = new HashSet<>();
            all.addAll(STExtarsStorage.Quests.activeClient(QuestCategory.DAILY));
            all.addAll(STExtarsStorage.Quests.completedClient(QuestCategory.DAILY));
            yield all;
         }
         case WEEKLY -> {
            Set<ResourceLocation> all = new HashSet<>();
            all.addAll(STExtarsStorage.Quests.activeClient(QuestCategory.WEEKLY));
            all.addAll(STExtarsStorage.Quests.completedClient(QuestCategory.WEEKLY));
            yield all;
         }
         case REPEATABLE -> {
            Set<ResourceLocation> all = new HashSet<>();
            all.addAll(STExtarsStorage.Quests.activeClient(QuestCategory.REPEATABLE));
            all.addAll(STExtarsStorage.Quests.completedClient(QuestCategory.REPEATABLE));
            yield all;
         }
         case REQUIRED -> {
            Set<ResourceLocation> all = new HashSet<>();
            all.addAll(STExtarsStorage.Quests.activeClient(QuestCategory.REQUIRED));
            all.addAll(STExtarsStorage.Quests.completedClient(QuestCategory.REQUIRED));
            yield all;
         }
         case RACE -> {
            Set<ResourceLocation> all = new HashSet<>();
            all.addAll(STExtarsStorage.Quests.activeClient(QuestCategory.RACE_PRESTIGE));
            all.addAll(STExtarsStorage.Quests.completedClient(QuestCategory.RACE_PRESTIGE));
            yield all;
         }
      };
      this.questIds.addAll(set.stream().sorted(Comparator.comparing(ResourceLocation::getNamespace).thenComparing(ResourceLocation::getPath)).toList());
      if (this.selectedQuest != null && !this.questIds.contains(this.selectedQuest)) {
         this.selectedQuest = null;
      }
   }

   private void clampQuestScroll() {
      int maxStart = Math.max(0, this.questIds.size() - 3);
      if (this.questListStartIndex > maxStart) {
         this.questListStartIndex = maxStart;
      }
   }

   private void clampRaceScroll() {
      int maxStart = Math.max(0, this.raceIds.size() - 6);
      if (this.raceListStartIndex > maxStart) {
         this.raceListStartIndex = maxStart;
      }
   }

   private void resetQuestScroll() {
      this.questListStartIndex = 0;
      this.questScrollOffset = 0.0F;
      this.questScrolling = false;
   }

   public void setListStartIndex(int value) {
      this.questListStartIndex = value;
   }

   public void setScrolling(boolean value) {
      this.questScrolling = value;
   }

   public void setScrollOffset(float value) {
      this.questScrollOffset = value;
   }

   public int getScrollBarX() {
      return guiLeft + 98;
   }

   public int getScrollBarY() {
      return guiTop + 119;
   }

   public int getScrollBarTotalSpace() {
      return 39;
   }

   public int getScrollBarListSize() {
      return this.questIds.size();
   }

   public int getScrollBarRenderCount() {
      return 3;
   }

   public float getScrollOffset() {
      return this.questScrollOffset;
   }

   public boolean isScrolling() {
      return this.questScrolling;
   }

   private enum QuestTab {
      DAILY,
      WEEKLY,
      REPEATABLE,
      REQUIRED,
      RACE;
   }
}
