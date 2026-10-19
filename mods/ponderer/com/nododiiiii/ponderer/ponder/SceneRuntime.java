package com.nododiiiii.ponderer.ponder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;

public final class SceneRuntime {
   private static volatile List<DslScene> scenes = List.of();
   private static final Pattern SCENE_SUFFIX = Pattern.compile("^(.+?)(?:_part(\\d+)|_(\\d+))$");
   private static final Map<String, List<SceneRuntime.SceneKeyEntry>> ponderIdToSceneKeys = new ConcurrentHashMap<>();

   private SceneRuntime() {
   }

   public static List<DslScene> getScenes() {
      return scenes;
   }

   public static void setScenes(List<DslScene> newScenes) {
      scenes = Collections.unmodifiableList(new ArrayList<>(newScenes));
   }

   public static void clearPonderIdMapping() {
      ponderIdToSceneKeys.clear();
   }

   public static void registerPonderIdMapping(String ponderSceneId, String sceneKey, int sceneIndex) {
      ponderIdToSceneKeys.computeIfAbsent(ponderSceneId, k -> new ArrayList<>()).add(new SceneRuntime.SceneKeyEntry(sceneKey, sceneIndex));
   }

   public static Set<SceneRuntime.PonderSceneRef> resolvePonderSceneRefs(Collection<String> sceneKeys) {
      Set<String> keySet = sceneKeys instanceof Set ? (Set)sceneKeys : new HashSet<>(sceneKeys);
      Set<SceneRuntime.PonderSceneRef> result = new HashSet<>();

      for (Entry<String, List<SceneRuntime.SceneKeyEntry>> entry : ponderIdToSceneKeys.entrySet()) {
         String ponderSceneId = entry.getKey();
         List<SceneRuntime.SceneKeyEntry> entries = entry.getValue();
         int occurrence = 0;

         for (SceneRuntime.SceneKeyEntry ske : entries) {
            if (keySet.contains(ske.sceneKey())) {
               result.add(new SceneRuntime.PonderSceneRef(ponderSceneId, occurrence));
            }

            occurrence++;
         }
      }

      return result;
   }

   @Nullable
   public static DslScene findByKey(String sceneKey) {
      if (sceneKey == null) {
         return null;
      }

      for (DslScene scene : scenes) {
         if (scene.id != null && sceneKey.equals(scene.sceneKey())) {
            return scene;
         }
      }

      return null;
   }

   @Nullable
   public static SceneRuntime.SceneMatch findBySceneId(ResourceLocation ponderSceneId) {
      return findBySceneId(ponderSceneId, -1);
   }

   @Nullable
   public static SceneRuntime.SceneMatch findBySceneId(ResourceLocation ponderSceneId, int occurrenceIndex) {
      if (ponderSceneId == null) {
         return null;
      }

      String fullId = ponderSceneId.toString();
      List<SceneRuntime.SceneKeyEntry> entries = ponderIdToSceneKeys.get(fullId);
      if (entries != null && !entries.isEmpty()) {
         SceneRuntime.SceneKeyEntry entry;
         if (occurrenceIndex >= 0 && occurrenceIndex < entries.size()) {
            entry = entries.get(occurrenceIndex);
         } else {
            entry = entries.get(0);
         }

         DslScene found = findByKey(entry.sceneKey());
         if (found != null) {
            return new SceneRuntime.SceneMatch(found, entry.sceneIndex());
         }
      }

      return findBySceneIdLegacy(ponderSceneId);
   }

   @Nullable
   private static SceneRuntime.SceneMatch findBySceneIdLegacy(ResourceLocation ponderSceneId) {
      String path = ponderSceneId.getPath();

      for (DslScene scene : scenes) {
         if (scene.id != null) {
            ResourceLocation sceneId = ResourceLocation.tryParse(scene.id);
            if (sceneId != null && sceneId.equals(ponderSceneId)) {
               return new SceneRuntime.SceneMatch(scene, 0);
            }
         }
      }

      Matcher matcher = SCENE_SUFFIX.matcher(path);
      if (matcher.matches()) {
         String basePath = matcher.group(1);
         String partNum = matcher.group(2) != null ? matcher.group(2) : matcher.group(3);
         int sceneIndex = 0;

         try {
            sceneIndex = Integer.parseInt(partNum) - 1;
         } catch (NumberFormatException var10) {
         }

         ResourceLocation baseId = ResourceLocation.fromNamespaceAndPath(ponderSceneId.getNamespace(), basePath);

         for (DslScene scene : scenes) {
            if (scene.id != null) {
               ResourceLocation sceneId = ResourceLocation.tryParse(scene.id);
               if (sceneId != null && sceneId.equals(baseId)) {
                  return new SceneRuntime.SceneMatch(scene, Math.max(0, sceneIndex));
               }
            }
         }
      }

      for (DslScene scene : scenes) {
         if (scene.id != null) {
            ResourceLocation sceneId = ResourceLocation.tryParse(scene.id);
            if (sceneId != null && ponderSceneId.getNamespace().equals(sceneId.getNamespace()) && path.startsWith(sceneId.getPath())) {
               String remainder = path.substring(sceneId.getPath().length());
               if (!remainder.isEmpty() && remainder.startsWith("_")) {
                  String suffix = remainder.substring(1);

                  try {
                     int idx = Integer.parseInt(suffix) - 1;
                     return new SceneRuntime.SceneMatch(scene, Math.max(0, idx));
                  } catch (NumberFormatException var11) {
                     if (scene.scenes != null) {
                        for (int i = 0; i < scene.scenes.size(); i++) {
                           DslScene.SceneSegment sc = scene.scenes.get(i);
                           if (sc.id != null && suffix.equals(sc.id)) {
                              return new SceneRuntime.SceneMatch(scene, i);
                           }
                        }

                        for (int i = 0; i < scene.scenes.size(); i++) {
                           DslScene.SceneSegment sc = scene.scenes.get(i);
                           if (sc.id != null && suffix.endsWith(sc.id)) {
                              return new SceneRuntime.SceneMatch(scene, i);
                           }
                        }
                     }

                     return new SceneRuntime.SceneMatch(scene, 0);
                  }
               }
            }
         }
      }

      return null;
   }

   public record PonderSceneRef(String ponderSceneId, int occurrenceIndex) {
   }

   public record SceneKeyEntry(String sceneKey, int sceneIndex) {
   }

   public record SceneMatch(DslScene scene, int sceneIndex) {
   }
}
