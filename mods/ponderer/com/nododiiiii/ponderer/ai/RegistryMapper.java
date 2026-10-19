package com.nododiiiii.ponderer.ai;

import com.nododiiiii.ponderer.compat.jei.JeiCompat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class RegistryMapper {
   private static void addEntry(
      List<RegistryMapper.Entry> allEntries,
      Set<String> indexedIdKinds,
      Map<String, List<RegistryMapper.Entry>> displayIndex,
      Map<String, List<RegistryMapper.Entry>> pathIndex,
      String id,
      String displayName,
      String path,
      String kind
   ) {
      String key = id + "|" + kind;
      if (!indexedIdKinds.contains(key)) {
         RegistryMapper.Entry e = new RegistryMapper.Entry(id, displayName, path, kind);
         allEntries.add(e);
         indexedIdKinds.add(key);
         displayIndex.computeIfAbsent(displayName, k -> new ArrayList<>()).add(e);
         pathIndex.computeIfAbsent(path, k -> new ArrayList<>()).add(e);
      }
   }

   private static int kindPriority(String kind) {
      if ("item".equals(kind)) {
         return 2;
      } else {
         return "block".equals(kind) ? 1 : 0;
      }
   }

   private static String formatKinds(String id, List<RegistryMapper.Entry> entries) {
      Set<String> kinds = new LinkedHashSet<>();

      for (RegistryMapper.Entry e : entries) {
         if (e.id().equals(id)) {
            kinds.add(e.kind());
         }
      }

      return kinds.stream().sorted(Comparator.comparingInt(RegistryMapper::kindPriority)).collect(Collectors.joining("/"));
   }

   private static String formatKindsFromAll(String id, List<RegistryMapper.Entry> allEntries) {
      Set<String> kinds = new LinkedHashSet<>();

      for (RegistryMapper.Entry e : allEntries) {
         if (e.id().equals(id)) {
            kinds.add(e.kind());
         }
      }

      return kinds.isEmpty() ? "block" : kinds.stream().sorted(Comparator.comparingInt(RegistryMapper::kindPriority)).collect(Collectors.joining("/"));
   }

   public static String buildMappingForDisplayNames(List<String> requestedNames, List<String> structureBlockIds) {
      Map<String, List<RegistryMapper.Entry>> displayIndex = new LinkedHashMap<>();
      Map<String, List<RegistryMapper.Entry>> pathIndex = new LinkedHashMap<>();
      List<RegistryMapper.Entry> allEntries = new ArrayList<>();
      Set<String> indexedIdKinds = new HashSet<>();
      BuiltInRegistries.BLOCK.forEach(block -> {
         String idx = BuiltInRegistries.BLOCK.getKey(block).toString();
         String displayName = block.getName().getString().toLowerCase(Locale.ROOT);
         String path = BuiltInRegistries.BLOCK.getKey(block).getPath().toLowerCase(Locale.ROOT);
         addEntry(allEntries, indexedIdKinds, displayIndex, pathIndex, idx, displayName, path, "block");
      });
      BuiltInRegistries.ENTITY_TYPE.forEach(entityType -> {
         String idx = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
         String displayName = entityType.getDescription().getString().toLowerCase(Locale.ROOT);
         String path = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath().toLowerCase(Locale.ROOT);
         addEntry(allEntries, indexedIdKinds, displayIndex, pathIndex, idx, displayName, path, "entity");
      });
      if (JeiCompat.isAvailable()) {
         for (String[] entry : JeiCompat.getAllExtraIngredientEntries()) {
            addEntry(allEntries, indexedIdKinds, displayIndex, pathIndex, entry[0], entry[1], entry[2], entry[3]);
         }
      } else {
         BuiltInRegistries.ITEM.forEach(item -> {
            String idx = BuiltInRegistries.ITEM.getKey(item).toString();
            String displayName = item.getDescription().getString().toLowerCase(Locale.ROOT);
            String path = BuiltInRegistries.ITEM.getKey(item).getPath().toLowerCase(Locale.ROOT);
            addEntry(allEntries, indexedIdKinds, displayIndex, pathIndex, idx, displayName, path, "item");
         });
      }

      StringBuilder sb = new StringBuilder("Display Name → Registry ID mapping:\n");
      Set<String> outputIds = new HashSet<>();

      for (String blockId : structureBlockIds) {
         ResourceLocation loc = ResourceLocation.tryParse(blockId);
         if (loc != null) {
            BuiltInRegistries.BLOCK.getOptional(loc).ifPresent(block -> {
               String displayName = block.getName().getString();
               String kindsx = formatKindsFromAll(blockId, allEntries);
               sb.append("  ").append(displayName).append(" → ").append(blockId).append(" [").append(kindsx).append("]\n");
               outputIds.add(blockId);
            });
         }
      }

      for (String name : requestedNames) {
         if (name != null && !name.isBlank()) {
            if (name.contains(":")) {
               sb.append("  ").append(name).append(" → ").append(name).append("\n");
            } else {
               String lower = name.toLowerCase(Locale.ROOT).trim();
               String asPath = lower.replace(' ', '_');
               Map<String, List<RegistryMapper.Entry>> exactById = new LinkedHashMap<>();
               List<RegistryMapper.Entry> byDisplay = displayIndex.get(lower);
               if (byDisplay != null) {
                  for (RegistryMapper.Entry e : byDisplay) {
                     exactById.computeIfAbsent(e.id(), k -> new ArrayList<>()).add(e);
                  }
               }

               for (String pathKey : List.of(lower, asPath)) {
                  List<RegistryMapper.Entry> byPath = pathIndex.get(pathKey);
                  if (byPath != null) {
                     for (RegistryMapper.Entry e : byPath) {
                        exactById.computeIfAbsent(e.id(), k -> new ArrayList<>()).add(e);
                     }
                  }
               }

               Set<String> alreadyOutput = new HashSet<>();

               for (String id : exactById.keySet()) {
                  if (outputIds.contains(id)) {
                     alreadyOutput.add(id);
                  }
               }

               if (alreadyOutput.isEmpty() || alreadyOutput.size() != exactById.size()) {
                  alreadyOutput.forEach(exactById::remove);
                  if (exactById.size() == 1) {
                     Map.Entry<String, List<RegistryMapper.Entry>> me = exactById.entrySet().iterator().next();
                     String id = me.getKey();
                     String kinds = formatKinds(id, me.getValue());
                     sb.append("  ").append(name).append(" → ").append(id).append(" [").append(kinds).append("]").append("  [Lv1: exact match]\n");
                     outputIds.add(id);
                  } else if (exactById.size() > 1) {
                     sb.append("  ").append(name).append(" → ");
                     sb.append(
                        exactById.entrySet()
                           .stream()
                           .map(mex -> (String)mex.getKey() + " [" + formatKinds((String)mex.getKey(), (List<RegistryMapper.Entry>)mex.getValue()) + "]")
                           .reduce((a, b) -> a + " | " + b)
                           .orElse("")
                     );
                     sb.append("  [Lv2: multiple exact matches, choose appropriate]\n");
                     exactById.keySet().forEach(outputIds::add);
                  } else {
                     String[] words = lower.split("\\s+");
                     String[] pathWords = asPath.split("_");
                     Map<String, List<RegistryMapper.Entry>> fuzzyById = new LinkedHashMap<>();
                     Iterator var42 = allEntries.iterator();

                     while (true) {
                        if (var42.hasNext()) {
                           RegistryMapper.Entry e = (RegistryMapper.Entry)var42.next();
                           if (fuzzyById.size() < 8) {
                              if (outputIds.contains(e.id())) {
                                 continue;
                              }

                              boolean allMatch = true;

                              for (String word : words) {
                                 String wordUnder = word.replace(' ', '_');
                                 if (!e.displayName().contains(word) && !e.path().contains(word) && !e.path().contains(wordUnder)) {
                                    allMatch = false;
                                    break;
                                 }
                              }

                              if (!allMatch && pathWords.length > 1) {
                                 allMatch = true;

                                 for (String pw : pathWords) {
                                    if (!e.displayName().contains(pw) && !e.path().contains(pw)) {
                                       allMatch = false;
                                       break;
                                    }
                                 }
                              }

                              if (allMatch) {
                                 fuzzyById.computeIfAbsent(e.id(), k -> new ArrayList<>()).add(e);
                              }
                              continue;
                           }
                        }

                        if (!fuzzyById.isEmpty()) {
                           sb.append("  ").append(name).append(" → ");
                           if (fuzzyById.size() == 1) {
                              Map.Entry<String, List<RegistryMapper.Entry>> me = fuzzyById.entrySet().iterator().next();
                              String id = me.getKey();
                              String kinds = formatKinds(id, me.getValue());
                              sb.append(id).append(" [").append(kinds).append("]").append("  [Lv3: fuzzy match]\n");
                           } else {
                              sb.append(
                                 fuzzyById.entrySet()
                                    .stream()
                                    .map(
                                       mex -> (String)mex.getKey() + " [" + formatKinds((String)mex.getKey(), (List<RegistryMapper.Entry>)mex.getValue()) + "]"
                                    )
                                    .reduce((a, b) -> a + " | " + b)
                                    .orElse("")
                              );
                              sb.append("  [Lv3: fuzzy, multiple candidates]\n");
                           }

                           fuzzyById.keySet().forEach(outputIds::add);
                        } else {
                           sb.append("  ").append(name).append(" → (NOT FOUND — use the exact registry ID, e.g. \"mod:block_name\")  [Lv4]\n");
                        }
                        break;
                     }
                  }
               }
            }
         }
      }

      return sb.toString();
   }

   private record Entry(String id, String displayName, String path, String kind) {
   }
}
