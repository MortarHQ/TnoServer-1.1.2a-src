package mcjty.incontrol.rules.support;

import java.util.HashMap;
import java.util.Map;
import mcjty.incontrol.compat.CustomNPCSupport;
import mcjty.incontrol.mob.CNPCMob;
import mcjty.incontrol.setup.Config;
import mcjty.incontrol.setup.ModSetup;
import mcjty.incontrol.tools.varia.Tools;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class RuleCache {
   private final Map<ResourceKey<Level>, RuleCache.CachePerWorld> caches = new HashMap<>();

   public void performCount(LevelAccessor world) {
      ResourceKey<Level> key = Tools.getDimensionKey(world);
      RuleCache.CachePerWorld cache = this.caches.computeIfAbsent(key, k -> new RuleCache.CachePerWorld());
      cache.count(world);
   }

   public void addMob(LevelAccessor world, Entity entity) {
      ResourceKey<Level> key = Tools.getDimensionKey(world);
      RuleCache.CachePerWorld cache = this.caches.computeIfAbsent(key, k -> new RuleCache.CachePerWorld());
      cache.addCountedMob(entity);
   }

   public void removeMob(LevelAccessor world, Entity entity) {
      ResourceKey<Level> key = Tools.getDimensionKey(world);
      RuleCache.CachePerWorld cache = this.caches.get(key);
      if (cache != null && !cache.removeCountedMob(entity)) {
         cache.setDirtyCounter(0);
      }
   }

   public int getValidSpawnChunks(LevelAccessor world) {
      RuleCache.CachePerWorld cache = this.getOrCreateCache(world);
      return cache.getValidSpawnChunks();
   }

   public int getValidPlayers(LevelAccessor world) {
      RuleCache.CachePerWorld cache = this.getOrCreateCache(world);
      return cache.getValidPlayers();
   }

   public int getCountAll(LevelAccessor world) {
      RuleCache.CachePerWorld cache = this.getOrCreateCache(world);
      return cache.getCountAll();
   }

   public int getCountPassive(LevelAccessor world) {
      RuleCache.CachePerWorld cache = this.getOrCreateCache(world);
      return cache.getCountPassive();
   }

   public int getCountHostile(LevelAccessor world) {
      RuleCache.CachePerWorld cache = this.getOrCreateCache(world);
      return cache.getCountHostile();
   }

   public int getCountNeutral(LevelAccessor world) {
      RuleCache.CachePerWorld cache = this.getOrCreateCache(world);
      return cache.getCountNeutral();
   }

   public int getCount(LevelAccessor world, EntityType entityType) {
      RuleCache.CachePerWorld cache = this.getOrCreateCache(world);
      return cache.getCount(entityType);
   }

   public int getNpcCount(LevelAccessor world, Entity entity) {
      RuleCache.CachePerWorld cache = this.getOrCreateCache(world);
      return cache.getNpcCount(entity);
   }

   public int getCountPerMod(LevelAccessor world, String mod) {
      RuleCache.CachePerWorld cache = this.getOrCreateCache(world);
      RuleCache.CountPerMod countPerMod = cache.getCountPerMod(mod);
      return countPerMod == null ? 0 : countPerMod.total;
   }

   public int getCountPerModHostile(LevelAccessor world, String mod) {
      RuleCache.CachePerWorld cache = this.getOrCreateCache(world);
      RuleCache.CountPerMod countPerMod = cache.getCountPerMod(mod);
      return countPerMod == null ? 0 : countPerMod.hostile;
   }

   public int getCountPerModPassive(LevelAccessor world, String mod) {
      RuleCache.CachePerWorld cache = this.getOrCreateCache(world);
      RuleCache.CountPerMod countPerMod = cache.getCountPerMod(mod);
      return countPerMod == null ? 0 : countPerMod.passive;
   }

   public int getCountPerModAll(LevelAccessor world, String mod) {
      RuleCache.CachePerWorld cache = this.getOrCreateCache(world);
      RuleCache.CountPerMod countPerMod = cache.getCountPerMod(mod);
      return countPerMod == null ? 0 : countPerMod.total;
   }

   private RuleCache.CachePerWorld getOrCreateCache(LevelAccessor world) {
      ResourceKey<Level> key = Tools.getDimensionKey(world);
      RuleCache.CachePerWorld cache = this.caches.get(key);
      if (cache == null) {
         cache = new RuleCache.CachePerWorld();
         this.caches.put(key, cache);
      }

      return cache;
   }

   private static class CachePerWorld {
      private final Map<EntityType, Integer> cachedCounters = new HashMap<>();
      private final Map<CNPCMob, Integer> cachedNpcCounters = new HashMap<>();
      private final Map<String, RuleCache.CountPerMod> countPerMod = new HashMap<>();
      private int countPassive = -1;
      private int countHostile = -1;
      private int countNeutral = -1;
      private int validSpawnChunks = -1;
      private int validPlayers = -1;
      private int dirtyCounter = 0;

      public int getValidSpawnChunks() {
         return this.validSpawnChunks;
      }

      public int getValidPlayers() {
         return this.validPlayers;
      }

      public int getCountAll() {
         return this.countHostile + this.countPassive + this.countNeutral;
      }

      public int getCountPassive() {
         return this.countPassive;
      }

      public int getCountHostile() {
         return this.countHostile;
      }

      public int getCountNeutral() {
         return this.countNeutral;
      }

      public int getDirtyCounter() {
         return this.dirtyCounter;
      }

      public void setDirtyCounter(int dirtyCounter) {
         this.dirtyCounter = dirtyCounter;
      }

      private int countValidPlayers(LevelAccessor world) {
         int cnt = 0;

         for (Player entityplayer : world.players()) {
            if (!entityplayer.isSpectator()) {
               cnt++;
            }
         }

         return cnt;
      }

      private void count(LevelAccessor world) {
         this.dirtyCounter--;
         if (this.dirtyCounter <= 0) {
            this.dirtyCounter = (Integer)Config.CACHE_RETENTION_TICKS.get();
            ServerLevel sw1 = Tools.getServerWorld(world);
            this.validSpawnChunks = sw1.getChunkSource().chunkMap.size();
            this.validPlayers = this.countValidPlayers(world);
            this.cachedCounters.clear();
            this.countPerMod.clear();
            this.cachedNpcCounters.clear();
            this.countPassive = 0;
            this.countHostile = 0;
            this.countNeutral = 0;
            ServerLevel sw = Tools.getServerWorld(world);
            sw.getEntities().getAll().forEach(this::addCountedMob);
         }
      }

      public void addCountedMob(Entity entity) {
         if (entity instanceof Mob) {
            int cnt = this.cachedCounters.getOrDefault(entity.getType(), 0) + 1;
            this.cachedCounters.put(entity.getType(), cnt);
            String mod = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getNamespace();
            RuleCache.CountPerMod count = this.countPerMod.computeIfAbsent(mod, s -> new RuleCache.CountPerMod());
            count.total++;
            if (entity instanceof Enemy) {
               count.hostile++;
               this.countHostile++;
            } else if (entity instanceof Animal) {
               count.passive++;
               this.countPassive++;
            } else {
               count.neutral++;
               this.countNeutral++;
            }

            if (ModSetup.customnpcs
               && CustomNPCSupport.hasNPCInterface(entity)
               && entity.getPersistentData().contains("InControlNatSpawnName")
               && entity.getPersistentData().contains("InControlNatSpawnTab")) {
               CNPCMob mob = new CNPCMob(
                  entity.getPersistentData().getInt("InControlNatSpawnTab"), entity.getPersistentData().getString("InControlNatSpawnName")
               );
               cnt = this.cachedNpcCounters.getOrDefault(mob, 0) + 1;
               this.cachedNpcCounters.put(mob, cnt);
            }
         }
      }

      public boolean removeCountedMob(Entity entity) {
         if (entity instanceof Mob) {
            int cnt = this.cachedCounters.getOrDefault(entity.getType(), 0);
            if (cnt <= 0) {
               return false;
            }

            this.cachedCounters.put(entity.getType(), cnt - 1);
            String mod = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getNamespace();
            RuleCache.CountPerMod count = this.countPerMod.get(mod);
            if (count != null) {
               count.total--;
               if (entity instanceof Enemy) {
                  if (count.hostile <= 0 || this.countHostile <= 0) {
                     return false;
                  }

                  count.hostile--;
                  this.countHostile--;
               } else if (entity instanceof Animal) {
                  if (count.passive <= 0 || this.countPassive <= 0) {
                     return false;
                  }

                  count.passive--;
                  this.countPassive--;
               } else {
                  if (count.neutral <= 0 || this.countNeutral <= 0) {
                     return false;
                  }

                  count.neutral--;
                  this.countNeutral--;
               }
            }
         }

         return true;
      }

      public int getCount(EntityType entityType) {
         return this.cachedCounters.getOrDefault(entityType, 0);
      }

      public RuleCache.CountPerMod getCountPerMod(String mod) {
         return this.countPerMod.get(mod);
      }

      public int getNpcCount(Entity entity) {
         if (CustomNPCSupport.hasNPCInterface(entity)
            && entity.getPersistentData().contains("InControlNatSpawnName")
            && entity.getPersistentData().contains("InControlNatSpawnTab")) {
            CNPCMob mob = new CNPCMob(entity.getPersistentData().getInt("InControlNatSpawnTab"), entity.getPersistentData().getString("InControlNatSpawnName"));
            return this.cachedNpcCounters.getOrDefault(mob, 0);
         } else {
            return this.getCount(entity.getType());
         }
      }
   }

   private static class CountPerMod {
      private int hostile;
      private int passive;
      private int neutral;
      private int total;
   }
}
