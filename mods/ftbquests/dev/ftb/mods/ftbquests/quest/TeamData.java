package dev.ftb.mods.ftbquests.quest;

import com.mojang.util.UndashedUuid;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.snbt.SNBT;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.events.QuestProgressEventData;
import dev.ftb.mods.ftbquests.net.ClaimRewardResponseMessage;
import dev.ftb.mods.ftbquests.net.ObjectCompletedMessage;
import dev.ftb.mods.ftbquests.net.ObjectCompletedResetMessage;
import dev.ftb.mods.ftbquests.net.ObjectStartedMessage;
import dev.ftb.mods.ftbquests.net.ObjectStartedResetMessage;
import dev.ftb.mods.ftbquests.net.ResetRewardMessage;
import dev.ftb.mods.ftbquests.net.SyncEditingModeMessage;
import dev.ftb.mods.ftbquests.net.SyncLockMessage;
import dev.ftb.mods.ftbquests.net.SyncRewardBlockingMessage;
import dev.ftb.mods.ftbquests.net.TogglePinnedResponseMessage;
import dev.ftb.mods.ftbquests.net.UpdateTaskProgressMessage;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardAutoClaim;
import dev.ftb.mods.ftbquests.quest.reward.RewardClaimType;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.util.FTBQuestsInventoryListener;
import dev.ftb.mods.ftbquests.util.QuestKey;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2LongMap.Entry;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.function.ToBooleanBiFunction;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TeamData {
   public static final int VERSION = 1;
   public static final int AUTO_PIN_ID = 1;
   private static final byte BOOL_UNKNOWN = -1;
   private static final byte BOOL_FALSE = 0;
   private static final byte BOOL_TRUE = 1;
   private static final Comparator<it.unimi.dsi.fastutil.longs.Long2LongMap.Entry> LONG2LONG_COMPARATOR = (e1, e2) -> Long.compareUnsigned(
      e1.getLongValue(), e2.getLongValue()
   );
   private static final Comparator<Entry<QuestKey>> OBJECT2LONG_COMPARATOR = (e1, e2) -> Long.compareUnsigned(e1.getLongValue(), e2.getLongValue());
   public static final StreamCodec<FriendlyByteBuf, TeamData> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, TeamData>() {
      public TeamData decode(FriendlyByteBuf buf) {
         return (TeamData)Util.make(new TeamData(buf.readUUID(), ClientQuestFile.INSTANCE), d -> d.readNetData(buf));
      }

      public void encode(FriendlyByteBuf buf, TeamData data) {
         buf.writeUUID(data.getTeamId());
         data.writeNetData(buf);
      }
   };
   private final UUID teamId;
   private final BaseQuestFile file;
   private String name;
   private boolean shouldSave;
   private boolean locked;
   private boolean rewardsBlocked;
   private final Long2LongMap taskProgress;
   private final Object2LongMap<QuestKey> claimedRewards;
   private final Long2LongMap started;
   private final Long2LongMap completed;
   private final Long2IntMap completionCount;
   private final Long2LongMap questRepeatableTime;
   private final Object2ObjectMap<UUID, TeamData.PerPlayerData> perPlayerData;
   private final Long2ByteMap areDependenciesCompleteCache;
   private final Long2ByteMap areDependenciesVisibleCache;
   private final Object2ByteMap<QuestKey> unclaimedRewardsCache;
   private final Long2BooleanMap exclusionCache;

   public TeamData(UUID teamId, BaseQuestFile file) {
      this(teamId, file, "");
   }

   public TeamData(UUID teamId, BaseQuestFile file, String name) {
      this.teamId = teamId;
      this.file = file;
      this.name = name;
      this.shouldSave = false;
      this.taskProgress = new Long2LongOpenHashMap();
      this.taskProgress.defaultReturnValue(0L);
      this.claimedRewards = new Object2LongOpenHashMap();
      this.claimedRewards.defaultReturnValue(0L);
      this.started = new Long2LongOpenHashMap();
      this.started.defaultReturnValue(0L);
      this.completed = new Long2LongOpenHashMap();
      this.completionCount = new Long2IntOpenHashMap();
      this.completed.defaultReturnValue(0L);
      this.perPlayerData = new Object2ObjectOpenHashMap();
      this.areDependenciesCompleteCache = new Long2ByteOpenHashMap();
      this.areDependenciesVisibleCache = new Long2ByteOpenHashMap();
      this.unclaimedRewardsCache = new Object2ByteOpenHashMap();
      this.exclusionCache = new Long2BooleanOpenHashMap();
      this.questRepeatableTime = new Long2LongOpenHashMap();
   }

   public UUID getTeamId() {
      return this.teamId;
   }

   public BaseQuestFile getFile() {
      return this.file;
   }

   @NotNull
   public static TeamData get(Player player) {
      return FTBQuestsAPI.api().getQuestFile(player.getCommandSenderWorld().isClientSide()).getTeamData(player).orElseThrow();
   }

   public void markDirty() {
      this.shouldSave = true;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      if (!this.name.equals(name)) {
         this.name = name;
         this.markDirty();
      }
   }

   public void saveIfChanged() {
      if (this.shouldSave && this.file instanceof ServerQuestFile sqf) {
         Path path = sqf.server.getWorldPath(ServerQuestFile.FTBQUESTS_DATA);
         SNBT.write(path.resolve(this.teamId + ".snbt"), this.serializeNBT());
         this.shouldSave = false;
      }
   }

   @Override
   public String toString() {
      return this.name.isEmpty() ? this.teamId.toString() : this.name;
   }

   public long getProgress(long taskId) {
      return this.taskProgress.get(taskId);
   }

   public long getProgress(Task task) {
      return this.getProgress(task.id);
   }

   public Optional<Date> getStartedTime(long questId) {
      long when = this.started.get(questId);
      return when == 0L ? Optional.empty() : Optional.of(new Date(when));
   }

   public boolean setStarted(long questId, @Nullable Date time) {
      if (!this.locked) {
         if (time == null) {
            if (this.started.remove(questId) >= 0L) {
               this.clearCachedProgress();
               this.markDirty();
               if (this.file.isServerSide()) {
                  NetworkManager.sendToPlayers(this.getOnlineMembers(), new ObjectStartedResetMessage(this.teamId, questId));
               }

               return true;
            }
         } else if (this.started.put(questId, time.getTime()) == 0L) {
            this.clearCachedProgress();
            this.markDirty();
            if (this.file.isServerSide()) {
               NetworkManager.sendToPlayers(this.getOnlineMembers(), new ObjectStartedMessage(this.teamId, questId));
            }

            return true;
         }
      }

      return false;
   }

   public Optional<Date> getCompletedTime(long questId) {
      long when = this.completed.get(questId);
      return when == 0L ? Optional.empty() : Optional.of(new Date(when));
   }

   public boolean setCompleted(long id, @Nullable Date time) {
      if (this.locked) {
         return false;
      }

      if (time == null) {
         if (this.completed.remove(id) >= 0L) {
            this.clearCachedProgress();
            this.markDirty();
            if (this.file.isServerSide()) {
               NetworkManager.sendToPlayers(this.getOnlineMembers(), new ObjectCompletedResetMessage(this.teamId, id));
            }

            return true;
         }
      } else if (this.completed.put(id, time.getTime()) == 0L) {
         this.clearCachedProgress();
         this.markDirty();
         if (this.file.isServerSide()) {
            NetworkManager.sendToPlayers(this.getOnlineMembers(), new ObjectCompletedMessage(this.teamId, id));
         }

         return true;
      }

      return false;
   }

   public Optional<Date> getRewardClaimTime(UUID player, Reward reward) {
      QuestKey key = QuestKey.forReward(player, reward);
      long t = this.claimedRewards.getLong(key);
      return t == 0L ? Optional.empty() : Optional.of(new Date(t));
   }

   public boolean areRewardsBlocked() {
      return this.rewardsBlocked;
   }

   public boolean isRewardBlocked(Reward reward) {
      return this.areRewardsBlocked() && !reward.ignoreRewardBlocking() && !reward.getQuest().ignoreRewardBlocking();
   }

   public boolean setRewardsBlocked(boolean rewardsBlocked) {
      if (rewardsBlocked != this.rewardsBlocked) {
         this.rewardsBlocked = rewardsBlocked;
         this.clearCachedProgress();
         this.markDirty();
         if (this.file.isServerSide()) {
            NetworkManager.sendToPlayers(this.getOnlineMembers(), new SyncRewardBlockingMessage(this.teamId, rewardsBlocked));
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isRewardClaimed(UUID player, Reward reward) {
      return this.getRewardClaimTime(player, reward).isPresent();
   }

   public long getMilliSecondsUntilRepeatable(Quest quest) {
      if (quest.canBeRepeated() && quest.getRepeatCooldown() != 0) {
         long now = System.currentTimeMillis();
         long when = this.questRepeatableTime.getOrDefault(quest.getId(), 0L);
         return now >= when ? 0L : when - now;
      } else {
         return 0L;
      }
   }

   public boolean hasUnclaimedRewards(UUID player, QuestObject object) {
      QuestKey key = QuestKey.create(player, object.id);
      byte b = this.unclaimedRewardsCache.getOrDefault(key, (byte)-1);
      if (b == -1) {
         b = (byte)(object.hasUnclaimedRewardsRaw(this, player) ? 1 : 0);
         this.unclaimedRewardsCache.put(key, b);
      }

      return b == 1;
   }

   public boolean markRewardAsClaimed(UUID player, Reward reward, long date) {
      if (!this.locked && !this.isRewardBlocked(reward)) {
         QuestKey key = QuestKey.forReward(player, reward);
         if (!this.claimedRewards.containsKey(key)) {
            this.claimedRewards.put(key, date);
            this.clearCachedProgress();
            this.markDirty();
            if (this.file.isServerSide()) {
               NetworkManager.sendToPlayers(this.getOnlineMembers(), new ClaimRewardResponseMessage(this.teamId, player, reward.id));
            }

            if (reward.getQuest().resetProgressIfRepeatable(this, player)) {
               long questId = reward.getQuest().getId();
               this.completionCount.merge(questId, 1, Integer::sum);
               if (reward.getQuest().getRepeatCooldown() > 0) {
                  this.questRepeatableTime.put(questId, System.currentTimeMillis() + reward.getQuest().getRepeatCooldown() * 1000L);
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void deleteReward(Reward reward) {
      if (!this.locked && this.claimedRewards.object2LongEntrySet().removeIf(e -> ((QuestKey)e.getKey()).getId() == reward.id)) {
         this.clearCachedProgress();
         this.markDirty();
      }
   }

   public boolean resetReward(UUID player, Reward reward) {
      if (!this.locked && this.claimedRewards.removeLong(QuestKey.forReward(player, reward)) != 0L) {
         this.clearCachedProgress();
         this.markDirty();
         if (this.file.isServerSide()) {
            NetworkManager.sendToPlayers(this.getOnlineMembers(), new ResetRewardMessage(this.teamId, player, reward.id));
         }

         return true;
      } else {
         return false;
      }
   }

   public void clearCachedProgress() {
      this.areDependenciesCompleteCache.clear();
      this.areDependenciesVisibleCache.clear();
      this.unclaimedRewardsCache.clear();
      this.exclusionCache.clear();
   }

   public SNBTCompoundTag serializeNBT() {
      SNBTCompoundTag nbt = new SNBTCompoundTag();
      nbt.putInt("version", 1);
      nbt.putString("uuid", UndashedUuid.toString(this.teamId));
      nbt.putString("name", this.name);
      nbt.putBoolean("lock", this.locked);
      nbt.putBoolean("rewards_blocked", this.rewardsBlocked);
      this.writeLongLongMap(nbt, this.taskProgress, "task_progress");
      this.writeLongLongMap(nbt, this.started, "started");
      this.writeLongLongMap(nbt, this.completed, "completed");
      this.writeLongLongMap(nbt, this.questRepeatableTime, "repeatable");
      this.writeLongIntMap(nbt, this.completionCount, "completion_count");
      SNBTCompoundTag claimedRewardsNBT = new SNBTCompoundTag();

      for (Entry<QuestKey> entry : this.claimedRewards.object2LongEntrySet().stream().sorted(OBJECT2LONG_COMPARATOR).toList()) {
         claimedRewardsNBT.putLong(((QuestKey)entry.getKey()).toString(), entry.getLongValue());
      }

      nbt.put("claimed_rewards", claimedRewardsNBT);
      CompoundTag ppdTag = new CompoundTag();
      this.perPlayerData.forEach((id, ppd) -> {
         if (!ppd.hasDefaultValues()) {
            ppdTag.put(UndashedUuid.toString(id), ppd.writeNBT());
         }
      });
      nbt.put("player_data", ppdTag);
      return nbt;
   }

   public void deserializeNBT(SNBTCompoundTag nbt) {
      int fileVersion = nbt.getInt("version");
      if (fileVersion != 1) {
         this.markDirty();
      }

      this.name = nbt.getString("name");
      this.locked = nbt.getBoolean("lock");
      this.rewardsBlocked = nbt.getBoolean("rewards_blocked");
      this.readLongLongMap(nbt, this.taskProgress, "task_progress");
      this.readLongLongMap(nbt, this.started, "started");
      this.readLongLongMap(nbt, this.completed, "completed");
      this.readLongLongMap(nbt, this.questRepeatableTime, "repeatable");
      this.readLongIntMap(nbt, this.completionCount, "completion_count");
      this.claimedRewards.clear();
      CompoundTag claimedRewardsNBT = nbt.getCompound("claimed_rewards");

      for (String s : claimedRewardsNBT.getAllKeys()) {
         this.claimedRewards.put(QuestKey.fromString(s), claimedRewardsNBT.getLong(s));
      }

      this.perPlayerData.clear();
      CompoundTag ppdTag = nbt.getCompound("player_data");

      for (String key : ppdTag.getAllKeys()) {
         try {
            UUID id = UndashedUuid.fromString(key);
            this.perPlayerData.put(id, TeamData.PerPlayerData.fromNBT(ppdTag.getCompound(key), this.file));
         } catch (IllegalArgumentException e) {
            FTBQuests.LOGGER.error("ignoring invalid player ID {} while loading per-player data for team {}", key, this.teamId);
         }
      }
   }

   private void writeLongLongMap(CompoundTag tag, Long2LongMap map, String key) {
      SNBTCompoundTag subTag = new SNBTCompoundTag();

      for (it.unimi.dsi.fastutil.longs.Long2LongMap.Entry entry : map.long2LongEntrySet().stream().sorted(LONG2LONG_COMPARATOR).toList()) {
         subTag.putLong(QuestObjectBase.getCodeString(entry.getLongKey()), entry.getLongValue());
      }

      tag.put(key, subTag);
   }

   private void readLongLongMap(CompoundTag tag, Long2LongMap map, String key) {
      map.clear();
      CompoundTag subTag = tag.getCompound(key);

      for (String subKey : subTag.getAllKeys()) {
         map.put(this.file.getID(subKey), subTag.getLong(subKey));
      }
   }

   private void writeLongIntMap(CompoundTag tag, Long2IntMap map, String key) {
      SNBTCompoundTag subTag = new SNBTCompoundTag();

      for (it.unimi.dsi.fastutil.longs.Long2IntMap.Entry entry : map.long2IntEntrySet().stream().toList()) {
         subTag.putInt(QuestObjectBase.getCodeString(entry.getLongKey()), entry.getIntValue());
      }

      tag.put(key, subTag);
   }

   private void readLongIntMap(CompoundTag tag, Long2IntMap map, String key) {
      map.clear();
      CompoundTag subTag = tag.getCompound(key);

      for (String subKey : subTag.getAllKeys()) {
         map.put(this.file.getID(subKey), subTag.getInt(subKey));
      }
   }

   private void writeNetData(FriendlyByteBuf buffer) {
      buffer.writeUtf(this.name, 32767);
      buffer.writeVarInt(this.taskProgress.size());
      ObjectIterator now = this.taskProgress.long2LongEntrySet().iterator();

      while (now.hasNext()) {
         it.unimi.dsi.fastutil.longs.Long2LongMap.Entry entry = (it.unimi.dsi.fastutil.longs.Long2LongMap.Entry)now.next();
         buffer.writeLong(entry.getLongKey());
         buffer.writeVarLong(entry.getLongValue());
      }

      long nowx = System.currentTimeMillis();
      buffer.writeVarInt(this.started.size());
      ObjectIterator var4 = this.started.long2LongEntrySet().iterator();

      while (var4.hasNext()) {
         it.unimi.dsi.fastutil.longs.Long2LongMap.Entry entry = (it.unimi.dsi.fastutil.longs.Long2LongMap.Entry)var4.next();
         buffer.writeLong(entry.getLongKey());
         buffer.writeVarLong(nowx - entry.getLongValue());
      }

      buffer.writeVarInt(this.completed.size());
      var4 = this.completed.long2LongEntrySet().iterator();

      while (var4.hasNext()) {
         it.unimi.dsi.fastutil.longs.Long2LongMap.Entry entry = (it.unimi.dsi.fastutil.longs.Long2LongMap.Entry)var4.next();
         buffer.writeLong(entry.getLongKey());
         buffer.writeVarLong(nowx - entry.getLongValue());
      }

      buffer.writeVarInt(this.questRepeatableTime.size());
      var4 = this.questRepeatableTime.long2LongEntrySet().iterator();

      while (var4.hasNext()) {
         it.unimi.dsi.fastutil.longs.Long2LongMap.Entry entry = (it.unimi.dsi.fastutil.longs.Long2LongMap.Entry)var4.next();
         buffer.writeLong(entry.getLongKey());
         buffer.writeVarLong(nowx - entry.getLongValue());
      }

      buffer.writeVarInt(this.completionCount.size());
      var4 = this.completionCount.long2IntEntrySet().iterator();

      while (var4.hasNext()) {
         it.unimi.dsi.fastutil.longs.Long2IntMap.Entry entry = (it.unimi.dsi.fastutil.longs.Long2IntMap.Entry)var4.next();
         buffer.writeLong(entry.getLongKey());
         buffer.writeVarInt(entry.getIntValue());
      }

      buffer.writeBoolean(this.locked);
      buffer.writeBoolean(this.rewardsBlocked);
      buffer.writeVarInt(this.claimedRewards.size());
      var4 = this.claimedRewards.object2LongEntrySet().iterator();

      while (var4.hasNext()) {
         Entry<QuestKey> entry = (Entry<QuestKey>)var4.next();
         ((QuestKey)entry.getKey()).toNetwork(buffer);
         buffer.writeVarLong(nowx - entry.getLongValue());
      }

      buffer.writeVarInt(this.perPlayerData.size());
      this.perPlayerData.forEach((id, ppd) -> {
         buffer.writeUUID(id);
         ppd.writeNet(buffer);
      });
   }

   private void readNetData(FriendlyByteBuf buffer) {
      this.name = buffer.readUtf(32767);
      this.taskProgress.clear();
      int ts = buffer.readVarInt();

      for (int i = 0; i < ts; i++) {
         this.taskProgress.put(buffer.readLong(), buffer.readVarLong());
      }

      long now = System.currentTimeMillis();
      this.started.clear();
      int ss = buffer.readVarInt();

      for (int i = 0; i < ss; i++) {
         this.started.put(buffer.readLong(), now - buffer.readVarLong());
      }

      this.completed.clear();
      int cs = buffer.readVarInt();

      for (int i = 0; i < cs; i++) {
         this.completed.put(buffer.readLong(), now - buffer.readVarLong());
      }

      this.questRepeatableTime.clear();
      int qs = buffer.readVarInt();

      for (int i = 0; i < qs; i++) {
         this.questRepeatableTime.put(buffer.readLong(), now - buffer.readVarLong());
      }

      this.completionCount.clear();
      int ccs = buffer.readVarInt();

      for (int i = 0; i < ccs; i++) {
         this.completionCount.put(buffer.readLong(), buffer.readVarInt());
      }

      this.locked = buffer.readBoolean();
      this.rewardsBlocked = buffer.readBoolean();
      this.claimedRewards.clear();
      this.perPlayerData.clear();
      int claimedRewardCount = buffer.readVarInt();

      for (int i = 0; i < claimedRewardCount; i++) {
         QuestKey key = QuestKey.fromNetwork(buffer);
         this.claimedRewards.put(key, now - buffer.readVarLong());
      }

      int ppdCount = buffer.readVarInt();

      for (int i = 0; i < ppdCount; i++) {
         UUID id = buffer.readUUID();
         this.perPlayerData.put(id, TeamData.PerPlayerData.fromNet(buffer));
      }
   }

   public final int getRelativeProgress(QuestObject object) {
      if (this.isCompleted(object)) {
         return 100;
      } else {
         return !this.isStarted(object) ? 0 : object.getRelativeProgressFromChildren(this);
      }
   }

   public boolean isStarted(QuestObject object) {
      return this.started.containsKey(object.id);
   }

   public boolean isCompleted(QuestObject object) {
      return this.completed.containsKey(object.id);
   }

   public int getCompletionCount(Quest quest) {
      if (quest.canBeRepeated()) {
         return this.completionCount.getOrDefault(quest.getId(), 0);
      } else {
         return this.isCompleted(quest) ? 1 : 0;
      }
   }

   private boolean checkDepsCached(Quest quest, Long2ByteMap cache, ToBooleanBiFunction<Quest, TeamData> checker) {
      if (!quest.hasDependencies()) {
         return true;
      }

      byte res = cache.getOrDefault(quest.id, (byte)-1);
      if (res == -1) {
         res = (byte)(checker.applyAsBoolean(quest, this) ? 1 : 0);
         cache.put(quest.id, res);
      }

      return res == 1;
   }

   public boolean areDependenciesComplete(Quest quest) {
      return this.checkDepsCached(quest, this.areDependenciesCompleteCache, Quest::areDependenciesComplete);
   }

   public boolean areDependenciesVisible(Quest quest) {
      return this.checkDepsCached(quest, this.areDependenciesVisibleCache, Quest::areDependenciesVisible);
   }

   public boolean canStartTasks(Quest quest) {
      return (quest.getProgressionMode() == ProgressionMode.FLEXIBLE || this.areDependenciesComplete(quest))
         && !this.isExcludedByOtherQuestline(quest)
         && this.getMilliSecondsUntilRepeatable(quest) == 0L;
   }

   public void claimReward(ServerPlayer player, Reward reward, boolean notify, long when) {
      if (this.markRewardAsClaimed(player.getUUID(), reward, when)) {
         reward.claim(player, notify);
      }
   }

   public void claimReward(ServerPlayer player, Reward reward, boolean notify) {
      this.claimReward(player, reward, notify, System.currentTimeMillis());
   }

   public Collection<ServerPlayer> getOnlineMembers() {
      return FTBTeamsAPI.api().getManager().getTeamByID(this.teamId).<List<ServerPlayer>>map(Team::getOnlineMembers).orElse(List.of());
   }

   public void checkAutoCompletion(Quest quest) {
      if (!quest.getRewards().isEmpty() && this.isCompleted(quest)) {
         Collection<ServerPlayer> online = null;

         for (Reward reward : quest.getRewards()) {
            RewardAutoClaim auto = reward.getAutoClaimType();
            if (auto != RewardAutoClaim.DISABLED) {
               if (reward.isTeamReward()) {
                  if (ServerQuestFile.INSTANCE.getCurrentPlayer() != null) {
                     this.claimReward(ServerQuestFile.INSTANCE.getCurrentPlayer(), reward, auto == RewardAutoClaim.ENABLED);
                  }
               } else {
                  if (online == null) {
                     online = this.getOnlineMembers();
                     if (online.isEmpty()) {
                        return;
                     }
                  }

                  for (ServerPlayer player : online) {
                     this.claimReward(player, reward, auto == RewardAutoClaim.ENABLED);
                  }
               }
            }
         }
      }
   }

   public RewardClaimType getClaimType(UUID player, Reward reward) {
      if (this.isRewardClaimed(player, reward)) {
         return RewardClaimType.CLAIMED;
      } else {
         return this.isCompleted(reward.getQuest()) ? RewardClaimType.CAN_CLAIM : RewardClaimType.CANT_CLAIM;
      }
   }

   public void resetProgress(Task task) {
      if (this.taskProgress.remove(task.id) > 0L) {
         this.markDirty();
      }
   }

   public final void setProgress(Task task, long progress) {
      if (!this.locked) {
         long maxProgress = task.getMaxProgress();
         progress = Math.max(0L, Math.min(progress, maxProgress));
         long prevProgress = this.getProgress(task);
         if (prevProgress != progress || progress == 0L && this.isStarted(task)) {
            if (progress == 0L) {
               this.taskProgress.remove(task.id);
               this.started.remove(task.id);
               this.completed.remove(task.id);
            } else {
               this.taskProgress.put(task.id, progress);
            }

            this.clearCachedProgress();
            if (this.file.isServerSide()) {
               Date now = new Date();
               Collection<ServerPlayer> onlineMembers = this.getOnlineMembers();
               NetworkManager.sendToPlayers(this.getOnlineMembers(), new UpdateTaskProgressMessage(this.getTeamId(), task.id, progress));
               if (prevProgress == 0L) {
                  task.onStarted(new QuestProgressEventData<>(now, this, task, onlineMembers, Collections.emptyList()));
               }

               if (progress >= maxProgress && this.areDependenciesComplete(task.getQuest())) {
                  this.markTaskCompleted(task);
               }
            }

            this.markDirty();
         }
      }
   }

   public void markTaskCompleted(Task task) {
      Collection<ServerPlayer> onlineMembers = this.getOnlineMembers();
      Collection<ServerPlayer> notifiedPlayers;
      if (!task.getQuest().getChapter().isAlwaysInvisible() && QuestObjectBase.shouldSendNotifications()) {
         notifiedPlayers = onlineMembers;
      } else {
         notifiedPlayers = List.of();
      }

      task.onCompleted(new QuestProgressEventData<>(new Date(), this, task, onlineMembers, notifiedPlayers));

      for (ServerPlayer player : onlineMembers) {
         FTBQuestsInventoryListener.detect(player, ItemStack.EMPTY, task.id);
      }

      if (this.isCompleted(task.getQuest())) {
         this.perPlayerData.values().forEach(data -> data.pinnedQuests.remove(task.getQuest().id));
         this.markDirty();
         NetworkManager.sendToPlayers(this.getOnlineMembers(), new TogglePinnedResponseMessage(task.getQuest().id, false));
      }
   }

   public final void addProgress(Task task, long progress) {
      this.setProgress(task, this.getProgress(task) + progress);
   }

   public boolean isLocked() {
      return this.locked;
   }

   public boolean setLocked(boolean newLocked) {
      if (this.locked != newLocked) {
         this.locked = newLocked;
         this.clearCachedProgress();
         this.markDirty();
         if (this.file.isServerSide()) {
            NetworkManager.sendToPlayers(this.getOnlineMembers(), new SyncLockMessage(this.teamId, this.locked));
         }

         return true;
      } else {
         return false;
      }
   }

   public void mergeData(TeamData from) {
      from.taskProgress.forEach((id, data) -> this.taskProgress.mergeLong(id, data, Long::max));
      from.started.forEach((id, data) -> this.started.mergeLong(id, data, (oldVal, newVal) -> oldVal));
      from.completed.forEach((id, data) -> this.completed.mergeLong(id, data, (oldVal, newVal) -> oldVal));
      from.claimedRewards.forEach((id, data) -> this.claimedRewards.mergeLong(id, data, (oldVal, newVal) -> oldVal));
      from.perPlayerData.forEach((id, data) -> this.perPlayerData.merge(id, data, (oldVal, newVal) -> oldVal));
   }

   public void mergeClaimedRewards(TeamData from) {
      from.claimedRewards.forEach((questKey, data) -> {
         if (questKey.uuid().equals(this.teamId)) {
            this.claimedRewards.put(questKey, data);
         }
      });
   }

   public void copyData(TeamData from) {
      this.locked = from.locked;
      this.taskProgress.putAll(from.taskProgress);
      this.claimedRewards.putAll(from.claimedRewards);
      this.started.putAll(from.started);
      this.completed.putAll(from.completed);
      this.perPlayerData.putAll(from.perPlayerData);
      this.rewardsBlocked = from.rewardsBlocked;
   }

   private Optional<TeamData.PerPlayerData> getOrCreatePlayerData(Player player) {
      if (!this.perPlayerData.containsKey(player.getUUID()) && this.file.isPlayerOnTeam(player, this)) {
         this.perPlayerData.put(player.getUUID(), new TeamData.PerPlayerData());
      }

      return Optional.ofNullable((TeamData.PerPlayerData)this.perPlayerData.get(player.getUUID()));
   }

   public boolean getCanEdit(Player player) {
      return this.getOrCreatePlayerData(player).map(d -> d.canEdit).orElse(false);
   }

   public boolean setCanEdit(Player player, boolean newCanEdit) {
      return this.getOrCreatePlayerData(player).map(playerData -> {
         if (playerData.canEdit != newCanEdit) {
            playerData.canEdit = newCanEdit;
            this.clearCachedProgress();
            this.markDirty();
            if (this.file.isServerSide() && player instanceof ServerPlayer sp) {
               NetworkManager.sendToPlayer(sp, new SyncEditingModeMessage(this.teamId, newCanEdit));
            }

            return true;
         } else {
            return false;
         }
      }).orElse(false);
   }

   public boolean isQuestPinned(Player player, long id) {
      return this.getOrCreatePlayerData(player).map(playerData -> playerData.pinnedQuests.contains(id)).orElse(false);
   }

   public void setQuestPinned(Player player, long id, boolean pinned) {
      this.getOrCreatePlayerData(player).ifPresent(playerData -> {
         if (pinned ? playerData.pinnedQuests.add(id) : playerData.pinnedQuests.remove(id)) {
            this.markDirty();
         }
      });
   }

   public LongSet getPinnedQuestIds(Player player) {
      return this.getOrCreatePlayerData(player).map(playerData -> playerData.pinnedQuests).orElse(LongSet.of());
   }

   public boolean isExcludedByOtherQuestline(QuestObject qo) {
      if (qo instanceof Excludable e) {
         if (this.exclusionCache.containsKey(e.getId())) {
            return this.exclusionCache.get(e.getId());
         }

         boolean excluded = e.isQuestObjectExcluded(this);
         this.exclusionCache.put(e.getId(), excluded);
         return excluded;
      } else {
         return false;
      }
   }

   public Component getCannotStartReason(Quest quest) {
      long ms = this.getMilliSecondsUntilRepeatable(quest);
      return ms > 0L
         ? Component.translatable("ftbquests.quest.locked.cooldown", new Object[]{DurationFormatUtils.formatDuration(ms, "[d'd'H'h'm'm']s's'")})
         : Component.translatable(this.isExcludedByOtherQuestline(quest) ? "ftbquests.quest.locked.excluded" : "ftbquests.quest.locked");
   }

   public void clearRepeatCooldown(Quest q) {
      this.questRepeatableTime.remove(q.getId());
      this.markDirty();
   }

   private static class PerPlayerData {
      private boolean canEdit;
      private boolean autoPin;
      private final LongSet pinnedQuests;

      PerPlayerData() {
         this.canEdit = this.autoPin = false;
         this.pinnedQuests = new LongOpenHashSet();
      }

      PerPlayerData(boolean canEdit, boolean autoPin, LongSet pinnedQuests) {
         this.canEdit = canEdit;
         this.autoPin = autoPin;
         this.pinnedQuests = pinnedQuests;
      }

      public boolean hasDefaultValues() {
         return !this.canEdit && !this.autoPin && this.pinnedQuests.isEmpty();
      }

      public static TeamData.PerPlayerData fromNBT(CompoundTag nbt, BaseQuestFile file) {
         boolean canEdit = nbt.getBoolean("can_edit");
         boolean autoPin = nbt.getBoolean("auto_pin");
         LongSet pq = nbt.getList("pinned_quests", 8).stream().map(tag -> file.getID(tag.getAsString())).collect(Collectors.toCollection(LongOpenHashSet::new));
         return new TeamData.PerPlayerData(canEdit, autoPin, pq);
      }

      public static TeamData.PerPlayerData fromNet(FriendlyByteBuf buffer) {
         TeamData.PerPlayerData ppd = new TeamData.PerPlayerData();
         ppd.canEdit = buffer.readBoolean();
         ppd.autoPin = buffer.readBoolean();
         int pinnedCount = buffer.readVarInt();

         for (int i = 0; i < pinnedCount; i++) {
            ppd.pinnedQuests.add(buffer.readLong());
         }

         return ppd;
      }

      public CompoundTag writeNBT() {
         CompoundTag nbt = new CompoundTag();
         if (this.canEdit) {
            nbt.putBoolean("can_edit", true);
         }

         if (this.autoPin) {
            nbt.putBoolean("auto_pin", true);
         }

         if (!this.pinnedQuests.isEmpty()) {
            long[] pinnedQuestsArray = this.pinnedQuests.toLongArray();
            Arrays.sort(pinnedQuestsArray);
            ListTag pinnedQuestsNBT = new ListTag();

            for (long l : pinnedQuestsArray) {
               pinnedQuestsNBT.add(StringTag.valueOf(QuestObjectBase.getCodeString(l)));
            }

            nbt.put("pinned_quests", pinnedQuestsNBT);
         }

         return nbt;
      }

      public void writeNet(FriendlyByteBuf buffer) {
         buffer.writeBoolean(this.canEdit);
         buffer.writeBoolean(this.autoPin);
         buffer.writeVarInt(this.pinnedQuests.size());
         LongIterator var2 = this.pinnedQuests.iterator();

         while (var2.hasNext()) {
            long reward = (Long)var2.next();
            buffer.writeLong(reward);
         }
      }
   }
}
