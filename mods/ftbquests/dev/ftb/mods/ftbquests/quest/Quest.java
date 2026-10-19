package dev.ftb.mods.ftbquests.quest;

import com.mojang.datafixers.util.Pair;
import dev.architectury.event.EventActor;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.ConfigCallback;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ListConfig;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.config.Tristate;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.IconAnimation;
import dev.ftb.mods.ftblibrary.math.Bits;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.client.ClientUtils;
import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.client.FTBQuestsClient;
import dev.ftb.mods.ftbquests.client.gui.MultilineTextEditorScreen;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import dev.ftb.mods.ftbquests.events.ObjectCompletedEvent;
import dev.ftb.mods.ftbquests.events.ObjectStartedEvent;
import dev.ftb.mods.ftbquests.events.QuestProgressEventData;
import dev.ftb.mods.ftbquests.integration.RecipeModHelper;
import dev.ftb.mods.ftbquests.net.MoveMovableMessage;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import dev.ftb.mods.ftbquests.quest.translation.TranslationKey;
import dev.ftb.mods.ftbquests.util.ConfigQuestObject;
import dev.ftb.mods.ftbquests.util.ProgressChange;
import dev.ftb.mods.ftbquests.util.TextUtils;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public final class Quest extends QuestObject implements Movable, Excludable {
   public static final String PAGEBREAK_CODE = "{@pagebreak}";
   private Chapter chapter;
   private double x;
   private double y;
   private Tristate hideUntilDepsVisible;
   private Tristate hideUntilDepsComplete;
   private String shape;
   private final List<QuestObject> dependencies;
   private final List<Task> tasks;
   private final List<Reward> rewards;
   private DependencyRequirement dependencyRequirement;
   private String guidePage;
   private Tristate hideDependencyLines;
   private boolean hideDependentLines;
   private int minRequiredDependencies;
   private Tristate hideTextUntilComplete;
   private Tristate disableJEI;
   private Tristate hideDetailsUntilStartable;
   private double size;
   private boolean optional;
   private int minWidth;
   private Tristate canRepeat;
   private boolean invisibleUntilCompleted;
   private int invisibleUntilTasks;
   private Tristate requireSequentialTasks;
   private double iconScale;
   private boolean hideLockIcon;
   private int maxCompletableDeps;
   private int repeatCooldown;
   private Component cachedSubtitle = null;
   private List<Component> cachedDescription = null;
   private boolean ignoreRewardBlocking;
   private ProgressionMode progressionMode;
   private final Set<Long> dependantIDs;

   public Quest(long id, Chapter chapter) {
      super(id);
      this.chapter = chapter;
      this.x = 0.0;
      this.y = 0.0;
      this.shape = "";
      this.dependencies = new ArrayList<>(0);
      this.tasks = new ArrayList<>(1);
      this.rewards = new ArrayList<>(1);
      this.guidePage = "";
      this.hideDependencyLines = Tristate.DEFAULT;
      this.hideDependentLines = false;
      this.hideUntilDepsVisible = Tristate.DEFAULT;
      this.hideUntilDepsComplete = Tristate.DEFAULT;
      this.dependencyRequirement = DependencyRequirement.ALL_COMPLETED;
      this.minRequiredDependencies = 0;
      this.hideTextUntilComplete = Tristate.DEFAULT;
      this.disableJEI = Tristate.DEFAULT;
      this.hideDetailsUntilStartable = Tristate.DEFAULT;
      this.size = 0.0;
      this.optional = false;
      this.minWidth = 0;
      this.canRepeat = Tristate.DEFAULT;
      this.invisibleUntilCompleted = false;
      this.invisibleUntilTasks = 0;
      this.ignoreRewardBlocking = false;
      this.progressionMode = ProgressionMode.DEFAULT;
      this.dependantIDs = new HashSet<>();
      this.requireSequentialTasks = Tristate.DEFAULT;
      this.iconScale = 1.0;
      this.hideLockIcon = false;
      this.maxCompletableDeps = 0;
      this.repeatCooldown = 0;
   }

   @Override
   public QuestObjectType getObjectType() {
      return QuestObjectType.QUEST;
   }

   @Override
   public BaseQuestFile getQuestFile() {
      return this.chapter.file;
   }

   @Override
   public Chapter getQuestChapter() {
      return this.chapter;
   }

   @Override
   public long getParentID() {
      return this.chapter.id;
   }

   public Collection<Task> getTasks() {
      return Collections.unmodifiableList(this.tasks);
   }

   public List<Task> getTasksAsList() {
      return Collections.unmodifiableList(this.tasks);
   }

   public Collection<Reward> getRewards() {
      return Collections.unmodifiableList(this.rewards);
   }

   public int getMinRequiredDependencies() {
      return this.minRequiredDependencies;
   }

   public boolean shouldHideDependentLines() {
      return this.hideDependentLines;
   }

   public String getGuidePage() {
      return this.guidePage;
   }

   public Tristate getHideTextUntilComplete() {
      return this.hideTextUntilComplete;
   }

   public boolean showInRecipeMod() {
      return !this.disableJEI.get(this.getQuestFile().isDefaultQuestDisableJEI());
   }

   public String getRawSubtitle() {
      return this.getQuestFile().getTranslationManager().getStringTranslation(this, this.getQuestFile().getLocale(), TranslationKey.QUEST_SUBTITLE).orElse("");
   }

   public void setRawSubtitle(String rawSubtitle) {
      this.setTranslatableValue(TranslationKey.QUEST_SUBTITLE, rawSubtitle);
      this.cachedSubtitle = null;
   }

   public void setX(double x) {
      this.x = x;
   }

   public void setY(double y) {
      this.y = y;
   }

   public double getSize() {
      return this.size == 0.0 ? this.chapter.getDefaultQuestSize() : this.size;
   }

   public void setSize(double size) {
      this.size = size;
   }

   public boolean isOptional() {
      return this.optional;
   }

   public int getMinWidth() {
      return this.minWidth;
   }

   public boolean canBeRepeated() {
      return this.canRepeat.get(this.chapter.isDefaultRepeatable());
   }

   public List<String> getRawDescription() {
      return this.getQuestFile()
         .getTranslationManager()
         .getStringListTranslation(this, this.getQuestFile().getLocale(), TranslationKey.QUEST_DESC)
         .orElse(List.of());
   }

   public void setRawDescription(List<String> rawDescription) {
      this.setTranslatableValue(TranslationKey.QUEST_DESC, rawDescription);
      this.cachedDescription = null;
   }

   public double getIconScale() {
      return this.iconScale;
   }

   @Override
   public boolean isOptionalForProgression(TeamData teamData) {
      return this.isOptional() || this.canBeRepeated() || teamData.isExcludedByOtherQuestline(this);
   }

   public boolean getRequireSequentialTasks() {
      return this.requireSequentialTasks.get(this.chapter.isRequireSequentialTasks());
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putDouble("x", this.x);
      nbt.putDouble("y", this.y);
      if (!this.shape.isEmpty()) {
         nbt.putString("shape", this.shape);
      }

      if (!this.guidePage.isEmpty()) {
         nbt.putString("guide_page", this.guidePage);
      }

      this.hideDependencyLines.write(nbt, "hide_dependency_lines");
      if (this.hideDependentLines) {
         nbt.putBoolean("hide_dependent_lines", true);
      }

      if (this.minRequiredDependencies > 0) {
         nbt.putInt("min_required_dependencies", (byte)this.minRequiredDependencies);
      }

      this.removeInvalidDependencies();
      if (this.hasDependencies()) {
         ListTag deps = new ListTag();

         for (QuestObject dep : this.dependencies) {
            deps.add(StringTag.valueOf(dep.getCodeString()));
         }

         nbt.put("dependencies", deps);
      }

      this.disableJEI.write(nbt, "disable_recipe_mod");
      this.hideUntilDepsVisible.write(nbt, "hide_until_deps_visible");
      this.hideUntilDepsComplete.write(nbt, "hide_until_deps_complete");
      if (this.dependencyRequirement != DependencyRequirement.ALL_COMPLETED) {
         nbt.putString("dependency_requirement", this.dependencyRequirement.getId());
      }

      this.hideTextUntilComplete.write(nbt, "hide_text_until_complete");
      if (this.size != 0.0) {
         nbt.putDouble("size", this.size);
      }

      if (this.iconScale != 1.0) {
         nbt.putDouble("icon_scale", this.iconScale);
      }

      if (this.optional) {
         nbt.putBoolean("optional", true);
      }

      if (this.minWidth > 0) {
         nbt.putInt("min_width", this.minWidth);
      }

      this.canRepeat.write(nbt, "can_repeat");
      if (this.invisibleUntilCompleted) {
         nbt.putBoolean("invisible", true);
      }

      if (this.invisibleUntilTasks > 0) {
         nbt.putInt("invisible_until_tasks", this.invisibleUntilTasks);
      }

      if (this.ignoreRewardBlocking) {
         nbt.putBoolean("ignore_reward_blocking", true);
      }

      if (this.progressionMode != ProgressionMode.DEFAULT) {
         nbt.putString("progression_mode", this.progressionMode.getId());
      }

      this.hideDetailsUntilStartable.write(nbt, "hide_details_until_startable");
      this.requireSequentialTasks.write(nbt, "require_sequential_tasks");
      if (this.hideLockIcon) {
         nbt.putBoolean("hide_lock_icon", true);
      }

      if (this.maxCompletableDeps > 0) {
         nbt.putInt("max_completable_dependents", this.maxCompletableDeps);
      }

      if (this.repeatCooldown > 0) {
         nbt.putInt("repeat_cooldown", this.repeatCooldown);
      }
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.x = nbt.getDouble("x");
      this.y = nbt.getDouble("y");
      this.shape = nbt.getString("shape");
      if (this.shape.equals("default")) {
         this.shape = "";
      }

      this.guidePage = nbt.getString("guide_page");
      this.hideDependencyLines = Tristate.read(nbt, "hide_dependency_lines");
      this.hideDependentLines = nbt.getBoolean("hide_dependent_lines");
      this.minRequiredDependencies = nbt.getInt("min_required_dependencies");
      this.clearDependencies();
      if (nbt.contains("dependencies", 11)) {
         for (int i : nbt.getIntArray("dependencies")) {
            QuestObject object = this.chapter.file.get(i);
            if (object != null) {
               this.addDependency(object);
            }
         }
      } else {
         ListTag deps = nbt.getList("dependencies", 8);

         for (int i = 0; i < deps.size(); i++) {
            QuestObject object = this.chapter.file.get(this.chapter.file.getID(deps.getString(i)));
            if (object != null) {
               this.addDependency(object);
            }
         }
      }

      if (nbt.contains("hide", 1)) {
         this.hideUntilDepsVisible = Tristate.read(nbt, "hide");
      } else {
         this.hideUntilDepsVisible = Tristate.read(nbt, "hide_until_deps_visible");
      }

      this.hideUntilDepsComplete = Tristate.read(nbt, "hide_until_deps_complete");
      this.disableJEI = Tristate.read(nbt, "disable_recipe_mod");
      this.dependencyRequirement = (DependencyRequirement)DependencyRequirement.NAME_MAP.get(nbt.getString("dependency_requirement"));
      this.hideTextUntilComplete = Tristate.read(nbt, "hide_text_until_complete");
      this.size = nbt.getDouble("size");
      this.iconScale = nbt.contains("icon_scale", 6) ? nbt.getDouble("icon_scale") : 1.0;
      this.optional = nbt.getBoolean("optional");
      this.minWidth = nbt.getInt("min_width");
      this.canRepeat = Tristate.read(nbt, "can_repeat");
      this.invisibleUntilCompleted = nbt.getBoolean("invisible");
      this.invisibleUntilTasks = nbt.getInt("invisible_until_tasks");
      this.ignoreRewardBlocking = nbt.getBoolean("ignore_reward_blocking");
      this.progressionMode = (ProgressionMode)ProgressionMode.NAME_MAP.get(nbt.getString("progression_mode"));
      this.hideDetailsUntilStartable = Tristate.read(nbt, "hide_details_until_startable");
      this.requireSequentialTasks = Tristate.read(nbt, "require_sequential_tasks");
      this.hideLockIcon = nbt.getBoolean("hide_lock_icon");
      this.maxCompletableDeps = nbt.getInt("max_completable_dependents");
      this.repeatCooldown = nbt.getInt("repeat_cooldown");
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      int flags = 0;
      flags = Bits.setFlag(flags, 4, this.size != 0.0);
      flags = Bits.setFlag(flags, 8, !this.guidePage.isEmpty());
      flags = Bits.setFlag(flags, 16, this.ignoreRewardBlocking);
      flags = Bits.setFlag(flags, 32, this.hideDependentLines);
      flags = Bits.setFlag(flags, 128, this.invisibleUntilCompleted);
      flags = Bits.setFlag(flags, 256, this.optional);
      flags = Bits.setFlag(flags, 512, this.minWidth > 0);
      flags = Bits.setFlag(flags, 1024, this.invisibleUntilTasks > 0);
      flags = Bits.setFlag(flags, 2048, this.hideDetailsUntilStartable != Tristate.DEFAULT);
      flags = Bits.setFlag(flags, 4096, this.hideDetailsUntilStartable == Tristate.TRUE);
      flags = Bits.setFlag(flags, 8192, this.canRepeat != Tristate.DEFAULT);
      flags = Bits.setFlag(flags, 16384, this.canRepeat == Tristate.TRUE);
      flags = Bits.setFlag(flags, 32768, this.requireSequentialTasks != Tristate.DEFAULT);
      flags = Bits.setFlag(flags, 65536, this.requireSequentialTasks == Tristate.TRUE);
      flags = Bits.setFlag(flags, 131072, this.iconScale != 1.0);
      flags = Bits.setFlag(flags, 262144, this.hideLockIcon);
      flags = Bits.setFlag(flags, 524288, this.disableJEI != Tristate.DEFAULT);
      flags = Bits.setFlag(flags, 1048576, this.disableJEI == Tristate.TRUE);
      buffer.writeVarInt(flags);
      this.hideUntilDepsComplete.write(buffer);
      this.hideUntilDepsVisible.write(buffer);
      this.hideDependencyLines.write(buffer);
      this.hideTextUntilComplete.write(buffer);
      buffer.writeDouble(this.x);
      buffer.writeDouble(this.y);
      buffer.writeUtf(this.shape, 32767);
      if (!this.guidePage.isEmpty()) {
         buffer.writeUtf(this.guidePage, 32767);
      }

      buffer.writeVarInt(this.minRequiredDependencies);
      DependencyRequirement.NAME_MAP.write(buffer, this.dependencyRequirement);
      buffer.writeVarInt(this.dependencies.size());

      for (QuestObject d : this.dependencies) {
         buffer.writeLong(d.invalid ? 0L : d.id);
      }

      if (this.size != 0.0) {
         buffer.writeDouble(this.size);
      }

      if (this.iconScale != 1.0) {
         buffer.writeDouble(this.iconScale);
      }

      if (this.minWidth > 0) {
         buffer.writeVarInt(this.minWidth);
      }

      if (this.invisibleUntilTasks > 0) {
         buffer.writeVarInt(this.invisibleUntilTasks);
      }

      ProgressionMode.NAME_MAP.write(buffer, this.progressionMode);
      buffer.writeVarInt(this.maxCompletableDeps);
      if (this.canRepeat.isTrue()) {
         buffer.writeInt(this.repeatCooldown);
      }
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      int flags = buffer.readVarInt();
      this.hideUntilDepsComplete = Tristate.read(buffer);
      this.hideUntilDepsVisible = Tristate.read(buffer);
      this.hideDependencyLines = Tristate.read(buffer);
      this.hideTextUntilComplete = Tristate.read(buffer);
      this.x = buffer.readDouble();
      this.y = buffer.readDouble();
      this.shape = buffer.readUtf(32767);
      this.guidePage = Bits.getFlag(flags, 8) ? buffer.readUtf(32767) : "";
      this.minRequiredDependencies = buffer.readVarInt();
      this.dependencyRequirement = (DependencyRequirement)DependencyRequirement.NAME_MAP.read(buffer);
      this.clearDependencies();
      int d = buffer.readVarInt();

      for (int i = 0; i < d; i++) {
         QuestObject object = this.chapter.file.get(buffer.readLong());
         if (object != null) {
            this.addDependency(object);
         }
      }

      this.size = Bits.getFlag(flags, 4) ? buffer.readDouble() : 0.0;
      this.iconScale = Bits.getFlag(flags, 131072) ? buffer.readDouble() : 1.0;
      this.minWidth = Bits.getFlag(flags, 512) ? buffer.readVarInt() : 0;
      this.ignoreRewardBlocking = Bits.getFlag(flags, 16);
      this.hideDependentLines = Bits.getFlag(flags, 32);
      this.canRepeat = Bits.getFlag(flags, 8192) ? (Bits.getFlag(flags, 16384) ? Tristate.TRUE : Tristate.FALSE) : Tristate.DEFAULT;
      this.invisibleUntilCompleted = Bits.getFlag(flags, 128);
      this.optional = Bits.getFlag(flags, 256);
      this.invisibleUntilTasks = Bits.getFlag(flags, 1024) ? buffer.readVarInt() : 0;
      this.hideDetailsUntilStartable = Bits.getFlag(flags, 2048) ? (Bits.getFlag(flags, 4096) ? Tristate.TRUE : Tristate.FALSE) : Tristate.DEFAULT;
      this.requireSequentialTasks = Bits.getFlag(flags, 32768) ? (Bits.getFlag(flags, 65536) ? Tristate.TRUE : Tristate.FALSE) : Tristate.DEFAULT;
      this.disableJEI = Bits.getFlag(flags, 524288) ? (Bits.getFlag(flags, 1048576) ? Tristate.TRUE : Tristate.FALSE) : Tristate.DEFAULT;
      this.hideLockIcon = Bits.getFlag(flags, 262144);
      this.progressionMode = (ProgressionMode)ProgressionMode.NAME_MAP.read(buffer);
      this.maxCompletableDeps = buffer.readVarInt();
      this.repeatCooldown = this.canRepeat.isTrue() ? buffer.readInt() : 0;
   }

   @Override
   public int getRelativeProgressFromChildren(TeamData data) {
      if (data.getCompletionCount(this) > 0) {
         return 100;
      }

      if (this.tasks.isEmpty()) {
         return data.areDependenciesComplete(this) ? 100 : 0;
      }

      int progress = 0;

      for (Task task : this.tasks) {
         progress += data.getRelativeProgress(task);
      }

      return progress > 0 && !data.areDependenciesComplete(this) ? 0 : getRelativeProgressFromChildren(progress, this.tasks.size());
   }

   @Override
   public void onStarted(QuestProgressEventData<?> data) {
      data.setStarted(this.id);
      ((EventActor)ObjectStartedEvent.QUEST.invoker()).act(new ObjectStartedEvent.QuestEvent(data.withObject(this)));
      if (!data.getTeamData().isStarted(this.chapter)) {
         this.chapter.onStarted(data.withObject(this.chapter));
      }
   }

   @Override
   public void onCompleted(QuestProgressEventData<?> data) {
      if (!data.getTeamData().isCompleted(this)) {
         data.setCompleted(this.id);
         ((EventActor)ObjectCompletedEvent.QUEST.invoker()).act(new ObjectCompletedEvent.QuestEvent(data.withObject(this)));
         if (!this.disableToast) {
            data.notifyPlayers(this.id);
         }

         if (!data.getTeamData().isCompleted(this.chapter) && this.chapter.isCompletedRaw(data.getTeamData())) {
            this.chapter.onCompleted(data.withObject(this.chapter));
         }

         data.getTeamData().checkAutoCompletion(this);
         this.checkForDependantCompletion(data.getTeamData());
      }
   }

   private void checkForDependantCompletion(TeamData data) {
      this.getDependants().forEach(questObject -> {
         if (questObject instanceof Quest quest) {
            if (quest.getProgressionMode() == ProgressionMode.FLEXIBLE && quest.checkDependencies(data::isCompleted)) {
               quest.tasks.forEach(task -> {
                  if (data.getProgress(task.id) >= task.getMaxProgress()) {
                     data.markTaskCompleted(task);
                  }
               });
            }

            data.checkAutoCompletion(quest);
         }
      });
   }

   public ProgressionMode getProgressionMode() {
      return this.progressionMode == ProgressionMode.DEFAULT ? this.chapter.getProgressionMode() : this.progressionMode;
   }

   @Override
   public void forceProgress(TeamData teamData, ProgressChange progressChange) {
      super.forceProgress(teamData, progressChange);

      for (Reward r : this.rewards) {
         r.forceProgress(teamData, progressChange);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Component getAltTitle() {
      return (Component)(!this.tasks.isEmpty() ? this.tasks.getFirst().getTitle() : Component.translatable("ftbquests.unnamed"));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Icon getAltIcon() {
      List<Icon> list = new ArrayList<>();

      for (Task task : this.tasks) {
         list.add(task.getIcon());
      }

      return IconAnimation.fromList(list, false);
   }

   @Override
   public void deleteSelf() {
      super.deleteSelf();
      this.chapter.removeQuest(this);
      List<QuestLink> linksToDel = new ArrayList<>();
      this.getQuestFile().forAllQuestLinks(l -> {
         if (l.linksTo(this)) {
            linksToDel.add(l);
         }
      });
      linksToDel.forEach(l -> this.getQuestFile().deleteObject(l.id));
   }

   @Override
   public void deleteChildren() {
      for (Task task : this.tasks) {
         task.deleteChildren();
         task.invalid = true;
      }

      for (Reward reward : this.rewards) {
         reward.deleteChildren();
         reward.invalid = true;
      }

      this.tasks.clear();
      this.rewards.clear();
   }

   @Override
   public void onCreated() {
      this.chapter.addQuest(this);
      if (!this.tasks.isEmpty()) {
         List<Task> l = new ArrayList<>(this.tasks);
         this.tasks.clear();

         for (Task task : l) {
            task.onCreated();
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("subtitle", this.getRawSubtitle(), this::setRawSubtitle, "");
      StringConfig descType = new StringConfig();
      config.add("description", new ListConfig<String, StringConfig>(descType) {
         public void onClicked(Widget clicked, MouseButton button, ConfigCallback callback) {
            new MultilineTextEditorScreen(Component.translatable("ftbquests.gui.edit_description"), this, callback).openGui();
         }
      }, this.getRawDescription(), this::setRawDescription, Collections.emptyList());
      ConfigGroup appearance = config.getOrCreateSubgroup("appearance");
      appearance.addEnum("shape", this.shape.isEmpty() ? "default" : this.shape, v -> this.shape = v.equals("default") ? "" : v, QuestShape.idMapWithDefault);
      appearance.addDouble("size", this.size, v -> this.size = v, 0.0, 0.0, 8.0);
      appearance.addDouble("x", this.x, v -> this.x = v, 0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
      appearance.addDouble("y", this.y, v -> this.y = v, 0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
      appearance.addInt("min_width", this.minWidth, v -> this.minWidth = v, 0, 0, 3000);
      appearance.addDouble("icon_scale", this.iconScale, v -> this.iconScale = v, 1.0, 0.1, 2.0);
      ConfigGroup visibility = config.getOrCreateSubgroup("visibility");
      visibility.addTristate("hide_until_deps_complete", this.hideUntilDepsComplete, v -> this.hideUntilDepsComplete = v);
      visibility.addTristate("hide_until_deps_visible", this.hideUntilDepsVisible, v -> this.hideUntilDepsVisible = v);
      visibility.addBool("invisible", this.invisibleUntilCompleted, v -> this.invisibleUntilCompleted = v, false);
      visibility.addInt("invisible_until_tasks", this.invisibleUntilTasks, v -> this.invisibleUntilTasks = v, 0, 0, Integer.MAX_VALUE)
         .setCanEdit(this.invisibleUntilCompleted);
      visibility.addTristate("hide_details_until_startable", this.hideDetailsUntilStartable, v -> this.hideDetailsUntilStartable = v);
      visibility.addTristate("hide_text_until_complete", this.hideTextUntilComplete, v -> this.hideTextUntilComplete = v);
      visibility.addBool("hide_lock_icon", this.hideLockIcon, v -> this.hideLockIcon = v, false);
      Predicate<QuestObjectBase> depTypes = object -> object != this.chapter.file && object != this.chapter && object instanceof QuestObject;
      this.removeInvalidDependencies();
      ConfigGroup deps = config.getOrCreateSubgroup("dependencies");
      deps.addList("dependencies", this.dependencies, new ConfigQuestObject(depTypes), null).setNameKey("ftbquests.dependencies");
      deps.addEnum("dependency_requirement", this.dependencyRequirement, v -> this.dependencyRequirement = v, DependencyRequirement.NAME_MAP);
      deps.addInt("min_required_dependencies", this.minRequiredDependencies, v -> this.minRequiredDependencies = v, 0, 0, Integer.MAX_VALUE);
      deps.addTristate("hide_dependency_lines", this.hideDependencyLines, v -> this.hideDependencyLines = v);
      deps.addBool("hide_dependent_lines", this.hideDependentLines, v -> this.hideDependentLines = v, false);
      deps.addInt("max_completable_dependents", this.maxCompletableDeps, v -> this.maxCompletableDeps = v, 0, 0, Integer.MAX_VALUE);
      ConfigGroup misc = config.getOrCreateSubgroup("misc");
      misc.addString("guide_page", this.guidePage, v -> this.guidePage = v, "");
      misc.addEnum("disable_jei", this.disableJEI, v -> this.disableJEI = v, Tristate.NAME_MAP);
      misc.addTristate("can_repeat", this.canRepeat, v -> this.canRepeat = v);
      misc.addInt("repeat_cooldown", this.repeatCooldown, v -> this.repeatCooldown = v, 0, 0, Integer.MAX_VALUE);
      misc.addBool("optional", this.optional, v -> this.optional = v, false);
      misc.addBool("ignore_reward_blocking", this.ignoreRewardBlocking, v -> this.ignoreRewardBlocking = v, false);
      misc.addEnum("progression_mode", this.progressionMode, v -> this.progressionMode = v, ProgressionMode.NAME_MAP);
      misc.addTristate("require_sequential_tasks", this.requireSequentialTasks, v -> this.requireSequentialTasks = v);
   }

   public boolean shouldHideDependencyLines() {
      return this.hideDependencyLines.get(this.chapter.defaultHideDependencyLines);
   }

   public boolean shouldHideLockIcon() {
      return this.hideLockIcon;
   }

   @Override
   public long getMovableID() {
      return this.id;
   }

   @Override
   public Chapter getChapter() {
      return this.chapter;
   }

   @Override
   public double getX() {
      return this.x;
   }

   @Override
   public double getY() {
      return this.y;
   }

   @Override
   public double getWidth() {
      return this.getSize();
   }

   @Override
   public double getHeight() {
      return this.getSize();
   }

   @Override
   public String getShape() {
      return this.shape.isEmpty() ? this.chapter.getDefaultQuestShape() : this.shape;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void initiateMoveClientSide(Chapter to, double x, double y) {
      NetworkManager.sendToServer(new MoveMovableMessage(this.id, to.id, x, y));
   }

   @Override
   public boolean isVisible(TeamData data) {
      if (!this.invisibleUntilCompleted
         || data.isCompleted(this)
         || this.invisibleUntilTasks != 0 && this.tasks.stream().filter(data::isCompleted).limit(this.invisibleUntilTasks).count() >= this.invisibleUntilTasks) {
         if (data.getFile().isHideExcludedQuests() && data.isExcludedByOtherQuestline(this)) {
            return false;
         } else if (this.dependencies.isEmpty()) {
            return true;
         } else if (this.hideUntilDepsComplete.get(this.chapter.hideQuestUntilDepsComplete())) {
            return data.areDependenciesComplete(this);
         } else {
            return this.hideUntilDepsVisible.get(this.chapter.isHideQuestUntilDepsVisible())
               ? data.areDependenciesVisible(this)
               : this.streamDependencies().anyMatch(object -> object.isVisible(data));
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean isSearchable(TeamData data) {
      return !this.chapter.isAlwaysInvisible() && super.isSearchable(data);
   }

   @Override
   public void clearCachedData() {
      super.clearCachedData();
      this.cachedSubtitle = null;
      this.cachedDescription = null;

      for (Task task : this.tasks) {
         task.clearCachedData();
      }

      for (Reward reward : this.rewards) {
         reward.clearCachedData();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public Component getSubtitle() {
      if (this.cachedSubtitle == null) {
         this.cachedSubtitle = TextUtils.parseRawText(this.getRawSubtitle(), this.holderLookup());
      }

      return this.cachedSubtitle;
   }

   @OnlyIn(Dist.CLIENT)
   public List<Component> getDescription() {
      if (this.cachedDescription == null) {
         this.cachedDescription = this.getRawDescription().stream().map(str -> TextUtils.parseRawText(str, this.holderLookup())).toList();
      }

      return this.cachedDescription;
   }

   public boolean hasDependency(QuestObject object) {
      if (object.invalid) {
         return false;
      }

      for (QuestObject dependency : this.dependencies) {
         if (dependency == object) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected boolean validateEditedConfig() {
      try {
         this.verifyDependenciesInternal(this.id, 0);
         return true;
      } catch (DependencyDepthException | DependencyLoopException ex) {
         this.clearDependencies();
         if (!this.getQuestFile().isServerSide()) {
            QuestScreen.displayError(Component.translatable("ftbquests.gui.looping_dependencies"));
         }

         return false;
      }
   }

   public boolean verifyDependencies(boolean autofix) {
      try {
         this.verifyDependenciesInternal(this.id, 0);
         return true;
      } catch (DependencyDepthException ex) {
         if (autofix) {
            FTBQuests.LOGGER.error("Too deep dependencies found in {} (referenced in {})! Deleting all dependencies...", this, ex.object);
            this.clearDependencies();
            this.chapter.file.markDirty();
         } else {
            FTBQuests.LOGGER.error("Too deep dependencies found in {} (referenced in {})!", this, ex.object);
         }

         return false;
      } catch (DependencyLoopException ex) {
         if (autofix) {
            FTBQuests.LOGGER.error("Looping dependencies found in {} (referenced in {})! Deleting all dependencies...", this, ex.object);
            this.clearDependencies();
            this.chapter.file.markDirty();
         } else {
            FTBQuests.LOGGER.error("Looping dependencies found in {} (referenced in {})!", this, ex.object);
         }

         return false;
      }
   }

   @Override
   protected void verifyDependenciesInternal(long original, int depth) {
      this._verify(original, new LongOpenHashSet(), 0);
   }

   private void _verify(long original, LongSet visited, int depth) {
      if (visited.add(this.id)) {
         if (depth >= 1000) {
            throw new DependencyDepthException(this);
         }

         for (QuestObject dependency : this.dependencies) {
            if (dependency.id == original) {
               throw new DependencyLoopException(this);
            }

            if (dependency instanceof Quest q) {
               q._verify(original, visited, depth + 1);
            } else if (dependency instanceof Chapter c) {
               c.getQuests().forEach(qx -> qx._verify(original, visited, depth + 1));
            }
         }
      }
   }

   @Override
   public Set<RecipeModHelper.Components> componentsToRefresh() {
      return EnumSet.of(RecipeModHelper.Components.QUESTS);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void editedFromGUI() {
      QuestScreen gui = (QuestScreen)ClientUtils.getCurrentGuiAs(QuestScreen.class);
      if (gui != null) {
         gui.refreshChapterPanel();
         gui.refreshQuestPanel();
         gui.refreshViewQuestPanel();
      }
   }

   @Override
   public void onMoved(double newX, double newY, long newChapterId) {
      this.x = newX;
      this.y = newY;
      if (newChapterId != this.chapter.id) {
         Chapter newChapter = this.getQuestFile().getChapter(newChapterId);
         if (newChapter != null) {
            this.chapter.removeQuest(this);
            newChapter.addQuest(this);
            this.chapter = newChapter;
         }
      }
   }

   @Override
   public void copyToClipboard() {
      FTBQuestsClient.copyToClipboard(this);
   }

   public Collection<QuestObject> getDependants() {
      return this.dependantIDs.stream().map(id -> this.getQuestFile().get(id)).filter(q -> q != null && !q.invalid).toList();
   }

   public boolean resetProgressIfRepeatable(TeamData data, UUID player) {
      if (this.canBeRepeated() && this.rewards.stream().allMatch(r -> data.isRewardClaimed(player, r))) {
         this.forceProgress(data, new ProgressChange(this, player));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public Collection<? extends QuestObject> getChildren() {
      return this.tasks;
   }

   @Override
   public boolean isCompletedRaw(TeamData data) {
      return data.canStartTasks(this) && super.isCompletedRaw(data);
   }

   @Override
   public boolean hasUnclaimedRewardsRaw(TeamData teamData, UUID player) {
      if (teamData.isCompleted(this)) {
         for (Reward reward : this.rewards) {
            if (!teamData.isRewardBlocked(reward) && teamData.getClaimType(player, reward).canClaim()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean ignoreRewardBlocking() {
      return this.ignoreRewardBlocking;
   }

   public void writeTasks(CompoundTag tag, Provider provider) {
      ListTag t = new ListTag();

      for (Task task : this.tasks) {
         TaskType type = task.getType();
         SNBTCompoundTag nbt3 = new SNBTCompoundTag();
         nbt3.putString("id", task.getCodeString());
         nbt3.putString("type", type.getTypeForNBT());
         task.writeData(nbt3, provider);
         t.add(nbt3);
      }

      tag.put("tasks", t);
   }

   public void writeRewards(CompoundTag tag, Provider provider) {
      ListTag r = new ListTag();

      for (Reward reward : this.rewards) {
         RewardType type = reward.getType();
         SNBTCompoundTag nbt3 = new SNBTCompoundTag();
         nbt3.putString("id", reward.getCodeString());
         nbt3.putString("type", type.getTypeForNBT());
         reward.writeData(nbt3, provider);
         r.add(nbt3);
      }

      tag.put("rewards", r);
   }

   public boolean hasDependencies() {
      return !this.dependencies.isEmpty();
   }

   public Stream<QuestObject> streamDependencies() {
      return this.dependencies.stream();
   }

   public void addDependency(QuestObject object) {
      this.dependencies.add(object);
      if (object instanceof Quest q) {
         q.addDependant(this.id);
      }
   }

   public void removeDependency(QuestObject object) {
      this.dependencies.remove(object);
      if (object instanceof Quest q) {
         q.removeDependant(this.id);
      }
   }

   public void removeInvalidDependencies() {
      Iterator<QuestObject> iter = this.dependencies.iterator();

      while (iter.hasNext()) {
         QuestObject qo = iter.next();
         if (qo == null || qo.invalid || qo == this) {
            iter.remove();
            if (qo instanceof Quest q) {
               q.removeDependant(this.id);
            }
         }
      }
   }

   public void clearDependencies() {
      this.dependencies.forEach(qo -> {
         if (qo instanceof Quest q) {
            q.removeDependant(this.id);
         }
      });
      this.dependencies.clear();
   }

   private void addDependant(long id) {
      this.dependantIDs.add(id);
   }

   private void removeDependant(long id) {
      this.dependantIDs.remove(id);
   }

   public boolean allTasksCompleted(TeamData teamData) {
      return this.tasks.stream().allMatch(task -> teamData.getProgress(task) >= task.getMaxProgress());
   }

   public boolean hideDetailsUntilStartable() {
      return this.hideDetailsUntilStartable.get(this.chapter.hideQuestDetailsUntilStartable());
   }

   public void addTask(Task task) {
      this.tasks.add(task);
   }

   public void removeTask(Task task) {
      this.tasks.remove(task);
   }

   public void addReward(Reward reward) {
      this.rewards.add(reward);
   }

   public void removeReward(Reward reward) {
      this.rewards.remove(reward);
   }

   public void setTaskList(List<Task> tasks) {
      this.tasks.clear();
      this.tasks.addAll(tasks);
   }

   public void setRewardList(List<Reward> rewards) {
      this.rewards.clear();
      this.rewards.addAll(rewards);
   }

   public int getRepeatCooldown() {
      return this.repeatCooldown;
   }

   private boolean checkDependencies(Quest.DependencyChecker checker) {
      if (this.minRequiredDependencies > 0) {
         return this.streamDependencies().filter(checker::check).limit(this.minRequiredDependencies).count() == this.minRequiredDependencies;
      } else {
         return this.dependencyRequirement.needOnlyOne()
            ? this.streamDependencies().anyMatch(checker::check)
            : this.streamDependencies().allMatch(checker::check);
      }
   }

   public boolean areDependenciesComplete(TeamData teamData) {
      return this.checkDependencies(dep -> this.dependencyRequirement.needCompletion() ? teamData.isCompleted(dep) : teamData.isStarted(dep));
   }

   public boolean areDependenciesVisible(TeamData teamData) {
      return this.checkDependencies(dep -> dep.isVisible(teamData));
   }

   public List<Pair<Integer, Integer>> buildDescriptionIndex() {
      List<Pair<Integer, Integer>> index = new ArrayList<>();
      List<String> rawDescription = this.getRawDescription();
      int l1 = 0;

      for (int l2 = l1; l2 < rawDescription.size(); l2++) {
         if (rawDescription.get(l2).equals("{@pagebreak}")) {
            index.add(Pair.of(l1, l2 - 1));
            l1 = l2 + 1;
         }
      }

      if (l1 < rawDescription.size()) {
         index.add(Pair.of(l1, rawDescription.size() - 1));
      }

      return index;
   }

   @Override
   public Quest getRelatedQuest() {
      return this;
   }

   @Override
   public boolean isQuestObjectExcluded(TeamData teamData) {
      if (this.dependencies.isEmpty()) {
         return false;
      }

      int nValidPaths = 0;
      int nQuestDeps = 0;

      for (QuestObject qo : this.dependencies) {
         if (qo instanceof Quest dependency) {
            nQuestDeps++;
            boolean blockedByMaxCompletable = false;
            if (!dependency.dependantIDs.isEmpty() && dependency.maxCompletableDeps > 0) {
               long completed = dependency.getDependants().stream().filter(teamData::isCompleted).count();
               if (completed >= dependency.maxCompletableDeps && !teamData.isCompleted(this)) {
                  blockedByMaxCompletable = true;
               }
            }

            if (!blockedByMaxCompletable) {
               if (teamData.isCompleted(dependency)) {
                  nValidPaths++;
               } else if (!teamData.isExcludedByOtherQuestline(dependency)) {
                  nValidPaths++;
               }
            }
         }
      }

      if (nQuestDeps == 0) {
         return false;
      }

      int requiredPaths;
      if (this.minRequiredDependencies > 0) {
         requiredPaths = this.minRequiredDependencies;
      } else if (this.dependencyRequirement.needOnlyOne()) {
         requiredPaths = 1;
      } else {
         requiredPaths = nQuestDeps;
      }

      return nValidPaths < requiredPaths;
   }

   public boolean isExclusiveQuest() {
      return this.dependencies.stream().anyMatch(qo -> qo instanceof Quest q && q.maxCompletableDeps > 0);
   }

   private <T> void moveItem(List<T> list, T item, int direction) {
      int currentIndex = list.indexOf(item);
      int targetIndex = currentIndex + direction;
      if (currentIndex >= 0 && targetIndex >= 0 && targetIndex < list.size()) {
         T targetItem = list.get(targetIndex);
         list.set(targetIndex, item);
         list.set(currentIndex, targetItem);
      }

      this.getQuestFile().markDirty();
   }

   public void moveTaskLeft(Task task) {
      this.moveItem(this.tasks, task, -1);
   }

   public void moveTaskRight(Task task) {
      this.moveItem(this.tasks, task, 1);
   }

   public void moveRewardLeft(Reward reward) {
      this.moveItem(this.rewards, reward, -1);
   }

   public void moveRewardRight(Reward reward) {
      this.moveItem(this.rewards, reward, 1);
   }

   @FunctionalInterface
   private interface DependencyChecker {
      default boolean check(QuestObject questObject) {
         return !questObject.invalid && this.check0(questObject);
      }

      boolean check0(QuestObject var1);
   }
}
