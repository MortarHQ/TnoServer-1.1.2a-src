package com.sighs.oneenoughblock.init;

import com.mafuyu404.oneenoughitem.data.Replacements;
import com.mafuyu404.oneenoughitem.data.Replacements.ProcessingMode;
import com.mafuyu404.oneenoughitem.data.Replacements.Rules;
import com.sighs.oneenoughblock.Oneenoughblock;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockReplacementCache {
   private static final ConcurrentHashMap<String, String> BlockMapCache = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<String, String> TagMapCache = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<String, Rules> BlockRulesCache = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<String, Rules> TagRulesCache = new ConcurrentHashMap<>();
   private static volatile Map<String, String> ReloadOverrideBlockMap = null;
   private static final Map<Block, Block> CACHE = new ConcurrentHashMap<>();

   public static String matchBlock(String id) {
      return Objects.requireNonNullElse(ReloadOverrideBlockMap, BlockMapCache).getOrDefault(id, null);
   }

   public static String matchTag(String tagId) {
      return TagMapCache.getOrDefault(tagId, null);
   }

   public static String matchTag(ResourceLocation tagId) {
      return tagId != null ? matchTag(tagId.toString()) : null;
   }

   public static boolean isTagReplaced(String tagId) {
      return tagId != null && TagMapCache.containsKey(tagId);
   }

   public static boolean isTagReplaced(ResourceLocation tagId) {
      return tagId != null && isTagReplaced(tagId.toString());
   }

   public static boolean isSourceBlockId(String id) {
      return id != null && Objects.requireNonNullElse(ReloadOverrideBlockMap, BlockMapCache).containsKey(id);
   }

   public static Optional<Block> resolveTarget(Block source, RegistryLookup<Block> lookup) {
      return Optional.ofNullable(CACHE.computeIfAbsent(source, b -> resolveInternal(b, lookup)));
   }

   private static Block resolveInternal(Block source, RegistryLookup<Block> lookup) {
      Optional<Block> direct = resolveTarget(source);
      return direct.orElseGet(() -> resolveTargetByTags(source, lookup).orElse(null));
   }

   public static Optional<Block> resolveTarget(Block source) {
      ResourceLocation id = BuiltInRegistries.BLOCK.getKey(source);
      String target = matchBlock(id.toString());
      if (target != null) {
         Block b = (Block)BuiltInRegistries.BLOCK.get(ResourceLocation.parse(target));
         return Optional.of(b);
      } else {
         return Optional.empty();
      }
   }

   public static Optional<Block> resolveTargetByTags(Block source, RegistryLookup<Block> registryLookup) {
      Reference<Block> sourceHolder = source.builtInRegistryHolder();

      for (Entry<String, String> entry : TagMapCache.entrySet()) {
         ResourceLocation tagId = ResourceLocation.parse(entry.getKey());
         ResourceLocation targetId = ResourceLocation.parse(entry.getValue());
         TagKey<Block> tagKey = TagKey.create(Registries.BLOCK, tagId);
         Optional<Named<Block>> tagOptional = registryLookup.get(tagKey);
         if (!tagOptional.isEmpty()) {
            Named<Block> holderSet = tagOptional.get();
            if (holderSet.contains(sourceHolder)) {
               Block target = (Block)BuiltInRegistries.BLOCK.get(targetId);
               return Optional.of(target);
            }
         }
      }

      return Optional.empty();
   }

   public static void putBlockMapping(String sourceId, String targetId) {
      if (sourceId != null && targetId != null) {
         BlockMapCache.put(sourceId, targetId);
      }
   }

   public static void putTagMapping(String sourceTag, String targetId) {
      if (sourceTag != null && targetId != null) {
         TagMapCache.put(sourceTag, targetId);
      }
   }

   public static boolean removeBlockReplacement(String blockId) {
      if (blockId != null && BlockMapCache.containsKey(blockId)) {
         String removed = BlockMapCache.remove(blockId);
         BlockRulesCache.remove(blockId);
         Oneenoughblock.LOGGER.debug("Removed fluid replacement from runtime cache: {} -> {}", blockId, removed);
         return true;
      } else {
         return false;
      }
   }

   public static boolean removeTagReplacement(String tagId) {
      if (tagId != null && TagMapCache.containsKey(tagId)) {
         String removed = TagMapCache.remove(tagId);
         TagRulesCache.remove(tagId);
         Oneenoughblock.LOGGER.debug("Removed tag replacement from runtime cache: {} -> {}", tagId, removed);
         return true;
      } else {
         return false;
      }
   }

   public static void removeReplacements(Collection<String> blockIds, Collection<String> tagIds) {
      boolean changed = false;
      if (blockIds != null) {
         for (String blockId : blockIds) {
            if (removeBlockReplacement(blockId)) {
               changed = true;
            }
         }
      }

      if (tagIds != null) {
         for (String tagId : tagIds) {
            if (removeTagReplacement(tagId)) {
               changed = true;
            }
         }
      }

      if (changed) {
         Oneenoughblock.LOGGER
            .info(
               "Removed {} block replacements and {} tag replacements from runtime cache",
               blockIds != null ? blockIds.size() : 0,
               tagIds != null ? tagIds.size() : 0
            );
      }
   }

   public static Collection<String> trackSourceIdOf(String id) {
      Collection<String> result = new HashSet<>();
      BlockMapCache.forEach((match, target) -> {
         if (target.equals(id)) {
            result.add(match);
         }
      });
      return result;
   }

   public static void clearCache() {
      BlockMapCache.clear();
      TagMapCache.clear();
      BlockRulesCache.clear();
      TagRulesCache.clear();
   }

   public static void putReplacement(Replacements replacement) {
      String result = replacement.result();
      replacement.rules().ifPresentOrElse(rules -> {
         for (String m : replacement.match()) {
            if (m != null && !m.isEmpty()) {
               if (m.startsWith("#")) {
                  String tagId = m.substring(1);
                  TagMapCache.put(tagId, result);
                  TagRulesCache.put(tagId, rules);
               } else {
                  BlockMapCache.put(m, result);
                  BlockRulesCache.put(m, rules);
               }
            }
         }
      }, () -> {
         for (String m : replacement.match()) {
            if (m != null && !m.isEmpty()) {
               if (m.startsWith("#")) {
                  TagMapCache.put(m.substring(1), result);
               } else {
                  BlockMapCache.put(m, result);
               }
            }
         }
      });
   }

   public static Optional<Rules> getGlobalDefaultRules() {
      try {
         OEBConfig cfg = OEBConfig.get();
         if (cfg != null) {
            return Optional.of(cfg.defaultRules().toRules());
         }
      } catch (Exception var1) {
      }

      return Optional.empty();
   }

   public static Optional<Rules> getBlockRules(String blockId) {
      return Optional.ofNullable(BlockRulesCache.get(blockId));
   }

   public static Optional<Rules> getTagRules(String tagId) {
      return Optional.ofNullable(TagRulesCache.get(tagId));
   }

   public static boolean shouldReplaceInDataDir(String blockId, String directory) {
      return getBlockRules(blockId)
         .or(BlockReplacementCache::getGlobalDefaultRules)
         .<Map>flatMap(Rules::data)
         .map(dataRules -> (ProcessingMode)dataRules.get(directory))
         .map(mode -> mode == ProcessingMode.REPLACE)
         .orElse(false);
   }

   public static boolean shouldReplaceInTagType(String blockId, String tagType) {
      return getBlockRules(blockId)
         .or(BlockReplacementCache::getGlobalDefaultRules)
         .<Map>flatMap(Rules::tag)
         .map(tagRules -> (ProcessingMode)tagRules.get(tagType))
         .map(mode -> mode == ProcessingMode.REPLACE)
         .orElse(false);
   }

   public static void beginReloadOverride(Map<String, String> currentBlockMap) {
      if (currentBlockMap != null && !currentBlockMap.isEmpty()) {
         ReloadOverrideBlockMap = new HashMap<>(currentBlockMap);
      } else {
         ReloadOverrideBlockMap = null;
      }
   }

   public static void endReloadOverride() {
      if (ReloadOverrideBlockMap != null) {
         Oneenoughblock.LOGGER.info("Disabled reload-override mapping: {} entries", ReloadOverrideBlockMap.size());
      }

      ReloadOverrideBlockMap = null;
   }

   public static boolean hasReloadOverride() {
      return ReloadOverrideBlockMap != null;
   }
}
