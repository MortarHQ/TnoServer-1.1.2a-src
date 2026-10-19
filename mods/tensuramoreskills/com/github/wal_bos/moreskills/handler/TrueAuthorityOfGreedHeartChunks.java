package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;

public final class TrueAuthorityOfGreedHeartChunks {
   private static final Map<String, ChunkPos> OWNER_CHUNKS = new HashMap<>();
   private static final Map<String, Integer> CHUNK_COUNTS = new HashMap<>();

   private TrueAuthorityOfGreedHeartChunks() {
   }

   public static void holdHeart(LivingEntity owner, LivingEntity heart, CompoundTag tag) {
      if ((Boolean)MoreSkillsConfigs.GRAND.trueAuthorityOfGreed.chunkLoadHeart.get()) {
         if (heart.level() instanceof ServerLevel level) {
            String var7 = ownerKey(owner);
            ChunkPos next = heart.chunkPosition();
            ChunkPos old = OWNER_CHUNKS.get(var7);
            if (!Objects.equals(old, next)) {
               if (old != null) {
                  release(level, old);
               }

               add(level, next);
               OWNER_CHUNKS.put(var7, next);
               tag.putInt("LittleKingChunkX", next.x);
               tag.putInt("LittleKingChunkZ", next.z);
            }
         }
      }
   }

   public static void releaseHeart(LivingEntity owner, CompoundTag tag) {
      String ownerKey = ownerKey(owner);
      ChunkPos old = OWNER_CHUNKS.remove(ownerKey);
      if (old != null && owner.level() instanceof ServerLevel level) {
         release(level, old);
      } else {
         if (tag.contains("LittleKingChunkX") && tag.contains("LittleKingChunkZ") && owner.level() instanceof ServerLevel level) {
            release(level, new ChunkPos(tag.getInt("LittleKingChunkX"), tag.getInt("LittleKingChunkZ")));
         }
      }
   }

   private static void add(ServerLevel level, ChunkPos pos) {
      String key = chunkKey(level, pos);
      int count = CHUNK_COUNTS.getOrDefault(key, 0);
      if (count <= 0) {
         level.setChunkForced(pos.x, pos.z, true);
      }

      CHUNK_COUNTS.put(key, count + 1);
   }

   private static void release(ServerLevel level, ChunkPos pos) {
      String key = chunkKey(level, pos);
      int count = CHUNK_COUNTS.getOrDefault(key, 0) - 1;
      if (count <= 0) {
         CHUNK_COUNTS.remove(key);
         level.setChunkForced(pos.x, pos.z, false);
      } else {
         CHUNK_COUNTS.put(key, count);
      }
   }

   private static String ownerKey(LivingEntity owner) {
      return owner.level().dimension().location() + ":" + owner.getStringUUID();
   }

   private static String chunkKey(ServerLevel level, ChunkPos pos) {
      return level.dimension().location() + ":" + pos.x + ":" + pos.z;
   }
}
