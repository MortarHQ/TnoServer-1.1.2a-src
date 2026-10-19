package com.foxablazeaqzl.wiki.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

public final class FoxWikiData {
   private static final ResourceLocation DATA_LOCATION = ResourceLocation.fromNamespaceAndPath("foxablazeaqzl_wiki", "wiki/skill_descriptions.json");
   private static Map<String, FoxWikiData.Entry> entries;

   private FoxWikiData() {
   }

   public static Optional<String> getName(ResourceLocation skillId) {
      return find(skillId).map(entry -> entry.name(languageKey()));
   }

   public static Optional<String> getDescription(ResourceLocation skillId) {
      return find(skillId).map(entry -> entry.description(languageKey())).filter(value -> !value.isBlank());
   }

   public static boolean hasDescription(ResourceLocation skillId) {
      return find(skillId).map(FoxWikiData.Entry::hasDescription).orElse(false);
   }

   private static Optional<FoxWikiData.Entry> find(ResourceLocation skillId) {
      if (skillId == null) {
         return Optional.empty();
      }

      Map<String, FoxWikiData.Entry> loaded = load();
      FoxWikiData.Entry entry = loaded.get(skillId.toString().toLowerCase(Locale.ROOT));
      if (entry == null) {
         entry = loaded.get(skillId.getPath().toLowerCase(Locale.ROOT));
      }

      return Optional.ofNullable(entry);
   }

   private static Map<String, FoxWikiData.Entry> load() {
      if (entries != null) {
         return entries;
      }

      Map<String, FoxWikiData.Entry> loaded = new HashMap<>();
      Minecraft minecraft = Minecraft.getInstance();
      Optional<Resource> resource = minecraft.getResourceManager().getResource(DATA_LOCATION);
      if (resource.isEmpty()) {
         return Map.of();
      }

      try (Reader reader = resource.get().openAsReader()) {
         JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
         JsonArray array = root.getAsJsonArray("entries");
         if (array != null) {
            for (JsonElement element : array) {
               if (element.isJsonObject()) {
                  JsonObject object = element.getAsJsonObject();
                  String id = getString(object, "id");
                  if (!id.isBlank()) {
                     FoxWikiData.Entry entry = new FoxWikiData.Entry(
                        getString(getObject(object, "name"), "zh_cn"),
                        getString(getObject(object, "name"), "en_us"),
                        getString(getObject(object, "description"), "zh_cn"),
                        getString(getObject(object, "description"), "en_us")
                     );
                     put(loaded, id, entry);
                     JsonArray namespaces = object.getAsJsonArray("namespaces");
                     if (namespaces != null) {
                        for (JsonElement namespace : namespaces) {
                           if (namespace.isJsonPrimitive()) {
                              put(loaded, namespace.getAsString() + ":" + id, entry);
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (IOException | IllegalStateException ignored) {
         return Map.of();
      }

      entries = Map.copyOf(loaded);
      return entries;
   }

   private static void put(Map<String, FoxWikiData.Entry> map, String key, FoxWikiData.Entry entry) {
      if (key != null && !key.isBlank()) {
         map.put(key.toLowerCase(Locale.ROOT), entry);
      }
   }

   private static JsonObject getObject(JsonObject object, String key) {
      JsonElement element = object.get(key);
      return element != null && element.isJsonObject() ? element.getAsJsonObject() : new JsonObject();
   }

   private static String getString(JsonObject object, String key) {
      JsonElement element = object.get(key);
      return element != null && element.isJsonPrimitive() ? element.getAsString() : "";
   }

   private static String languageKey() {
      String code = Minecraft.getInstance().options.languageCode;
      return code != null && code.toLowerCase(Locale.ROOT).startsWith("zh") ? "zh_cn" : "en_us";
   }

   private record Entry(String zhName, String enName, String zhDescription, String enDescription) {
      String name(String languageKey) {
         if ("zh_cn".equals(languageKey) && !this.zhName.isBlank()) {
            return this.zhName;
         } else {
            return !this.enName.isBlank() ? this.enName : this.zhName;
         }
      }

      String description(String languageKey) {
         if ("zh_cn".equals(languageKey) && !this.zhDescription.isBlank()) {
            return this.zhDescription;
         } else {
            return !this.enDescription.isBlank() ? this.enDescription : this.zhDescription;
         }
      }

      boolean hasDescription() {
         return !this.zhDescription.isBlank() || !this.enDescription.isBlank();
      }
   }
}
