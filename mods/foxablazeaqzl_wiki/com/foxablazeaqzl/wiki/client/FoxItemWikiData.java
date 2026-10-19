package com.foxablazeaqzl.wiki.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

public final class FoxItemWikiData {
   private static final ResourceLocation DATA_LOCATION = ResourceLocation.fromNamespaceAndPath("foxablazeaqzl_wiki", "wiki/item_descriptions.json");
   private static List<FoxItemWikiData.Entry> entries;

   private FoxItemWikiData() {
   }

   public static List<FoxItemWikiData.Entry> getEntries() {
      return load();
   }

   public static Set<ResourceLocation> getUnlockableItemIds() {
      return load()
         .stream()
         .filter(entry -> !entry.isSchematic())
         .map(FoxItemWikiData.Entry::itemResourceLocation)
         .filter(id -> id != null)
         .collect(Collectors.toUnmodifiableSet());
   }

   private static List<FoxItemWikiData.Entry> load() {
      if (entries != null) {
         return entries;
      }

      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.getResourceManager() == null) {
         return List.of();
      }

      FoxItemWikiData.OptionalResource resource = getResource(minecraft);
      if (resource.resource() == null) {
         entries = List.of();
         return entries;
      }

      List<FoxItemWikiData.Entry> loaded = new ArrayList<>();

      try (Reader reader = resource.resource().openAsReader()) {
         JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
         JsonArray array = root.getAsJsonArray("entries");
         if (array != null) {
            for (JsonElement element : array) {
               if (element.isJsonObject()) {
                  JsonObject object = element.getAsJsonObject();
                  String id = getString(object, "id");
                  if (!id.isBlank()) {
                     loaded.add(
                        new FoxItemWikiData.Entry(
                           id,
                           getString(object, "item_id"),
                           getLocalizedText(object, "name"),
                           getLocalizedText(object, "description"),
                           getLocalizedText(object, "category"),
                           getLocalizedText(object, "section")
                        )
                     );
                  }
               }
            }
         }
      } catch (IOException | IllegalStateException ignored) {
         entries = List.of();
         return entries;
      }

      entries = List.copyOf(loaded);
      return entries;
   }

   private static FoxItemWikiData.OptionalResource getResource(Minecraft minecraft) {
      return minecraft.getResourceManager()
         .getResource(DATA_LOCATION)
         .map(FoxItemWikiData.OptionalResource::new)
         .orElseGet(() -> new FoxItemWikiData.OptionalResource(null));
   }

   private static FoxItemWikiData.LocalizedText getLocalizedText(JsonObject object, String key) {
      JsonObject text = getObject(object, key);
      String zhCn = getString(text, "zh_cn");
      String enUs = getString(text, "en_us");
      return new FoxItemWikiData.LocalizedText(zhCn.isBlank() ? getString(text, "zh") : zhCn, enUs.isBlank() ? getString(text, "en") : enUs);
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

   public static final class Entry {
      private final String id;
      private final String itemId;
      private final FoxItemWikiData.LocalizedText name;
      private final FoxItemWikiData.LocalizedText description;
      private final FoxItemWikiData.LocalizedText category;
      private final FoxItemWikiData.LocalizedText section;

      private Entry(
         String id,
         String itemId,
         FoxItemWikiData.LocalizedText name,
         FoxItemWikiData.LocalizedText description,
         FoxItemWikiData.LocalizedText category,
         FoxItemWikiData.LocalizedText section
      ) {
         this.id = id;
         this.itemId = itemId;
         this.name = name;
         this.description = description;
         this.category = category;
         this.section = section;
      }

      public String id() {
         return this.id;
      }

      public String itemId() {
         return this.itemId;
      }

      public ResourceLocation itemResourceLocation() {
         return ResourceLocation.tryParse(this.itemId);
      }

      public boolean isSchematic() {
         return "Schematics".equals(this.category.enUs);
      }

      public String categoryName() {
         return this.category.localized(FoxItemWikiData.languageKey());
      }

      public String name() {
         return this.name.localized(FoxItemWikiData.languageKey());
      }

      public String description() {
         return this.description.localized(FoxItemWikiData.languageKey());
      }

      public boolean matches(String filter) {
         if (filter != null && !filter.isBlank()) {
            String lowerFilter = filter.toLowerCase(Locale.ROOT);
            return this.id.toLowerCase(Locale.ROOT).contains(lowerFilter)
               || this.itemId.toLowerCase(Locale.ROOT).contains(lowerFilter)
               || this.name.zhCn.toLowerCase(Locale.ROOT).contains(lowerFilter)
               || this.name.enUs.toLowerCase(Locale.ROOT).contains(lowerFilter)
               || this.category.zhCn.toLowerCase(Locale.ROOT).contains(lowerFilter)
               || this.category.enUs.toLowerCase(Locale.ROOT).contains(lowerFilter)
               || this.section.zhCn.toLowerCase(Locale.ROOT).contains(lowerFilter)
               || this.section.enUs.toLowerCase(Locale.ROOT).contains(lowerFilter);
         } else {
            return true;
         }
      }
   }

   private record LocalizedText(String zhCn, String enUs) {
      String localized(String languageKey) {
         if ("zh_cn".equals(languageKey) && !this.zhCn.isBlank()) {
            return this.zhCn;
         } else {
            return !this.enUs.isBlank() ? this.enUs : this.zhCn;
         }
      }
   }

   private record OptionalResource(Resource resource) {
   }
}
