package com.chaosthedude.explorerscompass.worker;

import com.chaosthedude.explorerscompass.config.ConfigHandler;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;

public class ConcentricRingsSearchWorker extends StructureSearchWorker<ConcentricRingsStructurePlacement> {
   private List<ChunkPos> potentialChunks;
   private int chunkIndex;
   private double minDistance = Double.MAX_VALUE;
   private Pair<BlockPos, Structure> closest;

   public ConcentricRingsSearchWorker(
      ServerLevel level,
      Player player,
      ItemStack stack,
      BlockPos startPos,
      List<BlockPos> prevPos,
      ConcentricRingsStructurePlacement placement,
      List<Structure> structureSet,
      ResourceLocation structureOrGroupId,
      boolean isGroup,
      String managerId
   ) {
      super(level, player, stack, startPos, prevPos, placement, structureSet, structureOrGroupId, isGroup, managerId);
      this.chunkIndex = 0;
      this.potentialChunks = level.getChunkSource().getGeneratorState().getRingPositionsFor(placement);
      this.finished = !level.getServer().getWorldData().worldGenOptions().generateStructures()
         || this.potentialChunks == null
         || this.potentialChunks.isEmpty();
   }

   @Override
   public boolean hasWork() {
      return !this.finished
         && this.prevPos.size() <= (Integer)ConfigHandler.GENERAL.maxNextSearches.get()
         && this.samples < (Integer)ConfigHandler.GENERAL.maxSamples.get()
         && this.chunkIndex < this.potentialChunks.size();
   }

   @Override
   public boolean doWork() {
      super.doWork();
      if (this.hasWork()) {
         ChunkPos chunkPos = this.potentialChunks.get(this.chunkIndex);
         this.currentPos = new BlockPos(SectionPos.sectionToBlockCoord(chunkPos.x, 8), 0, SectionPos.sectionToBlockCoord(chunkPos.z, 8));
         double distance = this.startPos.distSqr(this.currentPos);
         if (this.closest == null || distance < this.minDistance) {
            Pair<BlockPos, Structure> pair = this.getStructureGeneratingAt(chunkPos);
            if (pair != null && !this.shouldIgnore((BlockPos)pair.getFirst())) {
               this.minDistance = distance;
               this.closest = pair;
            }
         }

         this.samples++;
         this.chunkIndex++;
      }

      if (this.hasWork()) {
         return true;
      }

      if (this.closest != null) {
         this.prevPos.add((BlockPos)this.closest.getFirst());
         this.succeed((BlockPos)this.closest.getFirst(), (Structure)this.closest.getSecond());
      } else if (!this.finished) {
         this.fail();
      }

      return false;
   }

   @Override
   protected String getName() {
      return "ConcentricRingsSearchWorker";
   }

   @Override
   public boolean shouldLogRadius() {
      return false;
   }

   private Pair<BlockPos, Structure> getClosest() {
      List<ChunkPos> list = this.level.getChunkSource().getGeneratorState().getRingPositionsFor(this.placement);
      if (list == null) {
         return null;
      }

      Pair<BlockPos, Structure> closestPair = null;
      double minDistance = Double.MAX_VALUE;
      MutableBlockPos sampleBlockPos = new MutableBlockPos();

      for (ChunkPos chunkPos : list) {
         sampleBlockPos.set(SectionPos.sectionToBlockCoord(chunkPos.x, 8), 32, SectionPos.sectionToBlockCoord(chunkPos.z, 8));
         double distance = sampleBlockPos.distSqr(this.startPos);
         if (closestPair == null || distance < minDistance) {
            Pair<BlockPos, Structure> pair = this.getStructureGeneratingAt(chunkPos);
            if (pair != null) {
               closestPair = pair;
               minDistance = distance;
            }
         }
      }

      return closestPair;
   }
}
