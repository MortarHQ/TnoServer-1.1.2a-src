package com.chaosthedude.explorerscompass.worker;

import com.chaosthedude.explorerscompass.ExplorersCompass;
import com.chaosthedude.explorerscompass.items.ExplorersCompassItem;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public class GenericSearchWorker extends StructureSearchWorker<StructurePlacement> {
   public int chunkX;
   public int chunkZ;
   public int length;
   public double nextLength;
   public Direction direction;

   public GenericSearchWorker(
      ServerLevel level,
      Player player,
      ItemStack stack,
      BlockPos startPos,
      List<BlockPos> prevPos,
      StructurePlacement placement,
      List<Structure> structureSet,
      ResourceLocation structureOrGroupId,
      boolean isGroup,
      String managerId
   ) {
      super(level, player, stack, startPos, prevPos, placement, structureSet, structureOrGroupId, isGroup, managerId);
      this.chunkX = startPos.getX() >> 4;
      this.chunkZ = startPos.getZ() >> 4;
      this.nextLength = 1.0;
      this.length = 0;
      this.direction = Direction.UP;
   }

   @Override
   public boolean doWork() {
      if (this.hasWork()) {
         if (this.direction == Direction.NORTH) {
            this.chunkZ--;
         } else if (this.direction == Direction.EAST) {
            this.chunkX++;
         } else if (this.direction == Direction.SOUTH) {
            this.chunkZ++;
         } else if (this.direction == Direction.WEST) {
            this.chunkX--;
         }

         ChunkPos chunkPos = new ChunkPos(this.chunkX, this.chunkZ);
         this.currentPos = new BlockPos(SectionPos.sectionToBlockCoord(chunkPos.x, 8), 0, SectionPos.sectionToBlockCoord(chunkPos.z, 8));
         Pair<BlockPos, Structure> pair = this.getStructureGeneratingAt(chunkPos);
         if (pair != null && !this.shouldIgnore((BlockPos)pair.getFirst())) {
            this.prevPos.add((BlockPos)pair.getFirst());
            this.succeed((BlockPos)pair.getFirst(), (Structure)pair.getSecond());
         }

         this.samples++;
         this.length++;
         if (this.length >= (int)this.nextLength) {
            if (this.direction != Direction.UP) {
               this.nextLength += 0.5;
               this.direction = this.direction.getClockWise();
            } else {
               this.direction = Direction.NORTH;
            }

            this.length = 0;
         }

         int radius = this.getRadius();
         if (radius > 250 && radius / 250 > this.lastRadiusThreshold) {
            if (!this.stack.isEmpty() && this.stack.getItem() == ExplorersCompass.explorersCompass) {
               ((ExplorersCompassItem)this.stack.getItem()).setSearchRadius(this.stack, this.roundRadius(radius, 250), this.player);
            }

            this.lastRadiusThreshold = radius / 250;
         }
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
      return "GenericSearchWorker";
   }

   @Override
   public boolean shouldLogRadius() {
      return true;
   }
}
