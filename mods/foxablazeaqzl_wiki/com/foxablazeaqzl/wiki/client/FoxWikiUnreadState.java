package com.foxablazeaqzl.wiki.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public final class FoxWikiUnreadState {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final Set<ResourceLocation> knownSkillIds = new HashSet<>();
   private static final Set<ResourceLocation> viewedSkillIds = new HashSet<>();
   private static final Set<ResourceLocation> currentWikiSkillIds = new HashSet<>();
   private static UUID loadedPlayerId;
   private static boolean loaded;
   private static boolean baselineCurrentSkillsAsViewed;

   private FoxWikiUnreadState() {
   }

   public static void clearPlayer() {
      loaded = false;
      loadedPlayerId = null;
      baselineCurrentSkillsAsViewed = false;
      knownSkillIds.clear();
      viewedSkillIds.clear();
      currentWikiSkillIds.clear();
   }

   public static void observeLearnedWikiSkills(Player player) {
      if (player == null) {
         clearPlayer();
      } else {
         ensureLoaded(player);
         currentWikiSkillIds.clear();
         currentWikiSkillIds.addAll(collectCurrentWikiSkills(player));
         boolean changed = false;
         if (baselineCurrentSkillsAsViewed) {
            changed |= knownSkillIds.addAll(currentWikiSkillIds);
            changed |= viewedSkillIds.addAll(currentWikiSkillIds);
            baselineCurrentSkillsAsViewed = false;
            save();
         } else {
            for (ResourceLocation skillId : currentWikiSkillIds) {
               if (knownSkillIds.add(skillId)) {
                  changed = true;
               }
            }

            if (changed) {
               save();
            }
         }
      }
   }

   public static boolean hasUnreadWiki() {
      Player player = Minecraft.getInstance().player;
      if (player == null) {
         return false;
      }

      observeLearnedWikiSkills(player);

      for (ResourceLocation skillId : currentWikiSkillIds) {
         if (isUnread(skillId)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isUnread(ResourceLocation skillId) {
      return skillId != null && currentWikiSkillIds.contains(skillId) && knownSkillIds.contains(skillId) && !viewedSkillIds.contains(skillId);
   }

   public static void markViewed(ResourceLocation skillId) {
      if (skillId != null && FoxWikiData.hasDescription(skillId)) {
         Player player = Minecraft.getInstance().player;
         if (player != null) {
            ensureLoaded(player);
            boolean changed = knownSkillIds.add(skillId);
            changed |= viewedSkillIds.add(skillId);
            if (changed) {
               save();
            }
         }
      }
   }

   private static void ensureLoaded(Player player) {
      UUID playerId = player.getUUID();
      if (!loaded || !Objects.equals(loadedPlayerId, playerId)) {
         loaded = true;
         loadedPlayerId = playerId;
         knownSkillIds.clear();
         viewedSkillIds.clear();
         currentWikiSkillIds.clear();
         Path path = statePath(playerId);
         baselineCurrentSkillsAsViewed = !Files.isRegularFile(path);
         if (!baselineCurrentSkillsAsViewed) {
            try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
               JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
               loadSkillSet(root.getAsJsonArray("known"), knownSkillIds);
               loadSkillSet(root.getAsJsonArray("viewed"), viewedSkillIds);
            } catch (IOException | IllegalStateException ignored) {
               baselineCurrentSkillsAsViewed = true;
               knownSkillIds.clear();
               viewedSkillIds.clear();
            }
         }
      }
   }

   private static Set<ResourceLocation> collectCurrentWikiSkills(Player player) {
      Set<ResourceLocation> ids = new HashSet<>();

      try {
         for (ManasSkillInstance instance : SkillAPI.getSkillsFrom(player).getLearnedSkills()) {
            ResourceLocation skillId = instance.getSkillId();
            if (instance.getSkill() != null && FoxWikiData.hasDescription(skillId)) {
               ids.add(skillId);
            }
         }
      } catch (RuntimeException var5) {
      }

      return ids;
   }

   private static void loadSkillSet(JsonArray array, Set<ResourceLocation> target) {
      if (array != null) {
         for (JsonElement element : array) {
            if (element.isJsonPrimitive()) {
               ResourceLocation skillId = ResourceLocation.tryParse(element.getAsString());
               if (skillId != null) {
                  target.add(skillId);
               }
            }
         }
      }
   }

   private static void save() {
      if (loaded && loadedPlayerId != null) {
         JsonObject root = new JsonObject();
         root.add("known", toJsonArray(knownSkillIds));
         root.add("viewed", toJsonArray(viewedSkillIds));
         Path path = statePath(loadedPlayerId);

         try {
            Files.createDirectories(path.getParent());

            try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
               GSON.toJson(root, writer);
            }
         } catch (IOException var7) {
         }
      }
   }

   private static JsonArray toJsonArray(Set<ResourceLocation> skillIds) {
      JsonArray array = new JsonArray();
      skillIds.stream().map(ResourceLocation::toString).sorted().forEach(array::add);
      return array;
   }

   private static Path statePath(UUID playerId) {
      return Minecraft.getInstance().gameDirectory.toPath().resolve("config").resolve("foxablazeaqzl_wiki").resolve("wiki_viewed_" + playerId + ".json");
   }
}
