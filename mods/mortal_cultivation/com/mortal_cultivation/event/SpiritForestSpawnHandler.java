package com.mortal_cultivation.event;

import com.mortal_cultivation.MortalCultivation;
import com.mortal_cultivation.registry.WorldgenRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.storage.ServerLevelData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.level.LevelEvent.CreateSpawnPosition;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public final class SpiritForestSpawnHandler {
   private static final int VANILLA_SEARCH_RADIUS = 5;
   private static final int EXTENDED_SEARCH_RADIUS = 48;

   private SpiritForestSpawnHandler() {
   }

   @SubscribeEvent
   public static void onCreateSpawnPosition(CreateSpawnPosition event) {
      if (event.getLevel() instanceof ServerLevel level && level.dimension() == Level.OVERWORLD) {
         ServerLevelData settings = event.getSettings();
         ServerChunkCache chunkSource = level.getChunkSource();
         ChunkPos climateSpawn = new ChunkPos(chunkSource.randomState().sampler().findSpawnPosition());
         BlockPos spawn = findNonSpiritForestSpawn(level, climateSpawn);
         if (spawn == null) {
            MortalCultivation.LOGGER.warn("Could not find a non-spirit-forest world spawn near {}", climateSpawn);
         } else {
            settings.setSpawn(spawn, 0.0F);
            placeBonusChestIfRequested(level, settings, chunkSource);
            event.setCanceled(true);
         }
      }
   }

   private static BlockPos findNonSpiritForestSpawn(ServerLevel level, ChunkPos origin) {
      for (int radius = 0; radius <= 48; radius++) {
         if (radius <= 5) {
            BlockPos candidate = findInRing(level, origin, radius, 1);
            if (candidate != null) {
               return candidate;
            }
         } else {
            int stride = Math.max(1, radius / 8);
            BlockPos candidate = findInRing(level, origin, radius, stride);
            if (candidate != null) {
               return candidate;
            }
         }
      }

      return null;
   }

   private static BlockPos findInRing(ServerLevel level, ChunkPos origin, int radius, int stride) {
      if (radius == 0) {
         return findInChunk(level, origin);
      }

      int offset = -radius;

      while (offset <= radius) {
         BlockPos candidate = findInChunk(level, new ChunkPos(origin.x + offset, origin.z - radius));
         if (candidate != null) {
            return candidate;
         }

         candidate = findInChunk(level, new ChunkPos(origin.x + offset, origin.z + radius));
         if (candidate != null) {
            return candidate;
         }

         candidate = findInChunk(level, new ChunkPos(origin.x - radius, origin.z + offset));
         if (candidate != null) {
            return candidate;
         }

         candidate = findInChunk(level, new ChunkPos(origin.x + radius, origin.z + offset));
         if (candidate != null) {
            return candidate;
         }

         offset += stride;
      }

      return null;
   }

   private static BlockPos findInChunk(ServerLevel level, ChunkPos chunk) {
      BlockPos sample = new BlockPos(chunk.getMiddleBlockX(), level.getSeaLevel(), chunk.getMiddleBlockZ());
      if (level.getBiome(sample).is(WorldgenRegistry.SPIRIT_FORESTS)) {
         return null;
      }

      BlockPos spawn = PlayerRespawnLogic.getSpawnPosInChunk(level, chunk);
      return spawn != null && !level.getBiome(spawn).is(WorldgenRegistry.SPIRIT_FORESTS) ? spawn : null;
   }

   private static void placeBonusChestIfRequested(ServerLevel level, ServerLevelData settings, ServerChunkCache chunkSource) {
      if (level.getServer().getWorldData().worldGenOptions().generateBonusChest()) {
         level.registryAccess()
            .registry(Registries.CONFIGURED_FEATURE)
            .flatMap(registry -> registry.getHolder(MiscOverworldFeatures.BONUS_CHEST))
            .ifPresent(holder -> ((ConfiguredFeature)holder.value()).place(level, chunkSource.getGenerator(), level.getRandom(), settings.getSpawnPos()));
      }
   }
}
