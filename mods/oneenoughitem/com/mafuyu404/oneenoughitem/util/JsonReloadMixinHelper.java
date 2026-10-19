package com.mafuyu404.oneenoughitem.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mafuyu404.oneenoughitem.api.ReplacementStrategy;
import com.mafuyu404.oneenoughitem.data.Replacements;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

public final class JsonReloadMixinHelper {
   private JsonReloadMixinHelper() {
   }

   public static void processJsonReload(
      String directory,
      ResourceManager resourceManager,
      Map<ResourceLocation, JsonElement> results,
      ReplacementStrategy strategy,
      Consumer<Map<String, String>> cacheBeginReloadOverride,
      Supplier<Boolean> hasAnyMappings,
      Function<String, Replacements.Rules> getDefaultRules,
      String modId
   ) {
      MixinUtils.FieldRule baseRule = strategy.getDataDirFieldRule(directory);
      MixinUtils.ReplacementLoader.CurrentSnapshot snapshot = strategy.loadCurrentSnapshot(resourceManager);
      Map<String, String> currentDataMap = snapshot.dataMap();
      Map<String, Replacements.Rules> currentDataRules = snapshot.dataRules();
      if ("recipe".equals(directory) && cacheBeginReloadOverride != null) {
         cacheBeginReloadOverride.accept(currentDataMap);
      }

      Set<String> currentSourceIds = new HashSet<>(currentDataMap.keySet());
      if (!currentDataMap.isEmpty() || !Boolean.FALSE.equals(hasAnyMappings.get())) {
         MixinUtils.FieldRule effectiveRule = new MixinUtils.FieldRule(baseRule.keys(), baseRule.strict());
         Map<String, Replacements.Rules> effectiveDataRules = getStringRulesMap(currentDataRules, currentDataMap, getDefaultRules, modId);
         if (results != null && !results.isEmpty()) {
            int replacedFiles = 0;
            int droppedFiles = 0;
            boolean fallbackEnabled = currentDataMap.isEmpty();
            Iterator<Entry<ResourceLocation, JsonElement>> it = results.entrySet().iterator();

            while (it.hasNext()) {
               Entry<ResourceLocation, JsonElement> entry = it.next();
               JsonElement json = entry.getValue();
               if (json != null) {
                  MixinUtils.ReplaceContext ctx = new MixinUtils.ReplaceContext(directory, effectiveRule, currentDataMap, currentSourceIds, effectiveDataRules);
                  ctx.allowCacheFallback = fallbackEnabled;

                  try {
                     JsonElement processed = replaceElementWithStrategy(json, ctx, strategy);
                     if (ctx.shouldDrop) {
                        it.remove();
                        droppedFiles++;
                        MixinUtils.LogHelper.logFileOperation("dropped", entry.getKey(), directory, ctx.lastMappingOrigin);
                     } else if (ctx.mutated && processed != null) {
                        entry.setValue(processed);
                        replacedFiles++;
                        MixinUtils.LogHelper.logFileOperation("rewritten", entry.getKey(), directory, "RULE-BASED", fallbackEnabled);
                     }
                  } catch (Exception e) {
                     MixinUtils.LogHelper.logError("JSON rewrite", entry.getKey(), directory, e);
                  }
               }
            }

            if (replacedFiles > 0 || droppedFiles > 0) {
               MixinUtils.LogHelper.logSummary(directory, replacedFiles, droppedFiles, "RULE-BASED", currentDataMap.isEmpty());
            }
         }
      }
   }

   @NotNull
   private static Map<String, Replacements.Rules> getStringRulesMap(
      Map<String, Replacements.Rules> currentDataRules, Map<String, String> currentDataMap, Function<String, Replacements.Rules> getDefaultRules, String modId
   ) {
      Replacements.Rules defaultRules = null;

      try {
         defaultRules = getDefaultRules.apply(modId);
      } catch (Exception var8) {
      }

      Map<String, Replacements.Rules> effectiveDataRules = new HashMap<>(currentDataRules);
      if (defaultRules != null) {
         for (String from : currentDataMap.keySet()) {
            effectiveDataRules.putIfAbsent(from, defaultRules);
         }
      }

      return effectiveDataRules;
   }

   private static JsonElement replaceElementWithStrategy(JsonElement element, MixinUtils.ReplaceContext ctx, ReplacementStrategy strategy) {
      if (element == null || ctx.shouldDrop) {
         return element;
      } else if (element.isJsonObject()) {
         return replaceInObjectWithStrategy(element.getAsJsonObject(), ctx, strategy);
      } else {
         return element.isJsonArray() ? replaceInArrayWithStrategy(element.getAsJsonArray(), ctx, strategy) : element;
      }
   }

   private static JsonElement replaceInObjectWithStrategy(JsonObject obj, MixinUtils.ReplaceContext ctx, ReplacementStrategy strategy) {
      if (ctx.shouldDrop) {
         return obj;
      }

      for (String key : new HashSet(obj.keySet())) {
         JsonElement value = obj.get(key);
         if (value != null) {
            if (ctx.rule.keys().contains(key) && value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
               strategy.replaceIdCommon(
                  value.getAsString(),
                  ctx,
                  (oldId, newId) -> MixinUtils.LogHelper.logReplace(ctx.dataType, oldId, newId, ctx.lastMappingOrigin, false),
                  v -> obj.add(key, v)
               );
               if (ctx.shouldDrop) {
                  return obj;
               }
            } else if (value.isJsonObject()) {
               obj.add(key, replaceInObjectWithStrategy(value.getAsJsonObject(), ctx, strategy));
            } else if (value.isJsonArray()) {
               obj.add(key, replaceInArrayWithStrategy(value.getAsJsonArray(), ctx, strategy));
            }
         }
      }

      return obj;
   }

   private static JsonElement replaceInArrayWithStrategy(JsonArray array, MixinUtils.ReplaceContext ctx, ReplacementStrategy strategy) {
      if (ctx.shouldDrop) {
         return array;
      }

      for (int i = 0; i < array.size(); i++) {
         int index = i;
         JsonElement elt = array.get(i);
         if (elt != null) {
            if (elt.isJsonObject()) {
               array.set(index, replaceInObjectWithStrategy(elt.getAsJsonObject(), ctx, strategy));
            } else if (elt.isJsonArray()) {
               array.set(index, replaceInArrayWithStrategy(elt.getAsJsonArray(), ctx, strategy));
            } else if (elt.isJsonPrimitive() && elt.getAsJsonPrimitive().isString()) {
               strategy.replaceIdCommon(
                  elt.getAsString(),
                  ctx,
                  (oldId, newId) -> MixinUtils.LogHelper.logReplace(ctx.dataType, oldId, newId, ctx.lastMappingOrigin, true),
                  v -> array.set(index, v)
               );
               if (ctx.shouldDrop) {
                  return array;
               }
            }
         }
      }

      return array;
   }
}
