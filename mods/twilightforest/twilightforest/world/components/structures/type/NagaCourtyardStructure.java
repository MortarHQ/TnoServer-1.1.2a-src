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
import twilightforest.world.components.structures.courtyard.CourtyardMain;
import twilightforest.world.components.structures.util.ConquerableStructure;
import twilightforest.world.components.structures.util.DecorationClearance;

public class NagaCourtyardStructure extends ConquerableStructure implements CustomDensitySource {
   public static final MapCodec<NagaCourtyardStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> landmarkCodec(instance).apply(instance, NagaCourtyardStructure::new)
   );

   public NagaCourtyardStructure(
      Optional<DecorationClearance.DecorationConfig> decorationConfig,
      boolean centerInChunk,
      Optional<Holder<MapDecorationType>> structureIcon,
      StructureSettings structureSettings
   ) {
      super(decorationConfig, centerInChunk, structureIcon, structureSettings);
   }

   @Override
   protected StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
      return new CourtyardMain(random, 0, x + 1, y, z + 1, context.structureTemplateManager());
   }

   public StructureType<?> type() {
      return (StructureType<?>)TFStructureTypes.NAGA_COURTYARD.get();
   }

   public static NagaCourtyardStructure buildStructureConfig(BootstrapContext<Structure> context) {
      return new NagaCourtyardStructure(
         Optional.of(new DecorationClearance.DecorationConfig(3.0F, false, true, true)),
         true,
         Optional.of(TFMapDecorations.NAGA_COURTYARD),
         new StructureSettings(
            context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_NAGA_COURTYARD_BIOMES),
            Arrays.stream(MobCategory.values())
               .collect(
                  Collectors.toMap(
                     category -> (MobCategory)category, category -> new StructureSpawnOverride(BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                  )
               ),
            Decoration.SURFACE_STRUCTURES,
            TerrainAdjustment.BEARD_THIN
         )
      );
   }

   @Override
   public DensityFunction getStructureTerraformer(ChunkPos chunkPosAt, StructureStart structurePieceSource) {
      return CustomDensitySource.getInvertedPyramidTerraformer(structurePieceSource, 3, 4);
   }

   @Override
   public int adjustForTerrain(GenerationContext context, int x, int z) {
      return WorldUtil.adjustForTerrain(context, x, z, 40, 4) + 2;
   }
}
