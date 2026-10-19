package com.tensura_guild.client.screen;

import com.tensura_guild.client.GuildPartyClient;
import com.tensura_guild.data.DwarfTaskData;
import com.tensura_guild.data.DwarfTaskDefinitions;
import com.tensura_guild.data.GuildTaskBoardData;
import com.tensura_guild.data.GuildTaskDefinitions;
import com.tensura_guild.data.GuildTaskTime;
import com.tensura_guild.registry.GuildAttachments;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GuildTaskScreen extends Screen {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/task_gui.png");
   private static final ResourceLocation GUILD_TAB_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/guild_tab.png");
   private static final ResourceLocation PARTY_TAB_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/party_tab.png");
   private static final int TEXTURE_SIZE = 256;
   private static final int GUILD_TAB_TEXTURE_WIDTH = 48;
   private static final int GUILD_TAB_TEXTURE_HEIGHT = 21;
   private static final int GUILD_TAB_WIDTH = 24;
   private static final int GUILD_TAB_HEIGHT = 21;
   private static final int IMAGE_WIDTH = 256;
   private static final int IMAGE_HEIGHT = 163;
   private static final GuildTaskScreen.Rect BACK_BUTTON = new GuildTaskScreen.Rect(7, 2, 31, 23);
   private static final GuildTaskScreen.Rect PARTY_BUTTON = new GuildTaskScreen.Rect(59, 2, 83, 23);
   private static final GuildTaskScreen.Rect SEARCH_BOX = new GuildTaskScreen.Rect(165, 38, 236, 50);
   private static final GuildTaskScreen.Rect TASK_LIST = new GuildTaskScreen.Rect(163, 53, 219, 148);
   private static final GuildTaskScreen.Rect SCROLL_BAR = new GuildTaskScreen.Rect(222, 53, 232, 148);
   private static final GuildTaskScreen.Rect TASK_DETAIL = new GuildTaskScreen.Rect(14, 37, 159, 148);
   private static final int TASK_ROW_HEIGHT = 12;
   private static final int SCROLL_THUMB_HEIGHT = 13;
   private static final int TEXT_COLOR = -14671840;
   private static final int MUTED_TEXT_COLOR = -11184811;
   private static final int HOVER_FILL = 857874978;
   private static final int SELECTED_FILL = 1143087650;
   private static final int SCROLL_THUMB_COLOR = -1439485133;
   private static final float REWARD_ICON_SCALE = 0.75F;
   private final List<GuildTaskScreen.GuildTaskEntry> allTasks = new ArrayList<>();
   private final List<GuildTaskScreen.GuildTaskEntry> filteredTasks = new ArrayList<>();
   private EditBox searchBox;
   private GuildTaskScreen.GuildTaskEntry selectedTask;
   private int leftPos;
   private int topPos;
   private int listStartIndex;
   private float scrollOffset;
   private boolean scrolling;
   private String searchText = "";
   private List<GuildTaskBoardData.ActiveTask> lastActiveTasks = List.of();
   private DwarfTaskData lastDwarfTask = DwarfTaskData.EMPTY;

   public GuildTaskScreen() {
      super(Component.empty());
   }

   protected void init() {
      GuildPartyClient.requestSync();
      this.leftPos = (this.width - 256) / 2;
      this.topPos = (this.height - 163) / 2;
      this.allTasks.clear();
      this.allTasks.addAll(this.loadTasks());
      this.lastActiveTasks = this.currentActiveTasks();
      this.lastDwarfTask = this.currentDwarfTask();
      this.searchBox = new EditBox(
         this.font, this.leftPos + SEARCH_BOX.left(), this.topPos + SEARCH_BOX.top(), SEARCH_BOX.width(), SEARCH_BOX.height(), Component.empty()
      );
      this.searchBox.setBordered(false);
      this.searchBox.setMaxLength(64);
      this.searchBox.setTextColor(-14671840);
      this.searchBox.setTextColorUneditable(-11184811);
      this.searchBox.setTextShadow(false);
      this.searchBox.setResponder(this::setSearchText);
      this.addRenderableWidget(this.searchBox);
      this.updateFilteredTasks();
   }

   public void tick() {
      super.tick();
      List<GuildTaskBoardData.ActiveTask> activeTasks = this.currentActiveTasks();
      DwarfTaskData dwarfTask = this.currentDwarfTask();
      if (!activeTasks.equals(this.lastActiveTasks) || !dwarfTask.equals(this.lastDwarfTask)) {
         this.lastActiveTasks = activeTasks;
         this.lastDwarfTask = dwarfTask;
         GuildTaskScreen.GuildTaskEntry selected = this.selectedTask;
         this.allTasks.clear();
         this.allTasks.addAll(this.loadTasks());
         this.updateFilteredTasks();
         this.selectedTask = selected == null
            ? null
            : this.filteredTasks.stream().filter(task -> task.id().equals(selected.id()) && task.source() == selected.source()).findFirst().orElse(null);
      }
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0.0F, 0.0F, 256, 163, 256, 256);
      this.renderBackButton(graphics, mouseX, mouseY);
      this.renderPartyButton(graphics, mouseX, mouseY);
      this.renderTaskDetail(graphics);
      this.renderTaskList(graphics, mouseX, mouseY);
      this.renderScrollBar(graphics);
      super.render(graphics, mouseX, mouseY, partialTick);
   }

   private void renderBackButton(GuiGraphics graphics, int mouseX, int mouseY) {
      boolean hovered = this.isMouseOver(mouseX, mouseY, BACK_BUTTON);
      int uOffset = hovered ? 24 : 0;
      graphics.blit(GUILD_TAB_TEXTURE, this.leftPos + BACK_BUTTON.left(), this.topPos + BACK_BUTTON.top(), uOffset, 0.0F, 24, 21, 48, 21);
   }

   private void renderPartyButton(GuiGraphics graphics, int mouseX, int mouseY) {
      boolean hovered = this.isMouseOver(mouseX, mouseY, PARTY_BUTTON);
      int uOffset = hovered ? 24 : 0;
      graphics.blit(PARTY_TAB_TEXTURE, this.leftPos + PARTY_BUTTON.left(), this.topPos + PARTY_BUTTON.top(), uOffset, 0.0F, 24, 21, 48, 21);
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.isMouseOver(mouseX, mouseY, BACK_BUTTON)) {
         if (this.minecraft != null) {
            this.minecraft.setScreen(new AdventurerGuildScreen());
            ScreenHelper.clicked();
         }

         return true;
      } else if (button == 0 && this.isMouseOver(mouseX, mouseY, PARTY_BUTTON)) {
         if (this.minecraft != null) {
            this.minecraft.setScreen(new GuildPartyScreen());
            ScreenHelper.clicked();
         }

         return true;
      } else if (button == 0 && this.isMouseOver(mouseX, mouseY, SCROLL_BAR)) {
         this.scrolling = this.isScrollBarActive();
         if (this.scrolling) {
            this.updateScrollFromMouse(mouseY);
         }

         return true;
      } else if (button == 0 && this.isMouseOver(mouseX, mouseY, TASK_LIST)) {
         int index = this.listStartIndex + (int)((mouseY - (this.topPos + TASK_LIST.top())) / 12.0);
         if (index >= 0 && index < this.filteredTasks.size()) {
            this.selectedTask = this.filteredTasks.get(index);
            ScreenHelper.clicked();
         }

         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (this.scrolling) {
         this.updateScrollFromMouse(mouseY);
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      this.scrolling = false;
      return super.mouseReleased(mouseX, mouseY, button);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
      return (this.isMouseOver(mouseX, mouseY, TASK_LIST) || this.isMouseOver(mouseX, mouseY, SCROLL_BAR)) && this.scrollList(deltaY)
         ? true
         : super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
   }

   protected void renderBlurredBackground(float partialTick) {
   }

   public boolean isPauseScreen() {
      return false;
   }

   protected List<GuildTaskScreen.GuildTaskEntry> loadTasks() {
      if (this.minecraft != null && this.minecraft.player != null) {
         List<GuildTaskScreen.GuildTaskEntry> tasks = new ArrayList<>();
         GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);

         for (GuildTaskBoardData.ActiveTask activeTask : data.activeTasks()) {
            ResourceLocation taskId = activeTask.taskId();
            GuildTaskDefinitions.GuildTaskDefinition task = GuildTaskDefinitions.get(taskId).orElse(null);
            if (task == null) {
               tasks.add(new GuildTaskScreen.GuildTaskEntry(taskId, Component.literal(taskId.toString()), Component.empty(), GuildTaskScreen.TaskSource.GUILD));
            } else {
               Component description = Component.translatable(task.descriptionKey());
               Component reward = this.rewardSearchText(task);
               MutableComponent detail = Component.empty().append(description);
               if (activeTask.partyLeaderId().isPresent()) {
                  GuildPartyClient.currentPartyName()
                     .map(partyName -> Component.translatable("screen.tensura_guild.task.party", new Object[]{partyName}))
                     .ifPresent(party -> detail.append("\n").append(party));
                  if (this.isPartyTaskPaused(taskId, task)) {
                     detail.append("\n").append(Component.translatable("screen.tensura_guild.task.status.party_paused"));
                  }
               }

               this.targetCoordinatesText(activeTask).ifPresent(target -> detail.append("\n").append(target));
               tasks.add(
                  new GuildTaskScreen.GuildTaskEntry(
                     taskId, Component.translatable(task.titleKey()), detail.append("\n").append(reward), GuildTaskScreen.TaskSource.GUILD
                  )
               );
            }
         }

         DwarfTaskData dwarfTask = (DwarfTaskData)this.minecraft.player.getData(GuildAttachments.DWARF_TASK);
         if (dwarfTask.hasActiveTask()) {
            dwarfTask.taskId()
               .flatMap(DwarfTaskDefinitions::get)
               .ifPresent(
                  taskx -> {
                     Component descriptionx = Component.translatable(taskx.descriptionKey());
                     Component rewardx = this.dwarfRewardSearchText(taskx);
                     tasks.add(
                        new GuildTaskScreen.GuildTaskEntry(
                           dwarfTask.taskId().orElseThrow(),
                           Component.translatable(taskx.titleKey()),
                           Component.empty().append(descriptionx).append("\n").append(rewardx),
                           GuildTaskScreen.TaskSource.DWARF
                        )
                     );
                  }
               );
         }

         return tasks;
      } else {
         return List.of();
      }
   }

   private List<GuildTaskBoardData.ActiveTask> currentActiveTasks() {
      return this.minecraft != null && this.minecraft.player != null
         ? ((GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD)).activeTasks()
         : List.of();
   }

   private DwarfTaskData currentDwarfTask() {
      return this.minecraft != null && this.minecraft.player != null
         ? (DwarfTaskData)this.minecraft.player.getData(GuildAttachments.DWARF_TASK)
         : DwarfTaskData.EMPTY;
   }

   private void setSearchText(String value) {
      this.searchText = value == null ? "" : value;
      this.listStartIndex = 0;
      this.scrollOffset = 0.0F;
      this.updateFilteredTasks();
   }

   private void updateFilteredTasks() {
      this.filteredTasks.clear();
      String filter = this.searchText.trim().toLowerCase(Locale.ROOT);

      for (GuildTaskScreen.GuildTaskEntry task : this.allTasks) {
         if (filter.isEmpty() || task.matches(filter)) {
            this.filteredTasks.add(task);
         }
      }

      if (this.selectedTask != null && !this.filteredTasks.contains(this.selectedTask)) {
         this.selectedTask = null;
      }

      this.clampScrollState();
   }

   private void renderTaskList(GuiGraphics graphics, int mouseX, int mouseY) {
      int listLeft = this.leftPos + TASK_LIST.left();
      int listTop = this.topPos + TASK_LIST.top();
      int listRight = this.leftPos + TASK_LIST.right() + 1;
      int listBottom = this.topPos + TASK_LIST.bottom() + 1;
      graphics.enableScissor(listLeft, listTop, listRight, listBottom);

      try {
         if (this.filteredTasks.isEmpty()) {
            return;
         }

         int visibleRows = this.getVisibleRows();
         int rowY = listTop;

         for (int row = 0; row < visibleRows; row++) {
            int index = this.listStartIndex + row;
            if (index >= this.filteredTasks.size()) {
               break;
            }

            GuildTaskScreen.GuildTaskEntry task = this.filteredTasks.get(index);
            boolean selected = task.equals(this.selectedTask);
            boolean hovered = this.isMouseOver(mouseX, mouseY, listLeft, rowY, TASK_LIST.width(), 12);
            if (selected || hovered) {
               graphics.fill(listLeft, rowY, listRight, rowY + 12, selected ? 1143087650 : 857874978);
            }

            Component title = this.shorten(task.title(), TASK_LIST.width() - 4);
            graphics.drawString(this.font, title, listLeft + 2, rowY + 2, -14671840, false);
            rowY += 12;
         }
      } finally {
         graphics.disableScissor();
      }
   }

   private void renderTaskDetail(GuiGraphics graphics) {
      GuildTaskScreen.GuildTaskEntry task = this.selectedTask;
      if (task != null) {
         GuildTaskScreen.Rect textArea = new GuildTaskScreen.Rect(TASK_DETAIL.left(), TASK_DETAIL.top(), TASK_DETAIL.right(), TASK_DETAIL.bottom() - 9 * 2 - 4);
         if (task.source() == GuildTaskScreen.TaskSource.DWARF) {
            this.renderDwarfTaskDetail(graphics, task.id(), textArea);
         } else {
            this.renderGuildTaskDetail(graphics, task.id(), textArea);
         }

         this.renderTaskStatus(graphics, task);
      }
   }

   private void renderGuildTaskDetail(GuiGraphics graphics, ResourceLocation taskId, GuildTaskScreen.Rect area) {
      GuildTaskDefinitions.GuildTaskDefinition task = GuildTaskDefinitions.get(taskId).orElse(null);
      if (task == null) {
         this.drawWrappedText(graphics, Component.literal(taskId.toString()), area, -14671840);
      } else {
         int x = this.leftPos + area.left();
         int y = this.topPos + area.top();
         int right = this.leftPos + area.right() + 1;
         int bottom = this.topPos + area.bottom() + 1;
         graphics.enableScissor(x, y, right, bottom);

         try {
            int drawY = y;
            Component description = Component.translatable(task.descriptionKey());

            for (FormattedCharSequence line : this.font.split(description, area.width())) {
               if (drawY + 9 > bottom) {
                  return;
               }

               graphics.drawString(this.font, line, x, drawY, -14671840, false);
               drawY += 9 + 1;
            }

            Optional<Component> partyText = this.partyTaskText(taskId);
            if (partyText.isPresent()) {
               drawY += 2;

               for (FormattedCharSequence line : this.font.split((FormattedText)partyText.get(), area.width())) {
                  if (drawY + 9 > bottom) {
                     return;
                  }

                  graphics.drawString(this.font, line, x, drawY, -14671840, false);
                  drawY += 9 + 1;
               }
            }

            Optional<Component> partyPausedText = this.partyPausedText(taskId, task);
            if (partyPausedText.isPresent()) {
               drawY += 2;

               for (FormattedCharSequence line : this.font.split((FormattedText)partyPausedText.get(), area.width())) {
                  if (drawY + 9 > bottom) {
                     return;
                  }

                  graphics.drawString(this.font, line, x, drawY, -14671840, false);
                  drawY += 9 + 1;
               }
            }

            Optional<Component> targetText = this.targetCoordinatesText(taskId);
            if (targetText.isPresent()) {
               drawY += 2;

               for (FormattedCharSequence line : this.font.split((FormattedText)targetText.get(), area.width())) {
                  if (drawY + 9 > bottom) {
                     return;
                  }

                  graphics.drawString(this.font, line, x, drawY, -14671840, false);
                  drawY += 9 + 1;
               }
            }

            drawY += 4;
            if (drawY + 9 <= bottom) {
               this.drawRewardText(graphics, task, taskId, x, drawY, area.width());
            }
         } finally {
            graphics.disableScissor();
         }
      }
   }

   private void renderDwarfTaskDetail(GuiGraphics graphics, ResourceLocation taskId, GuildTaskScreen.Rect area) {
      DwarfTaskDefinitions.DwarfTaskDefinition task = DwarfTaskDefinitions.get(taskId).orElse(null);
      if (task == null) {
         this.drawWrappedText(graphics, Component.literal(taskId.toString()), area, -14671840);
      } else {
         int x = this.leftPos + area.left();
         int y = this.topPos + area.top();
         int right = this.leftPos + area.right() + 1;
         int bottom = this.topPos + area.bottom() + 1;
         graphics.enableScissor(x, y, right, bottom);

         try {
            int drawY = y;
            Component description = Component.translatable(task.descriptionKey());

            for (FormattedCharSequence line : this.font.split(description, area.width())) {
               if (drawY + 9 > bottom) {
                  return;
               }

               graphics.drawString(this.font, line, x, drawY, -14671840, false);
               drawY += 9 + 1;
            }

            drawY += 4;
            if (drawY + 9 <= bottom) {
               this.drawDwarfRewardText(graphics, task, taskId, x, drawY, area.width());
            }
         } finally {
            graphics.disableScissor();
         }
      }
   }

   private void renderTaskStatus(GuiGraphics graphics, GuildTaskScreen.GuildTaskEntry task) {
      if (this.minecraft != null && this.minecraft.player != null) {
         if (task.source() == GuildTaskScreen.TaskSource.DWARF) {
            this.renderDwarfTaskStatus(graphics, task.id());
         } else {
            GuildTaskDefinitions.GuildTaskDefinition definition = GuildTaskDefinitions.get(task.id()).orElse(null);
            if (definition != null) {
               GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
               int currentAmount = this.currentTaskAmount(task.id(), definition, data);
               GuildTaskBoardData.ActiveTask activeTask = data.activeTask(task.id()).orElse(null);
               Component status = (Component)(this.isPartyTaskPaused(task.id(), definition)
                  ? Component.translatable("screen.tensura_guild.task.status.party_paused")
                  : (
                     this.isTaskComplete(task.id(), definition, data)
                        ? Component.translatable("screen.tensura_guild.task.status.complete")
                        : this.taskProgressText(definition, activeTask, currentAmount)
                  ));
               Component var16 = this.shorten(status, TASK_DETAIL.width());
               int statusX = this.leftPos + TASK_DETAIL.right() + 1 - this.font.width(var16);
               int statusY = this.topPos + TASK_DETAIL.bottom() + 1 - 9;
               int timeLimitTicks = activeTask != null && activeTask.taskTimeLimitTicks() > 0
                  ? activeTask.taskTimeLimitTicks()
                  : definition.effectiveTimeLimitTicks();
               if (activeTask != null && timeLimitTicks > 0 && this.minecraft.level != null) {
                  long remainingTicks = data.remainingTaskTicks(task.id(), this.minecraft.level.getGameTime(), timeLimitTicks).orElse(0L);
                  Component remaining = Component.translatable(
                     "screen.tensura_guild.task.remaining_time", new Object[]{GuildTaskTime.formatCommissionDayDurationText(remainingTicks)}
                  );
                  Component var17 = this.shorten(remaining, TASK_DETAIL.width());
                  int remainingX = this.leftPos + TASK_DETAIL.right() + 1 - this.font.width(var17);
                  int remainingY = statusY - 9 - 2;
                  graphics.drawString(this.font, var17, remainingX, remainingY, -14671840, false);
               }

               graphics.drawString(this.font, var16, statusX, statusY, -14671840, false);
            }
         }
      }
   }

   private void renderDwarfTaskStatus(GuiGraphics graphics, ResourceLocation taskId) {
      if (this.minecraft != null && this.minecraft.player != null) {
         DwarfTaskDefinitions.DwarfTaskDefinition definition = DwarfTaskDefinitions.get(taskId).orElse(null);
         DwarfTaskData data = (DwarfTaskData)this.minecraft.player.getData(GuildAttachments.DWARF_TASK);
         if (definition != null && data.hasActiveTask()) {
            int requiredCount = data.requiredCountOrDefault(definition.requiredCount());
            int currentAmount = Math.min(this.countInventoryItems(definition), requiredCount);
            Component status = currentAmount >= requiredCount
               ? Component.translatable("screen.tensura_guild.task.status.complete")
               : Component.translatable("screen.tensura_guild.task.status.item_progress", new Object[]{currentAmount, requiredCount});
            Component var15 = this.shorten(status, TASK_DETAIL.width());
            int statusX = this.leftPos + TASK_DETAIL.right() + 1 - this.font.width(var15);
            int statusY = this.topPos + TASK_DETAIL.bottom() + 1 - 9;
            if (this.minecraft.level != null && definition.timeLimitTicks() > 0) {
               long remainingTicks = data.remainingTicks(this.minecraft.level.getGameTime(), definition.timeLimitTicks());
               Component remaining = Component.translatable(
                  "screen.tensura_guild.task.remaining_time", new Object[]{GuildTaskTime.formatCommissionDayDurationText(remainingTicks)}
               );
               Component var16 = this.shorten(remaining, TASK_DETAIL.width());
               int remainingX = this.leftPos + TASK_DETAIL.right() + 1 - this.font.width(var16);
               int remainingY = statusY - 9 - 2;
               graphics.drawString(this.font, var16, remainingX, remainingY, -14671840, false);
            }

            graphics.drawString(this.font, var15, statusX, statusY, -14671840, false);
         }
      }
   }

   private Component taskProgressText(GuildTaskDefinitions.GuildTaskDefinition task, GuildTaskBoardData.ActiveTask activeTask, int currentAmount) {
      if (task.isItemTask()) {
         return Component.translatable("screen.tensura_guild.task.status.item_progress", new Object[]{currentAmount, task.requiredCount()});
      }

      if (task.isVisitStructureTask()) {
         return Component.translatable("screen.tensura_guild.task.status.visit_progress", new Object[]{currentAmount, task.requiredCount()});
      }

      int requiredKills = activeTask == null ? task.requiredKills() : activeTask.requiredCountOrDefault(task.requiredKills());
      return Component.translatable("screen.tensura_guild.task.status.kill_progress", new Object[]{currentAmount, requiredKills});
   }

   private boolean isTaskComplete(ResourceLocation taskId, GuildTaskDefinitions.GuildTaskDefinition task, GuildTaskBoardData data) {
      return task.isItemTask() ? this.countInventoryItems(task) >= task.requiredCount() : data.isTaskComplete(taskId);
   }

   private int currentTaskAmount(ResourceLocation taskId, GuildTaskDefinitions.GuildTaskDefinition task, GuildTaskBoardData data) {
      return task.isItemTask() ? Math.min(this.countInventoryItems(task), task.requiredCount()) : data.taskProgress(taskId);
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

   private int countInventoryItems(DwarfTaskDefinitions.DwarfTaskDefinition task) {
      if (this.minecraft != null && this.minecraft.player != null) {
         if (task.hasRequiredItemSet()) {
            int count = 0;

            for (ResourceLocation itemId : task.requiredItems()) {
               Item item = (Item)BuiltInRegistries.ITEM.get(itemId);
               if (item != Items.AIR && this.countInventoryItem(item) > 0) {
                  count++;
               }
            }

            return count;
         } else {
            Item item = (Item)BuiltInRegistries.ITEM.get(task.requiredItem());
            return item == Items.AIR ? 0 : this.countInventoryItem(item);
         }
      } else {
         return 0;
      }
   }

   private int countInventoryItem(Item item) {
      int count = 0;

      for (int slot = 0; slot < this.minecraft.player.getInventory().getContainerSize(); slot++) {
         ItemStack stack = this.minecraft.player.getInventory().getItem(slot);
         if (stack.is(item)) {
            count += stack.getCount();
         }
      }

      return count;
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

   private void drawRewardText(GuiGraphics graphics, GuildTaskDefinitions.GuildTaskDefinition task, ResourceLocation taskId, int x, int y, int maxWidth) {
      Component reward = this.rewardText(task);
      Component experience = task.experienceReward() > 0
         ? Component.translatable("screen.tensura_guild.task.reward_experience_line", new Object[]{task.experienceReward()})
         : Component.empty();
      int drawY = y;
      FormattedCharSequence lastRewardLine = null;

      for (FormattedCharSequence line : this.font.split(reward, maxWidth)) {
         graphics.drawString(this.font, line, x, drawY, -14671840, false);
         lastRewardLine = line;
         drawY += 9 + 1;
      }

      int iconSize = Math.round(12.0F);
      int iconX = x + (lastRewardLine == null ? 0 : this.font.width(lastRewardLine)) + 3;
      int iconY = drawY - 9 - 3;
      if (!this.createRewardIconStack(taskId).isEmpty() && lastRewardLine != null && iconX + iconSize <= x + maxWidth) {
         this.renderRewardIcon(graphics, taskId, iconX, iconY);
      }

      for (FormattedCharSequence line : this.font.split(experience, maxWidth)) {
         graphics.drawString(this.font, line, x, drawY, -14671840, false);
         drawY += 9 + 1;
      }
   }

   private void drawDwarfRewardText(GuiGraphics graphics, DwarfTaskDefinitions.DwarfTaskDefinition task, ResourceLocation taskId, int x, int y, int maxWidth) {
      Component reward = this.dwarfRewardText(task);
      int drawY = y;
      FormattedCharSequence lastRewardLine = null;

      for (FormattedCharSequence line : this.font.split(reward, maxWidth)) {
         graphics.drawString(this.font, line, x, drawY, -14671840, false);
         lastRewardLine = line;
         drawY += 9 + 1;
      }

      int iconSize = Math.round(12.0F);
      int iconX = x + (lastRewardLine == null ? 0 : this.font.width(lastRewardLine)) + 3;
      int iconY = drawY - 9 - 3;
      if (!this.createDwarfRewardIconStack(taskId).isEmpty() && lastRewardLine != null && iconX + iconSize <= x + maxWidth) {
         this.renderDwarfRewardIcon(graphics, taskId, iconX, iconY);
      }

      Optional<Component> randomBonus = this.dwarfRandomBonusLine(task);
      if (randomBonus.isPresent()) {
         for (FormattedCharSequence line : this.font.split((FormattedText)randomBonus.get(), maxWidth)) {
            graphics.drawString(this.font, line, x, drawY, -14671840, false);
            drawY += 9 + 1;
         }
      }

      if (task.dwarfReputationReward() > 0) {
         Component reputation = Component.translatable("screen.tensura_guild.dwarf_task.reward_reputation_line", new Object[]{task.dwarfReputationReward()});

         for (FormattedCharSequence line : this.font.split(reputation, maxWidth)) {
            graphics.drawString(this.font, line, x, drawY, -14671840, false);
            drawY += 9 + 1;
         }
      }

      if (task.experienceReward() > 0) {
         Component experience = Component.translatable("screen.tensura_guild.task.reward_experience_line", new Object[]{task.experienceReward()});

         for (FormattedCharSequence line : this.font.split(experience, maxWidth)) {
            graphics.drawString(this.font, line, x, drawY, -14671840, false);
            drawY += 9 + 1;
         }
      }
   }

   private Component rewardSearchText(GuildTaskDefinitions.GuildTaskDefinition task) {
      Component reward = this.rewardText(task);
      return (Component)(task.experienceReward() <= 0
         ? reward
         : Component.empty()
            .append(reward)
            .append(Component.translatable("screen.tensura_guild.task.reward_experience", new Object[]{task.experienceReward()})));
   }

   private Component dwarfRewardSearchText(DwarfTaskDefinitions.DwarfTaskDefinition task) {
      Component reward = this.dwarfRewardText(task);
      MutableComponent result = Component.empty().append(reward);
      this.dwarfRandomBonusSearchText(task).ifPresent(result::append);
      if (task.dwarfReputationReward() > 0) {
         result.append(Component.translatable("screen.tensura_guild.dwarf_task.reward_reputation", new Object[]{task.dwarfReputationReward()}));
      }

      if (task.experienceReward() > 0) {
         result.append(Component.translatable("screen.tensura_guild.task.reward_experience", new Object[]{task.experienceReward()}));
      }

      return result;
   }

   private Component rewardText(GuildTaskDefinitions.GuildTaskDefinition task) {
      Item item = (Item)BuiltInRegistries.ITEM.get(task.rewardItem());
      Component itemName = (Component)(item == Items.AIR ? Component.literal(task.rewardItem().toString()) : item.getDescription());
      return Component.translatable("screen.tensura_guild.task.reward_item", new Object[]{task.rewardCount(), itemName});
   }

   private Component dwarfRewardText(DwarfTaskDefinitions.DwarfTaskDefinition task) {
      Item item = (Item)BuiltInRegistries.ITEM.get(task.rewardItem());
      Component itemName = (Component)(item == Items.AIR ? Component.literal(task.rewardItem().toString()) : item.getDescription());
      return Component.translatable("screen.tensura_guild.task.reward_item", new Object[]{task.rewardCount(), itemName});
   }

   private Optional<Component> dwarfRandomBonusSearchText(DwarfTaskDefinitions.DwarfTaskDefinition task) {
      return !task.hasRandomBonusRewards()
         ? Optional.empty()
         : Optional.of(
            Component.translatable(
               "screen.tensura_guild.dwarf_task.reward_random_bonus_search", new Object[]{task.randomBonusMinCount(), task.randomBonusMaxCount()}
            )
         );
   }

   private Optional<Component> dwarfRandomBonusLine(DwarfTaskDefinitions.DwarfTaskDefinition task) {
      if (!task.hasRandomBonusRewards()) {
         return Optional.empty();
      } else {
         return task.randomBonusTextKey().isPresent()
            ? Optional.of(Component.translatable(task.randomBonusTextKey().get(), new Object[]{task.randomBonusMinCount(), task.randomBonusMaxCount()}))
            : Optional.of(
               Component.translatable(
                  "screen.tensura_guild.dwarf_task.reward_random_bonus_line", new Object[]{task.randomBonusMinCount(), task.randomBonusMaxCount()}
               )
            );
      }
   }

   private Optional<Component> partyTaskText(ResourceLocation taskId) {
      if (this.minecraft != null && this.minecraft.player != null) {
         GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
         return data.activeTask(taskId).flatMap(GuildTaskBoardData.ActiveTask::partyLeaderId).isEmpty()
            ? Optional.empty()
            : GuildPartyClient.currentPartyName().map(partyName -> Component.translatable("screen.tensura_guild.task.party", new Object[]{partyName}));
      } else {
         return Optional.empty();
      }
   }

   private Optional<Component> partyPausedText(ResourceLocation taskId, GuildTaskDefinitions.GuildTaskDefinition task) {
      return this.isPartyTaskPaused(taskId, task) ? Optional.of(Component.translatable("screen.tensura_guild.task.status.party_paused")) : Optional.empty();
   }

   private Optional<Component> targetCoordinatesText(ResourceLocation taskId) {
      return taskId != null && this.minecraft != null && this.minecraft.player != null
         ? ((GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD)).activeTask(taskId).flatMap(this::targetCoordinatesText)
         : Optional.empty();
   }

   private Optional<Component> targetCoordinatesText(GuildTaskBoardData.ActiveTask task) {
      if (task.targetX().isEmpty() || task.targetZ().isEmpty()) {
         return Optional.empty();
      } else if (this.isActiveTaskComplete(task)) {
         return GuildTaskDefinitions.get(task.taskId()).map(GuildTaskDefinitions.GuildTaskDefinition::isVisitStructureTask).orElse(false)
            ? Optional.of(Component.translatable("screen.tensura_guild.task.target_reached"))
            : Optional.of(Component.translatable("screen.tensura_guild.task.target_defeated"));
      } else {
         return Optional.of(Component.translatable("screen.tensura_guild.task.target_coordinates", new Object[]{task.targetX().get(), task.targetZ().get()}));
      }
   }

   private boolean isActiveTaskComplete(GuildTaskBoardData.ActiveTask activeTask) {
      return GuildTaskDefinitions.get(activeTask.taskId())
         .filter(task -> task.isKillTask() || task.isVisitStructureTask())
         .map(task -> activeTask.taskProgress() >= activeTask.requiredCountOrDefault(task.requiredCount()))
         .orElse(false);
   }

   private boolean isPartyTaskPaused(ResourceLocation taskId, GuildTaskDefinitions.GuildTaskDefinition task) {
      if (taskId != null
         && task != null
         && task.partyOnly()
         && this.minecraft != null
         && this.minecraft.player != null
         && GuildPartyClient.isPartyUndermanned()) {
         GuildTaskBoardData data = (GuildTaskBoardData)this.minecraft.player.getData(GuildAttachments.GUILD_TASK_BOARD);
         return data.activeTask(taskId).flatMap(GuildTaskBoardData.ActiveTask::partyLeaderId).isPresent();
      } else {
         return false;
      }
   }

   private void renderScrollBar(GuiGraphics graphics) {
      if (this.isScrollBarActive()) {
         int x = this.leftPos + SCROLL_BAR.left();
         int y = this.topPos + SCROLL_BAR.top() + Math.round((SCROLL_BAR.height() - 13) * this.scrollOffset);
         graphics.fill(x + 1, y, this.leftPos + SCROLL_BAR.right(), y + 13, -1439485133);
      }
   }

   private boolean scrollList(double deltaY) {
      if (!this.isScrollBarActive()) {
         return false;
      }

      int maxStart = this.getMaxListStartIndex();
      this.listStartIndex = Mth.clamp(this.listStartIndex - (int)Math.signum(deltaY), 0, maxStart);
      this.scrollOffset = maxStart == 0 ? 0.0F : (float)this.listStartIndex / maxStart;
      return true;
   }

   private void updateScrollFromMouse(double mouseY) {
      if (this.isScrollBarActive()) {
         int top = this.topPos + SCROLL_BAR.top();
         int maxStart = this.getMaxListStartIndex();
         this.scrollOffset = Mth.clamp((float)((mouseY - top - 6.5) / (SCROLL_BAR.height() - 13)), 0.0F, 1.0F);
         this.listStartIndex = Mth.clamp(Math.round(this.scrollOffset * maxStart), 0, maxStart);
      }
   }

   private void clampScrollState() {
      int maxStart = this.getMaxListStartIndex();
      this.listStartIndex = Mth.clamp(this.listStartIndex, 0, maxStart);
      this.scrollOffset = maxStart == 0 ? 0.0F : (float)this.listStartIndex / maxStart;
   }

   private boolean isScrollBarActive() {
      return this.filteredTasks.size() > this.getVisibleRows();
   }

   private int getVisibleRows() {
      return Math.max(1, TASK_LIST.height() / 12);
   }

   private int getMaxListStartIndex() {
      return Math.max(0, this.filteredTasks.size() - this.getVisibleRows());
   }

   private void drawWrappedText(GuiGraphics graphics, Component text, GuildTaskScreen.Rect area, int color) {
      int x = this.leftPos + area.left();
      int y = this.topPos + area.top();
      int right = this.leftPos + area.right() + 1;
      int bottom = this.topPos + area.bottom() + 1;
      graphics.enableScissor(x, y, right, bottom);

      try {
         int drawY = y;

         for (FormattedCharSequence line : this.font.split(text, area.width())) {
            if (drawY + 9 > bottom) {
               break;
            }

            graphics.drawString(this.font, line, x, drawY, color, false);
            drawY += 9 + 1;
         }
      } finally {
         graphics.disableScissor();
      }
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

   private void renderDwarfRewardIcon(GuiGraphics graphics, ResourceLocation taskId, int x, int y) {
      ItemStack stack = this.createDwarfRewardIconStack(taskId);
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

   private ItemStack createRewardIconStack(ResourceLocation taskId) {
      GuildTaskDefinitions.GuildTaskDefinition task = GuildTaskDefinitions.get(taskId).orElse(null);
      if (task == null) {
         return ItemStack.EMPTY;
      }

      Item item = (Item)BuiltInRegistries.ITEM.get(task.rewardItem());
      return item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
   }

   private ItemStack createDwarfRewardIconStack(ResourceLocation taskId) {
      DwarfTaskDefinitions.DwarfTaskDefinition task = DwarfTaskDefinitions.get(taskId).orElse(null);
      if (task == null) {
         return ItemStack.EMPTY;
      }

      Item item = (Item)BuiltInRegistries.ITEM.get(task.rewardItem());
      return item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
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

   private boolean isMouseOver(double mouseX, double mouseY, GuildTaskScreen.Rect rect) {
      return this.isMouseOver(mouseX, mouseY, this.leftPos + rect.left(), this.topPos + rect.top(), rect.width(), rect.height());
   }

   private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int width, int height) {
      return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
   }

   protected record GuildTaskEntry(ResourceLocation id, Component title, Component detail, GuildTaskScreen.TaskSource source) {
      private boolean matches(String filter) {
         return this.title.getString().toLowerCase(Locale.ROOT).contains(filter) || this.detail.getString().toLowerCase(Locale.ROOT).contains(filter);
      }
   }

   private record Rect(int left, int top, int right, int bottom) {
      private int width() {
         return this.right - this.left + 1;
      }

      private int height() {
         return this.bottom - this.top + 1;
      }
   }

   private enum TaskSource {
      GUILD,
      DWARF;
   }
}
