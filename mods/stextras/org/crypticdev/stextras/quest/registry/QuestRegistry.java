package org.crypticdev.stextras.quest.registry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.logging.LogUtils;
import dev.architectury.registry.ReloadListenerRegistry;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.crypticdev.stextras.quest.codec.QuestDefinitionJsonParser;
import org.crypticdev.stextras.quest.codec.QuestDefinitionValidator;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.slf4j.Logger;

public class QuestRegistry extends SimpleJsonResourceReloadListener {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final Gson GSON = new GsonBuilder().registerTypeAdapter(ResourceLocation.class, new QuestRegistry.ResourceLocationAdapter()).create();
   private static final ResourceLocation LISTENER_ID = ResourceLocation.fromNamespaceAndPath("stextras", "st_quests_reload");
   public static final QuestRegistry INSTANCE = new QuestRegistry();
   private final Map<ResourceLocation, QuestDefinition> byId = new HashMap<>();
   private final Map<QuestCategory, List<QuestDefinition>> byCategory = new EnumMap<>(QuestCategory.class);
   private final Map<ResourceLocation, QuestDefinition> clientById = new HashMap<>();
   private final Map<QuestCategory, List<QuestDefinition>> clientByCategory = new EnumMap<>(QuestCategory.class);
   private boolean clientCacheInitialized;

   private QuestRegistry() {
      super(GSON, "st_quests");
   }

   public static void registerReloadListener() {
      ReloadListenerRegistry.register(PackType.SERVER_DATA, INSTANCE, LISTENER_ID);
   }

   protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {
      this.byId.clear();
      this.resetCategoryMap(this.byCategory);

      try {
         RacePrestigeMetadataRegistry.INSTANCE.clear();
      } catch (Throwable var12) {
      }

      for (Entry<ResourceLocation, JsonElement> entry : jsonMap.entrySet()) {
         ResourceLocation virtualPath = entry.getKey();
         String[] parts = virtualPath.getPath().split("/");
         boolean isRacePack = parts.length >= 1 && "race_prestige".equals(parts[0]);

         try {
            if (isRacePack) {
               for (QuestDefinition QuestDefinition : QuestDefinitionJsonParser.parseRacePack(virtualPath, entry.getValue().getAsJsonObject())) {
                  this.registerIfValid(QuestDefinition);
               }
            } else {
               QuestDefinition QuestDefinition = QuestDefinitionJsonParser.parseQuest(virtualPath, entry.getValue().getAsJsonObject());
               this.registerIfValid(QuestDefinition);
            }
         } catch (Exception ex) {
            LOGGER.error("Failed to load QuestDefinition {}: {}", virtualPath, ex.toString());
         }
      }

      this.sortCategoryMap(this.byCategory);
      Map<ResourceLocation, Long> perRace = this.byCategory
         .getOrDefault(QuestCategory.RACE_PRESTIGE, List.of())
         .stream()
         .collect(Collectors.groupingBy(QuestDefinitionx -> QuestDefinitionx.race, Collectors.counting()));
      if (perRace.isEmpty()) {
         LOGGER.info("[STExtras] Race Prestige: no quests loaded.");
      } else {
         perRace.forEach((raceId, count) -> LOGGER.info("[STExtras] Race Prestige: {} -> {} quests", raceId, count));
      }

      LOGGER.info(
         "[STExtras] Loaded {} quests (by category: {})",
         this.byId.size(),
         this.byCategory.entrySet().stream().map(entryx -> entryx.getKey() + ":" + ((List)entryx.getValue()).size()).collect(Collectors.joining(", "))
      );
   }

   private void registerIfValid(QuestDefinition QuestDefinition) {
      if (QuestDefinitionValidator.validate(QuestDefinition)) {
         this.byId.put(QuestDefinition.id, QuestDefinition);
         this.byCategory.get(QuestDefinition.category).add(QuestDefinition);
      }
   }

   public void setQuestsOnClient(List<QuestDefinition> quests) {
      this.clientCacheInitialized = true;
      this.clientById.clear();
      this.resetCategoryMap(this.clientByCategory);
      this.storeInto(this.clientById, this.clientByCategory, quests);
      LOGGER.info("[STExtras Client] Replaced synced quest cache with {} quests.", this.clientById.size());
   }

   public void mergeQuestsOnClient(List<QuestDefinition> quests) {
      this.clientCacheInitialized = true;
      if (this.clientByCategory.isEmpty()) {
         this.resetCategoryMap(this.clientByCategory);
      }

      for (QuestDefinition quest : quests) {
         if (quest != null && quest.id != null && quest.category != null) {
            this.clientById.put(quest.id, quest);
         }
      }

      this.rebuildCategoryMap(this.clientByCategory, this.clientById.values());
      LOGGER.info("[STExtras Client] Merged {} synced quests into client cache ({} total).", quests.size(), this.clientById.size());
   }

   public QuestDefinition byId(ResourceLocation id) {
      return this.byId.get(id);
   }

   public QuestDefinition byIdClient(ResourceLocation id) {
      return !this.clientCacheInitialized ? this.byId(id) : this.clientById.get(id);
   }

   public Collection<QuestDefinition> all() {
      return this.byId.values();
   }

   public Collection<QuestDefinition> allClient() {
      return !this.clientCacheInitialized ? this.all() : this.clientById.values();
   }

   public List<QuestDefinition> byCategory(QuestCategory category) {
      return this.byCategory.getOrDefault(category, List.of());
   }

   public List<QuestDefinition> byCategoryClient(QuestCategory category) {
      return !this.clientCacheInitialized ? this.byCategory(category) : this.clientByCategory.getOrDefault(category, List.of());
   }

   public List<QuestDefinition> requiredForPrestige(int prestige) {
      return this.byCategory.getOrDefault(QuestCategory.REQUIRED, List.of()).stream().filter(QuestDefinition -> QuestDefinition.prestige == prestige).toList();
   }

   private void resetCategoryMap(Map<QuestCategory, List<QuestDefinition>> target) {
      target.clear();

      for (QuestCategory category : QuestCategory.values()) {
         target.put(category, new ArrayList<>());
      }
   }

   private void storeInto(
      Map<ResourceLocation, QuestDefinition> targetById, Map<QuestCategory, List<QuestDefinition>> targetByCategory, Collection<QuestDefinition> quests
   ) {
      for (QuestDefinition quest : quests) {
         if (quest != null && quest.id != null && quest.category != null) {
            targetById.put(quest.id, quest);
         }
      }

      this.rebuildCategoryMap(targetByCategory, targetById.values());
   }

   private void rebuildCategoryMap(Map<QuestCategory, List<QuestDefinition>> targetByCategory, Collection<QuestDefinition> quests) {
      if (targetByCategory.isEmpty()) {
         this.resetCategoryMap(targetByCategory);
      } else {
         targetByCategory.values().forEach(List::clear);
      }

      for (QuestDefinition quest : quests) {
         targetByCategory.get(quest.category).add(quest);
      }

      this.sortCategoryMap(targetByCategory);
   }

   private void sortCategoryMap(Map<QuestCategory, List<QuestDefinition>> target) {
      for (Entry<QuestCategory, List<QuestDefinition>> entry : target.entrySet()) {
         entry.getValue().sort(Comparator.<QuestDefinition, Integer>comparing(quest -> quest.rarity.ordinal()).thenComparing(quest -> quest.id.toString()));
      }
   }

   private static class ResourceLocationAdapter implements JsonSerializer<ResourceLocation>, JsonDeserializer<ResourceLocation> {
      public ResourceLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
         return ResourceLocation.parse(json.getAsString());
      }

      public JsonElement serialize(ResourceLocation src, Type typeOfSrc, JsonSerializationContext context) {
         return new JsonPrimitive(src.toString());
      }
   }
}
