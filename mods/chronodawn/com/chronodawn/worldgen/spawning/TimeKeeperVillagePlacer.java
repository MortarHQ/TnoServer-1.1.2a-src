package com.chronodawn.worldgen.spawning;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.data.TimeKeeperVillageData;
import com.chronodawn.entities.mobs.TimeKeeperEntity;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModDimensions;
import com.chronodawn.registry.ModEntities;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.LootTable;

public class TimeKeeperVillagePlacer {
   private static final ResourceLocation VILLAGE_TEMPLATE = CompatResourceLocation.create("chronodawn", "time_keeper_village");
   private static final ResourceKey<LootTable> VILLAGE_LOOT_TABLE = ResourceKey.create(
      Registries.LOOT_TABLE, CompatResourceLocation.create("chronodawn", "chests/time_keeper_village")
   );
   private static final int[][] DISTANCE_RANGES = new int[][]{{32, 64}, {64, 128}, {128, 256}};
   private static final int STRUCTURE_WIDTH = 11;
   private static final int STRUCTURE_DEPTH = 11;
   private static final int MAX_HEIGHT_VARIATION = 3;
   private static final int MAX_ATTEMPTS = 100;

   public static void onPlayerEnterChronoDawn(ServerPlayer player) {
      ServerLevel level = player.serverLevel();
      if (level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         TimeKeeperVillageData data = TimeKeeperVillageData.get(level);
         if (data.isPlaced()) {
            ChronoDawn.LOGGER.debug("Time Keeper Village already placed at {}", data.getPosition());
         } else {
            BlockPos playerEntryPos = player.blockPosition();
            BlockPos villagePos = findSuitablePosition(level, playerEntryPos);
            if (villagePos != null) {
               boolean success = placeVillage(level, villagePos);
               if (success) {
                  data.setPlaced(villagePos);
                  ChronoDawn.LOGGER.debug("Successfully placed Time Keeper Village at {} for player {}", villagePos, player.getName().getString());
               } else {
                  ChronoDawn.LOGGER.warn("Failed to place Time Keeper Village structure at {}", villagePos);
               }
            } else {
               ChronoDawn.LOGGER.warn("Could not find suitable position for Time Keeper Village near player entry point {}", playerEntryPos);
            }
         }
      }
   }

   private static BlockPos findSuitablePosition(ServerLevel level, BlockPos center) {
      for (int phase = 0; phase < DISTANCE_RANGES.length; phase++) {
         int minDistance = DISTANCE_RANGES[phase][0];
         int maxDistance = DISTANCE_RANGES[phase][1];
         ChronoDawn.LOGGER.debug("Searching for Time Keeper Village location (phase {}: {}-{} blocks)", new Object[]{phase + 1, minDistance, maxDistance});
         BlockPos result = searchInRange(level, center, minDistance, maxDistance);
         if (result != null) {
            return result;
         }
      }

      ChronoDawn.LOGGER.warn("No suitable position found after searching all distance ranges");
      return null;
   }

   private static BlockPos searchInRange(ServerLevel level, BlockPos center, int minDistance, int maxDistance) {
      for (int attempt = 0; attempt < 100; attempt++) {
         int range = maxDistance - minDistance;
         int dx = level.random.nextInt(range + 1) + minDistance;
         int dz = level.random.nextInt(range + 1) + minDistance;
         if (level.random.nextBoolean()) {
            dx = -dx;
         }

         if (level.random.nextBoolean()) {
            dz = -dz;
         }

         int x = center.getX() + dx;
         int z = center.getZ() + dz;
         level.getChunk(x >> 4, z >> 4);
         int y = level.getHeight(Types.MOTION_BLOCKING, x, z);
         if (y <= level.getMinBuildHeight()) {
            ChronoDawn.LOGGER.debug("Skipping position ({}, {}) - heightmap returned minimum height {}", new Object[]{x, z, y});
         } else {
            BlockPos candidate = new BlockPos(x, y, z);
            if (isTerrainFlat(level, candidate, 11, 11, 3) && isSurfaceSuitable(level, candidate)) {
               ChronoDawn.LOGGER.debug("Found suitable position for Time Keeper Village at {} (attempt {})", candidate, attempt + 1);
               return candidate;
            }
         }
      }

      return null;
   }

   private static boolean isTerrainFlat(ServerLevel level, BlockPos center, int width, int depth, int maxVariation) {
      int minY = Integer.MAX_VALUE;
      int maxY = Integer.MIN_VALUE;
      int halfWidth = width / 2;
      int halfDepth = depth / 2;

      for (int dx = -halfWidth; dx <= halfWidth; dx++) {
         for (int dz = -halfDepth; dz <= halfDepth; dz++) {
            int y = level.getHeight(Types.MOTION_BLOCKING, center.getX() + dx, center.getZ() + dz);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
         }
      }

      return maxY - minY <= maxVariation;
   }

   private static boolean isSurfaceSuitable(ServerLevel level, BlockPos pos) {
      BlockPos[] checkPositions = new BlockPos[]{pos, pos.offset(5, 0, 5), pos.offset(-5, 0, 5), pos.offset(5, 0, -5), pos.offset(-5, 0, -5)};

      for (BlockPos checkPos : checkPositions) {
         int y = level.getHeight(Types.MOTION_BLOCKING, checkPos.getX(), checkPos.getZ());
         BlockState surfaceBlock = level.getBlockState(new BlockPos(checkPos.getX(), y - 1, checkPos.getZ()));
         if (surfaceBlock.is(Blocks.WATER)) {
            return false;
         }

         if (surfaceBlock.is(Blocks.LAVA)) {
            return false;
         }

         if (surfaceBlock.is(Blocks.ICE) || surfaceBlock.is(Blocks.PACKED_ICE) || surfaceBlock.is(Blocks.BLUE_ICE)) {
            return false;
         }
      }

      return true;
   }

   private static boolean placeVillage(ServerLevel level, BlockPos pos) {
      StructureTemplateManager structureTemplateManager = level.getStructureManager();
      Optional<StructureTemplate> templateOptional = structureTemplateManager.get(VILLAGE_TEMPLATE);
      if (templateOptional.isEmpty()) {
         ChronoDawn.LOGGER.error("Failed to load Time Keeper Village template: {}", VILLAGE_TEMPLATE);
         ChronoDawn.LOGGER.error("Make sure the NBT file exists at: data/chronodawn/structure/time_keeper_village.nbt");
         return false;
      } else {
         StructureTemplate template = templateOptional.get();
         Vec3i templateSize = template.getSize();
         ChronoDawn.LOGGER.debug("Time Keeper Village template size: {}x{}x{}", new Object[]{templateSize.getX(), templateSize.getY(), templateSize.getZ()});
         int offsetX = templateSize.getX() / 2;
         int offsetZ = templateSize.getZ() / 2;
         BlockPos placementPos = pos.offset(-offsetX, 0, -offsetZ);
         fillFoundation(level, placementPos, templateSize.getX(), templateSize.getZ());
         StructurePlaceSettings settings = new StructurePlaceSettings().setIgnoreEntities(true);
         template.placeInWorld(level, placementPos, placementPos, settings, level.random, 2);
         ChronoDawn.LOGGER.debug("Placed Time Keeper Village at {} (template origin: {})", pos, placementPos);
         spawnTimeKeepers(level, pos);
         setChestLootTables(level, placementPos, templateSize);
         return true;
      }
   }

   private static void spawnTimeKeepers(ServerLevel level, BlockPos villageCenter) {
      BlockPos[] spawnPositions = new BlockPos[]{villageCenter.offset(-2, 1, 0), villageCenter.offset(2, 1, 0)};
      int spawnedCount = 0;

      for (BlockPos spawnPos : spawnPositions) {
         TimeKeeperEntity timeKeeper = (TimeKeeperEntity)((EntityType)ModEntities.TIME_KEEPER.get()).create(level);
         if (timeKeeper != null) {
            timeKeeper.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, level.random.nextFloat() * 360.0F, 0.0F);
            timeKeeper.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.STRUCTURE, null);
            timeKeeper.setPersistenceRequired();
            level.addFreshEntity(timeKeeper);
            spawnedCount++;
            ChronoDawn.LOGGER.debug("Spawned Time Keeper at {}", spawnPos);
         }
      }

      ChronoDawn.LOGGER.debug("Spawned {} Time Keepers at Time Keeper Village", spawnedCount);
   }

   private static void setChestLootTables(ServerLevel level, BlockPos placementPos, Vec3i templateSize) {
      int chestsFound = 0;

      for (int dx = 0; dx < templateSize.getX(); dx++) {
         for (int dy = 0; dy < templateSize.getY(); dy++) {
            for (int dz = 0; dz < templateSize.getZ(); dz++) {
               BlockPos checkPos = placementPos.offset(dx, dy, dz);
               BlockState blockState = level.getBlockState(checkPos);
               if (blockState.getBlock() instanceof ChestBlock && level.getBlockEntity(checkPos) instanceof ChestBlockEntity chestBlockEntity) {
                  chestBlockEntity.setLootTable(VILLAGE_LOOT_TABLE, level.random.nextLong());
                  chestsFound++;
                  ChronoDawn.LOGGER.debug("Set loot table for chest at {}", checkPos);
               }
            }
         }
      }

      if (chestsFound > 0) {
         ChronoDawn.LOGGER.debug("Set loot tables for {} chest(s) in Time Keeper Village", chestsFound);
      } else {
         ChronoDawn.LOGGER.warn("No chests found in Time Keeper Village structure");
      }
   }

   private static void fillFoundation(ServerLevel level, BlockPos placementPos, int width, int depth) {
      int filledBlocks = 0;
      int maxDepth = 10;

      for (int dx = 0; dx < width; dx++) {
         for (int dz = 0; dz < depth; dz++) {
            int x = placementPos.getX() + dx;
            int z = placementPos.getZ() + dz;
            int surfaceY = placementPos.getY();

            for (int dy = 1; dy <= maxDepth; dy++) {
               BlockPos fillPos = new BlockPos(x, surfaceY - dy, z);
               BlockState existingBlock = level.getBlockState(fillPos);
               if (existingBlock.isSolid() && !existingBlock.is(Blocks.WATER) && !existingBlock.is(Blocks.LAVA)) {
                  break;
               }

               level.setBlock(fillPos, ((Block)ModBlocks.TEMPORAL_DIRT.get()).defaultBlockState(), 2);
               filledBlocks++;
            }
         }
      }

      if (filledBlocks > 0) {
         ChronoDawn.LOGGER.debug("Filled {} foundation blocks below Time Keeper Village", filledBlocks);
      }
   }
}
