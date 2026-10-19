package fzzyhmstrs.emi_loot.server;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.EMILootAgnos;
import fzzyhmstrs.emi_loot.mixins.LootContextTypesAccessor;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

public class ServerResourceData {
   private static final Multimap<ServerResourceData.TableChecker, LootTable> DIRECT_DROPS = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);
   public static final List<ResourceLocation> SHEEP_TABLES;
   private static final List<ServerResourceData.TableChecker> TABLE_EXCLUSIONS = new LinkedList<>();
   private static final int DIRECT_DROPS_PATH_LENGTH = "direct_drops/".length();
   private static final int FILE_SUFFIX_LENGTH = ".json".length();

   public static void loadDirectTables(ResourceManager resourceManager, RegistryOps<JsonElement> ops) {
      DIRECT_DROPS.clear();
      resourceManager.listResources("direct_drops", path -> path.getPath().endsWith(".json")).forEach((id, resource) -> loadDirectTable(id, resource, ops));
      resourceManager.listResources("emi_loot_data", path -> path.getPath().endsWith(".json")).forEach(ServerResourceData::loadTableExclusion);
   }

   private static void loadDirectTable(ResourceLocation id, Resource resource, RegistryOps<JsonElement> ops) {
      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.info("Reading direct drop table from file: {}", id.toString());
      }

      String path = id.getPath();
      ResourceLocation id2 = ResourceLocation.fromNamespaceAndPath(
         id.getNamespace(), path.substring(DIRECT_DROPS_PATH_LENGTH, path.length() - FILE_SUFFIX_LENGTH)
      );
      String path2 = id2.getPath();
      if (!path2.startsWith("blocks/") && !path2.startsWith("entities/") && !path2.startsWith("chests/")) {
         EMILoot.LOGGER.error("File path for [{}] not correct; needs a 'blocks', 'entities' or 'chests' subfolder. Skipping.", id);
         EMILoot.LOGGER
            .error("Example: [./data/mod_id/direct_drops/blocks/cobblestone.json] is a valid block direct drop table path for a block added by [mod_id].");
      } else {
         try {
            BufferedReader reader = resource.openAsReader();
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            ServerResourceData.TableChecker checker = loadTableCheckerFromTable(id2, json);
            LootTable lootTable = EMILootAgnos.loadLootTable(id2, (LootTable)LootTable.DIRECT_CODEC.parse(ops, json).getOrThrow(IllegalStateException::new));
            if (lootTable != null) {
               DIRECT_DROPS.put(checker, lootTable);
            } else {
               EMILoot.LOGGER.error("Loot table in file [{}] is empty!", id);
            }
         } catch (Exception e) {
            EMILoot.LOGGER.error("Failed to open or read direct drops loot table file: {}", id);
         }
      }
   }

   private static ServerResourceData.TableChecker loadTableCheckerFromTable(ResourceLocation id, JsonObject jsonObject) {
      if (!jsonObject.has("match_tables")) {
         return new ServerResourceData.TableChecker(id, Optional.empty(), Optional.empty(), Optional.empty());
      } else {
         JsonElement matchesElement = jsonObject.get("match_tables");
         if (!matchesElement.isJsonObject()) {
            EMILoot.LOGGER.error("Direct drops table {} has malformed table matcher, needs to be an object", id);
            return new ServerResourceData.TableChecker(id, Optional.empty(), Optional.empty(), Optional.empty());
         } else {
            JsonObject matchesObject = matchesElement.getAsJsonObject();
            return loadTableChecker(id, jsonObject, "Direct drops table");
         }
      }
   }

   private static ServerResourceData.TableChecker loadTableChecker(ResourceLocation id, JsonObject matchesObject, String messagePrefix) {
      Optional<Pattern> regexCheck = Optional.empty();
      if (matchesObject.has("regex")) {
         JsonElement regexElement = matchesObject.get("regex");
         if (!regexElement.isJsonPrimitive()) {
            EMILoot.LOGGER.error("{} {} has malformed table matcher. 'regex' key needs a primitive string value", messagePrefix, id);
            return new ServerResourceData.TableChecker(id, Optional.empty(), Optional.empty(), Optional.empty());
         }

         JsonPrimitive regexPrimitive = regexElement.getAsJsonPrimitive();
         if (!regexPrimitive.isString()) {
            EMILoot.LOGGER.error("{} {} has malformed table matcher. 'regex' key needs a string value", messagePrefix, id);
            return new ServerResourceData.TableChecker(id, Optional.empty(), Optional.empty(), Optional.empty());
         }

         regexCheck = Optional.of(Pattern.compile(regexPrimitive.getAsString()));
      }

      Optional<List<ResourceLocation>> idsCheck = Optional.empty();
      if (matchesObject.has("ids")) {
         JsonElement idsElement = matchesObject.get("ids");
         if (!idsElement.isJsonArray()) {
            EMILoot.LOGGER.error("{} {} has malformed table matcher. 'ids' key needs to be an array of strings", messagePrefix, id);
            return new ServerResourceData.TableChecker(id, Optional.empty(), Optional.empty(), Optional.empty());
         }

         JsonArray idsArray = idsElement.getAsJsonArray();
         List<ResourceLocation> ids = new ArrayList<>();

         for (int i = 0; i < idsArray.size(); i++) {
            JsonElement idElement = idsArray.get(i);
            if (!idElement.isJsonPrimitive()) {
               EMILoot.LOGGER
                  .error("{} {} has malformed table matcher. 'id' key has non-primitive-string value at index {}", new Object[]{messagePrefix, id, i});
               return new ServerResourceData.TableChecker(id, Optional.empty(), Optional.empty(), Optional.empty());
            }

            JsonPrimitive idPrimitive = idElement.getAsJsonPrimitive();
            if (!idPrimitive.isString()) {
               EMILoot.LOGGER.error("{} {} has malformed table matcher. 'id' key has non-string value at index {}", new Object[]{messagePrefix, id, i});
               return new ServerResourceData.TableChecker(id, Optional.empty(), Optional.empty(), Optional.empty());
            }

            ResourceLocation idsId = ResourceLocation.tryParse(idPrimitive.getAsString());
            if (idsId == null) {
               EMILoot.LOGGER
                  .error(
                     "{} {} has malformed table matcher. 'id' key has unparsable identifier {} at index {}",
                     new Object[]{messagePrefix, id, idPrimitive.getAsString(), i}
                  );
               return new ServerResourceData.TableChecker(id, Optional.empty(), Optional.empty(), Optional.empty());
            }

            ids.add(idsId);
         }

         idsCheck = Optional.of(ids);
      }

      Optional<LootContextParamSet> typeCheck = Optional.empty();
      if (matchesObject.has("type")) {
         JsonElement typeElement = matchesObject.get("type");
         if (!typeElement.isJsonPrimitive()) {
            EMILoot.LOGGER.error("{} {} has malformed table matcher. 'type' key needs a primitive string value", messagePrefix, id);
            return new ServerResourceData.TableChecker(id, Optional.empty(), Optional.empty(), Optional.empty());
         }

         JsonPrimitive typePrimitive = typeElement.getAsJsonPrimitive();
         if (!typePrimitive.isString()) {
            EMILoot.LOGGER.error("{} {} has malformed table matcher. 'type' key needs a string value", messagePrefix, id);
            return new ServerResourceData.TableChecker(id, Optional.empty(), Optional.empty(), Optional.empty());
         }

         ResourceLocation typeId = ResourceLocation.tryParse(typePrimitive.getAsString());
         if (typeId == null) {
            EMILoot.LOGGER
               .error(
                  "{} {} has malformed table matcher. 'type' key has unparsable identifier {}", new Object[]{messagePrefix, id, typePrimitive.getAsString()}
               );
            return new ServerResourceData.TableChecker(id, Optional.empty(), Optional.empty(), Optional.empty());
         }

         LootContextParamSet type = (LootContextParamSet)LootContextTypesAccessor.getREGISTRY().get(typeId);
         if (type == null) {
            EMILoot.LOGGER.error("{} {} has malformed table matcher. 'type' key has unregistered context type {}", new Object[]{messagePrefix, id, typeId});
            return new ServerResourceData.TableChecker(id, Optional.empty(), Optional.empty(), Optional.empty());
         }

         typeCheck = Optional.of(type);
      }

      return new ServerResourceData.TableChecker(id, regexCheck, idsCheck, typeCheck);
   }

   private static void loadTableExclusion(ResourceLocation id, Resource resource) {
      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.info("Reading exclusion table from file: {}", id.toString());
      }

      try {
         BufferedReader reader = resource.openAsReader();
         JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
         JsonElement list = json.get("exclusions");
         if (list != null && list.isJsonArray()) {
            for (JsonElement element : list.getAsJsonArray()) {
               if (element.isJsonPrimitive()) {
                  ResourceLocation identifier = ResourceLocation.parse(element.getAsString());
                  if (EMILoot.DEBUG) {
                     EMILoot.LOGGER.info("Adding exclusion: {}", identifier);
                  }

                  TABLE_EXCLUSIONS.add(new ServerResourceData.TableChecker(identifier, Optional.empty(), Optional.empty(), Optional.empty()));
               } else if (element.isJsonObject()) {
                  if (EMILoot.DEBUG) {
                     EMILoot.LOGGER.info("Adding complex exclusion: {}", element);
                  }

                  TABLE_EXCLUSIONS.add(loadTableChecker(id, element.getAsJsonObject(), "Table exclusion"));
               } else {
                  EMILoot.LOGGER.error("Exclusion element not properly formatted: {}", element);
               }
            }
         } else {
            EMILoot.LOGGER.error("Exclusions in file: {} not readable.", id);
         }
      } catch (Exception e) {
         EMILoot.LOGGER.error("Failed to open or read table exclusions file: {}", id);
      }
   }

   public static boolean skipTable(LootTable table, ResourceLocation id) {
      for (ServerResourceData.TableChecker checker : TABLE_EXCLUSIONS) {
         if (checker.check(table, id)) {
            return true;
         }
      }

      return false;
   }

   public static List<LootTable> getDirectTables(LootTable table, ResourceLocation id, boolean getDirect) {
      if (!getDirect) {
         return List.of();
      }

      List<LootTable> tables = new ArrayList<>();

      for (ServerResourceData.TableChecker checker : DIRECT_DROPS.keySet()) {
         if (checker.check(table, id)) {
            Collection<LootTable> checkedTables = DIRECT_DROPS.get(checker);
            tables.addAll(checkedTables);
         }
      }

      return tables;
   }

   public static Multimap<ResourceLocation, LootTable> getMissedDirectDrops(List<ResourceLocation> parsedList) {
      Multimap<ResourceLocation, LootTable> missedDrops = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);

      for (Entry<ServerResourceData.TableChecker, LootTable> entry : DIRECT_DROPS.entries()) {
         if (!entry.getKey().isComplex() && !parsedList.contains(entry.getKey().idCheck())) {
            missedDrops.put(entry.getKey().idCheck(), entry.getValue());
         }
      }

      return missedDrops;
   }

   static {
      ResourceLocation[] ids = new ResourceLocation[]{
         BuiltInLootTables.SHEEP_WHITE.location(),
         BuiltInLootTables.SHEEP_ORANGE.location(),
         BuiltInLootTables.SHEEP_MAGENTA.location(),
         BuiltInLootTables.SHEEP_LIGHT_BLUE.location(),
         BuiltInLootTables.SHEEP_YELLOW.location(),
         BuiltInLootTables.SHEEP_LIME.location(),
         BuiltInLootTables.SHEEP_PINK.location(),
         BuiltInLootTables.SHEEP_GRAY.location(),
         BuiltInLootTables.SHEEP_LIGHT_GRAY.location(),
         BuiltInLootTables.SHEEP_CYAN.location(),
         BuiltInLootTables.SHEEP_PURPLE.location(),
         BuiltInLootTables.SHEEP_BLUE.location(),
         BuiltInLootTables.SHEEP_BROWN.location(),
         BuiltInLootTables.SHEEP_GREEN.location(),
         BuiltInLootTables.SHEEP_RED.location(),
         BuiltInLootTables.SHEEP_BLACK.location()
      };
      SHEEP_TABLES = Arrays.stream(ids).toList();
   }

   private record TableChecker(
      ResourceLocation idCheck, Optional<Pattern> regexCheck, Optional<List<ResourceLocation>> idsCheck, Optional<LootContextParamSet> typeCheck
   ) {
      boolean check(LootTable table, ResourceLocation id) {
         if (this.idCheck.equals(id)) {
            return true;
         } else if (this.regexCheck.map(regex -> regex.matcher(id.toString()).find()).orElse(false)) {
            return true;
         } else {
            return this.idsCheck.<Boolean>map(ids -> ids.contains(id)).orElse(false)
               ? true
               : this.typeCheck.<Boolean>map(type -> type.equals(table.getParamSet())).orElse(false);
         }
      }

      boolean isComplex() {
         return this.regexCheck.isPresent() || this.idsCheck.isPresent() || this.typeCheck.isPresent();
      }
   }
}
