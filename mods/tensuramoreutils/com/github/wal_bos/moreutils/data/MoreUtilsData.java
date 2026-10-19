package com.github.wal_bos.moreutils.data;

import com.github.wal_bos.moreutils.TensuraMoreUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

public final class MoreUtilsData {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final Type SKILL_TYPE = (new TypeToken<Map<String, SkillBanType>>() {}).getType();
   private static final Type SET_TYPE = (new TypeToken<Set<String>>() {}).getType();
   private static final Type CAP_TYPE = (new TypeToken<MoreUtilsData.CapSaveData>() {}).getType();
   private static final Map<ResourceLocation, SkillBanType> SKILL_BANS = new LinkedHashMap<>();
   private static final Set<ResourceLocation> ENGRAVING_BANS = new LinkedHashSet<>();
   private static final Map<UUID, MoreUtilsData.PlayerCaps> PLAYER_CAPS = new LinkedHashMap<>();
   private static int globalUniqueCap = -1;
   private static int globalUltimateCap = -1;
   private static int uniqueResetInterval = 1;
   private static int ultimateResetInterval = 1;
   private static int globalResetCap = -1;

   private MoreUtilsData() {
   }

   public static void load(MinecraftServer server) {
      SKILL_BANS.clear();
      ENGRAVING_BANS.clear();
      PLAYER_CAPS.clear();
      globalUniqueCap = -1;
      globalUltimateCap = -1;
      uniqueResetInterval = 1;
      ultimateResetInterval = 1;
      globalResetCap = -1;
      Path root = root(server);
      Path skills = root.resolve("skill_bans.json");
      Path engravings = root.resolve("engraving_bans.json");
      Path caps = root.resolve("skill_caps.json");
      if (Files.exists(skills)) {
         try (Reader reader = Files.newBufferedReader(skills)) {
            Map<String, SkillBanType> map = (Map<String, SkillBanType>)GSON.fromJson(reader, SKILL_TYPE);
            if (map != null) {
               map.forEach((idx, type) -> {
                  try {
                     if (type != null) {
                        SKILL_BANS.put(ResourceLocation.parse(idx), type);
                     }
                  } catch (Exception var3x) {
                  }
               });
            }
         } catch (IOException | JsonSyntaxException | IllegalStateException e) {
            TensuraMoreUtils.LOGGER.error("Failed to load skill bans. Resetting broken skill_bans.json", e);
            resetBrokenFile(skills, "{}");
         }
      }

      if (Files.exists(engravings)) {
         try (Reader reader = Files.newBufferedReader(engravings)) {
            Set<String> set = (Set<String>)GSON.fromJson(reader, SET_TYPE);
            if (set != null) {
               for (String id : set) {
                  try {
                     ENGRAVING_BANS.add(ResourceLocation.parse(id));
                  } catch (Exception var12) {
                  }
               }
            }
         } catch (IOException | JsonSyntaxException | IllegalStateException e) {
            TensuraMoreUtils.LOGGER.error("Failed to load engraving bans. Resetting broken engraving_bans.json", e);
            resetBrokenFile(engravings, "[]");
         }
      }

      if (Files.exists(caps)) {
         try (Reader reader = Files.newBufferedReader(caps)) {
            MoreUtilsData.CapSaveData data = (MoreUtilsData.CapSaveData)GSON.fromJson(reader, CAP_TYPE);
            if (data != null) {
               globalUniqueCap = Math.max(-1, data.globalUniqueCap());
               globalUltimateCap = Math.max(-1, data.globalUltimateCap());
               uniqueResetInterval = Math.max(1, data.uniqueResetInterval());
               ultimateResetInterval = Math.max(1, data.ultimateResetInterval());
               globalResetCap = data.version() < 1 ? -1 : Math.max(-1, data.globalResetCap());
               if (data.players() != null) {
                  data.players()
                     .forEach(
                        (idx, value) -> {
                           try {
                              if (value != null) {
                                 PLAYER_CAPS.put(
                                    UUID.fromString(idx),
                                    data.version() < 1
                                       ? new MoreUtilsData.PlayerCaps(value.name(), value.uniqueCap(), value.ultimateCap(), -1, 0, 0, 0).normalized()
                                       : value.normalized()
                                 );
                              }
                           } catch (Exception var4x) {
                           }
                        }
                     );
               }
            }
         } catch (IOException | JsonSyntaxException | IllegalStateException e) {
            TensuraMoreUtils.LOGGER.error("Failed to load skill caps. Resetting broken skill_caps.json", e);
            resetBrokenFile(caps, defaultCapJson());
         }
      }
   }

   public static void save(MinecraftServer server) {
      try {
         Path root = root(server);
         Files.createDirectories(root);
         Map<String, SkillBanType> skills = new LinkedHashMap<>();
         SKILL_BANS.forEach((id, type) -> skills.put(id.toString(), type));
         Set<String> engravings = new LinkedHashSet<>();
         ENGRAVING_BANS.forEach(id -> engravings.add(id.toString()));
         Map<String, MoreUtilsData.PlayerCaps> players = new LinkedHashMap<>();
         PLAYER_CAPS.forEach((id, caps) -> players.put(id.toString(), caps));

         try (Writer writer = Files.newBufferedWriter(root.resolve("skill_bans.json"))) {
            GSON.toJson(skills, writer);
         }

         try (Writer writer = Files.newBufferedWriter(root.resolve("engraving_bans.json"))) {
            GSON.toJson(engravings, writer);
         }

         try (Writer writer = Files.newBufferedWriter(root.resolve("skill_caps.json"))) {
            GSON.toJson(
               new MoreUtilsData.CapSaveData(1, globalUniqueCap, globalUltimateCap, uniqueResetInterval, ultimateResetInterval, globalResetCap, players),
               writer
            );
         }
      } catch (IOException e) {
         TensuraMoreUtils.LOGGER.error("Failed to save TensuraMoreUtils data", e);
      }
   }

   private static void resetBrokenFile(Path path, String fallback) {
      try {
         Files.createDirectories(path.getParent());
         Files.writeString(path, fallback);
      } catch (IOException ioException) {
         TensuraMoreUtils.LOGGER.error("Failed to reset broken TensuraMoreUtils data file: " + path, ioException);
      }
   }

   private static String defaultCapJson() {
      return "{\n  \"version\": 1,\n  \"globalUniqueCap\": -1,\n  \"globalUltimateCap\": -1,\n  \"uniqueResetInterval\": 1,\n  \"ultimateResetInterval\": 1,\n  \"globalResetCap\": -1,\n  \"players\": {}\n}";
   }

   private static Path root(MinecraftServer server) {
      return server.getWorldPath(LevelResource.ROOT).resolve("tensuramoreutils");
   }

   private static MoreUtilsData.PlayerCaps getOrCreate(UUID player, String name) {
      return PLAYER_CAPS.getOrDefault(player, new MoreUtilsData.PlayerCaps(name, -1, -1, -1, 0, 0, 0));
   }

   public static int getGlobalCap(SkillCapType type) {
      return type == SkillCapType.UNIQUE ? globalUniqueCap : globalUltimateCap;
   }

   public static void setGlobalCap(MinecraftServer server, SkillCapType type, int cap) {
      if (type == SkillCapType.UNIQUE) {
         globalUniqueCap = Math.max(-1, cap);
      } else {
         globalUltimateCap = Math.max(-1, cap);
      }

      save(server);
   }

   public static int getResetInterval(SkillCapType type) {
      return type == SkillCapType.UNIQUE ? uniqueResetInterval : ultimateResetInterval;
   }

   public static void setResetInterval(MinecraftServer server, SkillCapType type, int interval) {
      if (type == SkillCapType.UNIQUE) {
         uniqueResetInterval = Math.max(1, interval);
      } else {
         ultimateResetInterval = Math.max(1, interval);
      }

      save(server);
   }

   public static int getGlobalResetCap() {
      return globalResetCap;
   }

   public static void setGlobalResetCap(MinecraftServer server, int cap) {
      globalResetCap = Math.max(-1, cap);
      save(server);
   }

   public static Integer getPlayerResetCap(UUID player) {
      MoreUtilsData.PlayerCaps caps = PLAYER_CAPS.get(player);
      return caps != null && caps.resetCap() >= 0 ? caps.resetCap() : null;
   }

   public static int getEffectiveResetCap(UUID player) {
      Integer personal = getPlayerResetCap(player);
      return personal == null ? globalResetCap : personal;
   }

   public static void setPlayerResetCap(MinecraftServer server, UUID player, String name, int cap) {
      MoreUtilsData.PlayerCaps current = getOrCreate(player, name);
      PLAYER_CAPS.put(
         player,
         new MoreUtilsData.PlayerCaps(
            name,
            current.uniqueCap(),
            current.ultimateCap(),
            Math.max(0, cap),
            current.uniqueResetBonus(),
            current.ultimateResetBonus(),
            current.lastResetCounter()
         )
      );
      save(server);
   }

   public static void clearPlayerResetCap(MinecraftServer server, UUID player) {
      MoreUtilsData.PlayerCaps current = PLAYER_CAPS.get(player);
      if (current != null) {
         PLAYER_CAPS.put(
            player,
            new MoreUtilsData.PlayerCaps(
               current.name(),
               current.uniqueCap(),
               current.ultimateCap(),
               -1,
               current.uniqueResetBonus(),
               current.ultimateResetBonus(),
               current.lastResetCounter()
            )
         );
         removeIfEmpty(player);
         save(server);
      }
   }

   public static Integer getPlayerCap(UUID player, SkillCapType type) {
      MoreUtilsData.PlayerCaps caps = PLAYER_CAPS.get(player);
      if (caps == null) {
         return null;
      }

      int value = type == SkillCapType.UNIQUE ? caps.uniqueCap() : caps.ultimateCap();
      return value < 0 ? null : value;
   }

   public static void setPlayerCap(MinecraftServer server, UUID player, String name, SkillCapType type, int cap) {
      MoreUtilsData.PlayerCaps current = getOrCreate(player, name);
      MoreUtilsData.PlayerCaps updated = type == SkillCapType.UNIQUE
         ? new MoreUtilsData.PlayerCaps(
            name,
            Math.max(0, cap),
            current.ultimateCap(),
            current.resetCap(),
            current.uniqueResetBonus(),
            current.ultimateResetBonus(),
            current.lastResetCounter()
         )
         : new MoreUtilsData.PlayerCaps(
            name,
            current.uniqueCap(),
            Math.max(0, cap),
            current.resetCap(),
            current.uniqueResetBonus(),
            current.ultimateResetBonus(),
            current.lastResetCounter()
         );
      PLAYER_CAPS.put(player, updated);
      save(server);
   }

   public static void resetPlayerCaps(MinecraftServer server, UUID player) {
      MoreUtilsData.PlayerCaps current = PLAYER_CAPS.get(player);
      if (current != null) {
         PLAYER_CAPS.put(
            player,
            new MoreUtilsData.PlayerCaps(
               current.name(), -1, -1, current.resetCap(), current.uniqueResetBonus(), current.ultimateResetBonus(), current.lastResetCounter()
            )
         );
         removeIfEmpty(player);
         save(server);
      }
   }

   public static int getResetBonus(UUID player, SkillCapType type) {
      MoreUtilsData.PlayerCaps caps = PLAYER_CAPS.get(player);
      if (caps == null) {
         return 0;
      }

      int bonus = type == SkillCapType.UNIQUE ? caps.uniqueResetBonus() : caps.ultimateResetBonus();
      int max = getEffectiveResetCap(player);
      return max < 0 ? bonus : Math.min(bonus, max);
   }

   public static boolean processResetCounter(MinecraftServer server, UUID player, String name, int resetCounter, boolean enabled) {
      int currentCounter = Math.max(0, resetCounter);
      MoreUtilsData.PlayerCaps current = getOrCreate(player, name);
      int previousCounter = Math.max(0, current.lastResetCounter());
      if (currentCounter == previousCounter && current.name().equals(name)) {
         return false;
      }

      int uniqueBonus = current.uniqueResetBonus();
      int ultimateBonus = current.ultimateResetBonus();
      if (enabled && currentCounter > previousCounter) {
         uniqueBonus += crossedIntervals(previousCounter, currentCounter, uniqueResetInterval);
         ultimateBonus += crossedIntervals(previousCounter, currentCounter, ultimateResetInterval);
         int max = getEffectiveResetCap(player);
         if (max >= 0) {
            uniqueBonus = Math.min(uniqueBonus, max);
            ultimateBonus = Math.min(ultimateBonus, max);
         }
      }

      PLAYER_CAPS.put(
         player, new MoreUtilsData.PlayerCaps(name, current.uniqueCap(), current.ultimateCap(), current.resetCap(), uniqueBonus, ultimateBonus, currentCounter)
      );
      save(server);
      return true;
   }

   private static int crossedIntervals(int previous, int current, int interval) {
      int safeInterval = Math.max(1, interval);
      return Math.max(0, current / safeInterval - previous / safeInterval);
   }

   private static void removeIfEmpty(UUID player) {
      MoreUtilsData.PlayerCaps caps = PLAYER_CAPS.get(player);
      if (caps != null
         && caps.uniqueCap() < 0
         && caps.ultimateCap() < 0
         && caps.resetCap() < 0
         && caps.uniqueResetBonus() == 0
         && caps.ultimateResetBonus() == 0
         && caps.lastResetCounter() == 0) {
         PLAYER_CAPS.remove(player);
      }
   }

   public static Map<UUID, MoreUtilsData.PlayerCaps> playerCaps() {
      return Map.copyOf(PLAYER_CAPS);
   }

   public static void updatePlayerName(MinecraftServer server, UUID player, String name) {
      MoreUtilsData.PlayerCaps current = PLAYER_CAPS.get(player);
      if (current != null && !current.name().equals(name)) {
         PLAYER_CAPS.put(
            player,
            new MoreUtilsData.PlayerCaps(
               name,
               current.uniqueCap(),
               current.ultimateCap(),
               current.resetCap(),
               current.uniqueResetBonus(),
               current.ultimateResetBonus(),
               current.lastResetCounter()
            )
         );
         save(server);
      }
   }

   public static void banSkill(MinecraftServer server, ResourceLocation skill, SkillBanType type) {
      SKILL_BANS.put(skill, type);
      save(server);
   }

   public static void banSkills(MinecraftServer server, Collection<ResourceLocation> skills, SkillBanType type) {
      for (ResourceLocation skill : skills) {
         SKILL_BANS.put(skill, type);
      }

      save(server);
   }

   public static void unbanSkill(MinecraftServer server, ResourceLocation skill) {
      SKILL_BANS.remove(skill);
      save(server);
   }

   public static void clearSkillBans(MinecraftServer server) {
      SKILL_BANS.clear();
      save(server);
   }

   public static SkillBanType getSkillBan(ResourceLocation skill) {
      return SKILL_BANS.get(skill);
   }

   public static boolean isSkillBanned(ResourceLocation skill) {
      return SKILL_BANS.containsKey(skill);
   }

   public static boolean hasSkillBans() {
      return !SKILL_BANS.isEmpty();
   }

   public static boolean hasOwningSkillBans() {
      for (SkillBanType type : SKILL_BANS.values()) {
         if (type.blocksOwning()) {
            return true;
         }
      }

      return false;
   }

   public static boolean hasBlacklistedSkills() {
      for (SkillBanType type : SKILL_BANS.values()) {
         if (type.isBlacklist()) {
            return true;
         }
      }

      return false;
   }

   public static Map<ResourceLocation, SkillBanType> skillBans() {
      return Map.copyOf(SKILL_BANS);
   }

   public static void banEngraving(MinecraftServer server, ResourceLocation engraving) {
      ENGRAVING_BANS.add(engraving);
      save(server);
   }

   public static void banEngravings(MinecraftServer server, Collection<ResourceLocation> engravings) {
      ENGRAVING_BANS.addAll(engravings);
      save(server);
   }

   public static void unbanEngraving(MinecraftServer server, ResourceLocation engraving) {
      ENGRAVING_BANS.remove(engraving);
      save(server);
   }

   public static void clearEngravingBans(MinecraftServer server) {
      ENGRAVING_BANS.clear();
      save(server);
   }

   public static boolean isEngravingBanned(ResourceLocation engraving) {
      return ENGRAVING_BANS.contains(engraving);
   }

   public static boolean hasEngravingBans() {
      return !ENGRAVING_BANS.isEmpty();
   }

   public static Set<ResourceLocation> engravingBans() {
      return Set.copyOf(ENGRAVING_BANS);
   }

   private record CapSaveData(
      int version,
      int globalUniqueCap,
      int globalUltimateCap,
      int uniqueResetInterval,
      int ultimateResetInterval,
      int globalResetCap,
      Map<String, MoreUtilsData.PlayerCaps> players
   ) {
   }

   public record PlayerCaps(String name, int uniqueCap, int ultimateCap, int resetCap, int uniqueResetBonus, int ultimateResetBonus, int lastResetCounter) {
      public MoreUtilsData.PlayerCaps normalized() {
         return new MoreUtilsData.PlayerCaps(
            this.name == null ? "Unknown" : this.name,
            Math.max(-1, this.uniqueCap),
            Math.max(-1, this.ultimateCap),
            Math.max(-1, this.resetCap),
            Math.max(0, this.uniqueResetBonus),
            Math.max(0, this.ultimateResetBonus),
            Math.max(0, this.lastResetCounter)
         );
      }
   }
}
