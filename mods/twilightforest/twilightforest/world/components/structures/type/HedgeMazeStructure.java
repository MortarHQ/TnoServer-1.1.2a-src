package twilightforest.world.components.structures.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride.BoundingBoxType;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFMapDecorations;
import twilightforest.init.TFStructureTypes;
import twilightforest.util.WorldUtil;
import twilightforest.world.components.structures.CustomDensitySource;
import twilightforest.world.components.structures.HedgeMazeComponent;
import twilightforest.world.components.structures.util.DecorationClearance;
import twilightforest.world.components.structures.util.LandmarkStructure;

public class HedgeMazeStructure extends LandmarkStructure implements CustomDensitySource {
   public static final MapCodec<HedgeMazeStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> landmarkCodec(instance).apply(instance, HedgeMazeStructure::new)
   );

   public HedgeMazeStructure(
      Optional<DecorationClearance.DecorationConfig> decorationConfig,
      boolean centerInChunk,
      Optional<Holder<MapDecorationType>> structureIcon,
      StructureSettings structureSettings
   ) {
      super(decorationConfig, centerInChunk, structureIcon, structureSettings);
   }

   @Override
   protected StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
      return new HedgeMazeComponent(0, x + 1, y + 4, z + 1);
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.HEDGE_MAZE.get();
   }

   public static HedgeMazeStructure buildStructureConfig(BootstrapContext<Structure> context) {
      return new HedgeMazeStructure(
         Optional.of(new DecorationClearance.DecorationConfig(2.0F, false, true, true)),
         true,
         Optional.of(TFMapDecorations.HEDGE_MAZE),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_HEDGE_MAZE_BIOMES),
            Arrays.stream(MobCategory.values())
               .collect(
                  Collectors.toMap(
                     category -> (MobCategory)category, category -> new StructureSpawnOverride(BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                  )
               ),
            Decoration.SURFACE_STRUCTURES,
            TerrainAdjustment.BEARD_BOX
         )
      );
   }

   @Override
   public DensityFunction getStructureTerraformer(ChunkPos chunkPosAt, StructureStart structurePieceSource) {
      return CustomDensitySource.getInvertedPyramidTerraformer(structurePieceSource, 0, 4);
   }

   @Override
   public int adjustForTerrain(GenerationContext context, int x, int z) {
      return WorldUtil.adjustForTerrain(context, x, z, 24, 4);
   }
}
