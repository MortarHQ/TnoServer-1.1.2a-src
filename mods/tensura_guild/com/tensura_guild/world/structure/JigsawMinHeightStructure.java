package com.tensura_guild.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tensura_guild.registry.GuildStructureTypes;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

public class JigsawMinHeightStructure extends Structure {
   public static final MapCodec<JigsawMinHeightStructure> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               Structure.settingsCodec(instance),
               StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
               ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
               Codec.intRange(0, 7).fieldOf("size").forGetter(structure -> structure.maxDepth),
               HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
               Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> structure.useExpansionHack),
               Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
               Codec.intRange(1, 256).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
               Codec.INT.fieldOf("minY").orElse(0).forGetter(structure -> structure.minY),
               Codec.intRange(0, 512).optionalFieldOf("terrain_check_width", 0).forGetter(structure -> structure.terrainCheckWidth),
               Codec.intRange(0, 512).optionalFieldOf("terrain_check_depth", 0).forGetter(structure -> structure.terrainCheckDepth),
               Codec.intRange(-256, 256).optionalFieldOf("terrain_check_offset_x", 0).forGetter(structure -> structure.terrainCheckOffsetX),
               Codec.intRange(-256, 256).optionalFieldOf("terrain_check_offset_z", 0).forGetter(structure -> structure.terrainCheckOffsetZ),
               Codec.intRange(1, 32).optionalFieldOf("terrain_check_step", 8).forGetter(structure -> structure.terrainCheckStep),
               Codec.intRange(0, 128).optionalFieldOf("max_terrain_delta", 0).forGetter(structure -> structure.maxTerrainDelta),
               Codec.intRange(0, 16).optionalFieldOf("avoid_other_structures_radius", 0).forGetter(structure -> structure.avoidOtherStructuresRadius)
            )
            .apply(instance, JigsawMinHeightStructure::new)
      )
      .flatXmap(verifyRange(), verifyRange());
   private final Holder<StructureTemplatePool> startPool;
   private final Optional<ResourceLocation> startJigsawName;
   private final int maxDepth;
   private final HeightProvider startHeight;
   private final boolean useExpansionHack;
   private final Optional<Types> projectStartToHeightmap;
   private final int maxDistanceFromCenter;
   private final int minY;
   private final int terrainCheckWidth;
   private final int terrainCheckDepth;
   private final int terrainCheckOffsetX;
   private final int terrainCheckOffsetZ;
   private final int terrainCheckStep;
   private final int maxTerrainDelta;
   private final int avoidOtherStructuresRadius;

   private static Function<JigsawMinHeightStructure, DataResult<JigsawMinHeightStructure>> verifyRange() {
      return structure -> {
         return structure.maxDistanceFromCenter + switch (structure.terrainAdaptation()) {
            case NONE -> 0;
            case BURY, BEARD_THIN, BEARD_BOX, ENCAPSULATE -> 12;
            default -> throw new IncompatibleClassChangeError();
         } > 256 ? DataResult.error(() -> "Structure size including terrain adaptation must not exceed 256") : DataResult.success(structure);
      };
   }

   public JigsawMinHeightStructure(
      StructureSettings settings,
      Holder<StructureTemplatePool> startPool,
      Optional<ResourceLocation> startJigsawName,
      int maxDepth,
      HeightProvider startHeight,
      boolean useExpansionHack,
      Optional<Types> projectStartToHeightmap,
      int maxDistanceFromCenter,
      int minY,
      int terrainCheckWidth,
      int terrainCheckDepth,
      int terrainCheckOffsetX,
      int terrainCheckOffsetZ,
      int terrainCheckStep,
      int maxTerrainDelta,
      int avoidOtherStructuresRadius
   ) {
      super(settings);
      this.startPool = startPool;
      this.startJigsawName = startJigsawName;
      this.maxDepth = maxDepth;
      this.startHeight = startHeight;
      this.useExpansionHack = useExpansionHack;
      this.projectStartToHeightmap = projectStartToHeightmap;
      this.maxDistanceFromCenter = maxDistanceFromCenter;
      this.minY = minY;
      this.terrainCheckWidth = terrainCheckWidth;
      this.terrainCheckDepth = terrainCheckDepth;
      this.terrainCheckOffsetX = terrainCheckOffsetX;
      this.terrainCheckOffsetZ = terrainCheckOffsetZ;
      this.terrainCheckStep = terrainCheckStep;
      this.maxTerrainDelta = maxTerrainDelta;
      this.avoidOtherStructuresRadius = avoidOtherStructuresRadius;
   }

   public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      ChunkPos chunkPos = context.chunkPos();
      int y = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
      if (this.projectStartToHeightmap.isEmpty()) {
         y = Math.max(y, this.minY);
      }

      BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), y, chunkPos.getMinBlockZ());
      return !this.isTooCloseToOtherRandomSpreadStructure(context) && this.hasReasonableTerrain(context, blockPos)
         ? JigsawPlacement.addPieces(
            context,
            this.startPool,
            this.startJigsawName,
            this.maxDepth,
            blockPos,
            this.useExpansionHack,
            this.projectStartToHeightmap,
            this.maxDistanceFromCenter,
            PoolAliasLookup.EMPTY,
            DimensionPadding.ZERO,
            LiquidSettings.APPLY_WATERLOGGING
         )
         : Optional.empty();
   }

   public StructureType<?> type() {
      return (StructureType<?>)GuildStructureTypes.JIGSAW_MIN_HEIGHT.get();
   }

   private boolean hasReasonableTerrain(GenerationContext context, BlockPos startPos) {
      if (this.terrainCheckWidth > 0 && this.terrainCheckDepth > 0 && this.maxTerrainDelta > 0) {
         int centerX = startPos.getX() + this.terrainCheckOffsetX;
         int centerZ = startPos.getZ() + this.terrainCheckOffsetZ;
         int minX = centerX - this.terrainCheckWidth / 2;
         int maxX = centerX + this.terrainCheckWidth / 2;
         int minZ = centerZ - this.terrainCheckDepth / 2;
         int maxZ = centerZ + this.terrainCheckDepth / 2;
         Types heightmap = this.projectStartToHeightmap
            .filter(type -> type != Types.WORLD_SURFACE_WG && type != Types.WORLD_SURFACE)
            .orElse(Types.OCEAN_FLOOR_WG);
         int minHeight = Integer.MAX_VALUE;
         int maxHeight = Integer.MIN_VALUE;

         for (int x = minX; x <= maxX; x += this.terrainCheckStep) {
            for (int z = minZ; z <= maxZ; z += this.terrainCheckStep) {
               int height = context.chunkGenerator().getFirstOccupiedHeight(x, z, heightmap, context.heightAccessor(), context.randomState());
               minHeight = Math.min(minHeight, height);
               maxHeight = Math.max(maxHeight, height);
               if (maxHeight - minHeight > this.maxTerrainDelta) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return true;
      }
   }

   private boolean isTooCloseToOtherRandomSpreadStructure(GenerationContext context) {
      if (this.avoidOtherStructuresRadius <= 0) {
         return false;
      }

      Registry<StructureSet> structureSets = context.registryAccess().registryOrThrow(Registries.STRUCTURE_SET);
      int chunkX = context.chunkPos().x;
      int chunkZ = context.chunkPos().z;

      for (Entry<ResourceKey<StructureSet>, StructureSet> entry : structureSets.entrySet()) {
         StructureSet structureSet = entry.getValue();
         if (!structureSet.structures().stream().anyMatch(selection -> selection.structure().value() == this)) {
            StructurePlacement placement = structureSet.placement();
            if (placement instanceof RandomSpreadStructurePlacement randomSpread) {
               for (int x = chunkX - this.avoidOtherStructuresRadius; x <= chunkX + this.avoidOtherStructuresRadius; x++) {
                  for (int z = chunkZ - this.avoidOtherStructuresRadius; z <= chunkZ + this.avoidOtherStructuresRadius; z++) {
                     ChunkPos potential = randomSpread.getPotentialStructureChunk(context.seed(), x, z);
                     if (potential.x == x && potential.z == z && placement.applyAdditionalChunkRestrictions(x, z, context.seed())) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }
}
