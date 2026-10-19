package dev.ftb.mods.ftbquests.quest;

import dev.architectury.event.EventActor;
import dev.architectury.utils.Env;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ItemStackConfig;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftblibrary.snbt.SNBT;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.api.QuestFile;
import dev.ftb.mods.ftbquests.client.FTBQuestsClient;
import dev.ftb.mods.ftbquests.client.config.LocaleConfig;
import dev.ftb.mods.ftbquests.events.ClearFileCacheEvent;
import dev.ftb.mods.ftbquests.events.CustomTaskEvent;
import dev.ftb.mods.ftbquests.events.ObjectCompletedEvent;
import dev.ftb.mods.ftbquests.events.ObjectStartedEvent;
import dev.ftb.mods.ftbquests.events.QuestProgressEventData;
import dev.ftb.mods.ftbquests.integration.RecipeModHelper;
import dev.ftb.mods.ftbquests.net.DeleteObjectResponseMessage;
import dev.ftb.mods.ftbquests.quest.loot.EntityWeight;
import dev.ftb.mods.ftbquests.quest.loot.LootCrate;
import dev.ftb.mods.ftbquests.quest.loot.RewardTable;
import dev.ftb.mods.ftbquests.quest.reward.CustomReward;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardAutoClaim;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import dev.ftb.mods.ftbquests.quest.reward.RewardTypes;
import dev.ftb.mods.ftbquests.quest.task.CustomTask;
import dev.ftb.mods.ftbquests.quest.task.ItemTask;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import dev.ftb.mods.ftbquests.quest.task.TaskTypes;
import dev.ftb.mods.ftbquests.quest.theme.property.ThemeProperties;
import dev.ftb.mods.ftbquests.quest.translation.TranslationKey;
import dev.ftb.mods.ftbquests.quest.translation.TranslationManager;
import dev.ftb.mods.ftbquests.util.FileUtils;
import dev.ftb.mods.ftbquests.util.TextUtils;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.client.ClientTeamManager;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseQuestFile extends QuestObject implements QuestFile {
   public static int VERSION = 13;
   public static final StreamCodec<RegistryFriendlyByteBuf, BaseQuestFile> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, BaseQuestFile>() {
      public BaseQuestFile decode(RegistryFriendlyByteBuf buf) {
         return (BaseQuestFile)Util.make(FTBQuestsClient.createClientQuestFile(), file -> file.readNetDataFull(buf));
      }

      public void encode(RegistryFriendlyByteBuf buf, BaseQuestFile file) {
         file.writeNetDataFull(buf);
      }
   };
   private final DefaultChapterGroup defaultChapterGroup;
   final List<ChapterGroup> chapterGroups;
   private final List<RewardTable> rewardTables;
   protected final Map<UUID, TeamData> teamDataMap;
   private final Long2ObjectOpenHashMap<QuestObjectBase> questObjectMap;
   protected final Int2ObjectOpenHashMap<TaskType> taskTypeIds;
   protected final Int2ObjectOpenHashMap<RewardType> rewardTypeIds;
   private final TranslationManager translationManager;
   private final List<ItemStack> emergencyItems;
   private int emergencyItemsCooldown;
   private int fileVersion = 0;
   private boolean defaultPerTeamReward;
   private boolean defaultTeamConsumeItems;
   private RewardAutoClaim defaultRewardAutoClaim;
   private String defaultQuestShape;
   private boolean defaultQuestDisableJEI;
   private boolean hideExcludedQuests;
   private boolean dropLootCrates;
   private final EntityWeight lootCrateNoDrop;
   private boolean disableGui;
   private double gridScale;
   private boolean pauseGame;
   protected String lockMessage;
   private ProgressionMode progressionMode;
   private int detectionDelay;
   private boolean showLockIcons;
   private boolean dropBookOnDeath;
   private String fallbackLocale;
   private boolean verifyOnLoad;
   private List<Task> allTasks;
   private List<Task> submitTasks;
   private List<Task> craftingTasks;

   public BaseQuestFile() {
      super(1L);
      this.defaultChapterGroup = new DefaultChapterGroup(this);
      this.chapterGroups = new ArrayList<>();
      this.chapterGroups.add(this.defaultChapterGroup);
      this.rewardTables = new ArrayList<>();
      this.teamDataMap = new HashMap<>();
      this.questObjectMap = new Long2ObjectOpenHashMap();
      this.taskTypeIds = new Int2ObjectOpenHashMap();
      this.rewardTypeIds = new Int2ObjectOpenHashMap();
      this.emergencyItems = new ArrayList<>();
      this.emergencyItemsCooldown = 300;
      this.defaultPerTeamReward = false;
      this.defaultTeamConsumeItems = false;
      this.defaultRewardAutoClaim = RewardAutoClaim.DISABLED;
      this.defaultQuestShape = "circle";
      this.defaultQuestDisableJEI = false;
      this.dropLootCrates = false;
      this.lootCrateNoDrop = new EntityWeight();
      this.lootCrateNoDrop.passive = 4000;
      this.lootCrateNoDrop.monster = 600;
      this.lootCrateNoDrop.boss = 0;
      this.disableGui = false;
      this.gridScale = 0.5;
      this.pauseGame = false;
      this.lockMessage = "";
      this.progressionMode = ProgressionMode.LINEAR;
      this.detectionDelay = 20;
      this.dropBookOnDeath = false;
      this.hideExcludedQuests = false;
      this.verifyOnLoad = false;
      this.allTasks = null;
      this.translationManager = new TranslationManager();
      this.fallbackLocale = "en_us";
   }

   public abstract Env getSide();

   @Override
   public abstract Provider holderLookup();

   @Override
   public boolean isServerSide() {
      return this.getSide() == Env.SERVER;
   }

   @Override
   public BaseQuestFile getQuestFile() {
      return this;
   }

   @Override
   public QuestObjectType getObjectType() {
      return QuestObjectType.FILE;
   }

   public boolean isLoading() {
      return false;
   }

   @Override
   public boolean canEdit() {
      return false;
   }

   public Path getFolder() {
      throw new IllegalStateException("This quest file doesn't have a folder!");
   }

   public TranslationManager getTranslationManager() {
      return this.translationManager;
   }

   @Override
   public int getRelativeProgressFromChildren(TeamData data) {
      MutableInt progress = new MutableInt(0);
      MutableInt chapters = new MutableInt(0);
      this.forAllChapters(chapter -> {
         progress.add(data.getRelativeProgress(chapter));
         chapters.increment();
      });
      return getRelativeProgressFromChildren(progress.intValue(), chapters.intValue());
   }

   @Override
   public void onStarted(QuestProgressEventData<?> data) {
      data.setStarted(this.id);
      ((EventActor)ObjectStartedEvent.FILE.invoker()).act(new ObjectStartedEvent.FileEvent(data.withObject(this)));
   }

   @Override
   public void onCompleted(QuestProgressEventData<?> data) {
      data.setCompleted(this.id);
      ((EventActor)ObjectCompletedEvent.FILE.invoker()).act(new ObjectCompletedEvent.FileEvent(data.withObject(this)));
      if (!this.disableToast) {
         data.notifyPlayers(this.id);
      }
   }

   @Override
   public void deleteSelf() {
      this.invalid = true;
   }

   @Override
   public void deleteChildren() {
      this.forAllChapters(chapter -> {
         chapter.deleteChildren();
         chapter.invalid = true;
      });
      this.defaultChapterGroup.clearChapters();
      this.chapterGroups.clear();
      this.chapterGroups.add(this.defaultChapterGroup);

      for (RewardTable table : this.rewardTables) {
         table.deleteChildren();
         table.invalid = true;
      }

      this.rewardTables.clear();
   }

   @Nullable
   public QuestObjectBase getBase(long id) {
      if (id <= 0L) {
         return null;
      }

      if (id == 1L) {
         return this;
      }

      QuestObjectBase object = (QuestObjectBase)this.questObjectMap.get(id);
      return object != null && !object.invalid ? object : null;
   }

   @Nullable
   public QuestObject get(long id) {
      return this.getBase(id) instanceof QuestObject qo ? qo : null;
   }

   @Nullable
   public QuestObjectBase remove(long id) {
      QuestObjectBase object = (QuestObjectBase)this.questObjectMap.remove(id);
      if (object != null) {
         if (object instanceof QuestObject qo) {
            this.forAllQuests(quest -> quest.removeDependency(qo));
         }

         object.invalid = true;
         this.refreshIDMap();
         return object;
      } else {
         return null;
      }
   }

   public <T extends QuestObjectBase> T getQuestObjectOrThrow(long id, Class<T> cls) {
      QuestObjectBase object = this.getBase(id);
      if (object == null) {
         throw new IllegalArgumentException("Unknown object id " + id);
      } else if (cls.isAssignableFrom(object.getClass())) {
         return cls.cast(object);
      } else {
         throw new IllegalArgumentException("Wrong class for object id " + id + ": wanted " + cls.getName() + ", got " + object.getClass().getName());
      }
   }

   @Nullable
   public Chapter getChapter(long id) {
      QuestObjectBase object = this.getBase(id);
      return object instanceof Chapter ? (Chapter)object : null;
   }

   @NotNull
   public Chapter getChapterOrThrow(long id) {
      if (this.getBase(id) instanceof Chapter c) {
         return c;
      } else {
         throw new IllegalArgumentException("Unknown chapter ID: c");
      }
   }

   @Nullable
   public Quest getQuest(long id) {
      QuestObjectBase object = this.getBase(id);
      return object instanceof Quest ? (Quest)object : null;
   }

   @Nullable
   public Task getTask(long id) {
      QuestObjectBase object = this.getBase(id);
      return object instanceof Task ? (Task)object : null;
   }

   @Nullable
   public Reward getReward(long id) {
      QuestObjectBase object = this.getBase(id);
      return object instanceof Reward ? (Reward)object : null;
   }

   @Nullable
   public RewardTable getRewardTable(long id) {
      QuestObjectBase object = this.getBase(id);
      return object instanceof RewardTable ? (RewardTable)object : null;
   }

   @Nullable
   public LootCrate getLootCrate(String id) {
      if (!id.startsWith("#")) {
         for (RewardTable table : this.rewardTables) {
            if (table.getLootCrate() != null && table.getLootCrate().getStringID().equals(id)) {
               return table.getLootCrate();
            }
         }
      }

      RewardTable table = this.getRewardTable(this.getID(id));
      return table == null ? null : table.getLootCrate();
   }

   public ChapterGroup getChapterGroup(long id) {
      QuestObjectBase object = this.getBase(id);
      return object instanceof ChapterGroup ? (ChapterGroup)object : this.defaultChapterGroup;
   }

   public void refreshIDMap() {
      this.clearCachedData();
      this.questObjectMap.clear();
      this.chapterGroups.forEach(group -> this.questObjectMap.put(group.id, group));
      this.rewardTables.forEach(table -> this.questObjectMap.put(table.id, table));
      this.forAllChapters(chapter -> {
         this.questObjectMap.put(chapter.id, chapter);

         for (Quest quest : chapter.getQuests()) {
            this.questObjectMap.put(quest.id, quest);
            quest.getTasks().forEach(task -> this.questObjectMap.put(task.id, task));
            quest.getRewards().forEach(reward -> this.questObjectMap.put(reward.id, reward));
         }

         chapter.getQuestLinks().forEach(link -> this.questObjectMap.put(link.id, link));
      });
      this.refreshRewardTableRewardIDs();
   }

   public void refreshRewardTableRewardIDs() {
      this.rewardTables.forEach(table -> table.getWeightedRewards().forEach(wr -> this.questObjectMap.put(wr.getReward().id, wr.getReward())));
   }

   public QuestObjectBase create(long id, QuestObjectType type, long parent, CompoundTag extra) {
      switch (type) {
         case CHAPTER:
            return new Chapter(id, this, this.getChapterGroup(extra.getLong("group")));
         case QUEST:
            Chapter chapter = this.getChapter(parent);
            if (chapter != null) {
               return new Quest(id, chapter);
            }

            throw new IllegalArgumentException("Parent chapter not found!");
         case QUEST_LINK:
            Chapter chapter = this.getChapter(parent);
            if (chapter != null) {
               return new QuestLink(id, chapter, 0L);
            }

            throw new IllegalArgumentException("Parent chapter not found!");
         case TASK:
            Quest quest = this.getQuest(parent);
            if (quest != null) {
               Task task = TaskType.createTask(id, quest, extra.getString("type"));
               if (task != null) {
                  return task;
               }

               throw new IllegalArgumentException("Unknown task type!");
            }

            throw new IllegalArgumentException("Parent quest not found!");
         case REWARD:
            String rewardType = extra.getString("type");
            if (RewardTable.isFakeQuestId(parent)) {
               return RewardTable.createRewardForTable(id, rewardType, this);
            }

            Quest quest = this.getQuestObjectOrThrow(parent, Quest.class);
            Reward reward = RewardType.createReward(id, quest, rewardType);
            Validate.isTrue(reward != null, "Unknown reward type: " + rewardType, new Object[0]);
            return reward;
         case REWARD_TABLE:
            return new RewardTable(id, this);
         case CHAPTER_GROUP:
            return new ChapterGroup(id, this);
         default:
            throw new IllegalArgumentException("Unknown type: " + type);
      }
   }

   @Override
   public final void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putBoolean("default_reward_team", this.defaultPerTeamReward);
      nbt.putBoolean("default_consume_items", this.defaultTeamConsumeItems);
      nbt.putString("default_autoclaim_rewards", this.defaultRewardAutoClaim.id);
      nbt.putString("default_quest_shape", this.defaultQuestShape);
      nbt.putBoolean("default_quest_disable_jei", this.defaultQuestDisableJEI);
      if (!this.emergencyItems.isEmpty()) {
         nbt.put("emergency_items", (Tag)Util.make(new ListTag(), l -> {
            for (ItemStack stack : this.emergencyItems) {
               l.add(stack.save(provider));
            }
         }));
      }

      nbt.putInt("emergency_items_cooldown", this.emergencyItemsCooldown);
      nbt.putBoolean("drop_loot_crates", this.dropLootCrates);
      SNBTCompoundTag lootCrateNoDropTag = new SNBTCompoundTag();
      this.lootCrateNoDrop.writeData(lootCrateNoDropTag);
      nbt.put("loot_crate_no_drop", lootCrateNoDropTag);
      nbt.putBoolean("disable_gui", this.disableGui);
      nbt.putDouble("grid_scale", this.gridScale);
      nbt.putBoolean("pause_game", this.pauseGame);
      nbt.putString("lock_message", this.lockMessage);
      nbt.putString("progression_mode", this.progressionMode.getId());
      nbt.putInt("detection_delay", this.detectionDelay);
      nbt.putBoolean("show_lock_icons", this.showLockIcons);
      nbt.putBoolean("drop_book_on_death", this.dropBookOnDeath);
      nbt.putBoolean("hide_excluded_quests", this.hideExcludedQuests);
      nbt.putString("fallback_locale", this.fallbackLocale);
      nbt.putBoolean("verify_on_load", this.verifyOnLoad);
   }

   @Override
   public final void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.defaultPerTeamReward = nbt.getBoolean("default_reward_team");
      this.defaultTeamConsumeItems = nbt.getBoolean("default_consume_items");
      this.defaultRewardAutoClaim = (RewardAutoClaim)RewardAutoClaim.NAME_MAP_NO_DEFAULT.get(nbt.getString("default_autoclaim_rewards"));
      this.defaultQuestShape = nbt.getString("default_quest_shape");
      if (this.defaultQuestShape.equals("default")) {
         this.defaultQuestShape = "";
      }

      this.defaultQuestDisableJEI = nbt.getBoolean("default_quest_disable_jei");
      this.emergencyItems.clear();

      for (Tag tag : nbt.getList("emergency_items", 10)) {
         this.emergencyItems.add(itemOrMissingFromNBT(tag, provider));
      }

      this.emergencyItemsCooldown = nbt.getInt("emergency_items_cooldown");
      this.dropLootCrates = nbt.getBoolean("drop_loot_crates");
      if (nbt.contains("loot_crate_no_drop")) {
         this.lootCrateNoDrop.readData(nbt.getCompound("loot_crate_no_drop"));
      }

      this.disableGui = nbt.getBoolean("disable_gui");
      this.gridScale = nbt.contains("grid_scale") ? nbt.getDouble("grid_scale") : 0.5;
      this.pauseGame = nbt.getBoolean("pause_game");
      this.lockMessage = nbt.getString("lock_message");
      this.progressionMode = (ProgressionMode)ProgressionMode.NAME_MAP_NO_DEFAULT.get(nbt.getString("progression_mode"));
      if (nbt.contains("detection_delay")) {
         this.detectionDelay = nbt.getInt("detection_delay");
      }

      this.showLockIcons = !nbt.contains("show_lock_icons") || nbt.getBoolean("show_lock_icons");
      this.dropBookOnDeath = nbt.getBoolean("drop_book_on_death");
      this.hideExcludedQuests = nbt.getBoolean("hide_excluded_quests");
      this.fallbackLocale = nbt.getString("fallback_locale");
      this.verifyOnLoad = nbt.getBoolean("verify_on_load");
   }

   public final void writeDataFull(Path folder, Provider provider) {
      boolean prev = false;

      try {
         prev = SNBT.setShouldSortKeysOnWrite(true);
         SNBTCompoundTag fileNBT = new SNBTCompoundTag();
         fileNBT.putInt("version", VERSION);
         this.writeData(fileNBT, provider);
         SNBT.write(folder.resolve("data.snbt"), fileNBT);

         for (ChapterGroup group : this.chapterGroups) {
            for (int ci = 0; ci < group.getChapters().size(); ci++) {
               Chapter chapter = group.getChapters().get(ci);
               SNBTCompoundTag chapterNBT = new SNBTCompoundTag();
               chapterNBT.putString("id", chapter.getCodeString());
               chapterNBT.putString("group", group.isDefaultGroup() ? "" : group.getCodeString());
               chapterNBT.putInt("order_index", ci);
               chapter.writeData(chapterNBT, provider);
               ListTag questList = new ListTag();

               for (Quest quest : chapter.getQuests()) {
                  if (quest.isValid()) {
                     SNBTCompoundTag questNBT = new SNBTCompoundTag();
                     quest.writeData(questNBT, provider);
                     questNBT.putString("id", quest.getCodeString());
                     if (!quest.getTasks().isEmpty()) {
                        quest.writeTasks(questNBT, provider);
                     }

                     if (!quest.getRewards().isEmpty()) {
                        quest.writeRewards(questNBT, provider);
                     }

                     questList.add(questNBT);
                  }
               }

               chapterNBT.put("quests", questList);
               ListTag linkList = new ListTag();

               for (QuestLink link : chapter.getQuestLinks()) {
                  if (link.getQuest().isPresent()) {
                     SNBTCompoundTag linkNBT = new SNBTCompoundTag();
                     link.writeData(linkNBT, provider);
                     linkNBT.putString("id", link.getCodeString());
                     linkList.add(linkNBT);
                  }
               }

               chapterNBT.put("quest_links", linkList);
               SNBT.write(folder.resolve("chapters/" + chapter.getFilename() + ".snbt"), chapterNBT);
            }
         }

         for (int ri = 0; ri < this.rewardTables.size(); ri++) {
            RewardTable table = this.rewardTables.get(ri);
            SNBTCompoundTag tableNBT = new SNBTCompoundTag();
            tableNBT.putString("id", table.getCodeString());
            tableNBT.putInt("order_index", ri);
            table.writeData(tableNBT, provider);
            SNBT.write(folder.resolve("reward_tables/" + table.getFilename() + ".snbt"), tableNBT);
         }

         ListTag chapterGroupTag = new ListTag();

         for (ChapterGroup group : this.chapterGroups) {
            if (!group.isDefaultGroup()) {
               SNBTCompoundTag groupTag = new SNBTCompoundTag();
               groupTag.singleLine();
               groupTag.putString("id", group.getCodeString());
               group.writeData(groupTag, provider);
               chapterGroupTag.add(groupTag);
            }
         }

         SNBTCompoundTag groupNBT = new SNBTCompoundTag();
         groupNBT.put("chapter_groups", chapterGroupTag);
         SNBT.write(folder.resolve("chapter_groups.snbt"), groupNBT);
      } finally {
         SNBT.setShouldSortKeysOnWrite(prev);
      }
   }

   public final void readDataFull(Path folder, Provider provider) {
      this.clearCachedData();
      this.questObjectMap.clear();
      this.defaultChapterGroup.clearChapters();
      this.chapterGroups.clear();
      this.chapterGroups.add(this.defaultChapterGroup);
      this.rewardTables.clear();
      MutableInt chapterCounter = new MutableInt();
      MutableInt questCounter = new MutableInt();
      Long2ObjectOpenHashMap<CompoundTag> dataCache = new Long2ObjectOpenHashMap();
      CompoundTag fileNBT = SNBT.read(folder.resolve("data.snbt"));
      if (fileNBT != null) {
         this.fileVersion = fileNBT.getInt("version");
         this.questObjectMap.put(1L, this);
         this.readData(fileNBT, provider);
         this.handleLegacyFileNBT(fileNBT);
      }

      this.translationManager.loadFromNBT(this, folder.resolve("lang"));
      Path groupsFile = folder.resolve("chapter_groups.snbt");
      if (Files.exists(groupsFile)) {
         CompoundTag chapterGroupsTag = SNBT.read(groupsFile);
         if (chapterGroupsTag != null) {
            ListTag groupListTag = chapterGroupsTag.getList("chapter_groups", 10);

            for (int i = 0; i < groupListTag.size(); i++) {
               CompoundTag groupNBT = groupListTag.getCompound(i);
               ChapterGroup chapterGroup = new ChapterGroup(this.readID(groupNBT.get("id")), this);
               this.handleLegacyChapterGroupNBT(groupNBT, chapterGroup);
               this.questObjectMap.put(chapterGroup.id, chapterGroup);
               dataCache.put(chapterGroup.id, groupNBT);
               this.chapterGroups.add(chapterGroup);
            }
         }
      }

      Path chaptersFolder = folder.resolve("chapters");
      Long2IntOpenHashMap objectOrderMap = new Long2IntOpenHashMap();
      objectOrderMap.defaultReturnValue(-1);
      if (Files.exists(chaptersFolder)) {
         try (Stream<Path> s = Files.list(chaptersFolder)) {
            s.filter(path -> path.toString().endsWith(".snbt"))
               .forEach(
                  path -> {
                     CompoundTag chapterNBT = SNBT.read(path);
                     if (chapterNBT != null) {
                        Chapter chapterx = new Chapter(
                           this.readID(chapterNBT.get("id")),
                           this,
                           this.getChapterGroup(this.getID(chapterNBT.get("group"))),
                           path.getFileName().toString().replace(".snbt", "")
                        );
                        this.handleLegacyChapterNBT(chapterNBT, chapterx);
                        objectOrderMap.put(chapterx.id, chapterNBT.getInt("order_index"));
                        this.questObjectMap.put(chapterx.id, chapterx);
                        dataCache.put(chapterx.id, chapterNBT);
                        chapterx.getGroup().addChapter(chapterx);
                        ListTag questList = chapterNBT.getList("quests", 10);

                        for (int i = 0; i < questList.size(); i++) {
                           CompoundTag questNBT = questList.getCompound(i);
                           Quest questx = new Quest(this.readID(questNBT.get("id")), chapterx);
                           this.handleLegacyQuestNBT(questx, questNBT);
                           this.questObjectMap.put(questx.id, questx);
                           dataCache.put(questx.id, questNBT);
                           chapterx.addQuest(questx);
                           ListTag taskList = questNBT.getList("tasks", 10);

                           for (int j = 0; j < taskList.size(); j++) {
                              CompoundTag taskNBT = taskList.getCompound(j);
                              long taskId = this.readID(taskNBT.get("id"));
                              Task task = TaskType.createTask(taskId, questx, taskNBT.getString("type"));
                              this.handleLegacyTaskNBT(task, taskNBT);
                              if (task == null) {
                                 task = new CustomTask(taskId, questx);
                                 task.setRawTitle("Unknown type: " + taskNBT.getString("type"));
                              }

                              this.questObjectMap.put(task.id, task);
                              dataCache.put(task.id, taskNBT);
                              questx.addTask(task);
                           }

                           ListTag rewardList = questNBT.getList("rewards", 10);

                           for (int j = 0; j < rewardList.size(); j++) {
                              CompoundTag rewardNBT = rewardList.getCompound(j);
                              long rewardId = this.readID(rewardNBT.get("id"));
                              Reward reward = RewardType.createReward(rewardId, questx, rewardNBT.getString("type"));
                              if (reward == null) {
                                 reward = new CustomReward(rewardId, questx);
                                 reward.setRawTitle("Unknown type: " + rewardNBT.getString("type"));
                              }

                              this.questObjectMap.put(reward.id, reward);
                              dataCache.put(reward.id, rewardNBT);
                              questx.addReward(reward);
                           }

                           questCounter.increment();
                        }

                        ListTag questLinks = chapterNBT.getList("quest_links", 10);

                        for (int i = 0; i < questLinks.size(); i++) {
                           CompoundTag linkNBT = questLinks.getCompound(i);
                           QuestLink link = new QuestLink(this.readID(linkNBT.get("id")), chapterx, this.readID(linkNBT.get("linked_quest")));
                           chapterx.addQuestLink(link);
                           this.questObjectMap.put(link.id, link);
                           dataCache.put(link.id, linkNBT);
                        }

                        chapterCounter.increment();
                     }
                  }
               );
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

      Path rewardTableFolder = folder.resolve("reward_tables");
      if (Files.exists(rewardTableFolder)) {
         try (Stream<Path> s = Files.list(rewardTableFolder)) {
            s.filter(path -> path.toString().endsWith(".snbt")).forEach(path -> {
               CompoundTag tableNBT = SNBT.read(path);
               if (tableNBT != null) {
                  String filename = path.getFileName().toString().replace(".snbt", "");
                  RewardTable table = new RewardTable(this.readID(tableNBT.get("id")), this, filename);
                  objectOrderMap.put(table.id, tableNBT.getInt("order_index"));
                  this.questObjectMap.put(table.id, table);
                  dataCache.put(table.id, tableNBT);
                  this.rewardTables.add(table);
               }
            });
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      }

      ObjectIterator var28 = this.questObjectMap.values().iterator();

      while (var28.hasNext()) {
         QuestObjectBase object = (QuestObjectBase)var28.next();
         CompoundTag data = (CompoundTag)dataCache.get(object.id);
         if (data != null) {
            object.readData(data, provider);
         }
      }

      for (ChapterGroup group : this.chapterGroups) {
         group.sortChapters(Comparator.comparingInt(c -> objectOrderMap.get(c.id)));

         for (Chapter chapter : group.getChapters()) {
            for (Quest quest : chapter.getQuests()) {
               quest.removeInvalidDependencies();
            }
         }
      }

      this.rewardTables.sort(Comparator.comparingInt(c -> objectOrderMap.get(c.id)));
      this.updateLootCrates();
      this.refreshRewardTableRewardIDs();
      if (this.verifyOnLoad) {
         this.forAllQuests(q -> q.verifyDependencies(false));
      }

      for (QuestObjectBase object : this.getAllObjects()) {
         if (object instanceof CustomTask) {
            ((EventActor)CustomTaskEvent.EVENT.invoker()).act(new CustomTaskEvent((CustomTask)object));
         }
      }

      if (this.fileVersion != VERSION) {
         this.markDirty();
      }

      FTBQuests.LOGGER
         .info(
            "Loaded {} chapter groups, {} chapters, {} quests, {} reward tables",
            this.chapterGroups.size(),
            chapterCounter,
            questCounter,
            this.rewardTables.size()
         );
   }

   private void handleLegacyFileNBT(CompoundTag fileNBT) {
      if (fileNBT.contains("title", 8)) {
         this.translationManager.addTranslation(this, "en_us", TranslationKey.TITLE, fileNBT.getString("title"));
         this.markDirty();
      }
   }

   private void handleLegacyChapterGroupNBT(CompoundTag groupNBT, ChapterGroup chapterGroup) {
      if (groupNBT.contains("title", 8)) {
         this.translationManager.addTranslation(chapterGroup, "en_us", TranslationKey.TITLE, groupNBT.getString("title"));
         this.markDirty();
      }
   }

   private void handleLegacyChapterNBT(CompoundTag chapterNBT, Chapter chapter) {
      if (chapterNBT.contains("title", 8)) {
         this.translationManager.addTranslation(chapter, "en_us", TranslationKey.TITLE, chapterNBT.getString("title"));
         this.markDirty();
      }

      if (chapterNBT.contains("subtitle", 9)) {
         this.translationManager.addTranslation(chapter, "en_us", TranslationKey.CHAPTER_SUBTITLE, TextUtils.fromListTag(chapterNBT.getList("subtitle", 8)));
         this.markDirty();
      }
   }

   private void handleLegacyQuestNBT(Quest quest, CompoundTag questNBT) {
      if (questNBT.contains("title", 8)) {
         this.translationManager.addTranslation(quest, "en_us", TranslationKey.TITLE, questNBT.getString("title"));
         this.markDirty();
      }

      if (questNBT.contains("subtitle", 8)) {
         this.translationManager.addTranslation(quest, "en_us", TranslationKey.QUEST_SUBTITLE, questNBT.getString("subtitle"));
         this.markDirty();
      }

      if (questNBT.contains("description", 9)) {
         this.translationManager.addTranslation(quest, "en_us", TranslationKey.QUEST_DESC, TextUtils.fromListTag(questNBT.getList("description", 8)));
         this.markDirty();
      }
   }

   private void handleLegacyTaskNBT(Task task, CompoundTag taskNBT) {
      if (taskNBT.contains("title", 8)) {
         this.translationManager.addTranslation(task, "en_us", TranslationKey.TITLE, taskNBT.getString("title"));
         this.markDirty();
      }
   }

   public void updateLootCrates() {
      Map<String, LootCrate> lootCrates = LootCrate.getLootCrates(!this.isServerSide());
      Set<String> prevCrateNames = new HashSet<>(lootCrates.keySet());
      Collection<ItemStack> oldStacks = LootCrate.allCrateStacks(!this.isServerSide());
      lootCrates.clear();

      for (RewardTable table : this.rewardTables) {
         if (table.getLootCrate() != null) {
            lootCrates.put(table.getLootCrate().getStringID(), table.getLootCrate());
         }
      }

      if (!this.isServerSide() && !prevCrateNames.equals(lootCrates.keySet())) {
         FTBQuestsClient.rebuildCreativeTabs();
         FTBQuests.getRecipeModHelper().updateItemsDynamic(oldStacks, LootCrate.allCrateStacks(!this.isServerSide()));
      }

      FTBQuests.LOGGER.debug("Updated loot crates (was {}, now {})", prevCrateNames.size(), lootCrates.size());
   }

   public void markDirty() {
   }

   @Override
   public final void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()).encode(buffer, this.emergencyItems);
      buffer.writeVarInt(this.emergencyItemsCooldown);
      buffer.writeBoolean(this.defaultPerTeamReward);
      buffer.writeBoolean(this.defaultTeamConsumeItems);
      RewardAutoClaim.NAME_MAP_NO_DEFAULT.write(buffer, this.defaultRewardAutoClaim);
      buffer.writeUtf(this.defaultQuestShape, 32767);
      buffer.writeBoolean(this.defaultQuestDisableJEI);
      buffer.writeBoolean(this.dropLootCrates);
      this.lootCrateNoDrop.writeNetData(buffer);
      buffer.writeBoolean(this.disableGui);
      buffer.writeDouble(this.gridScale);
      buffer.writeBoolean(this.pauseGame);
      buffer.writeUtf(this.lockMessage, 32767);
      ProgressionMode.NAME_MAP_NO_DEFAULT.write(buffer, this.progressionMode);
      buffer.writeVarInt(this.detectionDelay);
      buffer.writeBoolean(this.showLockIcons);
      buffer.writeBoolean(this.dropBookOnDeath);
      buffer.writeBoolean(this.hideExcludedQuests);
      buffer.writeUtf(this.fallbackLocale);
   }

   @Override
   public final void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.emergencyItems.clear();
      this.emergencyItems.addAll((Collection<? extends ItemStack>)ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()).decode(buffer));
      this.emergencyItemsCooldown = buffer.readVarInt();
      this.defaultPerTeamReward = buffer.readBoolean();
      this.defaultTeamConsumeItems = buffer.readBoolean();
      this.defaultRewardAutoClaim = (RewardAutoClaim)RewardAutoClaim.NAME_MAP_NO_DEFAULT.read(buffer);
      this.defaultQuestShape = buffer.readUtf(32767);
      this.defaultQuestDisableJEI = buffer.readBoolean();
      this.dropLootCrates = buffer.readBoolean();
      this.lootCrateNoDrop.readNetData(buffer);
      this.disableGui = buffer.readBoolean();
      this.gridScale = buffer.readDouble();
      this.pauseGame = buffer.readBoolean();
      this.lockMessage = buffer.readUtf(32767);
      this.progressionMode = (ProgressionMode)ProgressionMode.NAME_MAP_NO_DEFAULT.read(buffer);
      this.detectionDelay = buffer.readVarInt();
      this.showLockIcons = buffer.readBoolean();
      this.dropBookOnDeath = buffer.readBoolean();
      this.hideExcludedQuests = buffer.readBoolean();
      this.fallbackLocale = buffer.readUtf();
   }

   public final void writeNetDataFull(RegistryFriendlyByteBuf buffer) {
      int pos = buffer.writerIndex();
      buffer.writeVarInt(TaskTypes.TYPES.size());

      for (TaskType type : TaskTypes.TYPES.values()) {
         buffer.writeResourceLocation(type.getTypeId());
         buffer.writeVarInt(type.internalId);
      }

      buffer.writeVarInt(RewardTypes.TYPES.size());

      for (RewardType type : RewardTypes.TYPES.values()) {
         buffer.writeResourceLocation(type.getTypeId());
         buffer.writeVarInt(type.intId);
      }

      this.writeNetData(buffer);
      buffer.writeVarInt(this.rewardTables.size());

      for (RewardTable table : this.rewardTables) {
         buffer.writeLong(table.id);
      }

      buffer.writeVarInt(this.chapterGroups.size() - 1);

      for (ChapterGroup group : this.chapterGroups) {
         if (!group.isDefaultGroup()) {
            buffer.writeLong(group.id);
         }
      }

      for (ChapterGroup group : this.chapterGroups) {
         buffer.writeVarInt(group.getChapters().size());

         for (Chapter chapter : group.getChapters()) {
            buffer.writeLong(chapter.id);
            buffer.writeVarInt(chapter.getQuests().size());

            for (Quest quest : chapter.getQuests()) {
               buffer.writeLong(quest.id);
               buffer.writeVarInt(quest.getTasks().size());
               quest.getTasks().forEach(task -> {
                  buffer.writeVarInt(task.getType().internalId);
                  buffer.writeLong(task.id);
               });
               buffer.writeVarInt(quest.getRewards().size());
               quest.getRewards().forEach(reward -> {
                  buffer.writeVarInt(reward.getType().intId);
                  buffer.writeLong(reward.id);
               });
            }

            buffer.writeVarInt(chapter.getQuestLinks().size());

            for (QuestLink questLink : chapter.getQuestLinks()) {
               buffer.writeLong(questLink.id);
            }
         }
      }

      for (RewardTable table : this.rewardTables) {
         table.writeNetData(buffer);
      }

      for (ChapterGroup group : this.chapterGroups) {
         if (!group.isDefaultGroup()) {
            group.writeNetData(buffer);
         }
      }

      for (ChapterGroup group : this.chapterGroups) {
         for (Chapter chapter : group.getChapters()) {
            chapter.writeNetData(buffer);
            chapter.getQuests().forEach(quest -> {
               quest.writeNetData(buffer);
               quest.getTasks().forEach(task -> task.writeNetData(buffer));
               quest.getRewards().forEach(reward -> reward.writeNetData(buffer));
            });
            chapter.getQuestLinks().forEach(questLinkx -> questLinkx.writeNetData(buffer));
         }
      }

      FTBQuests.LOGGER.debug("Wrote {} bytes, {} objects", buffer.writerIndex() - pos, this.questObjectMap.size());
   }

   public final void readNetDataFull(RegistryFriendlyByteBuf buffer) {
      int pos = buffer.readerIndex();
      this.taskTypeIds.clear();
      this.rewardTypeIds.clear();

      for (TaskType type : TaskTypes.TYPES.values()) {
         type.internalId = 0;
      }

      for (RewardType type : RewardTypes.TYPES.values()) {
         type.intId = 0;
      }

      int taskTypesSize = buffer.readVarInt();

      for (int i = 0; i < taskTypesSize; i++) {
         TaskType type = TaskTypes.TYPES.get(buffer.readResourceLocation());
         int id = buffer.readVarInt();
         if (type != null) {
            type.internalId = id;
            this.taskTypeIds.put(type.internalId, type);
         }
      }

      int rewardTypesSize = buffer.readVarInt();

      for (int i = 0; i < rewardTypesSize; i++) {
         RewardType type = RewardTypes.TYPES.get(buffer.readResourceLocation());
         int id = buffer.readVarInt();
         if (type != null) {
            type.intId = id;
            this.rewardTypeIds.put(type.intId, type);
         }
      }

      this.readNetData(buffer);
      this.rewardTables.clear();
      int rewardTableSize = buffer.readVarInt();

      for (int i = 0; i < rewardTableSize; i++) {
         RewardTable table = new RewardTable(buffer.readLong(), this);
         this.rewardTables.add(table);
      }

      this.chapterGroups.clear();
      this.chapterGroups.add(this.defaultChapterGroup);
      int chapterGroupsSize = buffer.readVarInt();

      for (int i = 0; i < chapterGroupsSize; i++) {
         ChapterGroup group = new ChapterGroup(buffer.readLong(), this);
         this.chapterGroups.add(group);
      }

      for (ChapterGroup group : this.chapterGroups) {
         int chapterCount = buffer.readVarInt();

         for (int i = 0; i < chapterCount; i++) {
            Chapter chapter = new Chapter(buffer.readLong(), this, group);
            group.addChapter(chapter);
            int questCount = buffer.readVarInt();

            for (int j = 0; j < questCount; j++) {
               Quest quest = new Quest(buffer.readLong(), chapter);
               chapter.addQuest(quest);
               int taskCount = buffer.readVarInt();

               for (int k = 0; k < taskCount; k++) {
                  TaskType type = (TaskType)this.taskTypeIds.get(buffer.readVarInt());
                  quest.addTask(type.createTask(buffer.readLong(), quest));
               }

               int rewardCount = buffer.readVarInt();

               for (int k = 0; k < rewardCount; k++) {
                  RewardType type = (RewardType)this.rewardTypeIds.get(buffer.readVarInt());
                  quest.addReward(type.createReward(buffer.readLong(), quest));
               }
            }

            int questLinkCount = buffer.readVarInt();

            for (int j = 0; j < questLinkCount; j++) {
               QuestLink questLink = new QuestLink(buffer.readLong(), chapter, 0L);
               chapter.addQuestLink(questLink);
            }
         }
      }

      for (RewardTable table : this.rewardTables) {
         table.readNetData(buffer);
      }

      this.refreshIDMap();

      for (ChapterGroup group : this.chapterGroups) {
         if (!group.isDefaultGroup()) {
            group.readNetData(buffer);
         }
      }

      for (ChapterGroup group : this.chapterGroups) {
         for (Chapter chapter : group.getChapters()) {
            chapter.readNetData(buffer);

            for (Quest quest : chapter.getQuests()) {
               quest.readNetData(buffer);
               quest.getTasks().forEach(task -> task.readNetData(buffer));
               quest.getRewards().forEach(reward -> reward.readNetData(buffer));
            }

            for (QuestLink questLink : chapter.getQuestLinks()) {
               questLink.readNetData(buffer);
            }
         }
      }

      FTBQuests.LOGGER.info("Read {} bytes, {} objects", buffer.readerIndex() - pos, this.questObjectMap.size());
   }

   @Override
   public long getParentID() {
      return 0L;
   }

   @Nullable
   @Override
   public TeamData getNullableTeamData(UUID id) {
      return this.teamDataMap.get(id);
   }

   @Override
   public TeamData getOrCreateTeamData(UUID teamId) {
      return this.teamDataMap.computeIfAbsent(teamId, k -> new TeamData(teamId, this));
   }

   @Override
   public TeamData getOrCreateTeamData(Team team) {
      return this.getOrCreateTeamData(Objects.requireNonNull(team, "Non-null team required!").getId());
   }

   @Override
   public TeamData getOrCreateTeamData(Entity player) {
      return FTBTeamsAPI.api().getManager().getTeamForPlayerID(player.getUUID()).map(this::getOrCreateTeamData).orElse(null);
   }

   @Override
   public Optional<TeamData> getTeamData(Player player) {
      return player.level().isClientSide
         ? this.getClientTeamData(player)
         : FTBTeamsAPI.api().getManager().getTeamForPlayerID(player.getUUID()).map(this::getOrCreateTeamData);
   }

   private Optional<TeamData> getClientTeamData(Player player) {
      ClientTeamManager mgr = FTBTeamsAPI.api().getClientManager();
      return mgr.getKnownPlayer(player.getUUID()).map(kcp -> mgr.getTeamByID(kcp.teamId())).flatMap(team -> team.map(this::getOrCreateTeamData));
   }

   @Override
   public Collection<TeamData> getAllTeamData() {
      return Collections.unmodifiableCollection(this.teamDataMap.values());
   }

   public abstract void deleteObject(long var1);

   @OnlyIn(Dist.CLIENT)
   public MutableComponent getAltTitle() {
      return Component.translatable("ftbquests.file");
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Icon getAltIcon() {
      return ThemeProperties.MODPACK_ICON.get(this);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addList("emergency_items", this.emergencyItems, new ItemStackConfig(false, false), ItemStack.EMPTY);
      config.addInt("emergency_items_cooldown", this.emergencyItemsCooldown, v -> this.emergencyItemsCooldown = v, 300, 0, Integer.MAX_VALUE);
      config.addBool("drop_loot_crates", this.dropLootCrates, v -> this.dropLootCrates = v, false);
      config.addBool("disable_gui", this.disableGui, v -> this.disableGui = v, false);
      config.addDouble("grid_scale", this.gridScale, v -> this.gridScale = v, 0.5, 0.03125, 8.0);
      config.addString("lock_message", this.lockMessage, v -> this.lockMessage = v, "");
      config.addEnum("progression_mode", this.progressionMode, v -> this.progressionMode = v, ProgressionMode.NAME_MAP_NO_DEFAULT);
      config.addInt("detection_delay", this.detectionDelay, v -> this.detectionDelay = v, 20, 0, 200);
      config.addBool("pause_game", this.pauseGame, v -> this.pauseGame = v, false);
      config.addBool("show_lock_icons", this.showLockIcons, v -> this.showLockIcons = v, true).setNameKey("ftbquests.ui.show_lock_icon");
      config.addBool("drop_book_on_death", this.dropBookOnDeath, v -> this.dropBookOnDeath = v, true);
      config.addBool("hide_excluded_quests", this.hideExcludedQuests, v -> this.hideExcludedQuests = v, false);
      config.add("fallback_locale", new LocaleConfig(), this.fallbackLocale, v -> this.fallbackLocale = v, "");
      ConfigGroup defaultsGroup = config.getOrCreateSubgroup("defaults");
      defaultsGroup.addBool("reward_team", this.defaultPerTeamReward, v -> this.defaultPerTeamReward = v, false);
      defaultsGroup.addBool("consume_items", this.defaultTeamConsumeItems, v -> this.defaultTeamConsumeItems = v, false);
      defaultsGroup.addEnum("autoclaim_rewards", this.defaultRewardAutoClaim, v -> this.defaultRewardAutoClaim = v, RewardAutoClaim.NAME_MAP_NO_DEFAULT);
      defaultsGroup.addEnum("quest_shape", this.defaultQuestShape, v -> this.defaultQuestShape = v, QuestShape.idMap);
      defaultsGroup.addBool("quest_disable_jei", this.defaultQuestDisableJEI, v -> this.defaultQuestDisableJEI = v, false);
      ConfigGroup d = config.getOrCreateSubgroup("loot_crate_no_drop");
      d.addInt("passive", this.lootCrateNoDrop.passive, v -> this.lootCrateNoDrop.passive = v, 0, 0, Integer.MAX_VALUE)
         .setNameKey("ftbquests.loot.entitytype.passive");
      d.addInt("monster", this.lootCrateNoDrop.monster, v -> this.lootCrateNoDrop.monster = v, 0, 0, Integer.MAX_VALUE)
         .setNameKey("ftbquests.loot.entitytype.monster");
      d.addInt("boss", this.lootCrateNoDrop.boss, v -> this.lootCrateNoDrop.boss = v, 0, 0, Integer.MAX_VALUE).setNameKey("ftbquests.loot.entitytype.boss");
   }

   @Override
   public void clearCachedData() {
      super.clearCachedData();
      this.allTasks = null;
      this.submitTasks = null;
      this.craftingTasks = null;

      for (ChapterGroup group : this.chapterGroups) {
         group.clearCachedData();
      }

      this.clearCachedProgress();
      ((Consumer)ClearFileCacheEvent.EVENT.invoker()).accept(this);
   }

   public void clearCachedProgress() {
      this.getAllTeamData().forEach(TeamData::clearCachedProgress);
   }

   public long newID() {
      return this.readID(0L);
   }

   public long readID(long id) {
      while (id == 0L || id == 1L || this.questObjectMap.get(id) != null) {
         id = Math.abs(MathUtils.RAND.nextLong());
         this.markDirty();
      }

      return id;
   }

   public long readID(@Nullable Tag tag) {
      if (tag instanceof NumericTag) {
         this.markDirty();
         return this.readID(((NumericTag)tag).getAsLong());
      }

      if (tag instanceof StringTag) {
         try {
            String id = tag.getAsString();
            return this.readID(Long.parseLong(id.charAt(0) == '#' ? id.substring(1) : id, 16));
         } catch (Exception var3) {
         }
      }

      return this.newID();
   }

   public long getID(@Nullable Object obj) {
      switch (obj) {
         case null:
            return 0L;
         case Number n:
            return n.longValue();
         case NumericTag nt:
            return nt.getAsLong();
         case StringTag st:
            return this.getID(st.getAsString());
         default:
            String idStr = obj.toString();
            long id = parseCodeString(idStr);
            if (id == 0L && idStr.length() >= 2 && idStr.charAt(0) == '#') {
               String tagVal = idStr.substring(1);
               return this.questObjectMap.values().stream().filter(qob -> qob.hasTag(tagVal)).findFirst().map(qob -> qob.id).orElse(id);
            } else {
               return id;
            }
      }
   }

   public Optional<LootCrate> makeRandomLootCrate(Entity entity, RandomSource random) {
      int totalWeight = this.lootCrateNoDrop.getWeight(entity);

      for (RewardTable table : this.rewardTables) {
         if (table.getLootCrate() != null) {
            totalWeight += table.getLootCrate().getDrops().getWeight(entity);
         }
      }

      if (totalWeight <= 0) {
         return Optional.empty();
      }

      int number = random.nextInt(totalWeight) + 1;
      int currentWeight = this.lootCrateNoDrop.getWeight(entity);
      if (currentWeight < number) {
         for (RewardTable table : this.rewardTables) {
            if (table.getLootCrate() != null) {
               currentWeight += table.getLootCrate().getDrops().getWeight(entity);
               if (currentWeight >= number) {
                  return Optional.ofNullable(table.getLootCrate());
               }
            }
         }
      }

      return Optional.empty();
   }

   @Override
   public Set<RecipeModHelper.Components> componentsToRefresh() {
      return EnumSet.allOf(RecipeModHelper.Components.class);
   }

   public final Collection<QuestObjectBase> getAllObjects() {
      return Collections.unmodifiableCollection(this.questObjectMap.values());
   }

   @Override
   public boolean isVisible(TeamData data) {
      return this.chapterGroups.stream().anyMatch(group -> group.isVisible(data));
   }

   public List<Chapter> getAllChapters() {
      List<Chapter> list = new ArrayList<>();

      for (ChapterGroup g : this.chapterGroups) {
         list.addAll(g.getChapters());
      }

      return list;
   }

   public List<Task> getAllTasks() {
      if (this.allTasks == null) {
         this.allTasks = new ArrayList<>();
         this.forAllQuests(q -> this.allTasks.addAll(q.getTasks()));
      }

      return this.allTasks;
   }

   public List<Task> getSubmitTasks() {
      if (this.submitTasks == null) {
         this.submitTasks = this.getAllTasks().stream().filter(Task::submitItemsOnInventoryChange).toList();
      }

      return this.submitTasks;
   }

   public List<Task> getCraftingTasks() {
      if (this.craftingTasks == null) {
         this.craftingTasks = this.getAllTasks().stream().filter(task -> task instanceof ItemTask i && i.isOnlyFromCrafting()).toList();
      }

      return this.craftingTasks;
   }

   public List<Chapter> getVisibleChapters(TeamData data) {
      List<Chapter> list = new ArrayList<>();

      for (ChapterGroup group : this.chapterGroups) {
         list.addAll(group.getVisibleChapters(data));
      }

      return list;
   }

   @Nullable
   public Chapter getFirstVisibleChapter(TeamData data) {
      for (ChapterGroup group : this.chapterGroups) {
         Chapter c = group.getFirstVisibleChapter(data);
         if (c != null) {
            return c;
         }
      }

      return null;
   }

   public <T extends QuestObjectBase> List<T> collect(Class<T> cls, Predicate<T> filter) {
      List<T> list = new ArrayList<>();

      for (QuestObjectBase base : this.getAllObjects()) {
         if (cls.isAssignableFrom(base.getClass())) {
            T casted = (T)cls.cast(base);
            if (filter.test(casted)) {
               list.add(casted);
            }
         }
      }

      if (list.isEmpty()) {
         return Collections.emptyList();
      } else {
         return list.size() == 1 ? Collections.singletonList(list.getFirst()) : list;
      }
   }

   public <T extends QuestObjectBase> List<T> collect(Class<T> clazz) {
      return this.collect(clazz, o -> true);
   }

   public String getDefaultQuestShape() {
      return this.defaultQuestShape;
   }

   public void addData(TeamData data, boolean override) {
      if (override || !this.teamDataMap.containsKey(data.getTeamId())) {
         this.teamDataMap.put(data.getTeamId(), data);
      }
   }

   public void refreshGui() {
      this.clearCachedData();
   }

   @Override
   public Collection<? extends QuestObject> getChildren() {
      return this.chapterGroups;
   }

   @Override
   public boolean hasUnclaimedRewardsRaw(TeamData teamData, UUID player) {
      for (ChapterGroup group : this.chapterGroups) {
         if (teamData.hasUnclaimedRewards(player, group)) {
            return true;
         }
      }

      return false;
   }

   public ProgressionMode getProgressionMode() {
      return this.progressionMode;
   }

   public int getDetectionDelay() {
      return this.detectionDelay;
   }

   public boolean isPauseGame() {
      return this.pauseGame;
   }

   public boolean isDisableGui() {
      return this.disableGui;
   }

   public double getGridScale() {
      return this.gridScale;
   }

   public boolean isDropLootCrates() {
      return this.dropLootCrates;
   }

   public boolean isHideExcludedQuests() {
      return this.hideExcludedQuests;
   }

   public boolean isDefaultPerTeamReward() {
      return this.defaultPerTeamReward;
   }

   public boolean isDefaultTeamConsumeItems() {
      return this.defaultTeamConsumeItems;
   }

   public RewardAutoClaim getDefaultRewardAutoClaim() {
      return this.defaultRewardAutoClaim;
   }

   public boolean showLockIcons() {
      return this.showLockIcons;
   }

   public List<ItemStack> getEmergencyItems() {
      return Collections.unmodifiableList(this.emergencyItems);
   }

   public int getEmergencyItemsCooldown() {
      return this.emergencyItemsCooldown;
   }

   public boolean isDefaultQuestDisableJEI() {
      return this.defaultQuestDisableJEI;
   }

   public abstract boolean isPlayerOnTeam(Player var1, TeamData var2);

   public TaskType getTaskType(int typeId) {
      return (TaskType)this.taskTypeIds.get(typeId);
   }

   public RewardType getRewardType(int typeId) {
      return (RewardType)this.rewardTypeIds.get(typeId);
   }

   public DefaultChapterGroup getDefaultChapterGroup() {
      return this.defaultChapterGroup;
   }

   public List<RewardTable> getRewardTables() {
      return Collections.unmodifiableList(this.rewardTables);
   }

   public void addRewardTable(RewardTable rewardTable) {
      this.rewardTables.add(rewardTable);
   }

   public void removeRewardTable(RewardTable rewardTable) {
      this.rewardTables.remove(rewardTable);
   }

   public int removeEmptyRewardTables(CommandSourceStack source) {
      MutableInt del = new MutableInt(0);

      for (RewardTable table : this.rewardTables) {
         if (table.getWeightedRewards().isEmpty()) {
            del.increment();
            table.invalid = true;
            FileUtils.delete(ServerQuestFile.INSTANCE.getFolder().resolve(table.getPath().orElseThrow()).toFile());
            NetworkHelper.sendToAll(source.getServer(), new DeleteObjectResponseMessage(table.id));
         }
      }

      if (this.rewardTables.removeIf(rewardTable -> rewardTable.invalid)) {
         this.refreshIDMap();
         this.markDirty();
      }

      return del.intValue();
   }

   public List<ChapterGroup> getChapterGroups() {
      return Collections.unmodifiableList(this.chapterGroups);
   }

   public void forAllChapterGroups(Consumer<ChapterGroup> consumer) {
      this.chapterGroups.forEach(consumer);
   }

   @Override
   public void forAllChapters(Consumer<Chapter> consumer) {
      this.forAllChapterGroups(g -> g.getChapters().forEach(consumer));
   }

   @Override
   public void forAllQuests(Consumer<Quest> consumer) {
      this.forAllChapters(c -> c.getQuests().forEach(consumer));
   }

   @Override
   public void forAllQuestLinks(Consumer<QuestLink> consumer) {
      this.forAllChapters(c -> c.getQuestLinks().forEach(consumer));
   }

   public boolean moveChapterGroup(long id, boolean movingUp) {
      ChapterGroup group = this.getChapterGroup(id);
      if (!group.isDefaultGroup()) {
         int index = this.chapterGroups.indexOf(group);
         if (index != -1 && movingUp ? index > 1 : index < this.chapterGroups.size() - 1) {
            this.chapterGroups.remove(index);
            this.chapterGroups.add(movingUp ? index - 1 : index + 1, group);
            return true;
         }
      }

      return false;
   }

   public EntityWeight getLootCrateNoDrop() {
      return this.lootCrateNoDrop;
   }

   public abstract String getLocale();

   public String getFallbackLocale() {
      return this.fallbackLocale.isEmpty() ? "en_us" : this.fallbackLocale;
   }

   public boolean dropBookOnDeath() {
      return this.dropBookOnDeath;
   }
}
