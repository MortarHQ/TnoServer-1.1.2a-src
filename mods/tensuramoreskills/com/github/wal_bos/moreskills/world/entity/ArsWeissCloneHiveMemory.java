package com.github.wal_bos.moreskills.world.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class ArsWeissCloneHiveMemory {
   private static final Map<UUID, ArsWeissCloneHiveMemory.Hive> HIVES = new HashMap<>();
   private static final int MAX_DISCOVERIES = 40;
   private static final int MAX_CLONES = 32;
   private static final long DISCOVERY_TTL = 4800L;
   private static final long CLONE_TTL = 2400L;

   private ArsWeissCloneHiveMemory() {
   }

   public static void touch(UUID owner, UUID clone, String dimension, BlockPos pos, long gameTime) {
      if (owner != null && clone != null && pos != null) {
         ArsWeissCloneHiveMemory.Hive hive = HIVES.computeIfAbsent(owner, ignored -> new ArsWeissCloneHiveMemory.Hive());
         if (hive.clones.size() >= 32 && !hive.clones.containsKey(clone)) {
            prune(owner, gameTime);
            if (hive.clones.size() >= 32) {
               return;
            }
         }

         hive.clones.put(clone, new ArsWeissCloneHiveMemory.CloneTrace(dimension == null ? "" : dimension, pos.immutable(), gameTime));
      }
   }

   public static void remember(UUID owner, String type, String query, String dimension, BlockPos pos, int score, long gameTime) {
      if (owner != null && pos != null && type != null && !type.isBlank()) {
         ArsWeissCloneHiveMemory.Hive hive = HIVES.computeIfAbsent(owner, ignored -> new ArsWeissCloneHiveMemory.Hive());
         String cleanType = clean(type);
         String cleanQuery = clean(query);
         String dim = dimension == null ? "" : dimension;

         for (ArsWeissCloneHiveMemory.Discovery discovery : hive.discoveries) {
            if (discovery.type.equals(cleanType) && discovery.dimension.equals(dim) && discovery.pos.distSqr(pos) <= 16.0) {
               discovery.query = mergeQuery(discovery.query, cleanQuery);
               discovery.score = Math.max(discovery.score, score);
               discovery.gameTime = gameTime;
               return;
            }
         }

         if (hive.discoveries.size() >= 40) {
            prune(owner, gameTime);

            while (hive.discoveries.size() >= 40) {
               hive.discoveries.remove(0);
            }
         }

         hive.discoveries.add(new ArsWeissCloneHiveMemory.Discovery(cleanType, cleanQuery, dim, pos.immutable(), score, gameTime));
      }
   }

   public static BlockPos nearest(UUID owner, String dimension, String query, BlockPos origin, int maxDistance) {
      if (owner != null && origin != null) {
         ArsWeissCloneHiveMemory.Hive hive = HIVES.get(owner);
         if (hive != null && !hive.discoveries.isEmpty()) {
            String cleanQuery = clean(query);
            String dim = dimension == null ? "" : dimension;
            ArsWeissCloneHiveMemory.Discovery best = null;
            double bestScore = Double.MAX_VALUE;
            double max = Math.min(256, Math.max(16, maxDistance));
            double maxSqr = max * max;

            for (ArsWeissCloneHiveMemory.Discovery discovery : hive.discoveries) {
               if (discovery.dimension.equals(dim)) {
                  double dist = discovery.pos.distSqr(origin);
                  if (!(dist > maxSqr)) {
                     double score = dist - discovery.score * 64.0;
                     if (!cleanQuery.isBlank()
                        && (discovery.query.contains(cleanQuery) || cleanQuery.contains(discovery.query) || containsUsefulPart(discovery.query, cleanQuery))) {
                        score -= 4096.0;
                     }

                     if (best == null || score < bestScore) {
                        best = discovery;
                        bestScore = score;
                     }
                  }
               }
            }

            return best == null ? null : best.pos.immutable();
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static int discoveryCount(UUID owner) {
      ArsWeissCloneHiveMemory.Hive hive = owner == null ? null : HIVES.get(owner);
      return hive == null ? 0 : hive.discoveries.size();
   }

   public static int cloneCount(UUID owner) {
      ArsWeissCloneHiveMemory.Hive hive = owner == null ? null : HIVES.get(owner);
      return hive == null ? 0 : hive.clones.size();
   }

   public static void prune(UUID owner, long gameTime) {
      ArsWeissCloneHiveMemory.Hive hive = owner == null ? null : HIVES.get(owner);
      if (hive != null) {
         Iterator<ArsWeissCloneHiveMemory.Discovery> discoveries = hive.discoveries.iterator();

         while (discoveries.hasNext()) {
            ArsWeissCloneHiveMemory.Discovery discovery = discoveries.next();
            if (gameTime - discovery.gameTime > 4800L) {
               discoveries.remove();
            }
         }

         Iterator<Entry<UUID, ArsWeissCloneHiveMemory.CloneTrace>> clones = hive.clones.entrySet().iterator();

         while (clones.hasNext()) {
            Entry<UUID, ArsWeissCloneHiveMemory.CloneTrace> entry = clones.next();
            if (gameTime - entry.getValue().gameTime > 2400L) {
               clones.remove();
            }
         }

         if (hive.discoveries.isEmpty() && hive.clones.isEmpty()) {
            HIVES.remove(owner);
         }
      }
   }

   public static String itemQuery(ItemStack stack) {
      if (stack != null && !stack.isEmpty()) {
         ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
         String registry = id == null ? "" : id.toString();
         return clean(registry + " " + stack.getHoverName().getString());
      } else {
         return "";
      }
   }

   public static String entityQuery(LivingEntity entity) {
      if (entity == null) {
         return "";
      }

      ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
      String registry = id == null ? "" : id.toString();
      return clean(registry + " " + entity.getName().getString());
   }

   private static boolean containsUsefulPart(String a, String b) {
      for (String part : b.split(" ")) {
         if (part.length() >= 4 && a.contains(part)) {
            return true;
         }
      }

      return false;
   }

   private static String mergeQuery(String oldQuery, String newQuery) {
      if (newQuery == null || newQuery.isBlank() || oldQuery.contains(newQuery)) {
         return oldQuery;
      }

      if (oldQuery.isBlank()) {
         return newQuery;
      }

      String merged = oldQuery + " " + newQuery;
      return merged.length() > 180 ? merged.substring(0, 180) : merged;
   }

   private static String clean(String value) {
      return value == null
         ? ""
         : value.toLowerCase(Locale.ROOT).replace('_', ' ').replace('-', ' ').replaceAll("[^a-z0-9: ]", " ").replaceAll("\\s+", " ").trim();
   }

   private record CloneTrace(String dimension, BlockPos pos, long gameTime) {
   }

   private static final class Discovery {
      private final String type;
      private String query;
      private final String dimension;
      private final BlockPos pos;
      private int score;
      private long gameTime;

      private Discovery(String type, String query, String dimension, BlockPos pos, int score, long gameTime) {
         this.type = type;
         this.query = query;
         this.dimension = dimension;
         this.pos = pos;
         this.score = score;
         this.gameTime = gameTime;
      }
   }

   private static final class Hive {
      private final List<ArsWeissCloneHiveMemory.Discovery> discoveries = new ArrayList<>();
      private final Map<UUID, ArsWeissCloneHiveMemory.CloneTrace> clones = new HashMap<>();
   }
}
