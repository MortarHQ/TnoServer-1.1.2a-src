package com.chronodawn.mixin;

import com.chronodawn.ChronoDawn;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.worldgen.processors.CopyFluidLevelProcessor;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StructureStart.class)
public abstract class StructureStartMixin {
   private static final Set<String> WATERLOGGING_PREVENTION_STRUCTURES = Set.of(
      "master_clock", "guardian_vault", "clockwork_depths", "phantom_catacombs", "phantom_tower", "entropy_crypt"
   );
   @Shadow
   private Structure structure;
   private final Set<BlockPos> waterPositionsBeforePlacement = Collections.synchronizedSet(new HashSet<>());

   @Shadow
   public abstract BoundingBox getBoundingBox();

   @Shadow
   public abstract List<StructurePiece> getPieces();

   @Inject(
      method = "placeInChunk(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/ChunkPos;)V",
      at = @At("HEAD")
   )
   private void removeWaterBeforePlacement(
      WorldGenLevel level,
      StructureManager structureManager,
      ChunkGenerator chunkGenerator,
      RandomSource random,
      BoundingBox chunkBox,
      ChunkPos chunkPos,
      CallbackInfo ci
   ) {
      ResourceLocation structureLocation = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(this.structure);
      if (structureLocation != null
         && structureLocation.getNamespace().equals("chronodawn")
         && WATERLOGGING_PREVENTION_STRUCTURES.contains(structureLocation.getPath())) {
         this.waterPositionsBeforePlacement.clear();
         List<StructurePiece> pieces = this.getPieces();
         int totalWaterRemoved = 0;

         for (StructurePiece piece : pieces) {
            BoundingBox pieceBox = piece.getBoundingBox();
            if (pieceBox.intersects(chunkBox)) {
               int minX = Math.max(pieceBox.minX(), chunkBox.minX());
               int minY = Math.max(pieceBox.minY(), chunkBox.minY());
               int minZ = Math.max(pieceBox.minZ(), chunkBox.minZ());
               int maxX = Math.min(pieceBox.maxX(), chunkBox.maxX());
               int maxY = Math.min(pieceBox.maxY(), chunkBox.maxY());
               int maxZ = Math.min(pieceBox.maxZ(), chunkBox.maxZ());

               for (BlockPos pos : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
                  BlockState state = level.getBlockState(pos);
                  if (state.getFluidState().isSource() && state.getBlock() == Blocks.WATER && !state.is((Block)ModBlocks.DECORATIVE_WATER.get())) {
                     this.waterPositionsBeforePlacement.add(pos.immutable());
                     level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
                     totalWaterRemoved++;
                  }
               }
            }
         }

         if (totalWaterRemoved > 0) {
            ChronoDawn.LOGGER
               .debug("Removed {} Aquifer water blocks in chunk {} for {}", new Object[]{totalWaterRemoved, chunkPos, structureLocation.getPath()});
         }
      }
   }

   @Inject(
      method = "placeInChunk(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/ChunkPos;)V",
      at = @At("RETURN")
   )
   private void convertDecorativeWaterAfterPlacement(
      WorldGenLevel level,
      StructureManager structureManager,
      ChunkGenerator chunkGenerator,
      RandomSource random,
      BoundingBox chunkBox,
      ChunkPos chunkPos,
      CallbackInfo ci
   ) {
      ResourceLocation structureLocation = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(this.structure);
      if (structureLocation != null
         && structureLocation.getNamespace().equals("chronodawn")
         && WATERLOGGING_PREVENTION_STRUCTURES.contains(structureLocation.getPath())) {
         List<StructurePiece> pieces = this.getPieces();
         int totalDecorativeWaterConverted = 0;
         int totalWaterloggedRestored = 0;
         int totalWaterloggedRemoved = 0;

         for (StructurePiece piece : pieces) {
            BoundingBox pieceBox = piece.getBoundingBox();
            if (pieceBox.intersects(chunkBox)) {
               int minX = Math.max(pieceBox.minX(), chunkBox.minX() - 1);
               int minY = Math.max(pieceBox.minY(), chunkBox.minY());
               int minZ = Math.max(pieceBox.minZ(), chunkBox.minZ() - 1);
               int maxX = Math.min(pieceBox.maxX(), chunkBox.maxX() + 1);
               int maxY = Math.min(pieceBox.maxY(), chunkBox.maxY());
               int maxZ = Math.min(pieceBox.maxZ(), chunkBox.maxZ() + 1);

               for (BlockPos pos : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
                  BlockState state = level.getBlockState(pos);
                  if (state.is((Block)ModBlocks.DECORATIVE_WATER.get())) {
                     BlockState newState = Blocks.WATER.defaultBlockState();
                     if (state.hasProperty(BlockStateProperties.LEVEL)) {
                        int fluidLevel = (Integer)state.getValue(BlockStateProperties.LEVEL);
                        newState = (BlockState)newState.setValue(BlockStateProperties.LEVEL, fluidLevel);
                     }

                     level.setBlock(pos, newState, 2);
                     totalDecorativeWaterConverted++;
                  } else if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
                     boolean currentWaterlogged = (Boolean)state.getValue(BlockStateProperties.WATERLOGGED);
                     BlockPos immutablePos = pos.immutable();
                     boolean shouldBeWaterlogged = CopyFluidLevelProcessor.INTENTIONAL_WATERLOGGING.contains(immutablePos);
                     if (currentWaterlogged != shouldBeWaterlogged) {
                        BlockState newState = (BlockState)state.setValue(BlockStateProperties.WATERLOGGED, shouldBeWaterlogged);
                        level.setBlock(pos, newState, 2);
                        if (shouldBeWaterlogged) {
                           totalWaterloggedRestored++;
                           CopyFluidLevelProcessor.INTENTIONAL_WATERLOGGING.remove(immutablePos);
                        } else {
                           totalWaterloggedRemoved++;
                        }
                     } else if (shouldBeWaterlogged) {
                        CopyFluidLevelProcessor.INTENTIONAL_WATERLOGGING.remove(immutablePos);
                     }
                  }
               }
            }
         }

         int remainingPositions = CopyFluidLevelProcessor.INTENTIONAL_WATERLOGGING.size();
         if (remainingPositions > 0) {
            ChronoDawn.LOGGER
               .debug(
                  "INTENTIONAL_WATERLOGGING set still contains {} positions after chunk {} for {}",
                  new Object[]{remainingPositions, chunkPos, structureLocation.getPath()}
               );
         }

         if (remainingPositions > 10000) {
            ChronoDawn.LOGGER.warn("INTENTIONAL_WATERLOGGING set is too large ({}), clearing to prevent memory leak", remainingPositions);
            CopyFluidLevelProcessor.INTENTIONAL_WATERLOGGING.clear();
         }

         if (totalDecorativeWaterConverted > 0) {
            ChronoDawn.LOGGER
               .debug(
                  "Converted {} decorative water blocks in chunk {} for {}", new Object[]{totalDecorativeWaterConverted, chunkPos, structureLocation.getPath()}
               );
         }

         if (totalWaterloggedRestored > 0) {
            ChronoDawn.LOGGER
               .debug(
                  "Restored {} intentional waterlogged blocks in chunk {} for {}",
                  new Object[]{totalWaterloggedRestored, chunkPos, structureLocation.getPath()}
               );
         }

         if (totalWaterloggedRemoved > 0) {
            ChronoDawn.LOGGER
               .debug(
                  "Removed {} unintentional waterlogged blocks in chunk {} for {}",
                  new Object[]{totalWaterloggedRemoved, chunkPos, structureLocation.getPath()}
               );
         }

         this.waterPositionsBeforePlacement.clear();
      } else {
         this.waterPositionsBeforePlacement.clear();
      }
   }
}
