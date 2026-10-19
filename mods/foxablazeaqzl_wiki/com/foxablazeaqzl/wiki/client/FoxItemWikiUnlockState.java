package com.foxablazeaqzl.wiki.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class FoxItemWikiUnlockState {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final Set<ResourceLocation> unlockedItemIds = new HashSet<>();
   private static UUID loadedPlayerId;
   private static boolean loaded;

   private FoxItemWikiUnlockState() {
   }

   public static void clearPlayer() {
      loaded = false;
      loadedPlayerId = null;
      unlockedItemIds.clear();
   }

   public static boolean observeInventory(Player player) {
      if (player == null) {
         clearPlayer();
         return false;
      }

      ensureLoaded(player);
      Set<ResourceLocation> unlockableItemIds = FoxItemWikiData.getUnlockableItemIds();
      if (unlockableItemIds.isEmpty()) {
         return false;
      }

      boolean changed = false;

      for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
         ItemStack stack = player.getInventory().getItem(slot);
         if (!stack.isEmpty()) {
            ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
            if (unlockableItemIds.contains(itemId) && unlockedItemIds.add(itemId)) {
               changed = true;
            }
         }
      }

      if (changed) {
         save();
      }

      return changed;
   }

   public static boolean isVisible(FoxItemWikiData.Entry entry) {
      if (entry == null) {
         return false;
      }

      if (entry.isSchematic()) {
         return true;
      }

      ResourceLocation itemId = entry.itemResourceLocation();
      return itemId != null && unlockedItemIds.contains(itemId);
   }

   private static void ensureLoaded(Player player) {
      UUID playerId = player.getUUID();
      if (!loaded || !Objects.equals(loadedPlayerId, playerId)) {
         loaded = true;
         loadedPlayerId = playerId;
         unlockedItemIds.clear();
         Path path = statePath(playerId);
         if (Files.isRegularFile(path)) {
            try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
               JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
               loadItemSet(root.getAsJsonArray("unlocked"), unlockedItemIds);
            } catch (IOException | IllegalStateException ignored) {
               unlockedItemIds.clear();
            }
         }
      }
   }

   private static void loadItemSet(JsonArray array, Set<ResourceLocation> target) {
      if (array != null) {
         for (JsonElement element : array) {
            if (element.isJsonPrimitive()) {
               ResourceLocation itemId = ResourceLocation.tryParse(element.getAsString());
               if (itemId != null) {
                  target.add(itemId);
               }
            }
         }
      }
   }

   private static void save() {
      if (loaded && loadedPlayerId != null) {
         JsonObject root = new JsonObject();
         root.add("unlocked", toJsonArray(unlockedItemIds));
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

   private static JsonArray toJsonArray(Set<ResourceLocation> itemIds) {
      JsonArray array = new JsonArray();
      itemIds.stream().map(ResourceLocation::toString).sorted().forEach(array::add);
      return array;
   }

   private static Path statePath(UUID playerId) {
      return Minecraft.getInstance().gameDirectory.toPath().resolve("config").resolve("foxablazeaqzl_wiki").resolve("item_wiki_unlocked_" + playerId + ".json");
   }
}
