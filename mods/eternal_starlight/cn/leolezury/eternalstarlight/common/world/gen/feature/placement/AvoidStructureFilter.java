package cn.leolezury.eternalstarlight.common.world.gen.feature.placement;

import cn.leolezury.eternalstarlight.common.registry.ESPlacementModifierTypes;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class AvoidStructureFilter extends PlacementFilter {
   public static final MapCodec<AvoidStructureFilter> CODEC = RegistryCodecs.homogeneousList(Registries.STRUCTURE, Structure.DIRECT_CODEC)
      .fieldOf("structures")
      .xmap(AvoidStructureFilter::new, c -> c.structures);
   private final HolderSet<Structure> structures;

   public AvoidStructureFilter(HolderSet<Structure> structures) {
      this.structures = structures;
   }

   protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
      WorldGenLevel level = context.getLevel();
      if (level instanceof WorldGenRegion worldGenRegion) {
         Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
         StructureManager structureManager = worldGenRegion.getLevel().structureManager();
         ChunkAccess chunkAccess = level.getChunk(
            SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()), ChunkStatus.STRUCTURE_REFERENCES
         );
         if (!chunkAccess.getHighestGeneratedStatus().isOrAfter(ChunkStatus.STRUCTURE_REFERENCES)) {
            return false;
         }

         Map<Structure, LongSet> structureReferences = chunkAccess.getAllReferences();

         for (Entry<Structure, LongSet> entry : structureReferences.entrySet()) {
            Structure structure = entry.getKey();
            LongSet references = entry.getValue();
            Optional<ResourceKey<Structure>> key = registry.getResourceKey(structure);
            boolean shouldAvoid = key.isPresent() && this.structures.contains(registry.getHolderOrThrow(key.get()));
            if (shouldAvoid) {
               LongIterator var16 = references.iterator();

               while (var16.hasNext()) {
                  long reference = (Long)var16.next();
                  SectionPos startSection = SectionPos.of(new ChunkPos(reference), worldGenRegion.getMinSection());
                  if (worldGenRegion.hasChunk(startSection.x(), startSection.z())) {
                     ChunkAccess startChunk = worldGenRegion.getChunk(startSection.x(), startSection.z(), ChunkStatus.STRUCTURE_STARTS);
                     StructureStart structureStart = structureManager.getStartForStructure(startSection, structure, startChunk);
                     if (structureStart != null && structureStart.isValid() && structureStart.getBoundingBox().isInside(pos)) {
                        return false;
                     }
                  }
               }
            }
         }
      }

      return true;
   }

   public PlacementModifierType<?> type() {
      return ESPlacementModifierTypes.AVOID_STRUCTURE.get();
   }
}
