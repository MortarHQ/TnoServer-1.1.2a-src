package com.cerbon.bosses_of_mass_destruction.neoforge.attachment.saved_data;

import com.cerbon.bosses_of_mass_destruction.capability.ChunkBlockCache;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import org.jetbrains.annotations.NotNull;

public class LevelChunkBlockCache extends SavedData {
   private static final Factory<LevelChunkBlockCache> FACTORY = new Factory(LevelChunkBlockCache::new, LevelChunkBlockCache::new);
   public static LevelChunkBlockCache INSTANCE;
   private static final String DATA_KEY = "bomd_level_chunk_block_cache";
   private ChunkBlockCache chunkBlockCache;

   public LevelChunkBlockCache() {
      this(new CompoundTag(), new Provider() {
         @NotNull
         public Stream<ResourceKey<? extends Registry<?>>> listRegistries() {
            return Stream.empty();
         }

         @NotNull
         public <T> Optional<RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> registryKey) {
            return Optional.empty();
         }
      });
   }

   public LevelChunkBlockCache(CompoundTag tag, @NotNull Provider registries) {
   }

   @NotNull
   public CompoundTag save(@NotNull CompoundTag tag, @NotNull Provider registries) {
      return tag;
   }

   public static ChunkBlockCache get(Level level) {
      LevelChunkBlockCache levelChunkBlockCache = getSavedData(level);
      if (levelChunkBlockCache.chunkBlockCache == null) {
         levelChunkBlockCache.chunkBlockCache = new ChunkBlockCache();
      }

      return levelChunkBlockCache.chunkBlockCache;
   }

   private static LevelChunkBlockCache getSavedData(Level level) {
      return level.isClientSide
         ? INSTANCE
         : (LevelChunkBlockCache)((ServerLevel)level).getDataStorage().computeIfAbsent(FACTORY, "bomd_level_chunk_block_cache");
   }
}
