package com.chaosthedude.explorerscompass.worker;

import com.chaosthedude.explorerscompass.ExplorersCompass;
import com.chaosthedude.explorerscompass.config.ConfigHandler;
import com.chaosthedude.explorerscompass.items.ExplorersCompassItem;
import com.chaosthedude.explorerscompass.util.StructureUtils;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.neoforged.neoforge.common.WorldWorkerManager;
import net.neoforged.neoforge.common.WorldWorkerManager.IWorker;

public abstract class StructureSearchWorker<T extends StructurePlacement> implements IWorker {
   protected String managerId;
   protected ServerLevel level;
   protected Player player;
   protected ItemStack stack;
   protected BlockPos startPos;
   protected BlockPos currentPos;
   protected T placement;
   protected List<Structure> structureSet;
   protected ResourceLocation structureOrGroupId;
   protected boolean isGroup;
   protected int samples;
   protected boolean finished;
   protected int lastRadiusThreshold;
   protected List<BlockPos> prevPos;

   public StructureSearchWorker(
      ServerLevel level,
      Player player,
      ItemStack stack,
      BlockPos startPos,
      List<BlockPos> prevPos,
      T placement,
      List<Structure> structureSet,
      ResourceLocation structureOrGroupId,
      boolean isGroup,
      String managerId
   ) {
      this.level = level;
      this.player = player;
      this.stack = stack;
      this.startPos = startPos;
      this.prevPos = prevPos != null ? prevPos : new ArrayList<>();
      this.structureSet = structureSet;
      this.structureOrGroupId = structureOrGroupId;
      this.isGroup = isGroup;
      this.placement = placement;
      this.managerId = managerId;
      this.currentPos = startPos;
      this.samples = 0;
      this.finished = !level.getServer().getWorldData().worldGenOptions().generateStructures();
   }

   public void start() {
      if (!this.stack.isEmpty() && this.stack.getItem() == ExplorersCompass.explorersCompass) {
         if ((Integer)ConfigHandler.GENERAL.maxRadius.get() > 0) {
            ExplorersCompass.LOGGER
               .info(
                  "SearchWorkerManager "
                     + this.managerId
                     + ": "
                     + this.getName()
                     + " starting with "
                     + (this.shouldLogRadius() ? ConfigHandler.GENERAL.maxRadius.get() + " max radius, " : "")
                     + ConfigHandler.GENERAL.maxSamples.get()
                     + " max samples"
               );
            WorldWorkerManager.addWorker(this);
         } else {
            this.fail();
         }
      }
   }

   public boolean hasWork() {
      return !this.finished
         && this.prevPos.size() <= (Integer)ConfigHandler.GENERAL.maxNextSearches.get()
         && this.getRadius() < (Integer)ConfigHandler.GENERAL.maxRadius.get()
         && this.samples < (Integer)ConfigHandler.GENERAL.maxSamples.get();
   }

   public boolean doWork() {
      int radius = this.getRadius();
      if (radius > 250 && radius / 250 > this.lastRadiusThreshold) {
         if (!this.stack.isEmpty() && this.stack.getItem() == ExplorersCompass.explorersCompass) {
            ((ExplorersCompassItem)this.stack.getItem()).setSearchRadius(this.stack, this.roundRadius(radius, 250), this.player);
         }

         this.lastRadiusThreshold = radius / 250;
      }

      return false;
   }

   protected Pair<BlockPos, Structure> getStructureGeneratingAt(ChunkPos chunkPos) {
      for (Structure structure : this.structureSet) {
         StructureCheckResult result = this.level.structureManager().checkStructurePresence(chunkPos, structure, this.placement, false);
         if (result != StructureCheckResult.START_NOT_PRESENT) {
            if (result == StructureCheckResult.START_PRESENT) {
               return Pair.of(this.placement.getLocatePos(chunkPos), structure);
            }

            ChunkAccess chunkAccess = this.level.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.STRUCTURE_STARTS);
            StructureStart structureStart = this.level.structureManager().getStartForStructure(SectionPos.bottomOf(chunkAccess), structure, chunkAccess);
            if (structureStart != null && structureStart.isValid()) {
               return Pair.of(this.placement.getLocatePos(structureStart.getChunkPos()), structure);
            }
         }
      }

      return null;
   }

   protected void succeed(BlockPos pos, Structure structure) {
      ExplorersCompass.LOGGER
         .info(
            "SearchWorkerManager "
               + this.managerId
               + ": "
               + this.getName()
               + " succeeded with "
               + (this.shouldLogRadius() ? this.getRadius() + " radius, " : "")
               + this.samples
               + " samples"
         );
      if (!this.stack.isEmpty() && this.stack.getItem() == ExplorersCompass.explorersCompass) {
         ((ExplorersCompassItem)this.stack.getItem())
            .succeed(
               this.stack,
               StructureUtils.getKeyForStructure(this.level, structure),
               this.isGroup,
               pos.getX(),
               pos.getZ(),
               this.prevPos,
               this.samples,
               (Boolean)ConfigHandler.GENERAL.displayCoordinates.get()
            );
      } else {
         ExplorersCompass.LOGGER.error("SearchWorkerManager " + this.managerId + ": " + this.getName() + " found invalid compass after successful search");
      }

      this.finished = true;
   }

   public boolean shouldIgnore(BlockPos pos) {
      return this.prevPos.contains(pos);
   }

   protected void fail() {
      ExplorersCompass.LOGGER
         .info(
            "SearchWorkerManager "
               + this.managerId
               + ": "
               + this.getName()
               + " failed with "
               + (this.shouldLogRadius() ? this.getRadius() + " radius, " : "")
               + this.samples
               + " samples"
         );
      if (!this.stack.isEmpty() && this.stack.getItem() == ExplorersCompass.explorersCompass) {
         ((ExplorersCompassItem)this.stack.getItem()).fail(this.stack, this.structureOrGroupId, this.roundRadius(this.getRadius(), 250), this.samples);
      } else {
         ExplorersCompass.LOGGER.error("SearchWorkerManager " + this.managerId + ": " + this.getName() + " found invalid compass after failed search");
      }

      this.finished = true;
   }

   public void stop() {
      ExplorersCompass.LOGGER
         .info(
            "SearchWorkerManager "
               + this.managerId
               + ": "
               + this.getName()
               + " stopped with "
               + (this.shouldLogRadius() ? this.getRadius() + " radius, " : "")
               + this.samples
               + " samples"
         );
      this.finished = true;
   }

   protected int getRadius() {
      return StructureUtils.getHorizontalDistanceToLocation(this.startPos, this.currentPos.getX(), this.currentPos.getZ());
   }

   protected int roundRadius(int radius, int roundTo) {
      return radius / roundTo * roundTo;
   }

   protected abstract String getName();

   protected abstract boolean shouldLogRadius();
}
