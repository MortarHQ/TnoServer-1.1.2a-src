package com.chronodawn.gui.data;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

public class ChronicleData {
   private static final Gson GSON = new Gson();
   private static final String CATEGORIES_PATH = "assets/chronodawn/chronicle/categories.json";
   private static final String ENTRIES_PATH_PREFIX = "assets/chronodawn/chronicle/entries/";
   private final Map<String, Category> categories = new LinkedHashMap<>();
   private boolean loaded = false;
   private static ChronicleData instance;

   private ChronicleData() {
   }

   public static ChronicleData getInstance() {
      if (instance == null) {
         instance = new ChronicleData();
      }

      return instance;
   }

   public void load(ResourceManager resourceManager) {
      this.categories.clear();
      this.loaded = false;

      try {
         this.loadCategories(resourceManager);
         this.loadEntries(resourceManager);
         this.loaded = true;
         int totalEntries = this.categories.values().stream().mapToInt(category -> category.getEntries().size()).sum();
         ChronoDawn.LOGGER.debug("Loaded {} Chronicle categories with {} total entries", this.categories.size(), totalEntries);
      } catch (IOException e) {
         ChronoDawn.LOGGER.error("Failed to load Chronicle data", e);
      }
   }

   private void loadCategories(ResourceManager resourceManager) throws IOException {
      ResourceLocation categoriesLocation = CompatResourceLocation.create("chronodawn", "chronicle/categories.json");
      Optional<Resource> resource = resourceManager.getResource(categoriesLocation);
      if (resource.isEmpty()) {
         ChronoDawn.LOGGER.warn("Categories file not found: {}", "assets/chronodawn/chronicle/categories.json");
      } else {
         try (BufferedReader reader = resource.get().openAsReader()) {
            JsonObject json = (JsonObject)GSON.fromJson(reader, JsonObject.class);

            for (java.util.Map.Entry<String, JsonElement> entry : json.entrySet()) {
               String categoryId = entry.getKey();
               JsonObject categoryJson = entry.getValue().getAsJsonObject();
               Category category = new Category(categoryId, categoryJson);
               this.categories.put(categoryId, category);
            }
         }
      }
   }

   private void loadEntries(ResourceManager resourceManager) throws IOException {
      for (String categoryId : this.categories.keySet()) {
         String entryPathPrefix = "chronicle/entries/" + categoryId + "/";
         this.loadEntriesForCategory(resourceManager, categoryId);
      }
   }

   private void loadEntriesForCategory(ResourceManager resourceManager, String categoryId) {
      Map<String, String[]> categoryEntries = new HashMap<>();
      categoryEntries.put("basics", new String[]{"welcome", "ancient_ruins", "time_hourglass", "portal_building", "portal_activation", "escaping"});
      categoryEntries.put("progression", new String[]{"time_distortion", "biomes", "wood_types", "forgotten_library", "portal_stabilizer", "progression_path"});
      categoryEntries.put(
         "structures", new String[]{"desert_clock_tower", "guardian_vault", "clockwork_depths", "phantom_catacombs", "entropy_crypt", "master_clock"}
      );
      categoryEntries.put("bosses", new String[]{"time_guardian", "chronos_warden", "clockwork_colossus", "temporal_phantom", "entropy_keeper", "time_tyrant"});
      categoryEntries.put("items", new String[]{"clockstone", "time_crystals", "chrono_aegis", "ultimate_artifacts"});
      String[] entryNames = categoryEntries.getOrDefault(categoryId, new String[0]);

      for (String entryName : entryNames) {
         ResourceLocation entryLocation = CompatResourceLocation.create("chronodawn", "chronicle/entries/" + categoryId + "/" + entryName + ".json");

         try {
            Optional<Resource> resource = resourceManager.getResource(entryLocation);
            if (resource.isPresent()) {
               try (BufferedReader reader = resource.get().openAsReader()) {
                  JsonObject json = (JsonObject)GSON.fromJson(reader, JsonObject.class);
                  Entry entry = new Entry(entryName, json);
                  Category category = this.categories.get(categoryId);
                  if (category != null) {
                     category.addEntry(entry);
                  }
               }
            }
         } catch (IOException e) {
            ChronoDawn.LOGGER.warn("Failed to load entry: {}/{}", categoryId, entryName);
         }
      }
   }

   public List<Category> getCategories() {
      List<Category> categoryList = new ArrayList<>(this.categories.values());
      categoryList.sort((a, b) -> Integer.compare(a.getSortnum(), b.getSortnum()));
      return categoryList;
   }

   public Category getCategory(String id) {
      return this.categories.get(id);
   }

   public boolean isLoaded() {
      return this.loaded;
   }

   public void reload(ResourceManager resourceManager) {
      this.load(resourceManager);
   }
}
