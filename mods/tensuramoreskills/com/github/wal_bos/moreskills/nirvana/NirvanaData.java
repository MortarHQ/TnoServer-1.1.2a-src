package com.github.wal_bos.moreskills.nirvana;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public final class NirvanaData {
   public static final String ROOT = "MoreSkillsNirvana";
   public static final String LIVES = "Lives";
   public static final String KARMA = "Karma";
   public static final String UNLOCKED = "UnlockedModes";
   public static final String CURRENT_LIFE_ID = "CurrentLifeId";
   public static final String CURRENT_LIFE_STARTED = "CurrentLifeStarted";
   public static final String NO_KARMA_SKILLS = "NoKarmaSkills";
   public static final long MINIMUM_KARMA_LIFE_TICKS = 24000L;

   private NirvanaData() {
   }

   public static CompoundTag root(Player player) {
      CompoundTag persistent = player.getPersistentData();
      if (!persistent.contains("MoreSkillsNirvana", 10)) {
         persistent.put("MoreSkillsNirvana", new CompoundTag());
      }

      return persistent.getCompound("MoreSkillsNirvana");
   }

   public static void clearAll(Player player) {
      player.getPersistentData().remove("MoreSkillsNirvana");
   }

   public static UUID currentLifeId(Player player) {
      CompoundTag root = root(player);
      if (!root.hasUUID("CurrentLifeId")) {
         root.putUUID("CurrentLifeId", UUID.randomUUID());
      }

      return root.getUUID("CurrentLifeId");
   }

   public static void ensureCurrentLife(ServerPlayer player) {
      CompoundTag root = root(player);
      if (!root.hasUUID("CurrentLifeId")) {
         root.putUUID("CurrentLifeId", UUID.randomUUID());
      }

      if (!root.contains("CurrentLifeStarted", 4)) {
         root.putLong("CurrentLifeStarted", Math.max(0L, player.serverLevel().getGameTime() - Math.max(0, player.tickCount)));
      }
   }

   public static void beginNewLife(ServerPlayer player) {
      CompoundTag root = root(player);
      root.putUUID("CurrentLifeId", UUID.randomUUID());
      root.putLong("CurrentLifeStarted", player.serverLevel().getGameTime());
      root.put("NoKarmaSkills", new ListTag());
   }

   public static long currentLifeAgeTicks(ServerPlayer player) {
      CompoundTag root = root(player);
      return !root.contains("CurrentLifeStarted", 4)
         ? Math.max(0, player.tickCount)
         : Math.max(0L, player.serverLevel().getGameTime() - root.getLong("CurrentLifeStarted"));
   }

   public static boolean isDeathFarmLife(ServerPlayer player) {
      return currentLifeAgeTicks(player) < 24000L;
   }

   public static ListTag noKarmaSkills(Player player) {
      CompoundTag root = root(player);
      if (!root.contains("NoKarmaSkills", 9)) {
         root.put("NoKarmaSkills", new ListTag());
      }

      return root.getList("NoKarmaSkills", 8);
   }

   public static void markNoKarmaSkill(Player player, ManasSkill skill) {
      if (skill != null && skill.getRegistryName() != null) {
         String id = skill.getRegistryName().toString();
         ListTag list = noKarmaSkills(player);

         for (int i = 0; i < list.size(); i++) {
            if (list.getString(i).equals(id)) {
               return;
            }
         }

         list.add(StringTag.valueOf(id));
         root(player).put("NoKarmaSkills", list);
      }
   }

   public static void markCurrentSkillsNoKarma(ServerPlayer player) {
      for (ManasSkillInstance instance : SkillAPI.getSkillsFrom(player).getLearnedSkills()) {
         if (instance != null) {
            markNoKarmaSkill(player, instance.getSkill());
         }
      }
   }

   public static boolean isNoKarmaSkill(Player player, ManasSkill skill) {
      if (skill != null && skill.getRegistryName() != null) {
         String id = skill.getRegistryName().toString();
         ListTag list = noKarmaSkills(player);

         for (int i = 0; i < list.size(); i++) {
            if (list.getString(i).equals(id)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static long getKarma(Player player) {
      return root(player).getLong("Karma");
   }

   public static void setKarma(Player player, long karma) {
      root(player).putLong("Karma", Math.max(0L, karma));
   }

   public static boolean spendKarma(Player player, long amount) {
      long karma = getKarma(player);
      if (karma < amount) {
         return false;
      }

      setKarma(player, karma - amount);
      return true;
   }

   public static void addKarma(Player player, long amount) {
      setKarma(player, getKarma(player) + Math.max(0L, amount));
   }

   public static ListTag lives(Player player) {
      CompoundTag root = root(player);
      if (!root.contains("Lives", 9)) {
         root.put("Lives", new ListTag());
      }

      return root.getList("Lives", 10);
   }

   public static void addLife(Player player, CompoundTag life) {
      ListTag lives = lives(player);
      int max = (Integer)MoreSkillsConfigs.GRAND.nirvana.maxArchiveSize.get();

      while (lives.size() >= max) {
         lives.remove(0);
      }

      lives.add(life);
      root(player).put("Lives", lives);
   }

   public static boolean deleteLife(Player player, UUID id) {
      ListTag lives = lives(player);

      for (int i = 0; i < lives.size(); i++) {
         CompoundTag life = lives.getCompound(i);
         if (life.hasUUID("Id") && life.getUUID("Id").equals(id)) {
            lives.remove(i);
            root(player).put("Lives", lives);
            removeUnlockedFromLife(player, id);
            return true;
         }
      }

      return false;
   }

   private static void removeUnlockedFromLife(Player player, UUID id) {
      String oldPrefix = id + "|";
      String newPart = "|" + id + "|";
      ListTag old = unlocked(player);
      ListTag fresh = new ListTag();

      for (int i = 0; i < old.size(); i++) {
         String value = old.getString(i);
         if (!value.startsWith(oldPrefix) && !value.contains(newPart)) {
            fresh.add(StringTag.valueOf(value));
         }
      }

      root(player).put("UnlockedModes", fresh);
   }

   public static boolean renameLife(Player player, UUID id, String name) {
      String clean = name == null ? "" : name.strip();
      if (clean.length() > 32) {
         clean = clean.substring(0, 32);
      }

      ListTag lives = lives(player);

      for (int i = 0; i < lives.size(); i++) {
         CompoundTag life = lives.getCompound(i);
         if (life.hasUUID("Id") && life.getUUID("Id").equals(id)) {
            life.putString("Name", clean.isEmpty() ? "Reincarnation " + (i + 1) : clean);
            root(player).put("Lives", lives);
            return true;
         }
      }

      return false;
   }

   public static List<CompoundTag> sortedLives(Player player, NirvanaData.Sort sort) {
      List<CompoundTag> result = new ArrayList<>();
      ListTag lives = lives(player);

      for (int i = 0; i < lives.size(); i++) {
         result.add(lives.getCompound(i).copy());
      }

      switch (sort) {
         case OLDEST:
            result.sort(Comparator.comparingLong(tag -> tag.getLong("Created")));
            break;
         case MOST_KARMA:
            result.sort(Comparator.<CompoundTag>comparingLong(tag -> tag.getLong("KarmaGained")).reversed());
            break;
         case MOST_EP:
            result.sort(Comparator.<CompoundTag>comparingDouble(tag -> tag.getDouble("EP")).reversed());
            break;
         case NAME:
            result.sort(Comparator.comparing(tag -> tag.getString("Name").toLowerCase()));
            break;
         default:
            result.sort(Comparator.<CompoundTag>comparingLong(tag -> tag.getLong("Created")).reversed());
      }

      return result;
   }

   public static CompoundTag findLife(Player player, UUID id) {
      ListTag lives = lives(player);

      for (int i = 0; i < lives.size(); i++) {
         CompoundTag life = lives.getCompound(i);
         if (life.hasUUID("Id") && life.getUUID("Id").equals(id)) {
            return life;
         }
      }

      return null;
   }

   public static CompoundTag createLife(ServerPlayer player, double ep, long timeKarma, long epKarma, long achievementKarma, long bossKarma) {
      CompoundTag life = new CompoundTag();
      life.putUUID("Id", UUID.randomUUID());
      life.putLong("Created", System.currentTimeMillis());
      life.putString("Name", "Reincarnation " + (lives(player).size() + 1));
      life.putString("PlayerName", player.getGameProfile().getName());
      life.putString("Avatar", player.getStringUUID());
      life.putString("Race", RaceAPI.getRaceFrom(player).getRace().map(r -> r.getRace().getRegistryName().toString()).orElse("none"));
      life.putDouble("EP", ep);
      saveEnergyAndStats(player, life);
      life.putLong("TimeKarma", timeKarma);
      life.putLong("EPKarma", epKarma);
      life.putLong("AchievementKarma", achievementKarma);
      life.putLong("BossKarma", bossKarma);
      life.putLong("KarmaGained", timeKarma + epKarma + achievementKarma + bossKarma);
      ListTag skills = new ListTag();

      for (ManasSkillInstance instance : SkillAPI.getSkillsFrom(player).getLearnedSkills()) {
         if (shouldArchiveSkill(instance, player)) {
            CompoundTag skill = new CompoundTag();
            ManasSkill manasSkill = instance.getSkill();
            skill.putString("Skill", manasSkill.getRegistryName().toString());
            skill.putString("Name", manasSkill.getName().getString());
            skill.putDouble("Mastery", instance.getMastery());
            skill.putBoolean("Mastered", instance.isMastered(player));
            skill.putInt("Modes", Math.max(1, instance.getModes()));
            if (manasSkill instanceof Skill tensuraSkill) {
               skill.putString("Type", tensuraSkill.getType().name());
            } else {
               skill.putString("Type", "OTHER");
            }

            ListTag modes = new ListTag();

            for (int mode = 0; mode < Math.max(1, instance.getModes()); mode++) {
               CompoundTag modeTag = new CompoundTag();
               modeTag.putInt("Mode", mode);
               modeTag.putString("ModeName", getModeName(manasSkill, instance, mode));
               modes.add(modeTag);
            }

            skill.put("ModeList", modes);
            skills.add(skill);
         }
      }

      life.put("Skills", skills);
      return life;
   }

   private static boolean shouldArchiveSkill(ManasSkillInstance instance, ServerPlayer player) {
      if (instance != null && instance.getSkill() != null) {
         try {
            if (instance.isTemporarySkill()) {
               return false;
            }
         } catch (Throwable var5) {
         }

         try {
            if (instance.isSubInstance()) {
               return false;
            }
         } catch (Throwable var4) {
         }

         try {
            if (instance.getMastery() < 0.0) {
               return false;
            }
         } catch (Throwable var3) {
         }

         return true;
      } else {
         return false;
      }
   }

   private static void saveEnergyAndStats(ServerPlayer player, CompoundTag life) {
      IExistence existence = TensuraStorages.getExistenceFrom(player);
      life.putDouble("Magicules", callDouble(existence, "getMagicule", 0.0));
      life.putDouble("MaxMagicules", EnergyHelper.getMaxMagicule(player));
      life.putDouble("Aura", callDouble(existence, "getAura", 0.0));
      life.putDouble("MaxAura", EnergyHelper.getMaxAura(player));
      life.putDouble("SpiritualHealth", callDouble(existence, "getSpiritualHealth", 0.0));
      life.putDouble("MaxSpiritualHealth", player.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH));
      life.putDouble("Health", player.getHealth());
      life.putDouble("MaxHealth", player.getMaxHealth());
      life.putDouble("Armor", player.getAttributeValue(Attributes.ARMOR));
      life.putDouble("AttackDamage", player.getAttributeValue(Attributes.ATTACK_DAMAGE));
      life.putDouble("MovementSpeed", player.getAttributeValue(Attributes.MOVEMENT_SPEED));
      life.putInt("SoulPoints", (int)callDouble(existence, "getSoulPoints", 0.0));
      life.putBoolean("TrueDemonLord", callBoolean(existence, "isTrueDemonLord", false));
      life.putBoolean("TrueHero", callBoolean(existence, "isTrueHero", false));
      life.putBoolean("DemonLordSeed", callBoolean(existence, "isDemonLordSeed", false));
      life.putBoolean("SpiritualForm", callBoolean(existence, "isSpiritualForm", false));
   }

   private static double callDouble(Object target, String methodName, double fallback) {
      try {
         Method method = target.getClass().getMethod(methodName);
         if (method.invoke(target) instanceof Number number) {
            return number.doubleValue();
         }
      } catch (Throwable var7) {
      }

      return fallback;
   }

   private static boolean callBoolean(Object target, String methodName, boolean fallback) {
      try {
         Method method = target.getClass().getMethod(methodName);
         if (method.invoke(target) instanceof Boolean bool) {
            return bool;
         }
      } catch (Throwable var6) {
      }

      return fallback;
   }

   private static String getModeName(ManasSkill skill, ManasSkillInstance instance, int mode) {
      if (skill instanceof TensuraSkill tensuraSkill) {
         return tensuraSkill.getModeName(instance, mode).getString();
      } else {
         return mode == 0 ? skill.getName().getString() : skill.getName().getString() + " " + mode;
      }
   }

   public static boolean isUnlocked(Player player, UUID life, ResourceLocation skill, int mode) {
      ListTag unlocked = unlocked(player);
      String key = currentKey(player, life, skill, mode);

      for (int i = 0; i < unlocked.size(); i++) {
         if (unlocked.getString(i).equals(key)) {
            return true;
         }
      }

      return false;
   }

   public static void markUnlocked(Player player, UUID life, ResourceLocation skill, int mode) {
      if (!isUnlocked(player, life, skill, mode)) {
         ListTag unlocked = unlocked(player);
         unlocked.add(StringTag.valueOf(currentKey(player, life, skill, mode)));
         root(player).put("UnlockedModes", unlocked);
      }
   }

   public static ListTag unlocked(Player player) {
      CompoundTag root = root(player);
      if (!root.contains("UnlockedModes", 9)) {
         root.put("UnlockedModes", new ListTag());
      }

      return root.getList("UnlockedModes", 8);
   }

   public static List<String> unlockedStrings(Player player) {
      List<String> result = new ArrayList<>();
      String current = currentLifeId(player).toString() + "|";
      ListTag unlocked = unlocked(player);

      for (int i = 0; i < unlocked.size(); i++) {
         String value = unlocked.getString(i);
         if (value.startsWith(current)) {
            result.add(value.substring(current.length()));
         }
      }

      return result;
   }

   public static String key(UUID life, ResourceLocation skill, int mode) {
      return life + "|" + skill + "|" + mode;
   }

   public static String currentKey(Player player, UUID life, ResourceLocation skill, int mode) {
      return currentLifeId(player) + "|" + key(life, skill, mode);
   }

   public static void writeMenuSnapshot(ServerPlayer player, RegistryFriendlyByteBuf buf, NirvanaData.Sort sort) {
      buf.writeLong(getKarma(player));
      buf.writeEnum(sort);
      List<CompoundTag> list = sortedLives(player, sort);
      buf.writeVarInt(list.size());

      for (CompoundTag life : list) {
         buf.writeNbt(life);
      }

      List<String> unlocked = unlockedStrings(player);
      buf.writeVarInt(unlocked.size());

      for (String value : unlocked) {
         buf.writeUtf(value);
      }
   }

   public static NirvanaData.Snapshot readMenuSnapshot(RegistryFriendlyByteBuf buf) {
      long karma = buf.readLong();
      NirvanaData.Sort sort = (NirvanaData.Sort)buf.readEnum(NirvanaData.Sort.class);
      int size = buf.readVarInt();
      List<CompoundTag> lives = new ArrayList<>();

      for (int i = 0; i < size; i++) {
         CompoundTag tag = buf.readNbt();
         if (tag != null) {
            lives.add(tag);
         }
      }

      int unlockedSize = buf.readVarInt();
      List<String> unlocked = new ArrayList<>();

      for (int i = 0; i < unlockedSize; i++) {
         unlocked.add(buf.readUtf(32767));
      }

      return new NirvanaData.Snapshot(karma, sort, lives, unlocked);
   }

   public record Snapshot(long karma, NirvanaData.Sort sort, List<CompoundTag> lives, List<String> unlocked) {
   }

   public enum Sort {
      NEWEST,
      OLDEST,
      MOST_KARMA,
      MOST_EP,
      NAME;
   }
}
