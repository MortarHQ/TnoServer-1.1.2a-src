package com.tensura_tno.world.spawn;

import com.mojang.logging.LogUtils;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.slf4j.Logger;

public final class SpawnRelocator {
   private static final Logger LOGGER = LogUtils.getLogger();
   static final String LOG_PREFIX = "[TensuraTNO][SpawnRelocator]";
   static final String PLAYER_TAG_NS = "tensura_tno";
   static final String PLAYER_KEY_RELOCATED = "spawn_relocated";

   private SpawnRelocator() {
   }

   static boolean isInBlacklist(ServerLevel level, BlockPos pos) {
      return isInBlacklist(p -> biomeIdAt(level, p), pos, BiomeBlacklistConfig.getBlacklist());
   }

   static boolean isInBlacklist(BiomeLookup biomeLookup, BlockPos pos, Set<ResourceLocation> blacklist) {
      ResourceLocation id = biomeLookup.biomeAt(pos);
      return id != null && blacklist.contains(id);
   }

   static boolean hasRespawnPosition(ServerPlayer player) {
      return player.getRespawnPosition() != null;
   }

   static ResourceLocation biomeIdAt(ServerLevel level, BlockPos pos) {
      Holder<Biome> holder = level.getBiome(pos);
      return holder.unwrapKey().<ResourceLocation>map(ResourceKey::location).orElse(null);
   }

   static ResourceLocation biomeIdAt(BiomeLookup biomeLookup, BlockPos pos) {
      return biomeLookup.biomeAt(pos);
   }

   public static void onServerStarted(ServerStartedEvent event) {
      ServerLevel overworld = event.getServer().overworld();
      relocateWorldSpawnIfNeeded(overworld);
   }

   static void relocateWorldSpawnIfNeeded(ServerLevel overworld) {
      if (BiomeBlacklistConfig.isEnabled()) {
         if (!ModList.get().isLoaded("tensura")) {
            LOGGER.debug("{} skip: tensura mod not loaded", "[TensuraTNO][SpawnRelocator]");
         } else if (overworld.dimension() != Level.OVERWORLD) {
            LOGGER.debug("{} skip: non-overworld dimension {}", "[TensuraTNO][SpawnRelocator]", overworld.dimension().location());
         } else {
            SpawnRelocatedFlag flag;
            try {
               flag = SpawnRelocatedFlag.get(overworld);
            } catch (RuntimeException e) {
               LOGGER.error("{} failed to load relocation flag, falling back to original spawn", "[TensuraTNO][SpawnRelocator]", e);
               return;
            }

            relocateWorldSpawnCore(adaptToWorldContext(overworld, flag));
         }
      }
   }

   static void relocateWorldSpawnCore(WorldSpawnContext ctx) {
      if (ctx.isEnabled()) {
         if (!ctx.isTensuraLoaded()) {
            LOGGER.debug("{} skip: tensura mod not loaded", "[TensuraTNO][SpawnRelocator]");
         } else if (!ctx.isOverworld()) {
            LOGGER.debug("{} skip: non-overworld dimension {}", "[TensuraTNO][SpawnRelocator]", ctx.dimensionId());
         } else if (ctx.isFlagDone()) {
            LOGGER.debug("{} skip: already relocated", "[TensuraTNO][SpawnRelocator]");
         } else {
            try {
               BlockPos originalPos = ctx.getSharedSpawnPos();
               ResourceLocation originalBiome = ctx.biomeIdAt(originalPos);
               Set<ResourceLocation> blacklist = ctx.blacklist();
               if (originalBiome == null || !blacklist.contains(originalBiome)) {
                  ctx.markFlagDone();
                  return;
               }

               Optional<BlockPos> safe = ctx.findSafeSpawn(originalPos);
               if (safe.isPresent()) {
                  BlockPos safePos = safe.get();
                  ctx.setDefaultSpawnPos(safePos, 0.0F);
                  ResourceLocation newBiome = ctx.biomeIdAt(safePos);
                  LOGGER.info(
                     "{} relocated world spawn: {} (biome={}) -> {} (biome={})",
                     new Object[]{"[TensuraTNO][SpawnRelocator]", formatPos(originalPos), originalBiome, formatPos(safePos), newBiome}
                  );
                  ctx.markFlagDone();
               } else {
                  LOGGER.warn(
                     "{} no safe spawn found within radius={} from {}; will retry on next server start",
                     new Object[]{"[TensuraTNO][SpawnRelocator]", ctx.searchRadius(), formatPos(originalPos)}
                  );
               }
            } catch (RuntimeException e) {
               LOGGER.error("{} safe-spawn search threw, will retry on next server start", "[TensuraTNO][SpawnRelocator]", e);
            }
         }
      }
   }

   private static WorldSpawnContext adaptToWorldContext(final ServerLevel overworld, final SpawnRelocatedFlag flag) {
      return new WorldSpawnContext() {
         @Override
         public boolean isEnabled() {
            return BiomeBlacklistConfig.isEnabled();
         }

         @Override
         public boolean isTensuraLoaded() {
            return ModList.get().isLoaded("tensura");
         }

         @Override
         public boolean isOverworld() {
            return overworld.dimension() == Level.OVERWORLD;
         }

         @Override
         public ResourceLocation dimensionId() {
            return overworld.dimension().location();
         }

         @Override
         public BlockPos getSharedSpawnPos() {
            return overworld.getSharedSpawnPos();
         }

         @Override
         public void setDefaultSpawnPos(BlockPos pos, float angle) {
            overworld.setDefaultSpawnPos(pos, angle);
         }

         @Override
         public ResourceLocation biomeIdAt(BlockPos pos) {
            return SpawnRelocator.biomeIdAt(overworld, pos);
         }

         @Override
         public Set<ResourceLocation> blacklist() {
            return BiomeBlacklistConfig.getBlacklist();
         }

         @Override
         public boolean isFlagDone() {
            return flag.isDone();
         }

         @Override
         public void markFlagDone() {
            flag.markDone();
         }

         @Override
         public Optional<BlockPos> findSafeSpawn(BlockPos origin) {
            return SafeSpawnFinder.findSafeSpawnFast(
               overworld, origin, BiomeBlacklistConfig.getSearchRadius(), BiomeBlacklistConfig.getSearchStep(), BiomeBlacklistConfig.getBlacklist()
            );
         }

         @Override
         public int searchRadius() {
            return BiomeBlacklistConfig.getSearchRadius();
         }
      };
   }

   private static String formatPos(BlockPos pos) {
      return "(" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")";
   }

   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         relocatePlayerIfNeeded(serverPlayer);
      }
   }

   static void onPlayerRespawn(PlayerRespawnEvent event) {
      if (!event.isEndConquered()) {
         if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            clearPlayerFlag(serverPlayer);
            relocatePlayerIfNeeded(serverPlayer);
         }
      }
   }

   private static void clearPlayerFlag(ServerPlayer player) {
      CompoundTag root = player.getPersistentData();
      if (root.contains("tensura_tno", 10)) {
         CompoundTag tno = root.getCompound("tensura_tno");
         tno.remove("spawn_relocated");
         root.put("tensura_tno", tno);
      }
   }

   static void relocatePlayerIfNeeded(ServerPlayer player) {
      if (BiomeBlacklistConfig.isEnabled()) {
         if (!ModList.get().isLoaded("tensura")) {
            LOGGER.debug("{} skip: tensura mod not loaded", "[TensuraTNO][SpawnRelocator]");
         } else {
            relocatePlayerCore(adaptToPlayerContext(player));
         }
      }
   }

   static void relocatePlayerCore(PlayerSpawnContext ctx) {
      if (ctx.isEnabled()) {
         if (!ctx.isTensuraLoaded()) {
            LOGGER.debug("{} skip: tensura mod not loaded", "[TensuraTNO][SpawnRelocator]");
         } else {
            String playerName = ctx.playerName();
            if (ctx.isPersistentFlagSet()) {
               LOGGER.debug("{} skip: player {} already relocated", "[TensuraTNO][SpawnRelocator]", playerName);
            } else {
               try {
                  if (!ctx.isOverworld()) {
                     LOGGER.debug(
                        "{} skip: player {} in non-overworld dimension {}", new Object[]{"[TensuraTNO][SpawnRelocator]", playerName, ctx.dimensionId()}
                     );
                     ctx.setPersistentFlag();
                     return;
                  }

                  if (ctx.hasRespawnPosition()) {
                     LOGGER.debug("{} skip: player {} has respawn position", "[TensuraTNO][SpawnRelocator]", playerName);
                     ctx.setPersistentFlag();
                     return;
                  }

                  BlockPos originalPos = ctx.blockPosition();
                  ResourceLocation originalBiome = ctx.biomeIdAt(originalPos);
                  Set<ResourceLocation> blacklist = ctx.blacklist();
                  if (originalBiome == null || !blacklist.contains(originalBiome)) {
                     ctx.setPersistentFlag();
                     return;
                  }

                  Optional<BlockPos> safe = ctx.findSafeSpawn(originalPos);
                  if (safe.isPresent()) {
                     BlockPos safePos = safe.get();
                     ctx.teleportTo(safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5);
                     ResourceLocation newBiome = ctx.biomeIdAt(safePos);
                     LOGGER.info(
                        "{} relocated player {}: {} (biome={}) -> {} (biome={})",
                        new Object[]{"[TensuraTNO][SpawnRelocator]", playerName, formatPos(originalPos), originalBiome, formatPos(safePos), newBiome}
                     );
                     ctx.setPersistentFlag();
                  } else {
                     LOGGER.warn(
                        "{} no safe spawn found within radius={} from {} for player {}; will retry on next event",
                        new Object[]{"[TensuraTNO][SpawnRelocator]", ctx.searchRadius(), formatPos(originalPos), playerName}
                     );
                  }
               } catch (RuntimeException e) {
                  LOGGER.error("{} player relocation threw for {}, will retry on next event", new Object[]{"[TensuraTNO][SpawnRelocator]", playerName, e});
               }
            }
         }
      }
   }

   private static PlayerSpawnContext adaptToPlayerContext(final ServerPlayer player) {
      final ServerLevel level = player.serverLevel();
      return new PlayerSpawnContext() {
         @Override
         public boolean isEnabled() {
            return BiomeBlacklistConfig.isEnabled();
         }

         @Override
         public boolean isTensuraLoaded() {
            return ModList.get().isLoaded("tensura");
         }

         @Override
         public boolean isOverworld() {
            return level.dimension() == Level.OVERWORLD;
         }

         @Override
         public ResourceLocation dimensionId() {
            return level.dimension().location();
         }

         @Override
         public boolean isPersistentFlagSet() {
            CompoundTag root = player.getPersistentData();
            return root.contains("tensura_tno", 10) && root.getCompound("tensura_tno").getBoolean("spawn_relocated");
         }

         @Override
         public void setPersistentFlag() {
            SpawnRelocator.markPlayerRelocated(player);
         }

         @Override
         public boolean hasRespawnPosition() {
            return SpawnRelocator.hasRespawnPosition(player);
         }

         @Override
         public BlockPos blockPosition() {
            return player.blockPosition();
         }

         @Override
         public void teleportTo(double x, double y, double z) {
            player.teleportTo(x, y, z);
         }

         @Override
         public ResourceLocation biomeIdAt(BlockPos pos) {
            return SpawnRelocator.biomeIdAt(level, pos);
         }

         @Override
         public Set<ResourceLocation> blacklist() {
            return BiomeBlacklistConfig.getBlacklist();
         }

         @Override
         public Optional<BlockPos> findSafeSpawn(BlockPos origin) {
            return SafeSpawnFinder.findSafeSpawnFast(
               level, origin, BiomeBlacklistConfig.getSearchRadius(), BiomeBlacklistConfig.getSearchStep(), BiomeBlacklistConfig.getBlacklist()
            );
         }

         @Override
         public int searchRadius() {
            return BiomeBlacklistConfig.getSearchRadius();
         }

         @Override
         public String playerName() {
            return player.getGameProfile().getName();
         }
      };
   }

   private static void markPlayerRelocated(ServerPlayer player) {
      CompoundTag root = player.getPersistentData();
      CompoundTag tno = root.contains("tensura_tno", 10) ? root.getCompound("tensura_tno") : new CompoundTag();
      tno.putBoolean("spawn_relocated", true);
      root.put("tensura_tno", tno);
   }
}
