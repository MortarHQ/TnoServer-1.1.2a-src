package org.crypticdev.stextras.storage.quest;

import java.time.Instant;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.net.STExtrasQuestSync;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.quest.schedule.QuestSchedule;

public class STExtrasQuestStorage {
   private final EnumMap<QuestCategory, Set<ResourceLocation>> activeByCat = new EnumMap<>(QuestCategory.class);
   private final EnumMap<QuestCategory, Set<ResourceLocation>> completedByCat = new EnumMap<>(QuestCategory.class);
   private final EnumMap<QuestCategory, Map<ResourceLocation, Integer>> progressByCat = new EnumMap<>(QuestCategory.class);
   private final Map<ResourceLocation, Long> cooldownUntil = new HashMap<>();
   private final Map<ResourceLocation, Set<String>> countedItems = new HashMap<>();
   private int dailyRerollSalt;
   private int weeklyRerollSalt;
   private final Set<ResourceLocation> startedRaceMessages = new HashSet<>();
   private final Set<ResourceLocation> completedRaces = new HashSet<>();
   private boolean dirty;
   private long lastDailyApplied;
   private long lastWeeklyApplied;
   private int lastDailyNonce;
   private int lastWeeklyNonce;

   public Map<ResourceLocation, Long> getCooldownsSnapshot() {
      return new HashMap<>(this.cooldownUntil);
   }

   public Set<ResourceLocation> getCompletedRaces() {
      return Collections.unmodifiableSet(this.completedRaces);
   }

   public boolean hasCompletedRace(ResourceLocation raceId) {
      return this.completedRaces.contains(raceId);
   }

   public boolean addCompletedRace(ResourceLocation raceId) {
      boolean ch = this.completedRaces.add(raceId);
      if (ch) {
         this.dirty = true;
      }

      return ch;
   }

   public boolean removeCompletedRace(ResourceLocation raceId) {
      boolean ch = this.completedRaces.remove(raceId);
      if (ch) {
         this.dirty = true;
      }

      return ch;
   }

   public Set<ResourceLocation> getStartedRaceMessages() {
      return Collections.unmodifiableSet(this.startedRaceMessages);
   }

   public boolean hasStartedRaceMessage(ResourceLocation raceId) {
      return this.startedRaceMessages.contains(raceId);
   }

   public boolean markStartedRaceMessage(ResourceLocation raceId) {
      boolean ch = this.startedRaceMessages.add(raceId);
      if (ch) {
         this.dirty = true;
      }

      return ch;
   }

   public boolean clearStartedRaceMessage(ResourceLocation raceId) {
      boolean ch = this.startedRaceMessages.remove(raceId);
      if (ch) {
         this.dirty = true;
      }

      return ch;
   }

   public STExtrasQuestStorage() {
      for (QuestCategory c : QuestCategory.values()) {
         this.activeByCat.put(c, new HashSet<>());
         this.completedByCat.put(c, new HashSet<>());
         this.progressByCat.put(c, new HashMap<>());
      }
   }

   public int getDailyRerollSalt() {
      return this.dailyRerollSalt;
   }

   public void setDailyRerollSalt(int dailyRerollSalt) {
      if (this.dailyRerollSalt != dailyRerollSalt) {
         this.dailyRerollSalt = dailyRerollSalt;
         this.dirty = true;
      }
   }

   public int getWeeklyRerollSalt() {
      return this.weeklyRerollSalt;
   }

   public void setWeeklyRerollSalt(int weeklyRerollSalt) {
      if (this.weeklyRerollSalt != weeklyRerollSalt) {
         this.weeklyRerollSalt = weeklyRerollSalt;
         this.dirty = true;
      }
   }

   public long getCooldownUntil(ResourceLocation id) {
      return this.cooldownUntil.getOrDefault(id, 0L);
   }

   public long cooldownRemaining(ResourceLocation id) {
      long now = Instant.now().getEpochSecond();
      long until = this.getCooldownUntil(id);
      return Math.max(0L, until - now);
   }

   public boolean isOnCooldown(ResourceLocation id) {
      return this.cooldownRemaining(id) > 0L;
   }

   public void setCooldownUntil(ResourceLocation id, long epochSeconds) {
      if (epochSeconds <= 0L) {
         if (this.cooldownUntil.remove(id) != null) {
            this.dirty = true;
         }
      } else {
         Long prev = this.cooldownUntil.put(id, epochSeconds);
         if (prev == null || !prev.equals(epochSeconds)) {
            this.dirty = true;
         }
      }
   }

   public void clearCooldown(ResourceLocation id) {
      if (this.cooldownUntil.remove(id) != null) {
         this.dirty = true;
      }
   }

   public Set<ResourceLocation> active(QuestCategory c) {
      return this.activeByCat.get(c);
   }

   public Set<ResourceLocation> completed(QuestCategory c) {
      return this.completedByCat.get(c);
   }

   public Map<ResourceLocation, Integer> progressMap(QuestCategory c) {
      return this.progressByCat.get(c);
   }

   public Set<ResourceLocation> activeAll() {
      Set<ResourceLocation> out = new HashSet<>();

      for (Set<ResourceLocation> s : this.activeByCat.values()) {
         out.addAll(s);
      }

      return out;
   }

   public Set<ResourceLocation> completedAll() {
      Set<ResourceLocation> out = new HashSet<>();

      for (Set<ResourceLocation> s : this.completedByCat.values()) {
         out.addAll(s);
      }

      return out;
   }

   public boolean isDirty() {
      return this.dirty;
   }

   public void clearDirty() {
      this.dirty = false;
   }

   public long getLastDailyApplied() {
      return this.lastDailyApplied;
   }

   public long getLastWeeklyApplied() {
      return this.lastWeeklyApplied;
   }

   public void setLastApplied(long dailyCode, long weeklyCode, int dailyNonce, int weeklyNonce) {
      if (this.lastDailyApplied != dailyCode
         || this.lastWeeklyApplied != weeklyCode
         || this.lastDailyNonce != dailyNonce
         || this.lastWeeklyNonce != weeklyNonce) {
         this.lastDailyApplied = dailyCode;
         this.lastWeeklyApplied = weeklyCode;
         this.lastDailyNonce = dailyNonce;
         this.lastWeeklyNonce = weeklyNonce;
         this.dirty = true;
      }
   }

   private QuestCategory categoryOf(ResourceLocation id) {
      QuestDefinition q = QuestRegistry.INSTANCE.byId(id);
      if (q != null) {
         return q.category;
      }

      for (QuestCategory c : QuestCategory.values()) {
         if (this.activeByCat.get(c).contains(id) || this.completedByCat.get(c).contains(id) || this.progressByCat.get(c).containsKey(id)) {
            return c;
         }
      }

      return QuestCategory.DAILY;
   }

   public boolean pruneUnavailableQuestIds() {
      Set<ResourceLocation> validQuestIds = new HashSet<>();

      for (QuestDefinition quest : QuestRegistry.INSTANCE.all()) {
         if (quest != null && quest.id != null) {
            validQuestIds.add(quest.id);
         }
      }

      boolean changed = false;

      for (QuestCategory c : QuestCategory.values()) {
         changed |= this.activeByCat.get(c).removeIf(id -> !validQuestIds.contains(id));
         changed |= this.completedByCat.get(c).removeIf(id -> !validQuestIds.contains(id));
         changed |= this.progressByCat.get(c).keySet().removeIf(id -> !validQuestIds.contains(id));
      }

      changed |= this.cooldownUntil.keySet().removeIf(id -> !validQuestIds.contains(id));
      changed |= this.countedItems.keySet().removeIf(id -> !validQuestIds.contains(id));
      if (changed) {
         this.dirty = true;
      }

      return changed;
   }

   public void clearCategory(QuestCategory c) {
      boolean ch = false;
      if (!this.activeByCat.get(c).isEmpty()) {
         this.activeByCat.get(c).clear();
         ch = true;
      }

      if (!this.completedByCat.get(c).isEmpty()) {
         this.completedByCat.get(c).clear();
         ch = true;
      }

      if (!this.progressByCat.get(c).isEmpty()) {
         this.progressByCat.get(c).clear();
         ch = true;
      }

      if (this.countedItems.entrySet().removeIf(entry -> this.categoryOf(entry.getKey()) == c)) {
         ch = true;
      }

      if (c == QuestCategory.RACE_PRESTIGE && !this.startedRaceMessages.isEmpty()) {
         this.startedRaceMessages.clear();
         ch = true;
      }

      if (ch) {
         this.dirty = true;
      }
   }

   public void addActive(ResourceLocation id) {
      QuestCategory c = this.categoryOf(id);
      if (c == QuestCategory.REPEATABLE) {
         if (this.isOnCooldown(id)) {
            return;
         }

         if (this.completedByCat.get(c).remove(id)) {
            this.dirty = true;
         }

         if (this.countedItems.remove(id) != null) {
            this.dirty = true;
         }
      }

      if (this.activeByCat.get(c).add(id)) {
         this.dirty = true;
      }
   }

   public void removeActive(ResourceLocation id) {
      QuestCategory c = this.categoryOf(id);
      boolean changed = false;
      changed |= this.activeByCat.get(c).remove(id);
      changed |= this.progressByCat.get(c).remove(id) != null;
      if (this.countedItems.remove(id) != null) {
         changed = true;
      }

      if (changed) {
         this.dirty = true;
      }
   }

   public boolean hasCounted(ResourceLocation questId, String uniqueKey) {
      return this.countedItems.containsKey(questId) && this.countedItems.get(questId).contains(uniqueKey);
   }

   public void addCounted(ResourceLocation questId, String uniqueKey) {
      if (uniqueKey != null) {
         this.countedItems.computeIfAbsent(questId, k -> new HashSet<>()).add(uniqueKey);
         this.dirty = true;
      }
   }

   public void resetQuest(ResourceLocation id) {
      QuestCategory c = this.categoryOf(id);
      boolean ch = this.activeByCat.get(c).remove(id);
      ch |= this.completedByCat.get(c).remove(id);
      ch |= this.progressByCat.get(c).remove(id) != null;
      if (this.countedItems.remove(id) != null) {
         ch = true;
      }

      if (ch) {
         this.dirty = true;
      }
   }

   public void markCompleted(ResourceLocation id) {
      QuestCategory c = this.categoryOf(id);
      if (c == QuestCategory.REPEATABLE) {
         QuestDefinition q = QuestRegistry.INSTANCE.byId(id);
         int cd = q != null ? q.cooldownSeconds : -1;
         if (cd <= 0) {
            this.setProgress(id, 0);
            this.clearCooldown(id);
            this.countedItems.remove(id);
            if (!this.activeByCat.get(c).contains(id)) {
               this.activeByCat.get(c).add(id);
            }

            this.dirty = true;
         } else {
            boolean changed = false;
            changed |= this.completedByCat.get(c).add(id);
            changed |= this.activeByCat.get(c).remove(id);
            changed |= this.progressByCat.get(c).remove(id) != null;
            if (this.countedItems.remove(id) != null) {
               changed = true;
            }

            long now = Instant.now().getEpochSecond();
            this.setCooldownUntil(id, now + cd);
            if (changed) {
               this.dirty = true;
            }
         }
      } else {
         boolean changed = false;
         changed |= this.completedByCat.get(c).add(id);
         changed |= this.activeByCat.get(c).remove(id);
         changed |= this.progressByCat.get(c).remove(id) != null;
         if (this.countedItems.remove(id) != null) {
            changed = true;
         }

         if (changed) {
            this.dirty = true;
         }
      }
   }

   public void setProgress(ResourceLocation id, int v) {
      QuestCategory c = this.categoryOf(id);
      int nv = Math.max(0, v);
      Integer before = this.progressByCat.get(c).put(id, nv);
      if (before == null || before != nv) {
         this.dirty = true;
      }
   }

   public int getProgress(ResourceLocation id) {
      QuestCategory c = this.categoryOf(id);
      return this.progressByCat.get(c).getOrDefault(id, 0);
   }

   public boolean addProgressAndMaybeComplete(ResourceLocation id, int delta, int req) {
      QuestCategory c = this.categoryOf(id);
      int v = this.getProgress(id) + Math.max(0, delta);
      if (v >= req) {
         this.markCompleted(id);
         return true;
      } else {
         this.setProgress(id, v);
         return false;
      }
   }

   public void copyFrom(STExtrasQuestStorage other) {
      if (other != null) {
         boolean changed = false;

         for (QuestCategory c : QuestCategory.values()) {
            Set<ResourceLocation> a = this.activeByCat.get(c);
            Set<ResourceLocation> b = this.completedByCat.get(c);
            Map<ResourceLocation, Integer> p = this.progressByCat.get(c);
            if (!a.equals(other.activeByCat.get(c))) {
               a.clear();
               a.addAll(other.activeByCat.get(c));
               changed = true;
            }

            if (!b.equals(other.completedByCat.get(c))) {
               b.clear();
               b.addAll(other.completedByCat.get(c));
               changed = true;
            }

            if (!p.equals(other.progressByCat.get(c))) {
               p.clear();
               p.putAll(other.progressByCat.get(c));
               changed = true;
            }
         }

         if (!this.cooldownUntil.equals(other.cooldownUntil)) {
            this.cooldownUntil.clear();
            this.cooldownUntil.putAll(other.cooldownUntil);
            changed = true;
         }

         if (!this.countedItems.equals(other.countedItems)) {
            this.countedItems.clear();

            for (Entry<ResourceLocation, Set<String>> entry : other.countedItems.entrySet()) {
               this.countedItems.put(entry.getKey(), new HashSet<>(entry.getValue()));
            }

            changed = true;
         }

         if (!this.completedRaces.equals(other.completedRaces)) {
            this.completedRaces.clear();
            this.completedRaces.addAll(other.completedRaces);
            changed = true;
         }

         if (!this.startedRaceMessages.equals(other.startedRaceMessages)) {
            this.startedRaceMessages.clear();
            this.startedRaceMessages.addAll(other.startedRaceMessages);
            changed = true;
         }

         if (this.lastDailyApplied != other.lastDailyApplied
            || this.lastWeeklyApplied != other.lastWeeklyApplied
            || this.lastDailyNonce != other.lastDailyNonce
            || this.lastWeeklyNonce != other.lastWeeklyNonce
            || this.dailyRerollSalt != other.dailyRerollSalt
            || this.weeklyRerollSalt != other.weeklyRerollSalt) {
            changed = true;
         }

         this.lastDailyApplied = other.lastDailyApplied;
         this.lastWeeklyApplied = other.lastWeeklyApplied;
         this.lastDailyNonce = other.lastDailyNonce;
         this.lastWeeklyNonce = other.lastWeeklyNonce;
         this.dailyRerollSalt = other.dailyRerollSalt;
         this.weeklyRerollSalt = other.weeklyRerollSalt;
         this.dirty = this.dirty || changed;
      }
   }

   public void writeNbt(CompoundTag root) {
      this.pruneUnavailableQuestIds();
      CompoundTag tag = root.getCompound("stextras:quests");
      tag.putLong("last_daily_code", this.lastDailyApplied);
      tag.putLong("last_weekly_code", this.lastWeeklyApplied);
      tag.putInt("last_daily_nonce", this.lastDailyNonce);
      tag.putInt("last_weekly_nonce", this.lastWeeklyNonce);
      tag.putInt("daily_reroll_salt", this.dailyRerollSalt);
      tag.putInt("weekly_reroll_salt", this.weeklyRerollSalt);
      CompoundTag cd = new CompoundTag();

      for (Entry<ResourceLocation, Long> e : this.cooldownUntil.entrySet()) {
         cd.putLong(e.getKey().toString(), e.getValue());
      }

      tag.put("cooldowns", cd);
      ListTag cr = new ListTag();

      for (ResourceLocation race : this.completedRaces) {
         cr.add(StringTag.valueOf(race.toString()));
      }

      tag.put("completed_races", cr);
      ListTag srm = new ListTag();

      for (ResourceLocation race : this.startedRaceMessages) {
         srm.add(StringTag.valueOf(race.toString()));
      }

      tag.put("started_race_messages", srm);

      for (QuestCategory c : QuestCategory.values()) {
         CompoundTag ct = new CompoundTag();
         ListTag act = new ListTag();

         for (ResourceLocation id : this.activeByCat.get(c)) {
            act.add(StringTag.valueOf(id.toString()));
         }

         ct.put("active", act);
         ListTag comp = new ListTag();

         for (ResourceLocation id : this.completedByCat.get(c)) {
            comp.add(StringTag.valueOf(id.toString()));
         }

         ct.put("completed", comp);
         CompoundTag prog = new CompoundTag();

         for (Entry<ResourceLocation, Integer> e : this.progressByCat.get(c).entrySet()) {
            prog.putInt(e.getKey().toString(), e.getValue());
         }

         ct.put("progress", prog);
         tag.put(c.name().toLowerCase(), ct);
      }

      CompoundTag countedTag = new CompoundTag();

      for (Entry<ResourceLocation, Set<String>> entry : this.countedItems.entrySet()) {
         ListTag list = new ListTag();

         for (String s : entry.getValue()) {
            list.add(StringTag.valueOf(s));
         }

         countedTag.put(entry.getKey().toString(), list);
      }

      tag.put("counted_items", countedTag);
      root.put("stextras:quests", tag);
      this.dirty = false;
   }

   public void readNbt(CompoundTag root) {
      if (root.contains("stextras:quests")) {
         CompoundTag tag = root.getCompound("stextras:quests");
         this.lastDailyApplied = tag.getLong("last_daily_code");
         this.lastWeeklyApplied = tag.getLong("last_weekly_code");
         this.lastDailyNonce = tag.getInt("last_daily_nonce");
         this.lastWeeklyNonce = tag.getInt("last_weekly_nonce");
         this.dailyRerollSalt = tag.getInt("daily_reroll_salt");
         this.weeklyRerollSalt = tag.getInt("weekly_reroll_salt");
         this.cooldownUntil.clear();
         if (tag.contains("cooldowns")) {
            CompoundTag cd = tag.getCompound("cooldowns");

            for (String k : cd.getAllKeys()) {
               this.cooldownUntil.put(ResourceLocation.parse(k), cd.getLong(k));
            }
         }

         this.completedRaces.clear();
         if (tag.contains("completed_races")) {
            ListTag cr = tag.getList("completed_races", 8);

            for (int i = 0; i < cr.size(); i++) {
               this.completedRaces.add(ResourceLocation.parse(cr.getString(i)));
            }
         }

         this.startedRaceMessages.clear();
         if (tag.contains("started_race_messages")) {
            ListTag srm = tag.getList("started_race_messages", 8);

            for (int i = 0; i < srm.size(); i++) {
               this.startedRaceMessages.add(ResourceLocation.parse(srm.getString(i)));
            }
         }

         for (QuestCategory c : QuestCategory.values()) {
            CompoundTag ct = tag.getCompound(c.name().toLowerCase());
            Set<ResourceLocation> a = this.activeByCat.get(c);
            Set<ResourceLocation> b = this.completedByCat.get(c);
            Map<ResourceLocation, Integer> p = this.progressByCat.get(c);
            a.clear();
            b.clear();
            p.clear();
            ListTag act = ct.getList("active", 8);

            for (int i = 0; i < act.size(); i++) {
               a.add(ResourceLocation.parse(act.getString(i)));
            }

            ListTag comp = ct.getList("completed", 8);

            for (int i = 0; i < comp.size(); i++) {
               b.add(ResourceLocation.parse(comp.getString(i)));
            }

            CompoundTag prog = ct.getCompound("progress");

            for (String k : prog.getAllKeys()) {
               p.put(ResourceLocation.parse(k), prog.getInt(k));
            }
         }

         this.countedItems.clear();
         if (tag.contains("counted_items")) {
            CompoundTag ct = tag.getCompound("counted_items");

            for (String key : ct.getAllKeys()) {
               Set<String> set = new HashSet<>();
               ListTag list = ct.getList(key, 8);

               for (int i = 0; i < list.size(); i++) {
                  set.add(list.getString(i));
               }

               this.countedItems.put(ResourceLocation.parse(key), set);
            }
         }

         this.dirty = false;
         this.pruneUnavailableQuestIds();
      }
   }

   public CompoundTag toNet() {
      this.pruneUnavailableQuestIds();
      CompoundTag t = new CompoundTag();
      t.putLong("last_daily_code", this.lastDailyApplied);
      t.putLong("last_weekly_code", this.lastWeeklyApplied);
      t.putInt("last_daily_nonce", this.lastDailyNonce);
      t.putInt("last_weekly_nonce", this.lastWeeklyNonce);
      t.putInt("daily_reroll_salt", this.dailyRerollSalt);
      t.putInt("weekly_reroll_salt", this.weeklyRerollSalt);
      CompoundTag cd = new CompoundTag();

      for (Entry<ResourceLocation, Long> e : this.cooldownUntil.entrySet()) {
         cd.putLong(e.getKey().toString(), e.getValue());
      }

      t.put("cooldowns", cd);
      ListTag cr = new ListTag();

      for (ResourceLocation race : this.completedRaces) {
         cr.add(StringTag.valueOf(race.toString()));
      }

      t.put("completed_races", cr);
      ListTag srm = new ListTag();

      for (ResourceLocation race : this.startedRaceMessages) {
         srm.add(StringTag.valueOf(race.toString()));
      }

      t.put("started_race_messages", srm);
      CompoundTag countedTag = new CompoundTag();

      for (Entry<ResourceLocation, Set<String>> entry : this.countedItems.entrySet()) {
         ListTag list = new ListTag();

         for (String s : entry.getValue()) {
            list.add(StringTag.valueOf(s));
         }

         countedTag.put(entry.getKey().toString(), list);
      }

      t.put("counted_items", countedTag);

      for (QuestCategory c : QuestCategory.values()) {
         CompoundTag ct = new CompoundTag();
         ListTag act = new ListTag();

         for (ResourceLocation id : this.activeByCat.get(c)) {
            act.add(StringTag.valueOf(id.toString()));
         }

         ct.put("active", act);
         ListTag comp = new ListTag();

         for (ResourceLocation id : this.completedByCat.get(c)) {
            comp.add(StringTag.valueOf(id.toString()));
         }

         ct.put("completed", comp);
         CompoundTag prog = new CompoundTag();

         for (Entry<ResourceLocation, Integer> e : this.progressByCat.get(c).entrySet()) {
            prog.putInt(e.getKey().toString(), e.getValue());
         }

         ct.put("progress", prog);
         t.put(c.name().toLowerCase(), ct);
      }

      return t;
   }

   public void fromNet(CompoundTag t) {
      this.lastDailyApplied = t.getLong("last_daily_code");
      this.lastWeeklyApplied = t.getLong("last_weekly_code");
      this.lastDailyNonce = t.getInt("last_daily_nonce");
      this.lastWeeklyNonce = t.getInt("last_weekly_nonce");
      this.dailyRerollSalt = t.getInt("daily_reroll_salt");
      this.weeklyRerollSalt = t.getInt("weekly_reroll_salt");
      this.cooldownUntil.clear();
      if (t.contains("cooldowns")) {
         CompoundTag cd = t.getCompound("cooldowns");

         for (String k : cd.getAllKeys()) {
            this.cooldownUntil.put(ResourceLocation.parse(k), cd.getLong(k));
         }
      }

      this.completedRaces.clear();
      if (t.contains("completed_races")) {
         ListTag cr = t.getList("completed_races", 8);

         for (int i = 0; i < cr.size(); i++) {
            this.completedRaces.add(ResourceLocation.parse(cr.getString(i)));
         }
      }

      this.startedRaceMessages.clear();
      if (t.contains("started_race_messages")) {
         ListTag srm = t.getList("started_race_messages", 8);

         for (int i = 0; i < srm.size(); i++) {
            this.startedRaceMessages.add(ResourceLocation.parse(srm.getString(i)));
         }
      }

      this.countedItems.clear();
      if (t.contains("counted_items")) {
         CompoundTag ct = t.getCompound("counted_items");

         for (String key : ct.getAllKeys()) {
            Set<String> set = new HashSet<>();
            ListTag list = ct.getList(key, 8);

            for (int i = 0; i < list.size(); i++) {
               set.add(list.getString(i));
            }

            this.countedItems.put(ResourceLocation.parse(key), set);
         }
      }

      for (QuestCategory c : QuestCategory.values()) {
         CompoundTag ct = t.getCompound(c.name().toLowerCase());
         Set<ResourceLocation> a = this.activeByCat.get(c);
         Set<ResourceLocation> b = this.completedByCat.get(c);
         Map<ResourceLocation, Integer> p = this.progressByCat.get(c);
         a.clear();
         b.clear();
         p.clear();
         ListTag act = ct.getList("active", 8);

         for (int i = 0; i < act.size(); i++) {
            a.add(ResourceLocation.parse(act.getString(i)));
         }

         ListTag comp = ct.getList("completed", 8);

         for (int i = 0; i < comp.size(); i++) {
            b.add(ResourceLocation.parse(comp.getString(i)));
         }

         CompoundTag prog = ct.getCompound("progress");

         for (String k : prog.getAllKeys()) {
            p.put(ResourceLocation.parse(k), prog.getInt(k));
         }
      }
   }

   public boolean validateAgainst(QuestSchedule sched) {
      if (sched == null) {
         return false;
      }

      boolean changed = this.lastDailyApplied != sched.dailyCode
         || this.lastWeeklyApplied != sched.weeklyCode
         || this.lastDailyNonce != sched.dailyNonce
         || this.lastWeeklyNonce != sched.weeklyNonce;
      if (changed) {
         this.setLastApplied(sched.dailyCode, sched.weeklyCode, sched.dailyNonce, sched.weeklyNonce);
      }

      return changed;
   }

   public void syncAllTo(ServerPlayer player) {
      STExtrasQuestSync.sendTo(player, this.toNet());
   }
}
