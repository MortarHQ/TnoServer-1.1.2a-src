package mcjty.incontrol.tools.cache;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import mcjty.incontrol.tools.varia.Tools;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.Structure;

public class StructureCache {
   public static final StructureCache CACHE = new StructureCache();
   private final Map<StructureCacheEntry, Boolean> structureCache = new HashMap<>();

   public void clean() {
      this.structureCache.clear();
   }

   public boolean isInAnyStructure(LevelAccessor world, BlockPos pos) {
      ServerLevel sw = Tools.getServerWorld(world);
      ChunkAccess chunk = sw.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.STRUCTURE_REFERENCES, false);
      if (chunk == null) {
         return false;
      }

      Map<Structure, LongSet> references = chunk.getAllReferences();

      for (Entry<Structure, LongSet> e : references.entrySet()) {
         LongSet longs = e.getValue();
         if (!longs.isEmpty()) {
            return true;
         }
      }

      return false;
   }

   public boolean isInStructure(LevelAccessor world, String structure, BlockPos pos) {
      ResourceKey<Level> dimension = Tools.getDimensionKey(world);
      ChunkPos cp = new ChunkPos(pos);
      long cplong = ChunkPos.asLong(cp.x, cp.z);
      StructureCacheEntry entry = new StructureCacheEntry(structure, dimension, cplong);
      if (this.structureCache.containsKey(entry)) {
         return this.structureCache.get(entry);
      }

      ServerLevel sw = Tools.getServerWorld(world);
      ChunkAccess chunk = sw.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.STRUCTURE_REFERENCES, false);
      if (chunk == null) {
         return false;
      }

      Map<Structure, LongSet> references = chunk.getAllReferences();

      for (Entry<Structure, LongSet> e : references.entrySet()) {
         LongSet longs = e.getValue();
         if (!longs.isEmpty()) {
            Structure struct = e.getKey();
            ResourceLocation key = sw.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(struct);
            this.structureCache.put(new StructureCacheEntry(key.toString(), dimension, cplong), true);
         }
      }

      if (this.structureCache.containsKey(entry)) {
         return true;
      }

      this.structureCache.put(entry, false);
      return false;
   }
}
