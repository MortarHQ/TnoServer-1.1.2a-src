package com.chaosthedude.explorerscompass.worker;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;

public class RandomSpreadSearchWorker extends StructureSearchWorker<RandomSpreadStructurePlacement> {
   private int spacing;
   private int length;
   private int startSectionPosX;
   private int startSectionPosZ;
   private int x;
   private int z;

   public RandomSpreadSearchWorker(
      ServerLevel level,
      Player player,
      ItemStack stack,
      BlockPos startPos,
      List<BlockPos> prevPos,
      RandomSpreadStructurePlacement placement,
      List<Structure> structureSet,
      ResourceLocation structureOrGroupId,
      boolean isGroup,
      String managerId
   ) {
      super(level, player, stack, startPos, prevPos, placement, structureSet, structureOrGroupId, isGroup, managerId);
      this.spacing = placement.spacing();
      this.startSectionPosX = SectionPos.blockToSectionCoord(startPos.getX());
      this.startSectionPosZ = SectionPos.blockToSectionCoord(startPos.getZ());
      this.x = 0;
      this.z = 0;
      this.length = 0;
      this.finished = !level.getServer().getWorldData().worldGenOptions().generateStructures();
   }

   @Override
   public boolean doWork() {
      super.doWork();
      if (this.hasWork()) {
         boolean shouldSampleX = this.x == -this.length || this.x == this.length;
         boolean shouldSampleZ = this.z == -this.length || this.z == this.length;
         if (shouldSampleX || shouldSampleZ) {
            int sampleX = this.startSectionPosX + this.spacing * this.x;
            int sampleZ = this.startSectionPosZ + this.spacing * this.z;
            ChunkPos chunkPos = this.placement.getPotentialStructureChunk(this.level.getSeed(), sampleX, sampleZ);
            this.currentPos = new BlockPos(SectionPos.sectionToBlockCoord(chunkPos.x, 8), 0, SectionPos.sectionToBlockCoord(chunkPos.z, 8));
            Pair<BlockPos, Structure> pair = this.getStructureGeneratingAt(chunkPos);
            this.samples++;
            if (pair != null && !this.shouldIgnore((BlockPos)pair.getFirst())) {
               this.prevPos.add((BlockPos)pair.getFirst());
               this.succeed((BlockPos)pair.getFirst(), (Structure)pair.getSecond());
            }
         }

         this.z++;
         if (this.z > this.length) {
            this.x++;
            if (this.x > this.length) {
               this.length++;
               this.x = -this.length;
               this.z = -this.length;
            } else {
               this.z = -this.length;
            }
         }
      } else if (!this.finished) {
         this.fail();
      }

      if (this.hasWork()) {
         return true;
      }

      if (!this.finished) {
         this.fail();
      }

      return false;
   }

   @Override
   protected String getName() {
      return "RandomSpreadSearchWorker";
   }

   @Override
   public boolean shouldLogRadius() {
      return true;
   }

   private Pair<BlockPos, Structure> getClosest() {
      for (int x = -this.length; x <= this.length; x++) {
         boolean shouldSampleX = x == -this.length || x == this.length;

         for (int z = -this.length; z <= this.length; z++) {
            boolean shouldSampleZ = z == -this.length || z == this.length;
            if (shouldSampleX || shouldSampleZ) {
               int sampleX = this.startSectionPosX + this.spacing * x;
               int sampleZ = this.startSectionPosZ + this.spacing * z;
               ChunkPos chunkPos = this.placement.getPotentialStructureChunk(this.level.getSeed(), sampleX, sampleZ);
               Pair<BlockPos, Structure> pair = this.getStructureGeneratingAt(chunkPos);
               if (pair != null) {
                  return pair;
               }
            }
         }
      }

      return null;
   }
}
