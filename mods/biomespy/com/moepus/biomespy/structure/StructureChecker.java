package com.moepus.biomespy.structure;

import com.moepus.biomespy.biome.BiomeEnvelopeSelector;
import com.moepus.biomespy.biome.LazyBiomeNoiseChecker;
import com.moepus.biomespy.mixin.StructureCheckAccessor;
import com.moepus.biomespy.mixin.StructureManagerAccessor;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public class StructureChecker {
   static TagKey<Structure> UNINITED_SAFE_STRUCTURES = TagKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("biomespy", "uninit_safe"));

   private static boolean tryAddReference(StructureManager pStructureManager, StructureStart pStructureStart) {
      if (pStructureStart.canBeReferenced()) {
         pStructureManager.addReference(pStructureStart);
         return true;
      } else {
         return false;
      }
   }

   private static StructureCheckResult checkStructureInfo(Object2IntMap<Structure> pStructureChunks, Structure pStructure, boolean pSkipKnownStructures) {
      int i = pStructureChunks.getOrDefault(pStructure, -1);
      return i != -1 && (!pSkipKnownStructures || i == 0) ? StructureCheckResult.START_PRESENT : StructureCheckResult.START_NOT_PRESENT;
   }

   public static Pair<BlockPos, Holder<Structure>> getStructureGeneratingAt(
      Map<Holder<Structure>, BiomeEnvelopeSelector> pStructureHoldersSet,
      LevelReader pLevel,
      StructureManager pStructureManager,
      boolean pSkipKnownStructures,
      StructurePlacement pPlacement,
      ChunkPos pChunkPos,
      ParameterList<Holder<Biome>> parameters
   ) {
      StructureCheckAccessor structureCheckAccessor = (StructureCheckAccessor)((StructureManagerAccessor)pStructureManager).getStructureCheck();
      if (!pPlacement.applyAdditionalChunkRestrictions(pChunkPos.x, pChunkPos.z, structureCheckAccessor.getSeed())) {
         return null;
      }

      Sampler sampler = structureCheckAccessor.getRandomState().sampler();
      Object2IntMap<Structure> structureChunkMap = (Object2IntMap<Structure>)structureCheckAccessor.getLoadedChunks().get(pChunkPos.toLong());
      int x = pChunkPos.getMinBlockX();
      int z = pChunkPos.getMinBlockZ();
      Iterator var12 = pStructureHoldersSet.entrySet().iterator();

      while (true) {
         Holder<Structure> holder;
         StructureCheckResult structurecheckresult;
         while (true) {
            if (!var12.hasNext()) {
               return null;
            }

            Entry<Holder<Structure>, BiomeEnvelopeSelector> entry = (Entry<Holder<Structure>, BiomeEnvelopeSelector>)var12.next();
            holder = entry.getKey();
            if (structureChunkMap != null) {
               structurecheckresult = checkStructureInfo(structureChunkMap, (Structure)holder.value(), pSkipKnownStructures);
               break;
            }

            LazyBiomeNoiseChecker biomeChecker = new LazyBiomeNoiseChecker(entry.getValue(), parameters, x, z);
            if (biomeChecker.matches(sampler)) {
               structurecheckresult = pStructureManager.checkStructurePresence(pChunkPos, (Structure)holder.value(), pPlacement, pSkipKnownStructures);
               break;
            }
         }

         if (structurecheckresult != StructureCheckResult.START_NOT_PRESENT) {
            if (!pSkipKnownStructures) {
               if (structurecheckresult == StructureCheckResult.START_PRESENT) {
                  return Pair.of(pPlacement.getLocatePos(pChunkPos), holder);
               }

               if (holder.is(UNINITED_SAFE_STRUCTURES)) {
                  return Pair.of(pPlacement.getLocatePos(pChunkPos), holder);
               }
            }

            ChunkAccess chunkaccess = pLevel.getChunk(pChunkPos.x, pChunkPos.z, ChunkStatus.STRUCTURE_STARTS);
            StructureStart structurestart = pStructureManager.getStartForStructure(SectionPos.bottomOf(chunkaccess), (Structure)holder.value(), chunkaccess);
            if (structurestart != null && structurestart.isValid() && (!pSkipKnownStructures || tryAddReference(pStructureManager, structurestart))) {
               return Pair.of(pPlacement.getLocatePos(structurestart.getChunkPos()), holder);
            }
         }
      }
   }
}
