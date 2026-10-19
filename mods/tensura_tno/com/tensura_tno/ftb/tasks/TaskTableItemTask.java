package com.tensura_tno.ftb.tasks;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ItemStackConfig;
import dev.ftb.mods.ftblibrary.config.Tristate;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbquests.integration.item_filtering.ItemMatchingSystem;
import dev.ftb.mods.ftbquests.integration.item_filtering.ItemMatchingSystem.ComponentMatchType;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.loot.RewardTable;
import dev.ftb.mods.ftbquests.quest.loot.WeightedReward;
import dev.ftb.mods.ftbquests.quest.reward.ItemReward;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class TaskTableItemTask extends Task {
   private static final int MAX_TRACKED_ITEMS = 63;
   private static final ComponentMatchType DEFAULT_MATCH_COMPONENTS = ComponentMatchType.FUZZY;
   private static final Method MATCH_WITH_LOOKUP = findMatchMethod(ItemStack.class, ItemStack.class, ComponentMatchType.class, Provider.class);
   private static final Method MATCH_LEGACY = findMatchMethod(ItemStack.class, ItemStack.class, ComponentMatchType.class);
   public static TaskType TASK_TABLE_ITEM_TASK;
   private List<ItemStack> items = new ArrayList<>();
   private String rewardTable = "";
   private boolean requireAll = false;
   private Tristate consumeItems = Tristate.DEFAULT;
   private ComponentMatchType matchComponents = DEFAULT_MATCH_COMPONENTS;

   public TaskTableItemTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return TASK_TABLE_ITEM_TASK;
   }

   public long getMaxProgress() {
      if (!this.requireAll) {
         return Math.max(1L, this.entries().size());
      } else {
         int size = this.entries().size();
         if (size <= 0) {
            return 1L;
         } else {
            return size >= 63 ? Long.MAX_VALUE : (1L << size) - 1L;
         }
      }
   }

   public String formatMaxProgress() {
      return this.requireAll ? Integer.toString(this.entries().size()) : "1";
   }

   public String formatProgress(TeamData teamData, long progress) {
      return this.requireAll ? Integer.toString(Long.bitCount(progress & this.getMaxProgress())) : Long.toString(Math.min(progress, 1L));
   }

   public boolean hideProgressNumbers() {
      return false;
   }

   public boolean consumesResources() {
      return this.consumeItems.get(this.getQuest().getChapter().consumeItems());
   }

   public boolean canInsertItem() {
      return this.consumesResources();
   }

   public boolean submitItemsOnInventoryChange() {
      return !this.requireAll && !this.consumesResources();
   }

   public int autoSubmitOnPlayerTick() {
      if (this.requireAll) {
         return 0;
      } else {
         return this.consumesResources() ? 0 : 20;
      }
   }

   public boolean checkOnLogin() {
      return !this.requireAll && !this.consumesResources();
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      ListTag list = new ListTag();

      for (ItemStack stack : this.sanitize(this.items)) {
         list.add(stack.save(provider));
      }

      nbt.put("items", list);
      if (!this.rewardTable.isEmpty()) {
         nbt.putString("reward_table", this.rewardTable);
      }

      if (this.requireAll) {
         nbt.putBoolean("require_all", true);
      }

      this.consumeItems.write(nbt, "consume_items");
      nbt.putString("match_components", ComponentMatchType.NAME_MAP.getName(this.matchComponents));
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      List<ItemStack> loaded = new ArrayList<>();
      ListTag list = nbt.getList("items", 10);

      for (int i = 0; i < list.size() && loaded.size() < 63; i++) {
         ItemStack stack = itemOrMissingFromNBT(list.getCompound(i), provider);
         if (!stack.isEmpty()) {
            loaded.add(stack.copy());
         }
      }

      this.items = loaded;
      this.rewardTable = nbt.getString("reward_table");
      this.requireAll = nbt.getBoolean("require_all");
      this.consumeItems = Tristate.read(nbt, "consume_items");
      this.matchComponents = nbt.contains("match_components")
         ? (ComponentMatchType)ComponentMatchType.NAME_MAP.get(nbt.getString("match_components"))
         : DEFAULT_MATCH_COMPONENTS;
      if (this.matchComponents == null) {
         this.matchComponents = DEFAULT_MATCH_COMPONENTS;
      }
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      List<ItemStack> safeItems = this.sanitize(this.items);
      buffer.writeVarInt(safeItems.size());

      for (ItemStack stack : safeItems) {
         ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, stack);
      }

      buffer.writeUtf(this.rewardTable);
      buffer.writeBoolean(this.requireAll);
      this.consumeItems.write(buffer);
      ComponentMatchType.NAME_MAP.write(buffer, this.matchComponents);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      int size = Math.min(buffer.readVarInt(), 63);
      List<ItemStack> loaded = new ArrayList<>();

      for (int i = 0; i < size; i++) {
         ItemStack stack = (ItemStack)ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
         if (!stack.isEmpty()) {
            loaded.add(stack.copy());
         }
      }

      this.items = loaded;
      this.rewardTable = buffer.readUtf();
      this.requireAll = buffer.readBoolean();
      this.consumeItems = Tristate.read(buffer);
      this.matchComponents = (ComponentMatchType)ComponentMatchType.NAME_MAP.read(buffer);
      if (this.matchComponents == null) {
         this.matchComponents = DEFAULT_MATCH_COMPONENTS;
      }
   }

   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addList("items", this.items, new ItemStackConfig(true, false), v -> this.items = this.sanitize(v), ItemStack.EMPTY)
         .setNameKey("tensura_tno.task.task_table_item.items");
      config.addString("reward_table", this.rewardTable, v -> this.rewardTable = v, "").setNameKey("tensura_tno.task.task_table_item.reward_table");
      config.addBool("require_all", this.requireAll, v -> this.requireAll = v, false).setNameKey("tensura_tno.task.task_table_item.require_all");
      config.addEnum("consume_items", this.consumeItems, v -> this.consumeItems = v, Tristate.NAME_MAP)
         .setNameKey("ftbquests.task.ftbquests.item.consume_items");
      config.addEnum("match_components", this.matchComponents, v -> this.matchComponents = v, ComponentMatchType.NAME_MAP)
         .setNameKey("ftbquests.task.ftbquests.item.match_components");
   }

   public MutableComponent getAltTitle() {
      return Component.translatable("ftbquests.task.tensura_tno.task_table_item");
   }

   public Icon getAltIcon() {
      List<ItemStack> safeItems = this.entries();
      return safeItems.isEmpty() ? Icons.CHECK : ItemIcon.getItemIcon(this.displayStack(safeItems.get(this.animatedIndex(safeItems.size()))));
   }

   public void addMouseOverText(TooltipList list, TeamData teamData) {
      List<ItemStack> safeItems = this.entries();
      if (safeItems.isEmpty()) {
         list.blankLine();
         list.add(Component.translatable("tensura_tno.task.task_table_item.no_items").withStyle(ChatFormatting.RED));
      } else {
         list.blankLine();
         list.add(
            Component.translatable(this.requireAll ? "tensura_tno.task.task_table_item.requires_all" : "tensura_tno.task.task_table_item.requires_any")
               .withStyle(ChatFormatting.GRAY)
         );
         long progress = teamData == null ? 0L : teamData.getProgress(this);
         int totalItems = safeItems.size();
         int pageSize = this.tooltipItemLimit(totalItems);
         int page = this.tooltipPage(totalItems, pageSize);
         int startIndex = page * pageSize;
         int endIndex = Math.min(totalItems, startIndex + pageSize);
         if (totalItems > pageSize) {
            list.add(
               Component.translatable(
                     "tensura_tno.task.task_table_item.showing_items",
                     new Object[]{startIndex + 1, endIndex, totalItems, page + 1, this.tooltipPageCount(totalItems, pageSize)}
                  )
                  .withStyle(ChatFormatting.DARK_GRAY)
            );
         }

         for (int i = startIndex; i < endIndex; i++) {
            ItemStack stack = safeItems.get(i);
            boolean done = this.requireAll ? this.isEntryDone(progress, i) : this.isAnyEntryDone(progress, i, totalItems);
            Component prefix = Component.literal(done ? "[x] " : "[ ] ").withStyle(done ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY);
            list.add(prefix.copy().append(this.title(stack)));
         }

         if (this.consumesResources() && teamData != null && !teamData.isCompleted(this)) {
            list.blankLine();
            list.add(Component.translatable("ftbquests.task.click_to_submit").withStyle(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.UNDERLINE}));
         }
      }
   }

   public void onButtonClicked(Button button, boolean canClick) {
      if (canClick) {
         super.onButtonClicked(button, true);
      } else {
         button.playClickSound();
      }
   }

   public void submitTask(TeamData teamData, ServerPlayer player, ItemStack craftedItem) {
      if (player != null && teamData != null && this.checkTaskSequence(teamData) && !teamData.isCompleted(this)) {
         List<ItemStack> safeItems = this.entries();
         if (!safeItems.isEmpty()) {
            if (!this.requireAll) {
               this.submitAny(teamData, player, safeItems);
            } else {
               this.submitAll(teamData, player, safeItems);
            }
         }
      }
   }

   private void submitAny(TeamData teamData, ServerPlayer player, List<ItemStack> safeItems) {
      if (this.consumesResources()) {
         for (int i = 0; i < safeItems.size(); i++) {
            ItemStack target = safeItems.get(i);
            if (this.consumeTarget(player, target)) {
               this.completeAny(teamData, i);
               return;
            }
         }
      } else {
         for (int i = 0; i < safeItems.size(); i++) {
            ItemStack target = safeItems.get(i);
            if (this.countMatching(player, target) >= target.getCount()) {
               this.completeAny(teamData, i);
               return;
            }
         }
      }
   }

   private void completeAny(TeamData teamData, int index) {
      teamData.setProgress(this, Math.min(Math.max(0, index) + 1L, this.getMaxProgress()));
      if (!teamData.isCompleted(this)) {
         teamData.markTaskCompleted(this);
      }
   }

   private void submitAll(TeamData teamData, ServerPlayer player, List<ItemStack> safeItems) {
      long progress = teamData.getProgress(this);

      for (int i = 0; i < safeItems.size(); i++) {
         long bit = 1L << i;
         if ((progress & bit) == 0L) {
            ItemStack target = safeItems.get(i);
            boolean submitted = this.consumesResources() ? this.consumeTarget(player, target) : this.countMatching(player, target) >= target.getCount();
            if (submitted) {
               progress |= bit;
            }
         }
      }

      teamData.setProgress(this, progress & this.getMaxProgress());
   }

   private boolean consumeTarget(ServerPlayer player, ItemStack target) {
      int remaining = target.getCount();
      List<ItemStack> stacks = player.getInventory().items;

      for (ItemStack stack : stacks) {
         if (remaining <= 0) {
            break;
         }

         if (this.matches(target, stack)) {
            remaining -= Math.min(remaining, stack.getCount());
         }
      }

      if (remaining > 0) {
         return false;
      }

      remaining = target.getCount();
      boolean changed = false;

      for (int i = 0; i < stacks.size() && remaining > 0; i++) {
         ItemStack stack = stacks.get(i);
         if (this.matches(target, stack)) {
            int take = Math.min(remaining, stack.getCount());
            stack.shrink(take);
            remaining -= take;
            changed = true;
            if (stack.isEmpty()) {
               stacks.set(i, ItemStack.EMPTY);
            }
         }
      }

      if (changed) {
         player.getInventory().setChanged();
         player.containerMenu.broadcastChanges();
      }

      return changed;
   }

   private long countMatching(ServerPlayer player, ItemStack target) {
      long total = 0L;

      for (ItemStack stack : player.getInventory().items) {
         if (this.matches(target, stack)) {
            total += stack.getCount();
            if (total >= target.getCount()) {
               return total;
            }
         }
      }

      return total;
   }

   private boolean matches(ItemStack target, ItemStack stack) {
      ItemStack filter = this.displayStack(target);
      if (stack.isEmpty()) {
         return false;
      }

      Boolean ftbResult = this.invokeFtbMatcher(filter, stack);
      return ftbResult != null ? ftbResult : this.fallbackMatches(filter, stack);
   }

   private Boolean invokeFtbMatcher(ItemStack filter, ItemStack stack) {
      if (MATCH_WITH_LOOKUP != null) {
         try {
            return (Boolean)MATCH_WITH_LOOKUP.invoke(ItemMatchingSystem.INSTANCE, filter, stack, this.matchComponents, this.getQuestFile().holderLookup());
         } catch (ReflectiveOperationException | LinkageError var5) {
         }
      }

      if (MATCH_LEGACY != null) {
         try {
            return (Boolean)MATCH_LEGACY.invoke(ItemMatchingSystem.INSTANCE, filter, stack, this.matchComponents);
         } catch (ReflectiveOperationException | LinkageError var4) {
         }
      }

      return null;
   }

   private static Method findMatchMethod(Class<?>... parameterTypes) {
      try {
         return ItemMatchingSystem.class.getMethod("doesItemMatch", parameterTypes);
      } catch (NoSuchMethodException ignored) {
         return null;
      }
   }

   private boolean fallbackMatches(ItemStack filter, ItemStack stack) {
      if (filter == stack) {
         return true;
      }

      if (filter.getItem() != stack.getItem()) {
         return false;
      }

      return switch (this.matchComponents) {
         case NONE -> true;
         case FUZZY -> filter.getComponents()
            .stream()
            .allMatch(component -> stack.getComponents().has(component.type()) && stack.getComponents().get(component.type()).equals(component.value()));
         case STRICT -> ItemStack.isSameItemSameComponents(filter, stack);
         default -> throw new MatchException(null, null);
      };
   }

   private boolean isEntryDone(long progress, int index) {
      return index >= 0 && index < 63 && (progress & 1L << index) != 0L;
   }

   private boolean isAnyEntryDone(long progress, int index, int totalItems) {
      if (progress > 0L && index >= 0 && totalItems > 0) {
         int submittedIndex = Math.min((int)progress, totalItems) - 1;
         return index == submittedIndex;
      } else {
         return false;
      }
   }

   private int animatedIndex(int size) {
      Minecraft minecraft = Minecraft.getInstance();
      return minecraft.level != null && size > 1 ? (int)(minecraft.level.getGameTime() / 20L % size) : 0;
   }

   private int tooltipItemLimit(int totalItems) {
      Minecraft minecraft = Minecraft.getInstance();
      int limit = 12;
      if (minecraft.screen != null) {
         int availableHeight = Math.max(80, minecraft.screen.height - 120);
         limit = Math.max(8, availableHeight / (9 + 2));
      }

      return Math.max(1, Math.min(totalItems, limit));
   }

   private int tooltipPage(int totalItems, int pageSize) {
      int pageCount = this.tooltipPageCount(totalItems, pageSize);
      return pageCount <= 1 ? 0 : (int)(System.currentTimeMillis() / 2000L % pageCount);
   }

   private int tooltipPageCount(int totalItems, int pageSize) {
      return pageSize <= 0 ? 1 : Math.max(1, (totalItems + pageSize - 1) / pageSize);
   }

   private List<ItemStack> entries() {
      List<ItemStack> safe = this.sanitize(this.items);
      if (safe.size() >= 63) {
         return safe;
      }

      RewardTable table = this.resolveRewardTable();
      if (table == null) {
         return safe;
      }

      for (WeightedReward weightedReward : table.getWeightedRewards()) {
         if (safe.size() >= 63) {
            break;
         }

         if (weightedReward.getReward() instanceof ItemReward itemReward && !itemReward.getItem().isEmpty()) {
            ItemStack stack = itemReward.getItem().copy();
            stack.setCount(Math.max(1, itemReward.getCount()));
            safe.add(stack);
         }
      }

      return safe;
   }

   private RewardTable resolveRewardTable() {
      if (this.rewardTable != null && !this.rewardTable.isBlank()) {
         RewardTable byId = this.getQuestFile().getRewardTable(this.getQuestFile().getID(this.rewardTable));
         if (byId != null) {
            return byId;
         }

         String normalized = this.rewardTable.toLowerCase(Locale.ROOT);

         for (RewardTable table : this.getQuestFile().getRewardTables()) {
            if (table.getFilename().equalsIgnoreCase(this.rewardTable)
               || table.getCodeString().equalsIgnoreCase(this.rewardTable)
               || table.getRawTitle().toLowerCase(Locale.ROOT).equals(normalized)) {
               return table;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private List<ItemStack> sanitize(List<ItemStack> source) {
      List<ItemStack> safe = new ArrayList<>();
      if (source == null) {
         return safe;
      }

      for (ItemStack stack : source) {
         if (safe.size() >= 63) {
            break;
         }

         if (stack != null && !stack.isEmpty()) {
            ItemStack copy = stack.copy();
            copy.setCount(Math.max(1, copy.getCount()));
            safe.add(copy);
         }
      }

      return safe;
   }

   private ItemStack displayStack(ItemStack stack) {
      ItemStack copy = stack.copy();
      copy.setCount(1);
      return copy;
   }

   private Component title(ItemStack stack) {
      return (Component)(stack.getCount() > 1 ? Component.literal(stack.getCount() + "x ").append(stack.getHoverName()) : stack.getHoverName());
   }
}
