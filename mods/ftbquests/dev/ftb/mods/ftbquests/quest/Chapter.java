package dev.ftb.mods.ftbquests.quest;

import dev.architectury.event.EventActor;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.config.Tristate;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.IconAnimation;
import dev.ftb.mods.ftblibrary.math.Bits;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftbquests.events.ObjectCompletedEvent;
import dev.ftb.mods.ftbquests.events.ObjectStartedEvent;
import dev.ftb.mods.ftbquests.events.QuestProgressEventData;
import dev.ftb.mods.ftbquests.quest.translation.TranslationKey;
import dev.ftb.mods.ftbquests.util.NetUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public final class Chapter extends QuestObject {
   private static final Pattern HEX_STRING = Pattern.compile("^([a-fA-F0-9]+)?$");
   public final BaseQuestFile file;
   private ChapterGroup group;
   private String filename;
   private final List<Quest> quests;
   private final List<QuestLink> questLinks;
   private boolean alwaysInvisible;
   private String defaultQuestShape;
   private double defaultQuestSize;
   private final List<ChapterImage> images;
   boolean defaultHideDependencyLines;
   private int defaultMinWidth = 0;
   private ProgressionMode progressionMode;
   private boolean hideQuestDetailsUntilStartable;
   private boolean hideQuestUntilDepsComplete;
   private boolean hideQuestUntilDepsVisible;
   private boolean hideTextUntilComplete;
   private boolean defaultRepeatable;
   private Tristate consumeItems;
   private boolean requireSequentialTasks;
   private String autoFocusId;

   public Chapter(long id, BaseQuestFile file, ChapterGroup group) {
      this(id, file, group, "");
   }

   public Chapter(long id, BaseQuestFile file, ChapterGroup group, String filename) {
      super(id);
      this.file = file;
      this.group = group;
      this.filename = filename;
      this.quests = new ArrayList<>();
      this.questLinks = new ArrayList<>();
      this.alwaysInvisible = false;
      this.defaultQuestShape = "";
      this.defaultQuestSize = 1.0;
      this.images = new ArrayList<>();
      this.defaultHideDependencyLines = false;
      this.progressionMode = ProgressionMode.DEFAULT;
      this.hideQuestUntilDepsVisible = false;
      this.hideQuestUntilDepsComplete = false;
      this.hideQuestDetailsUntilStartable = false;
      this.defaultRepeatable = false;
      this.consumeItems = Tristate.DEFAULT;
      this.requireSequentialTasks = false;
      this.autoFocusId = "";
   }

   public void setDefaultQuestShape(String defaultQuestShape) {
      this.defaultQuestShape = defaultQuestShape;
   }

   public ChapterGroup getGroup() {
      return this.group;
   }

   void setGroup(ChapterGroup group) {
      this.group = group;
   }

   @Override
   public QuestObjectType getObjectType() {
      return QuestObjectType.CHAPTER;
   }

   @Override
   public BaseQuestFile getQuestFile() {
      return this.group.getFile();
   }

   @Override
   public Chapter getQuestChapter() {
      return this;
   }

   public int getDefaultMinWidth() {
      return this.defaultMinWidth;
   }

   public boolean isAlwaysInvisible() {
      return this.alwaysInvisible;
   }

   public boolean isDefaultRepeatable() {
      return this.defaultRepeatable;
   }

   public boolean isRequireSequentialTasks() {
      return this.requireSequentialTasks;
   }

   public boolean isHideTextUntilComplete() {
      return this.hideTextUntilComplete;
   }

   public List<Quest> getQuests() {
      return Collections.unmodifiableList(this.quests);
   }

   public List<QuestLink> getQuestLinks() {
      return Collections.unmodifiableList(this.questLinks);
   }

   public List<ChapterImage> getImages() {
      return Collections.unmodifiableList(this.images);
   }

   public void addQuest(Quest quest) {
      this.quests.add(quest);
   }

   public void removeQuest(Quest quest) {
      this.quests.remove(quest);
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      nbt.putString("filename", this.filename);
      super.writeData(nbt, provider);
      if (this.alwaysInvisible) {
         nbt.putBoolean("always_invisible", true);
      }

      nbt.putString("default_quest_shape", this.defaultQuestShape);
      if (this.defaultQuestSize != 1.0) {
         nbt.putDouble("default_quest_size", this.defaultQuestSize);
      }

      nbt.putBoolean("default_hide_dependency_lines", this.defaultHideDependencyLines);
      if (!this.images.isEmpty()) {
         ListTag list = new ListTag();

         for (ChapterImage image : this.images) {
            SNBTCompoundTag nbt1 = new SNBTCompoundTag();
            image.writeData(nbt1);
            list.add(nbt1);
         }

         nbt.put("images", list);
      }

      if (this.defaultMinWidth > 0) {
         nbt.putInt("default_min_width", this.defaultMinWidth);
      }

      if (this.progressionMode != ProgressionMode.DEFAULT) {
         nbt.putString("progression_mode", this.progressionMode.getId());
      }

      this.consumeItems.write(nbt, "consume_items");
      if (this.hideQuestDetailsUntilStartable) {
         nbt.putBoolean("hide_quest_details_until_startable", true);
      }

      if (this.hideQuestUntilDepsVisible) {
         nbt.putBoolean("hide_quest_until_deps_visible", true);
      }

      if (this.hideQuestUntilDepsComplete) {
         nbt.putBoolean("hide_quest_until_deps_complete", true);
      }

      if (this.hideTextUntilComplete) {
         nbt.putBoolean("hide_text_until_complete", true);
      }

      if (this.defaultRepeatable) {
         nbt.putBoolean("default_repeatable_quest", true);
      }

      if (this.requireSequentialTasks) {
         nbt.putBoolean("require_sequential_tasks", true);
      }

      if (!this.autoFocusId.isEmpty()) {
         nbt.putString("autofocus_id", this.autoFocusId);
      }
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      this.filename = nbt.getString("filename");
      super.readData(nbt, provider);
      this.alwaysInvisible = nbt.getBoolean("always_invisible");
      this.defaultQuestShape = nbt.getString("default_quest_shape");
      if (this.defaultQuestShape.equals("default")) {
         this.defaultQuestShape = "";
      }

      this.defaultQuestSize = nbt.contains("default_quest_size", 6) ? nbt.getDouble("default_quest_size") : 1.0;
      this.defaultHideDependencyLines = nbt.getBoolean("default_hide_dependency_lines");
      ListTag imgs = nbt.getList("images", 10);
      this.images.clear();

      for (int i = 0; i < imgs.size(); i++) {
         ChapterImage image = new ChapterImage(this);
         image.readData(imgs.getCompound(i));
         this.images.add(image);
      }

      this.defaultMinWidth = nbt.getInt("default_min_width");
      this.progressionMode = (ProgressionMode)ProgressionMode.NAME_MAP.get(nbt.getString("progression_mode"));
      this.consumeItems = Tristate.read(nbt, "consume_items");
      this.hideQuestDetailsUntilStartable = nbt.getBoolean("hide_quest_details_until_startable");
      this.hideQuestUntilDepsVisible = nbt.getBoolean("hide_quest_until_deps_visible");
      this.hideQuestUntilDepsComplete = nbt.getBoolean("hide_quest_until_deps_complete");
      this.hideTextUntilComplete = nbt.getBoolean("hide_text_until_complete");
      this.defaultRepeatable = nbt.getBoolean("default_repeatable_quest");
      this.requireSequentialTasks = nbt.getBoolean("require_sequential_tasks");
      this.autoFocusId = nbt.getString("autofocus_id");
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeUtf(this.filename, 32767);
      buffer.writeUtf(this.defaultQuestShape, 32767);
      buffer.writeDouble(this.defaultQuestSize);
      buffer.writeCollection(this.images, (buf, img) -> img.writeNetData(buf));
      buffer.writeInt(this.defaultMinWidth);
      ProgressionMode.NAME_MAP.write(buffer, this.progressionMode);
      int flags = 0;
      flags = Bits.setFlag(flags, 1, this.alwaysInvisible);
      flags = Bits.setFlag(flags, 2, this.defaultHideDependencyLines);
      flags = Bits.setFlag(flags, 4, this.hideQuestDetailsUntilStartable);
      flags = Bits.setFlag(flags, 8, this.hideQuestUntilDepsComplete);
      flags = Bits.setFlag(flags, 16, this.defaultRepeatable);
      flags = Bits.setFlag(flags, 32, this.consumeItems != Tristate.DEFAULT);
      flags = Bits.setFlag(flags, 64, this.consumeItems == Tristate.TRUE);
      flags = Bits.setFlag(flags, 128, this.requireSequentialTasks);
      flags = Bits.setFlag(flags, 256, !this.autoFocusId.isEmpty());
      flags = Bits.setFlag(flags, 512, this.hideQuestUntilDepsVisible);
      flags = Bits.setFlag(flags, 1024, this.hideTextUntilComplete);
      buffer.writeVarInt(flags);
      if (!this.autoFocusId.isEmpty()) {
         buffer.writeLong(QuestObjectBase.parseHexId(this.autoFocusId).orElse(0L));
      }
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.filename = buffer.readUtf(32767);
      this.defaultQuestShape = buffer.readUtf(32767);
      this.defaultQuestSize = buffer.readDouble();
      NetUtils.read(buffer, this.images, buf -> ChapterImage.fromNet(this, buf));
      this.defaultMinWidth = buffer.readInt();
      this.progressionMode = (ProgressionMode)ProgressionMode.NAME_MAP.read(buffer);
      int flags = buffer.readVarInt();
      this.alwaysInvisible = Bits.getFlag(flags, 1);
      this.defaultHideDependencyLines = Bits.getFlag(flags, 2);
      this.hideQuestDetailsUntilStartable = Bits.getFlag(flags, 4);
      this.hideQuestUntilDepsComplete = Bits.getFlag(flags, 8);
      this.defaultRepeatable = Bits.getFlag(flags, 16);
      this.consumeItems = Bits.getFlag(flags, 32) ? (Bits.getFlag(flags, 64) ? Tristate.TRUE : Tristate.FALSE) : Tristate.DEFAULT;
      this.requireSequentialTasks = Bits.getFlag(flags, 128);
      this.hideQuestUntilDepsVisible = Bits.getFlag(flags, 512);
      this.hideTextUntilComplete = Bits.getFlag(flags, 1024);
      this.autoFocusId = Bits.getFlag(flags, 256) ? QuestObjectBase.getCodeString(buffer.readLong()) : "";
   }

   public int getIndex() {
      return this.group.getChapters().indexOf(this);
   }

   @Override
   public int getRelativeProgressFromChildren(TeamData data) {
      if (this.alwaysInvisible) {
         return 100;
      }

      if (this.quests.isEmpty()) {
         return 100;
      }

      int progress = 0;
      int count = 0;

      for (Quest quest : this.quests) {
         if (!quest.isOptionalForProgression(data)) {
            progress += data.getRelativeProgress(quest);
            count++;
         }
      }

      return count <= 0 ? 100 : getRelativeProgressFromChildren(progress, count);
   }

   @Override
   public void onStarted(QuestProgressEventData<?> data) {
      data.setStarted(this.id);
      ((EventActor)ObjectStartedEvent.CHAPTER.invoker()).act(new ObjectStartedEvent.ChapterEvent(data.withObject(this)));
      if (!data.getTeamData().isStarted(this.file)) {
         this.file.onStarted(data.withObject(this.file));
      }
   }

   @Override
   public void onCompleted(QuestProgressEventData<?> data) {
      data.setCompleted(this.id);
      ((EventActor)ObjectCompletedEvent.CHAPTER.invoker()).act(new ObjectCompletedEvent.ChapterEvent(data.withObject(this)));
      if (!this.disableToast) {
         data.notifyPlayers(this.id);
      }

      this.file.forAllQuests(quest -> {
         if (quest.hasDependency(this)) {
            data.getTeamData().checkAutoCompletion(quest);
         }
      });
      if (this.group.isCompletedRaw(data.getTeamData())) {
         this.group.onCompleted(data.withObject(this.group));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public MutableComponent getAltTitle() {
      return Component.translatable("ftbquests.unnamed");
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Icon getAltIcon() {
      List<Icon> list = new ArrayList<>();

      for (Quest quest : this.quests) {
         list.add(quest.getIcon());
      }

      return IconAnimation.fromList(list, false);
   }

   @Override
   public void deleteSelf() {
      super.deleteSelf();
      this.group.removeChapter(this);
   }

   @Override
   public void deleteChildren() {
      for (Quest quest : this.quests) {
         quest.deleteChildren();
         quest.invalid = true;
      }

      this.quests.clear();
   }

   @Override
   public void onCreated() {
      if (this.filename.isEmpty()) {
         this.filename = this.getCodeString();
      }

      Set<String> existingNames = new HashSet<>();
      this.getQuestFile().forAllChapters(ch -> existingNames.add(ch.filename));
      if (existingNames.contains(this.filename)) {
         this.filename = this.filename + "_" + this.getCodeString();
      }

      this.group.addChapter(this);
      if (!this.quests.isEmpty()) {
         List<Quest> l = new ArrayList<>(this.quests);
         this.quests.clear();

         for (Quest quest : l) {
            quest.onCreated();
         }
      }
   }

   public String getFilename() {
      if (this.filename.isEmpty()) {
         this.filename = getCodeString(this);
      }

      return this.filename;
   }

   @Override
   public Optional<String> getPath() {
      return Optional.of("chapters/" + this.getFilename() + ".snbt");
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addList("subtitle", this.getRawSubtitle(), new StringConfig(), this::setRawSubtitle, "");
      ConfigGroup appearance = config.getOrCreateSubgroup("appearance").setNameKey("ftbquests.quest.appearance");
      appearance.addEnum(
         "default_quest_shape",
         this.defaultQuestShape.isEmpty() ? "default" : this.defaultQuestShape,
         v -> this.defaultQuestShape = v.equals("default") ? "" : v,
         QuestShape.idMapWithDefault
      );
      appearance.addDouble("default_quest_size", this.defaultQuestSize, v -> this.defaultQuestSize = v, 1.0, 0.0625, 8.0);
      appearance.addInt("default_min_width", this.defaultMinWidth, v -> this.defaultMinWidth = v, 0, 0, 3000);
      ConfigGroup visibility = config.getOrCreateSubgroup("visibility").setNameKey("ftbquests.quest.visibility");
      visibility.addBool("always_invisible", this.alwaysInvisible, v -> this.alwaysInvisible = v, false);
      visibility.addBool("default_hide_dependency_lines", this.defaultHideDependencyLines, v -> this.defaultHideDependencyLines = v, false);
      visibility.addBool("hide_quest_details_until_startable", this.hideQuestDetailsUntilStartable, v -> this.hideQuestDetailsUntilStartable = v, false);
      visibility.addBool("hide_quest_until_deps_visible", this.hideQuestUntilDepsVisible, v -> this.hideQuestUntilDepsVisible = v, false);
      visibility.addBool("hide_quest_until_deps_complete", this.hideQuestUntilDepsComplete, v -> this.hideQuestUntilDepsComplete = v, false);
      visibility.addBool("hide_text_until_complete", this.hideTextUntilComplete, v -> this.hideTextUntilComplete = v, false);
      ConfigGroup misc = config.getOrCreateSubgroup("misc").setNameKey("ftbquests.quest.misc");
      misc.addString("autofocus_id", this.autoFocusId, v -> this.autoFocusId = v, "", HEX_STRING);
      misc.addEnum("progression_mode", this.progressionMode, v -> this.progressionMode = v, ProgressionMode.NAME_MAP);
      misc.addBool("default_repeatable", this.defaultRepeatable, v -> this.defaultRepeatable = v, false);
      misc.addTristate("consume_items", this.consumeItems, v -> this.consumeItems = v);
      misc.addBool("require_sequential_tasks", this.requireSequentialTasks, v -> this.requireSequentialTasks = v, false);
   }

   @Override
   public boolean isVisible(TeamData data) {
      return !this.alwaysInvisible
         && (
            this.quests.isEmpty() && this.questLinks.isEmpty()
               || this.quests.stream().anyMatch(quest -> quest.isVisible(data))
               || this.questLinks.stream().anyMatch(link -> link.isVisible(data))
         );
   }

   @Override
   public void clearCachedData() {
      super.clearCachedData();

      for (Quest quest : this.quests) {
         quest.clearCachedData();
      }
   }

   @Override
   protected void verifyDependenciesInternal(long original, int depth) {
      if (depth >= 1000) {
         throw new DependencyDepthException(this);
      }

      for (Quest quest : this.quests) {
         if (quest.id == original) {
            throw new DependencyLoopException(this);
         }

         quest.verifyDependenciesInternal(original, depth + 1);
      }
   }

   public boolean hasGroup() {
      return !this.group.isDefaultGroup();
   }

   public String getDefaultQuestShape() {
      return this.defaultQuestShape.isEmpty() ? this.file.getDefaultQuestShape() : this.defaultQuestShape;
   }

   @Override
   public Collection<? extends QuestObject> getChildren() {
      return this.quests;
   }

   @Override
   public boolean hasUnclaimedRewardsRaw(TeamData teamData, UUID player) {
      for (Quest quest : this.quests) {
         if (teamData.hasUnclaimedRewards(player, quest)) {
            return true;
         }
      }

      return false;
   }

   public ProgressionMode getProgressionMode() {
      return this.progressionMode == ProgressionMode.DEFAULT ? this.file.getProgressionMode() : this.progressionMode;
   }

   public boolean hideQuestDetailsUntilStartable() {
      return this.hideQuestDetailsUntilStartable;
   }

   public boolean hideQuestUntilDepsComplete() {
      return this.hideQuestUntilDepsComplete;
   }

   public boolean isHideQuestUntilDepsVisible() {
      return this.hideQuestUntilDepsVisible;
   }

   public void addImage(ChapterImage image) {
      this.images.add(image);
   }

   public void removeImage(ChapterImage image) {
      this.images.remove(image);
   }

   public void addQuestLink(QuestLink link) {
      this.questLinks.add(link);
   }

   public void removeQuestLink(QuestLink link) {
      this.questLinks.remove(link);
   }

   public List<String> getRawSubtitle() {
      return this.file.getTranslationManager().getStringListTranslation(this, this.file.getLocale(), TranslationKey.CHAPTER_SUBTITLE).orElse(List.of());
   }

   public void setRawSubtitle(List<String> rawSubtitle) {
      this.setTranslatableValue(TranslationKey.CHAPTER_SUBTITLE, rawSubtitle);
   }

   public boolean consumeItems() {
      return this.consumeItems.get(this.file.isDefaultTeamConsumeItems());
   }

   public double getDefaultQuestSize() {
      return this.defaultQuestSize;
   }

   public boolean hasAnyVisibleChildren() {
      return !this.quests.isEmpty() || !this.questLinks.isEmpty();
   }

   public Optional<Movable> getAutofocus() {
      return this.autoFocusId != null && !this.autoFocusId.isEmpty()
         ? QuestObjectBase.parseHexId(this.autoFocusId)
            .flatMap(id -> this.file.get(id) instanceof Movable m && m.getChapter() == this ? Optional.of(m) : Optional.empty())
         : Optional.empty();
   }

   public void setAutofocus(long id) {
      this.autoFocusId = id == 0L ? "" : QuestObjectBase.getCodeString(id);
   }

   public boolean isAutofocus(long id) {
      return id == this.getAutofocus().map(Movable::getMovableID).orElse(0L);
   }
}
