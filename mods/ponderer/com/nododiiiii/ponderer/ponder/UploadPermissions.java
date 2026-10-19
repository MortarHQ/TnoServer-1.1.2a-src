package com.nododiiiii.ponderer.ponder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;
import org.slf4j.Logger;

public final class UploadPermissions {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final String PERMISSIONS_FILE = "permissions.txt";
   private static final String LEGACY_UPLOAD_ALLOWLIST_FILE = "upload_allowlist.txt";
   private static final Pattern USERNAME_PATTERN = Pattern.compile("[A-Za-z0-9_]{1,64}");

   private UploadPermissions() {
   }

   public static boolean canUpload(ServerPlayer player) {
      return hasRole(player, UploadPermissions.Role.UPLOAD);
   }

   public static boolean canPull(ServerPlayer player) {
      return hasRole(player, UploadPermissions.Role.PULL);
   }

   public static boolean canManage(ServerPlayer player) {
      if (player.hasPermissions(2)) {
         return true;
      }

      UploadPermissions.Role role = effectiveRole(player);
      return role != null && role.includes(UploadPermissions.Role.ADMIN);
   }

   public static UploadPermissions.Role effectiveRole(ServerPlayer player) {
      if (player.hasPermissions(2)) {
         return UploadPermissions.Role.ADMIN;
      }

      MinecraftServer server = player.server;
      Map<String, UploadPermissions.Entry> entries = loadEntryMap(server);
      return bestRoleFor(player, entries);
   }

   public static UploadPermissions.Snapshot snapshotFor(ServerPlayer player) {
      List<UploadPermissions.Entry> entries = loadEntries(player.server);
      return new UploadPermissions.Snapshot(entries, effectiveRole(player), player.hasPermissions(2), canManage(player));
   }

   public static void ensurePullAccess(ServerPlayer player) {
      if (player.hasPermissions(2)) {
         loadEntries(player.server);
      } else {
         try {
            Map<String, UploadPermissions.Entry> entries = loadEntryMap(player.server);
            if (bestRoleFor(player, entries) != null) {
               return;
            }

            String subject = sanitizeSubject(player.getGameProfile().getName());
            if (subject == null) {
               subject = player.getUUID().toString().toLowerCase(Locale.ROOT);
            }

            entries.put(subjectKey(subject), new UploadPermissions.Entry(subject, UploadPermissions.Role.PULL, false));
            saveEntries(player.server, entries.values());
         } catch (Exception e) {
            LOGGER.warn("Failed to add default pull permission for {}", player.getGameProfile().getName(), e);
         }
      }
   }

   public static UploadPermissions.UpdateResult upsert(ServerPlayer actor, String rawSubject, UploadPermissions.Role role) {
      if (!canManage(actor)) {
         return new UploadPermissions.UpdateResult(false, false, "ponderer.ui.function_page.permissions.denied", rawSubject);
      }

      String subject = sanitizeSubject(rawSubject);
      if (subject != null && role != null) {
         try {
            Map<String, UploadPermissions.Entry> entries = loadEntryMap(actor.server);
            String key = subjectKey(subject);
            UploadPermissions.Entry previous = entries.get(key);
            if (!isOperatorSubject(actor.server, subject) && (previous == null || !previous.operatorManaged())) {
               if (previous != null && previous.role() == role && previous.subject().equals(subject)) {
                  return new UploadPermissions.UpdateResult(true, false, "ponderer.ui.function_page.permissions.unchanged", subject);
               }

               if (previous != null
                  && previous.role() == UploadPermissions.Role.ADMIN
                  && role != UploadPermissions.Role.ADMIN
                  && !actor.hasPermissions(2)
                  && explicitAdminCount(entries) <= 1) {
                  return new UploadPermissions.UpdateResult(false, false, "ponderer.ui.function_page.permissions.last_admin", subject);
               }

               entries.put(key, new UploadPermissions.Entry(subject, role, false));
               saveEntries(actor.server, entries.values());
               return new UploadPermissions.UpdateResult(true, true, "ponderer.ui.function_page.permissions.updated." + role.id(), subject);
            } else {
               return new UploadPermissions.UpdateResult(false, false, "ponderer.ui.function_page.permissions.operator_locked", subject);
            }
         } catch (Exception e) {
            LOGGER.warn("Failed to update Ponderer permissions", e);
            return new UploadPermissions.UpdateResult(false, false, "ponderer.ui.function_page.permissions.error", subject);
         }
      } else {
         return new UploadPermissions.UpdateResult(false, false, "ponderer.ui.function_page.permissions.invalid_player", rawSubject);
      }
   }

   public static UploadPermissions.UpdateResult remove(ServerPlayer actor, String rawSubject) {
      if (!canManage(actor)) {
         return new UploadPermissions.UpdateResult(false, false, "ponderer.ui.function_page.permissions.denied", rawSubject);
      }

      String subject = sanitizeSubject(rawSubject);
      if (subject == null) {
         return new UploadPermissions.UpdateResult(false, false, "ponderer.ui.function_page.permissions.invalid_player", rawSubject);
      }

      try {
         Map<String, UploadPermissions.Entry> entries = loadEntryMap(actor.server);
         String key = subjectKey(subject);
         UploadPermissions.Entry previous = entries.get(key);
         if (previous == null) {
            return new UploadPermissions.UpdateResult(true, false, "ponderer.ui.function_page.permissions.not_found", subject);
         }

         if (!isOperatorSubject(actor.server, subject) && !previous.operatorManaged()) {
            if (previous.role() == UploadPermissions.Role.ADMIN && !actor.hasPermissions(2) && explicitAdminCount(entries) <= 1) {
               return new UploadPermissions.UpdateResult(false, false, "ponderer.ui.function_page.permissions.last_admin", previous.subject());
            }

            entries.remove(key);
            saveEntries(actor.server, entries.values());
            return new UploadPermissions.UpdateResult(true, true, "ponderer.ui.function_page.permissions.removed", previous.subject());
         } else {
            return new UploadPermissions.UpdateResult(false, false, "ponderer.ui.function_page.permissions.operator_locked", previous.subject());
         }
      } catch (Exception e) {
         LOGGER.warn("Failed to update Ponderer permissions", e);
         return new UploadPermissions.UpdateResult(false, false, "ponderer.ui.function_page.permissions.error", subject);
      }
   }

   public static List<UploadPermissions.Entry> loadEntries(MinecraftServer server) {
      return sortedEntries(loadEntryMap(server).values());
   }

   private static boolean hasRole(ServerPlayer player, UploadPermissions.Role required) {
      if (player.hasPermissions(2)) {
         return true;
      }

      UploadPermissions.Role role = effectiveRole(player);
      return role != null && role.includes(required);
   }

   private static UploadPermissions.Role bestRoleFor(ServerPlayer player, Map<String, UploadPermissions.Entry> entries) {
      String uuid = player.getUUID().toString().toLowerCase(Locale.ROOT);
      String name = player.getGameProfile().getName().toLowerCase(Locale.ROOT);
      UploadPermissions.Role best = null;
      UploadPermissions.Entry uuidEntry = entries.get(uuid);
      if (uuidEntry != null) {
         best = uuidEntry.role();
      }

      UploadPermissions.Entry nameEntry = entries.get(name);
      if (nameEntry != null && (best == null || nameEntry.role().level > best.level)) {
         best = nameEntry.role();
      }

      return best;
   }

   private static Map<String, UploadPermissions.Entry> loadEntryMap(MinecraftServer server) {
      Map<String, UploadPermissions.Entry> entries = new LinkedHashMap<>();
      Path permissionsPath = permissionPath(server);
      if (Files.exists(permissionsPath)) {
         readPermissionFile(permissionsPath, entries);
         syncServerOperators(server, entries);
         return entries;
      }

      Path legacyPath = legacyUploadAllowlistPath(server);
      if (Files.exists(legacyPath)) {
         readLegacyUploadAllowlist(legacyPath, entries);
      }

      syncServerOperators(server, entries);
      return entries;
   }

   private static void readPermissionFile(Path path, Map<String, UploadPermissions.Entry> entries) {
      try {
         for (String line : Files.readAllLines(path)) {
            UploadPermissions.Entry entry = parsePermissionLine(line);
            if (entry != null) {
               mergeEntry(entries, entry);
            }
         }
      } catch (Exception e) {
         LOGGER.warn("Failed to read Ponderer permissions: {}", path, e);
      }
   }

   private static void readLegacyUploadAllowlist(Path path, Map<String, UploadPermissions.Entry> entries) {
      try {
         for (String line : Files.readAllLines(path)) {
            String subject = sanitizeSubject(stripComment(line));
            if (subject != null) {
               mergeEntry(entries, new UploadPermissions.Entry(subject, UploadPermissions.Role.UPLOAD, false));
            }
         }
      } catch (Exception e) {
         LOGGER.warn("Failed to read legacy upload allowlist: {}", path, e);
      }
   }

   private static UploadPermissions.Entry parsePermissionLine(String line) {
      String stripped = stripComment(line);
      if (stripped.isBlank()) {
         return null;
      }

      int colonIndex = stripped.indexOf(58);
      String rolePart;
      String subjectPart;
      if (colonIndex > 0) {
         rolePart = stripped.substring(0, colonIndex);
         subjectPart = stripped.substring(colonIndex + 1);
      } else {
         String[] parts = stripped.split("\\s+", 2);
         if (parts.length == 2 && "op".equalsIgnoreCase(parts[0])) {
            rolePart = "op";
            subjectPart = parts[1];
         } else if (parts.length == 2 && UploadPermissions.Role.fromId(parts[0]) != null) {
            rolePart = parts[0];
            subjectPart = parts[1];
         } else {
            rolePart = UploadPermissions.Role.UPLOAD.id();
            subjectPart = stripped;
         }
      }

      boolean operatorManaged = "op".equalsIgnoreCase(rolePart);
      UploadPermissions.Role role = operatorManaged ? UploadPermissions.Role.ADMIN : UploadPermissions.Role.fromId(rolePart);
      String subject = sanitizeSubject(subjectPart);
      return role != null && subject != null ? new UploadPermissions.Entry(subject, role, operatorManaged) : null;
   }

   private static void mergeEntry(Map<String, UploadPermissions.Entry> entries, UploadPermissions.Entry entry) {
      String key = subjectKey(entry.subject());
      UploadPermissions.Entry previous = entries.get(key);
      if (previous == null || entry.operatorManaged() && !previous.operatorManaged() || entry.role().level > previous.role().level) {
         entries.put(key, entry);
      }
   }

   private static void saveEntries(MinecraftServer server, Iterable<UploadPermissions.Entry> entries) throws Exception {
      Path path = permissionPath(server);
      Files.createDirectories(path.getParent());
      List<String> lines = new ArrayList<>();
      lines.add("# Ponderer server permission allowlist");
      lines.add("# Format: role playerNameOrUuid");
      lines.add("# Roles: admin = manage/upload/pull, upload = upload/pull, pull = pull only");
      lines.add("# OP-derived admins are stored as: op playerName");

      for (UploadPermissions.Entry entry : sortedEntries(entries)) {
         String roleId = entry.operatorManaged() ? "op" : entry.role().id();
         lines.add(roleId + " " + entry.subject());
      }

      Files.write(path, lines);
   }

   private static List<UploadPermissions.Entry> sortedEntries(Iterable<UploadPermissions.Entry> entries) {
      List<UploadPermissions.Entry> sorted = new ArrayList<>();

      for (UploadPermissions.Entry entry : entries) {
         if (entry != null && entry.subject() != null && entry.role() != null) {
            sorted.add(entry);
         }
      }

      sorted.sort(
         Comparator.<UploadPermissions.Entry>comparingInt(entryx -> -entryx.role().level)
            .thenComparing(entryx -> !entryx.operatorManaged())
            .thenComparing(entryx -> entryx.subject().toLowerCase(Locale.ROOT))
      );
      return List.copyOf(sorted);
   }

   private static int explicitAdminCount(Map<String, UploadPermissions.Entry> entries) {
      int count = 0;

      for (UploadPermissions.Entry entry : entries.values()) {
         if (entry.role() == UploadPermissions.Role.ADMIN) {
            count++;
         }
      }

      return count;
   }

   private static Path permissionPath(MinecraftServer server) {
      return server.getWorldPath(LevelResource.ROOT).resolve("ponderer").resolve("permissions.txt");
   }

   private static Path legacyUploadAllowlistPath(MinecraftServer server) {
      return server.getWorldPath(LevelResource.ROOT).resolve("ponderer").resolve("upload_allowlist.txt");
   }

   private static void syncServerOperators(MinecraftServer server, Map<String, UploadPermissions.Entry> entries) {
      List<String> operatorSubjects = operatorDisplaySubjects(server);
      boolean changed = false;

      for (UploadPermissions.Entry entry : new ArrayList<>(entries.values())) {
         if (entry.operatorManaged() && !containsSubject(operatorSubjects, entry.subject())) {
            entries.put(subjectKey(entry.subject()), new UploadPermissions.Entry(entry.subject(), UploadPermissions.Role.UPLOAD, false));
            changed = true;
         }
      }

      for (String operatorSubject : operatorSubjects) {
         UploadPermissions.Entry previous = entries.get(subjectKey(operatorSubject));
         if (previous == null || !previous.operatorManaged() || previous.role() != UploadPermissions.Role.ADMIN || !previous.subject().equals(operatorSubject)) {
            entries.put(subjectKey(operatorSubject), new UploadPermissions.Entry(operatorSubject, UploadPermissions.Role.ADMIN, true));
            changed = true;
         }
      }

      if (changed) {
         try {
            saveEntries(server, entries.values());
         } catch (Exception e) {
            LOGGER.warn("Failed to synchronize Ponderer operator permissions", e);
         }
      }
   }

   private static List<String> operatorDisplaySubjects(MinecraftServer server) {
      List<String> subjects = new ArrayList<>();

      for (String name : server.getPlayerList().getOpNames()) {
         addSubject(subjects, name);
      }

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         if (player.hasPermissions(2)) {
            addSubject(subjects, player.getGameProfile().getName());
         }
      }

      return subjects;
   }

   private static boolean isOperatorSubject(MinecraftServer server, String subject) {
      return containsSubject(operatorAliases(server), subject);
   }

   private static List<String> operatorAliases(MinecraftServer server) {
      List<String> aliases = new ArrayList<>();

      for (String subject : operatorDisplaySubjects(server)) {
         addSubject(aliases, subject);
      }

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         if (player.hasPermissions(2)) {
            addSubject(aliases, player.getStringUUID());
         }
      }

      addOperatorFileAliases(server, aliases);
      return aliases;
   }

   private static void addOperatorFileAliases(MinecraftServer server, List<String> aliases) {
      Path path = server.getPlayerList().getOps().getFile().toPath();
      if (Files.exists(path)) {
         try (BufferedReader reader = Files.newBufferedReader(path)) {
            JsonElement root = JsonParser.parseReader(reader);
            if (root != null && root.isJsonArray()) {
               for (JsonElement element : root.getAsJsonArray()) {
                  if (element.isJsonObject()) {
                     JsonObject object = element.getAsJsonObject();
                     if (object.has("name")) {
                        addSubject(aliases, object.get("name").getAsString());
                     }

                     if (object.has("uuid")) {
                        addSubject(aliases, object.get("uuid").getAsString());
                     }
                  }
               }
            }
         } catch (Exception e) {
            LOGGER.warn("Failed to read server operator aliases", e);
         }
      }
   }

   private static void addSubject(List<String> subjects, String rawSubject) {
      String subject = sanitizeSubject(rawSubject);
      if (subject != null && !containsSubject(subjects, subject)) {
         subjects.add(subject);
      }
   }

   private static boolean containsSubject(List<String> subjects, String subject) {
      String key = subjectKey(subject);

      for (String candidate : subjects) {
         if (subjectKey(candidate).equals(key)) {
            return true;
         }
      }

      return false;
   }

   private static String stripComment(String line) {
      if (line == null) {
         return "";
      }

      int commentIndex = line.indexOf(35);
      String stripped = commentIndex >= 0 ? line.substring(0, commentIndex) : line;
      return stripped.trim();
   }

   private static String sanitizeSubject(String rawSubject) {
      if (rawSubject == null) {
         return null;
      }

      String subject = rawSubject.trim();
      if (!subject.isEmpty() && subject.length() <= 64 && !subject.chars().anyMatch(Character::isWhitespace)) {
         try {
            return UUID.fromString(subject).toString().toLowerCase(Locale.ROOT);
         } catch (IllegalArgumentException ignored) {
            return USERNAME_PATTERN.matcher(subject).matches() ? subject : null;
         }
      } else {
         return null;
      }
   }

   private static String subjectKey(String subject) {
      return subject.toLowerCase(Locale.ROOT);
   }

   public record Entry(String subject, UploadPermissions.Role role, boolean operatorManaged) {
   }

   public enum Role {
      PULL("pull", 1),
      UPLOAD("upload", 2),
      ADMIN("admin", 3);

      private final String id;
      private final int level;

      Role(String id, int level) {
         this.id = id;
         this.level = level;
      }

      public String id() {
         return this.id;
      }

      public boolean includes(UploadPermissions.Role required) {
         return this.level >= required.level;
      }

      public static UploadPermissions.Role fromId(String id) {
         if (id == null) {
            return null;
         }

         String normalized = id.trim().toLowerCase(Locale.ROOT);

         for (UploadPermissions.Role role : values()) {
            if (role.id.equals(normalized)) {
               return role;
            }
         }

         return null;
      }
   }

   public record Snapshot(List<UploadPermissions.Entry> entries, UploadPermissions.Role viewerRole, boolean serverOperator, boolean canManage) {
   }

   public record UpdateResult(boolean success, boolean changed, String messageKey, String subject) {
   }
}
