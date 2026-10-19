package com.tensurabossstructure.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tensurabossstructure.registry.ModStructureTypes;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
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
               Codec.INT.fieldOf("minY").orElse(0).forGetter(structure -> structure.minY)
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

   private static Function<JigsawMinHeightStructure, DataResult<JigsawMinHeightStructure>> verifyRange() {
      return structure -> {
         return structure.maxDistanceFromCenter + switch (structure.terrainAdaptation()) {
            case NONE -> 0;
            case BURY, BEARD_THIN, BEARD_BOX -> 12;
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
      int minY
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
   }

   public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      ChunkPos chunkPos = context.chunkPos();
      int centerX = chunkPos.getMiddleBlockX();
      int centerZ = chunkPos.getMiddleBlockZ();
      int y;
      if (this.projectStartToHeightmap.isPresent()) {
         Types projectedType = this.projectStartToHeightmap.get();
         Types heightType;
         if (projectedType == Types.WORLD_SURFACE_WG) {
            heightType = Types.WORLD_SURFACE;
         } else if (projectedType == Types.OCEAN_FLOOR_WG) {
            heightType = Types.OCEAN_FLOOR;
         } else {
            heightType = projectedType;
         }

         int terrainHeight = context.chunkGenerator().getFirstOccupiedHeight(centerX, centerZ, heightType, context.heightAccessor(), context.randomState());
         int seaLevel = context.chunkGenerator().getSeaLevel();
         y = Mth.clamp(terrainHeight, Math.max(this.minY, seaLevel - 5), seaLevel + 10);
      } else {
         y = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
      }

      BlockPos blockPos = new BlockPos(centerX, y, centerZ);
      return JigsawPlacement.addPieces(
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
      );
   }

   public StructureType<?> type() {
      return (StructureType<?>)ModStructureTypes.JIGSAW_MIN_HEIGHT.get();
   }
}
