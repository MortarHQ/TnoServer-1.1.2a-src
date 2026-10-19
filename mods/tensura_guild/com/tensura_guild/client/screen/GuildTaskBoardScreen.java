package com.tensura_guild.client.screen;

import com.mojang.math.Axis;
import com.tensura_guild.client.GuildPartyClient;
import com.tensura_guild.config.GuildConfig;
import com.tensura_guild.data.GuildLevelData;
import com.tensura_guild.data.GuildTaskBoardData;
import com.tensura_guild.data.GuildTaskDefinitions;
import com.tensura_guild.data.GuildTaskTime;
import com.tensura_guild.network.AcceptGuildTaskPayload;
import com.tensura_guild.network.CloseDialoguePayload;
import com.tensura_guild.network.CompleteGuildTaskPayload;
import com.tensura_guild.network.DismissFailedGuildTaskPayload;
import com.tensura_guild.registry.GuildAttachments;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.PacketDistributor;

public class GuildTaskBoardScreen extends Screen {
   private static final ResourceLocation BOARD_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/task_board.png");
   private static final ResourceLocation CONTENT_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/task_content_paper.png");
   private static final ResourceLocation RANK_TAB_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/task_rank_tab.png");
   private static final int TEXTURE_SIZE = 256;
   private static final int RANK_TAB_TEXTURE_SIZE = 64;
   private static final int BOARD_WIDTH = 227;
   private static final int BOARD_HEIGHT = 138;
   private static final float BOARD_SCALE = 1.35F;
   private static final GuildTaskBoardScreen.Rect RANK_TAB_DEFAULT_SRC = new GuildTaskBoardScreen.Rect(0, 0, 17, 11);
   private static final GuildTaskBoardScreen.Rect RANK_TAB_HOVER_SRC = new GuildTaskBoardScreen.Rect(0, 12, 27, 23);
   private static final GuildTaskBoardScreen.Rect RANK_TAB_LETTER_DEST = new GuildTaskBoardScreen.Rect(9, 2, 14, 10);
   private static final int RANK_TAB_LETTER_SOURCE_X = 2;
   private static final int RANK_TAB_LETTER_SOURCE_Y = 26;
   private static final int RANK_TAB_LETTER_SOURCE_WIDTH = 5;
   private static final int RANK_TAB_LETTER_SOURCE_HEIGHT = 8;
   private static final int RANK_TAB_LETTER_GAP = 2;
   private static final int RANK_TAB_START_Y = 8;
   private static final int RANK_TAB_GAP = 1;
   private static final GuildTaskBoardScreen.Rect TASK_PAPER_SRC = new GuildTaskBoardScreen.Rect(2, 140, 22, 160);
   private static final GuildTaskBoardScreen.Rect COMPLETED_TASK_PAPER_SRC = new GuildTaskBoardScreen.Rect(24, 140, 44, 160);
   private static final GuildTaskBoardScreen.Rect FAILED_TASK_PAPER_SRC = new GuildTaskBoardScreen.Rect(46, 140, 66, 161);
   private static final GuildTaskBoardScreen.Rect BOUNTY_TASK_PAPER_SRC = new GuildTaskBoardScreen.Rect(2, 162, 22, 182);
   private static final GuildTaskBoardScreen.Rect COMPLETED_BOUNTY_TASK_PAPER_SRC = new GuildTaskBoardScreen.Rect(24, 162, 44, 182);
   private static final int TASK_PAPER_DISPLAY_WIDTH = 26;
   private static final int TASK_PAPER_DISPLAY_HEIGHT = 26;
   private static final GuildTaskBoardScreen.Rect COMMISSION_TASK_AREA = new GuildTaskBoardScreen.Rect(9, 8, 143, 130);
   private static final GuildTaskBoardScreen.Rect PERSONAL_TASK_AREA = new GuildTaskBoardScreen.Rect(153, 8, 220, 130);
   private static final int CONTENT_WIDTH = 138;
   private static final int CONTENT_HEIGHT = 184;
   private static final float CONTENT_SCALE = 1.25F;
   private static final int CONTENT_TEXT_PADDING_X = 12;
   private static final int CONTENT_TEXT_PADDING_TOP = 22;
   private static final int CONTENT_TEXT_WIDTH = 114;
   private static final int CONTENT_TEXT_COLOR = -13950696;
   private static final float REWARD_ICON_SCALE = 0.75F;
   private static final GuildTaskBoardScreen.Rect TASK_BUTTON_AREA = new GuildTaskBoardScreen.Rect(39, 146, 98, 161);
   private static final GuildTaskBoardScreen.Rect ACCEPT_BUTTON_ZH_SRC = new GuildTaskBoardScreen.Rect(3, 189, 62, 204);
   private static final GuildTaskBoardScreen.Rect ACCEPT_BUTTON_EN_SRC = new GuildTaskBoardScreen.Rect(3, 207, 62, 222);
   private static final GuildTaskBoardScreen.Rect COMPLETE_BUTTON_ZH_SRC = new GuildTaskBoardScreen.Rect(65, 189, 124, 204);
   private static final GuildTaskBoardScreen.Rect COMPLETE_BUTTON_EN_SRC = new GuildTaskBoardScreen.Rect(65, 208, 124, 223);
   private static final float TASK_BUTTON_HOVER_SCALE = 1.08F;
   private static final int PAPER_META_TOP = 106;
   private static final int PAPER_META_BOTTOM = 144;
   private static final float PAPER_META_TEXT_SCALE = 0.72F;
   private static final int PAPER_META_LINE_GAP = 3;
   private static final int POPUP_ANIMATION_TICKS = 10;
   private static final int SLIDE_ANIMATION_TICKS = 14;
   private static final long COMMISSION_DAY_TICKS = 28800L;
   private final List<GuildTaskBoardScreen.TaskPaper> taskPapers = new ArrayList<>();
   private final boolean openCarriedTask;
   private final ResourceLocation carriedTaskId;
   private final int dialogueEntityId;
   private int leftPos;
   private int topPos;
   private float boardScale = 1.35F;
   private int lastTaskCount = -1;
   private List<ResourceLocation> lastAvailableTasks = List.of();
   private List<GuildTaskBoardData.ActiveTask> lastActiveTasks = List.of();
   private List<ResourceLocation> lastFailedTasks = List.of();
   private boolean lastTaskComplete;
   private GuildTaskBoardScreen.OpenTaskPaper openPaper;
   private GuildTaskBoardScreen.MovingTaskPaper movingPaper;
   private ResourceLocation pendingAcceptTaskId;
   private GuildTaskBoardScreen.TaskPaper pendingAcceptPaper;
   private String selectedRank = GuildLevelData.rankForLevel(1);
   private boolean dialogueReleased;

   public GuildTaskBoardScreen() {
      this(false, null, -1);
   }

   public GuildTaskBoardScreen(int dialogueEntityId) {
      this(false, null, dialogueEntityId);
   }

   private GuildTaskBoardScreen(boolean openCarriedTask, ResourceLocation carriedTaskId) {
      this(openCarriedTask, carriedTaskId, -1);
   }

   private GuildTaskBoardScreen(boolean openCarriedTask, ResourceLocation carriedTaskId, int dialogueEntityId) {
      super(Component.empty());
      this.openCarriedTask = openCarriedTask;
      this.carriedTaskId = carriedTaskId;
      this.dialogueEntityId = dialogueEntityId;
   }

   public static GuildTaskBoardScreen openCarriedTask(ResourceLocation taskId) {
      return new GuildTaskBoardScreen(true, taskId);
   }

   protected void init() {
      GuildPartyClient.requestSync();
      this.boardScale = this.calculateBoardScale();
      this.leftPos = (this.width - Math.round(227.0F * this.boardScale)) / 2;
      this.topPos = (this.height - Math.round(138.0F * this.boardScale)) / 2;
      this.ensureSelectedRankUnlocked();
      this.rebuildTaskPapers();
      if (this.openCarriedTask
         && this.minecraft != null
         && this.minecraft.player != null
         && this.carriedTaskId != null
         && ((GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD)).hasAcceptedTask(this.carriedTaskId)) {
         this.openPaper = new GuildTaskBoardScreen.OpenTaskPaper(this.personalTaskPaper(this.carriedTaskId), false, 10);
      }
   }

   public void tick() {
      super.tick();
      this.refreshTaskPapersIfNeeded();
      if (this.openPaper != null) {
         this.openPaper = this.openPaper.tick();
         if (this.openPaper.closing() && this.openPaper.animationTicks() >= 10) {
            this.openPaper = null;
            if (this.openCarriedTask && this.minecraft != null) {
               this.minecraft.setScreen(null);
            }
         }
      }

      if (this.movingPaper != null) {
         this.movingPaper = this.movingPaper.tick();
         if (this.movingPaper.animationTicks() >= 14) {
            this.taskPapers.removeIf(paper -> this.sameTaskPaper(paper, this.movingPaper.from()));
            if (this.taskPapers.stream().noneMatch(paper -> this.sameTaskPaper(paper, this.movingPaper.to()))) {
               this.taskPapers.add(this.movingPaper.to());
            }

            this.movingPaper = null;
         }
      }
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      if (!this.openCarriedTask) {
         graphics.pose().pushPose();

         try {
            graphics.pose().translate(this.leftPos, this.topPos, 0.0F);
            graphics.pose().scale(this.boardScale, this.boardScale, 1.0F);
            graphics.blit(BOARD_TEXTURE, 0, 0, 0, 0.0F, 0.0F, 227, 138, 256, 256);
            this.renderRankTabs(graphics, mouseX, mouseY);

            for (GuildTaskBoardScreen.TaskPaper paper : this.taskPapers) {
               if (this.movingPaper == null || !this.isMovingPaperEndpoint(paper)) {
                  GuildTaskBoardScreen.Rect source = this.taskPaperSource(paper);
                  graphics.blit(BOARD_TEXTURE, paper.x(), paper.y(), 26, 26, source.left(), source.top(), source.width(), source.height(), 256, 256);
               }
            }

            this.renderMovingPaper(graphics, partialTick);
         } finally {
            graphics.pose().popPose();
         }
      }

      if (this.openPaper != null) {
         this.renderOpenPaper(graphics, mouseX, mouseY, partialTick);
      }

      if (!this.openCarriedTask && this.openPaper == null) {
         this.renderWithdrawTooltip(graphics, mouseX, mouseY);
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button != 0) {
         return super.mouseClicked(mouseX, mouseY, button);
      }

      if (this.openPaper != null) {
         return button == 0 && this.handleOpenPaperButtonClick(mouseX, mouseY) ? true : true;
      }

      int localX = (int)Math.floor((mouseX - this.leftPos) / this.boardScale);
      int localY = (int)Math.floor((mouseY - this.topPos) / this.boardScale);
      if (this.handleRankTabClick(localX, localY)) {
         return true;
      }

      for (int i = 0; i < this.taskPapers.size(); i++) {
         GuildTaskBoardScreen.TaskPaper paper = this.taskPapers.get(i);
         if (paper.bounds().contains(localX, localY)) {
            if (paper.type() == GuildTaskBoardScreen.TaskPaperType.FAILED_TASK && paper.taskId() != null) {
               PacketDistributor.sendToServer(new DismissFailedGuildTaskPayload(paper.taskId()), new CustomPacketPayload[0]);
               this.taskPapers.remove(i);
               return true;
            }

            this.openPaper = new GuildTaskBoardScreen.OpenTaskPaper(paper, false, 0);
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode != 256 || this.openPaper == null) {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }

      if (this.openCarriedTask) {
         if (this.minecraft != null) {
            this.minecraft.setScreen(null);
         }

         return true;
      } else {
         if (!this.openPaper.closing()) {
            this.openPaper = new GuildTaskBoardScreen.OpenTaskPaper(this.openPaper.paper(), true, 0);
         }

         return true;
      }
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
   }

   protected void renderBlurredBackground(float partialTick) {
   }

   public boolean isPauseScreen() {
      return false;
   }

   public void onClose() {
      this.releaseDialogueLock();
      super.onClose();
   }

   private void releaseDialogueLock() {
      if (!this.dialogueReleased && this.dialogueEntityId >= 0) {
         this.dialogueReleased = true;
         PacketDistributor.sendToServer(new CloseDialoguePayload(this.dialogueEntityId), new CustomPacketPayload[0]);
      }
   }

   private float calculateBoardScale() {
      float maxWidthScale = Math.max(0.1F, (this.width - 16) / 227.0F);
      float maxHeightScale = Math.max(0.1F, (this.height - 16) / 138.0F);
      return Math.min(1.35F, Math.min(maxWidthScale, maxHeightScale));
   }

   private void rebuildTaskPapers() {
      List<GuildTaskBoardScreen.TaskPaper> previousPapers = List.copyOf(this.taskPapers);
      this.taskPapers.clear();
      if (this.minecraft != null && this.minecraft.player != null) {
         GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
         int count = data.debugTaskCount();
         this.lastAvailableTasks = data.availableTasks();
         this.lastActiveTasks = data.activeTasks();
         this.lastFailedTasks = data.failedTasks();
         this.lastTaskComplete = data.activeTasks().stream().anyMatch(taskx -> this.isAcceptedTaskComplete(taskx.taskId()));
         this.lastTaskCount = count;
         long seed = this.minecraft.player.getUUID().getMostSignificantBits()
            ^ this.minecraft.player.getUUID().getLeastSignificantBits()
            ^ 6071225355606249541L;
         Random random = new Random(seed);
         int taskIndex = 0;

         for (ResourceLocation taskId : this.visibleAvailableTasks(data)) {
            int index = taskIndex++;
            GuildTaskBoardScreen.TaskPaper task = this.findReusablePaper(
                  previousPapers, GuildTaskBoardScreen.TaskPaperType.TASK, taskId, index, COMMISSION_TASK_AREA
               )
               .orElseGet(() -> this.createTaskPaper(GuildTaskBoardScreen.TaskPaperType.TASK, taskId, index, random, COMMISSION_TASK_AREA));
            if (task != null) {
               this.taskPapers.add(task);
            }
         }

         int acceptedIndex = 0;

         for (GuildTaskBoardData.ActiveTask activeTask : data.activeTasks()) {
            int index;
            int var24 = index = acceptedIndex++;
            GuildTaskBoardScreen.TaskPaper task = this.findReusablePaper(
                  previousPapers, GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK, activeTask.taskId(), index, PERSONAL_TASK_AREA
               )
               .orElseGet(() -> this.createTaskPaper(GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK, activeTask.taskId(), index, random, PERSONAL_TASK_AREA));
            if (task != null) {
               this.taskPapers.add(task);
            }
         }

         for (ResourceLocation taskId : data.failedTasks()) {
            int index;
            int var25 = index = acceptedIndex++;
            GuildTaskBoardScreen.TaskPaper task = this.findReusablePaper(
                  previousPapers, GuildTaskBoardScreen.TaskPaperType.FAILED_TASK, taskId, index, PERSONAL_TASK_AREA
               )
               .orElseGet(() -> this.createTaskPaper(GuildTaskBoardScreen.TaskPaperType.FAILED_TASK, taskId, index, random, PERSONAL_TASK_AREA));
            if (task != null) {
               this.taskPapers.add(task);
            }
         }

         for (int i = 0; i < count; i++) {
            int index = i;
            GuildTaskBoardScreen.TaskPaper paper = this.findReusablePaper(
                  previousPapers, GuildTaskBoardScreen.TaskPaperType.DEBUG, null, index, COMMISSION_TASK_AREA
               )
               .orElseGet(() -> this.createTaskPaper(GuildTaskBoardScreen.TaskPaperType.DEBUG, null, index, random, COMMISSION_TASK_AREA));
            if (paper != null) {
               this.taskPapers.add(paper);
            }
         }
      } else {
         this.lastTaskCount = 0;
      }
   }

   private void refreshTaskPapersIfNeeded() {
      if (this.minecraft != null && this.minecraft.player != null) {
         if (this.movingPaper == null) {
            boolean rankChanged = this.ensureSelectedRankUnlocked();
            GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
            int count = data.debugTaskCount();
            boolean complete = data.activeTasks().stream().anyMatch(task -> this.isAcceptedTaskComplete(task.taskId()));
            if (rankChanged
               || count != this.lastTaskCount
               || !data.availableTasks().equals(this.lastAvailableTasks)
               || !data.activeTasks().equals(this.lastActiveTasks)
               || !data.failedTasks().equals(this.lastFailedTasks)
               || complete != this.lastTaskComplete) {
               this.rebuildTaskPapers();
            }

            this.closeUnavailableOpenTask(data);
         }
      }
   }

   private void renderRankTabs(GuiGraphics graphics, int mouseX, int mouseY) {
      List<String> ranks = this.visibleRankTabs();
      int localX = (int)Math.floor((mouseX - this.leftPos) / this.boardScale);
      int localY = (int)Math.floor((mouseY - this.topPos) / this.boardScale);

      for (int i = 0; i < ranks.size(); i++) {
         String rank = ranks.get(i);
         boolean hovered = this.rankTabBounds(i).contains(localX, localY);
         boolean selected = rank.equals(this.selectedRank);
         GuildTaskBoardScreen.Rect source = !hovered && !selected ? RANK_TAB_DEFAULT_SRC : RANK_TAB_HOVER_SRC;
         int drawX = 1 - source.width();
         int drawY = this.rankTabY(i);
         graphics.blit(RANK_TAB_TEXTURE, drawX, drawY, source.left(), source.top(), source.width(), source.height(), 64, 64);
         this.renderRankLetter(graphics, rank, drawX, drawY);
      }
   }

   private void renderRankLetter(GuiGraphics graphics, String rank, int tabX, int tabY) {
      int rankIndex = GuildLevelData.levelIndexForRank(rank) - 1;
      int sourceX = 2 + rankIndex * 7;
      graphics.blit(
         RANK_TAB_TEXTURE,
         tabX + RANK_TAB_LETTER_DEST.left(),
         tabY + RANK_TAB_LETTER_DEST.top(),
         RANK_TAB_LETTER_DEST.width(),
         RANK_TAB_LETTER_DEST.height(),
         sourceX,
         26.0F,
         5,
         8,
         64,
         64
      );
   }

   private boolean handleRankTabClick(int localX, int localY) {
      List<String> ranks = this.visibleRankTabs();

      for (int i = 0; i < ranks.size(); i++) {
         if (this.rankTabBounds(i).contains(localX, localY)) {
            String rank = ranks.get(i);
            if (!rank.equals(this.selectedRank)) {
               this.selectedRank = rank;
               this.rebuildTaskPapers();
            }

            return true;
         }
      }

      return false;
   }

   private GuildTaskBoardScreen.Rect rankTabBounds(int index) {
      int top = this.rankTabY(index);
      return new GuildTaskBoardScreen.Rect(1 - RANK_TAB_HOVER_SRC.width(), top, 0, top + RANK_TAB_HOVER_SRC.height() - 1);
   }

   private int rankTabY(int index) {
      return 8 + index * (RANK_TAB_HOVER_SRC.height() + 1);
   }

   private boolean ensureSelectedRankUnlocked() {
      String normalizedRank = GuildLevelData.normalizeRank(this.selectedRank);
      List<String> ranks = this.visibleRankTabs();
      String nextRank = ranks.contains(normalizedRank) ? normalizedRank : GuildLevelData.rankForLevel(1);
      boolean changed = !nextRank.equals(this.selectedRank);
      this.selectedRank = nextRank;
      return changed;
   }

   private List<String> visibleRankTabs() {
      if (this.minecraft != null && this.minecraft.player != null) {
         List<String> ranks = new ArrayList<>(
            GuildLevelData.ranksUpToLevel(((GuildLevelData)this.minecraft.player.getData(GuildAttachments.GUILD_LEVEL)).levelIndex())
         );
         GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);

         for (ResourceLocation taskId : data.availableTasks()) {
            GuildTaskDefinitions.get(taskId)
               .filter(this::canShowAvailableTaskOnClient)
               .map(GuildTaskDefinitions.GuildTaskDefinition::rank)
               .filter(rank -> !ranks.contains(rank))
               .ifPresent(ranks::add);
         }

         ranks.sort(Comparator.comparingInt(GuildLevelData::levelIndexForRank));
         return List.copyOf(ranks);
      } else {
         return List.of(GuildLevelData.rankForLevel(1));
      }
   }

   private List<ResourceLocation> visibleAvailableTasks(GuildTaskBoardData data) {
      return data.availableTasks()
         .stream()
         .filter(
            taskId -> GuildTaskDefinitions.get(taskId).map(task -> task.isRank(this.selectedRank) && this.canShowAvailableTaskOnClient(task)).orElse(false)
         )
         .toList();
   }

   private boolean canShowAvailableTaskOnClient(GuildTaskDefinitions.GuildTaskDefinition task) {
      return !task.partyOnly() || GuildPartyClient.isInParty() && !GuildPartyClient.isPartyUndermanned();
   }

   private GuildTaskBoardScreen.TaskPaper createTaskPaper(
      GuildTaskBoardScreen.TaskPaperType type, ResourceLocation taskId, int index, Random random, GuildTaskBoardScreen.Rect area
   ) {
      int minX = area.left();
      int minY = area.top();
      int maxX = area.right() - 26 + 1;
      int maxY = area.bottom() - 26 + 1;

      for (int attempt = 0; attempt < 80; attempt++) {
         int x = minX + random.nextInt(maxX - minX + 1);
         int y = minY + random.nextInt(maxY - minY + 1);
         GuildTaskBoardScreen.TaskPaper paper = new GuildTaskBoardScreen.TaskPaper(type, taskId, index, x, y);
         if (!this.overlapsExistingPaper(paper)) {
            return paper;
         }
      }

      for (int y = minY; y <= maxY; y += 27) {
         for (int x = minX; x <= maxX; x += 27) {
            GuildTaskBoardScreen.TaskPaper paper = new GuildTaskBoardScreen.TaskPaper(type, taskId, index, x, y);
            if (!this.overlapsExistingPaper(paper)) {
               return paper;
            }
         }
      }

      return null;
   }

   private GuildTaskBoardScreen.TaskPaper personalTaskPaper(ResourceLocation taskId) {
      for (GuildTaskBoardScreen.TaskPaper paper : this.taskPapers) {
         if (paper.type() == GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK && paper.taskId() != null && paper.taskId().equals(taskId)) {
            return paper;
         }
      }

      int index = 0;

      for (GuildTaskBoardScreen.TaskPaper paper : this.taskPapers) {
         if (paper.type() == GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK) {
            index++;
         }
      }

      long seed = taskId == null ? 0L : taskId.hashCode();
      GuildTaskBoardScreen.TaskPaper paper = this.createTaskPaper(
         GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK, taskId, index, new Random(seed ^ 5784119814193561932L), PERSONAL_TASK_AREA
      );
      if (paper != null) {
         return paper;
      }

      int x = PERSONAL_TASK_AREA.left() + (PERSONAL_TASK_AREA.width() - 26) / 2;
      int y = PERSONAL_TASK_AREA.top() + (PERSONAL_TASK_AREA.height() - 26) / 2;
      return new GuildTaskBoardScreen.TaskPaper(GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK, taskId, index, x, y);
   }

   private boolean overlapsExistingPaper(GuildTaskBoardScreen.TaskPaper paper) {
      for (GuildTaskBoardScreen.TaskPaper existing : this.taskPapers) {
         if (paper.bounds().intersects(existing.bounds())) {
            return true;
         }
      }

      return false;
   }

   private Optional<GuildTaskBoardScreen.TaskPaper> findReusablePaper(
      List<GuildTaskBoardScreen.TaskPaper> papers, GuildTaskBoardScreen.TaskPaperType type, ResourceLocation taskId, int index, GuildTaskBoardScreen.Rect area
   ) {
      for (GuildTaskBoardScreen.TaskPaper paper : papers) {
         if (this.matchesTaskPaper(paper, type, taskId, index) && area.contains(paper.x(), paper.y()) && area.contains(paper.x() + 26 - 1, paper.y() + 26 - 1)) {
            GuildTaskBoardScreen.TaskPaper reused = new GuildTaskBoardScreen.TaskPaper(type, taskId, index, paper.x(), paper.y());
            if (!this.overlapsExistingPaper(reused)) {
               return Optional.of(reused);
            }
         }
      }

      return Optional.empty();
   }

   private boolean matchesTaskPaper(GuildTaskBoardScreen.TaskPaper paper, GuildTaskBoardScreen.TaskPaperType type, ResourceLocation taskId, int index) {
      if (paper.type() != type) {
         return false;
      } else {
         return type == GuildTaskBoardScreen.TaskPaperType.DEBUG ? paper.index() == index : paper.taskId() != null && paper.taskId().equals(taskId);
      }
   }

   private boolean sameTaskPaper(GuildTaskBoardScreen.TaskPaper first, GuildTaskBoardScreen.TaskPaper second) {
      if (first == null || second == null || first.type() != second.type()) {
         return false;
      } else {
         return first.type() == GuildTaskBoardScreen.TaskPaperType.DEBUG
            ? first.index() == second.index()
            : first.taskId() != null && first.taskId().equals(second.taskId());
      }
   }

   private boolean isMovingPaperEndpoint(GuildTaskBoardScreen.TaskPaper paper) {
      return this.sameTaskPaper(paper, this.movingPaper.from()) || this.sameTaskPaper(paper, this.movingPaper.to());
   }

   private GuildTaskBoardScreen.Rect taskPaperSource(GuildTaskBoardScreen.TaskPaper paper) {
      if (paper.type() == GuildTaskBoardScreen.TaskPaperType.FAILED_TASK) {
         return FAILED_TASK_PAPER_SRC;
      } else if (paper.type() != GuildTaskBoardScreen.TaskPaperType.TASK && paper.type() != GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK) {
         return TASK_PAPER_SRC;
      } else if (paper.type() == GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK && this.isAcceptedTaskFailed(paper.taskId())) {
         return FAILED_TASK_PAPER_SRC;
      } else {
         boolean bountyTask = this.isBountyTask(paper.taskId());
         if (paper.type() == GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK && this.isAcceptedTaskComplete(paper.taskId())) {
            return bountyTask ? COMPLETED_BOUNTY_TASK_PAPER_SRC : COMPLETED_TASK_PAPER_SRC;
         } else {
            return bountyTask ? BOUNTY_TASK_PAPER_SRC : TASK_PAPER_SRC;
         }
      }
   }

   private void renderMovingPaper(GuiGraphics graphics, float partialTick) {
      if (this.movingPaper != null) {
         float progress = Mth.clamp((this.movingPaper.animationTicks() + partialTick) / 14.0F, 0.0F, 1.0F);
         float eased = progress * progress * (3.0F - 2.0F * progress);
         int x = Math.round(Mth.lerp(eased, this.movingPaper.from().x(), this.movingPaper.to().x()));
         int y = Math.round(Mth.lerp(eased, this.movingPaper.from().y(), this.movingPaper.to().y()));
         GuildTaskBoardScreen.Rect source = this.isBountyTask(this.movingPaper.from().taskId()) ? BOUNTY_TASK_PAPER_SRC : TASK_PAPER_SRC;
         graphics.blit(BOARD_TEXTURE, x, y, 26, 26, source.left(), source.top(), source.width(), source.height(), 256, 256);
      }
   }

   private void renderOpenPaper(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      GuildTaskBoardScreen.OpenTaskPaper current = this.openPaper;
      float progress = this.openCarriedTask ? 1.0F : Mth.clamp((current.animationTicks() + partialTick) / 10.0F, 0.0F, 1.0F);
      if (current.closing()) {
         progress = 1.0F - progress;
      }

      float eased = progress * progress * (3.0F - 2.0F * progress);
      float scale = Mth.clamp(eased, 0.01F, 1.0F);
      float sourceX = this.leftPos + (current.paper().x() + 13.0F) * this.boardScale;
      float sourceY = this.topPos + (current.paper().y() + 13.0F) * this.boardScale;
      float targetX = this.width / 2.0F;
      float targetY = this.height / 2.0F;
      float centerX = Mth.lerp(eased, sourceX, targetX);
      float centerY = Mth.lerp(eased, sourceY, targetY);
      graphics.pose().pushPose();

      try {
         graphics.pose().translate(centerX, centerY, 80.0F);
         graphics.pose().mulPose(Axis.ZP.rotationDegrees(Mth.lerp(eased, -3.0F, 0.0F)));
         graphics.pose().scale(scale * 1.25F, scale * 1.25F, 1.0F);
         graphics.pose().translate(-69.0F, -92.0F, 0.0F);
         graphics.blit(CONTENT_TEXTURE, 0, 0, 0, 0.0F, 0.0F, 138, 184, 256, 256);
         this.renderPaperText(graphics, current.paper());
         this.renderTaskButton(graphics, current.paper(), mouseX, mouseY);
      } finally {
         graphics.pose().popPose();
      }
   }

   private void renderPaperText(GuiGraphics graphics, GuildTaskBoardScreen.TaskPaper paper) {
      if (paper.type() != GuildTaskBoardScreen.TaskPaperType.TASK && paper.type() != GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK) {
         Component title = Component.translatable("screen.tensura_guild.task_board.debug_task.title", new Object[]{paper.index() + 1});
         Component body = Component.translatable("screen.tensura_guild.task_board.debug_task.content");
         graphics.drawString(this.font, title, 12, 22, -13950696, false);
         int y = 22 + 9 + 8;

         for (FormattedCharSequence line : this.font.split(body, 114)) {
            graphics.drawString(this.font, line, 12, y, -13950696, false);
            y += 9 + 2;
         }
      } else {
         this.renderTaskText(graphics, paper.taskId(), paper.type());
      }
   }

   private void renderTaskText(GuiGraphics graphics, ResourceLocation taskId, GuildTaskBoardScreen.TaskPaperType paperType) {
      Optional<GuildTaskDefinitions.GuildTaskDefinition> task = GuildTaskDefinitions.get(taskId);
      Component title = (Component)task.<MutableComponent>map(value -> Component.translatable(value.titleKey()))
         .orElse(Component.literal(taskId == null ? "" : taskId.toString()));
      Component description = (Component)task.<MutableComponent>map(value -> Component.translatable(value.descriptionKey())).orElse(Component.empty());
      Component reward = task.map(this::rewardText).orElse(Component.empty());
      Optional<Component> partyText = this.partyTaskText(taskId);
      Optional<Component> partyPausedText = this.partyPausedText(taskId);
      Optional<Component> targetText = this.targetCoordinatesText(taskId);
      Optional<Component> withdrawText = paperType == GuildTaskBoardScreen.TaskPaperType.TASK ? this.withdrawRemainingText(taskId) : Optional.empty();
      graphics.drawString(this.font, title, 12, 22, -13950696, false);
      int y = 22 + 9 + 8;
      this.drawWrappedPaperText(graphics, description, 12, y, 98);
      y = 106;
      if (partyText.isPresent()) {
         y = this.drawSmallPaperWrappedText(graphics, partyText.get(), 12, y, 114, 144);
      }

      if (partyPausedText.isPresent()) {
         y = this.drawSmallPaperWrappedText(graphics, partyPausedText.get(), 12, y, 114, 144);
      }

      if (targetText.isPresent()) {
         y = this.drawSmallPaperWrappedText(graphics, targetText.get(), 12, y, 114, 144);
      }

      int timeLimitTicks = this.taskTimeLimitTicks(taskId);
      if (timeLimitTicks > 0) {
         Component timeLimit = Component.translatable(
            "screen.tensura_guild.task.time_limit", new Object[]{GuildTaskTime.formatCommissionDayDurationText(timeLimitTicks)}
         );
         y = this.drawSmallPaperWrappedText(graphics, timeLimit, 12, y, 114, 144);
      }

      if (withdrawText.isPresent()) {
         y = this.drawSmallPaperWrappedText(graphics, withdrawText.get(), 12, y, 114, 144);
      }

      this.drawSmallPaperRewardText(graphics, reward, taskId, 12, y, 114, 144);
   }

   private Optional<Component> targetCoordinatesText(ResourceLocation taskId) {
      if (taskId != null && this.minecraft != null && this.minecraft.player != null) {
         GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
         return data.activeTask(taskId)
            .filter(task -> task.targetX().isPresent() && task.targetZ().isPresent())
            .flatMap(
               task -> {
                  if (data.isTaskComplete(taskId)) {
                     return GuildTaskDefinitions.get(taskId).map(GuildTaskDefinitions.GuildTaskDefinition::isVisitStructureTask).orElse(false)
                        ? Optional.of(Component.translatable("screen.tensura_guild.task.target_reached"))
                        : Optional.of(Component.translatable("screen.tensura_guild.task.target_defeated"));
                  } else {
                     return Optional.of(
                        Component.translatable("screen.tensura_guild.task.target_coordinates", new Object[]{task.targetX().get(), task.targetZ().get()})
                     );
                  }
               }
            );
      } else {
         return Optional.empty();
      }
   }

   private void renderWithdrawTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
      this.hoveredAvailableTaskPaper(mouseX, mouseY)
         .flatMap(paper -> this.withdrawRemainingText(paper.taskId()))
         .ifPresent(text -> graphics.renderTooltip(this.font, text, mouseX, mouseY));
   }

   private Optional<GuildTaskBoardScreen.TaskPaper> hoveredAvailableTaskPaper(int mouseX, int mouseY) {
      int localX = (int)Math.floor((mouseX - this.leftPos) / this.boardScale);
      int localY = (int)Math.floor((mouseY - this.topPos) / this.boardScale);

      for (int i = this.taskPapers.size() - 1; i >= 0; i--) {
         GuildTaskBoardScreen.TaskPaper paper = this.taskPapers.get(i);
         if (paper.type() == GuildTaskBoardScreen.TaskPaperType.TASK && paper.taskId() != null && paper.bounds().contains(localX, localY)) {
            return Optional.of(paper);
         }
      }

      return Optional.empty();
   }

   private Optional<Component> withdrawRemainingText(ResourceLocation taskId) {
      if (taskId != null && this.minecraft != null && this.minecraft.player != null && this.minecraft.level != null) {
         GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
         Long withdrawAt = data.availableTaskWithdrawAt().get(taskId);
         if (withdrawAt != null && withdrawAt >= 0L) {
            long remainingTicks = Math.max(0L, withdrawAt - this.minecraft.level.getGameTime());
            long days = Math.max(0L, (remainingTicks + 28800L - 1L) / 28800L);
            boolean partyOnly = GuildTaskDefinitions.get(taskId).map(GuildTaskDefinitions.GuildTaskDefinition::partyOnly).orElse(false);
            return Optional.of(
               Component.translatable(
                  partyOnly ? "screen.tensura_guild.task.withdraw_remaining_party" : "screen.tensura_guild.task.withdraw_remaining_solo", new Object[]{days}
               )
            );
         } else {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   private int taskTimeLimitTicks(ResourceLocation taskId) {
      if (taskId != null && this.minecraft != null && this.minecraft.player != null) {
         GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
         Optional<GuildTaskBoardData.ActiveTask> activeTask = data.activeTask(taskId);
         if (activeTask.isPresent() && activeTask.get().taskTimeLimitTicks() > 0) {
            return activeTask.get().taskTimeLimitTicks();
         }

         Optional<GuildTaskDefinitions.GuildTaskDefinition> definition = GuildTaskDefinitions.get(taskId);
         if (definition.isPresent()) {
            return definition.get().effectiveTimeLimitTicks();
         }
      }

      return GuildConfig.commissionTimeLimitTicks();
   }

   private Optional<Component> partyTaskText(ResourceLocation taskId) {
      if (taskId != null && this.minecraft != null && this.minecraft.player != null) {
         GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
         return data.activeTask(taskId).flatMap(GuildTaskBoardData.ActiveTask::partyLeaderId).isEmpty()
            ? Optional.empty()
            : GuildPartyClient.currentPartyName().map(partyName -> Component.translatable("screen.tensura_guild.task.party", new Object[]{partyName}));
      } else {
         return Optional.empty();
      }
   }

   private Optional<Component> partyPausedText(ResourceLocation taskId) {
      return this.isPartyTaskPaused(taskId) ? Optional.of(Component.translatable("screen.tensura_guild.task.status.party_paused")) : Optional.empty();
   }

   private boolean isPartyTaskPaused(ResourceLocation taskId) {
      if (taskId != null && this.minecraft != null && this.minecraft.player != null) {
         GuildTaskDefinitions.GuildTaskDefinition task = GuildTaskDefinitions.get(taskId).orElse(null);
         if (task != null && task.partyOnly() && GuildPartyClient.isPartyUndermanned()) {
            GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
            return data.activeTask(taskId).flatMap(GuildTaskBoardData.ActiveTask::partyLeaderId).isPresent();
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private int drawWrappedPaperText(GuiGraphics graphics, Component text, int x, int y, int maxY) {
      for (FormattedCharSequence line : this.font.split(text, 114)) {
         if (y + 9 > maxY) {
            break;
         }

         graphics.drawString(this.font, line, x, y, -13950696, false);
         y += 9 + 2;
      }

      return y;
   }

   private int drawSmallPaperWrappedText(GuiGraphics graphics, Component text, int x, int y, int maxWidth, int maxY) {
      int splitWidth = Math.max(1, Math.round(maxWidth / 0.72F));
      int lineHeight = this.smallPaperLineHeight();

      for (FormattedCharSequence line : this.font.split(text, splitWidth)) {
         if (y + lineHeight > maxY) {
            break;
         }

         this.drawSmallPaperLine(graphics, line, x, y);
         y += lineHeight + 3;
      }

      return y;
   }

   private void drawSmallPaperRewardText(GuiGraphics graphics, Component text, ResourceLocation taskId, int x, int y, int maxWidth, int maxY) {
      if (y + this.smallPaperLineHeight() <= maxY) {
         Optional<GuildTaskDefinitions.GuildTaskDefinition> task = GuildTaskDefinitions.get(taskId);
         Component experience = (Component)task.filter(value -> value.experienceReward() > 0)
            .map(value -> Component.translatable("screen.tensura_guild.task.reward_experience_line", new Object[]{value.experienceReward()}))
            .orElse(Component.empty());
         int splitWidth = Math.max(1, Math.round(maxWidth / 0.72F));
         int drawY = y;
         int lastRewardY = y;
         FormattedCharSequence lastRewardLine = null;

         for (FormattedCharSequence line : this.font.split(text, splitWidth)) {
            if (drawY + this.smallPaperLineHeight() > maxY) {
               return;
            }

            this.drawSmallPaperLine(graphics, line, x, drawY);
            lastRewardLine = line;
            lastRewardY = drawY;
            drawY += this.smallPaperLineHeight() + 3;
         }

         int iconSize = Math.round(8.64F);
         int iconX = x + (lastRewardLine == null ? 0 : Math.round(this.font.width(lastRewardLine) * 0.72F)) + 3;
         int iconY = lastRewardY - 2;
         if (!this.createRewardIconStack(taskId).isEmpty() && lastRewardLine != null && iconX + iconSize <= x + maxWidth && iconY + iconSize <= maxY) {
            this.renderSmallRewardIcon(graphics, taskId, iconX, iconY);
         }

         if (!experience.getString().isEmpty()) {
            for (FormattedCharSequence line : this.font.split(experience, splitWidth)) {
               if (drawY + this.smallPaperLineHeight() > maxY) {
                  return;
               }

               this.drawSmallPaperLine(graphics, line, x, drawY);
               drawY += this.smallPaperLineHeight() + 3;
            }
         }
      }
   }

   private void drawSmallPaperLine(GuiGraphics graphics, FormattedCharSequence line, int x, int y) {
      graphics.pose().pushPose();

      try {
         graphics.pose().translate(x, y, 0.0F);
         graphics.pose().scale(0.72F, 0.72F, 1.0F);
         graphics.drawString(this.font, line, 0, 0, -13950696, false);
      } finally {
         graphics.pose().popPose();
      }
   }

   private int smallPaperLineHeight() {
      return Math.max(1, Math.round(9.0F * 0.72F));
   }

   private void drawPaperRewardText(GuiGraphics graphics, Component text, ResourceLocation taskId, int x, int y, int maxY) {
      if (y + 9 <= maxY) {
         Optional<GuildTaskDefinitions.GuildTaskDefinition> task = GuildTaskDefinitions.get(taskId);
         Component experience = (Component)task.filter(value -> value.experienceReward() > 0)
            .map(value -> Component.translatable("screen.tensura_guild.task.reward_experience_line", new Object[]{value.experienceReward()}))
            .orElse(Component.empty());
         List<FormattedCharSequence> rewardLines = this.font.split(text, 114);
         int drawY = y;
         FormattedCharSequence lastRewardLine = null;

         for (FormattedCharSequence line : rewardLines) {
            if (drawY + 9 > maxY) {
               return;
            }

            graphics.drawString(this.font, line, x, drawY, -13950696, false);
            lastRewardLine = line;
            drawY += 9 + 2;
         }

         int iconSize = Math.round(12.0F);
         int iconX = x + (lastRewardLine == null ? 0 : this.font.width(lastRewardLine)) + 3;
         int iconY = drawY - 9 - 4;
         if (!this.createRewardIconStack(taskId).isEmpty() && lastRewardLine != null && iconX + iconSize <= x + 114) {
            this.renderRewardIcon(graphics, taskId, iconX, iconY);
         }

         for (FormattedCharSequence line : this.font.split(experience, 114)) {
            if (drawY + 9 > maxY) {
               return;
            }

            graphics.drawString(this.font, line, x, drawY, -13950696, false);
            drawY += 9 + 2;
         }
      }
   }

   private Component rewardText(GuildTaskDefinitions.GuildTaskDefinition task) {
      Item item = (Item)BuiltInRegistries.ITEM.get(task.rewardItem());
      Component itemName = (Component)(item == Items.AIR ? Component.literal(task.rewardItem().toString()) : item.getDescription());
      return Component.translatable("screen.tensura_guild.task.reward_item", new Object[]{task.rewardCount(), itemName});
   }

   private void renderRewardIcon(GuiGraphics graphics, ResourceLocation taskId, int x, int y) {
      ItemStack stack = this.createRewardIconStack(taskId);
      if (!stack.isEmpty()) {
         graphics.pose().pushPose();

         try {
            graphics.pose().translate(x, y, 100.0F);
            graphics.pose().scale(0.75F, 0.75F, 1.0F);
            graphics.renderItem(stack, 0, 0);
         } finally {
            graphics.pose().popPose();
         }
      }
   }

   private void renderSmallRewardIcon(GuiGraphics graphics, ResourceLocation taskId, int x, int y) {
      ItemStack stack = this.createRewardIconStack(taskId);
      if (!stack.isEmpty()) {
         graphics.pose().pushPose();

         try {
            graphics.pose().translate(x, y, 100.0F);
            float iconScale = 0.54F;
            graphics.pose().scale(iconScale, iconScale, 1.0F);
            graphics.renderItem(stack, 0, 0);
         } finally {
            graphics.pose().popPose();
         }
      }
   }

   private ItemStack createRewardIconStack(ResourceLocation taskId) {
      Optional<GuildTaskDefinitions.GuildTaskDefinition> task = GuildTaskDefinitions.get(taskId);
      if (task.isEmpty()) {
         return ItemStack.EMPTY;
      }

      Item item = (Item)BuiltInRegistries.ITEM.get(task.get().rewardItem());
      return item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
   }

   private void renderTaskButton(GuiGraphics graphics, GuildTaskBoardScreen.TaskPaper paper, int mouseX, int mouseY) {
      GuildTaskBoardScreen.Rect source = this.taskButtonSource(paper);
      if (source != null) {
         boolean hovered = this.openPaper != null
            && !this.openPaper.closing()
            && this.openPaper.animationTicks() >= 10
            && this.isTaskButtonMouseOver(mouseX, mouseY);
         float buttonScale = hovered ? 1.08F : 1.0F;
         int drawWidth = Math.round(TASK_BUTTON_AREA.width() * buttonScale);
         int drawHeight = Math.round(TASK_BUTTON_AREA.height() * buttonScale);
         int drawX = TASK_BUTTON_AREA.left() + (TASK_BUTTON_AREA.width() - drawWidth) / 2;
         int drawY = TASK_BUTTON_AREA.top() + (TASK_BUTTON_AREA.height() - drawHeight) / 2;
         graphics.blit(CONTENT_TEXTURE, drawX, drawY, drawWidth, drawHeight, source.left(), source.top(), source.width(), source.height(), 256, 256);
      }
   }

   private boolean handleOpenPaperButtonClick(double mouseX, double mouseY) {
      if (this.openPaper != null
         && !this.openPaper.closing()
         && this.openPaper.animationTicks() >= 10
         && (
            this.openPaper.paper().type() == GuildTaskBoardScreen.TaskPaperType.TASK
               || this.openPaper.paper().type() == GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK
         )
         && this.minecraft != null
         && this.minecraft.player != null) {
         GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
         if (this.isTaskButtonMouseOver(mouseX, mouseY)) {
            ResourceLocation taskId = this.openPaper.paper().taskId();
            if (this.openPaper.paper().type() == GuildTaskBoardScreen.TaskPaperType.TASK
               && this.pendingAcceptTaskId == null
               && this.canAcceptMoreTasks(data, taskId)) {
               PacketDistributor.sendToServer(new AcceptGuildTaskPayload(taskId), new CustomPacketPayload[0]);
               this.pendingAcceptTaskId = taskId;
               this.pendingAcceptPaper = this.openPaper.paper();
               return true;
            }

            if (this.openPaper.paper().type() == GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK && this.isAcceptedTaskComplete(taskId)) {
               this.playAcceptSound();
               PacketDistributor.sendToServer(new CompleteGuildTaskPayload(taskId), new CustomPacketPayload[0]);
               if (this.openCarriedTask) {
                  if (this.minecraft != null) {
                     this.minecraft.setScreen(null);
                  }

                  return true;
               }

               this.openPaper = new GuildTaskBoardScreen.OpenTaskPaper(this.openPaper.paper(), true, 0);
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public void handleAcceptTaskResult(ResourceLocation taskId, boolean accepted) {
      if (this.pendingAcceptTaskId != null && taskId != null && this.pendingAcceptTaskId.equals(taskId)) {
         GuildTaskBoardScreen.TaskPaper acceptedPaper = this.pendingAcceptPaper;
         this.pendingAcceptTaskId = null;
         this.pendingAcceptPaper = null;
         if (!accepted) {
            if (this.openPaper != null
               && this.sameTaskPaper(this.openPaper.paper(), acceptedPaper)
               && this.minecraft != null
               && this.minecraft.player != null
               && !((GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD)).isTaskAvailable(taskId)
               && !this.openPaper.closing()) {
               this.openPaper = new GuildTaskBoardScreen.OpenTaskPaper(this.openPaper.paper(), true, 0);
            }
         } else if (acceptedPaper != null) {
            this.playAcceptSound();
            this.movingPaper = new GuildTaskBoardScreen.MovingTaskPaper(acceptedPaper, this.personalTaskPaper(taskId), 0);
            if (this.openPaper != null && this.sameTaskPaper(this.openPaper.paper(), acceptedPaper) && !this.openPaper.closing()) {
               this.openPaper = new GuildTaskBoardScreen.OpenTaskPaper(this.openPaper.paper(), true, 0);
            }
         }
      }
   }

   private boolean isTaskButtonMouseOver(double mouseX, double mouseY) {
      float left = this.width / 2.0F - 86.25F + TASK_BUTTON_AREA.left() * 1.25F;
      float top = this.height / 2.0F - 115.0F + TASK_BUTTON_AREA.top() * 1.25F;
      float right = left + TASK_BUTTON_AREA.width() * 1.25F;
      float bottom = top + TASK_BUTTON_AREA.height() * 1.25F;
      return mouseX >= left && mouseX < right && mouseY >= top && mouseY < bottom;
   }

   private void playAcceptSound() {
      if (this.minecraft != null) {
         this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F));
      }
   }

   private GuildTaskBoardScreen.Rect taskButtonSource(GuildTaskBoardScreen.TaskPaper paper) {
      if ((paper.type() == GuildTaskBoardScreen.TaskPaperType.TASK || paper.type() == GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK)
         && this.minecraft != null
         && this.minecraft.player != null) {
         GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
         if (paper.type() == GuildTaskBoardScreen.TaskPaperType.TASK && this.pendingAcceptTaskId == null && this.canAcceptMoreTasks(data, paper.taskId())) {
            return this.usesChineseTaskButton() ? ACCEPT_BUTTON_ZH_SRC : ACCEPT_BUTTON_EN_SRC;
         } else if (paper.type() == GuildTaskBoardScreen.TaskPaperType.ACCEPTED_TASK && this.isAcceptedTaskComplete(paper.taskId())) {
            return this.usesChineseTaskButton() ? COMPLETE_BUTTON_ZH_SRC : COMPLETE_BUTTON_EN_SRC;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private boolean canAcceptMoreTasks(GuildTaskBoardData data, ResourceLocation taskId) {
      if (this.minecraft == null || this.minecraft.player == null || taskId == null) {
         return false;
      }

      if (GuildPartyClient.isInParty() && !GuildPartyClient.isLocalPlayerLeader()) {
         return false;
      }

      int maxTasks = Math.max(2, ((GuildLevelData)this.minecraft.player.getData(GuildAttachments.GUILD_LEVEL)).levelIndex() + 1);
      return data.isTaskAvailable(taskId) && data.activeTaskCount() < maxTasks && !data.hasAcceptedTask(taskId);
   }

   private void closeUnavailableOpenTask(GuildTaskBoardData data) {
      if (this.openPaper != null
         && !this.openPaper.closing()
         && this.openPaper.paper().type() == GuildTaskBoardScreen.TaskPaperType.TASK
         && this.pendingAcceptTaskId == null) {
         ResourceLocation taskId = this.openPaper.paper().taskId();
         if (taskId != null && !data.isTaskAvailable(taskId)) {
            this.openPaper = new GuildTaskBoardScreen.OpenTaskPaper(this.openPaper.paper(), true, 0);
         }
      }
   }

   private boolean isAcceptedTaskComplete(ResourceLocation taskId) {
      if (taskId == null || this.minecraft == null || this.minecraft.player == null) {
         return false;
      } else if (this.isAcceptedTaskFailed(taskId)) {
         return false;
      } else {
         GuildTaskDefinitions.GuildTaskDefinition task = GuildTaskDefinitions.get(taskId).orElse(null);
         if (task == null) {
            return false;
         } else {
            return task.isItemTask()
               ? this.countInventoryItems(task) >= task.requiredCount()
               : ((GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD)).isTaskComplete(taskId);
         }
      }
   }

   private boolean isAcceptedTaskFailed(ResourceLocation taskId) {
      if (taskId != null && this.minecraft != null && this.minecraft.player != null && this.minecraft.level != null) {
         GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
         return data.hasFailedTask(taskId) || data.isTaskExpired(taskId, this.minecraft.level.getGameTime(), GuildConfig.commissionTimeLimitTicks());
      } else {
         return false;
      }
   }

   private boolean isBountyTask(ResourceLocation taskId) {
      return taskId != null && GuildTaskDefinitions.get(taskId).map(GuildTaskDefinitions.GuildTaskDefinition::isKillTask).orElse(false);
   }

   private int countInventoryItems(GuildTaskDefinitions.GuildTaskDefinition task) {
      if (this.minecraft != null && this.minecraft.player != null) {
         int count = 0;

         for (int slot = 0; slot < this.minecraft.player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = this.minecraft.player.getInventory().getItem(slot);
            if (this.matchesRequiredItem(stack, task)) {
               count += stack.getCount();
            }
         }

         return count;
      } else {
         return 0;
      }
   }

   private boolean matchesRequiredItem(ItemStack stack, GuildTaskDefinitions.GuildTaskDefinition task) {
      if (stack.isEmpty()) {
         return false;
      }

      if (task.requiredItem().isPresent()) {
         Item item = (Item)BuiltInRegistries.ITEM.get(task.requiredItem().get());
         if (item != Items.AIR && stack.is(item)) {
            return true;
         }
      }

      if (task.requiredItemTag().isPresent()) {
         TagKey<Item> tag = TagKey.create(Registries.ITEM, task.requiredItemTag().get());
         return stack.is(tag);
      } else {
         return false;
      }
   }

   private Component shorten(Component text, int maxWidth) {
      if (this.font.width(text) <= maxWidth) {
         return text;
      }

      String value = text.getString();
      String suffix = "...";
      int suffixWidth = this.font.width(suffix);
      int stopAt = 0;
      int i = 1;

      while (i <= value.length() && this.font.width(value.substring(0, i)) + suffixWidth <= maxWidth) {
         stopAt = i++;
      }

      return Component.literal(value.substring(0, stopAt) + suffix).setStyle(text.getStyle());
   }

   private boolean usesChineseTaskButton() {
      if (this.minecraft == null) {
         return false;
      }

      String language = this.minecraft.getLanguageManager().getSelected();
      return language.equals("zh_cn") || language.equals("zh_tw") || language.equals("zh_hk");
   }

   private record MovingTaskPaper(GuildTaskBoardScreen.TaskPaper from, GuildTaskBoardScreen.TaskPaper to, int animationTicks) {
      private GuildTaskBoardScreen.MovingTaskPaper tick() {
         return new GuildTaskBoardScreen.MovingTaskPaper(this.from, this.to, this.animationTicks + 1);
      }
   }

   private record OpenTaskPaper(GuildTaskBoardScreen.TaskPaper paper, boolean closing, int animationTicks) {
      private GuildTaskBoardScreen.OpenTaskPaper tick() {
         return new GuildTaskBoardScreen.OpenTaskPaper(this.paper, this.closing, this.animationTicks + 1);
      }
   }

   private record Rect(int left, int top, int right, int bottom) {
      private int width() {
         return this.right - this.left + 1;
      }

      private int height() {
         return this.bottom - this.top + 1;
      }

      private boolean contains(int x, int y) {
         return x >= this.left && x <= this.right && y >= this.top && y <= this.bottom;
      }

      private boolean intersects(GuildTaskBoardScreen.Rect other) {
         return this.left <= other.right && this.right >= other.left && this.top <= other.bottom && this.bottom >= other.top;
      }
   }

   private record TaskPaper(GuildTaskBoardScreen.TaskPaperType type, ResourceLocation taskId, int index, int x, int y) {
      private GuildTaskBoardScreen.Rect bounds() {
         return new GuildTaskBoardScreen.Rect(this.x, this.y, this.x + 26 - 1, this.y + 26 - 1);
      }
   }

   private enum TaskPaperType {
      TASK,
      ACCEPTED_TASK,
      FAILED_TASK,
      DEBUG;
   }
}
