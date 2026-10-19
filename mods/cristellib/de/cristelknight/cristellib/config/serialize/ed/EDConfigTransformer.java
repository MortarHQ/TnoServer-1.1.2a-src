package de.cristelknight.cristellib.config.serialize.ed;

import de.cristelknight.cristellib.StructureConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;

public class EDConfigTransformer {
   public static Map<String, Boolean> stringBooleanMap(NestedEDConfig edConfig, String parent) {
      Map<String, Boolean> map = new HashMap<>();

      for (Entry<String, NestedEDConfig.Entry> entry : edConfig.entries().entrySet()) {
         String key = entry.getKey();
         NestedEDConfig.Entry object = entry.getValue();
         String finalKey = parent.isEmpty() ? key : parent + "/" + key;
         if (object.isBoolean()) {
            map.put(finalKey, object.value());
         } else {
            map.putAll(stringBooleanMap(object.nested(), finalKey));
         }
      }

      return map;
   }

   public static Map<String, NestedEDConfig> mapToNestedStructures(Map<ResourceLocation, List<ResourceLocation>> sets, StructureConfig structureConfig) {
      Map<String, NestedEDConfig> nestedStructures = new HashMap<>();

      for (Entry<ResourceLocation, List<ResourceLocation>> mapEntry : sets.entrySet()) {
         ResourceLocation location = mapEntry.getKey();
         List<ResourceLocation> stringList = mapEntry.getValue();
         Map<String, NestedEDConfig.Entry> entries = new HashMap<>();

         for (ResourceLocation structure : stringList) {
            String structureName = structureConfig.toDefaultString(structure);
            putStructureName(structureName, true, entries);
         }

         NestedEDConfig nestedStructure = new NestedEDConfig(entries);
         nestedStructures.put(structureConfig.toDefaultString(location), nestedStructure);
      }

      return nestedStructures;
   }

   public static Map<String, NestedEDConfig> mapToNestedStructuresWithValues(Map<ResourceLocation, EDConfig> sets, StructureConfig structureConfig) {
      Map<String, NestedEDConfig> nestedStructures = new HashMap<>();

      for (Entry<ResourceLocation, EDConfig> mapEntry : sets.entrySet()) {
         ResourceLocation location = mapEntry.getKey();
         EDConfig stringList = mapEntry.getValue();
         Map<String, NestedEDConfig.Entry> entries = new HashMap<>();

         for (Entry<String, Boolean> entry : stringList.setStructureInfo().entrySet()) {
            String structure = entry.getKey();
            boolean value = entry.getValue();
            putStructureName(structure, value, entries);
         }

         NestedEDConfig nestedStructure = new NestedEDConfig(entries);
         nestedStructures.put(structureConfig.toDefaultString(location), nestedStructure);
      }

      return nestedStructures;
   }

   public static void putStructureName(String structureName, boolean value, Map<String, NestedEDConfig.Entry> entries) {
      if (structureName.contains("/")) {
         String[] parts = structureName.split("/", 2);
         String key = parts[0];
         String restOfStructureName = parts[1];
         boolean containsNestedStructure = entries.containsKey(key);
         Map<String, NestedEDConfig.Entry> nestedEntries;
         if (containsNestedStructure) {
            nestedEntries = entries.get(key).nested().entries();
         } else {
            nestedEntries = new HashMap<>();
         }

         putStructureName(restOfStructureName, value, nestedEntries);
         if (!containsNestedStructure) {
            entries.put(key, NestedEDConfig.Entry.ofNested(new NestedEDConfig(nestedEntries)));
         }
      } else {
         entries.put(structureName, NestedEDConfig.Entry.ofBoolean(value));
      }
   }
}
