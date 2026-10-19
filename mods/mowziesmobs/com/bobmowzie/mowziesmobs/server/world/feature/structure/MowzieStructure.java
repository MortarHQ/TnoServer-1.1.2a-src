package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public abstract class MowzieStructure extends Structure {
   private final ConfigHandler.GenerationConfig config;
   private Set<Holder<Biome>> allowedBiomes;
   private boolean doCheckHeight;
   private boolean doAvoidWater;
   private boolean doAvoidStructures;

   public MowzieStructure(
      StructureSettings settings,
      ConfigHandler.GenerationConfig config,
      Set<Holder<Biome>> allowedBiomes,
      boolean doCheckHeight,
      boolean doAvoidWater,
      boolean doAvoidStructures
   ) {
      super(settings);
      this.config = config;
      this.allowedBiomes = allowedBiomes;
      this.doCheckHeight = doCheckHeight;
      this.doAvoidWater = doAvoidWater;
      this.doAvoidStructures = doAvoidStructures;
   }

   public MowzieStructure(StructureSettings settings, ConfigHandler.GenerationConfig config, Set<Holder<Biome>> allowedBiomes) {
      this(settings, config, allowedBiomes, true, true, true);
   }

   public MowzieStructure(StructureSettings settings, ConfigHandler.GenerationConfig config) {
      super(settings);
      this.config = config;
   }

   public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      return this.checkLocation(context)
         ? onTopOfChunkCenter(context, Types.WORLD_SURFACE_WG, builder -> this.generatePieces(builder, context))
         : Optional.empty();
   }

   public void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
   }

   public boolean checkLocation(GenerationContext context) {
      return this.checkLocation(context, this.config, this.allowedBiomes, this.doCheckHeight, this.doAvoidWater, this.doAvoidStructures);
   }

   protected boolean checkLocation(
      GenerationContext context,
      ConfigHandler.GenerationConfig config,
      Set<Holder<Biome>> allowedBiomes,
      boolean checkHeight,
      boolean avoidWater,
      boolean avoidStructures
   ) {
      if ((Integer)config.generationDistance.get() < 0) {
         return false;
      }

      ChunkPos chunkPos = context.chunkPos();
      BlockPos centerOfChunk = new BlockPos((chunkPos.x << 4) + 7, 0, (chunkPos.z << 4) + 7);
      int i = chunkPos.getMiddleBlockX();
      int j = chunkPos.getMiddleBlockZ();
      int k = context.chunkGenerator().getFirstOccupiedHeight(i, j, Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
      Holder<Biome> biome = context.chunkGenerator()
         .getBiomeSource()
         .getNoiseBiome(QuartPos.fromBlock(i), QuartPos.fromBlock(k), QuartPos.fromBlock(j), context.randomState().sampler());
      if (!allowedBiomes.contains(biome)) {
         return false;
      }

      if (checkHeight) {
         double minHeight = (Double)config.heightMin.get();
         double maxHeight = (Double)config.heightMax.get();
         int landHeight = getLowestY(context, 16, 16);
         if (minHeight != -65.0 && landHeight < minHeight) {
            return false;
         }

         if (maxHeight != -65.0 && landHeight > maxHeight) {
            return false;
         }
      }

      if (avoidWater) {
         ChunkGenerator chunkGenerator = context.chunkGenerator();
         LevelHeightAccessor heightLimitView = context.heightAccessor();
         int centerHeight = chunkGenerator.getBaseHeight(
            centerOfChunk.getX(), centerOfChunk.getZ(), Types.WORLD_SURFACE_WG, heightLimitView, context.randomState()
         );
         NoiseColumn columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ(), heightLimitView, context.randomState());
         BlockState topBlock = columnOfBlocks.getBlock(centerHeight);
         if (!topBlock.getFluidState().isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public Decoration step() {
      return Decoration.SURFACE_STRUCTURES;
   }

   public ConfigHandler.GenerationConfig getConfig() {
      return this.config;
   }
}
