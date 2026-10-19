package dev.shadowako.trlagbegon.part;

import io.github.manasmods.tensura.entity.template.TensuraPartEntity;
import io.github.manasmods.tensura.world.subclass.IMultipartLevel;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public final class TensuraPartChunkIndex {
   private static final Map<Level, TensuraPartChunkIndex.LevelIndex> INDICES = new ConcurrentHashMap<>();

   private TensuraPartChunkIndex() {
   }

   public static void onPartAdd(Level level, TensuraPartEntity part) {
      if (part.isAlive()) {
         index(level).reinsert(part, true);
      }
   }

   public static void onPartRemove(Level level, TensuraPartEntity part) {
      TensuraPartChunkIndex.LevelIndex idx = INDICES.get(level);
      if (idx != null) {
         idx.removePart(part, true);
      }
   }

   public static void onPartMaybeMoved(Level level, TensuraPartEntity part) {
      if (!part.isAlive()) {
         onPartRemove(level, part);
      } else {
         index(level).reinsert(part, false);
      }
   }

   public static void clearLevel(Level level) {
      INDICES.remove(level);
   }

   public static int trackedPartCount(Level level) {
      TensuraPartChunkIndex.LevelIndex idx = INDICES.get(level);
      return idx == null ? 0 : idx.size();
   }

   public static void rebuildAllFromTensuraMap(Level level, IMultipartLevel from) {
      clearLevel(level);
      ObjectIterator var2 = from.tensura$getParts().values().iterator();

      while (var2.hasNext()) {
         TensuraPartEntity p = (TensuraPartEntity)var2.next();
         onPartAdd(level, p);
      }
   }

   public static List<TensuraPartEntity> queryOverlappingParts(Level level, AABB query) {
      TensuraPartChunkIndex.LevelIndex idx = INDICES.get(level);
      if (idx == null) {
         return List.of();
      }

      AABB aabb = query;
      int minX = Mth.floor(aabb.minX) >> 4;
      int maxX = Mth.floor(aabb.maxX) >> 4;
      int minZ = Mth.floor(aabb.minZ) >> 4;
      int maxZ = Mth.floor(aabb.maxZ) >> 4;
      IntSet added = new IntOpenHashSet(32);
      List<TensuraPartEntity> out = new ArrayList<>();

      for (TensuraPartEntity p : idx.unbucketed) {
         if (added.add(p.getId()) && p.getBoundingBox().intersects(aabb)) {
            out.add(p);
         }
      }

      for (int cx = minX; cx <= maxX; cx++) {
         for (int cz = minZ; cz <= maxZ; cz++) {
            long key = ChunkPos.asLong(cx, cz);
            List<TensuraPartEntity> bucket = (List<TensuraPartEntity>)idx.byChunk.get(key);
            if (bucket != null) {
               for (TensuraPartEntity p : bucket) {
                  if (added.add(p.getId()) && p.getBoundingBox().intersects(aabb)) {
                     out.add(p);
                  }
               }
            }
         }
      }

      return out;
   }

   private static TensuraPartChunkIndex.LevelIndex index(Level level) {
      return INDICES.computeIfAbsent(level, l -> new TensuraPartChunkIndex.LevelIndex());
   }

   private static LongSet chunkColumnKeysCoveredBy(AABB b) {
      int x0 = Mth.floor(b.minX) >> 4;
      int x1 = Mth.floor(b.maxX) >> 4;
      int z0 = Mth.floor(b.minZ) >> 4;
      int z1 = Mth.floor(b.maxZ) >> 4;
      LongSet keys = new LongOpenHashSet((x1 - x0 + 1) * (z1 - z0 + 1) + 1);

      for (int cx = x0; cx <= x1; cx++) {
         for (int cz = z0; cz <= z1; cz++) {
            keys.add(ChunkPos.asLong(cx, cz));
         }
      }

      return keys;
   }

   private static final class LevelIndex {
      private final Long2ObjectMap<List<TensuraPartEntity>> byChunk = new Long2ObjectOpenHashMap();
      private final Int2ObjectMap<LongSet> partChunkKeys = new Int2ObjectOpenHashMap();
      private final List<TensuraPartEntity> unbucketed = new ArrayList<>();

      int size() {
         return this.partChunkKeys.size() + this.unbucketed.size();
      }

      void removePart(TensuraPartEntity part, boolean evenIfGone) {
         int id = part.getId();
         this.unbucketed.removeIf(e -> e.getId() == id);
         LongSet keys = (LongSet)this.partChunkKeys.remove(id);
         if (keys != null) {
            LongIterator var5 = keys.iterator();

            while (var5.hasNext()) {
               long key = (Long)var5.next();
               List<TensuraPartEntity> list = (List<TensuraPartEntity>)this.byChunk.get(key);
               if (list != null) {
                  list.removeIf(e -> e.getId() == id);
                  if (list.isEmpty()) {
                     this.byChunk.remove(key);
                  }
               }
            }
         }
      }

      void reinsert(TensuraPartEntity part, boolean hardReset) {
         AABB b = part.getBoundingBox();
         LongSet newKeys = TensuraPartChunkIndex.chunkColumnKeysCoveredBy(b);
         if (!hardReset) {
            LongSet old = (LongSet)this.partChunkKeys.get(part.getId());
            if (old != null && old.equals(newKeys)) {
               return;
            }
         }

         this.removePart(part, true);
         if (newKeys.isEmpty()) {
            this.unbucketed.add(part);
         } else {
            LongIterator var8 = newKeys.iterator();

            while (var8.hasNext()) {
               long key = (Long)var8.next();
               ((List)this.byChunk.computeIfAbsent(key, k -> new ArrayList())).add(part);
            }

            this.partChunkKeys.put(part.getId(), newKeys);
         }
      }
   }
}
