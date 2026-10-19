package noobanidus.mods.lootr.common.block.entity;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.DataToCopy;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.LootrTags;
import noobanidus.mods.lootr.common.api.PlatformAPI;
import noobanidus.mods.lootr.common.api.adapter.ILootrDataAdapter;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.chunk.LoadedChunks;
import org.jetbrains.annotations.Nullable;

public final class BlockEntityTicker {
   private static final Map<ResourceKey<Level>, BlockEntityTicker> TICKERS = new Object2ObjectOpenHashMap();
   private final ResourceKey<Level> levelKey;
   private final Map<ChunkPos, BlockEntityTicker.Entry> blockEntityEntries = new Object2ObjectOpenHashMap();
   private final Map<ChunkPos, BlockEntityTicker.Entry> pendingEntries = new Object2ObjectOpenHashMap();

   private BlockEntityTicker(ResourceKey<Level> levelKey) {
      this.levelKey = levelKey;
   }

   public static void addEntity(BlockEntity entity, Level level, ChunkPos chunkPos) {
      if (!LootrAPI.isDisabled()) {
         ResourceKey<Level> dimension = getServerDimensionIfValid(level);
         if (dimension != null) {
            BlockEntityTicker ticker;
            synchronized (TICKERS) {
               ticker = TICKERS.computeIfAbsent(dimension, BlockEntityTicker::new);
            }

            ticker.addEntity(level, entity, chunkPos);
         }
      }
   }

   private void addEntity(Level level, BlockEntity entity, ChunkPos chunkPos) {
      if (LootrAPI.isWorldBorderSafe(level, chunkPos)) {
         if (isValidEntity(entity)) {
            synchronized (this.pendingEntries) {
               BlockEntityTicker.Entry previousEntry = this.pendingEntries.get(chunkPos);
               if (previousEntry != null) {
                  previousEntry.entityPositions.add(entity.getBlockPos());
               } else {
                  HashSet<BlockPos> entityPositions = new HashSet<>();
                  entityPositions.add(entity.getBlockPos());
                  BlockEntityTicker.Entry entry = new BlockEntityTicker.Entry(chunkPos, entityPositions);
                  this.pendingEntries.put(chunkPos, entry);
               }
            }
         }
      }
   }

   private static boolean isValidEntity(BlockEntity entity) {
      if (LootrTags.BlockEntity.isTagged(entity, LootrTags.BlockEntity.CONVERT_BLACKLIST)) {
         return false;
      } else if (!(entity instanceof ILootrBlockEntity) && !(LootrAPI.resolveBlockEntity(entity) instanceof ILootrBlockEntity)) {
         ILootrDataAdapter<BlockEntity> adapter = LootrAPI.getAdapter(entity);
         return adapter != null;
      } else {
         return false;
      }
   }

   public static boolean isValidEntityFull(BlockEntity entity) {
      if (LootrAPI.isDisabled()) {
         return false;
      }

      if (LootrTags.BlockEntity.isTagged(entity, LootrTags.BlockEntity.CONVERT_BLACKLIST)) {
         return false;
      }

      Level level = entity.getLevel();
      BlockPos pos = entity.getBlockPos();
      if (level == null) {
         return false;
      }

      if (!level.isClientSide() && level.getServer() != null && LootrAPI.getServer() != null && level instanceof ServerLevel serverLevel) {
         if (!(entity instanceof ILootrBlockEntity) && !(LootrAPI.resolveBlockEntity(entity) instanceof ILootrBlockEntity)) {
            MinecraftServer server = level.getServer();
            if (!server.isSameThread()) {
               LootrAPI.LOG.error("Called isValidEntityFull on a non-server thread for {} in {}", entity, level.dimension());
               return false;
            }

            if (LootrAPI.isDimensionBlocked(serverLevel.dimension())) {
               return false;
            }

            if (!LootrAPI.isWorldBorderSafe(level, pos)) {
               return false;
            }

            if (LootrAPI.replacementBlockState(entity.getBlockState()) == null) {
               return false;
            }

            ILootrDataAdapter<BlockEntity> adapter = LootrAPI.getAdapter(entity);
            if (adapter == null) {
               return false;
            }

            ResourceKey<LootTable> lootTable = adapter.getLootTable(entity);
            if (lootTable == null) {
               return false;
            }

            if (LootrAPI.isLootTableBlacklisted(lootTable)) {
               return false;
            }

            BlockEntityTicker.Entry testEntry = new BlockEntityTicker.Entry(new ChunkPos(pos), Set.of(pos));
            Set<ChunkPos> loadedChunks = LoadedChunks.getLoadedChunks(level.dimension());
            return testEntry.getChunkLoadStatus(serverLevel, loadedChunks) == BlockEntityTicker.ChunkLoadStatus.COMPLETE;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static void onServerTick(MinecraftServer server) {
      if (!LootrAPI.isDisabled()) {
         for (BlockEntityTicker ticker : TICKERS.values()) {
            ServerLevel level = server.getLevel(ticker.levelKey);
            if (level != null) {
               ticker.onServerLevelTick(level);
            }
         }
      }
   }

   private void onServerLevelTick(ServerLevel level) {
      Set<ChunkPos> loadedChunks = LoadedChunks.getLoadedChunks(level.dimension());
      Iterator<BlockEntityTicker.Entry> iterator = this.blockEntityEntries.values().iterator();

      while (iterator.hasNext()) {
         BlockEntityTicker.Entry entry = iterator.next();
         switch (entry.getChunkLoadStatus(level, loadedChunks)) {
            case UNLOADED:
               iterator.remove();
            case SURROUNDING_CHUNKS_NOT_LOADED:
            case NOT_FULLY_LOADED:
            default:
               break;
            case COMPLETE:
               replaceEntitiesInChunk(level, entry);
               iterator.remove();
         }
      }

      synchronized (this.pendingEntries) {
         for (BlockEntityTicker.Entry entry : this.pendingEntries.values()) {
            this.blockEntityEntries.merge(entry.chunkPos, entry, (entry1, entry2) -> {
               entry1.entityPositions.addAll(entry2.entityPositions);
               return (BlockEntityTicker.Entry)entry1;
            });
         }

         this.pendingEntries.clear();
      }
   }

   private static boolean checkStructureValidity(ServerLevel level, ChunkPos chunkPos, BlockPos position) {
      if (!level.getServer().getWorldData().worldGenOptions().generateStructures()) {
         return true;
      } else {
         Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
         if (registry.getTag(LootrTags.Structure.STRUCTURE_BLACKLIST).filter(tag -> tag.size() != 0).isPresent()) {
            return !LootrAPI.isTaggedStructurePresent(level, chunkPos, LootrTags.Structure.STRUCTURE_BLACKLIST, position);
         } else {
            return registry.getTag(LootrTags.Structure.STRUCTURE_WHITELIST).filter(tag -> tag.size() != 0).isPresent()
               ? LootrAPI.isTaggedStructurePresent(level, chunkPos, LootrTags.Structure.STRUCTURE_WHITELIST, position)
               : true;
         }
      }
   }

   private static void replaceEntitiesInChunk(ServerLevel level, BlockEntityTicker.Entry entry) {
      for (BlockPos entityPos : entry.entityPositions()) {
         if (checkStructureValidity(level, entry.chunkPos(), entityPos)) {
            BlockEntity blockEntity = level.getBlockEntity(entityPos);
            if (!(LootrAPI.resolveBlockEntity(blockEntity) instanceof ILootrBlockEntity)) {
               ILootrDataAdapter<BlockEntity> adapter = LootrAPI.getAdapter(blockEntity);
               if (adapter != null) {
                  ResourceKey<LootTable> table = adapter.getLootTable(blockEntity);
                  long seed = adapter.getLootSeed(blockEntity);
                  if (table == null) {
                     if (LootrAPI.shouldWarnNoLootTables()) {
                        LootrAPI.LOG.warn("Potential block entity {} has no loot table in {} ({})", blockEntity, level.dimension(), entityPos);
                     }
                  } else if (!LootrAPI.isLootTableBlacklisted(table)) {
                     BlockState stateAt = level.getBlockState(entityPos);
                     BlockState replacement = LootrAPI.replacementBlockState(stateAt);
                     if (replacement != null) {
                        replaceEntity(level, entityPos, adapter, blockEntity, replacement, table, seed);
                     }
                  }
               }
            }
         }
      }
   }

   private static void replaceEntity(
      ServerLevel level,
      BlockPos entityPos,
      ILootrDataAdapter<BlockEntity> adapter,
      BlockEntity be,
      BlockState replacement,
      ResourceKey<LootTable> table,
      long seed
   ) {
      LootrAPI.preProcess(level, entityPos, be, replacement, table, seed);
      DataToCopy data = PlatformAPI.copySpecificData(be);
      ItemStack itemCopy = ItemStack.EMPTY;
      if (adapter.hasCopyableComponentsViaItem(be)) {
         itemCopy = new ItemStack(be.getBlockState().getBlock());
         be.saveToItem(itemCopy, level.registryAccess());
      }

      adapter.setLootTable(be, null, 0L);
      level.setBlock(entityPos, replacement, 2);
      BlockEntity newBlockEntity = level.getBlockEntity(entityPos);
      if (LootrAPI.resolveBlockEntity(newBlockEntity) instanceof ILootrBlockEntity ibe) {
         if (!itemCopy.isEmpty()) {
            ibe.asBlockEntity().applyComponentsFromItemStack(itemCopy);
         }

         PlatformAPI.restoreSpecificData(data, newBlockEntity);
         ibe.setLootTableInternal(table, seed);
         if (PlatformAPI.shouldDoInitialSave()) {
            ibe.performUpdate();
         }

         LootrAPI.postProcess(level, entityPos, ibe.asBlockEntity(), replacement, table, seed);
      } else {
         LootrAPI.LOG.error("Somehow, replacement result {} is not an ILootrBlockEntity {} at {}", replacement, level.dimension(), entityPos);
      }
   }

   @Nullable
   private static ResourceKey<Level> getServerDimensionIfValid(Level level) {
      if (LootrAPI.getServer() != null && !level.isClientSide()) {
         ResourceKey<Level> dimension = level.dimension();
         return LootrAPI.isDimensionBlocked(dimension) ? null : dimension;
      } else {
         return null;
      }
   }

   public enum ChunkLoadStatus {
      UNLOADED,
      SURROUNDING_CHUNKS_NOT_LOADED,
      NOT_FULLY_LOADED,
      COMPLETE;
   }

   public record Entry(ChunkPos chunkPos, Set<BlockPos> entityPositions) {
      public BlockEntityTicker.ChunkLoadStatus getChunkLoadStatus(ServerLevel level, Set<ChunkPos> loadedChunks) {
         ChunkSource chunkSource = level.getChunkSource();
         if (LootrAPI.isWorldBorderSafe(level, this.chunkPos) && chunkSource.hasChunk(this.chunkPos.x, this.chunkPos.z)) {
            if (!loadedChunks.contains(this.chunkPos)) {
               return BlockEntityTicker.ChunkLoadStatus.NOT_FULLY_LOADED;
            }

            for (int x = this.chunkPos.x - 2; x <= this.chunkPos.x + 2; x++) {
               for (int z = this.chunkPos.z - 2; z <= this.chunkPos.z + 2; z++) {
                  if (x != this.chunkPos.x || z != this.chunkPos.z) {
                     ChunkPos pos = new ChunkPos(x, z);
                     if (LootrAPI.isWorldBorderSafe(level, pos) && !loadedChunks.contains(pos)) {
                        return BlockEntityTicker.ChunkLoadStatus.SURROUNDING_CHUNKS_NOT_LOADED;
                     }
                  }
               }
            }

            return BlockEntityTicker.ChunkLoadStatus.COMPLETE;
         } else {
            return BlockEntityTicker.ChunkLoadStatus.UNLOADED;
         }
      }
   }
}
